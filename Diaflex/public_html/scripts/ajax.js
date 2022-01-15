 function initRequest() {
       if (window.XMLHttpRequest) {
           return new XMLHttpRequest();
       } else if (window.ActiveXObject) {
           isIE = true;
           return new ActiveXObject("Microsoft.XMLHTTP");
       }
   }

function setHiddenBtn(){
       document.getElementById('btn').value = 'update';
       return true;
}



function  CheckValidFname(btnName) {
         
           var fnme = document.getElementById("fnme").value;
           fnme=fnme.replace('&', '%26'); 
           fnme = fnme.replace('+', '%2B'); 
           fnme = fnme.replace('-', '%2D');
           var url = "ajaxContactAction.do?typ=check&fnme="+fnme;
             
           var req = initRequest();
           req.onreadystatechange = function() {    
         
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesValid(req.responseXML , btnName);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       
   }
   
  
  function parseMessagesValid(responseXML , btnName) {
    //alert('@parseMessage '+ fld);
    
 
    
       var totals = responseXML.getElementsByTagName("mnme")[0];
    
       
       var msg = totals.getElementsByTagName("msg")[0];
       
       var nmeIdn = msg.childNodes[0].nodeValue;
     
       if(nmeIdn=='YES'){
       document.getElementById('btn').value = btnName;
       document.forms[0].submit()
       }else{
        var r=confirm("Company already exit . Do You Want to see detail?");
         if(r==true){
            window.open('../contact/NmeReport.jsp?view=Y&nmeIdn='+nmeIdn,'_self') 
         }else{
             document.getElementById("fnme").value = "";
         }
           return false;
       }
          
   }
/* Table Columns */

function getTableColumn(tblNmeobj, fld) {
           var tblNme = tblNmeobj.options[tblNmeobj.options.selectedIndex].value;
          //alert('@getColumns '+ tblNme);
           var url = "ajaxAction.do?tableNm="+tblNme ;
             
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessages(req.responseXML, fld);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       
   }
   
 function parseMessages(responseXML, fld) {
    //alert('@parseMessage '+ fld);
        var columnDrop = document.getElementById(fld);
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("columns")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("keys")[0];
           var lval = columnroot.getElementsByTagName("values")[0];
           
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;;
             columnDrop.options[loop] = newOption;
       }
      
     
 }
 
 function srchChkSelOnText(pBoxId, pIdn,view) {
 var pBox = document.getElementById(pBoxId);
 srchChkSel(pBox, pIdn,view);
 }
 
  function srchChkSelPair(pBox, pIdn,view) {
   var stt = pBox.checked;   
   var pairIdStkIdnList="";
     var pairIdSrt = document.getElementById('cb_pr_'+pIdn).value;
       if(pairIdSrt!='' && view=='SRCH'){
            var frm_elements = document.forms['stock'].elements; 
            for(i=0; i<frm_elements.length; i++) {
               field_type = frm_elements[i].type.toLowerCase();
                if(field_type=='checkbox') {
                 var fieldId = frm_elements[i].id;
                 if(fieldId.indexOf('cb_memo_')!=-1){
                   var isDisabled = frm_elements[i].disabled
                    if(!isDisabled){
                       var pairIdStkIdn = document.getElementById(fieldId).value;
                       if(pairIdStkIdn!=pIdn){
                           var pairIdSrt2 = document.getElementById('cb_pr_'+pairIdStkIdn).value;
                           if(pairIdSrt2==pairIdSrt){
                               document.getElementById(fieldId).checked=stt;
                               var prBox= document.getElementById(fieldId);
                               setBGColorOnCHK(prBox,pairIdStkIdn);
                               pairIdStkIdnList+="~"+pairIdStkIdn;
                           }
                       }
                    }
                }
                }
           }
       }

       var idnLst = pIdn+"~"+pairIdStkIdnList;
       srchChkSel(pBox, idnLst,view);
  
  }
  function srchChkSel(pBox, pIdn,view) {
  loading();
     var stt = pBox.checked;                                                                                                                   
     var lstNme = document.getElementById('lstNme').value;
       var memoTyp = document.getElementById('memoTyp').value;
     if(pIdn=='ALL'){
     
       var check =document.getElementById('ALL').checked;
       var frm_elements;
       if(view!='BID')
       frm_elements = document.forms['stock'].elements; 
       else
       frm_elements = document.forms[0].elements;
 
        for(i=0; i<frm_elements.length; i++) {

           field_type = frm_elements[i].type.toLowerCase();

            if(field_type=='checkbox') {
              var fieldId = frm_elements[i].id;
             if(fieldId.indexOf('cb_memo_')!=-1 && fieldId.indexOf('cb_memo_offer_')==-1){
               var isDisabled = frm_elements[i].disabled
                if(isDisabled){
                if(memoTyp=='Z'){
                         frm_elements[i].checked = check;
                 }else if(memoTyp=='WAP' && fieldId=='cb_memo_PP'){
                   frm_elements[i].checked = check;
       
                  }
                 }else{
                     frm_elements[i].checked = check;
                }
       }}}
     }
     var url = "ajaxsrchAction.do?method=GtList&stt="+stt+"&stkIdn="+pIdn+"&lstNme="+lstNme+"&memoTyp="+memoTyp+"&view="+view;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                   
                      parseMessagesTtls(req.responseXML)
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
           closeloading();
}
 function optCB(pBox, pIdn,lstTyp , flg) {
     var stt = pBox.checked;
     var url = "ajaxsrchAction.do?method=selectList&Lst="+lstTyp+"&stt="+stt+"&stkIdn="+pIdn+"&flg="+flg ;
   
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                   
                      parseMessagesTtls(req.responseXML)
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}


function CheckAllCheckBox(){
var check =document.getElementById('ALL').checked;
 var frm_elements = document.forms['stock'].elements; 
 var memoTyp = document.getElementById('memoTyp').value;
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='checkbox') {
   var fieldId = frm_elements[i].id;
   if(fieldId.indexOf('cb_memo_')!=-1 && fieldId.indexOf('cb_memo_offer_')==-1){
     var isDisabled = frm_elements[i].disabled
      if(isDisabled){
      if(memoTyp=='Z'){
        frm_elements[i].checked = check;
      }else if(memoTyp=='WAP' && fieldId=='cb_memo_PP'){
        frm_elements[i].checked = check;
       
      }
      }else{
       frm_elements[i].checked = check;
      }
       }}
}


    
     var url = "ajaxsrchAction.do?method=selectList&ALL=Y&stt="+check+"&memoTyp="+memoTyp ;
   
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                   
                      parseMessagesTtls(req.responseXML)
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}

 function parseMessagesTtls(responseXML) {
//alert('@parseMessage '+ fld);

 var smmrydtlStr = document.getElementById('smmrydtlStr').value;
         var totals = responseXML.getElementsByTagName("values")[0];
         var columnroot = totals.childNodes[0];
          var cnt = columnroot.getElementsByTagName("cnt")[0];
         var qty = columnroot.getElementsByTagName("qty")[0];
        var cts = columnroot.getElementsByTagName("cts")[0];
         var base = columnroot.getElementsByTagName("base")[0];
          var vlu = columnroot.getElementsByTagName("vlu")[0];
         var avg = columnroot.getElementsByTagName("avg")[0];
         var dis = columnroot.getElementsByTagName("dis")[0];
         var rap = columnroot.getElementsByTagName("rapvlu")[0];
          var netprc = columnroot.getElementsByTagName("netprc")[0];
         var netdis = columnroot.getElementsByTagName("netdis")[0];
         var netvlu = columnroot.getElementsByTagName("netvlu")[0];
           var diff = columnroot.getElementsByTagName("diff")[0];
         var ctg = columnroot.getElementsByTagName("ctg")[0];
         var loyVlu = columnroot.getElementsByTagName("loyvlu")[0];
           var memVlu = columnroot.getElementsByTagName("memvlu")[0];
         var loyPct = columnroot.getElementsByTagName("loyPct")[0];
         var webPct = columnroot.getElementsByTagName("webPct")[0];
         var webVlu = columnroot.getElementsByTagName("webVlu")[0];
      if(smmrydtlStr.indexOf('selectcnt')!=-1){
        document.getElementById("selectcnt").innerHTML = cnt.childNodes[0].nodeValue;
        document.getElementById("regcnt").innerHTML = columnroot.getElementsByTagName("regcnt")[0].childNodes[0].nodeValue ;
        }
         
        if(smmrydtlStr.indexOf('selectpcs')!=-1){
        document.getElementById("selectpcs").innerHTML = qty.childNodes[0].nodeValue;
        document.getElementById("regpcs").innerHTML = columnroot.getElementsByTagName("regqty")[0].childNodes[0].nodeValue ;
        }
        if(smmrydtlStr.indexOf('selectcts')!=-1){
        document.getElementById("selectcts").innerHTML = cts.childNodes[0].nodeValue;
        document.getElementById("regcts").innerHTML = columnroot.getElementsByTagName("regcts")[0].childNodes[0].nodeValue ;
        }
        if(smmrydtlStr.indexOf('selectbase')!=-1){
        document.getElementById("selectbase").innerHTML = base.childNodes[0].nodeValue;
        document.getElementById("regbase").innerHTML = columnroot.getElementsByTagName("regbase")[0].childNodes[0].nodeValue ;
        }
        if(smmrydtlStr.indexOf('selectavgprice')!=-1){
        document.getElementById("selectavgprice").innerHTML = avg.childNodes[0].nodeValue;
        document.getElementById("regavgprice").innerHTML = columnroot.getElementsByTagName("regavg")[0].childNodes[0].nodeValue ;
        }
        if(smmrydtlStr.indexOf('selectavgdis')!=-1){
       document.getElementById("selectavgdis").innerHTML = dis.childNodes[0].nodeValue;
       document.getElementById("regavgdis").innerHTML =columnroot.getElementsByTagName("regdis")[0].childNodes[0].nodeValue ;
       }
       if(smmrydtlStr.indexOf('selectvalues')!=-1){
       document.getElementById("selectvalues").innerHTML = vlu.childNodes[0].nodeValue;
       document.getElementById("regvalues").innerHTML = columnroot.getElementsByTagName("regvlu")[0].childNodes[0].nodeValue ;
       }
       if(smmrydtlStr.indexOf('selectrapvlu')!=-1){
       document.getElementById("selectrapvlu").innerHTML = rap.childNodes[0].nodeValue;
       document.getElementById("regrapvlu").innerHTML = columnroot.getElementsByTagName("regrapvlu")[0].childNodes[0].nodeValue ;
       }
       if(smmrydtlStr.indexOf('selectcavg')!=-1){
       document.getElementById("selectcavg").innerHTML =columnroot.getElementsByTagName("cavg")[0].childNodes[0].nodeValue ;
       document.getElementById("regcavg").innerHTML =columnroot.getElementsByTagName("regcavg")[0].childNodes[0].nodeValue ;
       }
       if(smmrydtlStr.indexOf('selectcdis')!=-1){
       document.getElementById("selectcdis").innerHTML = columnroot.getElementsByTagName("cdis")[0].childNodes[0].nodeValue ;
       document.getElementById("regcdis").innerHTML = columnroot.getElementsByTagName("regcdis")[0].childNodes[0].nodeValue ;
       }
       if(smmrydtlStr.indexOf('selectcvlu')!=-1){
       document.getElementById("selectcvlu").innerHTML = columnroot.getElementsByTagName("cvlu")[0].childNodes[0].nodeValue ;
       document.getElementById("regcvlu").innerHTML = columnroot.getElementsByTagName("regcvlu")[0].childNodes[0].nodeValue ;
       }
        
       if(smmrydtlStr.indexOf('selectavgrap')!=-1){
       document.getElementById("selectavgrap").innerHTML = columnroot.getElementsByTagName("avgrap")[0].childNodes[0].nodeValue ;
       document.getElementById("regavgrap").innerHTML = columnroot.getElementsByTagName("regavgrap")[0].childNodes[0].nodeValue ;
       }
       document.getElementById("vluamt").value=vlu.childNodes[0].nodeValue;
       document.getElementById("net_dis").innerHTML=vlu.childNodes[0].nodeValue;
       var cnt = document.getElementById("CNT").value;
       if(cnt=='kj' || cnt=='ku'){
          document.getElementById("fnlDisc").innerHTML=netdis.childNodes[0].nodeValue;
          document.getElementById("fnlPrice").innerHTML=netprc.childNodes[0].nodeValue;
          document.getElementById("fnlAmt").innerHTML=netvlu.childNodes[0].nodeValue;
          document.getElementById("memVlu").innerHTML=memVlu.childNodes[0].nodeValue;
          document.getElementById("loyVlu").innerHTML=loyVlu.childNodes[0].nodeValue;
          document.getElementById("diff").innerHTML=diff.childNodes[0].nodeValue;
          document.getElementById("lCtg").innerHTML=loyPct.childNodes[0].nodeValue+"% "+ctg.childNodes[0].nodeValue;
          document.getElementById("webdiscPct").innerHTML=webPct.childNodes[0].nodeValue+"% ";
          document.getElementById("webdiscvlu").innerHTML=webVlu.childNodes[0].nodeValue;
       }
      var memVal= document.getElementById("memVlu");
      if(memVal!=null){
      memVal.innerHTML=memVlu.childNodes[0].nodeValue;
      }
}


 
 function getTrms(obj,page){
       var bryIdn = obj.options[obj.options.selectedIndex].value;
       if(bryIdn!='' && bryIdn!='0'){
       var url = "ajaxsrchAction.do?method=loadTrm&bryId="+bryIdn ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesTrm(req.responseXML,page,bryIdn);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }else{
      var columnDrop = document.getElementById('rlnId');
      removeAllOptions(columnDrop);
      var newOption = new Option();
       newOption = new Option();
       newOption.text = "---Selet---";
       newOption.value = "0";
       columnDrop.options[0] = newOption;
}
         
 }
 
 function getTrmsNME(bry,page){
var bryIdn=document.getElementById(bry).value;
if(bryIdn!='' && bryIdn!='0'){
var url = "ajaxsrchAction.do?method=loadTrm&bryId="+bryIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesTrm(req.responseXML,page,bryIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
      var columnDrop = document.getElementById('rlnId');
      removeAllOptions(columnDrop);
      var newOption = new Option();
       newOption = new Option();
       newOption.text = "---Selet---";
       newOption.value = "0";
       columnDrop.options[0] = newOption;
}
}

 function parseMessagesTrm(responseXML , page , bryIdn){
     var columnDrop = document.getElementById('rlnId');
    
      removeAllOptions(columnDrop);
      if(page!='REG' || page!='SRCH'){
      var newOption = new Option();
       newOption = new Option();
             newOption.text = "---Selet---";
             newOption.value = "0";
             columnDrop.options[0] = newOption;
      }
       var columns = responseXML.getElementsByTagName("trms")[0];
       var dftTrm ="";
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var selectIndex = loop+1;
          if(page=='REG' || page=='SRCH' )
            selectIndex = loop;
         
           var columnroot = columns.childNodes[loop];
          
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
           
            
             newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[selectIndex] = newOption;
             if(dftTrm=='')
             dftTrm=lkey.childNodes[0].nodeValue;
       }
    if(page=='SRCH')
    getDropDwonXrt(columnDrop);
 }
 
 function getWR(bryIdn , rlnId){
    
        var url = "ajaxsrchAction.do?method=loadWR&bryId="+bryIdn+"&rlnId="+rlnId ;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesWR(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
function parseMessagesWR(responseXML){
var str = "";
var counter=1;
var columns = responseXML.getElementsByTagName("memos")[0];

if(columns.childNodes.length==0){
document.getElementById('wrDiv').innerHTML = "";
}else{
document.getElementById('wrDiv').innerHTML = "";
str = " <table id=\"WRTABLE\"><tr><td><b>Web Request</b><input type=\"checkbox\" id=\"WRALL\" onclick=\"checkalladdInSrchRefBox()\" /> ALL";
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var memoId = columnroot.getElementsByTagName("memoId")[0];
var memodte = columnroot.getElementsByTagName("memoDte")[0];//shiv
var memoFlg = columnroot.getElementsByTagName("memoFlg")[0];

var memoIdVal = memoId.childNodes[0].nodeValue;
var memodteVal= memodte.childNodes[0].nodeValue;
var memoFlgVal= unescape(memoFlg.childNodes[0].nodeValue);
str = str + " <input type=\"checkbox\" id=\"WR_"+memoIdVal+"\" onclick=\"addInSrchRefBox('WR_"+memoIdVal+"')\" value="+memoIdVal+" /> " +
memoIdVal+" ("+memodteVal+")";
if(memoFlgVal!='NA'){
str = str +" ("+memoFlgVal+")";
}
}
str = str +" </td></tr></table>" ;
document.getElementById('wrDiv').innerHTML = str;
}
}
 
 
  function getHR(bryIdn , rlnId){
    
        var url = "ajaxsrchAction.do?method=loadHR&bryId="+bryIdn+"&rlnId="+rlnId ;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesHR(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
function parseMessagesHR(responseXML){
var str = "";
var counter=1;
var columns = responseXML.getElementsByTagName("memos")[0];

if(columns.childNodes.length==0){
document.getElementById('hrDiv').innerHTML = "";
}else{
document.getElementById('hrDiv').innerHTML = "";
str = " <table id=\"HRTABLE\"><tr><td><b>HH Request</b><input type=\"checkbox\" id=\"HRALL\" onclick=\"checkalladdInSrchRefBoxHR()\" /> ALL";
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var memoId = columnroot.getElementsByTagName("memoId")[0];
var memodte = columnroot.getElementsByTagName("memoDte")[0];//shiv
var memoFlg = columnroot.getElementsByTagName("memoFlg")[0];

var memoIdVal = memoId.childNodes[0].nodeValue;
var memodteVal= memodte.childNodes[0].nodeValue;
var memoFlgVal= unescape(memoFlg.childNodes[0].nodeValue);
str = str + " <input type=\"checkbox\" id=\"HR_"+memoIdVal+"\" onclick=\"addInSrchRefBox('HR_"+memoIdVal+"')\" value="+memoIdVal+" /> " +
memoIdVal+" ("+memodteVal+")";
if(memoFlgVal!='NA'){
str = str +" ("+memoFlgVal+")";
}
}
str = str +" </td></tr></table>" ;
document.getElementById('hrDiv').innerHTML = str;
}
}
 function getMemoByByr(obj){
     var byrIdn = obj.value;
     getLstMemo(byrIdn , "0");
 }
 function getLstMemo(bryIdn , rlnId){
    
        var url = "ajaxsrchAction.do?method=getLstMemo&bryId="+bryIdn+"&rlnId="+rlnId ;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesgetLstMemo(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
function parseMessagesgetLstMemo(responseXML){

var str = "";
var counter=1;
var columns = responseXML.getElementsByTagName("memos")[0];

if(columns.childNodes.length==0){
document.getElementById('lstMemoDiv').innerHTML = "";
}else{
document.getElementById('lstMemoDiv').innerHTML = "";
str = "<table  cellspacing=\"1\" cellpadding=\"1\" class=\"grid1\"  width=\"100%\"><tr><td colspan=\"5\" align=\"center\" onclick=\"displayDiv('LstMemo')\"><b>Last Memo's </b></td></tr><tr><td><div id=\"LstMemo\" style=\"margin:0px; padding:0px;\"><table class=\"grid1\" width=\"100%\"><tr><th align=\"center\">Sr No</th><th align=\"center\">Memo Id</th><th align=\"center\">Qty</th><th align=\"center\">Cts</th><th align=\"center\">Typ</th><th align=\"center\">Date</th></tr>";
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var memoId = columnroot.getElementsByTagName("memoId")[0].childNodes[0].nodeValue;
var dte = columnroot.getElementsByTagName("dte")[0].childNodes[0].nodeValue;//shiv
var qty = columnroot.getElementsByTagName("qty")[0].childNodes[0].nodeValue;//shiv
var cts = columnroot.getElementsByTagName("cts")[0].childNodes[0].nodeValue;//shiv
var typ = columnroot.getElementsByTagName("typ")[0].childNodes[0].nodeValue;//shiv
str = str + "<tr><td align=\"center\">" +counter+"</td><td align=\"center\">"+
memoId+"</td><td align=\"right\">"+qty+"</td><td align=\"right\">"+cts+"</td><td align=\"right\">"+typ+"</td><td align=\"right\">"+dte+"</td></tr>";
counter++;
}
str = str +"</table></div></td></tr></table>" ;
document.getElementById('lstMemoDiv').innerHTML = str;
}
}

 function getDmd(bryIdn , dftTrm){


//alert('@getColumns '+ tblNme);
// var url = "ajaxsrchAction.do?method=loadDmd&bryId="+bryIdn ;
var url = "ajaxsrchAction.do?method=loadDmd&bryId="+bryIdn+"&term="+dftTrm ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

parseMessagesDmd(req.responseXML , bryIdn,dftTrm);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}
function parseMessagesDmd(responseXML , bryIdn,dftTrm){
var str = "";
var columns = responseXML.getElementsByTagName("dmds")[0];
if(columns.childNodes.length==0){
document.getElementById('favDmd').innerHTML = "";
}else{
document.getElementById('favDmd').innerHTML = "";
str = " <table><tr><td nowrap=\"nowrap\"><span class=\"txtBold\">Demand</span></td>" +
"<td align=\"right\">" +
"<div class=\"multiplePrpdiv\" id=\"dmdlist\" align=\"center\" style=\"display:none; overflow:auto; width=250px; height:200px; padding-top:0px; margin-top:21px; margin-left:6px;\">" +
"<table cellpadding=\"3\" width=\"240px\" class=\"multipleBg\" cellspacing=\"3\">" +
"<tr><td colspan=\"3\">" +
"<input type=\"button\" value=\"Select ALL\" onclick=\"checkselect('dmdlist')\" style=\"font-size:10px;\" />" +
"<input type=\"button\" value=\"Clear All\" style=\"font-size:10px; margin-bottom:5px;\" onclick=\"uncheck('dmdlist')\" />" +
"<input type=\"button\" id=\"cls\" value=\"Close\" onclick=\"Hide('dmdlist')\" style=\"font-size:10px;\" />" +
"</td></tr>";
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = columnroot.getElementsByTagName("dmdDsc")[0];
var lkey = columnroot.getElementsByTagName("dmdId")[0];
var dmdDscVal = unescape(lval.childNodes[0].nodeValue);
var dmdIdVal = lkey.childNodes[0].nodeValue ;
var dmdDscID= "dmdDsc"+dmdIdVal;
var chId = "Dmdch_"+dmdIdVal;
var dmdClick = "loadDmdPrp('','"+dmdIdVal+"')";
var onclick= "checkDMD(this,'"+dmdIdVal+"')";
str = str + "<tr><td><input type=\"checkbox\" value="+dmdIdVal+" id="+chId+" onclick="+onclick+"> " +
"</td><td><A onclick="+dmdClick+"><label id="+dmdDscID+">"+dmdDscVal+"</label></a></td></tr>";
}
str = str +" </table></div><table cellpadding=\"0\" cellspacing=\"0\">" +
"<tr><td><input type=\"text\" disabled=\"disabled\" class=\"txtStyle\" size=\"22\" id=\"multiTxtdm\" /></td><td>&nbsp;&nbsp;</td>" +
" <td><img src=\"../images/plus.jpg\" id=\"IMGD_dmdlist\" class=\"img\" onclick=\"DisplayDiv('dmdlist')\" style=\"display:block\" border=\"0\"/></td>" +
" <td><img src=\"../images/minus.jpg\" id=\"IMGU_dmdlist\" class=\"img\" onclick=\"Hide('dmdlist')\" style=\"display:none\" border=\"0\"/></td>" +
" </tr></table></td>" +
"<td></td><td><input type=\"button\" value=\"View\" class=\"submit\" onclick=\"srchDmd('"+bryIdn+"','"+dftTrm+"')\" /> </td></tr></table>" ;

document.getElementById('favDmd').innerHTML = str;
}

var rlnId = document.getElementById('rlnId');

}


  function getWL(bryIdn , dftTrm){


//alert('@getColumns '+ tblNme);
// var url = "ajaxsrchAction.do?method=loadDmd&bryId="+bryIdn ;
var url = "ajaxsrchAction.do?method=loadWL&bryId="+bryIdn+"&term="+dftTrm ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

parseMessagesWL(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}
function parseMessagesWL(responseXML ){
   var columnDrop = document.getElementById('grpNme');
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("grpNmes")[0];
       var newOption = new Option();
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lval = columnroot.getElementsByTagName("grp")[0];
             newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lval.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
       }
             newOption = new Option();
             newOption.text = "--------Selet--------";
             newOption.value = "0";
             columnDrop.options[columns.childNodes.length] = newOption;
       if(columns.childNodes.length >0){
       document.getElementById('grpNmeDiv').style.display='block';
       }
}
 
 function getEmployee(obj){
       var bryIdn = obj.options[obj.options.selectedIndex].value;
        
          //alert('@getColumns '+ tblNme);
           var url = "ajaxsrchAction.do?method=loadEmp&bryId="+bryIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesEmp(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       document.getElementById('partytext').value="";
       document.getElementById('party').value="";
 var columnDrop = document.getElementById('rlnId');
 removeAllOptions(columnDrop);
 var newOption = new Option();
  newOption = new Option();
  newOption.text = "---Selet---";
  newOption.value = "0";
  columnDrop.options[0] = newOption;
 }
 
  function parseMessagesEmp(responseXML){
     var columnDrop = document.getElementById('party');
     //alert(responseXML);
      removeAllOptions(columnDrop);
      
      var newOption = new Option();
      newOption = new Option();
      newOption.text = "---select---";
      newOption.value = "0";
      columnDrop.options[0] = newOption;
       var columns = responseXML.getElementsByTagName("emps")[0];
     //alert(columns);
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lval = columnroot.getElementsByTagName("nme")[0];
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           
          
             newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop+1] = newOption;
       }
   
 }
  function getEmployeeGeneric(obj){
       var bryIdn = obj.options[obj.options.selectedIndex].value;
        
          //alert('@getColumns '+ tblNme);
           var url = "ajaxsrchAction.do?method=loadEmp&bryId="+bryIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesEmpGeneric(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       document.getElementById('partytext').value="";
       document.getElementById('party').value="";
 }
  function parseMessagesEmpGeneric(responseXML){
     var columnDrop = document.getElementById('party');
     //alert(responseXML);
      removeAllOptions(columnDrop);
      
      var newOption = new Option();
      newOption = new Option();
      newOption.text = "---select---";
      newOption.value = "0";
      columnDrop.options[0] = newOption;
       var columns = responseXML.getElementsByTagName("emps")[0];
     //alert(columns);
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lval = columnroot.getElementsByTagName("nme")[0];
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           
          
             newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop+1] = newOption;
       }
   
 }
  function getEmployeeDmd(obj){
       var bryIdn = obj.options[obj.options.selectedIndex].value;
        
          //alert('@getColumns '+ tblNme);
           var url = "ajaxsrchAction.do?method=loadEmpDmd&bryId="+bryIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesEmpDmd(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       document.getElementById('partytext').value="";
       document.getElementById('party').value="";
 }
 
  function parseMessagesEmpDmd(responseXML){
     var columnDrop = document.getElementById('party');
     //alert(responseXML);
      removeAllOptions(columnDrop);
      
      var newOption = new Option();
      newOption = new Option();
      newOption.text = "---select---";
      newOption.value = "0";
      columnDrop.options[0] = newOption;
       var columns = responseXML.getElementsByTagName("emps")[0];
     //alert(columns);
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lval = columnroot.getElementsByTagName("nme")[0];
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           
          
             newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop+1] = newOption;
       }
   
 }
 
 
 function getDropDwonXrt(tblNmeobj) {
           tblNmeobj = document.getElementById('rlnId');
           var rlnIdn = tblNmeobj.options[tblNmeobj.options.selectedIndex].value;
        if(rlnIdn!='0'){
          //alert('@getColumns '+ tblNme);
           var url = "ajaxsrchAction.do?method=loadXrt&rlnId="+rlnIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesXrt(req.responseXML,rlnIdn);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
        }else{
            document.getElementById('xrtId').value="1";
        }
       
   
   }
   
 function parseMessagesXrt(responseXML, rlnIdn) {
    //alert('@parseMessage '+ fld);
      
    
     
       var columns = responseXML.getElementsByTagName("xrtLst")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var cur = columnroot.getElementsByTagName("cur")[0];
           var xrt = columnroot.getElementsByTagName("xrt")[0];
           document.getElementById('xrtId').value=xrt.childNodes[0].nodeValue;
            var curVal = cur.childNodes[0].nodeValue;
       
            if(curVal=='usd'){
             document.getElementById('xrtId').readOnly=true;
            } else{
              document.getElementById('xrtId').readOnly=false;
            }
             
       }
   
       var byrIdn = document.getElementById('party').value;
       getContact(byrIdn);
         getDmd(byrIdn ,rlnIdn);
         getWR(byrIdn ,rlnIdn);
         getHR(byrIdn ,rlnIdn);
         getWL(byrIdn ,rlnIdn);
         getLstMemo(byrIdn ,rlnIdn);
         getLstMemoNG(byrIdn ,rlnIdn);
 }
 
 function getContact(byrIdn){
   
           var url = "ajaxsrchAction.do?method=ContDtl&bryId="+byrIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesContDtl(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
  
 function parseMessagesContDtl(responseXML){
    var columns = responseXML.getElementsByTagName("ContDtls")[0];
     var str="<table class=\"grid1\" width=\"100%\">";
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
           var eml = columnroot.getElementsByTagName("eml")[0];
           var mbl = columnroot.getElementsByTagName("mbl")[0];
           var ofc = columnroot.getElementsByTagName("ofc")[0];
           var emp = columnroot.getElementsByTagName("emp")[0];
           str = str+"<tr><td> <b>Sales person :</b></td><td> "+unescape(emp.childNodes[0].nodeValue)+" </td></tr>";
           str = str+"<tr><td> <b>Email Id :</b></td><td> "+unescape(eml.childNodes[0].nodeValue)+" </td></tr>";
           str = str+"<tr><td> <b>Mobile :</b></td><td> "+mbl.childNodes[0].nodeValue+" </td></tr>";
           str = str+"<tr><td> <b>Office No. :</b></td><td> "+ofc.childNodes[0].nodeValue+" </td></tr>";
  }
   str = str +"</table>"
   document.getElementById('conDtlDiv').innerHTML = str;
 }
 

function removeAllOptions(selectbox) {
    var i;
    for(i=selectbox.options.length-1;i>=0;i--) {
        selectbox.remove(i);
    }
}
/* Table Columns */

/* Conatct Names */ 

function getNames(fldNmeobj, fld) {
           var fldVal = fldNmeobj.value;
          //alert('@getColumns '+ tblNme);
           var url = "ajaxAction.do?getNames="+fldVal ;
             
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseNames(req.responseXML, fld);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       
}
   
 function parseNames(responseXML, fld) {
    //alert('@parseMessage '+ fld);
        var columnDrop = document.getElementById(fld);
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("columns")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("keys")[0];
           var lval = columnroot.getElementsByTagName("values")[0];
           
          
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
       }
 }
 function doCompletionGNR(fld, dspFldId, url, e){
      var val = document.getElementById('srchTyp').value;
      url = url+"&UsrTyp="+val ;
    
      doCompletion(fld, dspFldId, url, e);
 }
  function doCompletionMobileGNR(fld, dspFldId, url){
      var val = document.getElementById('srchTyp').value;
      url = url+"&UsrTyp="+val ;
    
      doCompletionMobile(fld, dspFldId, url);
 }
 
 function doCompletionBOXTyp(fld, dspFldId, url, e){
      var val = document.getElementById('boxTyp').value;
      url = url+"&boxTyp="+val ;
      doCompletion(fld, dspFldId, url, e);
 }
 
  function doCompletion(fld, dspFldId, url, e) {
var val = document.getElementById(fld).value;
var dspFld = document.getElementById(dspFldId);
var unicode=e.keyCode? e.keyCode : e.charCode;
//alert(val + " : " + unicode);
//alert('Calling completion : '+unicode);
if(unicode >=48 && unicode <=90 || unicode ==8 || unicode ==55) {
if (val == "") {
removeAllOptions(dspFld);
dspFld.style.display='none';
} else if(val.length >= 1) {
val=val.replace('&', '%26'); 
url = url + "&param="+val+"&lFld="+fld;
url = url.replace('+', '%2B'); 

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesNme(req.responseXML,fld, dspFldId);
} else if (req.status == 204){
//clearTable();
}
}
};
req.open("GET",url, true);
req.send(null);
moveFocus(dspFldId);
}

}


}

