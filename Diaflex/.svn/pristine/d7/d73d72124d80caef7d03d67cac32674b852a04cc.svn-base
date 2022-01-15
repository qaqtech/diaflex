function CalTtlOnChlick(stkIdn ,obj , isAll){
     var isChecked = obj.checked;
      if(isAll!='ALL'){
     var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
    var cts = document.getElementById('CTS_'+stkIdn).value;
    var qty = document.getElementById('QTY_'+stkIdn);
    var qtyVal=1;
    if(qty!=null)
    qtyVal = parseInt(document.getElementById('QTY_'+stkIdn).value);
    var ctsTtl = "";
    var qtyTtl = "";
    
    if(ctsDiv==0){
      ctsTtl = cts;
      qtyTtl = qtyVal;
    }else if(isChecked){
      qtyTtl = parseInt(qtyDiv)+qtyVal;
      ctsTtl = (parseFloat(ctsDiv) + parseFloat(cts));
    }else{
      qtyTtl = parseInt(qtyDiv)-qtyVal;
      ctsTtl =(parseFloat(ctsDiv) - parseFloat(cts));
    }
  }
 ctsTtl= new Number(ctsTtl).toFixed(3);
   document.getElementById('ctsTtl').innerHTML = ctsTtl;
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
    
}

function MixRtnCalCul(mstkIdn){
  var ttlQty=0;
  var ttlCts=0;
  var avgRte=0;
   var issQty=  parseInt(nvl(document.getElementById("qty_"+mstkIdn).value,"0"));
  var isscts = parseFloat(nvl(document.getElementById("cts_"+mstkIdn).value,"0"));
  var rtnQty=  parseInt(nvl(document.getElementById("QR_"+mstkIdn).value,"0"));
  var rtncts = parseFloat(nvl(document.getElementById("CR_"+mstkIdn).value,"0"));
    for(var i=1 ; i <=5 ;i++){
      var curQty =  parseInt(nvl(document.getElementById("QTY_"+mstkIdn+"_"+i).value,"0"));
      var curCts =  parseFloat(nvl(document.getElementById("CTS_"+mstkIdn+"_"+i).value,"0"));
      var curPrc =  parseFloat(nvl(document.getElementById("PRC_"+mstkIdn+"_"+i).value,"0"));
      ttlQty=ttlQty+curQty;
     var ttlVal =  ttlCts * avgRte;
     var curVal = curCts * curPrc;
      avgRte =(ttlVal+curVal)/(ttlCts+curCts);
      ttlCts=ttlCts+curCts;
      var curAmt = Math.round(curCts*curPrc);
      if(curAmt!=0)
      document.getElementById("AMT_"+mstkIdn+"_"+i).value = curAmt;
      
       var ttlPktQty = new Number(rtnQty+ttlQty);
       var ttlPktCts= new Number(rtncts+ttlCts).toFixed(3);
      if(issQty<ttlPktQty){
      alert("Please verify Qty.");
      document.getElementById("QTY_"+mstkIdn+"_"+i).value="";
      }
    if(isscts<ttlPktCts){
      alert("Please verify Carat.");
      document.getElementById("CTS_"+mstkIdn+"_"+i).value="";
    }
    }
    avgRte=Math.round(avgRte);
   
    document.getElementById("QS_"+mstkIdn).innerHTML=ttlQty;
    document.getElementById("CS_"+mstkIdn).innerHTML= new Number(ttlCts).toFixed(3);
    document.getElementById("PS_"+mstkIdn).innerHTML=avgRte;
    var amt =Math.round(ttlCts*avgRte);
    if(amt!=0){
     document.getElementById("AS_"+mstkIdn).innerHTML = amt;
     
    }
    if( document.getElementById("net_dis")!=null){
         document.getElementById("net_dis").innerHTML = amt;
    }
}

