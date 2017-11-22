package application;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Main extends Application {
	private static Canvas canvas = new Canvas(350, 270);
	private static GraphicsContext gc = canvas.getGraphicsContext2D();
	private static int hangmanState = 0;
	// private static boolean rotating = false;
	private static Timer timer = new Timer();
	private static int counter = 0;
	private static String currentWord;
	private static StringBuilder currentGuess = new StringBuilder("");
	private static StringBuilder lettersLeft;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 300);
			primaryStage.setTitle("Hangman");

			setLettersLeft();
			
			// Create Hanger
			gc.strokeArc(0, 250, 100, 30, 0, 180, ArcType.OPEN);

			gc.strokeLine(50, 250, 50, 20);

			gc.strokeLine(50, 20, 160, 20);

			root.setCenter(canvas);
			HBox input = new HBox();
			Button display = new Button("Display");
			display.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						drawPerson();
					} catch (Exception exception) {

					}
				}

				public void drawPerson() {

					if (hangmanState > 8) {
						hangmanState = 0;
					}

					switch (hangmanState++) {
					case 0:
						// Rope to head - Changes when swinging
						clearHangman();
						gc.strokeLine(160, 20, 160, 40);
						break;
					case 1:
						gc.strokeOval(140, 40, 40, 40);
						break;
					case 2:
						gc.strokeLine(145, 75, 100, 120);
						break;
					case 3:
						gc.strokeLine(175, 75, 215, 120);
						break;
					case 4:
						gc.strokeLine(160, 80, 160, 160);
						break;
					case 5:
						gc.strokeLine(160, 160, 120, 205);
						break;
					case 6:
						gc.strokeLine(160, 160, 200, 205);
						break;
					case 7:
						TimerTask swingMan = new TimerTask() {

							@Override
							public void run() {
								if (counter > 3) {
									counter = 0;
								}

								switch (counter++) {

								case 0:
									clearHangman();
									gc.strokeLine(160, 20, 150, 40);
									gc.strokeOval(130, 40, 40, 40);
									gc.strokeLine(134, 72, 85, 120);
									gc.strokeLine(165, 75, 190, 130);
									gc.strokeLine(146, 80, 120, 170);
									gc.strokeLine(120, 170, 75, 200);
									gc.strokeLine(120, 170, 165, 210);
									break;
								case 1:
									clearHangman();
									gc.strokeLine(160, 20, 160, 40);
									gc.strokeOval(140, 40, 40, 40);
									gc.strokeLine(145, 75, 100, 120);
									gc.strokeLine(175, 75, 215, 120);
									gc.strokeLine(160, 80, 160, 160);
									gc.strokeLine(160, 160, 120, 205);
									gc.strokeLine(160, 160, 200, 205);
									break;
								case 2:
									clearHangman();
									gc.strokeLine(160, 20, 170, 40);
									gc.strokeOval(155, 40, 40, 40);
									gc.strokeLine(164, 76, 130, 130);
									gc.strokeLine(191, 73, 240, 110);
									gc.strokeLine(175, 80, 205, 160);
									gc.strokeLine(205, 160, 170, 205);
									gc.strokeLine(205, 160, 245, 195);
									break;
								case 3:
									clearHangman();
									gc.strokeLine(160, 20, 160, 40);
									gc.strokeOval(140, 40, 40, 40);
									gc.strokeLine(145, 75, 100, 120);
									gc.strokeLine(175, 75, 215, 120);
									gc.strokeLine(160, 80, 160, 160);
									gc.strokeLine(160, 160, 120, 205);
									gc.strokeLine(160, 160, 200, 205);
									break;
								}
							}

						};

						timer.schedule(swingMan, 0, 220);
						break;
					case 8:
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								timer.cancel();
								timer = new Timer();
								clearHangman();
							}

						});
						break;
					}
				}

				public void clearHangman() {
					gc.clearRect(70, 21, 180, 190);
				}

				public void drawFullBody() {
					gc.strokeLine(160, 20, 160, 40);
					gc.strokeOval(140, 40, 40, 40);
					gc.strokeLine(145, 75, 100, 120);
					gc.strokeLine(175, 75, 215, 120);
					gc.strokeLine(160, 80, 160, 160);
					gc.strokeLine(160, 160, 120, 205);
					gc.strokeLine(160, 160, 200, 205);
				}

			});

			startGame(scene);

			input.getChildren().addAll(new Label("Enter an order: "), display);

			root.setBottom(input);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static void startGame(Scene scene) {
		currentWord = "java";
		for (int i = 0; i < currentWord.length(); i++) {
			currentGuess.append('*');
		}

		gc.fillText("Guess the word: " + currentGuess, 110, 250);
		gc.fillText(lettersLeft.toString(), 250, 15);
//		gc.fillText("Letters Left:", 250, 15);
//		gc.fillText("a b c d e f", 250, 30);
//		gc.fillText("g h i j k l", 250, 45);
//		gc.fillText("m n o p q r", 250, 60);
//		gc.fillText("s t u v w x", 250, 75);
//		gc.fillText("y z", 250, 90);
		

		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println();
				checkPress(event.getCode());
			}
		});
	}

	private static void checkPress(KeyCode code) {
		if (code.getName().length() == 1) {
			char letter = Character.toLowerCase(code.getName().charAt(0));
			for(int i = 0; i < currentWord.length(); i++){
				if(currentWord.charAt(i) == letter){
					currentGuess.setCharAt(i, letter);
				} else{
					System.out.println(currentWord.charAt(i) + " " + letter);
				}
			}
			gc.clearRect(110, 240, 200, 20);
			gc.fillText("Guess the word: " + currentGuess, 110, 250);
		}
	}
	
	private static void setLettersLeft(){
		lettersLeft = new StringBuilder("Letters Left:\n" +
							"a b c d e f\n" + 
							"g h i j k l\n" + 
							"m n o p q r\n" + 
							"s t u v w x\n" + 
							"y z");
	}

}
