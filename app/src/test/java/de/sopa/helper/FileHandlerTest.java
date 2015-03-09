package de.sopa.helper;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.sopa.model.game.GameFieldService;
import de.sopa.model.game.Level;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FileHandlerTest {

    private LevelTranslator levelTranslator = new LevelTranslator();
    private LevelSolver levelSolver = new LevelSolver(new GameFieldService());

    @Test
    public void testLevelOfLevelmode() throws IOException {

        List<Level> levels = getLevel();
        for (Level level : levels) {
            if (level.getMinimumMovesToSolve() < 5) {
                Level solve = levelSolver.solve(level, level.getMinimumMovesToSolve());
                if (level.getMinimumMovesToSolve() != solve.getMinimumMovesToSolve()) {
                    System.out.println(level.getId() + "    Alt: " + level.getMinimumMovesToSolve() + " Neu: " + solve.getMinimumMovesToSolve());
                }
                assertThat(solve, notNullValue());
                assertThat(solve.getMinimumMovesToSolve(), is(level.getMinimumMovesToSolve()));
            } else {
                System.out.println("Level " + level.getId() + " not tested");
            }
        }

    }

    private List<Level> getLevel() throws IOException {
        File[] listOfFiles = getLevelNames();
        List<Level> levels = new ArrayList<>();
        for (File listOfFile : listOfFiles) {
            String[] levelAsString = getLevelAsString(listOfFile);
            levels.add(levelTranslator.fromString(levelAsString));
        }
        return levels;
    }


    private String[] getLevelAsString(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(fis));
        StringBuilder lineContent = new StringBuilder();
        int content;
        List<String> lines = new ArrayList<>();
        while ((content = inputStream.read()) != -1) {
            if ((char) content == '\n') {
                lines.add(lineContent.toString());
                lineContent = new StringBuilder();
            } else {
                lineContent.append((char) content);
            }

        }
        if (lineContent.length() != 0) {
            lines.add(lineContent.toString());
        }
        inputStream.close();
        return lines.toArray(new String[lines.size()]);
    }

    private File[] getLevelNames() {
        File app = new File("app");
        File folder;
        if (app.isDirectory()) {
            folder = new File("app/src/main/assets/levels");
        } else {
            folder = new File("src/main/assets/levels");
        }
        return folder.listFiles();
    }
}