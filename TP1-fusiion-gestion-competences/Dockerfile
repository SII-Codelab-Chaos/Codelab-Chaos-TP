FROM adoptopenjdk:11-jre-hotspot
MAINTAINER SII
ENTRYPOINT ["java", "-jar", "/usr/share/fusiion/competences.jar", "--logging.path=/var/logs"]
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/fusiion/competences.jar