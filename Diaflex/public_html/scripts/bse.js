
document.onkeypress = checkKeycode
function checkKeycode(e)
{
    e = e || window.event;
 
    if (e.keyCode == 27)
    {
     var obj = document.getElementById("multiPrp");
   for(var i=0;i<obj.length;i++){
    var div =  obj.options[i].value ;
    document.getElementById(div).style.display='none';
    document.getElementById('IMGD_'+div).style.display = 'block';
    document.getElementById('IMGU_'+div).style.display = 'none';
    }
    } 
    
   
}
   function checkBoxVald(){
      
          
var bool=false;

var frm_elements = document.forms['multi'].elements;

for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('cb_img_')!=-1){
if(frm_elements[i].checked){
bool = true;
}
}
}
}
if(!bool){
  alert("Please Select Image Type");
  return false;
  }
  return true;
 }


function sfromcal(){ 
  alert("Select Date From Calendar.");
  document.getElementById('cid').focus();
}

function validatememo(){
var isChecked = false;
var v=document.getElementById('pak').value;
var v1=document.getElementById('memoid').value;
if((v!='' && v!=0) || (v1!='' && v1!=0)) {
  isChecked = true;
}
 if(!isChecked){
  alert("Please Enter Memo Id or Packet No.");
  document.getElementById('memoid').value='';
document.getElementById('memoid').focus();
  
}
  return isChecked;
}


function isEmail(fld) {
   var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;  
   return emailPattern.test(fld.value);  
}
function setDfltVal(fld, val) {
  setDfltValFctr(fld, val, 1);
  var imgs = new Array();
  
}
function setDfltValFctr(fld, val, fctr) {
  if(fctr > 1)
    document.getElementById(fld).value = val/fctr;
  else
    document.getElementById(fld).value = val;
}
function isNumberRangeFctr(fld, lmtFr, lmtTo, fctr) {
  //alert('checking number for '+fld);
  var fldId = document.getElementById(fld);
  var fldVal = fldId.value;
  var chk = isNaN(fldId.value);
  var clr = 'no';
  if(chk == true) {
    alert('Kindly enter a valid number');
    clr = 'yes';
    
  } else {
    if(lmtTo > 0) {
      lmtTo = lmtTo/fctr;
      lmtFr = lmtFr/fctr;
      if((fldVal < lmtFr) || (fldVal > lmtTo)) {
        alert(' Valid range is between '+ lmtFr + ' and '+ lmtTo);    
        clr = 'yes';
      }
    }  
  }
  if(clr == 'yes') {
    fldId.value = '';
    fldId.focus();
  }
}
function isNumberRange(fld, lmtFr, lmtTo) {
  isNumberRange(fld, lmtFr, lmtTo, 1);
}
function isNumber(fld) {
  isNumberRange(fld, 0, 0, 1);
}

function checkoldSrchId(){
var oldsrchId = document.getElementById('oldsrchId').value;
if(oldsrchId==""){
alert('Please Click on Search Id');
return false;
}else{
return true;
}
}
 function checkLab(){
var isChecked = false;
var val=document.getElementById('LAB_PRC_1').value;
if(val!="")
isChecked=true;
if(isChecked){
return true;
}else{
alert("Please Select Lab.");
return false;
}
}

function DisplayDiv(objId){
   

  var stt=document.getElementById(objId).style.display
  
  if(stt=='block'){
      document.getElementById(objId).style.display='none';
  }else{
      document.getElementById(objId).style.display='block';
  }
   document.getElementById('IMGU_'+objId).style.display = 'block';
    document.getElementById('IMGD_'+objId).style.display = 'none';
  multiPleDiv(objId);
}

function multiPleDiv(disObj){
 
   var obj = document.getElementById("multiPrp");
   for(var i=0;i<obj.length;i++){
    var div =  obj.options[i].value ;
    if(div!=disObj){
           document.getElementById(div).style.display='none';
           document.getElementById('IMGD_'+div).style.display = 'block';
            document.getElementById('IMGU_'+div).style.display = 'none';
      }
   }
}

function Display(objId){

  var stt=document.getElementById(objId).style.display
  
  if(stt=='block'){
      document.getElementById(objId).style.display='none';
  }else{
      document.getElementById(objId).style.display='block';
  }
   document.getElementById('IMGU_'+objId).style.display = 'block';
    document.getElementById('IMGD_'+objId).style.display = 'none';
}
function setCrtWt(srt, wtFr, wtTo) {
    var chkFld = 'sz_'+srt;
    var chkFrm = 'wt_fr_'+srt;
    var chkTo = 'wt_to_'+srt;
   
    var fld = document.getElementById(chkFld);
    
    var fldFr = document.getElementById(chkFrm);
    
    var fldTo = document.getElementById(chkTo);
    if(fld.checked) {
        fldFr.value = wtFr;
        fldTo.value = wtTo;
    } else {
       fldFr.value = '';
       fldTo.value = '';
    }
}
function isNumericDecimal(obj)
  {
	  var checkOK = "0123456789./-";
           var object = document.getElementById(obj);
	  var checkStr = document.getElementById(obj).value;
	  var allValid = true;
	  for (i = 0;  i < checkStr.length;  i++)
	  {
    	ch = checkStr.charAt(i);
		    for (j = 0;  j < checkOK.length;  j++)
		      if (ch == checkOK.charAt(j))
        		break;
		    if (j == checkOK.length)
		    {
	    	  allValid = false;
    		  break;
		    }
  	}
            if (!allValid)
  {
    alert("Please enter only numbers in the field.");
     object.focus();
   document.getElementById(obj).value = "";
    return (false);
  }
                
                  
            if(checkStr!=""){
          
                var strarry = obj.split("_");
                var txtid = strarry[0]+"_2";
                document.getElementById(txtid).value=checkStr;
    	   }else{
                var strarry = obj.split("_");
                var txtid = strarry[0]+"_2";
                document.getElementById(txtid).value = "";
        	   
    	   }
		  return true;
	} 	 	
  
function Hide(objId){
    document.getElementById(objId).style.display='none';
    document.getElementById('IMGD_'+objId).style.display = 'block';
     document.getElementById('IMGU_'+objId).style.display = 'none';
}

function hideObj(objId){
    document.getElementById(objId).style.display='none';
}

function isPagination(iPgN,cPgN){

    document.getElementById('pagination').value="YES";
    document.getElementById('iPageNo').value=iPgN;
    document.getElementById('cPageNo').value=cPgN;
}

function setFrToCheck(frId,toId,lprp){
   var frDp = document.getElementById(frId);
   var toDp = document.getElementById(toId);
   
   var frDpLen = frDp.length;
   var frRng = frDp.selectedIndex;
   var toRng = toDp.selectedIndex;
    document.getElementById('MTXT_'+lprp).value = "";
  for(var j=1;j<=frDpLen;j++ ){
      var srt = frDp.options[j].value;
      var chObj = document.getElementById(lprp+"_"+srt)
      if((frRng <= j)&&(j <= toRng)){
      
           chObj.checked = true;
       }else{
           chObj.checked = false;
      }
      
      checkPrp(chObj,'MTXT_'+lprp,lprp);
  }
    
}

function setFrToCheckNewpage(frId,toId,lprp){
   var frDp = document.getElementById(frId);
   var toDp = document.getElementById(toId);
   
   var frDpLen = frDp.length;
   var frRng = frDp.selectedIndex;
   var toRng = toDp.selectedIndex;
  for(var j=1;j<=frDpLen;j++ ){
      var srt = frDp.options[j].value;
      var chObj = document.getElementById(lprp+"_"+srt)
      if((frRng <= j)&&(j <= toRng)){
      
           chObj.checked = true;
       }else{
           chObj.checked = false;
      }
      
      checkPrp(chObj,'MTXT_'+lprp,lprp);
  }
    
}

function setFrToSameVal(f1, f2,typ) {
 if(typ=='F'){
     document.getElementById(f2).value = document.getElementById(f1).value;
    
 }else{
       document.getElementById(f1).value = document.getElementById(f2).value;
 }

}
function setFrTo(f1, f2) {
    if(document.getElementById(f2).value == 0){
        document.getElementById(f2).value = document.getElementById(f1).value;
    }else
        chkFrTo(f1, f2);
}   

function chkFrTo(f1, f2) {

    var val1 = document.getElementById(f1).value ;
    var val2 = document.getElementById(f2).value ;
    if(isNaN(parseInt(val1))) {
        val1 = 0 ;    
    }
    if(isNaN(parseInt(val2))) {
        if(f1.substring('RTE') > 0)
            val2 = 1000000 ;    
        else    
            val2 = 100 ;    
    }
    
    try {
        //alert(val1 + " - "+ val2) ;   
        if(parseFloat(val1) > parseFloat(val2)) {
            document.getElementById(f1).value = val2 ;
            document.getElementById(f2).value = val1 ;
        }

    } catch(err) {
        alert(err.description);
    }
}

function checkPrpCT(obj,prp){
 var isChecked = obj.checked;
    var txtFld = document.getElementById("MTXT_CRTWT").value;
  
    var fld="";
    if(isChecked){
       
        fld = obj.value;
        if(txtFld=='ALL')
          txtFld= '';
        if(txtFld==''){
             txtFld = fld;
        }else{
        txtFld = txtFld+' , '+fld;}
        document.getElementById("MTXT_CRTWT").value = txtFld;
    }else{
         fld = obj.value;
      
       var indxFld = txtFld.indexOf(fld);
       
        if(indxFld==0){
      
            if(txtFld.indexOf(',') != -1){
             txtFld = txtFld.replace(' , '+fld,'');
            }else{
                 txtFld = txtFld.replace(fld,'');
            }
        }else{
        txtFld = txtFld.replace(' , '+fld,'');
        }
        document.getElementById("MTXT_CRTWT").value = txtFld;
    }
   

}


function checkPrp(obj,prp,lprp){
 
    var isChecked = obj.checked;
    var txtFld = document.getElementById(prp).value;
    if(txtFld == 'ALL')
      txtFld = '';
    var fld="";
    if(isChecked){
        fld = obj.value;
        if(txtFld==''){
             txtFld = fld;
        }else{
        txtFld = txtFld+' , '+fld;}
        document.getElementById(prp).value = txtFld;
    }else{
         fld = obj.value;
        var indxFld = txtFld.indexOf(fld);
        if(indxFld==0){
            if(txtFld.indexOf(',')!= -1){
             txtFld = txtFld.replace(fld+' , ','');
            }else{
                 txtFld = txtFld.replace(fld,'');
            }
        }else{
        txtFld = txtFld.replace(' , '+fld,'');
        }
        if(txtFld.length==0)
           txtFld = 'ALL';
        document.getElementById(prp).value = txtFld;
    }
  
}

function setCrtWtSrch(srt, wtFr, wtTo , obj , prp) {
  var txtCwFt = document.getElementById("wt_fr_c").value;
  var txtCwTo = document.getElementById("wt_to_c").value;
   var chkFld = 'CRTWT_'+srt;
    var chkFrm = 'CRTWT_1_'+srt;
    var chkTo = 'CRTWT_2_'+srt;
  if(txtCwFt!="" || txtCwTo!=""){
      
     
     var r = confirm("Do you want to Remove carat from text box");
     if(r){
         document.getElementById("wt_fr_c").value = "";
          document.getElementById("wt_to_c").value = "";
          document.getElementById("MTXT_CRTWT").value="";
     }else{
     document.getElementById(chkFld).checked = false;
      return false;
     }
  }
  
   
    
    var fld = document.getElementById(chkFld);
    var fldFr = document.getElementById(chkFrm);
    var fldTo = document.getElementById(chkTo);
    if(fld.checked) {
        fldFr.value = wtFr;
        fldTo.value = wtTo;
    } else {
       fldFr.value = '';
       fldTo.value = '';
    }
   
    checkPrpCT(obj,prp);
}

function writeText(obj,prp,typ){

   var fld = obj.value;
  var txtFld = document.getElementById(prp).value;

  if(txtFld =='ALL')
      txtFld = '';
    if(txtFld==''){
     txtFld = fld;
     }else{
     txtFld = txtFld+'-'+fld;
     }
     if(fld==''){
         document.getElementById('wt_fr_c').value ='';
         document.getElementById('wt_fr_c').value ='';
         txtFld = 'ALL'
     }
 document.getElementById(prp).value = txtFld;
 }

function loadDmdPrp(page,fld) {
  var favId = "";

   if(page=='srch'){
     favId = document.getElementById(fld).value ;
   }else{
      favId = fld 
   }
    window.open('StockSearch.do?method=loadDmd&modify=Y&dmd='+favId ,'_self');
    
}
function directLoad(getReqUrl) {
    window.open(getReqUrl,'_self');
}


function srchDmd(byrIdn ,rlnIdn){
var dmdIdn = "";
var isSelected = false;
var frm_elements = document.forms[0].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
if (fieldId.indexOf('Dmdch_') != - 1) {
var isDmdChecked = document.getElementById(fieldId).checked;
if(isDmdChecked){
var dmdVal = document.getElementById(fieldId).value;
dmdIdn = dmdIdn+","+dmdVal;
isSelected = true;
}
}
}}
if(isSelected){
window.open('StockSearch.do?method=dmdRslt&dmdIdn='+dmdIdn+"&byrIdn="+byrIdn+"&rlnIdn="+rlnIdn , '_self');
}else{
alert("Please Select Demand");
}
}
function loadSrch(srchId){
        
        window.open('StockSearch.do?method=loadSrchPrp&srchId='+srchId, '_self');

}

function removeDmd(fld){
var answer = confirm("Are you sure to delete: ?")
    if (answer){
         window.open('StockSearch.do?method=rmvDmd&dmd='+fld, '_self');
    }
    
    return false; 
   
}

function setAllShapes(lprp){

   var shsize =  document.getElementById(lprp+"_SZ").value;
   var isChecked = document.getElementById(lprp+"_All").checked;
   for(var i=0;i<shsize;i++){
       var shid = lprp+"_"+i;
       if(isChecked){
       document.getElementById(shid).checked = true;
       }else{
       document.getElementById(shid).checked = false;
       }
   }
}

function loadFmt(){
 var mdl = document.getElementById('mdlLst').value;
 window.open('memoReport.do?method=loadFmt&mdl='+mdl, '_self');
}
function goTo(theURL,winName,features) { //v2.0
  //alert(' Going To '+theURL);	
  //window.open(theURL,winName,features);

  window.open(theURL, '_self');
}
function newWindow(url){    
    window.open(url,'_blank');
}

function WindowName(url,winNme){
    window.open(url,winNme);
}
function goToRePrice(seq) { //v2.0
    var webUrl = document.getElementById("webUrl").value;
  var reportUrl = document.getElementById("repUrl").value;
  var cnt = document.getElementById("cnt").value;
  var repPath = document.getElementById("repPath").value;
     var theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\latest_price_update.jsp&P_PRI_SEQ="+seq; 
    window.open(theURL, '_blank');
 
}
function confirmSelection(view){
 
  var x=0;
  var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {

  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='checkbox'){
    if(frm_elements[i].checked){
        var elementName =frm_elements[i].name;
       var isCB = elementName.indexOf('cb_bkt_');
      
       if(isCB!=-1){
         x = x+1;
       }
       
       
       }
       
       }

 }
 
 if(x!=0){
     
     var y=window.confirm("Are you sure you want to buy "+x+" stones ?")
    return y;
     
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

function loadFavSrch(fld){
      var rlnObj = document.getElementById('rlnId');
    var rln = rlnObj.options[rlnObj.options.selectedIndex].value
    
    if(rln==0){
    alert("Please Select buyer");
        
    }else{
      var favId = document.getElementById(fld).value ;
    window.open('StockSearch.do?method=loadFav&favId='+favId+'&rlnId='+rln , '_self');
    }
     
}

function rmvFavSrch(fld){
     var favId = document.getElementById(fld).value ;
    window.open('searchAction.do?method=rmvFav&favId='+favId, '_self');
}

function displayDiv(objId){
    var stt=document.getElementById(objId).style.display
  
  if(stt=='block'){
      document.getElementById(objId).style.display='none';
  }else{
      document.getElementById(objId).style.display='block';
  }
}

function displayDiv(objId){
    var stt=document.getElementById(objId).style.display
  
  if(stt=='block'){
      document.getElementById(objId).style.display='none';
      document.getElementById(objId+"_TAB").style.color='black';
  }else{
      document.getElementById(objId).style.display='block';
      document.getElementById(objId+"_TAB").style.color='#ffffff';
  }
}

function Validate(nmeDocId , obj){
     var stt = obj.checked;
     
      var url = "nmedocs.do?method=valified&nmeDocId="+nmeDocId+"&stt="+stt;
        
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

function checkRln(){
    var rlnObj = document.getElementById('rlnId');
  
    var rln = rlnObj.options[rlnObj.options.selectedIndex].value;
    
    if(rln==0){
    alert("Please Select Terms");
        return false;
    }else{
    var createExlBtn = document.getElementById("createExlBtn").value;
    var srchRef = document.getElementById("srchRef").value;
     var vnm = document.getElementById("vnm").checked;
        var memoId = document.getElementById("memoId").checked;
        var cert_no = document.getElementById("cert_no").checked;
        var show_id = document.getElementById("show_id").checked;
        var isChecked = true;
    if(srchRef==''){
        if(!vnm){
         if(!memoId){
             if(!cert_no){
               if(!show_id){
                   isChecked = true; 
                   if(createExlBtn=='N'){
                   loading();
                   }
                   document.getElementById('createExlBtn').value='N';
                return true;   
            }else{
                isChecked = false; 
             }
             }else{
                isChecked = false; 
             }
         }else{
               isChecked = false; 
         }
        }else{
           isChecked = false; 
        }
      if(!isChecked){
          alert("Please  Enter Number");
            return false;
      }
           
    }else{
        
        if(vnm){
        loading();
        return true;
        } else if(memoId){
        loading();
         return true;
        }else if(cert_no){
        loading();
        return true;
        }else if(show_id){
        loading();
        return true;
        }else{
       
         alert("Please Select Type of Enter Number");
          return false;
        }
    }
    }
    
}

function setBtncreateExl(){
    document.getElementById('createExlBtn').value='Y';
}
function setEX(exTyp){
    var cut = document.getElementById('cut').value;
    var sym = document.getElementById('sym').value;
    var pol = document.getElementById('pol').value;
    var cntac = document.getElementById('cntac').value;
    var isChecked = document.getElementById(exTyp).checked;
    var disVal ='';
    if(isChecked)
      disVal = 'none';

    document.getElementById(cut+'_1').style.display=disVal ;
    document.getElementById(cut+'_2').style.display=disVal ;
    if(isChecked){
     document.getElementById(sym+'_1').style.display=disVal ;
    document.getElementById(sym+'_2').style.display=disVal ;
    document.getElementById(pol+'_1').style.display=disVal ;
    document.getElementById(pol+'_2').style.display=disVal ;
    if(exTyp=='is2Ex'){
      document.getElementById('is3Ex').checked= false;
      document.getElementById('is3VG').checked= false;
      if(document.getElementById('is3VG_UP')!=null){
      document.getElementById('is3VG_UP').checked= false;
      }
    }
    if(exTyp=='is3Ex'){
      document.getElementById('is2Ex').checked= false;
      document.getElementById('is3VG').checked= false;
      if(document.getElementById('is3VG_UP')!=null){
      document.getElementById('is3VG_UP').checked= false;
      }
      if(cntac =='ag'){
      setEXVG('VG',document.getElementById('is3VG'));
      setEXVG('EX',document.getElementById('is3Ex'));
      }
    }
    if(exTyp=='is3VG'){
      document.getElementById('is2Ex').checked= false;
      document.getElementById('is3Ex').checked= false;
      if(document.getElementById('is3VG_UP')!=null){
      document.getElementById('is3VG_UP').checked= false;
      }
      if(cntac =='ag'){
      setEXVG('EX',document.getElementById('is3Ex'));
      setEXVG('VG',document.getElementById('is3VG'));
      }
    }
    if(exTyp=='is3VG_UP'){
      document.getElementById('is2Ex').checked= false;
      document.getElementById('is3Ex').checked= false;
      document.getElementById('is3VG').checked= false;
      setEXVG('EX',document.getElementById('is3Ex'));
      setEXVG('VG',document.getElementById('is3VG'));
    }
    document.getElementById('MTXT_'+cut).style.display=disVal ;
    document.getElementById('IMGD_'+cut).style.display=disVal ;
    }else{
    
    if(cntac =='ag'){
     if(exTyp=='is3Ex'){
      setEXVG('EX',document.getElementById('is3Ex'));
    }else if(exTyp=='is3VG'){
      setEXVG('VG',document.getElementById('is3VG'));
    }
    }
    
    if(exTyp=='is3VG_UP'){
      setEXVG('VG',document.getElementById('is3VG_UP'));
    }
     document.getElementById(sym+'_1').style.display=disVal ;
    document.getElementById(sym+'_2').style.display=disVal ;
      document.getElementById(pol+'_1').style.display=disVal ;
    document.getElementById(pol+'_2').style.display=disVal ;
    document.getElementById('MTXT_'+cut).style.display=disVal ;
    document.getElementById('IMGD_'+cut).style.display=disVal ;
    }
   
 }
function setExOnLoad(){
    var cut = document.getElementById('cut').value;
    var sym = document.getElementById('sym').value;
    var pol = document.getElementById('pol').value;
      var isEx = document.getElementById('exTyp').value;
      var isChecked = document.getElementById(isEx).checked;
    var disVal ='';
    if(isChecked)
      disVal = 'none';
    document.getElementById(cut+'_1').style.display=disVal ;
    document.getElementById(cut+'_2').style.display=disVal ;
       if(isChecked){
  
   document.getElementById(sym+'_1').style.display=disVal ;
    document.getElementById(sym+'_2').style.display=disVal ;
   
    document.getElementById(pol+'_1').style.display=disVal ;
    document.getElementById(pol+'_2').style.display=disVal ;
    document.getElementById('MTXT_'+cut).style.display=disVal ;
     document.getElementById('IMGD_'+cut).style.display=disVal ;
           document.getElementById('MTXT_'+pol).style.display=disVal ;
     document.getElementById('IMGD_'+pol).style.display=disVal ;
       document.getElementById('MTXT_'+sym).style.display=disVal ;
     document.getElementById('IMGD_'+sym).style.display=disVal ;
        document.getElementById(sym+'_SELECTED').style.display=disVal ;
           document.getElementById(pol+'_SELECTED').style.display=disVal ;
              document.getElementById(cut+'_SELECTED').style.display=disVal ;
    }else{
     document.getElementById('MTXT_'+cut).value = 'ALL';
     
     document.getElementById(sym+'_1').style.display=disVal ;
    document.getElementById(sym+'_2').style.display=disVal ;
      document.getElementById(pol+'_1').style.display=disVal ;
    document.getElementById(pol+'_2').style.display=disVal ;
      document.getElementById('IMGD_'+cut).style.display=disVal ;
        document.getElementById('MTXT_'+cut).style.display=disVal ;
    }
   
}
 function moveFocus(fldId){
  var fld = document.getElementById(fldId);
    fld.style.display = 'block'
 }
 
 function setVal(frmFldId, toFldId1, toFldId2, e){
var unicode=e.keyCode? e.keyCode : e.charCode;
var frmFld = document.getElementById(frmFldId);

if (unicode==13)
hideObj(frmFldId);

if(frmFld.selectedIndex > -1) {
var val = frmFld.options[frmFld.selectedIndex].value ;
var textVal = frmFld.options[frmFld.selectedIndex].text;
document.getElementById(toFldId1).value = val;
document.getElementById(toFldId2).value = textVal;
}
document.getElementById(toFldId2).focus();
}


 function setDown(frmFldId, toFldId1, toFldId2, e,key){
var unicode=e.keyCode? e.keyCode : e.charCode;
var frmFld = document.getElementById(frmFldId);
if (unicode==13){
hideObj(frmFldId);
}
if (unicode==40){
if(frmFld.selectedIndex > -1) {
var optionlength=frmFld.length;
var addindex=1;
if(optionlength==1)
addindex=0;
var val = frmFld.options[frmFld.selectedIndex+addindex].value ;
var textVal = frmFld.options[frmFld.selectedIndex+addindex].text;
frmFld.options[frmFld.selectedIndex+addindex].selected = true;
document.getElementById(toFldId1).value = val;
document.getElementById(toFldId2).value = textVal;
}
}
if(unicode==38){
if(frmFld.selectedIndex > -1) {
var val = frmFld.options[frmFld.selectedIndex-1].value ;
var textVal = frmFld.options[frmFld.selectedIndex-1].text;
frmFld.options[frmFld.selectedIndex-1].selected = true;
document.getElementById(toFldId1).value = val;
document.getElementById(toFldId2).value = textVal;
}
}
document.getElementById(toFldId2).focus();
}

function setDownSerchpage(frmFldId, toFldId1, toFldId2, e,key){
var unicode=e.keyCode? e.keyCode : e.charCode;
var frmFld = document.getElementById(frmFldId);
if (unicode==13){
hideObj(frmFldId);
getTrmsNME('party','SRCH');
}
if (unicode==40){
if(frmFld.selectedIndex > -1) {
var optionlength=frmFld.length;
var addindex=1;
if(optionlength==1)
addindex=0;
var val = frmFld.options[frmFld.selectedIndex+addindex].value ;
var textVal = frmFld.options[frmFld.selectedIndex+addindex].text;
frmFld.options[frmFld.selectedIndex+addindex].selected = true;
document.getElementById(toFldId1).value = val;
document.getElementById(toFldId2).value = textVal;
}
}
if(unicode==38){
if(frmFld.selectedIndex > -1) {
var val = frmFld.options[frmFld.selectedIndex-1].value ;
var textVal = frmFld.options[frmFld.selectedIndex-1].text;
frmFld.options[frmFld.selectedIndex-1].selected = true;
document.getElementById(toFldId1).value = val;
document.getElementById(toFldId2).value = textVal;
}
}
document.getElementById(toFldId2).focus();
}


 function setValAndHid(){
  
      var dropVal = document.getElementById('menuPop');
     var val = dropVal.options[dropVal.selectedIndex].value ;
      var textVal = dropVal.options[dropVal.selectedIndex].text;
      document.getElementById('complete-field').value = textVal;
     dropVal.style.display = 'none';
     document.getElementById('nmeID').value = val;
 }
 
 function setValueToTextUp(){
     var dropVal = document.getElementById('menuPop');
     var val = dropVal.options[dropVal.selectedIndex-1].value ;
      var textVal = dropVal.options[dropVal.selectedIndex-1].text;
      document.getElementById('complete-field').value = textVal;
   
 }
function setValueToTextDwon(){
     var dropVal = document.getElementById('menuPop');
     alert(dropVal.selectedIndex);
     var val = dropVal.options[dropVal.selectedIndex+1].value ;
      var textVal = dropVal.options[dropVal.selectedIndex+1].text;
      document.getElementById('complete-field').value = textVal;
   
 }
 
function CheckOvertons(lprp,obj){
var isChecked = obj.checked;
var prpLengh = document.getElementById(lprp+'_SZ').value;
var checkColor = new Array();
for(var i=0;i<prpLengh;i++){
var val = document.getElementById(lprp+'_'+i)
if(val.checked){
checkColor[i]= val.value;
}
}

for(var n=0;n <checkColor.length;n++){
var color = checkColor[n];
for(var m=0;m<prpLengh;m++){
var prpVal=document.getElementById(lprp+'_'+m).value;
if(prpVal.indexOf(obj.value)!=-1){
document.getElementById(lprp+'_'+m).checked = isChecked;
}
}
}
}


function checkContainFncy(){
    var obj = document.getElementById('contain');
    var prpLengh = document.getElementById('FANCY COLOR_SZ').value;
    var checkColor = new Array(); 
    var j=0;
    var isChecked= obj.checked;
    if(isChecked){
     for(var i=0;i<prpLengh;i++){
      var val = document.getElementById('FANCY COLOR_'+i)
      if(val.checked){
          checkColor[j]= val.value;
          j++
      }
    }
    }else{
        
    }
     if(isChecked){
      if(checkColor.length==0){
          alert("Please select Fancy color");
          document.getElementById('contain').checked = false;
      }else{
          for(var n=0;n <checkColor.length;n++){
              var color = checkColor[n];
              for(var m=0;m<prpLengh;m++){
                var prpVal=document.getElementById('FANCY COLOR_'+m).value;
               
                if(prpVal.indexOf(color)!=-1){
                
                    document.getElementById('FANCY COLOR_'+m).checked = true;
                }
              }
      }
     
     }}
    
    
}

function SelectRD(obj){
       var rdSTT = new Array(); 
       rdSTT[0]="ST";
       rdSTT[1]="RT";
       rdSTT[2]="AP";
       rdSTT[3]="CL";
       var count = document.getElementById("rdCount").value;
       for(var i =0 ;i<rdSTT.length ;i++){
           var val = rdSTT[i]
           for(var j=1;j <=count ; j++){
           var id=val+'_'+j
            if(val==obj){
            document.getElementById(id).checked = true;
            }else{
                 document.getElementById(id).checked = false; 
            }
           
           
           }
       }
    
}
function SelectRDSL(obj){
       var rdSTT = new Array(); 
       rdSTT[0]="SL";
       rdSTT[1]="RT";
    
       var count = document.getElementById("rdCount").value;
       for(var i =0 ;i<rdSTT.length ;i++){
           var val = rdSTT[i]
           for(var j=1;j <=count ; j++){
           var id=val+'_'+j
            if(val==obj){
            document.getElementById(id).checked = true;
            }else{
                 document.getElementById(id).checked = false; 
            }
           
           
           }
       }
    
}

function SelectRDDLV(obj){
       var rdSTT = new Array(); 
       rdSTT[0]="DLV";
       rdSTT[1]="RT";
    
       var count = document.getElementById("rdCount").value;
       for(var i =0 ;i<rdSTT.length ;i++){
           var val = rdSTT[i]
           for(var j=1;j <=count ; j++){
           var id=val+'_'+j
            if(val==obj){
            document.getElementById(id).checked = true;
            }else{
                 document.getElementById(id).checked = false; 
            }
           
           
           }
       }
    
}
function DisplayMemoSrch(objIdn){
for(var i=1;i<3;i++){
    var idn = "MS_"+i;
    var divId ="DMS_"+i;
    if(idn==objIdn){
        document.getElementById(divId).style.display='';
    }else{
         document.getElementById(divId).style.display='none';
    }
}
    
}

function openCumReport(){
    var memoPg="contact_detail.jsp";
    var webUrl = document.getElementById("webUrl").value;
  var reportUrl = document.getElementById("repUrl").value;
  var accessidn = document.getElementById("accessidn").value;
  var repPath = document.getElementById("repPath").value;
  var cnt = document.getElementById("cnt").value;
    var  emp = document.getElementById('emp');
    var typ = document.getElementById('typ');
    var empIdn = emp.options[emp.options.selectedIndex].value;
    var typVal = typ.options[typ.options.selectedIndex].value;
     var theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+memoPg+"&p_nme="+empIdn+"&p_typ="+typVal+"&p_access="+accessidn;
    window.open(theURL, '_blank');
}

function openReport(){

 var memoId = document.getElementById("memoId").value;
 var stt = "CRT";
 var isCheked = document.getElementById("ALL").checked;
 if(isCheked)
  stt="ALL";
 var memoPgObj = document.getElementById("memoPg")
  var memoPg = memoPgObj.options[memoPgObj.options.selectedIndex].value;
  var webUrl = document.getElementById("webUrl").value;
  var accessidn = document.getElementById("accessidn").value;
  var reportUrl = document.getElementById("repUrl").value;
  var cnt = document.getElementById("cnt").value;
   var p_boxrmkOb = document.getElementById("p_boxrmk");
   var p_boxrmk ="";
   if(p_boxrmkOb!=null){
    p_boxrmk = document.getElementById("p_boxrmk").value;
   }
  var repPath = document.getElementById("repPath").value;
  var theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+memoPg+"&p_id="+memoId+"&p_stt="+stt+"&p_access="+accessidn+"&p_boxrmk="+p_boxrmk; 
  
   var certY = document.getElementById("P_withoutcertno").checked;
   if(certY){
       certY = "Y";
   }else{
       certY = "N";
   } 
   var priceY = document.getElementById("p_withoutprice").checked;
   if(priceY){
      priceY = "Y";
   }else{
       priceY = "N";
   }
   var quotY = document.getElementById("p_quot").checked;
   if(quotY){
      quotY = "Y";
   }else{
       quotY = "N";
   }
    var wiPack = document.getElementById("P_WIthoutPacketcode").checked;
   if(wiPack){
       wiPack = "Y";
   }else{
       wiPack = "N";
   }
   
    var p_ttlb = document.getElementById("p_ttlb");
    if(p_ttlb!=null){
    p_ttlb=p_ttlb.checked;
   if(p_ttlb){
       p_ttlb = "Y";
   }else{
       p_ttlb = "N";
   }}
   
    var p_incl = document.getElementById("p_incl");
    if(p_incl!=null){
    p_incl=p_incl.checked;
   if(p_incl){
       p_incl = "Y";
   }else{
       p_incl = "N";
   }}
       var P_withoutdisc = document.getElementById("P_withoutdisc");
    if(P_withoutdisc!=null){
    P_withoutdisc=P_withoutdisc.checked;
   if(P_withoutdisc){
       P_withoutdisc = "Y";
   }else{
       P_withoutdisc = "N";
   }}
          var p_vol_dis = document.getElementById("p_vol_dis");
    if(p_vol_dis!=null){
    p_vol_dis=p_vol_dis.checked;
   if(p_vol_dis){
       p_vol_dis = "Y";
   }else{
       p_vol_dis = "N";
   }}
   
    var p_dtl = document.getElementById("p_dtl");
    if(p_dtl!=null){
    p_dtl=p_dtl.checked;
   if(p_dtl){
       p_dtl = "Y";
   }else{
       p_dtl = "N";
   }}
   
       var p_trm = document.getElementById("p_trm");
    if(p_trm!=null){
    p_trm=p_trm.checked;
   if(p_trm){
       p_trm = "Y";
   }else{
       p_trm = "N";
   }}
   
          var p_show = document.getElementById("p_show");
    if(p_show!=null){
    p_show=p_show.checked;
   if(p_show){
       p_show = "Y";
   }else{
       p_show = "N";
   }}
   theURL = webUrl+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\"+memoPg+"&p_id="+memoId+"&P_withoutcertno="+certY+"&p_withoutprice="+priceY+"&P_quot="+quotY+"&P_WIthoutPacketcode="+wiPack+"&P_withoutdisc="+P_withoutdisc+"&p_vol_dis="+p_vol_dis+"&p_trm="+p_trm+"&p_show="+p_show+"&p_dtl="+p_dtl+"&p_incl="+p_incl+"&p_ttlb="+p_ttlb+"&p_stt="+stt+"&p_access="+accessidn+"&p_boxrmk="+p_boxrmk; 
 
   
  window.open(theURL, '_blank');
}

function blockBS(){ 
        var elm=event.srcElement; 
        // only allow backspace if the event came from 
        // a text or textarea element. 
        if(elm.type!="text" && elm.type!="textarea" && event.keyCode==8){ 
                event.returnValue=false; 
                return false; 
        } 
}

function SelectAllPrp(lprp){

   var shsize =  document.getElementById(lprp+'_CN').value;
   var isChecked = document.getElementById(lprp).checked;
   for(var i=0;i<shsize;i++){
       var shid = lprp+"_"+i;
       if(isChecked){
       document.getElementById(shid).checked = true;
       }else{
       document.getElementById(shid).checked = false;
       }
   }
}

function chkAadat(fld1, fld2, lmt, obj) {
var isValid = false;
var val1 = document.getElementById(fld1).value;
var val2 = document.getElementById(fld2).value;
var val = 0 ;
if((val1.length > 0) && (!(isNaN(val1))))
val = val + parseFloat(val1);
if((val2.length > 0) && (!(isNaN(val2))))
val = val + parseFloat(val2);

if(val > lmt) {
alert('Total aadat cannot be greater than '+lmt);
isValid = false;
obj.value = '';
}
else
isValid = true;
/*
if((isNaN(val)) || (isNaN(val2)))
alert('Kindy enter a valid number');
else {
if((val1 + val2) > lmt)
alert('Total aadat cannot be greater than '+lmt);
else
isValid = true;
}
*/
return isValid;
}

function chkBroker(fld1, fld2, fld3, lmt, fctr, obj) {
var isValid = false;
var val1 = document.getElementById(fld1).value;
var val2 = document.getElementById(fld2).value;
var val3 = document.getElementById(fld3).value;
var val = 0 ;
if((val1.length > 0) && (!(isNaN(val1))))
val = val + parseFloat(val1);
if((val2.length > 0) && (!(isNaN(val2))))
val = val + parseFloat(val2);
if((val3.length > 0) && (!(isNaN(val3))))
val = val + parseFloat(val3);

lmt = lmt/fctr;
//alert(' aadat : '+ val + " lmt : "+ lmt);
if(val > lmt) {
alert('Total brokerage cannot be greater than '+lmt);
isValid = false;
obj.value = '';
obj.focus();
}
else
isValid = true;

return isValid;
}

function DisplayXlFormat(dis,hid){
    document.getElementById(dis).style.display = '';
    document.getElementById(hid).style.display = 'none';
    
}
function validateReport(){
    var memoId =  document.getElementById('memoId').value;
    if(memoId==''){
        alert("Please Specify Memo Id");
        return false;
    }
     var frm =  document.getElementById('formatVersion2').checked;
    
     if(frm){
       var txt = document.getElementById('formatTxt').value;
       if(txt==''){
        alert("Please Specify Format Name");
        return false;
      }
         
     }
    
    return true;
}

function selectAllReport(typ){

     var size =  document.getElementById('count').value;
     var isChecked = false;
     if(typ=='check')
      isChecked = true;
   for(var i=0;i<size;i++){
       var chid = "PRP_"+i;
      
       document.getElementById(chid).checked = isChecked;
       
   }
    
}

function setBGColorOnCHK(obj,objId){

    
    var ischecked = obj.checked;
    if(ischecked){
    document.getElementById(objId).style.backgroundColor ="#FFC977";
   
    }else{
     document.getElementById(objId).style.backgroundColor ="";
    
    }
    
}
function setBGColorOnClickCHK(obj,objId){

    
    var ischecked = obj.checked;
    if(ischecked){
    document.getElementById(objId).style.backgroundColor ="";
   
    }else{
     document.getElementById(objId).style.backgroundColor ="#FFC977";
    
    }
    
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
function checkedAllMemo(){
    var count = document.getElementById('ttl_memo').value;
    var check = document.getElementById('chAllMemo').checked;
    for(var i=0;i<count ; i++){
    var chId = "cb_memo_"+i;
    document.getElementById(chId).checked = check ;
    }   
}

function checkedALL(){
    var count = document.getElementById('ttl_cnt').value;
    var check = document.getElementById('chAll').checked;
    for(var i=0;i<count ; i++){
    var chId = "ch_"+i;
    document.getElementById(chId).checked = check ;
    }
       
}

function validateUpdate(){
 var frm_elements = document.forms[0].elements; 
 var isChecked = false;
 for(i=0; i<frm_elements.length; i++) {
  var field_type = frm_elements[i].type.toLowerCase();
   if(field_type=='checkbox'){
    if(frm_elements[i].checked){
    isChecked = true;
    }
   }}
  if(!isChecked){
  alert("Please check checkbox for modification");
  }
  return isChecked;
  
}

function validate_rte(){
    var count = document.getElementById('count').value;
    for(var i=1;i<=count;i++){
        var xrt = "rte_"+i;
        var xrtVal = document.getElementById(xrt).value
        if(xrtVal==""){
            alert("Please specify rate for all Currency");
            return false;
        }
    }
    
    return true;
}
function validate_assort(){
var prcIdn = document.getElementById("mprcIdn").value;
if(prcIdn==0){
 alert("Please Select Process");
  return false;
}
var prcIdn = document.getElementById("empIdn").value;
if(prcIdn==0){
 alert("Please Select Employee");
  return false;
}
return true;
}

function validate_return(){
var prcIdn = document.getElementById("mprcIdn").value;
if(prcIdn==0){
 alert("Please Select Process");
  return false;
}
return true;
}
function validate_Assortreturn(){
var prcIdn = document.getElementById("mprcIdn").value;
var issIdn = document.getElementById("issueId").value;
 
if(prcIdn==0 && issIdn==""){
 alert("Please Select Process");
  return false;
}
return true;
}
function validate_issue(checkId , count){

 var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
        var objId = checkId+i;
      var isChecked = document.getElementById(objId).checked ;
      if(isChecked){
      
       return true;
      }
        
    }
     alert("Please Select Pakets");
    return false;
}

function validate_issueMIX(checkId , count){
 var isVaild=true;
  var isSelected=false;
 var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
        var objId = checkId+i;
      var isChecked = document.getElementById(objId).checked ;
      if(isChecked){
      var stkIdn =  document.getElementById(objId).value;
      var rtnCts = parseFloat(nvl(document.getElementById('CR_'+stkIdn).value,'0'));
      var rtnQty = parseFloat(nvl(document.getElementById('QR_'+stkIdn).value,'0'));
     
      if(rtnCts>0){
          if(rtnQty<=0){
              alert("Please specify return Qty for Packet code:"+stkIdn);
               isVaild=false;
          }
      }
      
      var salCts = parseFloat(nvl(document.getElementById('CS_'+stkIdn).value,'0'));
      var salQty = parseFloat(nvl(document.getElementById('QS_'+stkIdn).value,'0'));
      if(salCts>0){
          if(salQty<=0){
              alert("Please specify Sale Qty for Packet code:"+stkIdn);
               isVaild=false;
          }
      }
     isSelected=true;
      }
        
    }
    if(!isSelected){
     alert("Please Select Pakets");
     isVaild=false;
    }
   
    return isVaild;
}

function validate_IssueEditComp(checkId , count){
var validate=false;
 var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
      var objId = checkId+i;
      var isChecked = document.getElementById(objId).checked ;
      if(isChecked){
       validate=true;
       var issEditPrp = document.getElementById('iss_edit_prp').value;
       var issPrp = issEditPrp.split(",");
       if(issPrp.length>0){
           for(var j=0;j<issPrp.length;j++){
           var prp = issPrp[j];
            var prpVal=document.getElementById(prp+'_'+i).value;
             if(prpVal=='' || prpVal=='0'){
                alert("Please Select "+prp+" For Selected Packets");
                  return false;
                }
           }
       }
      
      }
    }
     if(!validate){
     alert("Please Select Pakets");
     return false;
     }
      loading();
    return true;
}


