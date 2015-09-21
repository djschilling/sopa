package de.sopa.helper;

import de.sopa.database.LevelInfoDataSource;

import de.sopa.model.game.Level;
import de.sopa.model.game.LevelInfo;
import de.sopa.model.game.LevelResult;
import de.sopa.model.game.StarCalculator;

import java.util.List;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
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

            for (LevelInfo allLevelInfo : allLevelInfos) {
                if (allLevelInfo.getLevelId().equals(availableLevelId)) {
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
    public LevelResult calculateLevelResult(Level level) {

        int stars = starCalculator.getStars(level.getMovesCount(), level.getMinimumMovesToSolve());

        return new LevelResult(level.getId(), level.getMovesCount(), stars);
    }


    @Override
    public LevelInfo persistLevelResult(LevelResult levelResult) {

        LevelInfo levelInfo = levelInfoDataSource.getLevelInfoById(levelResult.getLevelId());

        if (levelInfo.getFewestMoves() == -1 || levelInfo.getFewestMoves() < levelResult.getMoveCount()) {
            levelInfo.setFewestMoves(levelResult.getMoveCount());
        }

        if (levelInfo.getStars() < levelResult.getStars()) {
            levelInfo.setStars(levelResult.getStars());
        }

        return levelInfoDataSource.updateLevelInfo(levelInfo);
    }


    @Override
    public void unlockLevel(int levelId) {

        if (levelId <= getLevelCount()) {
            Level levelById = getLevelById(levelId);
            levelById.getLevelInfo().setLocked(false);
            levelInfoDataSource.updateLevelInfo(levelById.getLevelInfo());
        }
    }
}
