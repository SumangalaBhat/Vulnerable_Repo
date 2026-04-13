package com.example;

import java.io.*;

public class SystemService {

    public String pingHost(String host) throws Exception {
        // ❌ Vulnerable to Command Injection
        Process process = Runtime.getRuntime().exec("ping " + host);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;
        StringBuilder output = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        return output.toString();
    }
}
