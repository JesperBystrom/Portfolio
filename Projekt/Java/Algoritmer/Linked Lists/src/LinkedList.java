
public class LinkedList<T> {
	
	private Link<T> first;
	
	public void add(Link<T> link){
		link.setNext(first);
		this.first = link;
	}
	
	public void add(T data, Link toPlace){
		Link<T> l = first;
		
		if(l != null){
			while(!l.getData().equals(toPlace.getData())){
				l = l.getNext();
			}
			Link<T> old = l.getNext();
			Link<T> nn = new Link<T>(data);
			l.setNext(nn);
			nn.setNext(old);
		}
	}
	
	public Link<?> remove(T data){
		if(isEmpty()) return null;
		Link<?> current = first;
		Link<?> previous = first;
		
		while(!current.getData().equals(data)){
			previous = current;
			current = current.getNext();
		}
		
		if(current.equals(first)){
			first = first.getNext();
		} else {
			previous.setNext(current.getNext());
		}
		
		return current;
	}
	
	public Link<T> getLink(T data){
		Link<T> l = first;
		while(!l.getData().equals(data)){
			l = l.getNext();
		}
		return l;
	}
	
	public void print(){
		Link<?> link = first;
		while(link != null){
			link.print();
			link = link.getNext();
		}
	}
	
	public boolean isEmpty(){
		return first == null;
	}
}
