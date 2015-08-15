package de.sopa.model.justplay;

/**
 * @author Raphael Schilling - raphaelschiling@gmail.com
 */
public interface JustPlayService {

    JustPlayResult calculateResult(JustPlayLevelResult justPlayLevelResult);

    JustPlayLevel getNextLevel();

}
