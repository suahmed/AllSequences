package AllSequences;


import java.util.*;

import org.apache.commons.lang.ArrayUtils;

public class StackDemo {
   public static void main(String args[]) {
      // creating stack
      Stack st = new Stack();
      LinkedList ll = new LinkedList();
  	  int inta[]= {12,13,14};
      LinkedList<Integer> newpath = new LinkedList<Integer>(Arrays.asList(ArrayUtils.toObject(inta)));
      LinkedList<Integer> newpath2 = new LinkedList<Integer>(newpath);
      newpath2.add(12);
      newpath2.add(15);
      newpath2.add(20);
      System.out.println("newpath2: "+newpath2);
      // populating stack
      st.push("Java");
      st.push("Source");
      st.push("code");
      
      // removing top object
      System.out.println("Removed object is: "+st.pop());
      
      // elements after remove
      System.out.println("Elements in stack: "+st);
      ll.addAll(st);
      ll.add("Code");
      ll.addAll(ll);
      System.out.println("Elements in linkedlist: "+ll);
      
   }    
}