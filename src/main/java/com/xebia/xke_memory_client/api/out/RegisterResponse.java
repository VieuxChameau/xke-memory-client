package com.xebia.xke_memory_client.api.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegisterResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
