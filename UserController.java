package com.example.vuln;

import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.*;

@RestController
public class UserController {

    // ❌ SQL Injection
    @GetMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test",
                "root",
                "root123"
            );

            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM users WHERE username='"
                    + username + "' AND password='" + password + "'";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return "Login success";
            }

        } catch (Exception e) {
            e.printStackTrace(); // ❌ Info leak
        }

        return "Login failed";
    }

    // ❌ Command Injection
    @GetMapping("/ping")
    public String ping(@RequestParam String host) {
        try {
            Process p = Runtime.getRuntime().exec("ping " + host);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );
            return reader.readLine();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // ❌ Path Traversal
    @GetMapping("/file")
    public String readFile(@RequestParam String name) {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(name)
            );
            return reader.readLine();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // ❌ XSS
    @GetMapping("/search")
    public String search(@RequestParam String q) {
        return "<h1>Result: " + q + "</h1>";
    }

    // ❌ Hardcoded admin
    @GetMapping("/admin")
    public String admin(@RequestParam String user,
                        @RequestParam String pass) {

        if ("admin".equals(user) && "admin123".equals(pass)) {
            return "Welcome Admin";
        }

        return "Access Denied";
    }
}