function doCompletionMobile(fld, dspFldId, url) {
var val = document.getElementById(fld).value;
var dspFld = document.getElementById(dspFldId);
var dspFldIdstt=document.getElementById(dspFldId).style.display;
if(dspFldIdstt=='none'){
if (val == "") {
removeAllOptions(dspFld);
dspFld.style.display='none';
} else if(val.length >= 1) {
val=val.replace('&', '%26'); 
url = url + "&param="+val+"&lFld="+fld;
url = url.replace('+', '%2B'); 

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesNme(req.responseXML,fld, dspFldId);
} else if (req.status == 204){
//clearTable();
}
}
};
req.open("GET",url, true);
req.send(null);
moveFocus(dspFldId);
}
}else{
hideObj(dspFldId);
}
}
function doAjaxList(fld, dspFldId, url, e) {
//alert('Calling ajaxList');
var val = document.getElementById(fld).value;
var dspFld = document.getElementById(dspFldId);
var unicode=e.keyCode? e.keyCode : e.charCode;
//alert(val + " : " + unicode);
if(unicode >=48 && unicode <=90 || unicode ==8) {
if (val == "") {
removeAllOptions(dspFld);
dspFld.style.display='none';
} else if(val.length >= 1) {
url = url + "&param="+val;
//alert(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesNme(req.responseXML,fld, dspFldId);
} else if (req.status == 204){
//clearTable();
}
}
};
req.open("GET", url, true);
req.send(null);
moveFocus(dspFldId);
}

}


}

function clearsugBoxDiv(fld,hiddenfld,dspFldId,form){
document.getElementById(fld).value='';
document.getElementById(hiddenfld).value='';
document.getElementById(fld).focus();
hideObj(dspFldId);
if(form=='SRCH'){
getTrmsNME(hiddenfld,'SRCH');
}
}

 function parseMessageLists(responseXML, dspFldId){
      var dspFld = document.getElementById(dspFldId);
      //alert('parseList');
      //alert('@parse : ln='+responseXML.length);      
      removeAllOptions(dspFld);
      //dspFld.style.display = 'block'
      var columns = responseXML.getElementsByTagName("list")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
        var columnroot = columns.childNodes[loop];
           //alert('Adding Values');
        var lkey = columnroot.getElementsByTagName("id")[0];
        var lval = columnroot.getElementsByTagName("nme")[0];
        var newOption = new Option();
        newOption.text = lval.childNodes[0].nodeValue;
        newOption.value = lkey.childNodes[0].nodeValue;
        dspFld.options[loop] = newOption;
      }
      
   }
   
   function parseMessagesNme(responseXML,fld, dspFldId){
  var dspFld = document.getElementById(dspFldId);
//alert('Msg');

removeAllOptions(dspFld);
//dspFld.style.display = 'block'
var columns = responseXML.getElementsByTagName("mnme")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
//alert('Adding Values');
var lkey = columnroot.getElementsByTagName("nmeid")[0];
var lval = columnroot.getElementsByTagName("nme")[0];


var newOption = new Option();
newOption.text = unescape(lval.childNodes[0].nodeValue);
newOption.value = lkey.childNodes[0].nodeValue;
dspFld.options[loop] = newOption;
if(loop == 0)
newOption.selected = true;
}

