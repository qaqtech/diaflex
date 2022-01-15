function initRequest() {
       if (window.XMLHttpRequest) {
           return new XMLHttpRequest();
       } else if (window.ActiveXObject) {
           isIE = true;
           return new ActiveXObject("Microsoft.XMLHTTP");
       }
   }
 
function addInList(obj,stkIdn,listNm){
        var isChecked = obj.checked;
      var url = "ajaxLabAction.do?method=AddList&stkIdn="+stkIdn+"&chk="+isChecked+'&listNme='+listNm;
        
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
   
   function updatePrp(obj , lprp , typ) {
 
   var issIdn = document.getElementById('issIdn').value;
   var mstkIdn = document.getElementById('mstkIdn').value;
   var prpVal = obj.value;
     lprp=lprp.replace('&', '%26'); 
   lprp = lprp.replace('+', '%2B'); 
   lprp = lprp.replace('-', '%2D');
    if(typ!='D'){
  prpVal=prpVal.replace('&', '%26'); 
   prpVal = prpVal.replace('+', '%2B'); 
   prpVal = prpVal.replace('-', '%2D');
   }
    var url = "ajaxAssortAction.do?method=prpUpd&mstkIdn="+mstkIdn+"&issIdn="+issIdn+"&prp="+lprp+"&prpVal="+prpVal ;
      
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesupdatePrp(req.responseXML,lprp);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
   
   }
   
    function RepairPrpUpdate(obj , lprp , typ) {
 
   var issIdn = document.getElementById('issIdn').value;
   var mstkIdn = document.getElementById('mstkIdn').value;
   var prpVal = obj.value;
   if(typ=='S')
   prpVal = obj.options[obj.selectedIndex].value;
   prpVal=prpVal.replace('&', '%26'); 
   prpVal = prpVal.replace('+', '%2B'); 
   prpVal = prpVal.replace('-', '%2D');
    var url = "ajaxAssortAction.do?method=prpUpd&mstkIdn="+mstkIdn+"&issIdn="+issIdn+"&prp="+lprp+"&prpVal="+prpVal+"&isRepair=YES" ;
      
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesupdatePrp(req.responseXML,lprp);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
   
   }
   
   function labResult(stkIdn ,lprp , stt , obj){
     var isChecked = obj.checked;
      var url = "ajaxLabAction.do?method=setRCOB&stkIdn="+stkIdn+"&lprp="+lprp+"&stt="+stt+"&chk="+isChecked;
        
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
 function labResult(stkIdn ,lprp , str){
   
      var url = "ajaxLabAction.do?method=setRI&stkIdn="+stkIdn+"&lprp="+lprp+"&str="+str ;
        
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
  document.getElementById(stkIdn+'_'+lprp).style.display='none';
}
function labResultcertnosave(stkIdn ,lprp , str){
   var certno=document.getElementById(stkIdn+'_'+lprp);
   certno=certno.options[certno.selectedIndex].value;
      var url = "ajaxLabAction.do?method=setRI&stkIdn="+stkIdn+"&lprp="+lprp+"&str="+str+"&certno="+certno ;
        
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

function labResultcertno(stkIdn ,lprp , str,lab){ 
      var url = "ajaxLabAction.do?method=setcertRI&stkIdn="+stkIdn+"&lprp="+lprp+"&str="+str+"&lab="+lab ;
        
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagescertno(req.responseXML,stkIdn ,lprp);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}

function parseMessagescertno(responseXML ,stkIdn ,lprp){
     var columnDrop = document.getElementById(stkIdn+'_'+lprp);
     document.getElementById(stkIdn+'_'+lprp).style.display='';
      removeAllOptions(columnDrop);
      var newOption = new Option();
       newOption = new Option();
             newOption.text = "---Select---";
             newOption.value = "0";
             columnDrop.options[0] = newOption;
       var columns = responseXML.getElementsByTagName("certsnos")[0];
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var selectIndex = loop+1;
           var columnroot = columns.childNodes[loop];
           var lval = columnroot.getElementsByTagName("certval")[0];
           var lkey = columnroot.getElementsByTagName("certkey")[0];
           newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[selectIndex] = newOption;
       }
 }
 function removeAllOptions(selectbox) {
    var i;
    for(i=selectbox.options.length-1;i>=0;i--) {
        selectbox.remove(i);
    }
}
function excelList(obj,stkIdn){
        var isChecked = obj.checked;
      var url = "ajaxLabAction.do?method=excel&stkIdn="+stkIdn+"&stt="+isChecked;
        
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
function ALLCheckBox( allCk , name ){
var isChecked = document.getElementById(allCk).checked;
var  obj = document.getElementById(allCk);
 var frm_elements = document.forms[1].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='checkbox') {
   var fieldId = frm_elements[i].id;
 
   if(fieldId.indexOf(name)!=-1){
     document.getElementById(fieldId).checked = isChecked;
   }

  }
}


ChangeFlg("ALL" ,obj , 'ALL');
if(isChecked){
     document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
     document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
      document.getElementById('vluTtl').innerHTML = document.getElementById('ttlvlu').innerHTML;
    }else{
     document.getElementById('ctsTtl').innerHTML = "0";
     document.getElementById('qtyTtl').innerHTML = "0";
     document.getElementById('vluTtl').innerHTML = "0";
    }
}

function ALLCheckBoxDisable( allCk , name ){
var isChecked = document.getElementById(allCk).checked;
var  obj = document.getElementById(allCk);
 var frm_elements = document.forms[1].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='checkbox') {
   var fieldId = frm_elements[i].id;
   var dis = document.getElementById(fieldId).disabled;
   if(fieldId.indexOf(name)!=-1 && !dis){
     document.getElementById(fieldId).checked = isChecked;
   }

  }
}
if(isChecked){
     document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
     document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
    }else{
     document.getElementById('ctsTtl').innerHTML = "0";
     document.getElementById('qtyTtl').innerHTML = "0";
    
    }
}

