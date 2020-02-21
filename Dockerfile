FROM maven:3.5-jdk-8
ADD target/marketplace-0.0.1-SNAPSHOT.jar marketplace.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /marketplace.jar" ]