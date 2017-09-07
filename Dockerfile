FROM tomcat:7-jre8

# ENV VERSION 1.0.0-SNAPSHOT

RUN mv /usr/local/tomcat/webapps/ROOT /usr/local/tomcat/webapps/ROOT_

COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

