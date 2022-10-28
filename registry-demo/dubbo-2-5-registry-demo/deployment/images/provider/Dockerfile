FROM java:8

WORKDIR /home

COPY dubbo-2-5-provider.jar /home

COPY start.sh /home

RUN chmod -R 777 /home

ENTRYPOINT ["sh", "/home/start.sh"]