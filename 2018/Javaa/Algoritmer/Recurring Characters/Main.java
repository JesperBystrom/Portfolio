import java.util.ArrayList;
import java.util.HashSet;

/*
 * 
 * @Author: Jesper Byström
 * Funktion: Detta program hittar alla karaktärer som kommer tillbaka mer än 1 gång.
 * 
 */

public class Main {

	public static void main(String[] args) {
		double time = System.nanoTime();
		HashSet<Character> result = findRecurringCharacter("ABCCDEFGHH");
		result.forEach(r -> System.out.println(r));
		System.out.println("Finished in: " + ((System.nanoTime()-time)/1000000) + " ms");
	}
	
	public static HashSet<Character> findRecurringCharacter(String input){
		char[] cInput = input.toCharArray();
		HashSet<Character> checks = new HashSet<Character>();
		HashSet<Character> recurrances = new HashSet<Character>();
		
		for(int i=0;i<cInput.length;i++){
			if(checks.contains(cInput[i]))
				recurrances.add(cInput[i]);
			checks.add(cInput[i]);
		}
		return recurrances;
	}
}
