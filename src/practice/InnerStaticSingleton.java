package practice;

import java.io.Serializable;

class InnerStaticSingleton implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value;

	private InnerStaticSingleton() {

	}

	//here we create a inner static class and instantiate class insidde it 
	//thus accomplishing lazy init and thread safety as or sattic class is 
	//initialized only once so other thread cant simutaneously do it .
	static class InnerStaticSingletonImpl {
		public static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
	}
	 
	  public static InnerStaticSingleton getInstance(){ 
		  return InnerStaticSingletonImpl.INSTANCE;
	  }
	
	

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	/*
	 * this method is a fix for serialization it tells jvm to return whatever we are
	 * returning whenever serialization and deserialization happen.
	 */
	protected Object readResolve() {
		return getInstance();
	}
	
	public static void main(String[] args) {
		InnerStaticSingleton instance = InnerStaticSingleton.getInstance();
		instance.setValue(1);
		System.out.println(instance.getValue());
	}
	
}
	