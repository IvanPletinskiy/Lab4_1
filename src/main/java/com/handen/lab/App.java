package com.handen.lab;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        BorderPane container = new BorderPane();
        initializeViews(container);
        root.getChildren().add(container);

        stage.setTitle("Ivan Pletinski 951008");
        stage.addEventHandler(...);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handle(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            startRace();
        }
    }

    private static void initializeViews(Pane container) {
        createBackground(container);

        createRunners(container);

        createFinishText(container);
        createFinishLabel(container);
    }

    private static void createBackground(Pane container) {
        var centerY = WINDOW_SIDE / 2;
        var centerX = WINDOW_SIDE / 2;

        Canvas background = new Canvas(WINDOW_SIDE, WINDOW_SIDE);
        var context = background.getGraphicsContext2D();
        drawLaps(context);
        context.strokeLine(centerX, centerY, 200, centerY);

        container.getChildren().add(background);
    }

    private static void drawLaps(GraphicsContext context) {
        var centerY = WINDOW_SIDE / 2;
        var centerX = WINDOW_SIDE / 2;
        context.setStroke(Paint.valueOf("black"));
        for(int i = 0; i < 8; i++) {
            var radius = START_RADIUS + DELTA_RADIUS * i;
            var circleX = centerX - radius;
            var circleY = centerY - radius;
            context.strokeOval(circleX, circleY, 2 * radius, 2 * radius);
        }
    }

    private static void createRunners(Pane pane) {
        var centerY = WINDOW_SIDE / 2;
        var centerX = WINDOW_SIDE / 2;

        for(int i = 0; i < 8; i++) {
            var lapRadius = START_RADIUS + DELTA_RADIUS * i;
            var circleX = centerX - lapRadius;

            Circle shape = new Circle(RUNNER_RADIUS, paints.get(i));
            shape.setCenterX(circleX);
            shape.setCenterY(centerY);
            pane.getChildren().add(shape);

            RunnerShape runnerShape = new RunnerShape(shape, lapRadius);
            runners.add(runnerShape);
        }
    }

    private static void createFinishLabel(Pane container) {
        Text finishLabel = new Text("START/FINISH");
        finishLabel.setLayoutY(WINDOW_SIDE / 2);
        finishLabel.setLayoutX(25);
        finishLabel.setFont(Font.font(22));
        container.getChildren().add(finishLabel);
    }

    private static void createFinishText(Pane container) {
        finishText = new Text("Результаты забега: \n");
        finishText.setLayoutX(25);
        finishText.setLayoutY(25);
        finishText.setFont(Font.font(14));
        container.getChildren().add(finishText);
    }

    private static void startRace() {
        for(RunnerShape runner : runners) {
            runner.startRun(new FinishListener() {...});
        }
    }

    public void onFinish() {
        finishedCount++;
        int id = runners.indexOf(runner) + 1;
        updateFinishText(id);
    }

    private static void updateFinishText(int id) {
        String text = finishText.getText();
        text += String.format(finishPattern, finishedCount, id);
        finishText.setText(text);
    }
}