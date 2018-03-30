import java.util.ArrayList;

/*
 * 
 * @Author: Jesper Byström
 * Funktion: Detta program hittar alla karaktärer som kommer tillbaka mer än 1 gång.
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
