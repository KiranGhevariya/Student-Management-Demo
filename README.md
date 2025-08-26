# Java Web CRUD App with Embedded Jetty

This is a basic Java web application that uses an **embedded Jetty server** and performs **CRUD operations** (Create, Read, Update, Delete) for learning and demonstration purposes.

---

## 🚀 Features

- Java-based web app with no external application server required
- Embedded Jetty for easy deployment and testing
- Basic CRUD operations using Servlets and JSP or raw HTML
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
/pom.xml (for Maven dependencies)

yaml
Copy code

---

## 🛠️ Technologies Used

- Java SE 8+
- Embedded Jetty Server
- JSP + Servlets
- Maven (for build & dependencies)

---

## ▶️ How to Run

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/jetty-crud-app.git
   cd jetty-crud-app
Build the project with Maven

bash
Copy code
mvn clean install
Run the app

bash
Copy code
mvn exec:java
Open your browser and go to:

arduino
Copy code
http://localhost:8080/
