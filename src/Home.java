
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Home extends Application {

    // Metodo main per l'avvio dell'applicazione --> launch
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carica il file FXML che definisce l'interfaccia utente --> home.fxml
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            //titolo applicazione
            primaryStage.setTitle("JavaStart");

            // Crea una nuova scena con il contenuto definito nel file FXML
            Scene scene = new Scene(root);
            // Imposta la finestra come massimizzata
            primaryStage.setMaximized(true);
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
