#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <sys/resource.h>

#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb_image_write.h"

char *fileIN, *fileOUT;
unsigned char *image;
unsigned char *d_image; //device
int width, height, pixelWidth; //meta info de la imagen
int num_filtro;

#define MIN(x, y) (((x) < (y)) ? (x) : (y))

#define BLANCO_Y_NEGRO 1
#define SATURAR 2
#define SEPIA 3
#define BRILLO 4
#define HISTOGRAMA 5

#define  Pr  .299
#define  Pg  .587
#define  Pb  .114



float saturacion; 
int brillo; //ESTÁ EN TANTO POR CIEN 

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

// Retorna un 0 si el maximo es 'a', un 1 si es 'b' y un 2 si es 'c'
int maximo(int a, int b, int c){
	if      (a >= b && a >= c) return 0;
	else if (b >= a && b >= c) return 1;
	else return 2;
}

float GetTime(void); 


int main(int argc, char** argv)
{
  float t1,t2, TiempoSEQ;

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

  if(num_filtro == BLANCO_Y_NEGRO){
	  t1=GetTime();
	  int C; int size = width*height*3;
	  for(int i=0; i<size; i=i+3){
		  C = 299 * image[i];
		  C += 587 * image[i+1];
		  C += 114 * image[i+2];
		  image[i] = image[i+1] = image[i+2] = C/1000;
	  }
	  t2=GetTime();
	  TiempoSEQ = (t2 - t1);
  }
  else if (num_filtro == SATURAR){
	t1=GetTime();
	int size = width*height*3; int R, G, B; double P;
	for(int i=0; i<size; i=i+3){
		R = image[i]; G = image[i+1]; B = image[i+2];
		P = sqrt( R*R*Pr + G*G*Pg + B*B*Pb ) ;
		R= MIN(P+(R-P)*saturacion , 255);
		G= MIN(P+(G-P)*saturacion , 255);
		B= MIN(P+(B-P)*saturacion , 255);
		image[i] = R; image[i+1] = G; image[i+2] = B;
	}
	t2=GetTime();
	TiempoSEQ = (t2 - t1);
  }
  else if (num_filtro == SEPIA){
	//https://abhijitnathwani.github.io/blog/2018/01/08/colortosepia-Image-using-C
	t1=GetTime();
	int size = width*height*3; int R, G, B;
	for(int i=0; i<size; i=i+3){
		R = MIN(255, (image[i]*0.393) + (image[i+1]*0.769) + (image[i+2]*0.189));
		G = MIN(255, (image[i]*0.349) + (image[i+1]*0.686) + (image[i+2]*0.168));
		B = MIN(255, (image[i]*0.272) + (image[i+1]*0.534) + (image[i+2]*0.131));
		image[i] = R; image[i+1] = G; image[i+2] = B;
	}
	t2=GetTime();
	TiempoSEQ = (t2 - t1);
  } 
  else if (num_filtro == BRILLO){
	t1=GetTime();
	int size = width*height*3; int R, G, B;
	for(int i=0; i<size; i=i+3){
		R = MIN(255, (image[i]*brillo/100));
		G = MIN(255, (image[i+1]*brillo/100));
		B = MIN(255, (image[i+2]*brillo)/100);
		image[i] = R; image[i+1] = G; image[i+2] = B;
	}
	t2=GetTime();
	TiempoSEQ = (t2 - t1);
  }  
  else if (num_filtro == HISTOGRAMA) {
	//t1=GetTime();
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
	unsigned char* redChannel = (unsigned char*)malloc(height*width);
	unsigned char* greenChannel = (unsigned char*)malloc(height*width);
	unsigned char* blueChannel = (unsigned char*)malloc(height*width);
	for (int i=0; i<height*width; i++) {
		redChannel[i] = image[i*3];
		greenChannel[i] = image[i*3+1];
		blueChannel[i] = image[i*3+2];
	}
	int R,G,B;
	double multR = (double)255/((double)maxR-(double)minR);
	double multG = (double)255/((double)maxG-(double)minG);
	double multB = (double)255/((double)maxB-(double)minB);
	t1=GetTime();	
	for (int i=0; i<height*width; ++i) {
		R = (redChannel[i]-minR)*multR;
		image[i*3] = MIN(255, R);
		G = (greenChannel[i]-minG)*multG;
		image[i*3+1] = MIN(255, G);
		B = (blueChannel[i]-minB)*multB;
		image[i*3+2] = MIN(255, B);
	}
	t2=GetTime();
	TiempoSEQ = (t2 - t1);
	free(redChannel);
	free(greenChannel);
	free(blueChannel);

	
  }


  printf("TIEMPO SECUENCIAL: %4.6f milseg\n", TiempoSEQ);
  
    
  printf("Escribiendo\n");
  //ESCRITURA DE LA IMAGEN EN SECUENCIAL
  stbi_write_png(fileOUT,width,height,pixelWidth,image,0);
  

}


float GetTime(void) {
  struct timeval tim;
  struct rusage ru;
  getrusage(RUSAGE_SELF, &ru);
  tim=ru.ru_utime;
  return ((double)tim.tv_sec + (double)tim.tv_usec / 1000000.0)*1000.0;
}

