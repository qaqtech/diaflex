function showDiv(hidDvId,obj){
    $('.floatingDiv').each(function(index) {
          if ($(this).attr("id") == hidDvId) {
          var objPosition = $(obj).position();
          $(this).css({
		"position": "absolute",
		"top":objPosition.top+15,
		"left":objPosition.left
            });
            checkPriDivRedio(obj);
               $(this).slideDown(100);
          }
          else {
               $(this).slideUp(200);
          }
     });
     
     document.getElementById('TXTID').value = obj.id;
}

function hidStyle(){

    $('.tr').each(function(index) {

    var prpId=$(this).attr("id");

    document.getElementById(prpId).style.display='none';

    });

}
function showHidDiv(showDvId){
    $('.floating').each(function(index) {
          if ($(this).attr("id") == showDvId) {
               $(this).slideDown(100);
          }
          else {
               $(this).slideUp(200);
          }
     });
     
}

function hideDiv(divId){
  $('#'+divId).slideUp(200);
}

function ShowHIDDiv(divId){
 var isDis =  document.getElementById(divId).style.display;
 if(isDis=='none')
 document.getElementById(divId).style.display='';
 else
  document.getElementById(divId).style.display='none';
  
  
}
function refineDiv(){
  var closeDiv = document.getElementById('closeImg').style.display;
  if(closeDiv=='none'){
    document.getElementById('closeImg').style.display='';
        document.getElementById('refImg').style.display='none';

  }else{
       document.getElementById('closeImg').style.display='none';
        document.getElementById('refImg').style.display='';
 
    }
    
        $('#refineDv').toggle( "slide" );

}

function sortDiv(){
  var closeDiv = document.getElementById('closeSortImg').style.display;
  if(closeDiv=='none'){
    document.getElementById('closeSortImg').style.display='';
        document.getElementById('refSortImg').style.display='none';

  }else{
       document.getElementById('closeSortImg').style.display='none';
        document.getElementById('refSortImg').style.display='';
 
    }
    
        $('#sortDv').toggle( "slide" );

}

function checkPriDivRedio(obj){
     var txtVal = obj.value;
     var FRVal = '';
     var TOVal = '';
     if(txtVal.indexOf('~')!=-1){
      FRVal = txtVal.substring(0,txtVal.indexOf('~'));
      TOVal = txtVal.substring(txtVal.indexOf('~')+1, txtVal.length);
     }else{
        FRVal = txtVal;
        TOVal = txtVal
     }
    
     var frm_elements = document.forms['1'].elements; 
     for(i=0; i<frm_elements.length; i++) {
          var field_type = frm_elements[i].type.toLowerCase();
          if(field_type=='radio') {
             var fieldname = frm_elements[i].name;
             var fieldVal = frm_elements[i].value;
             if(fieldname=='FR' && fieldVal==FRVal && FRVal!=''){
              frm_elements[i].checked=true;
              }else if(fieldname=='TO' && fieldVal==TOVal && TOVal !='' ){
              frm_elements[i].checked=true;
              }
     }}
}

function showDivComm(hidDvId,obj){
    $('.floatingDiv').each(function(index) {
          if ($(this).attr("id") == hidDvId) {
          var objPosition = $(obj).position();
          $(this).css({
		"position": "absolute",
		"top":objPosition.top+15,
		"left":objPosition.left
            });
            checkDivRedio(obj);
               $(this).slideDown(100);
          }
          else {
               $(this).slideUp(200);
          }
     });
     
     document.getElementById('TXTID').value = obj.id;
}
function checkDivRedio(obj){
     var txtVal = obj.value;
     var FRVal = '';
     FRVal = txtVal;      
     var frm_elements = document.forms['0'].elements; 
     for(i=0; i<frm_elements.length; i++) {
          var field_type = frm_elements[i].type.toLowerCase();
          if(field_type=='radio') {
             var fieldname = frm_elements[i].name;
             var fieldVal = frm_elements[i].value;
             if(fieldname=='FR' && fieldVal==FRVal && FRVal!=''){
              frm_elements[i].checked=true;
              }
     }}
}
function showHideDiv(divClass,hidDvId,obj){
    $(divClass).each(function(index) {
          if ($(this).attr("id") == hidDvId) {
               $(this).slideDown(100);
               document.getElementById($(this).attr("id")+"_TAB").style.color='#ffffff';
          }else {
          $(this).slideUp(200);
          document.getElementById($(this).attr("id")+"_TAB").style.color='black';
          }
     });
}
function showHideDivDashboard(tblID,hidDvId,obj,conQ){
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("BTN_DAYS").value=hidDvId;
loading();
formSubmit();
}

