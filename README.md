# Backend Daily Expenses

Daily Expenses is a web application that allows users to efficiently manage their daily expenses. This application is developed using Angular and focuses on providing a smooth and responsive user experience. It includes features such as expense tracking, income management, account transactions, and customizable categories.

This backend service for the "Daily Expenses" application is built using Java Spring Boot and connects to a PostgreSQL database. It provides a robust RESTful API to manage daily expenses, incomes, and account transfers.

## Technologies Used

- **Java Spring Boot**: Backend framework to build and manage the API.
- **PostgreSQL**: Relational database for data persistence.
- **Spring Security**: Security framework for authentication and authorization.
- **JSON Web Tokens (JWT)**: Used for secure API authentication.
- **MapStruct**: Java-based code generator to simplify data mapping.
- **Springdoc OpenAPI**: For API documentation.

## Installation and Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/luheresbar/BackEnd-Daily_Expenses.git
   cd BackEnd-Daily_Expenses
   ```

2. **Configure the Database**:
   Update the `application.properties` file with your PostgreSQL database configuration:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/DailyExpenses
   spring.datasource.username=yourUsername
   spring.datasource.password=yourPassword
   ```

3. **Build the Project**:
   Run the following command to build the project:
   ```bash
   ./mvnw clean package
   ```

4. **Run the Application**:
   Use this command to start the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## API Documentation

The API is documented using Swagger and can be accessed at: [Swagger UI - Daily Expenses](https://dailyexpenses.ddns.net/swagger-ui/index.html)

## Key Features

- **Expense Management**: Add, view, update, and delete daily expenses.
- **Income Management**: Track incomes across different categories.
- **Account Transfers**: Manage and track transfers between accounts.
- **Secure Endpoints**: Secured with JWT for authentication and role-based access control.

## Contributing

1. **Fork the Repository**.
2. **Create a New Branch**:
   ```bash
   git checkout -b feature/new-feature
   ```
3. **Make Your Changes and Commit**:
   ```bash
   git commit -m "Add new feature"
   ```
4. **Push to Your Fork**:
   ```bash
   git push origin feature/new-feature
   ```
5. **Submit a Pull Request**.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.
