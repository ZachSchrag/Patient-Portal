package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javafx.scene.control.TextInputControl;
import javafx.util.Pair;

public class Hospital {
	
	private String hospitalPublicKey = "";
	private String hospitalPrivateKey = "";
	private HashMap<String, Pair<PublicKey, PrivateKey>> patientKeys;
	private RSAKeyPairGenerator keyPairGenerator;
	
	
	public Hospital() {
		try {
			// retrieve hospital keys
			FileInputStream privateKeyFile = new FileInputStream("RSA/hospitalPrivateKey");
			FileInputStream publicKeyFile = new FileInputStream("RSA/hospitalPublicKey");
			hospitalPrivateKey = Base64.getEncoder().encodeToString(privateKeyFile.readAllBytes());
			hospitalPublicKey = Base64.getEncoder().encodeToString(publicKeyFile.readAllBytes());
			privateKeyFile.close();
			publicKeyFile.close();
			
			// read existing patient keys into patientKeys?
//			Scanner file = new Scanner(new FileReader(new File("RSA/patientKeys")));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String mergeData(HashMap<String, TextInputControl> fields) {
		String ret = "";
		Set<String> keys = fields.keySet();
		Iterator<String> keyIter = keys.iterator();
		
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			String value = fields.get(key).getText();
			ret += key + ":" + value + "~";
		}
		
		return ret;
	}
	
	public void unpackData(HashMap<String, TextInputControl> fields, String encryptedData) {
		try {
			String plainText = RSAUtil.decrypt(encryptedData, hospitalPrivateKey);		
			String[] args = plainText.split("~");
			for (String arg : args) {
				int seperator = arg.indexOf(':');
				String key = arg.substring(0, seperator);
				fields.get(key).setText(arg.substring(seperator + 1));
			}
//			System.out.println(args);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public String getHospitalPublicKey() { return hospitalPublicKey; }
	public String getHospitalPrivateKey() { return hospitalPrivateKey; }
	public HashMap<String, Pair<PublicKey, PrivateKey>> getPatientKeys() { return patientKeys; }
	
	
	
	
	public void createPatientKeys(String patient) {
		
		PublicKey publicKey = null;
		PrivateKey privateKey = null;
		try {
			keyPairGenerator = new RSAKeyPairGenerator();	
			publicKey = keyPairGenerator.getPublicKey();
			privateKey = keyPairGenerator.getPrivateKey();
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		patientKeys.put(patient, new Pair<PublicKey, PrivateKey>(publicKey, privateKey));
	}

}
