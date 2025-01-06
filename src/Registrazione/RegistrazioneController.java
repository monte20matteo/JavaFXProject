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

    @FXML
    private Label target; // Dichiarazione del campo target, collegato all'elemento Label nel file FXML

    @FXML
    private TextField tfUsername; 

    @FXML
    private TextField tfPassword; 

    @FXML
    private TextField tfcPassword;


    @FXML
    private boolean check(String usernameString){ //controlla se l'username è già presente nel file users.csv
        try {
            File file = new File("src/Data/users.csv");
            if (file.exists()) {
                @SuppressWarnings("resource") //solo per evitare il warning
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] fields = line.split(",");//divide la linea estrapolata in base alla virgola
                    if (fields[0].equals(usernameString)) { //controlla se l'username è già presente
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
            showAlert("Error", "Errore durante il caricamento della schermata di login", e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void registrazioneClicked(ActionEvent event) {

        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String confirmPassword = tfcPassword.getText();

        //if che controlla se i campi sono vuoti
        if (doValidation(username, password, confirmPassword)) {
            Utente utente = new Utente(username, password); //crea un nuovo utente
            try {
                // Effettua la registrazione su file CSV
                File file = new File("src/Data/users.csv");
                //if che controlla se il file esiste e se l'username è già presente
                if (file.exists() && check(username) == true) {
                    FileWriter fileWriter = new FileWriter(file, true);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.println(utente.onFile()); //scrive i dati dell'utente nel file in modalità append
                    printWriter.close();

                    // Carica la schermata front
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));

                    Parent front = loader.load();

                    FrontController frontController = loader.getController();

                    Scene froScene = new Scene(front);

                    frontController.setUtente(utente);

                    Stage stage = (Stage) tfUsername.getScene().getWindow();

                    stage.setScene(froScene);

                    stage.show();
                } else {
                    PrintWriter printWriter = new PrintWriter(file); //crea un nuovo file
                    printWriter.println(utente.onFile()); //scrive i dati dell'utente nel file creato
                    printWriter.close();

                    // Carica la schermata front
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));

                    Parent front = loader.load();

                    FrontController frontController = loader.getController();

                    Scene froScene = new Scene(front);

                    frontController.setUtente(utente);

                    Stage stage = (Stage) tfUsername.getScene().getWindow();

                    stage.setScene(froScene);

                    stage.show();
                }

            } catch (Exception e) {
                showAlert("Errore", "Si è verificato un errore durante la registrazione", e.getMessage());
            }
        }
    }

    // Metodo che controlla se i campi sono vuoti
    private boolean doValidation(String username, String password, String confirmPassword) {
        if (username.isEmpty()) {
            tfUsername.setPromptText("Il campo username è obbligatorio");
            return false;
        }
        if (password.isEmpty()) {
            tfPassword.setPromptText("Il campo password è obbligatorio");
            return false;
        }
        if (confirmPassword.isEmpty()) {
            tfcPassword.setPromptText("Il campo conferma password è obbligatorio");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showAlert("Errore", "Le password non combaciano", "Assicurati di aver inserito la stessa password in entrambi i campi");
            return false;
        }
        return true;
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


    // Metodo che mostra un alert
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
