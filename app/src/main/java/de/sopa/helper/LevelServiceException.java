package de.sopa.helper;

/**
 * @author  David Schilling - davejs92@gmail.com
 */
public class LevelServiceException extends RuntimeException {

    public LevelServiceException(String detailMessage, Throwable throwable) {

        super(detailMessage, throwable);
    }
}