document.getElementById(fld).focus();

}
    function SaveRole(roleIdn , usrIdn , obj){
     var stt = obj.checked;
   
      var url = "userRoleDetail.do?method=save&roleIdn="+roleIdn+"&usrIdn="+usrIdn+"&stt="+stt;
        
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
  function SaveWEBRole(roleIdn , usrIdn , obj){
     var stt = obj.checked;
   
      var url = "webaccess.do?method=saveRole&roleIdn="+roleIdn+"&usrIdn="+usrIdn+"&stt="+stt;
        
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 function saveMenuRole(menuIdn , itemIdn ,roleIdn, obj){
     
      var stt = obj.checked;
  
      var url = "menuRoleDetail.do?method=save&menuIdn="+menuIdn+"&itemIdn="+itemIdn+"&roleIdn="+roleIdn+"&stt="+stt;
         
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }

function SaveWEBRole(roleIdn , usrIdn , obj){
     var stt = obj.checked;
   
      var url = "webaccess.do?method=saveRole&roleIdn="+roleIdn+"&usrIdn="+usrIdn+"&stt="+stt;
        
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 
 function saveMenuRole(menuIdn , itemIdn ,roleIdn, obj){
     
      var stt = obj.checked;
  
      var url = "menuRoleDetail.do?method=save&menuIdn="+menuIdn+"&itemIdn="+itemIdn+"&roleIdn="+roleIdn+"&stt="+stt;
         
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }

function addProcess(obj , mprcIdn) {
     var stt = obj.checked;
     var deptIdn = document.getElementById('deptIdn').value;
   
      var url = "deptToProcessAction.do?method=save&deptIdn="+deptIdn+"&prcIdn="+mprcIdn+"&stt="+stt ;
         
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}

function setmailid(objId , obj) {
var isChecked = obj.checked ;
var url = "massmail.do?method=loadpg&nmeId="+objId+"&stt="+isChecked;

var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesTtls(req.responseXML)
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);
}
   
   
function  popEmpOnDpt(obj) {
         
           var deptIdn = obj.value;
           var txt = obj.options[obj.options.selectedIndex].text;
          
           var url = "ajaxContactAction.do?typ=DPT&deptIdn="+deptIdn+"&dept="+txt;
             
           var req = initRequest();
           req.onreadystatechange = function() {    
         
               if (req.readyState == 4) {
                   if (req.status == 200) {
                    parseMessagesDEPTEMP(req.responseXML);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       
   }
   
   function verifyName(){
var usr=document.getElementById('usr').value;
var url = "ajaxVerifyContactAction.do?method=verifyName&usr="+usr ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesVerifynam(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesVerifynam(responseXML){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='yes')
{
alert("User Name Is Already Exist Try New.");
document.getElementById('usr').value="";
document.getElementById('usr').focus();
}
}

    
    function RefineChange(fld , fldVal , obj){
  var stt = obj.checked;
  var url = "ajaxsrchAction.do?method=refine&fld="+escape(fld)+"&fldVal="+escape(fldVal)+"&stt="+escape(stt);
  url = url.replace('+', '%2B'); 
  var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     
                   } else if (req.status == 204){
                      
                   }
               }
           };
     req.open("GET", url, true);
     req.send(null);
}



    function parseMessagesDEPTEMP(responseXML){
      var dspFld = document.getElementById("emp");
      removeAllOptions(dspFld);
      var columns = responseXML.getElementsByTagName("mnme")[0];
      for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
            var newOption = new Option();
             newOption.text = lval.childNodes[0].nodeValue;
             newOption.value = lkey.childNodes[0].nodeValue;
             dspFld.options[loop] = newOption;
             if(loop == 0)
              newOption.selected = true;
       }
       
       dspFld.focus();
      
   }
   
   function saveWebMenuRole(menuIdn,roleIdn, obj){

var stt = obj.checked;
var url = "webmenurole.do?method=save&menuIdn="+menuIdn+"&roleIdn="+roleIdn+"&stt="+stt;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function saveBulkWebMenuRole(emp_idn,roleIdn, obj){

var stt = obj.checked;
var url = "bulkrole.do?method=save&emp_idn="+emp_idn+"&roleIdn="+roleIdn+"&stt="+stt;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function checkALLRole(checkId , count,loop,emp_idn){
var checked = document.getElementById('checkAll_'+checkId).checked;
for(var i=0;i<count;i++){
var objId = checkId+'_'+i;
var dis = document.getElementById(objId).disabled ;
if(!dis){
document.getElementById(objId).checked = checked;
if(i==0){
if(checked==true)
stt="A";
else
stt="IA";
var url = "bulkrole.do?method=checkAllBulk&roleName="+checkId+"&emp_idn="+emp_idn+"&stt="+stt;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}
}
}

function checkALLContact(count){
var checked = document.getElementById('checkAll_All').checked;

var url = "massmail.do?method=mailALL&stt="+checked;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
     
           for(var i=0;i<count;i++){
               var fldID = "check_"+i;
               document.getElementById(fldID).checked = checked;
           }
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}





   function UpdateGT(stkIdn , bidIdn , obj){
      var chkVal = obj.value;
       if(stkIdn=='ALL'){
           var count = document.getElementById('count').value;
           for(var i=1;i<count;i++){
               var fldID = "CHK_"+i;
               document.getElementById(fldID).checked = isChecked;
           }
       }
        var url = "ajaxRptAction.do?method=updGT&CHKVAL="+chkVal+"&stkIdn="+stkIdn+"&bidIdn="+bidIdn;
        
       var req = initRequest();
      req.onreadystatechange = function() {
      if (req.readyState == 4) {
      if (req.status == 200) {

       } else if (req.status == 204){
       }}};
       req.open("GET", url, true);
        req.send(null);
}



function  parseMessagesAVGTTL(responseXML , prp){
  
     var msgTag = responseXML.getElementsByTagName("total")[0];
      msg= msgTag.childNodes[0].nodeValue;
     document.getElementById('CAL_'+prp).innerHTML = msg;
     return true;
      
   
}

function checkALLExcelutility(allCk , name,flg,count){
var isChecked = document.getElementById(allCk).checked;
for(var i=0;i<count;i++){
var fldID = name+(i+1);
var isdisable=document.getElementById(fldID).disabled;
if(!isdisable){
document.getElementById(fldID).checked = isChecked;
}
}
}

function checkALLlabSelect( count,checkId, obj){

checkALLLabResult(checkId, obj , count);
var checked = document.getElementById('checkAll_All').checked;
var url = "labSelection.do?method=checkALL&stt="+checked;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

for(var i=0;i<count;i++){
var fldID = "check_"+(i+1);
document.getElementById(fldID).checked = checked;
}
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function setExcelidLabselect(objId,obj,issIdn,stkIdn,cts) {
LabTotalCal(obj , cts , issIdn, stkIdn);
var isChecked = obj.checked ;
var url = "labSelection.do?method=checkSingle&stkIdn="+stkIdn+"&stt="+isChecked;

var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {

}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);
}

function verifyMdl(){
var formatTxt=document.getElementById('formatTxt').value;
var url = "ajaxsrchAction.do?method=verifyMDL&formatTxt="+formatTxt ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesVerifyname(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesVerifyname(responseXML){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='yes')
{
alert("Already Exist Memo Excel Format Name. Try New");
document.getElementById('formatTxt').value="";
document.getElementById('formatTxt').focus();
}
}

function assortVerificationExlAll(){
var count=document.getElementById('count').value;
var checked = document.getElementById('checkAll').checked;
var url = "ajaxAssortAction.do?method=updateGTExcel&flg=ALL&stt="+checked;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

for(var i=0;i<count;i++){
var fldID = "check_"+i;
document.getElementById(fldID).checked = checked;
}
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function assortVerificationExlAllDisable(){
var count=document.getElementById('count').value;
var checked = document.getElementById('checkAll').checked;
var url = "ajaxAssortAction.do?method=updateGTExcel&flg=ALL&stt="+checked;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function assortVerificationExl(objId , obj) {
var isChecked = obj.checked ;
var url = "ajaxAssortAction.do?method=updateGTExcel&stt="+isChecked+"&stkIdn="+objId;

var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);
}



function CalculateAvgTotal(prp ,stt,lprp,typ){
var calStt= document.getElementById('calStt').value;
if(calStt=='ALL'){
stt='';
}
else if(calStt!=''){
stt=calStt;
}

var url = "ajaxRptAction.do?method=CalAvgTTl&prp="+prp+"&lprp="+lprp+"&typ="+typ+"&stt="+stt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesAVGTTL(req.responseXML,prp);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}

function saveSizeClr(mixClr,mixSize, obj){
var stt = obj.checked;
var url = "mixSizeClr.do?method=save&mixClr="+mixClr+"&mixSize="+mixSize+"&stt="+stt;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function  doScan(fldScan, fldCtr , dvcId,typ) {
document.getElementById('load').style.display='';
 var dvc = document.getElementById('dvcLst').value;
  var url = "scanProcess.do?method=start&dvc="+dvc+"&typ="+typ;
  var req = initRequest();
    req.onreadystatechange = function() {    
      if (req.readyState == 4) {
        if (req.status == 200) {
          parseScannedPackets(req.responseXML , fldScan, fldCtr,typ);
        } else if (req.status == 204){
        document.getElementById('load').style.display='none';
          }
      }
    };
          
   req.open("GET", url, true);
   req.send(null);
       
}
   
function parseScannedPackets(responseXML, fldScan, fldCtr, typ) {
  var list = responseXML.getElementsByTagName("list")[0];   
  //alert(' List Recd '+list.childNodes.length);
  var scannedList = "";
  for (var loop = 0; loop < list.childNodes.length; loop++) {
    var packets = list.childNodes[loop];
    var id = packets.getElementsByTagName("id")[0];
    scannedList = scannedList + id.childNodes[0].nodeValue + "\n";
  }
  document.getElementById(fldCtr).innerHTML = list.childNodes.length;
  document.getElementById(fldScan).value = scannedList ;
  document.getElementById('load').style.display='none';
  
  if(typ=='AUTOSCAN'){
   document.getElementById('rfScan').style.display='none';
   document.getElementById('autorfScan').style.display='none';
  }else{
   document.getElementById('rfScan').style.display='';
   document.getElementById('autorfScan').style.display='';
  }
      
  
}



function saveSizeClrText(mixClr,mixSize,dpVal,objId,lbIdn,rte, obj){
var val= document.getElementById(objId).value;
var sh = document.getElementById('SHAPE_1').value;
var memoId = document.getElementById('MEMO_1').value;
var lockId = document.getElementById('lockId').value;
var mprcIdn = document.getElementById('mprcIdn').value;
var upr = document.getElementById('PRI_'+objId).value;
var issueId = document.getElementById('issueId').value;
if(rte==''){
rte = upr;
}
var isStt = document.getElementById('isstt').value;
var url = "mixRtnMatrixAction.do?method=saveVal&issueId="+issueId+"&mixClr="+mixClr+"&mixSize="+mixSize+"&cts="+val+"&memo="+memoId+"&sh="+sh+"&lockId="+lockId+"&dp="+dpVal+"&cmp="+rte+"&upr="+upr+"&isStt="+isStt+"&prc="+mprcIdn+"&prp=CRTWT";

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesGtPkt(req.responseXML , mixSize+"_"+lbIdn)

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function saveSizeClrPriText(mixClr,mixSize,dpVal,objId,lbIdn,rte, obj){
var val= document.getElementById(objId).value;
var sh = document.getElementById('SHAPE_1').value;
var memoId = document.getElementById('MEMO_1').value;
var lockId = document.getElementById('lockId').value;
var isStt = document.getElementById('isstt').value;
var mprcIdn = document.getElementById('mprcIdn').value;
var issueId = document.getElementById('issueId').value;
var upr = obj.value;
var url = "mixRtnMatrixAction.do?method=saveVal&issueId="+issueId+"&mixClr="+mixClr+"&mixSize="+mixSize+"&cts="+val+"&memo="+memoId+"&sh="+sh+"&lockId="+lockId+"&dp="+dpVal+"&cmp="+rte+"&upr="+upr+"&isStt="+isStt+"&prc="+mprcIdn+"&prp=RTE";

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesGtPkt(req.responseXML , mixSize+"_"+lbIdn)

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagesGtPkt(responseXML , lblId){
   var cts = responseXML.getElementsByTagName("cts")[0];
   var ctsVal = cts.childNodes[0].nodeValue;
    document.getElementById(lblId).innerHTML =ctsVal;
    
}





function MixUnlock(){


var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
var field_type = frm_elements[i].type.toLowerCase();

if(field_type=='text') {
var fieldId = frm_elements[i].id;
var val =  frm_elements[i].value;
if(fieldId.indexOf("_CTS_")!=1){
document.getElementById(fieldId).disabled=false;
if(fieldId.indexOf('PRI')==-1){
if(val!=''){
var str = fieldId.split('_');
var mixClr = str[0];
var mixSize = str[2];
var dp = str[4];
var dpVal ="0";
if(dp.indexOf('>')!=-1){
    dpVal = "65";
}
saveSizeClrText(mixClr,mixSize,dpVal,fieldId,'DP_'+dp,'', frm_elements[i]);
}}
}
}}
document.getElementById('unlock').style.display='none';
document.getElementById('verify').style.display='';

}


function MixVerify(){
 document.getElementById("msg").innerHTML ="";
document.getElementById('process').display='';
var sh = document.getElementById('SHAPE_1').value;
var memoId = document.getElementById('MEMO_1').value;
var isStt = document.getElementById('isstt').value;
var url = "mixRtnMatrixAction.do?method=verify&memo="+memoId+"&sh="+sh+"&isStt="+isStt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMIXVERIFY(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagesMIXVERIFY(responseXML){
var list = responseXML.getElementsByTagName("msgs")[0];   
  
  for (var loop = 0; loop < list.childNodes.length; loop++) {
    var msg = list.childNodes[loop];
    var flg= msg.getElementsByTagName("flg")[0];
    var rtnMsg = msg.getElementsByTagName("rtnmsg")[0];
    document.getElementById("msg").innerHTML = rtnMsg.childNodes[0].nodeValue;
  document.getElementById('process').display='none';
  if(flg.childNodes[0].nodeValue=='T'){
     document.getElementById('Save').style.display='';
     document.getElementById('verify').style.display='none';
      document.getElementById('reset').style.display='';
      var frm_elements = document.forms[1].elements; 
    for(i=0; i<frm_elements.length; i++) {
    var field_type = frm_elements[i].type.toLowerCase();
    
    if(field_type=='text') {
     var fieldId = frm_elements[i].id;
     if(fieldId.indexOf("_CTS_")!=1){
      document.getElementById(fieldId).disabled=true;
     }
    }}
      
  }else{
      document.getElementById('Save').style.display='none';
      document.getElementById('verify').style.display='';
  }
  }
}

function MixFnlVerify(){
 document.getElementById("msg").innerHTML ="";
document.getElementById('process').display='';
var sh = document.getElementById('SHAPE_1').value;
var isStt = document.getElementById('isstt').value;
var url = "mixFnlRtnAction.do?method=verify&sh="+sh+"&isStt="+isStt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesFnlMIXVERIFY(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagesFnlMIXVERIFY(responseXML){
var list = responseXML.getElementsByTagName("msgs")[0];   
  
  for (var loop = 0; loop < list.childNodes.length; loop++) {
    var msg = list.childNodes[loop];
    var flg= msg.getElementsByTagName("flg")[0];
    var rtnMsg = msg.getElementsByTagName("rtnmsg")[0];
    document.getElementById("msg").innerHTML = rtnMsg.childNodes[0].nodeValue;
  document.getElementById('process').display='none';
  if(flg.childNodes[0].nodeValue=='T'){
     document.getElementById('Save').style.display='';
     document.getElementById('verify').style.display='none';
      document.getElementById('reset').style.display='';
      var frm_elements = document.forms[1].elements; 
    for(i=0; i<frm_elements.length; i++) {
    var field_type = frm_elements[i].type.toLowerCase();
    
    if(field_type=='text') {
     var fieldId = frm_elements[i].id;
    if(fieldId.indexOf("_CTS_")!=1){
       document.getElementById(fieldId).disabled=false;
       if(fieldId.indexOf('PRI')==-1){
            if(val!=''){
            var str = fieldId.split('_');
            var mixClr = str[0];
            var mixSize = str[2];
             var dp = str[4];
              var dpVal ="0";
             if(dp.indexOf('>')!=-1){
             dpVal = "65";
         }
          saveSizeClrTextFnl(mixClr,mixSize,dpVal,fieldId,'DP_'+dp,'', frm_elements[i]);
         }}
        }
    }}
      
  }else{
      document.getElementById('Save').style.display='none';
      document.getElementById('verify').style.display='';
  }
  }
}



function  MixFnlUnlock(){

 
   var frm_elements = document.forms[1].elements; 
    for(i=0; i<frm_elements.length; i++) {
    var field_type = frm_elements[i].type.toLowerCase();
    
    if(field_type=='text') {
     var fieldId = frm_elements[i].id;
     var val =  frm_elements[i].value;
     if(fieldId.indexOf("_CTS_")!=1){
      document.getElementById(fieldId).disabled=false;
      if(fieldId.indexOf('PRI')==-1){
if(val!=''){
var str = fieldId.split('_');
var mixClr = str[0];
var mixSize = str[2];
var dp = str[4];
var dpVal ="0";
if(dp.indexOf('>')!=-1){
    dpVal = "65";
}
saveSizeClrTextFnl(mixClr,mixSize,dpVal,fieldId,'DP_'+dp,'', frm_elements[i]);
}}
     }
    }}
    document.getElementById('unlock').style.display='none';
    document.getElementById('verify').style.display='';
    }


function saveSizeClrTextFnl(mixClr,mixSize,dpVal,objId,lbIdn,rte, obj){
var val= document.getElementById(objId).value;
var sh = document.getElementById('SHAPE_1').value;
var isStt = document.getElementById('isstt').value;
var lockId = document.getElementById('lockId').value;
var upr = document.getElementById('PRI_'+objId).value;
var mprcIdn = document.getElementById('mprcIdn').value;
var issueId = document.getElementById('issueId').value;
var url = "mixFnlRtnAction.do?method=saveVal&issueId="+issueId+"&mixClr="+mixClr+"&mixSize="+mixSize+"&cts="+val+"&sh="+sh+"&lockId="+lockId+"&dp="+dpVal+"&cmp="+rte+"&upr="+upr+"&isStt="+isStt+"&prc="+mprcIdn+"&prp=CRTWT";
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesGtPkt(req.responseXML , mixSize+"_"+lbIdn)

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}


function saveSizeClrPriTextFnl(mixClr,mixSize,dpVal,objId,lbIdn,rte, obj){
var val= document.getElementById(objId).value;
var sh = document.getElementById('SHAPE_1').value;
var upr = obj.value;
var lockId = document.getElementById('lockId').value;
var isStt = document.getElementById('isstt').value;
var mprcIdn = document.getElementById('mprcIdn').value;
var issueId = document.getElementById('issueId').value;
var url = "mixFnlRtnAction.do?method=saveVal&issueId="+issueId+"&mixClr="+mixClr+"&mixSize="+mixSize+"&cts="+val+"&sh="+sh+"&lockId="+lockId+"&dp="+dpVal+"&cmp="+rte+"&upr="+upr+"&isStt="+isStt+"&prc="+mprcIdn+"&prp=RTE";

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesGtPkt(req.responseXML , mixSize+"_"+lbIdn)

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagesGtPkt(responseXML , lblId){
   var cts = responseXML.getElementsByTagName("cts")[0];
   var ctsVal = cts.childNodes[0].nodeValue;
    document.getElementById(lblId).innerHTML =ctsVal;
    
}
function UnabledText(){
      var frm_elements = document.forms[1].elements; 
    for(i=0; i<frm_elements.length; i++) {
    var field_type = frm_elements[i].type.toLowerCase();
    if(field_type=='text') {
     var fieldId = frm_elements[i].id;
     if(fieldId.indexOf("_CTS_")!=1){
      document.getElementById(fieldId).disabled=false;
     }
    }}
     document.getElementById('Save').style.display='none';
     document.getElementById('verify').style.display='';
      document.getElementById('reset').style.display='none';
      document.getElementById("msg").innerHTML ="";
}

function compExlAll(){
var count=document.getElementById('count').value;
var checked = document.getElementById('checkExlAll').checked;
var url = "ajaxAssortAction.do?method=updateGTExcel&flg=ALL&stt="+checked;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

for(var i=0;i<count;i++){
var fldID = "check_"+i;
document.getElementById(fldID).checked = checked;
}
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function compExl(objId , obj) {
var isChecked = obj.checked ;
var url = "ajaxAssortAction.do?method=updateGTExcel&stt="+isChecked+"&stkIdn="+objId;

var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);
}


function getMixIdn(i,txt,event) {
var objId = "nmeID"+txt+"_"+i;
var memoid = "MEMOF_"+i;
var val= document.getElementById(objId).value;
var stt= document.getElementById('status').value;

if(stt=='0'){
alert("please specify stone Status");
}else{
var flg = document.getElementById('flg').value;
var url = "mixtomix.do?method=getMixIdn&rnk="+val+"&stt="+stt;
if(flg=='M'){
var memo = document.getElementById(memoid).value;
url +="&memo="+memo ;
}
if(memo=='' && flg=='M'){
alert("please specify memo");
}else if(val!='' ){
document.getElementById('loading').innerHTML = "<img src=\"../images/processing1.gif\" align=\"middle\" border=\"0\" />";
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMixIdn(req.responseXML , txt ,i)

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
}}

function parseMessagesMixIdn(responseXML,txt ,i){
var trfId = "TRF"+txt+"_"+i;

var idnId ="IDN"+txt+"_"+i;
var ctsId ="CTS"+txt+"_"+i
var columns = responseXML.getElementsByTagName("mnme")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
//alert('Adding Values');
var lkey = columnroot.getElementsByTagName("idn")[0];
var lval = columnroot.getElementsByTagName("cts")[0];
// alert(lkey.childNodes[0].nodeValue);
// alert(lval.childNodes[0].nodeValue);
document.getElementById(idnId).value=lkey.childNodes[0].nodeValue;
document.getElementById(ctsId).value=lval.childNodes[0].nodeValue;
}
document.getElementById('loading').innerHTML='';
document.getElementById(ctsId).focus();
document.getElementById(trfId).focus();
if(txt=='T'){
getMixTrfToIdnRte(i);
}
}
function getMixTrfToIdnRte(i) {
var mstkIdn= document.getElementById("IDNT_"+i).value;
var url = "mixtomix.do?method=getMixIdnRTE&mstkIdn="+mstkIdn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMixTrfToIdnRte(req.responseXML,i)

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagesMixTrfToIdnRte(responseXML,i){
var columns = responseXML.getElementsByTagName("mnme")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = columnroot.getElementsByTagName("rte")[0];
document.getElementById("RTET_"+i).value=lval.childNodes[0].nodeValue;
}
}

function updateGtCTSQt(stkidn , idfld,colmn,vlu,mixrtelmt){
 var val= parseFloat(document.getElementById(idfld).value);
    vlu =  parseFloat(vlu);
 if(colmn=='QUOT'){
   var percent = ((vlu * mixrtelmt)/100);
      var diff = vlu - val;
    var minPrc = vlu - percent;
    
    if(diff > percent){
        alert("Minmium expected price is "+minPrc); 
        document.getElementById(idfld).value= "";
        document.getElementById(idfld).value.focus();
          setTimeout(function(){document.getElementById(idfld).value.focus();},1); 
           return false;
    }else{
           ajaxCall(val,stkidn,colmn);
    }
 }else if(val>vlu){
      alert("Please Verify Value. It can't more then "+vlu);
     document.getElementById(idfld).value = vlu;
 }else{
 if(val!=''){
       ajaxCall(val,stkidn,colmn);
  }}
 }
 
 function ajaxCall(val,stkidn,colmn){
      var url = "ajaxsrchAction.do?method=updateGtCTSQt&stkIdn="+stkidn+"&val="+val+"&colmn="+colmn ;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     var stt = document.getElementById('STT_'+stkidn).value;
                    var checkedBox =document.getElementById('cb_memo_'+stt+"_"+stkidn);
                    var VIEW = document.getElementById('VIEW').value;
                    var isChecked = checkedBox.checked;
                 
                       if(isChecked){
                     srchChkSel(checkedBox,stkidn,VIEW);
                        }
                   } else if (req.status == 204){
                   
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
function dfgetpcs(stkidn){
var val= parseFloat(document.getElementById('cts_'+stkidn).value);
var url = "ajaxsrchAction.do?method=dfgetpcs&stkIdn="+stkidn+"&cts="+val;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesdfgetpcs(req.responseXML,stkidn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesdfgetpcs(responseXML,stkidn){
var qtys = responseXML.getElementsByTagName("qtys")[0];
var qty = qtys.childNodes[0];
var qtyval = (qty.getElementsByTagName('qtyval')[0]).childNodes[0].nodeValue;
document.getElementById('qty_'+stkidn).value=qtyval;
}
  function getPurIds(obj){
var purIdn = obj.options[obj.options.selectedIndex].value;
var url = "purchasePriceAction.do?method=loadpurId&purId="+purIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

parseMessagesPURID(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}

function parseMessagesPURID(responseXML){
var columnDrop = document.getElementById('purIdn');
//alert(responseXML);
removeAllOptions(columnDrop);

var newOption = new Option();
newOption = new Option();
newOption.text = "---select---";
newOption.value = "0";
columnDrop.options[0] = newOption;
var columns = responseXML.getElementsByTagName("venders")[0];
//alert(columns);
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var lval = columnroot.getElementsByTagName("nme")[0];
var lkey = columnroot.getElementsByTagName("nmeid")[0];


newOption = new Option();
newOption.text = unescape(lval.childNodes[0].nodeValue);
newOption.value = lkey.childNodes[0].nodeValue;
columnDrop.options[loop+1] = newOption;
}

}

function loyaltyDis(){
var isLoyalty=false;
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio') {
var fieldId = frm_elements[i].id;
if(fieldId!=''){
var isCheck = document.getElementById(fieldId).checked;
if(isCheck && fieldId.indexOf('SL') != -1){
isLoyalty=true;
break;
}}
}
}
if(isLoyalty){
var vlu = document.getElementById('vlu').innerHTML;
var nmeIdn = document.getElementById('nmeIdn').value;
var url = "ajaxsrchAction.do?method=loyaltyDis&nameIdn="+nmeIdn+"&vlu="+vlu ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesloyaltyDis(req.responseXML);
} else if (req.status == 204){
}}};
req.open("GET", url, true);
req.send(null);
}else{
alert('Please Check Sale Radio Button to get Loyalty Discount');
}
}
function parseMessagesloyaltyDis(responseXML){
var loyaltys = responseXML.getElementsByTagName("loyaltys")[0];
var loyalty = loyaltys.childNodes[0];
var dis = (loyalty.getElementsByTagName('loyaltydis')[0]).childNodes[0].nodeValue;
document.getElementById('loyaltyHdr').style.display='';
document.getElementById('loyaltyval').style.display='';
document.getElementById('loyalty').innerHTML=dis;
}

function getPurTyp(obj){
var VenderIdn = obj.options[obj.options.selectedIndex].value;
var url = "purPrice.do?method=loadtyp&VenderIdn="+VenderIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

parseMessagesPurTyp(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}

function parseMessagesPurTyp(responseXML){
var columnDrop = document.getElementById('typ');
//alert(responseXML);
removeAllOptions(columnDrop);

var newOption = new Option();
newOption = new Option();
newOption.text = "---select---";
newOption.value = "0";
columnDrop.options[0] = newOption;
var columns = responseXML.getElementsByTagName("types")[0];
//alert(columns);
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var lval = columnroot.getElementsByTagName("nme")[0];
var lkey = columnroot.getElementsByTagName("nmeid")[0];


newOption = new Option();
newOption.text = lval.childNodes[0].nodeValue;
newOption.value = lkey.childNodes[0].nodeValue;
columnDrop.options[loop+1] = newOption;
}

}

function getPurIdsVnd(){
var VenderIdn = document.getElementById('venId').value;
var url = "purPrice.do?method=loadtyppurId&typ=ON&VenderIdn="+VenderIdn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

parseMessagesPURIDTYP(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}

function getPurIdsTyp(obj){
var typ = obj.options[obj.options.selectedIndex].value;
var VenderIdn = document.getElementById('venId').value;
var url = "purPrice.do?method=loadtyppurId&typ="+typ+"&VenderIdn="+VenderIdn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

parseMessagesPURIDTYP(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}

function parseMessagesPURIDTYP(responseXML){
var columnDrop = document.getElementById('purIdn');
//alert(responseXML);
removeAllOptions(columnDrop);

var newOption = new Option();
newOption = new Option();
newOption.text = "---select---";
newOption.value = "0";
columnDrop.options[0] = newOption;
var columns = responseXML.getElementsByTagName("venders")[0];
//alert(columns);
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var lval = columnroot.getElementsByTagName("nme")[0];
var lkey = columnroot.getElementsByTagName("nmeid")[0];


newOption = new Option();
newOption.text = lval.childNodes[0].nodeValue;
newOption.value = lkey.childNodes[0].nodeValue;
columnDrop.options[loop+1] = newOption;
}
}

function savemlotDept(mlotidn,srtDept,accAvgID,coulmn){
var val = document.getElementById(accAvgID).value;
var url = "orclReportAction.do?method=savemlotDept&mlotidn="+mlotidn+"&srtDept="+srtDept+"&val="+val+"&coulmn="+coulmn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}

function ketanavg(ketanID){
var ttlfaavg=document.getElementById('TTLFA').value;
var ttlketanavg=document.getElementById(ketanID).value;
if(ttlfaavg!='' && ttlfaavg!='0'){
var per=myRound(ttlketanavg*100/ttlfaavg,2);
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='hidden'){
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('DEPTFA_')!=-1){
var deptavg=document.getElementById(fieldId).value;
if(deptavg!='' && deptavg!='0'){
var perasdept=myRound(deptavg*per/100,2);
var split=fieldId.split('_');
var deptsrt = parseInt(split[1]);
document.getElementById(ketanID+'_'+deptsrt).value=perasdept;
document.getElementById(ketanID+'_'+deptsrt).focus();
}
}
}
}    
}
}
function displayMemoFD(obj){
    var stt = obj.value;
    if(stt!='0'){
    var url = "mixtomix.do?method=IsMemo&stt="+stt;
    var req = initRequest();
     req.onreadystatechange = function() {
     if (req.readyState == 4) {
      if (req.status == 200) {
      parseMessagesMemoFD(req.responseXML);
      } else if (req.status == 204){
     }
    }
    };
   req.open("GET", url, true);
   req.send(null);
    }
}

function parseMessagesMemoFD(responseXML){
    var flg = responseXML.getElementsByTagName("flg")[0];
   var flgVal = flg.childNodes[0].nodeValue;
    document.getElementById('flg').value =flgVal;
    var isDisabled = true;
    if(flgVal=='M')
     isDisabled = false;
        for(var i=1 ; i <=5 ;i++){
            var txtId = "MEMOF_"+i;
           document.getElementById(txtId).disabled = isDisabled;
        }
}

function verifyVnm(obj,objpkt,loop){
var vnm=document.getElementById(obj).value;
var url = "mixToSingle.do?method=verifyVnm&vnm="+vnm ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesVerifyVnm(req.responseXML,obj,objpkt,loop);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesVerifyVnm(responseXML,obj,objpkt,loop){
var details = responseXML.getElementsByTagName("details")[0];
var detail = details.childNodes[0];
var exists = (detail.getElementsByTagName('exists')[0]).childNodes[0].nodeValue;
var pktgen = (detail.getElementsByTagName('pktgen')[0]).childNodes[0].nodeValue;
var existscts = (detail.getElementsByTagName('existscts')[0]).childNodes[0].nodeValue;
var pktidn = (detail.getElementsByTagName('pktidn')[0]).childNodes[0].nodeValue;
var stt = (detail.getElementsByTagName('stt')[0]).childNodes[0].nodeValue;
if(pktgen=='Y' && exists=='Y'){
alert("This Packet Id is Not Allowed");
document.getElementById(obj).value="";
document.getElementById("CRTWT_"+loop).value="";
document.getElementById(objpkt).value="Y";
document.getElementById("PKTIDN_"+loop).value="0";
document.getElementById(obj).focus();
}else{
document.getElementById("CRTWT_"+loop).value=existscts;
document.getElementById(objpkt).value=pktgen;
document.getElementById("PKTIDN_"+loop).value=pktidn;
document.getElementById("STT_"+loop).value=stt;
}
}

function saveUserRight(pgidn,pgitmidn,usrid, obj){
var stt = obj.checked;
var url = "ajaxdashAction.do?method=save&pgidn="+pgidn+"&pgitmidn="+pgitmidn+"&usrid="+usrid+"&stt="+stt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function saveUserVisibility(loadUrl){
var idn = document.getElementById('visibility').value;
if(idn!='' && idn!=0){
var url = "ajaxdashAction.do?method=saveVisibility&idn="+idn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseallow(req.responseXML,loadUrl);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
}

function saveUserRightallow(pgitmidn,stt,loadUrl){
var url = "ajaxdashAction.do?method=allowRight&pgitmidn="+pgitmidn+"&stt="+stt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseallow(req.responseXML,loadUrl);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseallow(xml,loadUrl) {
var reqUrl = document.getElementById('reqUrl').value;
window.open(reqUrl+loadUrl,'_self');
}

function chargesmanual(typ,loop){
var vlu = document.getElementById('vlu').innerHTML;
var olddis = document.getElementById(typ+"_dis").innerHTML;
var charge = document.getElementById(typ).value;
var nmeIdn = document.getElementById('nmeIdn');
if(nmeIdn!=null)
nmeIdn= nmeIdn.value;
else
nmeIdn=0;
var net_dis = document.getElementById('net_dis').innerHTML;
if(olddis!='' && parseFloat(charge) >= 0){
    vlu=parseFloat(net_dis)-parseFloat(olddis);
    document.getElementById('net_dis').innerHTML=vlu;
}
if(olddis!='' && parseFloat(charge) < 0){
    vlu=parseFloat(net_dis)+parseFloat(olddis);
    document.getElementById('net_dis').innerHTML=vlu;
}

var url = "ajaxsrchAction.do?method=charges&nmeIdn="+nmeIdn+"&vlu="+vlu+"&charge="+charge+"&loop="+loop;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescharges(req.responseXML,typ,charge);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}

function chargesmanualSaleByBilling(typ,loop){
var vlu = document.getElementById('vlu').innerHTML;
var olddis = document.getElementById(typ+"_dis").innerHTML;
var charge = document.getElementById(typ).value;
var nmeIdn = document.getElementById('invByrIdn').value;
var net_dis = document.getElementById('net_dis').innerHTML;
if(olddis!='' && parseFloat(charge) >= 0){
    vlu=parseFloat(net_dis)-parseFloat(olddis);
    document.getElementById('net_dis').innerHTML=vlu;
}
if(olddis!='' && parseFloat(charge) < 0){
    vlu=parseFloat(net_dis)+parseFloat(olddis);
    document.getElementById('net_dis').innerHTML=vlu;
}

var url = "ajaxsrchAction.do?method=charges&nmeIdn="+nmeIdn+"&vlu="+vlu+"&charge="+charge+"&loop="+loop;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescharges(req.responseXML,typ,charge);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescharges(responseXML,typ,charge){
var vlu = document.getElementById('net_dis').innerHTML;
var loyaltys = responseXML.getElementsByTagName("charges")[0];
var loyalty = loyaltys.childNodes[0];
var dis = (loyalty.getElementsByTagName('chargedis')[0]).childNodes[0].nodeValue;
document.getElementById(typ+'_dis').innerHTML=dis;
if(typ=='LOY')
document.getElementById(typ+'_save').value=dis;
else
document.getElementById(typ+'_save').value=charge;
vlu=parseFloat(vlu)+parseFloat(dis)
document.getElementById('vluamt').value=vlu;
document.getElementById('net_dis').innerHTML=vlu;
}


function chargesSH(typ,loop){
var vlu = document.getElementById('vlu').innerHTML;
var charge = document.getElementById(typ).value;
var nmeIdn = document.getElementById('nmeIdn').value;
var memoIdn = document.getElementById('noofmemoid').value;
var url = "ajaxsrchAction.do?method=chargesSH&nmeIdn="+nmeIdn+"&vlu="+vlu+"&charge="+charge+"&loop="+loop+"&memoIdn="+escape(memoIdn);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessageschargesSH(req.responseXML,typ,charge);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessageschargesSH(responseXML,typ,charge){
document.getElementById(typ+'_save').value=charge;

var columns = responseXML.getElementsByTagName("prices")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var avgDis = (columnroot.getElementsByTagName("avgDis")[0]).childNodes[0].nodeValue;
var avgRte = (columnroot.getElementsByTagName("avgRte")[0]).childNodes[0].nodeValue;
var Qty = (columnroot.getElementsByTagName("Qty")[0]).childNodes[0].nodeValue;
var Cts = (columnroot.getElementsByTagName("Cts")[0]).childNodes[0].nodeValue;
var Vlu = (columnroot.getElementsByTagName("Vlu")[0]).childNodes[0].nodeValue;

document.getElementById('qty').innerHTML = Qty;
document.getElementById('cts').innerHTML = Cts;
document.getElementById('vlu').innerHTML = Vlu;
document.getElementById('avgdis').innerHTML = avgDis;
document.getElementById('avgprc').innerHTML = avgRte;
document.getElementById('net_dis').innerHTML = Vlu;
document.getElementById('vluamt').value = Vlu;
}
}

function chargesmanualsrch(typ,loop){
document.getElementById('loading').innerHTML = "<img src=\"../images/processing1.gif\" align=\"middle\" border=\"0\" />";
var vlu = document.getElementById('selectvalues').innerHTML;
var charge = document.getElementById(typ).value;
var nmeIdn = document.getElementById('nmeIdn').value;
var url = "ajaxsrchAction.do?method=charges&nmeIdn="+nmeIdn+"&vlu="+vlu+"&charge="+charge+"&loop="+loop;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessageschargessrch(req.responseXML,typ,charge);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessageschargessrch(responseXML,typ,charge){
var vlu = document.getElementById('net_dis').innerHTML;
var loyaltys = responseXML.getElementsByTagName("charges")[0];
var loyalty = loyaltys.childNodes[0];
var dis = (loyalty.getElementsByTagName('chargedis')[0]).childNodes[0].nodeValue;
document.getElementById(typ+'_dis').innerHTML=dis;
if(typ=='LOY')
document.getElementById(typ+'_save').value=dis;
else
document.getElementById(typ+'_save').value=charge;
vlu=parseFloat(vlu)+parseFloat(dis)
document.getElementById('vluamt').value=vlu;
document.getElementById('net_dis').innerHTML=vlu;
document.getElementById('loading').innerHTML='';
}
function chargesmanualXlDelivery(typ,loop){
var sttAry = new Array();
sttAry[0] = "DWP";
sttAry[1] = "DLV";
for(var i=0;i<sttAry.length;i++){
var strVluId ="";
var stt = sttAry[i];
if(stt=='DWP'){ 
strVluId="IS_net_dis";
}else if(stt=='DLV'){
strVluId="DLV_net_dis";
}
callchargesmanualXlDelivery(strVluId,typ,loop,stt);
}
}
function callchargesmanualXlDelivery(strVluId,typ,loop,stt){
  var vlu = document.getElementById(strVluId).innerHTML;
var charge = document.getElementById(typ).value;
var nmeIdn = document.getElementById('nmeIdn').value;
var url = "ajaxsrchAction.do?method=charges&nmeIdn="+nmeIdn+"&vlu="+vlu+"&charge="+charge+"&loop="+loop;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessageschargesXlDelivery(req.responseXML,typ,charge,stt);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);  
}
function parseMessageschargesXlDelivery(responseXML,typ,charge,stt){
var strVluId ="";
var strnetDisId ="";
if(stt=='DWP'){ 
strVluId="IS_vluamt";
strnetDisId="IS_net_dis";
}else if(stt=='DLV'){
strVluId="DLV_vluamt"
strnetDisId="DLV_net_dis";
}
var vlu = document.getElementById(strnetDisId).innerHTML;
var loyaltys = responseXML.getElementsByTagName("charges")[0];
var loyalty = loyaltys.childNodes[0];
var dis = (loyalty.getElementsByTagName('chargedis')[0]).childNodes[0].nodeValue;
document.getElementById(typ+'_dis').innerHTML=dis;
if(typ=='LOY'){
document.getElementById(typ+'_save').value=dis;
}else{
document.getElementById(typ+'_save').value=charge;
}
vlu=parseFloat(vlu)+parseFloat(dis)
document.getElementById(strVluId).value=vlu;
document.getElementById(strnetDisId).innerHTML=vlu;
}
function verifyvnm(obj){
var vnm=document.getElementById(obj).value;
var url = "ajaxsrchAction.do?method=verifyvnm&vnm="+vnm ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesVerifyname(req.responseXML,obj);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesVerifyname(responseXML,obj){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='yes')
{
alert("Already Exist Try New.");
document.getElementById(obj).value="";
document.getElementById(obj).focus();
}
}


function savepageUserRight(pgitmidn,usrid, obj){
var pgidn = document.getElementById('pg').value;
var stt = obj.checked;
var url = "usrright.do?method=savepg&pgidn="+pgidn+"&pgitmidn="+pgitmidn+"&usrid="+usrid+"&stt="+stt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function savepageUserRightAll(pgitmidn, obj){
var pgidn = document.getElementById('pg').value;
var stt = obj.checked;
var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='checkbox'){
       var fieldId =frm_elements[i].id;
       if (fieldId.indexOf('_'+pgitmidn) != - 1) {
	   document.getElementById(fieldId).checked = stt;
       }
  } 
  }
var url = "usrright.do?method=savepgall&pgidn="+pgidn+"&pgitmidn="+pgitmidn+"&stt="+stt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}


function contactEntries(tbl,fld,grp,flg){
var nmestatus=document.getElementById('nmestatus').value;
var fldval='';
var dteval1='';
var dteval2='';
var execnn='N';
if(flg!='DR' && flg!='NR'){
fldval=document.getElementById(fld).value;
}else{
dteval1=document.getElementById(fld+'_1').value;
dteval2=document.getElementById(fld+'_2').value;
}
if(flg=='DT' && fldval!=''){
var split=fldval.split('-');
fldval=split[2]+split[1]+split[0];
}
if(flg=='DR'){
if(dteval1!=''){
split=dteval1.split('-');
dteval1=split[2]+split[1]+split[0];
}
if(dteval2!=''){
split=dteval2.split('-');
dteval2=split[2]+split[1]+split[0];
}

if(dteval1=='' && dteval2==''){
fldval='';
}else{
if(dteval1==''){
dteval1=dteval2;
}
if(dteval2==''){
dteval2=dteval1;
}
fldval=dteval1+' AND '+dteval2;
}}
if(flg=='NR'){
fldval=dteval1+' AND '+dteval2;
}

var srchid=document.getElementById('srchid').value;
if(flg=='M')
fldval=getValueM(fld);
if(flg=='T'){
execnn=document.getElementById('HIDD_'+fld).value;  
if(fldval!=''){
document.getElementById('HIDD_'+fld).value='Y';
}else{
document.getElementById('HIDD_'+fld).value='N';
execnn='N';
}
}
document.getElementById('loading').innerHTML = "<img src=\"../images/processing1.gif\" align=\"middle\" border=\"0\" />";
var url = "advcontact.do?method=contactEntries&srchid="+srchid+"&tbl="+escape(tbl)+"&tblfld="+escape(fld)+"&val="+escape(fldval)+"&srchgrp="+escape(grp)+"&flg="+escape(flg)+"&execnn="+escape(execnn)+"&nmestatus="+nmestatus ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescontactEntries(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}


function parseMessagescontactEntries(responseXML){
var consrchs = responseXML.getElementsByTagName("consrchs")[0];
var consrch = consrchs.childNodes[0];
var srchid = (consrch.getElementsByTagName('srchid')[0]).childNodes[0].nodeValue;
var cnt = (consrch.getElementsByTagName('cnt')[0]).childNodes[0].nodeValue;
document.getElementById("srchid").value=srchid;
document.getElementById("cnt").innerHTML=cnt;
document.getElementById('loading').innerHTML='';
}
function keypresscall(tbl,fld,grp,flg,e){
var unicode=e.keyCode? e.keyCode : e.charCode;
if(unicode==13){
contactEntries(tbl,fld,grp,flg);
}
}
function getValueM(fld){
var val='';
var tnl = document.getElementById(fld);
for(i=0;i<tnl.length;i++){
if(tnl[i].selected == true){
val = val+","+tnl[i].value;
}
}
val=val.substring(1,val.length);
return val;
}


function contactEmailIdAll(count){
var checked = document.getElementById('checkAll_All').checked;
for(var i=0;i<count;i++){
document.getElementById("check_"+i).checked = checked;
}
document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
var url = "advcontact.do?method=contactEmailId&stt="+checked;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescontactEmailId(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function contactEmailId(objId , obj) {
var isChecked = obj.checked ;
document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
var url = "advcontact.do?method=contactEmailId&nmeId="+objId+"&stt="+isChecked;

var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescontactEmailId(req.responseXML);
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescontactEmailId(responseXML){
var message = responseXML.getElementsByTagName("msg")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='yes')
{
document.getElementById('loading').innerHTML = "";
}
}

function conatctNR(fld,val){
var fld1=document.getElementById(fld+'_1').value;
var fld2=document.getElementById(fld+'_2').value;
if(fld1=='')
document.getElementById(fld+'_1').value='0';
if(fld2=='')
document.getElementById(fld+'_2').value=val;
}

function stkUpd(lab,stkIdn,lprp,obj){
       var lprpVal = obj.value;
       var url = "ajaxissueAction.do?method=stkUpd&lab="+lab+"&mstkIdn="+stkIdn+"&lprp="+lprp+"&lprpVal="+lprpVal;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                   
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}

function offerdisplay(stkidn){
var isCheck = document.getElementById('cb_memo_offer_'+stkidn).checked;
if(isCheck==true){
document.getElementById('TDOffer_'+stkidn).style.display = '';
var url = "ajaxsrchAction.do?method=getOffer&stkIdn="+stkidn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesofferdisplay(req.responseXML , stkidn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
document.getElementById('TDOffer_'+stkidn).style.display = 'none';
}
}
function parseMessagesofferdisplay(responseXML,stkidn){
var ofrrte='',ofrdis='',todte='',comm='';
var offers = responseXML.getElementsByTagName("offers")[0];
if(offers.childNodes.length>0){
var offer = offers.childNodes[0];
ofrrte = (offer.getElementsByTagName('ofrrte')[0]).childNodes[0].nodeValue;
ofrdis = (offer.getElementsByTagName('ofrdis')[0]).childNodes[0].nodeValue;
todte = (offer.getElementsByTagName('todte')[0]).childNodes[0].nodeValue;
comm = (offer.getElementsByTagName('comm')[0]).childNodes[0].nodeValue;
}
if(todte=='-')
todte='';

document.getElementById("bid_prc_"+stkidn).value=ofrrte;
document.getElementById("bid_dis_txt_"+stkidn).value=ofrdis;
document.getElementById("comm_"+stkidn).value=comm;
document.getElementById('DTE_'+stkidn).value=todte;
}


function getPrpvalues(obj){
var lprp = obj.options[obj.options.selectedIndex].value;
var url = "ajaxsrchAction.do?method=loadbulkprp&lprp="+lprp ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesPrpvalues(req.responseXML,lprp);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagesPrpvalues(responseXML , lprp){
var columnDrop = document.getElementById('memoValC');
removeAllOptions(columnDrop);
var newOption = new Option();
newOption = new Option();
newOption.text = "---Selet---";
newOption.value = "";
columnDrop.options[0] = newOption;
var columns = responseXML.getElementsByTagName("prps")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("prpVal")[0]).childNodes[0].nodeValue);
var lkey = unescape((columnroot.getElementsByTagName("prpValD")[0]).childNodes[0].nodeValue);
if(loop==0){
if(lval=='NT'){
document.getElementById('memoValC').style.display='none';
document.getElementById('memoValTN').style.display='';
}else{
document.getElementById('memoValTN').value='';
document.getElementById('memoValTN').style.display='none';
document.getElementById('memoValC').style.display='';
}
}else{
var selectIndex = loop;
newOption = new Option();
newOption.text = lval;
newOption.value = lkey;
columnDrop.options[selectIndex] = newOption;
}
}
}

function  MonthlyCmp(cnt, rowkey,key) {
         var divDis = document.getElementById(rowkey+"_"+cnt).style.display
           if(divDis=='none'){
           document.getElementById(rowkey+"_"+cnt).style.display=''
            var url = "monthCmp.do?method=PrpWise&cnt="+cnt+"&row="+rowkey+"&key="+key;
            var req = initRequest();
            req.onreadystatechange = function() {    
         
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesMonthlyCmp(req.responseXML,rowkey,key, cnt);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
           }else{
               document.getElementById(rowkey+"_"+cnt).style.display='none';
           }
       
   }
   
  
  function  parseMessagesMonthlyCmp(responseXML , rowkey ,key, prpCnt) {
    //alert('@parseMessage '+ fld);
    var divIdLst=new Array(); 
    var divCnt=0;
    var rows = responseXML.getElementsByTagName("dtls")[0];
   for (var loop = 0; loop < rows.childNodes.length; loop++) {
   
    var row = rows.childNodes[loop];
    var cnt = row.getElementsByTagName("cnt")[0].childNodes[0].nodeValue;
    var dsc = row.getElementsByTagName("dsc")[0].childNodes[0].nodeValue;
    var prp = row.getElementsByTagName("prp")[0].childNodes[0].nodeValue;
    var mon = row.getElementsByTagName("mon")[0].childNodes[0].nodeValue;
    var nmeIdn = row.getElementsByTagName("nmeIdn")[0].childNodes[0].nodeValue;
    var divId = rowkey+"_"+prpCnt+"_"+dsc+"_"+mon;
    if(key=='MONTH'){
       divId = rowkey+"_"+prpCnt+"_"+dsc+"_"+nmeIdn; 
    }
    if(divIdLst.indexOf(divId)==-1){
        divIdLst[divCnt]=divId;
        divCnt++;
        document.getElementById(divId).innerHTML = "";
    }

    var innerHt = document.getElementById(divId).innerHTML;
    
 
    innerHt = innerHt+"<div style=\"width:100%; margin:0px;\"><div style=\"width:75%;vertical-align: top; margin:0px;\" class=\"float\" align=\"l\">"+prp+"&nbsp;&nbsp;&nbsp;&nbsp;</div><div style=\"width:25%;vertical-align: top; margin:0px;\" class=\"float\" align=\"right\">&nbsp;&nbsp;"+cnt+"</div> </div>"
    document.getElementById(divId).innerHTML = innerHt;
   }

}


function  MonthlyCmpGrpWise(grp, shape,lprp,obj) {
         var divDis = document.getElementById(shape+"_"+lprp+"_"+grp).style.display
           if(divDis=='none'){
           document.getElementById(shape+"_"+lprp+"_"+grp).style.display='';
           obj.src='../images/minus.jpg';
            var url = "monthCmp.do?method=GrpWise&grp="+grp+"&shape="+shape+"&lprp="+lprp;
            var req = initRequest();
            req.onreadystatechange = function() {    
         
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesMonthlyCmpGrpWise(req.responseXML,grp,shape, lprp);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
           }else{
           document.getElementById(shape+"_"+lprp+"_"+grp).style.display='none';
           obj.src='../images/plus.jpg';
           }
       
   }
   
  
  function  parseMessagesMonthlyCmpGrpWise(responseXML ,grp,shape, lprp) {
    //alert('@parseMessage '+ fld);
    var divIdLst=new Array(); 
    var divCnt=0;
    var rows = responseXML.getElementsByTagName("dtls")[0];
   for (var loop = 0; loop < rows.childNodes.length; loop++) {
   
    var row = rows.childNodes[loop];
    var cnt = row.getElementsByTagName("cnt")[0].childNodes[0].nodeValue;
    var dsc = row.getElementsByTagName("dsc")[0].childNodes[0].nodeValue;
    var prp = row.getElementsByTagName("prp")[0].childNodes[0].nodeValue;
    var szgrp = row.getElementsByTagName("szgrp")[0].childNodes[0].nodeValue;
    var divId = shape+"_"+lprp+"_"+grp+"_"+szgrp+"_"+dsc;
    if(divIdLst.indexOf(divId)==-1){
        divIdLst[divCnt]=divId;
        divCnt++;
        document.getElementById(divId).innerHTML = "";
    }

    var innerHt = document.getElementById(divId).innerHTML;
    var dscC="P"
    if(dsc=='='){
       dscC="E" 
    }
    if(dsc=='-'){
       dscC="M" 
    }
    var prpuni = replaceAll(prp,'+','%2B');
    prpuni = replaceAll(prpuni,'-','%2D');
    var lnk="<a href=\"../report/monthCmp.do?method=moncmpGrpPKTDTL&lprp="+lprp+"&szgrp="+szgrp+"&grp="+grp+"&shape="+shape+"&gtplgtmul="+prpuni+"&sign="+dscC+"\" target=\"_blank\">"+cnt+"</a>"
    innerHt = innerHt+"<div style=\"width:100%; margin:0px;\"><div style=\"width:75%;vertical-align: top; margin:0px;\" class=\"float\" align=\"l\">"+prp+"&nbsp;&nbsp;&nbsp;&nbsp;</div><div style=\"width:25%;vertical-align: top; margin:0px;\" class=\"float\" align=\"right\">&nbsp;&nbsp;"+lnk+"</div> </div>"
    document.getElementById(divId).innerHTML = innerHt;
   }

}

function  irCmpGrpWise(grp, shape,lprp,obj) {
         var divDis = document.getElementById(shape+"_"+lprp+"_"+grp).style.display
           if(divDis=='none'){
           document.getElementById(shape+"_"+lprp+"_"+grp).style.display='';
           obj.src='../images/minus.jpg';
            var url = "comparisonReport.do?method=GrpWise&grp="+grp+"&shape="+shape+"&lprp="+lprp;
            var req = initRequest();
            req.onreadystatechange = function() {    
         
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesirCmpGrpWise(req.responseXML,grp,shape, lprp);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
           }else{
           document.getElementById(shape+"_"+lprp+"_"+grp).style.display='none';
           obj.src='../images/plus.jpg';
           }
       
   }
   
  
  function  parseMessagesirCmpGrpWise(responseXML ,grp,shape, lprp) {
    //alert('@parseMessage '+ fld);
    var divIdLst=new Array(); 
    var divCnt=0;
    var rows = responseXML.getElementsByTagName("dtls")[0];
   for (var loop = 0; loop < rows.childNodes.length; loop++) {
   
    var row = rows.childNodes[loop];
    var cnt = row.getElementsByTagName("cnt")[0].childNodes[0].nodeValue;
    var dsc = row.getElementsByTagName("dsc")[0].childNodes[0].nodeValue;
    var prp = row.getElementsByTagName("prp")[0].childNodes[0].nodeValue;
    var szgrp = row.getElementsByTagName("szgrp")[0].childNodes[0].nodeValue;
    var divId = shape+"_"+lprp+"_"+grp+"_"+szgrp+"_"+dsc;
    if(divIdLst.indexOf(divId)==-1){
        divIdLst[divCnt]=divId;
        divCnt++;
        document.getElementById(divId).innerHTML = "";
    }

    var innerHt = document.getElementById(divId).innerHTML;
    var dscC="P"
    if(dsc=='='){
       dscC="E" 
    }
    if(dsc=='-'){
       dscC="M" 
    }
    var prpuni = replaceAll(prp,'+','%2B');
    prpuni = replaceAll(prpuni,'-','%2D');
    var lnk="<a href=\"../report/comparisonReport.do?method=moncmpGrpPKTDTL&lprp="+lprp+"&szgrp="+szgrp+"&grp="+grp+"&shape="+shape+"&gtplgtmul="+prpuni+"&sign="+dscC+"\" target=\"_blank\">"+cnt+"</a>"
    innerHt = innerHt+"<div style=\"width:100%; margin:0px;\"><div style=\"width:75%;vertical-align: top; margin:0px;\" class=\"float\" align=\"l\">"+prp+"&nbsp;&nbsp;&nbsp;&nbsp;</div><div style=\"width:25%;vertical-align: top; margin:0px;\" class=\"float\" align=\"right\">&nbsp;&nbsp;"+lnk+"</div> </div>"
    document.getElementById(divId).innerHTML = innerHt;
   }

}
function GetMFG_PRI(stk_id){
    var disId ="uprDis_"+stk_id;
    var dis = document.getElementById(disId).value;
    var prcisstt=document.getElementById('prcisstt').value;
    if(prcisstt=='PRI_FN_IS'){
    document.getElementById('return').disabled=true;
    document.getElementById('MFG_PRI_'+stk_id).disabled=true;
    }
    var url = "ajaxPrcAction.do?method=Getmnjpri&dis="+dis+"&stkIdn="+stk_id;
    var req = initRequest();
    req.onreadystatechange = function() {    
          if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesGetMFG_PRI(req.responseXML,stk_id,prcisstt);
                   } else if (req.status == 204){
                
                   }
               }
      };
     req.open("GET", url, true);
     req.send(null);
}

function parseMessagesGetMFG_PRI(responseXML,stk_id,prcisstt){
    var dis = responseXML.getElementsByTagName("dis")[0];
    var disVal = dis.childNodes[0].nodeValue;
    var rapRte = document.getElementById("rap_"+stk_id).value;
    var uprFld = "MFG_PRI_"+stk_id;
    var upr = parseFloat(rapRte)+(100 + parseFloat(disVal))/100;
    document.getElementById(uprFld).value = upr;
    if(prcisstt=='PRI_FN_IS'){
    document.getElementById('return').disabled=false;
    document.getElementById('MFG_PRI_'+stk_id).disabled=false;
}
}

function labRechk(stk_idn,obj){
 
    var val = obj.value;
    var url = "ajaxLabAction.do?method=setRechkRI&stkIdn="+stk_idn+"&val="+val;
    var req = initRequest();
    req.onreadystatechange = function() {    
          if (req.readyState == 4) {
                   if (req.status == 200) {
                    
                   } else if (req.status == 204){
                
                   }
               }
      };
     req.open("GET", url, true);
     req.send(null);
}

function doCompletionPartyNME(fld, dspFldId, url, e) {
var emp_idn=document.getElementById('byrId')
if(emp_idn!=null)
emp_idn=emp_idn.value;
else
emp_idn="0";
var val = document.getElementById(fld).value;
var dspFld = document.getElementById(dspFldId);
var unicode=e.keyCode? e.keyCode : e.charCode;
//alert(val + " : " + unicode);
//alert('Calling completion : '+unicode);
if(unicode >=48 && unicode <=90 || unicode ==8 || unicode ==55) {
if (val == "") {
removeAllOptions(dspFld);
dspFld.style.display='none';
} else if(val.length >= 1) {
val=val.replace('&', '%26'); 
url = url + "&param="+val+"&lFld="+fld+"&emp="+emp_idn;
url = url.replace('+', '%2B');
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesNme(req.responseXML,fld, dspFldId);
} else if (req.status == 204){
//clearTable();
}
}
};
req.open("GET",url, true);
req.send(null);
moveFocus(dspFldId);
}

}
}
function doCompletionPartyNMEGeneric(fld, dspFldId, url,emp, e) {
var emp_idn=document.getElementById(emp).value;
var val = document.getElementById(fld).value;
var dspFld = document.getElementById(dspFldId);
var unicode=e.keyCode? e.keyCode : e.charCode;
//alert(val + " : " + unicode);
//alert('Calling completion : '+unicode);
if(unicode >=48 && unicode <=90 || unicode ==8 || unicode ==55) {
if (val == "") {
removeAllOptions(dspFld);
dspFld.style.display='none';
} else if(val.length >= 1) {
val=val.replace('&', '%26'); 
url = url + "&param="+val+"&lFld="+fld+"&emp="+emp_idn;
url = url.replace('+', '%2B');
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesNme(req.responseXML,fld, dspFldId);
} else if (req.status == 204){
//clearTable();
}
}
};
req.open("GET",url, true);
req.send(null);
moveFocus(dspFldId);
}

}
}
function doCompletionMobilePartyNME(fld, dspFldId, url) {
var emp_idn=document.getElementById('byrId').value;
var val = document.getElementById(fld).value;
var dspFldIdstt=document.getElementById(dspFldId).style.display;
var dspFld = document.getElementById(dspFldId);
if(dspFldIdstt=='none'){
if (val == "") {
removeAllOptions(dspFld);
dspFld.style.display='none';
} else if(val.length >= 1) {
val=val.replace('&', '%26'); 
url = url + "&param="+val+"&lFld="+fld+"&emp="+emp_idn;
url = url.replace('+', '%2B');
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesNme(req.responseXML,fld, dspFldId);
} else if (req.status == 204){
//clearTable();
}
}
};
req.open("GET",url, true);
req.send(null);
moveFocus(dspFldId);
}
}else{
hideObj(dspFldId);
getTrmsNME('party','SRCH');  
}
}

function saveBulkWebAdvRole(usrIdn,roleIdn, obj){
var stt = obj.checked;
var url = "bulkUpdate.do?method=save&usrIdn="+usrIdn+"&roleIdn="+roleIdn+"&stt="+stt;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function checkALLBulkRole(checkId , count,loop){
var checked = document.getElementById('checkAll_'+checkId).checked;
for(var i=0;i<count;i++){
var objId = checkId+'_'+i;
var dis = document.getElementById(objId).disabled ;
if(!dis){
document.getElementById(objId).checked = checked;
if(i==0){
if(checked==true)
stt="A";
else
stt="IA";
var url = "bulkUpdate.do?method=checkAllBulk&roleName="+checkId+"&stt="+stt;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}
}
}

function MixSaleHis(stkIdn, cnt ,typ, obj){

   var divId = "BYR_"+cnt;
   var dis = document.getElementById(divId).style.display;
   if(dis!=''){
   obj.src='../images/minus.png';
   var  dtfr = document.getElementById("dtefr").value;
   var  dtto = document.getElementById("dteto").value;
   var  vnm = document.getElementById("vnm").value;
   var  addatIdn = document.getElementById("addatIdn")
  if(addatIdn!=null)
    addatIdn=addatIdn.value;
    else
    addatIdn=0;
   var  brokerIdn = document.getElementById("brokerIdn")
   if(brokerIdn!=null)
   brokerIdn=brokerIdn.value;
   else
   brokerIdn=0;
   var  byrId = document.getElementById("byrId")
   if(byrId!=null)
    byrId=byrId.value;
    else
    byrId=0;
   
    var url = "mixSaleReport.do?method=saleHis&stkIdn="+stkIdn+"&dtefr="+dtfr+"&dteto="+dtto+"&vnm="+vnm+"&typ="+typ+"&addatIdn="+addatIdn+"&brokerIdn="+brokerIdn+"&byrId="+byrId;
    var req = initRequest();
    req.onreadystatechange = function() {    
          if (req.readyState == 4) {
                   if (req.status == 200) {
                    parseMessagesSaleHis(req.responseXML , divId)
                   } else if (req.status == 204){
                
                   }
               }
      };
     req.open("GET", url, true);
     req.send(null);
   }else{
     document.getElementById(divId).style.display ='none';
     obj.src='../images/plus.png';
   }
}

function MixSaleHisGroup(par,paramVal, cnt ,typ, obj){

   var divId = "BYR_"+cnt;
   var dis = document.getElementById(divId).style.display;
   if(dis!=''){
   obj.src='../images/minus.png';
   var  dtfr = document.getElementById("dtefr").value;
   var  dtto = document.getElementById("dteto").value;
   var  vnm = document.getElementById("vnm").value;
   var  addatIdn = document.getElementById("addatIdn")
  if(addatIdn!=null)
    addatIdn=addatIdn.value;
    else
    addatIdn=0;
   var  brokerIdn = document.getElementById("brokerIdn")
   if(brokerIdn!=null)
   brokerIdn=brokerIdn.value;
   else
   brokerIdn=0;
   var  byrId = document.getElementById("byrId")
   if(byrId!=null)
    byrId=byrId.value;
    else
    byrId=0;
    var url = "mixSaleReport.do?method=saleHis&"+par+"="+paramVal+"&dtefr="+dtfr+"&dteto="+dtto+"&vnm="+vnm+"&typ="+typ+"&addatIdn="+addatIdn+"&brokerIdn="+brokerIdn+"&byrId="+byrId;
    var req = initRequest();
    req.onreadystatechange = function() {    
          if (req.readyState == 4) {
                   if (req.status == 200) {
                    parseMessagesSaleHis(req.responseXML , divId)
                   } else if (req.status == 204){
                
                   }
               }
      };
     req.open("GET", url, true);
     req.send(null);
   }else{
     document.getElementById(divId).style.display ='none';
     obj.src='../images/plus.png';
   }
}

function  parseMessagesSaleHis(responseXML , divId){
  var  cnt = document.getElementById("cnt").value;
  var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">ID</th>";


str=str+"<th class=\"Orangeth\" align=\"center\">Buyer</th><th class=\"Orangeth\" align=\"center\">Date</th>" ;
if(cnt!='HK'){
str=str+"<th class=\"Orangeth\" style=\"text-align:right\" align=\"center\">Qty</th>" ;
}
str=str+"<th class=\"Orangeth\" style=\"text-align:right\" align=\"center\">Cts</th><th class=\"Orangeth\" style=\"text-align:right\">Rate</th><th class=\"Orangeth\" style=\"text-align:right\" align=\"center\">Value</th></tr>" ;


var memos = responseXML.getElementsByTagName("sales")[0];

for (var loop = 0; loop < memos.childNodes.length; loop++) {
var memo = memos.childNodes[loop];

var fnlSal = memo.getElementsByTagName("fnlsal")[0];
var idn = memo.getElementsByTagName("id")[0];
var cts = memo.getElementsByTagName("cts")[0];
var vlu = memo.getElementsByTagName("vlu")[0];
var dte = memo.getElementsByTagName("dte")[0];
var fnme = memo.getElementsByTagName("fnme")[0];
var qty = memo.getElementsByTagName("qty")[0];

str = str+"<tr> ";

str = str+ "<td align=\"center\">"+idn.childNodes[0].nodeValue+"</td><td align=\"center\">"+unescape(fnme.childNodes[0].nodeValue)+"</td> <td align=\"center\">"+dte.childNodes[0].nodeValue+"</td>" ;
if(cnt!='HK'){
str = str+"<td align=\"center\" style=\"text-align:right\">"+qty.childNodes[0].nodeValue+"</td>" ;
}
str = str+"<td align=\"center\" style=\"text-align:right\">"+cts.childNodes[0].nodeValue+"</td><td align=\"center\" style=\"text-align:right\">"+fnlSal.childNodes[0].nodeValue+"</td><td align=\"center\" style=\"text-align:right\">"+vlu.childNodes[0].nodeValue+"</td></tr>"
}

str = str+"</table>"
document.getElementById(divId).style.display ='';
document.getElementById(divId).innerHTML = str;
}

function getPurDmd(obj){
var nmeIdn = obj.options[obj.options.selectedIndex].value;
var url = "purchaseDmdAction.do?method=loaddmdIdns&nmeIdn="+nmeIdn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

parseMessagesPurDmd(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}

function parseMessagesPurDmd(responseXML){
var columnDrop = document.getElementById('dmdId');
//alert(responseXML);
removeAllOptions(columnDrop);

var newOption = new Option();
newOption = new Option();
newOption.text = "---select---";
newOption.value = "0";
columnDrop.options[0] = newOption;
var columns = responseXML.getElementsByTagName("dmdLsts")[0];
//alert(columns);
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = columnroot.getElementsByTagName("nme")[0];
var lkey = columnroot.getElementsByTagName("nmeid")[0];
newOption = new Option();
newOption.text = lval.childNodes[0].nodeValue;
newOption.value = lkey.childNodes[0].nodeValue;
columnDrop.options[loop+1] = newOption;
}
}


function getBoxRfidVal(){
var boxrfid=document.getElementById('boxrfid').value;
if(boxrfid!=''){
var url = "pktrfidboxalloc.do?method=getBoxRfidVal&boxrfid="+boxrfid ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesBoxRfidVal(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}
}
function parseMessagesBoxRfidVal(responseXML){
var details = responseXML.getElementsByTagName("details")[0];
var detail = details.childNodes[0];
var boxrfidval = (detail.getElementsByTagName('boxrfidval')[0]).childNodes[0].nodeValue;
if(boxrfidval!='N'){
document.getElementById('boxrfidval').value=boxrfidval;
document.getElementById('boxrfidvaldisplay').innerHTML=boxrfidval;
document.getElementById('prpVal').disabled=false;
}else{
document.getElementById('boxrfid').value='';
document.getElementById('boxrfidval').value='';
document.getElementById('boxrfidvaldisplay').innerHTML='';
document.getElementById('prpVal').disabled=true;
alert('Box Rfid Invalid Please Verify');
document.getElementById('boxrfid').focus();
}
}

function checkALLBulkwebusers(checkId , count){
var checked = document.getElementById('checkAll_'+checkId).checked;
for(var i=0;i<count;i++){
var objId = checkId+'_'+i;
document.getElementById(objId).checked = checked;
}
var url = "bulkUpdate.do?method=checkWebusers&stt="+checked;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}


function checkBulkwebusers(usrIdn, obj){
var stt = obj.checked;
var url = "bulkUpdate.do?method=checkWebusers&usrIdn="+usrIdn+"&stt="+stt;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {

} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function validateNmeDtlAttrVal(obj,mprpLst){
var vfyArray=mprpLst.split("_");
var varId=obj.split("_");
var mprp=document.getElementById("mprp_"+varId[1]).value;
if(mprp!=''){
for(var s =0 ;s<vfyArray.length ;s++){
var mprpArr = vfyArray[s];
if(mprpArr==mprp){
    verifyMailid(obj,mprp);
}
}
}else{
    alert("please Select Attribute");
    document.getElementById(obj).value='';
}
}
function verifyMailid(obj,mprp){
var val=document.getElementById(obj).value;
var nmeIdn=document.getElementById('nid').value;

var url = "ajaxVerifyContactAction.do?method=verifyEmailid&val="+val+"&mprp="+mprp+"&nmeIdn="+nmeIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesVerifymailid(req.responseXML,obj);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesVerifymailid(responseXML,obj){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='yes')
{
alert("Is Already Exist Try New.");
document.getElementById(obj).value="";
document.getElementById(obj).focus();
}
}

function generatesrnoxl(take,msgbody){
var nmeidn=document.getElementById(take).value;
if(nmeidn!='' && nmeidn!='0'){
var r=confirm("Are you sure want to generate SrNo.?");
if(r==true){
document.getElementById(msgbody).innerHTML = "<img src=\"../images/processing1.gif\" align=\"middle\" border=\"0\" />";
var url = "ajaxsrchAction.do?method=generatesrno&nmeIdn="+nmeidn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesgeneratesrno(req.responseXML,msgbody,nmeidn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
}else{
    alert("Please Select Buyer");
 }
}
function parseMessagesgeneratesrno(responseXML,msgbody,nmeidn){
var memoPg="show_stt.jsp";
    var webUrl = document.getElementById("webUrl").value;
  var reportUrl = document.getElementById("repUrl").value;
  var cnt = document.getElementById("cnt").value;
var repPath = document.getElementById("repPath").value;
var message = responseXML.getElementsByTagName("message")[0];
message = message.childNodes[0].nodeValue;
var msg="";
if(message=='S'){
var theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+memoPg+"&p_byr="+nmeidn; 
msg=" <label class=\"redLabel\" > SRNO Generated Successfully ..</label></br> <A href="+theURL+"  target=\"_blank\"> <span class=\"txtLink\" >Stock Report </span> </a>";
}else{
  msg="<label class=\"redLabel\" >SRNO Generatation Failed ..</label>";
}
document.getElementById(msgbody).innerHTML=msg;
}



function BranchReceivedTotal(frmNme, obj){
     var ttlQty = 0;
     var ttlCts = 0;
     var ttlVlu = 0;
     var frm_elements = document.forms[frmNme].elements; 
      for(i=0; i<frm_elements.length; i++) {
        var field_type = frm_elements[i].type.toLowerCase();
          if(field_type=='checkbox') {
          var fieldId = frm_elements[i].id;
         if(fieldId.indexOf(obj)!=-1){
           if(document.getElementById(fieldId).checked){
            var valLst = document.getElementById(fieldId).value;
            var val = valLst.split("_");
              if(val.length==2){
          	var lCts = parseFloat(document.getElementById('cts_'+valLst).value);
           
            var vlu =  parseFloat(document.getElementById('amt_'+valLst).value);
             ttlCts =  parseFloat(ttlCts) + lCts ;
            ttlVlu =  parseFloat(ttlVlu )+ vlu;
            ttlQty = ttlQty + 1;             
          }
           }
      }
  }
  }
           document.getElementById('ttlQty').innerHTML = ttlQty;
           document.getElementById('ttlCts').innerHTML = get2DigitNum(ttlCts);
           document.getElementById('ttlVlu').innerHTML = get2DigitNum(ttlVlu);
}
     
    

 function branchLst(valLst){
   var val = valLst.split("_");
   if(val.length==2){
    var idn =  val[0];
             var stkIdn = val[1];
       
       var url = "ajaxsrchAction.do?method=BranchIdnLst&Idn="+idn+"&stkIdn="+stkIdn+"&isChecked="+ischecked ;
       var req = initRequest();
       req.onreadystatechange = function() {
      if (req.readyState == 4) {
        if (req.status == 200) {
           
          } else if (req.status == 204){
          }
             }
         };
        req.open("GET", url, false);
        req.send(null);
   }
 }
 
   function  getBranchReceivedTtl(){
     
      var url = "ajaxsrchAction.do?method=BranchTtl";
       var req = initRequest();
       req.onreadystatechange = function() {
      if (req.readyState == 4) {
        if (req.status == 200) {
             parseMessagesBranchReceiveTttl(req.responseXML);
          } else if (req.status == 204){
          }
             }
         };
        req.open("GET", url, false);
        req.send(null);
   }
   function parseMessagesBranchReceiveTttl(responseXML){
     
     var details = responseXML.getElementsByTagName("ttls")[0];
var detail = details.childNodes[0];

var qty = detail.getElementsByTagName("qty")[0];
var cts = detail.getElementsByTagName("cts")[0];
var vlu = detail.getElementsByTagName("vlu")[0];
document.getElementById('ttlQty').innerHTML =qty.childNodes[0].nodeValue;
document.getElementById('ttlCts').innerHTML =cts.childNodes[0].nodeValue;
document.getElementById('ttlVlu').innerHTML =vlu.childNodes[0].nodeValue;
   }
   
   
function hithistory(stkIdn){
   var tdId = "HITROW_"+stkIdn;
   var isDisplay = document.getElementById(tdId).style.display ;
   if(isDisplay=='none'){
document.getElementById('HIT_'+stkIdn).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
var url = "genericReport.do?method=hitData&stkIdn="+stkIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessageshithistory(req.responseXML , stkIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
   }else{
       document.getElementById(tdId).style.display = 'none';
   }
}

function parseMessageshithistory(responseXML , stkIdn){
var tdId = "HITROW_"+stkIdn;
var hitss = responseXML.getElementsByTagName("hitss")[0];
var lnt = hitss.childNodes.length;
var str = "";
if(lnt==0){
str="<table><tr>No Data found.</tr></table>"
}else{
str ="<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:700px\">";
for (var loop = 0; loop < hitss.childNodes.length; loop++) {
var hit = hitss.childNodes[loop];
var idn = (hit.getElementsByTagName("idn")[0]).childNodes[0].nodeValue;
var byr = unescape((hit.getElementsByTagName("byr")[0]).childNodes[0].nodeValue);
var createdby = (hit.getElementsByTagName("createdby")[0]).childNodes[0].nodeValue;
var modifiedby = (hit.getElementsByTagName("modifiedby")[0]).childNodes[0].nodeValue;
var rmk = unescape((hit.getElementsByTagName("rmk")[0]).childNodes[0].nodeValue);
var dtertn = (hit.getElementsByTagName("dtertn")[0]).childNodes[0].nodeValue;
if(loop==0){
str += "<tr><th class=\"Orangeth\" align=\"center\">Memo Id</th><th class=\"Orangeth\" align=\"center\">Buyer</th><th class=\"Orangeth\" align=\"center\">Created By</th><th class=\"Orangeth\" align=\"center\">Modified By</th><th class=\"Orangeth\" align=\"center\">Remark</th><th class=\"Orangeth\" align=\"center\">Date Return</th></tr>";
}
str = str+ "<tr><td align=\"center\">"+idn+"</td> <td align=\"center\">"+byr+"</td>";
str = str+ "<td align=\"center\">"+createdby+"</td>";
str = str+ "<td align=\"center\">"+modifiedby+"</td><td align=\"center\">"+rmk+"</td><td align=\"center\">"+dtertn+"</td></tr>";
}
str = str+"</table>"
}
var divId = "HIT_"+stkIdn;
document.getElementById(divId).innerHTML = str;
document.getElementById(tdId).style.display = '';
}
  
function validate_print(jspname,param){
 var req = initRequest();
 var url = "ajaxsrchAction.do?method=selectPrint";
  req.onreadystatechange = function() {
 if (req.readyState == 4) {
 if (req.status == 200) {
 parseMessagesselectPrint(req.responseXML,jspname,param);
 } else if (req.status == 204){
 }
 }
 };
 req.open("GET", url, true);
 req.send(null);
 
 }

 function parseMessagesselectPrint(responseXML,jspname,param){
  var repcnt = document.getElementById("CNT").value;
  var repurl = document.getElementById("repUrl").value;
  var repPath = document.getElementById("repPath").value;
  var message = responseXML.getElementsByTagName("message")[0];
  var memoId = message.childNodes[0].nodeValue;  
  if(memoId != 'NOSELECTION') {
  if(memoId == "0"){
  alert("Print report not generated, Please try again.");
  } else {
  return window.open(repPath+'/reports/rwservlet?'+repcnt+'&report='+repurl+'\\reports\\'+jspname+'&'+param+'='+memoId,'PrintRecord','width=800,height=600,location=no,scrollbars=yes,resizable=yes');
  }}
  }
  
  
  
  
  function validate_MailPdf(jspname){
 var req = initRequest();
 var url = "ajaxsrchAction.do?method=selectPrint";
  req.onreadystatechange = function() {
 if (req.readyState == 4) {
 if (req.status == 200) {
 parseMessagesselectMailPdf(req.responseXML,jspname);
 } else if (req.status == 204){
 }
 }
 };
 req.open("GET", url, true);
 req.send(null);
 
 }

 function parseMessagesselectMailPdf(responseXML,jspname){
 
  var message = responseXML.getElementsByTagName("message")[0];
  var memoId = message.childNodes[0].nodeValue;  
  var url="StockSearch.do?method=mailExcelMass&typ=srch&memoId="+memoId+"&jspNme="+jspname;
   window.open(url,'_blank');
 }
  
  
function verifyPurchaseVnm(obj){
var vnm=document.getElementById(obj).value;
var url = "purchaseDtlAction.do?method=verifyPurchaseVnm&vnm="+vnm ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesverifyPurchaseVnm(req.responseXML,obj);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesverifyPurchaseVnm(responseXML,obj){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='yes')
{
alert("Packet Id Is Already Exist Try New.");
document.getElementById(obj).value="";
document.getElementById(obj).focus();
}
}



function populateSalePerson(grp , emp){

var grpNameIdn = document.getElementById(grp).value;
var url = "ajaxsrchAction.do?method=populateSalePerson&grpNameIdn="+grpNameIdn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesSalePerson(req.responseXML,emp);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}

function parseMessagesSalePerson(responseXML,emp){

var columnDrop = document.getElementById(emp);

removeAllOptions(columnDrop);
var newOption = new Option();
newOption = new Option();
newOption.text = "---select---";
newOption.value = "";
columnDrop.options[0] = newOption;
var columns = responseXML.getElementsByTagName("mnme")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = columnroot.getElementsByTagName("nme")[0];
var lkey = columnroot.getElementsByTagName("nmeid")[0];
newOption = new Option();
newOption.text = lval.childNodes[0].nodeValue;
newOption.value = lkey.childNodes[0].nodeValue;
columnDrop.options[loop+1] = newOption;
}
}

function getLstMemoNG(bryIdn , rlnId){
    
        var url = "ajaxsrchAction.do?method=getLstMemoNG&bryId="+bryIdn+"&rlnId="+rlnId ;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesgetLstMemoNG(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
function parseMessagesgetLstMemoNG(responseXML){

var str = "";
var counter=1;
var columns = responseXML.getElementsByTagName("memos")[0];

if(columns.childNodes.length==0){
document.getElementById('lstMemoNGDiv').innerHTML = "";
}else{
document.getElementById('lstMemoNGDiv').innerHTML = "";
str = "<table  cellspacing=\"1\" cellpadding=\"1\" class=\"grid1\"  width=\"100%\" id=\"memoNG\"><tr><td colspan=\"5\" align=\"center\" onclick=\"displayDiv('LstMemoNG')\"><b>Last New Goods Memo's </b></td></tr><tr><td><div id=\"LstMemoNG\" style=\"margin:0px; padding:0px;\"><table class=\"grid1\" width=\"100%\"><tr><th align=\"center\">Sr No</th>" +
"<th><input type=\"checkbox\" id=\"ALL\" onclick=\"CheckAllMemo('ALL')\"></th> " +
"<th align=\"center\">Memo Id</th><th align=\"center\">Qty</th><th align=\"center\">Cts</th><th align=\"center\">Date</th></tr>";
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var memoId = columnroot.getElementsByTagName("memoId")[0].childNodes[0].nodeValue;
var dte = columnroot.getElementsByTagName("dte")[0].childNodes[0].nodeValue;//shiv
var qty = columnroot.getElementsByTagName("qty")[0].childNodes[0].nodeValue;//shiv
var cts = columnroot.getElementsByTagName("cts")[0].childNodes[0].nodeValue;//shiv
var cb_memo = "cb_memo_"+memoId;
str = str + "<tr><td align=\"center\">" +counter+"</td>" +
"<td align=\"center\"><input type=\"checkbox\" id=\""+cb_memo+"\" value=\""+memoId+"\" onclick=\"CheckAllMemo('"+cb_memo+"')\"></td>" +
"<td align=\"center\">"+memoId+"</td><td align=\"right\">"+qty+"</td><td align=\"right\">"+cts+"</td><td align=\"right\">"+dte+"</td></tr>";
counter++;
}
str = str +"</table></div></td></tr></table>" ;
document.getElementById('lstMemoNGDiv').innerHTML = str;
}
}

function LOCKPKT(stkIdn,issIdn){
    var url = "mixAssortRtnAction.do?method=LockPkt&stkIdn="+stkIdn+"&issId="+issIdn ;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesLockPkt(req.responseXML,stkIdn);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
      
}

function parseMessagesLockPkt(responseXML,stkIdn){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='yes')
{
var linkId="LNKD_"+stkIdn;
var BTNId = "BTN_"+stkIdn;
var chkId = "CHK_"+stkIdn;
var txtId ="TXT_"+stkIdn;


window.parent.document.getElementById(linkId).style.display="none";
window.parent.document.getElementById(BTNId).style.display="block";
window.parent.document.getElementById(chkId).disabled=false;
window.parent.document.getElementById(txtId).readOnly = true;
	$("#backgroundPopup",window.parent.document).fadeOut("slow");
  $("#popupContactASSFNL",window.parent.document).fadeOut("slow");

}
}

function unlockLnk(issId,stkIdn){

var url = "mixAssortRtnAction.do?method=unLockPkt&stkIdn="+stkIdn+"&issId="+issId;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesunLockPkt(req.responseXML,stkIdn);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
    
 
}
function parseMessagesunLockPkt(responseXML,idn){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='yes')
{
 var linkId="LNKD_"+idn;
var BTNId = "BTN_"+idn;
var chkId = "CHK_"+idn;
var txtId ="TXT_"+idn;
document.getElementById(linkId).style.display="block";
document.getElementById(BTNId).style.display="none";
document.getElementById(chkId).disabled=true;
document.getElementById(txtId).readOnly = false;

}
}


function getMemoDtl(){
document.getElementById('memoDtl').style.display='';
document.getElementById('lstMemoDiv').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";

      var bryIdn = document.getElementById('byrIdn').value;
       var rlnId = document.getElementById('rlnIdn').value;
       var memosTyp = document.getElementById('memosTyp').value;
     var PKT_TY = document.getElementById('PKT_TY');
     if(PKT_TY!=null)
     PKT_TY =PKT_TY.value;
    else
    PKT_TY="";
     
        var url = "ajaxsrchAction.do?method=pricechangememotypIdn&nameIdn="+bryIdn+"&nmeRln="+rlnId+"&typ="+memosTyp+"&PKTTY="+PKT_TY ;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesgetMemoDtl(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
function parseMessagesgetMemoDtl(responseXML){

var str = "";
var counter=1;
var columns = responseXML.getElementsByTagName("memos")[0];

if(columns.childNodes.length==0){
document.getElementById('memoDtl').style.display='';

document.getElementById('lstMemoDiv').innerHTML = "Sorry no result found";

}else{
str = "<table  cellspacing=\"1\" cellpadding=\"1\" width=\"100%\" class=\"grid1\" ><tr><th><input type=\"checkbox\" id=\"ALL\" onclick=\"ALLCheckBoxTyp('ALL','cb_memo_','0')\"></th> </th></th><th align=\"center\">Memo Id</th><th align=\"center\">Qty</th><th align=\"center\">Cts</th><th>Value</th><th align=\"center\">Date</th><th>Type</th></tr>";
for(var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var memoId = columnroot.getElementsByTagName("id")[0].childNodes[0].nodeValue;
var dte = columnroot.getElementsByTagName("dte")[0].childNodes[0].nodeValue;//shiv
var qty = columnroot.getElementsByTagName("qty")[0].childNodes[0].nodeValue;//shiv
var cts = columnroot.getElementsByTagName("cts")[0].childNodes[0].nodeValue;//shiv
var typ = columnroot.getElementsByTagName("typ")[0].childNodes[0].nodeValue;//shiv
var vlu = columnroot.getElementsByTagName("vlu")[0].childNodes[0].nodeValue;//shiv

var cb_memo = "cb_memo_"+memoId;

str = str + "<tr><td><input type=\"checkbox\" name=\""+cb_memo+"\" id=\""+cb_memo+"\" value=\""+memoId+"\" ></td>"+
    "<td align=\"center\">"+memoId+"</td><td align=\"right\">"+qty+"</td><td align=\"right\">"+cts+"</td><td align=\"right\">"+vlu+"</td><td align=\"right\">"+dte+"</td><td align=\"right\">"+typ+"</td></tr>";
}
str = str +"</table>" ;
document.getElementById('memoDtl').style.display='';

document.getElementById('lstMemoDiv').innerHTML = str;
}
document.getElementById('memoDtlLst').style.display='none';

}

function DisplayDivDtl(style){
document.getElementById('memoDtl').style.display=style;

}

function getBuyTrms(byrId,rlnId,dfl){
           
       var bryIdn = document.getElementById(byrId).value;
       if(bryIdn!='' && bryIdn!='0'){
       var url = "ajaxsrchAction.do?method=loadTrm&bryId="+bryIdn ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesBuyTrm(req.responseXML,rlnId,bryIdn,dfl);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }else{
      var columnDrop = document.getElementById('rlnId');
      removeAllOptions(columnDrop);
      var newOption = new Option();
       newOption = new Option();
       newOption.text = "---Selet---";
       newOption.value = "0";
       columnDrop.options[0] = newOption;
}
 }

 
 function parseMessagesBuyTrm(responseXML , rlnId , bryIdn,dfl){
     var columnDrop = document.getElementById(rlnId);
    removeAllOptions(columnDrop);
    if(dfl=='Y'){
    var newOption = new Option();
       newOption = new Option();
             newOption.text = "---Selet---";
             newOption.value = "0";
             columnDrop.options[0] = newOption;
      
    }
       var columns = responseXML.getElementsByTagName("trms")[0];
       var dftTrm ="";
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var selectIndex = loop;
         if(dfl=='Y')
            selectIndex = loop+1;
           var columnroot = columns.childNodes[loop];
          
           var lval = columnroot.getElementsByTagName("trmDtl")[0];
           var lkey = columnroot.getElementsByTagName("relId")[0];
           
            
             newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[selectIndex] = newOption;
             if(dftTrm=='')
             dftTrm=lkey.childNodes[0].nodeValue;
       }
   }


function currentTm(fld,fldhidden){
       var url = "ajaxsrchAction.do?method=currentTm" ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagescurrentTm(req.responseXML,fld,fldhidden);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }

 
function parseMessagescurrentTm(responseXML ,fld,fldhidden){
var times = responseXML.getElementsByTagName("times")[0];
if(times.childNodes.length>0){
var time = times.childNodes[0];
var displaytm = (time.getElementsByTagName('displaytm')[0]).childNodes[0].nodeValue;
var inserttm = (time.getElementsByTagName('inserttm')[0]).childNodes[0].nodeValue;
document.getElementById(fld).innerHTML='<b>Start Time -'+displaytm+'</b>';
document.getElementById(fldhidden).value=inserttm;
}
}


function LotIdnGenrate(fldIdn){
var miner = document.getElementById("miner").value;
if(miner!=""){
    var url = "roughPurDtlAction.do?method=LotIdnGen&miner="+miner ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesLotIdnGen(req.responseXML,fldIdn);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null); 
}else{
    alert("Please select Miner propety");
}
}

function parseMessagesLotIdnGen(responseXML ,fldIdn){
 var lotno = responseXML.getElementsByTagName("lotno")[0];
  var lotIdn = lotno.childNodes[0].nodeValue;
  
  document.getElementById(fldIdn).value=lotIdn;
}

function createplan(loop,stkIdn,issueIdn){
var reqUrl = document.getElementById('reqUrl').value;
showHidDiv("PLANDIV_"+loop);
var plan=document.getElementById('plan_'+loop).value;
if(plan==''){
plan="1";
}
document.getElementById("PLANFRAME_"+loop).src=reqUrl+'/rough/planingReturnAction.do?method=loadPlan&stkIdn='+stkIdn+'&plan='+plan+'&issueIdn='+issueIdn+'&loop='+loop;

}

function closePlannigIframe(loop){
var iframeIdn = "#PLANDIV_"+loop;
$(iframeIdn,window.parent.document).css('display','none');
}

function frameOpen(frmId,url){
var reqUrl = document.getElementById('reqUrl').value;
document.getElementById(frmId).src=reqUrl+url;


}
function saveplan() {
var vld=false;
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[i].checked){
vld=true;
}
}
}
if(!vld){
alert('Please Select Plan checkbox to save Tranction');
return false;
}
return true;
}

function SetParentWindow(stkIdn,planSeq){
     var url = "planingReturnAction.do?method=PlanCal&stkIdn="+stkIdn+"&planSeq="+planSeq;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesSetParentWindow(req.responseXML,stkIdn);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null); 
}

function parseMessagesSetParentWindow(responseXML,stkIdn){
     
     var details = responseXML.getElementsByTagName("VALUES")[0];
    for (var loop = 0; loop < details.childNodes.length; loop++) {
var detail = details.childNodes[loop];
var seq = detail.getElementsByTagName("SEQ")[0].childNodes[0].nodeValue;
var qty = detail.getElementsByTagName("QTY")[0];
var cts = detail.getElementsByTagName("CTS")[0];
var vlu = detail.getElementsByTagName("VLU")[0];
var rte = detail.getElementsByTagName("RTE")[0];
var rcts = document.getElementById('CTS_'+stkIdn).value;
var rRte = get2DigitNum(parseFloat(vlu.childNodes[0].nodeValue)/parseFloat(rcts));

document.getElementById('PLANTR_'+seq+"_"+stkIdn).style.display = '';
document.getElementById('QTY_'+seq+"_"+stkIdn).innerHTML =qty.childNodes[0].nodeValue;
document.getElementById('CTS_'+seq+"_"+stkIdn).innerHTML =cts.childNodes[0].nodeValue;
document.getElementById('VLU_'+seq+"_"+stkIdn).innerHTML =vlu.childNodes[0].nodeValue;
document.getElementById('RTE_'+seq+"_"+stkIdn).innerHTML =rte.childNodes[0].nodeValue;
document.getElementById('RRTE_'+seq+"_"+stkIdn).innerHTML =rRte;
       }
   }

function GenratePkt(obj,lprpLst,lprpVal,lprp){
     var issueIdn = document.getElementById('issueIdn').value;
      var commonLprp = document.getElementById('commonLprp').value;
       var commonLprpSrt = document.getElementById('commonLprpSrt').value;
       lprpLst =lprpLst+"@"+commonLprp;
       lprpVal = lprpVal+"@"+commonLprpSrt;
     var lprpVlu = obj.value;
     var url = "mixAssortReturn.do?method=GenPkt&lprpLst="+lprpLst+"&lprpVal="+lprpVal+"&issueIdn="+issueIdn+"&lprp="+lprp+"&lprpVlu="+lprpVlu;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                   } else if (req.status == 204){
                      if(lprp=='CRTWT'){
                            parseMessagesSetRate(req.responseXML,obj.id);
                      }
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null); 
}


function parseMessagesSetRate(responseXML,rteid){
    rteid = rteid.replace('CTS','RTE');
     var rteNode = responseXML.getElementsByTagName("RTE")[0];
     var rte = rteNode.childNodes[0].nodeValue;
     document.getElementById(rteid).value=rte;
}

function SetPrpOnPrcID(){

 var mprcIdn = document.getElementById('mprcIdn').value;

 var url = "mixAssortReturn.do?method=PRCPRP&PRCID="+mprcIdn;

       var req = initRequest();

        req.onreadystatechange = function() {   

               if (req.readyState == 4) {

                   if (req.status == 200) {

                  

                      parseMessagesSetPRP(req.responseXML);

                   } else if (req.status == 204){

                

                            parseMessagesSetPRP(req.responseXML);

                  

                   }

               }

           };

           req.open("GET", url, true);

           req.send(null);

}
function parseMessagesSetPRP(responseXML){

  hidStyle();

    var details = responseXML.getElementsByTagName("values")[0];

   

    for (var loop = 0; loop < details.childNodes.length; loop++) {

     var detail = details.childNodes[loop];

    var prp = detail.getElementsByTagName("PRP")[0].childNodes[0].nodeValue;

 

     document.getElementById(prp+"_TR").style.display='';

    }

}

function validate_AssortMixreturn(){

   var frm_elements = document.forms['0'].elements;

  

   var isSelected = true;

 for(i=0; i<frm_elements.length; i++) {

  field_type = frm_elements[i].type.toLowerCase();

  

  if(field_type=='text'){

   var fdid= frm_elements[i].id;

   fdid = fdid.replace('_1','_TR')

   var styl =  document.getElementById(fdid).style.display;

 

   if(styl!='none'){

      var vlu= frm_elements[i].value;

      if(vlu==''){

          isSelected=false;

          break;

      }

   }

  }

 

  if(field_type=='select-one'){

   var fdid= frm_elements[i].id;

   fdid = fdid.replace('_1','_TR')

   var styl =  document.getElementById(fdid).style.display;

  

   if(styl!='none'){

       var vlu= frm_elements[i].value;

       if(vlu=='0'){

          isSelected=false;

           break;

      }}

  }

  }

 

  if(!isSelected){

      alert("Please select All field");

     

  }

  return isSelected;

}
function calCullateMix(obj,ttlMtId,ttlId,fldId,typ,frm){
      var ttlMtVal = document.getElementById(ttlMtId).innerHTML;
      if(ttlMtVal=='')
     ttlMtVal = 0;
     ttlMtVal = new Number(ttlMtVal);
    
  
    
      
    var frm_elements = document.forms[frm].elements; 
    var calTtl=0;
     for(i=0; i<frm_elements.length; i++) {
     field_type = frm_elements[i].type.toLowerCase();
     if(field_type=='text') {
     var fieldId = frm_elements[i].id;
     if(fieldId.indexOf(fldId)!=-1){
     var val = frm_elements[i].value;
     val = val.trim();
     if(val=='')
     val=0;
     if(typ=='F'){
     calTtl = parseFloat(calTtl)+parseFloat(val);
     calTtl=(new Number(calTtl)).toFixed(2);
     }
     }
     }
    }
  
     if(calTtl > ttlMtVal){

   var curVal = new Number(obj.value);
    var remain = (new Number(ttlMtVal -(calTtl-curVal))).toFixed(2);
        alert("You can't enter more then :-"+ttlMtVal+" Remaining Carat :-"+remain);
        obj.value='';
    }else{
     document.getElementById(ttlId).innerHTML=calTtl;
    }
    
}


function verifedTtlMIX(){
     var ttlMtVal = document.getElementById('issCts').innerHTML;
      if(ttlMtVal=='')
     ttlMtVal = 0;
     ttlMtVal = parseFloat(ttlMtVal,10);
     var  ttlVal = document.getElementById('rtnCts').innerHTML;
     if(ttlVal=='')
     ttlVal = 0;
     ttlVal =parseFloat(ttlVal,10);
       
  
   if(ttlVal>=ttlMtVal){
        var r = confirm("Do you want to save Changes?");
       return r;
   }else{
   alert("Varification failed : Total Carat="+ttlMtVal+" and Calculated Carat="+ttlVal );
       return false;
   }
}

function getvalidPrcEmp(obj){
       var prcIdn = document.getElementById(obj).value;
       var allow = document.getElementById("allow_valid_prc").value;
       if(allow=="Y"){
       var url = "ajaxAssortAction.do?method=getvalidPrcEmp&prcIdn="+prcIdn ;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesvalidPrcEmp(req.responseXML,prcIdn);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }
         
}
 
 function parseMessagesvalidPrcEmp(responseXML , prcIdn){
     var columnDrop = document.getElementById('empIdn');
    
      removeAllOptions(columnDrop);
      var newOption = new Option();
      newOption = new Option();
      newOption.text = "---Selet---";
      newOption.value = "0";
      columnDrop.options[0] = newOption;
       var columns = responseXML.getElementsByTagName("prcs")[0];
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
       
           var selectIndex = loop+1;       
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("emp")[0];
           var lkey = columnroot.getElementsByTagName("emp_idn")[0];
           
            
             newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[selectIndex] = newOption;
       }
 }
 
 
function verifyRelationCur(){
var rlnIdn = document.getElementById('rlnIdn').value;
var trfrlnId = document.getElementById('rlnId').value;
var currency = document.getElementById('currency').value;
if(trfrlnId!=0){
var url = "ajaxsrchAction.do?method=verifyrelCur&rlnIdn="+rlnIdn+"&trfrlnId="+trfrlnId+"&currency="+currency ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesverifyRelationCur(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
    alert("Please Select terms");
    return false;
}
}
function parseMessagesverifyRelationCur(responseXML){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='No'){
alert("Current Memo Will Transfer With in Same Currency");
return false;
}else{
    if(validate_selection('checkbox','0','cb_memo')){
    document.getElementById('trans').value='MEMO';
    document.forms[0].submit();
    }else{
    alert("Transfer Failed..");
    return false;
    }
}
}
function verifyRelationCurPacket(transTyp){
var rlnIdn = document.getElementById('rlnIdn').value;
var trfrlnId = document.getElementById('frlnId').value;
var currency = document.getElementById('currency').value;
if(trfrlnId!=0){
var url = "ajaxsrchAction.do?method=verifyrelCur&rlnIdn="+rlnIdn+"&trfrlnId="+trfrlnId+"&currency="+currency ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesverifyRelationCurPacket(req.responseXML,transTyp);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
    alert("Please Select terms");
    return false;
}
}
function parseMessagesverifyRelationCurPacket(responseXML,transTyp){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
if(msg=='No'){
alert("Current Memo Will Transfer With in Same Currency");
return false;
}else{
    document.getElementById('trans').value=transTyp;
    if(validate_selection('checkbox','0','cb_memo')){
    document.forms[0].submit();
    }else{
    alert("Transfer Failed..");
    return false;
    }
}
}

function VerifiedVNMTransfer(){
    var vnm = document.getElementById('vnm').value;
    var rtnVal=true;
    if(vnm==''){
        alert("Plese specify Pakets for Transfer");
        rtnVal=false;
    }else{
        var ischecked=validate_selection('checkbox','0','cb_memo')
        if(!ischecked){
            alert("Please select Memo..");
             rtnVal=false;
        }
        
    }
    
    return rtnVal;
}
function SetPartyList(obj,typ){
    var val = obj.value;
    var url = "paymentTransAction.do?method=PartyList&VIA="+val+"&TYP="+typ ;
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesSetPartyList(req.responseXML,typ,val);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
}

function parseMessagesSetPartyList(responseXML,typ,val){
   var details = responseXML.getElementsByTagName("NMES")[0];

   
  var curXrt = document.getElementById('curXrt').value;
    if(details!=null){
   var str = "<table cellpadding=\"1\" cellspacing=\"1\"><tr><td><table cellpadding=\"1\" cellspacing=\"1\"><tr><td> <span class=\"redLabel\"> Avg Xrt : <label id=\""+typ+"_XRTAVG\">"+curXrt+"</label> </span></td> <td>,</td><td> <span class=\"redLabel\"> Total Amount($) : <label id=\""+typ+"_CURTTL\">0.00</label> </span></td><td>,</td><td> <span class=\"redLabel\">Total Amount(INR) :<label id=\""+typ+"_AMTTTL\">0.00</label></span></td><td>,</td><td><input type=\"button\" name=\"apply\" id=\"apply\" style=\"submit\" value=\"Apply\" onclick=\"ApplyAmount('"+typ+"')\"/></td></tr></table> </td></tr>"+ 
   "<tr><td ><input type=\"hidden\" id=\""+typ+"BYCNT\" name=\""+typ+"BYCNT\" value=\""+details.childNodes.length+"\" /> </td></tr>" +
   "<tr><td><table class=\"Orange\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Name</th>";
  if(val=='T'){
    str=str+"<th class=\"Orangeth\" align=\"center\">Ref Idn</th>";
     str=str+"<th class=\"Orangeth\" align=\"center\">Ref Typ</th>";
    str=str+"<th class=\"Orangeth\" align=\"center\">Xrt</th>";

  }
      str=str+"<th class=\"Orangeth\" align=\"center\">Balance($)</th>";
   str=str+"<th class=\"Orangeth\" align=\"center\">Balance (INR)</th>" ;
  
     str=str+"<th class=\"Orangeth\" align=\"center\">Xrt</th><th class=\"Orangeth\" align=\"center\">Amount ($)</th>";
     str=str+"<th class=\"Orangeth\" align=\"center\">Amount(INR)</th>";
   
     if(val!='T'){
      str=str+"<th class=\"Orangeth\" align=\"center\">Charge($)</th>";
   str=str+"<th class=\"Orangeth\" align=\"center\">Charge (INR)</th>" ;
  
     }
  
       str=str+"</tr>" ;
  
    for (var loop = 0; loop < details.childNodes.length; loop++) {
      var detail = details.childNodes[loop];
      var cnt = loop+1;
      var idn = detail.getElementsByTagName("IDN")[0].childNodes[0].nodeValue;
      var dsc = unescape(detail.getElementsByTagName("DSC")[0].childNodes[0].nodeValue);
      var amt = detail.getElementsByTagName("AMT")[0].childNodes[0].nodeValue;
      var refIdn = detail.getElementsByTagName("REFIDN")[0].childNodes[0].nodeValue;
      var refTyp = detail.getElementsByTagName("REFTYP")[0].childNodes[0].nodeValue;
       var xrt = detail.getElementsByTagName("XRT")[0].childNodes[0].nodeValue;
         var cur = detail.getElementsByTagName("CUR")[0].childNodes[0].nodeValue;
       str = str+ "<tr><td align=\"center\">"+dsc+"  <input type=\"hidden\" name=\""+typ+"BYIDN_"+cnt+"\" id=\""+typ+"BYIDN_"+cnt+"\" value=\""+idn+"\" /></td> " ;
       if(val=='T'){
      str=str+"<td align=\"center\"> <a href=\"receviceInvAction.do?method=PktDtl&refIdn="+refIdn+"&TYP="+refTyp+" id=\"LNK_"+refIdn+" onclick=\"loadASSFNLPop('LNK_"+refIdn+"')\"  target=\"SC\" > "+refIdn+"  <input type=\"hidden\" name=\""+typ+"REFIDN_"+cnt+"\" id=\""+typ+"REFIDN_"+cnt+"\" value=\""+refIdn+"\" /></td>";
      str=str+"<td align=\"center\">"+refTyp+"</td><td align=\"center\">"+xrt+" </td>";
      
     }
        str=str+"<td align=\"center\">"+cur+" </td>";
        str=str+"<td align=\"right\">"+amt+"</td> ";
        str=str+ "<td align=\"center\">  <input type=\"text\" name=\""+typ+"BYXRT_"+cnt+"\" id=\""+typ+"BYXRT_"+cnt+"\" size=\"5\" onchange=\"CalCurORAmount('"+cnt+"','"+typ+"','XRT');CalTtlAmount('"+typ+"')\" value=\""+curXrt+"\" /></td>";
        str=str+ "<td align=\"center\">  <input type=\"text\" name=\""+typ+"BYCUR_"+cnt+"\" id=\""+typ+"BYCUR_"+cnt+"\" size=\"10\" onchange=\"CalCurORAmount('"+cnt+"','"+typ+"','CUR');CalTtlAmount('"+typ+"')\" /></td>";
        str=str+"<td align=\"center\">  <input type=\"text\" name=\""+typ+"BYAMT_"+cnt+"\" id=\""+typ+"BYAMT_"+cnt+"\" size=\"12\" onchange=\"CalCurORAmount('"+cnt+"','"+typ+"','AMT');CalTtlAmount('"+typ+"')\" /></td>";
    if(val!='T'){
      str=str+"<td align=\"center\"><input type=\"text\" name=\""+typ+"CHCUR_"+cnt+"\" id=\""+typ+"CHCUR_"+cnt+"\" onchange=\"CalChargeCurORAmount('"+cnt+"','"+typ+"','CUR');CalTtlAmount('"+typ+"')\" size=\"10\"  /></td>";
      str=str+"<td align=\"center\"><input type=\"text\" name=\""+typ+"CHAMT_"+cnt+"\" id=\""+typ+"CHAMT_"+cnt+"\" onchange=\"CalChargeCurORAmount('"+cnt+"','"+typ+"','AMT');CalTtlAmount('"+typ+"')\" size=\"10\" /></td>" ;
     }
  
        
        str=str+"</tr>";
       }
       str = str+"</table></td></tr></table>";
       document.getElementById(typ+'BY').innerHTML=str;
   }else{
       document.getElementById(typ+'BY').innerHTML="";
   }
}

function CalCurORAmount(cnt,typ,amtTyp){
  var cur= jsnvl(document.getElementById(typ+"BYCUR_"+cnt).value);
  var amt= jsnvl(document.getElementById(typ+"BYAMT_"+cnt).value);
  var xrt= jsnvl(document.getElementById(typ+"BYXRT_"+cnt).value);
 
  if(amtTyp=='CUR'){
      var calAmt = parseFloat(cur*xrt);
      document.getElementById(typ+"BYAMT_"+cnt).value=(new Number(calAmt)).toFixed(2);
  }else if(amtTyp=='AMT'){
   var calcur = parseFloat(amt/xrt);
   document.getElementById(typ+"BYCUR_"+cnt).value=(new Number(calcur)).toFixed(2);
  }else{
       calAmt = parseFloat(cur*xrt);
       document.getElementById(typ+"BYAMT_"+cnt).value=(new Number(calAmt)).toFixed(2);
       var chCur = document.getElementById(typ+"CHCUR_"+cnt);
       if(chCur!=null){
           var chcur= jsnvl(document.getElementById(typ+"CHCUR_"+cnt).value);
           calAmt = parseFloat(chcur*xrt);
         document.getElementById(typ+"CHAMT_"+cnt).value=(new Number(calAmt)).toFixed(2);
       }
  }

}

function CalChargeCurORAmount(cnt,typ,amtTyp){
  var cur= jsnvl(document.getElementById(typ+"CHCUR_"+cnt).value);
  var amt= jsnvl(document.getElementById(typ+"CHAMT_"+cnt).value);
  var xrt= jsnvl(document.getElementById(typ+"BYXRT_"+cnt).value);
 
  if(amtTyp=='CUR'){
      var calAmt = parseFloat(cur*xrt);
      document.getElementById(typ+"CHAMT_"+cnt).value=(new Number(calAmt)).toFixed(2);
  }else if(amtTyp=='AMT'){
   var calcur = parseFloat(amt/xrt);
   document.getElementById(typ+"CHCUR_"+cnt).value=(new Number(calcur)).toFixed(2);
  }

}
function CalTtlAmount(typ){
     var frm_elements = document.forms['1'].elements; 
    var calAmtTtl=0;
    var calCurTtl=0;
    var calXrtAvg=0
     for(i=0; i<frm_elements.length; i++) {
     field_type = frm_elements[i].type.toLowerCase();
     if(field_type=='text') {
     var fieldId = frm_elements[i].id;
    
     if(fieldId.indexOf(typ+"BYAMT_")!=-1){
     var val = frm_elements[i].value;
     val = val.trim();
   
     if(val=='')
     val=0;
     calAmtTtl = parseFloat(calAmtTtl)+parseFloat(val);
     
      }
       if(fieldId.indexOf(typ+"CHAMT_")!=-1){
     var val = frm_elements[i].value;
     val = val.trim();
   
     if(val=='')
     val=0;
     calAmtTtl = parseFloat(calAmtTtl)+parseFloat(val);
     
      }
      if(fieldId.indexOf(typ+"BYCUR_")!=-1){
     var curval = frm_elements[i].value;
     curval = curval.trim();
   
     if(curval=='')
     curval=0;
     calCurTtl = parseFloat(calCurTtl)+parseFloat(curval);
     
      }
      if(fieldId.indexOf(typ+"CHCUR_")!=-1){
     var curval = frm_elements[i].value;
     curval = curval.trim();
   
     if(curval=='')
     curval=0;
     calCurTtl = parseFloat(calCurTtl)+parseFloat(curval);
     
      }
     }
    }
    calXrtAvg = calAmtTtl/calCurTtl ;
     calCurTtl=(new Number(calCurTtl)).toFixed(2);
     calAmtTtl=(new Number(calAmtTtl)).toFixed(2);
     calXrtAvg=(new Number(calXrtAvg)).toFixed(2);
     document.getElementById(typ+"_AMTTTL").innerHTML=calAmtTtl;
     document.getElementById(typ+"_CURTTL").innerHTML=calCurTtl;
     document.getElementById(typ+"_XRTAVG").innerHTML=calXrtAvg;
}

function SetBuyerList(obj){
    var val = obj.value;
    var url = "paymentTransAction.do?method=BuyerList&TYP="+val ;
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesSetBuyerList(req.responseXML);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
}

function parseMessagesSetBuyerList(responseXML){
  var columnDrop = document.getElementById('byrIdn');
      removeAllOptions(columnDrop);
       var columns = responseXML.getElementsByTagName("NMES")[0];
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("IDN")[0];
           var lval = columnroot.getElementsByTagName("DSC")[0];
           
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;;
             columnDrop.options[loop] = newOption;
       }
}   

function getStatusbyGrp(grp,styleid){
document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById('sttLst').value='';
var tab = document.getElementById ("all_grp"); // get table  id  which contain check box
var elems = tab.getElementsByTagName ("input");
var all_grp='';
for ( var i = 0; i < elems.length; i++ ){
if ( elems[i].type =="checkbox"){
var fieldId = elems[i].id;
if (document.getElementById(fieldId).checked ==true){
var fldval=document.getElementById(fieldId).value;
          if(all_grp ==''){
             all_grp=fldval;
          }else{
          all_grp=all_grp+","+fldval;
          }
}
}
}
    if(all_grp!=''){
    var url = "discover.do?method=getStatusbyGrp&grp="+all_grp;
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesgetStatusbyGrp(req.responseXML,grp);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
    }else{
        document.getElementById('loading').innerHTML = "";
    }
}

function getStatusbyGrpQuick(grp,styleid){
document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
document.getElementById('sttLst').value='';
var tab = document.getElementById ("all_grp"); // get table  id  which contain check box
var elems = tab.getElementsByTagName ("input");
var all_grp='';
for ( var i = 0; i < elems.length; i++ ){
if ( elems[i].type =="checkbox"){
var fieldId = elems[i].id;
if (document.getElementById(fieldId).checked ==true){
var fldval=document.getElementById(fieldId).value;
          if(all_grp ==''){
             all_grp=fldval;
          }else{
          all_grp=all_grp+","+fldval;
          }
}
}
}
    if(all_grp!=''){
    var url = "discoverReport.do?method=getStatusbyGrp&grp="+all_grp;
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesgetStatusbyGrp(req.responseXML,grp);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
    }else{
        document.getElementById('loading').innerHTML = "";
    }
}

function parseMessagesgetStatusbyGrp(responseXML,grp){
    var details = responseXML.getElementsByTagName("details")[0];
    if(details!=null){
    var sttLst=document.getElementById('sttLst').value;
    for (var loop = 0; loop < details.childNodes.length; loop++) {
      var detail = details.childNodes[loop];
      var stt = unescape(detail.getElementsByTagName("stt")[0].childNodes[0].nodeValue);
          if(sttLst ==''){
             sttLst=stt;
          }else{
          sttLst=sttLst+","+stt;
          }
    }
          document.getElementById('sttLst').value=sttLst;
    }
    document.getElementById('loading').innerHTML = "";
}


function getNmeRelExcl(fld){
var fldval = document.getElementById(fld).value;
if(fldval!='' && fldval!='0'){
var url = "nmerel.do?method=getNmeRelExcl&fld="+fld+"&fldval="+fldval ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesgetNmeRelExcl(req.responseXML,fld);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}else{
    document.getElementById(fld+'_EXCL').innerHTML='';
}
}
function parseMessagesgetNmeRelExcl(responseXML,fld){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
document.getElementById(fld+'_EXCL').innerHTML=msg;
}

function SetBOXID(obj,boxId){
    var val = obj.value;
    var url = "mixAktAjaxAction.do?method=BoxIdns&boxTyp="+val ;
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesSetBoxIDList(req.responseXML,boxId);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
}

function parseMessagesSetBoxIDList(responseXML,boxId){

  var columnDrop = document.getElementById(boxId);
      removeAllOptions(columnDrop);
      var cnt=0
      if(boxId=='boxId'){
           var newOption = new Option();
             newOption.text = "---select---";
             newOption.value = "0";
             columnDrop.options[0] = newOption;
        cnt=1;
      }
       var columns = responseXML.getElementsByTagName("mnme")[0];
     
       for (var loop = cnt; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nmeid")[0];
           var lval = columnroot.getElementsByTagName("nme")[0];
           
             newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop] = newOption;
       }
       if(boxId=='box_id'){
       var optn =columnDrop.options;
       var optnlength =columnDrop.options.length;
       var frm_elements = document.forms[1].elements;
       for(i=0; i<frm_elements.length; i++) {
       field_type = frm_elements[i].type.toLowerCase();
        if(field_type=='select-one') {
            var fieldId = frm_elements[i].id;
           if(fieldId.indexOf('box_id_')!=-1){
                 for(j=0;j<optnlength;j++) {
                            var opt = optn[j];
                            newOption = new Option();
                              newOption.text = opt.text;
                             newOption.value = opt.value;
                            frm_elements[i].options[j] = newOption;
         }}
}}}
       
}   
function getNmeRelExclALL(getNmeRelExclALL){
getNmeRelExcl('cur');
getNmeRelExcl('trms_idn');
}


function selectRecheck(){
    var url = "ajaxLabAction.do?method=selectRecheck";
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesselectRecheck(req.responseXML);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
}

function parseMessagesselectRecheck(responseXML){
    var details = responseXML.getElementsByTagName("details")[0];
    if(details!=null){
    for (var loop = 0; loop < details.childNodes.length; loop++) {
      var detail = details.childNodes[loop];
      var stkidn = (detail.getElementsByTagName("stkidn")[0].childNodes[0].nodeValue);
      if(document.getElementById('RS_'+stkidn).checked || document.getElementById('RI_'+stkidn).checked){
      document.getElementById('RI_'+stkidn).checked=true;
      }
    }
   }
}


function selectRtnPrppkt(){
    var url = "pricertn.do?method=selectRtnPrppkt";
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesselectRtnPrppkt(req.responseXML);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
}

function parseMessagesselectRtnPrppkt(responseXML){
    var details = responseXML.getElementsByTagName("details")[0];
    if(details!=null){
    for (var loop = 0; loop < details.childNodes.length; loop++) {
      var detail = details.childNodes[loop];
      var stkidn = (detail.getElementsByTagName("stkidn")[0].childNodes[0].nodeValue);
      document.getElementById('TD_'+stkidn).style.backgroundColor="#FFC977";
    }
   }
}

function selectRtnPrppktFinalAsrt(){
    var url = "assortFinalRtn.do?method=selectRtnPrppktFinalAsrt";
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesselectRtnPrppktFinalAsrt(req.responseXML);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
}

function parseMessagesselectRtnPrppktFinalAsrt(responseXML){
    var details = responseXML.getElementsByTagName("details")[0];
    if(details!=null){
    for (var loop = 0; loop < details.childNodes.length; loop++) {
      var detail = details.childNodes[loop];
      var stkidn = (detail.getElementsByTagName("stkidn")[0].childNodes[0].nodeValue);
      document.getElementById('TD_'+stkidn).style.backgroundColor="#FFC977";
    }
   }
}

function selectRtnPrppktAsrtRtn(){
    var url = "assortReturn.do?method=selectRtnPrppktAsrtRtn";
    var req = initRequest();
     req.onreadystatechange = function() {
       if (req.readyState == 4) {
           if (req.status == 200) {
              parseMessagesselectRtnPrppktAsrtRtn(req.responseXML);
        } else if (req.status == 204){
                }
         }
    };
      req.open("GET", url, true);
      req.send(null);
}

function parseMessagesselectRtnPrppktAsrtRtn(responseXML){
    var details = responseXML.getElementsByTagName("details")[0];
    if(details!=null){
    for (var loop = 0; loop < details.childNodes.length; loop++) {
      var detail = details.childNodes[loop];
      var stkidn = (detail.getElementsByTagName("stkidn")[0].childNodes[0].nodeValue);
      document.getElementById('TD_'+stkidn).style.backgroundColor="#FFC977";
    }
   }
}

function  StockPrpUpdate(obj,lprp,grp,typ) {
         var lab = document.getElementById('lab_'+grp).value;
         var mstkIdn =document.getElementById('mstkIdn').value;
         var issueId = document.getElementById('issueId').value;
          var prpVal = obj.value;
           lprp=lprp.replace('&', '%26'); 
           lprp = lprp.replace('+', '%2B'); 
           lprp = lprp.replace('-', '%2D');
          if(typ!='D'){
            prpVal=prpVal.replace('&', '%26'); 
            prpVal = prpVal.replace('+', '%2B'); 
            prpVal = prpVal.replace('-', '%2D');
          }
           var url = "ajaxsrchAction.do?method=stockUpd&lprp="+lprp+"&lprpVal="+prpVal+"&lab="+lab+"&stkIdn="+mstkIdn+"&issueId="+issueId;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
         
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesStockPrpUpdate(req.responseXML,lprp,grp);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       
   }
   
  
  function parseMessagesStockPrpUpdate(responseXML,lprp,grp) {
    //alert('@parseMessage '+ fld);
    var msgDtl = responseXML.getElementsByTagName("msg")[0];
    var msg = msgDtl.childNodes[0].nodeValue;
      document.getElementById('msgLbl').innerHTML=msg;
   
    }
    
    
    function  AssortPrpUpdate(obj,lprp,typ,isRepair) {
         var issueId = document.getElementById('issIdn').value;
         var mstkIdn = document.getElementById('mstkIdn').value;
          var prpVal = obj.value;
          if(typ!='D'){
            prpVal=prpVal.replace('&', '%26'); 
            prpVal = prpVal.replace('+', '%2B'); 
            prpVal = prpVal.replace('-', '%2D');
          }
           var url = "ajaxAssortAction.do?method=prpUpd&prp="+lprp+"&prpVal="+prpVal+"&mstkIdn="+mstkIdn+"&issIdn="+issueId+"&isRepair="+isRepair;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
         
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesAssortPrpUpdate(req.responseXML,lprp);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       
   }
   
  
  function parseMessagesAssortPrpUpdate(responseXML,lprp) {
    //alert('@parseMessage '+ fld);
    var msgDtl = responseXML.getElementsByTagName("msg")[0];
    var msg = msgDtl.childNodes[0].nodeValue;
    if(msg=='SUCCESS'){
      document.getElementById(lprp+'_Lbl').style.display='';
      }
   
    }
function setDailyDlvParam(fieldId) {
var fieldval = document.getElementById(fieldId).value;
var chk=document.getElementById(fieldId).checked;
      var url = "dailyDlvReport.do?method=setDailyDlvParam&nmeidnDte="+fieldval+"&chk="+chk;
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                   document.getElementById('btns').style.display='';
                   document.getElementById('btns').style.display='';
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
return true;
}

function  WebUserRole(obj,usrIdn,roleIdn) {
     var stt=obj.checked;
     var url = "webUserReport.do?method=saveUSRROLE&stt="+stt+"&usrIdn="+usrIdn+"&roleIdn="+roleIdn;
     var req = initRequest();
      req.onreadystatechange = function() {    
           if (req.readyState == 4) {
                   if (req.status == 200) {
                    parseMessagesWebUserRole(req.responseXML,obj);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       
   }
   
  
  function parseMessagesWebUserRole(responseXML,obj) {
    //alert('@parseMessage '+ fld);
    var msgDtl = responseXML.getElementsByTagName("msg")[0];
    var msg = msgDtl.childNodes[0].nodeValue;
      document.getElementById('msgLbl').innerHTML=msg;
      if(msg.indexOf('Failed')!=-1){
          obj.checked=false;
      }
   
    }
    
    function saveStockCriteria(fldNmeobj,crt_idn) {
           var fldVal = fldNmeobj.value;
           var url = "ajaxsrchAction.do?method=saveStockCriteria&stt="+fldVal+"&crt_idn="+crt_idn ;
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       
   }
   
   function  getgrpcompanyLink(obj,lprp,grp,typ) {
       var byrId = obj.options[obj.selectedIndex].value;
       if(byrId!=0 && byrId!=''){
           var url = "ajaxsrchAction.do?method=getgrpcompanyLink&byrId="+byrId;
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesgetgrpcompanyLink(req.responseXML);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
       }
   }
   
  
  function parseMessagesgetgrpcompanyLink(responseXML) {
    var msgDtl = responseXML.getElementsByTagName("msg")[0];
    var msg = unescape(msgDtl.childNodes[0].nodeValue);
    document.getElementById('grpcmpny').innerHTML=msg;
    }
    
function EmptyBox(){
document.getElementById('lotno').value="";
}
function LotGenrate(){
 var usrLot = document.getElementById('lotno').value;
    var url = "mixManufacturingAction.do?method=LotIdnGen";
    if(usrLot!='')
    url=url+"&usrLot="+usrLot;
       var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesLotGenrate(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null); 

}

function parseMessagesLotGenrate(responseXML){
  var columns = responseXML.getElementsByTagName("lots")[0];
  var columnroot = columns.childNodes[0];
  var lotTgNo = columnroot.getElementsByTagName("lotno")[0];
  var lotTgIdn = columnroot.getElementsByTagName("lotIdn")[0];
  var lotNo=  lotTgNo.childNodes[0].nodeValue;
  var lotIdn = lotTgIdn.childNodes[0].nodeValue;
  document.getElementById('lotno').value=lotNo;
  document.getElementById('lotIdn').value=lotIdn;
}

function uploadPkt(obj){
    var noPkt = document.getElementById('pktNo').value;
   
    if(noPkt!=''){
   $.post("mixUploadPkt.jsp", {'noPkt':noPkt}, function(data){
                $("#pktgrid").html(data); //append received data into the element
    }).fail(function(xhr, ajaxOptions, thrownError) { //any errors?
					
        alert("error in loading"); //alert with HTTP error
					
				
    });
    }else{
        alert("Please specify No of Packets or Lot No")
    }
}

function getPrpvaluesmatchPair(obj,idn){
document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
var lprp = obj.options[obj.options.selectedIndex].value;
var url = "ajaxsrchAction.do?method=loadprpmatch&lprp="+lprp ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesPrpvaluesmatchPair(req.responseXML,lprp,idn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagesPrpvaluesmatchPair(responseXML , lprp,idn){
var columnDrop = document.getElementById(idn+'val_frmC');
removeAllOptions(columnDrop);
var columnDropto = document.getElementById(idn+'val_toC');
removeAllOptions(columnDropto);
var newOption = new Option();
newOption = new Option();
newOption.text = "---Selet---";
newOption.value = "";
columnDrop.options[0] = newOption;
newOption = new Option();
newOption.text = "---Selet---";
newOption.value = "";
columnDropto.options[0] = newOption;
var columns = responseXML.getElementsByTagName("prps")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("prpVal")[0]).childNodes[0].nodeValue);
var lkey = unescape((columnroot.getElementsByTagName("prpValD")[0]).childNodes[0].nodeValue);
if(loop==0){
if(lval=='NT'){
document.getElementById(idn+'val_frmC').style.display='none';
document.getElementById(idn+'val_toC').style.display='none';
document.getElementById(idn+'val_frm').style.display='';
document.getElementById(idn+'val_to').style.display='';
}else{
document.getElementById(idn+'val_frm').value='';
document.getElementById(idn+'val_to').value='';
document.getElementById(idn+'val_frm').style.display='none';
document.getElementById(idn+'val_to').style.display='none';
document.getElementById(idn+'val_frmC').style.display='';
document.getElementById(idn+'val_toC').style.display='';
}
}else{
var selectIndex = loop;
newOption = new Option();
newOption.text = lval;
newOption.value = lkey;
columnDrop.options[selectIndex] = newOption;
var selectIndexto = loop;
newOption = new Option();
newOption.text = lval;
newOption.value = lkey;
columnDropto.options[selectIndexto] = newOption;
}
}
document.getElementById('loading').innerHTML ="";
}

function SaveMfgLot(mstkIdn,memoId){
 
     var mfgLot = document.getElementById('mfg_lot_no').value;
     if(mfgLot!=''){
         var url = "roughClosureAction.do?method=SaveLotNo&mfgLotNo="+mfgLot+"&mstkIdn="+mstkIdn+"&memoId="+memoId ;
         var req = initRequest();
        req.onreadystatechange = function() {
           if (req.readyState == 4) {
            if (req.status == 200) {
               parseMessagesSaveMfgLot(req.responseXML);
            } else if (req.status == 204){
              }
            }
               };
             req.open("GET", url, true);
             req.send(null);
         
     }else{
         alert("Please Specify Mfg Lot No.");
     }
 }

function parseMessagesSaveMfgLot(responseXML){
    var columns = responseXML.getElementsByTagName("idndt")[0];
    for (var loop = 0; loop < columns.childNodes.length; loop++) {
    var columnroot = columns.childNodes[loop];
    var idn = columnroot.getElementsByTagName("idn")[0];
    var message = columnroot.getElementsByTagName("message")[0];
    var mlotidnVal = idn.childNodes[0].nodeValue;
    var messageVal = message.childNodes[0].nodeValue;
    if(messageVal='SUCESS'){
    var mstkIdn = document.getElementById('mstkIdn').value;
    var memoId = document.getElementById('memoId').value;
    var mfg_lot_no = document.getElementById('mfg_lot_no').value;
    document.getElementById('mlotidnVal').value=mlotidnVal;
    showHidDiv('pktDtl');
    frameOpen('FRAME','/rough/roughClosureAction.do?method=loadPkt&mstkIdn='+mstkIdn+'&memoId='+memoId+'&MFGLOTNO='+mfg_lot_no);
    }else{
        alert('Lot Not Created');
    }
    }
}


function contactGstSave(nmeIdn,objId) {
var answer = confirm("Are you sure to Save Goods and Services Tax Identification Number (GSTIN): ?")
if (answer){
var gstin = document.getElementById(objId).value;
document.getElementById('loadinggst').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
var url = "advcontact.do?method=contactGstSave&nmeIdn="+nmeIdn+"&gstin="+gstin;
var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescontactGstSave(req.responseXML);
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);
}
return false; 
}

function parseMessagescontactGstSave(responseXML){
var message = responseXML.getElementsByTagName("message")[0];
var msg = message.childNodes[0].nodeValue;
document.getElementById('loadinggst').innerHTML = "<span class=\"redLabel\">"+msg+"</span>";
}


function getPrcIssueIdn(obj,issueId){
    var prcId =obj.value;
     if(prcId!=''){
         var url = "roughReturnAction.do?method=prcIssue&prcId="+prcId ;
         var req = initRequest();
        req.onreadystatechange = function() {
           if (req.readyState == 4) {
            if (req.status == 200) {
               parseMessagesPrcIssueIdn(req.responseXML,issueId);
            } else if (req.status == 204){
              }
            }
               };
             req.open("GET", url, true);
             req.send(null);
         
     }
}

function parseMessagesPrcIssueIdn(responseXML,issueId){

      var columnDrop = document.getElementById(issueId);
      removeAllOptions(columnDrop);
      var newOption = new Option();
      newOption = new Option();
      newOption.text = "---Selet---";
      newOption.value = "";
      columnDrop.options[0] = newOption;
    var columns = responseXML.getElementsByTagName("issIds")[0];
     for (var loop = 0; loop < columns.childNodes.length; loop++) {
            var columnroot = columns.childNodes[loop];
            var issId = columnroot.getElementsByTagName("id")[0];
           
             newOption = new Option();
             newOption.text = unescape(issId.childNodes[0].nodeValue);
             newOption.value = issId.childNodes[0].nodeValue;
             columnDrop.options[loop+1] = newOption;
       }
       
}

function calRteOnPur(obj,purIdn,purDtlIdn,fldId) {

var purRte = obj.value;
var url = "purchaseDtlAction.do?method=mpurDtl&purIdn="+purIdn+"&purDtlIdn="+purDtlIdn+"&purRte="+purRte;
var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescalRteOnPur(req.responseXML,fldId);
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);

return false; 
}

function calRteForNewOnPur(obj,purIdn,fldId) {

var purRte = obj.value;
var url = "purchaseDtlAction.do?method=mpurforNewDtl&purIdn="+purIdn+"&purRte="+purRte;
var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescalRteOnPur(req.responseXML,fldId);
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);

return false; 
}



function parseMessagescalRteOnPur(responseXML,fldId){
var rteVal = responseXML.getElementsByTagName("RTE")[0];
var rte = rteVal.childNodes[0].nodeValue;
rte = parseFloat(rte);
var cts = jsnvl(document.getElementById('cts_'+fldId).value,'0'); 
var rap = jsnvl(document.getElementById('rap_'+fldId).value,'0');
var vlu = rte * cts;
var rapvlu = rap * cts;

dis="";
if(rap!='0'){
   dis =  ((vlu*(100/rapvlu))-100);
   dis = new Number(dis).toFixed(2);
}
document.getElementById('rte_'+fldId).value = new Number(rte).toFixed(2);;
document.getElementById('dis_'+fldId).value =dis;
document.getElementById('vlu_'+fldId).value =Math.round(vlu);

}





function saveEmpRemark(remarkId) {

var remarkVal = document.getElementById(remarkId).value
var mstkId = document.getElementById('mstkIdn').value
var url = "StockPrpUpd.do?method=saveEmpRemark&remarkVal="+remarkVal+"&mstkIdn="+mstkId;
var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagessaveEmpRemark(req.responseXML,remarkId);
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);

return false; 
}



function parseMessagessaveEmpRemark(responseXML,remarkId){
var rteVal = responseXML.getElementsByTagName("msg")[0];
var cnt = rteVal.childNodes[0].nodeValue;
document.getElementById('remarkLabel').innerHTML = cnt;
}

function updatePlanStatus(ref_idn,plan_seq,issue_id){
   var url = "roughReturnAction.do?method=updatePlanStatus&ref_idn="+ref_idn+"&plan_seq="+plan_seq+"&issue_id="+issue_id;
var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesupdatePlanStatus(req.responseXML);
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);

return false; 
   }
   
   function parseMessagesupdatePlanStatus(responseXML){

}

function calculateCp(purId){

   var url = "roughReturnAction.do?method=calculateCp&purId="+purId;
var req = initRequest();
req.onreadystatechange = function () {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescalculateCp(req.responseXML);
}
else if (req.status == 204) {
}
}
};
req.open("GET", url, true);
req.send(null);

return false; 
   }
   