function ChangeFlg(stkIdn ,obj , isAll){
     var isChecked = obj.checked;
      if(isAll!='ALL'){
     var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
    var cts = document.getElementById('CTS_'+stkIdn).value;
   
    var ctsTtl = "";
    var qtyTtl = "";
    if(ctsDiv==0){
      ctsTtl = cts;
      qtyTtl = 1;
    }else if(isChecked){
      qtyTtl = parseInt(qtyDiv)+1;
      ctsTtl = (parseFloat(ctsDiv) + parseFloat(cts));
     
     
    }else{
      qtyTtl = parseInt(qtyDiv)-1;
      ctsTtl =(parseFloat(ctsDiv) - parseFloat(cts));
      
     
    }
     document.getElementById('ctsTtl').innerHTML = (new Number(ctsTtl)).toFixed(2);
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
     
      }
      var url = "ajaxPrcAction.do?method=selectList&stkIdn="+stkIdn+"&stt="+isChecked+"&ALL="+isAll;
         
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
  
  
  
  function ChangeFlgTRF(stkIdn ,obj , isAll){
     var isChecked = obj.checked;
      if(isAll!='ALL'){
     var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
    var cts = document.getElementById('CTS_'+stkIdn).value;
     var vluDiv = jsnvl(document.getElementById('vluTtl').innerHTML,'0');
     var netrte = document.getElementById('netrte_'+stkIdn);

    if(netrte==null)
     netrte = document.getElementById('qout_'+stkIdn);
    if(netrte==null){
    netrte='0';
    } else{
    netrte=jsnvl(netrte.value,'0');
    }
   
  
    var ctsTtl = "";
    var qtyTtl = "";
    var valTtl = "";
    if(ctsDiv==0){
      ctsTtl = cts;
      qtyTtl = 1;
      valTtl = parseInt(netrte) * parseFloat(cts);
    }else if(isChecked){
      qtyTtl = parseInt(qtyDiv)+1;
      ctsTtl = (parseFloat(ctsDiv) + parseFloat(cts));
     
          valTtl=parseFloat(vluDiv)+(parseFloat(cts)*parseFloat(netrte));
     
    }else{
      qtyTtl = parseInt(qtyDiv)-1;
      ctsTtl =(parseFloat(ctsDiv) - parseFloat(cts));
      
          valTtl=parseFloat(vluDiv)-(parseFloat(cts)*parseFloat(netrte));
     
    }
     document.getElementById('ctsTtl').innerHTML = (new Number(ctsTtl)).toFixed(2);
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
     
        document.getElementById('vluTtl').innerHTML = (new Number(valTtl)).toFixed(2);
      
      }
      var url = "ajaxPrcAction.do?method=selectList&stkIdn="+stkIdn+"&stt="+isChecked+"&ALL="+isAll;
         
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
  
  function ALLRedio(allCk , name ){
  
var isChecked = document.getElementById(allCk).checked;


 var frm_elements = document.forms[1].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='radio') {
   var fieldId = frm_elements[i].id;
 
   if(fieldId.indexOf(name)!=-1){
    var isDisabled =  document.getElementById(fieldId).disabled;
   if(!isDisabled){
     document.getElementById(fieldId).checked = isChecked;
   }
   }

  }
}

}

  function ALLRedioform0(allCk , name ){
  
var isChecked = document.getElementById(allCk).checked;


 var frm_elements = document.forms[0].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='radio') {
   var fieldId = frm_elements[i].id;
 
   if(fieldId.indexOf(name)!=-1){
    var isDisabled =  document.getElementById(fieldId).disabled;
   if(!isDisabled){
     document.getElementById(fieldId).checked = isChecked;
   }
   }

  }
}

}

