package com.movies.dto;

/**
 * A DTO class for sending message responses.
 */
public class MessageResponse {

    // The message content
    private String message;

    /**
     * Constructor to initialize the message response.
     *
     * @param message the message content
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Gets the message content.
     *
     * @return the message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content.
     *
     * @param message the message content to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
