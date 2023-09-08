FROM quay.io/wildfly/wildfly:26.0.0.Final
COPY ./cli/* /opt/jboss/wildfly/
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
RUN ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
RUN ["/opt/jboss/wildfly/bin/jboss-cli.sh", "--file=/opt/jboss/wildfly/db.cli"]
RUN ["/opt/jboss/wildfly/bin/jboss-cli.sh", "--file=/opt/jboss/wildfly/mail.cli"]
RUN ["/opt/jboss/wildfly/bin/jboss-cli.sh", "--file=/opt/jboss/wildfly/security.cli"]
RUN ["/opt/jboss/wildfly/bin/shutdown.sh"]
ADD ./target/*.war /opt/jboss/wildfly/standalone/deployments/
RUN ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

# Run with custom configuration
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
