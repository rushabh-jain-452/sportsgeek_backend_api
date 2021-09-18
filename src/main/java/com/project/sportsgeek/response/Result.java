package com.project.sportsgeek.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    @JsonIgnore
    private int code;
    private String message;
    private T data;
    private List<SportsGeekSystemError> errors;

    public Result() {
        super();
    }

    public Result(String message){
        super();
        this.message = message;
    }

    public Result(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Result(int code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, List<SportsGeekSystemError> error) {
        super();
        this.code = code;
        this.errors = error;
    }

    public Result(int code, String message, List<SportsGeekSystemError> error) {
        super();
        this.code = code;
        this.message = message;
        this.errors = error;
    }

    public Result(int code, SportsGeekSystemError error) {
        super();
        this.code = code;
        addErrorToList(error);
    }

    public Result(int code, String message, SportsGeekSystemError error) {
        super();
        this.code = code;
        this.message = message;
        addErrorToList(error);
    }

    public Result(int code, String message, T data, List<SportsGeekSystemError> error) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<SportsGeekSystemError> getError() {
        return errors;
    }

    public void setErrorList(List<SportsGeekSystemError> error) {
        this.errors = error;
    }

    public void addErrorToList(SportsGeekSystemError error) {
        if (this.errors == null) {
            this.errors = new ArrayList<SportsGeekSystemError>();
        }
        this.errors.add(error);
    }

    @Override
    public String toString() {
//        return "SportsGeekSystemResponse [message=" + message + ", data=" + data + ", errors=" + errors + "]";
        return "SportsGeekSystemResponse [message=" + message + ", data=" + data + "]";
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SportsGeekSystemError {
        private int errorId;
        private String errorMessage;
        private String field;

        public SportsGeekSystemError() {
            super();
        }

        public SportsGeekSystemError(int errorId, String errorMessage) {
            super();
            this.errorId = errorId;
            this.errorMessage = errorMessage;
        }

        public SportsGeekSystemError(int errorId, String errorMessage, String field) {
            super();
            this.errorId = errorId;
            this.errorMessage = errorMessage;
            this.field = field;
        }

        public int getErrorId() {
            return errorId;
        }

        public void setErrorId(int errorId) {
            this.errorId = errorId;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return "SportsGeekSystemError [errorId=" + errorId + ", errorMessage=" + errorMessage + ", field=" + field
                    + "]";
        }
    }
}

