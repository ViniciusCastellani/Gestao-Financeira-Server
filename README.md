# Financial Management Server

## Overview
This project is the backend server for the Financial Management App, developed as part of an academic project. The server is built using Spring Boot and is responsible for storing and managing user data, financial goals, and transaction details. It provides RESTful APIs for communication with the Android application and includes a web-based administrative interface for managing the system.

## Features
### Core Functionalities
1. **RESTful API Endpoints**
   - Handles requests from the Android app for user registration, login, goal management, and analytics.

2. **Data Management**
   - Stores user data, financial goals, income, and expenses in an H2 database.

3. **Admin Web Interface**
   - Provides functionality to:
     - View all registered users.
     - View and manage user goals, income, and expenses.
     - Update or delete user data.
     - Add new users, goals, and transactions.

4. **Port Configuration**
   - Runs on port `8081` by default.

## Requirements
### Development Environment
- Java Development Kit (JDK 17)
- Maven
- Spring Boot Framework

### Database
- H2 Database (in file mode for persistence).

### Tools for Testing
- Postman or cURL for API testing.
- A modern web browser for accessing the admin interface.

## Getting Started
### Clone the Repository
1. Clone the project repository:
   ```bash
   git clone https://github.com/ViniciusCastellani/Gestao-Financeira-Server.git
   ```

2. Navigate to the project directory:
   ```bash
   cd Gestao-Financeira-Server
   ```

### Build and Run the Server
1. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```

2. Run the server:
   ```bash
   ./mvnw spring-boot:run
   ```

### Access the Server
1. H2 Database Console:
   - URL: `http://localhost:8081/h2-console`
   - Use the credentials specified in `application.properties`.

2. Admin Web Interface:
   - Open the file `PessoaListar.html` in a browser.

3. REST API:
   - Base URL: `http://<server-ip>:8081`

### Configuring the Android App
- Ensure the server's IP address is updated in the Android app's configuration to match the network setup.

## REST Endpoints
### PessoaController
- `GET /pessoa/obter/{idPessoa}`: Retrieve details of a person by ID.
- `GET /pessoa/listar`: List all registered people.
- `POST /pessoa`: Add a new person.
- `PUT /pessoa/atualizar/{idPessoa}`: Update details of a person by ID.
- `PUT /pessoa/atualizarTodasInfo/{idPessoa}`: Update all details of a person by ID.
- `DELETE /pessoa/deletar/{idPessoa}`: Delete a person by ID.

### MetaController
- `PUT /pessoa/meta/{idPessoa}`: Add a goal for a person by ID.
- `DELETE /deletar/meta/{idMeta}/pessoa/{idPessoa}`: Delete a goal by goal ID and person ID.

### FluxoController
- `PUT /pessoa/fluxo/{idPessoa}`: Add a financial transaction (income or expense) for a person by ID.
- `DELETE /deletar/fluxo/{idFluxo}/pessoa/{idPessoa}`: Delete a financial transaction by transaction ID and person ID.

## Project Structure
### Directories
- **src/main/java**:
  - `Pessoa`:
    - `PessoaController`: Handles HTTP requests for user operations.
    - `PessoaDao`: Manages database interactions for users.
    - `Pessoa`: Defines the user data structure.
  - `Meta`:
    - `MetaController`: Handles HTTP requests for goal operations.
    - `MetaDao`: Manages database interactions for goals.
    - `Meta`: Defines the goal data structure.
  - `FluxoFinanceiro`:
    - `FluxoController`: Handles HTTP requests for financial transactions.
    - `FluxoDao`: Manages database interactions for financial transactions.
    - `FluxoFinanceiro`: Defines the financial transaction data structure.

- **src/main/resources**:
  - `application.properties`: Contains server configuration, including database and port settings.
  - `html`: HTML files for the admin interface.
  - `js`: Javascript files
  - `css`: CSS files

### Key Files
- `PessoaListar.html`: The main entry point for the admin web interface.
- `application.properties`: Configurations for the database and server.

## Notes
- Ensure the server and Android app are on the same network for successful communication.
- Regularly back up the H2 database file to prevent data loss.

## Repository Links
- [Android App Repository](https://github.com/ViniciusCastellani/Gestao-Financeira-App)

## Contributors
This server was developed by a team of college students as an academic project.


