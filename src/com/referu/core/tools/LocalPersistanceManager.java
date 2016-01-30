package com.referu.core.tools;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class LocalPersistanceManager {

	//	private  PersistenceManager persistenceManager;
	
	protected static transient ThreadLocal<PersistenceManager> perThreadPM;
	
	private static final PersistenceManagerFactory PMF = 
		JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	
	
	public static PersistenceManager getPersistenceManager(){
		
		init();
		
		PersistenceManager persistenceManager = perThreadPM.get();
		
		if(persistenceManager == null){
			persistenceManager =  PMF.getPersistenceManager();
			perThreadPM.set(persistenceManager);
		}
		
		if(persistenceManager.isClosed()){
			persistenceManager =  PMF.getPersistenceManager();
			perThreadPM.set(persistenceManager);
		}
		
		else if(persistenceManager.currentTransaction() != null &&
				persistenceManager.currentTransaction().isActive()){
					persistenceManager.currentTransaction().commit();
		}
		
		return persistenceManager;
	}
	
	private static void init(){
		
		if(perThreadPM == null)
			perThreadPM = new ThreadLocal<PersistenceManager>();
	}
}
