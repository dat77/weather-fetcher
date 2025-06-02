FROM tomcat:10.1-jdk17
WORKDIR /usr/local/tomcat
COPY target/weather-fetcher.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
