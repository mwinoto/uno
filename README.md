# IMPORTANT!

This is not the root of the project.
You need to go one level down.

> cd uno 

# Setup

In application.properties update the access key and the secret key with values that generated from your AWS account.
The other settings should auto create a table in DynamoDB when the application starts up.

# Building
> uno%$ mvn clean install

# Running locally
> uno$ mvn spring-boot:run 

## Testing locally

In your browser go to

> http://localhost:8080/date/difference?fromDate=01.01.2020&toDate=05.01.2020

# Docker

These are instructions for Linux 

> uno$ sudo docker build -t uno/date .
> uno$ sudo docker run -p 8080:8080 -t uno/date

# Pushing to ECS

Create an ECR repository and give it a name. I've named mine date-diff.
Run the following commands. You may also need to run

> aws configure

If you have not already done so.

> aws ecr get-login --region us-east-2 --no-include-email
> sudo <output of above>
> sudo docker image ls
> sudo docker tag <image>:latest 345495424718.dkr.ecr.us-east-2.amazonaws.com/date-diff:latest
> sudo docker push 345495424718.dkr.ecr.us-east-2.amazonaws.com/date-diff:latest