function MixRtnCalCulSRCHRSLT(mstkIdn){
  var ttlQty=0;
  var ttlCts=0;
  var avgRte=0;
  var isscts = parseFloat(nvl(document.getElementById("cur_cts_"+mstkIdn).value,"0"))+0.2;
  var issqty = parseInt(nvl(document.getElementById("cur_qty_"+mstkIdn).value,"0"))+0.2;
     for(var i=1 ; i <=10 ;i++){
      var curQty =  parseInt(nvl(document.getElementById("QTY_"+mstkIdn+"_"+i).value,"0"));
      var curCts =  parseFloat(nvl(document.getElementById("CTS_"+mstkIdn+"_"+i).value,"0"));
      var curPrc =  parseFloat(nvl(document.getElementById("PRC_"+mstkIdn+"_"+i).value,"0"));
      ttlQty=ttlQty+curQty;
     var ttlVal =  ttlCts * avgRte;
     var curVal = curCts * curPrc;
      avgRte =(ttlVal+curVal)/(ttlCts+curCts);
      ttlCts=ttlCts+curCts;
      var curAmt = Math.round(curCts*curPrc);
      if(curAmt!=0)
      document.getElementById("AMT_"+mstkIdn+"_"+i).value = curAmt;
      if(issqty<ttlQty){
        alert("Please verify Qty it can't more then "+issqty);
      document.getElementById("QTY_"+mstkIdn+"_"+i).value="";
       ttlQty=ttlQty-curQty;
      break;
      }
      
      if(isscts<ttlCts){
      alert("Please verify Carat it can't more then "+isscts);
      document.getElementById("CTS_"+mstkIdn+"_"+i).value="";
       ttlCts=ttlCts-curCts;
      break;
    
    }
      
    if(isscts<ttlCts){
      alert("Please verify Carat.");
      document.getElementById("CTS_"+mstkIdn+"_"+i).value="";
       ttlCts=ttlCts-curCts;
      break;
    
    }
    }
    avgRte=Math.round(avgRte);
   ttlCts=new Number(ttlCts).toFixed(2);
    document.getElementById("QS_"+mstkIdn).innerHTML=ttlQty;
    document.getElementById("CS_"+mstkIdn).innerHTML= ttlCts;
    document.getElementById("PS_"+mstkIdn).innerHTML=avgRte;
    document.getElementById("qty_"+mstkIdn).value=ttlQty;
    document.getElementById("cts_"+mstkIdn).value=ttlCts;
    document.getElementById("MixRte_"+mstkIdn).value=avgRte;

     updateGtCTSQt(mstkIdn,'qty_'+mstkIdn,'QTY',ttlQty);
     updateGtCTSQt(mstkIdn,'cts_'+mstkIdn,'CTS',ttlCts);
    var amt =Math.round(ttlCts*avgRte);
    if(amt!=0)
     document.getElementById("AS_"+mstkIdn).innerHTML = amt;
     
     
}

function PolctsValid(mstkIdn,rghCtsId,polCtsId){
var rghCts = document.getElementById(rghCtsId).value;
if(rghCts==''){
alert("Please specify Rough Carat.");
}else{
rghCts = parseFloat(nvl(rghCts,"0"));
var polCts = parseFloat(nvl(document.getElementById(polCtsId).value));
if(polCts > rghCts){
alert("Polish Carat ("+polCts+") not be more then  Rough Carat ("+rghCts+")");
document.getElementById(polCtsId).value="";
}
}
}

function RoughRtnCalCul(mstkIdn){
  var ttlOrgcts =  parseFloat(nvl(window.parent.document.getElementById("cts").value,"0"));

  var isValid = true;
  var ttlQty= parseInt(nvl(document.getElementById("qtylbh_"+mstkIdn).value,"0"));
  var ttlCts=parseFloat(nvl(document.getElementById("ctslbh_"+mstkIdn).value,"0"));
  var ttlPolQty= parseFloat(nvl(document.getElementById("qtypollbh_"+mstkIdn).value,"0"));
  var ttlPolCts=parseFloat(nvl(document.getElementById("ctspollbh_"+mstkIdn).value,"0"));
  var avgRte= parseFloat(nvl(document.getElementById("rtelbh_"+mstkIdn).value,"0"));
  var cnt = document.getElementById("cnt_"+mstkIdn).value
    for(var i=1 ; i <=cnt ;i++){
       var Qty=  parseInt(nvl(document.getElementById("QTY_"+mstkIdn+"_"+i).value,"0"));
      var cts = parseFloat(nvl(document.getElementById("CTS_"+mstkIdn+"_"+i).value,"0"));
      var polQty=  parseInt(nvl(document.getElementById("POLQTY_"+mstkIdn+"_"+i).value,"0"));
      var polcts = parseFloat(nvl(document.getElementById("POLCTS_"+mstkIdn+"_"+i).value,"0"));
       var rte = parseFloat(nvl(document.getElementById("RTE_"+mstkIdn+"_"+i).value,"0"));
      var ttlVal =  ttlPolCts * avgRte;
     var curVal = polcts * rte;
      avgRte =(ttlVal+curVal)/(ttlPolCts+polcts);
     ttlQty=ttlQty+Qty;
     ttlCts=ttlCts+cts;
     if(ttlOrgcts < ttlCts){
     alert("Please verify  rough carat ("+ttlCts+"). it can not be greater then "+ttlOrgcts);
     document.getElementById("CTS_"+mstkIdn+"_"+i).value="";
     ttlCts=ttlCts-cts;
     isValid=false;
     break;
     }
     ttlPolQty=ttlPolQty+polQty;
     ttlPolCts=ttlPolCts+polcts;
     
    
    }
    if(isValid){
   avgRte=Math.round(avgRte);
   document.getElementById("qtylb_"+mstkIdn).innerHTML=ttlQty;
   document.getElementById("ctslb_"+mstkIdn).innerHTML= new Number(ttlCts).toFixed(2);
   document.getElementById("qtypollb_"+mstkIdn).innerHTML=ttlPolQty;
   document.getElementById("ctspollb_"+mstkIdn).innerHTML=new Number(ttlPolCts).toFixed(2);
   document.getElementById("rtelb_"+mstkIdn).innerHTML=avgRte;
   window.parent.document.getElementById("rghcts").value= new Number(ttlCts).toFixed(2);
   window.parent.document.getElementById("polcts").value=new Number(ttlPolCts).toFixed(2);
   window.parent.document.getElementById("rghqty").value= ttlQty;
   window.parent.document.getElementById("polqty").value=ttlPolQty;
   }

   }

