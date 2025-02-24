# Drools Consumer

This project demonstrates how to set up and run a Spring Boot application with Drools locally.

## Prerequisites

Before you begin, ensure you have the following installed on your local machine:

- Java Development Kit (JDK) 11 or higher
- Gradle 8.11.1 or higher
- Git

## Setup Instructions

1. **Clone the repository**

    Open a terminal and run the following command to clone the repository:

    ```sh
    git clone https://github.com/your-username/spring-boot-drools.git
    ```

2. **Build the project**

    Use Maven to build the project:

    ```sh
    gradle build
    ```

3. **Run the application**

    After the build is successful, run the Spring Boot application:

    ```sh
    gradle bootRun
    ```

4. **Access the application**

    Once the application is running, you can access it at `http://localhost:8080`.

## Additional Information

- **Configuration**: Configuration files are located in the `src/main/resources` directory.
- **Drools Rules**: Drools rules are located in the `src/main/resources/rules` directory.

For more detailed information, refer to the official [Spring Boot](https://spring.io/projects/spring-boot) and [Drools](https://www.drools.org/) documentation.

## Contributing

If you wish to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.