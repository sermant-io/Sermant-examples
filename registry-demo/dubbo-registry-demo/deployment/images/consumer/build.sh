#!/bin/bash;

version=1.0.0
name=dubbo-registry-consumer
dockerFile=Dockerfile
imageName=$name:$version
docker build -f $dockerFile -t $imageName .
docker push $imageName