function parseMessagescalculateCp(responseXML){
var msg = responseXML.getElementsByTagName("Message")[0];
$("#tfrToMkt").show();
alert("Successfully Calculated")
}

function setOrderItems(obj,itemId){
    var orderId = obj.value;
    if(orderId!=''  && orderId!='0'){
        var url = "orderDeliveryAction.do?method=GetItemIdn&orderId="+orderId;
        var req = initRequest();
          req.onreadystatechange = function () {
            if (req.readyState == 4) {
                if (req.status == 200) {
                parseMessagesSetOrderItems(req.responseXML,itemId);
                }
             else if (req.status == 204) {
            }
          }
            };
    req.open("GET", url, true);
       req.send(null);

           return false; 
    }
    
}


function parseMessagesSetOrderItems(responseXML,itemId){

      var columnDrop = document.getElementById(itemId);
      removeAllOptions(columnDrop);
      var newOption = new Option();
     
    var columns = responseXML.getElementsByTagName("itemLsts")[0];
     for (var loop = 0; loop < columns.childNodes.length; loop++) {
            var columnroot = columns.childNodes[loop];
            var itemId = columnroot.getElementsByTagName("itemIdn")[0];
             var itemDsc = columnroot.getElementsByTagName("itemDsc")[0];
             newOption = new Option();
             newOption.text = unescape(itemDsc.childNodes[0].nodeValue);
             newOption.value = itemId.childNodes[0].nodeValue;
             columnDrop.options[loop+1] = newOption;
       }
       
}


