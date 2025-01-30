package com.example.tms.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String status;
    private String error;

    public ErrorResponse(String status, String error) {
        this.status = status;
        this.error = error;
    }
}
