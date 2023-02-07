package com.hyunki.pointapi.exception.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private int code;
    private String message;
    private String errorCode;
}
