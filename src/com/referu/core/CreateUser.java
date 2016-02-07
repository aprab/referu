package com.referu.core;

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.referu.core.data.UserData;
import com.referu.core.tools.EncryptionTools;
import com.referu.core.tools.LocalPersistanceManager;

@SuppressWarnings("serial")
public class CreateUser extends ReferUHTTPServlet {
	
	public void goGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, Exception {
		
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		
		password = EncryptionTools.getInstance().
				getMD5Hash(password);
		
		PersistenceManager pm = 
				LocalPersistanceManager.getPersistenceManager();
		
		UserData previousID = null;
		
		try{
			previousID = (UserData) pm.getObjectById(UserData.class,email);
		} catch(JDOObjectNotFoundException ex){
			
			UserData ud = new UserData(email, password,firstName,lastName);
			
			pm.currentTransaction().begin();
			pm.makePersistent(ud);
			pm.currentTransaction().commit();
			
			UserData ud1 = (UserData) pm.getObjectById(UserData.class,email);
			
			addJsonOutput("valid", true);
			addJsonOutput("userID", ud1.getEmail());
			
			printIterativeJsonOutput();
			
			return;
		}
		
		addJsonOutput("valid", false);
		addJsonOutput("reason", "This email address is already registered.");
		printIterativeJsonOutput();
		
	}
	
	public boolean isJson(){
		return true;
	}
	
}
