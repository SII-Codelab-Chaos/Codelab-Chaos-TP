FROM openjdk:8-jre-alpine

ADD /opt/${project.build.finalName}-exec.jar /opt/
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/${project.build.finalName}-exec.jar", "--logging.path=/var/logs"]
