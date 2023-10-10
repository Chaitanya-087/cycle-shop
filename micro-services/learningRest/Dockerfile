# # Build Stage
# FROM maven:3.8.4-openjdk-17-slim AS build
# WORKDIR /app
# COPY . .
# RUN mvn install -DskipTests

# # Runtime Stage
# FROM openjdk:17-slim
# WORKDIR /app
# COPY --from=build /app/target/*.jar app.jar
# EXPOSE 8080
# CMD ["java", "-jar", "app.jar"]


#
# Build stage
#
# FROM maven:3.8.3-openjdk-17 AS build
# COPY src /home/app/src
# COPY pom.xml /home/app
# RUN mvn -f /home/app/pom.xml clean package -DskipTests
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","/home/app/target/spring_rest_docker.jar"]


FROM maven:3.8.3-openjdk-17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]