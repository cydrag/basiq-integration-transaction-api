package com.cydrag.util;

import java.util.HashMap;
import java.util.Map;

public class FormUrlEncodedConverter {

    private final Map<String, String> properties;

    public FormUrlEncodedConverter() {
        this.properties = new HashMap<>();
    }

    public FormUrlEncodedConverter addProperty(String name, String value) {
        properties.put(name, value);
        return this;
    }

    public FormUrlEncodedConverter removeProperty(String name) {
        properties.remove(name);
        return this;
    }

    public byte[] toByteArray() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : this.properties.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
        }

        return stringBuilder.toString().getBytes();
    }
}
