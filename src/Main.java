import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * @author Juhani
 * @GridPane Generate Gui with custom settings and text, textfields and button with in it 
 * 
 */
public class Main extends Application {
	final static GridPane grid = new GridPane();
	static Text givenInput, convertedValue, invalidRomanValue;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		// Set Gui size
		final int height = 400;
		final int width = 800;
		
		//Creating grid wich make easier add text, textfields, buttons and etc.
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25));
		
		Text title = new Text("Roman number converter");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(title, 0, 0, 2, 1);
		
		//Declare Label for the input box and Textfield which can takes the given data
		Label romanNumberTitle = new Label("Give Roman number with between 1 and 1000: ");
		grid.add(romanNumberTitle, 0, 1);
		TextField romanNumberInput = new TextField();
		grid.add(romanNumberInput, 0, 2);
		
		//Declare Button
		Button btn = new Button("Convert");
		grid.add(btn, 1, 2);
		
		//Declaring text, which tells user given input, converted values and errors
		givenInput = new Text("Given Roman value: " + romanNumberInput.getText());
		givenInput.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(givenInput, 0, 4);
		
		convertedValue = new Text("Converted value: ");
		convertedValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(convertedValue, 0, 5);
		
		invalidRomanValue = new Text();
		invalidRomanValue.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
		invalidRomanValue.setFill(Color.RED);
		grid.add(invalidRomanValue, 0, 6);
		
		
		// Onclick eventlistener, which activates after user pressed button
		btn.setOnAction( e-> {
			invalidRomanValue.setText("");
			//Calls convert function and get userinput data and makes given input toUpperCase, if user give values as lowercase
			convertValue(romanNumberInput.getText().toUpperCase());
		});
			
		//Generate Gui scene
		Scene scene = new Scene(grid, width, height);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Roman number converter");
		primaryStage.show();
	}
	
	public static void convertValue(String inputValue) {
		
		//Declaring variables and HashMap to get Roman values as integer value in converting function
		HashMap<String, Integer> romanNumbers = new HashMap<String, Integer>();
		char[] inputStringToChar;
		String reverse;
		romanNumbers.put("I", 1);
		romanNumbers.put("V", 5);
		romanNumbers.put("X", 10);
		romanNumbers.put("L", 50);
		romanNumbers.put("C", 100);
		romanNumbers.put("D", 500);
		romanNumbers.put("M", 1000);
		
		
		//Trying converting given value and catches declared errors if in given input has invalid data
		try {
			//Calls CheckValidRomanValue with given value
			CheckValidRomanValue(inputValue);
			//Convert string to char array list, to check after if given value is higher than 100
			inputStringToChar = inputValue.toCharArray();
			chekMaxRomanValue(inputStringToChar);
			
			//Reversing inputData and converting string to char array which makes converting easier
			// Reversing values because its easier to convert odd Roman Values
			reverse = new StringBuffer(inputValue).reverse().toString();
			inputStringToChar = reverse.toCharArray();
			convertValues(inputStringToChar, romanNumbers);
			
			givenInput.setText("Given Roman value: " + inputValue);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	// Checks given Roman values and if it is invalid it throws exception and gives user information about invalid data
	public static void CheckValidRomanValue(String value) throws Exception {
		if (!value.matches("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
			invalidRomanValue.setText("Given value is invalid!");
			throw new Exception("Given value is invalid");
		}
	}
	
	// Checks if given Roman value is higher than 1000, then it throws exception and gives user error about given input
	public static void chekMaxRomanValue(char[] values) throws Exception {
		if (values.length != 0 && values[0] == 'M' && values.length > 1) {
			invalidRomanValue.setText("Given Roman value is higher than 1000");
			throw new Exception("Given value cannot be higher than 1000");
		}
	}
	
	
	/*
	 * Convert input Roman data to the normal integers
	 */
	public static void convertValues(char[] values, HashMap<String, Integer> romanNumbers) {
		int convValue = 0;
		int prevValue = 0;
		
		for (char i : values) {	
			
			int currentValue = romanNumbers.get(String.valueOf(i));
			
			if (currentValue >= prevValue) {
				convValue += currentValue;
				prevValue = currentValue;
			} 
			else if (currentValue < prevValue) {
				convValue -= currentValue;
				prevValue = currentValue;
			}
		}
		convertedValue.setText("Converted value: " + convValue);
	}
}