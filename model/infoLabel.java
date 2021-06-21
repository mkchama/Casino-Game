package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.text.Font;

public class infoLabel extends Label {

	
	public final static String FONT_PATH = "model/resources/kenvector_future.ttf";
	
	public final static String BACKGROUND_IMAGE = "view/resources/grey_button02.png";
	
	/**
	 * Initializes the info label to take in
	 * @param text
	 * @param width
	 * @param height
	 * @param font
	 * @author mkchama
	 */
	
	public infoLabel(String text, int width, int height, int font) {
		
		setPrefWidth(width);
		setPrefHeight(height);
		setText(text);
		setWrapText(true);
		setLabelFont(font);
		setAlignment(Pos.CENTER);
		
		BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, width, height, false, true), BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(backgroundImage));
		
		
	}
	
	/**
	 * Sets the font of the label
	 * @param font
	 * @author mkchama
	 */
	
	private void setLabelFont(int font) {
		
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), font));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", font));
		}
		
	}
	
}
