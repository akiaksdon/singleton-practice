package practice;

import java.io.File;

/* Static Block singleton because of exceptions thrown in singleton constructor*/
public class StaticBlockSingleton {
	
	//no need to make final we get instance from static block
	private static StaticBlockSingleton Instance;
	
	private StaticBlockSingleton() throws Exception {
		System.out.println("creating file");
		//some initialization
		//throws some exception
		File.createTempFile(".", ".");
	}
	
	//static block only executes once 
	static {
		try {
			Instance = new StaticBlockSingleton();
		} catch ( Exception e) {
			System.out.println("error creating a file");
			
		}
	}
	
	public static StaticBlockSingleton getSingletonInstance() {
		return Instance;
	}
	
	public static void main(String[] args)  {
	}
		
}
	
