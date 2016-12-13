/**
 * Auto Generated Java Class.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Arrays;


public class GreekRank {
    
   String[] soroityNames;
   String[] fraternityNames;
   int[] fraterinityPairCount;
   HashMap<String, Integer> fratNameToIndex;
   Digraph G;
   
   
   int sororityLen;
   int fraternityLen;
   
   public GreekRank(){
       soroityNames = new String[100];
       fraternityNames = new String[100];
       fratNameToIndex = new HashMap<String, Integer>();
       
   }
    
    //builds internal graph structure, inFileName is the file name of the greek ranking data, 
    //inFileName is a comma seperated list, on each line first element is sorority subsequent elements are the fraternaties
   public String vertexToName(int v){
       if (v < 0){ return "Error, invalid vertex, bellow bounds " + v;}
       if (v < this.sororityLen){ return this.soroityNames[v];}
       if (v < this.fraternityLen+this.sororityLen){ return this.fraternityNames[v-this.sororityLen];}
       return "Error, invalid vertex, beyond bounds " + v;   
   }
   
    public void buildGraph(String inFileName){
        this.parseGreekNames(inFileName);
        this.generateGraph(inFileName);
    }
    
    public void generateGraph(String inFileName){
        this.G = new Digraph(this.sororityLen+this.fraternityLen);
        this.fraterinityPairCount = new int[fraternityLen];
        try{
            BufferedReader br = new BufferedReader(new FileReader(inFileName));
            String line;
            int sororityCount = 0;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                
                if (tokens.length >1)
                { 
                    assert this.soroityNames[sororityCount].equals(tokens[0]);
                    
                    //this.soroityNames[this.sororityLen++] = tokens[0];
                    //copy fraternity names
                    for(int i = 1; i < tokens.length; i++){
                        int curFrat = fratNameToIndex.get(tokens[i])+this.sororityLen;
                        //System.out.println("fratNameToIndex.get(tokens[i]): " + fratNameToIndex.get(tokens[i]));
                        fraterinityPairCount[fratNameToIndex.get(tokens[i])]++;
                        this.G.addEdge(sororityCount, curFrat);
                        this.G.addEdge(curFrat, sororityCount);
                    }
                    sororityCount++;
                }
            }
        } catch (Exception e) {
            System.out.println("error reading file in gernerateGraph, " + e);
        }
        
    }
    
    public void parseGreekNames(String inFileName){    
        try{
            BufferedReader br = new BufferedReader(new FileReader(inFileName));
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                
                if (tokens.length >1)
                { 
                    //copy sorority names
                    this.soroityNames[this.sororityLen++] = tokens[0];
                    //copy fraternity names
                    for(int i = 1; i < tokens.length; i++){
                        if (!this.fratNameToIndex.containsKey(tokens[i])) 
                        { 
                            this.fraternityNames[this.fraternityLen] = tokens[i];
                            this.fratNameToIndex.put(tokens[i], this.fraternityLen++);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error reading file, " + e);
        }
    }
    
    public static void main(String[] args) {  
        GreekRank greekLife = new GreekRank();
        greekLife.buildGraph("./greek1.txt");
        
        int[] minSorority = new int[greekLife.sororityLen];
        int min = 999999;
        int minSororityCount = 0;
        
        int[] sororityPairCount = new int[greekLife.sororityLen];
        
        for(int i = 0; i < greekLife.sororityLen; i++){
            for (int x : greekLife.G.adj(i)){
                sororityPairCount[i]++;
            }
        }
        
        for (int i = 0; i < greekLife.sororityLen; i++){
            // handle corner case of fraternaties on probation
            if (sororityPairCount[i] < min ){ min = sororityPairCount[i]; }
        }
        for (int i = 0; i < greekLife.sororityLen; i++){
            if (sororityPairCount[i] == min){ minSorority[minSororityCount++] = i; }
        }
        StdOut.printf("Min sororities:");
        for (int i = 0; i < minSororityCount; i++){
            StdOut.printf("%s, ", greekLife.vertexToName(minSorority[i]));
        }
        System.out.println("");
        
        BreadthFirstDirectedPaths[] bfs = new BreadthFirstDirectedPaths[greekLife.sororityLen+greekLife.fraternityLen];
        for (int i = 0; i < (greekLife.sororityLen+greekLife.fraternityLen); i++){
            bfs[i] = new BreadthFirstDirectedPaths(greekLife.G, i);
        }
        
        int[] fraternityDistToExit = new int[greekLife.fraternityLen];
        for (int i = 0; i < greekLife.fraternityLen; i++){
            fraternityDistToExit[i] = 99999;
            for (int j = 0; j < minSororityCount; j++){
                if (bfs[i+greekLife.sororityLen].hasPathTo(minSorority[j])){
                    //System.out.println(greekLife.vertexToName(minFrats[j]+greekLife.sororityLen));
                    if (bfs[i+greekLife.sororityLen].distTo(minSorority[j]) < fraternityDistToExit[i]){
                        fraternityDistToExit[i] = bfs[i+greekLife.sororityLen].distTo(minSorority[j]);
                    }
                }
            }
        }
        
        double fratAvgDist = 0;
        for (int i = 0; i < greekLife.fraternityLen; i++){
            fratAvgDist +=fraternityDistToExit[i];
            System.out.println("Fraternity: " + greekLife.vertexToName(i+greekLife.sororityLen) + ", fraternityDistToExit: " + fraternityDistToExit[i]);
        }
        fratAvgDist = fratAvgDist/(1.0*greekLife.fraternityLen);
        
        int[] sororityAdjSize = new int[greekLife.sororityLen];
        double[] sororityRank = new double[greekLife.sororityLen];
        for (int i = 0; i < greekLife.sororityLen; i++){
            for (int x : greekLife.G.adj(i)){
                //System.out.println(greekLife.vertexToName(i) + ": " + greekLife.vertexToName(x));
                if ( !greekLife.vertexToName(x).equals("snu")){
                    sororityRank[i] +=  fraternityDistToExit[x-greekLife.sororityLen];
                    sororityAdjSize[i]++;
                } else{
                    sororityRank[i] += fratAvgDist;
                    sororityAdjSize[i]++;
                    //System.out.println("Skipped");
                }
            }
            sororityRank[i] = sororityRank[i]/((double)sororityAdjSize[i]);
        }
        
        int[] fratAdjSize = new int[greekLife.fraternityLen];
        double[] fraternityRank = new double[greekLife.fraternityLen];
        for (int i = 0; i < greekLife.fraternityLen; i++){
            for (int x : greekLife.G.adj(i+greekLife.sororityLen)){
                fraternityRank[i] += sororityRank[x];
                fratAdjSize[i]++;
            }
            fraternityRank[i] = fraternityRank[i]/((double)fratAdjSize[i]);
        }
        
        ArrayIndexComparator sororityComparator = new ArrayIndexComparator(sororityRank);
        Integer[] sororityIndexes = sororityComparator.createIndexArray();
        Arrays.sort(sororityIndexes, sororityComparator);
        int rank = 1;
        System.out.println("---SORORITY RANK---");
        for ( int i = greekLife.sororityLen-1; i >= 0; i--){
            System.out.print(rank++ + ") " + greekLife.vertexToName(sororityIndexes[i]));
            System.out.println(", rank coefficient: " + sororityRank[sororityIndexes[i]]);
            
        }
        
        ArrayIndexComparator fratComparator = new ArrayIndexComparator(fraternityRank);
        Integer[] fratIndexes = fratComparator.createIndexArray();
        Arrays.sort(fratIndexes, fratComparator);
        rank = 1;
        System.out.println("\n---FRATERNITY RANK---");
        for ( int i = greekLife.fraternityLen-1; i >= 0; i--){
            //System.out.println("fratIndexes[i] + greekLife.sororityLen: " + (fratIndexes[i] + greekLife.sororityLen));
            System.out.print(rank++ + ") " + greekLife.vertexToName(fratIndexes[i] + greekLife.sororityLen) );
            System.out.println(", rank coefficient: " + fraternityRank[fratIndexes[i]]);
        }
        
        /*
        //calculate minimal fraternaties
        int[] minFrats = new int[greekLife.fraternityLen];
        int min = 999999;
        int minFratCount = 0;
        for (int i = 0; i < greekLife.fraternityLen; i++){
            // handle corner case of fraternaties on probation
            if (greekLife.fraterinityPairCount[i] < min && !greekLife.vertexToName(i+greekLife.sororityLen).equals("snu")){ min = greekLife.fraterinityPairCount[i]; }
        }
        for (int i = 0; i < greekLife.fraternityLen; i++){
            if (greekLife.fraterinityPairCount[i] == min && !greekLife.vertexToName(i+greekLife.sororityLen).equals("snu")){ minFrats[minFratCount++] = i; }
        }
        StdOut.printf("Min frats:");
        for (int i = 0; i < minFratCount; i++){
            StdOut.printf("%s, ", greekLife.vertexToName(minFrats[i]+greekLife.sororityLen));
        }
        System.out.println("");
        
        BreadthFirstDirectedPaths[] bfs = new BreadthFirstDirectedPaths[greekLife.sororityLen];
        for (int i = 0; i < greekLife.sororityLen; i++){
            bfs[i] = new BreadthFirstDirectedPaths(greekLife.G, i);
        }
        
        //determine dist from each sorority to each exit node
        int[] sororityDistToExit = new int[greekLife.sororityLen];
        for (int i = 0; i < greekLife.sororityLen; i++){
            sororityDistToExit[i] = 99999;
            for (int j = 0; j < minFratCount; j++){
                
                if (bfs[i].hasPathTo(minFrats[j]+greekLife.sororityLen)){
                    //System.out.println(greekLife.vertexToName(minFrats[j]+greekLife.sororityLen));
                    if (bfs[i].distTo(minFrats[j]+greekLife.sororityLen) < sororityDistToExit[i]){
                        sororityDistToExit[i] = bfs[i].distTo(minFrats[j]+greekLife.sororityLen);
                    }
                }
            }
        }
        for (int i = 0; i < greekLife.sororityLen; i++){
            System.out.println("Sorority: " + greekLife.vertexToName(i) + ", sororityDistToExit: " + sororityDistToExit[i]);
        }
        
        //determine intermediary fratrnity average distance
        int[] fratAdjSize = new int[greekLife.fraternityLen];
        double[] fraternityRank = new double[greekLife.fraternityLen];
        for (int i = 0; i < greekLife.fraternityLen; i++){
            for (int x : greekLife.G.adj(i+greekLife.sororityLen)){
                fraternityRank[i] += sororityDistToExit[x];
                fratAdjSize[i]++;
            }
            fraternityRank[i] = fraternityRank[i]/((double)fratAdjSize[i]);
        }
        
        //calculate sorority rank
        int[] sororityAdjSize = new int[greekLife.sororityLen];
        double[] sororityRank = new double[greekLife.sororityLen];
        for (int i = 0; i < greekLife.sororityLen; i++){
            for (int x : greekLife.G.adj(i)){
                if ( !greekLife.vertexToName(x+greekLife.sororityLen).equals("snu")){
                    sororityRank[i] +=  fraternityRank[x-greekLife.sororityLen];
                    sororityAdjSize[i]++;
                } else{
                    System.out.println("Skipped");
                }
            }
            sororityRank[i] = sororityRank[i]/((double)sororityAdjSize[i]);
        }
        
        //calculate fraternity rank
        fratAdjSize = new int[greekLife.fraternityLen];
        fraternityRank = new double[greekLife.fraternityLen];
        for (int i = 0; i < greekLife.fraternityLen; i++){
            for (int x : greekLife.G.adj(i+greekLife.sororityLen)){
                fraternityRank[i] += sororityRank[x];
                fratAdjSize[i]++;
            }
            fraternityRank[i] = fraternityRank[i]/((double)fratAdjSize[i]);
        }
        
        ArrayIndexComparator sororityComparator = new ArrayIndexComparator(sororityRank);
        Integer[] sororityIndexes = sororityComparator.createIndexArray();
        Arrays.sort(sororityIndexes, sororityComparator);
        int rank = 1;
        System.out.println("---SORORITY RANK---");
        for ( int i = 0; i < greekLife.sororityLen; i++){
            System.out.print(rank++ + ") " + greekLife.vertexToName(sororityIndexes[i]));
            System.out.println(", rank coefficient: " + sororityRank[sororityIndexes[i]]);
            
        }
        
        ArrayIndexComparator fratComparator = new ArrayIndexComparator(fraternityRank);
        Integer[] fratIndexes = fratComparator.createIndexArray();
        Arrays.sort(fratIndexes, fratComparator);
        rank = 1;
        System.out.println("\n---FRATERNITY RANK---");
        for ( int i = 0; i < greekLife.fraternityLen; i++){
            //System.out.println("fratIndexes[i] + greekLife.sororityLen: " + (fratIndexes[i] + greekLife.sororityLen));
            System.out.print(rank++ + ") " + greekLife.vertexToName(fratIndexes[i] + greekLife.sororityLen) );
            System.out.println(", rank coefficient: " + fraternityRank[fratIndexes[i]]);
        }
        */
        /*
        int s = 15;
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(greekLife.G, s);
        for (int v = 0; v < greekLife.G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%s to %d, %s (%d):  ", greekLife.vertexToName(s), v, greekLife.vertexToName(v), bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(greekLife.vertexToName(x));
                    else        StdOut.print("->" + greekLife.vertexToName(x));
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%s to %d, %s (-):  not connected\n", greekLife.vertexToName(s),v, greekLife.vertexToName(v));
            }
        }
        */
        
    }
    

}

