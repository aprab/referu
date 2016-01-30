package com.referu.core.tools;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.google.appengine.labs.repackaged.com.google.common.io.BaseEncoding;

public class EncryptionTools {
	
		public static EncryptionTools getInstance() {
			
			return new EncryptionTools();
		}
		
		private EncryptionTools(){

		}
		
		
		/**
		 * @return returnString
		 * @throws NoSuchAlgorithmException
		 * Creates a key.
		 */
		
		public String createKey() 
			throws NoSuchAlgorithmException {
			
			KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
			SecretKey skey = kgen.generateKey();
			
	        byte[] something = skey.getEncoded();
			
			String returnString = new String(BaseEncoding.base64().encode(something));
	        
			return returnString;
		}
		
		
		
		/**
		 * @param value
		 * @param secretKey
		 * Encodes the secret key.
		 */
		
		public String encode(String value,String secretKey){
			
			byte[] key = BaseEncoding.base64().decode(secretKey);
			
			Key encryptKey = new SecretKeySpec(key, "Blowfish");
			
			try {
				
				Cipher encryptCipher;
				
				encryptCipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
				encryptCipher.init(Cipher.ENCRYPT_MODE, encryptKey);
				
				byte[] op = encryptCipher.doFinal(value.getBytes());
				
				return new String(BaseEncoding.base64().encode(op));
				
			} catch (NoSuchAlgorithmException e) {
				
			} catch (NoSuchPaddingException e) {
				
			} catch (InvalidKeyException e) {
				
			} catch (IllegalBlockSizeException e) {
				
			} catch (BadPaddingException e) {
								
			}
			
			return "";
			
		}
		
		/**
		 * @param value
		 * @param secretKey
		 * Decodes the secret key.
		 */
		
		public String decode(String value,String secretKey){
			
			byte[] phrase = BaseEncoding.base64().decode(value);
			byte[] key = BaseEncoding.base64().decode(secretKey);
			
			Key encryptKey = new SecretKeySpec(key, "Blowfish");
			
			try {
				
				Cipher encryptCipher;
				
				encryptCipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
				encryptCipher.init(Cipher.DECRYPT_MODE, encryptKey);
				
				byte[] op = encryptCipher.doFinal(phrase);
				
				String opString = new String(op);
				
				return opString;
				
			} catch (NoSuchAlgorithmException e) {
				
			} catch (NoSuchPaddingException e) {
				
			} catch (InvalidKeyException e) {
				
			} catch (IllegalBlockSizeException e) {
				
			} catch (BadPaddingException e) {
								
			}
			
			return "";
			
		}
		
		public static String getMD5Hash(String inputString) throws UnsupportedEncodingException, NoSuchAlgorithmException{

			byte[] bytesOfMessage = inputString.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] encoded = md.digest(bytesOfMessage);

			String finalencodedString= "";

			for(int i = 0; i < encoded.length; i++)
			{
				String hex = Integer.toHexString(0xFF & encoded[i]);
				if (hex.length() == 1) {
					finalencodedString += "0";
				}
				finalencodedString += hex;
			}

			if(finalencodedString.length() > 500){
				return finalencodedString.substring(0,498);
			}

			return finalencodedString;
		}

}
