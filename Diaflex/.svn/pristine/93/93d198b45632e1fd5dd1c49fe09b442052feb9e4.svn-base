function useravgvalue(obj){
    
for (var loop = 1; loop <= obj; loop++) {

if(document.getElementById('bnmeto_'+loop).value != ''){
if(document.getElementById('userqty_'+loop).value != ''){
var ucts = parseFloat(document.getElementById('usercts_'+loop).value);
var urte = parseFloat(document.getElementById('userrte_'+loop).value);
var uavg = ucts * urte;
document.getElementById('useravg_'+loop).value=Math.round(uavg);
}else {
document.getElementById('userqty_'+loop).value='';
document.getElementById('usercts_'+loop).value='';
document.getElementById('userrte_'+loop).value='';
document.getElementById('useravg_'+loop).value='';
}
}
}
}

function calcdiff(){
var newqty = parseFloat(document.getElementById('newqty').value);
var newcts = parseFloat(document.getElementById('newcts').value);
var newrte = parseFloat(document.getElementById('newrte').value);
var newvalue = parseFloat(document.getElementById('newvalue').value);
var newsize = parseFloat(document.getElementById('newsize').value);

var uqty = parseFloat(document.getElementById('userqty').value);
var ucts = parseFloat(document.getElementById('usercts').value);
var urte = parseFloat(document.getElementById('userrte').value);

if(document.getElementById('newvalue').value !='') {
} else {
alert("please Check CheckBox of Related Packet then enter User Avg Value");
document.getElementById('userrte').value='';
document.getElementById('MIX_1').focus();
return false;
}

if(document.getElementById('userqty').value =='') {
uqty =0;
}
if(document.getElementById('usercts').value =='') {
ucts =0.00;
}


var uvalue = ucts * urte;
var usize = ucts / uqty;

var qtydf = newqty - uqty;
var ctsdf = newcts - ucts;
var rtedf = newrte - urte;
var valuedf = newvalue - uvalue;
var sizedf = newsize - usize;
if(document.getElementById('userqty').value =='' && document.getElementById('usercts').value =='') {
usize =0.00;
document.getElementById('sizediff').value=r2(newsize);;
}
document.getElementById('qtydiff').value=r2(qtydf);
document.getElementById('ctsdiff').value=r2(ctsdf);
document.getElementById('rtediff').value=Math.round(rtedf);
document.getElementById('valuediff').value=Math.round(valuedf);
document.getElementById('sizediff').value=r2(sizedf);
document.getElementById('uservalue').value=Math.round(uvalue);
document.getElementById('usersize').value=r2(usize);

if(document.getElementById('userqty').value ==0.00) {
document.getElementById('sizediff').value=r2(newsize);;
}
}

function calcdiffto(){
var newqty = parseFloat(document.getElementById('newtoqty').value);
var newcts = parseFloat(document.getElementById('newtocts').value);
var newrte = parseFloat(document.getElementById('newtorte').value);
var newvalue = parseFloat(document.getElementById('newtovalue').value);
var newsize = parseFloat(document.getElementById('newtosize').value);

var uqty = parseFloat(document.getElementById('usertoqty').value);
var ucts = parseFloat(document.getElementById('usertocts').value);
var urte = parseFloat(document.getElementById('usertorte').value);

var qtydf = newqty - uqty;
var ctsdf = newcts - ucts;
var rtedf = newrte - urte;

var uvalue = ucts * urte; 
var usize = ucts / uqty;

var valuedf = newvalue - uvalue;
var sizedf = newsize - usize;

document.getElementById('toqtydiff').value=r2(qtydf);
document.getElementById('toctsdiff').value=r2(ctsdf);
document.getElementById('tortediff').value=Math.round(rtedf);
document.getElementById('tovaluediff').value=Math.round(valuedf);
document.getElementById('tosizediff').value=r2(sizedf);
document.getElementById('usertovalue').value=Math.round(uvalue);
document.getElementById('usertosize').value=r2(usize);

}

function r2(n) {

  ans = n * 1000
  ans = Math.round(ans /10) + ""
  while (ans.length < 3) {ans = "0" + ans}
  len = ans.length
  ans = ans.substring(0,len-2) + "." + ans.substring(len-2,len)
  return ans
}

function calcdifffrom(){
var newqty = parseFloat(document.getElementById('newfromqty').value);
var newcts = parseFloat(document.getElementById('newfromcts').value);
var newrte = parseFloat(document.getElementById('newfromrte').value);
var newvalue = parseFloat(document.getElementById('newfromvalue').value);
var newsize = parseFloat(document.getElementById('newfromsize').value);


var uqty = parseFloat(document.getElementById('userfromqty').value);
var ucts = parseFloat(document.getElementById('userfromcts').value);
var urte = parseFloat(document.getElementById('userfromrte').value);

var uvalue = ucts * urte; 
var usize = ucts / uqty;


var qtydf = newqty - uqty;
var ctsdf = newcts - ucts;
var rtedf = newrte - urte ;
var valuedf = newvalue - uvalue ;
var sizedf = newsize - usize ;
document.getElementById('fromqtydiff').value=r2(qtydf);
document.getElementById('fromctsdiff').value=r2(ctsdf);
document.getElementById('fromrtediff').value=Math.round(rtedf);
document.getElementById('fromvaluediff').value=Math.round(valuedf);
document.getElementById('fromsizediff').value=r2(sizedf);
document.getElementById('userfromvalue').value=Math.round(uvalue);
document.getElementById('userfromsize').value=r2(usize);


}



function mixpkt(obj) {

var isChecked = document.getElementById('MIX_'+obj).checked;

if(document.getElementById("cts").value == ''){
alert("Please Select BOX");
document.getElementById('MIX_'+obj).checked = false;
return false;
} else {
document.getElementById("rvalue").value ='MIX_'+obj;
if (isChecked){
if(document.getElementById('newcts').value != null && document.getElementById('newcts').value != '')
{
var boxctsadd = parseFloat(document.getElementById('newcts').value);
var boxqtyadd = parseFloat(document.getElementById('newqty').value);
var boxrteadd = parseFloat(document.getElementById('newrte').value);

var ctsvaladd = parseFloat(document.getElementById('cts1_'+obj).value);
var qtyvaladd = parseFloat(document.getElementById('qty1_'+obj).value);
var rtevaladd = parseFloat(document.getElementById('rte1_'+obj).value);

var totcts = boxctsadd + ctsvaladd;
var totqty = boxqtyadd + qtyvaladd;
var aa = myRound(boxrteadd,3) * myRound(boxctsadd,3);
var bb = myRound(rtevaladd,3) * myRound(ctsvaladd,3);
var rate1 = aa + bb;
var totrte = myRound(rate1,3)/myRound(totcts,3);
    

var tovalue = Math.round( myRound(totrte,3) * myRound(totcts,3) );
var tosize = totcts / totqty;

document.getElementById('newqty').value=totqty;
document.getElementById('newcts').value=myRound(totcts,3);
document.getElementById('newrte').value=Math.round(totrte);
document.getElementById('newvalue').value=Math.round(tovalue);
document.getElementById('newsize').value=myRound(tosize,3);

} else {
var boxcts = parseFloat(document.getElementById('cts').value);
var boxqty = parseFloat(document.getElementById('qty').value);
var boxrte = parseFloat(document.getElementById('rte').value);

var ctsval = parseFloat(document.getElementById('cts1_'+obj).value);
var qtyval = parseFloat(document.getElementById('qty1_'+obj).value);
var rteval = parseFloat(document.getElementById('rte1_'+obj).value);

var newcts = boxcts + ctsval;
var newqty = boxqty + qtyval;

var a = myRound(boxrte,3) * myRound(boxcts,3);
var b = myRound(rteval,3) * myRound(ctsval,3);
var rate = a + b;
var newrte = myRound(rate,3)/myRound(newcts,3);


var tovalue1 = Math.round(newrte * newcts);
var tosize1 = newcts / newqty;


document.getElementById('newqty').value=newqty;
document.getElementById('newcts').value=get3DigitNum( newcts);
document.getElementById('newrte').value=Math.round(newrte);
document.getElementById('newvalue').value=Math.round(tovalue1);
document.getElementById('newsize').value=get3DigitNum(tosize1);

}
} else {
var boxcts = parseFloat(document.getElementById('cts').value);
var boxqty = parseFloat(document.getElementById('qty').value);
var boxrte = parseFloat(document.getElementById('rte').value);
var ctsval = parseFloat(document.getElementById('cts1_'+obj).value);
var qtyval = parseFloat(document.getElementById('qty1_'+obj).value);
var rteval = parseFloat(document.getElementById('rte1_'+obj).value);
var newcts=parseFloat(document.getElementById('newcts').value);
var newqty= parseFloat(document.getElementById('newqty').value);
var newrte= parseFloat(document.getElementById('newrte').value);

//calulate the price
//var x = ((newcts * newrte) - (ctsval*rteval))/boxcts;
//alert(x);
//document.getElementById('newrte').value = x;
var newqty_uncheck = newqty - qtyval;
if((newqty_uncheck-boxqty) == 0){
document.getElementById('newcts').value='';
document.getElementById('newqty').value='';
document.getElementById('newrte').value = '';
document.getElementById('newvalue').value = '';
document.getElementById('newsize').value = '';
}
else {
var ttlcts = newcts;
//var ttlpr = newrte;
var tempnpr = (newcts * newrte) - (ctsval * rteval);
var npr = tempnpr / (ttlcts - ctsval);
document.getElementById('newrte').value = Math.round(npr);

var newcts_uncheck = parseFloat(newcts) - parseFloat(ctsval);
document.getElementById('newcts').value=myRound(newcts_uncheck,3);

document.getElementById('newqty').value=newqty_uncheck;
document.getElementById('newvalue').value=Math.round(newcts_uncheck * npr) ;
document.getElementById('newsize').value=myRound(newcts_uncheck/newqty_uncheck,3);


}

}


}
}

function myRound(value, places) {
    var multiplier = Math.pow(10, places);

    return (Math.round(value * multiplier) / multiplier);
}