function VerifiedTotalRghCarat(obj){
    var lpCts=parseFloat(nvl(document.getElementById("lpCts").value,"0"));
    var rghcts=parseFloat(nvl(document.getElementById("rghcts").value,"0"));
    var rejectcts=parseFloat(nvl(document.getElementById("rejectcts").value,"0"));
    var cts=parseFloat(nvl(document.getElementById("cts").value,"0"));
    var ttlCts = lpCts+rghcts+rejectcts;
     ttlCts =get2DigitNum(ttlCts);
    if(ttlCts > cts){
        alert("Please verify total carat ("+ttlCts+"). it can not be greater than "+cts);
        obj.value="";
    }
}
function ALLCheckBoxCal( allCk , name ){
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
if(isChecked){
     document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
     document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
    }else{
     document.getElementById('ctsTtl').innerHTML = "0";
     document.getElementById('qtyTtl').innerHTML = "0";
    
    }
}


function CalTtlOnChlickLst(stkIdn ,obj , isAll){
     var isChecked = obj.checked;
      if(isAll!='ALL'){
     var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
    var cts = document.getElementById('CTS_'+stkIdn).value;
    var ctsTtl = "";
    var qtyTtl = "";
     var qty = document.getElementById('QTY_'+stkIdn);
    var qtyVal=1;
    if(qty!=null)
    qtyVal = parseInt(document.getElementById('QTY_'+stkIdn).value);
    if(ctsDiv==0){
      ctsTtl = cts;
      qtyTtl = qtyVal;
    }else if(isChecked){
      qtyTtl = parseInt(qtyDiv)+qtyVal;
      ctsTtl = (parseFloat(ctsDiv) + parseFloat(cts));
    }else{
      qtyTtl = parseInt(qtyDiv)-qtyVal;
      ctsTtl =(parseFloat(ctsDiv) - parseFloat(cts));
    }
  }
   document.getElementById('ctsTtl').innerHTML = ctsTtl;
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
     var lstNme  = document.getElementById('lstNme').value;
   var url = "ajaxAssortAction.do?method=GtList&stkIdn="+stkIdn+"&stt="+isChecked+"&lstNme="+lstNme ;
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


function ALLCheckBoxCalLst( allCk , name ){
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
if(isChecked){
     document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
     document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
    }else{
     document.getElementById('ctsTtl').innerHTML = "0";
     document.getElementById('qtyTtl').innerHTML = "0";
    
    }
    
     var lstNme  = document.getElementById('lstNme').value;
   var url = "ajaxAssortAction.do?method=GtList&stkIdn=ALL&stt="+isChecked+"&lstNme="+lstNme ;
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

function refineParam(lprp,obj,typ){
var srt=0; 
var valFm='';
var valTo='';
var isChecked='';

if(typ=='C'){
    valFm = obj.value;
    valTo = valFm;
    isChecked = obj.checked;
}else{
  valFm = document.getElementById(lprp+"_MIN").value;
  valTo = document.getElementById(lprp+"_MAX").value;

}

 var url = "ajaxsrchAction.do?method=refScrh&lprp="+escape(lprp)+"&valFm="+escape(valFm)+"&valTo="+escape(valTo)+"&typ="+escape(typ)+"&isChecked="+isChecked;
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
function refineselectall(lprp,lprpfld){
var isChecked = document.getElementById(lprp+'_REFINE').checked;
var tab = document.getElementById ("refineTblALL"); // get table  id  which contain check box
var elems = tab.getElementsByTagName ("input");
for ( var i = 0; i < elems.length; i++ ){
if ( elems[i].type =="checkbox"){
       var fieldId = elems[i].id;
       if(fieldId.indexOf(lprpfld)!=-1){
       if(isChecked){
       document.getElementById(fieldId).checked = true;
       }else{
       document.getElementById(fieldId).checked = false;
       }
       }
}
}
        var url = "ajaxsrchAction.do?method=refScrh&lprp="+escape(lprp)+"&valFm=ALL&valTo=ALL&typ=C&isChecked="+isChecked;
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

function AssortTotalCalNew(obj , cts , stkIdn, vlu,rapvlu){
    var isChecked = obj.checked;
    var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
    var avgDiv = document.getElementById('avgTtl').innerHTML;
    var vluDiv = document.getElementById('vluTtl').innerHTML;
    var rapvluDiv = document.getElementById('rapvluTtl').innerHTML;
    var ttlcts = document.getElementById('ttlcts').innerHTML;
    
    var ctsTtl = "";
    var qtyTtl = "";
    var avgTtl = "";
    var vluTtl = "";
    var rapvluTtl = "";
    if(ctsDiv==0){
      ctsTtl = cts;
      qtyTtl = 1;
      vluTtl=parseFloat(vlu);
      rapvluTtl=parseFloat(rapvlu);
    }else if(isChecked){
      qtyTtl = parseInt(qtyDiv)+1;
      ctsTtl = (parseFloat(ctsDiv) + parseFloat(cts));
      vluTtl=formatNumber((parseFloat(vluDiv) + parseFloat(vlu)),2);
      rapvluTtl=formatNumber((parseFloat(rapvluDiv) + parseFloat(rapvlu)),2);
    }else{
      qtyTtl = parseInt(qtyDiv)-1;
      ctsTtl =(parseFloat(ctsDiv) - parseFloat(cts));
      vluTtl=formatNumber((parseFloat(vluDiv) - parseFloat(vlu)));
      rapvluTtl=formatNumber((parseFloat(rapvluDiv) - parseFloat(rapvlu)),2);
    }
     avgTtl=formatNumber((parseFloat(vluTtl) / parseFloat(ttlcts)),2);
     
     if(parseInt(qtyTtl)==0){
        avgTtl='0' ;
        vluTtl='0' ;
        rapvluTtl='0' ;
     }
     
     document.getElementById('ctsTtl').innerHTML = (new Number(ctsTtl)).toFixed(2);
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
     document.getElementById('vluTtl').innerHTML = vluTtl;
     document.getElementById('rapvluTtl').innerHTML = rapvluTtl;
     document.getElementById('avgTtl').innerHTML = avgTtl;
}
function verifedTtlVal(){
     var ttlMtVal = document.getElementById('TTLCTSMT').innerHTML;
      if(ttlMtVal=='')
     ttlMtVal = 0;
     ttlMtVal = parseFloat(ttlMtVal,10);
     var  ttlVal = document.getElementById('ttlcts').innerHTML;
     if(ttlVal=='')
     ttlVal = 0;
     ttlVal =parseFloat(ttlVal,10);
     
      var vari =parseFloat(document.getElementById('WT_DIFF').value);
      if(vari==null && vari=='')
      vari=0.1;
     var ttlMtVrVal = (new Number(ttlMtVal+vari)).toFixed(3);
       var frm_elements = document.forms[1].elements; 
   
     for(i=0; i<frm_elements.length; i++) {
     field_type = frm_elements[i].type.toLowerCase();
     if(field_type=='text') {
     var fieldId = frm_elements[i].id;
     if(fieldId.indexOf('CTS_')!=-1){
     var curCts = (frm_elements[i].value).trim();
     if(curCts!=''){
           var rteFldId = fieldId.replace('CTS','RTE');
           var rteVal = document.getElementById(rteFldId).value;
            
           if(rteVal==''){
           var lst= rteFldId.split("_");
           if(lst.length==3){
            var boxIdVal = lst[2];
           }else{
           var boxId=rteFldId.replace('RTE','BOXTXT_ID1');
             boxIdVal = document.getElementById(boxId).value;
           }
               alert("Please Specifiy price For Box ID:-"+boxIdVal);
               return false;
           }
     }
     }}}
 
   
   if(ttlVal>=ttlMtVal && ttlVal <=ttlMtVrVal){
      var r = confirm("Do you want to save Changes?");
       return r;
      
   }else{
   alert("Varification failed : Total Carat="+ttlMtVal+" and Calculated Carat="+ttlVal );
       return false;
   }
}

function calCullateTtt(obj,ttlMtId,ttlId,fldId,typ,frm){
      var ttlMtVal = document.getElementById(ttlMtId).innerHTML;
      if(ttlMtVal=='')
     ttlMtVal = 0;
     ttlMtVal = new Number(ttlMtVal);
     
    var vari =parseFloat(document.getElementById('WT_DIFF').value);
      if(vari==null && vari=='')
      vari=0.1;
     var ttlMtVrVal = (new Number(ttlMtVal+vari)).toFixed(3);
      
      
     var  ttlVal = document.getElementById(ttlId).innerHTML;
     if(ttlVal=='')
     ttlVal = 0;
     ttlVal = new Number(ttlVal);
    var frm_elements = document.forms[frm].elements; 
    var calTtl=0;
    var rtRte=0;
    var calQty=0;
     for(i=0; i<frm_elements.length; i++) {
     field_type = frm_elements[i].type.toLowerCase();
     if(field_type=='text') {
     var fieldId = frm_elements[i].id;
     if(fieldId.indexOf(fldId)!=-1){
    
     var curCts = parseFloat(nvl((frm_elements[i].value).trim(),"0"));
     var rteFldId = fieldId.replace('CTS','RTE');
     var qtyFldId = fieldId.replace('CTS','QTY');
     var oldCtsFldId = fieldId.replace('CTS','OLD');
      var diffCtsFldId = fieldId.replace('CTS','DIFF');
     var curQty = parseInt(nvl(document.getElementById(qtyFldId).value,0));
    var curRte = parseFloat(nvl(document.getElementById(rteFldId).value,0));
    
       var ttlAmtVal =  calTtl * rtRte;
       var curAmtVal = curCts * curRte;
       var ctsAdd = parseFloat(calTtl+curCts);
    
       if(ctsAdd > 0)
      rtRte =parseFloat((ttlAmtVal+curAmtVal)/(calTtl+curCts));
      
     calTtl = parseFloat(calTtl+curCts);
      calQty = parseInt(calQty+curQty);
      var oldCtsfld = document.getElementById(oldCtsFldId);
    
      if(oldCtsfld!=null){
       var oldcts = parseFloat(nvl(document.getElementById(oldCtsFldId).value,0));
       var diff = curCts-oldcts;
    
       document.getElementById(diffCtsFldId).value=diff;
      }
     }
     }
    }
    if(parseFloat(calTtl) > parseFloat(ttlMtVrVal)){

   var curVal = new Number(obj.value);
    var remain = (new Number(ttlMtVrVal -(calTtl-curVal))).toFixed(3);
        alert("You can't enter more then :-"+ttlMtVrVal+" Remaining Carat :-"+remain);
        obj.value='';
    }else{
     document.getElementById(ttlId).innerHTML=new Number(calTtl).toFixed(3);
      document.getElementById('AVGRTE').innerHTML=Math.round(rtRte);
      document.getElementById("ttlqty").innerHTML=calQty;
     curVal = new Number(obj.value);
     remain = (new Number(ttlMtVal -(calTtl))).toFixed(3);
      document.getElementById('rmncts').innerHTML=remain;
     
    }
    
}

function calCulateAvgRte(frm,fldId){
      
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
    
}
function checkBoxIDFld(obj,fldId){
    var boxId =  document.getElementById(fldId).value;
    if(boxId==''){
        alert("please select Box ID");
        obj.value="";
    }
}
function chksrvAllFrm(name,frm) {
var idval=document.getElementById(name).value;
var frm_elements = document.forms[frm].elements;
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

if(field_type=='select-one') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf(name)!=-1){
var fdisable=document.getElementById(fieldId).disabled;
if(!fdisable){
document.getElementById(fieldId).value=idval;
}
}
}
}
}

function chksrvAlltextFrm(name,frm) {
var idval=document.getElementById(name).value;
var frm_elements = document.forms[frm].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
var fieldId = frm_elements[i].id;
if(field_type=='text') {
if(fieldId.indexOf(name)!=-1){
document.getElementById(fieldId).value=idval;
}
}
}
}
function validatepacket(){
var isChecked = false;
var v=document.getElementById('pid').value;
if((v!='' && v!=0)) {
isChecked = true;
}
if(!isChecked){
alert("Please Enter Packet Id.");
document.getElementById('pid').value='';
document.getElementById('pid').focus();

}
return isChecked;
}

