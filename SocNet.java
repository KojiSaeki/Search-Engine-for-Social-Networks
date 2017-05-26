package socnet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.ProgressMonitor;

public class SocNet {
    
    static HashMap<String, ArrayList<DoFre>> tags;
    static double[] usersCosDist;
    static int numUsers=0;
    static int lastUser;
    
    public static void main(String[] args) throws FileNotFoundException {
        
        tags = new HashMap<>();
        MainFrame m1 = new MainFrame();
        m1.setLocationRelativeTo(null);
        m1.setVisible(true);
        m1.progressMonitor();
        createIndex();   
        System.out.println("---------------------------------------------------------------------------->");
        System.out.println(numUsers);
        MainFrame.endMonitor();
    }
    
    static public void computeDistance(){

    }

    
    static public void createIndex() throws FileNotFoundException{
        //open and read the file
        String line="";
        String tmp = "";
        String userId1 = "";
        String tag = ""; 
        String freq = "";
        String lastUser1="";

        ArrayList<DoFre> tags1 = new ArrayList<>();
        Scanner x=new Scanner(new File("user_tags2.txt"));
        
        while(x.hasNextLine()){ 
              int i=0;
              line = x.nextLine();  
              numUsers++;
              MainFrame.pm2.setValue(numUsers);
              while(i<line.length()){  
                  if(line.charAt(i)==':'){
                      
                      userId1 = tmp;
                      
                      
                      
                      System.out.println(userId1+" ");
                      tmp="";
                      i++;
                  }
                  else if(line.charAt(i)==','){
                      
                      tag = tmp;
                      tmp="";
                      i++;
                  }
                  else if(line.charAt(i)==')'){
                      
                      freq = tmp;
                      tmp="";
                      i++;
                      
                      if(tag==""){
                          
                      }
                      else{
                      DoFre g1 = new DoFre(tag, Double.parseDouble(freq));
                      tags1.add(g1);
                      }
                  }
                  else if(line.charAt(i)=='('){
                      i++;
                  }
                  else{
                      tmp+=line.charAt(i);
                      i++;
                  }
              }

              convInd(Integer.parseInt(userId1),tags1);
              tags1.clear();
          }
        
        lastUser = Integer.parseInt(userId1);
       System.out.println("LAST user = "+lastUser);
        
    }
    
    public static ArrayList<String> copyArray(ArrayList<String> usr,ArrayList<DoFre> tags1){
        
        for(int i=0;i<tags1.size();i++){
            
            usr.add(tags1.get(i).tag);
            
        }
        
        
        return usr;
    }
    
    
    static public String darkMatter(String tag){
        
        if (tag.matches(".*[a-zA-Z1-9].*")){
        
       tag =  tag.replaceAll(("[^a-zA-Z1-9]"), "-");
       
       while(Pattern.compile("[-][-]").matcher(tag).find()){
       tag =  tag.replaceAll(("[-][-]*"), "-");
       
      
       }
       tag =  tag.replaceAll(("[/-]$"), "");
       tag =  tag.replaceAll(("^[/-]"), "");
       
       
       return tag;
    } 
        return "";
    }
    
    static public void convInd(int userId,ArrayList<DoFre> tagFre){
        ArrayList<DoFre> tmp;
        int size1 = tagFre.size();
        for(int i=0;i< size1;i++){
            
            if(tags.containsKey(tagFre.get(i).tag)){
                
                
                tmp = tags.get(tagFre.get(i).tag);
                tmp.add(new DoFre(userId,tagFre.get(i).frequ));
                tags.put(tagFre.get(i).tag, tmp);
                
            }
            else{
                
                if(tagFre.get(i).tag != ""){
                ArrayList<DoFre> tmp1 = new ArrayList<>();
                tmp1.add(new DoFre(userId,tagFre.get(i).frequ));
                tags.put(tagFre.get(i).tag, tmp1);
                }
            }
            
            
        }
        
        
    }
    
    static public void printIndex(){
        System.out.println("AAAAAAAAAA");
        Iterator<String> keySetIterator = tags.keySet().iterator();

            while(keySetIterator.hasNext()){
                String key = keySetIterator.next();
                System.out.println("\nkey: " + key + " value: " + tags.get(key).size());
                
                for(int i=0;i<tags.get(key).size();i++){
                    
                    System.out.print(tags.get(key).get(i).UserId+"--> ");
                }
            }
        
        
    }
    
    
    static private void print_lexico() {

        Set<String> key = tags.keySet();

        for (String i : key) {
            System.out.print(i + "  --> ");

            for (int g = 0; g <= tags.get(i).size() - 1; g++) {
                System.out.print(tags.get(i).get(g) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
}

