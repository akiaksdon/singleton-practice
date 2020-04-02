package practice;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Test {
	
	static int[] a = {1,2,3,4,5};
	public static void main(String[] args) {
		Test test = new Test();
		
		olve(Array.getLength(a),a);
	}
	private static void olve(int length, int[] a2) {
		Arrays.sort(a2);
	}

}
