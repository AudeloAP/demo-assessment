package com.agileengine.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseBody {
    private String message;
    private String date_and_time;
    private String code;
}
