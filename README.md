# hello-docker
To RUN hello-docker on Amazon EC2 Container Service, do the following --

aws ecs register-task-definition --family hello --container-definitions file://task-hello.json

aws ecs run-task --task-definition hello:version

aws ecs list-tasks

aws ecs describe-tasks --tasks <<task-id>>


Authorization: Intuit_IAM_Authentication intuit_appid=Intuit.platform.servicestesting.awstestclient, intuit_app_secret=preprdM3FJkqaTNMlefKm0r4ZRLUZXYgBIXamYAL,intuit_token_type=IAM-Ticket ,intuit_token=V1-220-a3b9c051kl3bdpbg7grqix,intuit_authid=123146624740964,intuit_userid=123146624740964, intuit_realmid=123146624740974
Accept: application/json
Content-Type: application/json
intuit_originatingip: 192.168.1.1

GET https://access-aws-e2e.platform.intuit.com/v2/tickets/

POST http://localhost:8080/v1/jersey?method=GET&outboundUrl=https%3A%2F%2Faccess-aws-e2e.platform.intuit.net%2Fv2%2Ftickets%2F
