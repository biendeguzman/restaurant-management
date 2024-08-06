Springboot Project Restaurant-Management

### This is a simple CRUD (Create, Read, Update, Delete) application for managing restaurants, owners, menus, and employees using Spring Boot. The application allows users to perform operations such as adding new restaurants, updating existing ones, viewing details, and deleting records.

Prerequisites

    Maven
    MySQL or any other preferred database
    Java Development Kit(JDK) or higher version.
    Intellij IDEA Community Edition 2023 or any JAVA IDE.
    
Clone the repository
1. git clone [link](https://github.com/biendeguzman/restaurant-management.git)
2. cd restaurant-management

### Configure the Database 
Update the application.properties file located in src/main/resources with your database configuration:

    spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
