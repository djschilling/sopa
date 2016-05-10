package de.sopa.model.justplay;

/**
 * @author  Raphael Schilling - raphaelschiling@gmail.com
 */
class LevelSetting {

    private final int size;
    private final int moves;
    private final int extraTime;
    private final int maxScore;
    private final int minTubes;
    private final int maxTubes;

    LevelSetting(int size, int moves, int extraTime, int maxScore, int minTubes, int maxTubes) {

        this.size = size;
        this.moves = moves;
        this.extraTime = extraTime;
        this.maxScore = maxScore;
        this.minTubes = minTubes;
        this.maxTubes = maxTubes;
    }

    int getExtraTime() {

        return extraTime;
    }


    int getMaxScore() {

        return maxScore;
    }


    int getMoves() {

        return moves;
    }


    public int getSize() {

        return size;
    }


    int getMinTubes() {

        return minTubes;
    }


    int getMaxTubes() {

        return maxTubes;
    }
}
