# About

This is a Spring Boot application that returns the number of days between 2 dates, exclusive of the start and end dates.
E.g. fromDate=01.01.2020, toDate=03.01.2020, returns 1. 

A log of the result is stored in DynamoDB. The table needed for this is created at application start up. 
So only the credentials needed access your DB should be needed.

The record contains: fromDate, toDate, difference, id.
Where id is just the Java nano time for now.

# IMPORTANT!

This is not the root of the project.
You need to go one level down before running any of these commands.
These intructions are for Linux!

> cd uno 

# Setup

In application.properties update the access key and the secret key with values that generated from your AWS account.
The other settings should auto create a table in DynamoDB when the application starts up.

# Building
> uno%$ mvn clean install

# Running locally
> uno$ mvn spring-boot:run 

# Docker

These are instructions for Linux.

> uno$ sudo docker build -t uno/date .

> uno$ sudo docker run -p 8080:8080 -t uno/date

# Testing locally

In your browser go to

> http://localhost:8080/date/difference?fromDate=01.01.2020&toDate=05.01.2020

# Works in progress

## Error responses

There are suitable HTTP codes for the error responses, but the error handling is pretty bad.

## Pushing to ECS

Create an ECR repository and give it a name. I've named mine date-diff.
Run the following commands. You may also need to run

> aws configure

If you have not already done so.

> aws ecr get-login --region us-east-2 --no-include-email

> sudo <output of above>

> sudo docker image ls

> sudo docker tag <image>:latest 345495424718.dkr.ecr.us-east-2.amazonaws.com/date-diff:latest

> sudo docker push 345495424718.dkr.ecr.us-east-2.amazonaws.com/date-diff:latest

