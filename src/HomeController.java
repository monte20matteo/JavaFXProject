import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class HomeController {

    @FXML private Label target; // Dichiarazione del campo target, collegato all'elemento Label nel file FXML

    @FXML
    void loginClicked(ActionEvent event) {
       try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Parent login = loader.load(); 
            Scene loginScene = new Scene(login); 
            Stage loginWindow = (Stage) target.getScene().getWindow();

            loginWindow.setScene(loginScene); // Imposta la scena appena creata nella finestra di login
            loginWindow.show();
        } catch(Exception e){
            System.out.println("Verificato un errore nel caricamento della finestra di login: --> "+e.getMessage());
            // Gestione dell'eccezione nel caso in cui il caricamento della finestra di login fallisca
        }
    }

    @FXML
    void registrazioneClicked(ActionEvent event) {

    }

}
