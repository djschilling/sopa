package de.sopa.model;


public class GameFieldDestroyer {
    public Level destroyField(Level level, int minShiftCount, int maxShiftCount, int maxValue) {
        GameFieldService gameFieldService = new GameFieldService();
        GameEndService gameEndService = new GameEndService();
        int shiftCount = (int) (Math.random()*(maxShiftCount-minShiftCount+1)+minShiftCount);
        for (int i = 0; i<shiftCount; i++) {
            boolean horizontal=(Math.random()>0.5f);
            int row;
            if(horizontal) {
                row = (int) (Math.random()*(level.getField()[0].length-2));
            } else {
                row = (int) (Math.random()*(level.getField().length-2));
            }
            gameFieldService.shiftLine(level,horizontal,row,(int) (Math.random()*(maxValue+1)));
        }
        return level;
    }
}
