package de.sopa.model.justplay;

import de.sopa.model.game.Level;


/**
 * @author  Raphael Schilling - raphaelschiling@gmail.com
 */
public class JustPlayLevel {

    private final int leftTime;
    private final Level level;

    public JustPlayLevel(int leftTime, Level level) {

        this.leftTime = leftTime;
        this.level = level;
    }

    public Level getLevel() {

        return level;
    }


    public int getLeftTime() {

        return leftTime;
    }
}
