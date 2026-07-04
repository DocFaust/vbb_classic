#!/usr/bin/env bash
set -euo pipefail

DB_HOST="${DB_HOST:-mariadb}"
DB_PORT="${DB_PORT:-3306}"
DB_NAME="${DB_NAME:-vbb}"
DB_USER="${DB_USER:-vbb}"
DB_PASSWORD="${DB_PASSWORD:-vbb}"
WILDFLY_MGMT_USER="${WILDFLY_MGMT_USER:-mgmt}"
WILDFLY_MGMT_PASSWORD="${WILDFLY_MGMT_PASSWORD:-mgmtpw}"
WILDFLY_BIND_ADDRESS="${WILDFLY_BIND_ADDRESS:-0.0.0.0}"
WILDFLY_BIND_MANAGEMENT_ADDRESS="${WILDFLY_BIND_MANAGEMENT_ADDRESS:-0.0.0.0}"

if [[ ! -f /opt/jboss/wildfly/standalone/configuration/mgmt-users.properties ]] \
  || ! grep -q "^${WILDFLY_MGMT_USER}=" /opt/jboss/wildfly/standalone/configuration/mgmt-users.properties; then
  /opt/jboss/wildfly/bin/add-user.sh "$WILDFLY_MGMT_USER" "$WILDFLY_MGMT_PASSWORD" --silent
fi

/opt/jboss/wildfly/bin/standalone.sh -b "$WILDFLY_BIND_ADDRESS" -bmanagement "$WILDFLY_BIND_MANAGEMENT_ADDRESS" &
WILDFLY_PID=$!

for i in {1..60}; do
  if /opt/jboss/wildfly/bin/jboss-cli.sh -c ":read-attribute(name=server-state)" >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

cat > /tmp/configure-vbb.cli <<EOF
if (outcome != success) of /subsystem=datasources/jdbc-driver=mysql:read-resource
  module add --name=com.mysql --resources=/opt/jboss/wildfly/mysql-connector-j.jar --dependencies=javax.api,javax.transaction.api
  /subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql)
end-if
if (outcome != success) of /subsystem=datasources/data-source=MySQLPool:read-resource
  data-source add --jndi-name=java:/jdbc/vbb2 --name=MySQLPool --connection-url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME} --driver-name=mysql --user-name=${DB_USER} --password=${DB_PASSWORD}
end-if
if (outcome != success) of /subsystem=security/security-domain=secureDomainVBB:read-resource
  /subsystem=security/security-domain=secureDomainVBB:add(cache-type=default)
  /subsystem=security/security-domain=secureDomainVBB/authentication=classic:add(login-modules=[{"code"=>"Database","flag"=>"required","module-options"=>[("dsJndiName"=>"java:/jdbc/vbb2"),("principalsQuery"=>"SELECT PASSWORD FROM V_USER_ROLE WHERE USERID=?"),("rolesQuery"=>"SELECT NAME, 'Roles' FROM V_USER_ROLE WHERE USERID=?"),("hashAlgorithm"=>"MD5"),("hashEncoding"=>"base64")] }])
end-if
EOF

/opt/jboss/wildfly/bin/jboss-cli.sh -c --file=/tmp/configure-vbb.cli || true

wait $WILDFLY_PID
