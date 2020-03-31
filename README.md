## Expense Tracking Service/APIs
    The transactions to be stored have a type and an amount. 
    The service should support returning all transactions of a type. 
    Transactions can be linked to each other using a "parent_id". 
    Should get total amount involved for all transactions linked to a particular transaction.

## API specs
PUT /transactionservice/transaction/$transaction_id
Body: { "amount":double,"type":string,"parent_id":long }
Returns: {"status": "ok" }

GET /transactionservice/transaction/$transaction_id
Returns: { "amount":double,"type":string,"parent_id":long }

GET /transactionservice/types/$type
Returns: [ long, long, .... ]

GET /transactionservice/sum/$transaction_id
Returns { "sum", double }

## Story
We would like to have a RESTful web service that stores some transactions (in memory is fine) and returns information about those transactions.

The transactions to be stored have a type and an amount. The service should support return- ing all transactions of a type. Also, transactions can be linked to each other (using a "par- ent_id") and we need to know the total amount involved for all transactions linked to a particular transaction.

## How to install?
1. Make sure you have java 8(or higher version) installed on your systems
2. Ensure Maven package manager wrapper(mvnw) is included in the project root directory
3. Clean and Install
	`./mvnw clean install`

## How to run tests?
1. Ensure to go through "How to install?"
2. Run test with Maven
	`./mvnw clean test` 

## How to start server?
	`java -jar target/adviqo-0.0.1-SNAPSHOT.jar`
	
## Approach
Clean Architecture Design Pattern, Practices and Principles
Test Driven Development(TDD) 
 
## Can be extended:
1. For concurrent request (Using ReentrantReadWriteLock, ConcurrentHashMap).
2. Invalid attributes can be restricted with the help of ObjectMapper of Jackson library.
3. Current implementation replaces the existing transaction if sent again. 
4. Add transaction uses search for existing transaction, can also use `GetTransactionById` in case of different module.

## DataStore Options
NoSQL Database like Redis can also be used for in-memory data-store.

## Sample Input/Output
    PUT: 
    input:  /transactionservice/transaction/10 { "amount": 5000, "type": "cars" }
    output: {"status": "ok" }

    input:  /transactionservice/transaction/11 { "amount": 10000, "type": "shopping", "parent_id":10}
    output: {"status": "ok" }
    
    input:  GET /transactionservice/types/cars 
    output: [10] 
    input: GET /transactionservice/sum/10 
    output: {"sum":15000} 
    input: GET  /transactionservice/sum/11 
    output: {"sum":10000}
