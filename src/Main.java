import java.util.Random;


public class Main {
	  /**
	   * 
	   * @param args
	   * -p indicates whether to print the sequences
	   * -m indicates to use memoization. otherwise only non-memoization technique is applied
	   * -n NUM : the number of events to generate. default is 20
	   * -el VALUE : input event length. default is 20
	   * -r		: random length of event, otherwise fixed length
	   * -wl VALUE : input window length. default is 1000
	   * -a1	: use CES fusion, this is the default algorithm
	   * -a2	: use CES non-dymanic
	   * -a3	: use CES dynamic
	   */
	  
	  
	  public static void main(String[] args) {
		  int n=20;
		  boolean print=false;
		  boolean memoization=false;
		  int eventLength=20;
		  int windowLength=1000;
		  boolean alg1=false; // ces fusion
		  boolean alg2=false; // ces non-dynamic
		  boolean alg3=false; // ces dynamic
		  boolean randomlength=false;
		  
		  for (int i=0; i< args.length; i++){
			  if (args[i].equals("-p")) print=true;
			  if (args[i].equals("-a1")) alg1=true;
			  if (args[i].equals("-a2")) alg2=true;
			  if (args[i].equals("-a3")) alg3=true;
			  if (args[i].equals("-r")) randomlength=true;
			  if (args[i].equals("-m")) memoization=true;
			  if (args[i].equals("-n")) n=Integer.parseInt(args[++i]);
			  if (args[i].equals("-el")) eventLength=Integer.parseInt(args[++i]);
			  if (args[i].equals("-wl")) windowLength=Integer.parseInt(args[++i]);
			  //if (args[i].charAt(0)!='-') n=Integer.parseInt(args[i]);
		  }
		  if(alg1==false && alg2==false && alg3==false){
			  alg1=true;
		  }
		  GeenerateAndTestEventProcessor(n, print, alg1, alg2, alg3, randomlength, memoization,eventLength,windowLength);
	   }
	  
	  /**
	   * randomly create the events and apply sequence construction technique based on the input
	   * @param n : number of events
	   * @param p : print the sequences. otherwise only print the number of sequence
	   * @param alg1 : use CES Fusion
	   * @param alg2 : use CEStream non-dynamic
	   * @param alg3 : use CEStream dynamic
	   * @param randomlength : generate random length events, otherwise fixed length
	   * @param memoizaton : apply memoization. otherwise only do non-memoization
	   * @param eventlength : length of an event
	   * @param windowLength : length of the window
	   */
			  
	  
	  public static void GeenerateAndTestEventProcessor(int n, boolean p, 
			  boolean alg1, boolean alg2, boolean alg3, boolean randomlength,
			  boolean memoizaton, int eventlength, int windowLength){
	      int N = n;

	      // generate N random intervals and insert into data structure
		  EventProcessor<String> ep=new EventProcessor<String>();//sourceNode,targetNode);
		  long startTime = System.currentTimeMillis();
		  long nanoStartTime = System.nanoTime();
		  Random rndNumbers = new Random(System.currentTimeMillis());
	      for (int i = 1; i <= N; i++) { 
	          //int low  = (int) (Math.random()  * (windowLength-eventlength)); // was N * 25 // was 1000
	          int low  = rndNumbers.nextInt(windowLength-eventlength); // was N * 25 // was 1000
	          int high;
	          if(randomlength==false){
	        	  high = eventlength + low ; // (int) (Math.random() * eventlength) + low; // was 50
	          }
	          else{
	        	  high = (int) (Math.random() * eventlength) + low; // was 50
	          }
	          
	          Node<String> node = new Node<String>(new Interval1D(low, high),""+i);
	          if(!ep.contains(node)){
		          System.out.print(node + " ");
		          if (i%5==0) System.out.println();
		          ep.add(node);
	          }
	          else{
	        	  i--;
	          }
	      }
	      long stopTime = System.currentTimeMillis();
		  long nanoStopTime = System.nanoTime();
	      long elapsedTime = stopTime - startTime;
	      long nanoElapsedTime = nanoStopTime - nanoStartTime;
	      System.out.println("\nDAG Construction Time: " + elapsedTime +" ms");
	      System.out.println("DAG Construction Time: " + nanoElapsedTime +" ns");
	      
	      //HybridTest(ep,1,p);

	      //HybridTest(ep,5,p);

	      if(alg1==true)
	    	  HybridTest(ep,10,p);

	      //HybridTest(ep,15,p);
	      /*
	      for(int i=2;i<=N/eventlength;i++){
	    	  
	    	  int limit=(N-5)/i;
	    	  HybridTest(ep,limit,p);
	      }
	      */

	      /*
	      HybridTest(ep,27,p);
	      HybridTest(ep,18,p);
	      HybridTest(ep,13,p);
	      HybridTest(ep,11,p);
	      HybridTest(ep,9,p);
	      HybridTest(ep,8,p);
	      HybridTest(ep,7,p);
	      HybridTest(ep,6,p);
	      HybridTest(ep,5,p);
	      HybridTest(ep,4,p);
	      */
	      // Example 2 with limit 14
	      
	      if(alg2==true){
	      // without memoization
	    	  NoMemTest(ep,p);
	      }  
	      
	      // with memoization
	      //if(memoizaton){
	      if(alg3==true){
	    	  FullMemTest(ep,p);
	      }
	      
	      
	  }

	  
	  private static void FullMemTest(EventProcessor<String> ep, boolean p) {
		  System.out.println();
	      System.out.println("----------------------------------------");
		  long startTime = System.currentTimeMillis();
		  long nanoStartTime = System.nanoTime();
	      if(p){
	    	  System.out.println("\n\nDFS (Memoization) Sequences: ");
	    	  ep.graph.printPaths();
	      }
	    	  
	      else
	          ep.graph.printPathCount();
	      
	      long stopTime = System.currentTimeMillis();
		  long nanoStopTime = System.nanoTime();
	      long elapsedTime = stopTime - startTime;
	      long nanoElapsedTime = nanoStopTime - nanoStartTime;
	      System.out.println("DFS (Memoization) Time: " + elapsedTime +" ms");
	      System.out.println("DFS (Memoization) Time: " + nanoElapsedTime +" ns");
		
	  }

