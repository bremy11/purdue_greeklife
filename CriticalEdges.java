import java.util.*;

public class CriticalEdges{
    public static void main(String[] args) {  
        In in = new In(args[0]);
        
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST mst;
        int vertexCount = G.V();
        int[][] edgeCount = new int[vertexCount][vertexCount];
        int start;
        int end;
        MaxPQ<Edge> pq = new MaxPQ<Edge>();
        for(int i = 0; i < 50; i++){ //mst 
            for(Edge w : G.edges()){		//set random weight
                w.setWeight(StdRandom.uniform());
            }
            mst = new KruskalMST(G);
            for(Edge w :mst.edges()){
                
                start = w.either();
                end = w.other(w.either());
                
                
                edgeCount[start][end] +=1;		//increment hash map loc
            }
        }
        int topNum = Integer.parseInt(args[1]);
        
        Edge e;
        for (int i = 0; i < vertexCount; i++){
        	for(int j = 0; j <vertexCount ; j++){ //loop through hash map
        	if (edgeCount[i][j] >0){		//if weight has been set
        		e = new Edge(i, j, (double)edgeCount[i][j]);
        		pq.insert(e);
        	}
        	
        	}
        }
        System.out.println("Top edges:");
        for (int i = 0; (i < topNum) && (!pq.isEmpty()) ; i++){
        	e = pq.delMax();
        	System.out.println("Edge "+ e.either() + "-" + e.other(e.either()));
        }
        
        
        int count = 0;		//count of edges connected
        double n;
        double sum = 0.0;
        int[] inAdj = new int[vertexCount];
        int vtxCur;
        
        for(int v = 0; v < vertexCount; v++){//loop trough all edges
        	count = 0;
        	n = 0;
        	Arrays.fill(inAdj, 0);
        	for(Edge w : G.adj(v)){ //loop through adjcency list
        		inAdj[w.other(v)] = 1;	//mark to seen
        		n++;
        	}
        	for(Edge w : G.adj(v)){	//loop through adjcency list
        		vtxCur = w.other(v);
        		for (Edge g: G.adj(vtxCur)){	
        		//loop through adjcency list of adj list
        			if (inAdj[g.other(vtxCur)] == 1){
        				
        				count++;
        				
        			}
        		
        		}
        	}
        	
        	if (n-1 > 0){
        		sum+= count/n/(n-1);
        		
        		}
        }
        
        //print clustering coefficient
        //System.out.println(sum/vertexCount);
        System.out.format("Clustering coefficient of graph: %.6f\n", sum/vertexCount);
        
        
    }
      
      
    
}
