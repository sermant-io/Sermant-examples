#!/bin/bash;

version=1.0.0
name=dubbo-2-5-consumer
dockerFile=Dockerfile
imageName=$name:$version
docker build -f $dockerFile -t $imageName .
docker push $imageName