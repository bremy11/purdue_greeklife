
import java.io.BufferedReader;
import java.io.FileReader;

public class formatcsv{
    
    public static boolean isin(String a, String[] in) {
        for(int i =0; i < in.length; i++){
            //System.out.println(in[i]);
            if(in[i] == null) {break;}
            if(in[i].equals(a)){return true;}
        }
        return false;
    }
    
    public static void main(String[] args) {  
        String[] names = new String[100];
        int icount = 0;
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("./greek1.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                
                if (tokens.length >1&& !isin(tokens[0], names)) { names[icount++] = tokens[0];}
                for(int i = 1; i < tokens.length; i++){
                    if (!isin(tokens[i], names)) { names[icount++] = tokens[i];}
                }
            }
        } catch (Exception e) {
            System.out.println("error reading file" + e);
        }
        for(int i =0; i < icount; i++){
            System.out.println(names[i]);
        }
    }
}