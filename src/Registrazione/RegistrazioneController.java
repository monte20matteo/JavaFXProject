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
import Esercizi.Front.FrontController;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import Login.Utente;

public class RegistrazioneController {

    // Dichiarazione dei campi collegati agli elementi nel file FXML
    @FXML
    private Label target; 
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfcPassword;

    // controlla se l'username è già presente nel file users.csv
    @FXML
    private boolean check(String usernameString) { 
        try {
            // file users.csv e controllo se esiste
            File file = new File("src/Data/users.csv");
            if (file.exists()) {
                //soppressione del warning --> lo evito
                @SuppressWarnings("resource") 
                // scanner per leggere il file users.csv riga per riga
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    //memorizza la riga corrente
                    String line = scanner.nextLine();
                    // divide la riga in base alla virgola come separatore
                    String[] fields = line.split(",");
                     // controlla se l'username è già presente --> non è possibile registrarsi con lo stesso username
                    if (fields[0].equals(usernameString)) {
                        showAlert("Errore", "Username già esistente", "Cambiare l'username.");
                        tfUsername.clear();
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            showAlert("Errore", "Errore durante la registrazione", e.getMessage());
        }
        return true;
    }

    // Metodo che gestisce l'evento del click sul pulsante "Accedi"
    @FXML
    void accediClicked(ActionEvent event) {
        try {
            // Carica la schermata di login fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Parent login = loader.load();
            // Imposta la scena
            Scene loginScene = new Scene(login);
            Stage loginWindow = (Stage) tfUsername.getScene().getWindow();
            // Cambia scena
            loginWindow.setScene(loginScene);
            // Forza la finestra ad avere dimensioni massimizzate
            loginWindow.setMaximized(true); // Abilita la modalità massimizzata
            loginWindow.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth()); 
            loginWindow.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight()); 
            loginWindow.show();
        } catch (Exception e) {
            showAlert("Error", "Errore durante il caricamento della schermata di login", e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo che gestisce l'evento del click sul pulsante "Registrati"
    @FXML
    void registrazioneClicked(ActionEvent event) {
        // Ottiene i valori inseriti dall'utente --> username, password e conferma password
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String confirmPassword = tfcPassword.getText();

        // Controlla se i campi sono validi
        if (doValidation(username, password, confirmPassword)) {
            try {
                File file = new File("src/Data/users.csv");

                // Controlla se il file esiste e se l'username è già presente
                if (file.exists() && !check(username)) {
                    showAlert("Errore", "Registrazione fallita", "Username già esistente.");
                    return;
                }

                // Crea un nuovo oggetto Utente caratterizzato da username e password
                Utente utente = new Utente(username, password);

                // Scrive i dati dell'utente nel file
                try (FileWriter fileWriter = new FileWriter(file, true);
                        PrintWriter printWriter = new PrintWriter(fileWriter)) {
                    //rimozione di eventuali spazi vuoti
                    printWriter.println(utente.onFile().trim()); 
                }

                // Carica la schermata Front.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));
                Parent front = loader.load();
                // ottenimento del controller e setting dell'utente
                FrontController frontController = loader.getController();
                frontController.setUtente(utente);
                // imposto la nuova scena e mostro la finestra
                Scene froScene = new Scene(front);
                Stage stage = (Stage) tfUsername.getScene().getWindow();
                stage.setScene(froScene);
                stage.show();
            } catch (Exception e) {
                showAlert("Errore", "Si è verificato un errore durante la registrazione", e.getMessage());
            }
        }
    }

    // Metodo che controlla i campi se vuoti o meno --> validazione campi di input
    private boolean doValidation(String username, String password, String confirmPassword) {
        //caso username vuoto
        if (username.isEmpty()) {
            tfUsername.setPromptText("Il campo username è obbligatorio");
            return false;
        }
        //caso password vuota
        if (password.isEmpty()) {
            tfPassword.setPromptText("Il campo password è obbligatorio");
            return false;
        }
        //caso conferma password vuota
        if (confirmPassword.isEmpty()) {
            tfcPassword.setPromptText("Il campo conferma password è obbligatorio");
            return false;
        }
        //caso password e conferma password non combaciano
        if (!password.equals(confirmPassword)) {
            showAlert("Errore", "Le password non combaciano",
                    "Assicurati di aver inserito la stessa password in entrambi i campi");
            return false;
        }
        return true;
    }

    // Metodo che gestisce l'evento del click sul pulsante "Home"
    @FXML
    void homeClicked(ActionEvent event) {
        try {
            // Carica la schermata home fxml
            Parent home = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            // Imposta la scena
            Scene homeScene = new Scene(home);
            Stage primaryStage = (Stage) tfUsername.getScene().getWindow();
            primaryStage.setScene(homeScene);
            // Forza la finestra ad avere dimensioni massimizzate
            primaryStage.setMaximized(true); // Abilita la modalità massimizzata
            primaryStage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth()); 
            primaryStage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight()); 
            //mostro la finestra
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Errore durante il caricamento della schermata home: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo che mostra un alert
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
