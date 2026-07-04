FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY pom.xml ./
COPY src ./src
COPY cli ./cli
COPY db ./db
COPY README.md ./
COPY checkstyle.xml ./
COPY spotbugs-exclude.xml ./
COPY lombok.config ./

RUN mvn -B -DskipTests package \
 && mvn -B dependency:copy -Dartifact=com.mysql:mysql-connector-j:8.4.0 -DoutputDirectory=target/dependencies

FROM quay.io/wildfly/wildfly:26.1.3.Final

USER root
COPY --from=build /workspace/target/vbb-2.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/vbb.war
COPY --from=build /workspace/target/dependencies/mysql-connector-j-8.4.0.jar /opt/jboss/wildfly/mysql-connector-j.jar
COPY docker/entrypoint.sh /opt/jboss/wildfly/bin/entrypoint.sh
RUN chmod +x /opt/jboss/wildfly/bin/entrypoint.sh
USER jboss

EXPOSE 8080 9990
ENTRYPOINT ["/opt/jboss/wildfly/bin/entrypoint.sh"]
