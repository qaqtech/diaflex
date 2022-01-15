function priceGPPrp(link,nme) {
var prp=document.getElementById('mprp_'+nme).value;
window.open(link+'&prp='+prp ,'subContact');

}


function setpricevalid(typ,dsp) {
var isChecked = false;
var typ=document.getElementById(typ).value;
var dsp_flg=document.getElementById(dsp).value;
if(dsp_flg=='R' && typ=='CMN')
{
 return true;
}
if(dsp_flg=='M' && typ=='ROW')
{
return true;
}

if((dsp_flg=='I' && typ=='ROW') || (dsp_flg=='D' && typ=='ROW'))
{
 return true;
}
if(dsp_flg=='I' && typ=='REF')
{
 return true;
}
if((dsp_flg=='I' && typ=='COL') || (dsp_flg=='D' && typ=='COL'))
{
 return true;
}
if(!isChecked){
  alert("Please select proper Value");  
  document.getElementById(dsp).value=''
}
}
function initRequest() {
       if (window.XMLHttpRequest) {
           return new XMLHttpRequest();
       } else if (window.ActiveXObject) {
           isIE = true;
           return new ActiveXObject("Microsoft.XMLHTTP");
       }
   }
function priCalcList(obj,prcCalcGrp,styleid,sheetdatadisplay){
     prcCalcGrp = prcCalcGrp.replace('_', '');
     var isCh = document.getElementById('AU').checked;
     var mstkIdn = document.getElementById('stkIdn').value;
     var lprp = obj;
     var val = document.getElementById(styleid).value;
     if(isCh){
     loading();
     var url = "ajaxPrcAction.do?method=prcCal&stkIdn="+mstkIdn+"&lprp="+lprp+"&val="+val+"&prcCalcGrp="+prcCalcGrp;
     var req = initRequest();
     req.onreadystatechange = function() {    
     if (req.readyState == 4) {
         if (req.status == 200) {
            parseMessagesPri(req.responseXML , mstkIdn,prcCalcGrp,sheetdatadisplay);
             } else if (req.status == 204){
                
           }
         }
        };
      req.open("GET", url, true);
      req.send(null);
     }else{
         document.getElementById('prcDis').innerHTML =""; 
     }
}

function priCalc(obj,prcCalcGrp,sheetdatadisplay){
     prcCalcGrp = prcCalcGrp.replace('_', '');
     var isCh = document.getElementById('AU').checked;
     var mstkIdn = document.getElementById('stkIdn').value;
     var lprp = obj;
     var val = document.getElementById(obj).value;
     if(isCh){
         loading();
     document.getElementById('loading').innerHTML = "<img src=\"../images/processing1.gif\" align=\"middle\" border=\"0\" />";
     var url = "ajaxPrcAction.do?method=prcCal&stkIdn="+mstkIdn+"&lprp="+lprp+"&val="+val+"&prcCalcGrp="+prcCalcGrp;
     var req = initRequest();
     req.onreadystatechange = function() {    
     if (req.readyState == 4) {
         if (req.status == 200) {
            parseMessagesPri(req.responseXML , mstkIdn,prcCalcGrp,sheetdatadisplay);
             } else if (req.status == 204){
                
           }
         }
        };
      req.open("GET", url, true);
      req.send(null);
     }else{
         document.getElementById('prcDis').innerHTML =""; 
     }
}

