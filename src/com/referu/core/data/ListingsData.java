package com.referu.core.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.search.GeoPoint;


@PersistenceCapable
public class ListingsData {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long _key;
	
	@Persistent  
	private String _email;
	
	@Persistent  
	private Double _latitude;
	
	@Persistent  
	private Double _longitude;
	
	@Persistent  
	private GeoPoint geopoint;
	
	@Persistent  
	private String _nameOfListing;
	
	@Persistent  
	private String _description;
	
	@Persistent  
	private String _phone;
	
	@Persistent
	private Double _price;
	
	@Persistent
	private String _url;
	
	public ListingsData(GeoPoint geoPT, String email,
			String nameOfListing, String description, String phone, String url,
			Double price) {

		_email = email;
		
		_nameOfListing = nameOfListing;
		_description = description;	
		_phone = phone;
		_price = price;
		_url = url;		
	}
	
	public String getEmail() {
		return _email;
	}
	
	public String getNameOfListing() {
		return _nameOfListing;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public GeoPoint getPoint() {
		return geopoint;
	}
	
	public String getPhone(){
		return _phone;
	}
	
	public Double getPrice(){
		return _price;
	}
	
	public String getUrl(){
		return _url;
	}
}
