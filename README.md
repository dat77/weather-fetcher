# weather-fetcher
Application fetches the current weather from a public weather API

## Description

Weather-fetcher is a web-application based on Jakarta RESTful WS API standart in Jersey implementation


### Compile and run the project

Git bash:
```bash
# build
$ ./mvnw clean install
```

Command promt:
```bash
# build
mvn clean install
```

The `target/weather-fetcher.war` artifact should be deployed into any servlet container (e.g. [Tomcat](https://dlcdn.apache.org/tomcat/tomcat-10/v10.1.41/bin/apache-tomcat-10.1.41-windows-x64.zip) )


## Docker:

```bash
# Image build
docker build -t weather-fetcher .

# run
docker run -d -p 8080:8080 weather-fetcher
```

Application is accessible now via the link `http://localhost:8080/weather-fetcher/`
