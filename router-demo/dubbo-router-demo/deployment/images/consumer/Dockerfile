FROM java:8

WORKDIR /home

COPY dubbo-a.jar /home

COPY start.sh /home

RUN chmod -R 777 /home

ENTRYPOINT ["sh", "/home/start.sh"]