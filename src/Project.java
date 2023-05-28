import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Project extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int OBJECT_SIZE = 50;

    private List<GameObject> objects;

    @Override
    public void start(Stage primaryStage) {
        objects = new ArrayList<>();
        Random random = new Random();

        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Rock Paper Scissors Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Создание и добавление объектов на сцену
        for (int i = 0; i < 3; i++) {
            Circle object = new Circle(OBJECT_SIZE);
            object.setFill(Color.RED);
            object.setTranslateX(random.nextDouble() * (WIDTH - OBJECT_SIZE));
            object.setTranslateY(random.nextDouble() * (HEIGHT - OBJECT_SIZE));
            objects.add(new GameObject(object));
            root.getChildren().add(object);
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Движение объектов
                for (GameObject object : objects) {
                    object.move();
                }

                // Обработка столкновений
                for (int i = 0; i < objects.size(); i++) {
                    for (int j = i + 1; j < objects.size(); j++) {
                        GameObject obj1 = objects.get(i);
                        GameObject obj2 = objects.get(j);

                        if (obj1.collidesWith(obj2)) {
                            obj1.handleCollision(obj2);
                            obj2.handleCollision(obj1);
                        }
                    }
                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    class GameObject {
        private Circle object;
        private Point2D velocity;

        public GameObject(Circle object) {
            this.object = object;
            Random random = new Random();
            this.velocity = new Point2D(random.nextDouble() - 0.5, random.nextDouble() - 0.5).normalize().multiply(3);
        }

        public void move() {
            object.setTranslateX(object.getTranslateX() + velocity.getX());
            object.setTranslateY(object.getTranslateY() + velocity.getY());

            // Обработка отскоков от границ окна
            if (object.getTranslateX() <= 0 || object.getTranslateX() + OBJECT_SIZE >= WIDTH) {
                velocity = new Point2D(-velocity.getX(), velocity.getY());
            }
            if (object.getTranslateY() <= 0 || object.getTranslateY() + OBJECT_SIZE >= HEIGHT) {
                velocity = new Point2D(velocity.getX(), -velocity.getY());
            }
        }

        public boolean collidesWith(GameObject other) {
            return object.getBoundsInParent().intersects(other.object.getBoundsInParent());
        }

        public void handleCollision(GameObject other) {
            if (this.object.getFill() == Color.RED && other.object.getFill() == Color.GREEN) {
                this.object.setFill(Color.GREEN);
            } else if (this.object.getFill() == Color.GREEN && other.object.getFill() == Color.BLUE) {
                this.object.setFill(Color.BLUE);
            } else if (this.object.getFill() == Color.BLUE && other.object.getFill() == Color.RED) {
                this.object.setFill(Color.RED);
            }
        }
    }
}
