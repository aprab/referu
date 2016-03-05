package com.referu.core.tools;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.referu.core.data.UserData;
import com.referu.core.exception.GenericException;
import com.referu.core.exception.GenericException.TYPE;
import com.referu.core.exception.NotLoggedInException;
import com.referu.core.tools.MemCache.MemcacheEnum;

public class UserTools {
	
	private HttpSession _session;
	
	
	private UserTools(HttpServletRequest request) {

		_session = request.getSession(true);

	}
	
	public static UserTools getInstance(HttpServletRequest req) {
		return new UserTools(req);
	}
	
	public UserData getUser() throws GenericException, NotLoggedInException {

		if (_session == null)
			throw new GenericException("Not a valid session.", TYPE.OnlyRedraw);

		if (_session.getAttribute("userId") == null
				&& _session.getAttribute("loginTime") == null) {
			return null;
		}

		String userID = (String) _session.getAttribute("userId");
		
		UserData info = findUserInCache(userID);

		if (info == null) {
			_session.invalidate();
			throw new NotLoggedInException();
		}

		return info;
	}
	
	public UserData findUserInCache(String userId) throws GenericException {

		MemCache cache = MemCache.getInstance();

		if (cache.getMemcacheForKeyandOffset(MemcacheEnum.USER_ID, userId) != null) {

			return (UserData) cache.getMemcacheForKeyandOffset(
					MemcacheEnum.USER_ID, userId);
		}

		PersistenceManager pm = LocalPersistanceManager
				.getPersistenceManager();

		UserData userData = (UserData) pm.getObjectById(userId);

		putUserInCache(userData);

		return userData;
	}
	
	public void putUserInCache(UserData user) throws GenericException {

		MemCache cache = MemCache.getInstance();

		String email = user.getEmail();

		cache.putMemcacheForkeyandOffset(MemcacheEnum.USER_ID, 
			email,user);

	}
	
//	private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
//		  double theta = lon1 - lon2;
//		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//		  dist = Math.acos(dist);
//		  dist = rad2deg(dist);
//		  dist = dist * 60 * 1.1515;
//		  
//		  if (unit == "K") {
//			  dist = dist * 1.609344;
//		    
//		  } else if (unit == "N") {
//			  
//			  dist = dist * 0.8684;
//		  }
//		  
//		  return (dist);
//	}
//	
//	private void calculateCircle(double lat1, double lon1, )
//		
//	
//	
//		private double deg2rad(double deg) {
//		  return (deg * Math.PI / 180.0);
//		}
//		
//		private double rad2deg(double rad) {
//		  return (rad * 180.0 / Math.PI);
//		}

}
