package com.referu.core;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.referu.core.data.UserData;
import com.referu.core.tools.EncryptionTools;
import com.referu.core.tools.LocalPersistanceManager;

@SuppressWarnings("serial")
public class CreateUser extends ReferUHTTPServlet {
	
	public void goGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, Exception {
		
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String userId = req.getParameter("userId");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		
		password = EncryptionTools.getInstance().
				getMD5Hash(password);
		
		PersistenceManager pm = 
				LocalPersistanceManager.getPersistenceManager();
		
		UserData previousID = (UserData) pm.getObjectById(UserData.class,"userId");
		
		if(previousID == null) {
			
			addJsonOutput("valid", false);
			printIterativeJsonOutput();
				
			return;
		}
		
		UserData ud = new UserData(userId, email, password,firstName,lastName);
		
		pm.currentTransaction().begin();
		pm.makePersistent(ud);
		pm.currentTransaction().commit();
		
		UserData ud1 = (UserData) pm.getObjectById(UserData.class,"userId");
		
		addJsonOutput("valid", true);
		addJsonOutput("userID", ud1.getEmail());
		
		printIterativeJsonOutput();
		
		
	}
	
	public boolean isJson(){
		return true;
	}
	
}
