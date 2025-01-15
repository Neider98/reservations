echo "Ejecutando mvn clean compile..."
mvn clean compile || { echo "Error al compilar el proyecto"; exit 1; }

echo "Iniciando la aplicación Spring Boot..."
mvn spring-boot:run &
SPRING_PID=$!

sleep 10
echo "Abriendo el navegador en http://localhost:8080"
xdg-open "http://localhost:8080" || echo "Por favor, abre manualmente http://localhost:8080"

wait $SPRING_PID