function validate_labIssue(checkId , count){
var validate=false;
 var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
      var objId = checkId+i;
      var isChecked = document.getElementById(objId).checked ;
      if(isChecked){
       validate=true;
       var service=document.getElementById('SERVICE_'+i).value;
       if(service=='' || service=='0'){
        alert("Please Select Service For Selected Packets");
        return false;
       }
      }
    }
     if(!validate){
     alert("Please Select Pakets");
     return false;
     }else{
     loading();
     }
    return true;
}

function validate_repairIssue(checkId , count){
  var empIdn = document.getElementById('empIdn').value;
  var byrRln = document.getElementById('rlnId').value;
  if(empIdn==0){
      alert("Please Select Party.");
      return false;
  }
  if(byrRln==0){
      alert("Please Select Terms.");
      return false;
  }
 var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
        var objId = checkId+i;
      var isChecked = document.getElementById(objId).checked ;
      if(isChecked){
      
       return true;
      }
        
    }
     alert("Please Select Pakets");
    return false;
}
function checkALL(checkId , count){
      var checked = document.getElementById('checkAll').checked;
     var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
        var objId = checkId+i;
        document.getElementById(objId).checked = checked;
    }
    if(checked){
     document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
     document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
    }else{
     document.getElementById('ctsTtl').innerHTML = "0";
     document.getElementById('qtyTtl').innerHTML = "0";
    
    }
   
}

function checkALLNEW(checkId , count){
      var checked = document.getElementById('checkAll').checked;
     var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
        var objId = checkId+i;
        document.getElementById(objId).checked = checked;
    }
    if(checked){
     document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
     document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
     document.getElementById('avgTtl').innerHTML = document.getElementById('ttlavg').innerHTML;
     document.getElementById('vluTtl').innerHTML = document.getElementById('ttlvlu').innerHTML;
     document.getElementById('rapvluTtl').innerHTML = document.getElementById('ttlrapvlu').innerHTML;
    }else{
     document.getElementById('ctsTtl').innerHTML = "0";
     document.getElementById('qtyTtl').innerHTML = "0";
    document.getElementById('avgTtl').innerHTML = "0";
    document.getElementById('vluTtl').innerHTML = "0";
    document.getElementById('rapvluTtl').innerHTML = "0";
    }
    assortVerificationExlAll();
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

function checkALLLABEXC(checkId , count){
      var checked = document.getElementById('checkAllEXC').checked;
     var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
        var objId = checkId+i;
        var dis = document.getElementById(objId).disabled 
        if(!dis){
        document.getElementById(objId).checked = checked;
        }
    }
   
   
}
function checkALLLAB(checkId , count){
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
function AssortTotalCal(obj , cts , issIdn, stkIdn){
    var isChecked = obj.checked;
    var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
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
    
     
     
     document.getElementById('ctsTtl').innerHTML = (new Number(ctsTtl)).toFixed(3);
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
   
     if(issIdn!=''){
     if(isChecked){
        document.getElementById("DV_"+stkIdn).style.display = '';
        var target = "SC_"+stkIdn;
        var url ="assortFinalRtn.do?method=stockUpd&mstkIdn="+stkIdn+"&issIdn="+issIdn ;
          window.open(url ,target);
     }else{
        document.getElementById("DV_"+stkIdn).style.display = 'none'; 
     }
          
     }
}

function get2DigitNum(lNum) {
	return parseFloat(parseInt(lNum*100)/100);//(new Number(lNum)).toFixed(2);//parseFloat(parseInt(lNum*100)/100);
}

function checkALLUpGT(checkId , count){
       var obj = document.getElementById('checkAll');
      var checked = obj.checked;
     var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
        var objId = checkId+i;
        document.getElementById(objId).checked = checked;
    }
    if(checked){
     document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
     document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
    }else{
     document.getElementById('ctsTtl').innerHTML = "0";
     document.getElementById('qtyTtl').innerHTML = "0";
    
    }
   GTUpdate(obj,'ALL');
}
function TotalCalUpdGT(obj , cts , issIdn, stkIdn){
    var isChecked = obj.checked;
    var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
    var ctsTtl = "";
    var qtyTtl = "";
    if(ctsDiv==0){
     ctsTtl = cts;
     qtyTtl = 1;
    }else if(isChecked){
     ctsTtl = (parseFloat(ctsDiv) + parseFloat(cts));
      qtyTtl = parseInt(qtyDiv)+1;
    }else{
      ctsTtl = (parseFloat(ctsDiv) - parseFloat(cts));
       qtyTtl = parseInt(qtyDiv)-1;
    }
      
   
    
     document.getElementById('ctsTtl').innerHTML = (new Number(ctsTtl)).toFixed(2);
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
   GTUpdate(obj,stkIdn);
  
}

function GTUpdate(obj,stkIdn){
        var isChecked = obj.checked;
      var url = "ajaxAssortAction.do?method=updGt&stkIdn="+stkIdn+"&stt="+isChecked ;
        
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
function AssortRtnTotalCal(obj , cts , issIdn, stkIdn){
    var isChecked = obj.checked;
    var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
    var ctsTtl = "";
    var qtyTtl = "";
    if(ctsDiv==0){
     ctsTtl = cts;
     qtyTtl = 1;
    }else if(isChecked){
     ctsTtl = (parseFloat(ctsDiv) + parseFloat(cts));
      qtyTtl = parseInt(qtyDiv)+1;
    }else{
      ctsTtl = (parseFloat(ctsDiv) - parseFloat(cts));
       qtyTtl = parseInt(qtyDiv)-1;
    }
      
   
    
     document.getElementById('ctsTtl').innerHTML = (new Number(ctsTtl)).toFixed(2);
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
   
  
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function validate_Mail(){
var val = document.getElementById('eml').value;

if (val == "" ){
alert("Please specify Email Id");
document.getElementById('eml').focus();
return false;
}
window.open("StockSearch.do?method=mailExcel&eml="+val, '_blank');
return true;
}

function Labselect(obj , stkIdn){
  var labIdn = "lab_"+stkIdn;
  var fldVal = obj.value;
  if(fldVal=='LB'){
  document.getElementById(labIdn).disabled = false;
  }else{
   document.getElementById(labIdn).disabled = true;
  }
}

function check_file() {
var str=document.getElementById('fileUpload').value.toUpperCase();
var filety=document.getElementById('filety').value.toUpperCase();
var ext=document.getElementById(filety).value.toUpperCase();
var currentstr=str.substring(str.lastIndexOf('.'),str.length);
var suffix=ext ;
if(!(suffix.indexOf(currentstr) !== -1)){
alert('File type not allowed,\nAllowed file:'+suffix);
document.getElementById('fileUpload').value='';
document.getElementById('fileUpload').focus();
}
}

function chkSele(pktListSize,type) {

var lstNme = document.getElementById('lstNme');
var isMix = document.getElementById('VIEW').value;
if(lstNme!=null)
lstNme=lstNme.value;
var typ="checkbox";
if(isMix=='RGH')
typ="radio";
var bool=false;
var frm_elements = document.forms['stock'].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();
  if(field_type==typ) {
   var fieldId = frm_elements[i].id;
   if(fieldId.indexOf('cb_memo_')!=-1){
       if(frm_elements[i].checked && !frm_elements[i].disabled){
           bool = true;
           break;
       }
   }

  }
  if(bool)
   break;
}

if(!bool && type!='excel') {
alert("Please select stones ");
return false;
}
else {
if(type=='excel')
{
goTo('StockSearch.do?method=saveExcel&lstNme='+lstNme,'', '');
}else if(type=='mail') {

window.open("StockSearch.do?method=mailExcel&eml="+val+"&lstNme="+lstNme, '_blank');
return true;
}

var isMemoCrt = document.getElementById('isMemoCrt').value;
var memoTyp = document.getElementById('memoTyp').value;

if(isMemoCrt!='N' && memoTyp!='Z' && memoTyp!='WH' && memoTyp!='I' && memoTyp!='O'){
  var avMemoCrt = parseInt(document.getElementById('avMemoCrt').innerHTML);
  if(avMemoCrt > 0){
  var xrt =  parseFloat(document.getElementById('xrt').value);
  //var calMemoCrt = avMemoCrt*xrt;
  var calMemoCrt = avMemoCrt;
  var ttlVlu = parseInt(document.getElementById('selectvalues').innerHTML);
  if(ttlVlu > calMemoCrt || calMemoCrt < 0){
  alert("Available Memo Credit Limit is "+avMemoCrt+" $, Hence You Can Not Issue Stones More then "+avMemoCrt+" Amount $.");
  return false;
  }
}}
}
if(type=='sale'){
return confirmChangesMsg('Are you sure want to confrim stone for sale.')
}
if(isMix=='MIX'){
var isMixRte=true;
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();
 if(field_type=='checkbox') {
    fieldId = frm_elements[i].id;
   if(fieldId.indexOf('cb_memo_')!=-1){
       if(frm_elements[i].checked && !frm_elements[i].disabled){
        var stkIdn =   frm_elements[i].value;
        var cts = parseFloat(jsnvl(document.getElementById('cts_'+stkIdn).value));
        var rte = parseFloat(jsnvl(document.getElementById('MixRte_'+stkIdn).value));
         var vnm = parseFloat(jsnvl(document.getElementById('vnm_'+stkIdn).value));
         
        if(cts <= 0){
        alert("Please specified memo Carat for Packet Id :- "+vnm);
        isMixRte=false;
        }
        
        if(rte <= 0 ){
        alert("Please specified price for Packet Id :- "+vnm);
        isMixRte=false;
        }
        
        
       }
   }
  if(!isMixRte)
   break;
  }
  
}
 if(!isMixRte){
 return false;
  }
}

  if(type!='excel'){

  loading();
  }
return true;
}

