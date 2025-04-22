FROM amazoncorretto:21
WORKDIR /app
USER root

COPY build/libs/bdl-backend-0.0.1.jar /app/bdl-backend-0.0.1.jar

ENTRYPOINT ["java", "-jar", "/app/bdl-backend-0.0.1.jar"]
