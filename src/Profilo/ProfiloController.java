package Profilo;

import java.io.IOException;

import Esercizi.Front.FrontController;
import Login.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfiloController {

    // Dichiarazione delle variabili collegandole agli elementi grafici presenti
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label maskedPasswordLabel;
    @FXML
    private Button togglePasswordButton;

    private Utente utente;
    private boolean PasswordVisible = false;

    // setUtente() permette di impostare l'utente corrente
    public void setUtente(Utente utente) {
        this.utente = utente;
        //username dell'utente loggato
        usernameLabel.setText("Username: " + utente.getUsername());
        // Imposta la password inizialmente sottoforma di asterischi
        maskedPasswordLabel.setText("********");
        passwordLabel.setText("Password: ");
    }

    // VisualizzaPassword() permette di visualizzare la password in chiaro o come asterischi
    @FXML
    private void VisualizzaPassword() {
        if (PasswordVisible) {
            // Imposta il testo della password come asterischi
            maskedPasswordLabel.setText("********");
            maskedPasswordLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;"); 
            togglePasswordButton.setText("Vedi");
        } else {
            // Mostra la password in chiaro
            maskedPasswordLabel.setText(utente.getPassword());
            maskedPasswordLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;"); 
            togglePasswordButton.setText("Nascondi");
        }
        // Inverte il valore di PasswordVisible
        PasswordVisible = !PasswordVisible;
    }

    // permette di tornare alla schermata di login --> l'utente corrente viene sloggato
    @FXML
    private void logoutClicked(ActionEvent event) throws IOException {
        // Carica la schermata di login.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
        // Crea una nuova scena 
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameLabel.getScene().getWindow(); // Ottiene il riferimento alla finestra corrente
        stage.setScene(scene);
        stage.show();
    }

    // permette di tornare alla dashboard
    @FXML
    private void tornaDashboardClicked(ActionEvent event) throws IOException {
        // Carica la schermata della dashboard
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));
        Parent root = loader.load();
        // Passa l'utente al controller della dashboard
        FrontController frontController = loader.getController();
        frontController.setUtente(utente);
        // Crea una nuova scena e la mostra
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
