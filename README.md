# hello-docker
To RUN hello-docker on Amazon EC2 Container Service, do the following --

aws ecs register-task-definition --family hello --container-definitions file://task-hello.json

aws ecs run-task --task-definition hello:version

aws ecs list-tasks

aws ecs describe-tasks --tasks <<task-id>>


Authorization: <auth-token>
GET https://access-aws-e2e.platform.intuit.com/v2/tickets/

POST http://localhost:8080/v1/jersey?method=GET&outboundUrl=https%3A%2F%2Faccess-aws-e2e.platform.intuit.net%2Fv2%2Ftickets%2F