function MatchReturnCts(idn){
    var issCts = nvl(document.getElementById('ISSCTS_'+idn).value,0);
    var rtnCts = nvl(document.getElementById('RTNCTS_'+idn).value,0);
    issCts =issCts *0.5;
    if(rtnCts>issCts){
        alert("Return carat is more then Issue. it will not be return..");
        document.getElementById('RTNCTS_'+idn).value='';
    }
}
function MatchReturnQty(idn){
    var issQty = nvl(document.getElementById('ISSQTY_'+idn).value,0);
    var rtnQty = nvl(document.getElementById('RTNQTY_'+idn).value,0);
   
    if(rtnQty>issQty){
        alert("Return qty is more then qty. it will not be return..");
        document.getElementById('RTNQTY_'+idn).value='';
    }
}
function nvl(val,rplVal){
  var rtnVal = val
  if(val=='')
    rtnVal=rplVal;
    
    return rtnVal;
      
  }
  
  
function caratDiff(typ){
  var pktcts = parseFloat(nvl(document.getElementById('pktcts').value,'0'));
  var pktctshd = parseFloat(nvl(document.getElementById('pktctshd').value,'0'));
  var pktctsiss = parseFloat(nvl(document.getElementById('pktctsiss').value,'0'));
  if(pktctshd > pktcts){
      alert("Carat can not be more then :-"+pktcts);
      document.getElementById('pktctshd').value="";
      document.getElementById('pktctshd').focus();
  }else if(pktctsiss > pktcts){
        alert("Carat can not be more then :-"+pktcts);
        document.getElementById('pktctsiss').value="";
      document.getElementById('pktctsiss').focus();
  }else{
  if(typ=='HD'){
      pktctsiss = pktcts-pktctshd;
  }else{
      pktctshd = pktcts-pktctsiss;
  }
  document.getElementById('pktctshd').value=new Number(pktctshd).toFixed(3);
  document.getElementById('pktctsiss').value=new Number(pktctsiss).toFixed(3);
  }
}

