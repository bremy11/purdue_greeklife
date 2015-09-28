
public class CriticalVertices{

 public static void main(String[] args) {
  //In in = new In(args[0]);
  In in = new In("./circlesEWD.txt");
  EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
  int len = G.V();
  DijkstraSP sp;
  int vNum = Integer.parseInt(args[1]);
  double [] sum = new double[len];
  int [] spCount = new int[len];
  int prev;
  int size;
  for (int i =0; i < len; i++){     //loop through all vertecies
   sp = new DijkstraSP(G, i);
   
   for (int j = 0; j< len; j++){    //loop  all verticies
    sum[i] +=sp.distTo(j);
    
    for(DirectedEdge e : sp.pathTo(j)){    //loop through shortest path
     if (e.to() != j){ //if not an endpoint
      spCount[e.to()] +=1;
     }
    }
   }
  }
  Edge e;
  MaxPQ<Edge> between = new MaxPQ<Edge>();
  MinPQ<Edge> close = new MinPQ<Edge>();
  for(int i = 0; i <len; i++){   //build min and max pws for sorting output
            //i am using edges for simplicity in coding, v is vertex, w issnt used, and the weight is what is being counted and sorted by
   e = new Edge(i, 0, spCount[i]);
   between.insert(e);
   e = new Edge(i, 0, sum[i]);
   close.insert(e);
   //System.out.println(i+" "+sum[i]);
  }
  System.out.print("Vertices with high betweenness centrality: ");
  for (int k = 0; k < vNum && (!between.isEmpty()); k++){
   e = between.delMax();
   System.out.print(e.either());
   if(k+1 < vNum && (!between.isEmpty())){
    System.out.print(", ");
   }else{
    System.out.print("\n");
   }
  }
  System.out.print("Vertices with high closeness centrality: ");
  for (int k = 0; k < vNum && (!close.isEmpty()); k++){
   e = close.delMin();
   System.out.print(e.either());
   if(k+1 < vNum && (!close.isEmpty())){
    System.out.print(", ");
   }else{
    System.out.print("\n");
   }
  }
 }

}
