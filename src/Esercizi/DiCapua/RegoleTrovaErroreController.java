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

public class RegoleTrovaErroreController {

    // Variabile per memorizzare l'utente attualmente loggato
    private Utente utente;

    //Metodo per impostare l'utente attuale
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @FXML
    private void avantiClicked(ActionEvent event) {
        try {
            // Carica il file FXML della schermata "TrovaErrore.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrovaErrore.fxml"));
            Parent root = loader.load();
            // Ottiene il controller della nuova schermata e imposta l'utente
            Esercizi.DiCapua.TrovaErroreController TrovaErroreController = loader.getController();
            TrovaErroreController.setUtente(utente);
            // Imposta la nuova scena e mostra la finestra aggiornata
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Gestione dell'errore in caso di problemi nel caricamento della schermata
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Errore nel caricamento dell'esercizio.");
            alert.showAndWait();
        }
    }

    //Metodo chiamato quando viene premuto il pulsante "Indietro"
    @FXML
    private void indietroClicked(ActionEvent event) {
        try {
            // Carica il file FXML della schermata Front.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/front.fxml"));
            Parent root = loader.load();
            // Ottiene il controller della schermata Front e imposta l'utente
            FrontController frontController = loader.getController();
            frontController.setUtente(utente);
            // Imposta la nuova scena e mostra la finestra aggiornata
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
