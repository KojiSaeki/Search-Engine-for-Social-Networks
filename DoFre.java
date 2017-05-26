
package socnet;

import java.util.ArrayList;
import java.util.HashMap;

public class DoFre {
    int UserId;
    Double frequ;
    String tag;
    static int op=0;
    
    public DoFre(int Userid,Double frequ){
        
        this.UserId = Userid;
        this.frequ = frequ;
        op++;
        
    }
    
    public DoFre(String tag,Double frequ){
        
        this.tag = tag;
        this.frequ = frequ;
        
    }
}


