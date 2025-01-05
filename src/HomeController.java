import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class HomeController {

    @FXML
    private Label target1; // Dichiarazione del campo target1, collegato all'elemento Label nel file FXML

    @FXML
void loginClicked(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
        Parent login = loader.load();
        Scene loginScene = new Scene(login);
        Stage loginWindow = (Stage) target1.getScene().getWindow();

        loginWindow.setScene(loginScene);

        // Forza la finestra ad avere dimensioni massimizzate
        loginWindow.setMaximized(true); 
        loginWindow.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
        loginWindow.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());

        loginWindow.show();
    } catch (Exception e) {
        System.out.println("Verificato un errore nel caricamento della finestra di login: --> " + e.getMessage());
    }
}

@FXML
void registrazioneClicked(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registrazione/Registrazione.fxml"));
        Parent register = loader.load();
        Scene regScene = new Scene(register);
        Stage regWindow = (Stage) target1.getScene().getWindow();

        regWindow.setScene(regScene);

        // Forza la finestra ad avere dimensioni massimizzate
        regWindow.setMaximized(true);
        regWindow.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
        regWindow.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());

        regWindow.show();
    } catch (Exception e) {
        System.out.println("Verificato un errore nel caricamento della finestra di registrazione: --> " + e.getMessage());
    }
}

}
