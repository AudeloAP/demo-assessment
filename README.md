# demo-assessment - Documentation

## Description

This REST API allows the management of products, orders, and order items for an e-commerce platform. It is built with **Spring Boot** and **PostgreSQL**. The API includes CRUD operations for products and orders, and allows associating products with orders.

## Prerequisites

Before running this API, make sure you have the following installed:

- **Docker** (to build and run containers)
- **Docker Compose** (to orchestrate containers)

## Clone the repository

To get started, first clone this repository to your local machine:

```bash
git clone https://github.com/AudeloAP/demo-assessment.git
cd demo
```

## Run command ()
    docker-compose up --build

## On a browser, go to
you can test the cases in the following link: 
    http://localhost:8080/swagger-ui/index.html#

## One you can see the services exposed by this rest api you need to
- **Create a Product** 
```
    {
    "name": "Laptop",
    "description": "High-end laptop with i7 processor"
    }
 ```
- **Create an order**
```dtd
{
  "description": "Juan's order",
  "status": "PENDING"
}
```
- **Create an OrderItem**
```dtd
{
  "order_id": 1,
  "product_id": 1
}
```

Now you can proof any endpoint...