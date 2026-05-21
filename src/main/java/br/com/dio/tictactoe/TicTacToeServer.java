package br.com.dio.tictactoe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("O servidor do jogo da velha está iniciando...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)){

            // Conexão com o jogador 1 X
            Socket player1 = serverSocket.accept(); // Accept bloqueia o servidor até a chegada de requisição de conexao
            PrintWriter output1 = new PrintWriter(player1.getOutputStream(), true);
            BufferedReader input1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            output1.println("Bem vindo jogador X");
            System.out.println("Jogador 1 (x) conectado");

            // Conexão com o jogador 2 O
            Socket player2 = serverSocket.accept(); // Accept bloqueia o servidor até a chegada de requisição de conexao
            PrintWriter output2 = new PrintWriter(player1.getOutputStream(), true);
            BufferedReader input2 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            output1.println("Bem vindo jogador O");
            System.out.println("Jogador 2 (O) conectado");

            String inputLine;
            while(true)
            {
                // Lê a jogada do Jogador 1 e repassa para o Jogador 2
                if(input1.ready() && (inputLine = input1.readLine()) != null)
                {
                    System.out.println("Recebido do X: " + inputLine);
                    output2.println(inputLine);
                }

                // Lê a jogada do Jogador 2 e repassa para o Jogador 1
                if(input2.ready() && (inputLine = input2.readLine()) != null)
                {
                    System.out.println("Recebido do 0: " + inputLine);
                    output1.println(inputLine);
                }
                Thread.sleep(50); // Diminui a chance do meu computador explodir
            }


        }
        catch (Exception e)
        {
            System.out.println("Erro ao tentar se conectar com o servidor: " + e.getMessage());
        }
    }
}
