import java.util.*;


public class PersonSorter {
    private static class Person implements Comparable<Person> {
        String first;
        String last;
        public Person(String s) {
            first = s.split(" ")[0];
            last = s.split(" ")[1];
        }
        public String getLast(){
            return last;
        }
        public String getFirst(){
            return first;
        }
      
        public String toString(){
            return first + " " + last;
        }
        @Override
        public int compareTo(Person p){
            int diff = last.compareTo(p.last);
            if (diff != 0) return diff;
            return first.compareTo(p.first);
        }
    }
    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Sam Smith"));
        list.add(new Person("Alex Jones"));
        list.add(new Person("Zach Jones"));
        list.add(new Person("Chris Donald"));
        list.add(new Person("Donald Chris"));
        list.add(new Person("Alex Smith"));
        Collections.sort(list);
        System.out.println(list);
        System.out.println("------");
        Comparator<Person> comp = 
                Comparator.comparing(Person::getFirst)
                          .thenComparing(Person::getLast);
        Collections.sort(list,comp);
        System.out.println(list);
        System.out.println("------");
        comp = Comparator.comparing(Person::getLast)
        		             .thenComparing(Person::getFirst);
        Collections.sort(list,comp);
        System.out.println(list);
    }
}
