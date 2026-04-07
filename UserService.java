package com.example.vuln.service;

import java.sql.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public boolean login(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test", "root", "root123"
            );

            Statement stmt = conn.createStatement();

            // ❌ SQL Injection vulnerability
            String query = "SELECT * FROM users WHERE username='" 
                + username + "' AND password='" + password + "'";

            ResultSet rs = stmt.executeQuery(query);

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace(); // ❌ Info leakage
            return false;
        }
    }
}
