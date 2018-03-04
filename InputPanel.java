/* InputPanel.java
 * Responsible for GUI that allows users to input the raw data and labels
 * and add or subtract new labels as they wish
 * The data will be stored in DataManager.java
 */

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class InputPanel extends Application {
	int i = 0;
	BorderPane border = new BorderPane();
	GridPane root = new GridPane();
	TextField label[] = new TextField[6];
	TextField tally[] = new TextField[6];
	public DataManager dataManager = new DataManager();
	DisplayTriangles display;

	@Override
	public void start(Stage primaryStage) {
		root.setHgap(100);
		root.setVgap(5);
		root.setPadding(new Insets(10, 70, 20, 20));

		Label instructions2 = new Label();
		instructions2.setWrapText(true);
		instructions2.setText("Click the \"+\" button to add another data point and the \"-\" button to remove a data point. Five is the maximum amount you may add.");

		Label instructions3 = new Label();
		instructions3.setWrapText(true);
		instructions3.setText("Make sure your data is an integer and none of the fields are left blank.");

		VBox vb = new VBox(5);
		vb.setPadding(new Insets(30, 70, 0, 70));

		vb.getChildren().addAll(instructions2, instructions3);

	    Label labelprompt = new Label("Label");
	    root.add(labelprompt, 1, 0);

	    Label tallyprompt = new Label("Data");
	    root.add(tallyprompt, 2, 0);

	    click();

	    HBox hb = new HBox(80);
	    hb.setPadding(new Insets(20, 50, 50, 50));
	    hb.setAlignment(Pos.CENTER);

	    Button btn = new Button("+");
	    btn.setOnAction(e -> {
	    	click();    
	    });

	    Button rmv = new Button("-");
	    rmv.setOnAction(e -> removebtn());

	    Button submit = new Button("Submit");
	    GridPane.setHalignment(submit, HPos.RIGHT);
	    submit.setOnAction(e -> {
	    	if (contentExists() && allValues()) {
	    		if (display != null) {
	    			display.closeWindow();
	    		}
	    		dataManager.clear();
    			for (int j = 0; j < i; j++) {
    				Data data = new Data(label[j].getText(), Integer.parseInt(tally[j].getText()));
    				dataManager.addData(data);
    			}
	    		display = new DisplayTriangles(dataManager);
	    	}
	    });

	    hb.getChildren().addAll(btn, rmv, submit);

	    border.setBottom(hb);
	    border.setCenter(root);
	    border.setTop(vb);

	    // root.setGridLinesVisible(true);

	    Scene scene = new Scene(border, 1000, 800);
	    primaryStage.setTitle("Triangles");
	    primaryStage.setScene(scene);
	    scene.getStylesheets().add("button-styles.css");
	    primaryStage.show();

	}

	public void click() {
		if (i == 5) {
			AlertBox outOfBounds = new AlertBox();
			outOfBounds.alert("Error", "You cannot add more than five data points.");
			return;
		}

		label[i] = new TextField();

	   	root.add(label[i], 1, i + 1);
	   	addTextLimiter(label[i], 15);

	    tally[i] = new TextField();
	    root.add(tally[i], 2, i + 1);

	    i++;
	}

	public void removebtn() {
		if (i <= 1) {
			AlertBox newalert = new AlertBox();
			newalert.alert("Error", "You cannot remove that text field.");
			return;
		}

		root.getChildren().remove(label[i - 1]);
		root.getChildren().remove(tally[i - 1]);

		i--;
	}

	private boolean isInt(String value) {
		try {
			for (int j = 0; j < i; j++) {
				int number = Integer.parseInt(tally[j].getText());
			}
			return true;
		} catch (NumberFormatException e) {
			AlertBox alert = new AlertBox();
			alert.alert("Error", "Data input must be an integer.");
			return false;
		}
	}

	private boolean allValues() {
		for (int j = 0; j < i; j++) {
			if (!isInt(tally[j].getText())) {
				return false;
			}
		}
		return true;
	}

	public class AlertBox {
		private void alert(String title, String message) {
			Stage popup = new Stage();

			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle(title);

			Label errorMessage = new Label(message);
			errorMessage.setWrapText(true);

			Button closebtn = new Button("Close");
			closebtn.setOnAction(e -> popup.close());

			VBox errorlayout = new VBox(10);
			errorlayout.setPadding(new Insets(20, 40, 20, 20));

			errorlayout.getChildren().addAll(errorMessage, closebtn);
			errorlayout.setAlignment(Pos.CENTER);

			Scene error = new Scene(errorlayout, 500, 200);
			error.getStylesheets().add("error-message.css");
			popup.setScene(error);
			popup.showAndWait();
		}
	}

	private boolean contentExists() {
		for (int j = 0; j < i; j++) {
			if (tally[j].getText().isEmpty()) {
				AlertBox empty = new AlertBox();
				empty.alert("Error", "Please fill out all fields.");
				return false;
			}
			if (label[j].getText().isEmpty()) {
				AlertBox empty = new AlertBox();
				empty.alert("Error", "Please fill out all fields.");
				return false;
			}
		}
		return true;
	}

	public static void addTextLimiter(final TextField tf, final int maxLength) {
	    tf.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	            if (tf.getText().length() > maxLength) {
	                String s = tf.getText().substring(0, maxLength);
	                tf.setText(s);
	            }
	        }
	    });
	}

	public static void main (String[] args) {
		launch(args);
	}
}