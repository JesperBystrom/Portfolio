import java.util.ArrayList;
import java.util.Random;

public class Main {

    public int[] array = new int[] {
        3,2,1
    };
    
    public static void main(String[] args){
        new Main();
    }
    
    public Main(){
        
        double time = System.currentTimeMillis();
        int indexPicked = 0;
        int elementPicked = array[indexPicked];
        while(indexPicked < array.length){
            elementPicked = array[indexPicked];
            for(int i=indexPicked;i>=0;i--){
                if(elementPicked < array[i]){
                    int temp = array[i+1];
                    array[i+1] = array[i];
                    array[i] = temp;
                } 
            }
            print();
            indexPicked++;

        }
        System.out.println("Finished in: " + (System.currentTimeMillis() - time) + " ms");
    }
    
    public void print(){
        for(int j=0;j<array.length;j++){
            System.out.print(array[j] + ", ");
        }
        System.out.println();
    }
}