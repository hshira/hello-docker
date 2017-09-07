mvn install
docker stop hello-docker-app
docker build -t hello-docker .
#mkdir ~/docker
docker run --name hello-docker-app -d -p 8080:8080 -v `pwd`/logs:/usr/local/tomcat/logs hello-docker
sleep 2
tail -f ./logs/catalina.out