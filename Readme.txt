Clone the repo from github and execute the below command

mvn clean install

mvn spring-boot:run

Once application gets started you can try to execute below rest end points from the browser on port 8089.

To get list of all the atms execute the below endpoint.

All atms example: http://localhost:8089/atms

To filter the atms by city execute below endpoint

Example with city: http://localhost:8089/atms/Helmond