function SplitPrpChanges(obj,lprp,issId,stkIdn,dflVal){
var  isValid=true;
   if(lprp=='CRTWT'){
      isValid=IsValidcalCulatespiltVal(obj,dflVal);
    }
    if(isValid){
    var prpVal = obj.value;
     var url = "roughReturnAction.do?method=savePrp&prpVal="+prpVal+"&lprp="+lprp+"&issId="+issId+"&stkIdn="+stkIdn;
        var req = initRequest();
          req.onreadystatechange = function () {
            if (req.readyState == 4) {
                if (req.status == 200) {
                parseMessagesSplitPrpChanges(req.responseXML,lprp,prpVal,stkIdn);
                }
             else if (req.status == 204) {
            }
          }
            };
      req.open("GET", url, true);
       req.send(null);
    
           return false; 
    }
}

function parseMessagesSplitPrpChanges(responseXML,lprp,prpVal,stkIdn){
   var msgTag = responseXML.getElementsByTagName("msg")[0];
   var msg = msgTag.childNodes[0].nodeValue;
    if(msg=="SUCCESS"){
    calCulatespiltVal();
      document.getElementById('prpChgMsg').innerHTML = "<b> Packet "+stkIdn+" Modified "+lprp+" => "+prpVal+" </b>" ;
    }else{
      document.getElementById('prpChgMsg').innerHTML = "<b>Process fail to chnages</b>" ;

    }
    
  
}

