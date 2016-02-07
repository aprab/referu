package com.referu.core;

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.GeoPt;
import com.referu.core.data.ListingsData;
import com.referu.core.tools.LocalPersistanceManager;

@SuppressWarnings("serial")
public class GetSurroundingListings extends ReferUHTTPServlet {
	
	public void goGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, Exception {
		
		Long key = Long.parseLong(req.getParameter("key"));
				
		PersistenceManager pm = 
				LocalPersistanceManager.getPersistenceManager();
		
		try {
		
			ListingsData doesIdExist = 
				(ListingsData) pm.getObjectById(ListingsData.class,key);
			
			GeoPt center = new GeoPt(
				doesIdExist.getPoint().getLatitude(), 
				doesIdExist.getPoint().getLongitude());
			
			double radius = 10;
			
//			GeoR
//			
//			Filter f = new StContainsFilter("_geoPT", new Ci(center, radius));
			
//			Filter f = 
//			Query q = new Query("Kind").setFilter(f);
//
//			// Testing for containment within a rectangle
//			GeoPt southwest = ...
//			GeoPt northeast = ...
//			Filter f = new StContainsFilter("location", new Rectangle(southwest, northeast));
//			Query q = new Query("Kind").setFilter(f);
		
		} catch (JDOObjectNotFoundException ex) {
			
			addJsonOutput("valid", false);
			addJsonOutput("reason", "No such listing");
			printIterativeJsonOutput();
			
			return;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public boolean isJson(){
		return true;
	}
	
}
