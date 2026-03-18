FROM container-registry.oracle.com/middleware/weblogic:12.2.1.4

COPY target/incident-management.war /u01/oracle/user_projects/domains/base_domain/autodeploy/           