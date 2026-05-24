package br.com.dio.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML private Button btnIpConfig;
    @FXML
    private TextField campoIp;

    @FXML
    public void clicouIpConfig(ActionEvent event) {
        btnIpConfig.setVisible(false);
        campoIp.setVisible(true);
        campoIp.requestFocus();
    }

    @FXML
    public void clicouStart(ActionEvent event) {
        String ip = campoIp.getText();

        // Se o usuário não digitar nada, define um IP padrão para não quebrar
        if (ip == null || ip.trim().isEmpty()) {
            ip = "localhost";
        }

        System.out.println("Iniciando jogo! Conectando no IP: " + ip);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TicTacToe.fxml"));
            Parent root = loader.load();

            // PEGA o controller real do jogo que o JavaFX acabou de criar
            Controller gameController = loader.getController();

            // Passa o IP digitado e a porta fixa (12345) de forma segura
            gameController.initData(ip, 12345);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela do jogo.");
        }
    }

    @FXML
    public void clicouInfo(ActionEvent event) {
        System.out.println("Botão Info clicado!");
    }

}
