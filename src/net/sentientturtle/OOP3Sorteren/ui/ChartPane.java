package net.sentientturtle.OOP3Sorteren.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.sentientturtle.OOP3Sorteren.sort.DataSet;

public class ChartPane extends Pane {
    private Canvas canvas;

    public ChartPane() {
        super();
        canvas = new Canvas(10, 10);
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        this.getChildren().add(canvas);
    }

    public void reDraw(DataSet<Integer> dataSet) {
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        graphicsContext2D.setLineWidth(1);
        //stroke outline border
        graphicsContext2D.setStroke(Color.BLACK);
        graphicsContext2D.stroke();

        double width = canvas.getWidth();
        double height = canvas.getHeight();
        graphicsContext2D.clearRect(0, 0, width, height);

        Integer[] data = dataSet.getData();

        double max = 0;
        for (Number datum : data) max = Math.max(datum.doubleValue(), max);
        double colWidth = width / data.length;
        double colHeightFactor = height / max;

        for (int i = 0; i < data.length; i++) {
            if (dataSet.getSwappedColumns().contains(i)) {
                graphicsContext2D.setFill(Color.DARKRED);
            } else {
                graphicsContext2D.setFill(Color.DARKGREY);
            }
            double colHeight = data[i] == null ? 0 : data[i].doubleValue();
            graphicsContext2D.fillRect(colWidth * i, height - colHeightFactor * colHeight, colWidth, colHeightFactor * colHeight);
            graphicsContext2D.strokeRect(colWidth * i, height - colHeightFactor * colHeight, colWidth, colHeightFactor * colHeight);
        }
    }
}
