function alphNumeric_pwd(obj){
       var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
       var num = "0123456789";
       var alphnum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	  var checkStr = obj.value;
	  var allValid = true;
          var allnum = true;
          var allAlpNum = true;
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
       
       for (i = 0;  i < checkStr.length;  i++)
	  {
    	ch = checkStr.charAt(i);
		    for (j = 0;  j < alphnum.length;  j++)
		      if (ch == alphnum.charAt(j))
        		break;
		    if (j == alphnum.length)
		    {
	    	  allAlpNum = false;
                   
    		  break;
		    }
         }
        
         
  	
  if (allValid)
  {  
   
    alert("Password must be Alphanumeric");
    obj.focus();
      obj.value="";
    return false;
  }
  
  if (allnum)
  {  
   
    alert("Password must be Alphanumeric");
    obj.focus();
      obj.value="";
    return false;
  }
  
  if (!allAlpNum)
  {  
   
    alert("Password must be Alphanumeric");
    obj.focus();
      obj.value="";
    return false;
  }
  
  
  if(obj.value.length < 5){
       alert("Password must contain  min 5 characters!");
       obj.focus();
        obj.value="";
     return false; 
    }
  return true;
}

function echeck(obj) {
               var str = obj.value;
		var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID")
                   obj.value="";
		   return false
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID")
                   obj.value="";
		   return false
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID")
                    obj.value="";
		    return false
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID")
                    obj.value="";
		    return false
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID")
                    obj.value="";
		    return false
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID")
                    obj.value="";
		    return false
		 }
		
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID")
                    obj.value="";
		    return false
		 }

 		 return true					
	}


function validate_required(obj ,alerttxt ) {
	var value = document.getElementById(obj).value;
		if (value==null||value=="") {
			alert(alerttxt);
			return false;
		}
		else {
			return true;
		}
	
}

function validateFeedbackForm(){
 var starttm = document.getElementById('starttm').value;
  var endtm = document.getElementById('endtm').value;
 var mode = document.getElementById('mode').value;
  var memoref = document.getElementById('memoref').value;
   var dicuss = document.getElementById('dicuss').value;
                if (starttm=='') {
			alert('Please Start Time!');
			return false;
		}
                if (mode=='') {
			alert('Please specify Mode of Communication.!');
			return false;
		}  
                  if (dicuss=='') {
			alert('Please specify Points Discussed.!');
			return false;
		} 
                if (endtm=='') {
			alert('Please End Time!');
			return false;
		}
                return true;
}
function validateUpd() {
        var vnm = document.getElementById('vnm').value;
        var memo = document.getElementById('memo').value;
        var prp = document.getElementById('prp').value;
        if(prp=='0'){
            alert("Please specify");
              
        }
        if(vnm!=''){
                if (validate_required("prpVal","Please specify  Update Values!")==false) {
			
			return false;
		}
              }
        if(memo!=''){
                  if (validate_required("memoVal","Please specify  Update Values!")==false) {
			
			return false;
		} 
              }
             
        if(vnm=='' && memo==''){
                 alert("Please specify Packets OR Memo No. ");
                 
                 return false;
        }
                
                return true;
    }

function validateStt() {

              if (validate_required("stt","Please specify Status.!")==false) {
			
			return false;
		} 
                
                if (validate_required("flg","Please specify  flag!")==false) {
			
			return false;
		}
                
                
                return true;




}

function validate_select(objId , alerttxt){
    var value = document.getElementById(objId).value;
   
   if (value=="0") {
			alert(alerttxt);
			return false;
		}
		else {
			return true;
		}
}

function validate_sale(){
    var vnm = document.getElementById('vnmLst').value;

   
       var byIdn = document.getElementById('byrId').value;
    if(vnm==""){
       if(byIdn==0){
            
            
                alert("Please select Buyer Name");
                return false;
            
       }
    }
    return true;
}

