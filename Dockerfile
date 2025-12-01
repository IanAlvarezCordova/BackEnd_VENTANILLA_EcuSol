# ETAPA 1: Construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos dependencias y optimizamos cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos código fuente y empaquetamos
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true -Dfile.encoding=UTF-8

# ETAPA 2: Ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto 8083 (Específico para Ventanilla)
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]