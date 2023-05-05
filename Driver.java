package application;

import javafx.application.Application;

import java.io.FileInputStream;
import java.security.*;
import java.util.Base64;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Driver extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			Scene scene = new Scene(root, 500, 350);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
	
		
//		RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
//        keyPairGenerator.writeToFile("./RSA/hospitalPublicKey", keyPairGenerator.getPublicKey().getEncoded());
//        keyPairGenerator.writeToFile("./RSA/hospitalPrivateKey", keyPairGenerator.getPrivateKey().getEncoded());
//        String publicKey = Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded());
//        String privateKey = Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded());
//        System.out.println(publicKey);
//        System.out.println(privateKey);
        
//		FileInputStream privateKeysFile = new FileInputStream("RSA/privateKey");
//		FileInputStream publicKeysFile = new FileInputStream("RSA/publicKey");
//		String publicKey = Base64.getEncoder().encodeToString(publicKeysFile.readAllBytes());
//		String privateKey = Base64.getEncoder().encodeToString(privateKeysFile.readAllBytes());
//		privateKeysFile.close();
//		publicKeysFile.close();
		
//		System.out.println(publicKey);
//		System.out.println(privateKey);
		
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCa7DqHGqjjDeTvf4ac/PDfAC5NV2htc0zuX0L4FSHXbsnns6MoITJLvhCYCZUBFZ/rTfRkR3V9pD+zS3JsL615LK449Kc1pkDhSgsjCK+eSlLxrnDgIXS63O4GMBC4Suo6ew2e/lICxcsjMvPvh32RpDGd850TMspi1skGz55QywIDAQAB";
//		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJrsOocaqOMN5O9/hpz88N8ALk1XaG1zTO5fQvgVIdduyeezoyghMku+EJgJlQEVn+tN9GRHdX2kP7NLcmwvrXksrjj0pzWmQOFKCyMIr55KUvGucOAhdLrc7gYwELhK6jp7DZ7+UgLFyyMy8++HfZGkMZ3znRMyymLWyQbPnlDLAgMBAAECgYBAbi6JMdU7WPP1hxv8/lVO6UxYbhnIK0O7f3LiqidA6Zqe+l7IucWpKorMMG//23Z+7hTN/8olTsIPVzlnNQz3yZKFwaYJXLWAVUKdU+mBlpftRm2pWmg/hDDoxjJ3yeVlRcCm36ZsVYMGVHyP2gfLRaI8Gycko/5Ow3ksBg8HgQJBAN+X8Gf5xCdJXczgwqbEG0ycnzsoPb5mz6w5M8uSTjqZHPZMI/OY15WWSH0ATK5QWPucMFV+gaggRy2BAJB+YkkCQQCxYGCOEc0j6Jay3M7tKg0YrErNMrIXJgU40UDH33AvJoThf1CCmJiN6U2af6e+4ZTNudq+n89tVRl3Kfkt9tpzAkBV0XliTwkskwo/kpzjaS2ZMBwlloJsTAW1cpcgsVz5PL9TAVJjMuy761yTLcRAu3IL0Jz0k4OsRgdLvdG7o+aRAkEAjzOOAGTix3DJ6ZFXBiYC+L5wjYW2PfYWFS7rK/J6MbbZZOxSZX0o/Lk0S91v4i1g405/CZMf9dbUfl5GrBM1fQJAMnXwhKhzY9WjgUOGV5aQFQtQDQKhGM6rdof2sKpmhhjui/DbupXxKP98m+AwCozWiZczzVU0mzw6avTS1wDFPQ==";
//        try {
//        	
//        	String patientData = "name: Patient, Test\\symptoms: cough, fever, headache\\age: 29\\sex: male\\medicines: none";
//        	String patientData2 = "testing123";
//        	String patientData3 = "testing1234";
//        	String patientData4 = "testing1234";
//        	
//            String encryptedString = Base64.getEncoder().encodeToString(RSAUtil.encrypt(patientData, publicKey));
//            String encryptedString2 = Base64.getEncoder().encodeToString(RSAUtil.encrypt(patientData2, publicKey));
//            String encryptedString3 = Base64.getEncoder().encodeToString(RSAUtil.encrypt(patientData3, publicKey));
//            String encryptedString4 = Base64.getEncoder().encodeToString(RSAUtil.encrypt(patientData4, publicKey));
//            System.out.println(encryptedString);
//            System.out.println(encryptedString2);
//            System.out.println(encryptedString3);
//            System.out.println(encryptedString4);
//            System.out.println(encryptedString.length());
//            System.out.println(encryptedString2.length());
//            System.out.println(encryptedString3.length());
//            System.out.println(encryptedString4.length());
//            
//            String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
//            System.out.println(decryptedString);
//        } catch (NoSuchAlgorithmException e) {
//            System.err.println(e.getMessage());
//        }
//		
		
		
		
		
	}
}