function IsValidcalCulatespiltVal(obj,dflVal){
  
   var frm_elements = document.forms[1].elements; 
   var ttlCts=0;
   var ttlRte=0;
   var ttlAmt=0;
   var ttlQty=0;
   for(i=0; i<frm_elements.length; i++) {
   var field_type = frm_elements[i].type.toLowerCase();
   if(field_type=='checkbox') {
    var field_id = frm_elements[i].id;
    if(field_id.indexOf('cb_pkt_')!=-1){
      var stk_idn = frm_elements[i].value;
       var qty = parseInt(nvl(document.getElementById('PN_QTY_'+stk_idn).value,'0'));
      var cts = parseFloat(nvl(document.getElementById('PN_CRTWT_'+stk_idn).value,'0'));
      var rte = parseFloat(nvl(document.getElementById('PN_RTE_'+stk_idn).value,'0'));
      var amt = cts*rte;
      ttlQty=ttlQty+qty;
      ttlCts=ttlCts+cts;
      ttlAmt=ttlAmt+amt;
      
    }
   }}
   alert(ttlCts);
   ttlCts=new Number(ttlCts).toFixed(3);
   var issCts =  parseFloat(nvl(document.getElementById('issCts').innerHTML));
   var rtnObj = document.getElementById('rtnCts');
   var rtnCts=0;
   if(rtnObj!=null)
   rtnCts = parseFloat(nvl(document.getElementById('rtnCts').innerHTML));
   var balCts = new Number((issCts-rtnCts)+0.01).toFixed(3);
     alert(balCts);
   if(ttlCts>balCts){
   obj.value=dflVal;
   alert("Please verified total carat it can't be more then "+balCts);
   return false;
   }else{
     return true;
   }
   
  
   
}