function qtyDiff(typ){
  var pktqty = parseFloat(nvl(document.getElementById('pktqty').value,'0'));
  var pktqtyhd = parseFloat(nvl(document.getElementById('pktqtyhd').value,'0'));
  var pktqtyiss = parseFloat(nvl(document.getElementById('pktqtyiss').value,'0'));
  if(pktqtyhd > pktqty){
      alert("Qty can not be more then :-"+pktqty);
      document.getElementById('pktqtyhd').value="";
      document.getElementById('pktqtyhd').focus();
  }else if(pktqtyiss > pktqty){
        alert("Qty can not be more then :-"+pktqty);
        document.getElementById('pktqtyiss').value="";
      document.getElementById('pktqtyiss').focus();
  }else{
  if(typ=='HD'){
      pktqtyiss = pktqty-pktqtyhd;
  }else{
      pktqtyhd = pktqty-pktqtyiss;
  }
  document.getElementById('pktqtyhd').value=new Number(pktqtyhd).toFixed(0);
  document.getElementById('pktqtyiss').value=new Number(pktqtyiss).toFixed(0);
  }
}
function CalTotalIssqty(){
    var memoqtyIss = parseFloat(nvl(document.getElementById('memoqtyIss').value,'0'));
    var memoqtyRtn = parseFloat(nvl(document.getElementById('memoqtyRtn').value,'0'));
    var ttlqty = memoqtyIss+memoqtyRtn;
    document.getElementById('memoqtyTtl').value=new Number(ttlqty).toFixed(0);
    
}
function CalTotalIsscarat(){
    var memoctsIss = parseFloat(nvl(document.getElementById('memoctsIss').value,'0'));
    var memoctsRtn = parseFloat(nvl(document.getElementById('memoctsRtn').value,'0'));
    var ttlcts = memoctsIss+memoctsRtn;
    document.getElementById('memoctsTtl').value=new Number(ttlcts).toFixed(3);
    
}


