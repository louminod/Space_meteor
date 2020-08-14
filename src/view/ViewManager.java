package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

public class ViewManager {

	private final int HEIGHT = 768;
	private final int WIDTH = 1024;

	private final int MENU_BUTTON_START_X = 100;
	private final int MENU_BUTTON_START_Y = 150;

	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;

	private List<SpaceRunnerButton> lstButtonsMenu;
	private List<ShipPicker> lstShips;
	
	private SpaceRunnerSubScene creditsSubScene;
	private SpaceRunnerSubScene helpSubScene;
	private SpaceRunnerSubScene scoreSubScene;
	private SpaceRunnerSubScene shipChooserSubScene;
	
	private SpaceRunnerSubScene sceneToHide;
	
	private SHIP choosenShip;

	public ViewManager() {
		this.lstButtonsMenu = new ArrayList<>();

		this.mainPane = new AnchorPane();
		this.mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		this.mainStage = new Stage();
		this.mainStage.setScene(mainScene);

		this.createSubScenes();
		this.createButton();
		this.createBackground();
		this.createLogo();
	}
	
	private void showSubScene(SpaceRunnerSubScene subScene) {
		if (this.sceneToHide != null) {
			this.sceneToHide.moveSubScene();
		}
		
		subScene.moveSubScene();
		this.sceneToHide = subScene;
	}
	
	private void createSubScenes() {
		this.creditsSubScene = new SpaceRunnerSubScene();
		this.mainPane.getChildren().add(this.creditsSubScene);
		
		this.helpSubScene = new SpaceRunnerSubScene();
		this.mainPane.getChildren().add(this.helpSubScene);
		
		this.scoreSubScene = new SpaceRunnerSubScene();
		this.mainPane.getChildren().add(this.scoreSubScene);
		
		createShipChoosenSubScene();
	}

	private void createShipChoosenSubScene() {
		this.shipChooserSubScene = new SpaceRunnerSubScene();
		this.mainPane.getChildren().add(this.shipChooserSubScene);
		
		InfoLabel chooseSheepLabel = new InfoLabel("CHOOSE YOUR SHIP");
		chooseSheepLabel.setLayoutX(110);
		chooseSheepLabel.setLayoutY(25);
		this.shipChooserSubScene.getPane().getChildren().add(chooseSheepLabel);
		this.shipChooserSubScene.getPane().getChildren().add(this.createShipToChoose());
		this.shipChooserSubScene.getPane().getChildren().add(this.createButtonStart());
	}
	
	private HBox createShipToChoose() {
		HBox hBox = new HBox();
		hBox.setSpacing(20);
		
		this.lstShips = new ArrayList<>();
		
		for (SHIP ship : SHIP.values()) {
			ShipPicker shipToPick = new ShipPicker(ship);
			this.lstShips.add(shipToPick);
			hBox.getChildren().add(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					for(ShipPicker ship: lstShips) {
						ship.setIsCircleChoosen(false);
					}
					shipToPick.setIsCircleChoosen(true);
					choosenShip = shipToPick.getShip();
				}
			});
		}
		
		hBox.setLayoutX(300 - (118 * 2));
		hBox.setLayoutY(100);
		
		return hBox;
	}
	
	private SpaceRunnerButton createButtonStart() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("START");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(choosenShip != null) {
					GameViewManager gameManager = new GameViewManager();
					gameManager.createNewGame(mainStage, choosenShip);
				}
			}
		});
		
		return startButton;
	}

	public Stage getMainStage() {
		return this.mainStage;
	}

	private void addMenuButton(SpaceRunnerButton button) {
		button.setLayoutX(this.MENU_BUTTON_START_X);
		button.setLayoutY(this.MENU_BUTTON_START_Y + (this.lstButtonsMenu.size() * 100));
		this.lstButtonsMenu.add(button);
		this.mainPane.getChildren().add(button);
	}

	private void createButton() {
		this.createStartButton();
		this.createScoresButton();
		this.createHelpButton();
		this.createCreditsButton();
		this.createExitButton();
	}

	private void createStartButton() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
		this.addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubScene(shipChooserSubScene);
			}
		});
	}

	private void createScoresButton() {
		SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
		this.addMenuButton(scoresButton);
		
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubScene(scoreSubScene);
			}
		});
	}

	private void createHelpButton() {
		SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
		this.addMenuButton(helpButton);
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubScene);
			}
		});
	}

	private void createCreditsButton() {
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
		this.addMenuButton(creditsButton);
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubScene);
			}
		});
	}

	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
		this.addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}
		});
	}

	private void createBackground() {
		Image backgroundImage = new Image("view/resources/purple.png", 256, 256, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		this.mainPane.setBackground(new Background(background));
	}

	private void createLogo() {
		ImageView logo = new ImageView("view/resources/logo.png");
		logo.setLayoutX(170);
		logo.setLayoutY(0);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
		});
		
		this.mainPane.getChildren().add(logo);
	}
}