function ALLRedioBill(name){
var frm_elements = document.forms[1].elements; 
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='radio') {
   var fieldId = frm_elements[i].id;
   if(fieldId.indexOf(name)!=-1  && !frm_elements[i].disabled){
     document.getElementById(fieldId).checked = true;
   }
  }
}
}
function ALLRedioBK(allCk , name , memoIdn ){
var chkId = allCk+"_"+memoIdn;
var isChecked = document.getElementById(chkId).checked;


 var frm_elements = document.forms[1].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='radio') {
   var fieldId = frm_elements[i].id;
  
 if(fieldId.indexOf(memoIdn)!=-1){
   if(fieldId.indexOf(name)!=-1){
    var isDisabled =  document.getElementById(fieldId).disabled;
   if(!isDisabled){
     document.getElementById(fieldId).checked = isChecked;
   }
   }}

  }
}

}
 function ALLRedioLAB(allCk , name ){
 
var isChecked = document.getElementById(allCk).checked;


 var frm_elements = document.forms[1].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='radio') {
   var fieldId = frm_elements[i].id;
 
   if(fieldId.indexOf(name)!=-1){
     document.getElementById(fieldId).checked = isChecked;
   }

  }
}

}

function ExcelGen(url){
var isture = false;
var frm_elements = document.forms[1].elements; 
 for(i=0; i<frm_elements.length; i++) {
 field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='checkbox') {
   var fieldId = frm_elements[i].id;
    if(fieldId.indexOf('CHK_')!=-1){
       var isChecked = document.getElementById(fieldId).checked;
       if(isChecked){
         window.open(url);
         isture = true;
         break;
         
       }
    }
  }
  
}
if(!isture){
      alert("Please Selected Stone.");
  }
}
function setLabToLabLst(obj , typ){
   var val = obj.value;
 
    var frm_elements = document.forms[1].elements; 
 
for(i=0; i<frm_elements.length; i++) {

   field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='select-one') {
   var fieldId = frm_elements[i].id;
 
   if(fieldId.indexOf(typ)!=-1){
     document.getElementById(fieldId).value = val;
     var fieldVal=document.getElementById(fieldId).value;
     if(fieldVal==''){
     document.getElementById(fieldId).value='NONE';
   }

  }
}
}
}

