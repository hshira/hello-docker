# hello-docker
To RUN hello-docker on Amazon EC2 Container Service, do the following --

aws ecs register-task-definition --family hello --container-definitions file://task-def.json

aws ecs run-task --task-definition hello:version

aws ecs list-tasks

aws ecs describe-tasks --tasks <<task-id>>
