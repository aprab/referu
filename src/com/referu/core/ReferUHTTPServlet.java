package com.referu.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.NamespaceManager;
import com.referu.core.data.UserData;
import com.referu.core.exception.GenericException;
import com.referu.core.exception.GenericException.TYPE;
import com.referu.core.exception.NotLoggedInException;
import com.referu.core.tools.MemCache;
import com.referu.core.tools.UserTools;

@SuppressWarnings("serial")
public abstract class ReferUHTTPServlet extends HttpServlet{
	

	protected transient ThreadLocal<HttpServletRequest> perThreadRequest;
	protected transient ThreadLocal<HttpServletResponse> perThreadResponse;
	
	protected transient ThreadLocal<String> perThreadSecretKey;
	protected transient ThreadLocal<String> perThreadEncryptedString;
	
	protected transient ThreadLocal<JSONObject> perThreadIterativeJsonObject;
	
	
	protected static final String API_KEY =
		     "AIzaSyBz_0OoXCY1yDFJkE6tvAAxvE3_fQCoNTs";
	
	
	
	protected transient ThreadLocal<String> perThreadApplicationId;
	
	protected transient ThreadLocal<String> perThreadJSONFunction;
	
	protected transient ThreadLocal<Integer> perThreadRowCount;
	protected transient ThreadLocal<StringBuilder> perThreadIterativeMessage;
	
	private void validateThreadLocalData() {

		if (perThreadRequest == null) {
			perThreadRequest = new ThreadLocal<HttpServletRequest>();
		}

		if (perThreadResponse == null) {
			perThreadResponse = new ThreadLocal<HttpServletResponse>();
		}

		if (perThreadSecretKey == null) {
			perThreadSecretKey = new ThreadLocal<String>();
		}

		if (perThreadEncryptedString == null) {
			perThreadEncryptedString = new ThreadLocal<String>();
		}

		if (perThreadIterativeJsonObject == null) {
			perThreadIterativeJsonObject = new ThreadLocal<JSONObject>();
		}

		if (perThreadApplicationId == null) {
			perThreadApplicationId = new ThreadLocal<String>();
		}

		if (perThreadJSONFunction == null) {
			perThreadJSONFunction = new ThreadLocal<String>();
		}

		if (perThreadRowCount == null) {
			perThreadRowCount = new ThreadLocal<Integer>();
		}

		if (perThreadIterativeMessage == null) {
			perThreadIterativeMessage = new ThreadLocal<StringBuilder>();
		}

		
	}
	 
	protected final HttpServletRequest getThreadLocalRequest() {
		synchronized (this) {
			validateThreadLocalData();
			return perThreadRequest.get();
		}
	}

	protected final HttpServletResponse getThreadLocalResponse() {
		synchronized (this) {
			validateThreadLocalData();
			return perThreadResponse.get();
		}
	}

	protected final String getApplicationId() {
		synchronized (this) {
			validateThreadLocalData();
			return perThreadApplicationId.get();
		}
	}

	
	
	protected final String getSecretKey() {
		synchronized (this) {
			validateThreadLocalData();
			return perThreadSecretKey.get();
		}
	}
	
	protected final String getEncryptedString() {
		synchronized (this) {
			validateThreadLocalData();
			return perThreadSecretKey.get();
		}
	}

	public UserData getUser() throws NotLoggedInException, GenericException {
		
		UserData returnInfo =  
			UserTools.getInstance(getThreadLocalRequest()).getUser();
		
		if(returnInfo == null){
			throw new NotLoggedInException();
		}
		
		return returnInfo;
	}
	
	protected MemCache getUserMemCache() throws GenericException{
		return MemCache.getInstance();
	}
	
	
	protected UserTools getUserTools() {
		return UserTools.getInstance(getThreadLocalRequest());
	}
	
	protected void setRootNamespace() {
		NamespaceManager.set("root");
	}
	
	
	
	protected void setJSONParameter(){
		synchronized (this) {
			perThreadJSONFunction.set(
				getThreadLocalRequest().getParameter("callback"));
		}
	}
	
	protected String padJSONP(String json){
		
		String jsonFunction = perThreadJSONFunction.get();
		
		if(jsonFunction != null && jsonFunction.trim().length() > 0){
			json = jsonFunction + "(" + json + ")"; 
		}
		
		return json;
	}
	
	
	