function calCulatespiltVal(){
  
   var frm_elements = document.forms[1].elements; 
   var ttlCts=0;
   var ttlRte=0;
   var ttlAmt=0;
   var ttlQty=0;
   for(i=0; i<frm_elements.length; i++) {
   var field_type = frm_elements[i].type.toLowerCase();
   if(field_type=='checkbox') {
    var field_id = frm_elements[i].id;
    if(field_id.indexOf('cb_pkt_')!=-1){
      var stk_idn = frm_elements[i].value;
       var qty = parseInt(nvl(document.getElementById('PN_QTY_'+stk_idn).value,'0'));
      var cts = parseFloat(nvl(document.getElementById('PN_CRTWT_'+stk_idn).value,'0'));
      var rte = parseFloat(nvl(document.getElementById('PN_RTE_'+stk_idn).value,'0'));
      var amt = cts*rte;
      ttlQty=ttlQty+qty;
      ttlCts=ttlCts+cts;
      ttlAmt=ttlAmt+amt;
      
    }
   }}
   var issCts =  parseFloat(nvl(document.getElementById('issCts').innerHTML,'0'));
   var rtnObj = document.getElementById('rtnCts');
   var rtnCts=0;
   if(rtnObj!=null)
   rtnCts = parseFloat(nvl(document.getElementById('rtnCts').innerHTML,'0'));
   var balCts = new Number(issCts-rtnCts-ttlCts).toFixed(3);
   
    var issQty =  parseFloat(nvl(document.getElementById('issQty').innerHTML,'0'));
    var rtnQtyObj = document.getElementById('rtnQty');
   var rtnQty=0;
   if(rtnQtyObj!=null)
   rtnQty = parseFloat(nvl(document.getElementById('rtnQty').innerHTML,'0'));
    var balQty = (issQty-rtnQty-ttlQty);
    var avgRte =new Number(ttlAmt/ttlCts).toFixed(2);
    ttlAmt = new Number(ttlAmt).toFixed(2);
   ttlCts = new Number(ttlCts).toFixed(3);
   document.getElementById('remqty').innerHTML=balQty;
   document.getElementById('remcts').innerHTML=balCts;
    document.getElementById('pndQty').innerHTML=ttlQty;
   document.getElementById('pndCts').innerHTML=ttlCts;
   document.getElementById('pndRte').innerHTML=avgRte;
   document.getElementById('pndAmt').innerHTML=ttlAmt;
}


