package Esercizi.Magliarella;

import Esercizi.Front.FrontController;
import Login.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class RegoleCosaStampaController {

    private Utente utente;

    // Metodo per settare l'utente
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    // metodo per andare all'esercizio cosa stampa
    @FXML
    private void avantiClicked(ActionEvent event) {
        try {
            // Caricamento della finestra CosaStampa.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CosaStampa.fxml"));
            Parent root = loader.load();
            // ottenimento del controller della finestra CosaStampa.fxml e settare l'utente
            CosaStampaController CosaStampa = loader.getController();
            CosaStampa.setUtente(this.utente);
            // Creazione della scena e visualizzazione della finestra
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Errore nel caricamento della finestra: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo per tornare alla dashboard
    @FXML
    private void indietroClicked(ActionEvent event) {
        try {
            // Caricamento della finestra front.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/front.fxml"));
            Parent root = loader.load();
            // ottenimento del controller della finestra front.fxml e settare l'utente
            FrontController frontController = loader.getController();
            frontController.setUtente(utente);
            // Creazione della scena e visualizzazione della finestra
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
