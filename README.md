[![Known Vulnerabilities](https://snyk.io/test/github/jesusgsdev/moneytransferexample/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/jesusgsdev/moneytransferexample?targetFile=pom.xml)
# RESTful API for Money Transfer

#### RESTful API (including data model and the backing implementation) for money transfers between accounts.

### Stack
- Java 11
- Javalin 3.6.0
- JUnit 5.5.2
- Mockito 3.2.0
- Jackson 2.10.1
- Slf4j 1.7.29
- Maven 3.8.1
- Maven Sunfire plugin 2.2.1

### Why I used Javalin as framework
After reading the requirements for this API, I found that Javalin had the best balance between simplicity and performance. The server starts in 200ms.

### Security
The project has been scanned by Snyk (https://snyk.io/) to ensure there is no vulnerabilities with the libraries used.

### Simplifications and decisions made
- I used Java 11 because I am more familiar with it than with Kotlin even I worked a little bit with it.
- I haven't used Lombok to reduce the number of imported libraries.
- Currency as a simple String with no validations. I would take https://www.xe.com/iso4217.php as source to validate is a valid currency.
- Used IBAN as account identification to be broader than just Sort Code and Account Number that are usual in the UK.
- No real database system: a in-memory maps to hold the accounts and transfers.
- Transfers happens within accounts of the same currency, otherwise a currency converter should be in place in between the call made and the transfer done.
- TransferDTO just include Account IBAN and not Account Holder as even banks nowadays don't take this into account.
- On the Account entity, IBAN and Currency cannot be changes so no setters provided.
- On the Transfer entity, nothing can be updated so no setters provided. Equals method will compromise all parameters due to the id is an UUID which has a chance to be duplicated even if it is very small (https://towardsdatascience.com/are-uuids-really-unique-57eb80fc2a87).
- On the Account entity, IBAN is enough to make it unique as no two account with same values can exists.
- There is no security around access to account balances or doing transfers.
- Responses are done in String rather than using DTOs or JSON to simplify the project.
- I haven't implemented builder pattern in some classes to reduce lines of code and improve simplicity for this exercise.
- I haven't take too much into account GDPR or PII data for this example.
- To keep it simple, I haven't added any DockerFile. The app will run as standalone java application.

## Test Coverage
Test coverage in classes with logic is 100%, these classes are within exceptions, htthandler, service and dao packages.

## How to test using maven
To run the test suite, just execute the command `mvn test` and all the tests will be executed

## How to test manually
The application is running on port 7000.
To start the application execute the following command in a terminal:
```
mvn package
```
and then, to start it
```
java -jar target/moneytransfer-1.0-jar-with-dependencies.jar
```

The app will start at port `7000`. You will see the logs in the console while running the following operations:

While running, you can execute the following command to retrieve the balances of the account `GB26MIDL40051512345671` and `GB26MIDL40051512345675` are:
> http://localhost:7000/account/balance/GB26MIDL40051512345671

and
> http://localhost:7000/account/balance/GB26MIDL40051512345675

Then, you can execute a transfer between both using the following post request using cURL:

```
curl -X POST \
  http://localhost:7000/transfer \
  -H 'Content-Type: application/json' \
  -d '{
    "senderAccountIBAN": "GB26MIDL40051512345671",
    "recipientAccountIBAN": "GB26MIDL40051512345675",
    "amount": 100
}'
```
You can again use above `GET` queries to get the updated balances in each account.

The logs for this operations would be something like
```
[qtp433287555-19] INFO com.jesusgsdev.httphandler.MoneyTransferHandler - Received a balance request for the account GB26MIDL40051512345671
[qtp433287555-19] INFO com.jesusgsdev.httphandler.MoneyTransferHandler - Balance for the account GB26MIDL40051512345671 retrieved successfully. Current balance is: GBP1000.0
[qtp433287555-17] INFO com.jesusgsdev.httphandler.MoneyTransferHandler - Transfer of 100.0 received to be executed from GB26MIDL40051512345671 to GB26MIDL40051512345675
[qtp433287555-17] INFO com.jesusgsdev.services.TransferService - Transfer of GBP100.0 completed from GB26MIDL40051512345671 to GB26MIDL40051512345675 accounts with Reference 7a7f1cff-43a4-4750-bcf5-c98b6047312c
[qtp433287555-17] INFO com.jesusgsdev.services.AccountService - Balance for account GB26MIDL40051512345671 was be decreased in 100.0, 900.0 is available
[qtp433287555-17] INFO com.jesusgsdev.services.AccountService - Balance for account GB26MIDL40051512345675 was increased in 100.0, 200.0 is available
```

#### Postman Collection for Testing
I have provided a postman collection `MoneyTransfers.postman_collection.json` to simplify the testing.