 function initRequest() {
       if (window.XMLHttpRequest) {
           return new XMLHttpRequest();
       } else if (window.ActiveXObject) {
           isIE = true;
           return new ActiveXObject("Microsoft.XMLHTTP");
       }
   }
   
   function SalAvg(stkIdn){
       var dis = document.getElementById(stkIdn).style.display;
   
    if(dis!=''){
         var url = "ajaxPrcAction.do?method=salAvg&stkIdn="+stkIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesSalAvg(req.responseXML , stkIdn);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
    }else{
        document.getElementById(stkIdn).style.display = 'none';
    }
   }
function  setAddr(obj){
 var nme_idn= document.getElementById(obj).value ;
  var url = "ajaxRptAction.do?method=getAddrs&nme_idn="+nme_idn ;
     var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                 parseMessageAddr(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}
function parseMessageAddr(responseXML){
var addrs = responseXML.getElementsByTagName("addrs")[0];

for (var loop = 0; loop < addrs.childNodes.length; loop++) {
var addr = addrs.childNodes[loop];
var idn = addr.getElementsByTagName("idn")[0].childNodes[0].nodeValue;;
var addrDtl = addr.getElementsByTagName("addrss")[0].childNodes[0].nodeValue;;

document.getElementById("addr_idn").value =idn ;
document.getElementById("addr").value =addrDtl ;
}
}
      function changeInvNme(){
        var nme_idn= document.getElementById('nmeID').value ;
        var addr_idn= document.getElementById('addr_idn').value ;
        var sal_idn= document.getElementById('sal_idn').value ;
        var sal_dte= document.getElementById('sal_dte').value ;
        var mnlsale_id= document.getElementById('mnlsale_id').value ;
        var url = "ajaxRptAction.do?method=UpdateInvNme&nme_idn="+nme_idn+"&addr_idn="+addr_idn+"&sal_idn="+sal_idn+"&sal_dte="+sal_dte+"&mnlsale_id="+mnlsale_id ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessageInvNme(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
    }
    function  parseMessageInvNme(responseXML){
  var message = responseXML.getElementsByTagName("message")[0];
  var msg = message.childNodes[0].nodeValue;  
  if(msg=='Yes'){
  alert("Buyer Name Updated Successfully.");  
  } else {
  alert("Buyer Name Not Update  Please Try Again .");
  }
     }
   
   function parseMessagesSalAvg(responseXML , stkIdn){
       var ptks = responseXML.getElementsByTagName("values")[0];
       var lnt = ptks.childNodes.length;
       var str = "";
      if(lnt==0){
          str+="<td align=\"center\" class=\"OrangetdEv\">No Data found.</td>"
      }else{
      for (var loop = 0; loop < ptks.childNodes.length; loop++) {
           var ptk = ptks.childNodes[loop];
           var rte = (ptk.getElementsByTagName("rte")[0]).childNodes[0].nodeValue;
           var dis = (ptk.getElementsByTagName("dis")[0]).childNodes[0].nodeValue;
           str+= "<td align=\"center\" >Rate : &nbsp;&nbsp;&nbsp;</td><td align=\"center\" ><B>"+rte+"</B></td>";
           str+= "<td align=\"center\">Rap Dis : &nbsp;&nbsp;&nbsp;</td><td align=\"center\"><B>"+dis+"</B></td>";

      }
      }
      
       var divId = "SL_"+stkIdn;
      document.getElementById(divId).innerHTML = str;
      document.getElementById(stkIdn).style.display = '';
    }
   function PktPriceDtl(stkIdn){
    var dis = document.getElementById(stkIdn).style.display;
   
    if(dis!=''){
         var url = "ajaxsrchAction.do?method=ptkPrc&stkIdn="+stkIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesPRPPRC(req.responseXML , stkIdn);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
    }else{
        document.getElementById(stkIdn).style.display = 'none';
        document.getElementById('IMG_'+stkIdn).src='../images/plus.png';
    }
   }
   
  
function parseMessagesPRPPRC(responseXML , stkIdn){
var ptks = responseXML.getElementsByTagName("prcs")[0];
var lnt = ptks.childNodes.length;

var str = "<div><table><tr>";
if(lnt==0){
str+="<td align=\"center\" class=\"OrangetdEv\">No Data found.</td>"
}else{
var count=0;
var disCount=0;
for (var loop = 0; loop < ptks.childNodes.length; loop++) {
var ptk = ptks.childNodes[loop];

var prc = (ptk.getElementsByTagName("pct")[0]).childNodes[0].nodeValue;
var grp = (ptk.getElementsByTagName("grp")[0]).childNodes[0].nodeValue;
if(grp.indexOf('Purchase Price')!=-1){
str+="</tr><tr>";
}
if(prc!=0.0){
disCount= disCount+2;
count=count+2;
str+= "<td align=\"center\" class=\"OrangetdEv\"><span class=\"grpSty\">"+grp+"</span></td><td align=\"center\" class=\"Orangetd\"><B>"+prc+"</B></td>";
}
if(count==12){
count=0
str+="</tr><tr>";
}
}}
str+="</tr></table></div>";



var str1 = "<div class=\"memo\"><table class=\"Orange\" cellpadding=\"3\" cellspacing=\"3\" ><tr>";
str= str1+str;
var divId = "DV_"+stkIdn;
document.getElementById(divId).innerHTML = str;
document.getElementById(stkIdn).style.display = '';
document.getElementById('IMG_'+stkIdn).src='../images/minus.png';
}


   function PktGRPPriceDtl(stkIdn,grp){
    var dis = document.getElementById(grp).style.display;
    if(dis!=''){
         var url = "ajaxsrchAction.do?method=ptkGrpPrc&stkIdn="+stkIdn+"&grp="+grp ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesGRPPRPPRC(req.responseXML , stkIdn,grp);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
    }else{
        document.getElementById(grp).style.display = 'none';
        document.getElementById('IMG_'+grp).src='../images/plus.png';
    }
   }
   
  
function parseMessagesGRPPRPPRC(responseXML , stkIdn,stkgrp){
var ptks = responseXML.getElementsByTagName("prcs")[0];
var lnt = ptks.childNodes.length;

var str = "<div><table><tr>";
if(lnt==0){
str+="<td align=\"center\" class=\"OrangetdEv\">No Data found.</td>"
}else{
var count=0;
var disCount=0;
for (var loop = 0; loop < ptks.childNodes.length; loop++) {
var ptk = ptks.childNodes[loop];

var prc = (ptk.getElementsByTagName("pct")[0]).childNodes[0].nodeValue;
var grp = (ptk.getElementsByTagName("grp")[0]).childNodes[0].nodeValue;
if(grp.indexOf('Purchase Price')!=-1){
str+="</tr><tr>";
}
if(prc!=0.0){
disCount= disCount+2;
count=count+2;
str+= "<td align=\"center\" class=\"OrangetdEv\"><span class=\"grpSty\">"+grp+"</span></td><td align=\"center\" class=\"Orangetd\"><B>"+prc+"</B></td>";
}
if(count==12){
count=0
str+="</tr><tr>";
}
}}
str+="</tr></table></div>";



var str1 = "<div class=\"memo\"><table class=\"Orange\" cellpadding=\"3\" cellspacing=\"3\" ><tr>";
str= str1+str;
var divId = "DV_"+stkgrp;
document.getElementById(divId).innerHTML = str;
document.getElementById(stkgrp).style.display = '';
document.getElementById('IMG_'+stkgrp).src='../images/minus.png';
}
  function callMemoPkt(nmeIdn,objId,typ,pkttyp,obj) {
var dis = document.getElementById('BYR_'+objId).style.display;
var typhidden = document.getElementById('typhidden').value;
if(dis!='' || typhidden!=typ){
//alert('@getColumns '+ tblNme);
document.getElementById('typhidden').value=typ;
var url = "ajaxsrchAction.do?method=memo&nameIdn="+nmeIdn+"&typ="+typ+"&pkttyp="+pkttyp ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemo(req.responseXML,objId,typ,pkttyp);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('BYR_'+objId).style.display = 'none';
document.getElementById('IMG_'+typ+'_'+objId).src='../images/plus.png';
}
}

function parseMessagesMemo(responseXML, objId , typ ,pkttyp) {
//alert('@parseMessage '+ typ);
var memormk = document.getElementById("rmk2").value;
var rtnUrl = document.getElementById("RTNURL");
if(rtnUrl!=null)
 rtnUrl = rtnUrl.value;
var msg='';
var colspan = 6;
if(typ=='WEB'){
msg='Web Confirmation';
}else if(typ=='IS' || typ=='CS'){
msg='Issued';
colspan=7;
}else{
msg='Approved';
}
if(memormk=='Y')
colspan=colspan+2;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Memo ID</th>";
str= str+"<th class=\"Orangeth\" align=\"center\">Price Change</th>";
if(typ=='IS' || typ == 'CS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";
if(memormk=='Y')
str=str+"<th class=\"Orangeth\" align=\"center\">Remark 1</th><th class=\"Orangeth\" align=\"center\">Remark 2</th>";



str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";

var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
var mrmk = memo.getElementsByTagName("memormk")[0];
var mtyp=memoTyp.childNodes[0].nodeValue;
str = str+"<tr> ";
if((typ == 'WA') || (typ == 'IS')){
if(mtyp=='LP'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{ 
str = str+"<td align=\"center\"><a href=\"memoReturn.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}} else if(typ == 'CS') {
if(pkttyp=='' || pkttyp=='NR' || pkttyp=='SMX'){
str = str+"<td align=\"center\"><a href=\"consignmentSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\"><a href=\""+rtnUrl+"&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;    
}
}else {
str = str+"<td align=\"center\"><a href=\"memoSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}

str = str+"<td align=\"center\"><a title=\"Click Here To Redirect on Price Change\" href=\"../marketing/memoPrice.do?method=pktList&memoId="+id.childNodes[0].nodeValue+"\">"+id.childNodes[0].nodeValue+"</a></td>" ;
if(typ=='IS' || typ == 'CS')
str = str+"<td align=\"center\">"+memoTyp.childNodes[0].nodeValue+"</td>";

if(memormk=='Y'){
    var rmk = mrmk.childNodes[0].nodeValue;
    var rmkLst = rmk.split("@");
    str = str+ "<td align=\"center\">"+rmkLst[0]+"</td><td align=\"center\">"+rmkLst[1]+"</td>"; 
}

str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
var rdSTT = new Array();
rdSTT[0]="IS";
rdSTT[1]="AP";
rdSTT[2]="WA";

if(typ == 'CS') {
rdSTT = new Array();
rdSTT[0]="CS";
}

for(var i=1;i <= bryNo;i++){
var divId = "BYR_"+i;
if(i==objId){
document.getElementById(divId).style.display ='';
document.getElementById(divId).innerHTML = str;
for(var j =0 ;j<rdSTT.length ;j++){
var val = rdSTT[j];
if(val==typ){
document.getElementById('IMG_'+typ+'_'+objId).src='../images/minus.png';
}else{
document.getElementById('IMG_'+val+'_'+i).src='../images/plus.png';
}
}
}else{
document.getElementById(divId).innerHTML = "";
for(var j =0 ;j<rdSTT.length ;j++){
val = rdSTT[j];
document.getElementById('IMG_'+val+'_'+i).src='../images/plus.png';
}
}
}
}


 
 function CheckAllCheck(){
var check =document.getElementById('ALL').checked;
var frm_elements = document.forms['0'].elements;

for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('cb_memo_')!=-1){
frm_elements[i].checked = check;
}
}
}
}


function callMemoMerge(){


var rlnId = document.getElementById('trms').value;
var type = document.getElementById('type').value;

var nmeIdn = document.getElementById('nmeIdn').value;
var typ = document.getElementById('typ').value;
var PKT_TY = document.getElementById('PKT_TY').value;
if(rlnId!=0){
var url = "ajaxsrchAction.do?method=memoMerge&nameIdn="+nmeIdn+"&nmeRln="+rlnId+"&typ="+typ+"&type="+type+"&PKT_TY="+PKT_TY;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoMerge(req.responseXML ,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
} else {
alert("Please Select Term")
}
}


function parseMessagesMemoMerge(responseXML , typ) {
//alert('@parseMessage '+ fld);
var url = "memoReturn.do?method=load&memoId";

if(typ=='AP')
url = "memoSale.do?method=load&memoId";

var str = "<table class=\"memo\" cellpadding=\"0\" cellspacing=\"2\" width=\"600\" >" +
"<tr><th>Select<input type=\"checkbox\" name='ALL' value='ALL' id='ALL' onclick=\"CheckAllCheck()\" /></th><th >Memo ID</th><th >Term</th><th >Memo Date</th><th >Type</th><th >Qty</th><th>cts</th><th>Value</th></tr>";

var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var term = memo.getElementsByTagName("term")[0];
var dte = memo.getElementsByTagName("dte")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var typ = memo.getElementsByTagName("typ")[0];
var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
str = str+"<tr><td align=\"center\"><input type=\"checkbox\" name="+checkNme+" id="+checkNme+" value="+id.childNodes[0].nodeValue+" /></td> <td align=\"center\"> <a href="+url+"="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td><td align=\"center\">"+term.childNodes[0].nodeValue+"</td><td align=\"center\">"+dte.childNodes[0].nodeValue+"</td><td align=\"center\">"+typ.childNodes[0].nodeValue+"</td><td class=\"nm\">"+qty.childNodes[0].nodeValue+"</td><td class=\"nm\">"+cts.childNodes[0].nodeValue+"</td><td class=\"nm\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>" ;
document.getElementById('btnMge').style.display = 'block';
document.getElementById('memoDtl').innerHTML = str;




}

 
 
  function GetMemoIdn(typ){
     var byrObj = document.getElementById("byrId");
     var trmObj = document.getElementById("rlnId");
     
     var rlnId = trmObj.options[trmObj.selectedIndex].value;
     var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
     if(rlnId!=0 && nmeIdn!=0){
   var url = "ajaxsrchAction.do?method=memoIdn&nameIdn="+nmeIdn+"&nmeRln="+rlnId+"&typ="+typ ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesMemoIdn(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
       
   }
   
 function parseMessagesMemoIdn(responseXML) {
    //alert('@parseMessage '+ fld);
      var memoDtlObj = document.getElementById('memoDtl');
      var memoDtlVal = memoDtlObj.length;
      var maxCnt = 0;
      if(memoDtlVal < 3){
      maxCnt=4;
      }if(memoDtlVal < 5){
       maxCnt=2;
      }else{
      maxCnt=1;
      }
        var memos = responseXML.getElementsByTagName("memos")[0];
       if(memos.childNodes.length==1){
       maxCnt=1;
      }
      
      var str="<table class=\"grid1\"><tr>";
      for(var i=0;i<maxCnt;i++){
      if(i==0)
      str=str+"<th><input type=\"checkbox\" checked=\"checked\" onchange=\"checkAllpage(this,'cb_memo_')\" /></th>";
     else
      str=str+"<th></th>";
       for (var j=0; j<memoDtlVal; j++) {
       str=str+"<th>"+memoDtlObj[j].text+"</th>";
       }
     }
      str=str+"</tr><tr>";
      
       var count = 0;
     
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
          count++;
           var memo = memos.childNodes[loop];
          
         
           var id = memo.getElementsByTagName("id")[0];
              
             var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
              str = str 
                +"<td>" +
                "<input type=\"checkbox\" checked=\"checked\" id="+checkNme+" name="+checkNme+" value="+id.childNodes[0].nodeValue+" />" +
                "</td>" ;
                 for (var x=0; x<memoDtlVal; x++) {
                   str=str+"<td>"+((memo.getElementsByTagName(memoDtlObj[x].value)[0])).childNodes[0].nodeValue+"</td>";
                 }
                 if(count==maxCnt){
                str = str +"</tr><tr>";
                count=0;
               }
      }
      
      str = str+"</tr></table>" ;
   
     document.getElementById('memoIdn').innerHTML = str;
       
       
      
     
 }
 
 
 function GetADDR(){
     var byrObj = document.getElementById("byrId1");
 
   
     var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
    
     if(nmeIdn!=0){
   var url = "ajaxsrchAction.do?method=Addr&nameIdn="+nmeIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesAdd(req.responseXML,nmeIdn);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
       
   }
   
 function parseMessagesAdd(responseXML, nmeIdn) {
    //alert('@parseMessage '+ fld);
     
      var columnDrop = document.getElementById("addrId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("addrs")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("id")[0];
           var lval = columnroot.getElementsByTagName("add")[0];
           
          
            var newOption = new Option();
             newOption.text = lval.childNodes[0].nodeValue;
             newOption.value = lkey.childNodes[0].nodeValue;;
             columnDrop.options[loop] = newOption;
       }
      getByrTrms(nmeIdn);
     
}
 
 function getByrTrms(byrId){
     
       var url = "ajaxsrchAction.do?method=loadTrm&bryId="+byrId ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesByrTrms(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 function parseMessagesByrTrms(responseXML){
     var columnDrop = document.getElementById('rlnId1');
     removeAllOptions(columnDrop);
      var columns = responseXML.getElementsByTagName("trms")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
           
           
       }
      invTermsDtls(); 
 
 }
 
 
 function invTermsDtls(){
     var relIdn = document.getElementById('rlnId1').value;
       var url = "ajaxsrchAction.do?method=invTermsDtls&relIdn="+relIdn ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesinvTermsDtls(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 function parseMessagesinvTermsDtls(responseXML){
      var columns = responseXML.getElementsByTagName("dtls")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
      var columnroot = columns.childNodes[loop];
      var aadatcomm = (columnroot.getElementsByTagName("aadatcomm")[0]).childNodes[0].nodeValue;
      var brk1comm = (columnroot.getElementsByTagName("brk1comm")[0]).childNodes[0].nodeValue;
      var brk2comm = (columnroot.getElementsByTagName("brk2comm")[0]).childNodes[0].nodeValue;
      var brk3comm = (columnroot.getElementsByTagName("brk3comm")[0]).childNodes[0].nodeValue;
      var brk4comm = (columnroot.getElementsByTagName("brk4comm")[0]).childNodes[0].nodeValue;
      if(aadatcomm!='N'){
      var aaDat = (columnroot.getElementsByTagName("aaDat")[0]).childNodes[0].nodeValue;
      var aadatpaid = (columnroot.getElementsByTagName("aadatpaid")[0]).childNodes[0].nodeValue;
      document.getElementById("aaDatNme").innerHTML=aaDat;
      document.getElementById("aaDatComm").innerHTML=aadatcomm;
      document.getElementById("aadatcomm").value=aadatcomm;
      document.getElementById("aadatcommdisplay").style.display='';
      if(aadatpaid=='NN'){
      document.getElementById("aadatpaid3").value=aadatpaid;    
      }else if(aadatpaid=='N'){
      document.getElementById("aadatpaid2").value=aadatpaid;    
      }else{
      document.getElementById("aadatpaid1").value=aadatpaid;    
      }
      }else{
      document.getElementById("aaDatNme").innerHTML=' ';
      document.getElementById("aaDatComm").innerHTML=' ';
      document.getElementById("aadatcomm").value='  ';
      document.getElementById("aadatcommdisplay").style.display='none'; 
      document.getElementById("aadatpaid1").value="Y"; 
      }
      
      if(brk1comm!='N'){
      var brk1 = unescape((columnroot.getElementsByTagName("brk1")[0]).childNodes[0].nodeValue);
      var brk1paid = (columnroot.getElementsByTagName("brk1paid")[0]).childNodes[0].nodeValue;
      document.getElementById("brk1Nme").innerHTML=brk1;
      document.getElementById("brk1comm").value=brk1comm;  
      document.getElementById("brk1commdisplay").style.display=''; 
      if(brk1paid=='NN'){
      document.getElementById("brk1paid3").value=aadatpaid;    
      }else if(brk1paid=='N'){
      document.getElementById("brk1paid2").value=brk1paid;    
      }else{
      document.getElementById("brk1paid1").value=brk1paid;    
      }
      }else{
      document.getElementById("brk1Nme").innerHTML=' ';
      document.getElementById("brk1comm").value=' ';
      document.getElementById("brk1paid1").value='Y';
      document.getElementById("brk1commdisplay").style.display='none'; 
      }
      
      if(brk2comm!='N'){
      var brk2 = unescape((columnroot.getElementsByTagName("brk2")[0]).childNodes[0].nodeValue);
      var brk2paid = (columnroot.getElementsByTagName("brk2paid")[0]).childNodes[0].nodeValue;
      document.getElementById("brk2Nme").innerHTML=brk2;
      document.getElementById("brk2comm").value=brk2comm; 
      document.getElementById("brk2commdisplay").style.display=''; 
      if(brk2paid=='N'){
      document.getElementById("brk2paid2").value=brk2paid;    
      }else{
      document.getElementById("brk2paid1").value=brk2paid;    
      }
      }else{
      document.getElementById("brk2Nme").innerHTML=' ';
      document.getElementById("brk2comm").value=' ';
      document.getElementById("brk2paid1").value='Y';
      document.getElementById("brk2commdisplay").style.display='none'; 
      }
      if(brk3comm!='N'){
      var brk3 =unescape((columnroot.getElementsByTagName("brk3")[0]).childNodes[0].nodeValue);
      var brk3paid =(columnroot.getElementsByTagName("brk3paid")[0]).childNodes[0].nodeValue;
      document.getElementById("brk3Nme").innerHTML=brk3;
      document.getElementById("brk3comm").value=brk3comm;
      document.getElementById("brk3commdisplay").style.display=''; 
      if(brk3paid=='N'){
      document.getElementById("brk3paid2").value=brk3paid;    
      }else{
      document.getElementById("brk3paid1").value=brk3paid;    
      }
      }else{
      document.getElementById("brk3Nme").innerHTML=' ';
      document.getElementById("brk3comm").value=' ';
      document.getElementById("brk3paid1").value='Y';
      document.getElementById("brk3commdisplay").style.display='none'; 
      }
      if(brk4comm!='N'){
      var brk4 = unescape((columnroot.getElementsByTagName("brk4")[0]).childNodes[0].nodeValue);
      var brk4paid = (columnroot.getElementsByTagName("brk4paid")[0]).childNodes[0].nodeValue;
      document.getElementById("brk4Nme").innerHTML=brk4;
      document.getElementById("brk4comm").value=brk4comm; 
      document.getElementById("brk4commdisplay").style.display=''; 
      if(brk4paid=='N'){
      document.getElementById("brk4paid2").value=brk4paid;    
      }else{
      document.getElementById("brk4paid1").value=brk4paid;    
      }
      }else{
      document.getElementById("brk4Nme").innerHTML='';
      document.getElementById("brk4comm").value='';
      document.getElementById("brk4paid1").value='Y';
      document.getElementById("brk4commdisplay").style.display='none'; 
      }
      }
 
 }
 function getPrice(obj){
     var memoIdn = obj.value;
 
   
     
     if(memoIdn!=0){
   var url = "ajaxsrchAction.do?method=cts&memoIdn="+memoIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesCts(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
       
   }
   
 function parseMessagesCts(responseXML) {
//alert('@parseMessage '+ fld);



var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];


var qty = memo.getElementsByTagName("qty")[0];

if(qty.childNodes[0].nodeValue=='ALLRT'){
alert("All stones of this memo has been returned");
document.getElementById("memoId").value = "";
document.getElementById("qty").value = "" ;
document.getElementById("cts").value = "";
document.getElementById("vlu").value = "";
document.getElementById("rln").value ="";
}else if(qty.childNodes[0].nodeValue=='invalid'){
alert("Please Verify Memo Id.");
document.getElementById("memoId").value = "";
document.getElementById("qty").value = "" ;
document.getElementById("cts").value = "";
document.getElementById("vlu").value = "";
document.getElementById("rln").value ="";
} else{
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var rln = memo.getElementsByTagName("rln")[0];
var mdl = memo.getElementsByTagName("mdl")[0];
var typ = memo.getElementsByTagName("typ")[0];
var byr = memo.getElementsByTagName("byr")[0];
var mdlVal=mdl.childNodes[0].nodeValue.replace(' ','');
document.getElementById("qty").value = qty.childNodes[0].nodeValue ;
document.getElementById("cts").value = cts.childNodes[0].nodeValue ;
document.getElementById("vlu").value = vlu.childNodes[0].nodeValue ;
document.getElementById("rln").value =rln.childNodes[0].nodeValue ;
document.getElementById("typ").value =typ.childNodes[0].nodeValue ;
document.getElementById("byr").innerHTML=unescape(byr.childNodes[0].nodeValue);
var selectObj = document.getElementById("mdlLst");
document.getElementById("ALL").checked = true;
document.getElementById("memoALL").checked = true;
for(var i = 0; i < selectObj.length; i++)
{
var selectObjVal = selectObj.options[i].value.replace(' ','');
if( selectObjVal==mdlVal){

selectObj[i].selected = true;
}
}

}

}




}


function getmemoAllCurcts(obj){
    var buttonTyp = obj.value;
    var memoIdn = document.getElementById("memoId").value;
    var memoTyp = document.getElementById("typ").value;
    
    if(buttonTyp!=''){
    var url = "ajaxsrchAction.do?method=memoAllCurcts&memoIdn="+memoIdn+"&buttonTyp="+buttonTyp+"&memoTyp="+memoTyp ;
           
          var req = initRequest();
          req.onreadystatechange = function() {    
              if (req.readyState == 4) {
                  if (req.status == 200) {
                      parseMessagesgetmemoAllCurcts(req.responseXML);
                  } else if (req.status == 204){
                  }
              }
          };
          req.open("GET", url, true);
          req.send(null);
    } 
  }
  
function parseMessagesgetmemoAllCurcts(responseXML) {
var memos = responseXML.getElementsByTagName("memos")[0];
for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];
var qty = memo.getElementsByTagName("qty")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
document.getElementById("qty").value = qty.childNodes[0].nodeValue ;
document.getElementById("cts").value = cts.childNodes[0].nodeValue ;
document.getElementById("vlu").value = vlu.childNodes[0].nodeValue ;
}
}
 
function SetCalculation(stkIdn , typ , memoId){
    var url = "ajaxsrchAction.do?method=SaleTotal&stkIdn="+stkIdn+"&typ="+typ+"&memoId="+memoId;
   
     var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesTotal(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
function SetCalculationDlvTll(typ){
   if(typ=='DLV'){
    var rdCount = document.getElementById('rdCount').value;
    var stkIdn = "";
    for(var i=1 ; i <= rdCount;i++){
    if(!document.getElementById("DLV_"+i).disabled){
        var lstkIdn = document.getElementById("STKIDN_"+i).value;
        stkIdn = stkIdn+","+lstkIdn;
    }
    }
   
     var url = "ajaxsrchAction.do?method=DlvTotal&stkIdn="+stkIdn+"&typ=ALL";
   
     var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesTotal(req.responseXML , stkIdn);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
    }else{
          document.getElementById("ttlqty").innerHTML = 0 ;
           document.getElementById("ttlcts").innerHTML = 0 ;
           document.getElementById("vlu").innerHTML = 0 ;
           document.getElementById("ttldis").innerHTML =0;
           document.getElementById('net_dis').innerHTML = 0 ;
           document.getElementById('vluamt').value =0
          document.getElementById("ttlIncvlu").innerHTML =0;
     }
}

function SetCalculationDlvTllMix(typ){
   if(typ=='DLV'){
    var rdCount = document.getElementById('rdCount').value;
    var stkIdn = "";
    for(var i=1 ; i <= rdCount;i++){
        var lstkIdn = document.getElementById("STKIDN_"+i).value
        stkIdn = stkIdn+","+lstkIdn;
    }
     var saleIdn = document.getElementById('saleIdnLst').value;
   
     var url = "ajaxsrchAction.do?method=DlvTotal&stkIdn="+stkIdn+"&typ=ALL&isMix=Y&saleIdn="+saleIdn;
   
     var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesTotal(req.responseXML , stkIdn);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
    }else{
          document.getElementById("ttlqty").innerHTML = 0 ;
           document.getElementById("ttlcts").innerHTML = 0 ;
           document.getElementById("vlu").innerHTML = 0 ;
           document.getElementById("ttldis").innerHTML =0;
           document.getElementById('net_dis').innerHTML = 0 ;
           document.getElementById('vluamt').value =0
          document.getElementById("ttlIncvlu").innerHTML =0;
     }
}

function SetCalculationDlv(stkIdn , typ , saleId){
    var url = "ajaxsrchAction.do?method=DlvTotal&stkIdn="+stkIdn+"&typ="+typ+"&saleId="+saleId;
   
     var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesTotal(req.responseXML , stkIdn);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
     
     function SetCalculationDlvMix(stkIdn , typ , saleId){
     var saleIdn = document.getElementById('saleIdnLst').value;
   
    var url = "ajaxsrchAction.do?method=DlvTotal&stkIdn="+stkIdn+"&typ="+typ+"&saleId="+saleId+"&isMix=Y&saleIdn="+saleIdn;
   
     var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesTotal(req.responseXML , stkIdn);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
 function parseMessagesTotal(responseXML , stkIdn) {
    //alert('@parseMessage '+ fld);
     
     
      
       var memos = responseXML.getElementsByTagName("memos")[0];
       var ttQty = document.getElementById("ttlqty").innerHTML;
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
           var memo = memos.childNodes[loop];
          
         
           var qty = memo.getElementsByTagName("qty")[0];
           var cts = memo.getElementsByTagName("cts")[0];
           var vlu = memo.getElementsByTagName("vlu")[0];
           var dis = memo.getElementsByTagName("dis")[0];
           document.getElementById("ttlqty").innerHTML = qty.childNodes[0].nodeValue ;
           document.getElementById("ttlcts").innerHTML = cts.childNodes[0].nodeValue ;
           document.getElementById("vlu").innerHTML = vlu.childNodes[0].nodeValue ;
           document.getElementById("ttldis").innerHTML = dis.childNodes[0].nodeValue ;
           document.getElementById('net_dis').innerHTML = vlu.childNodes[0].nodeValue ;
           document.getElementById('vluamt').value = vlu.childNodes[0].nodeValue ;
          var ttl_vlu = document.getElementById("ttlIncvlu").innerHTML 
          var crt_inc_vlu;
          if(stkIdn.indexOf(',')!=-1){
                 var NewstkIdn = stkIdn.indexOf(',') == 0 ? stkIdn.substring(1) : stkIdn;
                 var mstkidn=NewstkIdn.split(",");
                 crt_inc_vlu=0.0;
                 for (var i = 0; i < mstkidn.length; i++)
                {
                 crt_inc_vlu=crt_inc_vlu+parseFloat(document.getElementById("inc_vlu_"+mstkidn[i]).value);
                }
          }else{
          crt_inc_vlu = parseFloat(document.getElementById("inc_vlu_"+stkIdn).value);
          }
          if(ttl_vlu!=''){
          var curQty = qty.childNodes[0].nodeValue;
          if(ttQty > curQty){
          ttl_vlu = parseFloat(ttl_vlu) - crt_inc_vlu ;
          }else{
          ttl_vlu = parseFloat(ttl_vlu) + crt_inc_vlu ;
          }
          if(curQty==0){
          ttl_vlu=0;
          }
          document.getElementById("ttlIncvlu").innerHTML = get2DigitNum(ttl_vlu);
          }else{
          document.getElementById("ttlIncvlu").innerHTML = crt_inc_vlu;
          }
        
        
      }
      
     
   
     
 }
 function SetALLBuy(stt){
    var cnt = document.getElementById("rdCount").value;
   for(var j=1 ; j<=cnt;j++){
    var rdId = stt+"_"+j;
     document.getElementById(rdId).checked=true;
   }
   SetCalculationBuy();
 }
 function SetCalculationBuy(){
 var memoId = document.getElementById('memoIdnlst').value;
  var sttAry = new Array();
   sttAry[0] = "BB";
   sttAry[1] = "PR";
   sttAry[2] = "DLV";
   sttAry[3] = "RT";
   sttAry[4] = "WFP";
   for(var i=0;i<sttAry.length;i++){
   var stt = sttAry[i]
  
   var str ="";
   var cnt = document.getElementById("rdCount").value;
   for(var j=1 ; j<=cnt;j++){
   
     var rdId = stt+"_"+j;
     var ischecked = document.getElementById(rdId).checked;
    
     if(ischecked){
     var val = document.getElementById(rdId).value;
    
     var valLst=val.split("_");
     var mstk_idn = valLst[1];
       str = str+","+mstk_idn;
     }
     
   }
   if(str!=""){
    callCalculationBuy(str,stt,memoId);

   }else{
            if(stt=='BB'){
               str="pn_";
            }else if(stt=='WFP'){
               str="wfp_";
            } else if(stt=='PR'){
               str="pr_";
            } else if(stt=='RT'){
                str ="rt_";
             }
           document.getElementById(str+"qty").innerHTML ="0" ;
           document.getElementById(str+"cts").innerHTML = "0" ;
           document.getElementById(str+"avgPrc").innerHTML ="0" ;
           document.getElementById(str+"avgDis").innerHTML = "0" ;
           document.getElementById(str+"vlu").innerHTML = "0" ;
   }
           
   }
  }
     function callCalculationBuy(str,stt,memoId){
        var url = "ajaxsrchAction.do?method=BuyTotal&stkIdn="+str+"&typ="+stt+"&memoId="+memoId;
    var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBuyTotal(req.responseXML );
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
      function parseMessagesBuyTotal(responseXML ) {
    //alert('@parseMessage '+ fld);
     
     var str="";
    
     
       var memos = responseXML.getElementsByTagName("memos")[0];
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
           var memo = memos.childNodes[loop];
          
         
           var qty = memo.getElementsByTagName("qty")[0];
           var cts = memo.getElementsByTagName("cts")[0];
           var vlu = memo.getElementsByTagName("vlu")[0];
           var dis = memo.getElementsByTagName("dis")[0];
           
              var prc = memo.getElementsByTagName("prc")[0];
               var stt = memo.getElementsByTagName("typ")[0].childNodes[0].nodeValue ;
             
          if(stt=='BB'){
               str="pn_";
            }else if(stt=='WFP'){
               str="wfp_";
            } else if(stt=='PR'){
               str="pr_";
            } else if(stt=='RT'){
                str ="rt_";
             }
         
           document.getElementById(str+"qty").innerHTML = qty.childNodes[0].nodeValue ;
           document.getElementById(str+"cts").innerHTML = cts.childNodes[0].nodeValue ;
           document.getElementById(str+"avgPrc").innerHTML = prc.childNodes[0].nodeValue ;
           document.getElementById(str+"avgDis").innerHTML = dis.childNodes[0].nodeValue ;
           document.getElementById(str+"vlu").innerHTML = vlu.childNodes[0].nodeValue ;
     }}
     
     
     function SetALLBrcDLV(stt){
    var cnt = document.getElementById("rdCount").value;
   for(var j=1 ; j<=cnt;j++){
    var rdId = stt+"_"+j;
     document.getElementById(rdId).checked=true;
   }
  
   SetCalculationBrcDLV();
   
 }
 function SetCalculationBrcDLV(){
 var memoId = document.getElementById('memoIdnlst').value;
  var sttAry = new Array();
   sttAry[0] = "RC";
   sttAry[1] = "PR";
   sttAry[2] = "DLV";
  sttAry[3] = "AV";
   sttAry[4] = "INV";
   for(var i=0;i<sttAry.length;i++){
   var stt = sttAry[i]
  
   var str ="";
   var cnt = document.getElementById("rdCount").value;
   for(var j=1 ; j<=cnt;j++){
   
     var rdId = stt+"_"+j;
     var ischecked = document.getElementById(rdId).checked;
     if(stt!='INV'){
     if(ischecked){
     var val = document.getElementById(rdId).value;
    
     var valLst=val.split("_");
     var mstk_idn = valLst[1];
       str = str+","+mstk_idn;
     }}
     
   }
   if(str!=""){
    callCalculationBrcDLV(str,stt,memoId);

   }else{  if(stt!='INV'){
            if(stt=='RC'){
               str="rc_";
            } else if(stt=='PR'){
               str="pr_";
            }else if(stt=='AV'){
               str="av_";
            }else if(stt=='RT'){
                str ="rt_";
             }
           document.getElementById(str+"qty").innerHTML ="0" ;
           document.getElementById(str+"cts").innerHTML = "0" ;
           document.getElementById(str+"avgPrc").innerHTML ="0" ;
           document.getElementById(str+"avgDis").innerHTML = "0" ;
           document.getElementById(str+"vlu").innerHTML = "0" ;
   }
   }
           
   }
  }
     function callCalculationBrcDLV(str,stt,memoId){
        var url = "ajaxsrchAction.do?method=BrcTotal&stkIdn="+str+"&typ="+stt+"&memoId="+memoId;
    var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBrcDLVTotal(req.responseXML );
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
      function parseMessagesBrcDLVTotal(responseXML ) {
    //alert('@parseMessage '+ fld);
     
     var str="";
    
     
       var memos = responseXML.getElementsByTagName("memos")[0];
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
           var memo = memos.childNodes[loop];
          
         
           var qty = memo.getElementsByTagName("qty")[0];
           var cts = memo.getElementsByTagName("cts")[0];
           var vlu = memo.getElementsByTagName("vlu")[0];
           var dis = memo.getElementsByTagName("dis")[0];
           
              var prc = memo.getElementsByTagName("prc")[0];
               var stt = memo.getElementsByTagName("typ")[0].childNodes[0].nodeValue ;
             
          if(stt=='RC'){
                  str="rc_";
            } else if(stt=='PR'){
               str="pr_";
            } else if(stt=='RT'){
                str ="rt_";
             }else if(stt=='AV'){
               str="av_";
            }
         
           document.getElementById(str+"qty").innerHTML = qty.childNodes[0].nodeValue ;
           document.getElementById(str+"cts").innerHTML = cts.childNodes[0].nodeValue ;
           document.getElementById(str+"avgPrc").innerHTML = prc.childNodes[0].nodeValue ;
           document.getElementById(str+"avgDis").innerHTML = dis.childNodes[0].nodeValue ;
           document.getElementById(str+"vlu").innerHTML = vlu.childNodes[0].nodeValue ;
     }}
     
     function SetALLBuySal(stt){
    var cnt = document.getElementById("rdCount").value;
   for(var j=1 ; j<=cnt;j++){
    var rdId = stt+"_"+j;
     document.getElementById(rdId).checked=true;
   }
   SetCalculationBuySal();
 }
 function SetCalculationBuySal(){
 var memoId = document.getElementById('memoIdnlst').value;
 
  var sttAry = new Array();
   sttAry[0] = "AP";
   sttAry[1] = "SL";
   sttAry[2] = "WFR";
   sttAry[3] = "RT";
   for(var i=0;i<sttAry.length;i++){
   var stt = sttAry[i]
  
   var str ="";
   var cnt = document.getElementById("rdCount").value;
   for(var j=1 ; j<=cnt;j++){
   
     var rdId = stt+"_"+j;
     var ischecked = document.getElementById(rdId).checked;
    
     if(ischecked){
     var val = document.getElementById(rdId).value;
    
     var valLst=val.split("_");
     var mstk_idn = valLst[1];
       str = str+","+mstk_idn;
     }
     
   } 
   
   
   if(str!=""){
  callCalculationBuySal(str,stt,memoId);
   }else{
            if(stt=='AP'){
               str="ap_";
            } else if(stt=='WFR'){
               str="wfr_";
            } else if(stt=='RT'){
                str ="rt_";
             }
           document.getElementById(str+"qty").innerHTML ="0" ;
           document.getElementById(str+"cts").innerHTML = "0" ;
           document.getElementById(str+"avgPrc").innerHTML ="0" ;
           document.getElementById(str+"avgDis").innerHTML = "0" ;
           document.getElementById(str+"vlu").innerHTML = "0" ;
           if(stt=='SL'){
            document.getElementById('REBATE_dis').innerHTML="0" ;
            document.getElementById('REBATE_save').value="0" ;
            document.getElementById('REBATEvluamt').value="0" ;
            document.getElementById('REBATEnet_dis').innerHTML="0" ;
           }
   }
           
   }
  }
   function callCalculationBuySal(str,stt,memoId){
     var url = "ajaxsrchAction.do?method=BuyTotal&stkIdn="+str+"&typ="+stt+"&memoId="+memoId;
    var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBuySalTotal(req.responseXML );
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
           if(stt=='SL'){
           callrebateCalculationBuySal(str,stt,memoId)
           }
     }
     
      function parseMessagesBuySalTotal(responseXML ) {
    //alert('@parseMessage '+ fld);
     
     
    
     
       var memos = responseXML.getElementsByTagName("memos")[0];
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
           var memo = memos.childNodes[loop];
          
         
           var qty = memo.getElementsByTagName("qty")[0];
           var cts = memo.getElementsByTagName("cts")[0];
           var vlu = memo.getElementsByTagName("vlu")[0];
           var dis = memo.getElementsByTagName("dis")[0];
           
              var prc = memo.getElementsByTagName("prc")[0];
               var stt = memo.getElementsByTagName("typ")[0].childNodes[0].nodeValue ;
           var str="";
           if(stt=='AP'){
               str="ap_";
            } else if(stt=='WFR'){
               str="wfr_";
            } else if(stt=='RT'){
                str ="rt_";
             }
          
           document.getElementById(str+"qty").innerHTML = qty.childNodes[0].nodeValue ;
           document.getElementById(str+"cts").innerHTML = cts.childNodes[0].nodeValue ;
           document.getElementById(str+"avgPrc").innerHTML = prc.childNodes[0].nodeValue ;
           document.getElementById(str+"avgDis").innerHTML = dis.childNodes[0].nodeValue ;
           document.getElementById(str+"vlu").innerHTML = vlu.childNodes[0].nodeValue ;
     }}
     
     function callrebateCalculationBuySal(str,stt,memoId){
     var vlu = document.getElementById('vlu').innerHTML;
     var url = "ajaxsrchAction.do?method=rebateCalculation&stkIdn="+str+"&typ="+stt+"&memoId="+memoId+"&vlu="+vlu;
    var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesrebateCalculationBuySal(req.responseXML );
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
function parseMessagesrebateCalculationBuySal(responseXML){
var vlu = document.getElementById('vlu').innerHTML;
var loyaltys = responseXML.getElementsByTagName("charges")[0];
var loyalty = loyaltys.childNodes[0];
var dis = (loyalty.getElementsByTagName('chargedis')[0]).childNodes[0].nodeValue;
document.getElementById('REBATE_dis').innerHTML=dis;
document.getElementById('REBATE_save').value=dis;
vlu=parseFloat(vlu)-parseFloat(dis)
document.getElementById('REBATEvluamt').value=vlu;
document.getElementById('REBATEnet_dis').innerHTML=vlu;
}
  function getFinalByrLS(obj ,typ){
       var byrId = obj.options[obj.selectedIndex].value;
       var url = "ajaxsrchAction.do?method=FinalByr&bryIdn="+byrId+"&typ="+typ ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFNByrLS(req.responseXML , byrId , typ);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 function  parseMessagesFNByrLS(responseXML , byrId , typ) {
    //alert('@parseMessage '+ fld);
     var prtyId = 0;
      var columnDrop = document.getElementById("prtyId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
             if(loop==0)
             prtyId = lkey.childNodes[0].nodeValue;
       }
      
        getSaleTrmsLS(byrId , 'SL');
}
 
 function getSaleIdnLS(prtyId , typ){
     
      var url = "ajaxsrchAction.do?method=saleIdn&nameIdn="+prtyId+"&typ="+typ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesMemoIdn(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }

 function getFinalByr(obj ,typ){
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0){
       var url = "ajaxsrchAction.do?method=FinalByrNR&bryIdn="+byrId+"&typ="+typ ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFNByr(req.responseXML , byrId , typ);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }else{
            var columnDrop = document.getElementById("prtyId");
            removeAllOptions(columnDrop);
             columnDrop = document.getElementById('rlnId');
            removeAllOptions(columnDrop);
             document.getElementById('memoIdn').innerHTML = "";
       }
 }
 
 function  parseMessagesFNByr(responseXML , byrId , typ) {
    //alert('@parseMessage '+ fld);
     var prtyId = 0;
      var columnDrop = document.getElementById("prtyId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
             if(loop==0)
             prtyId = lkey.childNodes[0].nodeValue;
       }
      
      getSaleTrms(prtyId , typ);
}
 
 
 function getSaleTrms(byrId , typ){
     
       var url = "ajaxsrchAction.do?method=loadSLTrm&bryId="+byrId ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesSaleTrm(req.responseXML , typ);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 function parseMessagesSaleTrm(responseXML , typ){
     var columnDrop = document.getElementById('rlnId');
     removeAllOptions(columnDrop);
      var columns = responseXML.getElementsByTagName("trms")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
           
           
       }
   
   getSaleIdn(typ);
    
 }
 
 function getSaleIdn(typ){
     var  prtyId =document.getElementById("prtyId").value;
     var rlnId = document.getElementById("rlnId").value;
     var pktTy =document.getElementById("PKTTYP");
     if(pktTy!=null)
     pktTy = pktTy.value;
     else
      pktTy = "";
     
      var url = "ajaxsrchAction.do?method=saleIdn&nameIdn="+prtyId+"&rlnId="+rlnId+"&typ="+typ+"&pktTy="+pktTy;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                        parseMessagesMemoIdn(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 function parseMessagesSaleIdn(responseXML){
 
      var memoDtlObj = document.getElementById('memoDtl');
      var memoDtlVal = memoDtlObj.length;
      var maxCnt = 0;
      if(memoDtlVal < 3){
      maxCnt=4;
      }if(memoDtlVal < 5){
       maxCnt=2;
      }else{
      maxCnt=1;
      }
      var memos = responseXML.getElementsByTagName("memos")[0];
      if(memos.childNodes.length==1){
       maxCnt=1;
      }
      var str="<table class=\"grid1\"><tr>";
      for(var i=0;i<maxCnt;i++){
      if(i==0)
      str=str+"<th><input type=\"checkbox\" checked=\"checked\" onchange=\"checkAllpage(this,'cb_memo_')\" /></th>";
     else
      str=str+"<th></th>";
       for(var j=0; j<memoDtlVal; j++) {
       str=str+"<th>"+memoDtlObj[j].text+"</th>";
       }
     }
      str=str+"</tr><tr>";
      
       var count = 0;
    
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
          count++;
           var memo = memos.childNodes[loop];
          
         
           var id = memo.getElementsByTagName("id")[0];
              
             var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
              str = str 
                +"<td>" +
                "<input type=\"checkbox\" checked=\"checked\" id="+checkNme+" name="+checkNme+" value="+id.childNodes[0].nodeValue+" />" +
                "</td>" ;
                 for (var x=0; x<memoDtlVal; x++) {
                   str=str+"<td>"+((memo.getElementsByTagName(memoDtlObj[x].value)[0])).childNodes[0].nodeValue+"</td>";
                 }
                 if(count==maxCnt){
                str = str +"</tr><tr>";
                count=0;
               }
      }
      
      str = str+"</tr></table>" ;
   
     document.getElementById('memoIdn').innerHTML = str;
       
       
    
 }
 
  function getFinalByrMix(obj ,typ){
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0){
       var url = "saleDelivery.do?method=FinalByrMIX&bryIdn="+byrId+"&typ="+typ ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFNByrMix(req.responseXML , byrId , typ);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }else{
            var columnDrop = document.getElementById("prtyId");
            removeAllOptions(columnDrop);
             columnDrop = document.getElementById('rlnId');
            removeAllOptions(columnDrop);
             document.getElementById('memoIdn').innerHTML = "";
       }
 }
 
 function  parseMessagesFNByrMix(responseXML , byrId , typ) {
    //alert('@parseMessage '+ fld);
     var prtyId = 0;
      var columnDrop = document.getElementById("prtyId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
             if(loop==0)
             prtyId = lkey.childNodes[0].nodeValue;
       }
      
      getSaleTrmsMix(prtyId , typ);
}
 
  function getBrncTrms(byrId){
       var url = "ajaxsrchAction.do?method=loadBrcTrm&bryId="+byrId ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesBrncTrm(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 function  parseMessagesBrncTrm(responseXML){
     var columnDrop = document.getElementById('rlnId');
     removeAllOptions(columnDrop);
      var columns = responseXML.getElementsByTagName("trms")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
           
           
       }
   
   getBrnchIdn();
    
 }
 
 
  function getFinalByrBrcDlv(obj ){
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0){
       var url = "ajaxsrchAction.do?method=FinalByrBrcDlv&bryIdn="+byrId;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFNByrBrcDlv(req.responseXML , byrId);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }else{
            var columnDrop = document.getElementById("prtyId");
            removeAllOptions(columnDrop);
             columnDrop = document.getElementById('rlnId');
            removeAllOptions(columnDrop);
             document.getElementById('memoIdn').innerHTML = "";
       }
 }
  function  parseMessagesFNByrBrcDlv(responseXML , byrId) {
    //alert('@parseMessage '+ fld);
     var prtyId = 0;
      var columnDrop = document.getElementById("prtyId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
             if(loop==0)
             prtyId = lkey.childNodes[0].nodeValue;
       }
      
      getBrncTrms(prtyId);
}
 function getBrnchIdn(){
     var  prtyId =document.getElementById("prtyId").value;
     var rlnId = document.getElementById("rlnId").value;
   
      var url = "ajaxsrchAction.do?method=brnchIdn&nameIdn="+prtyId+"&rlnId="+rlnId;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                        parseMessagesMemoIdn(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 function getSaleTrmsMix(byrId , typ){
       var url = "saleDelivery.do?method=loadSLTrm&bryId="+byrId+"&typ="+typ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesSaleTrmMix(req.responseXML , typ);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 function parseMessagesSaleTrmMix(responseXML , typ){
     var columnDrop = document.getElementById('rlnId');
     removeAllOptions(columnDrop);
      var columns = responseXML.getElementsByTagName("trms")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
           
           
       }

   getSaleIdnMix(typ);
    
 }
 
 function getSaleIdnMix(typ){

     var  prtyId =document.getElementById("prtyId").value;
     var rlnId = document.getElementById("rlnId").value;
      var url = "saleDelivery.do?method=saleIdn&nameIdn="+prtyId+"&rlnId="+rlnId+"&typ="+typ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                        parseMessagesMemoIdn(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 
 
 function getByrFromBill(obj,typ){
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0){
       var url = "ajaxsrchAction.do?method=ByrFromBill&bryIdn="+byrId+"&PKTTYP="+typ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesByrFromBill(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }
 }
 
 function  parseMessagesByrFromBill(responseXML){
      var columns = responseXML.getElementsByTagName("mnme")[0];
      var columnsLength = columns.childNodes.length;
      var maxCount = 0;
      if(columnsLength>6)
        maxCount = 2;
        
      var str ="<table class=\"grid1\"><tr><th><input type=\"checkbox\" checked=\"checked\" onchange=\"checkAllpage(this,'cb_nme_')\" /></th><th>Buyer Name</th>";
      if(maxCount==2)
        str =str + "<th></th><th>Buyer Name</th>";
        
       str =str + "</tr><tr>";
        var count = 0;
       for (var loop = 0; loop < columnsLength; loop++) {
           count++;
           var columnroot = columns.childNodes[loop];
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           var byr =unescape(lval.childNodes[0].nodeValue);;
            var byrId = lkey.childNodes[0].nodeValue;
             var checkNme = "cb_nme_"+byrId;
              str = str 
                +"<td>" +
                "<input type=\"checkbox\" checked=\"checked\" id="+checkNme+" name="+checkNme+" value="+byrId+" />" +
                "</td>" +
             
                "<td>"+byr+"</td> ";
                if(count==maxCount){
                str = str +"</tr><tr>";
                count=0;
                }
           }
             str = str +"</tr></table>";
         document.getElementById('byr').innerHTML = str;
 }
 
  
 function getSaleByrFromBill(obj){
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0){
       var url = "ajaxsrchAction.do?method=SaleByrFromBill&bryIdn="+byrId;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesSaleByrFromBill(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }
       document.getElementById('vnmLst').value='';
 }
 
 function  parseMessagesSaleByrFromBill(responseXML){
      var columns = responseXML.getElementsByTagName("mnme")[0];
      var columnsLength = columns.childNodes.length;
        var maxCount = 0;
      if(columnsLength>6)
        maxCount = 2;
        
      var str ="<table class=\"grid1\"><tr><th><input type=\"checkbox\" checked=\"checked\" onchange=\"checkAllpage(this,'cb_nme_')\" /></th><th>Buyer Name</th>";
      if(maxCount==2)
        str =str + "<th></th><th>Buyer Name</th>";
        
       str =str + "</tr><tr>";
      var count = 0;
       for (var loop = 0; loop < columnsLength; loop++) {
       count++;
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           var byr =unescape(lval.childNodes[0].nodeValue);;
            var byrId = lkey.childNodes[0].nodeValue;
             var checkNme = "cb_nme_"+byrId;
              str = str 
                +"<td>" +
                "<input type=\"checkbox\" checked=\"checked\" name="+checkNme+" id="+checkNme+" value="+byrId+" />" +
                "</td>" +
             
                "<td nowrap=\"nowrap\">"+byr+"</td>";
                if(count==maxCount){
                str = str +"</tr><tr>";
                count=0;
                }
           }
           
            str = str +"</tr></table>";
//           if(str!=''){
//           str ="<table>"+str+
//           "<tr><td><input type=\"checkbox\" checked=\"checked\" name=\"checkAll\" onclick=\"CheckBOXALLForm('0',this,'cb_nme_')\" /> </td>"+
//           "<td nowrap=\"nowrap\">Check All</td></tr> ";
//           "</table>";
//           }
           
         document.getElementById('byr').innerHTML = str;
 }
 function getFinalByrDlv(obj){
       var byrId = obj.options[obj.selectedIndex].value;
       var url = "ajaxsrchAction.do?method=FinalDlvByr&bryIdn="+byrId ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFNByrDlv(req.responseXML , byrId );
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 function  parseMessagesFNByrDlv(responseXML , byrId ) {
    //alert('@parseMessage '+ fld);
     var prtyId = 0;
      var columnDrop = document.getElementById("prtyId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
             if(loop==0)
             prtyId = lkey.childNodes[0].nodeValue;
       }
       
       getDlvIdn(prtyId);
}
 
 function getDlvIdn(prtyId){
     
      var url = "ajaxsrchAction.do?method=dlvIdn&nameIdn="+prtyId ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesDlvIdn(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 function parseMessagesDlvIdn(responseXML){
     
      var str ="<table><tr><td><input type=\"checkbox\" checked=\"checked\" name=\"checkAll\" onclick=\"CheckBOXALLForm('0',this,'cb_memo_')\" /> </td><td nowrap=\"nowrap\">Check All</td>";
      var count = 1;
       var memos = responseXML.getElementsByTagName("memos")[0];
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
           count++;
           var memo = memos.childNodes[loop];
          
         
           var id = memo.getElementsByTagName("id")[0];
          var dte = memo.getElementsByTagName("dte")[0];       
             var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
          str = str 
                +"<td>" +
                "<input type=\"checkbox\" checked=\"checked\" name="+checkNme+" id="+checkNme+" value="+id.childNodes[0].nodeValue+" />" +
                "</td>" +
                "<td>"+id.childNodes[0].nodeValue+"</td> "+
                "<td>"+dte.childNodes[0].nodeValue+"</td> ";
                if(count==4){
                str = str +"</tr><tr>";
                count=0;
                }
      }
      
      str = str+"</tr></table>" ;
   
     document.getElementById('memoIdn').innerHTML = str;
       
       
 }
 function PriceChange(obj){
     var memoIdn = obj.value;
     if(memoIdn!=0){
   var url = "ajaxsrchAction.do?method=memoPrice&memoIdn="+memoIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesPriceCts(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
       
   }
   
 function parseMessagesPriceCts(responseXML) {
    //alert('@parseMessage '+ fld);
     
     
      
       var memos = responseXML.getElementsByTagName("memos")[0];
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
           var memo = memos.childNodes[loop];
          
         
           var qty = memo.getElementsByTagName("qty")[0];
           var cts = memo.getElementsByTagName("cts")[0];
           var vlu = memo.getElementsByTagName("vlu")[0];
           var avgvlu = memo.getElementsByTagName("avgvlu")[0];
           var avgdis = memo.getElementsByTagName("avgdis")[0];
           document.getElementById("qty").value  = qty.childNodes[0].nodeValue ;
           document.getElementById("cts").value  = cts.childNodes[0].nodeValue ;
           document.getElementById("vlu").value  = vlu.childNodes[0].nodeValue ;
           document.getElementById("avgval").value  = avgvlu.childNodes[0].nodeValue ;
           document.getElementById("avgdis").value  = avgdis.childNodes[0].nodeValue ;
        
      }
      
     
   
     
 }
 
 function StockUpd(stkIdn){
    var url = "ajaxsrchAction.do?method=stockUpd&mstkIdn="+stkIdn+"";
  
     var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesStockUpd(req.responseXML ,stkIdn);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }

function parseMessagesStockUpd(responseXML , stkIdn) {
    //alert('@parseMessage '+ fld);
       var lab = "";
      var str = "<table class=\"Orange\"  cellpadding=\"0\" cellspacing=\"2\" width=\"600\"  ><tr><th class=\"Orangeth\">Lab</th><th class=\"Orangeth\">Propetie</th><th class=\"Orangeth\">Value</th></tr>";
      
       var values = responseXML.getElementsByTagName("values")[0];
    
       for (var loop = 0; loop < values.childNodes.length; loop++) {
           var value = values.childNodes[loop];
          
         
         lab = value.getElementsByTagName("lab")[0];
          
           var mprp = value.getElementsByTagName("mprp")[0];
          var mprpVal = mprp.childNodes[0].nodeValue;
           var fld = value.getElementsByTagName("fldval")[0];
           var typ = value.getElementsByTagName("typ")[0];
           var fldVal = fld.childNodes[0].nodeValue;
            var edit = value.getElementsByTagName("edit")[0];
            var editVal= edit.childNodes[0].nodeValue;
           str+="<tr><td>"+lab.childNodes[0].nodeValue+"</td><td>"+mprpVal+"</td><td>";
           if(typ.childNodes[0].nodeValue=='C'){
           if(editVal=='NO'){
            str+=fldVal;
             }else{
          
             str+="<select style=\"width:300\"  name="+mprpVal+">";
   
           
            var prpVal = value.getElementsByTagName("prpVal")[0];
               for(var val=0;val < prpVal.childNodes.length ; val++){
                   var dpVal = prpVal.childNodes[val].childNodes[0].nodeValue;
                 
                   if(dpVal==fldVal){
                   str+="<option   selected=\"selected\" value="+dpVal+">"+dpVal+"</option>" ;  
                   } else{
                  str+="<option value="+dpVal+">"+dpVal+"</option>" ;  
                   }
               }
                str+="</select>";
           }
           }else{
            if(editVal=='NO'){
              str+=fldVal; 
            }else{
             str+=" <input style=\"width:300\" type=\"text\" name="+mprpVal+"/>";
 
                 
            }
           }
           str+="</td></tr>";
           
           }
         
           str+="</table>";
           
           document.getElementById('stkupd').innerHTML = str;
            document.getElementById('mstkIdn').value = stkIdn;
             document.getElementById('lab').value = lab.childNodes[0].nodeValue;
      }
      
     
   
     
 

function removeAllOptions(selectbox) {
    var i;
    for(i=selectbox.options.length-1;i>=0;i--) {
        selectbox.remove(i);
    }
}

function dspSubProp(obj) {
  
    var prp = obj.value;
       var url = "ajaxsrchAction.do?method=loadProp&prp="+prp ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesPrpSub(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}
function  parseMessagesPrpSub(responseXML) {
var columnDrop = document.getElementById('subPrp');
removeAllOptions(columnDrop);
var columns = responseXML.getElementsByTagName("prps")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("prpVal")[0]).childNodes[0].nodeValue);
var lkey = unescape((columnroot.getElementsByTagName("prpValD")[0]).childNodes[0].nodeValue);
if(loop==0){
if(lval=='NT'){
document.getElementById('subPrp').style.display='none';
document.getElementById('subPrpTN').style.display='';
}else{
document.getElementById('subPrpTN').value='';
document.getElementById('subPrpTN').style.display='none';
document.getElementById('subPrp').style.display='';
}
}else{
var selectIndex = loop-1;
newOption = new Option();
newOption.text = lval;
newOption.value = lkey;
columnDrop.options[selectIndex] = newOption;
}
}
document.getElementById('valueDes').style.display = 'block';
}
     
function chkTpeDes() {

var type= document.getElementById('type').value;
var desc= document.getElementById('dmdDsc').value;
   var url = "ajaxsrchAction.do?method=descType&type="+type+"&desc="+desc;
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessageType(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}
 function parseMessageType(responseXML){
      var columns = responseXML.getElementsByTagName("msgTag")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
        var columnroot = columns.childNodes[loop];
           var lkey = columnroot.getElementsByTagName("msgDesc")[0];
        var msg=lkey.childNodes[0].nodeValue;
         if(msg=="not valid")
        {
       alert("This description is already entered.Please change Description");
        return false;
      }}
      
   }
     
function selectCrt() {

 //to load typeonly 
           
//   var url = "ajaxsrchAction.do?method=loadType" ;
//     var req = initRequest();
//           req.onreadystatechange = function() {    
//               if (req.readyState == 4) {
//                   if (req.status == 200) {
//                       parseMessagesLoadType(req.responseXML);
//                   } else if (req.status == 204){
//                   }
//               }
//           };
//           req.open("GET", url, true);
//           req.send(null);
      //    load criteria type,desc,prop,value
    var crtId=document. getElementById('exiCrt').value;

     url = "ajaxsrchAction.do?method=getType&crtId="+crtId ;
     req = initRequest();
          req.onreadystatechange = function() {
            if (req.readyState == 4) {
              if (req.status == 200) {
                parseMessageTypeDsc(req.responseXML);
              } else if (req.status == 204){
                //clearTable();
              }
            }
         };
          req.open("GET", url, true);
           req.send(null);
           
           
}

 
 function parseMessageTypeDsc(responseXML){
      var typeDrpdwn = document.getElementById('type');
      document.getElementById('dmdDsc').value="";
      var descr=document.getElementById('dmdDsc');
      var prp=document.getElementById('prp');
      var subPrp=document.getElementById('subPrp');
      var nmeid=document.getElementById('nmeID');
       var nmeTxt=document.getElementById('nme');
    //  removeAllOptions(typeDrpdwn);
     
    
          
              //////////////////////////////////////
          var columns = responseXML.getElementsByTagName("typeTag")[0];
           var columnroot = columns.childNodes[0]; 
           var typekey = columnroot.getElementsByTagName("typkey")[0];
            var typedsc = columnroot.getElementsByTagName("typval")[0];
           var dsc = columnroot.getElementsByTagName("dsc")[0];
           var mprp = columnroot.getElementsByTagName("mprp")[0];
            var val = columnroot.getElementsByTagName("val")[0];
            var prpTyp = columnroot.getElementsByTagName("prpTyp")[0];
             var nmeId = columnroot.getElementsByTagName("nmeId")[0];
            var nme = columnroot.getElementsByTagName("nme")[0];
           descr.value= dsc.childNodes[0].nodeValue;
           typeDrpdwn.value= typekey.childNodes[0].nodeValue;
           prp.value= mprp.childNodes[0].nodeValue;
           if(prpTyp ='C'){
           document.getElementById('subPrp').value=val.childNodes[0].nodeValue;
           }
           else{
           document.getElementById('subPrpTN').value=val.childNodes[0].nodeValue;
           }
           // to load sub properties(values)
              var url = "ajaxsrchAction.do?method=loadProp&prp="+mprp.childNodes[0].nodeValue ;
   
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesPrp(req.responseXML,val.childNodes[0].nodeValue);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);        
           
   }
   
   function  parseMessagesLoadType(responseXML) {
  
     
      var columnDrop = document.getElementById('type');
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName('typeTag')[0];
    
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName('typeKey')[0];
           var lval = columnroot.getElementsByTagName('typeVal')[0];
           
         
            var newOption = new Option();
             newOption.text = lval.childNodes[0].nodeValue;
             newOption.value = lkey.childNodes[0].nodeValue;;
             columnDrop.options[loop] = newOption;
       }
     
     }
     
   
   
   function  parseMessagesPrp(responseXML,val) {
var columnDrop = document.getElementById('subPrp');
removeAllOptions(columnDrop);
var columns = responseXML.getElementsByTagName("prps")[0];
var dtatyp="N";
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("prpVal")[0]).childNodes[0].nodeValue);
var lkey = unescape((columnroot.getElementsByTagName("prpValD")[0]).childNodes[0].nodeValue);
if(loop==0){
if(lval=='NT'){
document.getElementById('subPrp').style.display='none';
document.getElementById('subPrpTN').style.display='';
dtatyp="N";
}else{
document.getElementById('subPrpTN').value='';
document.getElementById('subPrpTN').style.display='none';
document.getElementById('subPrp').style.display='';
dtatyp="C";
}
}else{
var selectIndex = loop-1;
newOption = new Option();
newOption.text = lval;
newOption.value = lkey;
columnDrop.options[selectIndex] = newOption;
}
}
if(dtatyp=="C"){
    document.getElementById('subPrp').value=val;
}else{
    document.getElementById('subPrpTN').value=val;
}
document.getElementById('valueDes').style.display = 'block';
     }
   
  // to check type and description already there in the table 
   function dbaseChk() {
    if(valdFields()) {
   
  var type=document.getElementById('type').value;
  var desc=document.getElementById('dmdDsc').value;
    var url = "ajaxsrchAction.do?method=chkTypeDesc&type="+type+"&desc="+desc ;
    var req = initRequest();
          req.onreadystatechange = function() {
            if (req.readyState == 4) {
              if (req.status == 200) {
                parseMessageChkTypeDsc(req.responseXML);
              } else if (req.status == 204){
                //clearTable();
              }
            }
          };
           req.open("GET", url, true);
           req.send(null);
   }else{
      return false;
    
    
   }
}

 function parseMessageChkTypeDsc(responseXML){
      var msg="";
      var columns = responseXML.getElementsByTagName("msgTag")[0];
      var columnroot = columns.childNodes[0];
      var msgkey = columnroot.getElementsByTagName("msgDtl")[0];
       msg= msgkey.childNodes[0].nodeValue;
     if(msg=="notvalid") {
       alert("Please change description");
       return false;
     }
     return true;
      
   }
   
   function valdFields() {
var type=document.getElementById('type').value;
var desc=document.getElementById('dmdDsc').value;
var prp=document.getElementById('prp').value;
var subprp=document.getElementById('subPrp').value;
var nmeid=document.getElementById('nmeID').value;

if(type==0) {
alert("Please select Type");
return false;
}
if(desc=="") {
alert("Please select Description ");
return false;
}


return true;
}

function getHoldByr(vnm,stkIdn,typ,cpRte){
   var tdId = "TD_"+stkIdn;
   var isDisplay = document.getElementById(tdId).style.display ;
   if(isDisplay=='none'){
    var url = "ajaxsrchAction.do?method=getHoldByr&vnm="+vnm+"&stkIdn="+stkIdn+"&typ="+typ ;
    var divId = "BYR_"+stkIdn;
    document.getElementById(tdId).style.display = '';
    document.getElementById(divId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
    var req = initRequest();
          req.onreadystatechange = function() {
            if (req.readyState == 4) {
              if (req.status == 200) {
                parseMessageHoldByr(req.responseXML , stkIdn , cpRte);
              } else if (req.status == 204){
                //clearTable();
              }
            }
          };
           req.open("GET", url, true);
           req.send(null);
   }else{
       document.getElementById(tdId).style.display = 'none';
   }
}

function parseMessageHoldByr(responseXML , stkIdn , cpRte){

var divId = "BYR_"+stkIdn;
var tdId = "TD_"+stkIdn;
var memos = responseXML.getElementsByTagName("memos")[0];
var memo = memos.childNodes[0];
var byr = unescape((memo.getElementsByTagName('byr')[0]).childNodes[0].nodeValue);
var idn = (memo.getElementsByTagName('idn')[0]).childNodes[0].nodeValue;
var dte = (memo.getElementsByTagName('dte')[0]).childNodes[0].nodeValue;
var type = (memo.getElementsByTagName('typ')[0]).childNodes[0].nodeValue;
var cabin = (memo.getElementsByTagName('cabin')[0]).childNodes[0].nodeValue;
var review = (memo.getElementsByTagName('review')[0]).childNodes[0].nodeValue;
var thru = (memo.getElementsByTagName('thru')[0]).childNodes[0].nodeValue;
var memormk = (memo.getElementsByTagName('memormk')[0]).childNodes[0].nodeValue;
var noteperson = (memo.getElementsByTagName('noteperson')[0]).childNodes[0].nodeValue;
//End -- changes for Memo typ display
var str = "<div align=\"left\">";
if(byr==0){

}else{
//Start -- changes for Memo typ display
str = str +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Memo Party: <b>"+byr+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Memo ID: <b>"+idn+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Memo Typ: <b>"+type+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date: <b>"+dte+"</b> ";
//End -- changes for Memo typ display
}
if(cabin!=0)
str = str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Buyer Cabin: <b>"+cabin+"</b> ";
if(thru!=0)
str = str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Thru Person: <b>"+thru+"</b> ";
if(review!=0)
str = str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Vender: <b>"+review+"</b> ";
if(memormk!='NA')
str = str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Remark: <b>"+memormk+"</b> ";
if(noteperson!='NA')
str = str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Note Person: <b>"+noteperson+"</b> ";
if(cpRte!=0 && cpRte!='' && cpRte!='0'){
str = str +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Cost Price : <b>"+cpRte+"</b>";
}
str = str+"</div>";
document.getElementById(divId).innerHTML=str;
document.getElementById(tdId).style.display = '';
}

function GenericSrchDW(obj){
    var SrchTypLst = new Array();
    SrchTypLst[0]="BUYER";
    SrchTypLst[1]="EMPLOYEE";
     SrchTypLst[2]="VENDOR";
     SrchTypLst[3]="BROKER";
     SrchTypLst[4]="COUNTRY";
    var srchTyp = obj.value;
   
    if(srchTyp==0){
      document.getElementById('addInfo').style.display='none';
    }else{
     if(SrchTypLst.indexOf(srchTyp)!=-1){
    
         document.getElementById('idn').style.display = 'none';
           document.getElementById('byr').style.display = '';
     }else{
    
        document.getElementById('idn').style.display = '';
       document.getElementById('byr').style.display = 'none'; 
     }
     document.getElementById('addInfo').style.display='';
    }
}

function parseMessagesGenericSrch(responseXML){
     
       var columns = responseXML.getElementsByTagName('mnme')[0];
       var lnt = columns.childNodes.length;
       if(lnt==0){
           document.getElementById('idn').style.display = '';
           document.getElementById('byr').style.display = 'none';
       }else{
       var byrDw = document.getElementById('byrDW');
        removeAllOptions(byrDw);
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName('nmeid')[0];
           var lval = columnroot.getElementsByTagName('nme')[0];
           
           
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             byrDw.options[loop] = newOption;
       }
       document.getElementById('byr').style.display = '';
       document.getElementById('idn').style.display = 'none';
       }
       document.getElementById('addInfo').style.display='';
}

function SetBankAddr(obj){
    var bankIdn = obj.value;
    var url = "ajaxsrchAction.do?method=bankAddr&bankIdn="+bankIdn ;
   
     var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBank(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }
     
function  parseMessagesBank(responseXML) {
  
     
      var columnDrop = document.getElementById('bankAddr');
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName('bank')[0];
    
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName('addrIdn')[0];
           var lval = columnroot.getElementsByTagName('addr')[0];
           
          
            var newOption = new Option();
             newOption.text = lval.childNodes[0].nodeValue;
             newOption.value = lkey.childNodes[0].nodeValue;;
             columnDrop.options[loop] = newOption;
       }
     document.getElementById('bankAddr').style.display = '';
}

function checkGenPerformInv(){
  var count = document.getElementById('rdCount').value;
 
  for(i=1; i <=count; i++) {
  var isChecked = document.getElementById('INV_'+i).checked;
  if (isChecked) {
     return true;
  }
  }
   return false;
}

function GenPerformInv(typ){
if(checkGenPerformInv()==false){
alert("Please Select Atleast One Radio Button To Generate Performa Inv");
return false;
}
var idnList = "";
var byrIdn = document.getElementById('byrId1').value;
var byrAddIdn = document.getElementById('addrId').value;
var relIdn = document.getElementById('rlnId1').value;
var grpIdn = document.getElementById('grpIdn').value;
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('INV_')!=-1){
var isCheck = document.getElementById(fieldId).checked;
if(isCheck){
var val = document.getElementById(fieldId).value;
idnList = idnList+","+val;
}
}

}
}
var aadatcomm=document.getElementById('aadatcommval').value;
var brk1comm=document.getElementById('brk1commval').value;
var brk2comm=document.getElementById('brk2commval').value;
var brk3comm=document.getElementById('brk3commval').value;
var brk4comm=document.getElementById('brk4commval').value;
var courier=document.getElementById('courier').value;
var brocommval=parseFloat(aadatcomm)+parseFloat(brk1comm)+parseFloat(brk2comm)+parseFloat(brk3comm)+parseFloat(brk4comm);
if(brocommval=='')
brocommval='0'
var bankIdn = document.getElementById('bankIdn').value;
var bankAddr = document.getElementById('bankAddr').value;
var echarge=document.getElementById('EXTRA').value
//window.open("saleDelivery.do?method=perInv&byrIdn="+byrIdn+"&byrAddIdn="+byrAddIdn+"&bankIdn="+bankIdn+"&bankAddIdn="+bankAddr+"&perInvIdn="+idnList+"&relIdn="+relIdn+"&typ="+typ+"&echarge="+echarge+"&grpIdn="+grpIdn+"&brocommval="+brocommval+"&courier="+courier, '_blank');
window.open("performa.do?method=perInv&byrIdn="+byrIdn+"&byrAddIdn="+byrAddIdn+"&bankIdn="+bankIdn+"&bankAddIdn="+bankAddr+"&perInvIdn="+idnList+"&relIdn="+relIdn+"&typ="+typ+"&grpIdn="+grpIdn+"&echarge="+echarge+"&brocommval="+brocommval+"&courier="+courier, '_blank');
// window.open("saleDelivery.do?method=perInv&byrIdn="+byrIdn+"&byrAddIdn="+byrAddIdn+"&perInvIdn="+idnList , '_blank');
}

function SendMailSrch(url){
    var bool=false;

var frm_elements = document.forms['stock'].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='checkbox') {
   var fieldId = frm_elements[i].id;
   if(fieldId.indexOf('cb_memo_')!=-1){
       if(frm_elements[i].checked){
           bool = true;
           break;
       }
   }

  }
  if(bool)
   break;
}

if(!bool) {
alert("Please select stones ");
return false;
}else{
    
    
    window.open(url,'_blank');
}
}

function TtlBuyerSH(stkIdn){
var dis = document.getElementById(stkIdn).style.display;
if(dis!=''){
document.getElementById('SL_'+stkIdn).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById(stkIdn).style.display = '';

var url = "ajaxissueAction.do?method=salAvg&stkIdn="+stkIdn ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesTtlBuyerSH(req.responseXML , stkIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById(stkIdn).style.display = 'none';
document.getElementById('IMG_'+stkIdn).src='../images/plus.png';
}
}

function parseMessagesTtlBuyerSH(responseXML , stkIdn){
var msg='';
var colspan =4;
var cnt = document.getElementById('cnt').value;
var reqUrl = document.getElementById('reqUrl').value;
var ptks = responseXML.getElementsByTagName("values")[0];
var lnt = ptks.childNodes.length;
var str = "";
if(lnt==0){
str="<table><tr>No Data found.</tr></table>"
}else{
var inStock=true;
for (var loop = 0; loop < ptks.childNodes.length; loop++) {
var ptk = ptks.childNodes[loop];
var name = unescape((ptk.getElementsByTagName("nme")[0]).childNodes[0].nodeValue);
var date = (ptk.getElementsByTagName("dte")[0]).childNodes[0].nodeValue;
var rte = (ptk.getElementsByTagName("rte")[0]).childNodes[0].nodeValue;
var dis = (ptk.getElementsByTagName("dis")[0]).childNodes[0].nodeValue;
var raprte = (ptk.getElementsByTagName("raprte")[0]).childNodes[0].nodeValue;
var vnm = (ptk.getElementsByTagName("vnm")[0]).childNodes[0].nodeValue;
var mstkidn = (ptk.getElementsByTagName("mstkidn")[0]).childNodes[0].nodeValue;
if(loop==0){
str ="<table><tr><td>"+name+"</td><td>"+date+"</td></tr></table><table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
}
else{

if(loop==1){
str += "<tr><th class=\"Orangeth\" align=\"center\">Name</th><th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Pkt No.</th><th class=\"Orangeth\" align=\"center\">Rap Rate</th><th class=\"Orangeth\" align=\"center\">Rate</th><th class=\"Orangeth\" align=\"center\">Dis</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";
}

if(mstkidn=='INSTOCK'  && inStock){
str += "<tr><th class=\"Orangeth\" align=\"center\">Packet Code</th><th class=\"Orangeth\" align=\"center\">Status</th><th class=\"Orangeth\" align=\"center\">Rate</th><th class=\"Orangeth\" align=\"center\">Rap Rate</th><th class=\"Orangeth\" align=\"center\">Rap Dis</th><th class=\"Orangeth\" align=\"center\">Amount</th></tr>" ;
inStock=false;
}


if(mstkidn!='INSTOCK'){
str = str+ "<tr><td align=\"center\">"+name+"</td> <td align=\"center\">"+date+"</td>";
if(cnt=='svk'){
str = str+ "<td align=\"center\">"+vnm+"</td>";
}else{
var urlID = "DTL_"+stkIdn;
var clk="loadASSFNL('"+stkIdn+"','"+urlID+"')";
var url = reqUrl+"/Inward/transferMktSH.do?method=details&vnm="+vnm ;
str = str+ "<td align=\"center\"><A target=\"frame\" id=\""+urlID+"\" href=\""+url+"\" onclick=\""+clk+"\">"+vnm+"</a></td>";
}
str = str+ "<td align=\"center\">"+raprte+"</td><td align=\"center\">"+rte+"</td><td align=\"center\">"+dis+"</td></tr>";
}else{
var urlID = "DTL_"+stkIdn;
var clk="loadASSFNL('"+stkIdn+"','"+urlID+"')";
var url = reqUrl+"/Inward/transferMktSH.do?method=details&vnm="+vnm ;
  str = str+ "<tr><td align=\"center\"><A target=\"frame\" id=\""+urlID+"\" href=\""+url+"\" onclick=\""+clk+"\">"+vnm+"</a></td></td> <td align=\"center\">"+name+"</td>";
  str = str+ "<td align=\"center\">"+rte+"</td><td align=\"center\">"+raprte+"</td><td align=\"center\">"+dis+"</td><td align=\"center\">"+date+"</td></tr>";
  
}

}
}
str = str+"</table>"
}
var divId = "SL_"+stkIdn;
document.getElementById(divId).innerHTML = str;
document.getElementById('IMG_'+stkIdn).src='../images/minus.png';
}




function callMemoReportPkt(byridn,objId,typ) {
var dtefr=document.getElementById("dtefr")
if(dtefr!=null)
dtefr=dtefr.value;
else
dtefr="";
var dteto=document.getElementById("dteto");
if(dteto!=null)
dteto=dteto.value;
else
dteto="";
//alert('@getColumns '+ tblNme);
var divId = "BYRTRDIV_"+objId;
var dis = document.getElementById(divId).style.display;
if(dis!=''){
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById("BYRTRDIV_"+objId).style.display = '';

var url = "ajaxRptAction.do?method=memo&byridn="+byridn+"&dtefr="+dtefr+"&dteto="+dteto+"&typ="+typ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoReport(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById(divId).style.display = 'none';
}
}

function parseMessagesMemoReport(responseXML, objId,typ) {
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var msg='';
var colspan = 7;
if(memormk=='Y')
colspan=8;
if(note_person=='Y')
colspan=9;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Memo ID</th>";
if(typ=='IS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Status</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var stt = memo.getElementsByTagName("stt")[0];
var sttDs = memo.getElementsByTagName("sttDs")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
str = str+"<tr> ";
var chk=stt.childNodes[0].nodeValue;
if(stt.childNodes[0].nodeValue=='IS'){
if(typ=='IAP' || typ=='EAP' || typ=='WAP') {

str = str+"<td align=\"center\"><a href=\"../marketing/memoSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}
if(typ=='Z' || typ=='E' || typ=='I' || typ=='WH' ||  typ=='H') {

str = str+"<td align=\"center\"><a href=\"../marketing/memoReturn.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}

}
else if(stt.childNodes[0].nodeValue=='RT'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=loadfromdaily&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else 
if(stt.childNodes[0].nodeValue=='AP'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=loadfromdaily&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\">"+id.childNodes[0].nodeValue+"</td>" ;
}

if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";

str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+stt.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;

if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}


}

function callWRReportPkt(byridn,objId,typ) {
var dtefr=document.getElementById("dtefr").value;
var dteto=document.getElementById("dteto").value;
//alert('@getColumns '+ tblNme);
var divId = "BYRTRDIV_"+objId;
var dis = document.getElementById(divId).style.display;
if(dis!=''){
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById("BYRTRDIV_"+objId).style.display = '';

var url = "ajaxRptAction.do?method=memoWR&byridn="+byridn+"&dtefr="+dtefr+"&dteto="+dteto+"&typ="+typ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesWRReport(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById(divId).style.display = 'none';
}
}

function parseMessagesWRReport(responseXML, objId,typ) {
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var msg='';
var colspan = 7;
if(memormk=='Y')
colspan=8;
if(note_person=='Y')
colspan=9;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Memo ID</th>";
if(typ=='IS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var stt = memo.getElementsByTagName("stt")[0];
var sttDs = memo.getElementsByTagName("sttDs")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
str = str+"<tr> ";
var chk=stt.childNodes[0].nodeValue;
if(stt.childNodes[0].nodeValue=='IS'){
if(typ=='IAP' || typ=='EAP' || typ=='WAP') {

str = str+"<td align=\"center\"><a href=\"../marketing/memoSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}
if(typ=='Z' || typ=='E' || typ=='I' || typ=='WH' ||  typ=='H') {

str = str+"<td align=\"center\"><a href=\"../marketing/memoReturn.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}

}
else if(stt.childNodes[0].nodeValue=='RT'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=loadfromdaily&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else 
if(stt.childNodes[0].nodeValue=='AP'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=loadfromdaily&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\">"+id.childNodes[0].nodeValue+"</td>" ;
}

if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";

str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td>  <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;

if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}


}


function callMemoApproveReportPkt(byridn,objId,typ,cnt,dte) {
var memotbl=document.getElementById("memotbl").value;
//alert('@getColumns '+ tblNme);
var divId = "BYRTRDIV_"+objId;
var dis = document.getElementById(divId).style.display;
if(dis!=''){
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById("BYRTRDIV_"+objId).style.display = '';

var url = "ajaxRptAction.do?method=memo&byridn="+byridn+"&dtefr="+dte+"&dteto="+dte+"&typ="+typ+"&memotbl="+memotbl;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoApproveReport(req.responseXML,objId,typ,cnt);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById(divId).style.display = 'none';
}
}

function parseMessagesMemoApproveReport(responseXML, objId,typ,cnt) {
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var msg='';
var colspan = 7;

if(memormk=='Y')
colspan=8;
if(note_person=='Y')
colspan=9;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Memo ID</th>";
if(typ=='IS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";


str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Status</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var stt = memo.getElementsByTagName("stt")[0];
var sttDs = memo.getElementsByTagName("sttDs")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
str = str+"<tr> ";
var chk=stt.childNodes[0].nodeValue;
if(stt.childNodes[0].nodeValue=='IS'){
if(typ=='IAP' || typ=='EAP' || typ=='WAP') {

str = str+"<td align=\"center\"><a href=\"../marketing/memoSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}
if(typ=='Z' || typ=='E' || typ=='I' || typ=='WH' ||  typ=='H') {

str = str+"<td align=\"center\"><a href=\"../marketing/memoReturn.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}

}
else if(stt.childNodes[0].nodeValue=='RT'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=loadfromdaily&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else 
if(stt.childNodes[0].nodeValue=='AP'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\">"+id.childNodes[0].nodeValue+"</td>" ;
}

if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";

str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+stt.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;

if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}


}

function callMemoConsignmentReportPkt(byridn,objId,typ,cnt,dte) {
var memotbl=document.getElementById("memotbl").value;
//alert('@getColumns '+ tblNme);
var divId = "BYRTRDIV_"+objId;
var dis = document.getElementById(divId).style.display;
if(dis!=''){
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById("BYRTRDIV_"+objId).style.display = '';

var url = "ajaxRptAction.do?method=memoCS&byridn="+byridn+"&dtefr="+dte+"&dteto="+dte+"&typ="+typ+"&memotbl="+memotbl;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoConsignmentReport(req.responseXML,objId,typ,cnt);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById(divId).style.display = 'none';
}
}

function parseMessagesMemoConsignmentReport(responseXML, objId,typ,cnt) {
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var msg='';
var colspan = 7;

if(memormk=='Y')
colspan=8;
if(note_person=='Y')
colspan=9;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Memo ID</th>";
if(typ=='IS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";


str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Status</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var stt = memo.getElementsByTagName("stt")[0];
var sttDs = memo.getElementsByTagName("sttDs")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
str = str+"<tr> ";
var chk=stt.childNodes[0].nodeValue;
if(stt.childNodes[0].nodeValue=='IS'){
str = str+"<td align=\"center\"><a href=\"../marketing/consignmentSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}
else if(stt.childNodes[0].nodeValue=='RT'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=loadfromdaily&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else 
if(stt.childNodes[0].nodeValue=='AP'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\">"+id.childNodes[0].nodeValue+"</td>" ;
}

if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";

str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+stt.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;

if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}


}
function callDailySalPkt(byridn,objId,typ,dte) {

var pkt_ty=document.getElementById("pkt_ty").value;
var isLs=document.getElementById("isLS").value;
var sl_slip = document.getElementById("sl_slip").value;
//alert('@getColumns '+ tblNme);
var divId = "BYRTRDIV_"+objId;
var dis = document.getElementById(divId).style.display;
if(dis!=''){
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById("BYRTRDIV_"+objId).style.display = '';

var url = "ajaxRptAction.do?method=callDailySale&byridn="+byridn+"&dtefr="+dte+"&dteto="+dte+"&typ="+typ+"&PKTTY="+pkt_ty+"&isLs="+isLs+"&sl_slip="+sl_slip;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesDailySalReport(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById(divId).style.display = 'none';
}
}


function parseMessagesDailySalReport(responseXML, objId,typ) {
var memoPg="sale_dlv_rep.jsp";
    var webUrl = document.getElementById("webUrl").value;
  var reportUrl = document.getElementById("repUrl").value;
  var cnt = document.getElementById("cnt").value;
var repPath = document.getElementById("repPath").value;
var accessidn = document.getElementById("accessidn").value;
var vluininr = document.getElementById("vluininr").value;
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var performa = document.getElementById("performa").value;
var reqUrlPage = document.getElementById("reqUrlPage").value;
var vigat = document.getElementById("vigat").value;
var sl_slip = document.getElementById("sl_slip").value;
var msg='';
var colspan = 9;
if(sl_slip=='Y')
colspan=parseInt(colspan)+1;
if(vigat=='Y')
colspan=parseInt(colspan)+1;
if(memormk=='Y')
colspan=parseInt(colspan)+1;
if(note_person=='Y')
colspan=parseInt(colspan)+1;

if(performa=='Y')
colspan=parseInt(colspan)+1;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Sale ID</th>";
if(typ=='IS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

if(sl_slip=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Sale Slip</th>";
if(vigat=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Vigat</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";

if(performa=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Performa</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Edit</th>";


str=str+"<th class=\"Orangeth\" align=\"center\">Packet Deatail</th><th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Status</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Aadat</th><th class=\"Orangeth\" align=\"center\">Aadat2</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th>";
if(vluininr==''){
str=str+"<th class=\"Orangeth\" align=\"center\">Vlu IN INR</th>"  
}
str=str+"</tr><tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];
var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var fnlexhvlu = memo.getElementsByTagName("fnlexhvlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var stt = memo.getElementsByTagName("stt")[0];
var trm = memo.getElementsByTagName("trm")[0];
var aadatpaid = memo.getElementsByTagName("aadatpaid")[0];
var aadatpaid2 = memo.getElementsByTagName("aadatpaid2")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
str = str+"<tr> ";
var chk=stt.childNodes[0].nodeValue;

if(typ=='SL' || typ=='DLV') {
var theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+memoPg+"&p_sal_idn="+id.childNodes[0].nodeValue+"&p_access="+accessidn; 
str = str+"<td align=\"center\"><a href="+theURL+" target=\"_blank\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\">"+id.childNodes[0].nodeValue+"</td>" ;
}
if(sl_slip=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("sl_slip")[0]).childNodes[0].nodeValue)+"</td>";

if(vigat=='Y')
str = str = str+ "<td align=\"center\"><A href=dailySalesReport.do?method=pktDtlvigat&saleId="+id.childNodes[0].nodeValue+"  target=\"_blank\" nowrap=\"nowrap\">Pkt Dtl </a></td>";
if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";
if(performa=='Y'){
var theURLper = reqUrlPage+"/marketing/performa.do?method=loadFromForm&form=Y&typ=SL&pktTyp=SINGLE&idn="+id.childNodes[0].nodeValue+"&p_access="+accessidn; 
str = str+"<td align=\"center\"><a href="+theURLper+" target=\"_blank\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}

str = str+ "<td align=\"center\"><A href=\"dailySalesReport.do?method=chnageInvNme&p_sal_idn="+id.childNodes[0].nodeValue+"\"  id=\"LNK_"+id.childNodes[0].nodeValue+"\" onclick=\"loadASSFNLPop('LNK_"+id.childNodes[0].nodeValue+"')\" target=\"SC\">Edit</a></td>";

var pktUrl ="dailySalesReport.do?method=pktDtl&saleId="+id.childNodes[0].nodeValue ;
if(cnt=='xljf')
 pktUrl ="dailySalesReport.do?method=pktDtlXL&saleId="+id.childNodes[0].nodeValue ;
str = str+ "<td align=\"center\"><A href="+pktUrl+"  target=\"_blank\">Pkt Dtl </a></td><td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+stt.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+aadatpaid.childNodes[0].nodeValue+"</td><td align=\"center\">"+aadatpaid2.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td>";
if(vluininr==''){
str = str+ "<td align=\"center\">"+fnlexhvlu.childNodes[0].nodeValue+"</td>";
}
str = str+ "</tr>";
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;

if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}

function callDailyDlvPkt(byridn,objId,typ,dte) {

var ISHK = document.getElementById("ISHK").value;
var pkt_ty = document.getElementById("pkt_ty").value;
//alert('@getColumns '+ tblNme);
var divId = "BYRTRDIV_"+objId;
var dis = document.getElementById(divId).style.display;
if(dis!=''){
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById("BYRTRDIV_"+objId).style.display = '';
var url = "ajaxRptAction.do?method=callDailyDlv&byridn="+byridn+"&dtefr="+dte+"&dteto="+dte+"&typ="+typ+"&ISHK="+ISHK+"&pkt_ty="+pkt_ty;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesDailyDlvReport(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById(divId).style.display = 'none';
}
}

function parseMessagesDailyDlvReport(responseXML, objId,typ) {
//alert('@parseMessage '+ typ);
var memoPg="sale_dlv_rep.jsp";
var accessidn = document.getElementById("accessidn").value;
    var webUrl = document.getElementById("webUrl").value;
  var reportUrl = document.getElementById("repUrl").value;
  var cnt = document.getElementById("cnt").value;
var repPath = document.getElementById("repPath").value;
var vluininr = document.getElementById("vluininr").value;
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var performa = document.getElementById("performa").value;
var reqUrlPage = document.getElementById("reqUrlPage").value;
var msg='';
var colspan = 9;

if(memormk=='Y')
colspan=10;
if(note_person=='Y')
colspan=11;

if(performa=='Y')
colspan=12;
var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">DLV ID</th>";
if(typ=='IS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";

if(performa=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Performa</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Packet Deatail</th><th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Status</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Aadat</th><th class=\"Orangeth\" align=\"center\">Aadat2</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th>";
if(vluininr==''){
str=str+"<th class=\"Orangeth\" align=\"center\">Exchange Rte</th><th class=\"Orangeth\" align=\"center\">Vlu IN INR</th>";
}
str=str+"</tr><tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var stt = memo.getElementsByTagName("stt")[0];
var trm = memo.getElementsByTagName("trm")[0];
var aadatpaid = memo.getElementsByTagName("aadatpaid")[0];
var aadatpaid2 = memo.getElementsByTagName("aadatpaid2")[0];
var fnlexhvlu = memo.getElementsByTagName("fnlexhvlu")[0];
var fnlexhrte = memo.getElementsByTagName("fnlexhrte")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
str = str+"<tr> ";
var chk=stt.childNodes[0].nodeValue;
if(stt.childNodes[0].nodeValue=='IS'){

if(typ=='SL' || typ=='DLV') {
var theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+memoPg+"&p_dlv_idn="+id.childNodes[0].nodeValue+"&p_access="+accessidn; 
str = str+"<td align=\"center\"><a href="+theURL+" target=\"_blank\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\">"+id.childNodes[0].nodeValue+"</td>" ;
}

}
else if(stt.childNodes[0].nodeValue=='RT'){
str = str+"<td align=\"center\">"+id.childNodes[0].nodeValue+"</td>" ;
}

if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";
if(performa=='Y'){
var theURLper = reqUrlPage+"/marketing/performa.do?method=loadFromForm&form=Y&typ=DLV&pktTyp=SINGLE&idn="+id.childNodes[0].nodeValue+"&p_access="+accessidn; 
str = str+"<td align=\"center\"><a href="+theURLper+" target=\"_blank\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}
var pktUrl ="dailyDlvReport.do?method=pktDtl&dlvId="+id.childNodes[0].nodeValue+"&typ="+memoTyp.childNodes[0].nodeValue ;
if(cnt=='xljf')
 pktUrl ="dailyDlvReport.do?method=pktDtlXL&dlvId="+id.childNodes[0].nodeValue ;
str = str+ "<td align=\"center\"><A href="+pktUrl+"  target=\"_blank\">Pkt Dtl </a></td><td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+stt.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+aadatpaid.childNodes[0].nodeValue+"</td><td align=\"center\">"+aadatpaid2.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td>";
if(vluininr==''){
str = str+ "<td align=\"center\">"+fnlexhrte.childNodes[0].nodeValue+"</td>";
str = str+ "<td align=\"center\">"+fnlexhvlu.childNodes[0].nodeValue+"</td>";
}
str = str+ "</tr>";
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;

if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}


function callBranchDailyDlvPkt(byridn,objId,typ,dte) {
//var dtefr=document.getElementById("dtefr").value;
//var dteto=document.getElementById("dteto").value;

//alert('@getColumns '+ tblNme);
var divId = "BYRTRDIV_"+objId;
var dis = document.getElementById(divId).style.display;
if(dis!=''){
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById("BYRTRDIV_"+objId).style.display = '';
var url = "ajaxRptAction.do?method=callBranchDailyDlv&byridn="+byridn+"&dtefr="+dte+"&dteto="+dte+"&typ="+typ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesDailyBranchDlvReport(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById(divId).style.display = 'none';
}
}

function parseMessagesDailyBranchDlvReport(responseXML, objId,typ) {

var performa = document.getElementById("performa").value;
var reqUrlPage = document.getElementById("reqUrlPage").value;



var msg='';
var colspan = 7;
var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">DLV ID</th><th class=\"Orangeth\" align=\"center\">Sale ID</th>";
if(typ=='IS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

if(performa=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Performa</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Packet Deatail</th><th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Status</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var salidn = memo.getElementsByTagName("salidn")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var stt = memo.getElementsByTagName("stt")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
str = str+"<tr> ";
var chk=stt.childNodes[0].nodeValue;
str = str+"<td align=\"center\">"+id.childNodes[0].nodeValue+"</td>" ;
str = str+"<td align=\"center\">"+salidn.childNodes[0].nodeValue+"</td>" ;

if(performa=='Y'){
var theURLper = reqUrlPage+"/marketing/performa.do?method=loadFromForm&form=Y&typ=SL&pktTyp=SINGLE&idn="+salidn.childNodes[0].nodeValue+"&p_access="+accessidn; 
str = str+"<td align=\"center\"><a href="+theURLper+" target=\"_blank\">"+salidn.childNodes[0].nodeValue+"</a></td>" ;
}


var pktUrl ="dailyBranchDlv.do?method=pktDtl&salId="+salidn.childNodes[0].nodeValue ;
str = str+ "<td align=\"center\"><A href="+pktUrl+"  target=\"_blank\">Pkt Dtl </a></td><td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+stt.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;

if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}

function getFinalConsignmentByr(obj ,typ){
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0){
       var url = "ajaxsrchAction.do?method=FinalConsignmentByr&bryIdn="+byrId+"&typ="+typ ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFNConsignmentByr(req.responseXML , byrId , typ);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }else{
            var columnDrop = document.getElementById("prtyId");
            removeAllOptions(columnDrop);
             columnDrop = document.getElementById('rlnId');
            removeAllOptions(columnDrop);
             document.getElementById('memoIdn').innerHTML = "";
       }
 }
 
 function  parseMessagesFNConsignmentByr(responseXML , byrId , typ) {
    //alert('@parseMessage '+ fld);
     var prtyId = 0;
      var columnDrop = document.getElementById("prtyId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
             if(loop==0)
             prtyId = lkey.childNodes[0].nodeValue;
       }
      
      getConsignmentTrms(byrId);
}
 
 function getConsignmentTrms(byrId){
     
       var url = "ajaxsrchAction.do?method=loadTrm&bryId="+byrId ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesConsignmentTrm(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 function parseMessagesConsignmentTrm(responseXML){
     var columnDrop = document.getElementById('rlnId');
     removeAllOptions(columnDrop);
      var columns = responseXML.getElementsByTagName("trms")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
           
           
       }
   getConsignmentIdn('CS');
 }
 
 function getConsignmentIdn(typ){
     var  prtyId =document.getElementById("prtyId").value;
     var rlnId = document.getElementById("rlnId").value;
       var url = "ajaxsrchAction.do?method=ConsignmentIdn&nameIdn="+prtyId+"&rlnId="+rlnId+"&typ="+typ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                        parseMessagesMemoIdn(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 function GetConsignmentIdn(){
 
var byrObj = document.getElementById("byrId");
var trmObj = document.getElementById("rlnId");
var rlnId = trmObj.options[trmObj.selectedIndex].value;
var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
if(rlnId!=0 && nmeIdn!=0){
var url = "ajaxsrchAction.do?method=consignmentIdn&nameIdn="+nmeIdn+"&nmeRln="+rlnId ;
var req = initRequest();
req.onreadystatechange = function(){
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoIdn(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}   


function callpendfMemoPkt(nmeIdn,objId,typ,obj) {
var dis = document.getElementById('BYRTRDIV_'+objId).style.display;
if(dis!=''){
document.getElementById("BYRTRDIV_"+objId).style.display = '';
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
//alert('@getColumns '+ tblNme);

var pktTy=document.getElementById('pktTy').value;

var url = "ajaxsrchAction.do?method=memof&nameIdn="+nmeIdn+"&typ="+typ+"&pktTy="+pktTy ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagespendfMemo(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('BYRTRDIV_'+objId).style.display = 'none';
}
}

function callmixpendfMemoPkt(nmeIdn,objId,typ,obj) {
var dis = document.getElementById('BYRTRDIV_'+objId).style.display;
if(dis!=''){
document.getElementById("BYRTRDIV_"+objId).style.display = '';
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
//alert('@getColumns '+ tblNme);
var url = "penfmemo.do?method=memof&nameIdn="+nmeIdn+"&typ="+typ ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesmixpendfMemo(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('BYRTRDIV_'+objId).style.display = 'none';
}
}

 function parseMessagesmixpendfMemo(responseXML, objId , typ ) {
//alert('@parseMessage '+ typ);
var msg='';
var rtnUrl = document.getElementById("RTNURL").value;

var colspan = 6;
var memormk = document.getElementById("rmk2");
if(memormk!=null)
colspan = 8;
var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Memo ID</th>";
if(typ=='IS' || typ == 'CS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th>" ;
if(memormk!=null)
str=str+"<th class=\"Orangeth\" align=\"center\">Remark 1</th><th class=\"Orangeth\" align=\"center\">Remark 2</th>";

str=str+"</tr>" +"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";

var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
 var mrmk = memo.getElementsByTagName("memormk")[0];

var mtyp=memoTyp.childNodes[0].nodeValue;
str = str+"<tr> ";
if(mtyp.indexOf('AP') !=-1) {
str = str+"<td align=\"center\"><a href=\"../marketing/memoSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}
else if(mtyp!='LP'){
str = str+"<td align=\"center\"><a href=\"../"+rtnUrl+"&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}


if(typ=='IS' || typ == 'CS')
str = str+"<td align=\"center\">"+memoTyp.childNodes[0].nodeValue+"</td>";


str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td>";

if(memormk!=null){
    var rmk = unescape(mrmk.childNodes[0].nodeValue);
    var rmkLst = rmk.split("@");
    str = str+ "<td align=\"center\">"+rmkLst[0]+"</td><td align=\"center\">"+rmkLst[1]+"</td>"; 
}

}


str = str+"</tr></table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;
if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}
 function parseMessagespendfMemo(responseXML, objId , typ ) {
//alert('@parseMessage '+ typ);
var redirct_to_pricechange = document.getElementById("redirct_to_pricechange").value;
var redirct_to_memoreport = document.getElementById("redirct_to_memoreport").value;
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var msg='';
var colspan = 6;


if(redirct_to_pricechange=='Y')
colspan=colspan+1;
if(memormk=='Y')
colspan=colspan+1;
if(note_person=='Y')
colspan=colspan+1;
if(redirct_to_memoreport=='Y')
colspan=colspan+1;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Memo ID</th>";

if(redirct_to_memoreport=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Memo Report</th>";

if(redirct_to_pricechange=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Price Change</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";

if(typ=='IS' || typ == 'CS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";

var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
var mtyp=memoTyp.childNodes[0].nodeValue;
str = str+"<tr> ";
if(mtyp=='RGAP'){
str = str+"<td align=\"center\"><a href=\"../mixAkt/mixReturnAction.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else if(mtyp=='RGH'){
str = str+"<td align=\"center\"><a href=\"../rough/roughClosureAction.do?method=fetch&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else if(mtyp.indexOf('AP') !=-1) {
str = str+"<td align=\"center\"><a href=\"../marketing/memoSale.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else if(mtyp.indexOf('BB') !=-1){
str = str+"<td align=\"center\"><a href=\"../marketing/buyBack.do?method=Fetch&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;    
}else if(mtyp.indexOf('WB') !=-1){
str = str+"<td align=\"center\"><a href=\"../marketing/buyBack.do?method=Fetch&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;    
}else  if(mtyp!='LP'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReturn.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}else{
str = str+"<td align=\"center\"><a href=\"../marketing/memoReport.do?method=load&memoId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;
}

if(redirct_to_pricechange=='Y')
str = str+"<td align=\"center\"><a title=\"Click Here To Redirect on Price Change\" href=\"../marketing/memoPrice.do?method=pktList&memoId="+id.childNodes[0].nodeValue+"\">"+id.childNodes[0].nodeValue+"</a></td>" ;
if(redirct_to_memoreport=='Y')
str = str+"<td align=\"center\"><a title=\"Click Here To Redirect on Memo Report\" href=\"../marketing/memoReport.do?method=load&memoId="+id.childNodes[0].nodeValue+"\">"+id.childNodes[0].nodeValue+"</a></td>" ;

if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";

if(typ=='IS' || typ == 'CS')
str = str+"<td align=\"center\">"+memoTyp.childNodes[0].nodeValue+"</td>";


str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;
if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}

function callpendfSalePkt(nmeIdn,objId,typ,obj) {
var dis = document.getElementById('BYRTRDIV_'+objId).style.display;
if(dis!=''){
document.getElementById("BYRTRDIV_"+objId).style.display = '';
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
//alert('@getColumns '+ tblNme);

var pktTy=document.getElementById('pktTy').value;

var url = "ajaxsrchAction.do?method=salef&nameIdn="+nmeIdn+"&typ="+typ+"&pktTy="+pktTy ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagespendfSale(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('BYRTRDIV_'+objId).style.display = 'none';
}
}

 function parseMessagespendfSale(responseXML, objId , typ ) {
//alert('@parseMessage '+ typ);
var redirct_to_pricechange = document.getElementById("redirct_to_pricechange").value;
var redirct_to_memoreport = document.getElementById("redirct_to_memoreport").value;
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var msg='';
var colspan = 6;


if(redirct_to_pricechange=='Y')
colspan=colspan+1;
if(memormk=='Y')
colspan=colspan+1;
if(note_person=='Y')
colspan=colspan+1;
if(redirct_to_memoreport=='Y')
colspan=colspan+1;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Sale ID</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";

if(typ=='IS' || typ == 'CS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";

var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
var mtyp=memoTyp.childNodes[0].nodeValue;
str = str+"<tr> ";
if(mtyp=='SL'){
str = str+"<td align=\"center\"><a href=\"../marketing/saleDelivery.do?method=loadPkt&saleId="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;    
}
if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";

if(typ=='IS' || typ == 'CS')
str = str+"<td align=\"center\">"+memoTyp.childNodes[0].nodeValue+"</td>";


str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;
if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}

function callpendfbrcdlvPkt(nmeIdn,objId,typ,obj) {
var dis = document.getElementById('BYRTRDIV_'+objId).style.display;
if(dis!=''){
document.getElementById("BYRTRDIV_"+objId).style.display = '';
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
//alert('@getColumns '+ tblNme);

var pktTy=document.getElementById('pktTy').value;

var url = "ajaxsrchAction.do?method=brcdlvf&nameIdn="+nmeIdn+"&typ="+typ+"&pktTy="+pktTy ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagespendfbrcdlv(req.responseXML,objId,typ);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('BYRTRDIV_'+objId).style.display = 'none';
}
}

 function parseMessagespendfbrcdlv(responseXML, objId , typ ) {
//alert('@parseMessage '+ typ);
var redirct_to_pricechange = document.getElementById("redirct_to_pricechange").value;
var redirct_to_memoreport = document.getElementById("redirct_to_memoreport").value;
var memormk = document.getElementById("memormk").value;
var note_person = document.getElementById("note_person").value;
var msg='';
var colspan = 6;


if(redirct_to_pricechange=='Y')
colspan=colspan+1;
if(memormk=='Y')
colspan=colspan+1;
if(note_person=='Y')
colspan=colspan+1;
if(redirct_to_memoreport=='Y')
colspan=colspan+1;

var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Sale ID</th>";

if(memormk=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Remark</th>";

if(note_person=='Y')
str= str+"<th class=\"Orangeth\" align=\"center\">Note person</th>";

if(typ=='IS' || typ == 'CS')
str= str+"<th class=\"Orangeth\" align=\"center\">Type</th>";

str=str+"<th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Term</th><th class=\"Orangeth\" align=\"center\">Qty</th><th class=\"Orangeth\">cts</th><th class=\"Orangeth\" align=\"center\">Value</th></tr>" +
"<tr><td align=\"left\" colspan="+colspan+"><b>"+msg+"</b></td></tr>";

var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var qty = memo.getElementsByTagName("qty")[0];
var id = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var trm = memo.getElementsByTagName("trm")[0];
var memoTyp = memo.getElementsByTagName("typ")[0];
var mtyp=memoTyp.childNodes[0].nodeValue;
str = str+"<tr> ";
if(mtyp=='DLV'){
str = str+"<td align=\"center\"><a href=\"../marketing/branceDelivery.do?method=fetch&saleIdn="+id.childNodes[0].nodeValue+"&pnd=Y&view=Y\">"+id.childNodes[0].nodeValue+"</a></td>" ;    
}
if(memormk=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("memormk")[0]).childNodes[0].nodeValue)+"</td>";
if(note_person=='Y')
str = str+"<td align=\"center\">"+unescape((memo.getElementsByTagName("noteperson")[0]).childNodes[0].nodeValue)+"</td>";

if(typ=='IS' || typ == 'CS')
str = str+"<td align=\"center\">"+memoTyp.childNodes[0].nodeValue+"</td>";


str = str+ "<td align=\"center\">"+dte.childNodes[0].nodeValue+"</td> <td align=\"center\">"+trm.childNodes[0].nodeValue+"</td><td align=\"center\">"+qty.childNodes[0].nodeValue+"</td><td align=\"center\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;
if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}
function callpendfMemosametermsPkt(nmeIdn,objId,frm,obj) {
var dis = document.getElementById('BYRTERMTRDIV_'+objId).style.display;
if(dis!=''){
document.getElementById("BYRTERMTRDIV_"+objId).style.display = '';
document.getElementById('BYRTERM_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
//alert('@getColumns '+ tblNme);
var url = "ajaxsrchAction.do?method=memosameterms&nameIdn="+nmeIdn;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagespendfMemosameterms(req.responseXML,objId,frm);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('BYRTERMTRDIV_'+objId).style.display = 'none';
}
}
function parseMessagespendfMemosameterms(responseXML, objId , frm ) {
//alert('@parseMessage '+ typ);
var msg='';
var colspan = 6;
var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Terms</th>";
var memos = responseXML.getElementsByTagName("memos")[0];
var trmsln=memos.childNodes.length;
for (var loop = 0; loop < trmsln; loop++) {
var memo = memos.childNodes[loop];

var nmeId = (memo.getElementsByTagName('nmeId')[0]).childNodes[0].nodeValue;
var trmidn = (memo.getElementsByTagName('trmidn')[0]).childNodes[0].nodeValue;
var trm = (memo.getElementsByTagName('trm')[0]).childNodes[0].nodeValue;
str = str+"<tr> ";
if(frm.indexOf('S') !=-1) {
str = str+"<td align=\"center\"><a href=\"../marketing/memoSale.do?method=load&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y\">"+trm+"</a></td>" ;
if(trmsln==1){
window.open("memoSale.do?method=load&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y", '_self');
}
}
else if(frm=='M'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReturn.do?method=load&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y\">"+trm+"</a></td>" ;
}
str = str+ "</tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYRTERM_"+i;
if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}

function callpendfSalesametermsPkt(nmeIdn,objId,frm,obj) {
var dis = document.getElementById('BYRTERMTRDIV_'+objId).style.display;
if(dis!=''){
document.getElementById("BYRTERMTRDIV_"+objId).style.display = '';
document.getElementById('BYRTERM_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
//alert('@getColumns '+ tblNme);
var url = "ajaxsrchAction.do?method=salesameterms&nameIdn="+nmeIdn;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagespendfSalesameterms(req.responseXML,objId,frm);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('BYRTERMTRDIV_'+objId).style.display = 'none';
}
}
function parseMessagespendfSalesameterms(responseXML, objId , frm ) {
//alert('@parseMessage '+ typ);
var msg='';
var colspan = 6;
var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Terms</th>";
var memos = responseXML.getElementsByTagName("memos")[0];
var trmsln=memos.childNodes.length;
for (var loop = 0; loop < trmsln; loop++) {
var memo = memos.childNodes[loop];

var nmeId = (memo.getElementsByTagName('nmeId')[0]).childNodes[0].nodeValue;
var trmidn = (memo.getElementsByTagName('trmidn')[0]).childNodes[0].nodeValue;
var trm = (memo.getElementsByTagName('trm')[0]).childNodes[0].nodeValue;
str = str+"<tr> ";
if(frm.indexOf('S') !=-1) {
str = str+"<td align=\"center\"><a href=\"../marketing/saleDelivery.do?method=loadPkt&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y\">"+trm+"</a></td>" ;
if(trmsln==1){
window.open("saleDelivery.do?method=loadPkt&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y", '_self');
}
}
else if(frm=='M'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReturn.do?method=load&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y\">"+trm+"</a></td>" ;
}
str = str+ "</tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYRTERM_"+i;
if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}

function callpendfbrcdlvsametermsPkt(nmeIdn,objId,frm,obj) {
var dis = document.getElementById('BYRTERMTRDIV_'+objId).style.display;
if(dis!=''){
document.getElementById("BYRTERMTRDIV_"+objId).style.display = '';
document.getElementById('BYRTERM_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
//alert('@getColumns '+ tblNme);
var url = "ajaxsrchAction.do?method=brcdlv&nameIdn="+nmeIdn;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagespendfbrcdlvsameterms(req.responseXML,objId,frm);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('BYRTERMTRDIV_'+objId).style.display = 'none';
}
}
function parseMessagespendfbrcdlvsameterms(responseXML, objId , frm ) {
//alert('@parseMessage '+ typ);
var msg='';
var colspan = 6;
var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Terms</th>";
var memos = responseXML.getElementsByTagName("memos")[0];
var trmsln=memos.childNodes.length;
for (var loop = 0; loop < trmsln; loop++) {
var memo = memos.childNodes[loop];

var nmeId = (memo.getElementsByTagName('nmeId')[0]).childNodes[0].nodeValue;
var trmidn = (memo.getElementsByTagName('trmidn')[0]).childNodes[0].nodeValue;
var trm = (memo.getElementsByTagName('trm')[0]).childNodes[0].nodeValue;
str = str+"<tr> ";
if(frm.indexOf('S') !=-1) {
str = str+"<td align=\"center\"><a href=\"../marketing/saleDelivery.do?method=loadPkt&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y\">"+trm+"</a></td>" ;
if(trmsln==1){
window.open("saleDelivery.do?method=loadPkt&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y", '_self');
}
}
else if(frm=='M'){
str = str+"<td align=\"center\"><a href=\"../marketing/memoReturn.do?method=load&nmeId="+nmeId+"&trmId="+trmidn+"&pnd=Y&view=Y\">"+trm+"</a></td>" ;
}
str = str+ "</tr>"
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYRTERM_"+i;
if(i==objId){
document.getElementById(divId).innerHTML = str;

}else{
document.getElementById(divId).innerHTML = "";
}
}
}
 
  function GetTypMemoReturnRadioIdn(){
var byrObj = document.getElementById("byrId");
var trmObj = document.getElementById("rlnId");
var pktTy = document.getElementById("PKTTYP");
 if(pktTy!=null)
    pktTy=pktTy.value;
 else
   pktTy="";
var rlnId = "";
if(trmObj!=null)
rlnId = trmObj.options[trmObj.selectedIndex].value;
var nmeIdn = byrObj.options[byrObj.selectedIndex].value;

if(nmeIdn!=0){
var url = "ajaxsrchAction.do?method=MemoReturnIdn&nameIdn="+nmeIdn+"&nmeRln="+rlnId+"&PKTTYP="+pktTy;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoReturnRadioIdn(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}
function parseMessagesMemoReturnRadioIdn(responseXML) {
    //alert('@parseMessage '+ fld);
     var chkTypVal="radio";
     var append="";
    var chkTyp = document.getElementById("chkTyp");
    if(chkTyp!=null){
    chkTypVal=chkTyp.value;
    append=document.getElementById('memoIdn').innerHTML;
    }
    
      var str =append+"<table><tr>";
      var count = 0;
       var memos = responseXML.getElementsByTagName("memos")[0];
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
          count++;
           var memo = memos.childNodes[loop];
          
         
           var id = memo.getElementsByTagName("id")[0];
          var dte = memo.getElementsByTagName("dte")[0];       
//             var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
           var checkNme = "cb_memo";
           if(chkTypVal=='checkbox')
           checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
              str = str 
                +"<td>" +
                "<input type="+chkTypVal+" id="+checkNme+"_"+id.childNodes[0].nodeValue+" name="+checkNme+" value="+id.childNodes[0].nodeValue+" />" +
                "</td>" +
                "<td>"+id.childNodes[0].nodeValue+"</td> "+
                "<td>"+dte.childNodes[0].nodeValue+"</td> ";
                if(count==4){
                str = str +"</tr><tr>";
                count=0;
                }
      }
      
      str = str+"</tr></table>" ;
   
     document.getElementById('memoIdn').innerHTML = str;
       
       
      
     
 }
 
   function GetMemoSMXSALE(){
var byrObj = document.getElementById("byrId");
var trmObj = document.getElementById("rlnId");
var rlnId = trmObj.options[trmObj.selectedIndex].value;
var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
if(rlnId!=0 && nmeIdn!=0){
var url = "ajaxsrchAction.do?method=MemoSMX&nameIdn="+nmeIdn+"&nmeRln="+rlnId;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoSMXSALE(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}
function parseMessagesMemoSMXSALE(responseXML) {
    //alert('@parseMessage '+ fld);
    
      var str ="<table><tr>";
      var count = 0;
       var memos = responseXML.getElementsByTagName("memos")[0];
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
          count++;
           var memo = memos.childNodes[loop];
          
         
           var id = memo.getElementsByTagName("id")[0];
          var dte = memo.getElementsByTagName("dte")[0];       
//             var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
           var checkNme = "cb_memo";
              str = str 
                +"<td>" +
                "<input type=\"radio\" id="+checkNme+"_"+id.childNodes[0].nodeValue+" name="+checkNme+" value="+id.childNodes[0].nodeValue+" />" +
                "</td>" +
                "<td>"+id.childNodes[0].nodeValue+"</td> "+
                "<td>"+dte.childNodes[0].nodeValue+"</td> ";
                if(count==4){
                str = str +"</tr><tr>";
                count=0;
                }
      }
      
      str = str+"</tr></table>" ;
   
     document.getElementById('memoIdn').innerHTML = str;
       
       
      
     
 }
function GetReturnTypMemoIdn(){
var byrObj = document.getElementById("byrId");
var trmObj = document.getElementById("rlnId");
var tyObj = document.getElementById("typId");
var typ=tyObj.options[tyObj.selectedIndex].value;
var rlnId = trmObj.options[trmObj.selectedIndex].value;
var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
if(rlnId!=0 && nmeIdn!=0){
var url = "ajaxsrchAction.do?method=memoReturntypIdn&nameIdn="+nmeIdn+"&nmeRln="+rlnId+"&typ="+typ ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoIdn(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}

function getSaleIdnBox(typ){
var byrObj = document.getElementById("byrId");
var trmObj = document.getElementById("rlnId");
var rlnId = trmObj.options[trmObj.selectedIndex].value;
var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
var url = "ajaxsrchAction.do?method=boxSaleIdn&nameIdn="+nmeIdn+"&rlnId="+rlnId+"&typ="+typ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoIdn(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}


function checkMemoPrice(stkIdn, qout , bidRte , offerLmt){

    var prcObj = document.getElementById('memo_prc_'+stkIdn);
    var pepPrc = prcObj.value;
   if(isNaN(pepPrc)){
    alert("Please verify price");
    prcObj.value = bidRte;
    prcObj.focus();
    return false;
   }
    var percent = ((qout * offerLmt)/100);
   
    var diff = qout - pepPrc;
    var minPrc = qout - percent;
    
    if(diff > percent){
        alert("Minmium expected price is "+minPrc); 
        prcObj.value = bidRte;
        prcObj.focus();
          setTimeout(function(){prcObj.focus();},1); 
           return false;
    }
    
    if(pepPrc >= qout){
        alert("Price is too high stone avaliable at lower price"); 
        prcObj.value = bidRte;
        prcObj.focus();
          setTimeout(function(){prcObj.focus();},1); 
           return false;
    }
    
    if(pepPrc != ""){
       var dis = document.getElementById('dis_'+stkIdn).value;
       var rdis = 100 + parseFloat(dis) ;
       var  rap1 = (qout * 100)/rdis;
       var rap=(pepPrc*100/rap1)-100;
       document.getElementById('memo_dis_'+stkIdn).value = formatNumber(rap,2);
       var url = "ajaxsrchAction.do?method=gtQuot&quot="+pepPrc+"&stkIdn="+stkIdn ;
       
        var req = initRequest();
        req.onreadystatechange = function() {
        if (req.readyState == 4) {
       if (req.status == 200) {
        } else if (req.status == 204){
       }
       }};
      req.open("GET", url, true);
      req.send(null);
   
}}

function checkMemoDis(stkIdn, qout , bidRte , offerLmt){
   var dis = document.getElementById('dis_'+stkIdn).value;
   var ofrDis = document.getElementById('memo_dis_'+stkIdn).value ;
   if(ofrDis!=""){
     var rdis = 100 + parseFloat(dis) ;
     var  rap1 = (qout * 100)/rdis;
     var pepPrc =(rap1*(100+parseFloat(ofrDis))/100);
     
     if(isNaN(pepPrc)){
    alert("Please verify Discount");
    dis.value = bidRte;
    dis.focus();
    return false;
   }
    var percent = ((qout * offerLmt)/100);
   
    var diff = qout - pepPrc;
    var minPrc = qout - percent;
    
    if(diff > percent){
        alert("Minmium expected price is "+minPrc); 
        dis.value = bidRte;
        dis.focus();
          setTimeout(function(){prcObj.focus();},1); 
           return false;
    }
    
    if(pepPrc >= qout){
        alert("Price is too high stone avaliable at lower price"); 
        dis.value = bidRte;
        dis.focus();
          setTimeout(function(){prcObj.focus();},1); 
           return false;
    }
    document.getElementById('memo_prc_'+stkIdn).value = pepPrc;
    
    var url = "ajaxsrchAction.do?method=gtQuot&quot="+pepPrc+"&stkIdn="+stkIdn ;
        var req = initRequest();
        req.onreadystatechange = function() {
        if (req.readyState == 4) {
       if (req.status == 200) {
        } else if (req.status == 204){
       }
       }};
      req.open("GET", url, true);
      req.send(null);
   }
}


function displayExDay(obj,cnt){
     var memoTyp = obj.options[obj.selectedIndex].value;
    if(memoTyp=='E'){
      document.getElementById("expDay").style.display ='';
      document.getElementById("byrCb").style.display ='none';
      document.getElementById("charge").style.display ='none';
      document.getElementById("webBlockDiv").style.display ='none';
      document.getElementById("web_block").value ='NO';
      var symbol= document.getElementById("symbol");
      if(symbol!=null){
       document.getElementById("symbol").style.display ='none';
      }
      }else if(memoTyp=='I'){
     document.getElementById("expDay").style.display ='none';
     document.getElementById("byrCb").style.display ='';
     document.getElementById("charge").style.display ='none';
      document.getElementById("webBlockDiv").style.display ='none';
      document.getElementById("web_block").value ='NO';
        var symbol= document.getElementById("symbol");
      if(symbol!=null){
       document.getElementById("symbol").style.display ='none';
      }
     }else if(memoTyp=='BB'){
     document.getElementById("expDay").style.display ='none';
     document.getElementById("byrCb").style.display ='none';
     document.getElementById("charge").style.display ='';
      document.getElementById("webBlockDiv").style.display ='none';
      document.getElementById("web_block").value ='NO';
       var symbol= document.getElementById("symbol");
      if(symbol!=null){
       document.getElementById("symbol").style.display ='none';
      }
     }else{
      document.getElementById("expDay").style.display ='none';
      document.getElementById("byrCb").style.display ='none';
      document.getElementById("charge").style.display ='none';
      document.getElementById("webBlockDiv").style.display ='none';
      document.getElementById("web_block").value ='NO';
       var symbol= document.getElementById("symbol");
      if(symbol!=null){
       document.getElementById("symbol").style.display ='none';
      }
     }
     if(memoTyp.indexOf('AP')!=-1 || memoTyp.indexOf('WR')!=-1){
     document.getElementById("rmkOn").style.display =''; 
     document.getElementById('rmkval').value='';
     var symbol= document.getElementById("symbol");
      if(symbol!=null){
       document.getElementById("symbol").style.display ='';
      }
     if(cnt=='smf' && memoTyp.indexOf('IAP')!=-1){
     document.getElementById("byrCb").style.display ='';
     }
     }else{
        
     document.getElementById('rmkval').value=''
     if(memoTyp=='CS' && cnt=='hk'){
      document.getElementById("webBlockDiv").style.display ='block';
      document.getElementById("web_block").value ='NO';
     }
     }
     if(memoTyp!='RGAP' && memoTyp!='RGH'){
        var url = "ajaxsrchAction.do?method=selChBox&typ="+memoTyp;
        var req = initRequest();
        req.onreadystatechange = function() {
        if (req.readyState == 4) {
       if (req.status == 200) {
       parseMessagesCheckBox(req.responseXML);
        } else if (req.status == 204){
       }
       }};
      req.open("GET", url, true);
      req.send(null);
     }
   
}

function parseMessagesCheckBox(responseXML){
 var frm_elements = document.forms['stock'].elements; 

       for(i=0; i<frm_elements.length; i++) {

        var field_type = frm_elements[i].type.toLowerCase();

           if(field_type=='checkbox') {
             var fieldId = frm_elements[i].id;
             if(fieldId.indexOf('cb_memo_')!=-1){
             frm_elements[i].disabled = false;
             }
           }
       }
    var pkts = responseXML.getElementsByTagName("pkts")[0];

   for(var loop = 0; loop < pkts.childNodes.length; loop++) {
         var memo = pkts.childNodes[loop];
         var pkt = memo.getElementsByTagName("Idn")[0]
          var pktVal = pkt.childNodes[0].nodeValue;
         var stt = memo.getElementsByTagName("stt")[0]
          var sttVal = stt.childNodes[0].nodeValue;
         var cb_memo = "cb_memo_"+sttVal+"_"+pktVal;
         document.getElementById(cb_memo).disabled = true;
    }
}

function GetTypMemoRadioIdn(){
var byrObj = document.getElementById("byrId");
var trmObj = document.getElementById("rlnId");
var tyObj = document.getElementById("typId");
var typ=tyObj.options[tyObj.selectedIndex].value;
var rlnId = trmObj.options[trmObj.selectedIndex].value;
var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
if(rlnId!=0 && nmeIdn!=0){
var url = "ajaxsrchAction.do?method=memotypIdn&nameIdn="+nmeIdn+"&nmeRln="+rlnId+"&typ="+typ ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemoRadioIdn(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}
function parseMessagesMemoRadioIdn(responseXML) {
//alert('@parseMessage '+ fld);

var str ="<table><tr>";
var count = 0;
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
count++;
var memo = memos.childNodes[loop];


var id = memo.getElementsByTagName("id")[0];
var dte = memo.getElementsByTagName("dte")[0];
// var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
var checkNme = "cb_memo";
str = str
+"<td>" +
"<input type=\"radio\" id="+checkNme+"_"+id.childNodes[0].nodeValue+"\" name="+checkNme+" value="+id.childNodes[0].nodeValue+" />" +
"</td>" +
"<td>"+id.childNodes[0].nodeValue+"</td> "+
"<td>"+dte.childNodes[0].nodeValue+"</td> ";
if(count==4){
str = str +"</tr><tr>";
count=0;
}
}

str = str+"</tr></table>" ;

document.getElementById('memoIdn').innerHTML = str;
}


function GetTyppricechangeMemoRadioIdn(){
var byrObj = document.getElementById("byrId");
var trmObj = document.getElementById("rlnId");
var tyObj = document.getElementById("typId");
var typ=tyObj.options[tyObj.selectedIndex].value;
var rlnId = trmObj.options[trmObj.selectedIndex].value;
var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
if(rlnId!=0 && nmeIdn!=0){
var url = "ajaxsrchAction.do?method=pricechangememotypIdn&nameIdn="+nmeIdn+"&nmeRln="+rlnId+"&typ="+typ ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesGetTyppricechangeMemoRadioIdn(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}
function parseMessagesGetTyppricechangeMemoRadioIdn(responseXML) {
//alert('@parseMessage '+ fld);


var count = 0;
var memos = responseXML.getElementsByTagName("memos")[0];
var size =memos.childNodes.length;
var str ="<input type=\"hidden\" name=\"ttl_memo\" id=\"ttl_memo\" value="+size+" /><table><tr>";
str =str+"<td>All</td><td><input type=\"checkbox\" name=\"chAllMemo\" id=\"chAllMemo\" onclick=\"checkedAllMemo()\" /> </td></tr><tr>";
for (var loop = 0; loop < memos.childNodes.length; loop++) {
count++;
var memo = memos.childNodes[loop];


var id = memo.getElementsByTagName("id")[0];
var dte = memo.getElementsByTagName("dte")[0];
var typ = memo.getElementsByTagName("typ")[0];
// var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
var checkNme = "cb_memo";
str = str
+"<td>"+
"<input type=\"checkbox\" id="+checkNme+"_"+loop+" name="+checkNme+"_"+loop+" value="+id.childNodes[0].nodeValue+" />" +
"</td>" +
"<td>"+id.childNodes[0].nodeValue+"</td> "+
"<td>"+typ.childNodes[0].nodeValue+"</td> "+
"<td>"+dte.childNodes[0].nodeValue+"</td> ";
if(count==3){
str = str +"</tr><tr>";
count=0;
}
}

str = str+"</tr></table>" ;

document.getElementById('memoIdn').innerHTML = str;
}
function getlocationIdn(){
var prtyId =document.getElementById("byrId").value;
var loc =document.getElementById("location").value;
var url = "ajaxsrchAction.do?method=locationDlvIdn&nameIdn="+prtyId+"&loc="+loc;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessageslocationIdn(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessageslocationIdn(responseXML){

var str ="<table><tr>";
var count = 0;
var memos = responseXML.getElementsByTagName("memos")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
count++;
var memo = memos.childNodes[loop];


var id = memo.getElementsByTagName("id")[0];
var dte = memo.getElementsByTagName("dte")[0];
var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
str = str
+"<td>" +
"<input type=\"checkbox\" checked=\"checked\" name="+checkNme+" value="+id.childNodes[0].nodeValue+" />" +
"</td>" +
"<td>"+id.childNodes[0].nodeValue+"</td> "+
"<td>"+dte.childNodes[0].nodeValue+"</td> ";
if(count==4){
str = str +"</tr><tr>";
count=0;
}
}

str = str+"</tr></table>" ;

document.getElementById('memoIdn').innerHTML = str;


}

function SetCalculationDlvLocAll(typ){
if(typ=='DLV' || typ=='RECIVE'){
var rdCount = document.getElementById('rdCount').value;
var stkIdn = "";
for(var i=1 ; i <= rdCount;i++){
var lstkIdn = document.getElementById("STKIDN_"+i).value
stkIdn = stkIdn+","+lstkIdn;
}
var url = "ajaxsrchAction.do?method=DlvTotalLoc&stkIdn="+stkIdn+"&typ="+typ+"&select=ALL";

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesTotalLoc(req.responseXML , stkIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById("ttlqtyDLV").innerHTML = 0 ;
document.getElementById("ttlctsDLV").innerHTML = 0 ;
document.getElementById("ttlvluDLV").innerHTML = 0 ;
document.getElementById("ttldisDLV").innerHTML =0;
document.getElementById("ttlqtyRECIVE").innerHTML = 0 ;
document.getElementById("ttlctsRECIVE").innerHTML = 0 ;
document.getElementById("ttlvluRECIVE").innerHTML = 0 ;
document.getElementById("ttldisRECIVE").innerHTML =0;
}
}
function SetCalculationDlvLoc(stkIdn , typ , saleId){
var url = "ajaxsrchAction.do?method=DlvTotalLoc&stkIdn="+stkIdn+"&typ="+typ+"&saleId="+saleId+"&typ="+typ+"&select=S";

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesTotalLoc(req.responseXML , stkIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}


function parseMessagesTotalLoc(responseXML , stkIdn) {
//alert('@parseMessage '+ fld);
var memos = responseXML.getElementsByTagName("memos")[0];
for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];
var typ = (memo.getElementsByTagName("typ")[0]).childNodes[0].nodeValue;
var qty = (memo.getElementsByTagName("qty")[0]).childNodes[0].nodeValue;
var cts =(memo.getElementsByTagName("cts")[0]).childNodes[0].nodeValue;
var vlu =(memo.getElementsByTagName("vlu")[0]).childNodes[0].nodeValue;
var dis =(memo.getElementsByTagName("dis")[0]).childNodes[0].nodeValue;
document.getElementById("ttlqty"+typ).innerHTML = qty;
document.getElementById("ttlcts"+typ).innerHTML = cts;
document.getElementById("ttlvlu"+typ).innerHTML = vlu;
document.getElementById("ttldis"+typ).innerHTML = dis;
}
}


function GenPerformInvLoc(typ){
if(checkGenPerformInv()==false){
alert("Please Select Atleast One Radio Button To Generate Performa Inv");
return false;
}
var idnList = "";
var location = document.getElementById('location').value;
var byrIdn = document.getElementById('byrId1').value;
var byrAddIdn = document.getElementById('addrId').value;
var relIdn = document.getElementById('rlnId1').value;
var grpIdn = document.getElementById('grpIdn').value;
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('INV_')!=-1){
var isCheck = document.getElementById(fieldId).checked;
if(isCheck){
var val = document.getElementById(fieldId).value;
idnList = idnList+","+val;
}
}

}
}
var aadatcomm=document.getElementById('aadatcommval').value;
var brk1comm=document.getElementById('brk1commval').value;
var brk2comm=document.getElementById('brk2commval').value;
var brk3comm=document.getElementById('brk3commval').value;
var brk4comm=document.getElementById('brk4commval').value;
var courier=document.getElementById('courier').value;
var brocommval=parseFloat(aadatcomm)+parseFloat(brk1comm)+parseFloat(brk2comm)+parseFloat(brk3comm)+parseFloat(brk4comm);
if(brocommval=='')
brocommval='0'
var bankIdn = document.getElementById('bankIdn').value;
var bankAddr = document.getElementById('bankAddr').value;
var echarge=document.getElementById('echarge').value;
window.open("performa.do?method=perInvhk&byrIdn="+byrIdn+"&byrAddIdn="+byrAddIdn+"&bankIdn="+bankIdn+"&bankAddIdn="+bankAddr+"&perInvIdn="+idnList+"&relIdn="+relIdn+"&location="+location+"&echarge="+echarge+"&grpIdn="+grpIdn+"&brocommval="+brocommval+"&courier="+courier+"&typ=DLV", '_blank');
// window.open("saleDelivery.do?method=perInv&byrIdn="+byrIdn+"&byrAddIdn="+byrAddIdn+"&perInvIdn="+idnList , '_blank');
}


function GenPerformBranchLoc(){
if(checkGenPerformInv()==false){
alert("Please Select Atleast One Radio Button To Generate Performa Inv");
return false;
}
var idnList = "";
var location = document.getElementById('location').value;
var byrIdn = document.getElementById('byrId1').value;
var byrAddIdn = document.getElementById('addrId').value;
var relIdn = document.getElementById('rlnId1').value;
var grpIdn = document.getElementById('grpIdn').value;
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('INV_')!=-1){
var isCheck = document.getElementById(fieldId).checked;
if(isCheck){
var val = document.getElementById(fieldId).value;
idnList = idnList+","+val;
}
}

}
}
var aadatcomm=document.getElementById('aadatcommval').value;
var brk1comm=document.getElementById('brk1commval').value;
var brk2comm=document.getElementById('brk2commval').value;
var brk3comm=document.getElementById('brk3commval').value;
var brk4comm=document.getElementById('brk4commval').value;
var courier=document.getElementById('courier').value;
var brocommval=parseFloat(aadatcomm)+parseFloat(brk1comm)+parseFloat(brk2comm)+parseFloat(brk3comm)+parseFloat(brk4comm);
if(brocommval=='')
brocommval='0'
var bankIdn = document.getElementById('bankIdn').value;
var bankAddr = document.getElementById('bankAddr').value;
var echarge=document.getElementById('echarge').value;
var benefitId = document.getElementById('benefit');
var benefit="";
if(benefitId==null)
benefit="";
else
benefit=benefitId.value;
window.open("performa.do?method=perInvBrnch&byrIdn="+byrIdn+"&byrAddIdn="+byrAddIdn+"&bankIdn="+bankIdn+"&bankAddIdn="+bankAddr+"&perInvIdn="+idnList+"&relIdn="+relIdn+"&location="+location+"&echarge="+echarge+"&grpIdn="+grpIdn+"&brocommval="+brocommval+"&courier="+courier+"&benefit="+benefit+"&typ=DLV", '_blank');
// window.open("saleDelivery.do?method=perInv&byrIdn="+byrIdn+"&byrAddIdn="+byrAddIdn+"&perInvIdn="+idnList , '_blank');
}


function GenPerformInvSaleID(){
var saleid=document.getElementById('idn').value;
var invoice='';
var stt='';
var pktTyp='';
var buy ='';
var consign='';
var format='';
var grpIdn = document.getElementById('grpIdn').value;
var fmt = document.getElementById('fmt').value;
var buyId = document.getElementById('buy');
var bankIdnLoc = document.getElementById('bankIdnLoc').value;
var datePrt = document.getElementById('datePrt').value;
if(buyId==null)
buy="";
else
buy=buyId.value;

var consignId = document.getElementById('consign');
if(consignId==null)
consign="";
else
consign=consignId.value;


var invno = document.getElementById('invno');
if(invno==null)
invno="";
else
invno=invno.value;

var formatId = document.getElementById('format');
if(formatId==null)
format="";
else
format=formatId.value;

var frm_elements = document.getElementsByTagName('input');
for(j=0; j<frm_elements.length; j++) {
field_type = frm_elements[j].type.toLowerCase();
if(field_type=='radio'){
var elementid =frm_elements[j].id;
var elementNme = frm_elements[j].name;
if(document.getElementById(elementid).checked){
if(elementNme.indexOf('invoice')!=-1){
invoice=document.getElementById(elementid).value;
}
if(elementNme.indexOf('stt')!=-1){
stt=document.getElementById(elementid).value;
}
if(elementNme.indexOf('pktTyp')!=-1){
pktTyp=document.getElementById(elementid).value;
}
}
}
}
if(saleid!='' && invoice!='' && pktTyp!=''){
window.open("performa.do?method=perInv&form=Y&idn="+saleid+"&typ="+invoice+"&grpIdn="+grpIdn+"&stt="+stt+"&location="+fmt+"&pktTyp="+pktTyp+"&buy="+buy+"&consign="+consign+"&bankIdnLoc="+bankIdnLoc+"&format="+format+"&datePrt="+datePrt+"&invno="+invno, '_blank');
// window.open("saleDelivery.do?method=perInv&byrIdn="+byrIdn+"&byrAddIdn="+byrAddIdn+"&perInvIdn="+idnList , '_blank');
}
else{
alert('Please Enter Sale/Delivery Id.');
}
}


function salehistory(stkIdn){
document.getElementById('SALE_'+stkIdn).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
var url = "ajaxissueAction.do?method=salAvg&stkIdn="+stkIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagessalehistory(req.responseXML , stkIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagessalehistory(responseXML , stkIdn){
var msg='';
var colspan =4;
var ptks = responseXML.getElementsByTagName("values")[0];
var lnt = ptks.childNodes.length;
var str = "";
if(lnt==0){
str="<table><tr>No Data found.</tr></table>"
}else{
for (var loop = 0; loop < ptks.childNodes.length; loop++) {
var ptk = ptks.childNodes[loop];
var name = unescape((ptk.getElementsByTagName("nme")[0]).childNodes[0].nodeValue);
var date = (ptk.getElementsByTagName("dte")[0]).childNodes[0].nodeValue;
var rte = (ptk.getElementsByTagName("rte")[0]).childNodes[0].nodeValue;
var dis = (ptk.getElementsByTagName("dis")[0]).childNodes[0].nodeValue;
var raprte = (ptk.getElementsByTagName("raprte")[0]).childNodes[0].nodeValue;
var vnm = (ptk.getElementsByTagName("vnm")[0]).childNodes[0].nodeValue;
if(loop==0){
str ="<table><tr><td>"+name+"</td><td>"+date+"</td></tr></table><table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:700px\">";
}
else{
if(loop==1){
str += "<tr><th class=\"Orangeth\" align=\"center\">Name</th><th class=\"Orangeth\" align=\"center\">Date</th><th class=\"Orangeth\" align=\"center\">Pkt No.</th><th class=\"Orangeth\" align=\"center\">Rap Rate</th><th class=\"Orangeth\" align=\"center\">Rate</th><th class=\"Orangeth\" align=\"center\">Dis</th></tr>";
}
str = str+ "<tr><td align=\"center\">"+name+"</td> <td align=\"center\">"+date+"</td>";
str = str+ "<td align=\"center\">"+vnm+"</td>";
str = str+ "<td align=\"center\">"+raprte+"</td><td align=\"center\">"+rte+"</td><td align=\"center\">"+dis+"</td></tr>";
}
}
str = str+"</table>"
}
var divId = "SALE_"+stkIdn;
document.getElementById(divId).innerHTML = str;
}
function offerhistory(stkIdn,offerLmtAllow){
document.getElementById('OFFER_'+stkIdn).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
var url = "ajaxsrchAction.do?method=offerhistory&stkIdn="+stkIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesofferhistory(req.responseXML , stkIdn,offerLmtAllow);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagesofferhistory(responseXML , stkIdn,offerLmtAllow){
var offers = responseXML.getElementsByTagName("offers")[0];
var lnt = offers.childNodes.length;
var str = "";
if(lnt==0){
str="";
}else{
str ="<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:700px\">";
for (var loop = 0; loop < offers.childNodes.length; loop++) {
var offerby = offers.childNodes[loop];
var offer = (offerby.getElementsByTagName("offer")[0]).childNodes[0].nodeValue;
var offerDis = (offerby.getElementsByTagName("offerDis")[0]).childNodes[0].nodeValue;
var offercolor = (offerby.getElementsByTagName("offercolor")[0]).childNodes[0].nodeValue;
var offerByr = unescape((offerby.getElementsByTagName("offerbyr")[0]).childNodes[0].nodeValue);
var offertill = (offerby.getElementsByTagName("offertill")[0]).childNodes[0].nodeValue;
var offercmmt = (offerby.getElementsByTagName("offercmmt")[0]).childNodes[0].nodeValue;
var offerlmt = (offerby.getElementsByTagName("offerlmt")[0]).childNodes[0].nodeValue;
var offerstt = (offerby.getElementsByTagName("offerstt")[0]).childNodes[0].nodeValue;
var style='';
var title='';
if(offercolor=='red'){
style='color:#FF3030';  
title='Offer Expired';
}
if(loop==0){
str += "<tr><th class=\"Orangeth\" align=\"center\">Max Offer</th><th class=\"Orangeth\" align=\"center\">Max Offer Dis</th><th class=\"Orangeth\" align=\"center\">Offer By.</th><th class=\"Orangeth\" align=\"center\">Valid Till</th><th class=\"Orangeth\" align=\"center\">Comment</th><th class=\"Orangeth\" align=\"center\">Status</th>";
if(offerLmtAllow=='Y'){
str += "<th class=\"Orangeth\" align=\"center\">Offer Limit</th></tr>";
}
}
str = str+ "<tr style=\""+style+"\" title=\""+title+"\"><td align=\"center\">"+offer+"</td> <td align=\"center\">"+offerDis+"</td>";
str = str+ "<td align=\"center\">"+offerByr+"</td>";
str = str+ "<td align=\"center\">"+offertill+"</td><td align=\"center\">"+offercmmt+"</td><td align=\"center\">"+offerstt+"</td>";
if(offerLmtAllow=='Y'){
str = str+ "<td align=\"center\">"+offerlmt+"</td></tr>";
}
}
str = str+"</table>"
}
var divId = "OFFER_"+stkIdn;
document.getElementById(divId).innerHTML = str;
}

function GetBank(){
var byrObj = document.getElementById("byrId1");


var nmeIdn = byrObj.options[byrObj.selectedIndex].value;

if(nmeIdn!=0){
var url = "ajaxsrchAction.do?method=Banknme&nameIdn="+nmeIdn ;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
setparseMessagesBank(req.responseXML,nmeIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}

function setparseMessagesBank(responseXML, nmeIdn) {
//alert('@parseMessage '+ fld);

var columnDrop = document.getElementById("throubnk");
removeAllOptions(columnDrop);
var newOption = new Option();
newOption = new Option();
newOption.text = "-----select bank through-----";
newOption.value = "0";
columnDrop.options[0] = newOption;
var columns = responseXML.getElementsByTagName("banks")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var lkey = columnroot.getElementsByTagName("id")[0];
var lval = columnroot.getElementsByTagName("nme")[0];


var newOption = new Option();
newOption.text = lval.childNodes[0].nodeValue;
newOption.value = lkey.childNodes[0].nodeValue;;
columnDrop.options[loop] = newOption;
}

}

function setDfltGrpBank(){
var byrObj = document.getElementById("byrId1");
var nmeIdn = byrObj.options[byrObj.selectedIndex].value;
if(nmeIdn!=0){
var url = "ajaxsrchAction.do?method=setDfltGrpBank&nameIdn="+nmeIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
setparseMessagessetDfltGrpBank(req.responseXML,nmeIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}

function setparseMessagessetDfltGrpBank(responseXML, nmeIdn) {
var columns = responseXML.getElementsByTagName("dfltbanks")[0];
var detail = columns.childNodes[0];
var lkey = (detail.getElementsByTagName('id')[0]).childNodes[0].nodeValue;
var lval =(detail.getElementsByTagName('nme')[0]).childNodes[0].nodeValue;
if(lval=='Y'){
document.getElementById('bankIdn').value=lkey;
}
SetBankAddr(document.getElementById("bankIdn"));
}

function SetALLBuyDlv(stt){
    var cnt = document.getElementById("rdCount").value;
   for(var j=1 ; j<=cnt;j++){
    var rdId = stt+"_"+j;
     document.getElementById(rdId).checked=true;
   }
   SetCalculationBuyDlv();
 }
 
 function SetCalculationBuyDlv(){ 
 document.getElementById("IS_net_dis").innerHTML = "0" ;   
 document.getElementById("IS_vluamt").value = "0" ;
 document.getElementById("DLV_net_dis").innerHTML = "0" ; 
 document.getElementById("DLV_vluamt").value = "0" ;
  var sttAry = new Array();
   sttAry[0] = "SL";
   sttAry[1] = "PR";
   sttAry[2] = "DWP";
   sttAry[3] = "DLV";
   sttAry[4] = "RT";
   sttAry[5] = "CL";
   for(var i=0;i<sttAry.length;i++){
   var stt = sttAry[i];
   var str ="";
   var cnt = document.getElementById("rdCount").value;
   for(var j=1 ; j<=cnt;j++){
     var rdId = stt+"_"+j;
     var ischecked = document.getElementById(rdId).checked;
     if(ischecked){
     var lstkIdn = document.getElementById("STKIDN_"+j).value;
       str = str+","+lstkIdn;
     }
   } 
   if(str!=""){
   callCalculationBuyDlv(str,stt);
   }else{
            if(stt=='SL'){
               str="sl_";
            } else if(stt=='PR'){
               str="pr_";
            } else if(stt=='DWP'){
                str ="dwp_";
             }else if(stt=='DLV'){
                str ="dlv_";
             }else if(stt=='RT'){
                str ="rt_";
             }else if(stt=='CL'){
                str ="cl_";
             }
           document.getElementById(str+"qty").innerHTML ="0" ;
           document.getElementById(str+"cts").innerHTML = "0" ;
           document.getElementById(str+"avgPrc").innerHTML ="0" ;
           document.getElementById(str+"avgDis").innerHTML = "0" ;
           document.getElementById(str+"vlu").innerHTML = "0" ;
   }
           
   }
  }
  function callCalculationBuyDlv(str,stt){
     var url = "ajaxsrchAction.do?method=DlvTotalXL&stkIdn="+str+"&typ="+stt;
    var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBuyDlvTotal(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);    
  }
  function parseMessagesBuyDlvTotal(responseXML) {
    //alert('@parseMessage '+ fld);
     var memos = responseXML.getElementsByTagName("memos")[0];
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
           var memo = memos.childNodes[loop];
          
         
           var qty = memo.getElementsByTagName("qty")[0];
           var cts = memo.getElementsByTagName("cts")[0];
          var Vlu = (memo.getElementsByTagName("vlu")[0]).childNodes[0].nodeValue;
           var dis = memo.getElementsByTagName("dis")[0];
           
              var prc = memo.getElementsByTagName("prc")[0];
               var stt = memo.getElementsByTagName("typ")[0].childNodes[0].nodeValue ;
           var str="";
           if(stt=='SL'){
               str="sl_";
            } else if(stt=='PR'){
               str="pr_";
            } else if(stt=='DWP'){
                str ="dwp_";
             }else if(stt=='DLV'){
                str ="dlv_";
             }else if(stt=='RT'){
                str ="rt_";
             }else if(stt=='CL'){
                str ="cl_";
             }
           document.getElementById(str+"qty").innerHTML = qty.childNodes[0].nodeValue ;
           document.getElementById(str+"cts").innerHTML = cts.childNodes[0].nodeValue ;
           document.getElementById(str+"avgPrc").innerHTML = prc.childNodes[0].nodeValue ;
           document.getElementById(str+"avgDis").innerHTML = dis.childNodes[0].nodeValue ;
           document.getElementById(str+"vlu").innerHTML = Vlu ;
           if(stt=='DWP'){
           document.getElementById("IS_net_dis").innerHTML = Vlu ;   
           document.getElementById("IS_vluamt").value = Vlu ;
           }else if(stt=='DLV'){
           document.getElementById("DLV_net_dis").innerHTML = Vlu ; 
           document.getElementById("DLV_vluamt").value = Vlu ;
           }
     }}
     
  function userLocation(obj, typ){
    var loc = obj.value;
     var url = "ajaxsrchAction.do?method=usrLoc&Loc="+loc+"&typ="+typ;
    var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBuyerList(req.responseXML,typ);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);    
  }
  
  function parseMessagesBuyerList(responseXML,typ) {
//alert('@parseMessage '+ fld);

var columnDrop = document.getElementById("byrId");
removeAllOptions(columnDrop);

var columns = responseXML.getElementsByTagName("nmes")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var lkey = columnroot.getElementsByTagName("id")[0];
var lval =columnroot.getElementsByTagName("buyNme")[0];
 if(loop==0){
    prtyId = lkey.childNodes[0].nodeValue;
 }
 var newOption = new Option();
newOption.text = unescape(lval.childNodes[0].nodeValue);
newOption.value = lkey.childNodes[0].nodeValue;;
columnDrop.options[loop] = newOption;
}
if(typ=='SALE'){
getTrms(columnDrop,typ);
}else{
getFinalByr(columnDrop ,"SL");
}
}



function getFinalByrXL(obj ,typ){
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0){
       var url = "ajaxsrchAction.do?method=FinalByrNR&bryIdn="+byrId+"&typ="+typ ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFNByrXL(req.responseXML , byrId , typ);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }else{
            var columnDrop = document.getElementById("prtyId");
            removeAllOptions(columnDrop);
             columnDrop = document.getElementById('rlnId');
            removeAllOptions(columnDrop);
             document.getElementById('memoIdn').innerHTML = "";
       }
 }
 
 function  parseMessagesFNByrXL(responseXML , byrId , typ) {
    //alert('@parseMessage '+ fld);
     var prtyId = 0;
      var columnDrop = document.getElementById("prtyId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
             if(loop==0)
             prtyId = lkey.childNodes[0].nodeValue;
       }
      
      getSaleTrmsXL(prtyId , typ);
}
 
 function getSaleTrmsXL(byrId , typ){
     
       var url = "ajaxsrchAction.do?method=loadSLTrm&bryId="+byrId ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesSaleTrmXL(req.responseXML , typ);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 function parseMessagesSaleTrmXL(responseXML , typ){
     var columnDrop = document.getElementById('rlnId');
     removeAllOptions(columnDrop);
      var columns = responseXML.getElementsByTagName("trms")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
           
           
       }
   
   getSaleIdnXL(typ);
    
 }
 
 function getSaleIdnXL(typ){
     var  prtyId =document.getElementById("prtyId").value;
     var rlnId = document.getElementById("rlnId").value;
   
      var url = "ajaxsrchAction.do?method=saleIdn&nameIdn="+prtyId+"&rlnId="+rlnId+"&typ="+typ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                        parseMessagesMemoIdnXL(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 
  function parseMessagesMemoIdnXL(responseXML) {
    //alert('@parseMessage '+ fld);
      var memoDtlObj = document.getElementById('memoDtl');
      var memoDtlVal = memoDtlObj.length;
      var maxCnt = 0;
      if(memoDtlVal < 3){
      maxCnt=4;
      }if(memoDtlVal < 5){
       maxCnt=2;
      }else{
      maxCnt=1;
      }
        var memos = responseXML.getElementsByTagName("memos")[0];
       if(memos.childNodes.length==1){
       maxCnt=1;
      }
      
      var str="<table class=\"grid1\"><tr>";
      for(var i=0;i<maxCnt;i++){
      if(i==0)
      str=str+"<th></th>";
     else
      str=str+"<th></th>";
       for (var j=0; j<memoDtlVal; j++) {
       str=str+"<th>"+memoDtlObj[j].text+"</th>";
       }
     }
      str=str+"</tr><tr>";
      
       var count = 0;
     
    
       for (var loop = 0; loop < memos.childNodes.length; loop++) {
          count++;
           var memo = memos.childNodes[loop];
          
         
           var id = memo.getElementsByTagName("id")[0];
              
             var checkNme = "cb_memo_"+id.childNodes[0].nodeValue;
              str = str 
                +"<td>" +
                "<input type=\"radio\"  id=\"saleId\" name=\"saleId\" value="+id.childNodes[0].nodeValue+" />" +
                "</td>" ;
                 for (var x=0; x<memoDtlVal; x++) {
                   str=str+"<td>"+((memo.getElementsByTagName(memoDtlObj[x].value)[0])).childNodes[0].nodeValue+"</td>";
                 }
                 if(count==maxCnt){
                str = str +"</tr><tr>";
                count=0;
               }
      }
      
      str = str+"</tr></table>" ;
   
     document.getElementById('memoIdn').innerHTML = str;
       
       
      
     
 }
 
 function userLocationXL(obj, typ){
    var loc = obj.value;
     var url = "ajaxsrchAction.do?method=usrLoc&Loc="+loc+"&typ="+typ;
    var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBuyerListXL(req.responseXML,typ);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);    
  }
  
  function parseMessagesBuyerListXL(responseXML,typ) {
//alert('@parseMessage '+ fld);

var columnDrop = document.getElementById("byrId");
removeAllOptions(columnDrop);

var columns = responseXML.getElementsByTagName("nmes")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var lkey = columnroot.getElementsByTagName("id")[0];
var lval = columnroot.getElementsByTagName("buyNme")[0];
 if(loop==0){
    prtyId = lkey.childNodes[0].nodeValue;
 }
 var newOption = new Option();
newOption.text = unescape(lval.childNodes[0].nodeValue);
newOption.value = lkey.childNodes[0].nodeValue;;
columnDrop.options[loop] = newOption;
}
if(typ=='SALE'){
getTrmsXL(columnDrop,typ);
}else{
getFinalByrXL(columnDrop ,"SL");
}
}

  function getFinalByrDlvMix(obj ,typ){
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0){
       var url = "mixDeliveryUpdateAction.do?method=FinalByrMIX&bryIdn="+byrId+"&typ="+typ ;
   
        var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFNByrDlvMix(req.responseXML , byrId , typ);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }else{
            var columnDrop = document.getElementById("prtyId");
            removeAllOptions(columnDrop);
             columnDrop = document.getElementById('rlnId');
            removeAllOptions(columnDrop);
             document.getElementById('memoIdn').innerHTML = "";
       }
 }
 
 function  parseMessagesFNByrDlvMix(responseXML , byrId , typ) {
    //alert('@parseMessage '+ fld);
     var prtyId = 0;
      var columnDrop = document.getElementById("prtyId");
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
             if(loop==0)
             prtyId = lkey.childNodes[0].nodeValue;
       }
      
      getSaleTrmsDlvMix(prtyId , typ);
}

 function getSaleTrmsDlvMix(byrId , typ){
     
       var url = "mixDeliveryUpdateAction.do?method=loadSLTrm&bryId="+byrId ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesSaleTrmDlvMix(req.responseXML , typ);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 function parseMessagesSaleTrmDlvMix(responseXML , typ){
     var columnDrop = document.getElementById('rlnId');
     removeAllOptions(columnDrop);
      var columns = responseXML.getElementsByTagName("trms")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
           
           
       }

   getSaleIdnDlvMix(typ);
    
 }
 
 function getSaleIdnDlvMix(typ){

     var  prtyId =document.getElementById("prtyId").value;
     var rlnId = document.getElementById("rlnId").value;
      var url = "mixDeliveryUpdateAction.do?method=saleIdn&nameIdn="+prtyId+"&rlnId="+rlnId+"&typ="+typ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                        parseMessagesMemoIdn(req.responseXML);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
