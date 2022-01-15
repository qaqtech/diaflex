package ft.com.receipt;

import java.util.Comparator;
import java.util.HashMap;

public class CommperSort implements Comparator<HashMap>{
    public CommperSort() {
        super();
    }
    
    String prp=null;
    String order=null;
    String typ=null;
    
    public CommperSort(String prp,String order,String typ){
        this.prp=prp;
        this.order=order;
        this.typ=typ;
    }
    
    @Override
    public int compare(HashMap e1, HashMap e2) {
        if(typ.equals("STR")){
          String IDN1 = (String)e1.get(prp);
          String IDN2 = (String)e2.get(prp);
            if(order.equals("ASC"))
            return IDN1.compareTo(IDN2);
            else
            return IDN2.compareTo(IDN1); 
        }else{
         double dtenum1 = Double.parseDouble((String)e1.get(prp));
         double dtenum2 = Double.parseDouble((String)e2.get(prp));
            if(order.equals("ASC")){
             if(dtenum1 < dtenum2){
             return 1;
               } else {
              return -1;
             }
            }else{
                
                if(dtenum2 < dtenum1){
                return 1;
                  } else {
                 return -1;
                }
            }
        }
      } 
}