function DiffCtscal(oldCtsId,diffId,newCtsId){
    var oldcts = parseFloat(nvl(document.getElementById(oldCtsId).value,'0'));
    var diffval = parseFloat(nvl(document.getElementById(diffId).value,'0'));
    var newcts = oldcts+(diffval);
    document.getElementById(newCtsId).value=new Number(newcts).toFixed(2);
    var ctsId =document.getElementById(newCtsId);
    calCullateTtt(ctsId,'TTLCTSMT','ttlcts','CTS_','F','1');
}

function stockTallyRtnCal(calId,lbNme){
    var ttlVal=0;
    var frm_elements = document.forms[1].elements;
    for(i=0; i<frm_elements.length; i++) {
         field_type = frm_elements[i].type.toLowerCase();
        var fieldId = frm_elements[i].id;
       if(fieldId.indexOf(calId)!=-1){
           ttlVal =ttlVal+parseFloat(nvl(frm_elements[i].value,'0'));
    
        }}
   if(lbNme=='ttlRtnQty'){
        document.getElementById(lbNme).innerHTML=new Number(ttlVal).toFixed(0);
   }
   if(lbNme=='ttlRtnCts'){
        document.getElementById(lbNme).innerHTML=new Number(ttlVal).toFixed(3);
   }


}

