
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Johannes on 10.09.2015.
 */
public class Layer extends  HBox{

    private int orderID;
    private GisVisualization gisVis;
    private Button buttonUp;
    private Button buttonDown;
    private VBox parentContainer;
    public static ArrayList<Layer> layers = new ArrayList<>();

    public Layer(GisVisualization gisVis, VBox parentContainer)
    {
        this.gisVis = gisVis;
        this.orderID = gisVis.getID();
        this.parentContainer = parentContainer;
        createLayer();
    }


    public void createLayer()
    {

        CheckBox cb = new CheckBox();
        cb.setOnAction(event -> this.gisVis.toggleVisibility());


        cb.setSelected(true);

        //TODO style textfield with css so it looks like a label when not highlighted
        TextField tf = new TextField("Layer" + this.orderID);

        buttonUp = new Button("Up");
        buttonUp.setOnAction(event -> {
            this.orderID--;
            Layer.layers.get(this.orderID).setOrderID(this.orderID + 1);
            reorderLayers();
        });
        buttonDown = new Button("Down");
        buttonDown.setOnAction(event -> {
            this.orderID++;
            Layer.layers.get(this.orderID).setOrderID(this.orderID - 1);
            reorderLayers();
        });

        //TODO style up and down buttons with better representation, generally improve layer visuals

        VBox vb = new VBox();
        vb.getChildren().add(buttonUp);
        vb.getChildren().add(buttonDown);

        this.getChildren().add(cb);
        this.getChildren().add(tf);
        this.getChildren().add(vb);
    }

    public int getOrderID()
    {
        return this.orderID;
    }

    public void setOrderID(int id)
    {
        this.orderID = id;
    }

    /**
     * Disable or enable the up button
     * @param b If True, disables button. Enables if false
     */
    public void setUpDisable(boolean b)
    {
        buttonUp.setDisable(b);
    }

    /**
     * Disable or enable the down button
     * @param b If True, disables button. Enables if false
     */
    public void setDownDisable(boolean b)
    {
        buttonDown.setDisable(b);
    }


    /**
     * Reorders the layers according to their ID and redraws the polygons in the same order
     */
    public void reorderLayers()
    {
        this.parentContainer.getChildren().remove(0, this.parentContainer.getChildren().size());

        Collections.sort(layers, Comparator.comparing(Layer::getOrderID));

        GisVisualization.group.getChildren().remove(0, GisVisualization.group.getChildren().size());

        for (Layer hb : layers)
        {
            hb.gisVis.redraw();
            hb.setUpDisable(false);
            hb.setDownDisable(false);
            this.parentContainer.getChildren().add(hb);

        }
        layers.get(0).setUpDisable(true);
        layers.get(layers.size()-1).setDownDisable(true);


    }











}
