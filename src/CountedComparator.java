import java.util.Comparator;

public class CountedComparator<T> implements Comparator<T> {

	private Comparator<T> myComparator;
	private int myCompares;

	public CountedComparator(Comparator<T> obj) {
		myComparator = obj;
	}
	@Override
	public int compare(T a, T b) {
		myCompares++;
		return myComparator.compare(a,b);
	}
	public int getCompares(){
		return myCompares;
	}
	
	@Override
	public boolean equals(Object o) {
		throw new RuntimeException("equals");
	}

}
