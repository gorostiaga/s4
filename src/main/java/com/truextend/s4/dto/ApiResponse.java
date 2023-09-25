package com.truextend.s4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse implements Serializable {
    private boolean success;
    private String message;
    private String id;
    private int status;
    private long timeStamp;

    public ApiResponse(boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
        this.status = status;
    }

    public ApiResponse (boolean success, String message, String id, int status){
        this.success = success;
        this.message = message;
        this.id = id;
        this.timeStamp = System.currentTimeMillis();
        this.status = status;
    }
}
