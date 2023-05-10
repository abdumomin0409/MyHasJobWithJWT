package com.company.job.myhasjobwithjwt.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T>{

    private String message;
    private T data;
    private AppErrorDTO error;
    private boolean success;

    public ResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
        this.success = true;
    }

    public ResponseDTO(T data) {
        this.data = data;
        this.success = true;
    }


    public ResponseDTO(AppErrorDTO error) {
        this.error = error;
        this.success = false;
    }


}
