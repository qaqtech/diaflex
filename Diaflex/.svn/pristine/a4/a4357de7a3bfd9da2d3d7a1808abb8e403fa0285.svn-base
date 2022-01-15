 function getPrpList(){
	
	var dropdownIndex = document.getElementById('mainPrp').selectedIndex;
	var disPrp='prp_'+dropdownIndex;
       
        var mainPrpSz = document.getElementById('srchPrpSZ').value;
       
        for(var i=1;i <= mainPrpSz;i++){
        var prp = 'prp_'+i;
       
        if(prp==disPrp)
	document.getElementById(prp).style.display='block';
        else
         document.getElementById(prp).style.display='none';
        }
       
    }
    
    function showBox(objId){
        document.getElementById(objId).style.display = 'block';
    }
    
     function hiddenBox(objId){
        document.getElementById(objId).style.display = 'none';
    }
    
    function setFrTo(f1, f2) {
        if(document.getElementById(f2).value == 0)
            document.getElementById(f2).value = document.getElementById(f1).value;
        else
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
function isNumeric(obj)
  {
	  var checkOK = "0123456789.-";
	  var checkStr = obj.value;
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
    alert("Please enter only numeric values in the field.");
    obj.value="";
    obj.focus();
    return (false);
  }
   return true;
  
}
    function isMorePrp(){
        var redioBtn = document.getElementsByName('isMore');
        var redioLent = document.getElementsByName('isMore').length;
        for(var i=0 ; i<redioLent ; i++){
           var isChecked =  redioBtn[i].checked ;
            if(isChecked){
            if(redioBtn[i].value=='Y'){
            document.getElementById('morePrp').style.display = 'block';
             }else{
                 document.getElementById('morePrp').style.display = 'none';
             }}
        }
       
    }
    
function chkAll(grp, fld) {
    
    var allFlds = document.getElementsByTagName("input");
    //var allFlds = document.forms[0].elements ;
    for(var i=0; i<allFlds.length; i++) {
        if(allFlds[i].type == "checkbox") {
            var fldNm = allFlds[i].name;
            if(fldNm.indexOf(grp) > -1) {
                //alert('checking '+ fldNm);
                if(document.getElementById(fld).checked == true)                
                    document.getElementById(fldNm).checked = true;        
                 else   
                    document.getElementById(fldNm).checked = false;        
            }
        }   
        
    }
}

function printSelect(){
var checkCount = document.getElementById("PNTCNT").value;
var webUrl = document.getElementById("webUrl").value;
var cnt =  document.getElementById("cnt").value;
var reportUrl =  document.getElementById("repUrl").value;
var grpNme =  document.getElementById("grpNme").value;
var repPath = document.getElementById("repPath").value;
var accessidn = document.getElementById("accessidn").value;
var Idn="" ;
var flg = 0;
for(var i=1 ; i <=checkCount ; i++){
    var chId = "PR_"+i;
    var obj = document.getElementById(chId);
    if(obj.checked){
    if(Idn==""){
      Idn  =obj.value;
    } else{
    Idn  = Idn+","+obj.value;
    }
    flg=1;
    }
}

if(flg==1){
   var theURL = repPath+"/reports/rwservlet?"+cnt+"&report="+reportUrl+"\\reports\\Pricing_matrix_rpt_nw.jsp&p_idn="+Idn+"&p_rem="+grpNme+"&p_access="+accessidn;
 
     window.open(theURL, '_blank');
}else{
    alert("Please select Group for print");
}


}
function verifyKey(){
    var key =  document.getElementById("security").value;
    
     if(key!=''){
     
           var url = "ajaxPrcAction.do?method=security&key="+key;
             
           var req = initRequest();
           req.onreadystatechange = function() {    
             if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesValidKey(req.responseXML);
                   } else if (req.status == 204){
                 }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }else{
        alert("Please Specify Security Code");
         return false;
     }
}
function  parseMessagesValidKey(responseXML){
     var msgTag = responseXML.getElementsByTagName("msg")[0];
      var msg = msgTag.childNodes[0].nodeValue;
    
      if(msg=='N'){
      alert("Verify Security Code");
      document.getElementById('security').value="";
     
      return false;
      }else{
        document.forms[1].submit();
         return true;
      }
}

