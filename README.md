### The solution I provided is basesd on async communication based on messaging through RabbitMQ (hosted externally on Heroku)

![system schema](schema.png)

#### Run as each application as
``` 
mvnw spring-boot:run
```
#### Client application has a REST endpoint where you can POST transactions.
```
POST: http://localhost:8080/transaction
```

##### Example transaction json:
``` 
{
	"payer" : {
		"name" : "Tiberiu Marinica",
		"CNP" : "5000326135291",
		"IBAN" : "RO66PORL7242895992327856"
	},
	
	"payee" : {
		"name" : "Ion Ionescu",
		"CNP" : "1930702462895",
		"IBAN" : "RO21RZBR9234222628351991"
	},
	
	"type" : "WALLET_TO_IBAN",
	"sum" : 996.83,
	"description" : "descriere"
}
```

#### To get transactions report, call client application report endpoint (which sends a report request message to RabbitMQ), and see output in client console:
```
GET: http://localhost:8080/reportRequest/allTransactions
```
