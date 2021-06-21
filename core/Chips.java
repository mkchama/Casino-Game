package core;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Chips {
	
	private String chipWhite = new String("/chips/chip_white_one.png");
	private String chipBlue = new String("/chips/chip_blue_ten.png");
	private String chipGreen = new String("/chips/chip_green_twentyfive.png");
	private String chipRed = new String("/chips/chip_red_five.png");
	private String chipBlack = new String("/chips/chip_black_onehundred.png");
	private StackPane chipView;
	private ImageView frontView;
	private ImageView backView;
	
	public enum Chip {
		White(1), Red(5), Blue(10), Green(25), Black(100);
		
		private int chipValue;
		
		Chip(int chipValue) {
			this.chipValue = chipValue;
		}
		
		 public int getChipValue()
	        {
	            return chipValue;
	        }
	}
	
	
	/**
	 * Takes a Chip then creates a stackpane, and image based on the chip
	 * @param chip
	 */
	
	public Chips(Chip chip) {
		if (chip == Chip.White) {
			this.frontView = new ImageView(chipWhite);
			this.backView = new ImageView(chipWhite);
			this.chipView = new StackPane();
			this.chipView.getChildren().addAll(frontView, backView);
		}
		else if ( chip == Chip.Red) {
			this.frontView = new ImageView(chipRed);
			this.backView = new ImageView(chipRed);
			this.chipView = new StackPane();
			this.chipView.getChildren().addAll(frontView, backView);
		}
		else if ( chip == Chip.Blue ) {
			this.frontView = new ImageView(chipBlue);
			this.backView = new ImageView(chipBlue);
			chipView = new StackPane();
			this.chipView.getChildren().addAll(frontView, backView);
		}
		else if ( chip == Chip.Green ) {
			this.frontView = new ImageView(chipGreen);
			this.backView = new ImageView(chipGreen);
			this.chipView = new StackPane();
			this.chipView.getChildren().addAll(frontView, backView);
		}
		else if ( chip == Chip.Black ) {
			this.frontView = new ImageView(chipBlack);
			this.backView = new ImageView(chipBlack);
			this.chipView = new StackPane();
			this.chipView.getChildren().addAll(frontView, backView);
		}
		else {
			this.frontView = new ImageView(chipBlack);
			this.backView = new ImageView(chipBlack);
			this.chipView = new StackPane();
			this.chipView.getChildren().addAll(frontView, backView);
		}
	}
	
	public Chips(Chips toCopy) {
		this.backView = toCopy.backView;
		this.frontView = toCopy.frontView;
		this.chipView = toCopy.chipView;
	}
	
	
	/**
	 * Returns the stackpane of the chip
	 * @return
	 */
	
	public StackPane getChipView() {
		return chipView;
	}
}
