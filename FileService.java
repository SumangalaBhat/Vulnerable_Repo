package com.example;

import java.io.*;

public class FileService {

    public String readFile(String filename) throws Exception {
        // ❌ Path Traversal vulnerability
        File file = new File("/var/data/" + filename);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder content = new StringBuilder();

        while ((line = br.readLine()) != null) {
            content.append(line);
        }

        return content.toString();
    }
}
