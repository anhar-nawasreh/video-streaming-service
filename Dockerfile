FROM bellsoft/liberica-runtime-container:jre-17-stream-musl

WORKDIR /app

COPY target/video-streaming-webapp-0.0.1-SNAPSHOT.jar /app/video-streaming-webapp-0.0.1-SNAPSHOT.jar

EXPOSE 8085

CMD ["java", "-jar", "video-streaming-webapp-0.0.1-SNAPSHOT.jar"]
