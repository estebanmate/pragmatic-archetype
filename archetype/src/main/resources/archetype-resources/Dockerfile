#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
FROM maven:3.6.2-jdk-11-slim AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=builder /usr/src/app/target/${artifactId}-${version}.jar /usr/app/${artifactId}-${version}.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/app/${artifactId}-${version}.jar"]