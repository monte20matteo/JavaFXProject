package Esercizi.DiCapua;

import Esercizi.Front.FrontController;
import Login.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import Login.Utente;

public class RegoleTrovaErroreController {

    private Utente utente;

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @FXML
    private void avantiClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrovaErrore.fxml"));
            Parent root = loader.load();
            Esercizi.DiCapua.TrovaErroreController TrovaErroreController = loader.getController();
            TrovaErroreController.setUtente(utente);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Errore nel caricamento dell'esercizio.");
            alert.showAndWait();
        }
    }

    @FXML
    private void indietroClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/front.fxml"));
            Parent root = loader.load();
            FrontController frontController = loader.getController();
            frontController.setUtente(utente);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
