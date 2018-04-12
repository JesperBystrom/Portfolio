
public class Main {

	int[] sortedArray = {
		0,1,2,3,4,5,6,7,8,9	
	};
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		System.out.println("Found it: " + search());
	}
	
	public int search(){
		int start = 0;
		int end = sortedArray.length-1;
		int middle = 0;
		int searchValue = 7;
		while(true){
			middle = (end+start)/2;
			if(sortedArray[middle] > searchValue){
				//left side
				end = middle-1;
			}
			if(sortedArray[middle] < searchValue){
				//right side
				start = middle+1;
			} else {
				return sortedArray[middle];
			}
		}
	}

}
