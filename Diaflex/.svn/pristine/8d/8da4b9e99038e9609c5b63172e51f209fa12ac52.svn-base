/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/

//SETTING UP OUR POPUP
//0 means disabled; 1 means enabled;
var popupStatus = 0;

//loading popup with jQuery magic!

//CONTROLLING EVENTS IN jQuery


function loadMail(){
   //centering with css
		centerPopupMail();
		//load popup
		loadPopupMail(); 
}

function loadPopupMail(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.8"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContactMail").fadeIn("slow");
		popupStatus = 1;
	}
}

function centerPopupMail(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#popupContactMail").height();
	var popupWidth = $("#popupContactMail").width();
	//centering
	$("#popupContactMail").css({
		"position": "absolute",
		"top":200,
		"left":300
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}

function disablePopupMail(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContactMail").fadeOut("slow");
		popupStatus = 0;
	}
}
function loadFunRult(){
//centering with css
		centerPopupRult();
		//load popup
		loadPopupRult();
}

function loadPopupRult(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.8"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContactRult").fadeIn("slow");
		popupStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disablePopupRult(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContactRult").fadeOut("slow");
		popupStatus = 0;
	}
}

//centering popup
function centerPopupRult(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#popupContactRult").height();
	var popupWidth = $("#popupContactRult").width();
	//centering
	$("#popupContactRult").css({
		"position": "absolute",
		"top":30,
		"left":55,
                "right":55,
                "bottom":30
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}

function loadDmd(){
//centering with css
		centerPopupDmd();
		//load popup
		loadPopupDmd();
}

function loadSVDmd(srchId){

document.getElementById('svsrchId').value=srchId;
//centering with css
		centerPopupDmd();
		//load popup
		loadPopupDmd();
}

function loadPopupDmd(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.8"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContactDmd").fadeIn("slow");
		popupStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disablePopupDmd(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContactDmd").fadeOut("slow");
		popupStatus = 0;
	}
}

//centering popup
function centerPopupDmd(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#popupContactDmd").height();
	var popupWidth = $("#popupContactDmd").width();
	//centering
	$("#popupContactDmd").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}



function loadSale(action){
   //centering with css
  
     document.getElementById('action').value = action;
     centerPopupSale();
		//load popup
     loadPopupSale(); 
}

function loadPopupSale(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.8"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContactSale").fadeIn("slow");
		popupStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disablePopupSale(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContactSale").fadeOut("slow");
		popupStatus = 0;
	}
}

//centering popup
function centerPopupSale(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#popupContactSale").height();
	var popupWidth = $("#popupContactSale").width();
	//centering
	$("#popupContactSale").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}

function loadSEC(){
   //centering with css
   
     centerPopupSEC();
		//load popup
     loadPopupSEC(); 
}

function loadPopupSEC(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.8"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContactSEC").fadeIn("slow");
		popupStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disablePopupSEC(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContactSEC").fadeOut("slow");
		popupStatus = 0;
	}
        var objId=document.getElementById('cellid').value
        document.getElementById(objId).value =document.getElementById('cellVal').value;
}

//centering popup
function centerPopupSEC(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#popupContactSEC").height();
	var popupWidth = $("#popupContactSEC").width();
	//centering
	$("#popupContactSEC").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}

function loadASSFNL(obj , id){
//centering with css
setBGColorSelectTR(obj);
		centerPopupASSFNL(id);
		//load popup
		loadPopupASSFNL();
}
function loadASSFNLPop(id){
//centering with css

		centerPopupASSFNL(id);
		//load popup
		loadPopupASSFNL();
}
function loadLoadingDiv(id){
    centerloadLoadingDiv(id);
		//load popup
		loadPopupLoadingDiv();
}
function loadPopupASSFNL(){
	//loads popup only if it is disabled
  popupStatus=0;
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.4"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContactASSFNL").fadeIn("slow");
		popupStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disablePopupASSFNL(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContactASSFNL").fadeOut("slow");
		popupStatus = 0;
	}
}

function disablePopupParantWn(){
	$("#backgroundPopup",window.parent.document).fadeOut("slow");
		$("#popupContactASSFNL",window.parent.document).fadeOut("slow");
  	}

