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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class RegoleCosaStampaController {

    private Utente utente;

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    
    @FXML
    private void avantiClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CosaStampa.fxml"));
            Parent root = loader.load();
            CosaStampaController CosaStampa = loader.getController();
            CosaStampa.setUtente(this.utente);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Errore nel caricamento della finestra: "+e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    @FXML private void indietroClicked(ActionEvent event) {
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
