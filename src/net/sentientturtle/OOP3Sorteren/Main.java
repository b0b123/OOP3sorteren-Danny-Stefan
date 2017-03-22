package net.sentientturtle.OOP3Sorteren;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sentientturtle.OOP3Sorteren.sort.*;
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
    private Button step;
    private Button auto;
    private Thread bgThread;

    private static final int dataSize = 50;

    private void newSort(SortingType sortingType) {
        Integer[] data = new Integer[dataSize];
        for (int i = 0; i < data.length; i++) data[i] = random.nextInt(10)+1;
        dataSet = new YieldingArray<>(data);
        switch (sortingType) {
            case BubbleSort:
                sort = new BubbleSort<>(dataSet);
                break;
            case InsertionSort:
                sort = new InsertionSort<>(dataSet);
                break;
            case QuickSort:
                sort = new QuickSort<>(dataSet);
                break;
            case MergeSort:
                sort = new MergeSort<>(dataSet);
                break;
        }

        BGRunnable bgRunnable = new BGRunnable(sort, dataSet);
        if (bgThread != null) bgThread.interrupt();
        bgThread = new Thread(bgRunnable);
        bgThread.start();
        pane.reDraw(dataSet);
        auto.setOnMouseClicked(event -> bgRunnable.toggle());
    }

    @Override
    public void start(Stage primaryStage){
        pane = new ChartPane();
        pane.setStyle("-fx-border-color: black");

        //Create MenuBar
        MenuBar menuBar = new MenuBar();

        //create Menus
        Menu sortingMenu = new Menu("Algoritmen");


        RadioMenuItem bubbleSortMenu = new RadioMenuItem("BubbleSort");
        RadioMenuItem insertionSortMenu = new RadioMenuItem("InsertionSort");
        RadioMenuItem quickSortMenu = new RadioMenuItem("QuickSort");
        RadioMenuItem mergeSortMenu = new RadioMenuItem("MergeSort");

        ToggleGroup group = new ToggleGroup();
        bubbleSortMenu.setToggleGroup(group);
        insertionSortMenu.setToggleGroup(group);
        quickSortMenu.setToggleGroup(group);
        mergeSortMenu.setToggleGroup(group);
        bubbleSortMenu.setSelected(true);

        sortingMenu.getItems().addAll(bubbleSortMenu, insertionSortMenu, quickSortMenu, mergeSortMenu);

        menuBar.getMenus().add(sortingMenu);

        //create buttons
        step = new Button("Step");
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

        newSort(SortingType.BubbleSort);

        bubbleSortMenu.setOnAction(event -> newSort(SortingType.BubbleSort));
        insertionSortMenu.setOnAction(event -> newSort(SortingType.InsertionSort));
        quickSortMenu.setOnAction(event -> newSort(SortingType.QuickSort));
        mergeSortMenu.setOnAction(event -> newSort(SortingType.MergeSort));

        step.setOnMouseClicked(event -> {
            if (!sort.isFinished()) {
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
                        if (!sort.isFinished()) {
                            sort.step();
                        } else {
                            break;
                        }
                        Platform.runLater(() -> pane.reDraw(yieldingArray));
                    }
                    Thread.sleep(getTime);
                }
            } catch (InterruptedException e) {
                if (!sort.isFinished()) sort.stop();
            }
            yieldingArray.clearMarkers();
            Platform.runLater(() -> pane.reDraw(yieldingArray));
            if (isRunning) toggle();
        }

        void toggle() {
            step.setDisable(isRunning = !isRunning);
        }
    }

    private enum SortingType {
        BubbleSort,
        InsertionSort,
        QuickSort,
        MergeSort
    }
}
