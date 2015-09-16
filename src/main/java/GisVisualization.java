
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Johannes on 10.09.2015.
 */
public class GisVisualization {

    private int id;
    private static int id_counter = 0;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    public static AnchorPane group;
    private double[] x_coords;
    private double[] y_coords;
    private ArrayList<Rectangle> tooltips;

    public GisVisualization(int canvas_width, int canvas_height, double[] x_coords, double[] y_coords, AnchorPane group)
    {
        this.id = id_counter++;
        GisVisualization.group = group;
        this.canvas = new Canvas(canvas_width, canvas_height);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.x_coords = x_coords;
        this.y_coords = y_coords;
        this.tooltips = new ArrayList<>();
    }


    /**
     * Creates a polygon from the given points and draw it on the canvas.
     * Also creates tooltips for each point in the polygon
     * @param canvas_width Width of the canvas the GIS-visualization will drawn to
     * @param canvas_height Height of the canvas the GIS-visualization will drawn to
     * @param x_coords The x coordinates of the points used to draw the polygon
     * @param y_coords The y coordinates of the points used to draw the polygon
     * @param group The group the polygon will be drawn at
     * @return a GisVisualization object
     */
    public static GisVisualization createVisualization(int canvas_width, int canvas_height, double[] x_coords, double[] y_coords, AnchorPane group)
    {
        GisVisualization gisVis = new GisVisualization(canvas_width, canvas_height, x_coords, y_coords, group);
        Random r = new Random();
        gisVis.create2DShape(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        group.getChildren().add(gisVis.canvas);
        gisVis.createTooltips(group);

        return gisVis;
    }


    /**
     * Redraws this GisVisualization object and tooltips to its given group
     */
    public void redraw()
    {
        group.getChildren().add(this.canvas);
        for (Rectangle r : tooltips)
        {
            group.getChildren().add(r);
        }
    }

    /**
     * Creates a polygon using this GisVisualization object's graphicsContext
     * @param color The color of the polygon
     */
    private void create2DShape(Color color)
    {
        graphicsContext.setFill(color);
        graphicsContext.fillPolygon(this.x_coords, this.y_coords, this.x_coords.length);
    }


    /**
     * Creates tooltips for each of the points involved in this GisVisualization
     * @param group The group the tooltips will be added to
     */
    private void createTooltips(AnchorPane group)
    {
        for (int i = 0; i < this.x_coords.length; i++)
        {
            Rectangle r = new Rectangle(this.x_coords[i]-2.5, this.y_coords[i]-2.5,5.0,5.0);

            Tooltip t = new Tooltip(this.x_coords[i] + " , " + this.y_coords[i]);
            Tooltip.install(r, t);
            tooltips.add(r);
            group.getChildren().add(r);
        }
    }

    /**
     * Toggles the visibility of this GisVisualization object
     */
    public void toggleVisibility()
    {
        this.canvas.setVisible(!canvas.isVisible());
        for (Rectangle rect : this.tooltips)
        {
            rect.setVisible(canvas.isVisible());
        }
    }


    /**
     * Get the ID for this GisVisualization object
     * @return The ID
     */
    public int getID()
    {
        return this.id;
    }

}
