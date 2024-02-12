FROM mcr.microsoft.com/openjdk/jdk:17-distroless
CMD ["./mvnw", "clean", "package"]
COPY ./target/*.jar ./build/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]