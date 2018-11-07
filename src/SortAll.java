import java.util.*;

public class SortAll {

	public  static interface Sorter {
		public <T extends Comparable<? super T>>
		void sort(List<T> list);

		default public <T extends Comparable<? super T>>
		void swap(List<T> list, int ia, int ib) {
			T temp = list.get(ia);
			list.set(ia,list.get(ib));
			list.set(ib, temp);
		}
	}

	public static class BubbleSort implements Sorter {

		@Override
		public <T extends Comparable<? super T>> 
		  void sort(List<T> list) {
			for(int j=list.size()-1; j > 0; j--) {
				for(int k=0; k < j; k++) {
					if (list.get(k+1).compareTo(list.get(k)) < 0) {
						swap(list,k,k+1);
					}
				}
			}
		}
	}

	public static class SelectionSort implements Sorter {

		@Override
		public <T extends Comparable<? super T>> 
		void sort(List<T> list) {

			for(int j=0; j < list.size()-1; j++) {
				int min = j;
				for(int k=j+1; k < list.size(); k++) {
					if (list.get(k).compareTo(list.get(min)) < 0){
						min = k;
					}
				}
				swap(list,min,j);
			}
		}
	}

	public static class RecursiveSelectionSort implements Sorter {

		private <T extends Comparable<? super T>> 
		int minIndex(List<T> list, int start) {
			int min = start;
			for(int k=start+1; k < list.size(); k++) {
				if (list.get(k).compareTo(list.get(min)) < 0) {
					min = k;
				}
			}
			return min;
		}
		private <T extends Comparable<? super T>> 
		void doSort(List<T> list, int start) {
			if (start >= list.size()) return;

			int dex = minIndex(list,start);
			swap(list,dex,start);
			doSort(list,start+1);
		}
		@Override
		public <T extends Comparable<? super T>> void sort(List<T> list) {
			doSort(list,0);
		}
	}

	public static class InsertionSort implements Sorter {

		@Override
		public <T extends Comparable<? super T>> void sort(List<T> list) {

			for(int k=1; k < list.size(); k++) {
				T hold = list.get(k);
				int loc = k;
				while (0 < loc && hold.compareTo(list.get(loc-1)) < 0) {
					list.set(loc, list.get(loc-1));
					loc--;
				}
				list.set(loc, hold);
			}
		}
	}

	public static class JavaUtilSort implements Sorter {
		@Override
		public <T extends Comparable<? super T>> void sort(List<T> list) {
			Collections.sort(list);
		}
	}


	public static class QuickSort implements Sorter {

		private <T extends Comparable<? super T>> 
		int pivot(List<T> list,int first, int last){

			T piv = list.get(first);
			int p = first;
			for(int k=first+1; k <= last; k++){
				if (list.get(k).compareTo(piv) <= 0){
					p++;
					swap(list,k,p);
				}
			}
			swap(list,p,first);
			return p;
		}

		public <T extends Comparable<? super T>> 
		void doQuick(List<T> list, int first, int last) {
			if (first >= last) return;

			int piv = pivot(list,first,last);
			doQuick(list,first,piv-1);
			doQuick(list,piv+1,last);
		}

		@Override
		public <T extends Comparable<? super T>> void sort(List<T> list) {
			doQuick(list,0,list.size()-1);
		}
	}

	public static class MergeSort implements Sorter {

		public <T extends Comparable<?super T>>
		void merge(List<T> list, List<T> a1, List<T> a2) {
			List<T> copy = new ArrayList<>(list);
			int len1 = a1.size();
			int dex1 = 0;
			int len2 = a2.size();
			int dex2 = 0;
			for(int j=0; j < copy.size(); j++) {
				if ( (dex2 == len2) || 
						(dex1 < len1 && 
								a1.get(dex1).compareTo(a2.get(dex2)) < 0)) {
					copy.set(j, a1.get(dex1));
				}
				else {
					copy.set(j, a2.get(dex2));
				}
			}
			list.clear();
			list.addAll(copy);
		}

		@Override
		public <T extends Comparable<? super T>> void sort(List<T> list) {
			if (list.size() <= 1) return;

			int half = list.size()/2;
			List<T> a1 = new ArrayList<>(list.subList(0, half));
			List<T> a2 = new ArrayList<>(list.subList(half, list.size()));
			sort(a1);
			sort(a2);
			merge(list,a1,a2);   
		}
	}

	public static <T extends Comparable<? super T>> 
	boolean inOrder(List<T> list){
		for(int k=1; k < list.size(); k++){
			if (list.get(k).compareTo(list.get(k-1)) < 0) {
				return false;
			}
		}
		return true;
	}

	public static <T extends Comparable<? super T>> 
	double timer(List<T> list, Sorter sorter) {
		double start = System.nanoTime();
		sorter.sort(list);
		if (! inOrder(list)) {
			throw new RuntimeException("not in order "+sorter.getClass().getName());
		}
		double end = System.nanoTime();
		return (end-start)/1e9;
	}

	public static void main(String[] args) {

		Sorter[] sorts = {
				new JavaUtilSort(),
				new QuickSort(),
				new MergeSort(),
				new InsertionSort(),
				new SelectionSort(),
				//new RecursiveSelectionSort(),
				new BubbleSort()
		};
		int first = 10000;
		int last = 100000;
		int increment = 10000;

		// print labels
		System.out.print("size\t");
		for(Sorter s: sorts) {
			System.out.printf("%s\t",
					s.getClass().getName().substring(8,14));
		}
		System.out.println("\n");

		for(int k=first; k <= last; k += increment){
			List<Integer> list = new ArrayList<>();
			for(int j=0; j < k; j++) {
				list.add((int) (Math.random()*10000));
			}
			Collections.shuffle(list);
			System.out.printf("%d\t", k);
			for(Sorter s : sorts) {
				List<Integer> copy = new ArrayList<>(list);
				double time = timer(copy,s);
				System.out.printf("%2.4f\t",time);
			}
			System.out.println();
		}
	}
}
