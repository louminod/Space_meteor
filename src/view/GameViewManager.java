package view;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 800;

    private Stage menuStage;
    private ImageView ship;
    private ImageView star;

    private boolean isLeftKeyPressed, isRightKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane1, gridPane2;

    private final String BACKGROUND_IMAGE = "view/resources/purple.png";
    private final String METEOR_BROWM_IMAGE = "view/resources/meteor_brown.png";
    private final String METEOR_GREY_IMAGE = "view/resources/meteor_grey.png";
    private final int METEOR_BROWM_NUMBERS = 3;
    private final int METEOR_GREY_NUMBERS = 3;

    private ImageView[] tabBrownMeteors;
    private ImageView[] tabGreyMeteors;
    private ImageView[] tabPlayerLifes;

    private SmallInfoLabel pointsLabel;

    private int playerLife, points;

    private final String GOLD_STAR_IMAGE = "view/resources/star_gold.png";

    private final int STAR_RADIUS = 12;
    private final int SHIP_RADIUS = 27;
    private final int METEOR_RADIUS = 20;

    Random randomPositionGenerator;

    public GameViewManager() {
        this.initializeStage();
        this.createKeyListener();
        this.randomPositionGenerator = new Random();
    }

    private void createKeyListener() {

        this.gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                }
            }
        });

        this.gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                }
            }
        });
    }

    private void initializeStage() {
        this.gamePane = new AnchorPane();
        this.gameScene = new Scene(this.gamePane, this.GAME_WIDTH, this.GAME_HEIGHT);
        this.gameStage = new Stage();
        this.gameStage.setScene(this.gameScene);
    }

    public void createNewGame(Stage menuStage, SHIP choosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        this.createBackground();
        this.createShip(choosenShip);
        this.createGameElements(choosenShip);
        this.createGameLoop();
        this.gameStage.show();
    }

    private void createGameElements(SHIP choosenShip) {

        this.playerLife = 2;
        this.star = new ImageView(this.GOLD_STAR_IMAGE);
        this.setNewElementPosition(this.star);
        this.gamePane.getChildren().add(this.star);
        this.pointsLabel = new SmallInfoLabel("POINTS : 00");
        this.pointsLabel.setLayoutX(460);
        this.pointsLabel.setLayoutY(20);
        this.gamePane.getChildren().add(this.pointsLabel);

        this.tabPlayerLifes = new ImageView[3];

        for (int i = 0; i < this.tabPlayerLifes.length; i++) {
            this.tabPlayerLifes[i] = new ImageView(choosenShip.getUrlLife());
            this.tabPlayerLifes[i].setLayoutX(455 + (i * 50));
            this.tabPlayerLifes[i].setLayoutY(80);
            this.gamePane.getChildren().add(this.tabPlayerLifes[i]);
        }

        this.tabBrownMeteors = new ImageView[this.METEOR_BROWM_NUMBERS];
        for (int i = 0; i < this.tabBrownMeteors.length; i++) {
            this.tabBrownMeteors[i] = new ImageView(this.METEOR_BROWM_IMAGE);
            this.setNewElementPosition(this.tabBrownMeteors[i]);
            this.gamePane.getChildren().add(this.tabBrownMeteors[i]);
        }

        this.tabGreyMeteors = new ImageView[this.METEOR_GREY_NUMBERS];
        for (int i = 0; i < this.tabGreyMeteors.length; i++) {
            this.tabGreyMeteors[i] = new ImageView(this.METEOR_GREY_IMAGE);
            this.setNewElementPosition(this.tabGreyMeteors[i]);
            this.gamePane.getChildren().add(this.tabGreyMeteors[i]);
        }
    }

    private void moveGameElements() {

        this.star.setLayoutY(this.star.getLayoutY() + 5);

        for (int i = 0; i < this.tabBrownMeteors.length; i++) {
            this.tabBrownMeteors[i].setLayoutY(this.tabBrownMeteors[i].getLayoutY() + 7);
            this.tabBrownMeteors[i].setRotate(this.tabBrownMeteors[i].getRotate() + 4);
        }

        for (int i = 0; i < this.tabGreyMeteors.length; i++) {
            this.tabGreyMeteors[i].setLayoutY(this.tabGreyMeteors[i].getLayoutY() + 7);
            this.tabGreyMeteors[i].setRotate(this.tabGreyMeteors[i].getRotate() + 4);
        }
    }

    private void checkElementsPosition() {

        if (this.star.getLayoutY() > 1200) {
            this.setNewElementPosition(this.star);
        }

        for (int i = 0; i < this.tabBrownMeteors.length; i++) {
            if (this.tabBrownMeteors[i].getLayoutY() > 900) {
                this.setNewElementPosition(this.tabBrownMeteors[i]);
            }
        }

        for (int i = 0; i < this.tabGreyMeteors.length; i++) {
            if (this.tabGreyMeteors[i].getLayoutY() > 900) {
                // TODO test
                this.setNewElementPosition(this.tabGreyMeteors[i]);
            }
        }
    }

    private void setNewElementPosition(ImageView image) {
        image.setLayoutX(this.randomPositionGenerator.nextInt(600));
        image.setLayoutY(-(this.randomPositionGenerator.nextInt(3200) + 600));
    }

    private void createShip(SHIP choosenShip) {
        this.ship = new ImageView(choosenShip.getURL());
        this.ship.setLayoutX(this.GAME_WIDTH / 2);
        this.ship.setLayoutY(this.GAME_HEIGHT - 90);
        this.gamePane.getChildren().add(this.ship);
    }

    private void createGameLoop() {
        this.gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveBackground();
                moveGameElements();
                checkElementsPosition();
                checkElementCollide();
                moveShip();
            }
        };

        this.gameTimer.start();
    }

    private void moveShip() {

        if (this.isLeftKeyPressed && !this.isRightKeyPressed) {
            if (this.angle > -30) {
                this.angle -= 5;
            }

            this.ship.setRotate(this.angle);

            if (this.ship.getLayoutX() > 20) {
                this.ship.setLayoutX(this.ship.getLayoutX() - 3);
            }
        }

        if (this.isRightKeyPressed && !this.isLeftKeyPressed) {
            if (this.angle < 30) {
                this.angle += 5;
            }

            this.ship.setRotate(this.angle);

            if (this.ship.getLayoutX() < 522) {
                this.ship.setLayoutX(this.ship.getLayoutX() + 3);
            }
        }

        if (!this.isRightKeyPressed && !this.isLeftKeyPressed) {
            if (this.angle < 0) {
                this.angle += 5;
            } else if (this.angle > 0) {
                this.angle -= 5;
            }

            this.ship.setRotate(this.angle);
        }

        if (this.isRightKeyPressed && this.isLeftKeyPressed) {
            if (this.angle < 0) {
                this.angle += 5;
            } else if (this.angle > 0) {
                this.angle -= 5;
            }

            this.ship.setRotate(this.angle);
        }
    }

    private void createBackground() {
        this.gridPane1 = new GridPane();
        this.gridPane2 = new GridPane();

        for (int i = 0; i < 12; i++) {
            ImageView backgroudImage1 = new ImageView(this.BACKGROUND_IMAGE);
            ImageView backgroudImage2 = new ImageView(this.BACKGROUND_IMAGE);

            GridPane.setConstraints(backgroudImage1, i % 3, i / 3);
            GridPane.setConstraints(backgroudImage2, i % 3, i / 3);

            this.gridPane1.getChildren().add(backgroudImage1);
            this.gridPane2.getChildren().add(backgroudImage2);
        }

        this.gridPane2.setLayoutY(-1024);

        this.gamePane.getChildren().addAll(this.gridPane1, this.gridPane2);
    }

    private void moveBackground() {
        this.gridPane1.setLayoutY(this.gridPane1.getLayoutY() + 0.5);
        this.gridPane2.setLayoutY(this.gridPane2.getLayoutY() + 0.5);

        if (this.gridPane1.getLayoutY() >= 1024) {
            this.gridPane1.setLayoutY(-1024);
        }

        if (this.gridPane2.getLayoutY() >= 1024) {
            this.gridPane2.setLayoutY(-1024);
        }
    }

    private void checkElementCollide() {

        if (this.SHIP_RADIUS + this.STAR_RADIUS > this.calculateDistance(this.ship.getLayoutX() + 49,
                this.star.getLayoutX() + 15, this.ship.getLayoutY() + 37, this.star.getLayoutY() + 15)) {
            this.setNewElementPosition(this.star);

            this.points++;
            String textToSet = "POINTS : ";
            if (this.points < 10) {
                textToSet = textToSet + "0";
            }

            this.pointsLabel.setText(textToSet + this.points);
        }

        for (int i = 0; i < this.tabBrownMeteors.length; i++) {
            if (this.METEOR_RADIUS + this.SHIP_RADIUS > this.calculateDistance(this.ship.getLayoutX() + 49,
                    this.tabBrownMeteors[i].getLayoutX() + 15, this.ship.getLayoutY() + 37, this.tabBrownMeteors[i].getLayoutY() + 15)) {
                this.setNewElementPosition(this.tabBrownMeteors[i]);

                this.removeLife();
            }
        }

        for (int i = 0; i < this.tabGreyMeteors.length; i++) {
            if (this.METEOR_RADIUS + this.SHIP_RADIUS > this.calculateDistance(this.ship.getLayoutX() + 49,
                    this.tabGreyMeteors[i].getLayoutX() + 15, this.ship.getLayoutY() + 37, this.tabGreyMeteors[i].getLayoutY() + 15)) {
                this.setNewElementPosition(this.tabGreyMeteors[i]);

                this.removeLife();
            }
        }
    }

    private void removeLife() {
        this.gamePane.getChildren().remove(this.tabPlayerLifes[this.playerLife]);
        this.playerLife--;

        if (this.playerLife < 0) {
            this.gameStage.close();
            this.gameTimer.stop();
            this.menuStage.show();
        }
    }

    private double calculateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
