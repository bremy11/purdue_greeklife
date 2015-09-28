
public class SAP{
    private Digraph G;
    public static void main(String[] args){
        In in1 = new In(args[0]);
        //In in2 = new In(args[1]);
        
        Digraph G = new Digraph(in1); 					//build diagraph
       
        SAP a = new SAP(G); 							//build sap from diagraph
        int[] ancestorList = In.readInts(args[1]);		//build input integer list
        //System.out.println(ancestorList[1]+  "afsadf");
            
        int distTo1[];
        int distTo2[];
        int len = 0;
        int temp;
        int ancestorLoc;
        for (int i = 0; i < ancestorList.length; i = i+2){
        												//loop through input list by twos
            //System.out.println(ancestorList[i] + " " + ancestorList[i+1]);
            /*
            bfs1 = new BreadthFirstDirectedPaths(G, ancestorList[i]);
            bfs2 = new BreadthFirstDirectedPaths(G, ancestorList[i+1]);
            len = 100000;
            ancestorLoc = -1;
            for(int j =0; j < G.V(); j++){
              
                if(bfs1.hasPathTo(j) && bfs2.hasPathTo(j)){
                    temp = bfs1.distTo[j] + bfs2.distTo[j];
                    //System.out.println(temp);
                    if(temp < len){
                        len = temp;
                        ancestorLoc = j;
                    }
                }
            }
            System.out.println(ancestorLoc);*/
            
            //print output
            System.out.println("sap = " +a.length(ancestorList[i],ancestorList[i+1]) +", ancestor = " + a.ancestor(ancestorList[i],ancestorList[i+1]));
        }
    }
    // constructor
    public SAP (Digraph G) {
        this.G = G;
    }

// return length of shortest ancestral path of v and w; -1 if no such path
    public int length (int v, int w){
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        //two bfs
        int len = Integer.MAX_VALUE;
        int temp;
        for(int j =0; j < G.V(); j++){		//loop through all vertexes
            
            if(bfs1.hasPathTo(j) && bfs2.hasPathTo(j)){		//if bothe connecteb by bfs add lengths and store max	
                temp = bfs1.distTo(j) + bfs2.distTo(j);
                //System.out.println(temp);
                if(temp < len){
                    len = temp;
                }
            }
        }
        if (len ==Integer.MAX_VALUE){		//if not connected
            return -1;
        }
        return len;
    }
        
// return a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor (int v, int w){
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int ancestorLoc = -1;
        int temp;
        int len = Integer.MAX_VALUE;
        for(int j =0; j < G.V(); j++){
            
            if(bfs1.hasPathTo(j) && bfs2.hasPathTo(j)){
                temp = bfs1.distTo(j) + bfs2.distTo(j);
                //System.out.println(temp);
                if(temp < len){		//store shortest path ancestor
                    ancestorLoc = j;
                }
            }
        }
        return ancestorLoc;
    }
}
