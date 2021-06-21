package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;


public class BlackJackButton extends Button {

	
	private final String FONT_PATH = "model/resources/kenvector_future.ttf";
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/grey_button01.png');";
	private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/grey_button00.png');";
	
	
	/**
	 * Initializes the size, height and font size of the button
	 * @param text
	 * @param fontSize
	 * @param prefHeigh
	 * @param prefWidth
	 * @author mkchama
	 */
	public BlackJackButton(String text, int fontSize, int prefHeight, int prefWidth) {
		setText(text);
		setButtonFont(fontSize);
		setPrefWidth(prefWidth);
		setPrefHeight(prefHeight);
		setStyle(BUTTON_FREE_STYLE);
		initializeButtonListeners(prefHeight);
		
	}
	
	/**
	 * Sets the button font and catches an exception
	 * @param fontSize
	 * @author mkchama
	 */
	
	private void setButtonFont(int fontSize) {
		
		try {
		
		setFont(Font.loadFont(new FileInputStream(FONT_PATH), fontSize));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", fontSize));
		}
		
	}
	
	/**
	 * Animates button press using two different images
	 * @param prefHeight
	 * @author mkchama
	 */
	
	private void setButtonPressedStyle(int prefHeight) {
		setStyle(BUTTON_PRESSED_STYLE);
		setPrefHeight(prefHeight - 4);
		setLayoutY(getLayoutY()+4);
	}
	
	private void setButtonReleasedStyle(int prefHeight) {
		setStyle(BUTTON_FREE_STYLE);
		setPrefHeight(prefHeight);
		setLayoutY(getLayoutY()-4);
	}
	
	
	/**
	 * Initializes the button listener for whether it was hovered on and off the button, 
	 * pressed and released
	 * @param prefHeight
	 * @author mkchama
	 */
	
	private void initializeButtonListeners(int prefHeight) {
		
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle(prefHeight);
				}
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle(prefHeight);
				}
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
			}
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
				
			}
		});
		
	}
}
