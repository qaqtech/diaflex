package ft.com.box;

import java.util.ArrayList;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoxSelectionInterface {
    public ArrayList FetchResult(HttpServletRequest req ,HttpServletResponse res, BoxSelectionForm form);
    public ArrayList getBox(HttpServletRequest req, BoxSelectionForm form);
    public ArrayList getBoxList(HttpServletRequest req, BoxSelectionForm form);
//    public ArrayList LBGenricSrch(HttpServletRequest req);
    public ArrayList SearchResult(HttpServletRequest req,HttpServletResponse res , BoxSelectionForm form);
}
