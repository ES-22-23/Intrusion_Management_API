FROM maven:3.8.6-amazoncorretto-11

COPY . /app
WORKDIR /app

RUN mvn package -DskipTests=true
RUN mv target/imapi*.jar intrusion-management-API.jar

EXPOSE 8083

ENTRYPOINT [ "java", "-jar", "intrusion-management-API.jar" ]
