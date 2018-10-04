# Cryptocurrency Exchange

## User Story
This application implements a prototype for a cryptocurrency exchange. The system has two roles: `UserActor` and `MarketActor`.
- ### UserActor
* The UserActor aims to assist users in making the best possible purchase choice. So, when
buying BTC, the actor will try to buy at the lowest possible price. 
The UserActor also maintains user’s account balance in both BTC and USD.
- ### MarketActor
* The MarketActor’s job is to maintain the orderbook by making changes to it as offers are accepted. It will also disallow any invalid trades. The actor responds to Hold/Confirm requests.

This project is inspired by the paper Pardon & Pautasso, 2014, particially implementing a prototype two-phase commit protocol. <br>
This project is built using `sbt` with Play framework, a lightweight web framework built with Java and Scala. The REST APIs listed below are used for communication with the actors from the outside world.
Internal communication between the actors happen through [Akka](https://doc.akka.io/docs/akka/2.5/actors.html)’s messaging system.
## How to Run

### Play Framework
* Install latest version of sbt: [SBT](http://www.scala-sbt.org/download.html)
* Clone this repository: `git clone https://github.com/ylu36/cryto-exchange.git`
* Go to `cryto-exchange` folder: `cd cryto-exchange`
* Type `sbt run` to run the application.
* For stopping the server, Press the `Enter` key on keyboard.
* For opening the sbt console, type `sbt` from the command prompt.
* There are following APIs present in the web application:

| Type | Endpoint | Description | Response
|---|---|---|---|
| GET | /                                    | The default Play home page |                         |
| POST | /addbalance/usd/:amount             | Add the given amount to the user’s USD balance | { “status”: “success”} |
| GET | /getbalance                          | Return the user’s USD and BTC balance | { “status”: “success”, “usd”: [amount], “btc”: [amount]} |
| GET | /transactions                        | Get a list of successful transactions. | {“status”: “success”, “transactions”: [list of transaction IDs]} |
| GET | /transactions/:transactionID        | Get details of a given transaction |“rate”:[in USD per BTC] } or { “status”: “error”, “message”: [error message]} |
| GET | /selloffers | Get a list of sell Offer IDs | {“status”: “success”,“offers”: [list of sell OfferIDs]} |
| GET | /selloffers/:offerid                     | Get sell offer details. | { “status”: “success”,“rate”: [in USD per BTC],“amount”: [in BTC]} or {“status”: “error”,“message”: [error message}|
| POST | /buy/:maxrate/:amount                | Request a buy transaction, for the given BTC amount at the given maximum rate | { “status”: “success”, “transactionID”: [transactionID] } or { “status”: “error”, “message”: [error message] } |
| POST | /debug/confirm_fail        | After this request is posted, the market actor will reply fail to subsequent Confirm requests without actual processing| {status”:“success”} |
| POST | /debug/confirm_no_response | After this request is posted, the market actor will not reply to subsequent Confirm requests without actual processing|{status”:“success”} |
| POST | /debug/reset               | After this request is posted, the actor will reset to normal|{status”:“success”} |


**Note: This project is created in Linux environment; Endpoints tested with [Postman](https://www.getpostman.com/).

