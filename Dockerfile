FROM openjdk:17-alpine
ADD target/search-1.0.0-SNAPSHOT.jar nagp-search-service.jar
ENTRYPOINT ["java", "-jar", "nagp-search-service.jar"]