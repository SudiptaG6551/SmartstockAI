# 📦 SmartStockAI - Intelligent Inventory Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen?style=for-the-badge&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue?style=for-the-badge&logo=mysql)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

A modern, full-stack inventory management system with AI-powered demand forecasting, role-based access control, and real-time analytics.

[Features](#-features) • [Quick Start](#-quick-start) • [Documentation](#-documentation) • [API](#-api-endpoints)

</div>

---

## 🎮 Try It Now (No Setup Required!)

Want to test the application immediately? Use the H2 in-memory database:

```bash
git clone https://github.com/yourusername/smartstockai.git
cd smartstockai
mvn clean package -DskipTests
java -jar target/smartstockai-1.0.0.jar --spring.profiles.active=test
```

Then open `http://localhost:8080` and login with:
- **Username**: `prabir` | **Password**: `prabir123` (Owner)
- **Username**: `sales` | **Password**: `sales123` (Sales Manager)
- **Username**: `stock` | **Password**: `stock123` (Stock Manager)

> 💡 **Note**: First-time users need to register. Use the "Register" tab on the login page.

---

## ✨ Features

### 🔐 Role-Based Access Control
- **Owner**: Full access to all features including analytics, predictions, and user management
- **Sales Manager**: Record sales, view sales history, and check stock levels
- **Stock Manager**: Receive stock, manage inventory, and view stock levels

### 📊 Core Functionality
- **Product Management**: Create, update, and delete products with SKU tracking
- **Stock Management**: Track inventory levels with low-stock alerts
- **Sales Tracking**: Record and monitor sales transactions
- **Profit Analysis**: Calculate and display profit/expense summaries
- **Demand Forecasting**: AI-powered predictions for product demand

### 🎨 Modern UI
- Responsive Bootstrap 5 design
- Role-specific dashboards
- Real-time data updates
- Interactive charts and visualizations

## Technology Stack

### Backend
- **Spring Boot 3.x**
- **Spring Security** with JWT authentication
- **Spring Data JPA** with Hibernate
- **MySQL** database
- **Machine Learning** integration for demand forecasting

### Frontend
- **HTML5/CSS3**
- **JavaScript (ES6+)**
- **Bootstrap 5**
- **Chart.js** for data visualization
- **Font Awesome** icons

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE smartstockai;
```

2. Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartstockai
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Installation & Running

#### 🎯 Quick Test (No Database Setup Required)

Perfect for trying out the application without MySQL installation:

```bash
git clone https://github.com/yourusername/smartstockai.git
cd smartstockai
mvn clean package -DskipTests
java -jar target/smartstockai-1.0.0.jar --spring.profiles.active=test
```

This uses H2 in-memory database. Access at `http://localhost:8080`

> **Note**: Data will be lost when you stop the application. For persistent storage, use MySQL (see below).

#### 💾 Production Setup (MySQL)

1. **Install MySQL 8.0+** and create database:
```sql
CREATE DATABASE smartstockai;
```

2. **Set database credentials**:

**Option A**: Create `local-config.bat` (recommended - not tracked by git):
```batch
set DB_USERNAME=root
set DB_PASSWORD=your_actual_password
java -jar target\smartstockai-1.0.0.jar
```

**Option B**: Set environment variables:
```bash
# Windows
set DB_USERNAME=root
set DB_PASSWORD=your_password

# Linux/Mac
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

**Option C**: Update `start-smartstockai.bat` with your credentials

3. **Clone and run**:
```bash
git clone https://github.com/yourusername/smartstockai.git
cd smartstockai
mvn clean package -DskipTests
java -jar target/smartstockai-1.0.0.jar
```

#### 🪟 Windows Users - Easy Startup

1. Build once: `mvn clean package -DskipTests`
2. Double-click `start-smartstockai.bat`
3. Or create desktop shortcut: Double-click `create-desktop-shortcut.bat`

See [HOW-TO-START.md](HOW-TO-START.md) for detailed instructions.

## 👥 Default Login Credentials

| Role | Username | Password | Access Level |
|------|----------|----------|--------------|
| Owner | `prabir` | `prabir123` | Full system access |
| Sales Manager | `sales` | `sales123` | Sales operations |
| Stock Manager | `stock` | `stock123` | Inventory management |

> **Note**: Change these passwords after first login for security.

## 📸 Screenshots

### Login Page
Modern, animated login interface with gradient background.

### Owner Dashboard
Comprehensive analytics with profit tracking, charts, and inventory management.

### Sales Dashboard
Streamlined interface for recording sales and checking stock levels.

### Stock Dashboard
Efficient inventory management with low-stock alerts.

---

## 📚 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Products
- `GET /api/products` - Get all products
- `POST /api/products` - Create product (Owner/Stock Manager)
- `PUT /api/products/{id}` - Update product (Owner/Stock Manager)
- `DELETE /api/products/{id}` - Delete product (Owner)

### Stock
- `GET /api/stock` - Get all stock
- `POST /api/stock/receive` - Receive stock (Owner/Stock Manager)
- `GET /api/stock/low-stock` - Get low stock items

### Sales
- `GET /api/sales` - Get all sales
- `POST /api/sales` - Record sale (Owner/Sales Manager)
- `GET /api/sales/date-range` - Get sales by date range

### Dashboard
- `GET /api/owner/summary` - Get profit summary (Owner)
- `GET /api/predictions/forecast/{productId}` - Get demand forecast (Owner)

## Project Structure

```
src/
├── main/
│   ├── java/com/smartstockai/
│   │   ├── config/          # Security, Database, Scheduler configs
│   │   ├── controller/      # REST API controllers
│   │   ├── dao/             # Data Access Objects
│   │   ├── model/           # Entity models
│   │   ├── service/         # Business logic
│   │   ├── ml/              # Machine learning services
│   │   └── util/            # Utility classes
│   └── resources/
│       ├── static/          # Frontend files
│       │   ├── dashboard/   # Role-specific dashboards
│       │   ├── js/          # JavaScript utilities
│       │   ├── api.js       # API service layer
│       │   ├── login.html   # Login page
│       │   └── index.html   # Landing page
│       └── application.properties
```

## Features by Role

### Owner Dashboard
- View total income, profit, and product count
- Manage products (CRUD operations)
- Receive stock and manage inventory
- Record sales
- View demand predictions with charts
- Monitor low stock alerts

### Sales Manager Dashboard
- Record new sales
- View sales history
- Check current stock levels
- Search products
- Low stock warnings

### Stock Manager Dashboard
- Receive new stock
- View complete inventory
- Monitor stock levels
- Track critical stock items
- Search inventory

## Security

- JWT-based authentication
- Role-based authorization using Spring Security
- Password encryption
- Protected API endpoints
- Session management

## 🛠️ Built With

- **Backend**: Spring Boot, Spring Security, Spring Data JPA, Hibernate
- **Frontend**: HTML5, CSS3, JavaScript (ES6+), Bootstrap 5, Chart.js
- **Database**: MySQL 8.0+
- **Authentication**: JWT (JSON Web Tokens)
- **Build Tool**: Maven
- **AI/ML**: Custom demand forecasting algorithms

## 📱 Mobile Support

Fully responsive design works seamlessly on:
- 📱 Mobile phones
- 📱 Tablets
- 💻 Desktops
- 🖥️ Large screens

Access from any device on your local network using your computer's IP address.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 💬 Support

- 📧 For issues and questions, please create an issue in the repository
- 📖 Check [HOW-TO-START.md](HOW-TO-START.md) for detailed setup instructions
- 🐛 Report bugs via GitHub Issues

## 🌟 Acknowledgments

- Spring Boot team for the excellent framework
- Bootstrap team for the UI components
- Chart.js for beautiful data visualizations

---

<div align="center">

Made with ❤️ by SmartStockAI Team

⭐ Star this repo if you find it helpful!

</div>
