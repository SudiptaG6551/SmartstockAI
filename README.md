# SmartStockAI - Inventory Management System

A full-stack inventory management system built with Spring Boot and MySQL. It includes features like demand forecasting, user roles, and real-time analytics.

## What This Project Does

This is an inventory management system I built to help manage products, track sales, and monitor stock levels. It has three different user roles (Owner, Sales Manager, Stock Manager) and each role has different permissions.

The cool part is that it also tries to predict future demand for products using some basic machine learning concepts.

## Main Features

### User Roles
- **Owner** - Can access everything (analytics, predictions, manage users)
- **Sales Manager** - Can record sales and check stock
- **Stock Manager** - Can add new stock and manage inventory

### What You Can Do
- Add, edit, and delete products
- Keep track of inventory levels
- Record sales transactions
- See profit and expense reports
- Get predictions on product demand (still working on improving this!)
- Get alerts when stock is running low

### The Interface
- Built with Bootstrap 5, so it looks decent
- Different dashboards for each role
- Some charts to visualize the data
- Works on mobile too (mostly)

## Technologies I Used

### Backend
- Spring Boot 3.x (my first time using version 3!)
- Spring Security for login stuff
- Spring Data JPA for database operations
- MySQL database
- Some basic ML for predictions

### Frontend
- HTML/CSS/JavaScript
- Bootstrap 5 for styling
- Chart.js for graphs
- Font Awesome for icons

## How to Get Started

### What You Need First
- Java 17 or newer
- Maven (for building the project)
- MySQL 8.0 or newer

### Quick Test Run (No MySQL Setup!)

If you just want to try it out quickly without installing MySQL:

```bash
git clone https://github.com/Sudipta6051/SmartstockAI.git
cd SmartstockAI
mvn clean package -DskipTests
java -jar target/smartstockai-1.0.0.jar --spring.profiles.active=test
```

Then go to `http://localhost:8080` in your browser.

**Note:** This uses an in-memory database, so your data will disappear when you close the app. It's just for testing.

### Full Setup (With MySQL)

1. **Setup MySQL database:**
```sql
CREATE DATABASE smartstockai;
```

2. **Update database credentials** in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartstockai
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. **Clone and run:**
```bash
git clone https://github.com/Sudipta6051/SmartstockAI.git
cd SmartstockAI
mvn clean package -DskipTests
java -jar target/smartstockai-1.0.0.jar
```

4. Open your browser and go to `http://localhost:8080`

## Test Login Credentials

You can use these accounts to test different features:

| Username | Password | Role |
|----------|----------|------|
| prabir | prabir123 | Owner |
| sales | sales123 | Sales Manager |
| stock | stock123 | Stock Manager |

**Important:** You should probably change these passwords if you're actually going to use this!

## Project Structure

Here's how I organized the code:

```
src/
├── main/
│   ├── java/com/smartstockai/
│   │   ├── config/          # Configuration files
│   │   ├── controller/      # REST API endpoints
│   │   ├── dao/             # Database access
│   │   ├── model/           # Data models
│   │   ├── service/         # Business logic
│   │   ├── ml/              # ML prediction stuff
│   │   └── util/            # Helper classes
│   └── resources/
│       ├── static/          # Frontend files (HTML, CSS, JS)
│       │   ├── dashboard/   # Different dashboards
│       │   ├── js/          # JavaScript files
│       │   └── login.html
│       └── application.properties
```

## API Endpoints (The Main Ones)

### Authentication
- `POST /api/auth/register` - Create new account
- `POST /api/auth/login` - Login

### Products
- `GET /api/products` - Get all products
- `POST /api/products` - Add new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Stock Management
- `GET /api/stock` - View current stock
- `POST /api/stock/receive` - Add stock

### Sales
- `GET /api/sales` - View sales history
- `POST /api/sales` - Record a sale

## Things I Learned Building This

- How to use JWT tokens for authentication (took me a while to figure this out)
- Spring Security configuration (still learning this tbh)
- Connecting frontend to backend using REST APIs
- Basic machine learning concepts for predictions
- MySQL database design and relationships

## Known Issues / Things to Improve

- The demand prediction isn't super accurate yet, needs more work
- Could use better error handling in some places
- Mobile responsive design could be better
- Need to add more unit tests
- The UI could look more modern

## Contributing

If you want to improve this project, feel free to fork it and submit a pull request! I'm still learning, so any suggestions are welcome.

## License

MIT License - feel free to use this code for whatever you want

## Contact

If you find any bugs or have questions, just create an issue on GitHub.

---

Made by me as a learning project. Hope it helps someone! ⭐

**PS:** This is my first major Spring Boot project, so there might be some rough edges. Any feedback is appreciated!