function showHideDivDashboardtab(tblID,hidDvId,obj,conQ){
var table = document.getElementById(tblID);
var spancell = table.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!=''){
if(spanID.indexOf(conQ)!=-1){
if(spanID.indexOf(hidDvId+conQ)!=-1){
    document.getElementById(spanID).style.color='#ffffff';
}else{
    document.getElementById(spanID).style.color='black';
}
}
}
}
}
function refreshboardgridPrp(tblID,hidDvId,obj,conQ){
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("RPT_PRP").value=hidDvId;
var pietyp=document.getElementById("PIE_TYP").value;
var pieprp=document.getElementById("PIE_PRP").value;
callcommonpieChart('screen.do?method=grpcomparasionpie&lprp='+hidDvId+"&pietyp="+pietyp+"&pieprp="+pieprp,' Group Chart','75','500');
ioWeekChart('screen.do?method=stackGrpChart&lprp='+hidDvId,'I/O Group Summary','50','362');
}
function refreshboardgridPrpQTYVLU(tblID,hidDvId,obj,conQ){
loading();
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("GRAPH_BY").value=hidDvId;
   var groupby=document.getElementById("GRAPH_BY").value;
   var filterlprp=document.getElementById("filterlprp").value;
    var ttl=document.getElementById("TEST_TTL").value;
    var pietyp=document.getElementById("PIE_TYP").value;
  callcommonpieChart('screen.do?method=grpcomparasionPiePrpWise&graphby='+groupby+'&filterlprp='+filterlprp+'&pietyp='+pietyp,' '+groupby+' Wise','75','362');
  closeloading();
}

function refreshboardgridPrpPRPGRP(tblID,hidDvId,obj,conQ){
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
loading();
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("PIE_TYP").value=hidDvId;
var groupby=document.getElementById("GRAPH_BY").value;
var filterlprp=document.getElementById("filterlprp").value;
var ttl=document.getElementById("TEST_TTL").value;
callcreateMultipleBarGraphmultionPg('screen.do?method=grpcomparasionBarChartPrpWise&filterlprp='+filterlprp+'&pietyp='+hidDvId,ttl+' - '+filterlprp+' Wise','50','362');
callcommonpieChart('screen.do?method=grpcomparasionPiePrpWise&graphby='+groupby+'&filterlprp='+filterlprp+'&pietyp='+hidDvId,' '+groupby+' Wise','75','362');
closeloading();
}


function netshowHideDivDashboard(tblID,hidDvId,obj,conQ){
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("BTN_DAYS").value=hidDvId;
loading();
formSubmit();
}

function netshowHideDivDashboardtab(tblID,hidDvId,obj,conQ){
var table = document.getElementById(tblID);
var spancell = table.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!=''){
if(spanID.indexOf(conQ)!=-1){
if(spanID.indexOf(hidDvId+conQ)!=-1){
    document.getElementById(spanID).style.color='#ffffff';
}else{
    document.getElementById(spanID).style.color='black';
}
}
}
}
}
function netrefreshboardgridPrp(tblID,hidDvId,obj,conQ){
netshowHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("RPT_PRP").value=hidDvId;
var pietyp=document.getElementById("PIE_TYP").value;
callcommonpieChart('screen.do?method=netgrpcomparasionpie&lprp='+hidDvId+"&pietyp="+pietyp,' Group Chart','75','500');
callcreateMultipleBarGraph('screen.do?method=netgrpcomparasionbargraph&lprp='+hidDvId,'Group Wise','50','362');
}
function netrefreshboardgridPrpQTYVLU(tblID,hidDvId,obj,conQ){
netshowHideDivDashboardtab(tblID,hidDvId,obj,conQ);
var lprp=document.getElementById("RPT_PRP").value;
document.getElementById("PIE_TYP").value=hidDvId;
callcommonpieChart('screen.do?method=netgrpcomparasionpie&lprp='+lprp+"&pietyp="+hidDvId,' Group Chart','75','500');
callcreateMultipleBarGraph('screen.do?method=netgrpcomparasionbargraph&lprp='+lprp,'Group Wise','50','362');
}

function displayfilterQuick(){
loading();
    var disObj = document.getElementById('filterlprp').value
    document.getElementById('IMGU_'+disObj).style.display = 'none';
    document.getElementById('IMGD_'+disObj).style.display = 'block';
    document.getElementById('TD_DIV_'+disObj).style.display= 'block';
    var obj = document.getElementById("multiPrp");
   for(var i=0;i<obj.length;i++){
    var div =  obj.options[i].value ;
    if(div!=disObj){
    document.getElementById('TD_DIV_'+div).style.display= 'none';
           document.getElementById(div).style.display='none';
           document.getElementById('IMGD_'+div).style.display = 'block';
            document.getElementById('IMGU_'+div).style.display = 'none';
      }
   }
   var groupby=document.getElementById("GRAPH_BY").value;
   var pietyp=document.getElementById("PIE_TYP").value;
   var filterlprp=document.getElementById("filterlprp").value;
    var ttl=document.getElementById("TEST_TTL").value;
  callcreateMultipleBarGraphmultionPg('screen.do?method=grpcomparasionBarChartPrpWise&filterlprp='+filterlprp+'&pietyp='+pietyp,ttl+' - '+filterlprp+' Wise','50','362');
    callcommonpieChart('screen.do?method=grpcomparasionPiePrpWise&graphby='+groupby+'&filterlprp='+filterlprp+'&pietyp='+pietyp,' '+groupby+' Wise','75','362');
    closeloading();
}

