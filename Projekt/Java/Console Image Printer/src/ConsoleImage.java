
public class ConsoleImage {

	private int[] pixels;
	private int w;
	private int h;
	
	public ConsoleImage(int[] pixels, int w, int h){
		this.w = w;
		this.h = h;
		this.pixels = pixels;
	}
	
	public void render(){
		for(int y=0;y<h;y++){
			for(int x=0;x<w;x++){
				if(pixels[x+y*w] != 0xFFFF0000){
					System.out.print("_");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}
