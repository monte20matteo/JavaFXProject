import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class HomeController {

    // Dichiarazione di un oggetto di tipo Label collegato ad home.fxml con il suo fx:id
    @FXML
    private Label target1; 

    // Metodo che gestisce l'evento di click sul bottone "Login"
    @FXML
    void loginClicked(ActionEvent event) {
        try {
            // Caricamento della finestra di login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Parent login = loader.load();
            // Creazione di una nuova scena e di una nuova finestra
            Scene loginScene = new Scene(login);
            Stage loginWindow = (Stage) target1.getScene().getWindow();
            loginWindow.setScene(loginScene);
            // Forza la finestra ad avere dimensioni massimizzate
            loginWindow.setMaximized(true);
            loginWindow.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
            loginWindow.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());
            // Mostra la finestra
            loginWindow.show();
        } catch (Exception e) {
            System.out.println("Verificato un errore nel caricamento della finestra di login: --> " + e.getMessage());
        }
    }

    // Metodo che gestisce l'evento di click sul bottone "Registrazione"
    @FXML
    void registrazioneClicked(ActionEvent event) {
        try {
            // Caricamento della finestra di registrazione
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registrazione/Registrazione.fxml"));
            Parent register = loader.load();
            // Creazione di una nuova scena e di una nuova finestra
            Scene regScene = new Scene(register);
            Stage regWindow = (Stage) target1.getScene().getWindow();
            regWindow.setScene(regScene);
            // Forza la finestra ad avere dimensioni massimizzate
            regWindow.setMaximized(true);
            regWindow.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
            regWindow.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());
            // Mostra la finestra
            regWindow.show();
        } catch (Exception e) {
            System.out.println(
                    "Verificato un errore nel caricamento della finestra di registrazione: --> " + e.getMessage());
        }
    }

}
