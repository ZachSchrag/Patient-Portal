package application;

import java.io.IOException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginScreenController {
	
	@FXML public TextField firstNameTextField;
	@FXML public TextField lastNameTextField;
	@FXML public ChoiceBox<String> roleChoiceBox;
	@FXML private Pane passwordPane;
	@FXML private PasswordField passwordField;
	@FXML private Label passwordLabel;
	private Alert errorAlert;
	private final static String DOCTOR_PASSWORD = "doctor"; // would be real passwords in practice, these are for testing
	private final static String NURSE_PASSWORD = "nurse";
	public static String firstName;
	public static String lastName;
	public static String role;
	
	public void initialize() {
		// set up choice box
		roleChoiceBox.setValue("Patient");
		roleChoiceBox.setItems(FXCollections.observableArrayList("Patient", "Nurse", "Doctor"));
		roleChoiceBox.setTooltip(new Tooltip("Select a role"));
		
		// add listener to center password prompt text
		passwordLabel.widthProperty().addListener((observable, oldValue, newValue) -> {
			   passwordLabel.setLayoutX((200 - newValue.doubleValue()) / 2.0);
			});
		passwordPane.setVisible(false);
		
		firstName = "";
		lastName = "";
		role = "";
	}
	
	
	
	
	public static void loadScene(URL fxml, int width, int height, ActionEvent e, String _role) throws IOException {
		role = _role;
		Parent parent = FXMLLoader.load(fxml);
		Scene scene = new Scene(parent, width, height); 
		
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	public void updatePasswordPane(String who) {
		passwordLabel.setText("Please enter the " + who + " password");
		passwordPane.setVisible(true);
		passwordField.requestFocus();
	}
	
	public void onLogin(ActionEvent event) {
		errorAlert = new Alert(AlertType.ERROR);
		if (firstNameTextField.getText() == "") {
			errorAlert.setHeaderText("First Name Missing");
			errorAlert.setContentText("Please input a first name");
		}
		else if (lastNameTextField.getText() == "") {
			errorAlert.setHeaderText("Last Name Missing");
			errorAlert.setContentText("Please input a last name");
		}
		if (errorAlert.getContentText() != "") {errorAlert.show(); return; }
		
		// save name
		firstName = firstNameTextField.getText();
		lastName = lastNameTextField.getText();
		if (roleChoiceBox.getValue() == "Nurse") {
			updatePasswordPane("nurse");
		}
		else if (roleChoiceBox.getValue() == "Doctor") {
			updatePasswordPane("doctor");
		}
		
		else {
			try {
				loadScene(getClass().getResource("MainMenu.fxml"), 600, 400, event, "Patient");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void onCancelPassword(ActionEvent e) {
		passwordPane.setVisible(false);
		roleChoiceBox.requestFocus();
	}
	
	
	public void onSubmitPassword(ActionEvent e) throws IOException {
		
//		System.out.println(roleChoiceBox.getValue());
		if (roleChoiceBox.getValue() == "Nurse" && passwordField.getText().compareTo(NURSE_PASSWORD) == 0) {
			passwordPane.setVisible(false);
			loadScene(getClass().getResource("MainMenu.fxml"), 600, 400, e, "Nurse");
		}
		
		else if (roleChoiceBox.getValue() == "Doctor" && passwordField.getText().compareTo(DOCTOR_PASSWORD) == 0) {
			passwordPane.setVisible(false);
			loadScene(getClass().getResource("MainMenu.fxml"), 600, 400, e, "Dr.");
		}
		
		else {
			errorAlert.setHeaderText("Password is incorrect");
			errorAlert.setContentText("Please try again");
			errorAlert.show();
		}
	}
	
}
