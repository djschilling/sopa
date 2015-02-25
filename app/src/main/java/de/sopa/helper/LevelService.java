package de.sopa.helper;

import de.sopa.model.game.Level;
import de.sopa.model.game.LevelInfo;
import java.util.List;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface LevelService {

    /**
     *
     * @return the total count of {@link de.sopa.model.game.Level}s
     */
    int getLevelCount();

    /**
     *
     * @param id
     *
     * @return the level with the given id
     */
    Level getLevelById(Integer id);

    /**
     *
     * @return {@link de.sopa.model.game.LevelInfo}s for all Level
     */
    List<LevelInfo> getLevelInfos();

    LevelInfo getLastUnlocked();

    void updateLevelInfos();

    Level calculateLevelResult(Level level);

    LevelInfo updateLevelInfo(LevelInfo levelInfo);

    void unlockLevel(int levelId);
}
