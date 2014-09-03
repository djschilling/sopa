package de.sopa.helper;

import de.sopa.model.Level;
import de.sopa.model.LevelInfo;
import java.util.List;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface LevelService {

    /**
     *
     * @return the total count of {@link de.sopa.model.Level}s
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
     * @return {@link de.sopa.model.LevelInfo}s for all Level
     */
    List<LevelInfo> getLevelInfos();

    /**
     * Saves the given {@link de.sopa.model.LevelInfo} for the given id.
     *
     * @param levelInfo the {@link de.sopa.model.LevelInfo} to save.
     * @param levelId the id for which the {@link de.sopa.model.LevelInfo} is saved.
     */
    void saveLevelInfo(LevelInfo levelInfo, Integer levelId);

    LevelInfo updateLevelInfo(LevelInfo levelInfo);

    /**
     * Saves the given {@link de.sopa.model.Level}.
     *
     * If the given {@link de.sopa.model.Level} has a id the level is updated,
     * else its saved as new level.
     *
     * @param level which should be saved.
     *
     * @return the id of the saved {@link de.sopa.model.Level}
     */
    Integer createLevel(Level level);

    void updateLevelInfos();
}
