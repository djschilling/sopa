package de.sopa.model.justplay;

import de.sopa.model.game.Level;
import de.sopa.scene.score.JustPlayResult;

/**
 * @author Raphael Schilling - raphaelschiling@gmail.com
 */
public interface JustPlayService {

    JustPlayResult calculateResult(JustPlayLevelResult justPlayLevelResult);

    JustPlayLevel getNextLevel();

}
