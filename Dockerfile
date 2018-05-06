FROM java:8

RUN mkdir -p /var/log/account

RUN mkdir -p /manza/app/account

WORKDIR /manza/app/account

COPY . /manza/app/account

EXPOSE 8080

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/account.jar"]
