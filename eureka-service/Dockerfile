FROM java:8-jre-alpine
COPY target/eureka-service-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8761
CMD ["/usr/bin/java", "-jar", "/app.jar"]