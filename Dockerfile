FROM eclipse-temurin:8-jdk-alpine as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY ./src ./src
RUN ./mvnw clean install -Dmaven.test.skip=true

FROM eclipse-temurin:8-jre-alpine
WORKDIR /app
EXPOSE 9090
COPY --from=builder /app/target/*.jar /app/*.jar
CMD ["java", "-jar", "/app/*.jar"]