<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>excelFilter</title>
  </head>
  <body>
  <%String mdl= (String)request.getAttribute("mdl"); 
  
  %>
 
  <table cellspacing="1" align="center" class="grid1"  cellpadding="3">
  <tr><th colspan="2">Excel Detail</th></tr>
  <tr><td>Address</td><td><input type="checkbox" name="add" id="add" checked="checked"  value="yes" /></td></tr>
  <tr><td>Buyer Details</td><td><input type="checkbox" name="buyer" checked="checked"  id="buyer" value="yes" /></td></tr>
  <tr><td>Total</td><td>
  <table> 
  <tr><td>Qty/cts</td><td><input type="checkbox" name="QC" id="QC" checked="checked"  value="yes" /></td></tr>
  <tr><td>Prcie</td><td><input type="checkbox" name="PRC" id="PRC" checked="checked"  value="yes" /></td></tr>
  </table>
  </td></tr>
  <tr><td>Font Name</td><td>
  <select name="fontName" id="fontName">
  <option value="Arial">Arial </option>
  <option value="Arial Black">Arial Black</option>
  <option value="Comic Sans MS">Comic Sans MS</option>
  <option value="Courier New">Courier New</option>
  <option value="Lucida Console">Lucida Console</option>
  <option value="Tahoma">Tahoma</option>
  <option value="Times New Roman">Times New Roman</option>
  <option value="Trebuchet MS">Trebuchet MS</option>
  <option value="Verdana">Verdana</option>
 </select>
  </td> </tr>
  <tr><td>Font Size</td><td>
  <input type="text" name="size" id="size" value="10" />
  </td> </tr>
  <tr><td><input type="submit" name="submit" class="submit" onclick="disablePopupASSFNL()" value="Create Excel" /> </td>
  <td><input type="button" class="submit"  onclick="disablePopupASSFNL()" value="Close"  /></td>
  </tr>
  </table>
  <input type="hidden" name="mdl" value="<%=mdl%>" />
  </form>
  </body>
</html>