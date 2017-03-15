package net.sentientturtle.OOP3Sorteren;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sentientturtle.OOP3Sorteren.sort.AbstractSort;
import net.sentientturtle.OOP3Sorteren.sort.BubbleSort;
import net.sentientturtle.OOP3Sorteren.sort.DataSet;
import net.sentientturtle.OOP3Sorteren.sort.InsertionSort;
import net.sentientturtle.OOP3Sorteren.ui.ChartPane;

import java.util.Random;

public class Main extends Application {
    private Random random = new Random();
    private ChartPane pane;
    private static TextField time;
    private int getTime;
    private AbstractSort<Integer> sort;
    private String menuText = "insertionsort";
    BGRunnable bgRunnable;




    @Override
    public void start(Stage primaryStage){
//        final AbstractSort<Integer> sort;
        pane = new ChartPane();
        pane.setStyle("-fx-border-color: black");

        //create Tabpane
//        TabPane tabPane = new TabPane();

        //create Tabs
//        Tab bubbleTab = new Tab();
//        bubbleTab.setText("BubbleSort");
//        Tab insertionTab = new Tab();
//        insertionTab.setText("InsertionSort");

        //add tabs to tabpane
//        tabPane.getTabs().addAll(bubbleTab, insertionTab);



        //add content to tab
//        bubbleTab.setContent(pane);
//        insertionTab.setContent(pane);


        //Create MenuBar
        MenuBar menuBar = new MenuBar();

        //create Menus
        Menu sortingMenu = new Menu("Algoritmen");


        RadioMenuItem bubblesortMenu = new RadioMenuItem("bubblesort");
        RadioMenuItem insertionsortMenu = new RadioMenuItem("insertionsort");
        RadioMenuItem quicksortMenu = new RadioMenuItem("quicksort");

        ToggleGroup group = new ToggleGroup();
        bubblesortMenu.setToggleGroup(group);
        insertionsortMenu.setToggleGroup(group);
        quicksortMenu.setToggleGroup(group);
        bubblesortMenu.setSelected(true);

        sortingMenu.getItems().addAll(bubblesortMenu, insertionsortMenu, quicksortMenu);

        menuBar.getMenus().add(sortingMenu);

        //create buttons
        Button step = new Button("Step");
        Button auto = new Button("Auto");
        Button set = new Button("Set");
        Label label = new Label("set time in ms:");


        time = new TextField("50");

        time.setPrefWidth(60);

        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(step, auto, label, time, set);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(pane);
        borderPane.setBottom(hBox);


        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 600, 250);
        primaryStage.setTitle("Sorteren"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        Integer[] data = new Integer[20];
        for (int i = 0; i < data.length; i++) data[i] = random.nextInt(10)+1;
        DataSet<Integer> dataSet = new DataSet<>(data);
        sort = new BubbleSort<>(dataSet);

        bubblesortMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sort = new BubbleSort<>(dataSet);
                System.out.println("Bubblesort");
                bgRunnable = new BGRunnable(sort);
                Thread bgThread = new Thread(bgRunnable);
                bgThread.start();

            }
        });

        insertionsortMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sort = new InsertionSort<>(dataSet);
                System.out.println("InsertionSort");
                menuText = "InsertionSort";
                bgRunnable = new BGRunnable(sort);
                Thread bgThread = new Thread(bgRunnable);
                bgThread.start();
            }
        });

        step.setOnMouseClicked(event -> {
            if (!sort.isDone()) {
                sort.step();
                pane.reDraw(dataSet);
            }
        });

        pane.reDraw(dataSet);
        bgRunnable = new BGRunnable(sort);
        Thread bgThread = new Thread(bgRunnable);
        bgThread.start();
        auto.setOnMouseClicked(event -> bgRunnable.toggle());
        primaryStage.setOnCloseRequest(event -> bgThread.interrupt());

        set.setOnMouseClicked(event -> getTime = Integer.parseInt(time.getText()));

    }
    public static void main(String[] args) {
        launch(args);
    }

    private class BGRunnable implements Runnable {
        private boolean isRunning = false;
        private AbstractSort<Integer> sort;
        private DataSet<Integer> dataSet;



        BGRunnable(AbstractSort<Integer> sort) {
            this.sort = sort;
            this.dataSet = sort.getDataSet();
        }

        @Override
        public void run() {
            while (true){
                if (isRunning) {
                    if (!sort.isDone()) {
                        sort.step();
                    } else {
                        break;
                    }
                    Platform.runLater(() -> pane.reDraw(dataSet));
                }
                try {
                    Thread.sleep(getTime);
                    //System.out.println(setTime);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("DONE");
        }

        public void toggle() {
            isRunning = !isRunning;
        }




    }
}
