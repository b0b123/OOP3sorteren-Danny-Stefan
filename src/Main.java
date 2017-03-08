import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sentientturtle.OOP3Sorteren.BubbleStepSort;
import net.sentientturtle.OOP3Sorteren.InsertionStepSort;

import java.util.Arrays;
import java.util.Random;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        Barchart pane = new Barchart();
        pane.setStyle("-fx-border-color: black");

        Button step = new Button("Step");

        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(step);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(hBox);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 600, 250);
        primaryStage.setTitle("sorteren"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

    }
    public static void main(String[] args) {
        launch(args);


        Random random = new Random();
        Integer[] data = new Integer[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(200);
        }
        InsertionStepSort<Integer> insertionStepSort = new InsertionStepSort<>(data);
        do{
            System.out.println(Arrays.toString(insertionStepSort.getData())+ "\t" + insertionStepSort.index);
        }while(!insertionStepSort.step());

        BubbleStepSort<Integer> bubbleStepSort = new BubbleStepSort<>(data);
        do {
            System.out.println(Arrays.toString(bubbleStepSort.getData()) + "\t" + bubbleStepSort.index);
        } while (!bubbleStepSort.step());
    }

}
class Barchart extends Pane{

}
