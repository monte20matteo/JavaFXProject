import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Home extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carica il file FXML che definisce l'interfaccia utente
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));

            // Crea una nuova scena con il contenuto definito nel file FXML
            Scene scene = new Scene(root);

            // Imposta la scena come contenuto della finestra principale (Stage)
            primaryStage.setScene(scene);

            // Mostra la finestra principale (Stage)
            primaryStage.show();

        } catch (Exception e) {
            // Gestisce eventuali eccezioni durante il caricamento dell'interfaccia utente
            System.out.println("Verificato un errore nel caricamento della finestra principale: --> " + e.getMessage());
            e.printStackTrace(); 
        }
    }

}
