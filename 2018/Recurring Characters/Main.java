import java.util.ArrayList;

/*
 * 
 * @Author: Jesper Bystr�m
 * Funktion: Detta program hittar alla karakt�rer som kommer tillbaka mer �n 1 g�ng.
 * 
 */

public class Main {

	public static void main(String[] args) {
		ArrayList<Character> result = findRecurringCharacter("ABCDCEFGHH");
		result.forEach(r -> System.out.println(r));
	}
	public static ArrayList<Character> findRecurringCharacter(String input){
		char[] cInput = input.toCharArray();
		char[] checks = new char[cInput.length];
		ArrayList<Character> recurrances = new ArrayList<Character>();
		for(int i=0;i<cInput.length;i++){
			for(int j=0;j<checks.length;j++){
				if(checks[j] == cInput[i]){
					recurrances.add(checks[j]);
				}
			}
			checks[i] = cInput[i];
		}
		return recurrances;
	}
}
