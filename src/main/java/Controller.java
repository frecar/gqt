
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    private Button submit;
    @FXML
    private TextArea queryInput;
    @FXML
    private AnchorPane upperPane;
    @FXML
    public VBox vboxLayers;
    @FXML
    public void pressed(){
        System.out.println(queryInput.getText());
        draw_polygon(queryInput.getText());

        vboxLayers.getChildren().add(new HBox());


    }



    public void draw_polygon(String poly) {
        //'POLYGON((10 10,20 10,20 20,10 20,10 10))'
        Pattern p = Pattern.compile(".POLYGON\\(\\((.*?)\\)\\).");
        Matcher m = p.matcher(poly); // get a matcher object
        if (m.find()) {
            String s = m.group(1);
            String[] s_array = s.split(",");
            ArrayList<Double> x_coords = new ArrayList<Double>();
            ArrayList<Double> y_coords = new ArrayList<Double>();

            for (String a : s_array) {
                String[] coords = a.split(" ");
                x_coords.add(Double.parseDouble(coords[0]));
                y_coords.add(Double.parseDouble(coords[1]));
            }

            double[] x_array = new double[x_coords.size()];
            for (int i = 0; i < x_array.length; i++) {
                x_array[i] = x_coords.get(i);                // java 1.5+ style (outboxing)
            }
            double[] y_array = new double[y_coords.size()];
            for (int i = 0; i < y_array.length; i++) {
                y_array[i] = y_coords.get(i);                // java 1.5+ style (outboxing)
            }

            System.out.println(x_array.length);
            System.out.println(y_array.length);
            //draw2DShapes(gc, Color.rgb(255,0,0,0.5f), x_array, y_array,g);
            GisVisualization gv = GisVisualization.createVisualization(500, 500, x_array, y_array, upperPane);
            Layer hb = new Layer(gv, vboxLayers);
            Layer.layers.add(hb);
            hb.reorderLayers();
        }

    }










}
