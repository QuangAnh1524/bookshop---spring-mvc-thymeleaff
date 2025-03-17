FROM openjdk:23
WORKDIR /app
COPY 01_spring_mvc/target/bookshop-0.0.1-SNAPSHOT.jar bookshop.jar
CMD ["java", "-jar", "bookshop.jar"]