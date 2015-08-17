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
    private int[] difficultyGameSize =  {4,     5,      5,      6,      6,      6,      6};
    private int[] difficultyMoves =     {1,     2,      3,      2,      3,      4,      4};
    private int[] difficultyTime =      {3,     5,      7,      7,      10,     15,     12};
    private int[] difficultyScore =     {50,    70,    100,    100,    150,     250,    350};

    public JustPlayServiceImpl() {
        levelCreator = new LevelCreator();
        levelDestroyer = new LevelDestroyer();
        this.levelCount = 0;
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
        if(levelCount <= 2) {
            return new LevelSetting(difficultyGameSize[0], difficultyMoves[0], difficultyTime[0], difficultyScore[0]);
        } else if(levelCount <= 5) {
            return new LevelSetting(difficultyGameSize[1], difficultyMoves[1], difficultyTime[1], difficultyScore[1]);
        } else if(levelCount <= 13) {
            return new LevelSetting(difficultyGameSize[2], difficultyMoves[2], difficultyTime[2], difficultyScore[2]);
        } else if(levelCount <= 16) {
            return new LevelSetting(difficultyGameSize[3], difficultyMoves[3], difficultyTime[3], difficultyScore[3]);
        } else if(levelCount <= 22) {
            return new LevelSetting(difficultyGameSize[4], difficultyMoves[4], difficultyTime[4], difficultyScore[4]);
        } else if(levelCount <= 32) {
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
        return new JustPlayLevel(leftTime, levelDestroyer.destroyField(levelCreator.generateSolvedField(currentLevelSetting.getSize(),
                currentLevelSetting.getSize()), currentLevelSetting.getMoves(), currentLevelSetting.getMoves()));
    }

}
