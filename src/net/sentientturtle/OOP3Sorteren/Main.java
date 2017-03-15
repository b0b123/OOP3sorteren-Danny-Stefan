package net.sentientturtle.OOP3Sorteren;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sentientturtle.OOP3Sorteren.sort.BubbleSort;
import net.sentientturtle.OOP3Sorteren.sort.InsertionSort;
import net.sentientturtle.OOP3Sorteren.sort.QuickSort;
import net.sentientturtle.OOP3Sorteren.sort.YieldingArray;
import net.sentientturtle.OOP3Sorteren.thread.Coroutine;
import net.sentientturtle.OOP3Sorteren.ui.ChartPane;

import java.util.Random;

public class Main extends Application {
    private Random random = new Random();
    private ChartPane pane;
    private static TextField time;
    private int getTime;
    private Coroutine sort;
    private YieldingArray<Integer> dataSet;
    private Button auto;
    private Thread bgThread;

    private void newSort(SortingType sortingType) {
        Integer[] data = new Integer[20];
        for (int i = 0; i < data.length; i++) data[i] = random.nextInt(10)+1;
        dataSet = new YieldingArray<>(data);
        switch (sortingType) {
            case Bubblesort:
                sort = new Coroutine(new BubbleSort<>(dataSet));
                break;
            case InsertionSort:
                sort = new Coroutine(new InsertionSort<>(dataSet));
                break;
            case QuickSort:
                sort = new Coroutine(new QuickSort<>(dataSet));
                break;
            case MergeSort:
                // TODO
                throw new RuntimeException("No mergesort exists yet!");
        }

        pane.reDraw(dataSet);
        BGRunnable bgRunnable = new BGRunnable(sort, dataSet);
        if (bgThread != null) bgThread.interrupt();
        bgThread = new Thread(bgRunnable);
        bgThread.start();
        auto.setOnMouseClicked(event -> bgRunnable.toggle());
    }

    @Override
    public void start(Stage primaryStage){
//        final AbstractSort<Integer> sort;
        pane = new ChartPane();
        pane.setStyle("-fx-border-color: black");


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
        auto = new Button("Auto");
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

        newSort(SortingType.Bubblesort);

        bubblesortMenu.setOnAction(event -> newSort(SortingType.Bubblesort));
        insertionsortMenu.setOnAction(event -> newSort(SortingType.InsertionSort));
        quicksortMenu.setOnAction(event -> newSort(SortingType.QuickSort));

        step.setOnMouseClicked(event -> {
            if (!sort.isDone()) {
                try {
                    sort.step();
                    pane.reDraw(dataSet);
                } catch (InterruptedException ignored) {
                }
            }
        });

        getTime = Integer.parseInt(time.getText());
        set.setOnMouseClicked(event -> getTime = Integer.parseInt(time.getText()));

        primaryStage.setOnCloseRequest(event -> {
            sort.stop();
            bgThread.interrupt();
        });
    }
    public static void main(String[] args) {
        launch(args);
    }

    private class BGRunnable implements Runnable {
        private boolean isRunning = false;
        private Coroutine sort;
        private YieldingArray<Integer> yieldingArray;

        BGRunnable(Coroutine sort, YieldingArray<Integer> data) {
            this.sort = sort;
            this.yieldingArray = data;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (isRunning) {
                        if (!sort.isDone()) {
                            sort.step();
                        } else {
                            break;
                        }
                        Platform.runLater(() -> pane.reDraw(yieldingArray));
                    }
                    try {
                        Thread.sleep(getTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                System.out.println("DONE");
            } catch (InterruptedException ignored) {
                // Exit immediately
            }
        }

        public void toggle() {
            isRunning = !isRunning;
        }
    }

    private enum SortingType {
        Bubblesort,
        InsertionSort,
        QuickSort,
        MergeSort
    }
}
