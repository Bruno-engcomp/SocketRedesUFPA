package br.com.dio.tictactoe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller implements GameMessageListener {
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

    private TicTacToeClient client;
    ScoreBoard scoreBoard = new ScoreBoard();
    GameLogic gameLogic = new GameLogic();
    List<Button> allButtons = new ArrayList<>();

    @FXML
    private void initialize()
    {
        allButtons.addAll(Arrays.asList(btn11, btn12, btn13, btn21, btn22, btn23, btn31, btn32, btn33));

        scoreBoard.setScoreX(0);
        scoreBoard.setScoreO(0);
        resetBoardUI();

        // Instancia a classe de rede separada passando "this" como o listener das mensagens
        client = new TicTacToeClient("localhost", 12345, this);
        client.start();
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        // O controller pergunta para classe de rede se e o meu turno
        if(!client.isMyTurn()) return;

        Button clickedButton = (Button) event.getSource();
        if(clickedButton.getText().isEmpty())
        {
            int buttonIndex = allButtons.indexOf(clickedButton);

            applyMove(clickedButton, client.getMySign());
            client.sendMove(buttonIndex); // Envia a jogada para a rede através do objeto cliente

            statusLabel.setText("Aguardando Oponente...");
            checkGameStatus();
        }
    }

    @FXML
    private void restartButtonClick()
    {
        client.sendRestart();
        resetBoardUI();
    }

    // Metodos da interface GameMessageListener

    @Override
    public void onGameStarted(String sign, boolean isFirstPlayer) {
        Platform.runLater(() -> statusLabel.setText("Você é o jogador: " + sign +
                (isFirstPlayer ? " (Seu turno)" : " (Aguardando oponente)")));
    }

    @Override
    public void onOpponentMoved(int index, String opponentSign) {
        Platform.runLater(() -> {
            Button btn = allButtons.get(index);
            applyMove(btn, opponentSign);
            statusLabel.setText("Seu turno (" + client.getMySign() + ")!");
            checkGameStatus();
        });
    }

    @Override
    public void onRestartReceived() {
        Platform.runLater(this::resetBoardUI);
    }

    @Override
    public void onError(String errorMessage) {
        Platform.runLater(() -> statusLabel.setText(errorMessage));
    }

    // Metodos auxiliares da interface grafica, buscando evitar a repeticao de codigo

    private void applyMove(Button button, String sign) {
        button.setText(sign);
        if (sign.equals("X")) {
            button.setStyle("-fx-text-fill: #3498db; -fx-font-size: 40px; -fx-font-weight: bold;");
        } else {
            button.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 40px; -fx-font-weight: bold;");
        }
    }

    private void checkGameStatus() {
        String winner = gameLogic.checkWinner(allButtons);
        if (winner != null) {
            announceWinner(winner);
        } else if (gameLogic.isDraw(allButtons)) {
            statusLabel.setText("Empate!");
        }
    }

    private void resetBoardUI ()
    {
        for (Button btn : allButtons) {
            btn.setDisable(false);
            btn.setText("");
            btn.setStyle("-fx-font-weight: bold; -fx-padding: 0;"); //-fx-font-size: 40px;

        }
        statusLabel.setText("Aguardando conexão...");
        if (client != null && client.getMySign() != null) {
            statusLabel.setText("Você é o jogador: " + client.getMySign() +
                    (client.isMyTurn() ? " (Seu turno)" : " (Aguardando oponente)"));
        }
        refreshScore();
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
}
