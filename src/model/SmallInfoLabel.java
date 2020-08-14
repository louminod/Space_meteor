package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class SmallInfoLabel extends Label {

	private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	private final String BACKGROUND_IMAGE = "view/resources/buttonBlue.png";

	public SmallInfoLabel(String text) {
		setPrefWidth(130);
		setPrefHeight(50);

		BackgroundImage image = new BackgroundImage(new Image(this.BACKGROUND_IMAGE, 130, 50, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(image));
		setAlignment(Pos.CENTER);
		setPadding(new Insets(10, 10, 10, 10));
		setText(text);
		this.setLabelFont();
	}
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 15));
		} catch (FileNotFoundException exception) {
			setFont(Font.font("Verdana", 15));
		}
	}
}
