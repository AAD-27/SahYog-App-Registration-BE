FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY target/ms-app-reg-*.jar app.jar

EXPOSE 8090
HEALTHCHECK --interval=5s --timeout=3s --start-period=20s --retries=12 \
  CMD bash -c '</dev/tcp/127.0.0.1/8090'

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
