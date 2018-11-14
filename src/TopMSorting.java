import java.util.*;

public class TopMSorting {
	private static final int SEED = 1234;
	private static final int MIN_LEN = 5;
	private static final int MAX_LEN = 15;
	
	public static List<String> getRandomStrings(int size){
		ArrayList<String> list = new ArrayList<>();
		String alph = "abcdefghijklmnopqrstuvwxyz";
		Random rand = new Random(SEED);
		for(int k=0; k < size; k++) {
			int start = rand.nextInt(MIN_LEN);
			int end = start + 1 + rand.nextInt(MAX_LEN);
			list.add(alph.substring(start,end));
		}
		
		return list;
	}
	
	public static List<String> sortTopM(List<String> list, int mSize, 
			                            Comparator<String> comp){
		List<String> copy = new ArrayList<>(list);
		Collections.sort(copy,comp.reversed());
		return copy.subList(0, mSize);
	}
	
	public static List<String> pqTopM(List<String> list, int mSize, 
			                          Comparator<String> comp) {
		PriorityQueue<String> pq = new PriorityQueue<>(comp);
		for(String s : list) {
			pq.add(s);
			if (pq.size() > mSize) {
				pq.remove();
			}
		}
		LinkedList<String> ret = new LinkedList<>();
		while (pq.size() > 0) {
			ret.addFirst(pq.remove());
		}
		return ret;
	}
	
	public static void main(String[] args) {
		final int SIZE = 10000000;
		Comparator<String> comp = Comparator.naturalOrder();
		double start = System.nanoTime();
		List<String> list = getRandomStrings(SIZE);
		double end = System.nanoTime();
		System.out.printf("creating %d random strings in %1.3f\n", list.size(),(end-start)/1e9);
		System.out.println("\ncomparison counts in thousands\n");
		System.out.println("size\tsort time/comp\tpq time/comp\n");
		for(int k=2; k <= 4096; k *= 2) {
			start = System.nanoTime();
			CountedComparator<String> scomp = new CountedComparator(comp);
			List<String> slist = sortTopM(list,k,scomp);
			end = System.nanoTime();
			double stime = (end-start)/1e9;
			start = System.nanoTime();
			CountedComparator<String> pcomp = new CountedComparator(comp);
			List<String> plist = pqTopM(list,k,pcomp);
			end = System.nanoTime();
			double ptime = (end-start)/1e9;
			if (! slist.equals(plist)) {
				System.err.println("error on plist ! = slist");
			}
			System.out.printf("%d\t%1.3f / %d\t%1.3f / %d\n", 
					          k,stime,scomp.getCompares()/1000,
					          ptime,pcomp.getCompares()/1000);
		}
	}
}
