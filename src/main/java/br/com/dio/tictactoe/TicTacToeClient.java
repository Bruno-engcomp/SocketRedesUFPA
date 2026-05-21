package br.com.dio.tictactoe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TicTacToeClient {

    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private String mySign;
    private boolean isMyTurn;

    private GameMessageListener listener;

    public TicTacToeClient(String host, int port, GameMessageListener listener)
    {
        this.host = host;
        this.port = port;
        this.listener = listener;
    }

    public void start()
    {
        new Thread (() -> {
            try
            {
                this.socket = new Socket(host, port);
                this.output = new PrintWriter(socket.getOutputStream(), true);
                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Processa a mensagem inicial de boas-vindas do Servidor
                String welcomeMessage = input.readLine();
                if(welcomeMessage != null && welcomeMessage.toLowerCase().startsWith("welcome"))
                {
                    this.mySign = welcomeMessage.split(" ")[1];
                    this.isMyTurn = mySign.equalsIgnoreCase("X"); // Sempre quem começa no jogo da velha e o X


                    // Notifica ao controller que o jogo iniciou para este cliente
                    listener.onGameStarted(mySign, isMyTurn);
                }

                // Loop contínuo escutando os dados da rede
                String serverMessage;
                while((serverMessage = input.readLine()) != null)
                {
                    String lowerMessage = serverMessage.toLowerCase();
                    if(lowerMessage.startsWith("move "))
                    {
                        int movedIndex = Integer.parseInt(serverMessage.split(" ")[1]);
                        String opponentSign = mySign.equalsIgnoreCase("X") ? "O" : "X";
                        this.isMyTurn = true;

                        listener.onOpponentMoved(movedIndex, opponentSign);
                    } else if (lowerMessage.equals("restart")) {
                        this.isMyTurn = mySign.equalsIgnoreCase("X");
                        listener.onRestartReceived();
                        
                    }
                }
            }
            catch (Exception e)
            {
                listener.onError("Falha na conexão com o servidor de rede.");
            }
        }).start();
    }

    //  Método simples para enviar uma jogada
    public void sendMove(int index)
    {
        if(output != null)
        {
            output.println("MOVE " + index);
            this.isMyTurn = false; // O meu turno recebe falso, ou seja, vira o turno do oponente
        }
    }

    // método para enviar o comando de reiniciar
    public void sendRestart()
    {
        if(output != null)
        {
            output.println("RESTART");
        }
    }

    public String getMySign() {
        return mySign;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }
}
