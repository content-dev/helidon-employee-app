
# Helidon Quickstart SE Example

This project implements a simple REST service using Helidon SE.

## Prerequisites

1. Maven 3.5 or newer
2. Java SE 8 or newer
3. Docker 17 or newer to build and run docker images
4. Kubernetes minikube v0.24 or newer to deploy to Kubernetes (or access to a K8s 1.7.4 or newer cluster)
5. Kubectl 1.7.4 or newer to deploy to Kubernetes

Verify prerequisites
```
java -version
mvn --version
docker --version
minikube version
kubectl version --short
```

## Build

```
mvn package
```

## Start the application

```
java -jar target/employee-app.jar
```

## Exercise the application

```
curl -X GET http://localhost:8080/employees
- A list of employees

curl -X GET http://localhost:8080/employees/lastname/{name}
- get employees by lastname

curl -X GET http://localhost:8080/employees/department/{name}
- get employees by department

curl -X GET http://localhost:8080/employees/title/{name}
- get employees by title

curl -X PUT -H "Content-Type: application/json" -d '{
"birthDate": "1980-08-26",
"department": "Division",
"email": "john.doe@example.com",
"firstName": "Jonh",
"id": "07700c37-a418-4762-9d7e-740ec1ea796e",
"lastName": "Doe",
"phone": "293-510-3451",
"title": "Principal Factors Architect"
}' http://localhost:8080/employees

curl -X GET http://localhost:8080/greet/Jose
{"message":"Hola Jose!"}
```

## Try health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .

```

## Build the Docker Image

```
docker build -t employee-app .
```

## Start the application with Docker

```
docker run --rm -p 8080:8080 employee-app:latest
```

Exercise the application as described above

## Deploy the application to Kubernetes

```
kubectl cluster-info                # Verify which cluster
kubectl get pods                    # Verify connectivity to cluster
kubectl create -f app.yaml   # Deply application
kubectl get service employee-app  # Get service info
```

## Native image with GraalVM

GraalVM allows you to compile your programs ahead-of-time into a native
 executable. See https://www.graalvm.org/docs/reference-manual/aot-compilation/
 for more information.

You can build a native executable in 2 different ways:
* With a local installation of GraalVM
* Using Docker

### Local build

Download Graal VM at https://github.com/oracle/graal/releases, the version
 currently supported for Helidon is `19.0.0`.

```
# Setup the environment
export GRAALVM_HOME=/path
# build the native executable
mvn package -Pnative-image
```

You can also put the Graal VM `bin` directory in your PATH, or pass
 `-DgraalVMHome=/path` to the Maven command.

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin
 for more information.

Start the application:

```
./target/employee-app
```

### Multi-stage Docker build

Build the "native" Docker Image

```
docker build -t employee-app -f Dockerfile.native .
```

Start the application:

```
docker run --rm -p 8080:8080 employee-app:latest
```