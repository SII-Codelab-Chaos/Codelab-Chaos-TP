FROM openjdk:8-jre-alpine

RUN apk add --update curl && \
    rm -rf /var/cache/apk/*

ADD target/fusiion-gestion-competences-0.6-SNAPSHOT-exec.jar /opt/
EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:+UseG1GC", "-jar", "/opt/fusiion-gestion-competences-0.6-SNAPSHOT-exec.jar", "--logging.path=/var/logs"]
