# Tratamiento de imágenes
Este proyecto de Targetes Gràfiques i Acceleradors consiste en los siguientes filtros aplicables a imágenes:
- Blanco y negro
- Saturación
- Sepia
- Brillo
- Ecualización mediante histograma

# Usage
Para ejecutar el código:
- make 
- sbatch job.sh

El script genera automáticamente un conjunto de fotografías con los filtros aplicados.
Se pueden modificar los parámetros de entrada de cada filtro predefinidos en job.sh. Para ello, consultar el usage() en filtrar.cu.

# Autores
Albert Bernal 
Ismael Quiñones