function setcommonValLst(obj , typ,field){
   var val = obj.value;
 
    var frm_elements = document.forms[1].elements; 
 
for(i=0; i<frm_elements.length; i++) {

   field_type = frm_elements[i].type.toLowerCase();

  if(field_type==field) {
   var fieldId = frm_elements[i].id;
 
   if(fieldId.indexOf(typ)!=-1){
     document.getElementById(fieldId).value = val;
  }
}
}
}
   function  parseMessagesupdatePrp(responseXML,lprp){
         var msgDtl = responseXML.getElementsByTagName("msg")[0];
    var msg = msgDtl.childNodes[0].nodeValue;
    if(msg=='SUCCESS'){
      document.getElementById(lprp+'_Lbl').style.display='';
      }
   }
   
   
function getdetails(obj){
var issueIdn = obj.value;
if(issueIdn!=0){
var url = "ajaxissueAction.do?method=issueDetail&issueIdn="+issueIdn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesIssue(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

}

function parseMessagesIssue(responseXML) {
//alert('@parseMessage '+ fld);
var issues = responseXML.getElementsByTagName("issue")[0];

for (var loop = 0; loop < issues.childNodes.length; loop++) {
var issue = issues.childNodes[loop];
var qty = issue.getElementsByTagName("qty")[0];
if(qty.childNodes[0].nodeValue=='ALL'){
alert(" Please Verify Issue Id ");
document.getElementById("p_iss_id").value = "";
document.getElementById("qty").value = "" ;
document.getElementById("cts").value = "";
document.getElementById("p_iss_id").focus();
}else if(qty.childNodes[0].nodeValue=='invalid'){
alert(" Please Verify Issue Id ");
document.getElementById("p_iss_id").value = "";
document.getElementById("qty").value = "" ;
document.getElementById("cts").value = "";
document.getElementById("p_iss_id").focus();
} else{
var cts = issue.getElementsByTagName("cts")[0];
document.getElementById("qty").value = qty.childNodes[0].nodeValue ;
document.getElementById("cts").value = cts.childNodes[0].nodeValue ;
document.getElementById("memoPg").focus();
}
}
}

function openIssueReport(){
var issueId = document.getElementById("p_iss_id").value;
if(issueId==''){
alert("Please Specify Issue Id");
document.getElementById("p_iss_id").value = "";
document.getElementById("p_iss_id").focus();
return false;
}
else{
var memoPgObj = document.getElementById("memoPg")
var memoPg = memoPgObj.options[memoPgObj.options.selectedIndex].value;
var webUrl = document.getElementById("webUrl").value;
var repPath = document.getElementById("repPath").value;
var reportUrl = document.getElementById("repUrl").value;
var accessidn = document.getElementById("accessidn").value;
var stt = "CUR";
 var isCheked = document.getElementById("sttA").checked;
 if(isCheked)
  stt="ALL";

var cnt = document.getElementById("cnt").value;
var theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+memoPg+"&p_iss_id="+issueId+"&p_stt="+stt+"&p_access="+accessidn;
window.open(theURL, '_blank');
}
}

function checkNone(stkIdn){
document.getElementById('CHKOK_'+stkIdn).disabled = false;
document.getElementById('CHKFL_'+stkIdn).checked = false;
document.getElementById('CHKFL_'+stkIdn).disabled = true;
document.getElementById('APPOK_'+stkIdn).checked = false;
document.getElementById('APPOK_'+stkIdn).disabled = true;
document.getElementById('APPFL_'+stkIdn).checked = false;
document.getElementById('APPFL_'+stkIdn).disabled = true;
document.getElementById('CHKRMK_'+stkIdn).disabled = true;
document.getElementById('APPRMK_'+stkIdn).disabled = true;

}
function unableAPP(stkIdn){

document.getElementById('APPOK_'+stkIdn).disabled = false;
document.getElementById('APPFL_'+stkIdn).disabled = false;
document.getElementById('CHKRMK_'+stkIdn).disabled = false;
document.getElementById('APPRMK_'+stkIdn).disabled = false;
document.getElementById('APPRMK').disabled = false;
document.getElementById('CHKRMK').disabled = false;


}

function disabledAPP(stkIdn){

document.getElementById('CHKFL_'+stkIdn).checked = false;
document.getElementById('CHKFL_'+stkIdn).disabled = false;
document.getElementById('APPOK_'+stkIdn).checked = false;
document.getElementById('APPOK_'+stkIdn).disabled = true;
document.getElementById('APPFL_'+stkIdn).checked = false;
document.getElementById('APPFL_'+stkIdn).disabled = true;
document.getElementById('CHKRMK_'+stkIdn).disabled = true;
document.getElementById('APPRMK_'+stkIdn).disabled = true;



}

function DisplayHISDIV(url){
    var isDis = document.getElementById('hisDiv').style.display;
   
    if(isDis=='none'){
     window.open(url,'ASRT');
     document.getElementById('hisDiv').style.display ='';
     document.getElementById('hisLbl').innerHTML = "Hide Assort History"
     
    }else{
        document.getElementById('hisDiv').style.display ='none';
        document.getElementById('hisLbl').innerHTML = "Show Assort History"
    }
}

function DisplayProssDIV(url){
    var isDis = document.getElementById('prcDiv').style.display;
   
    if(isDis=='none'){
     window.open(url,'PRC');
     document.getElementById('prcDiv').style.display ='';
     document.getElementById('prcLbl').innerHTML = "Hide Process History"
     
    }else{
        document.getElementById('prcDiv').style.display ='none';
        document.getElementById('prcLbl').innerHTML = "Show Process History"
    }
}

function ALLCheckDirect( allCk , name ){
var isChecked = document.getElementById(allCk).checked;
var obj = document.getElementById(allCk);
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf(name)!=-1){
document.getElementById(fieldId).checked = isChecked;
}
}
}
ChangeFlgDirect("ALL" ,obj , 'ALL');
}

