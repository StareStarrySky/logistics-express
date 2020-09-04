FROM openjdk:8-alpine
ADD build/libs/app.jar /
WORKDIR /
RUN yum -y install fontconfig
RUN fc-cache --forc
CMD ["java", "-jar", "app.jar"]
