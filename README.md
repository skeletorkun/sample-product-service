# Marketplace Api 

A REST/JSON web service that provides to manage products and orders.
https://github.com/skeletorkun/sample-product-service

Supported operations

Products: 
* Create a new product
* Get a list of all products
* Get a product by its id
* Update a product

Orders:
* Place an order
* Retrieve all orders within a given time period

### Maven module

Build with tests

```
mvn clean install
```

Run using maven 
```
mvn spring-boot:run
```


### With Docker

You can have the service up and running with 

```
docker compose up
```

### Swagger UI

Test the Order and Product endpoints

```
http://localhost:8080/swagger-ui.html#/
```


### Considerations

- Java11 could have been used instead of Java8, admittedly old.
- MariaDB and MongoDB are used to showcase both usages - for Aggregate Pattern. 
- Some integration testing is provided with Rest Assured.


#### Service Redundancy

For the service to be highly available and scalable, 
we can create multiple application servers - e.g. using kubernetes.
A load balancer would be used to distribute traffic on each node.
Mongo and MariaDB can function in cluster mode that would mitigate similar issues on storage level.


#### Authentication

There is no authentication mechanism in this version.
Spring security is a good option to make use of OAuth and JWT.
In the future, with multiple services, we might want to have an authentication server 
to manage tokens.