function ChangeFlgDirect(stkIdn ,obj , isAll){
var isChecked = obj.checked;
var url = "ajaxPrcAction.do?method=selectList&stkIdn="+stkIdn+"&stt="+isChecked+"&ALL="+isAll;

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


function bulkRtnUpdate(obj,lprp,lstNme,prcId){
        var lprpVal = obj.value;
        loading();
       
      var url = "ajaxAssortAction.do?method=bulkRtnUpdate&lprp="+lprp+"&lprpVal="+lprpVal+'&lstNme='+lstNme+"&prcId="+prcId;
        url = replaceAll(url,'+', '%2B'); 
        url= replaceAll(url,'-','%2D');
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesbulkUpdate(req.responseXML)
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}

function parseMessagesbulkUpdate(responseXML){
closeloading();
var msgtag = responseXML.getElementsByTagName("message")[0];
var msg = msgtag.childNodes[0].nodeValue;;
document.getElementById('msg').innerHTML=msg;


}

function fetchStatus(obj,sttId){
     var prcId = obj.value;
      var url = "ajaxAssortAction.do?method=fetchStatus&prcId="+prcId;
        
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesfetchStatus(req.responseXML,sttId);
                   } else if (req.status == 204){
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}

function parseMessagesfetchStatus(responseXML, sttId) {
    //alert('@parseMessage '+ fld);
        var columnDrop = document.getElementById(sttId);
         var columns = responseXML.getElementsByTagName("stts")[0];
         if(columns.childNodes.length!=0){
      removeAllOptions(columnDrop);
      
     
       for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
          
           var lkey = columnroot.getElementsByTagName("nme")[0];
           var lval = columnroot.getElementsByTagName("ttl")[0];
           
            var newOption = new Option();
             newOption.text = unescape(lval.childNodes[0].nodeValue);
             newOption.value = lkey.childNodes[0].nodeValue;;
             columnDrop.options[loop] = newOption;
       }
      
      document.getElementById('status').style.display='';
         }
     
 }
function replaceAll(str, src, dst) {
while (str.indexOf(src) !== -1) {
str = str.replace(src, dst);
}
return str;
}

function jsnvl(val){
    if(val=='')
    val="0";
    return val;
}