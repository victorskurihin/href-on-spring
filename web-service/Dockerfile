FROM docker.io/vskurikhin/href:latest

FROM openjdk:8-jdk

MAINTAINER Victor N. Skurikhin
ENV TARGET=web-service
ENV TARGET_JAR=$TARGET.jar
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
ENV TARGET_DIR=$PROJECT_DIR/$TARGET/target
WORKDIR $TARGET_DIR

COPY --from=0 $TARGET_DIR/$TARGET.jar $PROJECT_DIR/

EXPOSE 8080

CMD [ "java", "-jar", "/opt/project/web-service.jar" ]
