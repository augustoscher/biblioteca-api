FROM openjdk:8-jdk-alpine
MAINTAINER  Augusto Scher <augustoscher@gmail.com>
VOLUME /tmp
COPY target/biblioteca-api-0.0.1.jar biblioteca-api.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "/biblioteca-api.jar"]