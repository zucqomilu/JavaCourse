package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Pane root;

    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();

    private GameObject player;

    private Parent createContent(){
        root = new Pane();
        root.setPrefSize(600, 600);

        player = new Player();
        player.setVelocety(new Point2D(1, 0));

        addGameObject(player, 300, 300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        return root;
    }

    private void addBullet(GameObject bullet, double x, double y){
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(GameObject enemy, double x, double y){
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }

    private void addGameObject(GameObject gameObject, double x, double y){
        gameObject.getView().setTranslateX(x);
        gameObject.getView().setTranslateY(y);
        root.getChildren().add(gameObject.getView());
    }
    //runs 60 times/sec
    private void onUpdate(){
        for(GameObject bullet : bullets){
            for(GameObject enemy : enemies){
                if(bullet.isColliding(enemy)){
                    bullet.setAlive(false);
                    enemy.setAlive(false);

                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                }
            }
        }
        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);

        player.update();

        if(Math.random() < 0.02){
            addEnemy(new Enemy(), Math.random() * root.getPrefWidth(),
                    Math.random() * root.getPrefHeight());
        }
    }

    private static class Player extends GameObject{
        Player(){
            super(new Rectangle(40, 20, Color.BLUE));
        }
    }

    private static class Enemy extends GameObject{
        Enemy(){
            super(new Circle(15, 15, 15, Color.RED));
        }
    }

    private static class Bullet extends GameObject{
        Bullet(){
            super(new Circle(5, 5, 5, Color.BROWN));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.getScene().setOnKeyPressed( event -> {
            if(event.getCode() == KeyCode.LEFT){
                player.rotateLeft();
            }
            else if(event.getCode() == KeyCode.RIGHT){
                player.rotateRight();
            }
            else if(event.getCode() == KeyCode.SPACE){
                Bullet bullet = new Bullet();
                bullet.setVelocety(player.getVelocety().normalize().multiply(5));
                addBullet(bullet, player.getView().getTranslateX(),
                        player.getView().getTranslateY());
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