function showHideDivDashboardMix(tblID,hidDvId,obj,conQ){
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("BTN_DAYS").value=hidDvId;
loading();
formSubmit();
}

function refreshboardgridPrpCTSVLUMix(tblID,hidDvId,obj,conQ){
loading();
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("GRAPH_BY").value=hidDvId;
   var groupby=document.getElementById("GRAPH_BY").value;
   var filterlprp=document.getElementById("filterlprp").value;
    var ttl=document.getElementById("TEST_TTL").value;
    var pietyp=document.getElementById("PIE_TYP").value;
  callcreateMultipleBarGraphmultionPgCtsVlu('screen.do?method=grpcomparasionBarChartPrpWiseMix&filterlprp='+filterlprp+'&pietyp='+pietyp,ttl+' - '+filterlprp+' Wise','50','362');
  callcommonpieChart('screen.do?method=grpcomparasionPiePrpWiseMix&graphby='+groupby+'&filterlprp='+filterlprp+'&pietyp='+pietyp,' '+groupby+' Wise','75','362');
  closeloading();
}
function refreshboardgridPrpPRPGRPMix(tblID,hidDvId,obj,conQ){
loading();
showHideDivDashboardtab(tblID,hidDvId,obj,conQ);
document.getElementById("PIE_TYP").value=hidDvId;
   var groupby=document.getElementById("GRAPH_BY").value;
   var filterlprp=document.getElementById("filterlprp").value;
    var ttl=document.getElementById("TEST_TTL").value;
  callcreateMultipleBarGraphmultionPgCtsVlu('screen.do?method=grpcomparasionBarChartPrpWiseMix&filterlprp='+filterlprp+'&pietyp='+hidDvId,ttl+' - '+filterlprp+' Wise','50','362');
  callcommonpieChart('screen.do?method=grpcomparasionPiePrpWiseMix&graphby='+groupby+'&filterlprp='+filterlprp+'&pietyp='+hidDvId,' '+groupby+' Wise','75','362');
  closeloading();
}

function displayfilterQuickMix(){
loading();
    var disObj = document.getElementById('filterlprp').value
    document.getElementById('IMGU_'+disObj).style.display = 'none';
    document.getElementById('IMGD_'+disObj).style.display = 'block';
    document.getElementById('TD_DIV_'+disObj).style.display= 'block';
    var obj = document.getElementById("multiPrp");
   for(var i=0;i<obj.length;i++){
    var div =  obj.options[i].value ;
    if(div!=disObj){
    document.getElementById('TD_DIV_'+div).style.display= 'none';
           document.getElementById(div).style.display='none';
           document.getElementById('IMGD_'+div).style.display = 'block';
            document.getElementById('IMGU_'+div).style.display = 'none';
      }
   }
var groupby=document.getElementById("GRAPH_BY").value;
   var filterlprp=document.getElementById("filterlprp").value;
    var ttl=document.getElementById("TEST_TTL").value;
        var pietyp=document.getElementById("PIE_TYP").value;
  callcreateMultipleBarGraphmultionPgCtsVlu('screen.do?method=grpcomparasionBarChartPrpWiseMix&filterlprp='+filterlprp+'&pietyp='+pietyp,ttl+' - '+filterlprp+' Wise','50','362');
  callcommonpieChart('screen.do?method=grpcomparasionPiePrpWiseMix&graphby='+groupby+'&filterlprp='+filterlprp+'&pietyp='+pietyp,' '+groupby+' Wise','75','362');
  closeloading();
}

