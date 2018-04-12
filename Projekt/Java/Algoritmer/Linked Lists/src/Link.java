
public class Link<T> {
	
	private Link<T> next;
	private T data;
	
	public Link(T data){
		this.data = data;
	}
	
	public Link<T> getNext(){
		return next;
	}
	
	public void setNext(Link link){
		this.next = link;
	}
	
	public void print(){
		System.out.println(data);
	}
	
	public T getData(){
		return data;
	}
}
