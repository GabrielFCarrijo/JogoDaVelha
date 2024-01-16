package com.jogodavelha.jogodavelha;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class JogoDaVelhaController {
    @FXML
    private Button button00;
    @FXML
    private Button button01;
    @FXML
    private GridPane gameGrid;
    private Label victoryMessage; // Alteração do tipo para Label
    @FXML
    private Label placarLabel;
    int vitoriasJogadorX;
    int vitoriasJogadorO;

    private char[][] tabuleiro = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    private char jogadorAtual = 'X';

    @FXML
    private void initialize() {
        victoryMessage = new Label();
        victoryMessage.setTextFill(Color.RED);
        victoryMessage.setStyle("-fx-font-size: 24;");

        GridPane.setHalignment(victoryMessage, HPos.CENTER);
        GridPane.setValignment(victoryMessage, VPos.CENTER);

        gameGrid.add(victoryMessage, 0, 3, 3, 1);
        victoryMessage.setVisible(false);
    }

    private void showVictoryMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vitória");
        alert.setHeaderText(null);
        alert.setContentText("Jogador " + jogadorAtual + " venceu!");

        // Incrementa o placar do jogador correspondente
        if (jogadorAtual == 'X') {

            vitoriasJogadorX++;
        } else {
            vitoriasJogadorO++;
        }

        // Exibe o placar
        exibirPlacar();

        alert.showAndWait();
    }

    private void exibirPlacar() {
        placarLabel.setText("Placar: X - " + vitoriasJogadorX + ", O - " + vitoriasJogadorO);
    }

    private void showDrawMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empate");
        alert.setHeaderText(null);
        alert.setContentText("O jogo terminou em empate!");
        alert.showAndWait();
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button botao = (Button) event.getSource();

        Integer linha = GridPane.getRowIndex(botao);
        Integer coluna = GridPane.getColumnIndex(botao);

        if (linha == null || coluna == null) {
            System.out.println("Erro: Não foi possível obter a posição do botão no GridPane.");
            return;
        }

        if (tabuleiro[linha][coluna] == ' ' && !verificarVitoria()) {
            tabuleiro[linha][coluna] = jogadorAtual;
            botao.setText(String.valueOf(jogadorAtual));
            trocarJogador();

            if (verificarVitoria()) {
                showVictoryMessage();
            } else if (verificarEmpate()) {
                reiniciarJogo();
            }
        }
    }

    @FXML
    private void reiniciarJogo() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
        jogadorAtual = 'X';

        // Limpa os textos apenas dos botões do jogo
        for (Node node : gameGrid.getChildren()) {
            if (node instanceof Button && !((Button) node).getText().equals("Reiniciar")) {
                ((Button) node).setText("");
            }
        }

        // Esconde a mensagem de vitória ou empate
        victoryMessage.setVisible(false);
    }


    private void trocarJogador() {
        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
    }

    private boolean verificarVitoria() {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == jogadorAtual && tabuleiro[i][1] == jogadorAtual && tabuleiro[i][2] == jogadorAtual) {
                return true;
            }
            if (tabuleiro[0][i] == jogadorAtual && tabuleiro[1][i] == jogadorAtual && tabuleiro[2][i] == jogadorAtual) {
                return true;
            }
        }

        if (tabuleiro[0][0] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][2] == jogadorAtual) {
            return true;
        }
        if (tabuleiro[0][2] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][0] == jogadorAtual) {
            return true;
        }

        return false;
    }

    private boolean verificarEmpate() {
        if (tabuleiroCompleto() && !verificarVitoria()) {
            showDrawMessage();
            return true;
        }
        return false;
    }


    private boolean tabuleiroCompleto() {
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                if (tabuleiro[linha][coluna] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

}
