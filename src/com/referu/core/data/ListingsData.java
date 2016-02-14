package com.referu.core.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.GeoPt;


@PersistenceCapable
public class ListingsData {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long _key;
	
	@Persistent  
	private String _email;
	
	@Persistent  
	private GeoPt _geoPT;
	
	@Persistent  
	private String _nameOfListing;
	
	@Persistent  
	private Double _price;
	
	@Persistent  
	private String _phone;
	
	@Persistent  
	private String _description;
	
	
	public ListingsData(GeoPt geoPT, String email,
			String nameOfListing, String description) {
		
		_email = email;
		_geoPT = geoPT;
		_nameOfListing = nameOfListing;
		_description = description;	
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
	
	public GeoPt getPoint() {
		return _geoPT;
	}
}
