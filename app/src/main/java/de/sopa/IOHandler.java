package de.sopa;

import java.io.IOException;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public interface IOHandler {
    void writeToFile(String filename, String[] strings, int fileCreationMode) throws IOException;

    String[] readFromPrivateFile(String filename) throws IOException;
    String[] getAvailableLevels();

    Integer[] getAvailableLevelIds();
}
