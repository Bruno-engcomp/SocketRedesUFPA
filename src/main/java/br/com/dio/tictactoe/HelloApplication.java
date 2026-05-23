package br.com.dio.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Point to your Tic-Tac-Toe FXML instead of hello-view
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start.fxml"));

        // 2. Increase the size (600x400) so the grid isn't squashed
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        String myCss = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(myCss);

        stage.setTitle("TicTacToe Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}