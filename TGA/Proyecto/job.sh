#!/bin/bash

### Directivas para el gestor de colas
#SBATCH --job-name=MultiGPU
#SBATCH -D .
#SBATCH --output=submit-FILTRO.o%j
#SBATCH --error=submit-FILTRO.e%j
#SBATCH -A cuda
#SBATCH -p cuda
### Se piden 4 GPUs 
#SBATCH --gres=gpu:4

export PATH=/Soft/cuda/11.2.1/bin:$PATH

./filtrar.exe IMG01.jpg Out_ByN.jpg 1 
./filtrar.exe IMG01.jpg Out_MasSaturacion.jpg 2 1.8
./filtrar.exe IMG01.jpg Out_MenosSaturacion.jpg 2 0.5
./filtrar.exe IMG01.jpg Out_Sepia.jpg 3
./filtrar.exe IMG01.jpg Out_Brillo150.jpg 4 150
./filtrar.exe IMG01.jpg Out_Brillo50.jpg 4 50
./filtrar.exe default.bmp Out_Ecualizar.jpg 5



 



