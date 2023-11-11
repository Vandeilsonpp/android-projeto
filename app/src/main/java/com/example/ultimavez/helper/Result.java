package com.example.ultimavez.helper;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private final List<String> errors = new ArrayList<>();
    private T resultObject;

    public Result(T resultObject) {
        this.resultObject = resultObject;
    }

    public static <T> Result<T> valid(T resultObject) {
        return new Result<>(resultObject);
    }

    public static <T> Result<T> invalid(List<String> errors) {
        Result<T> validationResult = new Result<>(null);
        validationResult.addError(errors);
        return validationResult;
    }

    public static <T> Result<T> invalid(String error) {
        Result<T> validationResult = new Result<>(null);
        validationResult.addError(error);
        return validationResult;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public T getResultObject() {
        return resultObject;
    }

    public void addError(String notification) {
        errors.add(notification);
    }

    public void addError(List<String> notification) {
        errors.addAll(notification);
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }
}

