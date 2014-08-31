package de.sopa.helper;

import de.sopa.database.LevelInfoDataSource;
import de.sopa.model.Level;
import de.sopa.model.LevelInfo;
import java.io.IOException;
import java.util.List;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LevelServiceImpl implements LevelService {

    private final LevelFileService levelHandler;
    private final LevelInfoDataSource levelInfoDataSource;

    public LevelServiceImpl(LevelFileService levelHandler, LevelInfoDataSource levelInfoDataSource) {
        this.levelHandler = levelHandler;
        this.levelInfoDataSource = levelInfoDataSource;
        updateLevelInfos();
    }

    @Override
    public int getLevelCount() {
        throw new UnsupportedOperationException();
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
    public void saveLevelInfo(LevelInfo levelInfo, Integer levelId) {
        levelInfo.setLevelId(levelId);
        levelInfoDataSource.createLevelInfo(levelInfo);
    }

    @Override
    public Integer createLevel(Level level) {
        try {
            Integer saveGameFieldId = levelHandler.saveGameField(level);
            levelInfoDataSource.createLevelInfo(new LevelInfo(saveGameFieldId, true, -1));
            return saveGameFieldId;
        } catch (IOException e) {
            throw new LevelServiceException("could not save level", e);
        }
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
                levelInfoDataSource.createLevelInfo(new LevelInfo(availableLevelId, true, -1));
            }
        }
    }
}