function getmemoLmt(memoTyp){
var url = "ajaxsrchAction.do?method=getmemolimit&typ="+memoTyp;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesgetmemoLmt(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesgetmemoLmt(responseXML){
var pkts = responseXML.getElementsByTagName("pkts")[0];
        var pkt = pkts.childNodes[0];
        var lmt = (pkt.getElementsByTagName('lmt')[0]).childNodes[0].nodeValue;
        var memovlu = (pkt.getElementsByTagName('vlu')[0]).childNodes[0].nodeValue;
        if(lmt!=-1){
        avMemoCrt=parseFloat(lmt)-parseFloat(memovlu);
        document.getElementById('avMemoCrt').innerHTML=avMemoCrt;
        document.getElementById('ttlMemoCrt').innerHTML=lmt;
        }else{
        document.getElementById('avMemoCrt').innerHTML='N';
        }
}

function getcreditLmt(memoTyp){
var url = "ajaxsrchAction.do?method=getcreditLmt&typ="+memoTyp;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesgetcreditLmt(req.responseXML);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesgetcreditLmt(responseXML){
var pkts = responseXML.getElementsByTagName("pkts")[0];
        var pkt = pkts.childNodes[0];
        var lmt = (pkt.getElementsByTagName('lmt')[0]).childNodes[0].nodeValue;
        var memovlu = (pkt.getElementsByTagName('vlu')[0]).childNodes[0].nodeValue;
        var ttlVlu = memovlu+parseInt(document.getElementById('osamount').innerHTML);
        if(lmt!=-1){
        avMemoCrt=parseFloat(lmt)-parseFloat(ttlVlu);
        document.getElementById('avMemoCrt').innerHTML=avMemoCrt;
        document.getElementById('ttlMemoCrt').innerHTML=lmt;
        }else{
        document.getElementById('avMemoCrt').innerHTML='N';
        }
}

function purActionExmail(pktListSize,type) {
var bool=false;
var frm_elements = document.forms[1].elements; 
 
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='checkbox') {
   var fieldId = frm_elements[i].id;
   if(fieldId.indexOf('cb_prc_')!=-1){
       if(frm_elements[i].checked && !frm_elements[i].disabled){
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
}
else {
if(type=='excel')
{
goTo('purPrice.do?method=createXL','', '');
}else if(type=='mail') {
var venId=document.getElementById('venId').value;
window.open("purPrice.do?method=createXL&mail=Y&nmeIdn="+venId, '_blank');
return true;
}
}
return true;
}

function chkSelonMail(pktSize) {
var pksize=pktSize;

if(mailId()) {

if(! chkSele(pksize,'mail'))
return false;
}

}



function mailId() {
var str=document.getElementById('eml').value;
if(str=="" && str.length==0) {
alert("Please enter emailId");
return false;
}
else
{

var regEx = /^.{1,}@.{2,}\..{2,}/;
if(!regEx.test(str)) {
alert("Please enter proper mail id");
return false;
}
else
return true;
}
}

function displayAdvSrch(){
   var advDiv = document.getElementById('advSrchDiv').style.display
   if(advDiv=='none'){
       document.getElementById('advSrchDiv').style.display='';
       document.getElementById('advanceSrch').style.display='none';
       document.getElementById('hideAdvSrch').style.display='block';
   }else{
       document.getElementById('advSrchDiv').style.display='none';
       document.getElementById('advanceSrch').style.display='block';
       document.getElementById('hideAdvSrch').style.display='none';
   }
}
function setIframe(stkIdn ,dis , hid ){
    var disDiv = dis+stkIdn;
    var hidDiv = hid+stkIdn;
    document.getElementById(disDiv).style.display = '';
     document.getElementById(hidDiv).style.display = 'none';
}

function DisPalyObj( link ){

    document.getElementById('frameDiv').style.display='';
    window.open(link, 'subContact');
}

function setBGColorSelectTR(objID){
    var table = document.getElementById('dataTable');
  
    var rows = table.getElementsByTagName("tr");
    for(i = 0; i < rows.length; i++){ 
      rows[i].style.backgroundColor ="";
    }
    document.getElementById(objID).style.backgroundColor ="#FFC977";
}

function setBGColorSelectTR1(objID,tableId){
    var table = document.getElementById(tableId);
  
    var rows = table.getElementsByTagName("tr");
    for(i = 0; i < rows.length; i++){ 
      rows[i].style.backgroundColor ="";
    }
    document.getElementById(objID).style.backgroundColor ="#FFC977";
}
function setBGColorOnClickTR(objID){
var color=document.getElementById(objID).style.backgroundColor;
    if(color=='')
    document.getElementById(objID).style.backgroundColor ="#FFC977";
    else
    document.getElementById(objID).style.backgroundColor ="";
}

function createReport(){
    window.open("orclReportAction.do?method=viewRT", '_blank');
}
function rePricing(){
    var frm_elements = document.forms[0].elements; 
     var flg=false;
   
         for(i=0; i<frm_elements.length; i++) {

          field_type = frm_elements[i].type.toLowerCase();

  if(field_type=='checkbox') {
   var fieldId = frm_elements[i].id;
   if(fieldId.indexOf('cb_prc_')!=-1){
   var isChecked =  document.getElementById(fieldId).checked;
   if(isChecked){
       flg=true;
       break;
   }}

  }}
  if(flg){
    window.open("transferMkt.do?method=save", '_blank');
  }else{
      alert("Please select stones")
  }
}
function sumitForm(){
var pageNme = document.getElementById('reportPag').value;
var reqUrl = document.getElementById('reqUrl').value;
 window.open(reqUrl+'/report/orclReportAction.do?method=fecthParam&reportPag='+pageNme, '_self');
     
}

function prcReportPrint(seq , typ){

    window.open("transferMkt.do?method=pktPrint&seq="+seq+"&typ="+typ, '_blank');
}

function confirmPRC(){
var r = confirm("Do you want to reprice");
return r;
}

function confirmREPRC(typ){

   var msg = "Do you want to reprice";
   if(typ=='lab')
    msg = "Do you want to Apply for lab.";
   if(typ=='memo')
     msg = "Do you want take Memo ptint.";
    var r = confirm(msg);
    if(r){
        var frm_elements = document.forms[1].elements; 
        var flg=false;
      
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();

            if (field_type == 'checkbox') {
                var fieldId = frm_elements[i].id;
                if (fieldId.indexOf('cb_prc_') !=  - 1) {
                    var isChecked = document.getElementById(fieldId).checked;
                    if (isChecked) {
                        flg = true;
                        break;
                    }
                }
            }}
        if(flg){
              if(typ=='lab'){
              window.open("transferMkt.do?method=save&typ="+typ, '_self');
              }else if(typ=='memo'){
              window.open("transferMkt.do?method=memo", '_blank');
              }else{
              window.open("transferMkt.do?method=save&typ="+typ, '_blank');
              }
             return true;
        }else{
            alert("Please select stones")
            return false;
        }
    }
    return r;
}

function confirmStockTally(){
var r = confirm("Do You Want Continuous Stock Tally Process..");
return r;
}
function confirmChangesSL1(){
    var r = confirm("Are You Sure You Want To Save Changes?");
   if(r){
    loadPopupSale();
   }
}
function confirmChangesRSLTSL(){
loadPopupSale();
        return false;
}
function confirmChangesSL(){
    var r = confirm("Are You Sure You Want To Save Changes?");
   if(r){
      var frm_elements = document.forms[1].elements; 
        var flg=false;
      
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();

            if (field_type == 'radio') {
                var fieldId = frm_elements[i].id;
                if (fieldId.indexOf('SL') !=  - 1) {
                    var isChecked = document.getElementById(fieldId).checked;
                    if (isChecked) {
                        flg = true;
                        break;
                    }
                }
            }}
            if(flg){
       var addrId = document.getElementById('addrId').value;
       var addrIdHIDD = document.getElementById('addr_HIDD').value;
       if(addrIdHIDD=='Y'){
       if(addrId==''){
           alert('Please Select Party/Shipment Address');
           return false;
       }
       }
       
       var dlvpopup_yn = document.getElementById('DLV_POPUP').value;  
        if(flg && dlvpopup_yn=='Y'){
        loadPopupSale();
        return false;
        }else{
        loading();
           return true;
        }
            }
   }
   return r;
}

function confirmChangesROSL(){
    var r = confirm("Are You Sure You Want To Save Changes?");
   if(r){
      var frm_elements = document.forms[1].elements; 
        var flg=false;
      
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();

            if (field_type == 'radio') {
                var fieldId = frm_elements[i].id;
                if (fieldId.indexOf('SL') !=  - 1) {
                    var isChecked = document.getElementById(fieldId).checked;
                    if (isChecked) {
                        flg = true;
                        break;
                    }
                }
            }}
            if(flg){
       var addrId = document.getElementById('addrId').value;
       var addrIdHIDD = document.getElementById('addr_HIDD').value;
       if(addrIdHIDD=='Y'){
       if(addrId==''){
           alert('Please Select Party/Shipment Address');
           return false;
       }
       }
       
        var saleTyp = document.getElementById('saleTyp').value;
         if(saleTyp==''){
           alert('Please Specify Sale Type');
           return false;
       }
       
       var dlvpopup_yn = document.getElementById('DLV_POPUP').value;  
        if(flg && dlvpopup_yn=='Y'){
        loadPopupSale();
        return false;
        }else{
        loading();
           return true;
        }}
   }
   return r;
}



function confirmChangesMIXSL(){
    var isVaild=true;  
    var flg = validate_issue('CHK_' , 'count');
    if(flg){
    var r = confirm("Are You Sure You Want To Save Changes?");
       if(r){
       var dlvpopup_yn = document.getElementById('DLV_POPUP').value;  
        if(dlvpopup_yn=='Y'){
        loadPopupSale();
        return false;
        }else{
        loading();
           return true;
        }
       }else{
            isVaild=false;
       }
    }else{
          isVaild=false;
    }
     
   
   return isVaild;
}


function confirmChangesMIX(){
     var isVaild=true;  
     var flg=validate_issueMIX('CHK_' , 'count')
     if(flg){
       var r = confirm("Are You Sure You Want To Save Changes?");
       if(r){
       var dlvpopup_yn = document.getElementById('DLV_POPUP').value;  
        if(dlvpopup_yn=='Y'){
        loadPopupSale();
        return false;
        }else{
        loading();
         return true;
        }
       }else{
            isVaild=false;
       }
    }else{
           isVaild=false;
    }
   
   return isVaild;
}
function confirmChangesConsignment(){
    var r = confirm("Are You Sure You Want To Save Changes?");
   if(r){
        var blockpopup_yn = document.getElementById('BLOCK_POPUP').value;  
        if(blockpopup_yn=='Y'){
        loadPopupSale();
        return false;
        }else{
        loading();
        return true;
        }
   }
   return r;
}
function confirmChangesWithPopup(){
var r = confirm("Are You Sure You Want To Save Changes?");
if(r){
  loading();
}
    return r;
}


function confirmChangesWithPopupRtn(){
 var limt =document.getElementById('creditlimit').innerHTML;
if(limt!='N'){
 var ttlVlu = parseInt(document.getElementById('vlu').innerHTML);
 var ttlByrVal = parseInt(document.getElementById('ttlByrVal').value);
 ttlVlu=ttlVlu+ttlByrVal;
 var calMemoCrt=parseFloat(limt);
  if(ttlVlu > calMemoCrt || calMemoCrt < 0){
  alert("Available Memo Credit Limit is "+calMemoCrt+" $, Hence You Can Not Approve Stones More then "+calMemoCrt+" Amount $.");
  return false;
  }
}
var r = confirm("Are You Sure You Want To Save Changes?");
if(r){
  loading();
}
    return r;
}
function confirmChanges(){
var r = confirm("Are You Sure You Want To Save Changes?");
return r;
}
function confirmChangesMsg(msg){
var r = confirm(msg);
return r;
}
function checkMyOfferPrice(stkIdn, cmp){
   var myRteObj = document.getElementById('BIDRte_'+stkIdn)
    var myRteVal =  myRteObj.value;
   if(isNaN(myRteVal)){
    alert("Please verify price");
    myRteObj.value = "";
    myRteObj.focus();
     document.getElementById('BIDDis_'+stkIdn).value ="";
    return false;
   }
  
   if(myRteVal > cmp){
       alert("Minmium expected price is "+myRound(cmp,2)); 
       myRteObj.value = "";
       myRteObj.focus();
        document.getElementById('BIDDis_'+stkIdn).value ="";
           return false;
   }
   if(myRteVal!=""){
       var  rap1 = document.getElementById('rap_'+stkIdn).value;
       var rap = (myRteVal*100/rap1)-100;
       document.getElementById('BIDDis_'+stkIdn).value = formatNumber(rap,2);
   }
}
function checkMyOfferDis(stkIdn, cmp){
    var myDisObj = document.getElementById('BIDDis_'+stkIdn);
     var ofrDis = document.getElementById('BIDDis_'+stkIdn).value;
    
     var rap1 = document.getElementById('rap_'+stkIdn).value;
     var myRteVal =(rap1*(100+parseFloat(ofrDis))/100);
     if(myRteVal > cmp){
       alert("Minmium expected price is "+myRound(cmp,2)); 
       myDisObj.value = "";
       myDisObj.focus();
       document.getElementById('BIDRte_'+stkIdn).value ="";
       setTimeout(function(){myRteObj.focus();},1); 
       return false;
    }
    
    if(ofrDis!=""){
        document.getElementById('BIDRte_'+stkIdn).value = myRteVal;
    }
}

function getRangePrc(stkIdn,qout,offerLmt,basedon,rangeto){
var prc=0;
    if(basedon=='PRC'){
          var percent = ((parseFloat(qout) * parseFloat(offerLmt))/100);  
          if(rangeto=='FROM'){
          prc = parseFloat(qout) - parseFloat(percent);
          }else{
          prc = parseFloat(qout) + parseFloat(percent);   
          }
    }
    else{
        var dis = document.getElementById('dis_'+stkIdn).value;
        var  rap=0;
        if(rangeto=='FROM'){
        var rangeDisfr=parseFloat(dis)+parseFloat(offerLmt);
        var rdisfr = 100 + parseFloat(dis) ;
        rap = (qout * 100)/rdisfr;
        prc =(rap*(100+parseFloat(rangeDisfr))/100);    
        }else{
            var rangeDisto=parseFloat(dis)-parseFloat(offerLmt);
            var rdis = 100 + parseFloat(dis) ;
            var  rap1 = (qout * 100)/rdis;
            prc =(rap1*(100+parseFloat(rangeDisto))/100);   
        }
    }
    return prc;
}

function checkPepPrice(stkIdn, qout , bidRte , offerLmtfr,offerLmtto,basedon){
    var prcObj = document.getElementById('bid_prc_'+stkIdn);
    var pepPrc = prcObj.value;
    var dis = document.getElementById('dis_'+stkIdn).value;
     var cts = document.getElementById('ctsval_'+stkIdn).value;
    var pepPrcfr=0;
    var pepPrcto=0;
   if(isNaN(pepPrc)){
    alert("Please verify price");
    prcObj.value = bidRte;
    prcObj.focus();
    return false;
   }
    if(basedon=='DIS'){
    pepPrcfr=getRangePrc(stkIdn,qout,offerLmtfr,basedon,'TO');
    pepPrcto=getRangePrc(stkIdn,qout,offerLmtto,basedon,'FROM');
    }else{
    pepPrcfr=getRangePrc(stkIdn,qout,offerLmtfr,basedon,'FROM');
    pepPrcto=getRangePrc(stkIdn,qout,offerLmtto,basedon,'TO');
    }
    if(pepPrc<pepPrcfr){
      alert("Minmium expected price is "+myRound(pepPrcfr,2)); 
      prcObj.value = bidRte;
      prcObj.focus();
      setTimeout(function(){prcObj.focus();},1); 
      return false;  
    }
    
    if(pepPrc >pepPrcto){
        alert("Price is too high stone avaliable at lower price"); 
        prcObj.value = bidRte;
        prcObj.focus();
        setTimeout(function(){prcObj.focus();},1); 
        return false;
    }
    if(pepPrc != ""){
       var rdis = 100 + parseFloat(dis) ;
       var  rap1 = (qout * 100)/rdis;
       var rap=(pepPrc*100/rap1)-100;
       var offerAmount = pepPrc*cts;
       document.getElementById('bid_dis_'+stkIdn).value = formatNumber(rap,2);
       document.getElementById('bid_dis_txt_'+stkIdn).value = formatNumber(rap,2);
       document.getElementById('bid_amount_txt_'+stkIdn).value = formatNumber(offerAmount,2);
   }
}

function checkPepDis(stkIdn, qout , bidRte , offerLmtfr,offerLmtto,basedon){
   var prcObj = document.getElementById('bid_prc_'+stkIdn);
   var dis = document.getElementById('dis_'+stkIdn).value;
      var cts = document.getElementById('ctsval_'+stkIdn).value;
   var ofrDis = document.getElementById('bid_dis_txt_'+stkIdn).value ;
   var pepPrc = prcObj.value;
    var pepPrcfr=0;
    var pepPrcto=0;
   if(ofrDis!=""){
     if(basedon=='DIS'){
    pepPrcfr=getRangePrc(stkIdn,qout,offerLmtfr,basedon,'TO');
    pepPrcto=getRangePrc(stkIdn,qout,offerLmtto,basedon,'FROM');
    }else{
    pepPrcfr=getRangePrc(stkIdn,qout,offerLmtfr,basedon,'FROM');
    pepPrcto=getRangePrc(stkIdn,qout,offerLmtto,basedon,'TO');
    }
    var rdis = 100 + parseFloat(dis) ;
    var  rap1 = (qout * 100)/rdis;
    var pepPrc =(rap1*(100+parseFloat(ofrDis))/100);
     
     if(isNaN(pepPrc)){
    alert("Please verify Discount");
    dis.value = bidRte;
    dis.focus();
    return false;
   }
   
   if(pepPrc<pepPrcfr){
      alert("Minmium expected price is "+myRound(pepPrcfr,2)); 
      prcObj.value = bidRte;
        prcObj.focus();
          setTimeout(function(){prcObj.focus();},1); 
           return false;
    }
    if(pepPrc > pepPrcto){
        alert("Price is too high stone avaliable at lower price"); 
        prcObj.value = bidRte;
        prcObj.focus();
          setTimeout(function(){prcObj.focus();},1); 
           return false;
    }
    document.getElementById('bid_prc_'+stkIdn).value = pepPrc;
    var offerAmount=pepPrc*cts;
     document.getElementById('bid_amount_txt_'+stkIdn).value = formatNumber(offerAmount,2);
     
   }
}

function checkPepPriceBID(stkIdn,qout,bidPic){
         var prcObj = document.getElementById('bid_prc_'+stkIdn);
         var pepPrc = prcObj.value;
          var cts = document.getElementById('ctsval_'+stkIdn);
          if(isNaN(pepPrc)){
             alert("Please verify price");
             prcObj.value = "";
              prcObj.focus();
            setTimeout(function(){prcObj.focus();},1); 
           return false;
           }

//alert('Quot : '+qout+" + "+pepPrc);
           if(bidPic != ""){
            if(pepPrc < bidPic){

               alert("Price can not be less then pervious bid ("+bidPic+") ");
              prcObj.value = bidPic;
             setTimeout(function(){prcObj.focus();},1); 
              return false;
           }
             }
           if(parseFloat(pepPrc) < parseFloat(qout)){
              alert(" Price "+ pepPrc + " cannot be less then avaliable price of "+ qout);
              prcObj.value = bidPic;
            setTimeout(function(){prcObj.focus();},1); 
             return false;
             } else if(parseFloat(pepPrc) > parseFloat(qout*2)){
                alert("Price is too high");
                prcObj.value = bidPic;
                setTimeout(function(){prcObj.focus();},1); 
                   return false;
             }
           if(bidPic != ""){
             var dis = document.getElementById('dis_'+stkIdn).value;
             var rdis = 100 + parseFloat(dis) ;
             var  rap1 = (qout * 100)/rdis;
             var rap=(bidPic*100/rap1)-100;
              var offerAmount = bidPic*cts ;
                document.getElementById('bid_dis_'+stkIdn).value= formatNumber(rap,2);
                document.getElementById('bid_dis_txt_'+stkIdn).value = formatNumber(rap,2);
                 document.getElementById('bid_amount_txt_'+stkIdn).value = formatNumber(offerAmount,2);
      }
}


function checkPepDisBID(stkIdn, qout , bidRte){
  var cts = document.getElementById('ctsval_'+stkIdn);
   var dis = document.getElementById('dis_'+stkIdn).value;
   var ofrDis = document.getElementById('bid_dis_txt_'+stkIdn).value ;
   if(ofrDis!=""){
     var rdis = 100 + parseFloat(dis) ;
     var  rap1 = (qout * 100)/rdis;
     var pepPrc =( rap1*(100+parseFloat(ofrDis))/100);
     
     if(isNaN(pepPrc)){
    alert("Please verify Discount");
    dis.value = bidRte;
    dis.focus();
    return false;
   }
     if(parseFloat(pepPrc) < parseFloat(qout)){
              alert(" Price "+ pepPrc + " cannot be less then avaliable price of "+ qout);
              prcObj.value = bidPic;
            setTimeout(function(){prcObj.focus();},1); 
             return false;
             } else if(parseFloat(pepPrc) > parseFloat(qout*2)){
                alert("Price is too high");
                prcObj.value = bidPic;
                setTimeout(function(){prcObj.focus();},1); 
                   return false;
             }
              var offerAmount = pepPrc*cts ;
    document.getElementById('bid_prc_'+stkIdn) = pepPrc;
     document.getElementById('bid_amount_txt_'+stkIdn).value = formatNumber(offerAmount,2);
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
function uncheckbox(){
     var frm_elements = document.forms[1].elements; 
      
      
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();
          if (field_type == 'checkbox') {
           var fieldId = frm_elements[i].id;
           document.getElementById(fieldId).checked = false;
            }
        }   
        
       document.getElementById('ctsTtl').innerHTML = 0;
     document.getElementById('qtyTtl').innerHTML = 0; 
     RemoveStoneList();
}
function RemoveStoneList(){
     var url = "ajaxLabAction.do?method=remove";
        
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
function openPopUp(url,target,id){
   var rtn = validate_issue('CHK_' , 'count')
   if(rtn){
    window.open(url,target,id);
    loadASSFNLPop(id);
    return true;
   }else{
       return false;
   }
   
}
function openPopUpDirect(url,target,id){
    window.open(url,target,id);
    loadASSFNLPop(id);
    return true;  
}

function openPopValid(url,target,id,objId){
var frm_elements = document.forms[1].elements;
var bool = false;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();

if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;

if(fieldId.indexOf(objId)!=-1){
if(frm_elements[i].checked){
bool = true;
}}}
}
if(!bool){
alert("Please Select stones");
return false;
}
if(bool){
window.open(url,target,id);
loadASSFNLPop(id);
}

}

function SetiframeUrl(url,idn){
alert(url);
alert(document.getElementById(idn));
    document.getElementById(idn).value=url;


}

function windowOpen(url,target){
     window.open(url,target);
}
function Issue_cancel(){
    var issID = document.getElementById('issueId').value;
    if(issID==''){
        alert("Please specify IssueId");
        return false;
    }
    
    return true;
}

function checkalladdInSrchRefBox(){
    document.getElementById('srchRef').value='';
    var isChecked = document.getElementById('WRALL').checked;
    var tab = document.getElementById ( "WRTABLE" ); // table with id tbl1
    var elems = tab.getElementsByTagName ( "input" );
    var len = elems.length;
    for ( var i = 0; i < len; i++ ){
    	if ( elems[i].type == "checkbox" ){
        var fieldId = elems[i].id;
        if(fieldId!='WRALL'){
        document.getElementById(fieldId).checked=isChecked;
        if(isChecked){
        addInSrchRefBox(fieldId);
    	}
        }
        }
    }
        if(!isChecked){
        document.getElementById('srchRef').value='';
        document.getElementById('memoId').checked = false;
        }
}

function checkalladdInSrchRefBoxHR(){
    document.getElementById('srchRef').value='';
    var isChecked = document.getElementById('HRALL').checked;
    var tab = document.getElementById ( "HRTABLE" ); // table with id tbl1
    var elems = tab.getElementsByTagName ( "input" );
    var len = elems.length;
    for ( var i = 0; i < len; i++ ){
    	if ( elems[i].type == "checkbox" ){
        var fieldId = elems[i].id;
        if(fieldId!='HRALL'){
        document.getElementById(fieldId).checked=isChecked;
        if(isChecked){
        addInSrchRefBox(fieldId);
    	}
        }
        }
    }
        if(!isChecked){
        document.getElementById('srchRef').value='';
        document.getElementById('memoId').checked = false;
        }
}

function addInSrchRefBox(idval){
    var memoId = document.getElementById(idval).value;
    var isChecked = document.getElementById(idval).checked;
    var srchRefVal = document.getElementById('srchRef').value;
    var str ="";
    if(isChecked){
    str = srchRefVal+","+memoId;
    }else{
        str = srchRefVal.replace(','+memoId , '');
    }
    if(str!=""){
         document.getElementById('memoId').checked = true;
    }else{
         document.getElementById('memoId').checked = false;
    }
    
    document.getElementById('srchRef').value =str;
}

   function checkselect(objId)
{

var inputs_in_table = document.getElementById(objId).getElementsByTagName("input");

var multiTxt = document.getElementById("multiTxtdm").value;
var txtFld = '';
var dmdDscID ='';
var elementName='';
var dmdFld='';


for(var i=3; i<inputs_in_table.length; i++) {
if(inputs_in_table[i].type == "checkbox") {
inputs_in_table[i].checked= true;
elementName =inputs_in_table[i].value;
dmdDscID = "dmdDsc"+elementName;

dmdFld = document.getElementById(dmdDscID).innerHTML;
multiTxt = document.getElementById("multiTxtdm").value ;
if(multiTxt == ''){

document.getElementById("multiTxtdm").value = dmdFld;
}
else {
txtFld = multiTxt+' , '+dmdFld;
document.getElementById("multiTxtdm").value = txtFld;
}
}
}
}

    
   function uncheck(objId) {
var inputs_in_table = document.getElementById(objId).getElementsByTagName("input");
var txtFld = '';
var mutiTextId = '';

for(var i=3; i<inputs_in_table.length; i++) {
if(inputs_in_table[i].type == "checkbox"){
inputs_in_table[i].checked= false;
}
var elementName =inputs_in_table[i].value;
txtFld = txtFld+elementName+',';

}
document.getElementById("multiTxtdm").value = '';
}

    function goToNew(theURL,winName,features) { //v2.0
//alert(' Going To '+theURL);
//window.open(theURL,winName,features);
  if(winName==''){
    winName="mywindow";
  }
  
  
var bool=false;

var frm_elements = document.forms['stock'].elements;

for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('cb_memo_')!=-1){
if(frm_elements[i].checked){
bool = true;
}
}
}
}
if(!bool){
alert("Please select stones");
}

if(bool){
if(winName=='SC'){
    loadASSFNLPop('img')
}
var lstNme = document.getElementById('lstNme');
if(lstNme!=null){
var nme = lstNme.value;
   theURL=theURL+"&lstNme="+nme;
}
window.open(theURL, winName);
}

}


function displayHidDiv(divId){
var rdSTT = new Array();
rdSTT[0]="BSC";
rdSTT[1]="MEAS";
rdSTT[2]="CUST";
rdSTT[3]="MFG";
rdSTT[4]="TXT";
rdSTT[5]="ADDINFO";
rdSTT[6]="PKTINFO";
for(var i =0 ;i<rdSTT.length ;i++){
var val = rdSTT[i];
if(val==divId){


document.getElementById(val).style.display='block';
document.getElementById(val+"_TAB").style.color='#ffffff';

}else{
document.getElementById(val).style.display='none';
document.getElementById(val+"_TAB").style.color='black';
}
}
}


function genericDtl(url){
url = url.replace('+', '%2B'); 
url = url.replace('-', '%2D');
window.open(url,'pktlst');
}

function genericDtlQuick(url){
url = url.replace('+', '%2B'); 
url = url.replace('-', '%2D');
window.open(url,'pktlst1');
}

function setDeleteConfirm(url){
var mes = confirm("Are you sure to delete: ?");
if (mes){
window.open(url, '_self');
}
return false;
}

function setConfirm(url , msg , target ){
var mes = confirm(msg);
if(mes){
window.open(url,target);
}
return false;
}
function matchpair(link) {

var pairtype = document.getElementById('pairtyp').value;

if(pairtype != '0') {
window.open(link+'&pairid='+pairtype ,'_self');
} else {
alert("Please Select Match Pair");
}
}

 function saleperson(){
var saleEmpObj = document.getElementById("saleEmp")
var saleEmpidn = saleEmpObj.options[saleEmpObj.options.selectedIndex].value;
var link = "bulkrole.do?method=selectEmp&saleEmpidn="+saleEmpidn;
document.getElementById('frameDiv').style.display='';
window.open(link, 'subContact');
}

function validate_prc(objId){
 var frm_elements = document.forms[1].elements;
 var bool = false;
 for(i=0; i<frm_elements.length; i++) {
    field_type = frm_elements[i].type.toLowerCase();

    if(field_type=='checkbox') {
            var fieldId = frm_elements[i].id;
          
         if(fieldId.indexOf(objId)!=-1){
              if(frm_elements[i].checked){
               bool = true;
     }}}
   }
  if(!bool){
    alert("Please Select Packet To apply Changes");
    return false;
  }
   var r = confirm("Are You Sure You Want To Save Changes?");
  return r;
 
}

function validate_prcMixKu(objId){
 var frm_elements = document.forms[1].elements;
 var bool = false;
 for(i=0; i<frm_elements.length; i++) {
    field_type = frm_elements[i].type.toLowerCase();

    if(field_type=='checkbox') {
            var fieldId = frm_elements[i].id;
          
         if(fieldId.indexOf(objId)!=-1){
             if(frm_elements[i].checked){
             bool = true;
             var split=fieldId.split('_');
             var stkIdn=split[2];
             var stt=document.getElementById('STT_'+stkIdn).value;
             if(stt=='MIX_AS_AV'){
                 bool = false;
                 var mnlOld=jsnvl(document.getElementById('mnlOld_'+stkIdn).value);
                 var upr=jsnvl(document.getElementById('upr_'+stkIdn).value);
                 if(mnlOld!='0' || upr!='0'){
                 bool = true;
                 }
                   if(!bool){
                   var vnm=jsnvl(document.getElementById('VNM_'+stkIdn).value);
                    alert("Please Enter "+vnm+" Packet Rate to move");
                    return false;
                  }
             }
             }
         }}
   }
  if(!bool){
    alert("Please Select Packet To apply Changes");
    return false;
  }
   var r = confirm("Are You Sure You Want To Save Changes?");
  return r;
 
}

 function setaddDeleteConfirm(url){
var nid=document.getElementById('nid').value;
var mes = confirm("Are you sure to delete: ?")
if (mes){
window.open(url+'&nmeIdn='+nid, '_self');
}
return false;
}
function validate_SelectAll(form,objId){
 var frm_elements = document.forms[form].elements;
 var bool = false;
 for(i=0; i<frm_elements.length; i++) {
    field_type = frm_elements[i].type.toLowerCase();

    if(field_type=='checkbox') {
            var fieldId = frm_elements[i].id;
       
         if(fieldId.indexOf(objId)!=-1){
              if(frm_elements[i].checked){
               bool = true;
     }}}
   }
  if(!bool){
    alert("Please Select");
    return false;
  }
  var r = confirm("Are You Sure You Want To Save Changes?");
  return r;
 
}

function sumbitConfirm(msg){
  var isChecked = false;
    var frm_elements = document.forms['1'].elements; 
 
       for(i=0; i<frm_elements.length; i++) {

        var field_type = frm_elements[i].type.toLowerCase();
          
           if(field_type=='checkbox') {
             var fieldId = frm_elements[i].id;
            
            if(fieldId.indexOf('cb_mat_')!=-1){
              isChecked =  document.getElementById(fieldId).checked;
              
              if(isChecked)
               break;
            }}}
    if(isChecked){
    var r = confirm(msg);
    return r;
    }else{
        alert("please select sheet for delete");
        return false;
    }

}

function CheckBOXALLForm(frmNme, obj , fldId){

     var ischecked  = obj.checked;
     var frm_elements = document.forms[frmNme].elements; 
      for(i=0; i<frm_elements.length; i++) {
        var field_type = frm_elements[i].type.toLowerCase();
          if(field_type=='checkbox') {
          var fieldId = frm_elements[i].id;
           if(fieldId.indexOf(fldId)!=-1){
           document.getElementById(fieldId).checked = ischecked;
           }
          }
     }
}


function checkAllpage(obj , fldId){
     var ischecked  = obj.checked;
     var frm_elements = document.getElementsByTagName('input');
      for(i=0; i<frm_elements.length; i++) {
        var field_type = frm_elements[i].type.toLowerCase();
          if(field_type=='checkbox') {
          var fieldId = frm_elements[i].id;
           if(fieldId.indexOf(fldId)!=-1){
           document.getElementById(fieldId).checked = ischecked;
           }
          }
     }
}




 function loadMemoXLFmt(){
var mdl = document.getElementById('mdlLst').value;
window.open('memoXL.do?method=loadFmt&mdl='+mdl, '_self');
}

function seqvalSet(){
var seq=document.getElementById('seq').value;
document.getElementById('seqid').value=seq;
}

 function fileType(obj)
{
var fType = document.getElementById('fType').value;
alert(fType);
document.getElementById('file_ext').value=fType;
}


function checkALLLabResult(checkId, obj , count){
var checked =obj.checked;
var countval = document.getElementById('count').value;

for(var i=1;i<=countval;i++){
var objId = checkId+i;
document.getElementById(objId).checked = checked;
}
if(checked){
document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
}else{
document.getElementById('ctsTtl').innerHTML = "0";
document.getElementById('qtyTtl').innerHTML = "0";

}

}
function LabTotalCal(obj , cts , issIdn, stkIdn){
var isChecked = obj.checked;
var ctsDiv = document.getElementById('ctsTtl').innerHTML;
var qtyDiv = document.getElementById('qtyTtl').innerHTML;
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

function createPdfPrppos(proposIdn,liveIdn,nme,grpNme){
window.open("proposeToLive.do?method=createPDF&proposIdn="+proposIdn+"&liveIdn="+liveIdn+"&nme="+nme+"&grpNme="+grpNme,'_blank');
}

function addRow(maxRow , btnObj){
    disRow = document.getElementById('trcount').value;
    
    for(var i=1 ; i <= disRow ; i++){
        var trID = "TR_"+i
        document.getElementById(trID).style.display = '';
    }
 
    if(maxRow==disRow){
        btnObj.style.display = 'none'
    }
    document.getElementById('trcount').value = parseInt(disRow)+parseInt(1);
}

function DisplayGRP(grp){
var rdSTT = new Array();
rdSTT[0]="LOOKUP_BSC_LST";
rdSTT[1]="LOOKUP_ADV_LST";
rdSTT[2]="LOOKUP_FIX_LST";
for(var j=0 ; j < rdSTT.length ; j++){
    var tbID = 'dataTable_'+rdSTT[j];
     var table = document.getElementById(tbID);
    var idn = "TD_"+grp;
    var thID = "TD_"+grp+"_"+rdSTT[j];
    var cell = table.getElementsByTagName("td");
    
    for(i = 0; i < cell.length; i++){ 
      var cellID =  cell[i].id;
    
      if(cellID.indexOf(idn)!=-1){
         var isDis = document.getElementById(cellID).style.display ;
        
         if(isDis==''){
             document.getElementById(cellID).style.display = 'none';
         }else{
          
             document.getElementById(cellID).style.display ='';
         }
         
      }
    }
    
     isDis = document.getElementById(thID).style.display ;
        
         if(isDis==''){
             document.getElementById(thID).style.display = 'none';
         }else{
          
             document.getElementById(thID).style.display ='';
         }
    }
}

function chksrvAll(name) {
var idval=document.getElementById(name).value;
var frm_elements = document.forms[1].elements;
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

function chksrvAlltext(name) {
var idval=document.getElementById(name).value;
var frm_elements = document.forms[1].elements;
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

function chksrvAllForm0(name) {
var idval=document.getElementById(name).value;
var frm_elements = document.forms[0].elements;
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

function validate_massMail(objId){
var frm_elements = document.forms[0].elements;
var bool = false;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();

if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;

if(fieldId.indexOf(objId)!=-1){
if(frm_elements[i].checked){
bool = true;
}}}
}
if(!bool){
alert("Please Select Customer");
return false;
}
var r = confirm("Are You Sure You Want To Send Mail?");
if (r){
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();

if(field_type=='checkbox') {
var fieldId1 = frm_elements[i].id;

if(fieldId1.indexOf(objId)!=-1){
frm_elements[i].checked=false;
}}
}
window.open('massmail.do?method=loadM&isMassMail=YES', '_blank');
}
return r;

}


function CheckAlllprp(lprp , obj){
var ischecked = obj.checked;
var labObj = document.getElementById(lprp+'_1');
var txtFld = document.getElementById('MTXT_'+lprp).value;
var labLnth = labObj.length;
for(var i=1;i < labLnth ; i++){
var objId = lprp+"_"+labObj.options[i].value;
var fld = labObj.options[i].text;
if(ischecked){
if(txtFld == 'ALL')
txtFld = '';
if(txtFld==''){
txtFld = fld;
}else{
txtFld = txtFld+' , '+fld;
}
document.getElementById('MTXT_'+lprp).value = txtFld;
document.getElementById(objId).checked = true;
}else{

var indxFld = txtFld.indexOf(fld);

if(indxFld==0){

if(txtFld.indexOf(',') != -1){
txtFld = txtFld.replace(fld+' , ','');
}else{
txtFld = txtFld.replace(fld,'');
}
}else{
txtFld = txtFld.replace(' , '+fld,'');
}
document.getElementById('MTXT_'+lprp).value = txtFld;
document.getElementById(objId).checked = false;
}
}
if(!ischecked){
document.getElementById('MTXT_'+lprp).value = "ALL";
}
}

function createPdfAnal(i){
var colSpan=0;
var qty="";
var cts="";
var avg="";
var rap="";
var age="";
var hit="";
var frm_elements = document.getElementsByTagName('input');
for(j=0; j<frm_elements.length; j++) {
field_type = frm_elements[j].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[j].checked){
var elementName =frm_elements[j].name;
if(elementName=="QTY_dis_"+i){
colSpan++;
qty="Y";
}
if(elementName=="CTS_dis_"+i){
colSpan++;
cts="Y";
}
if(elementName=="AVG_dis_"+i){
colSpan++;
avg="Y";
}
if(elementName=="RAP_dis_"+i){
colSpan++;
rap="Y";
}
if(elementName=="AGE_dis_"+i){
colSpan++;
age="Y";
}
if(elementName=="HIT_dis_"+i){
colSpan++;
hit="Y";
}
}
}
}
window.open("genericReport.do?method=createPDF&loopno="+i+"&colSpan="+colSpan+"&qty="+qty+"&cts="+cts+"&avg="+avg+"&rap="+rap+"&age="+age+"&hit="+hit, '_blank');
}

function displayCTSAVG(tblID,objID,obj){
var chk = document.getElementById(objID).checked;
var table = document.getElementById(tblID);
var spancell = table.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!=''){
if(spanID.indexOf('CLR_')!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
if(spanID.indexOf(obj)!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
}
}
}

function displayCTSAVGHITMAP(tblID,objID,obj){
var chk = document.getElementById(objID).checked;
var table = document.getElementById(tblID);
var spancell = table.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!=''){
if(spanID.indexOf('CLR_')!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
if(spanID.indexOf(obj)!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
}
}
}

