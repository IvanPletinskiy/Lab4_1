package com.handen.lab;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

class RunnerShape {
    private Circle shape;
    private final Random random;
    private static final double START_ANGULAR_VELOCITY = 0.5;
    private double angularVelocity;
    private double angle = 0;
    private final int lapRadius;
    private final double lapCenterX;
    private final double lapCenterY;

    private Timeline timeline;
    private FinishListener mFinishListener;

    public RunnerShape(Circle shape, int lapRadius) {
        random = new Random();
        angularVelocity = START_ANGULAR_VELOCITY;

        this.lapRadius = lapRadius;
        lapCenterX = shape.getCenterX() + lapRadius;
        lapCenterY = shape.getCenterY();

        this.shape = shape;

        timeline = new Timeline(new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                runTick();
                moveShape();
            }
        }));

        timeline.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observable, Animation.Status oldValue, Animation.Status newValue) {
                if(oldValue == Animation.Status.RUNNING) {
                    if(newValue == Animation.Status.STOPPED) {
                        if(angle < 300) {
                            timeline.setCycleCount(20);
                        }
                        if(angle < 360) {
                            timeline.play();
                        }
                        else {
                            mFinishListener.onFinish();
                        }
                    }
                }
            }
        });
    }

    private void moveShape() {
        if(angle > 360) {
            angle = 360;
        }
        var radianAngle = angle * Math.PI / 180;
        var sin = Math.sin(radianAngle);
        var cos = Math.cos(radianAngle);
        var centerY = lapCenterX - lapRadius * sin;
        var centerX = lapCenterY - lapRadius * cos;
        shape.setCenterX(centerX);
        shape.setCenterY(centerY);
    }

    public void startRun(FinishListener finishListener) {
        this.mFinishListener = finishListener;
        timeline.play();
    }

    public void runTick() {
        angle += angularVelocity;
        updateVelocity();
    }

    private void updateVelocity() {
        var acceleration = random.nextDouble();
        acceleration -= 0.3;
        acceleration /= 10;
        angularVelocity += acceleration;
        if(angularVelocity < 0) {
            angularVelocity = 0;
        }
    }

    interface FinishListener {
        void onFinish();
    }
}
