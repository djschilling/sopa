package de.sopa.model;


public class GameFieldDestroyer {
    public Level destroyField(Level level, int minShiftCount, int maxShiftCount) {
        GameFieldService gameFieldService = new GameFieldService();
        int shiftCount = (int) (Math.random() * (maxShiftCount - minShiftCount + 1) + minShiftCount);
        for (int i = 0; i < shiftCount; i++) {
            System.out.println("Destroyed");
            int row;
            int value;
            boolean horizontal;
            do {
                System.out.println("Destroyed123");
                horizontal = (Math.random() > 0.5f);
                if (horizontal) {
                    row = (int) (Math.random() * (level.getField()[0].length - 2));
                } else {
                    row = (int) (Math.random() * (level.getField().length - 2));
                }
                if(Math.random()>0.5) {
                    value = 1;
                } else {
                    value= -1;
                }
            } while (!gameFieldService.shiftLine(level, horizontal, row,value));

        }
        level.setMinimumMovesToSolve(shiftCount);
        return level;
    }
}
