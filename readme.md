# ODM Java GAE Logging

## Introduction

This app exposes a very simple rest endpoint (`/greeting`). There are two log statements, one in the controller and one in the model.

GAE sends the output of `stdout` to Stackdriver. There are three main packages / frameworks for logging in Java
- java.util.logging
- Log4J
- logback

In this project I used logback. The configuration is in the `logback.xml` file.

Logback is used in conjunction with `logstash` which is a package to allow to send structured logs. i.e. in JSON format.

Finally, in this proof of concept, I explore MDC (Mapped Diagnostic Context). Which allows to include key/ value pairs in every log statement. This is done in the filter.

The project uses `maven`.

## Run the app

To run locally:
```bash
./mvnw -DskipTests spring-boot:ru
curl http://localhost:8080/greeting -H 'X_REQUEST_ID: odm-req-1'
```

To upload to GCP, first set the project, and log in using my personal gmail login.
```bash
gcloud config set project odm-gae-java
./mvnw package appengine:deploy
curl http://odm-gae-java.appspot.com/greeting\?name\=olivier%20de%20meulder -H 'X_REQUEST_ID: odm-req-2'
```