function setEXVG(exTyp,obj){
var cut = document.getElementById('cut').value;
var sym = document.getElementById('sym').value;
var pol = document.getElementById('pol').value;
var ischecked = obj.checked;
var isSelected = false;
var frm_elements = document.forms[0].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
if (fieldId.indexOf(cut+'_') != - 1 || fieldId.indexOf(pol+'_') != - 1 || fieldId.indexOf(sym+'_') != - 1) {
var val= document.getElementById(fieldId).value;
if(val.indexOf(exTyp) != -1){
document.getElementById(fieldId).checked = ischecked;
}
if(val.indexOf('ID') != -1 && (exTyp=='EX' || exTyp=='VG')){
document.getElementById(fieldId).checked = ischecked;
}
if(val.indexOf('MIN') != -1 && (exTyp=='EX' || exTyp=='VG')){
document.getElementById(fieldId).checked = ischecked;
}
if(val.indexOf('EX') != -1 && exTyp=='VG'){
document.getElementById(fieldId).checked = ischecked;
}
if(val.indexOf('MIN') != -1 && exTyp=='VG'){
document.getElementById(fieldId).checked = ischecked;
}
}
}
}
}

function setAnalysEX(exTyp,obj){
var cut = document.getElementById('cut').value;
var sym = document.getElementById('sym').value;
var pol = document.getElementById('pol').value;
var ischecked = obj.checked;
var isSelected = false;
var frm_elements = document.forms[0].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
if (fieldId.indexOf(cut+'_') != - 1 || fieldId.indexOf(pol+'_') != - 1 || fieldId.indexOf(sym+'_') != - 1) {
var val= document.getElementById(fieldId).value;
if(val.indexOf(exTyp) != -1){
document.getElementById(fieldId).checked = ischecked;
}
if(val.indexOf('ID') != -1 && exTyp=='EX'){
document.getElementById(fieldId).checked = ischecked;
}
if(val.indexOf('MIN') != -1 && exTyp=='EX'){
document.getElementById(fieldId).checked = ischecked;
}
}
}
}
}


function viewDetailGeneric(key){
var checkedCol = key+'_row_';
var checkedClr = key+'_col_';
var Col='';
var totalCol="";
var Clr='';
var totalClr="";
var loopCol=document.getElementById('loopcol').value;
for(i = 0; i < loopCol; i++){
var cheCol = document.getElementById(checkedCol+i).checked;
if(cheCol){
Col=document.getElementById(checkedCol+i).value;
if(totalCol.length==0){
totalCol=totalCol+Col+"'";
}
else{
totalCol=totalCol+",'"+Col+"'";
}
}
}
var loopClr=document.getElementById('loopclr').value;
for(i =0; i < loopClr; i++){
var cheClr= document.getElementById(checkedClr+i).checked;
if(cheClr){
Clr=document.getElementById(checkedClr+i).value;
if(totalClr.length==0){
totalClr=totalClr+Clr+"'";
}
else{
totalClr=totalClr+",'"+Clr+"'";
}
}
}

var count=totalCol.lastIndexOf("'");
totalCol=totalCol.substring(0,count)
count=totalClr.lastIndexOf("'");
totalClr=totalClr.substring(0,count)

if(totalClr.length ==0)
{
totalClr='ALL';
}
if(totalCol.length ==0)
{
totalCol='ALL';
}
window.open("genericReport.do?method=loadDtl&key="+key+"&row="+totalCol+"&col="+totalClr+"&status=ALL",'_blank');
}


 function GenericRT(objID){
var table = document.getElementById('dataTable');
var rows = table.getElementsByTagName("tr");

for(i = 0; i < rows.length; i++){
var rowID = rows[i].id;
if(rowID!='hed' && rowID.indexOf('HITROW_')==-1){
if(objID=='ALL'){
document.getElementById(rowID).style.display='';
}else{
if(rowID.indexOf(objID)!=-1){
document.getElementById(rowID).style.display='';
}else if(rowID=='emphed') {
document.getElementById(rowID).style.display='';
}else{
document.getElementById(rowID).style.display='none';
}
}
}
}
table = document.getElementById('sum');
rows = table.getElementsByTagName("tr");

for(i = 0; i < rows.length; i++){
rowID = rows[i].id;
if(rowID!='sumHed'){
if(rowID==objID){
document.getElementById(rowID).style.display='';
}else{
document.getElementById(rowID).style.display='none';
}}
}
document.getElementById('calStt').value=objID;
}

function validate_IssueRptRtn(){
var issueId = document.getElementById("issueId").value;
var Typ = document.getElementById("Typ").value;
var mprcIdn = document.getElementById("mprcIdn").value;
var empIdn = document.getElementById("empIdn").value;
var vnm = document.getElementById("vnm").value;
var checked=false;
if(vnm.length!=""){
if((issueId!="" || Typ!="0" || mprcIdn!="0" || empIdn!="0")){
checked=true;
}else{
checked=false;
}
}else{
if(issueId!="" || Typ!="0" || mprcIdn!="0" || empIdn!="0" || vnm.length!=""){
checked=true;
}
}

if(!checked){
alert("Please Select Atleast Issue Id To fetch Result ");
}
return checked;
}

function goToPacketLookUp(theURL) { //v2.0
var mdl = document.getElementById("mdlLst").value;
window.open(theURL+"&mdl="+mdl, '_self');
}

function goTocontactmerge(theURL){
    var r = confirm("Are You Sure You Want To Merge Contact?");
   if(r){
      var frm_elements = document.forms[0].elements; 
        var flg=false;
		var masteridn='';
      
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();

            if (field_type == 'radio') {
                var fieldId = frm_elements[i].id;
                if (fieldId.indexOf('merge_') !=  - 1) {
                    var isChecked = document.getElementById(fieldId).checked;
                    if (isChecked) {
                        flg = true;
			masteridn=document.getElementById(fieldId).value;
                        break;
                    }
                }
            }}
        if(flg){
		window.open(theURL+"&masteridn="+masteridn, '_self');
        }else{
        return false;   
        }
   }
   return r;
}
 function verifycontactmerge(){
 var cnt=document.getElementById('cnt').innerHTML;
 if(cnt>50){
 alert('Here Not Possible to view More than 50 contacts Plz filter');
 return false;       
 }else if(cnt==0){
 alert('No Data Found');
 return false;    
 }else{
 return true;    
 }
 }

 function validatpriceChange(){
var frm_elements = document.forms[0].elements;
var isChecked = false;
var ms1=document.getElementById('MS_1').checked;
var ms2=document.getElementById('MS_2').checked;
var memoid=document.getElementById('memoId').value;
var vnm=document.getElementById('vnm').value;
if(ms1==true || ms2==true){
if(document.getElementById('MS_2').checked) {
if(((memoid!='' && memoid!=0)) || vnm!='') {
isChecked=true;
}
if(!isChecked){
alert("Please Enter Memo Id .");
document.getElementById('memoId').value='';
document.getElementById('memoId').focus();
}
return isChecked;
}
else {
if(document.getElementById("byrId").value!=0 && document.getElementById("rlnId").value!=0) {
isChecked=false;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('cb_memo')!=-1){
if(document.getElementById(fieldId).checked){
isChecked=true;
}
}
}
}
if(!isChecked){
alert("Please Select Memo Id");
return isChecked;
}
}else{
alert("Please Select Buyer and Terms .");
document.getElementById('byrId').value='';
document.getElementById('byrId').focus();
}
}
}else{
alert("Select Memos Search By Buyer or MemoId.");
isChecked=false;
}
return isChecked;
} 




function genericWeeks(idn){
var weeks=document.getElementById('WEEKS').value;
var url='';
url=document.getElementById(idn).href;
url=url.substring(0,url.lastIndexOf('=')+1);
document.getElementById(idn).href=url+weeks;
}


function displayWeekAVG(tblID,obj){
var chk = document.getElementById(tblID).checked;
var table = document.getElementById('ALL');
var spancell = table.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!=''){
if(spanID.indexOf(obj)!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
}
}
}

function validate_priCalMulti(checkId , count){
var countval = document.getElementById(count).value;
var isReturn=false;
for(var i=1;i<=countval;i++){
var objId = checkId+i;
var isChecked = document.getElementById(objId).checked ;
if(isChecked){
var crtWt=document.getElementById("CRTWT_"+i).value ;
if(crtWt!='')
isReturn=true;
else{
alert("Please Enter Caret Weight");
document.getElementById("CRTWT_"+i).focus();
return false;
}
}
}
if(isReturn==false){
alert("Please Select To Calculate");
return isReturn;
}
else{
return isReturn;
}
}


function validate_reChk(){
var lab = document.getElementById("LAB_PRC_1").value;
if(lab==0 || lab=='' ){
alert("Please Select Lab To Fetch");
return false;
}

return true;
}

function chkQtyCts(){
var ttlqty=document.getElementById('tQty').value;
var ttlcts=parseFloat(document.getElementById('tCts').value);
var ttlQtyVal='';
var ttlCtsVal='';
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='text') {
var fieldId = frm_elements[i].id;
if (fieldId.indexOf('RTNQTY_') != - 1) {
var QtyVal = document.getElementById(fieldId).value;
if(QtyVal!='')
ttlQtyVal+=parseFloat(QtyVal);
}
if (fieldId.indexOf('RTNCTS_') != - 1) {
var ctsVal = document.getElementById(fieldId).value;
if(ctsVal!='')
ttlCtsVal+=parseFloat(ctsVal);
}
}
}
if(ttlQtyVal=='' || ttlCtsVal==''){
alert("Please Enter Qty and cts ");
return false;
}
var ttlCtsValOperp=parseFloat(ttlcts)*0.01;
var ttlCtsValless=parseFloat(ttlcts)-parseFloat(ttlCtsValOperp);
var ttlCtsValplus=parseFloat(ttlcts)+parseFloat(ttlCtsValOperp);
if((ttlCtsVal>=ttlCtsValless && ttlCtsVal<=ttlCtsValplus) && ttlqty<=ttlQtyVal){
return true;
}
if(ttlqty<ttlQtyVal || ttlcts<ttlCtsVal ){
alert("Return Quentity or Cts is Greater than issue Quentity or Cts ");
return false;
}


return true;
}


function displayVerification(divId){
var rdSTT = new Array();
rdSTT[0]="NEW";
rdSTT[1]="REP";
rdSTT[2]="REVIEW";
for(var i =0 ;i<rdSTT.length ;i++){
var val = rdSTT[i];
if(val==divId){
document.getElementById(val).style.display='block';
document.getElementById(val+"_TAB").style.color='#ffffff';
document.getElementById('vertyp').value=val;
}else{
document.getElementById(val).style.display='none';
document.getElementById(val+"_TAB").style.color='black';
}
}
}
function chkSubmit() {
var frm_elements = document.forms['verification'].elements;
var flag = false;
for(i=0; i<frm_elements.length; i++)
{
field_type = frm_elements[i].type.toLowerCase();

if(field_type=='checkbox' && frm_elements[i].checked)
{
flag = frm_elements[i].checked;
break;
}
}
//alert(flag);
if(flag){
loading();
return true;
}else {
alert("Please select a stone for Verification.");
return false;
}

}

function CheckAllChkbox(id, fldNme){
var check =document.getElementById(id).checked;
var frm_elements = document.forms['verification'].elements;
for(i=0; i<frm_elements.length; i++)
{
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox')
{
var fieldId = frm_elements[i].id;
if(fieldId.indexOf(fldNme)!=-1){
document.getElementById(fieldId).checked = check;
}
}
}
}

function CheckAllradioBtn(allCk , name ){
var isChecked = document.getElementById(allCk).checked;

var frm_elements = document.forms['verification'].elements;

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
function validate_verify(){
var memo = document.getElementById("MEMO_1").value;
var mfg = document.getElementById("MFG_TYP_1").value;
if(memo==0 || memo==''){
alert("Please Enter Memo Id");
return false;
}
if(mfg==0 || mfg==''){
alert("Please Select Mfg Type");
return false;
}

return true;
}

function repairIssue(msg){
var empIdn = document.getElementById('empIdn').value;
if(empIdn=="0"){
alert("Please Select "+msg);
return false;
}
return true;
}

function goToCreateXl(theURL) {
var colSpan=0;
var qty="Y";
var cts="";
var avg="";
var rap="";
var fa="";
var mfg="";
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[i].checked){
var elementName =frm_elements[i].name;
if(elementName=="FAPRI_dis"){
colSpan++;
fa="Y";
}
if(elementName=="MFGPRI_dis"){
colSpan++;
mfg="Y";
}
if(elementName=="CTS_dis"){
colSpan++;
cts="Y";
}
if(elementName=="AVG_dis"){
colSpan++;
avg="Y";
}
if(elementName=="RAP_dis"){
colSpan++;
rap="Y";
}
}
}
}
window.open(theURL+"&colSpan="+colSpan+"&qty="+qty+"&cts="+cts+"&avg="+avg+"&rap="+rap+"&fap="+fa+"&mfgp="+mfg, '_self');
}
function goToMixCreateXl(theURL) {
var colSpan=1;
var qty="";
var rap="";
var cts="Y";
var avg="Y";
var fa="";
var mfg="";
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[i].checked){
var elementName =frm_elements[i].name;
if(elementName=="QTY_dis"){
colSpan++;
qty="Y";
}
if(elementName=="CTS_dis"){
colSpan++;
cts="Y";
}
if(elementName=="FAPRI_dis"){
colSpan++;
fa="Y";
}
if(elementName=="MFGPRI_dis"){
colSpan++;
mfg="Y";
}
}
}
}
window.open(theURL+"&colSpan="+colSpan+"&qty="+qty+"&cts="+cts+"&avg="+avg+"&rap="+rap+"&fap="+fa+"&mfgp="+mfg , '_self');
}


function displayReports(tblID,objID,obj){
var chk = document.getElementById(objID).checked;
var table = document.getElementById(tblID);
var spancell = table.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!=''){
if(spanID.indexOf('CLR_')!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
if(spanID.indexOf(obj)!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
}
}
}

function validatkaccha(){
var isChecked = false;
var v2=document.getElementById('deptLst').value;
var v1=document.getElementById('memoId').value;
if(v2=='' || v2==0){
isChecked = true;
}
if(isChecked){
alert("Select Dept To fetch Result");
return false;
}
if((v1=='' || v1==0)){
isChecked = true;
}
if(isChecked){
alert("Enter Memo Id To fetch Result");
document.getElementById('memoId').value='';
document.getElementById('memoId').focus();
return false;
}
return true;
}

function validatBoxPrp(){
var isChecked = false;
var bname=document.getElementById('bname').value;
var ptyp=document.getElementById('ptyp').value;
var qty=document.getElementById('qty').value;
var cts=document.getElementById('cts').value;

if((bname== '')){
isChecked = true;
}
if(isChecked){
alert("Please specify Box Name");
document.getElementById('bname').value='';
document.getElementById('bname').focus();
return false;
}
if(ptyp== '' || ptyp==0){
isChecked = true;
}
if(isChecked){
alert("Please specify PKT Type");
document.getElementById('ptyp').value='';
document.getElementById('ptyp').focus();
return false;
}
if((qty== '')){
isChecked = true;
}
if(isChecked){
alert("Please specify Qty");
document.getElementById('qty').value='';
document.getElementById('qty').focus();
return false;
}
if((cts== '')){
isChecked = true;
}
if(isChecked){
alert("Please specify Cts");
document.getElementById('cts').value='';
document.getElementById('cts').focus();
return false;
}
return true;
}



function confirmREPRCSH(typ){

   var msg = "Do you want to reprice";
   if(typ=='lab')
    msg = "Do you want to Apply for lab.";
   if(typ=='memo')
     msg = "Do you want take Memo ptint.";
    var r = confirm(msg);
    if(r){
        var frm_elements = document.forms[1].elements; 
        var flg=false;
      
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();

            if (field_type == 'checkbox') {
                var fieldId = frm_elements[i].id;
                if (fieldId.indexOf('cb_prc_') !=  - 1) {
                    var isChecked = document.getElementById(fieldId).checked;
                    if (isChecked) {
                        flg = true;
                        break;
                    }
                }
            }}
        if(flg){
              if(typ=='lab'){
              window.open("transferMktSH.do?method=save&typ="+typ, '_self');
              }else if(typ=='memo'){
              window.open("transferMktSH.do?method=memo", '_blank');
              }else{
              window.open("transferMktSH.do?method=save&typ="+typ, '_blank');
              }
             return true;
        }else{
            alert("Please select stones")
            return false;
        }
    }
    return r;
}

function pagniation(id){
var lastpage=document.getElementById('lastpage').value;
var currentpage=document.getElementById('currentpage').value;
var url=document.getElementById('url').value;

if(id=='findvnm'){
    var findvnm=document.getElementById('findvnm').value;
    if(findvnm!=''){
    window.open(url+"&lastpage="+lastpage+"&currentpage="+currentpage+"&findVnm="+findvnm,'_self')
    }else{
        alert('Please Enter Find Vnm');
        document.getElementById('findvnm').focus()
    }
}

if(id=='first')
window.open(url+"&lastpage="+lastpage+"&currentpage=0" ,'_self');

if(id=='previous'){
var val=document.getElementById('currentpage').value;
if(val==0)
window.open(url+"&lastpage="+lastpage+"&currentpage=0" ,'_self');
else
window.open(url+"&lastpage="+lastpage+"&currentpage="+(parseInt(currentpage)-1) ,'_self');
}

if(id=='next'){
var val=document.getElementById('currentpage').value;
if(val>=0 && val<parseInt(lastpage))
window.open(url+"&lastpage="+lastpage+"&currentpage="+(parseInt(currentpage)+1) ,'_self');
else if(val==lastpage)
window.open(url+"&lastpage="+lastpage+"&currentpage="+val ,'_self');
}
if(id=='last')
window.open(url+"&lastpage="+lastpage+"&currentpage="+lastpage ,'_self');
}

function checkDMD(obj,dmdId){

var isChecked = obj.checked;
var txtFld = document.getElementById("multiTxtdm").value;
var dmdDscID = "dmdDsc"+dmdId;

var dmdFld = document.getElementById(dmdDscID).innerHTML;
var fld="";
var txt = "" ;
if(isChecked){
fld = obj.value;

if(txtFld==''){
txtFld = dmdFld;
}else{
txtFld = txtFld+' , '+dmdFld;
}
document.getElementById("multiTxtdm").value = txtFld;
}
else{
fld = obj.value;
var indxFld = txtFld.indexOf(dmdFld);
if(indxFld==0){
if(txtFld.indexOf(',')!= -1){
txtFld = txtFld.replace(dmdFld+' , ','');
}else{
txtFld = txtFld.replace(dmdFld,'');
}
}else{
txtFld = txtFld.replace(' , '+dmdFld,'');
}
document.getElementById("multiTxtdm").value = txtFld;
}

}

function valDmddsc(){

var dmdNme = document.getElementById("dmdNme").value;
if(dmdNme==''){
alert("Please enter the Demand description");
return false;
}
return true;
}

function formSubmit()
{
document.forms[0].submit();
}



function changeImg(source) {
document.getElementById('change').src=source;
var zoomUrl=document.getElementById('zoomUrl').value;
document.getElementById('zoom').href=zoomUrl+source;
}

function uploadPurfile(idn){
    document.getElementById('loadFileIdn').value = idn;
}



function checkExlALL(checkId , count){
var checked = document.getElementById('checkExlAll').checked;
var countval = document.getElementById(count).value;
for(var i=1;i<=countval;i++){
var objId = checkId+i;
document.getElementById(objId).checked = checked;
}
}

function validatMonthCmp(){
var emp=document.getElementById('empLst').value;
if(emp=='' || emp==0){
alert("Select Employee To fetch Result");
return false;
}
return true;
}


 function disableEnterKey(e)
{
var key;
if(window.event)
key = window.event.keyCode; //IE
else
key = e.which; //firefox

return (key != 13);
}


function mixtomixTrf(loop)
{
var ctsf = parseFloat(document.getElementById('CTSF_'+loop).value);
var trff = parseFloat(document.getElementById('TRFF_'+loop).value);
if(ctsf>=trff){
document.getElementById('BAL_'+loop).value=parseFloat(ctsf)-parseFloat(trff);
document.getElementById('TRFT_'+loop).value=trff;
}else if(ctsf=='' || ctsf=='0'){
alert('Not Possible Transfer');
document.getElementById('nmef_'+loop).focus();
return false;
}else if(ctsf<trff){
alert('Current Cts is Less than Transfer Cts ');
document.getElementById('TRFF_'+loop).value='';
setTimeout(function(){document.getElementById('TRFF_'+loop).focus();},1);
return false;
}
}

function mixtomixTrt(loop)
{
var ctst = document.getElementById('CTST_'+loop).value;
var trft = document.getElementById('TRFT_'+loop).value;
document.getElementById('TTL_'+loop).value=parseFloat(ctst)+parseFloat(trft);
}
function mixtomixAllow(loop)
{
var trff = document.getElementById('TRFF_'+loop).value;
if(trff=='' || trff=='0'){
alert('Enter From Transfer cts');
document.getElementById('TRFF_'+loop).value='';
document.getElementById('TRFF_'+loop).focus();
return false;
}
}
function validateRtn_memo(){
    
     var issueid = document.getElementById('issueId').value;
    if(issueid==''){
         alert("Please Specify  Issue Id");
        return false;
    }
    
    return true;
}

function validate_memo(){
   
    var empIdn = document.getElementById('empIdn').value;
       if(empIdn=="0"){
      alert("Please Select  Employee.");
      return false;
     }
    return true;
}

 function validate_trfToMkt(){
var sh = document.getElementById('SHAPE_1').value;
if(sh=='0' || sh==''){
alert("Please Select Shape.");
document.getElementById('SHAPE_1').focus();
return false;
}
return true;
}

function validatetrfSave(){
var chkSz=false;
var chkClr=false;
var szcount = document.getElementById('szcount').value;
var clrcount = document.getElementById('clrcount').value;
for(var i=1;i<=szcount;i++){
var size=document.getElementById('sz_'+i).checked;
if(size){
chkSz=true;
break;
}}
for(var i=1;i<=clrcount;i++){
var clearity=document.getElementById('clr_'+i).checked;
if(clearity){
chkClr=true;
break;
}}
if(chkSz==false){
alert('Please Select Mix Size To save Changes');
return false;
}
if(chkClr==false){
alert('Please Select Mix Clarity To save Changes');
return false;
}
return true;
}


function validatememoBoxsaleRadio(){
var frm_elements = document.forms[0].elements;
var isChecked = false;
var ms1=document.getElementById('MS_1').checked;
var ms2=document.getElementById('MS_2').checked;
var memoid=document.getElementById('memoid').value;
if(ms1==true || ms2==true){
if(document.getElementById('MS_2').checked) {
if((memoid!='' && memoid!=0)) {
isChecked=true;
}
if(!isChecked){
alert("Please Enter Memo Id .");
document.getElementById('memoid').value='';
document.getElementById('memoid').focus();
}
return isChecked;
}
else {
if(document.getElementById("byrId").value!=0 && document.getElementById("rlnId").value!=0) {
isChecked=false;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio'){
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('cb_memo')!=-1){
if(document.getElementById(fieldId).checked){
isChecked=true;
}
}
}
}
if(!isChecked){
alert("Please Select Memo Id");
return isChecked;
}
}else{
alert("Please Select Buyer and Terms .");
document.getElementById('byrId').value='';
document.getElementById('byrId').focus();
}
}
}else{
alert("Select Memos Search By Buyer or MemoId.");
isChecked=false;
}
return isChecked;
}

function validatememoReturnRadio(){
var frm_elements = document.forms[0].elements;
var isChecked = false;
var ms1=document.getElementById('MS_1').checked;
var ms2=document.getElementById('MS_2').checked;
var memoid=document.getElementById('memoid').value;
var vnmLst=document.getElementById('vnmLst').value;
if(ms1==true || ms2==true){
if(document.getElementById('MS_2').checked) {
if((memoid!='' && memoid!=0) || (vnmLst!=0 && vnmLst!='')) {
isChecked=true;
}
if(!isChecked){
alert("Please Enter Memo Id / Packet Id");
document.getElementById('memoid').value='';
document.getElementById('memoid').focus();
}
return isChecked;
}
else {
if(document.getElementById("byrId").value!=0 && document.getElementById("rlnId").value!=0) {
isChecked=false;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio'){
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('cb_memo')!=-1){
if(document.getElementById(fieldId).checked){
isChecked=true;
}
}
}
}
if(!isChecked){
alert("Please Select Memo Id");
return isChecked;
}
}else{
alert("Please Select Buyer and Terms .");
document.getElementById('byrId').value='';
document.getElementById('byrId').focus();
}
}
}else{
alert("Select Memos Search By Buyer or MemoId.");
isChecked=false;
}
return isChecked;
}

function validatememosale(){
var frm_elements = document.forms[0].elements;
var isChecked = false;
var ms1=document.getElementById('MS_1').checked;
var ms2=document.getElementById('MS_2').checked;
var memoid=document.getElementById('memoid').value;
var vnmLst=document.getElementById('vnmLst').value;
if(ms1==true || ms2==true){
if(document.getElementById('MS_2').checked) {
if((memoid!='' && memoid!=0) || (vnmLst!=0 && vnmLst!='')) {
isChecked=true;
}
if(!isChecked){
alert("Please Enter Memo Id .");
document.getElementById('memoid').value='';
document.getElementById('memoid').focus();
}
return isChecked;
}
else {
if(document.getElementById("byrId").value!=0 && document.getElementById("rlnId").value!=0) {
isChecked=false;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[i].checked){
isChecked=true;
}
}
}
if(!isChecked){
alert("Please Select Memo Id Checkbox");
return isChecked;
}
}else{
alert("Please Select Buyer and Terms .");
document.getElementById('byrId').value='';
document.getElementById('byrId').focus();
}
}
}else{
alert("Select Memos Search By Buyer or MemoId.");
isChecked=false;
}
return isChecked;
}

function SubmitForm(formNme){
document.forms[formNme].submit();
}

function validateChangePass(){
var current = document.getElementById('current').value;
var newpass = document.getElementById('new').value;
var confirm = document.getElementById('confirm').value;
if(newpass==''){
alert('Enter New Password');
document.getElementById('new').focus();
return false;
}
if(confirm==''){
alert('Enter Confirm Password');
document.getElementById('confirm').focus();
return false;
}
if(newpass!='' && confirm!=''){
if(newpass==confirm){
if(newpass==current){
alert('Current password and New password are same');
document.getElementById('new').value='';
document.getElementById('confirm').value='';
document.getElementById('new').focus();
return false;
}
else
return true;
}else{
alert('New Passord and Confirm Password are not same');
document.getElementById('confirm').value='';
document.getElementById('confirm').focus();
return false;
}
}else{
alert('Enter New and Confirm Password');
document.getElementById('new').focus();
return false;
}
return false;
}

function validatePurPricing(){
var venId = document.getElementById('venId').value;
var purIdn = document.getElementById('purIdn').value;
var typ = document.getElementById('typ').value;
if(venId=='' || venId==0){
alert('Please Select Vender');
return false;
}
if(typ=='' || typ==0){
alert('Please Select Type');
return false;
}
if(purIdn=='' || purIdn==0){
alert('Please Select Purchase id');
return false;
}
return true;
}

function setByKey(objId,e){
var unicode=e.keyCode? e.keyCode : e.charCode;
var columnindex = document.getElementById('columnindex').value;
var rowindex = document.getElementById('rowindex').value;
var split=objId.split('_');
var row = parseInt(split[0]);
var column = parseInt(split[1]);
if (unicode==113 && column!=0){
document.getElementById(objId).value=document.getElementById(row+"_"+(column-1)).value;
}else if(unicode==113){
alert('Not Possible To copy');
}
if (unicode==114 && row!=0){
document.getElementById(objId).value=document.getElementById((row-1)+"_"+column).value;
}else if(unicode==114){
alert('Not Possible To copy');
}
if (unicode==13){
document.getElementById(row+"_"+(column+1)).focus();
}
}

function gridCalculationPricing(){
var columnindex = parseInt(document.getElementById('columnindex').value);
var rowindex = parseInt(document.getElementById('rowindex').value);
var activity = document.getElementById('ACTIVITY_1').checked;
var selected=true;
if(activity==''){
activity='BYCOLUMN';
}else{
activity='BYROW';
}
var cal = document.getElementById('CAL_1').checked;
if(cal==''){
cal='PER';
}else{
cal='ACTUAL';
}
var caltext = document.getElementById('calText').value;
if(caltext!=''){
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked
if(chk){
selected=false;
if (fieldId.indexOf('R_') != - 1) {
if(activity=='BYROW'){
var split=fieldId.split('_');
var nm = parseInt(split[0]);
var row = parseInt(split[1]);
for(p=0;p<columnindex; p++){
var val=document.getElementById(row+"_"+p).value;
if(val==''){
val='0';
}
if(cal=='ACTUAL'){
document.getElementById(row+"_"+p).value=parseFloat(val)+parseFloat(caltext);
}
if(cal=='PER'){
document.getElementById(row+"_"+p).value=(parseFloat(val)+((parseFloat(val)/100)*parseFloat(caltext))).toFixed(2);
}

}
}
}
if (fieldId.indexOf('C_') != - 1) {
if(activity=='BYCOLUMN'){
var split=fieldId.split('_');
var nm = parseInt(split[0]);
var column = parseInt(split[1]);
for(p=0;p<rowindex; p++){
var val=document.getElementById(p+"_"+column).value;
if(val==''){
val='0';
}
if(cal=='ACTUAL'){
document.getElementById(p+"_"+column).value=parseFloat(val)+parseFloat(caltext);
}
if(cal=='PER'){
document.getElementById(p+"_"+column).value=(parseFloat(val)+((parseFloat(val)/100)*parseFloat(caltext))).toFixed(2);
}
}
}
}
}
}
}}else{
alert('Please Enter Value In TextBox');
selected=false;
document.getElementById('calText').focus();
}
if(selected){
alert('Please Select Row Column CheckBox To Apply Calculation');
}
}

function gridCalculationPricingNew(){
var cal = "";
var caltext = document.getElementById('calText').value;
var frm_elements = document.forms[1].elements;
if(document.getElementById('CAL_1').checked) {
cal="ACTUAL";
}else if(document.getElementById('CAL_2').checked) {
cal="PER";
}else{
cal="STEPDWN";
}
if(caltext!=''){
  var rowAry = new Array();
  var colAry = new Array();
  var rowcounter=0;
  var colcounter=0;
  var executelogic=true;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked
if(chk){
var split=fieldId.split('_');
var nm = parseInt(split[0]);
var no = parseInt(split[1]);
if (fieldId.indexOf('R_') != - 1) {
rowAry[rowcounter++]=no;
}else if (fieldId.indexOf('C_') != - 1) {
colAry[colcounter++]=no;
}
}
}
}
if(rowAry.length==0){
    alert('Please Select Row CheckBox To Apply Calculation');
    executelogic=false;
}
if(colAry.length==0 && executelogic){
    alert('Please Select Column CheckBox To Apply Calculation');
    executelogic=false;
}
    if(executelogic){
    for(r=0;r<rowAry.length; r++){
    var rowno=rowAry[r];
    for(c=0;c<colAry.length; c++){
    var colno=colAry[c];
    var id=rowno+"_"+colno;
     var val=document.getElementById(id).value;
     if(val==''){
        val='0';
     }
     if(cal=='ACTUAL'){
     document.getElementById(id).value=parseFloat(val)+parseFloat(caltext);
     }
     if(cal=='PER'){
     document.getElementById(id).value=(parseFloat(val)+((parseFloat(val)/100)*parseFloat(caltext))).toFixed(2);
     }
     if(cal=='STEPDWN'){
     document.getElementById(id).value=(((100-parseFloat(replaceAll(val,'-','')))*(100-parseFloat(replaceAll(caltext,'-','')))/100)-100).toFixed(2);
     }
    }
    }
}
}else{
alert('Please Enter Value In TextBox');
selected=false;
document.getElementById('calText').focus();
}
}

