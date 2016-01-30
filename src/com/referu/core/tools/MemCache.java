package com.referu.core.tools;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.InvalidValueException;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheService.IdentifiableValue;
import com.google.appengine.api.memcache.MemcacheServiceException;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.referu.core.exception.GenericException;


public class MemCache {

	public static final Logger _log = 
			Logger.getLogger(MemCache.class.getName());

	private boolean isMemCache = true;
	
	private String appendString = "";

	private static MemcacheService _instance;

	public enum MemcacheEnum {
		
		USER_ID
	}  

	private MemCache(HttpServletRequest request) throws GenericException {

		
	}

	private MemCache()  {
	}
	

	public static MemCache getInstance() {
		return new MemCache();
	}


	private MemcacheService getMemcacheService(){

		if(_instance == null)
			_instance = MemcacheServiceFactory.getMemcacheService();

		return _instance;
	}

//	private MemcacheService getMemcacheService(String type) {
//
//		if(_instance == null)
//			_instance = MemcacheServiceFactory.getMemcacheService();
//
//		NamespaceManager.set(type);
//
//		return _instance;
//	}

	public void incrementMemcacheKey(MemcacheEnum enumValue, long delta, long initialValue){
		
		if(!isMemCache)
			return;
		
		MemcacheService current = getMemcacheService();
		
		String keyVal = enumValue.toString() + appendString;
		
		try {
			
			current.increment(keyVal,delta,initialValue);
			
		} catch (IllegalArgumentException ex){
			current.delete(keyVal);
		}
	
	}
	
	public Object getMemcacheForKey(MemcacheEnum enumValue) {

		String enumString = enumValue.toString() + appendString;
		try {
			MemcacheService current = getMemcacheService();    	

			if(isMemCache && current.contains(enumString)) {

				Object returnValue = 
						current.get(enumString);

				return returnValue;

			}

			return null;
		} catch (InvalidValueException e) {
			
			getMemcacheService().delete(enumString);
			return null;
		} catch(MemcacheServiceException e){
			return null;
		}  	
	}

	public Object getMemcacheForKeyandOffset(MemcacheEnum enumValue, 
			String offset) {

		String keyVal = enumValue + appendString + offset;

		try {
			MemcacheService current = getMemcacheService();



			if(isMemCache && current.contains(keyVal)) {

				Object returnValue = 
						current.get(keyVal);

				return returnValue;

			}

			return null;
		} catch (InvalidValueException e) {
			getMemcacheService().delete(keyVal);
			return null;
		} catch(MemcacheServiceException e){
			return null;
		}
	}
	
	public IdentifiableValue getMemcacheIdentifierForKey(MemcacheEnum enumValue) {

		String enumString = enumValue.toString() + appendString;
		try {
			MemcacheService current = getMemcacheService();    	

			if(isMemCache && current.contains(enumString)) {

				IdentifiableValue returnValue = 
						current.getIdentifiable(enumString);

				return returnValue;

			}

			return null;
		} catch (InvalidValueException e) {
			
			getMemcacheService().delete(enumString);
			return null;
		}    	
	}

	public IdentifiableValue getMemcacheIdentifierForKeyandOffset(MemcacheEnum enumValue, 
			String offset) {

		String keyVal = enumValue + appendString + offset;

		try {
			MemcacheService current = getMemcacheService();



			if(isMemCache && current.contains(keyVal)) {

				IdentifiableValue returnValue = 
						current.getIdentifiable(keyVal);

				return returnValue;

			}

			return null;
		} catch (InvalidValueException e) {
			getMemcacheService().delete(keyVal);
			return null;
		}    	
	}

	public void deleteObjectFromMemCacheByOffset(
			MemcacheEnum enumValue, String offset){

		String key = enumValue + appendString + offset;

		try {
			if(!isMemCache)
				return;

			MemcacheService current = getMemcacheService();

			if(current.contains(key)){
				current.delete(key);
			}
		}  catch (InvalidValueException e) {

			getMemcacheService().delete(key);
		}   

	}

	public void deleteObjectFromMemCache(MemcacheEnum enumVal ){

		String enumString = enumVal.toString() + appendString;
		try {
			if(!isMemCache)
				return;

			MemcacheService current = getMemcacheService();

			if(current.contains(enumString))
				current.delete(enumString);

		} catch (InvalidValueException e) {
			getMemcacheService().delete(enumString);
		}
	}
	
	public boolean putThreadSafeObjectInMemcacheForKey(MemcacheEnum enumValue, Object objectValue, IdentifiableValue identifier){

		return putThreadSafeObjectInMemcacheForKeyOffsetAndSetExpiration(enumValue,null,objectValue,null,identifier);

	}

	public boolean putThreadSafeObjectInMemcacheForKeyAndOffset(MemcacheEnum enumValue, 
			String value, Object objectVal, IdentifiableValue identifier){

		return putThreadSafeObjectInMemcacheForKeyOffsetAndSetExpiration(enumValue,value,objectVal,null,identifier);
	}
	
	public boolean putThreadSafeObjectInMemcacheForKeyOffsetAndSetExpiration(MemcacheEnum memEnum,
			String key, Object value,Integer seconds, IdentifiableValue identifier) {
		
		
		if(!isMemCache)
			return false;
		
		MemcacheService current = getMemcacheService();
		
		String keyVal = memEnum.toString() + appendString;
		
		try {
			
			if(key != null && key.length() != 0)
				keyVal = keyVal + key;

			Expiration expiration = null;
			
			if(seconds != null && seconds > 0){
				expiration = Expiration.byDeltaSeconds(seconds);
			}
			
			IdentifiableValue identifiable;
			
			if( identifier != null )
				identifiable = identifier;
			else
				identifiable = current.getIdentifiable(keyVal);
			
			return current.putIfUntouched(keyVal, identifiable, value, expiration);
			
		} catch (IllegalArgumentException ex){
			current.delete(keyVal);
			return false;
		}
		 
	}

	public void putMemcacheForkey(MemcacheEnum enumValue, Object objectValue){

		putMemCacForKeyandOffSetandExpiration(enumValue,null,objectValue,null);

	}

	public void putMemcacheForkeyandOffset(MemcacheEnum enumValue, 
			String value, Object objectVal){

		putMemCacForKeyandOffSetandExpiration(enumValue,value,objectVal,null);
	}

	public void clearCacheForAllNamespaces() {
		
		getMemcacheService().clearAll();
		
	}


	public void putMemCacForKeyandOffSetandExpiration(MemcacheEnum memEnum,
			String key, Object value,Integer seconds){

		if(!isMemCache)
			return;
		
		MemcacheService current = getMemcacheService();
		
		String keyVal = memEnum.toString() + appendString;
		
		try {
			
			if(key != null && key.length() != 0)
				keyVal = keyVal + key;

			if(seconds == null || seconds == 0){
				
				current.put(keyVal, value);
				return;
			}
			
			Expiration expiration = 
				Expiration.byDeltaSeconds(seconds);
			
			current.put(keyVal, value,expiration);
			
		} catch (IllegalArgumentException ex) {
			
			System.err.println("Either Key " + keyVal + 
				" or Value " + value + "is not serializable.");
			
			current.delete(keyVal);
		}
	}
	
	public static class MemcacheExpiryTime{
		public static final int levelExpiryTime = 2*60*60;
		public static final int categoryExpiryTime = 2*60*60;
		public static final int enabledSkillMapExpiryTime = 24*60*60;
			
	}
}
