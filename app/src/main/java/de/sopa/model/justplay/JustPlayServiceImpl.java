package de.sopa.model.justplay;

import de.sopa.helper.LevelCreator;
import de.sopa.model.game.LevelDestroyer;

/**
 * @author Raphael Schilling - raphaelschiling@gmail.com
 */
public class JustPlayServiceImpl implements JustPlayService {
    private int levelCount;
    private final LevelCreator levelCreator;
    private int leftTime;
    private int lastScore;
    private int[] difficultyGameSize =  {4,     5,      5,      6,      6,      6,      6};
    private int[] difficultyMoves =     {1,     2,      3,      2,      3,      4,      4};
    private int[] difficultyTime =      {3,     4,      5,      6,      8,      14,     12};
    private int[] difficultyScore =     {50,    70,    100,    100,    150,     250,    350};

    public JustPlayServiceImpl() {
        levelCreator = new LevelCreator();
        this.levelCount = 1;
        leftTime = 10;
        lastScore = 0;
    }

    @Override
    public JustPlayResult calculateResult(JustPlayLevelResult justPlayLevelResult) {
        LevelSetting levelSetting = getCurrentLevelSetting();
        int currentScore = calculateNewScore(justPlayLevelResult, levelSetting);
        JustPlayResult justPlayResult = new JustPlayResult(levelCount, justPlayLevelResult.getLeftTime(), levelSetting.getExtraTime(), lastScore, currentScore);
        lastScore = currentScore;
        leftTime = levelSetting.getExtraTime() + justPlayLevelResult.getLeftTime();
        levelCount++;
        return justPlayResult;
    }

    private LevelSetting getCurrentLevelSetting() {
        if(levelCount <= 3) {
            return new LevelSetting(difficultyGameSize[0], difficultyMoves[0], difficultyTime[0], difficultyScore[0]);
        } else if(levelCount <= 6) {
            return new LevelSetting(difficultyGameSize[1], difficultyMoves[1], difficultyTime[1], difficultyScore[1]);
        } else if(levelCount <= 14) {
            return new LevelSetting(difficultyGameSize[2], difficultyMoves[2], difficultyTime[2], difficultyScore[2]);
        } else if(levelCount <= 17) {
            return new LevelSetting(difficultyGameSize[3], difficultyMoves[3], difficultyTime[3], difficultyScore[3]);
        } else if(levelCount <= 23) {
            return new LevelSetting(difficultyGameSize[4], difficultyMoves[4], difficultyTime[4], difficultyScore[4]);
        } else if(levelCount <= 33) {
            return new LevelSetting(difficultyGameSize[5], difficultyMoves[5], difficultyTime[5], difficultyScore[5]);
        } else {
            return new LevelSetting(difficultyGameSize[6], difficultyMoves[6], difficultyTime[6], difficultyScore[6]);
        }
    }

    private int calculateNewScore(JustPlayLevelResult justPlayLevelResult, LevelSetting levelSetting) {
        if(justPlayLevelResult.getLeftTime() == -1) {
            return lastScore;
        } else {
            return lastScore +  levelSetting.getMaxScore() - justPlayLevelResult.getMoves() * 10;
        }
    }



    @Override
    public JustPlayLevel getNextLevel() {
        LevelSetting currentLevelSetting = getCurrentLevelSetting();
        return new JustPlayLevel(leftTime, levelCreator.generateLevel(currentLevelSetting.getSize(), currentLevelSetting.getMoves()));
    }

}
