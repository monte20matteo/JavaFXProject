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

    @FXML
    private TextField tfUsername; 

    @FXML
    private TextField tfPassword; 

    @FXML
    private Label target; // Dichiarazione del campo target, collegato all'elemento Label nel file FXML

    private Utente utente; // Oggetto Utente per memorizzare le informazioni dell'utente loggato

    @FXML
    void accediClicked(ActionEvent event) {
        if (tfUsername == null || tfPassword == null) { // Verifica che i campi siano stati inizializzati correttamente
            showAlert("Errore nell'inizializzazione degli elementi UI.");
            return;
        }
    
        String usernameText = tfUsername.getText(); 
        String passwordText = tfPassword.getText();
    
        if (usernameText.isEmpty() || passwordText.isEmpty()) { // Controlla se l'username o la password sono vuoti
            showAlert("Username e password sono obbligatori.");
            return;
        }
    
        try {
            Scanner scan = new Scanner(new File("src/Data/users.csv")); // Apre il file users.csv per la lettura
            while (scan.hasNextLine()) { 
                String line = scan.nextLine(); 
                String[] data = line.split(","); // Divide la riga usando la virgola come delimitatore e rimuove gli spazi iniziali e finali da ogni elemento
                for (int i = 0; i < data.length; i++) 
                    data[i] = data[i].trim();
                
                if (data.length >= 3 && data[0].equals(usernameText) && data[1].equals(passwordText)) {
                    this.utente = new Utente(data[0], data[1]); 
                    this.utente.loadFile(data[0], data[1]);//caricare da file 
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml")); 
    
                    Parent front = loader.load(); 
    
                    FrontController frontController = loader.getController(); 
                    frontController.setUtente(utente); 
    
                    Scene froScene = new Scene(front); 
    
                    Stage stage = (Stage) tfUsername.getScene().getWindow(); 
    
                    stage.setScene(froScene);
    
                    stage.show(); 
                    scan.close();
                    return; 
                }
            }
            scan.close(); 
            showAlert("Username o password non corretti, riprova."); 
        } catch (FileNotFoundException e) {
            showAlert("File 'users.csv' non trovato."); 
        } catch (IOException e) {
            showAlert("Errore durante la lettura di 'users.csv': " + e.getMessage()); 
        } catch (Exception e) {
            showAlert("Errore: " + e.getMessage()); 
        }
    }

    @FXML
    void registrazioneClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registrazione/Registrazione.fxml"));
            Parent register = loader.load(); 
            Scene regScene = new Scene(register); 
            Stage regWindow = (Stage) tfUsername.getScene().getWindow(); 
            
            // Cambia scena
            regWindow.setScene(regScene);

            // Forza la finestra ad avere dimensioni massimizzate
            regWindow.setMaximized(true); // Abilita la modalità massimizzata
            regWindow.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth()); // Imposta la larghezza massima
            regWindow.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight()); // Imposta l'altezza massima

            regWindow.show(); 
        } catch (Exception e) {
            showAlert("Errore durante il caricamento della schermata di registrazione: " + e.getMessage());
        }
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