//centering popup
function centerPopupASSFNL(id){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
        var obj = document.getElementById(id)
        var objPosition = $(obj).position();
        var popupBottom = objPosition.top;
        if(popupBottom > 250)
          popupBottom = popupBottom-250;
      
       
	$("#popupContactASSFNL").css({
		"position": "absolute",
		"top":popupBottom,
		"left":70,
                "right":70,
                "height": 500
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}

 function loading(){
   centerPopuploading();
   loadPopuploading();
}

function centerPopuploading(){
var windowWidth = document.documentElement.clientWidth;
var windowHeight = document.documentElement.clientHeight;
var popupHeight = $("#popupContactSave").height();
var popupWidth = $("#popupContactSave").width();
$("#popupContactSave").css({
"position": "absolute",
"top": windowHeight/2-popupHeight/2,
"left": windowWidth/2-popupWidth/2
});

$("#backgroundPopup").css({
"height": windowHeight
});

}

function loadPopuploading(){
if(popupStatus==0){
$("#backgroundPopup").css({
"opacity": "0.8"
});
$("#backgroundPopup").fadeIn("slow");
$("#popupContactSave").fadeIn("slow");
popupStatus = 1;
}
}

function floatDiv(obj,divId ,divCnt){
   for(var i=1;i<=divCnt;i++){
     var div = "DIV_"+i
      $("#"+div).hide();
     }
    
    var objPosition = $(obj).position();
    $("#"+divId).css({
		"position": "absolute",
		"top":objPosition.top+15,
		"left":objPosition.left
            });
 
       $("#"+divId).slideDown("slow");
     
    document.getElementById('TXTID').value = obj.id;
}
function closeFloatDiv(divId){
     $("#"+divId).slideUp("slow");
}
function closeloading() {
   //disables popup only if it is enabled
   if (popupStatus == 1) {
       $("#backgroundPopup").fadeOut("slow");
       $("#popupContactSave").fadeOut("slow");
       popupStatus = 0;
   }
}
function MKEmptyPopup(target){
    var reqUrl = document.getElementById('reqUrl').value;
      window.open(reqUrl+'/emptyPg.jsp',target);
}

function displayPopup(){

    document.getElementById('popupFrame').style.display = '';
    
}
function goToNewTab(theURL,winName,features) { //v2.0
  //alert(' Going To '+theURL);	
  //window.open(theURL,winName,features);
  var emilId= document.getElementById("mail").value;
  validate_Mail();
  disablePopupMail();
  window.open(theURL+"&mail="+emilId);
}


function validate_Inv(){
     var val = document.getElementById('byr').value;
    
     if (val == "" ){
         alert("Please specify your Name");
          document.getElementById('byr').focus();
         return false;
     }
     
     disablePopupInv();
      return true;
}



function validate_Mail(){
     var val = document.getElementById('eml').value;
    
     if (val == "" ){
         alert("Please specify Email Id");
          document.getElementById('eml').focus();
         return false;
     }
     
                var str = val;
		var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID")
		   return false
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID")
		   return false
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID")
		    return false
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID")
		    return false
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID")
		    return false
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID")
		    return false
		 }
		
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID")
		    return false
		 }

 		
     window.open("StockSearch.do?method=mailExcel&eml="+val, '_blank');
     
      return true;
}

function validate_txt(){
     var val = document.getElementById('srch_dsc').value;
    
     if (val == "" ){
         alert("Please specify Search Description");
          document.getElementById('srch_dsc').focus();
         return false;
     }
     
     disablePopup();
      return true;
}

function loadDashPOP(obj , id){
centerPopupDashPOP(id);
//load popup
loadPopupDashPOP();
}

function centerPopupDashPOP(id){

//request data for centering
var windowWidth = document.documentElement.clientWidth;
var windowHeight = document.documentElement.clientHeight;
var obj = document.getElementById(id)
var objPosition = $(obj).position();
var popupBottom = objPosition.top;
if(popupBottom > 250)
popupBottom = popupBottom-250;


$("#popupDashPOP").css({
"position": "absolute",
"top":popupBottom,
"left":70,
"right":70,
"height": 400
});
//only need force for IE6

$("#backgroundPopup").css({
"height": windowHeight
});

}

function loadPopupDashPOP(){
//loads popup only if it is disabled
if(popupStatus==0){
$("#backgroundPopup").css({
"opacity": "0.4"
});
$("#backgroundPopup").fadeIn("slow");
$("#popupDashPOP").fadeIn("slow");
popupStatus = 1;
}
}


function disablePopupDashPOP(){
//disables popup only if it is enabled
if(popupStatus==1){
$("#backgroundPopup").fadeOut("slow");
$("#popupDashPOP").fadeOut("slow");
popupStatus = 0;
}
}

function loadPOP1(id){
//centering with css

centerEditPOP1(id);
//load popup
loadEditPOP1();
}

function loadEditPOP1(){
//loads popup only if it is disabled
if(popupStatus==0){
$("#backgroundPopup").css({
"opacity": "0.8"
});
$("#backgroundPopup").fadeIn("slow");
$("#popupEditPOP1").fadeIn("slow");
popupStatus = 1;
}
}

//disabling popup with jQuery magic!
function disableEditPOP1(){
//disables popup only if it is enabled
if(popupStatus==1){
$("#backgroundPopup").fadeOut("slow");
$("#popupEditPOP1").fadeOut("slow");
popupStatus = 0;
}
}

//centering popup
function centerEditPOP1(id){
//request data for centering
var windowWidth = document.documentElement.clientWidth;
var windowHeight = document.documentElement.clientHeight;
var obj = document.getElementById(id)
var objPosition = $(obj).position();
var popupBottom = objPosition.top;
if(popupBottom > 250)
popupBottom = popupBottom-250;

//centering
$("#popupEditPOP1").css({
"position": "absolute",
"top":popupBottom,
"left":70,
"right":70,
"height": 400
});
//only need force for IE6

$("#backgroundPopup").css({
"height": windowHeight

});

}

function loadNewGoods(){
//centering with css
		centerPopupNewGoods();
		//load popup
		loadPopupNewGoods();
}
function centerPopupNewGoods(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#popupContactNewGoods").height();
	var popupWidth = $("#popupContactNewGoods").width();
	//centering
	$("#popupContactDmd").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}

function loadPopupNewGoods(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.8"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContactNewGoods").fadeIn("slow");
		popupStatus = 1;
	}
}
function disablePopupNewGoods(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContactNewGoods").fadeOut("slow");
		popupStatus = 0;
	}
}