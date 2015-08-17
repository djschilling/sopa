package de.sopa.model.justplay;

import de.sopa.helper.LevelCreator;
import de.sopa.model.game.LevelDestroyer;

/**
 * @author Raphael Schilling - raphaelschiling@gmail.com
 */
public class JustPlayServiceImpl implements JustPlayService {
    private int levelCount;
    private final LevelCreator levelCreator;
    private final LevelDestroyer levelDestroyer;
    private int leftTime;
    private int lastScore;

    public JustPlayServiceImpl() {
        levelCreator = new LevelCreator();
        levelDestroyer = new LevelDestroyer();
        this.levelCount = 0;
        leftTime = 10;
        lastScore = 0;
    }

    @Override
    public JustPlayResult calculateResult(JustPlayLevelResult justPlayLevelResult) {
        int currentScore = calculateNewScore(justPlayLevelResult);
        JustPlayResult justPlayResult = new JustPlayResult(levelCount, justPlayLevelResult.getLeftTime(), calculateExtraTime(),lastScore, currentScore);
        lastScore = currentScore;
        leftTime = calculateExtraTime() + justPlayLevelResult.getLeftTime();
        return justPlayResult;
    }

    private int calculateNewScore(JustPlayLevelResult justPlayLevelResult) {
        if(justPlayLevelResult.getLeftTime() == -1) {
            return lastScore;
        } else {
            return lastScore + 1000;
        }
    }

    private int calculateExtraTime() {
        return 5;
    }

    @Override
    public JustPlayLevel getNextLevel() {
        levelCount++;
        return new JustPlayLevel(leftTime, levelDestroyer.destroyField(levelCreator.generateSolvedField(4, 4), 1, 1));
    }




}
