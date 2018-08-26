FROM java:8-jre-alpine
COPY target/zuul-gateway-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
CMD ["/usr/bin/java", "-jar", "/app.jar"]