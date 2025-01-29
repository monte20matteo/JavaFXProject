package Esercizi.Magliarella;

// tutti gli import per il funzionamento del controller

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Esercizi.Front.FrontController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import Login.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CosaStampaController implements Initializable {

    // Dichiarazione di tutti gli elementi presenti nel file .fxml tramite il loro fx:id
    @FXML
    private Label nameUser;
    @FXML
    private ImageView mostraimage;
    @FXML
    private TextField answer;
    @FXML
    private StackPane root;
    @FXML
    private Label difficult;
    @FXML
    private GridPane spazioCodice;
    @FXML
    private Label titoloEs;
    @FXML
    private TextField input;

    // Dichiarazione di tutte le variabili necessarie per il funzionamento del controller
    private String difficolta;
    private List<String> segmentiCodice;
    private String Stampa;
    private Utente loggedUtente;
    private int IndiceEsercizioCorrente = 0;

    // setta l'utente corrente e recupera la difficoltà a cui l'utente era arrivato
    public void setUtente(Utente utente) {
        this.loggedUtente = utente;
        nameUser.setText(utente.toString());
        difficolta = getDiffCOrrenteCosaStampa(); // Imposta la difficoltà corrente

        // Calcola l'indice iniziale in base al punteggio
        double[] score = loggedUtente.getScore();
        int index = -1;
        switch (difficolta) {
            case "Semplice":
                index = 0;
                break;
            case "Medio":
                index = 1;
                break;
            case "Difficile":
                index = 2;
                break;
        }
        if (index != -1) {
            // Ogni esercizio completato incrementa il punteggio di 0.25
            IndiceEsercizioCorrente = (int) (score[index] / 0.25);
        }
    }

    // Metodo di inizializzazione del controller, viene eseguito automaticamente quando il FXML viene caricato
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        loadDomanda(); // Chiama loadDomanda quando la finestra è mostrata
                    }
                });
            }
        });
    }

    // metodo principale per caricare la domanda in base alla difficoltà
    private void loadDomanda() {
        // switch che serve a mostrare a schermo la giusta difficoltà e con il giusto colore
        switch (difficolta) {
            case "Semplice":
                this.difficult.setText("Semplice");
                this.difficult.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 18px;");
                break;
            case "Medio":
                this.difficult.setText("Medio");
                this.difficult.setStyle("-fx-text-fill: yellow; -fx-font-weight: bold; -fx-font-size: 18px;");
                break;
            default:
                this.difficult.setText("Difficile");
                this.difficult.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 18px;");
                break;
        }

        // recupera in base a difficolta il giusto file tramite il path corretto
        String exerciseFilePath = "src/Data/Code_CosaStampa/" + difficolta + "/stampa.txt";

        // Legge il file di testo 
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

                // Recupera dal file di testo la riga della risposta corretta
                Stampa = reader.readLine();
            }

            // Mostra i segmenti di codice a video
            mostraSegmentiCodice(segmentiCodice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metodo per mostrare a schermo i segmenti di codice
    private void mostraSegmentiCodice(List<String> segmentiCodice) {
        // Pulisce la griglia
        spazioCodice.getChildren().clear();
        // Aggiunge il titolo dell'esercizio in alto con il suo style personalzzato
        Label titoloLabel = new Label(titoloEs.getText());
        titoloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        // Mostra ogni segmento di codice come una nuova riga (label) nella griglia
        int rowIndex = 1;
        for (String segmento : segmentiCodice) {
            Label codiceLabel = new Label(segmento);
            codiceLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 14px;");
            spazioCodice.add(codiceLabel, 0, rowIndex);
            rowIndex++;
        }
    }

    // metodo richiamato quando l'utente clicca sul tasto "Avanti" per controllare la risposta
    @FXML
    private void avanti() {
        // Controlla se la sequenza di lettere dell'utente è corretta
        String riga = input.getText();
        //risposta corretta
        if (riga.equals(Stampa)) {
            // Incrementa l'indice --> esercizio successivo
            IndiceEsercizioCorrente++;

            // Aggiorna il punteggio nell'array relativo all'utente
            aggiornaPunteggio(difficolta);

            // Mostra un alert per comunicare all'utente che ha completato l'esercizio
            Alert alertGiusto = new Alert(Alert.AlertType.INFORMATION);
            alertGiusto.setTitle("Corretto!");
            alertGiusto.setHeaderText("Hai completato l'esercizio.");
            alertGiusto.setContentText("La risposta è corretta!");
            alertGiusto.showAndWait();

            // Pulisci il campo di testo subito dopo aver mostrato l'alert
            input.clear();

            // Se ha completato tutti gli esercizi della modalità difficile
            if (IndiceEsercizioCorrente == 4 && difficolta.equals("Difficile")) {
                // Aggiorna il punteggio finale 
                aggiornaPunteggio(difficolta); // Incrementa il punteggio per l'ultimo esercizio
                save(); // Salva i progressi dell'utente

                // Mostra un alert per comunicare all'utente che ha completato la modalità difficile
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completato!");
                alert.setHeaderText("Hai completato tutti gli esercizi nella modalità difficile.");
                alert.setContentText("Ottimo lavoro! Verrai reindirizzato alla dashboard.");

                // Crea un evento fittizio da passare al metodo tornaDashboard
                ActionEvent fakeEvent = new ActionEvent();
                // Aggiungi il comportamento al click del pulsante OK dell'alert
                alert.setOnHidden(e -> tornaDashboard(fakeEvent)); // Passa l'evento fittizio
                // Mostra l'alert 
                alert.showAndWait();
                return;
            }

            // Se non ha completato tutti gli esercizi della modalità difficile
            if (IndiceEsercizioCorrente == 4) {
                // Aggiorna la difficoltà da cui dipende la scelta dell'esercizio 
                if (difficolta.equals("Semplice")) {
                    difficolta = "Medio";
                } else if (difficolta.equals("Medio")) {
                    difficolta = "Difficile";
                }
                //azzero l'indice --> primo esercizio della nuova difficoltà
                IndiceEsercizioCorrente = 0;
            }

            // Salva e carica la domanda successiva
            save();
            loadDomanda();
        } else {
            // Se la risposta è sbagliata, mostra un alert di errore
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stampa errata");
            alert.setHeaderText("La stampa inserita non è corretta.");
            alert.setContentText("Riprova!");
            alert.showAndWait();
            input.clear();
        }
    }

    // metodo per il salvataggio dei progressi dell'utente
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

        // Lettura del file 
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Divide la riga in elementi utilizzando la virgola come delimitatore
                String[] elements = line.split(",");
                if (elements.length >= 11) {
                    // Controlla se la riga corrisponde all'utente loggato --> username e password
                    if (elements[0].equals(loggedUtente.getUsername())
                            && elements[1].equals(loggedUtente.getPassword())) {
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

    // metodo richiamato quando l'utente clicca sul pulsante "torna alla dashboard"
    @FXML
    private void tornaDashboard(ActionEvent event) {
        try {
            // Carica la dashboard fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));
            Parent front = loader.load();
            // ottiene il controller della dashboard e imposta l'utente loggato
            FrontController frontController = loader.getController();
            frontController.setUtente(this.loggedUtente);
            // Crea la scena e la mostra
            Scene frontScene = new Scene(front);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(frontScene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Verificato un errore nel caricamento della Dashboard: --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo per ottenere la difficoltà corrente dell'utente
    private String getDiffCOrrenteCosaStampa() {
        //punteggio dell'utente loggato
        double[] score = loggedUtente.getScore();
        //punteggio facile e medio
        if (score[0] >= 1.0) {
            if (score[1] >= 1.0) {
                return "Difficile"; // Ritorna "difficile" se entrambi i livelli precedenti sono completati
            }
            return "Medio"; // Ritorna "medio" se solo il livello "semplice" è completato
        }
        return "Semplice"; // Ritorna "semplice" se il livello "semplice" non è ancora completato
    }

    // Metodo per aggiornare la difficoltà e il punteggio dell'utente
    private void aggiornaPunteggio(String diff) {
        double[] score = loggedUtente.getScore();
        int index = -1;
        switch (diff) {
            case "Semplice":
                index = 0;
                break;
            case "Medio":
                index = 1;
                break;
            case "Difficile":
                index = 2;
                break;
        }

        if (index != -1) {
            //incrementa sempre di 0.25
            if (score[index] < 1.0) {
                score[index] += 0.25;
            }
        }
    }

}
