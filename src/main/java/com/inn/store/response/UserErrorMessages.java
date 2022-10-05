package com.inn.store.response;

public enum UserErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field ."),
    RECORD_ALREADY_EXISTS("Record already exists ."),
    INTERNAL_SERVER_ERROR("Internal your Server error ."),
    NO_RECORD_FOUND("Record with provided id is not found");

    private String errorMessage;
    private UserErrorMessages(String errorMessage) { this.errorMessage =errorMessage;}
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
