package de.sopa;


import de.sopa.model.GameEndService;
import de.sopa.model.GameField;
import de.sopa.model.GameFieldService;

public class GameFieldDestroyer {
    public GameField destroyField(GameField gameField, int minShiftCount, int maxShiftCount, int maxValue) {
        GameFieldService gameFieldService = new GameFieldService();
        GameEndService gameEndService = new GameEndService();
        int shiftCount = (int) (Math.random()*(maxShiftCount-minShiftCount+1)+minShiftCount);
        for (int i = 0; i<shiftCount; i++) {
            boolean horizontal=(Math.random()>0.5f);
            int row;
            if(horizontal) {
                row = (int) (Math.random()*(gameField.getField()[0].length-2));
            } else {
                row = (int) (Math.random()*(gameField.getField().length-2));
            }
            gameFieldService.shiftLine(gameField,horizontal,row,(int) (Math.random()*(maxValue+1)));
        }
        return gameField;
    }
}
