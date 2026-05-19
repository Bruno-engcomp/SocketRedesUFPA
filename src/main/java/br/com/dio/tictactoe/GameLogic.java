package br.com.dio.tictactoe;

import javafx.scene.control.Button;

import java.util.List;

public class GameLogic {

    private static final int[][] WINNING_POSITIONS = { // all the 8 possibilities to win
            {0,1,2}, {3,4,5}, {6,7,8}, // Lines
            {0,3,6}, {1,4,7}, {2,5,8}, // Collunms
            {0,4,8}, {6,4,2}  // diagonal
    };

    public String checkWinner(List<Button> allButtons)
    {
        for(int [] possibilities : WINNING_POSITIONS)
        {
            String value1 = allButtons.get(possibilities[0]).getText();
            String value2 = allButtons.get(possibilities[1]).getText();
            String value3 = allButtons.get(possibilities[2]).getText();

            if(!value1.isEmpty() && value1.equals(value2) && value1.equals(value3))
            {
                return value1;
            }
        }
        return null;
    }

    public boolean isDraw(List<Button> allButtons)
    {
        for(Button btn : allButtons)
        {
            if(btn.getText().isEmpty()) return false;
        }
        return true;
    }

}