function  parseMessagesPri(responseXML , stkIdn,prcCalcGrp,sheetdatadisplay){

    document.getElementById('prcDis').innerHTML = "";
    var cnt  = document.getElementById('cnt').value;
      var ptks = responseXML.getElementsByTagName("prcs")[0];
       var lnt = ptks.childNodes.length;
     var currentTime = new Date();
     var month = currentTime.getMonth()+1;
       var str = "";
       var prcStr="";
      if(lnt==0){
          str+=" Last refresh Time : "+currentTime.getDate()+"-"+month+"-"+currentTime.getFullYear()+"  "+currentTime.getHours()+":"+currentTime.getMinutes()+":"+currentTime.getSeconds()+"<br/>"+
                " No Data found." ;
      }else{
     str += "</br><B>Pricing Details For Packet :- "+stkIdn+"</b>" +
     "</br>"+
     "Last refresh Time : "+currentTime.getDate()+"-"+month+"-"+currentTime.getFullYear()+"  "+currentTime.getHours()+":"+currentTime.getMinutes()+":"+currentTime.getSeconds()+"<br/>";
      
     var hdrSCnt=true;
     var hdrMCnt = true;
      for (var loop = 0; loop < ptks.childNodes.length; loop++) {
           var ptk = ptks.childNodes[loop];
          
           var grp = (ptk.getElementsByTagName("grp")[0]).childNodes[0].nodeValue;
           var val = (ptk.getElementsByTagName("val")[0]).childNodes[0].nodeValue;
           var val1 = (ptk.getElementsByTagName("val1")[0]).childNodes[0].nodeValue;
            var val2 = (ptk.getElementsByTagName("val2")[0]).childNodes[0].nodeValue;
             var val3 = (ptk.getElementsByTagName("val3")[0]).childNodes[0].nodeValue;
              var val4 = (ptk.getElementsByTagName("val4")[0]).childNodes[0].nodeValue;
              var val5 = (ptk.getElementsByTagName("val5")[0]).childNodes[0].nodeValue;
              var val6 = (ptk.getElementsByTagName("val6")[0]).childNodes[0].nodeValue;
              var val7 = (ptk.getElementsByTagName("val7")[0]).childNodes[0].nodeValue;
                 var amt = (ptk.getElementsByTagName("amt")[0]).childNodes[0].nodeValue;
           if(loop == 0){
            str+="<div><table border=0 cellpadding=\"5\" cellspacing=\"1\" style=\"border:dotted 1px #CCCCCC;\"> "+
                 "<tr><th>Packet Id</th>";
                 if(prcCalcGrp=='NN' || prcCalcGrp=='' || prcCalcGrp=='MKT'){
                 str+="<th>Rte</th><th>Amount</th><th>Dis</th>";
                 }
                 if(prcCalcGrp=='MFG' || prcCalcGrp=='' || prcCalcGrp=='MKT'){
                 str+="<th>MFG Price</th><th>MFG Discount</th>";
                 }
                 str+="<th>Mix Rte</th><th>Rap Rte</th>" ;
                if(cnt=='kj'){
                if(prcCalcGrp=='MFG' || prcCalcGrp=='' || prcCalcGrp=='MKT'){
                 str+="<th>Mfg Cmp</th><th>Mfg Cmp Dis</th><th>Mfg Cmp Vlu</th>";
                }
                }
                 str+="</tr> ";
                 
                  str+=" <tr> <td>"+stkIdn+"</td>";
                  if(prcCalcGrp=='NN' || prcCalcGrp=='' || prcCalcGrp=='MKT'){
                  str+=" <td>"+grp+"</td><td>"+amt+"</td><td>"+val+"</td>";
                  }
                  if(prcCalcGrp=='MFG' || prcCalcGrp=='' || prcCalcGrp=='MKT'){
                  str+=" <td>"+val2+"</td><td>"+val3+"</td>";
                  }
                  str+=" <td>"+val1+"</td><td>"+val4+"</td>" ;
                 if(cnt=='kj'){
                 if(prcCalcGrp=='MFG' || prcCalcGrp=='' || prcCalcGrp=='MKT'){
                 str+="<td>"+val5+"</td><td>"+val6+"</td><td>"+val7+"</td>";
                 }
                  }
                  str+="</tr> ";
                   str+=" </table> </div> ";
                   
           }else{
           if(sheetdatadisplay=='N'){
           str+=" <div style=\"display:none;\">" ;
           }else{
           str+=" <div>" ;
           }
           if(val1=='S' && hdrSCnt){
           str+=" <div class=\"float\" style=\"padding-right:5px;\"> <table class=\"grid1\"> " ;
           str+=" <tr><th colspan=\"2\">Single Stone Pricing</th><th>Last Update Date</th></tr><tr><td>Packet Id</td><td>"+stkIdn+"</td></tr>";
           hdrSCnt=false;
           }else if(val1=='M' && hdrMCnt){
               if(!hdrSCnt){
                   str+="</table></div>";
                 } 
             str+=" <div class=\"float\" style=\"padding-right:5px;\"> <table class=\"grid1\"> " ;
             str+=" <tr><th colspan=\"2\">Mix Stone Pricing</th><th>Last Update Date</th></tr><tr><td>Packet Id</td><td>"+stkIdn+"</td></tr>";
                
            hdrMCnt=false;
           }
           
           str+= "<tr><td>"+grp+"</td><td>"+val+"</td><td>"+val2+"</td></tr>";
           }
       }
       str+="</table></div></div>";
      }
      document.getElementById('loading').innerHTML='';
      document.getElementById('prcDis').innerHTML = str;
      closeloading()
}

