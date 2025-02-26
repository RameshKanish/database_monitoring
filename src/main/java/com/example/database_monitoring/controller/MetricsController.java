package com.example.database_monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @GetMapping("/cpu")
    public String getCPUUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        return "CPU Usage: " + osBean.getSystemLoadAverage();
    }

    @GetMapping("/memory")
    public String getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        return "Used Memory: " + (usedMemory / (1024 * 1024)) + " MB\n" +
                "Free Memory: " + (freeMemory / (1024 * 1024)) + " MB\n" +
                "Total Memory: " + (totalMemory / (1024 * 1024)) + " MB";
    }

    @Autowired(required = true)
    private DataSource dataSource;

    @GetMapping("/dbstats")  // Fixed incorrect path
    public ResponseEntity<Map<String , String>> getDbStats() throws SQLException {

        StringBuilder stats = new StringBuilder();
        Map<String , String> map = new HashMap<>();

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SHOW GLOBAL STATUS WHERE Variable_name IN ('Threads_connected', 'Queries', 'Slow_queries')")){
            System.out.println(resultSet);

            while (resultSet.next()){
                map.put(resultSet.getString("Variable_name") , resultSet.getString("value"));
            }
            return ResponseEntity.of(Optional.of(map));
        }catch (SQLException exception){
            Map<String , String> errorResponse = new HashMap<>();
            errorResponse.put("error" , "Error fetching db Stats : " +exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}