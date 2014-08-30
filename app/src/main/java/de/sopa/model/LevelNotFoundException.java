package de.sopa.model;

/**
 * @author Raphael Schilling
 */
public class LevelNotFoundException extends RuntimeException {
    public LevelNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
