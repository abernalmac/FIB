#include <stdio.h>
#include <stdlib.h>

#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb_image_write.h"

char *fileIN, *fileOUT;
unsigned char *image;
unsigned char *d_image; //device
unsigned char *redChannel;
unsigned char *d_redChannel; //device
unsigned char *greenChannel;
unsigned char *d_greenChannel; //device
unsigned char *blueChannel;
unsigned char *d_blueChannel; //device
int width, height, pixelWidth; //meta info de la imagen
int num_filtro;
float saturacion; 
int brillo;

#define MIN(x, y) (((x) < (y)) ? (x) : (y))

#define BLANCO_Y_NEGRO 1
#define SATURAR 2
#define SEPIA 3
#define BRILLO 4
#define HISTOGRAMA 5

void Usage(){
	printf("Usage: ./exe fileIN fileOUT num_filtro [factor brillo o saturacion]\n");
	printf("num filtro:\n");
	printf("\t1: convertir a blanco y negro\n");
	printf("\t2: saturar imagen\n");
		printf("\t\tfactor saturacion: valor en coma flotante y en tanto por uno. Valor menor a 1 es quitar saturacion y mayor a 1 es aumentarla en el factor indicado\n");
		printf("\t\tSe recomienda usar como fileIN las imagenes IMG01.jpg e IMG02.jpg\n");
		printf("\t\tEjemplo de uso: ./filtrar.exe IMG01.jpg Out01.jpg 2 1.5\n");
	printf("\t3: filtro sepia\n");
	printf("\t4: cambiar brillo\n");
		printf("\t\tfactor brillo: valor entero en tanto por cien. Valor menor a 100 es quitar brillo y mayor a 100 es aumentarlo en el factor indicado\n");
		printf("\t\tEjemplo de uso: ./filtrar.exe IMG01.jpg Out01.jpg 2 150\n");
	exit(0);
}

void CheckCudaError(char sms[], int line);

__global__ void KernelBlancoNegro_UsingFloats(unsigned char *image, int N) {
  int elem = (blockIdx.x * blockDim.x + threadIdx.x)*3; //cada thread se ocupa de 3 posiciones de imagen (RGB de 1 pixel)
  if(elem < N){
	float C;
	C = 0.299 * image[elem];
        C += 0.587 * image[elem+1];
        C += 0.114 * image[elem+2];
        image[elem] = image[elem+1] = image[elem+2] = C;
  }
}

__global__ void KernelBlancoNegro(unsigned char *image, int N) {
  int elem = (blockIdx.x * blockDim.x + threadIdx.x)*3; //cada thread se ocupa de 3 posiciones de imagen (RGB de 1 pixel)
  if(elem < N){
	int C;
	C = 299 * image[elem];
        C += 587 * image[elem+1];
        C += 114 * image[elem+2];
        image[elem] = image[elem+1] = image[elem+2] = C/1000;
  }
}

__global__ void KernelSaturar(unsigned char *image, float saturacion, int N) {
  int elem = (blockIdx.x * blockDim.x + threadIdx.x)*3; 
  if(elem < N){
	int R, G, B; float P;
        R = image[elem]; G = image[elem+1]; B = image[elem+2];
        P = sqrt( R*R*0.299 + G*G*0.587 + B*B*0.114 );
        R= MIN(P+(R-P)*saturacion , 255);
        G= MIN(P+(G-P)*saturacion , 255);
        B= MIN(P+(B-P)*saturacion , 255);
        image[elem] = R; image[elem+1] = G; image[elem+2] = B;
  }
}

__global__ void KernelSepia(unsigned char *image, int N) {
  int elem = (blockIdx.x * blockDim.x + threadIdx.x)*3; 
  if(elem < N){
	int R, G, B;
    R = MIN(255, (image[elem]*393 + image[elem+1]*769 + image[elem+2]*189)/1000 );
	G = MIN(255, (image[elem]*349 + image[elem+1]*686 + image[elem+2]*168)/1000 );
	B = MIN(255, (image[elem]*272 + image[elem+1]*534 + image[elem+2]*131)/1000 );
	image[elem] = R; image[elem+1] = G; image[elem+2] = B;
  }
}

__global__ void KernelBrillo(unsigned char *image, float brillo, int N) {
  int elem = (blockIdx.x * blockDim.x + threadIdx.x)*3; 
  if(elem < N){
	int R, G, B;
	R = MIN(255, (image[elem]*brillo/100));
	G = MIN(255, (image[elem+1]*brillo/100));
	B = MIN(255, (image[elem+2]*brillo)/100);
	image[elem] = R; image[elem+1] = G; image[elem+2] = B; 
  }
}

