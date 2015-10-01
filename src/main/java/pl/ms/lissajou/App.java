package pl.ms.lissajou;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.ms.lissajou.view.MainView;

/**
 * Created by Marcin on 2015-09-02.
 */
@SpringBootApplication
public class App extends AbstractJavaFxApplicationSupport {

    @Value("${app.ui.title}")
    private String windowTitle;

    @Autowired
    private MainView mainView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(windowTitle);
        primaryStage.centerOnScreen();

        Scene sc = new Scene(mainView, 1024, 768);
        sc.getStylesheets().add("myStyle.css");

        primaryStage.setScene(sc);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launchApp(App.class, args);
    }
}