	  private static void NoMemTest(EventProcessor<String> ep, boolean p) {
	      System.out.println();
	      System.out.println("----------------------------------------");
		  long startTime = System.currentTimeMillis();
		  long nanoStartTime = System.nanoTime();
	      if(p){
	    	  System.out.println("\n\nDFS (No Memoization) Sequences: ");
	    	  ep.graph.printPathsNoDyn();
	      }
	      else
	    	  ep.graph.printPathCountNoDyn();
	      
	      long stopTime = System.currentTimeMillis();
		  long nanoStopTime = System.nanoTime();
	      long elapsedTime = stopTime - startTime;
	      long nanoElapsedTime = nanoStopTime - nanoStartTime;
	      System.out.println("DFS (No Memoization) Time: " + elapsedTime +" ms");
	      System.out.println("DFS (No Memoization) Time: " + nanoElapsedTime +" ns");
		
	  }

	public static void HybridTest(EventProcessor<String> ep, int minSize, boolean print) {

	    System.out.println();
	    System.out.println("----------------------------------------");
		  long startTime = System.currentTimeMillis();
		  long nanoStartTime = System.nanoTime();
	      // call the 0-cut partitioning with limit 8
	      //ep.findCuts(8, 0);
	      ep.findCuts(minSize, 0);
	      
	      
	      // call DFS for each of the partition and record # of sequences
	      ep.applyDfsOnParts();
	      /*
	      // if a partition is too big discard all and redo from beginning
	      ep.validateParts();
	      // apply optimal partitioning algorithm
	      ep.searchOptimal();
	      */
	      // call recursive sequence construction on optimal partitions and record time
	      // DFS with memoization on parts and generate sequences recursively without memoization
	      ep.genSequencesFromParts(print);
	      long stopTime = System.currentTimeMillis();
		  long nanoStopTime = System.nanoTime();
	      long elapsedTime = stopTime - startTime;
	      long nanoElapsedTime = nanoStopTime - nanoStartTime;
	      System.out.println("Partition Seq Generation Time: " + elapsedTime +" ms");
	      System.out.println("Partition Seq Generation Time: " + nanoElapsedTime +" ns");

	}

}
