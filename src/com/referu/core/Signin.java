package com.referu.core;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.referu.core.data.UserData;
import com.referu.core.tools.LocalPersistanceManager;

@SuppressWarnings("serial")
public class Signin extends ReferUHTTPServlet {
	
	public void goGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, Exception {
		
		PersistenceManager pm = 
				LocalPersistanceManager.getPersistenceManager();
		
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		
		
		UserData userData = 
			(UserData) pm.getObjectById(UserData.class,userId);
		
		userData.isValidPassword(password);
		
		addJsonOutput("firstName", userData.getFirstName());
		addJsonOutput("lastName", userData.getLastName());
		addJsonOutput("email", userData.getEmail());
		
		printIterativeJsonOutput();
		
	}
	
	public boolean isJson(){
		return true;
	}
	
}
