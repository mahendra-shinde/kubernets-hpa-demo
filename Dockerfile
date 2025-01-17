FROM maven:3-jdk-11-slim as build
COPY . .
RUN mvn package -DskipTests

FROM openjdk:11-jre-slim as final
COPY --from=build target/image-resizer-1.0.jar /image-resizer.jar
ENTRYPOINT ["java","-jar","image-resizer.jar"]
