package Esercizi.Front;

import Login.Utente;
import Profilo.ProfiloController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

//import Esercizi.Catta.RulesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrontController implements Initializable{

    @FXML private Label nameUser;
    @FXML private Label diffCS;
    @FXML private Label diffOC;
    @FXML private Label diffTE;
    @FXML private ProgressBar CosaStampaBar;
    @FXML private ProgressBar OrdinaCodiceBar;
    @FXML private ProgressBar TrovaErroreBar;
    @FXML private Pane root;
    @FXML private Button buttonTest;
    private Utente utente;

   
// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Aggiungi un listener alla scena per chiamare showprogress e mostraTestFinale quando la finestra viene mostrata
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        showProgress();  // Chiamare showprogress quando la finestra è mostrata
                    }
                });
            }
        });
    }

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void setUtente(Utente utente){
        this.utente = utente;
        nameUser.setText(utente.toString());
    }

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //metodo per andare alla schermata del profilo
    @FXML private void goProfilo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profilo/Profilo.fxml"));
            Parent profilo = loader.load();
            ProfiloController profiloController = loader.getController();
            profiloController.setUtente(utente);
            Scene profiloScene = new Scene(profilo);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(profiloScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //metodo per andare alla schermata di cosaStampa rules
    @FXML private void CosaStampaClicked(MouseEvent event){
        try {
            if(utente.getScore()[2] >= 1.0){
                Alert alertFine = new Alert(Alert.AlertType.INFORMATION);
                alertFine.setTitle("Esercizi completati!");
                alertFine.setHeaderText("Tipologia di esercizi già completata!");
                alertFine.setContentText("Hai già completato questa tipologia di esercizi!");
                alertFine.showAndWait();
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Magliarella/RegoleCosaStampa.fxml"));
                Parent cosaStampa = loader.load();
                Esercizi.Magliarella.RegoleCosaStampaController rulesController = loader.getController();
                rulesController.setUtente(this.utente);
                Scene cosaStampaScene = new Scene(cosaStampa);
                Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow(); //prova
                stage.setScene(cosaStampaScene);
                stage.show();
            }
        } catch (Exception e) {
            System.out.println("Verificato un errore nel caricamento della finestra di cosaStampa: --> " + e.getMessage());
            e.printStackTrace();
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
//metodo per andare alla schermata di OrdinaCodice 
@FXML private void OrdinamentoCodiceClicked(MouseEvent event) {
    try {
        if(utente.getScore()[5] >= 1.0){
            Alert alertFine = new Alert(Alert.AlertType.INFORMATION);
            alertFine.setTitle("Esercizi completati!");
            alertFine.setHeaderText("Tipologia di esercizi già completata!");
            alertFine.setContentText("Hai già completato questa tipologia di esercizi!");
            alertFine.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Monteventi/RegoleOrdinamentoCodice.fxml"));
            Parent root = loader.load();
            Esercizi.Monteventi.RegoleOrdinamentoCodiceController rulesController = loader.getController();
            rulesController.setUtente(utente);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }catch (Exception e) {
        e.printStackTrace();
    }  
}

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
//metodo per andare alla schermata di ConfrontaCodice 
@FXML private void TrovaErroreClicked(MouseEvent event) {
    try {
        if(utente.getScore()[8] >= 1.0){
            Alert alertFine = new Alert(Alert.AlertType.INFORMATION);
            alertFine.setTitle("Esercizi completati!");
            alertFine.setHeaderText("Tipologia di esercizi già completata!");
            alertFine.setContentText("Hai già completato questa tipologia di esercizi!");
            alertFine.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/DiCapua/RegoleTrovaErrore.fxml"));
            Parent root = loader.load();
            Esercizi.DiCapua.RegoleTrovaErroreController rulesController = loader.getController();
            rulesController.setUtente(utente);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //metodo per mostrare l'avanzamento dell'utente a seconda dell'esercizio
    @FXML private void showProgress(){
        //progress CosaStampa
        for(int i = 0; i < 3; i++){
            if(utente.getScore()[0] >= 0 && utente.getScore()[1] == 0 && utente.getScore()[2] == 0){
                CosaStampaBar.setProgress(utente.getScore()[0]);
                diffCS.setText("Facile");
                diffCS.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
            else if(utente.getScore()[0] == 1 && utente.getScore()[1] >= 0 && utente.getScore()[2] == 0){
                CosaStampaBar.setProgress(utente.getScore()[1]);
                diffCS.setText("Medio");
                diffCS.setStyle("-fx-text-fill: orange; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
            else{
                CosaStampaBar.setProgress(utente.getScore()[2]);
                diffCS.setText("Difficile");
                diffCS.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
        }

        //progress OrdinaCodice
        for(int i = 3; i < 6; i++){
            if(utente.getScore()[3] >= 0 && utente.getScore()[4] == 0 && utente.getScore()[5] == 0){
                OrdinaCodiceBar.setProgress(utente.getScore()[3]);
                diffOC.setText("Facile");
                diffOC.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
            else if(utente.getScore()[3] == 1 && utente.getScore()[4] >= 0 && utente.getScore()[5] == 0){
                OrdinaCodiceBar.setProgress(utente.getScore()[4]);
                diffOC.setText("Medio");
                diffOC.setStyle("-fx-text-fill: orange; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
            else{
                OrdinaCodiceBar.setProgress(utente.getScore()[5]);
                diffOC.setText("Difficile");
                diffOC.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
        }

        //progress trova l'errore
        for(int i = 6; i < 9; i++){
            if(utente.getScore()[6] >= 0 && utente.getScore()[7] == 0 && utente.getScore()[8] == 0){
                TrovaErroreBar.setProgress(utente.getScore()[6]);
                diffTE.setText("Facile");
                diffTE.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
            else if(utente.getScore()[6] == 1 && utente.getScore()[7] >= 0 && utente.getScore()[8] == 0){
                TrovaErroreBar.setProgress(utente.getScore()[7]);
                diffTE.setText("Medio");
                diffTE.setStyle("-fx-text-fill: orange; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
            else{
                TrovaErroreBar.setProgress(utente.getScore()[8]);
                diffTE.setText("Difficile");
                diffTE.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 14px;");
            }
        }

    }

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------

//metodo per salvare i dati dell'utente
@FXML private void salva(ActionEvent event){
                try {
            File inputFile = new File("src/Data/users.csv");
            if (!inputFile.exists()) {
                System.out.println("Errore: il file di input non esiste.");
                return;
            }

            Scanner scan = new Scanner(inputFile);// Apro il file users.csv per la lettura
            Set<String[]> lines = new HashSet<>();// Creo un set per contenere le righe del file
            while (scan.hasNextLine()) {
                String line = scan.nextLine();// Leggo una riga
                String[] elements = line.split(",");// Divido la riga in base alla virgola
                if (elements.length >= 10) { // Verifica che ci siano almeno 10 elementi
                    lines.add(elements); // Aggiungo la riga al set
                } else {
                    System.out.println("Riga con formato errato: " + line);
                }
            }
            scan.close();

        // Ora lavoro sul set
            File outputFile = new File("src/Data/users.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (String[] s : lines) {
                if (s[0].equals(utente.getUsername()) && s[1].equals(utente.getPassword())) { // Eseguo il check sull'utente
                    s = utente.onFile().split(","); // Aggiorno la riga
                }
            // Controllo che l'array s abbia almeno 10 elementi prima di accedere agli indici
                if (s.length >= 10) {
                    writer.write(s[0] + "," + s[1] + "," + s[2] + "," + s[3] + "," + s[4] + "," + s[5] + "," + s[6] + "," + s[7] + "," + s[8]+ "," + s[9]+ "," + s[10]);
                    writer.newLine();
                } else {
                    System.out.println("Riga con formato errato dopo aggiornamento: " + String.join(",", s));
                }
             }
            writer.close();

        } catch (Exception e) {
            System.out.println("Errore in save: " + e.getMessage());
            e.printStackTrace();
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------

}


