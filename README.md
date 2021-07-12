# Demo files for the "holy-graal" talk

In this repository you'll find all the demo projects I showed at the GraalVM talk. This includes...


### functionGraphDemo

This demo shows the polyglott abilities of the GraalVM and is taken from here:  
https://github.com/graalvm/graalvm-demos/tree/master/functionGraphDemo
All the information about how to use it can be found in the corresponding README.


### hello-world

"Hello World" example for showing how long it takes to compile the tiniest program to a native binary.  
To try it out execute the following steps:
```shell
javac HelloWorld.java
native-image HelloWorld helloWorld
```
To see the difference in "start-up" time, use
```shell
gtime -p java HelloWorld
gtime -p ./helloWorld
```

### jvm-benchmark

Benchmark to show the difference the JIT compilers in HotSpot vs. GraalVM using the Java Microbenchmarking Harness framework for (more) reliable results.  
The original code comes from https://github.com/chrisseaton/graalvm-ten-things/blob/master/TopTen.java and finds the top twenty words of a text file, in our case the bible ;-)
To make the results clearer you should take longer text, e.g. copy the bible 20 times in the same file (20xbible.txt).  
To try it out execute the following steps (using SDKMAN! to change Java SDKs):
```shell
mvn clean package

sdk use java 11.0.11.hs-adpt
java -jar target/benchmarks.jar

sdk use java 21.1.0.r11-grl
java -jar target/benchmarks.jar
```
The results of the iterations are printed out by the tool.

### member-service

Service written in Quarkus to show the cold start problem (e.g. in a serverless environment) can be solved by using native images.
The tool itself manages "members" in a Postgres database. You can get/add/delete them via corresponding REST calls.  
To try it out execute the following steps:
```shell
cd src/main/docker
docker compose up

cd -
mvn clean install -Pnative

java -jar target/quarkus-app/quarkus-run.jar

./target/member-service-1.0.0-SNAPSHOT-runner
```

To let the tracing agent help you find configs for reflection, dynamic proxy etc. start the app with the following command:
```shell
java -agentlib:native-image-agent=config-output-dir=./src/main/resources/META-INF/native-image/ -jar target/quarkus-app/quarkus-run.jar
```

To check your current members just open the following URL in your browser to open the Swagger UI:  
http://localhost:8080/swagger-ui/
