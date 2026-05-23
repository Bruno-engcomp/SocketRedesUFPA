package br.com.dio.tictactoe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    @FXML
    private Button btn11, btn12, btn13;
    @FXML
    private Button btn21, btn22, btn23;
    @FXML
    private Button btn31, btn32, btn33;
    @FXML private Button btnRestart;
    @FXML private Label statusLabel;
    @FXML private Label scoreXLabel;
    @FXML private Label scoreOLabel;
    @FXML private Button btnIpConfig;
    @FXML private TextField campoIp;

    //private TicTacToeClient client;
    ScoreBoard scoreBoard = new ScoreBoard();
    GameLogic gameLogic = new GameLogic();
    List<Button> allButtons = new ArrayList<>();

    private boolean isXturn = true;

    @FXML
    private void initialize()
    {

        if (btn11 != null) {
            allButtons.addAll(Arrays.asList(btn11, btn12, btn13, btn21, btn22, btn23, btn31, btn32, btn33));
            scoreBoard.setScoreX(0);
            scoreBoard.setScoreO(0);
            resetBoardUI();
        }
    }

    private void resetBoardUI ()
    {
        for (Button btn : allButtons) {
            btn.setText("");
            btn.setStyle("-fx-font-weight: bold; -fx-padding: 0;"); //-fx-font-size: 40px;

        }
        scoreXLabel.setText(String.valueOf(scoreBoard.getScoreX()));
        scoreOLabel.setText(String.valueOf(scoreBoard.getScoreO()));
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton.getText().isEmpty())
        {
            if(isXturn)
            {
                clickedButton.setText("X");
                clickedButton.setStyle("-fx-text-fill: #3498db; -fx-font-size: 40px; -fx-font-weight: bold;");
            }  // Blue for X
            else
            {
                clickedButton.setText("O");
                clickedButton.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 40px; -fx-font-weight: bold;"); // Red for X
            }
            isXturn = !isXturn;

            String winner = gameLogic.checkWinner(allButtons);
            if (winner != null)
                announceWinner(winner);
            else if (gameLogic.isDraw(allButtons))
                statusLabel.setText("It's draw!");
        }
    }

    @FXML
    private void restartButtonClick()
    {
        for(Button btn : allButtons)
        {
            btn.setDisable(false);
            btn.setText("");
            statusLabel.setText("");
        }
        isXturn = true;
    }

    private void announceWinner (String winner)
    {
        statusLabel.setText("Player " + winner + " wins!");

        for (Button btn : allButtons) {
            btn.setDisable(true); // Disable all buttons to no more moves can be made
        }

        scoreBoard.scorePoint(winner);
        refreshScore();
    }

    private void refreshScore()
    {
        scoreXLabel.setText(String.valueOf(scoreBoard.getScoreX()));
        scoreOLabel.setText(String.valueOf(scoreBoard.getScoreO()));
    }



    @FXML
    public void clicouIpConfig(ActionEvent event) {
        // Esconde o botão e mostra o input
        btnIpConfig.setVisible(false);
        campoIp.setVisible(true);
        campoIp.requestFocus(); // Coloca o cursor piscando lá dentro
    }

    @FXML
    public void clicouStart(ActionEvent event) {
        String ipDigitado = campoIp.getText();
        System.out.println("Iniciando jogo! Conectando no IP: " + ipDigitado);

        try {

            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("TicTacToe.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar carregar a tela do jogo.");
        }
    }

    @FXML
    public void clicouInfo(ActionEvent event) {
        System.out.println("Botão Info clicado!");
    }

}