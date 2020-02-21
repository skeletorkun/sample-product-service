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

### Installation


```
docker pull devorkun/marketplace:0.0.1
```

### Considerations

In a production environment, we would not use the following application property.
Instead we would use a mode like validate and manage our DB schema through scripts.

spring.jpa.hibernate.ddl-auto=create-drop


#### Service Redundancy


#### Authentication
