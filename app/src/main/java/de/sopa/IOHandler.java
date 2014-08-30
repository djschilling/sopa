package de.sopa;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public interface IOHandler {
    public void writeToFile(String filename, String[] strings, int fileCreationMode) throws IOException;

    public String[] readFromPrivateFile(String filename) throws IOException;
    public String[] getAvailableLevels();
}
