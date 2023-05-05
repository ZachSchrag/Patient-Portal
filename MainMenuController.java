package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuController {
	
	// locations for placing buttons depending on role
	private static double NON_PATIENT_ACCESS_X = 210.2;
	private static double NON_PATIENT_ACCESS_Y = 110.0;
	private static double NON_PATIENT_EDIT_X = 220.35;
	private static double NON_PATIENT_EDIT_Y = 175.0;
	private static double NON_PATIENT_UPLOAD_X = 219.7;
	private static double NON_PATIENT_UPLOAD_Y = 240.0;
	private static double NON_PATIENT_REMOVE_X = 204.77;
	private static double NON_PATIENT_REMOVE_Y = 305;
	private static double PATIENT_ACCESS_X = 223.5;
	private static double PATIENT_ACCESS_Y = 125.0;
	private static double PATIENT_EDIT_X = 232.9;
	private static double PATIENT_EDIT_Y = 200.0;
	private static double PATIENT_REMOVE_X = 218.0;
	private static double PATIENT_REMOVE_Y = 275.0;
	
	// Scene attributes
	@FXML private Label welcomeLabel;
	@FXML private Button accessButton;
	@FXML private Button editButton;
	@FXML private Button uploadButton;
	@FXML private Button removeButton;
	
	@FXML private Pane argsPane;
	@FXML private Label argsMethodLabel;
	@FXML private TextField argsFirstName;
	@FXML private TextField argsLastName;
	
	@FXML private Pane queryPane;
	@FXML private TextField queryPaneKey;
	@FXML private TextField queryEncryptedData;
	@FXML private Label queryMethodLabel;
	@FXML private Label queryTaskLabel;
	@FXML private Label queryResultLabel;
	@FXML private Button queryCopyKey;
	@FXML private Button queryCopyData;
	@FXML private Button queryFinished;
	@FXML private Button queryCancel;
	@FXML private Button querySubmit;
	
	@FXML private Pane dataPane;
	@FXML private TextField dataName;
	@FXML private TextField dataAge;
	@FXML private TextField dataSex;
	@FXML private TextField dataMeds;
	@FXML private TextField dataReason;
	@FXML private TextField dataDate;
	@FXML private TextField dataSymptoms;
	@FXML private TextArea dataNotes;
	@FXML private Button dataClose;
	@FXML private Button dataSubmit;
	private HashMap<String, TextInputControl> fields;
	
//	@FXML private Pane removePane;
//	@FXML private Pane submitPane;
	
	private Alert errorAlert;
	private Hospital hospital;

	
	public void initNonPatient() {
		welcomeLabel.setText("Welcome, " + LoginScreenController.role + " " + LoginScreenController.lastName);
		accessButton.setLayoutX(NON_PATIENT_ACCESS_X);
		accessButton.setLayoutY(NON_PATIENT_ACCESS_Y);
		accessButton.setText("Access Existing Record");
		editButton.setLayoutX(NON_PATIENT_EDIT_X);
		editButton.setLayoutY(NON_PATIENT_EDIT_Y);
		editButton.setText("Edit Existing Record");
		uploadButton.setLayoutX(NON_PATIENT_UPLOAD_X);
		uploadButton.setLayoutY(NON_PATIENT_UPLOAD_Y);
		uploadButton.setText("Upload New Record");
		removeButton.setLayoutX(NON_PATIENT_REMOVE_X);
		removeButton.setLayoutY(NON_PATIENT_REMOVE_Y);
		removeButton.setText("Remove Existing Record");
	}
	
	public void initPatient() {
		welcomeLabel.setText("Welcome, " + LoginScreenController.lastName + ", " + LoginScreenController.firstName);
		accessButton.setLayoutX(PATIENT_ACCESS_X);
		accessButton.setLayoutY(PATIENT_ACCESS_Y);
		accessButton.setText("Access My Records");
		editButton.setLayoutX(PATIENT_EDIT_X);
		editButton.setLayoutY(PATIENT_EDIT_Y);
		editButton.setText("Edit My Records");
		uploadButton.setVisible(false);
		removeButton.setLayoutX(PATIENT_REMOVE_X);
		removeButton.setLayoutY(PATIENT_REMOVE_Y);
		removeButton.setText("Remove My Records");
	}
	
	public void initFields() {
		fields = new HashMap<String, TextInputControl>();
		fields.put("name", dataName);
		fields.put("age", dataAge);
		fields.put("sex", dataSex);
		fields.put("meds", dataMeds);
		fields.put("reason", dataReason);
		fields.put("date", dataDate);
		fields.put("symptoms", dataSymptoms);
		fields.put("notes", dataNotes);
	}
	
	public void initialize() {
		hospital = new Hospital(); // holds keys
		
		// listeners to center text
		welcomeLabel.widthProperty().addListener((observable, oldValue, newValue) -> {
			   welcomeLabel.setLayoutX((600 - newValue.doubleValue()) / 2.0);
			});
		
		argsMethodLabel.widthProperty().addListener((observable, oldValue, newValue) -> {
			   argsMethodLabel.setLayoutX((300 - newValue.doubleValue()) / 2.0);
			});
		
		queryMethodLabel.widthProperty().addListener((observable, oldValue, newValue) -> {
			   queryMethodLabel.setLayoutX((300 - newValue.doubleValue()) / 2.0);
			});
		
		queryTaskLabel.widthProperty().addListener((observable, oldValue, newValue) -> {
			   queryTaskLabel.setLayoutX((300 - newValue.doubleValue()) / 2.0);
			});
		
		argsPane.setVisible(false);
		queryPane.setVisible(false);
		dataPane.setVisible(false);
		
		// load buttons based on role
		if (LoginScreenController.role != "Patient") { initNonPatient(); }
		else initPatient();
		initFields();
	}
	
	
	
	public void updateArgs(String method) {
		argsMethodLabel.setText(method);
		argsPane.setVisible(true);
		argsFirstName.requestFocus();
	}
	
	public void clearArgs() {
		argsFirstName.clear();
		argsLastName.clear();
		argsPane.setVisible(false);
	}
	public void onArgsCancel(ActionEvent e) { clearArgs(); }
	
	
	public void onArgsSubmit(ActionEvent e) {
		errorAlert = new Alert(AlertType.ERROR);
		if (argsFirstName.getText() == "") {
			errorAlert.setHeaderText("First Name Missing");
			errorAlert.setContentText("Please input a first name");
		}
		else if (argsLastName.getText() == "") {
			errorAlert.setHeaderText("Last Name Missing");
			errorAlert.setContentText("Please input a last name");
		}
		if (errorAlert.getContentText() != "") {errorAlert.show(); return; }
		
		
		String methodLabel = argsMethodLabel.getText();
		String method = methodLabel.substring(0, methodLabel.indexOf(' '));
		switch(method) {
		case "Access":
			updateQuery(e, "Access");
			break;
		case "Edit":
			updateQuery(e, "Edit");
			break;
		case "Upload":
			updateQuery(e, "Upload");
			
			break;
		case "Remove":
			updateQuery(e, "Remove");
			break;
		// is only ever these 4 cases
		}
	}
	
	
	
	public String getFirstName() {
		if (LoginScreenController.role == "Patient") return LoginScreenController.firstName;
		return argsFirstName.getText();
	}
	
	public String getLastName() {
		if (LoginScreenController.role == "Patient") return LoginScreenController.lastName;
		return argsLastName.getText();
	}
	
	// HAVE THE CONTRACT DO THIS? 
	public String getPatientID() {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest((getLastName() + ", " + getFirstName()).getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	
	public void displayRemoveInstructions(String patientID) {
		queryEncryptedData.setVisible(false);
		queryResultLabel.setVisible(false);
		queryCancel.setVisible(false);
		queryFinished.setVisible(true);
		queryFinished.setLayoutY(160);
		querySubmit.setVisible(false);
		queryCopyData.setVisible(false);
		queryTaskLabel.setText("Please call removeRecord with the key:");
		queryPaneKey.setText(patientID);
		
	}
	
	public void displaySubmitInstructions(String patientID, String encryptedData) {
		queryEncryptedData.setVisible(true);
		queryResultLabel.setVisible(true);
		queryCancel.setVisible(false);
		queryFinished.setVisible(true);
		querySubmit.setVisible(false);
		queryFinished.setLayoutY(220);
		queryTaskLabel.setText("Query the blockchain using key:");
		queryResultLabel.setText("and value:");
		queryPaneKey.setText(patientID);
		queryEncryptedData.setText(encryptedData);
		queryEncryptedData.setEditable(false);
		queryPane.setVisible(true);
	}
	
	
	
	public void updateDataPaneFields(boolean to) {
		dataName.setEditable(to);
		dataAge.setEditable(to);
		dataSex.setEditable(to);
		dataMeds.setEditable(to);
		dataReason.setEditable(to);
		dataDate.setEditable(to);
		dataSymptoms.setEditable(to);
		dataNotes.setEditable(to);
	}
	
	public void updateDataPane(ActionEvent e, String method, String patientID) {
		
		switch(method) {
		case "Remove":
			displayRemoveInstructions(patientID);
			return;
		case "Upload":
			dataSubmit.setVisible(true);
			updateDataPaneFields(true);
			break;
		case "Access":
			dataSubmit.setVisible(false);
			updateDataPaneFields(false);
			hospital.unpackData(fields, patientID);
			break;
		case "Edit":
			dataSubmit.setVisible(true);
			updateDataPaneFields(true);
			break;
		}
		dataPane.setVisible(true);
		updateWindow(e, 800, 630);
		
	}
	
	
	public void updateWindow(ActionEvent e, int width, int height) {
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		window.setWidth(width);
		window.setHeight(height);	
	}
	
	
	public void clearDataPane() {
		dataName.clear();
		dataAge.clear();
		dataSex.clear();
		dataMeds.clear();
		dataReason.clear();
		dataDate.clear();
		dataSymptoms.clear();
		dataNotes.clear();
	}
	
	public void onDataClose(ActionEvent e) {
		dataPane.setVisible(false);
		clearDataPane();
		resetQuery();
		updateWindow(e, 600, 430);
	}
	
	public void onDataSubmit(ActionEvent e) {
		String patientID = getPatientID();
		String plainText = hospital.mergeData(fields);
		String encryptedText = "";
		System.out.println(plainText);
		try {
			encryptedText = Base64.getEncoder().encodeToString(RSAUtil.encrypt(plainText, hospital.getHospitalPublicKey()));			
		}
		catch (Exception except) {
			except.printStackTrace();
		}

		displaySubmitInstructions(patientID, encryptedText);
		dataPane.setVisible(false);
		updateWindow(e, 600, 430);
	}
	
	
	
	public void updateQuery(ActionEvent e, String method) {
		String patientID = getPatientID();
		if (method == "Upload") {
			updateDataPane(e, method, patientID);
		}
		else if (method == "Remove") {
			displayRemoveInstructions(patientID);
		}
		
		queryMethodLabel.setText(method);
		queryPane.setVisible(true);
		queryPaneKey.setText(patientID);
		queryCopyKey.requestFocus();
		
		
	}
	
	public void onQueryCopyKey(ActionEvent e) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(queryPaneKey.getText());
		clipboard.setContent(content);
	}
	
	public void onQueryCopyData(ActionEvent e) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(queryEncryptedData.getText());
		clipboard.setContent(content);
	} 
	
	public void clearQuery() {
		queryPaneKey.clear();
		queryEncryptedData.clear();
		queryPane.setVisible(false);
	}
	public void onQueryCancel(ActionEvent e) { clearQuery(); }
	
	public void resetQuery() {
		queryTaskLabel.setText("Please Query the blockchain with:");
		queryResultLabel.setText("Paste result from blockchain here:");
		queryCancel.setVisible(true);
		querySubmit.setVisible(true);
		queryResultLabel.setVisible(true);
		queryEncryptedData.setVisible(true);
		queryFinished.setVisible(false);
		queryEncryptedData.setEditable(true);
		clearQuery();
		
	}
	public void onQueryFinished(ActionEvent e) { 
		resetQuery();
		dataPane.setVisible(false);
	}
	
	public void onQuerySubmit(ActionEvent e) {
		errorAlert = new Alert(AlertType.ERROR);
		String encryptedData = queryEncryptedData.getText();
		if (encryptedData.length() < 172) {
			errorAlert.setHeaderText("Invalid input");
			errorAlert.setContentText("The entered string does not match the length requirement. Please try again.");
		}
		
		if (errorAlert.getContentText() != "") errorAlert.show();
		if (false) {}
		else {
			clearQuery();
			clearArgs();
			updateWindow(e, 815, 630);
			updateDataPane(e, queryMethodLabel.getText(), encryptedData);
			// decrypt and display data 
			
		}
	}
	
	
	
	
	
	
	
	public void onAccess(ActionEvent e) {
		
		if (LoginScreenController.role != "Patient") { updateArgs("Access Records For:"); }
		
		else updateQuery(e, "Access");
		
	}
	
	public void onEdit(ActionEvent e) {
		
		if (LoginScreenController.role != "Patient") { updateArgs("Edit Records For:"); }
		
		else updateQuery(e, "Edit");
		
	}
	
	public void onRemove(ActionEvent e) {
		
		if (LoginScreenController.role != "Patient") { updateArgs("Remove Records For:"); }
		
		else updateQuery(e, "Remove");
		
	}

	
	public void onUpload(ActionEvent e) {
		if (LoginScreenController.role != "Patient") { updateArgs("Upload Records For:"); }
		
		else updateQuery(e, "Upload");
	}
	
	

	
	public void onSignOut(ActionEvent event) {
		try { LoginScreenController.loadScene(getClass().getResource("LoginScreen.fxml"), 500, 350, event, ""); }
		catch (IOException e) { e.printStackTrace(); }
//		updateWindow(event, 600, 400);
		
	}

}
