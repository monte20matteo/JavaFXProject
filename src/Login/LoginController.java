package Login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import Esercizi.Front.FrontController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class LoginController {

    // Dichiarazione delle variabili collegaate agli elementi di login.fxml tramite il loro fx:id
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Label target; 

    // Oggetto Utente per memorizzare le informazioni dell'utente loggato
    private Utente utente; 

    // Metodo per gestire l'evento di click sul pulsante "Accedi"
    @FXML
    void accediClicked(ActionEvent event) {
        //uno o entrambi i campi nulli
        if (tfUsername == null || tfPassword == null) { 
            showAlert("Errore nell'inizializzazione degli elementi UI.");
            return;
        }

        //username e password inseriti nel textfield e passwordfield
        String usernameText = tfUsername.getText();
        String passwordText = tfPassword.getText();

        // Controlla se l'username o la password sono vuoti
        if (usernameText.isEmpty() || passwordText.isEmpty()) { 
            showAlert("Username e password sono obbligatori.");
            return;
        }

        try {
            // File users.csv per la lettura
            Scanner scan = new Scanner(new File("src/Data/users.csv")); 
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                // Divide la riga usando la virgola come delimitatore e rimuove gli spazi iniziali e finali da ogni elemento
                String[] data = line.split(","); 
                for (int i = 0; i < data.length; i++)
                    data[i] = data[i].trim();

                // Se l'utente è stato trovato, carica i dati dell'utente e passa alla schermata principale --> Front.fxml
                if (data.length >= 3 && data[0].equals(usernameText) && data[1].equals(passwordText)) {
                    this.utente = new Utente(data[0], data[1]);
                    this.utente.loadFile(data[0], data[1]);
                    // Carica la schermata principale fxml di front
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));
                    Parent front = loader.load();
                    // ottenere il controller associato alla schermata principale fxml e impostare l'utente
                    FrontController frontController = loader.getController();
                    frontController.setUtente(utente);
                    // Creare una nuova scena e impostarla sulla finestra attuale
                    Scene froScene = new Scene(front);
                    Stage stage = (Stage) tfUsername.getScene().getWindow();
                    stage.setScene(froScene);
                    stage.show();
                    scan.close();
                    return;
                }
            }
            scan.close();
            //alert errori e gestione eccezioni
            showAlert("Username o password non corretti, riprova.");
        } catch (FileNotFoundException e) {
            showAlert("File 'users.csv' non trovato.");
        } catch (IOException e) {
            showAlert("Errore durante la lettura di 'users.csv': " + e.getMessage());
        } catch (Exception e) {
            showAlert("Errore: " + e.getMessage());
        }
    }

    // Metodo per gestire l'evento di click sul pulsante "Registrati"
    @FXML
    void registrazioneClicked(ActionEvent event) {
        try {
            // Carica la schermata di registrazione fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registrazione/Registrazione.fxml"));
            Parent register = loader.load();
            // Creare una nuova scena e impostarla sulla finestra attuale
            Scene regScene = new Scene(register);
            Stage regWindow = (Stage) tfUsername.getScene().getWindow();
            // Cambia scena
            regWindow.setScene(regScene);
            // Forza la finestra ad avere dimensioni massimizzate
            regWindow.setMaximized(true); // Abilita la modalità massimizzata
            regWindow.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth()); // Imposta la larghezza massima
            regWindow.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight()); // Imposta l'altezza massima
            //mostra la finestra
            regWindow.show();
        } catch (Exception e) {
            showAlert("Errore durante il caricamento della schermata di registrazione: " + e.getMessage());
        }
    }

    // Metodo per gestire l'evento di click sul pulsante "Home"
    @FXML
    void homeClicked(ActionEvent event) {
        try {
            // Carica la schermata home fxml
            Parent home = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            // Creare una nuova scena e impostarla sulla finestra attuale
            Scene homeScene = new Scene(home);
            Stage primaryStage = (Stage) tfUsername.getScene().getWindow();
            // Cambia scena
            primaryStage.setScene(homeScene);
            // Forza la finestra ad avere dimensioni massimizzate
            primaryStage.setMaximized(true); // Abilita la modalità massimizzata
            primaryStage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth()); 
            primaryStage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight()); 
            //mostra la finestra
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Errore durante il caricamento della schermata home: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo per mostrare un alert con un messaggio specificato
    @FXML
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di Login");
        alert.setContentText(message);
        tfUsername.clear();
        tfPassword.clear();
        alert.showAndWait();
    }

}
