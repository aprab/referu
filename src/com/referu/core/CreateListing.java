package com.referu.core;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.GeoPt;
import com.referu.core.data.ListingsData;
import com.referu.core.data.UserData;
import com.referu.core.tools.LocalPersistanceManager;

@SuppressWarnings("serial")
public class CreateListing extends ReferUHTTPServlet {
	
	public void goGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, Exception {
		
		String email = req.getParameter("email");
		String latitude = req.getParameter("latitude");
		String longitude = req.getParameter("longitude");
		
		String nameOfVenue = req.getParameter("name");
		String description = req.getParameter("description");
		String phone = req.getParameter("phone");
		Double price = Double.parseDouble(req.getParameter("price"));
		String url = req.getParameter("url");
		
				
		
		PersistenceManager pm = 
				LocalPersistanceManager.getPersistenceManager();
		
		UserData doesIdExist = (UserData) pm.getObjectById(UserData.class,email);
		
		if(doesIdExist == null) {
			
			addJsonOutput("valid", false);
			printIterativeJsonOutput();
				
			return;
		}
		
		GeoPt geoPT = 
			new GeoPt(Float.parseFloat(latitude), Float.parseFloat(longitude));
		
		ListingsData listingData = new ListingsData(geoPT, 
			email,nameOfVenue,description,phone,url,price);
		
		pm.makePersistent(listingData);
		
		addJsonOutput("valid", true);
		printIterativeJsonOutput();
		
	}
	
	public boolean isJson(){
		return true;
	}
	
}