function gridCalculationPricingManualNew(){
var cal = "";
var caltext = document.getElementById('calText').value;
var scriptkey = document.getElementById('scriptkey').value;
var frm_elements = document.forms[1].elements;
if(document.getElementById('CAL_1').checked) {
cal="ACTUAL";
}else if(document.getElementById('CAL_2').checked) {
cal="PER";
}else{
cal="STEPDWN";
}


if(caltext!=''){
  var rowAry = new Array();
  var colAry = new Array();
  var rowcounter=0;
  var colcounter=0;
  var executelogic=true;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked
if(chk){
var split=fieldId.split('_');
var nm = parseInt(split[0]);
var no = parseInt(split[1]);
if (fieldId.indexOf('R_') != - 1) {
rowAry[rowcounter++]=no;
}else if (fieldId.indexOf('C_') != - 1) {
colAry[colcounter++]=no;
}
}
}
}
if(rowAry.length==0){
    alert('Please Select Row CheckBox To Apply Calculation');
    executelogic=false;
}
if(colAry.length==0 && executelogic){
    alert('Please Select Column CheckBox To Apply Calculation');
    executelogic=false;
}
    if(executelogic){
    for(r=0;r<rowAry.length; r++){
    var rowno=rowAry[r];
    for(c=0;c<colAry.length; c++){
    var colno=colAry[c];
var repscriptkey=scriptkey;
repscriptkey=replaceAll(repscriptkey,'#',rowno+'~');
var id=replaceAll(repscriptkey,'.',colno+'~');
id=replaceAll(id,' ','');
id=id.substring(0,id.lastIndexOf('~'));
var val=document.getElementById(id).value;
     if(val==''){
        val='0';
     }
     if(cal=='ACTUAL'){
     document.getElementById(id).value=parseFloat(val)+parseFloat(caltext);
     }
     if(cal=='PER'){
     document.getElementById(id).value=(parseFloat(val)+((parseFloat(val)/100)*parseFloat(caltext))).toFixed(2);
     }
     if(cal=='STEPDWN'){
     document.getElementById(id).value=(((100-parseFloat(replaceAll(val,'-','')))*(100-parseFloat(replaceAll(caltext,'-','')))/100)-100).toFixed(2);
     }
    }
    }
}
}else{
alert('Please Enter Value In TextBox');
selected=false;
document.getElementById('calText').focus();
}
}


function setByKeyManual(row,column,e){
var unicode=e.keyCode? e.keyCode : e.charCode;
var columnindex = document.getElementById('totalColumn').value;
var rowindex = document.getElementById('totalRow').value;
var scriptkey = document.getElementById('scriptkey').value;
if (unicode==113 && parseInt(column)!=0){
var repscriptkey=scriptkey;
repscriptkey=replaceAll(repscriptkey,'#',row+'~');
copyfromID=replaceAll(repscriptkey,'.',(column-1)+'~');
copyfromID=replaceAll(copyfromID,' ','');
copyfromID=copyfromID.substring(0,copyfromID.lastIndexOf('~'));
copyToID=replaceAll(scriptkey,'#',row+'~');
copyToID=replaceAll(copyToID,'.',(column)+'~');
copyToID=replaceAll(copyToID,' ','');
copyToID=copyToID.substring(0,copyToID.lastIndexOf('~'));
document.getElementById(copyToID).value=document.getElementById(copyfromID).value;
}else if(unicode==113){
alert('Not Possible To copy');
}
if (unicode==114 && parseInt(row)!=0){
var repscriptkey=scriptkey;
repscriptkey=replaceAll(repscriptkey,'#',(row-1)+'~');
copyfromID=replaceAll(repscriptkey,'.',column+'~');
copyfromID=replaceAll(copyfromID,' ','');
copyfromID=copyfromID.substring(0,copyfromID.lastIndexOf('~'));
copyToID=replaceAll(scriptkey,'#',row+'~');
copyToID=replaceAll(copyToID,'.',column+'~');
copyToID=replaceAll(copyToID,' ','');
copyToID=copyToID.substring(0,copyToID.lastIndexOf('~'));
document.getElementById(copyToID).value=document.getElementById(copyfromID).value;
}else if(unicode==114){
alert('Not Possible To copy');
}
if (unicode==13){
copyToID=replaceAll(scriptkey,'#',row+'~');
copyToID=replaceAll(copyToID,'.',parseInt(parseInt(column)+1)+'~');
copyToID=replaceAll(copyToID,' ','');
copyToID=copyToID.substring(0,copyToID.lastIndexOf('~'));
document.getElementById(copyToID).focus();
}
}
function gridCalculationPricingManual(){
var columnindex = parseInt(document.getElementById('totalColumn').value);
var rowindex = parseInt(document.getElementById('totalRow').value);
var scriptkey = document.getElementById('scriptkey').value;
var activity = document.getElementById('ACTIVITY_1').checked;
var selected=true;
if(activity==''){
activity='BYCOLUMN';
}else{
activity='BYROW';
}
var cal = document.getElementById('CAL_1').checked;
if(cal==''){
cal='PER';
}else{
cal='ACTUAL';
}
var caltext = document.getElementById('calText').value;
if(caltext!=''){
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked
if(chk){
selected=false;
if (fieldId.indexOf('R_') != - 1) {
if(activity=='BYROW'){
var split=fieldId.split('_');
var nm = parseInt(split[0]);
var row = parseInt(split[1]);
for(p=0;p<columnindex; p++){
var repscriptkey=scriptkey;
repscriptkey=replaceAll(repscriptkey,'#',row+'~');
copyID=replaceAll(repscriptkey,'.',p+'~');
copyID=replaceAll(copyID,' ','');
copyID=copyID.substring(0,copyID.lastIndexOf('~'));
var val=document.getElementById(copyID).value;
if(val==''){
val='0';
}
if(cal=='ACTUAL'){
document.getElementById(copyID).value=parseFloat(val)+parseFloat(caltext);
}
if(cal=='PER'){
document.getElementById(copyID).value=(parseFloat(val)+((parseFloat(val)/100)*parseFloat(caltext))).toFixed(2);
}

}
}
}
if (fieldId.indexOf('C_') != - 1) {
if(activity=='BYCOLUMN'){
var split=fieldId.split('_');
var nm = parseInt(split[0]);
var column = parseInt(split[1]);
for(p=0;p<rowindex; p++){
var repscriptkey=scriptkey;
repscriptkey=replaceAll(repscriptkey,'#',p+'~');
copyID=replaceAll(repscriptkey,'.',column+'~');
copyID=replaceAll(copyID,' ','');
copyID=copyID.substring(0,copyID.lastIndexOf('~'));
var val=document.getElementById(copyID).value;
if(val==''){
val='0';
}
if(cal=='ACTUAL'){
document.getElementById(copyID).value=parseFloat(val)+parseFloat(caltext);
}
if(cal=='PER'){
document.getElementById(copyID).value=(parseFloat(val)+((parseFloat(val)/100)*parseFloat(caltext))).toFixed(2);
}

}
}
}
}
}
}}else{
alert('Please Enter Value In TextBox');
selected=false;
document.getElementById('calText').focus();
}
if(selected){
alert('Please Select Row Column CheckBox To Apply Calculation');
}
}


function replaceAll(str, src, dst) {
while (str.indexOf(src) !== -1) {
str = str.replace(src, dst);
}
return str;
}


 function goToHistory(theURL,winName,features) { //v2.0
var r = confirm("Are You Sure You Want To Create Excel?");
if(r){
document.getElementById('excel').style.display='none';
window.open(theURL, '_self');
}

}


function validatecarets() {
var cts = parseFloat(document.getElementById('cts').value)+parseFloat(0.2);
var count=document.getElementById('COUNT').value
var ttlcts=0;
for(p=0;p<count; p++){
var vnm=document.getElementById('VNM_'+p).value;
var carets=document.getElementById('CRTWT_'+p).value;
if(carets!='' && carets!=0){
ttlcts=ttlcts + parseFloat(carets);
var prpLst= document.getElementById('prpList');
var prpLgth= document.getElementById('prpList').length;
for(j=0;j<prpLgth; j++){
  var fld = prpLst.options[j].value;
  var fldVal=document.getElementById(fld+'_'+p).value;
  if(fldVal=="0" || fldVal=="NA"){
      alert("Please specify "+fld+" for Packet "+vnm);
      return false;
  }
}
}




}
if(ttlcts<=cts){
return true;
}else{
alert('Available Carets less than new Carets!')
return false;
}
}



function insertrow(currentrow) {
var columnindex = document.getElementById('totalColumn').value;
var rowindex = document.getElementById('totalRow').value;
var rowListsz = parseInt(document.getElementById('rowListsz').value)+parseInt(1);
var table=document.getElementById("priceGrd");
var scriptkey = document.getElementById('scriptkey').value;
var newrowindex=parseInt(rowindex)+parseInt(1);
var rowkey=scriptkey.substring(0,scriptkey.lastIndexOf('#'));
var nm='';
var id='';
var inctment=0;
var loopcolumnindex=columnindex;
var row=table.insertRow(parseInt(rowindex)+parseInt(rowListsz));
var cell1=row.insertCell(inctment++);
nm="R_"+rowindex;
id="R_"+rowindex;
cell1.innerHTML=newrowindex+" <input type=\"checkbox\" name=\""+nm+"\" value=\"Yes\" id=\""+id+"\" /> <img src=\"../images/addcolumn.jpg\" onclick=\"insertrow('"+rowindex+"')\" title=\"Click Here To Add Row\" />";
var split=rowkey.split('#');
for(var i =0 ;i<split.length ;i++){
var val = split[i];
val=replaceAll(val,' ','');
nm=val+rowindex;
nm=replaceAll(nm,' ','');
id=nm;
var cell2=row.insertCell(inctment++);
cell2.innerHTML="<input type=\"text\" name="+nm+" id="+id+" value=\"\" readonly=\"readonly\" Class=\"txtStyle\" size=\"5\" onclick=\"showDiv('"+val+"',this)\" />";
}
loopcolumnindex=parseInt(loopcolumnindex)+parseInt(inctment)
rowkey=replaceAll(scriptkey,'#',rowindex+'~');
var colinc=0;
for(p=inctment;p<loopcolumnindex; p++){
var key=replaceAll(rowkey,'.',colinc+'~');
key=key.substring(0,key.lastIndexOf('~'));
key=replaceAll(key,' ','');
var cell3=row.insertCell(p);
cell3.innerHTML="<input type=\"text\" name="+key+" id="+key+" value=\"\" Class=\"txtStyle\" size=\"5\" onkeydown=\"setByKeyManual('"+rowindex+"','"+colinc+"',event)\" />";
colinc++;
}
currentrow=parseInt(currentrow)+parseInt(1);
for(var j =rowindex ;j>=currentrow ;j--){
var copyfrom=replaceAll(scriptkey,'#',j-1+'~');
var copyto=replaceAll(scriptkey,'#',j+'~');
for(var s =0 ;s<split.length ;s++){
val = split[s];
var fromvalId=val;
var tovalId=val;
fromvalId=fromvalId+(j-1);
tovalId=tovalId+j;
fromvalId=replaceAll(fromvalId,' ','');
tovalId=replaceAll(tovalId,' ','');
if(j==currentrow){
document.getElementById(tovalId).value='';
}
else{
document.getElementById(tovalId).value=document.getElementById(fromvalId).value;
}
}
for(var k=0;k<columnindex; k++){
var from =replaceAll(copyfrom,'.',k+'~');
from=from.substring(0,from.lastIndexOf('~'));
from=replaceAll(from,' ','');
var to =replaceAll(copyto,'.',k+'~');
to=to.substring(0,to.lastIndexOf('~'));
to=replaceAll(to,' ','');
if(j==currentrow){
document.getElementById(to).value='';
}
else{
document.getElementById(to).value=document.getElementById(from).value;
}
}
}
document.getElementById('totalRow').value=newrowindex;
}

function insertcol(currentcol) {
var columnindex = document.getElementById('totalColumn').value;
var rowindex = document.getElementById('totalRow').value;
var table=document.getElementById("priceGrd").tBodies[0];
var scriptkey = document.getElementById('scriptkey').value;
var colkey=scriptkey.substring(scriptkey.lastIndexOf('#')+1,scriptkey.length-1);
var newcolindex=parseInt(columnindex)+parseInt(1);
var nm='';
var id='';
var inctment=0;
nm="C_"+columnindex;
id="C_"+columnindex;
var cell1 = table.rows[inctment++].insertCell(-1);
cell1.innerHTML=newcolindex+" <input type=\"checkbox\" name=\""+nm+"\" value=\"Yes\" id=\""+id+"\" /> <img src=\"../images/addcolumn.jpg\" onclick=\"insertcol('"+columnindex+"')\" title=\"Click Here To Add Column\" />";
var split=colkey.split('.');
for(var i =0 ;i<split.length ;i++){
var val = split[i];
val=replaceAll(val,' ','');
nm=val+columnindex;
nm=replaceAll(nm,' ','');
id=nm;
var cell2 = table.rows[inctment++].insertCell(-1);
cell2.innerHTML="<input type=\"text\" name="+nm+" id="+id+" value=\"\" readonly=\"readonly\" Class=\"txtStyle\" size=\"5\" onclick=\"showDiv('"+val+"',this)\" />";
}
var rowinc=0;
colkey=replaceAll(scriptkey,'.',columnindex+'~');
for (var j=inctment; j<table.rows.length; j++) {
var key=replaceAll(colkey,'#',rowinc+'~');
key=key.substring(0,key.lastIndexOf('~'));
key=replaceAll(key,' ','');
var cell3 = table.rows[j].insertCell(-1);
cell3.innerHTML="<input type=\"text\" name="+key+" id="+key+" value=\"\" Class=\"txtStyle\" size=\"5\" onkeydown=\"setByKeyManual('"+columnindex+"','"+rowinc+"',event)\" />";
rowinc++;
}
currentcol=parseInt(currentcol)+parseInt(1);
for(var k =columnindex ;k>=currentcol ;k--){
var copyfrom=replaceAll(scriptkey,'.',(k-1)+'~');
var copyto=replaceAll(scriptkey,'.',k+'~');
for(var s =0 ;s<split.length ;s++){
val = split[s];
var fromvalId=val;
var tovalId=val;
fromvalId=fromvalId+(k-1);
tovalId=tovalId+k;
fromvalId=replaceAll(fromvalId,' ','');
tovalId=replaceAll(tovalId,' ','');
if(k==currentcol){
document.getElementById(tovalId).value='';
}
else{
document.getElementById(tovalId).value=document.getElementById(fromvalId).value;
}
}
for(var l=0;l<rowindex; l++){
var from =replaceAll(copyfrom,'#',l+'~');
from=from.substring(0,from.lastIndexOf('~'));
from=replaceAll(from,' ','');
var to =replaceAll(copyto,'#',l+'~');
to=to.substring(0,to.lastIndexOf('~'));
to=replaceAll(to,' ','');
if(k==currentcol){
document.getElementById(to).value='';
}else{
document.getElementById(to).value=document.getElementById(from).value;
}
}
}
document.getElementById('totalColumn').value=newcolindex;
}

function replaceAt(str,index, char) {
return str.substring(0, index) + char + str.substring(index+1,str.length);
}

function goToCreateXl(theURL) {
var colSpan=0;
var qty="Y";
var cts="";
var avg="";
var rap="";
var age="";
var vlu="";
var fa="";
var mfg="";
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[i].checked){
var elementName =frm_elements[i].name;
if(elementName=="CTS_dis"){
colSpan++;
cts="Y";
}
if(elementName=="FAPRI_dis"){
colSpan++;
fa="Y";
}
if(elementName=="MFGPRI_dis"){
colSpan++;
mfg="Y";
}
if(elementName=="AVG_dis"){
colSpan++;
avg="Y";
}
if(elementName=="RAP_dis"){
colSpan++;
rap="Y";
}
if(elementName=="VLU_dis"){
colSpan++;
vlu="Y";
}
if(elementName=="AGE_dis"){
colSpan++;
age="Y";
}
}
}
}
window.open(theURL+"&colSpan="+colSpan+"&qty="+qty+"&cts="+cts+"&avg="+avg+"&rap="+rap+"&vlu="+vlu+"&fap="+fa+"&mfgp="+mfg+"&age="+age, '_self');
}


function displayover(id){
document.getElementById(id).style.display='';
}

function displayout(id){
document.getElementById(id).style.display='none';
}

function refreshdash(idname) {
document.getElementById(idname).contentWindow.location.reload();
}

function setBrokerComm(id,set) {

var val=document.getElementById(id).value;
if(val==''){
val='0'
}

if(set=='Y'){
document.getElementById(id+'val').value='0';
}else{
document.getElementById(id+'val').value=val;
}
}


function setBrokerCommMIX(id,set) {
if(set=='Y'){

document.getElementById(id).value=document.getElementById(id+'txt').innerHTML;
}else{
document.getElementById(id).value='0';
}
}


function viewDetailGenericGraph(key,chart,typ){
var reqUrl = document.getElementById('reqUrl').value;
var checkedCol = key+'_row_';
var checkedClr = key+'_col_';
if(checkedCol.indexOf('~')!= -1){
checkedCol = checkedCol.replace('~','+');
}
if(checkedClr.indexOf('~')!= -1){
checkedClr = checkedClr.replace('~','+');
}
var Col='';
var totalCol="";
var Clr='';
var totalClr="";
var loopCol=document.getElementById('loopcol').value;
for(i = 0; i < loopCol; i++){
var cheCol = document.getElementById(checkedCol+i).checked;
if(cheCol){
Col=document.getElementById(checkedCol+i).value;
if(totalCol.length==0){
totalCol=totalCol+Col+"";
}
else{
totalCol=totalCol+","+Col+"";
}
}
}
var loopClr=document.getElementById('loopclr').value;
for(i =0; i < loopClr; i++){
var cheClr= document.getElementById(checkedClr+i).checked;
if(cheClr){
Clr=document.getElementById(checkedClr+i).value;
if(totalClr.length==0){
totalClr=totalClr+Clr+"";
}
else{
totalClr=totalClr+","+Clr+"";
}
}
}

var count=totalCol.lastIndexOf("");
totalCol=totalCol.substring(0,count)
count=totalClr.lastIndexOf("");
totalClr=totalClr.substring(0,count)

if(totalClr.length ==0)
{
totalClr='ALL';
}
if(totalCol.length ==0)
{
totalCol='ALL';
}
if(chart=='PIE'){
window.open(reqUrl+"/report/commonpieSelection.jsp?key="+key+"&row="+totalCol+"&col="+totalClr+"&status=ALL&typ="+typ,'SC');
}
if(chart=='BAR'){

window.open(reqUrl+"/report/barGraphSelection.jsp?key="+key+"&row="+totalCol+"&col="+totalClr+"&status=ALL&typ="+typ,'SC');
}
}

function viewDetailGenericGraphQuick(key,chart,typ){
var reqUrl = document.getElementById('reqUrl').value;
var checkedCol = key+'_row_';
var checkedClr = key+'_col_';
if(checkedCol.indexOf('~')!= -1){
checkedCol = checkedCol.replace('~','+');
}
if(checkedClr.indexOf('~')!= -1){
checkedClr = checkedClr.replace('~','+');
}
var Col='';
var totalCol="";
var Clr='';
var totalClr="";
var loopCol=document.getElementById('loopcol').value;
for(i = 0; i < loopCol; i++){
var cheCol = document.getElementById(checkedCol+i).checked;
if(cheCol){
Col=document.getElementById(checkedCol+i).value;
if(totalCol.length==0){
totalCol=totalCol+Col+"";
}
else{
totalCol=totalCol+","+Col+"";
}
}
}
var loopClr=document.getElementById('loopclr').value;
for(i =0; i < loopClr; i++){
var cheClr= document.getElementById(checkedClr+i).checked;
if(cheClr){
Clr=document.getElementById(checkedClr+i).value;
if(totalClr.length==0){
totalClr=totalClr+Clr+"";
}
else{
totalClr=totalClr+","+Clr+"";
}
}
}

var count=totalCol.lastIndexOf("");
totalCol=totalCol.substring(0,count)
count=totalClr.lastIndexOf("");
totalClr=totalClr.substring(0,count)

if(totalClr.length ==0)
{
totalClr='ALL';
}
if(totalCol.length ==0)
{
totalCol='ALL';
}
if(chart=='PIE'){
window.open(reqUrl+"/report/commonpieSelection.jsp?key="+key+"&row="+totalCol+"&col="+totalClr+"&status=ALL&typ="+typ,'SC');
}
if(chart=='BAR'){

window.open(reqUrl+"/report/barGraphSelection.jsp?key="+key+"&row="+totalCol+"&col="+totalClr+"&status=ALL&typ="+typ,'SC');
}
}
function validatesendMail(){
var subject = document.getElementById('subject').value;
var msg = document.getElementById('msg').value;
if(subject==''){
alert('Please Enter Subject To send Mail');
document.getElementById('subject').focus();
return false;
}
if(msg==''){
alert('Please Enter Message To send Mail');
return false;
}
return true;
}

 function dowenloadexcel(mail){
var reqUrl = document.getElementById('reqUrl').value;
var labchk='';
var issueidnchk='';
var chk=false;
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[i].checked){
chk=true;
var elementName =frm_elements[i].name;
var val=document.getElementById(elementName).value;
val=val+',';
if(labchk.indexOf(val) == -1) {
labchk = labchk+val;
}
issueidnchk=issueidnchk+','+elementName;
}
}
}
if(!chk){
alert('Please Select Checkbox');
return false;
}
if(labchk.length>5){
alert('Multiple Lab Not Allowed To Create Excel');
return false;
}
labchk=labchk.substring(0,labchk.length-1);
issueidnchk=issueidnchk.substring(1,issueidnchk.length);
window.open(reqUrl+"/excel/labxl?issueIdn="+issueidnchk+"&lab="+labchk+"&mailExcl="+mail,'_blank');
}

 function chargesmanualrmk(typ,loop){
document.getElementById(typ+'_rmksave').value=document.getElementById(typ+'_rmk').value;
}
function chargesfixed(typ){
document.getElementById(typ+'_save').value=document.getElementById(typ).value;
}

function chargesfixedSH(typ){
document.getElementById(typ+'_save').value=document.getElementById(typ).value;
}
function closewindow(twindow)
{
var reqUrl = document.getElementById('reqUrl').value;
window.open(reqUrl+'/blank.jsp',twindow);
}

function calpurdis(rapID,rteID,disID){
var rap=document.getElementById(rapID).value;
if(rap!='' && rap!='0'){
if(rap=='1'){
document.getElementById(disID).value='';
}else{
var rte=document.getElementById(rteID).value;
if(rte!='' && rte!='0'){
var uprDis= ((((parseFloat(rte)/parseFloat(rap))*100) -100))
document.getElementById(disID).value=myRound(uprDis,2);
}else{
document.getElementById(disID).value='';
}
}
}else{
 document.getElementById(disID).value='';
}
}

function calpurLoc(refID,locID){
var vnm=String(document.getElementById(refID).value.toUpperCase());
if(vnm.indexOf('YK')!= -1){
    document.getElementById(locID).value="IND";
}else if(vnm.indexOf('SLP')!= -1){
    document.getElementById(locID).value="PC HK";
}else if(vnm.indexOf('LP')!= -1){
    document.getElementById(locID).value="HK";
}
}

function calpurrate(rapID,rteID,disID){
var rap=document.getElementById(rapID).value;
if(rap!='' && rap!='0'){
var dis=document.getElementById(disID).value;
if(dis!='' && dis!='0'){
var rate = ( rap * (100+parseFloat(dis))/100);
document.getElementById(rteID).value = myRound(rate,2);
}else{
document.getElementById(rteID).value=''; 
}
}else{
document.getElementById(rteID).value='';
}
}

function calpurdisrate(rapID,rteID,disID){
var dis=document.getElementById(disID).value;
var rte=document.getElementById(rteID).value;
var rap=document.getElementById(rapID).value;
var rate=0;
var exe=true;
if(dis!='' && dis!='0' && exe){
rate = ( rap * (100+parseFloat(dis))/100);
document.getElementById(rteID).value = myRound(rate,2);
exe=false;
}
if(rte!='' && rte!='0' && exe){
var uprDis= ((((parseFloat(rte)/parseFloat(rap))*100) -100))
document.getElementById(disID).value=myRound(uprDis,2);
}
}

function purlab(popid,tgt,frm){
document.getElementById('close').style.display='none';
document.getElementById('closereload').style.display='block';
var reqUrl = document.getElementById('reqUrl').value;
if(frm=='PUR'){
var purIdn=document.getElementById('purIdn').value;
var refidnLst='';
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked;
if (fieldId.indexOf('upd_') != - 1 && chk) {
var split=fieldId.split('_');
var purdtlidn = parseInt(split[1]);
var refidn=document.getElementById('ref_idn_'+purdtlidn).value;
if(refidn!='' && refidn!='0'){
refidnLst=refidnLst+","+refidn;
}
}
}
}
if(refidnLst!=''){
loadASSFNLPop('summary');
refidnLst=refidnLst.substring(1,refidnLst.length);
window.open(reqUrl+'/masters/utility.do?method=loadlab&purIdn='+purIdn+"&refidn="+refidnLst+"&typ=PUR",tgt);
}else{
alert("Please Select Checkbox");
return false;
}
}
if(frm=='TRF'){
loadASSFNLPop('summary');
window.open(reqUrl+'/masters/utility.do?method=loadlab&typ=TRF',tgt);
}
if(frm=='STK'){
loadASSFNLPop('summary');
window.open(reqUrl+'/masters/utility.do?method=loadlab&typ=STK',tgt);
}
if(frm=='TALLYHK'){
loadASSFNL('SC_0','LNK_1');
window.open(reqUrl+'/marketing/deleteStocktallyhk.jsp','SC');
}
if(frm=='TALLY'){
loadASSFNL('SC_0','LNK_1');
window.open(reqUrl+'/marketing/newStockTally.do?method=loaddelete','SC');
}
}
function reportUploadclose(frm){
var reqUrl = document.getElementById('reqUrl').value;
if(frm=='PUR'){
var purIdn=document.getElementById('purIdn').value;
window.open(reqUrl+"/purchase/purchaseDtlAction.do?method=load&purIdn="+purIdn,'_self');
}
if(frm=='TRF'){
window.open(reqUrl+"/Inward/transferMktSH.do?method=load",'_self');
}
if(frm=='STK'){
window.open(reqUrl+"/marketing/StockPrpUpd.do?method=load",'_self');
}
if(frm=='TALLYHK'){
window.open(reqUrl+"/marketing/stockTallyhk.do?method=loadTally",'_self');
}
if(frm=='TALLY'){
window.open(reqUrl+"/marketing/newStockTally.do?method=loadTally",'_self');
}
if(frm=='CNT'){
window.open(reqUrl+"/contact/advcontact.do?method=loadSearch",'_self');
}
if(frm=='CNTFEEDBACK'){
window.open(reqUrl+"/contact/advcontact.do?method=loadFeedback",'_self');
}
}

function keypressMix(fieldId,nextSrt,fld,e){
var unicode=e.keyCode? e.keyCode : e.charCode;
if(unicode==13){
if(fld=='CTS'){
document.getElementById('PRI_'+fieldId).focus();
}
if(fld=='PRI'){
fieldId=fieldId.replace('PRI_','');
var split=fieldId.split('_');
fieldId=fieldId.replace(split[0],nextSrt);
document.getElementById(fieldId).focus();
}
}
}

function AllSelectOne(obj,lprp, formNum){
  var subStr = "UPD_"+lprp;
  var selVal=obj.value;
  var frm_elements = document.forms[formNum].elements;
  for(i=0; i<frm_elements.length; i++) {
    var field_type = frm_elements[i].type.toLowerCase();
     if(field_type=='select-one' || field_type=='text') {
       var fieldId = frm_elements[i].id;
      if(fieldId.indexOf(subStr)!=-1){
            document.getElementById(fieldId).value=selVal;
            
            var fieldS = fieldId.split("_");
            var stkIdn = fieldS[fieldS.length-1];
           
            var lab = document.getElementById("LAB_"+stkIdn).value;
            stkUpd(lab,stkIdn,lprp,obj);
}
}
} 
}
function tester(){
    alert(document.getElementById('exhRte').readOnly);
}
function goToCreateExcel(theURL,winName,features) { //v2.0
var valid=false;
var nmeidnLst='',dteLst='';
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked;
if (fieldId.indexOf('XL_') != - 1 && chk) {
var fieldval=document.getElementById(fieldId).value;
valid=true;
var split=fieldval.split('_');
var idn = split[0];
var dte = split[1];
nmeidnLst=nmeidnLst+","+idn;
dteLst=dteLst+","+dte;
}
}
}
if(valid){
dteLst=replaceAll(dteLst,'-','');
theURL=theURL+"&nmeidn="+nmeidnLst+"&dte="+dteLst;
window.open(theURL, '_blank');
}
if(!valid){
alert('Please Select Checkbox to get Packets');
}
return false;
}

function goToCreateExcelDlv(theURL,winName,features) { //v2.0
var valid=false;
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked;
if (fieldId.indexOf('XL_') != - 1 && chk) {
valid=true;
break;
}
}
}
if(valid){
window.open(theURL, '_blank');
}
if(!valid){
alert('Please Select Checkbox to get Packets');
}
return false;
}