function getBox(obj){
       
       var vnmIdn = obj.options[obj.options.selectedIndex].value;
       
        var url = "ajaxsrchAction.do?method=loadBox&vnmId="+vnmIdn ;
             
        var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBox(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
function parseMessagesBox(responseXML) {

var columns = responseXML.getElementsByTagName("boxdt")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var qty = columnroot.getElementsByTagName("qty")[0];
var cts = columnroot.getElementsByTagName("cts")[0];
var rte = columnroot.getElementsByTagName("rate")[0];
document.getElementById('qty').value=qty.childNodes[0].nodeValue;
document.getElementById('cts').value=cts.childNodes[0].nodeValue;
document.getElementById('rte').value=rte.childNodes[0].nodeValue;
var a = qty.childNodes[0].nodeValue;
var b = cts.childNodes[0].nodeValue;
var c = rte.childNodes[0].nodeValue;
var d = c * b;
document.getElementById('val').value=Math.round(d);
if (b != 0) {
var e = b / a;
document.getElementById('size').value=r2(e);
} else {
document.getElementById('size').value=0;
}
}
}

function getBoxselection(obj){
       
       var vnmIdn = obj.options[obj.options.selectedIndex].value;
       
        var url = "ajaxsrchAction.do?method=loadBoxselection&vnmId="+vnmIdn ;
             
        var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesBoxselection(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
function parseMessagesBoxselection(responseXML) {

var columns = responseXML.getElementsByTagName("boxdt")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var qty = columnroot.getElementsByTagName("qty")[0];
var cts = columnroot.getElementsByTagName("cts")[0];
var avlqty = columnroot.getElementsByTagName("avlqty")[0];
var avlcts = columnroot.getElementsByTagName("avlcts")[0];
var rte = columnroot.getElementsByTagName("rate")[0];
document.getElementById('qty').value=qty.childNodes[0].nodeValue;
document.getElementById('cts').value=cts.childNodes[0].nodeValue;
document.getElementById('avlqty').innerHTML=avlqty.childNodes[0].nodeValue;
document.getElementById('avlcts').innerHTML=avlcts.childNodes[0].nodeValue;
document.getElementById('rte').value=rte.childNodes[0].nodeValue;
document.getElementById('avlrte').innerHTML=rte.childNodes[0].nodeValue;
var a = qty.childNodes[0].nodeValue;
var b = cts.childNodes[0].nodeValue;
var c = rte.childNodes[0].nodeValue;
var d = c * b;
document.getElementById('val').value=Math.round(d);
document.getElementById('avlval').innerHTML=Math.round(rte.childNodes[0].nodeValue*avlcts.childNodes[0].nodeValue);
if (b != 0) {
var e = b / a;
document.getElementById('size').value=r2(e);
document.getElementById('avlsize').innerHTML=r2(avlcts.childNodes[0].nodeValue/avlqty.childNodes[0].nodeValue);
} else {
document.getElementById('size').value=0;
document.getElementById('avlsize').innerHTML=0;
}
}
}
function getBoxFrom(obj){

       
        var vnmIdn = obj.options[obj.options.selectedIndex].value;
       
        var url = "ajaxsrchAction.do?method=loadBox&vnmId="+vnmIdn ;
             
        var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFromBox(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
function parseMessagesFromBox(responseXML) {

var columns = responseXML.getElementsByTagName("boxdt")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var vnm = columnroot.getElementsByTagName("vnm")[0];
var qty = columnroot.getElementsByTagName("qty")[0];
var cts = columnroot.getElementsByTagName("cts")[0];
var rte = columnroot.getElementsByTagName("rate")[0];
document.getElementById('vnm').value=vnm.childNodes[0].nodeValue;
document.getElementById('fromqty').value=qty.childNodes[0].nodeValue;
document.getElementById('fromcts').value=cts.childNodes[0].nodeValue;
document.getElementById('fromrte').value=rte.childNodes[0].nodeValue;
}
}

function getBoxFromSplit(obj){

       
       
        var vnmIdn = obj.options[obj.options.selectedIndex].value;
       
        var url = "ajaxsrchAction.do?method=loadBoxSplit&vnmId="+vnmIdn ;
             
        var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesFromBoxSplit(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
function parseMessagesFromBoxSplit(responseXML) {

var columns = responseXML.getElementsByTagName("boxdt")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var qty = columnroot.getElementsByTagName("qty")[0];
var cts = columnroot.getElementsByTagName("cts")[0];
var rte = columnroot.getElementsByTagName("rate")[0];
var avlfrmqty = columnroot.getElementsByTagName("avlqty")[0];
var avlfrmcts = columnroot.getElementsByTagName("avlcts")[0];
document.getElementById('fromqty').value=qty.childNodes[0].nodeValue;
document.getElementById('fromcts').value=cts.childNodes[0].nodeValue;
document.getElementById('fromrte').value=Math.round(rte.childNodes[0].nodeValue);
document.getElementById('fromvalue').value=Math.round((cts.childNodes[0].nodeValue * rte.childNodes[0].nodeValue));
document.getElementById('avlfrmqty').innerHTML=avlfrmqty.childNodes[0].nodeValue;
document.getElementById('avlfrmcts').innerHTML=avlfrmcts.childNodes[0].nodeValue;
document.getElementById('avlfrmrte').innerHTML=rte.childNodes[0].nodeValue;
document.getElementById('avlfrmval').innerHTML=Math.round((avlfrmcts.childNodes[0].nodeValue * rte.childNodes[0].nodeValue));
if(qty.childNodes[0].nodeValue=='0'){
document.getElementById('fromsize').value='0';
document.getElementById('avlfrmsize').innerHTML=0;
}else{
document.getElementById('fromsize').value=r2((cts.childNodes[0].nodeValue / qty.childNodes[0].nodeValue));
document.getElementById('avlfrmsize').innerHTML=r2((avlfrmcts.childNodes[0].nodeValue / avlfrmqty.childNodes[0].nodeValue));
}
}
}

function getSplit(obj){
document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
       var vnmIdn = obj.options[obj.options.selectedIndex].value;
        var url = "ajaxsrchAction.do?method=loadSplit&vnmId="+vnmIdn ;   
        var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesgetSplit(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);      
 }
 
function parseMessagesgetSplit(responseXML) {
var columns = responseXML.getElementsByTagName("boxdt")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var qty = columnroot.getElementsByTagName("qty")[0];
var cts = columnroot.getElementsByTagName("cts")[0];
var rte = columnroot.getElementsByTagName("rate")[0];
document.getElementById('fromqty').value=qty.childNodes[0].nodeValue;
document.getElementById('fromcts').value=cts.childNodes[0].nodeValue;
document.getElementById('fromrte').value=Math.round(rte.childNodes[0].nodeValue);
document.getElementById('fromvalue').value=Math.round((cts.childNodes[0].nodeValue * rte.childNodes[0].nodeValue));
}
document.getElementById('loading').innerHTML = "";
}

function getBoxTo(obj, obj1){
  document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
       var vnmIdn = obj.options[obj.options.selectedIndex].value;
       
        var url = "ajaxsrchAction.do?method=loadBox&vnmId="+vnmIdn ;
             
        var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesToBox(req.responseXML, obj1);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
 
 function getpkt(obj,cnt){
 document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
 var boxtyp=obj.value; 
 boxtyp = replaceAll(boxtyp,'+','%2B');
 boxtyp = replaceAll(boxtyp,'-','%2D');
 var stt=document.getElementById('stt').value;
        var url = "ajaxsrchAction.do?method=loadboxtypvnm&boxtyp="+boxtyp+"&stt="+stt ;
        var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesgetpkt(req.responseXML, cnt);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
 
 function parseMessagesgetpkt(responseXML, cnt) {
var columnDrop = document.getElementById("tovnm_"+cnt);
removeAllOptions(columnDrop);
var newOption = new Option();
newOption = new Option();
newOption.text = "---select---";
newOption.value = "0";
columnDrop.options[0] = newOption;
var columns = responseXML.getElementsByTagName("boxdt")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var vnm = columnroot.getElementsByTagName("vnm")[0];
var idn = columnroot.getElementsByTagName("idn")[0];
newOption = new Option();
newOption.text = vnm.childNodes[0].nodeValue;
newOption.value = idn.childNodes[0].nodeValue;
columnDrop.options[loop+1] = newOption;
}
document.getElementById('loading').innerHTML = "";
}
 
function parseMessagesToBox(responseXML, obj1) {

var columns = responseXML.getElementsByTagName("boxdt")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var qty = columnroot.getElementsByTagName("qty")[0];
var cts = columnroot.getElementsByTagName("cts")[0];
var rte = columnroot.getElementsByTagName("rate")[0];
document.getElementById('toqty_'+obj1).value=qty.childNodes[0].nodeValue;
document.getElementById('tocts_'+obj1).value=cts.childNodes[0].nodeValue;
document.getElementById('torte_'+obj1).value=rte.childNodes[0].nodeValue;
var a = cts.childNodes[0].nodeValue;
var b = rte.childNodes[0].nodeValue;
var c = a * b;
document.getElementById('toavg_'+obj1).value=Math.round(c);
}

document.getElementById('loading').innerHTML = "";
}

function getBoxToSplit(obj){
  
       var vnmIdn = obj.options[obj.options.selectedIndex].value;
       
        var url = "ajaxsrchAction.do?method=loadBoxSplit&vnmId="+vnmIdn ;
             
        var req = initRequest();
        req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                       parseMessagesToBoxtoSplit(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
function parseMessagesToBoxtoSplit(responseXML) {

var columns = responseXML.getElementsByTagName("boxdt")[0];

for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];

var qty = columnroot.getElementsByTagName("qty")[0];
var cts = columnroot.getElementsByTagName("cts")[0];
var rte = columnroot.getElementsByTagName("rate")[0];
var avltoqty = columnroot.getElementsByTagName("avlqty")[0];
var avltocts = columnroot.getElementsByTagName("avlcts")[0];
document.getElementById('toqty').value=qty.childNodes[0].nodeValue;
document.getElementById('tocts').value=cts.childNodes[0].nodeValue;
document.getElementById('torte').value=Math.round(rte.childNodes[0].nodeValue);
document.getElementById('tovalue').value=Math.round((cts.childNodes[0].nodeValue * rte.childNodes[0].nodeValue));
document.getElementById('avltoqty').innerHTML=avltoqty.childNodes[0].nodeValue;
document.getElementById('avltocts').innerHTML=avltocts.childNodes[0].nodeValue;
document.getElementById('avltorte').innerHTML=rte.childNodes[0].nodeValue;
document.getElementById('avltoval').innerHTML=Math.round((avltocts.childNodes[0].nodeValue * rte.childNodes[0].nodeValue));
if(qty.childNodes[0].nodeValue=='0'){
document.getElementById('tosize').value='0';
document.getElementById('avltosize').innerHTML=0;
}else{
document.getElementById('tosize').value=r2((cts.childNodes[0].nodeValue / qty.childNodes[0].nodeValue));
document.getElementById('avltosize').innerHTML=r2((avltocts.childNodes[0].nodeValue / avltoqty.childNodes[0].nodeValue));
}
}
}


    
function splitpkt() {

var row = 1;
var count = document.getElementById('ttl_cnt').value;
for(var i=1;i<count;i++){

var isChecked = document.getElementById('bcheck_'+i).checked;      
if (isChecked){
var ctsval = parseFloat(document.getElementById('cts_'+i).value);
var qtyval = parseFloat(document.getElementById('qty_'+i).value);
var rteval = parseFloat(document.getElementById('rte_'+i).value);

var oldcts = parseFloat(document.getElementById('ttlcts').value);
var oldqty = parseFloat(document.getElementById('ttlqty').value);
var oldrte = parseFloat(document.getElementById('ttlrte').value);

var newcts = oldcts - ctsval;
var newqty = oldqty - qtyval;
var newrte = oldrte - rteval;

document.getElementById('ttlcts').value=newcts;
document.getElementById('ttlqty').value=newqty;
document.getElementById('ttlrte').value=newrte;
} else {
var ctsval1 = parseFloat(document.getElementById('cts_'+i).value);
var qtyval1 = parseFloat(document.getElementById('qty_'+i).value);
var rteval1 = parseFloat(document.getElementById('rte_'+i).value);

var oldcts1 = parseFloat(document.getElementById('ttlcts').value);
var oldqty1 = parseFloat(document.getElementById('ttlqty').value);
var oldrte1 = parseFloat(document.getElementById('ttlrte').value);

var newcts1 = oldcts1 + ctsval1;
var newqty1 = oldqty1 + qtyval1;
var newrte1 = oldrte1 + rteval1;

document.getElementById('ttlcts').value=newcts1;
document.getElementById('ttlqty').value=newqty1;
document.getElementById('ttlrte').value=newrte1;
}
}
row = row+1;
}

function getboxtype(obj){


       var bryIdn = obj.options[obj.options.selectedIndex].value;
        //alert("Mayur");
          //alert('@getColumns '+ tblNme);
           var url = "ajaxsrchAction.do?method=loadboxlst&bryId="+bryIdn ;
            
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                  
                       parseMessagesboxname(req.responseXML);
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
         
 }
 
  function parseMessagesboxname(responseXML){
     var columnDrop = document.getElementById('bnme');
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
             newOption.text = lval.childNodes[0].nodeValue;
             newOption.value = lkey.childNodes[0].nodeValue;
             columnDrop.options[loop+1] = newOption;
       }
   
 }
 

function getboxsplit(obj){
var isChecked = document.getElementById('splitID').checked;
var isChecked1 = document.getElementById('mergeID').checked;

if(isChecked==false && isChecked1==false) {
alert("please select Split or Merge");
return false;
}

document.getElementById('splitesave').disabled=false;

if (isChecked){

var boxqty1 = parseFloat(document.getElementById('fromqty').value);
var boxcts1 = parseFloat(document.getElementById('fromcts').value);
var boxrte1 = parseFloat(document.getElementById('fromrte').value);

var totalqty = 0;
var totalcts = 0;
var totalrte = 0;

for (var loop = 1; loop <= obj; loop++) {


if(document.getElementById('bnmeto_'+loop).value != ''){

var boxqty = parseFloat(document.getElementById('toqty_'+loop).value);
var boxcts = parseFloat(document.getElementById('tocts_'+loop).value);
var boxrte = parseFloat(document.getElementById('torte_'+loop).value);

if(document.getElementById('addqty_'+loop).value != '') {
var qtyval = parseFloat(document.getElementById('addqty_'+loop).value); 
} else {
var qtyval = 0; 
}

if(document.getElementById('addcts_'+loop).value != '') {
var ctsval = parseFloat(document.getElementById('addcts_'+loop).value); 
} else {
var ctsval = 0; 
}

if(document.getElementById('addrte_'+loop).value != '') {
var rteval = parseFloat(document.getElementById('addrte_'+loop).value);
} else {
var rteval = 0; 
}

var newqty = boxqty + qtyval;
var newcts = boxcts + ctsval;

var a = boxrte * boxcts;
var b = rteval * ctsval;
var rate = a + b;
if(newcts != 0){
var newrte = rate/newcts;
} else {
var newrte=0;
} 


totalqty = totalqty + qtyval;
totalcts = totalcts + ctsval;
totalrte = totalrte + rteval;

if(totalcts>boxcts1){
alert("CTS quantity should not exceed");
document.getElementById('totalqty_'+loop).value='';
document.getElementById('totalcts_'+loop).value='';
document.getElementById('totalrte_'+loop).value='';
document.getElementById('addcts_'+loop).focus();
document.getElementById('addqty_'+loop).value='0';
document.getElementById('addcts_'+loop).value='0';
document.getElementById('addrte_'+loop).value='0';
document.getElementById('splitID').checked = false;
return;
}
document.getElementById('totalqty_'+loop).value=newqty;
document.getElementById('totalcts_'+loop).value=r2(newcts);

if(newcts != 0 ){
document.getElementById('totalrte_'+loop).value=Math.round(newrte);
} else {
document.getElementById('totalrte_'+loop).value=0;
}

var aavg = ctsval * rteval;
document.getElementById('addavg_'+loop).value=Math.round(aavg);


var tavg = newcts * newrte;
document.getElementById('totalavg_'+loop).value=Math.round(tavg);

}
}

var qtydiff = boxqty1 - totalqty;
var ctsdiff = boxcts1 - totalcts;

var aa = boxcts1 * boxrte1;
var bb = totalcts * totalrte;
var rt = aa - bb;

if(ctsdiff != 0){
var newr = rt/ctsdiff;
} else {
var newr=0;
}



if(totalcts>boxcts1){
} else {

document.getElementById('newfromqty').value=qtydiff;  
document.getElementById('newfromcts').value=r2(ctsdiff);
document.getElementById('newfromrte').value=Math.round(newr);
var valdiff = ctsdiff * newr;
document.getElementById('newfromvalue').value=Math.round(valdiff);

if(qtydiff != 0){
var sizediff = ctsdiff / qtydiff ;
document.getElementById('newfromsize').value=r2(sizediff); 
} else {
document.getElementById('newfromsize').value=0; 
}
}
}
if (isChecked1){
getboxmerge(obj);
}
}


function getboxmerge(obj){

var boxqty1 = parseFloat(document.getElementById('fromqty').value);
var boxcts1 = parseFloat(document.getElementById('fromcts').value);
var boxrte1 = parseFloat(document.getElementById('fromrte').value);

var totalqty = 0;
var totalcts = 0;
var totalrte = 0;

for (var loop = 1; loop <= obj; loop++) {

if(document.getElementById('bnmeto_'+loop).value != ''){

var boxqty = parseFloat(document.getElementById('toqty_'+loop).value);
var boxcts = parseFloat(document.getElementById('tocts_'+loop).value);
var boxrte = parseFloat(document.getElementById('torte_'+loop).value);

if(document.getElementById('addqty_'+loop).value != '') {
var qtyval = parseFloat(document.getElementById('addqty_'+loop).value); 
} else {
var qtyval = 0; 
}

if(document.getElementById('addcts_'+loop).value != '') {
var ctsval = parseFloat(document.getElementById('addcts_'+loop).value); 
} else {
var ctsval = 0; 
}

if(document.getElementById('addrte_'+loop).value != '') {
var rteval = parseFloat(document.getElementById('addrte_'+loop).value);
} else {
var rteval = 0; 
}


if(ctsval>boxcts){
alert("CTS quantity should not exceed");
document.getElementById('mergeID').checked = false;
} else {

var newqty = boxqty - qtyval;
var newcts = boxcts - ctsval;

var a = boxrte * boxcts;
var b = rteval * ctsval;
var rate = a - b;

if(newcts != 0){
var newrte = rate/newcts;
} else {
var newrte=0;
}


document.getElementById('totalqty_'+loop).value=newqty;
document.getElementById('totalcts_'+loop).value=r2(newcts);

if(newcts != 0 ){
document.getElementById('totalrte_'+loop).value=Math.round(newrte);
} else {
document.getElementById('totalrte_'+loop).value=0;
}

var aavg = ctsval * rteval;
document.getElementById('addavg_'+loop).value=Math.round(aavg);
var tavg = newcts * newrte;
document.getElementById('totalavg_'+loop).value=Math.round(tavg);


totalqty = totalqty + qtyval;
totalcts = totalcts + ctsval;
totalrte = totalrte + rteval;

var qtydiff = boxqty1 + totalqty;
var ctsdiff = boxcts1 + totalcts;

var aa = boxcts1 * boxrte1;
var bb = totalcts * totalrte;
var rt = aa + bb;

if(ctsdiff != 0){
var newr = rt/ctsdiff;
} else {
var newr=0;
}

document.getElementById('newfromqty').value=qtydiff;  
document.getElementById('newfromcts').value=r2(ctsdiff);
document.getElementById('newfromrte').value=Math.round(newr);
var valdiff = ctsdiff * newr;
document.getElementById('newfromvalue').value=Math.round(valdiff);
document.getElementById('newfromsize').value=r2(sizediff); 

if(qtydiff != 0){
var sizediff = ctsdiff / qtydiff ;
document.getElementById('newfromsize').value=r2(sizediff); 
} else {
document.getElementById('newfromsize').value=0; 
}



}
}
}
}


function ALLRedioBox(allCk , name ){
 
var isChecked = document.getElementById(allCk).checked;


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

}

function calculateValue()
{
var carat,price,total_value;
if(document.forms[0].cts.value != null && document.forms[0].bprice.value){
carat=parseFloat(document.forms[0].cts.value);
price=parseFloat(document.forms[0].bprice.value);
total_value = carat * price;
document.forms[0].vlu.value=total_value;
} else {
document.forms[0].vlu.value="";
}
}

function calculateValueboxMaster()
{
var carat=document.getElementById('cts').value;
var price=document.getElementById('bprice').value;
var total_value='';
if(carat!='' && price!=''){
total_value = carat * price;
document.getElementById('vlu').value=formatNumber(total_value,2);
} else {
document.getElementById('vlu').value='';
}
}

function validate_boxmaster(){
var bname=document.getElementById('bname').value;
var ptyp=document.getElementById('ptyp').value;
var qty=document.getElementById('qty').value;
var cts=document.getElementById('cts').value;
if(bname=='' || bname=='0'){
    alert('Please Enter Box Name');
    document.getElementById('bname').focus();
    return false;
}
if(ptyp=='' || ptyp=='0'){
    alert('Please Select packet Type');
    document.getElementById('ptyp').focus();
    return false;
}
if(qty==''){
    alert('Please Enter Qty');
    document.getElementById('qty').focus();
    return false; 
}
if(cts==''){
    alert('Please Enter Carets');
    document.getElementById('cts').focus();
    return false; 
}
return true;
}

function initRequest() {
       if (window.XMLHttpRequest) {
           return new XMLHttpRequest();
       } else if (window.ActiveXObject) {
           isIE = true;
           return new ActiveXObject("Microsoft.XMLHTTP");
       }
   }

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}



function setBGColor(objID , divNme){

    
    var obj =divNme+objID;
    
    var display = document.getElementById(obj).style.display
  
    if(display=='none'){
    document.getElementById(obj).style.display = '';
   
    }else{
     document.getElementById(obj).style.display = 'none'
    
    }
    
}

function validatesplit(){

var isChecked = false;
var v=document.getElementById('bnmefrom').value;
if(v!='' && v!=0) {
isChecked = true;
}
if(!isChecked){
alert("Please Select Box.");
document.getElementById('bnmefrom').value='';
document.getElementById('bnmefrom').focus();
}
return isChecked;
}


function callBoxPkt1(obj) {
var count = obj.options.selectedIndex;
var idn = obj.options[obj.options.selectedIndex].value;
var idnhid = document.getElementById("selidn");
idnhid.value = idn;

var loop = document.getElementById("count").value;
for(var i=0; i<loop; i++) {
document.getElementById(i).style.display = "none";
}

var div = document.getElementById(count-1);
div.style.display = "block";

return false;

}
function pkttoboxALL(obj,id) {
      var checked = document.getElementById(obj).checked;
     var countval = document.getElementById('count').value;
    for(var i=1;i<=countval;i++){
        var objId = id+i;
        document.getElementById(objId).checked = checked;
        if(objId.indexOf("BOX_")!=-1){
        pkttobox(objId);
        }else{
         pkttoboxsingle(objId);  
        }
    }
}

function pkttobox(obj) {
var isChecked = document.getElementById(obj).checked;
var count=obj.substring(obj.indexOf('_')+1);
if(document.getElementById("fromcts").value == '' || document.getElementById("tocts").value == ''){
//alert("Please Select BOX");
document.getElementById(obj).checked = false;
return false;
} else {


if (isChecked == true){
document.getElementById('SINGLE_'+count).disabled=true;
if(document.getElementById("newfromqty").value == '') {

var boxqty = parseFloat(document.getElementById('fromqty').value);
var boxcts = parseFloat(document.getElementById('fromcts').value);
var boxrte = parseFloat(document.getElementById('fromrte').value);

var boxqty1 = parseFloat(document.getElementById('toqty').value);
var boxcts1 = parseFloat(document.getElementById('tocts').value);
var boxrte1 = parseFloat(document.getElementById('torte').value);

var qtyval = parseFloat(document.getElementById('qty_'+count).value);
var ctsval = parseFloat(document.getElementById('cts_'+count).value);
var rteval = parseFloat(document.getElementById('rte_'+count).value);

var newfromcts = boxcts - ctsval;
var newfromqty = boxqty - qtyval;

var a = boxrte * boxcts;
var b = rteval * ctsval;
var rate = a - b;
var newfromrte = rate/newfromcts;

var newtocts = boxcts1 + ctsval;
var newtoqty = boxqty1 + qtyval;

var a1 = boxrte1 * boxcts1;
var b1 = rteval * ctsval;
var rate1 = a1 + b1;
var newtorte = rate1/newtocts;


document.getElementById('newfromqty').value=newfromqty;
document.getElementById('newfromcts').value=r2(newfromcts);
document.getElementById('newfromrte').value=Math.round(newfromrte);
document.getElementById('newfromvalue').value=Math.round(newfromcts * newfromrte);
document.getElementById('newfromsize').value=r2(newfromcts / newfromqty);



document.getElementById('newtoqty').value=newtoqty;
document.getElementById('newtocts').value=r2(newtocts);
document.getElementById('newtorte').value=Math.round(newtorte);
document.getElementById('newtovalue').value=Math.round(newtocts * newtorte);
document.getElementById('newtosize').value=r2(newtocts / newtoqty);


} else {

var newqty = parseFloat(document.getElementById('newfromqty').value);
var newcts = parseFloat(document.getElementById('newfromcts').value);
var newrte = parseFloat(document.getElementById('newfromrte').value);

var newqty1 = parseFloat(document.getElementById('newtoqty').value);
var newcts1 = parseFloat(document.getElementById('newtocts').value);
var newrte1 = parseFloat(document.getElementById('newtorte').value);

var qtyval4 = parseFloat(document.getElementById('qty_'+count).value);
var ctsval4 = parseFloat(document.getElementById('cts_'+count).value);
var rteval4 = parseFloat(document.getElementById('rte_'+count).value);

var newfromcts1 = newcts - ctsval4;
var newfromqty1 = newqty - qtyval4;

var a4 = newrte * newcts;
var b4 = rteval4 * ctsval4;

var rate4 = a4 - b4;
var newfromrte1 = rate4/newfromcts1;


var newtocts5 = newcts1 + ctsval4;
var newtoqty5 = newqty1 + qtyval4;

var a8 = newrte1 * newcts1;
var b8 = rteval4 * ctsval4;
var rate8 = a8 + b8;
var newtorte8 = rate8/newtocts5;


document.getElementById('newfromqty').value=newfromqty1;
document.getElementById('newfromcts').value=r2(newfromcts1);
document.getElementById('newfromrte').value=Math.round(newfromrte1);
document.getElementById('newfromvalue').value=Math.round(newfromcts1 * newfromrte1);


document.getElementById('newfromsize').value=r2(newfromcts1 / newfromqty1);



document.getElementById('newtoqty').value=newtoqty5;
document.getElementById('newtocts').value=r2(newtocts5);
document.getElementById('newtorte').value=Math.round(newtorte8);
document.getElementById('newtovalue').value=Math.round(newtocts5 * newtorte8);
document.getElementById('newtosize').value=r2(newtocts5 / newtoqty5);

}
}
if(isChecked == false) {

document.getElementById('SINGLE_'+count).disabled=false;
var boxqty5 = parseFloat(document.getElementById('newfromqty').value);
var boxcts5 = parseFloat(document.getElementById('newfromcts').value);
var boxrte5 = parseFloat(document.getElementById('newfromrte').value);

var qtyval7 = parseFloat(document.getElementById('qty_'+count).value);
var ctsval7 = parseFloat(document.getElementById('cts_'+count).value);
var rteval7 = parseFloat(document.getElementById('rte_'+count).value);

var boxqty8 = parseFloat(document.getElementById('newtoqty').value);
var boxcts8 = parseFloat(document.getElementById('newtocts').value);
var boxrte8 = parseFloat(document.getElementById('newtorte').value);

var boxqty = parseFloat(document.getElementById('fromqty').value);
var boxcts = parseFloat(document.getElementById('fromcts').value);
var boxrte = parseFloat(document.getElementById('fromrte').value);

var boxqty1 = parseFloat(document.getElementById('toqty').value);
var boxcts1 = parseFloat(document.getElementById('tocts').value);
var boxrte1 = parseFloat(document.getElementById('torte').value);

var p9 = boxqty5 + qtyval7;
var q9 = boxcts5 + ctsval7;

var a9 = boxrte5 * boxcts5;
var b9 = rteval7 * ctsval7;
var rate9 = a9 + b9;
var newfromrte9 = rate9/q9;

var m9 = boxqty8 - qtyval7;
var n9 = boxcts8 - ctsval7;

var l9 = boxrte8 * boxcts8;
var x9 = rteval7 * ctsval7;
var rate5 = l9 - x9;
var newtorte9 = rate5/n9;


document.getElementById('newfromqty').value=p9;
document.getElementById('newfromcts').value=r2(q9);
document.getElementById('newfromrte').value=Math.round(newfromrte9);
document.getElementById('newfromvalue').value=Math.round(q9 * newfromrte9);
document.getElementById('newfromsize').value=r2(q9 / p9);

document.getElementById('newtoqty').value=m9;
document.getElementById('newtocts').value=r2(n9);
document.getElementById('newtorte').value=Math.round(newtorte9);
document.getElementById('newtovalue').value=Math.round(n9 * newtorte9);
document.getElementById('newtosize').value=r2(n9 / m9);



if(p9 == boxqty && m9 == boxqty1){
document.getElementById('newfromqty').value='';
document.getElementById('newfromcts').value='';
document.getElementById('newfromrte').value='';
document.getElementById('newfromvalue').value='';
document.getElementById('newfromsize').value='';



document.getElementById('newtoqty').value='';
document.getElementById('newtocts').value='';
document.getElementById('newtorte').value='';
document.getElementById('newtovalue').value='';
document.getElementById('newtosize').value='';

}
}
}
}

function pkttoboxsingle(obj) {

var isChecked = document.getElementById(obj).checked;
var count=obj.substring(obj.indexOf('_')+1);

if (isChecked == true){

document.getElementById('BOX_'+count).disabled=true;

if(document.getElementById("newfromqty").value == '') {

var boxqty = parseFloat(document.getElementById('fromqty').value);
var boxcts = parseFloat(document.getElementById('fromcts').value);
var boxrte = parseFloat(document.getElementById('fromrte').value);

var qtyval = parseFloat(document.getElementById('qty_'+count).value);
var ctsval = parseFloat(document.getElementById('cts_'+count).value);
var rteval = parseFloat(document.getElementById('rte_'+count).value);

var newfromqty = boxqty - qtyval;
var newfromcts = boxcts - ctsval;

var a9 = boxrte * boxcts;
var b9 = rteval * ctsval;
var rate9 = a9 - b9;
var newfromrte = rate9/newfromcts;


document.getElementById('newfromqty').value=newfromqty;
document.getElementById('newfromcts').value=r2(newfromcts);
document.getElementById('newfromrte').value=Math.round(newfromrte);
document.getElementById('newfromvalue').value=Math.round(newfromcts * newfromrte);
document.getElementById('newfromsize').value=r2(newfromcts / newfromqty);



} else {



var boxqty = parseFloat(document.getElementById('newfromqty').value);
var boxcts = parseFloat(document.getElementById('newfromcts').value);
var boxrte = parseFloat(document.getElementById('newfromrte').value);

var qtyval = parseFloat(document.getElementById('qty_'+count).value);
var ctsval = parseFloat(document.getElementById('cts_'+count).value);
var rteval = parseFloat(document.getElementById('rte_'+count).value);

var newfromqty = boxqty - qtyval;
var newfromcts = boxcts - ctsval;

var a8 = boxrte * boxcts;
var b8 = rteval * ctsval;
var rate8 = a8 - b8;
var newfromrte = rate8/newfromcts;

document.getElementById('newfromqty').value=newfromqty;
document.getElementById('newfromcts').value=newfromcts;
document.getElementById('newfromrte').value=newfromrte;
document.getElementById('newfromvalue').value=Math.round(newfromcts * newfromrte);
document.getElementById('newfromsize').value=r2(newfromcts / newfromqty);


}
}
if (isChecked == false){
document.getElementById('BOX_'+count).disabled=false;
var boxqty1 = parseFloat(document.getElementById('fromqty').value);
var boxcts1 = parseFloat(document.getElementById('fromcts').value);
var boxrte1 = parseFloat(document.getElementById('fromrte').value);

var boxqty = parseFloat(document.getElementById('newfromqty').value);
var boxcts = parseFloat(document.getElementById('newfromcts').value);
var boxrte = parseFloat(document.getElementById('newfromrte').value);

var qtyval = parseFloat(document.getElementById('qty_'+count).value);
var ctsval = parseFloat(document.getElementById('cts_'+count).value);
var rteval = parseFloat(document.getElementById('rte_'+count).value);

var newfromqty = boxqty + qtyval;
var newfromcts = boxcts + ctsval;

var a9 = boxrte * boxcts;
var b9 = rteval * ctsval;
var rate9 = a9 + b9;
var newfromrte = rate9/newfromcts;


document.getElementById('newfromqty').value=newfromqty;
document.getElementById('newfromcts').value=newfromcts;
document.getElementById('newfromrte').value=newfromrte;
document.getElementById('newfromvalue').value=Math.round(newfromcts * newfromrte);
document.getElementById('newfromsize').value=r2(newfromcts / newfromqty);


if(newfromqty == boxqty1){

document.getElementById('newfromqty').value='';
document.getElementById('newfromcts').value='';
document.getElementById('newfromrte').value='';
document.getElementById('newfromvalue').value='';
document.getElementById('newfromsize').value='';


}
}
}


function callBoxPkt() {
//var count = obj.options.selectedIndex;
//alert(count);
//var idn = obj.options[obj.options.selectedIndex].value;
//alert(idn);
//var idnhid = document.getElementById("selidn");
//alert(idnhid.value);
//idnhid.value = idn;
//
//var loop = document.getElementById("count").value;
//for(var i=0; i<loop; i++) {
// document.getElementById(i).style.display = "none";
//}
//
//var div = document.getElementById(count-1);
//div.style.display = "block";
//
//
//return false;
var idn=document.getElementById('bnmefrom').value;
var vnmLst=document.getElementById('vnmLst').value;
vnmLst = vnmLst.replace(" ", ",");
vnmLst = vnmLst.replace('\n',',');
vnmLst = vnmLst.toUpperCase();
if(vnmLst.length==1){
    vnmLst='';
}
document.getElementById('boxpktid').innerHTML = "<img src=\"../images/loadbig.gif\" align=\"middle\" border=\"0\" />";
var url = "boxSplit.do?method=boxdtl&idn="+idn ;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesMemo(req.responseXML,idn,vnmLst);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);

}
function parseMessagesMemo(responseXML, idn,vnmLst) {
// var str = "<table cellpadding='0' cellspacing='0' class='grid1' ><tr><th align='center' width='100px'>Packet No</th>"+
// "<th align='center' width='100px'>QTY</th><th align='center' width='100px'>CTS</th><th align='center' width='100px'>RATE</th>" +
// "<th width='100px'>BOX <input type='radio' id='BOX' name='BOX' onclick=\"ALLRedioBox('BOX','BOX_')\"/></th>" +
// "<th width='100px'>SINGLE <input type='radio' id='SINGLE' name='SINGLE' onclick=\"ALLRedioBox('SINGLE','SINGLE_')\"/></th>"+
// "</tr>";
document.getElementById('boxpktid').innerHTML ="";
var mySplitResult = vnmLst.split(",");
if(vnmLst.length==0){
    mySplitResult=new Array();
}
var table = document.createElement('table');
table.width = "50%";
table.className = "grid1";

var tblBody = document.createElement("tbody");
var row = document.createElement("TR");
var cell = document.createElement("th");

var cellText = document.createTextNode("PACKET NO");
cell.appendChild(cellText);
row.appendChild(cell);

cell = document.createElement("th");
cellText = document.createTextNode("QTY");
cell.appendChild(cellText);
row.appendChild(cell);

cell = document.createElement("th");
cellText = document.createTextNode("CTS");
cell.appendChild(cellText);
row.appendChild(cell);

cell = document.createElement("th");
cellText = document.createTextNode("AVG VALUE");
cell.appendChild(cellText);
row.appendChild(cell);


cell = document.createElement("th");
cellText = document.createTextNode("TOTAL VALUE");
cell.appendChild(cellText);
row.appendChild(cell);

cell = document.createElement("th");
cellText = document.createTextNode("BOX");
myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','checkbox');
myCheckBoxUnchecked.setAttribute('id',"BOX_ALL");
myCheckBoxUnchecked.setAttribute('name',"BOX_ALL");
myCheckBoxUnchecked.onclick = function() {
pkttoboxALL(this.id,'BOX_');
};
cell.appendChild(cellText);
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);

