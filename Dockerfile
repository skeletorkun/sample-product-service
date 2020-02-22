#FROM openjdk:8-jdk-alpine
#
#ADD target/marketplace-0.0.1-SNAPSHOT.jar marketplace.jar
#
#EXPOSE 8080
#
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /marketplace.jar" ]
#

FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/marketplace

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/marketplace/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /marketplace/lib
COPY --from=build ${DEPENDENCY}/META-INF /marketplace/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /marketplace

ENTRYPOINT ["java","-cp","marketplace:marketplace/lib/*","com.company.marketplace.MarketplaceApplication"]