function validate_saleXl(){
    var vnm = document.getElementById('vnmLst').value;

   var saleIdn = document.getElementById('saleIdn').value;
       var byIdn = document.getElementById('byrId').value;
    if(vnm=="" && saleIdn==""){
       if(byIdn==0){
            
            
                alert("Please select Buyer Name Or Sale Idn Or Packets.");
                return false;
            
       }
    }
    return true;
}
function validateHoliDay(){
     if (validate_required("frmDte","Please specify From Date.!")==false) {
			
			return false;
		} 
                
      if (validate_select("typ","Please select holiday type!")==false) {
			
			return false;
    }
       
                return true;
                
}

function approvalVal(){
   var isVal = false;
    var FrmDte = document.getElementById('FrmDte').value;
    var toDte = document.getElementById('toDte').value;
    var frmDiff = document.getElementById('frmDiff').value;
    var toDiff = document.getElementById('toDiff').value;
    var seq = document.getElementById('seq').value;
    var vnm = document.getElementById('vnm').value;
    if(FrmDte!=''){
        isVal= true;
    }else if(toDte!=''){
        isVal = true;
    }else if(frmDiff!=''){
         isVal = true;
    }else if(toDiff!=''){
         isVal = true;
    }else if(seq!=''){
         isVal = true;
    }else if(vnm!=''){
         isVal = true;
    }
    if(!isVal){
        alert("Please add criteria for view");
    }
      return isVal; 
}

function PwdValidation(obj){
    if(obj.value.length < 4){
       alert("Password must contain  min 4 characters!");
       obj.focus();
        obj.value="";
     return false; 
    }
  return true;
}

function validateSelection(objId , msg , url){
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
    alert(msg);
    return false;
  }
  
  window.open(url, '_self');
}

function validateCommonPRP(obj){
    var frm_elements = document.forms[1].elements;
   var bool = false;
   for(i=0; i<frm_elements.length; i++) {
    field_type = frm_elements[i].type.toLowerCase();

    if(field_type=='select-one') {
            var fieldId = frm_elements[i].id;
            
            if(fieldId.indexOf('COM_')!=-1){
            
            var fldVal = document.getElementById(fieldId).value;
         
            if(fldVal==''){
                bool = true;
            }
            }
          
    }}
     if(bool){
    alert("Please select Common Properties.");
    return false;
  }
}

 function validate_locationDlv(){
var vnm = document.getElementById('vnmLst').value;
var byIdn = document.getElementById('byrId').value;
if(byIdn==0){


alert("Please select Buyer Name");
return false;

}
return true;
}


function sumbitFormConfirm(fldId,frm,msg,errMsg,fldTyp){
  var isChecked = false;
 
    var frm_elements = document.forms[frm].elements; 
 
       for(i=0; i<frm_elements.length; i++) {

        var field_type = frm_elements[i].type.toLowerCase();
          
           if(field_type==fldTyp) {
             var fieldId = frm_elements[i].id;
            
            if(fieldId.indexOf(fldId)!=-1){
              isChecked =  document.getElementById(fieldId).checked;
            

              if(isChecked)
               break;
            }}}
    if(isChecked){
    var r = confirm(msg);
    return r;
    }else{
        alert(errMsg);
        return false;
    }

}

function checkEmpSelection(){
   var empIdn = document.getElementById('empIdn').value;
   if(empIdn=='0'){
       alert("Please select Employee..");
       return false;
   }else{
          return true;
   }
}

function SelectALLPrp(name,frmId) {
var idval=document.getElementById(name).value;
var frm_elements = document.forms[frmId].elements;
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


function validateManualEntries() {

              if (validate_required("refIdn","Please specify Ref Idn.!")==false) {
			
			return false;
		} 
                
                if (validate_required("cts","Please specify  Carat!")==false) {
			
			return false;
		}
                 if (validate_required("amt","Please specify  Amount!")==false) {
			
			return false;
		}
                
                return true;
}

function validateMasterEntries() {

       if (validate_required("nme","Please specify Name!")==false) {
			
			return false;
		} 
                
                if (validate_required("cd","Please specify  Shory Name!")==false) {
			
			return false;
		}
                 
                return true;
}