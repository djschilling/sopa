package de.sopa.helper;

/**
 * @author  David Schilling - davejs92@gmail.com
 */
class LevelServiceException extends RuntimeException {

    LevelServiceException(String detailMessage, Throwable throwable) {

        super(detailMessage, throwable);
    }
}
