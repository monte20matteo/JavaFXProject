package Esercizi.DiCapua;

// tutti gli import per il funzionamento del controller

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Esercizi.Front.FrontController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import Login.Utente;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TrovaErroreController implements Initializable {
    @FXML private Label nameUser;
    @FXML private ImageView mostraimage;
    @FXML private TextField answer;
    @FXML private AnchorPane root;
    @FXML private Label difficult;
    @FXML private GridPane spazioCodice;
    @FXML private Label titoloEs;
    @FXML private TextField input;
    @FXML private Label timerLabel; // Label per il timer

    private boolean isOnDashboard = false; // Variabile di stato per tracciare se sei sulla dashboard
    private Timer timer; // Timer per gestire il tempo
    private int tempoRimanente = 30; // Tempo rimanente in secondi
    private String difficolta; 
    private List<String> segmentiCodice;
    private String RigaErrore;
    private Utente loggedUtente;
    private int IndiceEsercizioCorrente = 0;

    // setta l'utente corrente e recupera la difficoltà a cui l'utente era arrivato
    public void setUtente(Utente utente) {
        this.loggedUtente = utente;
        nameUser.setText(utente.toString());
        difficolta = getDiffCOrrenteOrdinaCodice(); // Imposta la difficoltà corrente
        
        // Calcola l'indice iniziale in base al punteggio
        double[] score = loggedUtente.getScore();
        int index = -1;
        switch (difficolta) {
            case "Facile":
                index = 6;
                break;
            case "Medio":
                index = 7;
                break;
            case "Difficile":
                index = 8;
                break;
        }
        if (index != -1) {
            // Ogni esercizio completato incrementa il punteggio di 0.25
            IndiceEsercizioCorrente = (int) (score[index] / 0.25);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------------------

    @Override public void initialize(URL location, ResourceBundle resources) {
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        loadDomanda();  // Chiamare loadDomanda quando la finestra è mostrata
                    }
                });
            }
        });
    }

    // -----------------------------------------------------------------------------------------------------------------------------------
    // metodo principale per caricare la giusta domanda in base alla difficoltà

    private void loadDomanda() {
        // Reset dello stato quando carichi una nuova domanda
        isOnDashboard = false;
    
        resetTimer(); // Resetta il timer corrente
        startTimer();
        // switch che serve a mostrare a schermo la giusta difficoltà e con il giusto colore 

        switch (difficolta) { 
            case "Facile":
                this.difficult.setText("Facile");
                this.difficult.setStyle("-fx-text-fill: green;");
                break;

            case "Medio":
                this.difficult.setText("Medio");
                this.difficult.setStyle("-fx-text-fill: yellow;");
                break;
        
            default:
                this.difficult.setText("Difficile");
                this.difficult.setStyle("-fx-text-fill: red;");
                break;
        }

        // recupera in base a difficolta il giusto file
        String exerciseFilePath = "src/Data/TrovaErrore/" + difficolta + "/es.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(exerciseFilePath))) {
            // Salta le righe fino all'indice corrente
            for (int i = 0; i <= IndiceEsercizioCorrente; i++) {
                // Leggi il titolo dell'esercizio (prima riga)
                String title = reader.readLine();
                if (title == null) {
                    return; // Se non ci sono più righe da leggere, esci
                }
                titoloEs.setText(title);

                // Leggi i segmenti di codice fino a trovare la riga vuota
                segmentiCodice = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    segmentiCodice.add(line);
                }

                // Recupera dal file di testo la riga dell'errore
                RigaErrore = reader.readLine();
            }

            // Mostra i segmenti di codice a video
            mostraSegmentiCodice(segmentiCodice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // metodo per mostrare a schermo i segmenti

    private void mostraSegmentiCodice(List<String> segmentiCodice) {
        // Pulisce la griglia
        spazioCodice.getChildren().clear();

        // Aggiunge il titolo dell'esercizio in alto
        Label titoloLabel = new Label(titoloEs.getText());
        titoloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        spazioCodice.add(titoloLabel, 0, 0, 2, 1);

        // Mostra ogni segmento di codice come una nuova riga nella griglia
        int rowIndex = 1;
        for (String segmento : segmentiCodice) {
            Label codiceLabel = new Label(segmento);
            codiceLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 14px;");
            spazioCodice.add(codiceLabel, 0, rowIndex);
            rowIndex++;
        }

        // Mostra un messaggio con la posizione dell'errore (opzionale)
        if (RigaErrore != null) {
            Label erroreLabel = new Label("Errore da individuare: " + RigaErrore);
            erroreLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            spazioCodice.add(erroreLabel, 0, rowIndex, 2, 1);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // metodo richiamato quando l'utente clicca sul tasto "Avanti" presente nel file .fxml
    
    @FXML private void avanti() {
        // Controlla se la sequenza di lettere dell'utente è corretta
        String riga = input.getText();
        if (riga.equals(RigaErrore)) {
            // Incrementa l'indice
            IndiceEsercizioCorrente++;
            
            // Aggiorna il punteggio nell'array relativo all'utente
            aggiornaPunteggio(difficolta);
    
            Alert alertGiusto = new Alert(Alert.AlertType.INFORMATION);
            alertGiusto.setTitle("Corretto!");
            alertGiusto.setHeaderText("Hai completato l'esercizio.");
            alertGiusto.setContentText("La risposta è corretta!");
            alertGiusto.showAndWait();
    
            // Pulisci il campo di testo subito dopo aver mostrato l'alert
            input.clear();
    
            // Se ha completato tutti gli esercizi della modalità difficile
            if (IndiceEsercizioCorrente == 4 && difficolta.equals("Difficile")) {
                // Aggiorna il punteggio finale anche se l'utente ha completato l'ultimo esercizio
                aggiornaPunteggio(difficolta); // Incrementa il punteggio per l'ultimo esercizio
                save(); // Salva i progressi dell'utente
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completato!");
                alert.setHeaderText("Hai completato tutti gli esercizi nella modalità difficile.");
                alert.setContentText("Ottimo lavoro! Verrai reindirizzato alla dashboard.");

                // Crea un evento fittizio da passare al metodo tornaDashboard
                ActionEvent fakeEvent = new ActionEvent();

                // Aggiungi il comportamento al click del pulsante OK dell'alert
                alert.setOnHidden(e -> tornaDashboard(fakeEvent)); // Passa l'evento fittizio
    
                // Mostra l'alert e gestisci la risposta dell'utente
                alert.showAndWait();
                return;
            }
    
            // Se non ha completato tutti gli esercizi della modalità difficile
            if (IndiceEsercizioCorrente == 4) {
                // Aggiorna la difficoltà da cui dipende la scelta dell'esercizio solo dopo aver completato tutti e 4 gli esercizi
                if (difficolta.equals("Facile")) {
                    difficolta = "Medio";
                } else if (difficolta.equals("Medio")) {
                    difficolta = "Difficile";
                }
                IndiceEsercizioCorrente = 0;
            }
    
            // Salva e carica la domanda successiva
            save();
            loadDomanda();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ordine Errato");
            alert.setHeaderText("L'ordine inserito non è corretto.");
            alert.setContentText("Riprova!");
            alert.showAndWait();
        }
    }
    
    
    
    
    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // metodo per il salvataggio

    @FXML
    private void save() {
        // Prepara il file per la lettura
        File inputFile = new File("src/Data/users.csv");
        if (!inputFile.exists()) {
            // Controlla se il file di input esiste
            System.out.println("Errore: il file di input non esiste.");
            return;
        }
        // Prepara una lista di righe aggiornate
        List<String> lines = new ArrayList<>();
    
        // Lettura del file riga per riga
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Divide la riga in elementi utilizzando la virgola come delimitatore
                String[] elements = line.split(",");
                if (elements.length >= 11) {
                    // Controlla se la riga corrisponde all'utente loggato
                    if (elements[0].equals(loggedUtente.getUsername()) && elements[1].equals(loggedUtente.getPassword())) {
                        // Aggiorna la riga con le informazioni dell'utente loggato
                        elements = loggedUtente.onFile().split(",");
                    }
                    // Aggiunge la riga aggiornata o originale alla lista
                    lines.add(String.join(",", elements));
                } else {
                    System.out.println("Riga con formato errato: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore in save: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    
        // Prepara il file per la scrittura
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            // Scrive ogni riga della lista nel file
            for (String s : lines) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Errore in save: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    private void startTimer() {
        timer = new Timer();
        tempoRimanente = 30; // Imposta il tempo iniziale a 30 secondi
    
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    // Aggiorna il timerLabel
                    timerLabel.setText("Tempo: " + tempoRimanente + "s");
    
                    if (tempoRimanente <= 0) {
                        timer.cancel(); // Ferma il timer
                        save(); // Salva i progressi
                        
                        // Controlla se sei sulla dashboard
                        if (!isOnDashboard) {
                            // Se non sei sulla dashboard, torna alla dashboard
                            ActionEvent fakeEvent = new ActionEvent();
                            tornaDashboard(fakeEvent); // Passa l'evento fittizio
                        }
                    }
    
                    tempoRimanente--; // Decrementa il tempo rimanente
                });
            }
        }, 0, 1000); // Aggiorna ogni secondo
    }
    
    private void resetTimer() {
        if (timer != null) {
            timer.cancel(); // Ferma il timer corrente
        }
    }
    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // metodo richiamato quando l'utente clicca sul pulsante "torna alla dashboard"

    @FXML
    private void tornaDashboard(ActionEvent event) {
    try {
        // Imposta isOnDashboard a true quando sei sulla dashboard
        isOnDashboard = true;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));
        Parent front = loader.load();
        FrontController frontController = loader.getController();
        frontController.setUtente(this.loggedUtente);
        Scene frontScene = new Scene(front);
        
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(frontScene);
        stage.show();
    } catch (Exception e) {
        System.out.println("Verificato un errore nel caricamento della Dashboard: --> " + e.getMessage());
        e.printStackTrace();
    }
}


    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // Metodo per ottenere la difficoltà corrente dell'utente 

    private String getDiffCOrrenteOrdinaCodice() {
        double[] score = loggedUtente.getScore();
        if (score[6] >= 1.0) { 
            if (score[7] >= 1.0) { 
                return "Difficile"; // Ritorna "difficile" se entrambi i livelli precedenti sono completati
            }
            return "Medio"; // Ritorna "medio" se solo il livello "semplice" è completato
        }
        return "Facile"; // Ritorna "semplice" se il livello "semplice" non è ancora completato
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // Metodo per aggiornare la difficoltà 

    private void aggiornaPunteggio(String diff) {
        double[] score = loggedUtente.getScore();
        int index = -1;
        switch (diff) {
            case "Facile":
                index = 6;
                break;
            case "Medio":
                index = 7;
                break;
            case "Difficile":
                index = 8;
                break;
        }
    
        if (index != -1) {
            // Invece di limitare il punteggio a 0.75, incrementa sempre di 0.25
            if(score[index] < 1.0){
                score[index] += 0.25;
            }
        }
    }
    

}

