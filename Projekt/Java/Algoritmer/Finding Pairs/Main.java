import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

	
	public static void main(String[] args) {
		double time = System.nanoTime();
		System.out.println(tryAndFind());
		System.out.println("Completion time: " + (System.nanoTime() - time) + " ns");
	}
	
	public static String tryAndFind(){
		int target = 10;
		int[] num = new int[] { 1, -2, 5, -6, 3, 2, 6,8 };
		
		HashSet<Integer> seen = new HashSet<Integer>();
		
		for(int i=0;i<num.length;i++){
			seen.add(num[i]);
			int test = target-num[i];
			if(seen.contains(test) && test != num[i])
				return num[i] + " : " + test;
		}
		return "null";
	}
}
