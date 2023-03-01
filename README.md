# Checker for Card-Certificates from the Gematik System

This utility will connect to the KoPS system and verify the certificates of the inserted cards
depending on which parameters you pass over the POST request

# Setup
```
mvn package
mvn quarkus:dev
```

Please make sure to mvn package the application *before* opening it in IntelliJ. If not, delete the .idea
hidden folder. Otherwise the syntax-highlighting and dependency-scan will be inaccurate.

go to

http://localhost:8080

the homepage will show you the endpoints that you can use

# KoPS
run kops with the following command
````
start localhost 8082
````

use port 8082 for KoPS to avoid port-conflicts with the quarkus application.
It is better to run KoPS statically on another port "forever" since the Quarkus Application will be re-run
many times (not to need to specifiy an alternative port all the times)

# Postman
All the GET requests (Endpoints that start with "get") can be seen via the browser

POST requests need the Postman applications and the Headers should be specified in the corresponding fields
and NOT as URL Parameters

# Tests
````
\src\test\java\health\medunited\pwdchanger
````
In the above folder there are a few .java files that have test-functions which can be run inside of IntelliJ
To do this highlight the corresponding function, insert a breakpoint at the end and press debug.




