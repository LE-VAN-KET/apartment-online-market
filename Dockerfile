FROM eclipse-temurin:8-jdk-alpine as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY ./src ./src
RUN ./mvnw clean install -DskipTests=true

FROM eclipse-temurin:8-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/.*jar
EXPOSE 9090
CMD ["java", "-jar", "/app/*.jar"]