function AddRowsTable(keyFmt){

    var rowCnt = parseInt(document.getElementById('rowCnt').value);
   
    var colCnt = parseInt(document.getElementById('colCnt').value);
    
    var addCnt = parseInt(document.getElementById('addRow').value);
    var newCnt = rowCnt + addCnt;
    for(var i = rowCnt ; i <=newCnt ; i++ ){
    
    var table=document.getElementById("priceGrd");
    
     var row=table.insertRow(i);
     var cell = row.insertCell(0);
     cell.innerHTML="";
     for(var j=1; j < colCnt;j++){
          var txtId = keyFmt.replace(/CMLCNT/g,i+'~');
         
            txtId = txtId.replace(/ROWCNT/g,j-1+'~');
           
            txtId = txtId.substring(0,txtId.length-1);
           
          cell = row.insertCell(j);
        
         cell.innerHTML="<input type=\"text\" id="+txtId+" name="+txtId+"  value="+txtId+"   size=\"5\" />";
     }
        
    }
    document.getElementById('rowCnt').value = newCnt;
}

function setTXTVal(obj,RDTYP,cnt){
    var txtId = document.getElementById('TXTID').value;
     
    var val = obj.value;
    var txtVal = document.getElementById(txtId).value;
    
    if(RDTYP=='FR'){
     if(txtVal.indexOf('~')!=-1){
    txtVal = txtVal.substring(txtVal.indexOf('~')+1, txtVal.length);
    document.getElementById(txtId).value = val+"~"+txtVal;
    }else{
    document.getElementById(txtId).value = val;
    }
    }else{
    if(txtVal.indexOf('~')!=-1){
    txtVal = txtVal.substring(0,txtVal.indexOf('~'));
    }
    if(val!=''){
    document.getElementById(txtId).value =txtVal+"~"+val;
    }else{
          document.getElementById(txtId).value ="";
    }
    }
}

function priCalcAssort(obj){
var stkIdn = document.getElementById('stkIdn').value;
var mstkIdn = document.getElementById('mstkIdn').value;
var lprp = obj;
var val = document.getElementById('RP_'+mstkIdn+'_'+lprp).value;
var url = "ajaxPrcAction.do?method=prcCal&stkIdn="+stkIdn+"&lprp="+lprp+"&val="+val;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesPriAssort(req.responseXML , mstkIdn);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);

}
function parseMessagesPriAssort(responseXML , stkIdn){
document.getElementById('prcDis').innerHTML = "";
var ptks = responseXML.getElementsByTagName("prcs")[0];
var lnt = ptks.childNodes.length;
var currentTime = new Date();
var month = currentTime.getMonth()+1;
var str = "";

if(lnt==0){
str+=" Last refresh Time : "+currentTime.getDay()+"-"+month+"-"+currentTime.getFullYear()+" "+currentTime.getHours()+":"+currentTime.getMinutes()+":"+currentTime.getSeconds()+" "+
" No Data found." ;
}else{
str +=
"Last refresh Time : "+currentTime.getDay()+"-"+month+"-"+currentTime.getFullYear()+" "+currentTime.getHours()+":"+currentTime.getMinutes()+":"+currentTime.getSeconds()+" ";
for (var loop = 0; loop < ptks.childNodes.length; loop++) {
var ptk = ptks.childNodes[loop];
var grp = (ptk.getElementsByTagName("grp")[0]).childNodes[0].nodeValue;
var prm = (ptk.getElementsByTagName("prm")[0]).childNodes[0].nodeValue;
var val = (ptk.getElementsByTagName("val")[0]).childNodes[0].nodeValue;
if(grp=='prcDtl'){
str+="<b>Calculation Details</b>"+
" Rte : "+prm+"<td>Dis : "+val;
}
}
}
document.getElementById('prcDis').innerHTML = str;
}

