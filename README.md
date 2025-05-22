# Spring Security JWT Auth Service

This project is a secure Spring Boot backend application that implements:

- ✅ **DAO-based authentication** (custom `UserDetailsService`)
- 🔐 **JWT access and refresh token mechanism**
- 🧑‍🤝‍🧑 **Role-based access control**
- 🗄️ **PostgreSQL** as the database
- 🐳 **Docker** support for containerization

---

## 🔧 Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Docker
- Maven

## 🛠️ Features

- 🛂 **User registration and login** with username/password
- 🧾 **JWT Access Token** for authorization (short-lived)
- ♻️ **JWT Refresh Token** for renewing access (long-lived)
- 🔑 Role-based method and URL-level access
- ❌ Auto-block push of `.yml` with credentials using GitHub Secret Scanning
- 🐳 Fully Dockerized for production-like environments

---INFO

you need to configure the application.properties or the application.yml file locally. 
