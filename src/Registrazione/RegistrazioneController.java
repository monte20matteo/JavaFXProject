package Registrazione;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrazioneController {

    @FXML
    private Label target; // Dichiarazione del campo target, collegato all'elemento Label nel file FXML

    @FXML
    private TextField tfUsername; 

    @FXML
    private TextField tfPassword; 

    @FXML
void accediClicked(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
        Parent login = loader.load();
        Scene loginScene = new Scene(login);
        Stage loginWindow = (Stage) tfUsername.getScene().getWindow();

        // Cambia scena
        loginWindow.setScene(loginScene);

        // Forza la finestra ad avere dimensioni massimizzate
        loginWindow.setMaximized(true); // Abilita la modalità massimizzata
        loginWindow.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth()); // Imposta la larghezza massima
        loginWindow.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight()); // Imposta l'altezza massima

        loginWindow.show();
    } catch (Exception e) {
        showAlert("Errore durante il caricamento della schermata di login: " + e.getMessage());
        e.printStackTrace();
    }
}


    @FXML
    void reg(ActionEvent event) {

    }

    @FXML
void homeClicked(ActionEvent event) {
    try {
        Parent home = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        Scene homeScene = new Scene(home);
        Stage primaryStage = (Stage) tfUsername.getScene().getWindow();

        // Cambia scena
        primaryStage.setScene(homeScene);

        // Forza la finestra ad avere dimensioni massimizzate
        primaryStage.setMaximized(true); // Abilita la modalità massimizzata
        primaryStage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth()); // Imposta la larghezza massima
        primaryStage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight()); // Imposta l'altezza massima

        primaryStage.show();
    } catch (Exception e) {
        System.out.println("Errore durante il caricamento della schermata home: " + e.getMessage());
        e.printStackTrace();
    }
}


    // Metodo per mostrare un alert con un messaggio specificato
    @FXML private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); 
        alert.setTitle("Errore di Login"); 
        alert.setContentText(message); 
        tfUsername.clear(); 
        tfPassword.clear(); 
        alert.showAndWait(); 
    }

}
