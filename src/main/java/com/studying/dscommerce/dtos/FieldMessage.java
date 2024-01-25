package com.studying.dscommerce.dtos;

public class FieldMessage {

    String fieldName;
    String fieldMessage;

    public FieldMessage(String name, String message) {
        this.fieldName = name;
        this.fieldMessage = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }
}
