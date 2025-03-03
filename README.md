# Table of Contents

## 1. Introduction

1. Introduction ........................................................................ 1  
2. User Requirements Analysis (Project Specifications) ................................ 1  

   2.1. Specific Assumptions for the Chosen Domain (Requirements, Constraints) ............ 1  
   2.2. Structured (Tabular) Organization of User Requirements .................................... 2  
   2.3. Identification and Characterization of User Profiles (Admin, Internal User, External User, Other Actors) .......................................................................................... 2  

3. Data Model and Description ........................................................................... 3  

   3.1. Entities and Their Attributes (Detailed Description – Physical Implementation) ....... 3  
   3.2. EER/UML Diagram for the Complete Data Model ............................................ 5  
   3.3. Data Normalization .................................................................................... 5  

4. Implementation Details ............................................................................. 7  

   4.1. Functional Description of Modules (Logical Organization – e.g., Java Class Structure, HTML, JSP, ASP, PHP Code) ............................................................... 12  
   4.2. User/Installation Manual (Differentiated by User Type) ............................... 13  
   4.3. Application Security Elements ................................................................... 16  

5. Conclusions, Limitations, and Future Development ......................................... 17  

---

## 1. Introduction

The project involved creating an application to assist clients, donors, and the owner of an animal shelter. The purpose of the application is to facilitate access to the shelter’s database through an intuitive graphical interface. The application can be used by three types of users: clients, donors, and the shelter owner.

With this application, any client can view animals available for adoption and check their veterinary history. Donors can contribute a sum of money, which will be directed to a chosen animal shelter. The owner can track incoming funds from donations and adoptions, keeping an updated record of the shelter’s budget to properly provision the shelter. Additionally, the owner keeps track of the animals in the shelter and the available spaces.

---

## 2. User Requirements Analysis (Project Specifications)

### 2.1. Specific Assumptions for the Chosen Domain (Requirements, Constraints)

#### **Requirements**
- **Adoption Requirements**: The system must allow the registration of information about animal adoptions, including details of the new owners and the current status of the animal.  
- **Supply Management**: There is a need for an efficient way to manage supplies such as food, toys, and other necessities.  
- **Veterinary Services**: The system must maintain a record of each animal’s veterinary history and manage veterinary visits.

#### **Constraints**
- **Limited Financial Resources**: Animal shelters may have limited financial resources, requiring efficient budget, donation, and expense management.  
- **Shelter Capacity**: The shelter has a limited physical capacity, affecting the number of animals that can be housed at any given time.  
- **Legislation and Regulations**: Compliance with animal welfare laws and shelter operation regulations is mandatory and may impose specific constraints.

---

### 2.2. Structured (Tabular) Organization of User Requirements

The database consists of tables storing information about shelters, owners, animals (including their veterinary history), donations, donors, transactions, and financial records. The database is designed to support all aspects of an animal shelter’s operation and handle various scenarios.

---

### 2.3. Identification and Characterization of User Profiles (Admin, Internal User, External User, Other Actors)

The application is designed for two main user options:  
1. **Shelter Owner** – Manages the shelter, transactions, and animal records.  
2. **Client** – Can adopt an animal or make donations to a shelter.

#### **Operations Available for Each User:**
- **Shelter Owner**:
  - View information about animals.
  - Monitor transactions and available funds.
  - Track available shelter spaces.

- **Client**:
  - Choose to adopt an animal or donate.
  - View veterinary history of animals.
  - Browse multiple shelters.
  - Select and donate to a preferred shelter.

---

## 3. Data Model and Description

### 3.1. Entities and Their Attributes (Detailed Description – Physical Implementation)

#### **Shelter Table**
- Stores information about the animal shelter.
- Attributes: `id_shelter`, `shelter_name`, `shelter_address`, `number_of_spaces`.

#### **Owner Table**
- Stores information about the shelter owner.
- Attributes: `id_owner`, `name`, `address`, `phone`, `id_shelter` (FK).

#### **Location Table**
- Stores information about animal placement within the shelter.
- Attributes: `id_location`, `location_name`, `status` (Available/Occupied).

#### **Donor Table**
- Stores donor information.
- Attributes: `id_donor`, `name`, `address`, `phone`.

#### **Donation Table**
- Stores donation records.
- Attributes: `id_donation`, `donor_id` (FK), `amount`.

#### **Funds Table**
- Tracks shelter budget (income and expenses).
- Automatically updates upon transactions.

#### **Animal Table**
- Stores animal information.
- Attributes: `id_animal`, `name`, `species`, `id_location` (FK).

#### **Veterinary History Table**
- Stores medical history for each animal.

