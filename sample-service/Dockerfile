FROM java:8-jre-alpine
COPY target/sample-service-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 5080
CMD ["/usr/bin/java", "-jar", "/app.jar"]