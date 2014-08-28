package de.sopa;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public interface IOHandler {
    public void writeToFile(String filename, String[] strings) throws IOException;

    public String[] readFromFile(String filename);
}
