package Login;

import java.io.File;
import java.util.Scanner;

// Classe Utente per la gestione degli utenti
public class Utente {
    private String username; // istanza username
    private String password; // istanza password
    private double[] score = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }; // istanza score per tenere traccia dei punteggi

    // costuttore --> oggetto Utente caratterizzato da username e password
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // metodi getter e setter per username e password
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // metodo toString per stampare il benvenuto nelle pagine fxml dove è richiesto
    @Override
    public String toString() {
        return "Benvenuto in JavaStart " + this.username;
    }

    public double[] getScore() {
        return this.score;
    }

    // metodo per settare il punteggio, aggiunge 0.25 al punteggio 
    public void setScore(int indx) {
        this.score[indx] += 0.25;
    }

    // metodo per salvare i dati su file in un formato prestabilito --> username,password,puteggi
    public String onFile() {
        return this.username + "," + this.password + "," + score[0] + "," + score[1] + "," + score[2] + "," + score[3]
                + "," + score[4] + "," + score[5] + "," + score[6] + "," + score[7] + "," + score[8];
    }

    // metodo per caricare i dati da file
    public void loadFile(String user, String password) {
        try {
            // Apre il file users.csv per la lettura
            Scanner scan = new Scanner(new File("src/Data/users.csv")); 
            // Legge il file riga per riga
            while (scan.hasNextLine()) {
                // Divide la riga in un array di stringhe usando la virgola come separatore
                String[] line = scan.nextLine().split(",");
                // Verifica se la prima stringa dell'array è uguale all'username e la seconda è uguale alla password
                if (line[0].equals(user) && line[1].equals(password)) {
                    // Se la condizione è verificata, assegna i punteggi alle variabili 
                    this.score[0] = Double.parseDouble(line[2]);
                    this.score[1] = Double.parseDouble(line[3]);
                    this.score[2] = Double.parseDouble(line[4]);
                    this.score[3] = Double.parseDouble(line[5]);
                    this.score[4] = Double.parseDouble(line[6]);
                    this.score[5] = Double.parseDouble(line[7]);
                    this.score[6] = Double.parseDouble(line[8]);
                    this.score[7] = Double.parseDouble(line[9]);
                    this.score[8] = Double.parseDouble(line[10]);
                    break;
                }
            }
            scan.close();
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getDiffCOrrenteOrdinaCodice() {
        if (score[3] >= 1.0) { // Verifica se il livello "semplice" è completato
            if (score[4] >= 1.0) { // Verifica se il livello "medio" è completato
                return "difficile"; // Ritorna "difficile" se entrambi i livelli precedenti sono completati
            }
            return "medio"; // Ritorna "medio" se solo il livello "semplice" è completato
        }
        return "semplice"; // Ritorna "semplice" se il livello "semplice" non è ancora completato
    }

    // Metodo per aggiornare difficoltà per l'esercizio Ordina Codice
    public void aggiornaDiff(String diff) {
        int index = -1;
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
            if (score[index] <= 0.75) {
                score[index] += 0.25;
            }
        }
    }

}
