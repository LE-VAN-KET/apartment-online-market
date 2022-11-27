package com.cdcn.apartmentonlinemarket.exception.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {
    private Integer code;
    private String message;
    private List<String> messages;

    public ErrorResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public ErrorResponse(Integer code, List<String> messages) {
        this.code = code;
        this.messages = messages;
    }

    public ErrorResponse(Integer code, String message, List<String> messages) {
        this.code = code;
        this.message = message;
        this.messages = messages;
    }
}
