import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.JFileChooser;

/*
 * Created on Oct 29, 2004
 * Modified on March 19, 2006 (Java 5)
 * Modified on November 9, 2016 (Java 8)
 */

/**
 * @author Owen Astrachan
 *
 */
public class PQDemo {
    private static JFileChooser ourChooser = new JFileChooser(".");
    
 

    public static void print(PriorityQueue<String> pq){
        while (pq.size() > 0){
            System.out.println(pq.remove());
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException{
        
        int retval = ourChooser.showOpenDialog(null);

        if (retval == JFileChooser.APPROVE_OPTION){
            File f = ourChooser.getSelectedFile();  
            Scanner s = new Scanner(f);
            ArrayList<String> list = new ArrayList<>();
            
            while (s.hasNext()){
                list.add(s.next());
            }
            System.out.println("read # words = "+list.size());
            PriorityQueue<String> pq = new PriorityQueue<>();
            pq.addAll(list);
            //pq.stream().forEach(e->System.out.println(e));
            
            print(pq);
            
            System.out.println("\n reversed \n");
            PriorityQueue<String> rpq = new PriorityQueue<>(10, Collections.reverseOrder());
            rpq.addAll(list);
            
            //rpq.stream().forEach(e->System.out.println(e));
            print(rpq);
            
        }
        else {
            System.out.println("operation cancelled");   
        }
        System.exit(0);
    }
}