cell = document.createElement("th");
cellText = document.createTextNode("SINGLE");
myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','checkbox');
myCheckBoxUnchecked.setAttribute('id',"SINGLE_ALL");
myCheckBoxUnchecked.setAttribute('name',"SINGLE_ALL");
myCheckBoxUnchecked.onclick = function() {
pkttoboxALL(this.id,'SINGLE_');
};
cell.appendChild(cellText);
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);

tblBody.appendChild(row);
var count=0;
var memos = responseXML.getElementsByTagName("boxs")[0];
var sr=0;
var vnmLength=mySplitResult.length;
for (var loop = 0; loop < memos.childNodes.length; loop++) {

var memo = memos.childNodes[loop];
var vnm = memo.getElementsByTagName("vnm")[0];
var qty = memo.getElementsByTagName("qty")[0];
var cts = memo.getElementsByTagName("cts")[0];
var upr = memo.getElementsByTagName("upr")[0];
var sttPrpS = vnm.childNodes[0].nodeValue;
var sttPrpB = vnm.childNodes[0].nodeValue;

if(mySplitResult.indexOf(sttPrpS)!=-1 || vnmLength==0){

sr++;
var sttPrp = "stt_"+sr;
var vnmID = "vnm_"+sr;
var qtyID = "qty_"+sr;
var ctsID = "cts_"+sr;
var uprID = "rte_"+sr;
var totalID = "total_"+sr;
var boxID = "BOX_"+sr;
var singleID = "SINGLE_"+sr;

row = document.createElement("TR");
count++;
myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','text');
myCheckBoxUnchecked.setAttribute('id',vnmID);
myCheckBoxUnchecked.setAttribute('name',vnmID);
myCheckBoxUnchecked.setAttribute('readonly','readonly');
myCheckBoxUnchecked.setAttribute('value',vnm.childNodes[0].nodeValue);
cell = document.createElement("td");
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);


myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','text');
myCheckBoxUnchecked.setAttribute('id',qtyID);
myCheckBoxUnchecked.setAttribute('name',qtyID);
myCheckBoxUnchecked.setAttribute('readonly','readonly');
myCheckBoxUnchecked.setAttribute('value',qty.childNodes[0].nodeValue);
cell = document.createElement("td");
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);

myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','text');
myCheckBoxUnchecked.setAttribute('id',ctsID);
myCheckBoxUnchecked.setAttribute('name',ctsID);
myCheckBoxUnchecked.setAttribute('readonly','readonly');
myCheckBoxUnchecked.setAttribute('value',cts.childNodes[0].nodeValue);
cell = document.createElement("td");
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);

myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','text');
myCheckBoxUnchecked.setAttribute('id',uprID);
myCheckBoxUnchecked.setAttribute('name',uprID);
myCheckBoxUnchecked.setAttribute('readonly','readonly');
myCheckBoxUnchecked.setAttribute('value',upr.childNodes[0].nodeValue);
cell = document.createElement("td");
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);


myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','text');
myCheckBoxUnchecked.setAttribute('id',totalID);
myCheckBoxUnchecked.setAttribute('name',totalID);
myCheckBoxUnchecked.setAttribute('readonly','readonly');
myCheckBoxUnchecked.setAttribute('value',Math.round(cts.childNodes[0].nodeValue * upr.childNodes[0].nodeValue));
cell = document.createElement("td");
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);


myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','checkbox');
myCheckBoxUnchecked.setAttribute('id',boxID);
myCheckBoxUnchecked.setAttribute('name',boxID);
myCheckBoxUnchecked.setAttribute('value',sttPrpB);
myCheckBoxUnchecked.onclick = function() {

pkttobox(this.id);

};
cell = document.createElement("td");
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);

myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','checkbox');
myCheckBoxUnchecked.setAttribute('id',singleID);
myCheckBoxUnchecked.setAttribute('name',singleID);
myCheckBoxUnchecked.setAttribute('value',sttPrpS);
myCheckBoxUnchecked.onclick = function() {

pkttoboxsingle(this.id);

};
cell = document.createElement("td");
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);


tblBody.appendChild(row);

// str = str+"<tr><td><input type='text' name='"+vnmID+"' id='"+vnmID+"' value='"+vnm.childNodes[0].nodeValue+"' /></input> </td>" +
// "<td><input type='text' name='"+qtyID+"' id='"+qtyID+"' value='"+qty.childNodes[0].nodeValue+"' > </input></td>" +
// "<td><input type='text' name='"+ctsID+"' id='"+ctsID+"' value='"+cts.childNodes[0].nodeValue+"' > </input></td>" +
// "<td><input type='text' name='"+uprID+"' id='"+uprID+"' value='"+upr.childNodes[0].nodeValue+"' > </input></td>" +
// "<td><input type='checkbox' name='"+boxID+"' id='"+boxID+"' value='"+sttPrpB+"' onclick=\"pkttobox('"+sr+"')\" ></input></td>" +
// "<td><input type='checkbox' name='"+singleID+"' id='"+singleID+"' value='"+sttPrpS+"' ></input></td>" +
// "</tr>";

}
// str = str +"<tr><td><input type='hidden' name='"+idn+"' id='"+idn+"' value='"+loop+"'/></td></tr>";
// str = str+"</table>";
// alert(str);
// document.getElementById('boxpktid').innerHTML = str;

row = document.createElement("TR");
myCheckBoxUnchecked = document.createElement('input');
myCheckBoxUnchecked.setAttribute('type','hidden');
myCheckBoxUnchecked.setAttribute('id','h_'+idn);
myCheckBoxUnchecked.setAttribute('name','h_'+idn);
myCheckBoxUnchecked.setAttribute('value',loop);
cell = document.createElement("td");
cell.appendChild(myCheckBoxUnchecked);
row.appendChild(cell);
table.appendChild(tblBody);
var div = document.getElementById('boxpktid');
div.appendChild(table);

var idnhid = document.getElementById("selidn");
idnhid.value = idn;

}
document.getElementById('count').value=count;

}