function checkAllpageDlv(obj , fldId){
     var ischecked  = obj.checked;
     var frm_elements = document.getElementsByTagName('input');
      for(i=0; i<frm_elements.length; i++) {
        var field_type = frm_elements[i].type.toLowerCase();
          if(field_type=='checkbox') {
          var fieldId = frm_elements[i].id;
           if(fieldId.indexOf(fldId)!=-1){
           document.getElementById(fieldId).checked = ischecked;
           }
          }
     }
           var url = "dailyDlvReport.do?method=setDailyDlvParam&nmeidnDte=ALL&chk="+ischecked;
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

function goToCreateConsReceExcel(theURL,winName,features) { //v2.0
var valid=false;
var memoidnLst='',dteLst='';
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked;
if (fieldId.indexOf('cb_memo_') != - 1 && chk) {
var fieldval=document.getElementById(fieldId).value;
valid=true;
var split=fieldval.split('cb_memo_');
var idn = split[0];
//var dte = split[2];
memoidnLst=memoidnLst+","+idn;
//dteLst=dteLst+","+dte;
}
}
}
if(valid){
dteLst=replaceAll(dteLst,'-','');
theURL=theURL+"&memoid="+memoidnLst//+"&dte="+dteLst;
window.open(theURL, '_blank');
}
if(!valid){
alert('Please Select Checkbox to get Packets');
}
return false;
}
function goToCreateMemoExcel(theURL,winName,features) { //v2.0
var valid=false;
var nmeidnLst='',dteLst='';
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
var fieldId = frm_elements[i].id;
if(fieldId!=''){
var chk=document.getElementById(fieldId).checked;
if (fieldId.indexOf('XL_') != - 1 && chk) {
var fieldval=document.getElementById(fieldId).value;
valid=true;
var split=fieldval.split('_');
var idn = split[0];
var dte = split[1];
nmeidnLst=nmeidnLst+","+idn;
dteLst=dteLst+","+dte;
}
}
}}
if(valid){
dteLst=replaceAll(dteLst,'-','');
theURL=theURL+"&nmeidn="+nmeidnLst+"&dte="+dteLst;
window.open(theURL, '_blank');
}
if(!valid){
alert('Please Select Checkbox to get Packets');
}
return false;
}
function goToCreateApproveExcel(theURL,winName,features) { //v2.0
var valid=false;
var nmeidnLst='',dteLst='';
var memotbl=document.getElementById("memotbl").value;
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked;
if (fieldId.indexOf('XL_') != - 1 && chk) {
var fieldval=document.getElementById(fieldId).value;
valid=true;
var split=fieldval.split('_');
var idn = split[0];
var dte = split[1];
nmeidnLst=nmeidnLst+","+idn;
dteLst=dteLst+","+dte;
}
}
}
if(valid){
dteLst=replaceAll(dteLst,'-','');
theURL=theURL+"&nmeidn="+nmeidnLst+"&dte="+dteLst+"&memotbl="+memotbl;
window.open(theURL, '_blank');
}
if(!valid){
alert('Please Select Checkbox to get Packets');
}
return false;
}

function validatecontactreporttyp(){
var reporttyp=document.getElementById('reporttyp').value;
var dtefr=document.getElementById('dtefr').value;
var dteto=document.getElementById('dteto').value;
if(dtefr!='' && dteto!='' && reporttyp!=''){
var diff = days_between(dtefr,dteto);
if(diff>45){
alert('Maximum Date Range Difference is 45') ;
document.getElementById('dtefr').value='';
document.getElementById('dteto').value='';
return false;
}
}
    return true;
}
function days_between(dtefr, dteto) {
    dtefr = dtefr.split("-");
    var year = dtefr[2]; var month = dtefr[1]; var day =  dtefr[0]; 
    dtefr=new Date(year, month - 1, day) 
    dteto = dteto.split("-");
    year = dteto[2];  month = dteto[1];  day =  dteto[0]; 
    dteto=new Date(year, month - 1 , day) //Month is 0-11 in JavaScript
    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24
    // Convert both dates to milliseconds
    var dtefr_ms = dtefr.getTime()
    var dteto_ms = dteto.getTime()
    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(dtefr_ms - dteto_ms)
    // Convert back to days and return
    return Math.round(difference_ms/ONE_DAY)
}
function contactbulkupdate(page){
var reqUrl = document.getElementById('reqUrl').value;
document.getElementById('close').style.display='none';
document.getElementById('closereload').style.display='block';
loadDashPOP('1','DTL_1');
if(page=='BULK'){
reqUrl=reqUrl+"/contact/contactBulkUpd.jsp";
}
if(page=='EXCEL'){
reqUrl=reqUrl+"/contact/contactXlFilter.jsp";
}
if(page=='ROLE'){
reqUrl=reqUrl+"/contact/bulkUpdate.do?method=viewbulkwebrole";
}
if(page=='SALEEX'){
reqUrl=reqUrl+"/contact/bulkUpdate.do?method=loadSaleExAlloc";
}
window.open(reqUrl, 'SC');
}
function  clientFeedback(empIdn,nmeIdn){
var reqUrl = document.getElementById('reqUrl').value;
document.getElementById('close').style.display='none';
document.getElementById('closereload').style.display='block';
loadDashPOP('1','DTL_1');
reqUrl=reqUrl+"/contact/advcontact.do?method=loadclientFeedback&nmeIdn="+nmeIdn+"&empIdn="+empIdn;
window.open(reqUrl, 'SC');
}
function  clientFeedbackrpt(empIdn,nmeIdn){
var reqUrl = document.getElementById('reqUrl').value;
loadDashPOP('1','DTL_1');
reqUrl=reqUrl+"/contact/advcontact.do?method=loadclientFeedback&nmeIdn="+nmeIdn+"&empIdn="+empIdn;
window.open(reqUrl, 'SC');
}
function checkALLPerforma(checkId , count){
    var checked = document.getElementById('checkAll').checked;
    var countval = document.getElementById(count).value;
    for(var i=1;i<=countval;i++){
        var objId = checkId+i;
        document.getElementById(objId).checked = checked;
    }
    if(checked){
     document.getElementById('ctsTtl').innerHTML = document.getElementById('ttlcts').innerHTML;
     document.getElementById('qtyTtl').innerHTML = document.getElementById('ttlqty').innerHTML;
     document.getElementById('grandttlVluTtl').innerHTML = document.getElementById('grandttlvlu').innerHTML;
    }else{
     document.getElementById('ctsTtl').innerHTML = "0";
     document.getElementById('qtyTtl').innerHTML = "0";
     document.getElementById('grandttlVluTtl').innerHTML = "0";
    } 
}

function PerformaTotalCal(obj , cts,vlu, stkIdn){
    var isChecked = obj.checked;
    var ctsDiv = document.getElementById('ctsTtl').innerHTML;
    var qtyDiv = document.getElementById('qtyTtl').innerHTML;
    var vluDiv = document.getElementById('grandttlVluTtl').innerHTML;
    var ctsTtl = "";
    var qtyTtl = "";
    var vluTtl = "";
    if(ctsDiv==0){
      ctsTtl = cts;
      qtyTtl = 1;
      vluTtl=vlu;
    }else if(isChecked){
      qtyTtl = parseInt(qtyDiv)+1;
      ctsTtl = (parseFloat(ctsDiv) + parseFloat(cts));
      vluTtl = (parseFloat(vluDiv) + parseFloat(vlu));
    }else{
      qtyTtl = parseInt(qtyDiv)-1;
      ctsTtl =(parseFloat(ctsDiv) - parseFloat(cts));
      vluTtl = (parseFloat(vluDiv) - parseFloat(vlu));
    }
     document.getElementById('ctsTtl').innerHTML = (new Number(ctsTtl)).toFixed(2);
     document.getElementById('qtyTtl').innerHTML = qtyTtl;
     document.getElementById('grandttlVluTtl').innerHTML = (new Number(vluTtl)).toFixed(2);
}

function PerformachargeCal(obj ,vlu){
    var isChecked = obj.checked;
    var vluDiv = document.getElementById('grandttlVluTtl').innerHTML;
if(vluDiv==0){
      vluTtl=vlu;
    }else if(isChecked){
      vluTtl = (parseFloat(vluDiv) + parseFloat(vlu));
    }else{
      vluTtl = (parseFloat(vluDiv) - parseFloat(vlu));
    }
    document.getElementById('grandttlVluTtl').innerHTML = (new Number(vluTtl)).toFixed(2);
}

function validatepriceDtlMatrix(){
var shape= document.getElementById('MTXT_SHAPE').value;
var priceondte= document.getElementById('priceondte').value;
var compondte= document.getElementById('compondte').value;
if(shape=='ALL' || shape.indexOf(',') != - 1){
if(shape=='ALL'){
alert('Please Select Shape');
return false;
}else{
alert('Please only one Shape to fetch'); 
return false;
}
}
if(priceondte==''){
 alert('Please Select Price On Date to fetch');    
 return false;
}
if(compondte==''){
 alert('Please Select Compare Date to fetch'); 
 return false;
}
return true
}

function reportmenu(blk,enlarge){
document.getElementById('reportmenu').style.display=blk;
document.getElementById('enlarge').style.display=enlarge;
}

function validateAnalysisRpt(){
if(document.getElementById('bycriteria').checked){
var tab = document.getElementById ("statusTbl"); // get table  id  which contain check box
var elems = tab.getElementsByTagName ("input");
for ( var i = 0; i < elems.length; i++ ){
if ( elems[i].type =="checkbox"){
var fieldId = elems[i].id;
if (document.getElementById(fieldId).checked ==true){
return true
}
}
}
alert('Please Check Status Checkbox To get Data');
return false;
}else{
var vnm=document.getElementById('pid').value;
if(vnm==''){
alert('Selected Search By Packet Please Enter Packet To fetch result');
return false;
}else{
return true; 
}
}
}

function displayAllCTSAVG(objID,obj){
var chk = document.getElementById(objID).checked;
var spancell = document.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!=''){
if(spanID.indexOf('CLR_')!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
if(spanID.indexOf(obj)!=-1){
if(chk==true)
document.getElementById(spanID).style.display='';
else
document.getElementById(spanID).style.display='none';
}
}
}
var tab = document.getElementById ("gridall"); // get table  id  which contain check box
var elems = tab.getElementsByTagName ("input");
for ( var i = 0; i < elems.length; i++ ){
if ( elems[i].type =="checkbox"){
var fieldId = elems[i].id;
if (fieldId.indexOf(objID)!=-1){
document.getElementById(fieldId).checked = chk;
}
}
}
}

function createXlAnal(i){
var colSpan="0";
var qty="";
var cts="";
var avg="";
var rap="";
var age="";
var hit="";
var vlu="";
var frm_elements = document.getElementsByTagName('input');
for(j=0; j<frm_elements.length; j++) {
field_type = frm_elements[j].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[j].checked){
var elementName =frm_elements[j].name;
if(elementName=="QTY_dis_"+i){
colSpan++;
qty="Y";
}
if(elementName=="CTS_dis_"+i){
colSpan++;
cts="Y";
}
if(elementName=="AVG_dis_"+i){
colSpan++;
avg="Y";
}
if(elementName=="RAP_dis_"+i){
colSpan++;
rap="Y";
}
if(elementName=="AGE_dis_"+i){
colSpan++;
age="Y";
}
if(elementName=="HIT_dis_"+i){
colSpan++;
hit="Y";
}
if(elementName=="VLU_dis_"+i){
colSpan++;
vlu="Y";
}
}
}
}
colSpan=colSpan-1;
var theURL="genericReport.do?method=createGenXL&loopno="+i+"&colSpan="+colSpan+"&qty="+qty+"&cts="+cts+"&avg="+avg+"&rap="+rap+"&age="+age+"&hit="+hit+"&vlu="+vlu;
window.open(theURL , '_self');
}

function createXlComnAnal(i){
var colSpan="0";
var qty="Y";
var cts="";
var avg="";
var rap="";
var age="";
var hit="";
var frm_elements = document.getElementsByTagName('input');
for(j=0; j<frm_elements.length; j++) {
field_type = frm_elements[j].type.toLowerCase();
if(field_type=='checkbox'){
if(frm_elements[j].checked){
var elementName =frm_elements[j].name;
if(elementName=="CTS_dis"){
colSpan++;
cts="Y";
}
if(elementName=="AVG_dis"){
colSpan++;
avg="Y";
}
if(elementName=="RAP_dis"){
colSpan++;
rap="Y";
}
if(elementName=="AGE_dis"){
colSpan++;
age="Y";
}
if(elementName=="HIT_dis"){
colSpan++;
hit="Y";
}
}
}
}
var theURL="genericReport.do?method=createGenXL&loopno="+i+"&colSpan="+colSpan+"&qty="+qty+"&cts="+cts+"&avg="+avg+"&rap="+rap+"&age="+age+"&hit="+hit;
window.open(theURL , '_self');
}

function validateAutoOpt(objID){
var chk = document.getElementById(objID).checked;
if(chk){
document.getElementById(objID+'OPT').value='N';
}
else{
 document.getElementById(objID+'OPT').value='Y';   
}
}

function SendMailimages(url){
var filenm='';
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='checkbox') {
   var fieldId = frm_elements[i].id;
   if(fieldId.indexOf('counter_')!=-1){
       if(frm_elements[i].checked){
       if(filenm==''){
       filenm=document.getElementById(fieldId).value;
       }else{
       filenm=filenm+','+document.getElementById(fieldId).value;
       }
       }
   }
  }
}

if(filenm=='') {
alert("Please select Atlest one item to send Mail");
return false;
}else{
url=url+"&file="+filenm;
window.open(url,'_blank');
}
}

function validatemixclrmapp(url){
var dept=document.getElementById('dept').value
var wtfr=document.getElementById('wtfr').value
var wtto=document.getElementById('wtto').value
if(dept==''){
alert('Please Select Dept');
return false;
}
if(wtfr==''){
alert('Please Enter Weight From');
document.getElementById('wtfr').focus();
return false;
}
if(wtto==''){
alert('Please Enter Weight To');
document.getElementById('wtto').focus();
return false;
}
loading();
return true;
}
function analysisperiod(obj,to){
var dtefr=obj.value;
dtefr = dtefr.split("-");
var year = dtefr[2]; var month = dtefr[1]; var day = dtefr[0];
dtefr=new Date(year, month - 1, day)
dtefr.getMonth(); // 2, which is March minus 1
dtefr.setDate( dtefr.getDate() + 30); //Add two days
dtefr.getMonth();
document.getElementById(to).value=dtefr.getDate() + "-" + (dtefr.getMonth() + 1) + "-" + dtefr.getFullYear();
}
function selectgridAna(typ,lprp,loop){
var chk = document.getElementById(typ+'_'+loop).checked;
var setchk=true;
if(chk){
setchk=false;
if(typ!='COMM')
document.getElementById('COMM_'+loop).checked=setchk;
if(typ!='ROW')
document.getElementById('ROW_'+loop).checked=setchk;
if(typ!='COL')
document.getElementById('COL_'+loop).checked=setchk;
}
}

function validateanalysisgrid(){
var countcmn=0;
var countrow=0;
var countcol=0;
var tab = document.getElementById ("analysis"); // get table  id  which contain check box
var elems = tab.getElementsByTagName ("input");
for ( var i = 0; i < elems.length; i++ ){
if (elems[i].type =="checkbox"){
var fieldId = elems[i].id;
if (document.getElementById(fieldId).checked ==true){
if(fieldId.indexOf('COMM_')!=-1){
countcmn++;
}
if(fieldId.indexOf('ROW_')!=-1){
countrow++;
}
if(fieldId.indexOf('COL_')!=-1){
countcol++;
}
}
}
}
if(countcmn==0){
alert('Please Check Atleast one Property In Common');
return false;
}
if(countrow==0){
alert('Please Check Atleast one Property In Row');
return false;
}
if(countcol==0){
alert('Please Check Atleast one Property In Column');
return false;
}
if(countrow>1){
alert('Please Check only one Property In Row');
return false;
}
if(countcol>1){
alert('Please Check only one Property In Column');
return false;
}
return true;
}
function copygrp(nme){
document.getElementById('copyfrom').value=nme;
document.getElementById('copygrp').innerHTML= nme;
}
function callAnalysisGrpby(key,row,col,status){
var grpby=document.getElementById('grpby').value;
if(grpby!=''){
window.open("genericReport.do?method=loadDtl&orderby="+grpby+"&key="+key+"&orderbyrow="+row+"&orderbycol="+col+"&status="+status,'_self');
}
}

function callAnalysisOrderby(key,row,col,status){
var orderby=document.getElementById('orderby').value;
if(orderby!=''){
window.open("genericReport.do?method=loadDtl&orderby="+orderby+"&key="+key+"&orderbyrow="+row+"&orderbycol="+col+"&status="+status+"&onlysrtby=Y",'_self');
}
}

function redirectAnalysissave(){
var gridfmt=document.getElementById('gridfmt').value;
window.open("genericReport.do?method=redirectsave&gridfmt="+gridfmt,'_self');
}

function redirectAnalysissaveByGroup(){
var gridby=document.getElementById('gridby').value;
window.open("genericReport.do?method=redirectsave&gridby="+gridby,'_self');
}
function validateNameSpace(styleId,typ){
var val=document.getElementById(styleId).value
if(val!=''){
if(val.indexOf(' ')!=-1){
if(typ=='P'){
    alert('Not allowed Space in Password');
}
if(typ=='U'){
    alert('Not allowed Space in User Name');
}  
document.getElementById(styleId).value='';
setTimeout(function(){document.getElementById(styleId).focus();},1);
return false;
}
}else{
alert('Please Provide Value')
return false;
}
return true;
}

function echeck(styleId) {
var objId= styleId;
var str = document.getElementById(objId).value;
var at="@"
var dot="."
var lat=str.indexOf(at)
var lstr=str.length
var ldot=str.indexOf(dot)
var emailStr=str;
var array=emailStr.split(".");
var arrLen=array.length;
if(arrLen>4)
{
alert("Invalid E-mail ID .")
document.getElementById(objId).value="";
document.getElementById(objId).focus();
return false
}

if (str.indexOf(at)==-1){
alert("Invalid E-mail ID .")
document.getElementById(objId).value="";
document.getElementById(objId).focus();
return false
}

if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
alert("Invalid E-mail ID .")
document.getElementById(objId).value="";
document.getElementById(objId).focus();
return false
}

if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
alert("Invalid E-mail ID .")
document.getElementById(objId).value="";
document.getElementById(objId).focus();
return false
}

if (str.indexOf(at,(lat+1))!=-1){
alert("Invalid E-mail ID .")
document.getElementById(objId).value="";
document.getElementById(objId).focus();
return false
}

if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
alert("Invalid E-mail ID .")
document.getElementById(objId).value="";
document.getElementById(objId).focus();
return false
}

if (str.indexOf(dot,(lat+2))==-1){
alert("Invalid E-mail ID .")
document.getElementById(objId).value="";
document.getElementById(objId).focus();
return false
}

if (str.indexOf(" ")!=-1){
alert("Invalid E-mail ID .")
document.getElementById(objId).value="";
document.getElementById(objId).focus();

return false
}

return true
}

function callwebActivityScreen()
{
document.getElementById('frame1').contentWindow.location.reload();
document.getElementById('frame2').contentWindow.location.reload();
}

