package com.example;

import java.io.*;

public class DeserializeService {

    public Object deserialize(byte[] data) throws Exception {
        // ❌ Insecure deserialization
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));

        return ois.readObject();
    }
}
