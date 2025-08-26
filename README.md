# Java Web CRUD App with Embedded Jetty

This is a basic Java web application that uses an **embedded Jetty server** and performs **CRUD operations** (Create, Read, Update, Delete) for learning and demonstration purposes.

---

## 🚀 Features

- Java-based web app with no external application server required
- Embedded Jetty for easy deployment and testing
- Basic CRUD operations using Servlets and JSP (or raw HTML)
- Lightweight structure — no Spring or heavy frameworks

---

## 📁 Project Structure

/src
└── main
├── java
│ └── com.example.crud # Java servlet classes
└── webapp
├── index.jsp # Homepage
├── form.jsp # Add/Edit form
└── list.jsp # Display data


---

## 🛠️ Technologies Used

- Java SE 8+
- Embedded Jetty Server
- JSP + Servlets
- Maven (for build & dependency management)

---

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/jetty-crud-app.git
cd jetty-crud-app
mvn clean install
mvn exec:java

##port
http://localhost:8080/

📌 CRUD Functionalities

Create – Add a new item via a form

Read – List all items

Update – Edit existing items

Delete – Remove items
