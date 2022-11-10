#FROM maven as build
#WORKDIR /app
#COPY . .
#RUN mvn install

FROM openjdk:8-jre
WORKDIR /app
#COPY --from=build /app/target/*.jar /app/
COPY target/*.jar /app/
EXPOSE 9090
CMD ["java", "-jar", "apartment-online-market-0.0.1-SNAPSHOT.jar"]