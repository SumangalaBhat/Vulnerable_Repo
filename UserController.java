package com.example.vuln.controller;

import com.example.vuln.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ❌ SQL Injection via service
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        boolean success = userService.login(username, password);

        return success ? "Login success" : "Login failed";
    }

    // ❌ Command Injection
    @GetMapping("/ping")
    public String ping(@RequestParam String host) throws IOException {
        Process p = Runtime.getRuntime().exec("ping -c 1 " + host);

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(p.getInputStream())
        );

        return reader.readLine();
    }

    // ❌ Path Traversal
    @GetMapping("/file")
    public String readFile(@RequestParam String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(name)));
    }

    // ❌ XSS
    @GetMapping("/search")
    public String search(@RequestParam String q) {
        return "<h1>Result: " + q + "</h1>";
    }

    // ❌ Hardcoded admin credentials
    @GetMapping("/admin")
    public String admin(@RequestParam String user,
                        @RequestParam String pass) {

        if ("admin".equals(user) && "admin123".equals(pass)) {
            return "Welcome Admin";
        }
        return "Access Denied";
    }
}