function chksrvTXTAll(name) {
var idval=document.getElementById(name).value;
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

if(field_type=='text') {
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
function chksrvTXTAllForm0(name) {
var idval=document.getElementById(name).value;
var frm_elements = document.forms[0].elements;
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

if(field_type=='text') {
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
function byrsetupvalidate(){
var byrId=document.getElementById('byrId').value;
var party=document.getElementById('party').value;
var rlnId=document.getElementById('rlnId').value;
var xrtId=document.getElementById('xrtId').value;
if(byrId=='0' || byrId==''){
  alert("Please Select Employee");
    return false;
}
if(party=='0' || party==''){
  alert("Please Select Party");
    return false;
}
if(rlnId=='0' || rlnId==''){
  alert("Please Select Terms");
    return false;
}
if(xrtId=='0' || xrtId==''){
  alert("Please Enter Exchange Rate");
    return false;
}
return true;
}
function getCookie(cname)
{
var name = cname + "=";
var ca = document.cookie.split(';');
for(var i=0; i<ca.length; i++) 
  {
  var c = ca[i].trim();
  if (c.indexOf(name)==0) return c.substring(name.length,c.length);
}
return "";
}

function cook(logId)
{
var refresh=getCookie("refresh_"+logId);
if(refresh=='Y'){
var appurl=getCookie("APPURL");
if(appurl!=''){
var app = appurl.split('diaflex');
window.open(app[0]+"diaflex/home.do?method=logout",'_self');
}else{
window.open("../home.do?method=logout",'_self');
}
}
}

function setCookie(cname,cvalue,exdays)
{
var refreshId=document.getElementById('refreshId').value;
var d = new Date();
d.setTime(d.getTime()+(exdays*24*60*60*1000));
var expires = "expires="+d.toGMTString();
document.cookie = cname+"_"+refreshId+ "=" + cvalue + "; " + expires;
}

function checkAccountNmCookie()
{
var accountname=getCookie("ACCOUNTNM");
if(accountname!=''){
document.getElementById('dfaccount').value=accountname;
}else{
document.getElementById('dfaccount').value='';  
}
}

function verifyDailyApproveRpt(){
var dtefr=document.getElementById('dtefr').value;
var dteto=document.getElementById('dteto').value;
var nmeID=document.getElementById('nmeID').value;
var msg="";
        if(nmeID!='' && nmeID!=0 &&  dtefr=='' && dteto==''){
        msg="Date not Selected We will Consider Todays Date"; 
        }
        var frm_elements = document.forms[0].elements; 
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();
            if (field_type == 'select-one') {
                var fieldId = frm_elements[i].id;
                if (fieldId.indexOf('saleEmp') !=  - 1) {
                var saleEmp=document.getElementById('saleEmp').value;
                if(saleEmp!=0 && dtefr=='' && dteto==''){
                msg="Date not Selected We will Consider Todays Date";
                }
                }
                if (fieldId.indexOf('grp') !=  - 1) {
                var grp=document.getElementById('grp').value;
                if(grp!='' && dtefr=='' && dteto==''){
                msg="Date not Selected We will Consider Todays Date";
                }
                }
                if (fieldId.indexOf('typId') !=  - 1) {
                var typId=document.getElementById('typId').value;
                var memofr=document.getElementById('memofr').value;
                var memoto=document.getElementById('memoto').value;
                if(typId!='' && dtefr=='' && dteto==''){
                msg="Date not Selected We will Consider Todays Date";
                }
                if(memoto!='' || memofr!=''){
                msg='';
                }
                }
            }
            }
if(msg==''){           
return true;
}else{
    var r = confirm(msg+" Do You Want to Continue..");
    return r;
}
}

function reloadParentWindow(grpNme,SrchId){

 window.opener.location.href ="pricegpmatrix.do?method=edit&grpNme="+grpNme+"&srchID="+SrchId;
}



function validateNewStockTallyRtn(){
var pid=document.getElementById('pid').value;
if(pid==''){
alert("Please Enter Packet Id / Rfid to take Return");
document.getElementById('pid').focus();
return false;
}else{
loading();
return true;
}
}

function validatepacketRfidAlloc(){
var vnm=document.getElementById('vnm').value;
var prpVal=document.getElementById('prpVal').value;
if(vnm==''){
alert("Please Enter Packet Id");
document.getElementById('vnm').focus();
return false;
}else if(prpVal==''){
alert("Please Enter Rfid Values");
document.getElementById('prpVal').focus();
return false;
}else{
loading();
return true;
}
}

function validatepacketRfidBoxAlloc(){
var boxrfid=document.getElementById('boxrfid').value;
var boxrfidval=document.getElementById('boxrfidval').value;
var prpVal=document.getElementById('prpVal').value;
if(boxrfid=='' || boxrfid=='0'){
alert("Please Enter Box Rfid To Get Box");
document.getElementById('boxrfid').focus();
return false;
}else if(boxrfidval=='boxrfidval'){
alert("Please Click on Get Box Button to get Box Name");
return false;
}if(prpVal=='' || prpVal=='0'){
alert("Please Enter Packet Rfid");
document.getElementById('prpVal').value='';
document.getElementById('prpVal').focus();
return false;
}else{
loading();
return true;
}
}


function validatepacketRfidBoxAllocRtn(){
var boxrfid=document.getElementById('boxrfid').value;
var boxrfidval=document.getElementById('boxrfidval').value;
var pid=document.getElementById('pid').value;
if(boxrfid=='' || boxrfid=='0'){
alert("Please Enter Box Rfid To Get Box");
document.getElementById('boxrfid').focus();
return false;
}else if(boxrfidval=='boxrfidval'){
alert("Please Click on Get Box Button to get Box Name");
return false;
}if(pid=='' || pid=='0'){
alert("Please Enter Packet Rfid");
document.getElementById('pid').value='';
document.getElementById('pid').focus();
return false;
}else{
loading();
return true;
}
}



function validatepacketRfidBoxAllocRtnhk(){
var boxrfid=document.getElementById('boxrfid').value;
var boxrfidval=document.getElementById('boxrfidval').value;
var box=document.getElementById('box').value;
var rtnseq=document.getElementById('rtnseq').value;
var pid=document.getElementById('pid').value;
if((boxrfid=='' || boxrfid=='0') && box==''){
alert("Please Enter Box Rfid To Get Box");
document.getElementById('boxrfid').focus();
return false;
}else if((boxrfidval=='boxrfidval')  && box==''){
alert("Please Click on Get Box Button to get Box Name");
return false;
}else if(box=='' && (boxrfidval=='')){
alert("Please Select Box Rfid");
return false;
}else if(rtnseq==''){
alert("Please Select Return Sequence");
return false;
}else if(pid=='' || pid=='0'){
alert("Please Enter Packet Rfid");
document.getElementById('pid').value='';
document.getElementById('pid').focus();
return false;
}else{
loading();
return true;
}
}
function check_file_contact(fildNm) {
var str=document.getElementById(fildNm).value;
var currentstr=str.substring(0,str.lastIndexOf('.'));
if (currentstr.indexOf(' ') !=  - 1 || currentstr.indexOf(".") !=  - 1){
alert("Invalid File Name Please Verify")
document.getElementById(fildNm).value="";
document.getElementById(fildNm).focus();
return false
}
return true;
}

function isValidFileName(fildNm) {
var str=document.getElementById(fildNm).value;
//str=str.substring(0,str.lastIndexOf('.'));
str=str.substring(str.lastIndexOf( "\\" )+1,str.length);
    var iChars = "~`!#$%^&* +=-[]\\\';,/{}|\":<>? ";
    for (var i = 0; i < str.length; i++) {
       if (iChars.indexOf(str.charAt(i)) != -1) {
       document.getElementById(fildNm).value="";
       document.getElementById(fildNm).focus();
           alert ("File name has special characters ~`!#$%^&* +=-[]\\\';,/{}|\":<>? \nThese are not allowed\n");
           return false;
       }
    }
    return true;
}

function validatedfmsgtime(fildNm) {
var val=document.getElementById(fildNm).value;
if (val.indexOf(':') !=  - 1){
if(val.length==5){
return true;
}
}
alert("Invalid Time Format required HH24:MI Format")
document.getElementById(fildNm).value="";
document.getElementById(fildNm).focus();
return true;
}
  function webactivityshowdiv(){
  var typ=document.getElementById('typ').value;
  if(typ=='APPOINTMENT'){
  document.getElementById('logintyp').style.display = "block";
  }
  else
  {
  document.getElementById('logintyp').style.display = "none";
  }
  }
  
  function validateNmeDtlAttr(obj){
  var val=document.getElementById(obj).value;
        var frm_elements = document.forms[0].elements; 
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();
            if (field_type == 'select-one') {
                var fieldId = frm_elements[i].id;
                var fieldVal=document.getElementById(fieldId).value;
                if(fieldId!=obj){
                if (fieldVal==val && val!='') {
                alert('Contact Attribute Already Added in ur Contact please verify');
                document.getElementById(obj).value="";
                document.getElementById(obj).focus();
                return false;
                }
                }
            }
  }
  return true;
  }
  
  
  function validateLinkToExiting(nmeID,rlnId){
  var valnmeID=document.getElementById(nmeID).value;
  var valrlnId=document.getElementById(rlnId).value;
  if(valnmeID=='0' || valnmeID==''){
      alert('Please Select Contact Name');
      return false;
  }
  if(valrlnId=='0' || valrlnId==''){
      alert('Please Select term');
      return false;
  }
  return true;
  }
  
  function validatemlotpri(){
  var memoid=document.getElementById('memoid').value;
  var dteFrm=document.getElementById('dteFrm').value;
  var dteTo=document.getElementById('dteTo').value;
  if(memoid=='0' || memoid==''){
      alert('Please Enter memo Id');
      document.getElementById('memoid').value="";
      document.getElementById('memoid').focus();
      return false;
  }
  if(dteFrm==''){
      alert('Please Enter p1 Date');
      document.getElementById('dteFrm').value="";
      document.getElementById('dteFrm').focus();
      return false;
  }
  if(dteTo==''){
      alert('Please Enter p2 Date');
      document.getElementById('dteTo').value="";
      document.getElementById('dteTo').focus();
      return false;
  }
  return true;
  }
  
   function validate_saleTypConfirmSL(){
    var isSalTyp = document.getElementById('isSalTyp').value;
     if(isSalTyp=='Y'){
     var frm_elements = document.forms[1].elements; 
        var flg=false;
        for (i = 0;i < frm_elements.length;i++) {
            field_type = frm_elements[i].type.toLowerCase();

            if (field_type == 'radio') {
                var fieldId = frm_elements[i].id;
                if (fieldId.indexOf('SL') !=  - 1) {
                    var isChecked = document.getElementById(fieldId).checked;
                    if (isChecked) {
                        flg = true;
                        break;
                    }
                }
            }}
     var saleTyp = document.getElementById('saleTyp').value;
     if(saleTyp=="" && flg){
       alert("Please Select Sale Type");
       return false;
     }else{
      return confirmChangesSL()
     }
    
    }else{
      return confirmChangesSL();
    }
    
    }
function  validsaleandgroup(){
var cnt=document.getElementById('cnt').value;
     var isChecked = false;
     var grp=document.getElementById('grp').value;
     var emp=document.getElementById('emp').value;
     if(grp!="" && emp!=""){
       isChecked=true;       
     }
     if(cnt =='ag'){
         if(grp!=""){
         isChecked=true; 
         }
     }
     if(!isChecked){
          alert("Please select Group Company & sale person");
     }
 return isChecked;
 }
  function validate_prp(obj,checkwith) {
    var id=obj.id;
    var checkStr = obj.value;
    var ch='';
    var allValid = true;
    for (j = 0;  j < checkStr.length;  j++){
	if (checkwith.indexOf(checkStr.charAt(j))==-1){
        allValid=false;
        ch=checkStr.charAt(j);
        break;
        }
    }
    if (!allValid){
      alert("'"+ch+"'  Not Allowed In This Field Please Verify it");
      setTimeout(function() {obj.focus(); }, 1);
     return false;
   }else{
   obj.value=checkStr.toUpperCase();
   }
   return true;
}

  function validatestkcriteria(){
   var exiCrt=document.getElementById('exiCrt').value;
   if(exiCrt=='' || exiCrt=='0'){
        alert('Please Select Existing Criteria To load or remove');
       return false;
   }
  return true;
  }
    function validatewebservice(){
   var nmeID=document.getElementById('nmeID').value;
   var typ=document.getElementById('typ').value;
   var vnm=document.getElementById('vnm').value;
   if(nmeID=='' || nmeID=='0'){
        alert('Please Select Party');
       return false;
   }
      if(typ=='' || typ=='0'){
        alert('Please Select Type');
       return false;
   }
      if(vnm=='' || vnm=='0'){
        alert('Please Enter packet id');
       return false;
   }
  return true;
  }
  function callheadermsg(){
  var reqUrl = document.getElementById('REQURLMSG').value;
 var req = initRequest();
 var url = reqUrl+"/home.do?method=headermsg";
  req.onreadystatechange = function() {
 if (req.readyState == 4) {
 if (req.status == 200) {
 parseMessagesheadermsg(req.responseXML);
 } else if (req.status == 204){
 }
 }
 };
 req.open("GET", url, true);
 req.send(null);
 }

 function parseMessagesheadermsg(responseXML){
  var message = responseXML.getElementsByTagName("message")[0];
  var msg = message.childNodes[0].nodeValue; 
  if(msg=='NULL'){
      msg='';
  }
  document.getElementById('headermsginterval').innerHTML= msg;
  }
  
  
function validateRecNo(){
var recno =document.getElementById("recNum").value;
if(recno=='' || recno==null){
alert("Field Not be Blank !");
return false ;
}
return true;
}

function validatePurbulkUpd() {
        var vnm = document.getElementById('vnm').value;
        var prp = document.getElementById('prp').value;
         var prpVal = document.getElementById('prpVal').value;
        if(prp=='0'){
            alert("Please specify Propeties");
            return false;
        }
        if(vnm=='' || prpVal==''){
                 if(vnm==''){
                 alert("Please specify Packets OR Memo No. ");
                 }else{
                 alert("Please specify Property Val. ");
                 }
                 return false;
        }            
        return true;
}

function validateLoginForm(){
        var dfaccount = document.getElementById('dfaccount').value;
        var dfusr = document.getElementById('dfusr').value;
        var dfpwd = document.getElementById('dfpwd').value;
        if(dfaccount==''){
            alert('Please Enter Account Name');
            document.getElementById('dfaccount').focus();
            return false;
        }
                if(dfusr==''){
                            alert('Please Enter User Name');
            document.getElementById('dfusr').focus();
            return false;
        }
                if(dfpwd==''){
            alert('Please Enter Password');
            document.getElementById('dfpwd').focus();
            return false;
        }
        return true;
}
function validateHitratio(){
        var row = document.getElementById('row').value;
        var column = document.getElementById('column').value;
        if(row==column){
            alert('Row property Must differ from Column Property');
            return false;
        }
        return true;
}
function validatepriceChgPrimum(){
    var premiumLnk = document.getElementById('premiumLnk').value;
    var old_vlu = document.getElementById('old_vlu').value;
    var new_vlu = document.getElementById('new_vlu').value;
    if(premiumLnk=='Y'){
    if(parseFloat(old_vlu)>parseFloat(new_vlu)){
        alert("Old Value Cant be less than New Value In Premium Please Verify");
        return false;
    }
    }
    return validate_prc('ch_');
}
function validateOTP(){
    var dfOtp = document.getElementById('dfOtp').value;
    if(dfOtp==''){
        alert("Please Enter One Time Password We have Already Forwarded on Mobile/Email");
        return false;
    }
    return true;
}



function validateEmpDep(){
var isValid = false;
var rowcnt = document.getElementById('ROWCNT').value;
for(i=1; i<=rowcnt; i++) {
var nmeIdn = document.getElementById('nme_'+i).value;
var empIdn = document.getElementById('empIdn_'+i).value;
var obj = document.getElementById("multiPrp");
for(var j=0;j<obj.length;j++){
var prp =  obj.options[j].value ;
var prpVal = document.getElementById(prp+'_'+i).value;
if(nmeIdn != '' && empIdn != 0 && prpVal != ''){
isValid = true;
}
}
}
if(isValid){
return true;
} else {
alert('Please Enter Values');
return false;
}
}

function validatepriceChgPrimumRP(){
    var premiumLnk = document.getElementById('premiumLnk').value;
    var old_vlu = document.getElementById('old_vlu').value;
    var new_vlu = document.getElementById('new_vlu').value;
    if(premiumLnk=='Y'){
    if(parseFloat(old_vlu)>parseFloat(new_vlu)){
        alert("Old Value Cant be less than New Value In Premium Please Verify");
        return false;
    }
    }
    var r = confirm("Are You Sure You Want To Save Changes?");
    return r;
}

function loadPricePRO(objId){
if(objId=='prp'){
  document.getElementById('diff').style.display = 'none';
  document.getElementById('prp').style.display = 'block';
}else{
  document.getElementById('diff').style.display = 'block';
  document.getElementById('prp').style.display = 'none';
}
 loadPopupSale();
}
function checkPricePro(){
  var frmpro =  document.getElementById('frmpro').value ;
  var topro =  document.getElementById('topro').value ;
 if(frmpro==topro){
 alert("CMP 1 And CMP 2 Not Be same ");
 return false;
 }
 return true;
}



function CheckAllMemo(chid)
{
    var str ="";
    var memoId = ""
    
    if(chid == 'ALL'){
    var check = document.getElementById(chid).checked;
    var tab = document.getElementById("memoNG" ); 
    var elems = tab.getElementsByTagName("input");
    var len = elems.length;
    for ( var i = 0; i < len; i++ )
    {
    	if ( elems[i].type == "checkbox" )
    	{
        
        if(check){
                elems[i].checked = true;
                memoId = elems[i].value;
                str = str+","+memoId;
        }else{
                elems[i].checked = false;
                memoId = document.getElementById(chid).value;
                str = str.replace(','+memoId,'');
                
        }
    	}
    
    }
    
    } else {
    
    str = document.getElementById('srchRef').value;
    var memocheck = document.getElementById(chid).checked;
    
    if(memocheck){
    document.getElementById(chid).checked = memocheck;
    memoId = document.getElementById(chid).value;
    str = str+","+memoId;
    } else {
    document.getElementById(chid).checked = memocheck;
    memoId = document.getElementById(chid).value;
    str = str.replace(','+memoId,'');
    }
    
    }
    
    str = str.replace('on,','');
    document.getElementById('srchRef').value = str;
    if(str == '')
    document.getElementById('memoId').checked = false ;
    else
    document.getElementById('memoId').checked = true ;
    
    
}
function setCountURL(obj, txtId){
 var hrefval = obj.href;
 var count = document.getElementById(txtId).value;
 var cntindex =  hrefval.indexOf('&COUNT');
 if(cntindex!=-1){
hrefval = hrefval.substring(0,cntindex);
}
 hrefval = hrefval+"&COUNT="+count;
 obj.href=hrefval;
}



function changeTrftyp(obj){
if (obj.indexOf('SELECTED') !=  - 1) {
document.getElementById('trfselected').style.display = 'block';
}else{
document.getElementById('trfselected').style.display = 'none';
}
}

function calldashboardgrpComp(days){
  callcommonpieChart('screen.do?method=grpcomparasionpie&days='+days,'Group Chart','60','362');  
}

function setSelected(dashId,tbl){
var spancell = document.getElementsByTagName("span");
for(k = 0; k < spancell.length; k++){
spanID = spancell[k].id;
if(spanID!='' && spanID.indexOf('dash_') !=  - 1){
if(spanID==dashId){
document.getElementById(dashId).innerHTML = "&nbsp;<b>>></b>";
}else{
document.getElementById(spanID).innerHTML = "";
}
}
}
}
function CreateNewPkt(){
 var count =document.getElementById("noPkt").value;
   var stt =document.getElementById("stt").value;

 if(count=='' || count=='0'){
   alert("Please give no of packets need to create.. ");
 }else{
 loadASSFNLPop('creatBtn');
 window.open("transferPktAction.do?method=LoadPkt&COUNT="+count+"&stt="+stt,'SC');
 }

}

function Repairing_selection(fldTyp,formNm,fldId){
   var rtnVal = true;
   rtnVal = validate_selection(fldTyp,formNm,fldId);
    if(rtnVal){
        rtnVal = confirm("Are you sure you want to save chnages.");
    }
    return rtnVal;
}
function validate_selection(fldTyp,formNm,fldId){
    var frm_elements = document.forms[formNm].elements; 
       var flg=false;
       for (i = 0;i < frm_elements.length;i++) {
           field_type = frm_elements[i].type.toLowerCase();

           if (field_type == fldTyp) {
               var fieldId = frm_elements[i].id;
               if (fieldId.indexOf(fldId) !=  - 1) {
                   var isChecked = document.getElementById(fieldId).checked;
                   if (isChecked) {
                       flg = true;
                       break;
                   }
               }
           }}
           if(!flg){
               alert("please select packets...");
           }
           return flg;
}


function ALLCheckBoxTyp( allCk , name,formNme ){
var isChecked = document.getElementById(allCk).checked;
var frm_elements = document.forms[formNme].elements; 
 
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

function validate_buyerTrans(){
  var buy = document.getElementById('byrId').value;
  if(buy=='0'){
    alert("please select transfer buyer");
  }
  var r = confirmChanges();
  return r;
}


function DisplayDivDtl(style){
document.getElementById('memoDtl').style.display=style;

}

function setvld(val){
document.getElementById('vld').style.display=val;
}
function clearfield(dspFld,dspFldDisable,htmlFld){
    document.getElementById(dspFld).value='';
    document.getElementById(dspFldDisable).value='None';
    document.getElementById(htmlFld).value='';
}

function discomemopri(){
    var dtefr=document.getElementById('dtefr').value;
    var dteto=document.getElementById('dteto').value;
    if(dtefr==''){
        alert("Please Enter P1");
        document.getElementById('dtefr').focus();
        return false;
    }
    if(dteto==''){
        alert("Please Enter P2");
        document.getElementById('dteto').focus();
        return false;
    }
    return true;
}

function heatmapvalidate(){
var mkt='';
var sold='';
var tab = document.getElementById ("statusTbl"); // get table  id  which contain check box
var elems = tab.getElementsByTagName ("input");
for ( var i = 0; i < elems.length; i++ ){
if ( elems[i].type =="checkbox"){
var fieldId = elems[i].id;
if (document.getElementById(fieldId).checked ==true){
if(document.getElementById(fieldId).value =='MKT'){
    mkt='MKT';
}
if(document.getElementById(fieldId).value =='SOLD'){
    sold='SOLD';
}
}
}
}
if(mkt==''){
    alert('Please Check MKT Status Checkbox To get Data');
    return false;
}if(sold==''){
    alert('Please Check SOLD Status Checkbox To get Data');
    return false;
} else if(mkt=='MKT' && sold=='SOLD'){
     return true;
}
}


function resetSort(fld,size,vlu){
for ( var i = 1; i <= size; i++ ){
document.getElementById(fld+i).value=vlu;
}
}


function heatmapvalidate(){
var mkt='';
var sold='';
var tab = document.getElementById ("statusTbl"); // get table  id  which contain check box
var elems = tab.getElementsByTagName ("input");
for ( var i = 0; i < elems.length; i++ ){
if ( elems[i].type =="checkbox"){
var fieldId = elems[i].id;
if (document.getElementById(fieldId).checked ==true){
if(document.getElementById(fieldId).value =='MKT'){
    mkt='MKT';
}
if(document.getElementById(fieldId).value =='SOLD'){
    sold='SOLD';
}
}
}
}
if(mkt==''){
    alert('Please Check MKT Status Checkbox To get Data');
    return false;
}if(sold==''){
    alert('Please Check SOLD Status Checkbox To get Data');
    return false;
} else if(mkt=='MKT' && sold=='SOLD'){
     return true;
}
}

function settarget(formId,target){
    document.getElementById(formId).target='';
    document.getElementById(formId).target=target;
}

function diaplyBoxTr(){
    var cnt = document.getElementById('COUNT').value;
    var newCnt = parseInt(cnt)+1;
    var trId = "TR_"+newCnt;
    document.getElementById(trId).style.display = '';
    document.getElementById('COUNT').value=newCnt;
}

function goToRapnetExcel(theURL,nmeIdn) { 
var memoIdn = document.getElementById("memoId").value;
var memoTyp = document.getElementById("typ").value;
window.open(theURL+"&memoIdn="+memoIdn+"&memoTyp="+memoTyp+"&nmeIdn="+nmeIdn, '_self');
}

function CreateExcelPlaningReturn(theURL,winName,features) { //v2.0
var valid=false;
var nmeidnLst='';
var frm_elements = document.getElementsByTagName('input');
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='checkbox'){
var fieldId = frm_elements[i].id;
var chk=document.getElementById(fieldId).checked;
if (fieldId.indexOf('CHK_') != - 1 && chk) {
valid=true;
var split=fieldId.split('_');
var idn = split[1];
nmeidnLst=nmeidnLst+","+idn;
}
}
}
if(valid){
theURL=theURL+"&idn="+nmeidnLst;
window.open(theURL, '_blank');
}
if(!valid){
alert('Please Select Checkbox');
}
return false;
}
function transferMemotoSametype(){
     var memoTrftype = document.getElementById('memoTrftype').value;
     var memosTyp = document.getElementById('memosTyp').value;
     var memoTyp = document.getElementById('memoTyp').value;
     if(memosTyp==memoTyp || memoTyp=='Z' || (memosTyp=='I' && memoTyp=='E') || (memosTyp=='E' && memoTyp=='I') || (memosTyp=='IAP' && memoTyp=='EAP') || (memosTyp=='EAP' && memoTyp=='IAP')  || (memosTyp=='WAP' && memoTyp=='EAP')  || (memosTyp=='WAP' && memoTyp=='IAP') || (memosTyp=='IAP' && memoTyp=='CS') || (memosTyp=='EAP' && memoTyp=='CS') || (memosTyp=='WAP' && memoTyp=='CS') || (memosTyp=='I' && memoTyp=='CS') || (memosTyp=='E' && memoTyp=='CS') || (memosTyp=='IAP' && memoTyp=='WAP') || (memosTyp=='EAP' && memoTyp=='WAP')){
     if(memoTyp=='E'){
     document.getElementById("expDay").style.display ='';
     }
     else{
         document.getElementById("expDay").style.display ='none';
     }
     }else{
         if(memosTyp=='E'){
         document.getElementById("expDay").style.display ='';
         }
         else{
         document.getElementById("expDay").style.display ='none';
         }
         if(memoTrftype=='Y'){
         alert("Memo Type Will be same To Tranfer(Current Memo and Transfer To memo)");
         document.getElementById('memoTyp').value=memosTyp;
         return false
         }else{
             return true;
         }
         
     }
}

function transferMemotoSametypepacket(){
     var memoTrftype = document.getElementById('memoTrftype').value;
     var memosTyp = document.getElementById('memosTyp').value;
     var memoTyp = document.getElementById('memofTyp').value;
     if(memosTyp==memoTyp || memoTyp=='Z' || (memosTyp=='I' && memoTyp=='E') || (memosTyp=='E' && memoTyp=='I')|| (memosTyp=='IAP' && memoTyp=='EAP') || (memosTyp=='EAP' && memoTyp=='IAP') || (memosTyp=='WAP' && memoTyp=='EAP')  || (memosTyp=='WAP' && memoTyp=='IAP')  || (memosTyp=='IAP' && memoTyp=='CS') || (memosTyp=='EAP' && memoTyp=='CS') || (memosTyp=='WAP' && memoTyp=='CS') ||  (memosTyp=='I' && memoTyp=='CS') || (memosTyp=='E' && memoTyp=='CS') || (memosTyp=='IAP' && memoTyp=='WAP') || (memosTyp=='EAP' && memoTyp=='WAP')){
          if(memoTyp=='E'){
     document.getElementById("expDayf").style.display ='';
     }
     else{
         document.getElementById("expDayf").style.display ='none';
     }
     }else{
         if(memosTyp=='E'){
         document.getElementById("expDay").style.display ='';
         }
         else{
         document.getElementById("expDay").style.display ='none';
         }
         if(memoTrftype=='Y'){
         alert("Memo Type Will be same To Tranfer(Current Memo and Transfer To memo)");
         document.getElementById('memofTyp').value=memosTyp;
         return false;
         }else{
             return true;
         }
     }
}

function moveALL(typ){

  var leftDropdown = document.getElementById("leftListXL");

  var rightDropdown = document.getElementById("rightListXL");

      

 if(typ=='R'){

    leftDropdown = document.getElementById("rightListXL");

  rightDropdown = document.getElementById("leftListXL");

 }

         var leftLength = leftDropdown.length;

    

       

        for(var j = 0;j< leftLength;j++){

            var newOption = new Option();

 

   newOption.text = leftDropdown.options[j].text;

   newOption.value =leftDropdown.options[j].value;

 

   rightDropdown.options[rightDropdown.length] = newOption;

 

  

        }

          for(var i = leftDropdown.options.length-1;i>=0;i--){

        

           leftDropdown.remove(i);

          }

       

    }

  function MoveUP(prpType){

       var Dropdown='';

      if(prpType=='XL')

      Dropdown = document.getElementById("rightListXL");

      else

     Dropdown = document.getElementById("rightListSrch");

     var slctitemidx= Dropdown.options.selectedIndex;

     if(slctitemidx==-1){

    alert('Please select an Item to move');

     return false;

     }

     if(slctitemidx==0){

     alert('You canont move this item');

     return false;

     }

     var upitemidx = slctitemidx -1;

     var slctitemtext =  Dropdown.options[slctitemidx].text;

     var slctitemvale =  Dropdown.options[slctitemidx].value;

     var upitemtext = Dropdown.options[upitemidx].text;

     var upitemvale = Dropdown.options[upitemidx].value;

    

    

     Dropdown.options[slctitemidx].text = upitemtext;

     Dropdown.options[slctitemidx].value = upitemvale

     Dropdown.options[upitemidx].text = slctitemtext;

     Dropdown.options[upitemidx].value=slctitemvale ;

    

     Dropdown.options[upitemidx].selected = true;

     Dropdown.options[slctitemidx].selected = false;

  }

 function MoveDown(prpType){

    var Dropdown='';

      if(prpType=='XL')

      Dropdown = document.getElementById("rightListXL");

      else

     Dropdown = document.getElementById("rightListSrch");

     var length = Dropdown.length;

  

     var slctitemidx= Dropdown.options.selectedIndex;

     if(slctitemidx==-1){

    alert('Please select an Item to move');

     return false;

     }

     if(slctitemidx==length){

     alert('You canont move this item');

     return false;

     }

   

     var downitemidx = slctitemidx + 1;

     var slctitemtext =  Dropdown.options[slctitemidx].text;

     var slctitemvale =  Dropdown.options[slctitemidx].value;

     var downitemtext = Dropdown.options[downitemidx].text;

     var downitemvale = Dropdown.options[downitemidx].value;

    

    

     Dropdown.options[slctitemidx].text = downitemtext;

     Dropdown.options[slctitemidx].value = downitemvale

     Dropdown.options[downitemidx].text = slctitemtext;

     Dropdown.options[downitemidx].value = slctitemvale ;

    

     Dropdown.options[downitemidx].selected = true;

     Dropdown.options[slctitemidx].selected = false;

  }

 function fnMoveItemslr(prpTye){

    var leftDropdown = document.getElementById("leftList"+prpTye);

  

    var rightDropdown = document.getElementById("rightList"+prpTye);

 

   if(leftDropdown.options.selectedIndex == -1)

   {

   alert('Please select an Item to move');

   return false;

    }

    while ( leftDropdown.options.selectedIndex >= 0 )

  {

 

   var newOption = new Option();

 

   newOption.text = leftDropdown.options[leftDropdown.options.selectedIndex].text;

   newOption.value =leftDropdown.options[leftDropdown.options.selectedIndex].value;

 

   rightDropdown.options[rightDropdown.length] = newOption;

 

   leftDropdown.remove(leftDropdown.options.selectedIndex); 

 

  }

 

    return true;

}

 

function fnMoveItemsrl(prpType){

 

    var leftDropdown = null;

    var rightDropdown = null;

   if(prpType=="xlPrp"){

  

   

     leftDropdown = document.getElementById('leftListXL');

  

     rightDropdown = document.getElementById('rightListXL');

   }else{

      

     leftDropdown = document.getElementById('leftListSrch');

  

     rightDropdown = document.getElementById('rightListSrch');

   }

   if(prpType=="xlPrp"){

   

   if(rightDropdown.length < 5)

  {

   alert('You can not movies this properties');

   return false;

  }

   }

   if(rightDropdown.options.selectedIndex == -1)

   {

   alert('Please select an Item to move');

   return false;

    }

    while ( rightDropdown.options.selectedIndex >= 0 )

  {

 

   var newOption = new Option();

  

   newOption.text = rightDropdown.options[rightDropdown.options.selectedIndex].text;

   newOption.value =rightDropdown.options[rightDropdown.options.selectedIndex].value;

   var index = leftDropdown.length;

  

   leftDropdown.options[index] = newOption;

 

   rightDropdown.remove(rightDropdown.options.selectedIndex); 

 

  }

 

    return true;

}

 

function collectValue(){

    var  rightDropdownXL = document.getElementById('rightListXL');

    

    if((rightDropdownXL.length==0)){

        alert("Please select properties");

        return false;

    }

    for(var i=0;i < rightDropdownXL.length ;i++){

         rightDropdownXL.options[i].selected=true;

    }

 

    return true;

}

function redirectToParentUrl(url){

            window.opener.location.href=url;

            self.close();

 }
 
function validatePricerangeSearch(){
var PRI_CHNG_VAL=document.getElementById('PRI_CHNG_VAL').value;
if(PRI_CHNG_VAL>=-5 && PRI_CHNG_VAL<=15){
}else{
    alert('Valid Range Is -5 To 15');
    document.getElementById('PRI_CHNG_VAL').value='';
    document.getElementById('PRI_CHNG_VAL').focus();
}
}

function openRedirectPage(){
var memoIdn = document.getElementById("memoId").value;
var memoTyp = document.getElementById("typ").value;
var repPath = document.getElementById("reqUrlPage").value;
var theURL = repPath;
if (memoTyp.indexOf('AP') != - 1){
theURL+="/marketing/memoSale.do?method=load&memoId="+memoIdn+"&pnd=Y&view=Y";
}else if (memoTyp.indexOf('CS') != - 1){
theURL+="/marketing/consignmentSale.do?method=load&memoId="+memoIdn+"&pnd=Y&view=Y";
}else{
theURL+="/marketing/memoReturn.do?method=load&memoId="+memoIdn+"&pnd=Y&view=Y";
}
window.open(theURL, '_self');
}

function submitform(formNme){
     document.forms[formNme].submit();
 }
 
 function PaymentMode(obj,isRed,cnt,md){
     var newAmt =parseFloat(jsnvl(document.getElementById(md+"TXT_"+cnt).value));
     var oldAmt =parseFloat(jsnvl(document.getElementById(md+"AMT_"+cnt).value));
     var newCur =parseFloat(jsnvl(document.getElementById(md+"CURTXT_"+cnt).value));
     var oldCur =parseFloat(jsnvl(document.getElementById(md+"CUR_"+cnt).value));
     var cur = document.getElementById('CUR').value;
     var rd = obj.value;
     var chkId = md+"SHRT_"+cnt;
      var txtshrtId = md+"SHRTTXT_"+cnt;
     
     if(rd=='full'){
         document.getElementById(chkId).disabled=false;
          var diff=(new Number(oldAmt-newAmt)).toFixed(2);
         if(cur=='FOR'){
           var diffCur=(new Number(oldCur-newCur)).toFixed(2);
           var xrt = parseFloat(jsnvl(document.getElementById(md+"XRTTXT_"+cnt).value));
           diff = (new Number(diffCur*xrt)).toFixed(2);
           document.getElementById(md+"TXT_"+cnt).value = (new Number(newAmt-diff)).toFixed(2);
         }
      
          if(diff>1){
          document.getElementById(chkId).checked = true;
          document.getElementById(txtshrtId).disabled=false;
          document.getElementById(txtshrtId).value=diff;
            }
         
      }else{
         document.getElementById(chkId).disabled=true;
          document.getElementById(txtshrtId).disabled=true;
          document.getElementById(chkId).checked = false;
         
         document.getElementById(txtshrtId).value="";
      }
 }
 function unabletxt(obj,fldid){
     var ischecked = obj.checked;
     if(ischecked){
      document.getElementById(fldid).disabled=false;
     }else{
         document.getElementById(fldid).disabled=true;
     }
 }
 function chnagesFullAmt(obj,typ,cnt){
     var amt= jsnvl(document.getElementById(typ+"_AMTTTL").innerHTML);
     if(amt!="0"){
     var shrtAmt=parseFloat(obj.value);
     var calAmt = parseFloat(amt)-shrtAmt;
     document.getElementById(typ+"TXT_"+cnt).value=get2DigitNum(calAmt);
     }
 }
 function setAmountToTxt(fldId,amt){
    amt = amt.trim();
      document.getElementById(fldId).value=amt;
 }
 function setValToHID(obj,idn,typ){
     var val = obj.value;
      document.getElementById(idn+"By").value=val;
      diaplayThirdParty(typ,'none');
 }
 
 function diaplayThirdParty(typ,dis){
     var cnt = document.getElementById(typ+'CNT').value;
     for(var i=1;i<=cnt;i++){
     var divId = typ+"DIV_"+i;
      document.getElementById(divId).style.display = dis;
       var txtId = typ+"TXT_"+cnt;
        var AMTID = typ+'PRTAMT_'+cnt;
     var txtVal=document.getElementById(txtId).value;
      if(txtVal=='0')
      txtVal = parseFloat(txtVal);
      var lmtAmt =document.getElementById(AMTID).innerHTML;
       if(lmtAmt=='0')
      lmtAmt = parseFloat(lmtAmt);
       if(txtVal>lmtAmt){
         
          document.getElementById(txtId).value="";
     }}
     document.getElementById(typ+"DIV").style.display =dis;
 }
 
 function setPartyAmount(obj,lbId,cnt,typ){
     var val =obj.value;
     var vallst = val.split("_")
     var amt =  parseFloat(vallst[1]);
     document.getElementById(lbId+"_"+cnt).innerHTML=amt;
     var txtId = typ+"TXT_"+cnt;
     var txtVal=document.getElementById(txtId).value;
      if(txtVal=='0')
      txtVal = parseFloat(txtVal);
      if(txtVal>amt){
          document.getElementById(txtId).value="";
      }
 }
 
 function checkPartyAmtLmit(obj,typ,amt){
 if(typ=='PAY'){
     var currVal = parseFloat(obj.value);
     var balamt = parseFloat(amt);
     if(currVal > balamt){
        alert("Transcation not allow! Available Balance :-"+balamt); 
        obj.value="";
     }
 }
}

function VerifiedTransAmt(){

    var frm_elements = document.forms['1'].elements; 
    var cur = document.getElementById("CUR").value;
    var ischeckedR  = false;
     var rtnVal = true;
    for(i=0; i<frm_elements.length; i++) {
     var field_type = frm_elements[i].type.toLowerCase();
     if(field_type=='radio') {
     var fieldId = frm_elements[i].id;
     if(fieldId.indexOf("CB_REC_")!=-1){
     
     if(frm_elements[i].checked){
     var reccnt=frm_elements[i].value;
     var rectxtAmt=jsnvl(document.getElementById("RECTXT_"+reccnt).value);
    ischeckedR  = true;
     if(rectxtAmt=='0'){
          alert("Please specify Amount");
          rtnVal = false;
      }
      
      var ischecked=document.getElementById("RECFULL_"+reccnt).checked;
      if(ischecked){
     var curttl =parseFloat(jsnvl(document.getElementById("RECCUR_"+reccnt).value));
     var amtttl =parseFloat(jsnvl(document.getElementById("RECAMT_"+reccnt).value));
     var newcurttl =parseFloat(jsnvl(document.getElementById("RECCURTXT_"+reccnt).value));
     var newamtttl =parseFloat(jsnvl(document.getElementById("RECTXT_"+reccnt).value));
     var curdiff = Math.abs(newcurttl-curttl);
     var amtdiff = Math.abs(newamtttl-amtttl);
     if(cur=='LOC' && amtdiff > 1){
        alert("Please Verify Amount($) : Actual Amt($):"+amtttl+" && Apply Amt($):"+newamtttl);
        rtnVal = false;
      }else if(cur=='FOR' && curdiff > 2){
        alert("Please Verify Amount($) : Actual Amt($):"+curttl+" && Apply Amt($):"+newcurttl);
        rtnVal = false;
      }
      }
     }
      }
     
      if(fieldId.indexOf("CB_PAY_")!=-1){
      if(frm_elements[i].checked){
       ischeckedR  = true;
      var paycnt = frm_elements[i].value;
       var paytxtAmt=jsnvl(document.getElementById("PAYTXT_"+paycnt).value);
      if(paytxtAmt=='0'){
          alert("Please specify Amount");
          rtnVal = false;
      }
      
      var ischeckedp=document.getElementById("PAYFULL_"+reccnt).checked;
      if(ischeckedp){
     var curttlp =parseFloat(jsnvl(document.getElementById("PAYCUR_"+reccnt).value));
     var amtttlp =parseFloat(jsnvl(document.getElementById("PAYAMT_"+reccnt).value));
     var newcurttlp =parseFloat(jsnvl(document.getElementById("PAYCURTXT_"+reccnt).value));
     var newamtttlp =parseFloat(jsnvl(document.getElementById("PAYTXT_"+reccnt).value));
     var curdiffp = Math.abs(newcurttl-curttl);
     var amtdiffp = Math.abs(newamtttl-amtttl);
     if(cur=='LOC' && amtdiffp > 1){
        alert("Please Verify Amount($) : Actual Amt($):"+amtttlp+" && Apply Amt($):"+newamtttlp);
        rtnVal = false;
      }else if(cur=='FOR' && curdiffp > 2){
        alert("Please Verify Amount(INR) : Actual Amt(INR):"+curttlp+" && Apply Amt(INR):"+newcurttlp);
        rtnVal = false;
      }
      }
      }
      }
     }
      
    }
    
   if(!ischeckedR){
         alert("Please select Transaction!!!");
         rtnVal = false;
   }
    return rtnVal;
}

function ApplyAmount(typ){
  var cur = document.getElementById("CUR").value;
    var frm_elements = document.forms['1'].elements; 
     var ttlamt=parseFloat(jsnvl(document.getElementById(typ+"_AMTTTL").innerHTML));
      var ttlCur=parseFloat(jsnvl(document.getElementById(typ+"_CURTTL").innerHTML));
       var AvgXrt=parseFloat(jsnvl(document.getElementById(typ+"_XRTAVG").innerHTML));
     var txtAmtId="";
     var txtCurId="";
      var txtXrtId="";
      var hidCurId="";
       var hidAmtId="";
       var chkId = "";
      var txtshrtId = "";
     var fullRd="";
     var partRd="";
    for(i=0; i<frm_elements.length; i++) {
     field_type = frm_elements[i].type.toLowerCase();
     if(field_type=='radio') {
     var fieldId = frm_elements[i].id;
    
     if(fieldId.indexOf("CB_"+typ+"_")!=-1){
     if(frm_elements[i].checked){
    var cnt =  frm_elements[i].value;
     txtAmtId=typ+'TXT_'+cnt;
     txtCurId=typ+'CURTXT_'+cnt;
     txtXrtId=typ+'XRTTXT_'+cnt;
     hidCurId=typ+'CUR_'+cnt;
     hidAmtId=typ+'AMT_'+cnt;
     chkId = typ+"SHRT_"+cnt;
     txtshrtId = typ+"SHRTTXT_"+cnt;
     fullRd=typ+"FULL_"+cnt;
     partRd=typ+"PART_"+cnt;
      }
      }}
    }
 if(txtAmtId==""){
  alert("Please select Transaction!!!");
  }else{
  var curttl =parseFloat(jsnvl(document.getElementById(hidCurId).value));
  var amtttl =parseFloat(jsnvl(document.getElementById(hidAmtId).value));
 if(cur=='FOR' && (ttlCur-curttl)>1){
        alert("Please Verify Amount($) : Actual Amt($):"+curttl+" and Apply Amt($):"+ttlCur);
  }else if(cur=='LOC' && (ttlamt-amtttl)>1){
        alert("Please Verify Amount(INR) : Actual Amt(INR):"+amtttl+" and Apply Amt(INR):"+ttlamt);
 }else{
 document.getElementById(fullRd).checked=false;
 document.getElementById(partRd).checked=true;
 document.getElementById(chkId).disabled=true;
 document.getElementById(txtshrtId).disabled=true;
 document.getElementById(chkId).checked = false;
 document.getElementById(txtshrtId).value="";
 document.getElementById(txtAmtId).value=ttlamt;
 document.getElementById(txtCurId).value=ttlCur;
 document.getElementById(txtXrtId).value=AvgXrt;
 
  }
  }
}

function UnableXrt(obj){
    var val = obj.value;
    if(val=='FOR'){
         document.getElementById('xrt').disabled=false;
         document.getElementById('xrt').focus();
    }
}


function jsnvl(val){
    if(val=='')
    val="0";
    return val;
}

function validateSaleDisRpt(){
    var frmdte = document.getElementById('frmdte').value;
    var todte = document.getElementById('todte').value;
    if(frmdte==''){
        alert("Please Enter Date From");
        document.getElementById('frmdte').focus();
        return false;
    }
    if(todte==''){
        alert("Please Enter Date To");
        document.getElementById('todte').focus();
        return false;
    }
    return true;
}

function genericReportMixDays(fldid){
    var daysfilter=document.getElementById(fldid).value;
    if(daysfilter=='DATE'){
    document.getElementById("datefilter").style.display='';
    }else{
        document.getElementById('empLst').value="";
        document.getElementById('partytext').value="";
        document.getElementById('party').value="";
        document.getElementById('frmdte').value="";
        document.getElementById('todte').value="";
        document.getElementById("datefilter").style.display='none';
        
    }
}

function openfeedbackfromsrch(fld){
    var nme_idn=document.getElementById(fld).value;
    if(nme_idn=='' || nme_idn=='0'){
        alert('Select Buyer');
    }else{
    var theURL = document.getElementById('reqUrl').value;
    theURL+="/contact/advcontact.do?method=fromsearchrslt&srch=N&nme_idn="+nme_idn;
    window.open(theURL, 'fix1');
    }
}


function filteropenclosescreen(url,filterlprpval){
var gridbylprp=document.getElementById('gridbylprp').value;
var filterlprp=document.getElementById('filterlprp').value;
           filterlprpval=filterlprpval.replace('&', '%26'); 
           filterlprpval = filterlprpval.replace('+', '%2B'); 
           filterlprpval = filterlprpval.replace('-', '%2D');
url+="/report/genericReport.do?method=loadstkopencloseData&gridbylprp="+gridbylprp+"&filterlprpval="+filterlprpval+"&filterlprp="+filterlprp;
window.open(url,'_self' );
}

function filteropenclosescreenifrs(url,filterlprpval){
var gridbylprp=document.getElementById('gridbylprp').value;
var filterlprp=document.getElementById('filterlprp').value;
           filterlprpval=filterlprpval.replace('&', '%26'); 
           filterlprpval = filterlprpval.replace('+', '%2B'); 
           filterlprpval = filterlprpval.replace('-', '%2D');
url+="/report/ifrsaction.do?method=loadstkopencloseData&gridbylprp="+gridbylprp+"&filterlprpval="+filterlprpval+"&filterlprp="+filterlprp;
window.open(url,'_self' );
}

function tabtonext(obj,nextTxt){
    alert($(obj).next(".inputs").id);
}

function calttlCts(obj){
    var ttlCts = parseFloat(jsnvl(document.getElementById('TTLCTSMT').innerHTML));
    var varified = parseFloat(jsnvl(document.getElementById('ttlcts').innerHTML));
    var newCts=0;
    for(var i=1 ; i<=10;i++){
        var cts = parseFloat(jsnvl(document.getElementById('cts_'+i).value));
        newCts =newCts+cts;
    }
    var calcts = varified+newCts;
  
    if(ttlCts < calcts){
        alert("carat is  more than purchase carat lot.");
        obj.value='';
    }else{
       document.getElementById('addcts').innerHTML =newCts;
    }
}

function calttlCRTWT(obj){
    var ttlCts = parseFloat(jsnvl(document.getElementById('TTLCTSMT').innerHTML)).toFixed(2);
    var cnt = parseInt(document.getElementById('COUNT').value);
    var newCts=0;
    for(var i=0 ; i<cnt;i++){
        var cts = parseFloat(jsnvl(document.getElementById('CRTWT_'+i).value));
        newCts =newCts+cts;
    }

    newCts=parseFloat(newCts).toFixed(2);
    var balCts = (new Number(ttlCts-newCts)).toFixed(2);
    if(balCts < 0){
        alert("carat is  more than Available carat lot.");
        obj.value='';
    }else{
       document.getElementById('addcts').innerHTML =(new Number(newCts)).toFixed(2);
       document.getElementById('balcts').innerHTML =(new Number(balCts)).toFixed(2);
    }
}

function calttlRGHCRTWT(obj){
    var ttlCts = parseFloat(jsnvl(document.getElementById('TTLCTSMT').innerHTML)).toFixed(2);
    var cnt = parseInt(document.getElementById('COUNT').value);
    var newCts=0;
    for(var i=0 ; i<cnt;i++){
        var cts = parseFloat(jsnvl(document.getElementById('CB@CRTWT@'+i).value));
        newCts =newCts+cts;
    }


    newCts=parseFloat(newCts.toFixed(2));

    var balCts = (new Number(ttlCts-newCts)).toFixed(2);
    if(ttlCts < newCts){
        alert("carat is  more than purchase carat lot.");
        obj.value='';
    }else{
       document.getElementById('addcts').innerHTML =(new Number(newCts)).toFixed(2);
       document.getElementById('balcts').innerHTML =(new Number(balCts)).toFixed(2);
    }
}
function CalValue(){
     var cts = parseFloat(jsnvl(document.getElementById('ttl_cts').value));
      var rte = parseFloat(jsnvl(document.getElementById('avg_rte').value));
      var vlu =  cts*rte;
    
      vlu = (new Number(vlu)).toFixed(2);
      document.getElementById('ttl_vlu').value=vlu;
}

function CaltotalVlu(id){
    var cts = parseFloat(jsnvl(document.getElementById('cts_'+id).value));
      var rte = parseFloat(jsnvl(document.getElementById('rte_'+id).value));
      var vlu =  cts*rte;
      vlu = (new Number(vlu)).toFixed(2);
      document.getElementById('vlu_'+id).value=vlu;
}

function confirmManufacturing(){
   var isRtn =  true;
    var cnt = parseInt(document.getElementById('count').value);
   
    var isLot=false;
    for(var i=1;i<=cnt;i++){
        var lotNo = document.getElementById('LOTNO_'+i).value;
        if(lotNo!=''){
        isLot=true;
            var cts = document.getElementById('CRTWT_'+i).value;
            var rte = document.getElementById('RTE_'+i).value;
             var loc = document.getElementById('LOC_'+i).value;
            if(cts==''){
             alert("please Specify Carat for Lot "+lotNo);
             isRtn =  false;
                break;
            }
            
            if(rte==''){
             alert("please Specify Rate for Lot "+lotNo);
             isRtn =  false;
                break;
            }
            if(loc=='' || loc=='NA'){
             alert("please Specify Location for Lot "+lotNo);
             isRtn =  false;
                break;
            }
        }
        
        
    }
  if(!isLot){
      alert("please Specify Data");
      isRtn =  false;
  }
    return isRtn;
}

function calttlQTY(obj){
   var ttlQty = parseInt(jsnvl(document.getElementById('TTLQTYSMT').innerHTML));
   var cnt = parseInt(document.getElementById('COUNT').value);
   var newQty=0;
   for(var i=0 ; i<cnt;i++){
       var qty = parseInt(jsnvl(document.getElementById('QTY_'+i).value));
       newQty =newQty+qty;
   }
 
   var balQty = ttlQty-newQty;
   if(ttlQty < newQty){
       alert("Qty is  more than purchase Qty lot.");
       obj.value='';
   }else{
      document.getElementById('addqty').innerHTML =newQty;
      document.getElementById('balqty').innerHTML =balQty;
   }
}

function calttlVNQTY(obj){
   var ttlQty = parseInt(jsnvl(document.getElementById('TTLQTYSMT').innerHTML));
   var cnt = parseInt(document.getElementById('COUNT').value);
   var newQty=0;
   for(var i=0 ; i<cnt;i++){
       var qty = parseInt(jsnvl(document.getElementById('QTY_'+i).value));
       newQty =newQty+qty;
   }
 
   var balQty = ttlQty-newQty;
   
      document.getElementById('addqty').innerHTML =newQty;
      document.getElementById('balqty').innerHTML =balQty;
   
}
function AlllotTotalCts(obj){
   var isChecked = obj.checked;
   if(isChecked){
    var selectCts =  parseFloat(jsnvl(document.getElementById('ttlCts').innerHTML));
     var selectQty =  parseFloat(jsnvl(document.getElementById('ttlOty').innerHTML));
   document.getElementById('selectCts').innerHTML=selectCts;
    document.getElementById('selectQty').innerHTML=selectQty;
   }else{
        document.getElementById('selectCts').innerHTML=0.00;
        document.getElementById('selectQty').innerHTML=0;
   }

}
function lotTotalCts(fldId,obj){
    var cts = parseFloat(jsnvl(document.getElementById('cts_'+fldId).value));
     var qty = parseFloat(jsnvl(document.getElementById('qty_'+fldId).value));
    var selectCts =  parseFloat(jsnvl(document.getElementById('selectCts').innerHTML));
    var selectQty =  parseFloat(jsnvl(document.getElementById('selectQty').innerHTML));
    var isChecked = obj.checked;
    var ttlCts = "";
    var ttlQty="";
    if(isChecked){
        ttlCts=selectCts+cts;
        ttlQty=selectQty+qty;
    }else{
         ttlCts=selectCts-cts;
          ttlQty=selectQty-qty;
    }
     ttlCts = (new Number(ttlCts)).toFixed(2);
    document.getElementById('selectCts').innerHTML=ttlCts;
     document.getElementById('selectQty').innerHTML=ttlQty;
}

function lotInwardTotalCts(){
  var cnt  = document.getElementById('pkt_cnt').value;
   var ttlCts = 0.00;
   var ttlQty=0;
  for(var i=1;i<=cnt;i++){
     var cts = parseFloat(jsnvl(document.getElementById('CRTWT_'+i).value));
   
     var qty = parseInt(jsnvl(document.getElementById('qty_'+i).value));
     ttlCts=ttlCts+cts;
     ttlQty=ttlQty+qty;
   }
   document.getElementById('selectCts').innerHTML=(new Number(ttlCts)).toFixed(2);
   document.getElementById('selectQty').innerHTML=ttlQty;
}

function validateVerification(){
var isValid=true;

  var frm_elements = document.forms['0'].elements; 

 for(i=0; i<frm_elements.length; i++) {
     field_type = frm_elements[i].type.toLowerCase();
 
     if(field_type=='select-one') {
     var fieldId = frm_elements[i].id;
  
    if(fieldId.indexOf("CB@")!=-1){
     var val = document.getElementById(fieldId).value;
  
     if(val=='NA'){
        var elementLst = fieldId.split("@");
        var lprp = elementLst[1];
        var cnt = elementLst[2];
    
        var vnm = "VNM_"+elementLst[2];
        var cts = document.getElementById('CB@CRTWT@'+cnt).value;
        if(cts!=''){
         isValid=false;
           var vnmVal = document.getElementById(vnm).value;
         alert(lprp+" can not be NA for packet code :-"+vnmVal);
         break;
        }
     }
     }
     }
     }
     
   
     if(isValid){
       isValid = ValidateRte();
     }
    
    return isValid;
}

function ValidateRte(){
var isValid = true;
 var cnt = parseInt(jsnvl(document.getElementById('COUNT').value));
  for(var i=0;i<cnt;i++){
     var cts = document.getElementById('CB@CRTWT@'+i).value;
     if(cts!=''){
          var rte = document.getElementById('CB@RTE@'+i).value;
          if(rte==''){
            var vnm = document.getElementById('VNM_'+i).value;
              alert("Please Specify Rate for Packet Code"+vnm);
              isValid = false;
              break;
              
          }
          
     }
  }
  
  if(isValid){
      loading();
  }
  return isValid;
 }
 
 function valifiedRoughCarat(){
    var isValid=true;
     var lpCts = parseFloat(nvl(document.getElementById('lpCts').value,'0'));
     var mstkIdn = document.getElementById('mstkIdn').value;
     var rghCts = parseFloat(nvl(document.getElementById('rghcts').value));
      var rejectcts = parseFloat(nvl(document.getElementById('rejectcts').value));
      var polQty = parseInt(nvl(document.getElementById('polqty').value,'0'));
      var totalStone = parseInt(nvl(document.getElementById('totalStone').value,'0'));
     var ttlCts = rghCts+lpCts+rejectcts;
     ttlCts =get2DigitNum(ttlCts);
     var pCts = parseFloat(nvl(document.getElementById('cts').value));
     
     var min=pCts-0.5;
     var max = pCts+0.5;
  
     if(ttlCts <= min || ttlCts >= max){
     isValid=false;
     alert("Please Verify total return carat :"+ttlCts+"");
     
     } else if(polQty!=totalStone){
     if(totalStone==0){
           isValid=false;
           alert("Please specify total stone.");
     }else{
     isValid=false;
     alert("Please Verify total stone return it can't more then :"+totalStone+"");
     }
     
     }else{
     alert("verification done successfully");
      document.getElementById('Return').style.display='';
       document.getElementById('Verify').style.display='none';
     }
     return isValid
 }
 
 function valifiedStoneCnt(){
    var polQty = parseInt(nvl(document.getElementById('polqty').value,'0'));
    var totalStone = parseInt(nvl(document.getElementById('totalStone').value));
   if(polQty!=totalStone){
     alert("Please Verify total stone return it can't more then :"+polQty+"");
    document.getElementById('totalStone').value="";
   }

 }
 
 function validteSaleIdn(){
        var status = false;
     var saleId = document.getElementById('saleIdn').value;
     if(saleId==''){
          $("#memoIdn").find("input:checked").each(function() {
                      if($(this).prop("checked") == true){
                                    status=true;
            }
        })
        if(!status){
        alert("Please specified delivery Id");
        }
     }else{
     status  = true;
     }
     return status;
     
 }
 
  function validteDeliveryIdn(){
  var status = false;
     var dlvIdn = document.getElementById('dlvIdn').value;
     if(dlvIdn==''){
          $("#memoIdn").find("input:checked").each(function() {
                      if($(this).prop("checked") == true){
                                    status=true;
            }
        })
        if(!status){
        alert("Please specified delivery Id");
        }
     }else{
     status  = true;
     }
     return status;
     
 }
 
 function NumericCarat(obj){
       var num = "0123456789.";
	  var checkStr = obj.value;
          var allnum = true;
	
       
         for (i = 0;  i < checkStr.length;  i++)
	  {
    	ch = checkStr.charAt(i);
		    for (j = 0;  j < num.length;  j++)
		      if (ch == num.charAt(j))
        		break;
		    if (j == num.length)
		    {
	    	    allnum = false;

    		  break;
                    }    
                    
                    }
       
      
  	
  if (allnum)
  {  
   
    alert("Data must be numeric");
    obj.focus();
      obj.value="";
    return false;
  }
  
  
  return true;
}

function ValidMixToMix(){
var lprpvalid = document.getElementById('lprpValid').value;
var isVaild=true;
if(lprpvalid!=''){
var validLprp = lprpvalid.split(",");
for(var i=0; i<validLprp.length; i++) 
  {
  var lprp = validLprp[i];
  var lprpFldF1 = document.getElementById(lprp+"_F1");
  var lprpFldT1 = document.getElementById(lprp+"_T1");
  if(lprpFldF1!=null){
  var lprpValF1 = document.getElementById(lprp+"_F1").value;
   if(lprpValF1=='' || lprpValF1=="0"){
  alert("Please specify "+lprp);
  isVaild=false;
   break;
  }}
  if(lprpFldT1!=null){
    var lprpValT1 = document.getElementById(lprp+"_T1").value;
  if(lprpValT1=='' || lprpValT1=="0"){
  alert("Please specify "+lprp);
  isVaild=false;
    break;
  }}
}}

return isVaild;
}

function getVnmCsv(vnm){
vnm=replaceAll(vnm,' ',',');
vnm=replaceAll(vnm,'\n',',');
vnm=replaceAll(vnm,',,',',');
if(vnm.lastIndexOf(",")==vnm.length-1){
vnm=vnm.substring(0,vnm.lastIndexOf(",")-1);
if(vnm.lastIndexOf(",")==vnm.length){
vnm=vnm.substring(0,vnm.lastIndexOf(",")-1);
}
}
return vnm
}

function getVnmCount(srchRef,srchRefCnt){
   var vnm = getVnmCsv(document.getElementById(srchRef).value);
   if(vnm!=''){
   var array = vnm.split(",");
   var arrLen=array.length;
   document.getElementById(srchRefCnt).innerHTML="<b>"+arrLen+"</b>"
   }else{
       document.getElementById(srchRefCnt).innerHTML="<b>0</b>"
   }
}

function enableFindNotSeenOption(){
if(document.getElementById('callnotseenDrop').value=='EXCLUDE'){
document.getElementById('srchRefFindNotSeenExclude').style.display='block';
document.getElementById('srchRefValFindNotSeen').value='';
}else{
document.getElementById('srchRefFindNotSeenExclude').style.display='none';
document.getElementById('srchRefValFindNotSeen').value='';
}
}


function setEXVGPrcCalc(exTyp,exTypval){
var cut = document.getElementById('cut').value;
var sym = document.getElementById('sym').value;
var pol = document.getElementById('pol').value;
var isChecked = document.getElementById(exTyp).checked;
    if(exTyp=='is3Ex'){
      document.getElementById('is3VG').checked= false;
    }
     if(exTyp=='is3VG'){
      document.getElementById('is3Ex').checked= false;
    }
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio') {
var fieldId = frm_elements[i].id;
if (fieldId.indexOf(cut+'_') != - 1 || fieldId.indexOf(pol+'_') != - 1 || fieldId.indexOf(sym+'_') != - 1) {
var val= document.getElementById(fieldId).value;
if(val==exTypval){
document.getElementById(fieldId).checked = exTypval;
}
}
}
if(field_type=='select-one') {
var fieldId = frm_elements[i].id;
if (fieldId==cut || fieldId==pol || fieldId==sym) {
document.getElementById(fieldId).value = exTypval;
}
}
}
}