function stockTallyRtnCalPkt(){
    
    var ttlQty = parseInt(nvl(document.getElementById('ttlRtnLbQty').value,'0'));
    var ttlCts =  parseFloat(nvl(document.getElementById('ttlRtnLbCts').value,'0'));
   var sttlQty=0;
    var sttlCts=0.0;
    var frm_elements = document.forms[1].elements;
    for(i=0; i<frm_elements.length; i++) {
         field_type = frm_elements[i].type.toLowerCase();
        if(field_type=='checkbox'){
        var fieldId = frm_elements[i].id;
       if(fieldId.indexOf('CHK_')!=-1){
       if(frm_elements[i].checked){
       var stkIdn = frm_elements[i].value;
       var rtnQty = parseInt(nvl(document.getElementById('RTNQTY_'+stkIdn).value));
       var rtnCts = parseFloat(nvl(document.getElementById('RTNCTS_'+stkIdn).value));
        sttlQty = sttlQty+rtnQty;
        sttlCts = sttlCts+rtnCts;
       }
       }
       }}
        document.getElementById('sttlRtnQty').innerHTML =sttlQty;
      document.getElementById('sttlRtnCts').innerHTML=new Number(sttlCts).toFixed(2);
      ttlCts=ttlCts+sttlCts;
      ttlQty=ttlQty+sttlQty;
      document.getElementById('ttlRtnQty').innerHTML =ttlQty;
      document.getElementById('ttlRtnCts').innerHTML=new Number(ttlCts).toFixed(2);
       
    }
    


function setTallyLb(obj,setId){
    var val = nvl(obj.value,'0');
    document.getElementById(setId).value=val;
}

function calculatepurselrej(purStkIdn){
    var selCts=0;
    var selAVlu=0;
    var rejCts=0;
     var frm_elements = document.forms[1].elements;
    for(i=0; i<frm_elements.length; i++) {
         field_type = frm_elements[i].type.toLowerCase();
        if(field_type=='radio'){
        var fieldId = frm_elements[i].id;
       if(fieldId.indexOf('RDSEL@'+purStkIdn+'@')!=-1){
       if(frm_elements[i].checked){
       var rdVal = frm_elements[i].value;
       var rdSplit = rdVal.split("_");
       var stkIdn = rdSplit[1];
       var cts =  parseFloat(document.getElementById('CTS@'+purStkIdn+'@'+stkIdn).value);
        var val =  parseFloat(document.getElementById('VLU@'+purStkIdn+'@'+stkIdn).value);
       selCts=selCts+cts;
      selAVlu=selAVlu+val;
       
       }}
       
       if(fieldId.indexOf('RDREJ@'+purStkIdn+'@')!=-1){
       if(frm_elements[i].checked){
        var rdVal = frm_elements[i].value;
       var rdSplit = rdVal.split("_");
       var stkIdn = rdSplit[1];
       var cts =  parseFloat(document.getElementById('CTS@'+purStkIdn+'@'+stkIdn).value);
       rejCts=rejCts+cts;
     
       }}
       
       
       }}
       
       var fnlRte = parseFloat(document.getElementById('FNLRTE_'+purStkIdn).value);
       var selVlu = fnlRte*selCts;
       var rejVlu = fnlRte*rejCts;
       var avgRte = selAVlu/selCts;
       document.getElementById('SELCTS_'+purStkIdn).value=new Number(selCts).toFixed(2);
       document.getElementById('SELVLU_'+purStkIdn).value=new Number(selVlu).toFixed(0);
        document.getElementById('ASELCTS_'+purStkIdn).value=new Number(selCts).toFixed(2);
       document.getElementById('ASELVLU_'+purStkIdn).value=new Number(selAVlu).toFixed(0);
         document.getElementById('AFNLRTE_'+purStkIdn).value=new Number(avgRte).toFixed(0);
       document.getElementById('REJCTS_'+purStkIdn).value=new Number(rejCts).toFixed(2);
       document.getElementById('REJVLU_'+purStkIdn).value=new Number(rejVlu).toFixed(0);
       document.getElementById('REJVLU_'+purStkIdn).value=new Number(rejVlu).toFixed(0);
       
}

function changePurVlue(obj,purStkIdn){
    var rte = obj.value;
    var cts = parseFloat(document.getElementById('SELCTS_'+purStkIdn).value);
     var rejcts = parseFloat(document.getElementById('REJCTS_'+purStkIdn).value);
     document.getElementById('SELVLU_'+purStkIdn).value=new Number(rte*cts).toFixed(0);
        document.getElementById('REJVLU_'+purStkIdn).value=new Number(rte*rejcts).toFixed(0);
}

