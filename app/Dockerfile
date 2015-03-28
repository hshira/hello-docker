FROM tutum/tomcat

ENV VERSION 1.0.0-SNAPSHOT

RUN mv tomcat/webapps/ROOT tomcat/webapps/ROOT_

COPY /home/shippable/workspace/src/bitbucket.com/hshira/web2020/target/web2020.war /tomcat/webapps/ROOT.war
