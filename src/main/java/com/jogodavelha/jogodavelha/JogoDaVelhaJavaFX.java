package com.jogodavelha.jogodavelha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JogoDaVelhaJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("jogo-velha.fxml"));
        Parent root = loader.load();

        // Obtém a instância da classe controladora
        JogoDaVelhaController controller = loader.getController();

        primaryStage.setTitle("Jogo da Velha");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