function verifyPurConfrim(){
 var isVerified = false;
  var vnm="";
 var frm_elements = document.forms[1].elements;
    for(i=0; i<frm_elements.length; i++) {
         field_type = frm_elements[i].type.toLowerCase();
        if(field_type=='checkbox'){
        var fieldId = frm_elements[i].id;
       if(fieldId.indexOf('cb_pur_')!=-1){
       if(frm_elements[i].checked){
       var purIdn =frm_elements[i].value;
       var vnmpur = document.getElementById('VNM@'+purIdn).value;
        vnm = vnm+" , "+vnmpur;
        isVerified = true;
       }
          }
        }}
    if(isVerified){
    isVerified = confirm("Purchase confirmation done for :"+vnm+"  Are You Sure You Want To Save Chnages?");
       
    }else{
         alert("Please selected purchase.");
    }
    
    return isVerified;
}


function verifyBulkSplitCarats(obj){
  
    var remcts =   parseFloat(document.getElementById('remcts').innerHTML);
    remcts=remcts+0.20;
    var cts = parseFloat(obj.value);
    if(cts > remcts){
        alert("carat is  more than Available carat lot."+remcts);
        obj.value='';
    }
    
}

function verifyBulkSplitQty(obj){
    var remQty =  parseInt(document.getElementById('remqty').innerHTML);
    var qty = parseFloat(obj.value);
    if(qty > remQty){
        alert("Qty is more than Available qty lot."+remQty);
        obj.value='';
    }
    
}

function verifyBulkSplitRGHCarats(stkIdn,obj){
  
    var remcts =   parseFloat(nvl(document.getElementById('remcts').innerHTML,'0'));
    var cts = parseFloat(nvl(obj.value,'0'));
    if(cts > remcts){
        alert("carat is  more than Available carat lot."+remcts);
        obj.value='';
    }else{
    var rhgCts =parseFloat(nvl($('#'+stkIdn+"_POL_CTS", window.parent.document).val(),'0'));
    rhgCts=rhgCts+cts;
    $('#'+stkIdn+"_POL_CTS", window.parent.document).val(rhgCts);
    }
    
}

function verifyBulkSplitRGHQty(stkIdn,obj){
    var remQty =  parseInt(nvl(document.getElementById('remqty').innerHTML,'0'));
    var qty = parseInt(nvl(obj.value,'0'));
    if(qty > remQty){
        alert("Qty is more than Available qty lot."+remQty);
        obj.value='';
    }else{
     var rhgQty =parseInt(nvl($('#'+stkIdn+"_RGH_QTY", window.parent.document).val(),'0'));
    rhgQty=rhgQty+qty;
    $('#'+stkIdn+"_RGH_QTY", window.parent.document).val(rhgQty);
    }
    
}

function VerifedRGHTtl(obj,stk_idn,cts,sr){
       var remcts = parseFloat(obj.value);
      var rghCs = parseFloat(document.getElementById(stk_idn+"_POL_CTS").value);
      var ttlCts = parseFloat(remcts+rghCs)
      var ttlAvCts = parseFloat(cts);
       var blncts = ttlAvCts-rghCs;
        blncts =new Number(blncts).toFixed(3);
      if(ttlAvCts < ttlCts){
         
          alert("Rejected Carat can't be greater then "+blncts);
          obj.value="";
           document.getElementById("WT_LOSS_"+sr).value="";
                document.getElementById('CHK_'+stk_idn).disabled=true;
      }else{
       document.getElementById("WT_LOSS_"+sr).value=blncts;
     document.getElementById('CHK_'+stk_idn).disabled=false;
      }
}


function totalAmountCal(){
    var frm_elements = document.forms[0].elements;
    var ttlCts=0;
    var ttlVal=0;
   for(i=0; i<frm_elements.length; i++) {
     var field_type = frm_elements[i].type.toLowerCase();
   if(field_type=='text'){
   var fieldId = frm_elements[i].id;
    if(fieldId.indexOf('CTS_')!=-1){
     var rteId =fieldId.replace("CTS_","RTE_") ;
     var cts = frm_elements[i].value;
     if(cts=='')
     cts="0";
     var rte = document.getElementById(rteId).value;
     if(rte=='')
     rte="0";
     var ctsF = parseFloat(cts);
     var rteF = parseFloat(rte);
     var vluF = ctsF*rteF;
     ttlCts=ttlCts+ctsF;
     ttlVal=ttlVal+vluF;
     
     }
   }}
    document.getElementById('cts').innerHTML=new Number(ttlCts).toFixed(2);
     document.getElementById('vlu').innerHTML=new Number(ttlVal).toFixed(2);
}