	public void printValidString() throws IOException{
	
		if(isJson() == false)
			return;
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("valid", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		getThreadLocalResponse().getWriter().print(padJSONP(jsonObject.toString()));
	}

	public void printValidString(String key, Object data) throws IOException{
		
		if(isJson() == false)
			return;
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("valid", true);
			jsonObject.put(key, data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		getThreadLocalResponse().getWriter().print(padJSONP(jsonObject.toString()));
	}
	
	
	public void addJsonOutput(String key, Object data){
		
		JSONObject iterativeJsonObject;
		
		synchronized (this) {
			
			validateThreadLocalData();
			
			iterativeJsonObject = 
				perThreadIterativeJsonObject.get();
		}
		
		
		if(iterativeJsonObject == null){
			iterativeJsonObject = new JSONObject();
		}
		
		try {
			iterativeJsonObject.put(key, data);
		} catch (Exception e) {
		}
		
		synchronized (this) {
			
			perThreadIterativeJsonObject.set(iterativeJsonObject);
		}
	}
	
	public void printIterativeJsonOutput() throws IOException {
		
		if(isJson() == false){
			return;
		}
		
		JSONObject iterativeJsonObject;
		
		synchronized (this) {
			validateThreadLocalData();
			
			iterativeJsonObject = 
				perThreadIterativeJsonObject.get();
		}
		
		if(iterativeJsonObject == null){
			iterativeJsonObject = new JSONObject();
		}
		
		try {
			if(iterativeJsonObject.get("valid") == null)
				iterativeJsonObject.put("valid", true);	
		} catch (Exception e) {		
			try {
				iterativeJsonObject.put("valid", true);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		
		synchronized (this) {
			
			perThreadIterativeJsonObject.set(iterativeJsonObject);
		}
		
		getThreadLocalResponse().getWriter().print(
			padJSONP(iterativeJsonObject.toString()));
		
	}
	
	public void printIterativeJsonOutput(JSONObject returnJsonObj) 
			throws GenericException, IOException {
		
		if(isJson() == false){
			return;
		}

		if(returnJsonObj == null)
			throw new GenericException("No such object in the domain.", 
					TYPE.None);
		
		getThreadLocalResponse().getWriter().print(
			padJSONP(returnJsonObj.toString()));
		
	}
	
	
	

	public void printInvalidString(String reason) throws IOException{
		
		if(isJson()){
			JSONObject jsonObject = new JSONObject();
			
			try {
				jsonObject.put("valid", false);
				jsonObject.put("reason", reason);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			getThreadLocalResponse().getWriter().print(padJSONP(jsonObject.toString()));
			return;
		}
		
	}
	
	public void printInvalidString(GenericException e) throws IOException{
		
		if( isJson() ) {
			
			JSONObject jsonObject = new JSONObject();
			
			try {
				jsonObject.put("valid", false);
				jsonObject.put("reason", e.getMessage());
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
			
			getThreadLocalResponse().getWriter().print(padJSONP(jsonObject.toString()));
			return;
		}
	}
	
	public JSONObject getJSONFromHTTP(String URL) throws IOException {
		
		java.net.URL url = new java.net.URL(URL);
		
		InputStream is = url.openStream();
		
		try {
			
			java.util.Scanner s = 
				new java.util.Scanner(is,"UTF-8").useDelimiter("\\A");
			JSONObject jsonObject = new JSONObject(s.next());
			
			return jsonObject;
			
		} catch(JSONException ex) {
			
			return null;
			
		} finally {
			is.close();
		}
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
		
		doGetOrPost(req,resp,true);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
		
		doGetOrPost(req,resp,false);
	}
	
	
	@SuppressWarnings("deprecation")
	private void doGetOrPost(HttpServletRequest req, HttpServletResponse resp, Boolean isGet)
		throws IOException, ServletException {
		
		validateThreadLocalData();
		
		
		synchronized (this) {
			
	        validateThreadLocalData();
	        
	        perThreadRequest.set(req);
	        perThreadResponse.set(resp);
	        perThreadRowCount.set(0);
	        perThreadIterativeMessage.set(new StringBuilder());
	        perThreadIterativeJsonObject.set(null);
		}
		
		
		String isEncryption = 
			getThreadLocalRequest().getParameter("encryption");
		
		setJSONParameter();
		
		try{
			if(isGet) {
				
				if(isEncryption != null && isEncryption.equals("yes")) {
					throw new GenericException(
						"Get is not supported for this call",TYPE.None);
				}
				
				goGet(req, resp);
			} else {
				goPost(req, resp);
			}
			
		} catch(GenericException ex) {
			
			if(isJson()) {
				printInvalidString(ex);
				return;
			}
			
		} catch (JSONException ex) {
			
			printInvalidString(ex.getMessage());
			return;
			
		} catch (NotLoggedInException ex) {
			
			if(isJson()) {
				printInvalidString("Please log-in before attemting this call");
				return;
			} if(ex.getMessage().equals(TYPE.Maintenance)) {
				resp.sendRedirect("/auth/domain/maintenance");
				return;
			}
			
			getThreadLocalResponse().setStatus(403,"Unauthorised access. Forbidden content.");
			
			String redirect = req.getRequestURL()+ "?" + req.getQueryString();
			System.out.print(redirect);
			
			redirect = encodeURIComponent(redirect);
			
			System.out.print(redirect);

			return;
			
		} catch (Exception ex){
			
			printInvalidString(ex.getMessage());
			
			ex.printStackTrace();
		}
	}
	
	
	public static String encodeURIComponent(String s) {
	    
		String result = null;
		
		try {
			
	      result = URLEncoder.encode(s, "UTF-8")
             .replaceAll("\\+", "%20")
             .replaceAll("\\%21", "!")
             .replaceAll("\\%27", "'")
             .replaceAll("\\%28", "(")
             .replaceAll("\\%29", ")")
             .replaceAll("\\%7E", "~");
	      
	    } catch (UnsupportedEncodingException e) {
	      result = s;
	    }

	    return result;
	    
	  }
	
	
	@SuppressWarnings("deprecation")
	protected void goGet(HttpServletRequest req, HttpServletResponse resp) 
		throws GenericException,NotLoggedInException,ServletException, 
			IOException,JSONException,Exception {
		
		if(isJson() == false)
			getThreadLocalResponse().setStatus(404,"Method not found");
		
		throw new GenericException("Get is not supported for this call",TYPE.None);
	}
	
	@SuppressWarnings("deprecation")
	protected void goPost(HttpServletRequest req, HttpServletResponse resp) 
		throws GenericException,NotLoggedInException,ServletException, 
			IOException,JSONException,Exception {
		
		if(isJson() == false)
			getThreadLocalResponse().setStatus(404,"Method not found");
		
		throw new GenericException("Post is not supported for this call",TYPE.None);
	}
	
	protected boolean ignoreApplicationKey(){
		return false;
	}
	
	
	protected abstract boolean isJson();
	
	protected  boolean isRestricted(){
		return false;
	}
	

	protected String redrawForm(){
		return "";
	}

}
