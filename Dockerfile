FROM maven:latest as stage1
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app

RUN mvn clean package -DskipTests
FROM openjdk:19 as final
COPY --from=stage1 /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
