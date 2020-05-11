#!/bin/bash

docker run \
  --rm \
  -it \
  -v $HOME/.m2/repository:/root/.m2/repository \
  -v $(pwd):/app \
  --name graalvm oracle/graalvm-ce:20.0.0-java11-maven363 /bin/bash -c 'mvn clean package -P native -f /app/pom.xml'
