# Drools Consumer

This project demonstrates how to set up and run a Spring Boot application with Drools locally.

## Prerequisites

Before you begin, ensure you have the following installed on your local machine:

- Java Development Kit (JDK) 11 or higher
- Gradle 8.11.1 or higher
- Git

## Setup Instructions

1. **Build the project**

    Use Maven to build the project:
    ```sh
    gradle build
    ```
1. **Run the application**

    After the build is successful, run the Spring Boot application:
    ```sh
    gradle bootRun
    ```
1. **Access the application**

    Once the application is running, you can access it at `http://localhost:8080`.

## Additional Information

- **Configuration**: Configuration files are located in the `src/main/resources` directory.
- **Drools Rules**: Drools rules are located in the `src/main/resources/rules` directory.

For more detailed information, refer to the official [Spring Boot](https://spring.io/projects/spring-boot) and [Drools](https://www.drools.org/) documentation.
