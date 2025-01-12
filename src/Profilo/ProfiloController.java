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

    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;
    @FXML private Label maskedPasswordLabel;
    @FXML private Button togglePasswordButton;

    private Utente utente;
    private boolean PasswordVisible = false;

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void setUtente(Utente utente) {
        this.utente = utente;
        usernameLabel.setText("Username: " + utente.getUsername());
        // Imposta la password inizialmente mascherata
        maskedPasswordLabel.setText("********");
        passwordLabel.setText("Password: ");
    }

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @FXML private void VisualizzaPassword() {
        if (PasswordVisible) {
            maskedPasswordLabel.setText("********");
            togglePasswordButton.setText("VEDI");
        } else {
            maskedPasswordLabel.setText(utente.getPassword());
            togglePasswordButton.setText("NASCONDI");
        }
        PasswordVisible = !PasswordVisible;
    }

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------

     @FXML private void logoutClicked(ActionEvent event) throws IOException {
        // Carica la schermata di login.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameLabel.getScene().getWindow(); // Ottiene il riferimento alla finestra corrente
        stage.setScene(scene);
        stage.show();
    }
// -------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @FXML private void tornaDashboardClicked (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));
        Parent root = loader.load();
        
        // Passa l'utente al controller della dashboard
        FrontController frontController = loader.getController();
        frontController.setUtente(utente);
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
