FROM openjdk:8-alpine
ADD build/libs/app.jar /
WORKDIR /
RUN apk add --update fontconfig && rm -rf /var/cache/apk/*
CMD ["java", "-jar", "app.jar"]