function calqtydiff(){
var newqty = parseFloat(document.getElementById('newqty').value);
if(document.getElementById('newqty').value != '') {
var newqty = parseFloat(document.getElementById('newqty').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('userqty').value='';
document.getElementById('MIX_1').focus();
return false;
}
var uqty = parseFloat(document.getElementById('userqty').value);
if(document.getElementById('userqty').value == '') {
document.getElementById('usersize').value = '';
document.getElementById('qtydiff').value = 0;
}
if(document.getElementById('userqty').value == 0){
document.getElementById('qtydiff').value =newqty;
}
var qtydf = newqty - uqty;
if(qtydf > 0) {
document.getElementById('qtydiff').value=r2(qtydf);
} else {
document.getElementById('qtydiff').value='';
}
if(document.getElementById('usercts').value != ''){
var ucts = parseFloat(document.getElementById('usercts').value);
var nsize = parseFloat(document.getElementById('newsize').value);
var usize = ucts / uqty;
if(usize >0) {
document.getElementById('usersize').value=r2(usize); 
document.getElementById('sizediff').value=r2(nsize-usize);
} else {
document.getElementById('usersize').value=''; 
document.getElementById('sizediff').value='';
}
}
}

function calctsdiff(){
var newcts = parseFloat(document.getElementById('newcts').value);
if(document.getElementById('newcts').value != '') {
var newcts = parseFloat(document.getElementById('newcts').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('usercts').value='';
document.getElementById('MIX_1').focus();
return false;
}
var ucts = parseFloat(document.getElementById('usercts').value);
if(document.getElementById('usercts').value == '') {
document.getElementById('usersize').value = '';
document.getElementById('ctsdiff').value = 0;
}
if(document.getElementById('usercts').value == 0){
document.getElementById('ctsdiff').value =newcts;
}
var ctsdf = newcts - ucts;
if(ctsdf > 0 || ctsdf < 0) {
document.getElementById('ctsdiff').value=r2(ctsdf);
} else {
document.getElementById('ctsdiff').value='';
}
if(document.getElementById('userqty').value != ''){
var uqty = parseFloat(document.getElementById('userqty').value);
var nsize = parseFloat(document.getElementById('newsize').value);
var usize = ucts / uqty;
if(usize >0) {
document.getElementById('usersize').value=r2(usize); 
document.getElementById('sizediff').value=r2(nsize-usize);
} else {
document.getElementById('usersize').value=''; 
document.getElementById('sizediff').value='';
}
}
}

function calrtediff(){

var newvalue = parseFloat(document.getElementById('newrte').value);
if(document.getElementById('newrte').value != '') {
var newvalue = parseFloat(document.getElementById('newrte').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('userrte').value='';
document.getElementById('MIX_1').focus();
return false;
}
var uvalue = parseFloat(document.getElementById('userrte').value);
if(document.getElementById('userrte').value == '') {
document.getElementById('userrte').value = '';
document.getElementById('valuediff').value = 0;
}
if(document.getElementById('userrte').value == 0){
document.getElementById('valuediff').value =newvalue;
}
var valuedf = newvalue - uvalue;
if(valuedf > 0 || valuedf < 0) {
document.getElementById('rtediff').value=r2(valuedf);
} else {
document.getElementById('rtediff').value='';
}
if(document.getElementById('usercts').value != ''){
var ucts = parseFloat(document.getElementById('usercts').value);
var nvalue = parseFloat(document.getElementById('newrte').value);

var nval = uvalue * ucts;

if(nval >0) {
document.getElementById('uservalue').value=r2(nval); 
document.getElementById('valuediff').value=r2(nvalue-nval);
} else {
document.getElementById('uservalue').value=''; 
document.getElementById('valuediff').value='';
}
}
}

function calqtydifffrom(){
var newqty = parseFloat(document.getElementById('newfromqty').value);
if(document.getElementById('newfromqty').value != '') {
var newqty = parseFloat(document.getElementById('newfromqty').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('userfromqty').value='';
document.getElementById('MIX_1').focus();
return false;
}
var uqty = parseFloat(document.getElementById('userfromqty').value);
if(document.getElementById('userfromqty').value == '') {
document.getElementById('userfromsize').value = '';
document.getElementById('fromqtydiff').value = 0;
}
if(document.getElementById('userfromqty').value == 0){
document.getElementById('fromqtydiff').value =newqty;
}
var qtydf = newqty - uqty;
if(qtydf > 0) {
document.getElementById('fromqtydiff').value=r2(qtydf);
} else {
document.getElementById('fromqtydiff').value='';
}
if(document.getElementById('userfromcts').value != ''){
var ucts = parseFloat(document.getElementById('userfromcts').value);
var nsize = parseFloat(document.getElementById('newfromsize').value);
var usize = ucts / uqty;
if(usize >0) {
document.getElementById('userfromsize').value=r2(usize); 
document.getElementById('fromsizediff').value=r2(nsize-usize);
} else {
document.getElementById('userfromsize').value=''; 
document.getElementById('fromsizediff').value='';
}
}
}

function calctsdifffrom(){
var newcts = parseFloat(document.getElementById('newfromcts').value);
if(document.getElementById('newfromcts').value != '') {
var newcts = parseFloat(document.getElementById('newfromcts').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('userfromcts').value='';
document.getElementById('MIX_1').focus();
return false;
}
var ucts = parseFloat(document.getElementById('userfromcts').value);
if(document.getElementById('userfromcts').value == '') {
document.getElementById('userfromsize').value = '';
document.getElementById('fromctsdiff').value = 0;
}
if(document.getElementById('userfromcts').value == 0){
document.getElementById('fromctsdiff').value =newcts;
}
var ctsdf = newcts - ucts;
if(ctsdf > 0 || ctsdf < 0) {
document.getElementById('fromctsdiff').value=r2(ctsdf);
} else {
document.getElementById('fromctsdiff').value='';
}
if(document.getElementById('userfromqty').value != ''){
var uqty = parseFloat(document.getElementById('userfromqty').value);
var nsize = parseFloat(document.getElementById('newfromsize').value);
var usize = ucts / uqty;
if(usize >0) {
document.getElementById('userfromsize').value=r2(usize); 
document.getElementById('fromsizediff').value=r2(nsize-usize);
} else {
document.getElementById('userfromsize').value=''; 
document.getElementById('fromsizediff').value='';
}
}
}

function calrtedifffrom(){

var newvalue = parseFloat(document.getElementById('newfromrte').value);
if(document.getElementById('newfromrte').value != '') {
var newvalue = parseFloat(document.getElementById('newfromrte').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('userfromrte').value='';
document.getElementById('MIX_1').focus();
return false;
}
var uvalue = parseFloat(document.getElementById('userfromrte').value);
if(document.getElementById('userfromrte').value == '') {
document.getElementById('userfromrte').value = '';
document.getElementById('fromvaluediff').value = 0;
}
if(document.getElementById('userfromrte').value == 0){
document.getElementById('fromvaluediff').value =newvalue;
}
var valuedf = newvalue - uvalue;
if(valuedf > 0 || valuedf < 0) {

document.getElementById('fromrtediff').value=r2(valuedf);
} else {
document.getElementById('fromrtediff').value='';
}
if(document.getElementById('userfromcts').value != ''){
var ucts = parseFloat(document.getElementById('userfromcts').value);
var nvalue = parseFloat(document.getElementById('newfromrte').value);

var nval = uvalue * ucts;
if(nval >0) {
document.getElementById('userfromvalue').value=r2(nval); 
document.getElementById('fromvaluediff').value=r2(nvalue-nval);
} else {
document.getElementById('userfromvalue').value=''; 
document.getElementById('fromvaluediff').value='';
}
}
}


function calqtydiffto(){
var newqty = parseFloat(document.getElementById('newtoqty').value);
if(document.getElementById('newtoqty').value != '') {
var newqty = parseFloat(document.getElementById('newtoqty').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('usertoqty').value='';
document.getElementById('MIX_1').focus();
return false;
}
var uqty = parseFloat(document.getElementById('usertoqty').value);
if(document.getElementById('usertoqty').value == '') {
document.getElementById('usertosize').value = '';
document.getElementById('toqtydiff').value = 0;
}
if(document.getElementById('usertoqty').value == 0){
document.getElementById('toqtydiff').value =newqty;
}
var qtydf = newqty - uqty;
if(qtydf > 0) {
document.getElementById('toqtydiff').value=r2(qtydf);
} else {
document.getElementById('toqtydiff').value='';
}
if(document.getElementById('usertocts').value != ''){
var ucts = parseFloat(document.getElementById('usertocts').value);
var nsize = parseFloat(document.getElementById('newtosize').value);
var usize = ucts / uqty;
if(usize >0) {
document.getElementById('usertosize').value=r2(usize); 
document.getElementById('tosizediff').value=r2(nsize-usize);
} else {
document.getElementById('usertosize').value=''; 
document.getElementById('tosizediff').value='';
}
}
}

function calctsdiffto(){
var newcts = parseFloat(document.getElementById('newtocts').value);
if(document.getElementById('newtocts').value != '') {
var newcts = parseFloat(document.getElementById('newtocts').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('usertocts').value='';
document.getElementById('MIX_1').focus();
return false;
}
var ucts = parseFloat(document.getElementById('usertocts').value);
if(document.getElementById('usertocts').value == '') {
document.getElementById('usertosize').value = '';
document.getElementById('toctsdiff').value = 0;
}
if(document.getElementById('usertocts').value == 0){
document.getElementById('toctsdiff').value =newcts;
}
var ctsdf = newcts - ucts;
if(ctsdf > 0 || ctsdf < 0) {
document.getElementById('toctsdiff').value=r2(ctsdf);
} else {
document.getElementById('toctsdiff').value='';
}
if(document.getElementById('usertoqty').value != ''){
var uqty = parseFloat(document.getElementById('usertoqty').value);
var nsize = parseFloat(document.getElementById('newtosize').value);
var usize = ucts / uqty;
if(usize >0) {
document.getElementById('usertosize').value=r2(usize); 
document.getElementById('tosizediff').value=r2(nsize-usize);
} else {
document.getElementById('usertosize').value=''; 
document.getElementById('tosizediff').value='';
}
}
}

function calrtediffto(){

var newvalue = parseFloat(document.getElementById('newtorte').value);
if(document.getElementById('newtorte').value != '') {
var newvalue = parseFloat(document.getElementById('newtorte').value);
} else {
alert("please Check CheckBox of Related Packet then enter User qty");
document.getElementById('usertorte').value='';
document.getElementById('MIX_1').focus();
return false;
}
var uvalue = parseFloat(document.getElementById('usertorte').value);
if(document.getElementById('usertorte').value == '') {
document.getElementById('usertorte').value = '';
document.getElementById('tovaluediff').value = 0;
}
if(document.getElementById('usertorte').value == 0){
document.getElementById('tovaluediff').value =newvalue;
}
var valuedf = newvalue - uvalue;
if(valuedf > 0 || valuedf < 0) {

document.getElementById('tortediff').value=r2(valuedf);
} else {
document.getElementById('tortediff').value='';
}
if(document.getElementById('usertocts').value != ''){
var ucts = parseFloat(document.getElementById('usertocts').value);
var nvalue = parseFloat(document.getElementById('newtorte').value);

var nval = uvalue * ucts;
if(nval >0) {
document.getElementById('usertovalue').value=r2(nval); 
document.getElementById('tovaluediff').value=r2(nvalue-nval);
} else {
document.getElementById('usertovalue').value=''; 
document.getElementById('tovaluediff').value='';
}
}
}



function calctsdiff1(){
var newcts = parseFloat(document.getElementById('newcts').value);
if(document.getElementById('newcts').value != '') {
var newcts = parseFloat(document.getElementById('newcts').value);
} else {
alert("please Check CheckBox of Related Packet then enter User Cts");
document.getElementById('usercts').value='';
document.getElementById('MIX_1').focus();
return false;
}

var ucts = parseFloat(document.getElementById('usercts').value);
var ctsdf = newcts - ucts;
document.getElementById('ctsdiff').value=r2(ctsdf);
var uqty = parseFloat(document.getElementById('userqty').value);
var usize = ucts / uqty;
document.getElementById('usersize').value=r2(usize);
if(document.getElementById('userrte').value != '') {
var urte = parseFloat(document.getElementById('userrte').value);
var ucts = parseFloat(document.getElementById('usercts').value);
var uvalue = ucts * urte;
document.getElementById('uservalue').value=Math.round(uvalue);
}
}

function singletobox() {
var frm_elements = document.forms[0].elements;
var flgObj = document.getElementById('bnme');
var flg = flgObj.options[flgObj.options.selectedIndex].value;
var isChecked = false;
if(flg !=0){
for(i=0; i<frm_elements.length; i++) {
var field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[i].checked){
isChecked = true;
}
}
}
return isChecked;
}
if(!isChecked){
this.form.reset();
}
return isChecked;
}


function checkALLBOX(checkId , count){

var flg = document.getElementById('bnme');
if(flg.value == '' ){
alert("Please select BOX");
document.getElementById('checkAll').checked=false;
return false;
}
var checked = document.getElementById('checkAll').checked;
var countval = document.getElementById(count).value;
for(var i=1;i<=countval;i++){
var objId = checkId+i;
var dis = document.getElementById(objId).disabled
if(!dis){
document.getElementById(objId).checked = checked;
}
}

}


function singletomixall(checkId , count) {

var checked = document.getElementById('checkAll').checked;
var countval = document.getElementById(count).value;

var totalqty = 0;
var totalcts = 0;
var totalrte = 0;

var qty = parseFloat(document.getElementById('qty').value);
var cts = parseFloat(document.getElementById('cts').value);
var rte = parseFloat(document.getElementById('rte').value);

if(checked == true) {
for(var i=1;i<=countval;i++){
var objId = checkId+i;
var addqty = parseFloat(document.getElementById('qty1_'+i).value);
var addcts = parseFloat(document.getElementById('cts1_'+i).value);
var addrte = parseFloat(document.getElementById('rte1_'+i).value);

if(i == 1) {
totalqty =  qty + addqty; 
totalcts =  cts + addcts;
var aa = myRound(rte,3) * myRound(cts,3);
var bb = myRound(addcts,3) * myRound(addrte,3);
var rate1 = aa + bb;
var newrate = myRound(rate1,3)/myRound(totalcts,3);
totalrte = totalrte + newrate;
} else {
var aa = myRound(totalcts,3) * myRound(totalrte,3);
var bb = myRound(addcts,3) * myRound(addrte,3);
var rate1 = aa + bb;
totalcts =  totalcts + addcts;
var newrate = myRound(rate1,3)/myRound(totalcts,3);
totalqty =  totalqty + addqty; 
totalrte = myRound(newrate,3);
}
}
document.getElementById('newqty').value=totalqty;
document.getElementById('newcts').value=myRound(totalcts,3);
document.getElementById('newrte').value=Math.round(totalrte);

document.getElementById('newvalue').value=Math.round( myRound(totalrte,3) * myRound(totalcts,3) );
document.getElementById('newsize').value=myRound(totalqty/totalcts,3);
} 
if(checked == false) {
document.getElementById('newqty').value='';
document.getElementById('newcts').value='';
document.getElementById('newrte').value='';
document.getElementById('newvalue').value='';
document.getElementById('newsize').value='';
}
}


function calculateCts(cur,set,stkIdn){
var amtVal = document.getElementById('prc_'+stkIdn).value;
var ttlCts= document.getElementById('cts_'+stkIdn).value;
var curCts = document.getElementById(cur+"_"+stkIdn).value;
if(curCts!=''){
var setCts = r2(parseFloat(ttlCts) - parseFloat(curCts));
document.getElementById(set+"_"+stkIdn).value = setCts;
var salCts = document.getElementById("CS_"+stkIdn).value;

var frm_elements = document.forms[1].elements; 
    for(i=0; i<frm_elements.length; i++) {
    field_type = frm_elements[i].type.toLowerCase();
    if(field_type=='hidden') {
    var fieldId = frm_elements[i].id;
     if (fieldId.indexOf('out_') !=  - 1) {
      var outper = document.getElementById(fieldId).value;
      var split=fieldId.split('_');
      var from = parseFloat(split[1]);
      var to = parseFloat(split[2]);
      var rtnctsper=Math.round((parseFloat(salCts)/parseFloat(ttlCts))*100);
      rtnctsper=100-rtnctsper;
    if(rtnctsper>=parseFloat(from) && rtnctsper<=parseFloat(to)){
    document.getElementById('PS_'+stkIdn).value=Math.round(parseFloat(amtVal)*outper);
    }
     }
   }}
}
calculateAmt(stkIdn);
}

function calculateAmt(stkIdn){
   var ttlCts= document.getElementById('CS_'+stkIdn).value;
     var prcVal = document.getElementById('PS_'+stkIdn).value;
     var amtVal = document.getElementById('prc_'+stkIdn).value;
    var amt = parseFloat(ttlCts)*parseFloat(prcVal);
    document.getElementById('AS_'+stkIdn).value = r2(amt);
    document.getElementById('PR_'+stkIdn).value=amtVal-prcVal;
    
}

function calculateQty(cur,set,stkIdn){
    var ttlQty = document.getElementById('qty_'+stkIdn).value;
    var curQty = document.getElementById(cur+"_"+stkIdn).value;
    if(curQty!=''){
    var setQty = ttlQty - curQty;
    document.getElementById(set+"_"+stkIdn).value = setQty;
    }
    
}
function validateMemoRtn(){
var count=document.getElementById('count').value;
for(k = 1; k <= count; k++){
var checkedStt = document.getElementById('CHK_'+k).checked;
if(checkedStt) {
var mstkidn=document.getElementById('VNM_'+k).value; 
var qtysale=document.getElementById('QS_'+mstkidn).value;
var ctssale=document.getElementById('CS_'+mstkidn).value; 
var prcsale=document.getElementById('PS_'+mstkidn).value; 
    if(ctssale==''){
    alert('Enter Sale Cts');
    document.getElementById('CS_'+mstkidn).focus();
    return false;
    }
    if(prcsale==''){
    alert('Enter Sale Prc');
    document.getElementById('PS_'+mstkidn).focus();
    return false;
    }
var qtyRtn=document.getElementById('QR_'+mstkidn).value;
var ctsRtn=document.getElementById('CR_'+mstkidn).value; 
var prcRtn=document.getElementById('PR_'+mstkidn).value; 
var qty=document.getElementById('qty_'+mstkidn).value;
var cts=formatNumber(parseFloat(document.getElementById('cts_'+mstkidn).value),2); 

var prc=document.getElementById('prc_'+mstkidn).value; 
var Valqty=parseInt(qtysale)+parseInt(qtyRtn);
var Valcts=formatNumber(parseFloat(ctssale)+parseFloat(ctsRtn),2); 
var Valprc=parseFloat(prcsale)+parseFloat(prcRtn); 

        if(parseFloat(cts)<Valcts){
        alert('Enter Correct Sale & Return Cts');
        document.getElementById('CS_'+mstkidn).value='';
        document.getElementById('CR_'+mstkidn).value='';
        document.getElementById('CS_'+mstkidn).focus();
        return false;
        }
        if(prc!=Valprc){
        alert('Enter Correct Sale & Return Prc');
        document.getElementById('PS_'+mstkidn).value='';
        document.getElementById('PR_'+mstkidn).value='';
        document.getElementById('PS _'+mstkidn).focus();
        return false;
        }
}
}
}

function formatNumber(myNum, numOfDec)
{
      var decimal = 1
      for(i=1; i<=numOfDec;i++)
         decimal = decimal *10

      var myFormattedNum = (Math.round(myNum * decimal)/decimal).toFixed(numOfDec);
      return myFormattedNum;
     // alert(myNum + "   " +  myFormattedNum)
} 

function fullSaleRtn(key){
var count=document.getElementById('count').value;
document.getElementById('checkAll').checked =true;
for(k = 1; k <= count; k++){
document.getElementById('CHK_'+k).checked = true;
var stkIdn=document.getElementById('VNM_'+k).value; 
var qty=document.getElementById('qty_'+stkIdn).value;
var cts=document.getElementById('cts_'+stkIdn).value; 
var prc=document.getElementById('prc_'+stkIdn).value; 
if(key=='S'){
document.getElementById("QS_"+stkIdn).value = qty;
document.getElementById("QR_"+stkIdn).value = '0';
document.getElementById("CS_"+stkIdn).value = cts;
document.getElementById("CR_"+stkIdn).value = '0'; 
document.getElementById('PS_'+stkIdn).value = prc; 
var ttlCts= document.getElementById('CS_'+stkIdn).value;
var amt = parseFloat(ttlCts)*parseFloat(prc);
document.getElementById('AS_'+stkIdn).value = amt;
document.getElementById('PR_'+stkIdn).value='0';
}
if(key=='R'){
document.getElementById("QS_"+stkIdn).value = '0';
document.getElementById("QR_"+stkIdn).value = qty;  
document.getElementById("CS_"+stkIdn).value = '0';
document.getElementById("CR_"+stkIdn).value = cts;
document.getElementById('PS_'+stkIdn).value = '0'; 
document.getElementById('AS_'+stkIdn).value = '0';
document.getElementById('PR_'+stkIdn).value=prc;
}
if(key=='SS'){
document.getElementById("QTY_"+stkIdn+"_1").value = qty;
document.getElementById("QR_"+stkIdn).value = '0';
document.getElementById("CTS_"+stkIdn+"_1").value = cts;
document.getElementById("CR_"+stkIdn).value = '0'; 
document.getElementById('PRC_'+stkIdn+"_1").value = prc; 
var ttlCts= document.getElementById("CTS_"+stkIdn+"_1").value;
var amt = parseFloat(ttlCts)*parseFloat(prc);
document.getElementById('AMT_'+stkIdn+"_1").value = amt;
document.getElementById('PR_'+stkIdn).value='0';

}
if(key=='RR'){
document.getElementById("QS_"+stkIdn).value = '0';
document.getElementById("QR_"+stkIdn).value = qty;  
document.getElementById("CS_"+stkIdn).value = '0';
document.getElementById("CR_"+stkIdn).value = cts;
document.getElementById('PS_'+stkIdn).value = '0'; 
document.getElementById('AS_'+stkIdn).value = '0';
document.getElementById('PR_'+stkIdn).value=prc;
CearSalBox(stkIdn);
}
}
}

function CearSalBox(stkIdn){
 for(var i=1;i<=5;i++){
    document.getElementById("QTY_"+stkIdn+"_"+i).value="";
    document.getElementById("CTS_"+stkIdn+"_"+i).value="";
     document.getElementById("PRC_"+stkIdn+"_"+i).value="";
    document.getElementById("AMT_"+stkIdn+"_"+i).value="";
     document.getElementById("RMK_"+stkIdn+"_"+i).value="";
}
}
function boxConfirm(){
      var frm_elements = document.forms[1].elements; 
       var isChecked = false;
       for(i=0; i<frm_elements.length; i++) {

        var field_type = frm_elements[i].type.toLowerCase();

           if(field_type=='checkbox') {
             var fieldId = frm_elements[i].id;
            if(fieldId.indexOf('CHK_')!=-1){
            var checkedStt = document.getElementById(fieldId).checked;
            if(checkedStt) {
                isChecked = true;
                break;
            }
           
            }}}
     if(isChecked){
         var r = confirm("Are You Sure You Want To Save Changes?");
        return r;
     }else{
     alert("Select Packets for return. ");
       return false;
     }
}

 function boxreturn(mstkidn,objid){
var checked = document.getElementById(objid).checked;
if(checked){
document.getElementById("QS_"+mstkidn).readOnly = false;
document.getElementById("CS_"+mstkidn).readOnly = false;
document.getElementById("PS_"+mstkidn).readOnly = false;
document.getElementById("AS_"+mstkidn).readOnly = false;
document.getElementById("QR_"+mstkidn).readOnly = false;
document.getElementById("CR_"+mstkidn).readOnly = false;
document.getElementById("PR_"+mstkidn).readOnly = false;
calculateAll();
}else{
document.getElementById("QS_"+mstkidn).readOnly = true;
document.getElementById("CS_"+mstkidn).readOnly = true;
document.getElementById("PS_"+mstkidn).readOnly = true;
document.getElementById("AS_"+mstkidn).readOnly = true;
document.getElementById("QR_"+mstkidn).readOnly = true;
document.getElementById("CR_"+mstkidn).readOnly = true;
document.getElementById("PR_"+mstkidn).readOnly = true;

document.getElementById("QS_"+mstkidn).value = '';
document.getElementById("CS_"+mstkidn).value= '';
document.getElementById("PS_"+mstkidn).value= '';
document.getElementById("AS_"+mstkidn).value = '';
document.getElementById("QR_"+mstkidn).value = '';
document.getElementById("CR_"+mstkidn).value= '';
document.getElementById("PR_"+mstkidn).value = '';

}
}

 function calculateAll(){
var selectedcts = 0;
var selectedavgprc = 0;
var currentcts=0;
var calcvlu=0;
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='text'){
var elementid =frm_elements[i].id;
if(elementid.indexOf('CS_')!=-1){
var val=document.getElementById(elementid).value;
if(val!=''){
selectedcts=parseFloat(selectedcts)+parseFloat(val);
currentcts=val;
}
}
if(elementid.indexOf('PS_')!=-1){
var val=document.getElementById(elementid).value;
if(val!=''){
selectedavgprc=parseFloat(selectedavgprc)+parseFloat(val);
calcvlu=calcvlu+(parseFloat(currentcts)*parseFloat(val));
}
}
}
}
var calcavgprc = parseFloat(selectedcts)*parseFloat(selectedavgprc)/parseFloat(selectedcts);
//var calcvlu = parseFloat(selectedcts)*parseFloat(calcavgprc);
document.getElementById('cts').innerHTML=r2(selectedcts);
document.getElementById('avgprc').innerHTML=r2(calcavgprc);
document.getElementById('vlu').innerHTML=r2(calcvlu);
document.getElementById('net_dis').innerHTML=r2(calcvlu);

document.getElementById("vluamt").value='0'
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='hidden'){
var elementid =frm_elements[i].id;
if(elementid.indexOf('_save')!=-1){
document.getElementById(elementid).value='0';
}
}
}
var table = document.getElementById("chargesT");
var spancell = table.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!=''){
if(spanID.indexOf('_dis')!=-1 && spanID!='net_dis'){
document.getElementById(spanID).innerHTML='0';
 var txtId = spanID.replace("_dis","");
 txtId=txtId.replace("net_dis","");

 document.getElementById(txtId).value='0';
}
}
}
}


