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

    void updateLevelInfos();

    Level updateFewestMoves(Level level);

    void unlockLevel(int levelId);
}
