FROM maven:3-jdk-11-slim as build
COPY . .
RUN ["mvn","package"]

FROM openjdk:11-slim
COPY --from=build target/image-resizer-*.jar /image-resizer.jar
ENTRYPOINT ["java","-jar","image-resizer.jar"]
