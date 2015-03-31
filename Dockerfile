FROM tutum/tomcat

# ENV VERSION 1.0.0-SNAPSHOT

RUN mv tomcat/webapps/ROOT tomcat/webapps/ROOT_

COPY target/*.war /tomcat/webapps/ROOT.war
