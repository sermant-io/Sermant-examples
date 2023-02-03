#!/bin/bash;

version=1.0.0
name=providerA
dockerFile=Dockerfile
imageName=$name:$version
docker build -f $dockerFile -t $imageName .
docker push $imageName