package com.project.sportsgeek.exception;

import com.project.sportsgeek.response.Result;

public class ResultException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    Result<Object> resultException;

    public ResultException(Result<Object> resultException) {
        super(resultException.getMessage());
        this.resultException = resultException;
    }

    public Result<Object> getResultException() {
        return resultException;
    }

}
