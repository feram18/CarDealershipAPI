FROM openjdk:11
WORKDIR /
ADD target/CarDealership.jar CarDealershipApp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "CarDealershipApp.jar"]
