package com.referu.core;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.GeoRegion.Circle;
import com.google.appengine.api.datastore.Query.StContainsFilter;
import com.referu.core.data.ListingsData;
import com.referu.core.tools.LocalPersistanceManager;

@SuppressWarnings("serial")
public class GetSurroundingListings extends ReferUHTTPServlet {
	
	public void goGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, Exception {
		
		String key = req.getParameter("key");
		
		String URL = 
				"https://maps.googleapis.com/maps/api/geocode/"
					+ "json?address=" + key + "&key=AIzaSyBz_0OoXCY1yDFJkE6tvAAxvE3_fQCoNTs";
		
		JSONObject jsonObject = getJSONFromHTTP(URL);
		
		JSONObject location = 
			jsonObject.getJSONArray("results").getJSONObject(0).
			getJSONObject("geometry").getJSONObject("location");
		
		Double latitude = location.getDouble("lat");
		Double longitude = location.getDouble("lng");
		
		try {
		
			GeoPt center = new GeoPt(
				latitude.floatValue(), longitude.floatValue());
			
			Circle circle = new Circle(center, 10000);
			Filter f = new StContainsFilter("geoPT", circle);
			
			Query q = new Query("ListingsData").setFilter(f);
			
			DatastoreService datastore = 
				DatastoreServiceFactory.getDatastoreService();
			
			List<Entity> results = datastore.prepare(q)
                    .asList(FetchOptions.Builder.withDefaults());
			
			JSONArray returnArray = new JSONArray();
			
			for(Entity eachResult : results) {
				
				returnArray.put(eachResult.getProperties() );
			}
			
			addJsonOutput("valid", true);
			addJsonOutput("result", returnArray);
			
			printIterativeJsonOutput();
			
		
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
