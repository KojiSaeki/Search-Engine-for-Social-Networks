/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socnet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author Giorgos
 */
public class computeDistanceListener implements ActionListener {

    static JComboBox w1,w2,w3,s1;
    static String w1n,w2n,w3n,s1n;
    public computeDistanceListener(JComboBox w1,JComboBox w2,JComboBox w3,JComboBox s1){
        
        computeDistanceListener.w1 = w1;
        computeDistanceListener.w2 = w2;
        computeDistanceListener.w3 = w3;
        computeDistanceListener.s1 = s1;

   }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       System.out.println("Compute!");
       w1n = String.valueOf(w1.getSelectedItem()) ;
       w2n = String.valueOf(w2.getSelectedItem()) ;
       w3n = String.valueOf(w3.getSelectedItem()) ;
       s1n = String.valueOf(s1.getSelectedItem()) ;
       
       System.out.println(w1n+" - "+w2n+" - "+w3n+" - "+s1n);
       
       weightCom();
       
       System.out.println("The end");
       System.out.println("Distance OF user 4!! = "+SocNet.usersCosDist[4]);
       System.out.println(SocNet.tags.get("herbraiser").size());
       
       MainFrame.searchButton.setEnabled(true);
       MainFrame.mode.setText(" Mode: "+w1n+"-"+w2n+"-"+w3n+"-"+s1n+" ");
    }
    
    
    static public void weightCom(){
        int q=0;
       // int mapSize = tags.size();
        SocNet.usersCosDist = new double[SocNet.lastUser+1];
        int j=0;
        Double mxFreq=0.;
        Double agFreq=0. ;
        System.out.println(SocNet.tags.size());
        for(String key : SocNet.tags.keySet()){
            
            if(w1n.equals("Augmented")){
            mxFreq = maxFreq(key);
            }
            else if(w1n.equals("Log Avg")){
                agFreq = avgFreq(key);
            }
         //   int keyTmp = Integer.parseInt(key);
            ArrayList<DoFre> keyList = SocNet.tags.get(key);
            
            j++;
            
         //   System.out.println("jj = "+j);
            
            for(int i=0;i<keyList.size();i++){
                
               SocNet.usersCosDist[keyList.get(i).UserId]+=Math.pow(tfIdf(keyList.get(i).frequ,keyList.size(),key,mxFreq,agFreq),2);
               q++;
            }
          //  System.out.println(j+":"+key+" --> "+(double)(numUsers/keyList.size())+" -->"+Math.log10((double)(numUsers/keyList.size())));
        }
        System.out.println("Q="+q);
    }
    
    static public double tfIdf(double freq,int keyListSize,String word,Double mxFreq,Double agFreq){
       double tf=0,idf=0;
        if(w1n.equals("Natural")){
                tf = freq;
        }
        else if(w1n.equals("Logarithm")){
            tf = 1+Math.log10((double)freq);
        }
        else if(w1n.equals("Augmented")){
           // tf=0.5+((0.5*Math.log10(freq))/(maxFreq(word)));
            tf=0.5+((0.5*Math.log10(freq))/mxFreq);
          //  System.out.println("--<"+tf);
            
        }
        else if(w1n.equals("boolean")){
            tf=1;
        }
        else if(w1n.equals("Log Avg")){
            tf = (1+Math.log10(freq))/(1+Math.log10(agFreq));
        }
        //----------------------------------
        
        if(w2n.equals("No")){
               idf=1;
        }
        else if(w2n.equals("Idf")){
             idf = Math.log10((double)(SocNet.numUsers/keyListSize));
        }
        else if(w2n.equals("Prob. Idf")){
            
        }
        
        
        if(w3n.equals("None")){
               
        }
        else if(w3n.equals("Cosine")){
            
        }
        return tf*idf;
    }

    static public double tfIdf(double freq,int keyListSize,String word){
        double tf=0,idf=0;
        if(w1n.equals("Natural")){
                tf = freq;
        }
        else if(w1n.equals("Logarithm")){
            tf = 1+Math.log10((double)freq);
        }
        else if(w1n.equals("Augmented")){
           // tf=0.5+((0.5*Math.log10(freq))/(maxFreq(word)));
            tf=0.5+((0.5*Math.log10(freq))/(maxFreq(word)));
          //  System.out.println("--<"+tf);
            
        }
        else if(w1n.equals("boolean")){
            tf=1;
        }
        else if(w1n.equals("Log Avg")){
            tf = (1+Math.log10(freq))/(1+Math.log10(avgFreq(word)));
        }
        //----------------------------------
        
        if(w2n.equals("No")){
               idf=1;
        }
        else if(w2n.equals("Idf")){
             idf = Math.log10((double)(SocNet.numUsers/keyListSize));
        }
        else if(w2n.equals("Prob. Idf")){
            
        }
        
        
        if(w3n.equals("None")){
               
        }
        else if(w3n.equals("Cosine")){
            
        }
        return tf*idf;
    }
    
    static public double avgFreq(String word){
        Double avgFreq=0.;
        ArrayList<DoFre> hag;
        
        hag = SocNet.tags.get(word);
        
        for(int i=0;i<hag.size();i++){
            
            avgFreq+=hag.get(i).frequ;
            
            
        }

        return (avgFreq/hag.size());
    }
    
    static public double maxFreq(String word){
        Double maxFreq=0.;
        ArrayList<DoFre> hag;
        
        hag = SocNet.tags.get(word);
        
        for(int i=0;i<hag.size();i++){
            
            if(hag.get(i).frequ > maxFreq){
                maxFreq = hag.get(i).frequ;
            }
            
            
        }
        
        
        return maxFreq;
    }
    
    
}
