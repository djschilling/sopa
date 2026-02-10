package com.sopaapp.model.justplay;

/**
 * @author  Raphael Schilling - raphaelschiling@gmail.com
 */
interface JustPlayService {

    JustPlayResult calculateResult(JustPlayLevelResult justPlayLevelResult);


    JustPlayLevel getNextLevel();
}
