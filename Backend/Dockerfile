FROM openjdk:11
#MAINTAINER example.com
#RUN mkdir -p /tem/demo-0.0.1/lib
# Setting application source code working directory
#WORKDIR /opt/demo-0.0.1/
COPY target/tweet-app.jar tweet-app.jar
# ADD target/demo-0.0.1-SNAPSHOT.jar /opt/demo-0.0.1/lib/

RUN sh -c 'touch tweet-app.jar'
ENTRYPOINT ["java"]
CMD ["-jar", "tweet-app.jar"]