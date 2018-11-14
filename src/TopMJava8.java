import java.util.*;
import java.util.stream.*;

public class TopMJava8 {
    static int counter;
    static ArrayList<Integer> nums = new ArrayList<>();
    static PriorityQueue<Integer> pq = new PriorityQueue<>();
    static List<Integer> top1 = new ArrayList<>();
    static List<Integer> top2 = new ArrayList<>();
    static int TOP_LIMIT = 50;
    
    public static class LimitedPQ<T> extends PriorityQueue<T> {
    		private int mySize;
    		
    		public LimitedPQ(int size) {
    			mySize = size;
    		}
    		
    		public LimitedPQ(int size, Comparator<T> comp) {
    			super(comp);
    			mySize = size;
    		}
    		
    		@Override
    		public boolean add(T elt) {
    			boolean value = super.add(elt);
    			if (size() > mySize) {
    				remove();
    				//System.out.println("removing at "+size());
    			}
    			return value;
    		}
    }
    
    public static void main(String[] args){
        
        long NUMS = 10000000; //10000000000L;
        int low = 20;
        int high = 100000;
        Random r = new Random(1234);
        IntStream is = r.ints(low,high);
        List<Integer> list = is.limit(NUMS)
        		                   .mapToObj(Integer::valueOf)
        		                   .collect(Collectors.toList());
        
        Collections.shuffle(list);

        double start = System.nanoTime();    
        ArrayList<Integer> nums = new ArrayList<>(list);
        Collections.sort(nums);      
        top1 = nums.subList(nums.size()-TOP_LIMIT,nums.size());
        double end = System.nanoTime();
        double time = (end-start)/1e9;
        
        System.out.println(top1.size());
        System.out.printf("time = %1.3f\n",time);
    
        start = System.nanoTime();
        LimitedPQ<Integer> lpq = new LimitedPQ<>(TOP_LIMIT);
        list.stream()
            .forEach(e->lpq.add(e));
          
        while (lpq.size() > 0) {
            top2.add(lpq.remove());
        }
        end = System.nanoTime();
        time = (end-start)/1e9;
        System.out.println(top2.size());
        System.out.printf("time = %1.3f\n",time);
        
        if (! top1.equals(top2)) {
        		System.out.println("lists differ");
        }
    }
}
