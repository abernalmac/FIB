# Tratamiento de imagenes
Este proyecto de Targetes Grafiques i Acceleradors consiste en los siguientes filtros aplicables a imágenes:
- Blanco y negro
- Saturacion
- Sepia
- Brillo
- Ecualización mediante histograma

# Usage
Para ejecutar el código:
- make 
- sbatch job.sh

El script genera automaticamente un conjunto de fotografias con los filtros aplicados.
Se pueden modificar los parámetros de entrada de cada filtro predefinidos en job.sh. Para ello, consultar el usage() en filtrar.cu.

# Autores
Albert Bernal 
Ismael Quiñones
