mvn install
docker stop hello-docker
docker build -t hello-docker .
docker run -p 8080:8080 hello-docker