package practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*1.reflectio
 *2.serializtion : jvm constructs objects even if private*/
public class SingletonDemo {
	
	private SingletonDemo() {
		
	}
	
	//serializing a file 
	static void saveToFile(BasicSingleton bs,String filename) throws IOException {
		
		try(FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream oo = new ObjectOutputStream(fout)){
			//writing basic singleton object (serializing)
			oo.writeObject(bs);
		}
	}
	
	//deserializing a file
	static BasicSingleton readfromFile(String filename) throws IOException, ClassNotFoundException {
		
		try(FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream oin = new ObjectInputStream(fin)){
			return (BasicSingleton) oin.readObject();
		}
		
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		BasicSingleton singleton1 = BasicSingleton.getInstance();
		singleton1.setValue(111);
		
		String filename = "singleton.bin";
		saveToFile(singleton1, filename);//serializing
		
		singleton1.setValue(222);
		BasicSingleton singleton2 = readfromFile(filename);//de-serialization
		System.out.println(singleton2.getValue());
		System.out.println(singleton1.getValue());
		
		/*
		 * here singleton1 and singleton2 are two different singletons , if readresolve is not written
		 * inside the serializable class
		 * 
		 *  which means jvm
		 * is creating object even if private which is a violation of singleton
		 * principle
		 */
		
		/*
		 * Fix: providing readResolve method inside serializable object.
		 */
		
	}

}

//if you want to serialize object yoy have to implement serializable interface
//then the class becomes serializable class
class BasicSingleton implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value;

	private BasicSingleton() {

	}

	/*
	 * making this final as i dont want to change instance after lazy init .
	 * no need to make final as get instance will only workk if instance is already null
	 */
	private static  BasicSingleton INSTANCE ;

	/*
	 * static class can only access static variables as static class is directly
	 * called.
	 * ALso many threads may be calling get instance whicjh could result in race conditions
	 * so we add synchronized concept
	 */
	
	/*
	 * public static BasicSingleton getInstance() throws IOException {
	 * 
	 * //this is lazy initialization of singleton if(INSTANCE == null) INSTANCE =
	 * new BasicSingleton(); return INSTANCE; }
	 */
	
	
	  public static synchronized BasicSingleton getInstance() throws IOException {
	  
	  //this is lazy initialization of singleton 
		if (INSTANCE == null)
			INSTANCE = new BasicSingleton();
		return INSTANCE;
	}
	 
	  //this is double check locking 
	/*
	 * public static BasicSingleton getInstance() throws IOException { 
	 * //checking if instance is null 
	 * if (INSTANCE == null) { synchronized
	 * (BasicSingleton.class) { //still if instance is null
	 * 
	 * if (INSTANCE == null) INSTANCE = new BasicSingleton(); }
	 * 
	 * } 
	 * return INSTANCE;
	 *  }
	 */
	
	

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
		return INSTANCE;
	}
	
}
