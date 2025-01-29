package Esercizi.Monteventi;

// tutti gli import per il funzionamento del controller

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OrdinamentoCodiceController implements Initializable {

    // Dichiarazione degli elementi presenti nel file .fxml tramite il loro fx:id
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

    // Dichiarazione delle variabili necessarie per il funzionamento del controller
    private String difficolta;
    private List<String> segmentiCodice;
    private String ordineCorretto;
    private Map<Character, String> lettereSegmentiMap; // Mappa per associare lettere a segmenti di codice
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
            case "semplice":
                index = 3;
                break;
            case "medio":
                index = 4;
                break;
            case "difficile":
                index = 5;
                break;
        }
        if (index != -1) {
            // Ogni esercizio completato incrementa il punteggio di 0.25
            IndiceEsercizioCorrente = (int) (score[index] / 0.25);
        }
    }

    // inizializzazione della finestra
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

    // metodo per caricare la domanda in base alla difficoltà
    private void loadDomanda() {
        // switch che serve a mostrare a schermo la giusta difficoltà e con il giusto colore
        switch (difficolta) {
            case "semplice":
                this.difficult.setText("Facile");
                this.difficult.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 18px;");
                break;
            case "medio":
                this.difficult.setText("Medio");
                this.difficult.setStyle("-fx-text-fill: yellow; -fx-font-weight: bold; -fx-font-size: 18px;");
                break;
            default:
                this.difficult.setText("Difficile");
                this.difficult.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 18px;");
                break;
        }

        // recupera in base a difficolta il giusto file
        String exerciseFilePath = "src/Data/OrdinaCodice/" + difficolta + "/esercizi.txt";

        //lettura
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
                // aggiunge all'ArrayList le stringhe contenenti le parti di codice da ordinare
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    segmentiCodice.add(line);
                }

                // Recupera dal file di testo la soluzione dell'esercizio
                ordineCorretto = reader.readLine();

                // Associa in maniera univoca le lettere ai segmenti di codice
                lettereSegmentiMap = new HashMap<>(); 
                // map che associa ad ogni lettera maiuscola una stringa
                for (int j = 0; j < ordineCorretto.length(); j++) {
                    lettereSegmentiMap.put(ordineCorretto.charAt(j), segmentiCodice.get(j));
                }
            }

            // Ordina i segmenti di codice in ordine alfabetico
            List<Map.Entry<Character, String>> sortedSegments = new ArrayList<>(lettereSegmentiMap.entrySet());
            //// lettereSegmentiMap.entrySet() restituisce un set di tutte le coppie chiave-valore

            // sortedSegments.sort() ordina la lista sortedSegments in base a un criterio specificato da Comparator
            // Map.Entry::getKey restituisce la chiave di una Map.Entry
            sortedSegments.sort(Comparator.comparing(Map.Entry::getKey));

            // richiama il metodo per mostrare a schermo i segmenti
            mostraSegmentiCodice(sortedSegments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metodo per mostrare a schermo i segmenti di codice
    private void mostraSegmentiCodice(List<Map.Entry<Character, String>> sortedSegments) {
        spazioCodice.getChildren().clear();
        // Aggiunge il titolo dell'esercizio alla griglia nelle colonne 0 e 1, riga 0,
        spazioCodice.add(titoloEs, 0, 0, 2, 1);

        int rowIndex = 1;
        for (Map.Entry<Character, String> entry : sortedSegments) {
            //letter --> lettera dalla coppia corrente
            char letter = entry.getKey(); 
            // segmento di codice dalla coppia corrente
            String segment = entry.getValue(); 

            // crea le label e le aggiunge
            Label letterLabel = new Label(String.valueOf(letter));
            Label codeLabel = new Label(segment);
            spazioCodice.add(letterLabel, 0, rowIndex);
            spazioCodice.add(codeLabel, 1, rowIndex);
            //incremento della riga
            rowIndex++;
        }
    }

    // metodo richiamato quando l'utente clicca sul tasto "Avanti" per controllare la risposta
    @FXML
    private void avanti() {
        // Controlla se la sequenza di lettere dell'utente è corretta --> maiuscole
        String userOrder = input.getText().trim().toUpperCase();
        //se è l'ordine corretto
        if (userOrder.equals(ordineCorretto)) {
            // Incrementa l'indice --> passa all'esercizio successivo
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
            if (IndiceEsercizioCorrente == 4 && difficolta.equals("difficile")) {
                // Aggiorna il punteggio finale
                aggiornaPunteggio(difficolta);
                save(); // Salva i progressi dell'utente

                // Mostra un alert per comunicare all'utente che ha completato tutti gli esercizi
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
                if (difficolta.equals("semplice")) {
                    difficolta = "medio";
                } else if (difficolta.equals("medio")) {
                    difficolta = "difficile";
                }
                //l'indice riparte da 0 --> primo esercizio della nuova difficoltà
                IndiceEsercizioCorrente = 0;
            }

            // Salva e carica la domanda successiva
            save();
            loadDomanda();
        } else {
            // altrimenti mostra un alert di errore
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ordine Errato");
            alert.setHeaderText("L'ordine inserito non è corretto.");
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

        // Lettura del file riga per riga
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
            // carica la finestra fxml della dashboard --> Front.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Esercizi/Front/Front.fxml"));
            Parent front = loader.load();
            //ottenere il controller della finestra
            FrontController frontController = loader.getController();
            frontController.setUtente(this.loggedUtente);
            //crea la scena
            Scene frontScene = new Scene(front);
            // Invece di castare l'evento a Button, accedi alla finestra direttamente --> necessario per l'evento fittizio
            Stage stage = (Stage) root.getScene().getWindow(); // Usa root per ottenere la finestra corrente
            stage.setScene(frontScene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Verificato un errore nel caricamento della dashboard: --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo per ottenere la difficoltà corrente dell'utente
    private String getDiffCOrrenteOrdinaCodice() {
        //array di double che contiene i punteggi dell'utente
        double[] score = loggedUtente.getScore();
        // difficile
        if (score[3] >= 1.0) {
            if (score[4] >= 1.0) {
                return "difficile"; 
            }
            //medio se solo il semplice è completato
            return "medio"; 
        }
        //"semplice" se il livello "semplice" non è ancora completato
        return "semplice"; 
    }

    // Metodo per aggiornare punteggio e la difficoltà
    private void aggiornaPunteggio(String diff) {
        double[] score = loggedUtente.getScore();
        int index = -1;
        //ogni difficolta ha il suo indice associato
        switch (diff) {
            case "semplice":
                index = 3;
                break;
            case "medio":
                index = 4;
                break;
            case "difficile":
                index = 5;
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
