FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} server-app-web.jar
RUN java -Djarmode=layertools -jar server-app-web.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR application
ENV server.port 8080
ENV spring.profiles.active default
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
CMD ["-spring.profiles.active=local"]