#### **Animal_History Table**
- Intermediate table linking `Animal` and `Veterinary History`.

#### **Veterinary Clinic Table**
- Stores information about the veterinary clinic.

#### **Supplies Table**
- Stores information about shelter supplies.

#### **Inventory Table**
- Intermediate table linking `Shelter` and `Supplies`.

#### **Doctor_Clinic Table**
- Stores information about veterinarians.

#### **Client Table**
- Stores information about clients adopting animals.

#### **Transactions Table**
- Links `Client` and `Animal` and updates shelter funds.

---

### 3.2. EER/UML Diagram for the Complete Data Model

_(To be included in the project document.)_

---

### 3.3. Data Normalization

Each table meets the requirements for **1NF, 2NF, and 3NF**, ensuring:
- **No redundant data**.
- **Referential integrity**.
- **Proper relationships between tables**.

---

## 4. Implementation Details

### 4.1. User/Installation Manual 

#### 4.1.1. Tools

#### **MySQL Workbench**  
MySQL Workbench is a graphical tool for working with MySQL servers and databases. It is designed to support MySQL Server versions 5.1 and later. MySQL Workbench covers key database management tasks, including:  

- **SQL Development**: Manage connections to MySQL servers and execute SQL queries.  
- **Data Modeling**: Create and edit database models graphically. The Table Editor allows modification of tables, columns, indexes, triggers, partitioning, options, inserts, privileges, routines, and views.  
- **Server Administration**: Create and manage MySQL server instances.  
- **Data Migration**: Migrate data from Microsoft SQL Server, Sybase ASE, SQLite, SQL Anywhere, and PostgreSQL to MySQL.  

#### **IntelliJ IDEA**  
IntelliJ IDEA is an integrated development environment (IDE) for Java and Kotlin, designed to maximize developer productivity. It automates routine and repetitive tasks by providing intelligent code completion, static code analysis, and refactoring features, allowing developers to focus on the creative aspects of software development. This not only improves productivity but also makes the development experience enjoyable.  

### **Installation Steps**  
1. Install **MySQL Workbench** and **IntelliJ IDEA**.  
2. Download the archive containing the MySQL database tables and Java classes.  
3. Ensure a successful connection between IntelliJ IDEA and the MySQL database.  
4. Run the program by executing the `LogInPage` class in IntelliJ IDEA.  


#### 4.1.2. Interface
Upon opening the application, you have the option to log in either as an **Owner** or as a **Client**.  
Clicking on either button will present you with the option to log in or create a new account.

<img width="342" alt="1" src="https://github.com/user-attachments/assets/24b0c48a-8e13-473c-8586-62071d74e011" />


For the client option, you will choose whether you want to donate or adopt an animal from a shelter of your choice.

<img width="314" alt="7" src="https://github.com/user-attachments/assets/cc94cf2f-1fa6-44b7-bacd-3de97d4b5191" />


For the donor, a window will open with several fields to be filled in, and at the end, a message with the donation details will appear.

<img width="204" alt="10" src="https://github.com/user-attachments/assets/e10c3108-cb36-4031-a350-2b96f1cdebd7" />


For the client who will adopt an animal, they will see the animals available at the chosen shelter.

<img width="443" alt="11" src="https://github.com/user-attachments/assets/f08ce670-73ab-4ea8-a087-0792ffde792f" />


If you log in as an owner, you will have more options, such as:
- View information about animals
- Add an animal to the database
- View transactions
- View funds
- Keep track of the number of available spots at the shelter you own, as well as at others.
- 
<img width="225" alt="15" src="https://github.com/user-attachments/assets/227fd15b-7044-465c-aae6-9de1288fe3b0" />

---

## 5. Conclusions, Limitations, and Future Development

The development of this application has successfully provided a structured and efficient system for managing an animal shelter. Through the database and user-friendly interface, shelter owners can manage available spaces, track finances, and oversee adoption and donation processes. Clients can easily adopt animals and donate to shelters, improving the overall efficiency of the shelter’s operations.

### **Limitations**
- The application is currently designed for a single shelter; expanding it for multiple interconnected shelters would require additional development.
- Security measures need enhancement, especially in data encryption and access control.
- The current interface is built for desktops and lacks a mobile-friendly design.

---

## **Bibliography**
1. [JetBrains IntelliJ IDEA Documentation](https://www.jetbrains.com/help/idea/discover-intellij-idea.html)  
2. [Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)  
3. [MySQL Reference Manual](https://books.google.ro/books)  
4. [Animal Shelter Management Resources](https://www.youtube.com/watch?v=6zm8c6QFmjo&ab_channel=Intellipaat)  

