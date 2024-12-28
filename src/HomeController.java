import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    void accediClicked(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Parent login = loader.load(); 
            Scene loginScene = new Scene(login); 
            Stage loginWindow = (Stage) tfPassword.getScene().getWindow();

            loginWindow.setScene(loginScene); // Imposta la scena appena creata nella finestra di login
            loginWindow.show();
        } catch(Exception e){
            System.out.println("Verificato un errore nel caricamento della finestra di login: --> "+e.getMessage());
            // Gestione dell'eccezione nel caso in cui il caricamento della finestra di login fallisca
        }
    }

    @FXML
    void creaNuovoUtenteClicked(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registration/Register.fxml"));
            // Carica il file FXML di Register.fxml utilizzando il caricatore FXMLLoader

            Parent register = loader.load(); // Carica la radice del file FXML (AnchorPane) per la finestra di registrazione

            Scene regScene = new Scene(register); // Crea una nuova scena utilizzando la radice caricata

            Stage regWindow = (Stage) tfPassword.getScene().getWindow();
            // Ottiene il riferimento alla finestra corrente (della Home) per impostare la nuova scena

            regWindow.setScene(regScene); // Imposta la scena appena creata nella finestra di registrazione
            regWindow.show(); // Mostra la finestra di registrazione
        } catch(Exception e){
            System.out.println("Verificato un errore nel caricamento della finestra di registrazione: --> "+e.getMessage());
            // Gestione dell'eccezione nel caso in cui il caricamento della finestra di registrazione fallisca
        }
    }

}