function callSimilarPurchase(stkIdn,objId) {
var dis = document.getElementById('BYRTRDIV_'+objId).style.display;
if(dis!=''){
document.getElementById("BYRTRDIV_"+objId).style.display = '';
document.getElementById('BYR_'+objId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";

var url = "purPrice.do?method=similar&stkIdn="+stkIdn;

var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesSimilarPurchase(req.responseXML,objId);
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


 function parseMessagesSimilarPurchase(responseXML, objId) {
var str = "<table class=\"Orange\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><th class=\"Orangeth\" align=\"center\">Purchase ID</th>";
 
str=str+"<th class=\"Orangeth\" align=\"center\">Packet Id</th><th class=\"Orangeth\" align=\"center\">Cts</th><th class=\"Orangeth\" align=\"center\">Rap</th><th class=\"Orangeth\">Rte</th><th class=\"Orangeth\" align=\"center\">Dis</th></tr>";

var purchases = responseXML.getElementsByTagName("purchases")[0];
var validate="N";
for (var loop = 0; loop < purchases.childNodes.length; loop++) {
var purchase = purchases.childNodes[loop];

var pur_idn = purchase.getElementsByTagName("pur_idn")[0];
var ref_idn = purchase.getElementsByTagName("ref_idn")[0];
var cts = purchase.getElementsByTagName("cts")[0];
var rap = purchase.getElementsByTagName("rap")[0];
var rte = purchase.getElementsByTagName("rte")[0];
var dis = purchase.getElementsByTagName("dis")[0];

str = str+"<tr> ";
str = str+"<td align=\"center\">"+pur_idn.childNodes[0].nodeValue+"</td>";
str = str+"<td align=\"center\">"+ref_idn.childNodes[0].nodeValue+"</td>";
str = str+"<td align=\"center\">"+cts.childNodes[0].nodeValue+"</td>";
str = str+"<td align=\"center\">"+rap.childNodes[0].nodeValue+"</td>";
str = str+"<td align=\"center\">"+rte.childNodes[0].nodeValue+"</td>";
str = str+ "<td align=\"center\">"+dis.childNodes[0].nodeValue+"</td></tr>"
validate="Y";
}

str = str+"</table>"
var bryNo = document.getElementById("count").value;
for(var i=0;i < bryNo;i++){
var divId = "BYR_"+i;
if(i==objId){
if(validate=='Y'){
document.getElementById(divId).innerHTML = str;
}else{
   document.getElementById(divId).innerHTML = "Sorry No Result Found"; 
}

}else{
document.getElementById(divId).innerHTML = "";
}
}
}
