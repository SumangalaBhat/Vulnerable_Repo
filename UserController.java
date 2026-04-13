package com.example;

import java.sql.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/users")
public class UserController {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getUser(@PathParam("id") String id) throws Exception {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        // ❌ Vulnerable to SQL Injection
        String query = "SELECT name FROM users WHERE id = " + id;

        ResultSet rs = stmt.executeQuery(query);
        List<String> result = new ArrayList<>();

        while (rs.next()) {
            result.add(rs.getString("name"));
        }
        return result;
    }
}
