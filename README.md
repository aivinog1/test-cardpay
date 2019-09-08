# test-cardpay
Test task for a CardPay application.
# How to run
1. You need a maven to do this.
2. `mvn clean install`
3. `java -jar target/test-cardpay-1.0-SNAPSHOT.jar file1.json file2.csv`
4. In the output, you shall see the result of the command.
5. (Optional) You can change some concurrency set via a configuration file or via parameters to run. To see what properties could be changed see this: https://github.com/aivinog1/test-cardpay/blob/7118a61e029d69de8f898fd12564d6d037013bb7/src/main/resources/application.yml#L9-L29