function activeBoxAll(obj){
var ischecked = obj.checked;
if(ischecked)
ischecked=false;
else
ischecked=true;
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='text'){
var elementid =frm_elements[i].id;
if(elementid.indexOf('QS_')!=-1 || elementid.indexOf('CS_')!=-1 || elementid.indexOf('PS_')!=-1 || elementid.indexOf('AS_')!=-1 || elementid.indexOf('QR_')!=-1 || elementid.indexOf('CR_')!=-1 || elementid.indexOf('PR_')!=-1){
if(ischecked)
document.getElementById(elementid).value='';
document.getElementById(elementid).readOnly = ischecked;
}
}
}
calculateAll();
}


function addpacketrow(){
    var rowno=document.getElementById("noofrows").value;
    rowno=parseInt(rowno)+1;
    var table = document.getElementById("data");
    var row = table.insertRow(data.rows.length);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);
    var cell9 = row.insertCell(8);
    var cell10 = row.insertCell(9);
    var cell11 = row.insertCell(10);
    var cell12 = row.insertCell(11);
    var cell13 = row.insertCell(12);
    var cell14 = row.insertCell(13);
    var cell15 = row.insertCell(14);
    var cell16 = row.insertCell(15);
    var cell17 = row.insertCell(16);
    cell1.innerHTML = rowno;
    var str="<input type=\"text\" name=\"boxtyp_1\" list=\"characters\" onchange=\"getpkt(this,'"+rowno+"');\"><datalist id=\"characters\">";
    var boxtyp_ln = document.getElementById("characters").options.length;
    

    for(var i=0;i<boxtyp_ln;i++){
    str=str+"<option value=\""+document.getElementById("characters").options[0].value+"\">";
    }
   
    str=str+"</datalist>";
    
    cell2.innerHTML = str;
    cell3.innerHTML ="<input type=\"radio\" name=\"stt_"+rowno+"\" id=\"NEW_"+rowno+"\" value=\"NEW\" checked=\"checked\" onclick=\"splitNewEXISTING('NEW','"+rowno+"')\"/>"
    cell4.innerHTML ="<input type=\"radio\" name=\"stt_"+rowno+"\" id=\"EXISTING_"+rowno+"\" value=\"EXISTING\" onclick=\"splitNewEXISTING('EXISTING','"+rowno+"')\"/>"
    cell5.innerHTML = " <select  name=\"value(tovnm_"+rowno+")\" id=\"tovnm_"+rowno+"\" onchange=\"getBoxTo(this,'"+rowno+"');\"   ><option value=\"0\">---select---</option></select> ";
    cell6.innerHTML = "<input type=\"text\" id=\"toqty_"+rowno+"\" name=\"toqty_"+rowno+"\" class=\"sub\" value=\"0\" readonly=\"readonly\" />";
    cell7.innerHTML = "<input type=\"text\" id=\"tocts_"+rowno+"\" name=\"tocts_"+rowno+"\" class=\"sub\" value=\"0\" readonly=\"readonly\"/>";
    cell8.innerHTML = "<input type=\"text\" id=\"torte_"+rowno+"\" name=\"torte_"+rowno+"\" class=\"sub\" value=\"0\" readonly=\"readonly\"/>";
    cell9.innerHTML = "<input type=\"text\" id=\"toavg_"+rowno+"\" name=\"toavg_"+rowno+"\" class=\"sub\" value=\"0\" readonly=\"readonly\"/>";
    cell10.innerHTML = "<input type=\"text\" id=\"addqty_"+rowno+"\" name=\"addqty_"+rowno+"\" class=\"sub\" value=\"0\"   />";
    cell11.innerHTML = "<input type=\"text\" id=\"addcts_"+rowno+"\" name=\"addcts_"+rowno+"\" class=\"sub\" value=\"0\"  />";
    cell12.innerHTML = "<input type=\"text\" id=\"addrte_"+rowno+"\" name=\"addrte_"+rowno+"\" class=\"sub\" value=\"0\"/>";
    cell13.innerHTML = "<input type=\"text\" id=\"addavg_"+rowno+"\" name=\"addavg_"+rowno+"\" class=\"sub\" readonly=\"readonly\" />";
    cell14.innerHTML = "<input type=\"text\" id=\"totalqty_"+rowno+"\" name=\"totalqty_"+rowno+"\"  class=\"sub\" readonly=\"readonly\" />";
    cell15.innerHTML = "<input type=\"text\" id=\"totalcts_"+rowno+"\" name=\"totalcts_"+rowno+"\"  class=\"sub\" readonly=\"readonly\" />";
    cell16.innerHTML = "<input type=\"text\" id=\"totalrte_"+rowno+"\" name=\"totalrte_"+rowno+"\" class=\"sub\" readonly=\"readonly\" />";
    cell17.innerHTML = "<input type=\"text\" id=\"totalavg_"+rowno+"\" name=\"totalavg_"+rowno+"\" class=\"sub\" readonly=\"readonly\" />";
    document.getElementById("noofrows").value=rowno;
}

