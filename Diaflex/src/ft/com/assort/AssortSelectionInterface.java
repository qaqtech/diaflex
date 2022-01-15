package ft.com.assort;

import ft.com.lab.FinalLabSelectionForm;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AssortSelectionInterface {
    public ArrayList FetchResult(HttpServletRequest req ,HttpServletResponse res, HashMap params,AssortSelectionForm assortForm);
    public ArrayList ASPrprViw(HttpServletRequest req ,HttpServletResponse res);
    public HashMap GetTotal(HttpServletRequest req ,HttpServletResponse res, String flg);
//    public ArrayList ASGenricSrch(HttpServletRequest req , HttpServletResponse res);
    public ArrayList SearchResult(HttpServletRequest req , HttpServletResponse res, String vnmLst,AssortSelectionForm assortForm);
}
