package com.referu.core.data;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.referu.core.tools.EncryptionTools;

@PersistenceCapable
public class UserData {
	
	@PrimaryKey 
	private String _userName;
	
	@Persistent  
	private String _email;
	
	@Persistent  
	private String _firstName;
	
	@Persistent  
	private String _lastName;
	
	@Persistent  
	private String _password;
	
	
	public UserData(String userName,
		String email, String password, String firstName, String lastName) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException{
		
		_userName = userName;
		_email = email;
		_firstName = firstName;
		_lastName = lastName;
		
		_password = EncryptionTools.
			getInstance().getMD5Hash(password);
		
		
	}
	
	public String getEmail(){
		return _email;
	}
	
	public String getUserName(){
		return _userName;
	}
	
	public String getFirstName(){
		return _firstName;
	}
	
	public String getLastName(){
		return _lastName;
	}
	
	public boolean isValidPassword(String password) 
		throws UnsupportedEncodingException, NoSuchAlgorithmException{
		
		password = EncryptionTools.
			getInstance().getMD5Hash(password);
		
		if(password == _password)
			return true;
		
		return false;
	}
	
	

}
