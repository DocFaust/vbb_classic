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

cat > /tmp/configure-datasource.cli <<EOF
embed-server --server-config=standalone.xml --std-out=echo
if (outcome != success) of /subsystem=datasources/jdbc-driver=mysql:read-resource
  /subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.cj.jdbc.Driver)
end-if
if (outcome != success) of /subsystem=datasources/data-source=MySQLPool:read-resource
  /subsystem=datasources/data-source=MySQLPool:add(jndi-name=java:/jdbc/vbb2,connection-url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME},driver-name=mysql,user-name=${DB_USER},password=${DB_PASSWORD},enabled=true,use-ccm=true,min-pool-size=5,max-pool-size=20)
end-if
if (outcome != success) of /subsystem=security/security-domain=secureDomainVBB:read-resource
  /subsystem=security/security-domain=secureDomainVBB:add(cache-type=default)
end-if
if (outcome != success) of /subsystem=security/security-domain=secureDomainVBB/authentication=classic:read-resource
  /subsystem=security/security-domain=secureDomainVBB/authentication=classic:add(login-modules=[{"name"=>"loginRealm","code"=>"Database","flag"=>"required","module-options"=>[("dsJndiName"=>"java:/jdbc/vbb2"),("principalsQuery"=>"SELECT PASSWORD FROM V_USER_ROLE WHERE USERID=?"),("rolesQuery"=>"SELECT NAME, 'Roles' FROM V_USER_ROLE WHERE USERID=?"),("hashAlgorithm"=>"MD5"),("hashEncoding"=>"base64")]}])
end-if
stop-embedded-server
EOF

/opt/jboss/wildfly/bin/jboss-cli.sh --file=/tmp/configure-datasource.cli
rm -f /tmp/configure-datasource.cli

exec /opt/jboss/wildfly/bin/standalone.sh -b "$WILDFLY_BIND_ADDRESS" -bmanagement "$WILDFLY_BIND_MANAGEMENT_ADDRESS"