__global__ void KernelHistograma(unsigned char *image, unsigned char *redChannel, unsigned char *greenChannel, unsigned char *blueChannel, int minR, int minG, int minB, double multR, double multG, double multB, int N) {
	int elem = (blockIdx.x * blockDim.x + threadIdx.x);
	if (elem < N) {
		int R, G, B;
		R = (redChannel[elem]-minR)*multR;
		image[elem*3] = MIN(255, R);
		G = (greenChannel[elem]-minG)*multG;
		image[elem*3+1] = MIN(255, G);
		B = (blueChannel[elem]-minB)*multB;
		image[elem*3+2] = MIN(255, B);
	}
}

int main(int argc, char** argv)
{
   
  unsigned int numBytes, numPixels;
  unsigned int nBlocks, nThreads;
  float TiempoTotal, TiempoKernel;
  cudaEvent_t E0, E1, E2, E3;

  // Ficheros de entrada y de salida 
  if (argc == 4) {
	fileIN = argv[1]; fileOUT = argv[2]; num_filtro = atoi(argv[3]);
	if (num_filtro == SATURAR || num_filtro == BRILLO) Usage();
  }
  else if (argc == 5) {
	fileIN = argv[1]; fileOUT = argv[2]; num_filtro = atoi(argv[3]); brillo = atoi(argv[4]); saturacion = atof(argv[4]);
	if (num_filtro == SATURAR || num_filtro == BRILLO){
		if (brillo < 0 || saturacion < 0) Usage();
	} 
	else Usage();
  }
  else {
	Usage();
  }

  printf("Reading image...\n");
  image = stbi_load(fileIN, &width, &height, &pixelWidth, 0);
  if (!image) {
    fprintf(stderr, "Couldn't load image.\n");
     return (-1);
  }
  printf("Image Read. Width : %d, Height : %d, nComp: %d\n",width,height,pixelWidth);

  int count, gpu;
  // Buscar GPU de forma aleatoria. 
  cudaGetDeviceCount(&count);
  srand(time(NULL));
  gpu = (rand()>>3) % count;
  cudaSetDevice(gpu);

  // numero de Threads, bloques, bytes:
  nThreads = 1024;
  numPixels = width * height;
  numBytes = numPixels * pixelWidth; //pixelWidth=3
  nBlocks = (numPixels+nThreads-1)/nThreads;

  //creamos eventos
  cudaEventCreate(&E0);
  cudaEventCreate(&E1);
  cudaEventCreate(&E2);
  cudaEventCreate(&E3);

  // guardamos tiempos previos a coger espacio en GPU y pasarle datos
  cudaEventRecord(E0, 0);
  cudaEventSynchronize(E0); 

  // Obtener Memoria en el device
  cudaMalloc((unsigned char**)&d_image, numBytes);
  CheckCudaError((char *) "Obtener Memoria en el device", __LINE__);

  // Copiar datos desde el host en el device 
  cudaMemcpy(d_image, image, numBytes, cudaMemcpyHostToDevice);
  CheckCudaError((char *) "Copiar Datos Host --> Device", __LINE__);

  // guardamos tiempos posteriores a gestion de datos y previos al kernel
  cudaEventRecord(E1, 0);
  cudaEventSynchronize(E1);


  //ejecucion kernel:
  if(num_filtro == BLANCO_Y_NEGRO){
	KernelBlancoNegro<<<nBlocks, nThreads>>>(d_image, numBytes); 
  } else if (num_filtro == SATURAR){
	KernelSaturar<<<nBlocks, nThreads>>>(d_image, saturacion, numBytes);
  } else if (num_filtro == SEPIA){
	KernelSepia<<<nBlocks, nThreads>>>(d_image, numBytes);
  } else if (num_filtro == BRILLO){
	KernelBrillo<<<nBlocks, nThreads>>>(d_image, brillo, numBytes);
  } else if (num_filtro == HISTOGRAMA){
	int redH[256] = {0}; 
	int greenH[256] = {0}; 
	int blueH[256] = {0}; 
	int size = width*height*3; 
	for (int i=0; i<size; i=i+3) {
		redH[image[i]]++;
		greenH[image[i+1]]++;
		blueH[image[i+2]]++;
	}
	int minR, minG, minB, maxR, maxG, maxB;
	bool found = false;
	for (int i=0; found==false && i<256; ++i) {
		if (redH[i]>3) {
			minR=i;
			found = true;
		}
	}
	found = false;
	for (int i=0; found==false && i<256; ++i) {
		if (greenH[i]>3) {
			minG=i;
			found = true;
		}
	}
	found = false;
	for (int i=0; found==false && i<256; ++i) {
		if (blueH[i]>3) {
			minB=i;
			found = true;
		}
	}
	found = false;
	for (int i=255; found==false && i>0; --i) {
		if (redH[i]>3) {
			maxR=i;
			found = true;
		}
	}
	found = false;
	for (int i=255; found==false && i>0; --i) {
		if (greenH[i]>3) {
			maxG=i;
			found = true;
		}
	}
	found = false;
	for (int i=255; found==false && i>0; --i) {
		if (blueH[i]>3) {
			maxB=i;
			found = true;
		}
	}
	redChannel = (unsigned char*)malloc(height*width);
	greenChannel = (unsigned char*)malloc(height*width);
	blueChannel = (unsigned char*)malloc(height*width);
	for (int i=0; i<height*width; i++) {
		redChannel[i] = image[i*3];
		greenChannel[i] = image[i*3+1];
		blueChannel[i] = image[i*3+2];
	}

	double multR = (double)255/((double)maxR-(double)minR);
	double multG = (double)255/((double)maxG-(double)minG);
	double multB = (double)255/((double)maxB-(double)minB);

	cudaMalloc((unsigned char**)&d_redChannel, numPixels);
	CheckCudaError((char *) "Obtener Memoria en el device", __LINE__);
	cudaMemcpy(d_redChannel, redChannel, numPixels, cudaMemcpyHostToDevice);
	CheckCudaError((char *) "Copiar Datos Host --> Device", __LINE__);
	
	cudaMalloc((unsigned char**)&d_greenChannel, numPixels);
	CheckCudaError((char *) "Obtener Memoria en el device", __LINE__);
	cudaMemcpy(d_greenChannel, greenChannel, numPixels, cudaMemcpyHostToDevice);
	CheckCudaError((char *) "Copiar Datos Host --> Device", __LINE__);
	
	cudaMalloc((unsigned char**)&d_blueChannel, numPixels);
	CheckCudaError((char *) "Obtener Memoria en el device", __LINE__);
	cudaMemcpy(d_blueChannel, blueChannel, numPixels, cudaMemcpyHostToDevice);
	CheckCudaError((char *) "Copiar Datos Host --> Device", __LINE__);

	KernelHistograma<<<nBlocks, nThreads>>>(d_image, d_redChannel, d_greenChannel, d_blueChannel, minR, minG, minB, multR, multG, multB, numPixels);
	CheckCudaError((char *) "Invocar Kernel", __LINE__);

	cudaFree(d_redChannel);
	cudaFree(d_greenChannel);
	cudaFree(d_blueChannel);
  }
  //miramos errores invocacion de los kernels ByN, Sat, Sepia, Brillo.
  CheckCudaError((char *) "Invocar Kernel", __LINE__);

  // guardamos tiempos posteriores al kernel y previos a la copia D->H
  cudaEventRecord(E2, 0);
  cudaEventSynchronize(E2);

  // Obtener el resultado desde el host 
  cudaMemcpy(image, d_image, numBytes, cudaMemcpyDeviceToHost);
  CheckCudaError((char *) "Copiar Datos Device --> Host", __LINE__);

  // Liberar Memoria del device 
  cudaFree(d_image); 

  // guardamos tiempos finales 
  cudaEventRecord(E3, 0);
  cudaEventSynchronize(E3);

  // tiempos
  cudaEventElapsedTime(&TiempoTotal,  E0, E3);
  cudaEventElapsedTime(&TiempoKernel, E1, E2);

  // reporte
  printf("\nREPORTE FINAL:\n");
  printf("Tiempo Global: %4.6f milseg\n", TiempoTotal);
  printf("Tiempo Kernel: %4.6f milseg\n", TiempoKernel);

  //destruccion eventos
  cudaEventDestroy(E0); cudaEventDestroy(E1); cudaEventDestroy(E2); cudaEventDestroy(E3);

    
  printf("Escribiendo\n");
  //ESCRITURA DE LA IMAGEN EN SECUENCIAL
  stbi_write_png(fileOUT,width,height,pixelWidth,image,0);

}

void CheckCudaError(char sms[], int line) {
  cudaError_t error;
 
  error = cudaGetLastError();
  if (error) {
    printf("(ERROR) %s - %s in %s at line %d\n", sms, cudaGetErrorString(error), __FILE__, line);
    exit(EXIT_FAILURE);
  }
  //else printf("(OK) %s \n", sms);
}

