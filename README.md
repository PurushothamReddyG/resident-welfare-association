
## 📸 UI Mockups

### Home Page, Login, Dashboard & Registration
![Sri Sai County UI Mockups](docs/dashboard_home_login_registration.png)

# 🏡 Sri Sai County – Villa Management Application

A modern **Villa Management System** built with **Angular 20** (frontend) and **Spring Boot** (backend) to help manage villas, residents, maintenance, and community events efficiently.

---

## 📌 Features

### Resident Side
- Public **Home Page** with welcome banner, features, and contact info
- **Villa List** view with details and occupancy status
- **Dashboard** after login with:
  - Welcome message & villa info
  - Quick actions (Add/View Villas, Pay Maintenance, View Notices)
  - Summary widgets (Villas Occupied, Pending Payments, Total Members)
  - Latest notices & updates
- **Maintenance Tracking** with online payment support
- **Community Notices** & event updates
- **Service Requests** for maintenance issues
- **WhatsApp Notifications** (optional, via API integration)

### Admin / Committee Side
- Role-based login (Admin, Resident, Security)
- **Villa Management** – Add/Edit/Delete villas
- **Payment Records** with reporting
- **Visitor Log** & security notifications
- Bulk notices via app or WhatsApp

---

## 🛠 Tech Stack

### Frontend
- [Angular 20](https://angular.io/)
- Angular Material / Bootstrap
- RxJS for state management
- Chart.js for dashboards

### Backend
- [Spring Boot](https://spring.io/projects/spring-boot) 3.x
- Spring Data JPA & Hibernate
- MySQL / PostgreSQL
- REST API architecture

---

## 📂 Project Structure

/villa-management
├── backend/ # Spring Boot backend
├── frontend/ # Angular 20 frontend
├── README.md # Project documentation
└── docs/ # API docs, architecture diagrams

yaml
Copy
Edit

---

## 🚀 Getting Started

### 1️⃣ Clone the repository
```bash
git clone https://github.com/YOUR-USERNAME/villa-management.git
cd villa-management
2️⃣ Backend Setup (Spring Boot)
bash
Copy
Edit
cd backend
./mvnw spring-boot:run
Configure application.properties with your DB details:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/villa_db
spring.datasource.username=root
spring.datasource.password=yourpassword
3️⃣ Frontend Setup (Angular)
bash
Copy
Edit
cd frontend
npm install
ng serve
Access at: http://localhost:4200

🔔 WhatsApp Notifications (Optional)
We support sending notifications via:

Meta WhatsApp Business API

Twilio WhatsApp API

Setup details in docs/whatsapp-setup.md

📅 Roadmap
Phase 1

Core Villa Management

Dashboard, Login, Notices

Phase 2

Maintenance Payments

Service Request Tracking

WhatsApp Notifications

Phase 3

Visitor Management

Community Marketplace

Event Calendar

🤝 Contributing
We welcome contributions!

Fork the repo

Create a new branch (feature/your-feature)

Commit changes & push

Create a Pull Request

📜 License
This project is licensed under the MIT License – feel free to use and modify.

📧 Contact
For queries, feature requests, or collaboration:

Sri Sai County Committee

Email: info@srisai-rwa.com

WhatsApp: +91-XXXXXXXXXX