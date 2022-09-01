#!/bin/bash;

version=1.0.0
name=spring-cloud-router-provider
dockerFile=Dockerfile
imageName=$name:$version
docker build -f $dockerFile -t $imageName .
docker push $imageName