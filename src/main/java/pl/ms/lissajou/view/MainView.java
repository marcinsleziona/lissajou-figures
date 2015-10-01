package pl.ms.lissajou.view;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

/**
 * Created by Marcin on 2015-09-03.
 */
@Component
public class MainView extends VBox {

    private final TextField wxval;
    private final TextField rxval;
    private final TextField wyval;
    private final TextField ryval;
    private final TextField durval;
    private final Button apply;
    private final Group canvas;

    public MainView() {
        // add definition
        GridPane grid0 = new GridPane();
        grid0.setAlignment(Pos.CENTER_LEFT);
        //grid0.setPadding(new Insets(10, 10, 10, 10));
        grid0.setVgap(1);
        grid0.setHgap(1);
        grid0.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        final Label xlabel = new Label("x(t) = Asin(wx*t - rx), where A=200");
        xlabel.getStyleClass().add("label_12_bold");
        final Label ylabel = new Label("y(t) = Bcos(wy*t - ry), where B=200");
        ylabel.getStyleClass().add("label_12_bold");
        GridPane.setConstraints(xlabel, 0, 0);
        GridPane.setConstraints(ylabel, 0, 1);
        grid0.getChildren().addAll(xlabel, ylabel);

        // add variables
        GridPane grid = new GridPane();
        //grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        //grid.setGridLinesVisible(true);
        grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(30);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(30);
        grid.getColumnConstraints().addAll(column1, column2, column3, column4); // each get 25% of width

        // define the wx, rx in one row
        final Label wxlabel = new Label("wx");
        wxlabel.getStyleClass().add("label_12");
        GridPane.setConstraints(wxlabel, 0, 0);
        grid.getChildren().add(wxlabel);
        wxval = new TextField("4");
        wxval.setPrefColumnCount(5);
        GridPane.setConstraints(wxval, 1, 0);
        grid.getChildren().add(wxval);
        final Label rxlabel = new Label("rx");
        rxlabel.getStyleClass().add("label_12");
        GridPane.setConstraints(rxlabel, 2, 0);
        grid.getChildren().add(rxlabel);
        rxval = new TextField("3");
        rxval.setPrefColumnCount(5);
        GridPane.setConstraints(rxval, 3, 0);
        grid.getChildren().add(rxval);

        // define the wy, ry in one row
        final Label wylabel = new Label("wy");
        wylabel.getStyleClass().add("label_12");
        GridPane.setConstraints(wylabel, 0, 1);
        grid.getChildren().add(wylabel);
        wyval = new TextField("4");
        wyval.setPrefColumnCount(5);
        GridPane.setConstraints(wyval, 1, 1);
        grid.getChildren().add(wyval);
        final Label rylabel = new Label("ry");
        rylabel.getStyleClass().add("label_12");
        GridPane.setConstraints(rylabel, 2, 1);
        grid.getChildren().add(rylabel);
        ryval = new TextField("3");
        ryval.setPrefColumnCount(5);
        GridPane.setConstraints(ryval, 3, 1);
        grid.getChildren().add(ryval);

        // define the path transition
        final Label duration = new Label("Duration (ms)");
        duration.getStyleClass().add("label_12");
        GridPane.setConstraints(duration, 0, 2);
        grid.getChildren().add(duration);
        durval = new TextField("100000");
        durval.setPrefColumnCount(5);
        GridPane.setConstraints(durval, 1, 2);
        grid.getChildren().add(durval);

        // define the apply button
        apply = new Button("Apply");
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                canvas.getChildren().clear();

                Circle circle = new Circle(5, Color.LIGHTCORAL);
                canvas.getChildren().add(circle);

                Path path = new Path();
                double xMin = 0;
                double xMax = 1000;
                double xInc = 0.1;
                path.getElements().add(new MoveTo(
                        400 + 200 * Math.sin(Long.parseLong(wxval.getText()) * xMin - Long.parseLong(rxval.getText())),
                        300 + 200 * Math.cos(Long.parseLong(wyval.getText()) * xMin - Long.parseLong(ryval.getText()))));
                double x = xMin;

                while (x < xMax) {
                    path.getElements().add(new LineTo(
                            400 + 200 * Math.sin(Long.parseLong(wxval.getText()) * x - Long.parseLong(rxval.getText())),
                            300 + 200 * Math.cos(Long.parseLong(wyval.getText()) * x - Long.parseLong(ryval.getText()))));
                    x += xInc;
                }
                canvas.getChildren().add(path);

                PathTransition pt = new PathTransition(Duration.millis(Long.parseLong(durval.getText())), path, circle);
                pt.setCycleCount(Animation.INDEFINITE);
                pt.setAutoReverse(true);
                pt.play();
            }
        });
        GridPane.setConstraints(apply, 0, 3);
        grid.getChildren().add(apply);

        // define the canvas
        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setPadding(new Insets(10, 10, 10, 10));
        grid2.setVgap(5);
        grid2.setHgap(5);
        grid2.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        canvas = new Group();
        GridPane.setConstraints(canvas, 0, 0);
        grid2.getChildren().add(canvas);

        getChildren().add(grid0);
        getChildren().add(grid);
        getChildren().add(grid2);
    }
}
