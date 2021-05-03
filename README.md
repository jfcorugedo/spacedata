# Spacedata API

This API is design to give you some interesting data about spaceships, both fictional and real ones.

Behind the scenes it is intended to be fully reactive, using Spring Webflux and Netty.

## Technology

- Spring Boot
- Spring Webflux
- WireMock

## Pre-requisites

* JDK 11 or above
* Maven 3.3.9 or above

## Build the project

Just run a build using maven:

```shell
mvn clean install
```

After a few seconds you should see the green message:

```shell
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  11.029 s
[INFO] Finished at: 2021-05-03T13:15:14+02:00
[INFO] ------------------------------------------------------------------------
```

## Start the server

It does not require any external parameter from outside, but **make user you have internet connection**, since it will
consume `api.spacexdata.com` API.

```java
cd target
        java-jar spacedata-0.0.1-SNAPSHOT.jar
```

After a couple of seconds you should see the output log that tells you the server is running:

```shell
INFO 5525 --- [           main] o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port 8080
INFO 5525 --- [           main] c.j.spacedata.SpacedataApplication       : Started SpacedataApplication in 1.748 seconds (JVM running for 2.198)
```

## Testing the API

Just perform a GET operation over the endpoint `/api/v1/rockets/{name}` with a valid SpaceX rocket name (ie: falcon9).

You can use UI tools like Postman or RestClient, or just shell commands:

```shell
curl --request GET http://localhost:8080/api/v1/rockets/falcon9
```

If everything goes well, it should return a JSON:

```json
{
  "rocket_name": "Falcon 9",
  "cost_per_launch": 50000000,
  "success_rate_pct": 97.0
}
```