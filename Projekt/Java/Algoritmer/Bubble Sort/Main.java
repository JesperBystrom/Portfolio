
public class Main {

	public int[] array = new int[] {
		0,5,3,8,3,1,9,4,9,2
	};
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		double time = System.currentTimeMillis();
		int swaps = 1;
		while(swaps > 0){
			swaps = 0;
			for(int i=0;i<array.length;i++){
				int index = i+1;
				if(index < array.length){
					if(array[i] > array[index]){
						int temp = array[index];
						array[index] = array[i];
						array[i] = temp;
						swaps++;
					}
				}
			}
		}
		for(int i=0;i<array.length;i++){
			System.out.print(array[i] + ", ");
			System.out.println("Finished in: " + (System.currentTimeMillis() - time) + " ms");
		}
	}
}
