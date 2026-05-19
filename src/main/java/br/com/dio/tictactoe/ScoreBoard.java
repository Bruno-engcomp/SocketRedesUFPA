package br.com.dio.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScoreBoard {

    @FXML
    private Label scoreXLabel;

    @FXML
    private Label scoreOLabel;

    private int scoreX;
    private int scoreO;

    public int getScoreO() {
        return scoreO;
    }

    public void setScoreO(int scoreO) {
        this.scoreO = scoreO;
    }

    public int getScoreX() {
        return scoreX;
    }

    public void setScoreX(int scoreX) {
        this.scoreX = scoreX;
    }

    public void scorePoint(String value)
    {
        if(value.equalsIgnoreCase("X"))
        {
            scoreX++;
        }
        else
        {
            scoreO++;
        }
    }
}
