CUDA_HOME   = /Soft/cuda/11.2.1
CUDA_HOME   = /Soft/cuda/11.2.1

NVCC        = $(CUDA_HOME)/bin/nvcc
NVCC_FLAGS  = -O3 -Wno-deprecated-gpu-targets -I$(CUDA_HOME)/include -arch=compute_35 --ptxas-options=-v -I$(CUDA_HOME)/sdk/CUDALibraries/common/inc
LD_FLAGS    = -lcudart -Xlinker -rpath,$(CUDA_HOME)/lib64 -I$(CUDA_HOME)/sdk/CUDALibraries/common/lib
EXE	        = filtrar.exe
OBJ	        = filtrar.o
EXE_SEQ		= filtrar_seq.exe
OBJ_SEQ		= filtrar_seq.o

default: $(EXE) $(EXE_SEQ) $(EXE_PAR)

filtrar.o: filtrar.cu
	$(NVCC) -c -o $@ filtrar.cu  $(NVCC_FLAGS)  -I/Soft/stb/20200430

filtrar_seq.o: filtrar_seq.cu
	$(NVCC) -c -o $@ filtrar_seq.cu  $(NVCC_FLAGS)  -I/Soft/stb/20200430   

$(EXE): $(OBJ)
	$(NVCC) $(OBJ) -o $(EXE) $(LD_FLAGS)

$(EXE_SEQ): $(OBJ_SEQ)
	$(NVCC) $(OBJ_SEQ) -o $(EXE_SEQ) $(LD_FLAGS)

clean:
	rm -rf *.o *.exe *output.* FILTRAR.e1* FILTRAR.o1* submit* Out*
