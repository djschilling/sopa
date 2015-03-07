package de.sopa.helper;

import de.sopa.model.game.Level;
import de.sopa.model.game.LevelInfo;
import de.sopa.model.game.LevelResult;

import java.util.List;

/**
 * @author David Schilling - davejs92@gmail.com
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

    LevelResult calculateLevelResult(Level level);

    LevelInfo persistLevelResult(LevelResult levelResult);

    void unlockLevel(int levelId);
}