function setAvgRte(no){
    var intCnt = parseInt(no);
    var avgRte=0;
    var ttlCts=0;
    for(var i=0 ; i <=intCnt;i++){
        var curCts = parseFloat(nvl(document.getElementById('CB@CRTWT@'+i).value,'0'));
        var curPrc =  parseFloat(nvl(document.getElementById('CB@RTE@'+i).value,'0'));
        var ttlVal =  ttlCts * avgRte;
        var curVal = curCts * curPrc;
        avgRte =(ttlVal+curVal)/(ttlCts+curCts);
       ttlCts=ttlCts+curCts;
    }
     avgRte=Math.round(avgRte);
    document.getElementById('avgRTE').innerHTML= avgRte;
}

function nvl(val,rplVal){
  var rtnVal = val
  if(val=='')
    rtnVal=rplVal;
    
    return rtnVal;
      
  }
  
  function CalRateByTtlVal(){
   
    var cts = parseFloat(jsnvl(document.getElementById('ttl_cts').value));
     var rte = parseFloat(jsnvl(document.getElementById('avg_rte').value));
     var ttl_vlu =  parseFloat(jsnvl(document.getElementById('ttl_vlu').value));
   if(rte==""){
    var calrte ="";
   if(cts!=""){
     calrte = (ttl_vlu / cts);
   }
     calrte = (new Number(calrte)).toFixed(2);
     document.getElementById('avg_rte').value=calrte;
   }
}

function CalCpValue(obj,curCnt){
curCnt=parseInt(curCnt);
    var ttlCP = parseFloat(jsnvl(document.getElementById('TTLCPMT').innerHTML)).toFixed(2);
     var ttlCts = parseFloat(jsnvl(document.getElementById('TTLCTSMT').innerHTML)).toFixed(2);
    var cnt = parseInt(document.getElementById('COUNT').value);
      var balcts = parseFloat(jsnvl(document.getElementById('balcts').innerHTML)).toFixed(2);
      var nextCnt =curCnt+1;
      var newCts=0;
    var newCp=0;
    for(var i=0 ; i<cnt;i++){
        var cts = parseFloat(jsnvl(document.getElementById('CB@CRTWT@'+i).value));
         var cp = parseFloat(jsnvl(document.getElementById('CB@CP@'+i).value));
         if(i!=nextCnt){
          newCp =newCp+(cts*cp);
          newCts=newCts+cts;
         }
    }


    newCp=parseFloat(newCp.toFixed(2));
    newCts=parseFloat(newCts.toFixed(2));
    var balCP = (new Number(ttlCP-newCp)).toFixed(2);
      var balCts = (new Number(ttlCts-newCts)).toFixed(2);
    if(ttlCP < newCp){
        alert("Cp Value is  more than purchase CP value");
        obj.value='';
    }else{
       curCnt = parseInt(curCnt);
      if(cnt > nextCnt){
         
          nextCp= balCP/balCts;
       document.getElementById('CB@CP@'+nextCnt).value=(new Number(nextCp)).toFixed(2);
        
      }
      newCp=0;
      newCts=0;
      for(var i=0 ; i<cnt;i++){
         var cts = parseFloat(jsnvl(document.getElementById('CB@CRTWT@'+i).value));
         var cp = parseFloat(jsnvl(document.getElementById('CB@CP@'+i).value));
         newCp =newCp+(cts*cp);
         newCts=newCts+cts;
      }
      var cpAvg=newCp/newCts;
     document.getElementById('ttlcp').innerHTML =(new Number(newCp)).toFixed(2);
       document.getElementById('balcp').innerHTML =(new Number(ttlCP-newCp)).toFixed(2);
        document.getElementById('avgCP').innerHTML =(new Number(cpAvg)).toFixed(2);
    }
}
function CalPValue(ctsIdn,rteIdn,valIdn){
     var cts = parseFloat(jsnvl(document.getElementById(ctsIdn).value));
      var rte = parseFloat(jsnvl(document.getElementById(rteIdn).value));
      var vlu =  cts*rte;
    
      vlu = (new Number(vlu)).toFixed(2);
      document.getElementById(valIdn).value=vlu;
}

function CalPRateByTtlVal(ctsIdn,rteIdn,valIdn){

     var cts = parseFloat(jsnvl(document.getElementById(ctsIdn).value));
      var rte = parseFloat(jsnvl(document.getElementById(rteIdn).value));
      var ttl_vlu =  parseFloat(jsnvl(document.getElementById(valIdn).value));
    if(!rte){
     var calrte ="";
    if(cts){
      calrte = (ttl_vlu / cts);
    }
      calrte = (new Number(calrte)).toFixed(2);
      document.getElementById(rteIdn).value=calrte;
    }else{
        document.getElementById(valIdn).value=(new Number(cts*rte)).toFixed(2);
    }
}


function calAvlCts(ctsIdn){
var ctsIdnSplit = ctsIdn.split('_');

var frm_elements = document.forms[0].elements;
var ctsSum =0;
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

if(field_type=='text') {
var fieldId = frm_elements[i].id;

if(fieldId.indexOf(ctsIdnSplit[0]+'_')!=-1){
 var ctsVal = $('#'+fieldId).val();
if(ctsVal){
ctsSum = parseFloat(ctsSum)+parseFloat(ctsVal);
}


}
}
}
if(!isNaN(ctsSum)){
 ctsSum =  ctsSum ;
}else{
ctsSum =  $('#'+ctsIdn).val() ;
}
var avlCts= parseFloat(document.getElementById("purCts").innerHTML);
var trfCts=  parseFloat(document.getElementById("trfCts").innerHTML);

var preCurrCts= parseFloat(document.getElementById("curCts").innerHTML);
var preRemCts=  parseFloat(document.getElementById("remCts").innerHTML);

var resval = 0;
if(!isNaN(trfCts)){
 resval =  parseFloat(trfCts)+ parseFloat(ctsSum) ;
}else{
resval =  parseFloat(ctsSum) ;
}

resval =  parseFloat(resval).toFixed(2);
 avlCts = parseFloat(avlCts).toFixed(2);
 
 document.getElementById("curCts").innerHTML = resval;
var remval =  parseFloat(avlCts)- parseFloat(resval) ;
document.getElementById("remCts").innerHTML =(remval).toFixed(2);

 
if(avlCts < resval){
    alert("Carat cannot be greater than purchase lot...");
    document.getElementById(ctsIdn).value ="";
    document.getElementById("curCts").innerHTML =preCurrCts;
    document.getElementById("remCts").innerHTML =preRemCts;
}


}
function verifyAvlCarat(issueCaratIdn,caratVal){
var issueCaratVal = document.getElementById(issueCaratIdn).value;
if(caratVal < issueCaratVal){
    alert("Issue Carat cannot be greater than Carat...");
    document.getElementById(issueCaratIdn).value = caratVal;
}
}

function calSplitAvlCts(ctsIdn,remCts){
var currCts = $('#'+ctsIdn).val();
if(remCts < currCts){
    alert("Carat cannot be greater than remaining Carat...");
    document.getElementById(ctsIdn).value ="";
}
}


function calAvlValue(valIdn){

var ctsIdnSplit = valIdn.split('_');
var frm_elements = document.forms[0].elements;
var ctsSum =0;
for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

if(field_type=='text') {
var fieldId = frm_elements[i].id;

if(fieldId.indexOf(ctsIdnSplit[0]+'_')!=-1){
 var ctsVal = $('#'+fieldId).val();
if(ctsVal){

ctsSum = parseInt(ctsSum)+parseInt(ctsVal);
}


}
}
}
if(!isNaN(ctsSum)){
 ctsSum =  ctsSum ;
}else{
ctsSum =  $('#'+valIdn).val() ;
}
var avlCts= parseFloat(document.getElementById("purVal").innerHTML);
var trfCts=  parseFloat(document.getElementById("trfVal").innerHTML);

var preCurrCts= parseFloat(document.getElementById("curVal").innerHTML);
var preRemCts=  parseFloat(document.getElementById("remVal").innerHTML);

var resval = 0;
if(!isNaN(trfCts)){
 resval =  parseFloat(trfCts)+ parseFloat(ctsSum) ;
}else{
resval =  parseFloat(ctsSum) ;
}

resval =  parseFloat(resval).toFixed(2);
 avlCts = parseFloat(avlCts).toFixed(2);
 
document.getElementById("curVal").innerHTML = resval;
var remval =  parseFloat(avlCts)- parseFloat(resval) ;
document.getElementById("remVal").innerHTML =(remval).toFixed(2);

if(avlCts < resval){
    alert("Value cannot be greater than purchase lot...");
    document.getElementById(valIdn).value ="";
    document.getElementById("curVal").innerHTML =preCurrCts;
    document.getElementById("remVal").innerHTML =preRemCts;
}



}

function ClearFilter(){
    var frm_elements = document.forms[2].elements;
for(i=0; i<frm_elements.length; i++) {
var fldId = frm_elements[i].id;

if(fldId.indexOf('FR_')!=-1){
    frm_elements[i].value="";
}

}

 $('#dataTable tr').css('display', '');

   
}

function CheckDecimal(StyleId){ 
   var styleval =document.getElementById(StyleId).value;
   var bool=true;
   for (j = 0;  j < styleval.length;  j++){
    var ch=styleval.charCodeAt(j);
    if((ch >=48 && ch <=57) || ch==46 || ch==44 || ch==43 || ch==45 ){
        
    }else{
        bool=false;
        break;
    }
   }
   if(!bool){
       document.getElementById(StyleId).value='';
       alert('Data Must be Numeric');
       document.getElementById(StyleId).focus();
   }
}  
   
   function calWeightLoss(ctsAct){
   
   var currCrwts =document.getElementById(ctsAct).value;;
    var actCrwts =$("#"+ctsAct+"_0").text();
    var remCrwts =  parseFloat(actCrwts)- parseFloat(currCrwts) ;
    document.getElementById("wetLossIdn").value=(remCrwts).toFixed(2);
    
   }
       
function validateRepPrpSort(htmlFld){
var newsrt =parseInt(document.getElementById(htmlFld).value);
var validate=false;
var tab = document.getElementById ("tb");
var elems = tab.getElementsByTagName ("input");
for ( var i = 0; i < elems.length; i++ ){
if ( elems[i].type =="text"){
var fieldId = elems[i].id;
if (fieldId.indexOf('srt_') != - 1){
var currentsrt =parseInt(document.getElementById(fieldId).value);
if(newsrt==currentsrt && htmlFld != fieldId){
    validate=true;
    break;
}
}
}
}
if(validate){
    alert('Sort Already Exists in Module');
    document.getElementById(htmlFld).value=''
    document.getElementById(htmlFld).focus();
    return false;
}
}

function verifiedRoughReturn(){
    var frm_elements = document.forms[0].elements;
   for(i=0; i<frm_elements.length; i++) {
   var fieldId = frm_elements[i].id;
    if(fieldId.indexOf('CB#ICMP#')!=-1){
    var fieldFldVal = frm_elements[i].value;
    if(fieldFldVal=='Y'){
    var splitStr = fieldId.split("#");
   
    var lprp = splitStr[2];
    var lprpVal = document.getElementById('CB_'+lprp).value;
    
    if(lprpVal=='' || lprpVal=='0'){
    alert("Please specify "+lprp);
    return false;
    }
    }
      }
   }
   loading();
   return true;
}
    
