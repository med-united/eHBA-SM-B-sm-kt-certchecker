# Checker for Card-Certificates from the Gematik System

This utility will connect to the KoPS system and verify the certificates of the inserted cards
depending on which parameters you pass over the POST request

# Setup
mvn install
mvn package
mvn quarkus:dev

go to

localhost:8080

the homepage will show you the endpoints that you can use

# KoPS
run kops with the following command

start localhost 8082

use port 8082 for KoPS to avoid port-conflicts with the quarkus application.
It is better to run KoPS statically on another port "forever" since the Quarkus Application will be re-run
many times (not to need to specifiy an alternative port all the times)