### **Database Monitoring System**  

#### **Overview**  
The **Database Monitoring System** is a Spring Boot-based application designed to track and monitor real-time database performance metrics such as CPU usage, memory consumption, and database statistics (active connections, total queries, slow queries, etc.). It provides REST APIs to fetch these metrics in JSON format, making it easy to integrate with monitoring tools like Prometheus and Grafana.  

#### **Features**  
✅ **CPU Usage Monitoring** – Fetches system CPU usage.  
✅ **Memory Usage Monitoring** – Retrieves used, free, and total memory statistics.  
✅ **Database Statistics** – Tracks database metrics like active connections, total queries executed, and slow queries.  
✅ **REST API Endpoints** – Provides easy access to monitoring data in JSON format.  
✅ **Error Handling** – Proper exception handling for database connection issues.  

#### **API Endpoints**  
| Endpoint               | Method | Description |
|------------------------|--------|-------------|
| `/metrics/cpu`        | GET    | Returns current CPU usage |
| `/metrics/memory`     | GET    | Returns memory usage details (used, free, total) |
| `/metrics/dbstats`    | GET    | Fetches database statistics (threads, queries, slow queries) |

#### **Technologies Used**  
- **Spring Boot** (REST APIs)  
- **MySQL** (Database)  
- **JDBC** (Database connectivity)  
- **Prometheus & Grafana** (For future monitoring integration)  

#### **Setup & Installation**  
1. Clone the repository:  
   ```sh
   git clone https://github.com/your-username/database_monitoring.git
   cd database_monitoring
   ```
2. Configure **`application.properties`** with your database connection details:  
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```
3. Run the application:  
   ```sh
   mvn spring-boot:run
   ```

#### **Example Response**  
**GET `/metrics/dbstats`**  
```json
{
  "Queries": "1104",
  "Slow_queries": "0",
  "Threads_connected": "1"
}
```

#### **Future Enhancements**  
🚀 Add database query execution time monitoring  
🚀 Integrate with **Prometheus & Grafana** for visualization  
🚀 Implement **alerts** for slow queries and high CPU usage  

---  
Feel free to contribute and enhance the system! 🚀
