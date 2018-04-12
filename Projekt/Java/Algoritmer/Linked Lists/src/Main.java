
public class Main {

	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		LinkedList<String> list = new LinkedList<String>();
		list.add(new Link<String>("Jesper"));
		list.add(new Link<String>("Roffe"));
		list.add(new Link<String>("Jessica"));
		list.add(new Link<String>("Joe"));
		list.add("Hej", list.getLink("Jessica"));
		//list.remove("Jesper");
		list.print();
	}

}