function confirmPrc(){
    var r = confirm("Are You Sure You Want To Save Changes?");
   if(r){
     loadPopupSale();
        return false;
   }else{
        return false;
   }
}

function AppToLive(grp , nme , liveIdn){
     var r = confirm("Are You Sure You Want To Save Changes?");
 if(r){
 document.getElementById(nme).innerHTML = "Processing............";
     var url = "ajaxPrcAction.do?method=appToLive&rem="+grp+"&nme="+nme+"&liveIdn="+liveIdn;
     var req = initRequest();
     req.onreadystatechange = function() {    
          if (req.readyState == 4) {
               if (req.status == 200) {
                   parseMessagesAPPTOLIVE(req.responseXML , nme);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
 }
}

function CheckJBBasePriceSheet(action){
   var invaidPrice = new Array();
   
    var rows = document.getElementById('rows').value;
    var cols = document.getElementById('cols').value;
    for(var i=0 ; i < rows ; i++){
        for(var j=0 ; j < cols ; j++){
         invaidPrice = validteJBBasePrice(i,j,invaidPrice);
      }
    }
    if(invaidPrice.length>0){
    var msg = "Incorrect Prices : \n";
    for(var k=0;k<invaidPrice.length;k++){
        msg+=invaidPrice[k];
    }
    cfm = confirm(msg); 
     if(cfm){
          loadSale(action);
     }
    }else{
         document.getElementById('action').value = action;
        document.forms[1].submit();
    }
    
    return cfm;
}

function fileLoadJBBasePrice(){
   var i = document.getElementById('rows').value;
    var j = document.getElementById('cols').value;
  
  for(var row=0 ; row < i ; row++){
        for(var colm=0 ; colm < j ; colm++){
         var  leftVal='';
         var  abvVal='';
    if(row==0 && colm==0){
        
    }else if(row==0 && colm!=0){
        var leftId = row+'_'+(colm-1);
        leftVal = document.getElementById(leftId).value;
    }else if(colm==0 && row!=0){
         var abvId = (row-1)+'_'+colm;
         abvVal = document.getElementById(abvId).value;
         
    }else{
          leftId = row+'_'+(colm-1);
          leftVal = document.getElementById(leftId).value;
          abvId = (row-1)+'_'+colm;
          abvVal = document.getElementById(abvId).value;
     }
     var val = document.getElementById(row+'_'+colm).value;
    
     if(leftVal!=''){
    
            leftVal = parseFloat(leftVal);
            if(val > leftVal){
             
           document.getElementById(row+'_'+colm).style.backgroundColor = 'Yellow';
        }
     }
     if(abvVal!=''){
      
        abvVal = parseFloat(abvVal);
        if(val > abvVal){
       
         document.getElementById(row+'_'+colm).style.backgroundColor = 'Yellow';
        }
    }
    }}
}


function validteJBBasePrice(row , colm , prcLst){

var  leftVal="";
var  abvVal="";

    if(row==0 && colm==0){
        
    }else if(row==0 && colm!=0){
        var leftId = row+'_'+(colm-1);
        leftVal = document.getElementById(leftId).value;
    }else if(colm==0 && row!=0){
         var abvId = (row-1)+'_'+colm;
         abvVal = document.getElementById(abvId).value;
    }else{
          leftId = row+'_'+(colm-1);
          leftVal = document.getElementById(leftId).value;
          abvId = (row-1)+'_'+colm;
          abvVal = document.getElementById(abvId).value;
     }
     var val = document.getElementById(row+'_'+colm).value;
     var valRPst = document.getElementById("ROW_"+row).innerHTML;
      var valCPst = document.getElementById("COLM_"+colm).innerHTML;
     var msg = valRPst+"-"+valCPst+":"+val+"\n";
     if(leftVal!=''){
            leftVal = parseFloat(leftVal);
            if(val > leftVal){
           prcLst.push(msg);
        }
     }
     if(abvVal!=''){
        abvVal = parseFloat(abvVal);
        if(val > abvVal){
        prcLst.push(msg);
        }
    }
    return prcLst;
}
function CheckJBBasePrice(obj , orgVal){
    var objId = obj.id;
    var val  = obj.value;
    if(val!=''){
    val = parseFloat(val);
    var str=objId.split('_');
    var row = str[0];
    var colm = str[1];
    var cfm = '';
    var leftVal ='';
    var abvVal ='';
   
    if(row==0 && colm==0){
        
    }else if(row==0 && colm!=0){
        var leftId = row+'_'+(colm-1);
       
         leftVal = document.getElementById(leftId).value;
        
    }else if(colm==0 && row!=0){
    
        var abvId = (row-1)+'_'+colm;
         abvVal = document.getElementById(abvId).value;
        
    }else{
          leftId = row+'_'+(colm-1);
          leftVal = document.getElementById(leftId).value;
           abvId = (row-1)+'_'+colm;
          abvVal = document.getElementById(abvId).value;
         
          
    }
   
    if(leftVal!=''){
            leftVal = parseFloat(leftVal);
            if(val > leftVal){
               cfm = confirm("Price not correct Do you want to proceed."); 
               if(cfm){
               document.getElementById('cellid').value=objId;
                document.getElementById('cellVal').value=orgVal;
                   loadSEC();
               }else{
                   obj.value =  orgVal;
               }
            }
     }
    if(abvVal!=''){
        abvVal = parseFloat(abvVal);
        if(val > abvVal){
         document.getElementById('cellid').value=objId;
          document.getElementById('cellVal').value=orgVal;
            cfm = confirm("Price not correct Do you want to proceed."); 
            if(cfm){
                loadSEC();
            }else{
                obj.value= orgVal;
            }
        }
    }
    
}}

function verifysecurityKey(){
    var key =  document.getElementById("securityid").value;
    
     if(key!=''){
     
           var url = "ajaxPrcAction.do?method=security&key="+key;
             
           var req = initRequest();
           req.onreadystatechange = function() {    
             if (req.readyState == 4) {
                   if (req.status == 200) {
                     parseMessagesValidSECKey(req.responseXML);
                   } else if (req.status == 204){
                 }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }else{
        alert("Please Specify Security Code");
         return false;
     }
}
function  parseMessagesValidSECKey(responseXML){
     var msgTag = responseXML.getElementsByTagName("msg")[0];
      var msg = msgTag.childNodes[0].nodeValue;
    
      if(msg=='N'){
      alert("Verify Security Code");
       var objId=document.getElementById('cellid').value;
       document.getElementById(objId).value =document.getElementById('cellVal').value;
     
     }
      disablePopupSEC();
}
function CalculateDiffExtraAmt(obj){
     var ofrDis = obj.value;
      var amtID = "EXTRADISCAMT";
     if(ofrDis!=''){
      var amt = document.getElementById(amtID).value;
     if(amt=='')
     amt=0;
     var qout =  document.getElementById("quot").value;
     if(qout=='')
     qout=0;
     var rap1=document.getElementById("rap").value;
    var rate = ( rap1 * (100+parseFloat(ofrDis))/100);
    document.getElementById(amtID).value = parseFloat(amt) + (get2DigitRnd(qout - rate)*-1);
     }else{
         document.getElementById(amtID).value = "";
     }
}

function get2DigitRnd(lNum) {
	return parseInt(lNum);
}
function get2DigitNum(lNum) {
	return parseFloat(parseInt(lNum*100)/100);//(new Number(lNum)).toFixed(2);//parseFloat(parseInt(lNum*100)/100);
}
function  parseMessagesAPPTOLIVE(responseXML , nme){
     var message = responseXML.getElementsByTagName("msg")[0];
     
     var check =  message.childNodes[0].nodeValue;
     if(check=='DONE'){
        document.getElementById(nme).innerHTML = "Changes get Apply";
     }else{
         document.getElementById(nme).innerHTML = "Error in process";
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