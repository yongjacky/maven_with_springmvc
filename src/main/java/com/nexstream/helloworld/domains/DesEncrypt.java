package com.nexstream.helloworld.domains;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

public class DesEncrypt {
	private static Cipher encryptCipher;
    private static Cipher decryptCipher;
    
    /*private SecretKey sKey;
    private IvParameterSpec ivParam;
	
	public SecretKey getsKey() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		
	   String desKey = "0123456789abcdef0123456789abcdef0123456789abcdef"; // value from user
       byte[] keyBytes = DatatypeConverter.parseHexBinary(desKey);
       //System.out.println((int)keyBytes.length);
       
       
       //generate key for cipher init 
       SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
       sKey = factory.generateSecret(new DESedeKeySpec(keyBytes));

		
		return sKey;
	}


	public void setsKey(SecretKey sKey) {
		this.sKey = sKey;
	}


	public IvParameterSpec getIvParam() {
	   //generate iv for cipher init
	   SecureRandom sr = new SecureRandom(); //create new secure random
	   byte [] iv = new byte[8]; //create an array of 8 bytes 
	   sr.nextBytes(iv); //create random bytes to be used for the IV (?) Not too sure.
	   IvParameterSpec dps = new IvParameterSpec(iv); //creating the IV 
		
		return ivParam;
	}


	public void setIvParam(IvParameterSpec ivParam) {
	   
	       
		this.ivParam = ivParam;
	}*/


	public void initDes() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, 
	NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
	  
		String desKey = "0123456789abcdef0123456789abcdef0123456789abcdef"; // value from user
       byte[] keyBytes = DatatypeConverter.parseHexBinary(desKey);
      
       //System.out.println((int)keyBytes.length);
      
       //generate key for cipher init 
       SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
       SecretKey key = factory.generateSecret(new DESedeKeySpec(keyBytes));
       
       //generate iv for cipher init
     //SecureRandom sr = new SecureRandom(); //create new secure random
       byte [] iv = new byte[8]; //create an array of 8 bytes 
       //sr.nextBytes(iv); //create random bytes to be used for the IV (?) Not too sure.
       String desiv = "0123456789abcdef"; // value from user
       iv = DatatypeConverter.parseHexBinary(desiv);
              
       IvParameterSpec dps = new IvParameterSpec(iv); //creating the IV 
       System.out.println("ivspec: "+dps);


       encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");       
       encryptCipher.init(Cipher.ENCRYPT_MODE, key, dps);
       //byte[] encryptedData = encryptData("1");

       decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        
       
       decryptCipher.init(Cipher.DECRYPT_MODE, key, dps);
       //decryptCipher.init(Cipher.DECRYPT_MODE, key);
       //decryptData(encryptedData);
	}
	
	
	public byte[] encryptData(String data)
		       throws IllegalBlockSizeException, BadPaddingException {
      //System.out.println("Data Before Encryption :" + data);
      byte[] dataToEncrypt = data.getBytes();
      byte[] encryptedData = encryptCipher.doFinal(dataToEncrypt);
      //System.out.println("Encryted Data: " + encryptedData);
      //System.out.println((int)encryptedData.length);
      
      return encryptedData;
     }
	
	 public String decryptData(byte[] data)
		       throws IllegalBlockSizeException, BadPaddingException {
      byte[] textDecrypted = decryptCipher.doFinal(data);
      //System.out.println("Decryted Data: " + new String(textDecrypted));
      return new String(textDecrypted);
     }
}
