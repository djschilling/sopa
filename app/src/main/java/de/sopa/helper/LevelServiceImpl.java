package de.sopa.helper;

import de.sopa.database.LevelInfoDataSource;
import de.sopa.model.game.Level;
import de.sopa.model.game.LevelInfo;
import de.sopa.model.game.StarCalculator;
import java.util.List;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LevelServiceImpl implements LevelService {

    private final LevelFileService levelHandler;
    private final LevelInfoDataSource levelInfoDataSource;
    private final StarCalculator starCalculator;

    public LevelServiceImpl(LevelFileService levelHandler, LevelInfoDataSource levelInfoDataSource) {
        this.levelHandler = levelHandler;
        this.levelInfoDataSource = levelInfoDataSource;
        this.starCalculator = new StarCalculator();
    }

    @Override
    public int getLevelCount() {
        return levelInfoDataSource.getLevelCount();
    }

    @Override
    public Level getLevelById(Integer id) {
        Level level = levelHandler.getLevel(id);
        level.setId(id);
        return updateLevelWithLevelInfo(level);

    }

    private Level updateLevelWithLevelInfo(Level level) {
        LevelInfo levelInfo = levelInfoDataSource.getLevelInfoById(level.getId());
        level.setLevelInfo(levelInfo);
        return level;
    }

    @Override
    public List<LevelInfo> getLevelInfos() {
        return levelInfoDataSource.getAllLevelInfos();
    }

    @Override
    public LevelInfo getLastUnlocked() {
        return levelInfoDataSource.getLastUnlocked();
    }

    @Override
    public void updateLevelInfos() {
        Integer[] availableLevelIds = levelHandler.getAvailableLevelIds();
        List<LevelInfo> allLevelInfos = levelInfoDataSource.getAllLevelInfos();
        for (Integer availableLevelId : availableLevelIds) {
            boolean foundId = false;
            for (int levelInfoIndex = 0; levelInfoIndex < allLevelInfos.size(); levelInfoIndex++) {
                if (allLevelInfos.get(levelInfoIndex).getLevelId().equals(availableLevelId)) {
                    foundId = true;
                    break;
                }
            }
            if (!foundId) {
                levelInfoDataSource.createLevelInfo(new LevelInfo(availableLevelId, true, -1, 0));
            }
        }
    }

    @Override
    public Level calculateLevelResult(Level level) {
        if (level.getLevelInfo().getFewestMoves() == -1 || level.getMovesCount() < level.getMinimumMovesToSolve()){
            level.getLevelInfo().setFewestMoves(level.getMovesCount());
        }
        int stars = starCalculator.getStars(level.getMovesCount(), level.getMinimumMovesToSolve());
        if(level.getLevelInfo().getStars() < stars){
            level.getLevelInfo().setStars(stars);
        }
        return level;
    }

    @Override
    public LevelInfo updateLevelInfo(LevelInfo levelInfo) {
        return levelInfoDataSource.updateLevelInfo(levelInfo);
    }

    @Override
    public void unlockLevel(int levelId) {
        if(levelId <= getLevelCount()) {
            Level levelById = getLevelById(levelId);
            levelById.getLevelInfo().setLocked(false);
            levelInfoDataSource.updateLevelInfo(levelById.getLevelInfo());
        }
    }
}
