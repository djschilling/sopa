package com.sopaapp.highscore;

import com.sopaapp.database.LevelInfoDataSource;


/**
 * @author  David Schilling - schilling@synyx.de
 */
public class JustPlayScoreServiceImpl implements JustPlayScoreService {

    private final LevelInfoDataSource levelInfoDataSource;

    public JustPlayScoreServiceImpl(LevelInfoDataSource levelInfoDataSource) {

        this.levelInfoDataSource = levelInfoDataSource;
    }

    @Override
    public void submitScore(JustPlayScore score) {

        levelInfoDataSource.saveJustPlayScore(score);
    }


    @Override
    public JustPlayScore getHighscore() {

        return levelInfoDataSource.getBestJustPlayScore();
    }
}
