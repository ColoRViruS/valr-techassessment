# VALR Tech assessment.

This is an Implementation of an In-memory order book that can be retieved and added to with the use of limit orders

## Tech Stack

- Language: Kotlin
- Framework: Spring Boot
- Database: H2 (in-memory)
- JPA/Hibernate for persistence
- Java: 21

## Endpoints

There is a Swagger API spec in resources

- GET /echoTest
- GET /orderbook/{currencyPair}
- POST /limitorder
- GET /tradehistory/{currencyPair}

## Gradle Commands

```./gradlew bootRun```

## In-Memory Database

Database is seeded with list of all valid currency pairs and an Orderbook for BTCZAR.
Any other Currency pair will return empty Orderbook. 

## Postman collection

There is a postman collection in postmanScripts for localhost:8080