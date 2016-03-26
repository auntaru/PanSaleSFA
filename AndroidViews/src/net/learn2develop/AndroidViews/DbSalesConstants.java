package net.learn2develop.AndroidViews;

public final class DbSalesConstants {
	public static final String TABLE_NAME = "product";

	//define database columns
	public static final String ID = "ID";
	public static final String NAME = "Name";
	public static final String PRICE = "Price";
	public static final String SYMBOL = "Symbol";
	
	
	private DbSalesConstants(){
		//this prevents even the native class from 
	    //calling this constructor as well :
	    throw new AssertionError();
	}

}
