# PROG5121POE-PART3
PROG5121 Programming 1A POE-PART 3
# PROG5121POE – Part 3: QuickChat Application

## Student Information
- **Student Name:** Tlaleng Mahoana
- **Module:** PROG5121 – Programming 1A
- **Project:** Portfolio of Evidence – Part 3
- **Programming Language:** Java
- **IDE:** Apache NetBeans

## 1. Project Overview

QuickChat is a Java-based messaging application developed as part of the PROG5121 Programming 1A Portfolio of Evidence.

The application allows users to send, view, search, delete and manage messages using a menu-driven console interface.

The project demonstrates object-oriented programming, input validation, file handling, JSON storage and software testing.

## 2. Application Features

The QuickChat application includes the following features:

- Send messages to recipients.
- Validate South African cellphone numbers.
- Validate message length.
- Generate unique message IDs.
- Generate message hashes.
- View sent messages.
- Search for messages using message IDs and recipient details.
- Delete messages.
- Display a message report.
- Identify the longest message.
- Save and load sent messages using JSON.

## 3. Technologies Used

- **Java:** Main programming language.
- **Apache NetBeans:** Development environment.
- **Gson:** Used to convert message data into JSON format and read stored messages.
- **JUnit:** Used for unit testing.
- **GitHub:** Used for version control and project submission.

## 4. Project Structure

The project contains the following important folders and files:

- `src` – Contains the Java source code.
- `test` – Contains unit and automated test classes.
- `nbproject` – Contains NetBeans project configuration files.
- `build.xml` – Ant build configuration.
- `manifest.mf` – Application manifest file.
- `messages.json` – Stores sent message information.
- `README.md` – Project documentation.

## 5. How to Run the Application

1. Open Apache NetBeans.
2. Select **File → Open Project**.
3. Locate and open the `PROG5121POE-Part3` project.
4. Make sure the Gson library is available in the project.
5. Right-click the project and select **Clean and Build**.
6. Right-click the project and select **Run**.
7. Follow the instructions displayed in the console menu.

## 6. Testing

The project includes JUnit tests to check important application functionality.

Testing covers:
- Message ID validation.
- Cellphone number validation.
- Message length validation.
- Message hash generation.
- Message details and information.
- Menu input and application behaviour.

The unit tests and automated tests were used to identify errors and verify that the application works as expected.

## 7. JSON Message Storage

The application uses the Gson library to save sent messages in a JSON file named `messages.json`.

The saved messages can be loaded when the application starts again, allowing previously sent messages to remain available.

## 8. Version Control

GitHub was used to manage the project and track development changes through meaningful commits.

The repository contains the source code, project configuration, testing files and documentation.

## 9. Conclusion

The QuickChat application demonstrates the practical use of Java programming concepts, object-oriented programming, validation, JSON file handling and software testing.

This project helped develop my understanding of building, testing and managing a functional console-based messaging application.

**End of Project – PROG5121 Part 3**