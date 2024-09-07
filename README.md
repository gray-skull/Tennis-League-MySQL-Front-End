# Tennis League MySQL Front-End
## Introduction
This project is a Java-based front-end application designed to interact with a MySQL database for managing tennis league teams, players, and coaches. Built using Java Swing, it allows users to create, update, and delete records for teams, players, and coaches while viewing lists of each entity.

## Features
- **Team, Player, and Coach Management:** Create, edit, and delete teams, players, and coaches in a relational database.
- **Data Views:** The interface provides a list view for teams, players, and coaches, showing their details and relationships within the tennis league.
- CRUD Functionality: Implemented through Java Swing components like buttons, tables, and forms, allowing users to perform Create, Read, Update, and Delete operations on the database.

## Technologies Used
- **Java (Swing):** The front-end is built using Java’s Swing framework, providing a graphical user interface (GUI) that allows users to interact with the application. The Swing components like JTable, JButton, and JTextField are used to implement forms and data views.
- **MySQL:** The back-end database is implemented using MySQL. It stores and organizes tennis league data, such as teams, players, and coaches, in relational tables with primary and foreign keys to establish relationships. Data integrity and constraints ensure the validity of operations performed via the front-end.
- **JDBC (Java Database Connectivity):** JDBC is used to connect the Java application to the MySQL database. SQL queries are executed via JDBC to perform operations like retrieving, inserting, updating, and deleting records in the database.
- **SQL:** The application uses SQL scripts to define the database schema and perform data manipulation. Complex queries may be used to retrieve and display relational data between entities like players on a team or the coaches they are associated with.

## Setup Instructions
1. Ensure MySQL is installed and running on your system.
2. Create the required database schema using the provided SQL seed data.
3. Compile and run the Java Swing application to start interacting with the MySQL database.
4. Use the graphical interface to manage tennis league data.