function getsplitcalculation(){
document.getElementById('loading').innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
var rowno=document.getElementById("noofrows").value;
var boxqty1 = parseFloat(document.getElementById('fromqty').value);
var boxcts1 = parseFloat(document.getElementById('fromcts').value);
var boxrte1 = parseFloat(document.getElementById('fromrte').value);

var totalqty = 0;
var totalcts = 0;
var totalrte = 0;

for (var loop = 1; loop <= rowno; loop++) {


if(document.getElementById('tovnm_'+loop).value != ''){

var boxqty = parseFloat(document.getElementById('toqty_'+loop).value);
var boxcts = parseFloat(document.getElementById('tocts_'+loop).value);
var boxrte = parseFloat(document.getElementById('torte_'+loop).value);

if(document.getElementById('addqty_'+loop).value != '') {
var qtyval = parseFloat(document.getElementById('addqty_'+loop).value); 
} else {
var qtyval = 0; 
}

if(document.getElementById('addcts_'+loop).value != '') {
var ctsval = parseFloat(document.getElementById('addcts_'+loop).value); 
} else {
var ctsval = 0; 
}

if(document.getElementById('addrte_'+loop).value != '') {
var rteval = parseFloat(document.getElementById('addrte_'+loop).value);
} else {
var rteval = 0; 
}

var newqty = boxqty + qtyval;
var newcts = boxcts + ctsval;

var a = boxrte * boxcts;
var b = rteval * ctsval;
var rate = a + b;
if(newcts != 0){
var newrte = rate/newcts;
} else {
var newrte=0;
} 


totalqty = totalqty + qtyval;
totalcts = totalcts + ctsval;
totalrte = totalrte + rteval;

if(totalcts>boxcts1){
alert("CTS quantity should not exceed");
document.getElementById('totalqty_'+loop).value='';
document.getElementById('totalcts_'+loop).value='';
document.getElementById('totalrte_'+loop).value='';
document.getElementById('addcts_'+loop).focus();
document.getElementById('addqty_'+loop).value='0';
document.getElementById('addcts_'+loop).value='0';
document.getElementById('addrte_'+loop).value='0';
return;
}
document.getElementById('totalqty_'+loop).value=newqty;
document.getElementById('totalcts_'+loop).value=r2(newcts);

if(newcts != 0 ){
document.getElementById('totalrte_'+loop).value=Math.round(newrte);
} else {
document.getElementById('totalrte_'+loop).value=0;
}

var aavg = ctsval * rteval;
document.getElementById('addavg_'+loop).value=Math.round(aavg);


var tavg = newcts * newrte;
document.getElementById('totalavg_'+loop).value=Math.round(tavg);

}
}

var qtydiff = boxqty1 - totalqty;
var ctsdiff = boxcts1 - totalcts;

var aa = boxcts1 * boxrte1;
var bb = totalcts * totalrte;
var rt = aa - bb;

if(ctsdiff != 0){
var newr = rt/ctsdiff;
} else {
var newr=0;
}



if(totalcts>boxcts1){
} else {

document.getElementById('newfromqty').value=qtydiff;  
document.getElementById('newfromcts').value=r2(ctsdiff);
document.getElementById('newfromrte').value=Math.round(newr);
var valdiff = ctsdiff * newr;
document.getElementById('newfromvalue').value=Math.round(valdiff);
}
document.getElementById('loading').innerHTML = "";
}

function splitNewEXISTING(field,count){
    if(field=='NEW'){
        document.getElementById('tovnm_'+count).value="0";
        document.getElementById('toqty_'+count).value="0";
        document.getElementById('tocts_'+count).value="0";
        document.getElementById('torte_'+count).value="0";
        document.getElementById('toavg_'+count).value="0";
        getsplitcalculation();
    }else{
        
    }
}


function validatesplitform(){
    var newfromcts=document.getElementById('newfromcts').value;
    if(newfromcts=='0'){
    alert('Please Perform Calculation or Mentation Trf Criteria');
    return false;
    }else{
        loading();
    }
}