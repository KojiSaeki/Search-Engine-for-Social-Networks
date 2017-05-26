/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socnet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class SearchListener implements ActionListener {

    public String query;
    String qword;
    HashMap<String, Set<String>> miniIndex = new HashMap<>();
 //   ArrayList<DoFre> totalUsers = new ArrayList<>(); //int UserId, double frequ(scor)

    HashMap<String,Double> totalUsers = new HashMap<>();
    
    ValueComparator bvc =  new ValueComparator(totalUsers);
    TreeMap<String,Double> totalUsersSorted = new TreeMap<>(bvc);
    
    ValueComparator2 bvc2 =  new ValueComparator2(totalUsers);
    TreeMap<String,Double> totalUsersSorted2 = new TreeMap<>(bvc2);

    @Override
    public void actionPerformed(ActionEvent e) {
        query = MainFrame.getTxt();
        if(computeDistanceListener.s1n.equals("CosDist")){
              cosineSimilarity();
        }
        else if(computeDistanceListener.s1n.equals("Euklid")){
            
            euklideianDistance();
        }
         
    }
    
    
    public void computeCosim(){

    }
    
    public void computeEuclid(){

    }
    
     public void euklideianDistance(){
         
         totalUsers.clear();
         totalUsersSorted2.clear();
         MainFrame.d1.getDataVector().removeAllElements();
         
         String qword;
         Double QWtfidf=0.;
         Double minus=0.;
     
       
          ArrayList<DoFre> usrs;
         
          HashMap<String,Integer> Aquery = new HashMap<>(); 
         
         Scanner qw = new Scanner(query);
        
        while(qw.hasNext()){
            
            qword = qw.next();
            
            if(Aquery.containsKey(qword)){
                
                Aquery.put(qword, Aquery.get(qword)+1);
            }
            else{
                Aquery.put(qword, 1);
            }
      

        }
        
        //----------------------------------
        
        for (String key : Aquery.keySet()) {                  
                       QWtfidf = computeDistanceListener.tfIdf(Aquery.get(key), SocNet.tags.get(key).size(),key);

                       System.out.println("Qtfidf = "+QWtfidf);
                       
                       usrs = SocNet.tags.get(key);
                       
                       for(int i=0;i<usrs.size();i++){
                           
                           if(totalUsers.containsKey(String.valueOf(usrs.get(i).UserId))){
                             //  System.out.println("*********frequ = "+totalUsers.get(String.valueOf(usrs.get(i).UserId))+" + "+computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size())+" ::: usrs,frequ = "+usrs.get(i).frequ+" usrs.size = "+usrs.size());
                               totalUsers.put(String.valueOf(usrs.get(i).UserId),totalUsers.get(String.valueOf(usrs.get(i).UserId))+ Math.pow(computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size(),key)-QWtfidf,2) );
                             //  System.out.println("@@@@@@@frequ = "+totalUsers.get(String.valueOf(usrs.get(i).UserId)));
                                minus = computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size(),key);
                           }
                           else{
                               totalUsers.put(String.valueOf(usrs.get(i).UserId), Math.pow(computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size(),key)-QWtfidf,2));
                             //  System.out.println("+++++frequ = "+computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size())+" Scor = "+QWtfidf);
                             //  System.out.println("^^^^^^frequ = "+usrs.get(i).frequ+" Scor = "+QWtfidf+"  ursr.size = "+usrs.size());
                               minus = computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size(),key);
                           }
                           
                           SocNet.usersCosDist[usrs.get(i).UserId]-=Math.pow(minus,2);
                       }
                       
                       
          }
        
        for (String key : totalUsers.keySet()) {
                    
                          totalUsers.put(key, Math.sqrt(totalUsers.get(key)+(SocNet.usersCosDist[Integer.parseInt(key)])));
                        // System.out.println("Qnorm = "+Qnorm+"  dianisma 4 = "+SocNet.usersCosDist[Integer.parseInt(key)]);
                    
                }
        
        
        totalUsersSorted2.putAll(totalUsers);
        
        for(Map.Entry<String,Double> entry : totalUsersSorted2.entrySet()) {
                              String key = entry.getKey();
                              Double value = entry.getValue();
                               MainFrame.d1.addRow(new Object[]{key,value});
                 }
     }
    
    
    public void cosineSimilarity(){
        totalUsers.clear();
        totalUsersSorted.clear();
        MainFrame.d1.getDataVector().removeAllElements();
        ArrayList<DoFre> usrs;
         
        HashMap<String,Integer> Aquery = new HashMap<>(); //contains word of query and its frequency in the query
        String qword;
        Double Qnorm=0.;
        Double QWtfidf;
       
        Scanner qw = new Scanner(query);
        
        while(qw.hasNext()){
            
            qword = qw.next();
            
            if(Aquery.containsKey(qword)){
                
                Aquery.put(qword, Aquery.get(qword)+1);
            }
            else{
                Aquery.put(qword, 1);
            }
        }
            for (String key : Aquery.keySet()) {                   
                       QWtfidf = computeDistanceListener.tfIdf(Aquery.get(key), SocNet.tags.get(key).size(),key);
                       Qnorm+= Math.pow(QWtfidf,2);
                       System.out.println("Qtfidf = "+QWtfidf);
                       usrs = SocNet.tags.get(key);
                       
                       for(int i=0;i<usrs.size();i++){
                           
                           if(totalUsers.containsKey(String.valueOf(usrs.get(i).UserId))){
                             //  System.out.println("*********frequ = "+totalUsers.get(String.valueOf(usrs.get(i).UserId))+" + "+computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size())+" ::: usrs,frequ = "+usrs.get(i).frequ+" usrs.size = "+usrs.size());
                               totalUsers.put(String.valueOf(usrs.get(i).UserId),totalUsers.get(String.valueOf(usrs.get(i).UserId))+ computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size(),key)*QWtfidf );
                             //  System.out.println("@@@@@@@frequ = "+totalUsers.get(String.valueOf(usrs.get(i).UserId)));
                           }
                           else{
                               totalUsers.put(String.valueOf(usrs.get(i).UserId), computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size(),key)*QWtfidf);
                             //  System.out.println("+++++frequ = "+computeDistanceListener.tfIdf(usrs.get(i).frequ, usrs.size())+" Scor = "+QWtfidf);
                             //  System.out.println("^^^^^^frequ = "+usrs.get(i).frequ+" Scor = "+QWtfidf+"  ursr.size = "+usrs.size());
                           }
                       }
                       
                       
          }
            //-----------------------------adding the normalization
            
                for (String key : totalUsers.keySet()) {
                          totalUsers.put(key, totalUsers.get(key)/(Math.sqrt(Qnorm)*Math.sqrt(SocNet.usersCosDist[Integer.parseInt(key)])));
                         System.out.println("Qnorm = "+Qnorm+"  dianisma 4 = "+SocNet.usersCosDist[Integer.parseInt(key)]);
                }
            
            
            
            //-------------------------sorting
            
            totalUsersSorted.putAll(totalUsers);
			
            for(Map.Entry<String,Double> entry : totalUsersSorted.entrySet()) {
                              String key = entry.getKey();
                              Double value = entry.getValue();
                               MainFrame.d1.addRow(new Object[]{key,value});
                    
                 }
            System.out.println("results :"+totalUsersSorted);
            
            
}              

    public void printMiniIndex(){
        System.out.println("AAAAAAAAAA");
        Iterator<String> keySetIterator = miniIndex.keySet().iterator();

            while(keySetIterator.hasNext()){
                String key = keySetIterator.next();
                System.out.println("\nkey: " + key + " value: " + miniIndex.get(key).size());
            } 
    }

    public void fillV(){
        
                    Iterator it = SocNet.tags.entrySet().iterator();
                   while (it.hasNext()) {
                                Map.Entry pairs = (Map.Entry)it.next();
                                
                                String tmp0 = (String) pairs.getKey();
                                ArrayList<Double> tmp1 = (ArrayList<Double>) pairs.getValue();
                                it.remove(); // avoids a ConcurrentModificationException
        
                   }
    }
    
    
    public void CalculateIdf(String qword1){
        int N = SocNet.tags.size();
        
      
        Double tf = 0.0;
        ArrayList<DoFre> gmp = SocNet.tags.get(qword);
        System.out.println();
        if(gmp!= null){
        Double idf = Math.log(N/(gmp.size()));
                for(int i=0;i<gmp.size();i++){
                    tf = 1+Math.log(gmp.get(i).frequ);

                    System.out.print("TfIdf = "+tf*idf+" , ");
                }
      
        }
        else{
            System.out.print("Wrong!!");
        }
        
        System.out.println();
    }
    public void FillVit(String qword) throws FileNotFoundException{
        
         //open and read the file
        String line="";
        String tmp = "";
        String userId1 = "";
        String tag = ""; 
        String freq = "";

        ArrayList<DoFre> tags1 = new ArrayList<>();
        Scanner x=new Scanner(new File("user_tags2.txt"));
        
        while(x.hasNextLine()){ //x.hasNextLine()
              int i=0;
              line = x.nextLine();  
              //System.out.println(line);
              while(i<line.length()){  //AAAAAAAAAAAAAAAA
                  if(line.charAt(i)==':'){
                      
                      userId1 = tmp;
                      
                      if(SocNet.tags.get(qword).contains(userId1)){
                          System.out.println(userId1+" YOLO ");
                          tmp="";
                          break;
                      }
                      else{
                          System.out.println("NO "+userId1+" ");
                          userId1 = "";
                          tmp = "";
                          break;
                      }
                  }
                  
                  else{
                      tmp+=line.charAt(i);
                      i++;
                  }
                  
                  
              }
              tags1.clear();
          }
        
        
    }
    
    
    
    
    
    public double CalculateVlen(){
        return 0;
    }
    
    
}


class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}

class ValueComparator2 implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator2(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return 1;
        } else {
            return -1;
        } // returning 0 would merge keys
    }
}
