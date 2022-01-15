package ft.com;

public class HtmlMailUtil {
    
    String dfltCSS = "<style>\n"+
                    "body {\n" + 
                    "   margin-left: 0px;\n" + 
                    "   margin-top: 0px;\n" + 
                    "   margin-right: 0px;\n" + 
                    "   margin-bottom: 0px;\n" + 
                    "   background-color: #FFFFFF;\n" + 
                    "   font-family: Geneva, Arial, Helvetica, sans-serif;\n" + 
                    "   font-size: 9pt;\n" + 
                    "   color: #000000;        \n" + 
                    "  }   \n" + 
                    "   table {\n" + 
                    "           padding:5px;\n" + 
                    "   }\n" + 
                    "   table tr th {\n" + 
    //                    " border: 1px solid #666666;\n" +
                     
                    "   font-family: Arial, Helvetica, Sans-Serif;\n" + 
                    "   font-size: 8pt; \n" + 
                    "   font-weight:bold;\n" + 
                    "   color: #000000;  \n" + 
                    "  }\n" + 
                    "   table tr td {\n" + 
    //                    "         border: 1px solid #999999;\n" +
                   
                    "   white-space: nowrap;font-family:  Arial, Helvetica, Sans-Serif;\n" + 
                    "   font-size: 8pt;\n" + 
                    "   color: #000000;  \n" + 
                    "  }\n"+ 
                     
                     ".rowCo{ border-top: 1px solid black} \n"+
                     " .invoice{ \n "+
                    "   border: 0px; \n "+
                        "   padding : 0px;  \n "+
                        "  } \n "+
                    "  .invoice tr td { \n "+
                     "  padding : 0px; \n "+
                     "  border: 0px; \n "+
                    "} \n "+
    
                     "  .redText { \n "+
                     "  color : red; \n "+
                  
                     "} \n "+
                    "</style>";
    
    
    //  String tblHdr="", tblCell = "" ;
    
    public HtmlMailUtil() {
    }
    
    public String head(String ttl) {
        String hdrVal = "<html>";
        hdrVal += nwLn() + "<head>";
        hdrVal += nwLn() + "<title>"+ ttl + "</title>";
        hdrVal += nwLn() + dfltCSS;
        hdrVal += nwLn() + "</head>";
        return hdrVal ;
    }
    
    public String body() {
        return body("");        
    }

    public String body(String scrpt) {
        String bdy = "<body"+ scrpt + ">";
        return bdy ;
    }
    
    public String table(String id, int brdr) {
        String tbl = nwLn() + "<table id=\""+ id + "\"  border=\"1\"  cellspacing=\"0\" cellpadding=\"5\" >";
        return tbl ;
    }
    
    public String table(String id, int brdr, String styl) {
        String tbl = nwLn() + "<table id=\""+ id + "\" border=\"1\"  cellspacing=\"0\" cellpadding=\"5\">";
        return tbl ;
    }
    
    public String tr() {
        return nwLn() + "<tr>";
    }
    
    public String tr(String style) {
        return nwLn() + "<tr class=\"rowCo\">";
    }
    public String th(String s) {
        String hdr = "";
        hdr += nwLn() + "<th valign=\"middle\" style=\"white-space: nowrap;font-size: 8pt;font-weight:bold;font-family:  Arial, Helvetica, Sans-Serif;\">"+ s + "</th>";
        return hdr ;
    }
    
    public String td(String s) {
        return td(s, "center");        
    }
    
    public String td(String s, String algn) {
        String cell = "";
        cell += nwLn() + "<td valign=\"middle\" align=\""+ algn + "\" style=\" white-space: nowrap;font-family:  Arial, Helvetica, Sans-Serif;font-size: 8pt;\" >"+ s + "</td>";
        return cell ;
    }
    public String th(String s, String algn, int cols) {
        String cell = "";
        cell += nwLn() + "<td colspan=\"" + cols + "\" valign=\"middle\"  style=\"white-space: nowrap;font-size: 8pt;font-family:  Arial, Helvetica, Sans-Serif;font-weight:bold;\" align=\""+ algn + "\">"+ s + "</td>";
        return cell ;
    }
     public String tdbold(String s, String algn) {
            String cell = "";
            cell += nwLn() + "<td valign=\"middle\" align=\""+ algn + "\" style=\" white-space: nowrap;font-family:  Arial, Helvetica, Sans-Serif;font-size: 8pt;font-weight:bold;\" >"+ s + "</td>";
            return cell ;
     }
     public String tdbold(String s, String algn, int cols) {
            String cell = "";
            cell += nwLn() + "<td colspan=\"" + cols + "\" valign=\"middle\" style=\" white-space: nowrap;font-family:  Arial, Helvetica, Sans-Serif;font-size: 8pt;font-weight:bold;\" align=\""+ algn + "\">"+ s + "</td>";
            return cell ;
     }
    public String td(String s, String algn, int cols) {
        String cell = "";
        cell += nwLn() + "<td colspan=\"" + cols + "\" valign=\"middle\" style=\" white-space: nowrap;font-family:  Arial, Helvetica, Sans-Serif;font-size: 8pt;\" align=\""+ algn + "\">"+ s + "</td>";
        return cell ;
    }
    
    public String nwLn() {
        return "\n";
    }
    }

  
