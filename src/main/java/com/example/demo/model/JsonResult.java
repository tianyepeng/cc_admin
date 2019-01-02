package com.example.demo.model;

public class JsonResult<T> {

    private boolean isSuccess;
    private int statusCode;
    private String message;
    private T data;

    public JsonResult(T data) {
        this(true, 200, "OK", data);
    }

    public JsonResult(int statusCode, String message) {
        this(false, statusCode, message, null);
    }

    public JsonResult(boolean isSuccess, int statusCode, String message, T data) {
        this.setIsSuccess(isSuccess);
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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

}
