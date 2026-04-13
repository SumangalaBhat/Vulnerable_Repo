package com.example;

public class AuthService {

    // ❌ Hardcoded secret
    private static final String SECRET_KEY = "my-super-secret-key";

    public boolean authenticate(String token) {
        return token.equals(SECRET_KEY);
    }
}
