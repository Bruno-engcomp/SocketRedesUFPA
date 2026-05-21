package br.com.dio.tictactoe;

public interface GameMessageListener {
    void onGameStarted(String sign, boolean isFirstPlayer);
    void onOpponentMoved(int index, String opponentSign);
    void onRestartReceived();
    void onError(String errorMessage);
}
