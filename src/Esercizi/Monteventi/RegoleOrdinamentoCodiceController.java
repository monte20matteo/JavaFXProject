package Esercizi.Monteventi;

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

public class RegoleOrdinamentoCodiceController {

    private Utente utente;

    // Metodo per settare l'utente corrente
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    //metodo per passare alla finestra successiva --> esercizi ordinamento codice
    @FXML
    private void avantiClicked(ActionEvent event) {
        try {
            // Caricamento della finestra OrdinamentoCodice.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Monteventi/OrdinamentoCodice.fxml"));
            Parent root = loader.load();
            //ottenimento del controller della finestra OrdinamentoCodice.fxml
            Esercizi.Monteventi.OrdinamentoCodiceController OrdinaCodiceController = loader.getController();
            OrdinaCodiceController.setUtente(utente);
            // Creazione della scena e visualizzazione della finestra
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

    //metodo per tornare alla finestra precedente --> front
    @FXML
    private void indietroClicked(ActionEvent event) {
        try {
            // Caricamento della finestra front.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/front.fxml"));
            Parent root = loader.load();
            //ottenimento del controller della finestra front.fxml
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
