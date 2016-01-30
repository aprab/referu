package com.referu.core.exception;


@SuppressWarnings("serial")
public class GenericException extends Exception {
	
	// PlainError will not redraw form or add to logs or send emails
	
	// Silent error will not log into Logs, or send email. But will redraw form.  
	
	// None will do everything.
	
	public enum TYPE{ None,OnlyRedraw,RedrawAndLog,InvalidDomain ,Maintenance}
	
	public TYPE _type;
	
	public GenericException(){
		
		super();
		_type = TYPE.RedrawAndLog;
	}
	
	
	public GenericException(String message){
		
		super(message);
		_type = TYPE.RedrawAndLog;
	}
	
	public GenericException(String message, TYPE type){
		super(message);
		_type = type;
	}
	
	public TYPE getType(){
		
		if(_type == null)
			return TYPE.RedrawAndLog;
		
		return _type;
	}
	
	public  String toString(){
		return getMessage();
	}

}
