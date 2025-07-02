# Mess Committee Connect Portal

A simple Java-based console application that enables students and administrators to interact with a mess (canteen) management system. 
This project was built to simulate real-time menu handling and food rating processes using backend integration with **MongoDB** and session validation through **Redis**.

---

## 🔧 Features Implemented

### 👤 User Roles
- **Student**:  
  - Login
  - View daily/weekly menu
  - Submit ratings for meals with comments
  - View personal rating history

- **Admin**:  
  - Login
  - View all food ratings (filtered by day)
  - Create new menus
  - Edit or update existing menus (meal-wise or all)
  - View entire weekly or daily menu

---

## 🧰 Technologies & Tools Used

- **Java (Console-based)** – Core application logic
- **MongoDB** – Used for storing user data, food ratings, and menus
- **Redis** – Used to handle session management and validation
- **BCrypt** – Passwords are hashed using `BCrypt` for secure storage

---

## 📁 Project Structure

![image](https://github.com/user-attachments/assets/36a98453-678c-4868-9c48-a60de89b1953)



---

## 🧪 Functional Highlights

- Menu operations like creation, editing, and deletion are stored and retrieved from **MongoDB**.
- User login sessions are created and validated using **Redis**, with unique session IDs.
- Role-based access controls determine available options for students and admins.
- Passwords are securely hashed before storing using **BCrypt**.

---

## 🚀 How to Run

1. Clone the repository  
2. Ensure MongoDB and Redis servers are running  
3. Compile and run `App.java`  
4. Follow on-screen instructions for login and operations  

---

## 📌 Note

This project is built for learning and demonstration purposes, focusing on:
- CRUD operations using MongoDB
- Role-based console interaction
- Integration of Redis for session management in a Java environment

---

## 🧑‍💻 Author

Developed by **Danish zakariya**
