FROM openjdk:17-oracle
VOLUME /tmp
ARG JAR_FILE
EXPOSE 8089
COPY target/*.jar transaction.jar
ENTRYPOINT ["java","-jar","transaction.jar"]