function grpcomparasionDataGrid(){
var url = "screen.do?method=grpcomparasionDataGrid" ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesgrpcomparasionDataGrid(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesgrpcomparasionDataGrid(responseXML){
var columns = responseXML.getElementsByTagName("materTag")[0];
if(columns.childNodes.length==0){
document.getElementById('griddatadiff').innerHTML = "Sorry no result found";
}else{
document.getElementById('griddatadiff').innerHTML="";
var valLst = document.getElementById('barGrp').value;
var diff_prp = document.getElementById('diff_prp').value;
var lprpArray = valLst.split("_");
str = "<table><tr><td><table  cellspacing=\"1\" cellpadding=\"1\" width=\"100%\" class=\"grid1\" ><tr>";
var cnt=3
for(var s =0 ;s<lprpArray.length ;s++){
cnt+=1;
}

str +="<th colspan=\""+cnt+"\">Avg Wise</th></tr><tr><th>Group</th><th>QTY</th><th>CTS</th>";
for(var s =0 ;s<lprpArray.length ;s++){
var mprpArr = lprpArray[s];
str +="<th align=\"center\">"+mprpArr+"</th>";
}
str +="</tr>"
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var dsp_stt = columnroot.getElementsByTagName("attrNme")[0].childNodes[0].nodeValue;
str +="<tr><th>"+dsp_stt+"</th>";
str +="<td align=\"right\">"+columnroot.getElementsByTagName("QTY")[0].childNodes[0].nodeValue+"</td><td align=\"right\">"+columnroot.getElementsByTagName("CTS")[0].childNodes[0].nodeValue+"</td>";
for(var s =0 ;s<lprpArray.length ;s++){
var mprpArr = lprpArray[s];
str +="<td align=\"right\">"+columnroot.getElementsByTagName(mprpArr+"_AVG")[0].childNodes[0].nodeValue+"</td>";
}
str +="</tr>";
}
str +="</table></td>";




str += "<td><table  cellspacing=\"1\" cellpadding=\"1\" width=\"100%\" class=\"grid1\" ><tr>";
cnt=1
for(var s =0 ;s<lprpArray.length ;s++){
cnt+=1;
}

str +="<th colspan=\""+cnt+"\">Value Wise</th></tr><tr><th>Group</th>";
for(var s =0 ;s<lprpArray.length ;s++){
var mprpArr = lprpArray[s];
str +="<th align=\"center\">"+mprpArr+"</th>";
}
str +="</tr>"
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var dsp_stt = columnroot.getElementsByTagName("attrNme")[0].childNodes[0].nodeValue;
str +="<tr><th>"+dsp_stt+"</th>";
for(var s =0 ;s<lprpArray.length ;s++){
var mprpArr = lprpArray[s];
str +="<td align=\"right\">"+columnroot.getElementsByTagName(mprpArr+"_VLU")[0].childNodes[0].nodeValue+"</td>";
}
str +="</tr>";
}
str +="</table></td>";

str += "<td><table  cellspacing=\"1\" cellpadding=\"1\" width=\"100%\" class=\"grid1\" ><tr>";
cnt=1
for(var s =0 ;s<lprpArray.length ;s++){
cnt+=1;
}

str +="<th colspan=\""+cnt+"\">"+diff_prp+" Base Property(Diff Wise)</th></tr><tr><th>Group</th>";
for(var s =0 ;s<lprpArray.length ;s++){
var mprpArr = lprpArray[s];
str +="<th align=\"center\">"+mprpArr+"</th>";
}
str +="</tr>"
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var dsp_stt = columnroot.getElementsByTagName("attrNme")[0].childNodes[0].nodeValue;
str +="<tr><th>"+dsp_stt+"</th>";
for(var s =0 ;s<lprpArray.length ;s++){
var mprpArr = lprpArray[s];
str +="<td align=\"right\">"+columnroot.getElementsByTagName(mprpArr+"_DIFF")[0].childNodes[0].nodeValue+"</td>";
}
str +="</tr>";
}
str +="</table></td></tr></table>";

document.getElementById('griddatadiff').innerHTML=str;
}
}

function loadpktpndissue(days){
document.getElementById("BTN_DAYS").value=days;
loading();
formSubmit();
}

function setDueDate(purIdn,termId,dueId) {
var termData = $("#"+termId+" option:selected").text();
if(termData.indexOf('DAYS') != - 1){
var purDate = $("#"+purIdn).val()
var numberOfDays  =parseInt( termData.substring(0,termData.indexOf("DAYS")));
var dmy = purDate.split("-");
var joindate = new Date(
parseInt(
   dmy[2], 10),
   parseInt(dmy[1], 10) - 1,
   parseInt(dmy[0], 10)
);
joindate.setDate(joindate.getDate() + numberOfDays);
var formattedDate = ("0" + joindate.getDate()).slice(-2) + "-" +("0" + (joindate.getMonth() + 1)).slice(-2) + "-" + joindate.getFullYear();
   $('#'+dueId).val(formattedDate);

}else{
$('#'+dueId).val(" ");
}

}

function showHidDivFile(classNme,obj){
var objVal = obj.value;
var showDvId="DTEDIV_"+objVal;
    $('.'+classNme).each(function(index) {
             var objId=$(this).attr("id");
          if ($(this).attr("id") == showDvId) {
            document.getElementById(showDvId).style.display='';
          }
          else {
                  
              document.getElementById(objId).style.display='none';
          }
     });
     
}