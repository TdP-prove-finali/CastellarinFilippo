package it.polito.tdp.interrail_java;

import javafx.application.Application;
import static javafx.application.Application.launch;

import it.polito.tdp.interrail_java.model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class applies the Model View Controller (MVC) pattern principles, 
 * separating all components from each others.
 * @author Filippo Castellarin - TdP-2022 Politecnico di Torino
 *
 */
public class EntryPoint extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        Parent root = loader.load();
        
        FXMLController controller = loader.getController();
        Model model = new Model();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
//      scene.getStylesheets().add("/styles/Styles.css");
//      scene.getRoot().setStyle("-fx-font-family: 'sans-serif'");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
