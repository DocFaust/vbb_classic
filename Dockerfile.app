FROM maven:3.9.11-eclipse-temurin-25 AS build
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

FROM jboss/wildfly:23.0.2.Final

USER root
COPY --from=build /workspace/target/vbb-2.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/vbb.war
COPY --from=build /workspace/target/dependencies/mysql-connector-j-8.4.0.jar /opt/jboss/wildfly/mysql-connector-j.jar
RUN mkdir -p /opt/jboss/wildfly/modules/com/mysql/main \
 && cp /opt/jboss/wildfly/mysql-connector-j.jar /opt/jboss/wildfly/modules/com/mysql/main/mysql-connector-j.jar \
 && cat > /opt/jboss/wildfly/modules/com/mysql/main/module.xml <<'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.9" name="com.mysql">
	<resources>
		<resource-root path="mysql-connector-j.jar"/>
	</resources>
	<dependencies>
		<module name="javax.api"/>
		<module name="javax.transaction.api"/>
	</dependencies>
</module>
EOF
COPY docker/entrypoint.sh /opt/jboss/wildfly/bin/entrypoint.sh
RUN sed -i 's/\r$//' /opt/jboss/wildfly/bin/entrypoint.sh \
 && chmod +x /opt/jboss/wildfly/bin/entrypoint.sh
USER jboss

EXPOSE 8080 9990
ENTRYPOINT ["/opt/jboss/wildfly/bin/entrypoint.sh"]
