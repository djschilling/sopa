package de.sopa.model.justplay;

import de.sopa.helper.LevelCreator;


/**
 * @author  Raphael Schilling - raphaelschiling@gmail.com
 */
public class JustPlayServiceImpl implements JustPlayService {

    private int levelCount;
    private final LevelCreator levelCreator;
    private int leftTime;
    private int lastScore;
    private int[] difficultyGameSize =      {5,     5,     5,      5,      5,      6,      6,      6,       6 };
    private int[] difficultyMoves =         {2,     2,     2,      3,      3,      3,      3,      3,       3 };
    private int[] difficultyTime =          {5,     5,     7,      8,      8,      10,     8,      6,       6 };
    private int[] difficultyScore =         {5,     10,    20,     50,     200,    1000,   2000,   4000,    8000};
    private int[] difficultyTubesMin =      {3,     4,     4,      3,      5,      6,      8,      8,       10};
    private int[] difficultyTubesMax =      {3,     4,     9,      5,      9,      10,     12,     12,      16};

    public JustPlayServiceImpl() {

        levelCreator = new LevelCreator();
        this.levelCount = 1;
        leftTime = 10;
        lastScore = 0;
    }

    @Override
    public JustPlayResult calculateResult(JustPlayLevelResult justPlayLevelResult) {

        LevelSetting levelSetting = getCurrentLevelSetting();

        leftTime = calculateLeftTime(justPlayLevelResult.getLeftTime(), levelSetting.getExtraTime());
        int extraTime = calculateExtraTime(justPlayLevelResult.getLeftTime(), levelSetting.getExtraTime());

        int currentScore = calculateNewScore(justPlayLevelResult, levelSetting);
        JustPlayResult justPlayResult = new JustPlayResult(levelCount, justPlayLevelResult.getLeftTime(),
                extraTime, lastScore, currentScore);
        lastScore = currentScore;
        levelCount++;

        return justPlayResult;
    }

    private int calculateExtraTime(int leftTime, int extraTime) {
        int calculateLeftTime = calculateLeftTime(leftTime, extraTime);
        if(calculateLeftTime == 35) {
            return 35 - leftTime;
        }
        return extraTime;
    }

    private int calculateLeftTime(int leftTime, int extraTime) {
        int calculatedTime = extraTime + leftTime;
        if(calculatedTime >= 35) {
            return 35;
        }
        return calculatedTime;
    }


    private LevelSetting getCurrentLevelSetting() {

        if (levelCount <= 2) {
            return new LevelSetting(difficultyGameSize[0], difficultyMoves[0], difficultyTime[0], difficultyScore[0], difficultyTubesMin[0], difficultyTubesMax[0]);
        } else if (levelCount <= 5) {
            return new LevelSetting(difficultyGameSize[1], difficultyMoves[1], difficultyTime[1], difficultyScore[1], difficultyTubesMin[1], difficultyTubesMax[1]);
        } else if (levelCount <= 10) {
            return new LevelSetting(difficultyGameSize[2], difficultyMoves[2], difficultyTime[2], difficultyScore[2], difficultyTubesMin[2], difficultyTubesMax[2]);
        } else if (levelCount <= 20) {
            return new LevelSetting(difficultyGameSize[3], difficultyMoves[3], difficultyTime[3], difficultyScore[3], difficultyTubesMin[3], difficultyTubesMax[3]);
        } else if (levelCount <= 25) {
            return new LevelSetting(difficultyGameSize[4], difficultyMoves[4], difficultyTime[4], difficultyScore[4], difficultyTubesMin[4], difficultyTubesMax[4]);
        } else if (levelCount <= 30) {
            return new LevelSetting(difficultyGameSize[5], difficultyMoves[5], difficultyTime[5], difficultyScore[5], difficultyTubesMin[5], difficultyTubesMax[5]);
        } else if (levelCount <= 35) {
            return new LevelSetting(difficultyGameSize[5], difficultyMoves[5], difficultyTime[5], difficultyScore[5], difficultyTubesMin[5], difficultyTubesMax[5]);
        } else if (levelCount <= 40) {
            return new LevelSetting(difficultyGameSize[6], difficultyMoves[6], difficultyTime[6], difficultyScore[6], difficultyTubesMin[6], difficultyTubesMax[6]);
        } else {
            return new LevelSetting(difficultyGameSize[7], difficultyMoves[7], difficultyTime[7], difficultyScore[7], difficultyTubesMin[7], difficultyTubesMax[7]);
        }
    }


    private int calculateNewScore(JustPlayLevelResult justPlayLevelResult, LevelSetting levelSetting) {

        if (justPlayLevelResult.getLeftTime() == -1) {
            return lastScore;
        } else {
            double movesToMinMovesRatio = (double) justPlayLevelResult.getMinLevelMoves()
                / justPlayLevelResult.getMoves();

            double additionalScore = levelSetting.getMaxScore() * movesToMinMovesRatio;

            return lastScore + (int) additionalScore;
        }
    }


    @Override
    public JustPlayLevel getNextLevel() {

        LevelSetting currentLevelSetting = getCurrentLevelSetting();

        return new JustPlayLevel(leftTime,
                levelCreator.generateLevel(currentLevelSetting.getSize(), currentLevelSetting.getMoves(), currentLevelSetting.getMinTubes(), currentLevelSetting.getMaxTubes()));
    }
}
