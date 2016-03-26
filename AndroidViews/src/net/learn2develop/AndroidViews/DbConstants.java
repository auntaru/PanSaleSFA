package net.learn2develop.AndroidViews;

public final class DbConstants {
	public static final String TABLE_NAME = "contactTest1";

	//define database columns
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";
	
	
	/**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	private DbConstants(){
		//this prevents even the native class from 
	    //calling this constructor as well :
	    throw new AssertionError();
	}

}
