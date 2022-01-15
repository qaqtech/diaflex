function PriceChangeFileUP(cnt){
    var id = "IDN_"+cnt;
    
    var stkIdn = document.getElementById(id).value;
    var diff = document.getElementById("chng_"+stkIdn).value;
    if(diff!=''){
    PriceChangeMemo(stkIdn);
    }
}

 function calPRC(){
 alert('hi');
loading();
var count = document.getElementById('ttl_cnt').value;
for(var i=1 ;i<=count;i++){
   PriceChangeFileUP(i);
  }
closeloading();
}
  
function checkMemoBuyer(){
var memoId = document.getElementById("memoId").value;
if(memoId!='0' && memoId!=''){
var url = "ajaxPrcAction.do?method=checkMemoBuyer&memoId="+memoId;
  var req = initRequest();
     req.onreadystatechange = function() {    
          if (req.readyState == 4) {
               if (req.status == 200) {         
                   if(!parseMessagesMemoByr(req.responseXML)){
                   return false;
                   }
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}
}
function parseMessagesMemoByr(responseXML){
  var message = responseXML.getElementsByTagName("message")[0];
  var msg = message.childNodes[0].nodeValue;  
  if(msg=='Yes'){
  alert("Below Memoid Buyer are Diffrent please Enter same Buyer Memo Id");
  document.getElementById("memoId").value='0';
  return false ;
  }
  return true ;
}
function PriceChangeMemo(stkIdn){
     var typ = document.getElementById("typ_"+stkIdn).value;
     var diff = document.getElementById("chng_"+stkIdn).value;
     if(diff!=''){
     var Idn = document.getElementById("memoId").value;
     var url = "ajaxPrcAction.do?method=price&stkIdn="+stkIdn+"&typ="+typ+"&diff="+diff+"&Idn="+Idn;
     var req = initRequest();
     req.onreadystatechange = function() {    
          if (req.readyState == 4) {
               if (req.status == 200) {
                   parseMessagesPrice(req.responseXML , diff , typ , stkIdn);
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
     }else{
         alert('Please Enter Pct To Apply');
         document.getElementById("chng_"+stkIdn).focus();
     }
}

function parseMessagesPrice(responseXML , diff , typ , isGlbChng){
    var columns = responseXML.getElementsByTagName("prices")[0];
     for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
           var stkIdn = (columnroot.getElementsByTagName("stkIdn")[0]).childNodes[0].nodeValue;
           var fnlVlu = (columnroot.getElementsByTagName("fnlVlu")[0]).childNodes[0].nodeValue;
           var fnlQuot = (columnroot.getElementsByTagName("fnlQuot")[0]).childNodes[0].nodeValue;
           var fnlDis = (columnroot.getElementsByTagName("fnlDis")[0]).childNodes[0].nodeValue;
           var rapVlu = (columnroot.getElementsByTagName("rapVlu")[0]).childNodes[0].nodeValue;
           
           if(stkIdn=='TOTAL'){
            document.getElementById('new_vlu').value = fnlVlu;
            document.getElementById('new_dis').value = fnlDis;
            document.getElementById('new_avg').value = fnlQuot;
           
           }else{
            document.getElementById('val_'+stkIdn).value = fnlVlu;
            document.getElementById('nwdis_'+stkIdn).value = fnlDis;
            document.getElementById('nwprice_'+stkIdn).value = fnlQuot;
            document.getElementById('rapVal_'+stkIdn).value = rapVlu;
            if(isGlbChng=='ALL'){
            document.getElementById('typ_'+stkIdn).value = typ;
            document.getElementById('chng_'+stkIdn).value = diff;
           }
          }
      }
}

function PriceChangeMemoGiveOffer(stkIdn){
     var typ = document.getElementById("typ_"+stkIdn).value;
     var diff = document.getElementById("chng_"+stkIdn).value;
     if(diff!=''){
     var frm_elements = document.forms[0].elements;

        for(i=0; i<frm_elements.length; i++) {
           field_type = frm_elements[i].type.toLowerCase();
            if(field_type=='checkbox') {
              var fieldId = frm_elements[i].id;
             if(fieldId.indexOf('cb_memo_')!=-1 && document.getElementById(fieldId).checked ==true){
             var split=fieldId.split('_');
             var idn = parseInt(split[3]);
             var quot = document.getElementById('quot_'+idn).value;
             var dis = document.getElementById('dis_'+idn).value;
             if(typ=='PER_CRT_DIS'){
             var rdis = 100 + parseFloat(dis) ;
             var  rap1 = (quot * 100)/rdis;
             var disdiff=(parseFloat(dis)+parseFloat(diff));
             var pepPrc =((parseFloat(rap1)*(100+(parseFloat(disdiff)))/100));
             document.getElementById('bid_prc_'+idn).value = pepPrc;
            document.getElementById('bid_dis_txt_'+idn).value = disdiff;
            }
            if(typ=='PER_STONE_DIS'){
             var rdis = 100 + parseFloat(dis) ;
             var  rap1 = (quot * 100)/rdis;
             var disdiff=(parseFloat(diff));
             var pepPrc =((parseFloat(rap1)*(100+(parseFloat(disdiff)))/100));
             document.getElementById('bid_prc_'+idn).value = pepPrc;
            document.getElementById('bid_dis_txt_'+idn).value = disdiff;
            }
            }}
            }
     }else{
         alert('Please Enter Pct To Apply');
         document.getElementById("chng_"+stkIdn).focus();
     }
}

function appVerifySelect(){
    var row = 1;
    var count = document.getElementById('ttl_cnt').value;
    for(var i=0;i<count;i++){
        var cheId = "ch_"+i;
        var isChecked = document.getElementById(cheId).checked;
      
        if(isChecked){
            appVerifySC(row);
            
        }else{
            clearTxt(row)
        }
        row = row+1;
    }
   
   calcAvgsSC(count);	
  
}

function clearTxt(row){
    
    document.getElementById(row+'_fnl').value = "";
    document.getElementById(row+'_chng').value = "";
    document.getElementById(row+'_fnl_dis').value = "" ;

}

function calcAvgsSC(count) {
	var rapVlu = 0 ;
	var quotVlu = 0 ;
	var nwVlu = 0 ;
	var ttlCts = 0 ;
	for(i=1;i<=count;i++) {
            var chId = "ch_"+(i-1);
            var isChecked = document.getElementById(chId).checked;
            if(isChecked){
		var lCts = parseInt(document.getElementById(i+'_cts').value*100);
		lCts = parseFloat(lCts/100);
		ttlCts = ttlCts + lCts ;
		//lCts =  lCts.toDecimals(2); //Math.Floor(parseFloat(lCts)*100)/100;
		//alert(lCts);
		var lRap = get2DigitNum(document.getElementById(i+'_rap').value*lCts); //parseInt(document.getElementById(i+'_rap').value);
		var lQuot = get2DigitNum(document.getElementById(i+'_quot').value*lCts); //parseInt(document.getElementById(i+'_quot').value);
		var lNw = get2DigitNum(document.getElementById(i+'_fnl').value*lCts); //parseInt(document.getElementById(i+'_quot').value);
		
		rapVlu = rapVlu + lRap ; //(lRap*lCts);
		quotVlu = quotVlu + lQuot ; //(lQuot*lCts);
		nwVlu = nwVlu + lNw ;
            }
	}	
	rapVlu = Math.ceil(rapVlu);//get2DigitNum(rapVlu);
	quotVlu = Math.ceil(quotVlu);
	nwVlu = Math.ceil(nwVlu);
	ttlCts = (new Number(ttlCts)).toFixed(2);
	document.getElementById('glbl_cts').value = ttlCts ;
	document.getElementById('glbl_rap_vlu').value = rapVlu ;
	document.getElementById('old_vlusc').value = quotVlu ;
	document.getElementById('old_dissc').value = get2DigitNum((quotVlu/rapVlu*100) - 100);//parseFloat(parseInt(((quotVlu/rapVlu*100) - 100)*100)/100);
	document.getElementById('old_avgsc').value = get2DigitNum(quotVlu/ttlCts);
	document.getElementById('new_vlusc').value = nwVlu ;
	document.getElementById('new_dissc').value = get2DigitNum((nwVlu/rapVlu*100) - 100);//parseFloat(parseInt(((quotVlu/rapVlu*100) - 100)*100)/100);
	document.getElementById('new_avgsc').value = get2DigitNum(nwVlu/ttlCts);
	
}
function appVerifySC(rowNum) {
	var glbl_typ = document.getElementById('glbl_typ').value;
	var glbl_chng = document.getElementById('glbl_chng').value;
	var ctr = document.getElementById('ttl_cnt').value; 
	var frmCtr = rowNum;
	var toCtr = rowNum ;
		
	if(isNaN(glbl_chng)) {
		//alert('Not a valid change');
	} else {
		if(parseFloat(glbl_chng) != 0) {
			//alert(glbl_typ);
			
			
			if(glbl_typ == 'AVG_PRC')
				setAvgPrc(glbl_typ, glbl_chng, frmCtr, toCtr);
			if(glbl_typ == 'AVG_DIS')
				setAvgDis(glbl_typ, glbl_chng, frmCtr, toCtr);
			if(glbl_typ == 'PER_CRT_USD')
				setPerCrtUSD(glbl_typ, glbl_chng, frmCtr, toCtr);
			if(glbl_typ == 'PER_CRT_PCT')
				setPerCrtPCT(glbl_typ, glbl_chng, frmCtr, toCtr);
			if(glbl_typ == 'PER_STONE')
				setPerStone(glbl_typ, glbl_chng, frmCtr, toCtr);
                         if(glbl_typ =='PER_STONE_DIS')
				setperDis(glbl_typ, glbl_chng, frmCtr, toCtr);
				
			
		}
	}	
}

function appVerify(rowNum) {
         
	var glbl_typ = document.getElementById('glbl_typ').value;
	var glbl_chng = document.getElementById('glbl_chng').value;
	var ctr = document.getElementById('ttl_cnt').value; 
	var frmCtr = 1 ;
	var toCtr = ctr ;
	if(rowNum > 0) {
		toCtr = rowNum ;
		frmCtr = rowNum ;
		glbl_typ = document.getElementById(rowNum+'_typ').value;
		glbl_chng = document.getElementById(rowNum+'_chng').value;
	}	
	if(isNaN(glbl_chng)) {
		//alert('Not a valid change');
	} else {
		if(parseFloat(glbl_chng) != 0) {
			//alert(glbl_typ);
			calcAvgs(ctr);	
			
			if(glbl_typ == 'AVG_PRC')
				setAvgPrc(glbl_typ, glbl_chng, frmCtr, toCtr );
			if(glbl_typ == 'AVG_DIS')
				setAvgDis(glbl_typ, glbl_chng, frmCtr, toCtr );
			if(glbl_typ == 'PER_CRT_USD')
				setPerCrtUSD(glbl_typ, glbl_chng, frmCtr, toCtr);
			if(glbl_typ == 'PER_CRT_PCT')
				setPerCrtPCT(glbl_typ, glbl_chng, frmCtr, toCtr);
			if(glbl_typ == 'PER_STONE')
				setPerStone(glbl_typ, glbl_chng, frmCtr, toCtr);
                        if(glbl_typ =='PER_STONE_DIS')
				setperDis(glbl_typ, glbl_chng, frmCtr, toCtr);
                       if(glbl_typ =='FIX')
                          setPerFixprice(glbl_typ, glbl_chng, frmCtr, toCtr);
	
			calcAvgs(ctr);	
		}
	}	
}

function setAvgPrc(typ, val, i, ctr) {
         var exhRte = new Number(document.getElementById('exhRte').value);

	var lDiff = getDiffPct(val);
	for(i;i<=ctr;i++) {
		var lDiff = getDiffPct(val);
		var lRap = parseFloat(document.getElementById(i+'_rap').value);
		var lQuot = parseFloat(document.getElementById(i+'_quot').value);
		var lChng = new Number(lQuot*lDiff);
                
		var nwQuot = new Number(lQuot + lChng);
            
		document.getElementById(i+'_typ').value = typ ;
		document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
		document.getElementById(i+'_chng').value = lChng.toFixed(2);
                if(exhRte>1){
               document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
               document.getElementById(i+'_fnl$').value = nwQuot.toFixed(2)/exhRte.toFixed(2);
             }else{
              document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);  
            }
                if(lRap>1)
                document.getElementById(i+'_fnl_dis').value = new Number((nwQuot/lRap*100) - 100).toFixed(2) ;
	}
}

function setAvgDis(typ, val, i, ctr) {
// var lDiff = getDiffPct(val);
var exhRte = new Number(document.getElementById('exhRte').value);
var oldDis = new Number(document.getElementById('old_dis').value);

var lDiff = get6DigitNum(Math.abs((val - oldDis))) ;

if (val < oldDis)
    lDiff = lDiff*-1 ;

for(i;i<=ctr;i++) {
var lQuot = parseFloat(document.getElementById(i+'_quot').value);
var lDis = parseFloat(document.getElementById(i+'_dis').value);
var lRap = parseFloat(document.getElementById(i+'_rap').value);
var lChng = new Number(lDis + lDiff);
// lChng = new Number(parseFloat(val));
var nwQuot = new Number(lRap*(100 + lChng)/100);
document.getElementById(i+'_typ').value = typ ;
//alert(i + " : " + nwQuot);

if(exhRte>1){
document.getElementById(i+'_fnl').value = nwQuot.toFixed(4)*exhRte.toFixed(4);
document.getElementById(i+'_fnl$').value = nwQuot.toFixed(4);
}else{
  document.getElementById(i+'_fnl').value = nwQuot.toFixed(4);  
}
document.getElementById(i+'_chng').value = lChng.toFixed(4);
if(lRap>1)
document.getElementById(i+'_fnl_dis').value = lChng.toFixed(6) ;
}
}



function setperDis(typ, val, i, ctr) {
// var lDiff = getDiffPct(val);
var exhRte = new Number(document.getElementById('exhRte').value);

var lDiff = Math.abs(val) - Math.abs(document.getElementById('old_dis').value) ;
if(val < 0) 
    lDiff = lDiff*-1;
for(i;i<=ctr;i++) {
var lQuot = parseFloat(document.getElementById(i+'_quot').value);
var lDis = parseFloat(document.getElementById(i+'_dis').value);
var lRap = parseFloat(document.getElementById(i+'_rap').value);
var lChng = new Number(lDis - lDiff);
 lChng = new Number(parseFloat(val));
var nwQuot = new Number(lRap*(100 + lChng)/100);
document.getElementById(i+'_typ').value = typ ;

if(exhRte>1){
document.getElementById(i+'_fnl').value = nwQuot.toFixed(2)*exhRte.toFixed(2);
document.getElementById(i+'_fnl$').value = nwQuot.toFixed(2);
}else{
  document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);  
}
document.getElementById(i+'_chng').value = lChng.toFixed(2);
if(lRap>1)
document.getElementById(i+'_fnl_dis').value = lChng.toFixed(2) ;
}
}
function setPerCrtUSD(typ, val, i, ctr) {
var exhRte = new Number(document.getElementById('exhRte').value);

	for(i;i<=ctr;i++) {
		var lQuot = parseFloat(document.getElementById(i+'_quot').value);
		var lChng = new Number(parseFloat(val));
		var nwQuot = new Number(lQuot + lChng);
		var lRap = parseFloat(document.getElementById(i+'_rap').value);
		document.getElementById(i+'_typ').value = typ ;
		document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
		document.getElementById(i+'_chng').value = lChng.toFixed(2);
                if(exhRte>1){
               document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
               document.getElementById(i+'_fnl$').value = nwQuot.toFixed(2)/exhRte.toFixed(2);
               }else{
                document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);  
                }
                if(lRap>1)
                document.getElementById(i+'_fnl_dis').value = new Number((nwQuot/lRap*100) - 100).toFixed(2) ;

	}
}

function setPerCrtPCT(typ, val, i, ctr) {
var exhRte = new Number(document.getElementById('exhRte').value);

	for(i;i<=ctr;i++) {
		var lQuot = parseFloat(document.getElementById(i+'_quot').value);
		var lChng = new Number(parseFloat(val));
		var nwQuot = new Number(lQuot * (100 + lChng)/100);
		var lRap = parseFloat(document.getElementById(i+'_rap').value);
		document.getElementById(i+'_typ').value = typ ;
		document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
		document.getElementById(i+'_chng').value = lChng.toFixed(2);
                if(exhRte>1){
                document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
                document.getElementById(i+'_fnl$').value = nwQuot.toFixed(2)/exhRte.toFixed(2);
               }else{
                  document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);  
                }
                if(lRap>1)
                document.getElementById(i+'_fnl_dis').value = new Number((nwQuot/lRap*100) - 100).toFixed(2) ;

	}
	
}

function setPerStone(typ, val, i, ctr) {
var exhRte = new Number(document.getElementById('exhRte').value);

	for(i;i<=ctr;i++) {
		var lQuot = parseFloat(document.getElementById(i+'_quot').value);
		var lCts = parseFloat(document.getElementById(i+'_cts').value);
		var lChng = new Number(val/lCts);
		var nwQuot = new Number(lQuot + lChng);
		document.getElementById(i+'_typ').value = typ ;
		document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
                if(exhRte>1){
                document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
                document.getElementById(i+'_fnl$').value = nwQuot.toFixed(2)/exhRte.toFixed(2);
              }else{
                    document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);  
              }
		document.getElementById(i+'_chng').value = lChng.toFixed(2);
	}
	
}

function setPerFixprice(typ, val, i, ctr) {
var exhRte = new Number(document.getElementById('exhRte').value);

	for(i;i<=ctr;i++) {
		var lQuot = parseFloat(document.getElementById(i+'_quot').value);
		var lChng = new Number(parseFloat(val));
		var nwQuot = lChng
		var lRap = parseFloat(document.getElementById(i+'_rap').value);
		document.getElementById(i+'_typ').value = typ ;
		document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
		document.getElementById(i+'_chng').value = lChng.toFixed(2);
                if(exhRte>1){
                document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);
                document.getElementById(i+'_fnl$').value = nwQuot.toFixed(2)/exhRte.toFixed(2);
               }else{
                  document.getElementById(i+'_fnl').value = nwQuot.toFixed(2);  
                }
                if(lRap>1)
                document.getElementById(i+'_fnl_dis').value = new Number((nwQuot/lRap*100) - 100).toFixed(2) ;

	}
	
}

function getDiffPct(val) {
	var lDiff = 0 ;
	//lDiff = (new Number(lDiff)).toFixed( ;
	var ttlCts = document.getElementById('glbl_cts').value ;
	var rapVlu = document.getElementById('glbl_rap_vlu').value ;
	var oldVlu = document.getElementById('old_vlu').value ;
	var oldAvg = oldVlu/ttlCts ;
	var newAvg = oldAvg + val;
	lDiff = val/oldAvg ;
	return lDiff;
}
function calcAvgs(ctr) {
	var rapVlu = 0 ;
	var quotVlu = 0 ;
	var nwVlu = 0 ;
	var ttlCts = 0 ;
        var exhRte = document.getElementById('exhRte').value;
       
	for(i=1;i<=ctr;i++) {
		
//		var lCts = parseInt(document.getElementById(i+'_cts').value*100);
//               
//		lCts = parseFloat(lCts/100);
//                lCts = get2DigitNum(lCts);
                var lCts = parseFloat(document.getElementById(i+'_cts').value);
		ttlCts = ttlCts + lCts ;
                ttlCts = get2DigitNum(ttlCts);
		//lCts =  lCts.toDecimals(2); //Math.Floor(parseFloat(lCts)*100)/100;
		//alert(lCts);
                
		var lRap = Math.round(document.getElementById(i+'_rap').value*lCts); //parseInt(document.getElementById(i+'_rap').value);
		var lQuot = get2DigitNum(document.getElementById(i+'_quot').value*lCts)/get2DigitNum(exhRte); 
               // alert(i +' : ' + lCts + ' : ' + document.getElementById(i+'_quot').value + ' : ' );
		var lNw = get2DigitNum(document.getElementById(i+'_fnl').value*lCts)/get2DigitNum(exhRte); 
		document.getElementById(i+'_rapVal').value = lRap ;
                 document.getElementById(i+'_Val').value =lQuot ;
                if(exhRte>1){
                 document.getElementById(i+'_Val').value = get2DigitNum(document.getElementById(i+'_fnl$').value*lCts); 
                }
                
               
               
		rapVlu = rapVlu + lRap ; //(lRap*lCts);
		quotVlu = quotVlu + lQuot ; //(lQuot*lCts);
		nwVlu = nwVlu + lNw ;
	}	
	rapVlu = rapVlu;//get2DigitNum(rapVlu);
	quotVlu = get2DigitNum(quotVlu) ;
	nwVlu = nwVlu;
	ttlCts = (new Number(ttlCts)).toFixed(2);
	document.getElementById('glbl_cts').value = ttlCts ;
	document.getElementById('glbl_rap_vlu').value = rapVlu ;
	document.getElementById('old_vlu').value = quotVlu ;
	document.getElementById('old_dis').value = get6DigitNum((quotVlu/rapVlu*100) - 100);
	document.getElementById('old_avg').value = get2DigitNum(quotVlu/ttlCts);
	document.getElementById('new_vlu').value = nwVlu ;
	document.getElementById('new_dis').value = get6DigitNum((nwVlu/rapVlu*100) - 100);
	document.getElementById('new_avg').value = get2DigitNum(nwVlu/ttlCts);
	
}
function get3DigitNum(lNum) {
	return parseFloat(parseInt(lNum*1000)/1000);//(new Number(lNum)).toFixed(2);//parseFloat(parseInt(lNum*100)/100);
}
function get2DigitNum(lNum) {
	return parseFloat(parseInt(lNum*100)/100);//(new Number(lNum)).toFixed(2);//parseFloat(parseInt(lNum*100)/100);
}
function get4DigitNum(lNum) {
	return parseFloat(parseInt(lNum*10000)/10000);//(new Number(lNum)).toFixed(2);//parseFloat(parseInt(lNum*100)/100);
}
function get5DigitNum(lNum) {
	return parseFloat(parseInt(lNum*100000)/100000);//(new Number(lNum)).toFixed(2);//parseFloat(parseInt(lNum*100)/100);
}
function get6DigitNum(lNum) {
	return parseFloat(parseInt(lNum*1000000)/1000000);//(new Number(lNum)).toFixed(2);//parseFloat(parseInt(lNum*100)/100);
}
function get2DigitRnd(lNum) {
	return parseInt(lNum);
}

function calculation(obj){
        var rapVlu = 0 ;
	var quotVlu = 0 ;
	var nwVlu = 0 ;
	var ttlCts = 0 ;
        var ttlqty = 0;
        var exhRte = document.getElementById('exhRte').value;
       if(exhRte=='')
       exhRte=1;
   var frm_elements = document.forms[1].elements; 
   for(i=0; i<frm_elements.length; i++) {
    var field_type = frm_elements[i].type.toLowerCase();
     if(field_type=='radio') {
            var fieldId = frm_elements[i].id;
          
            if(fieldId.indexOf(obj)!=-1){
            if(frm_elements[i].checked){
             var val = fieldId.split("_");
                var count = val[1];
              
                var lCts = parseFloat(document.getElementById(count+'_cts').value);
		lCts = lCts;
		ttlCts = ttlCts + lCts ;
		//lCts =  lCts.toDecimals(2); //Math.Floor(parseFloat(lCts)*100)/100;
		//alert(lCts);
		var lRap = document.getElementById(count+'_rap').value*lCts; //parseInt(document.getElementById(i+'_rap').value);
		var lQuot = document.getElementById(count+'_quot').value*lCts/exhRte; 
		var lNw = document.getElementById(count+'_fnl').value*lCts/exhRte; 
            
               
		rapVlu = rapVlu + lRap ; //(lRap*lCts);
		quotVlu = quotVlu + lQuot ; //(lQuot*lCts);
		nwVlu = nwVlu + lNw ;
                ttlqty = ttlqty +1 ;
                
           
            }
            
            }
     }}
      document.getElementById('qty').innerHTML = ttlqty;
     document.getElementById('cts').innerHTML = get2DigitNum(ttlCts);
     document.getElementById('vlu').innerHTML = get2DigitNum(nwVlu);
     if(nwVlu==0){
     document.getElementById('avgdis').innerHTML='0'
     document.getElementById('avgprc').innerHTML='0'
     }else{
     document.getElementById('avgdis').innerHTML =  get2DigitNum((nwVlu/rapVlu*100) - 100);//parseFloat(parseInt(((quotVlu/rapVlu*100) - 100)*100)/100);
     document.getElementById('avgprc').innerHTML = get2DigitNum(nwVlu/ttlCts);
     }
}

function CalculateDiff(stkIdn , obj){
     var ofrDis = obj.value;
      var amtID = "DAMT_"+stkIdn;
      var hidAmtId = "amt_"+stkIdn;
     if(ofrDis!=''){
    
    
     var amt = document.getElementById(hidAmtId).value;
     if(amt=='')
     amt=0;
     var qout =  document.getElementById("qout_"+stkIdn).value;
     if(qout=='')
     qout=0;
     var rap1=document.getElementById("rap_"+stkIdn).value;
    var rate = ( rap1 * (100+parseFloat(ofrDis))/100);
    document.getElementById(amtID).value = parseFloat(amt) + (get2DigitRnd(qout - rate)*-1);
     }else{
         document.getElementById(amtID).value = "";
     }
}

function CalculateRapDis(stkIdn ){

var upr = document.getElementById("upr_"+stkIdn).value;
var rapRte = document.getElementById("rap_"+stkIdn).value;
var uprDisFld = "uprDis_"+stkIdn;
var uprDis= ((((parseFloat(upr)/parseFloat(rapRte))*100) -100));
document.getElementById(uprDisFld).value = get2DigitRnd(uprDis);
}


function CalculateRapRte(stkIdn){
var uprDis = document.getElementById("uprDis_"+stkIdn).value;
var rapRte = document.getElementById("rap_"+stkIdn).value;
var uprFld = "upr_"+stkIdn;
var upr = ((100 + parseFloat(uprDis))/100)*parseFloat(rapRte);
document.getElementById(uprFld).value = upr;
}

function CalculateRte(stkIdn){
var uprDis = document.getElementById("uprDis_"+stkIdn).value;
var rapRte = document.getElementById("rap_"+stkIdn).value;
var uprFld = "upr_"+stkIdn;
var upr = ( rapRte * (100+parseFloat(uprDis))/100);
document.getElementById(uprFld).value = upr;
}


function trfToMktRapDis(stkIdn){
     var upr =  document.getElementById("upr_"+stkIdn).value;
     var rapRte =  document.getElementById("rap_"+stkIdn).value;
     var uprDisFld = "uprDis_"+stkIdn;
     var  uprDis= ((((parseFloat(upr)/parseFloat(rapRte))*100) -100));
     if(rapRte=='1' || rapRte=="" )
     uprDis="";
     if(uprDis!=""){
     if(parseInt(get2DigitRnd(uprDis)) < -100 || parseInt(get2DigitRnd(uprDis)) > 100){
     alert("Please Verify Discount it can't be out of -100 and 100");
     document.getElementById("upr_"+stkIdn).value='';
     document.getElementById("upr_"+stkIdn).focus();
     }else{
     document.getElementById(uprDisFld).value = get2DigitNum(uprDis);
      var mnlOld=document.getElementById("mnlOld_"+stkIdn).value;
     if(mnlOld!='' && upr!='')
     document.getElementById('DAMT_'+stkIdn).value=get2DigitRnd(mnlOld-upr);
     }
     }
}

function trfToMktRapRte(stkIdn){
     var uprDis =  document.getElementById("uprDis_"+stkIdn).value;
     if(uprDis!=""){
     if(parseInt(uprDis) < -100 || parseInt(uprDis) > 100){
     alert("Please Verify Discount it can't be out of -100 and 100");
     document.getElementById("uprDis_"+stkIdn).value='';
     document.getElementById("uprDis_"+stkIdn).focus();
     }else{
     var rapRte =  document.getElementById("rap_"+stkIdn).value;
      var uprFld = "upr_"+stkIdn;
     var upr = parseFloat(rapRte)*(100 + parseFloat(uprDis))/100;
     document.getElementById(uprFld).value = get2DigitRnd(upr);
      var mnlOld=document.getElementById("mnlOld_"+stkIdn).value;
     if(mnlOld!='' && upr!='')
     document.getElementById('DAMT_'+stkIdn).value=get2DigitRnd(mnlOld-upr);
     }
     }
}

function trfToMktRapRtePri(stkIdn){
     var uprDis =  document.getElementById("uprDis_"+stkIdn).value;
     if(uprDis!=""){
     if(parseInt(uprDis) < -100 || parseInt(uprDis) > 100){
     alert("Please Verify Discount it can't be out of -100 and 100");
     document.getElementById("uprDis_"+stkIdn).value='';
     document.getElementById("uprDis_"+stkIdn).focus();
     }else{
     var rapRte =  document.getElementById("rap_"+stkIdn).value;
      var uprFld = "upr_"+stkIdn;
     var upr = parseFloat(rapRte)*(100 - parseFloat(uprDis))/100;
     document.getElementById(uprFld).value = get2DigitRnd(upr);
      var mnlOld=document.getElementById("mnlOld_"+stkIdn).value;
     if(mnlOld!='' && upr!='')
     document.getElementById('DAMT_'+stkIdn).value=get2DigitRnd(mnlOld-upr);
     }
     }
}
function CalculateDiffSH(stkIdn , obj){

 var ofrDis = obj.value;
 var mnlID = "MNL_"+stkIdn;
 var rap1=document.getElementById("rap_"+stkIdn).value;
 var rate = ( rap1 * (100+parseFloat(ofrDis))/100);
 document.getElementById(mnlID).value = get2DigitRnd(rate);

}

function CalculateUPRDiffSH(stkIdn , obj){

 var upr = obj.value;
 var mnlID = "modDis_"+stkIdn;
 var rap1=document.getElementById("rap_"+stkIdn).value;
 var uprDis = ((((parseFloat(upr)/parseFloat(rap1))*100) -100));
 document.getElementById(mnlID).value = get2DigitRnd(uprDis);

}

function getviceversaRTEDIS(lprp,getrtnlprp,grp){
var current=parseInt(document.getElementById(lprp+"_"+grp).value);
var rap=document.getElementById("rap_rte").value;
var rateDis=0;
if(parseFloat(current)<0){
  rateDis = (parseFloat(rap) * (100+parseFloat(current))/100);
}else{
 rateDis = ((((parseFloat(current)/parseFloat(rap))*100) -100));
}
 document.getElementById(getrtnlprp+"_"+grp).value = get2DigitRnd(rateDis);
}

function getviceversaRTEDISpricingissue(lprp,getrtnlprp){
var current=parseInt(document.getElementById(lprp).value);
var rap=document.getElementById("rap_rte").value;
var rateDis=0;
if(parseFloat(current)<0){
  rateDis = (parseFloat(rap) * (100+parseFloat(current))/100);
}else{
 rateDis = ((((parseFloat(current)/parseFloat(rap))*100) -100));
}
document.getElementById(getrtnlprp).value = get2DigitRnd(rateDis);
updatePrp(document.getElementById(getrtnlprp),getrtnlprp ,'N');
}

function ApplyNewRap(){
    var perVal = document.getElementById('perVal').value;
    var rows = document.getElementById('count').value;
    if(perVal!=''){
        for(var i=1;i<=rows;i++){
            var chkId = "cb_rap_"+i;
            var idn = document.getElementById(chkId).value;
            var diffVal  = document.getElementById('DIFF_'+idn).value;
            var newDiff = get2DigitNum(parseFloat(diffVal)*(parseInt(perVal)/100));
            document.getElementById('NEWDIFF_'+idn).value = newDiff;
        }
    }
}

function verifyPrice(obj){

var updPrice = document.getElementById('UpdPrice').value;

if(updPrice==''){
alert("Please enter the value for Price or Rap Discount");
}
if(updPrice !=''){
updPricings(obj);
}
}
function updPricings(obj){
var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
field_type = frm_elements[i].type.toLowerCase();
if(field_type=='hidden') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('stk_idn_')!=-1){
var stkIdn = document.getElementById(fieldId).value;
CommPriceChanges(stkIdn,obj);
}}
}
}

function CommPriceChanges(stkIdn,obj){
var upPrIndex = document.getElementById('updPriceSel').value;
var updPrice = document.getElementById('UpdPrice').value;
var pktUsrPrFlds = "uprf_"+stkIdn;
var uprFld ="upr_"+stkIdn;
var uprDisFld="uprDis_"+stkIdn;
var rapRteFldId= document.getElementById("rap_"+stkIdn).value;
var pktUsrPrFld = document.getElementById(pktUsrPrFlds).value;
var pktuprDisFlds ="uprDisc_"+stkIdn;
var oldUprDisFld = document.getElementById(pktuprDisFlds).value;
if(upPrIndex=='Price'){
var uprDis= ((((parseFloat(updPrice)/parseFloat(rapRteFldId))*100) -100));
var calUpr= ((100 + parseFloat(uprDis))/100)*parseFloat(rapRteFldId);
document.getElementById(uprDisFld).value = get2DigitNum(uprDis);
document.getElementById(uprFld).value= get2DigitNum(calUpr);
}
else if(upPrIndex=='RapDis'){
var NewPriceDis= (parseFloat(oldUprDisFld) - parseFloat(updPrice));
var upr = ((100 + parseFloat(NewPriceDis))/100)*parseFloat(rapRteFldId);
document.getElementById(uprFld).value = get2DigitNum(upr);
document.getElementById(uprDisFld).value = get2DigitNum(NewPriceDis);
}
}

function calculationNew(obj){

var stkIdn = '';
var memoIdn = '';

var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
var field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio') {
var fieldId = frm_elements[i].id;

if(fieldId.indexOf(obj)!=-1){
if(frm_elements[i].checked){

var val = fieldId.split("_");
var count = val[1];

var lstkIdn = parseFloat(document.getElementById(count+'_vnm').value);
var lmemoIdn = parseFloat(document.getElementById(count+'_memoid').value);

stkIdn = stkIdn+","+lstkIdn;
memoIdn = memoIdn+","+lmemoIdn;

}
}
}
}

var url = "ajaxPrcAction.do?method=priceCalc&stkIdn="+stkIdn+"&memoIdn="+memoIdn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesCalculationNew(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}

function parseMessagesCalculationNew(responseXML){

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
SetCalculationOnFnlExrt();
}
function calculationMemoSaleSingle(stt,typ,obj){
calculationMemoSaleAll(stt,typ,obj);
}
function calculationMemoSaleAll(stt,typ,obj){
var stkIdn = '';
var memoIdn = document.getElementById('noofmemoid').value;
if(typ=='S'){
var split=obj.split('_');
var count=split[1]
stkIdn = document.getElementById(count+'_vnm').value;
}
var url = "ajaxPrcAction.do?method=memosale&stkIdn="+stkIdn+"&memoIdn="+escape(memoIdn)+"&typ="+typ+"&stt="+stt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesCalculationNew(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescalculationMemoSale(responseXML){

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

function calculationConsignmentNew(obj){

var stkIdn = '';
var memoIdn = '';

var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
var field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio') {
var fieldId = frm_elements[i].id;

if(fieldId.indexOf(obj)!=-1){
if(frm_elements[i].checked){

var val = fieldId.split("_");
var count = val[1];

var lstkIdn = parseFloat(document.getElementById(count+'_vnm').value);
var lmemoIdn = parseFloat(document.getElementById(count+'_memoid').value);

stkIdn = stkIdn+","+lstkIdn;
memoIdn = memoIdn+","+lmemoIdn;

}
}
}
}

var url = "ajaxPrcAction.do?method=priceCalcConsignment&stkIdn="+stkIdn+"&memoIdn="+memoIdn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesCalculationConsignmentNew(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}
function parseMessagesCalculationConsignmentNew(responseXML){

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
if(document.getElementById('net_dis')!=null){
document.getElementById('net_dis').innerHTML = Vlu;
document.getElementById('vluamt').value = Vlu;
}
}
}
function calculationNewSaleByBIll(obj){

var stkIdn = '';
var memoIdn = '';

var frm_elements = document.forms[1].elements;
for(i=0; i<frm_elements.length; i++) {
var field_type = frm_elements[i].type.toLowerCase();
if(field_type=='radio') {
var fieldId = frm_elements[i].id;

if(fieldId.indexOf(obj)!=-1){
if(frm_elements[i].checked){

var val = fieldId.split("_");
var count = val[1];
var nmeidn = val[2];
var lstkIdn = parseFloat(document.getElementById(count+'_'+nmeidn+'_vnm').value);
var lmemoIdn = parseFloat(document.getElementById(count+'_'+nmeidn+'_memoid').value);

stkIdn = stkIdn+","+lstkIdn;
memoIdn = memoIdn+","+lmemoIdn;

}
}
}
}

var url = "saleByBilling.do?method=priceCalcSaleByBill&stkIdn="+stkIdn+"&memoIdn="+memoIdn;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescalculationNewSaleByBIll(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);

}


function parseMessagescalculationNewSaleByBIll(responseXML){

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




function calculationMemoSaleSingleSH(stt,typ,obj){
calculationMemoSaleAll(stt,typ,obj);
}
function calculationMemoSaleAllSH(stt,typ,obj){
var stkIdn = '';
var memoIdn = document.getElementById('noofmemoid').value;
if(typ=='S'){
var split=obj.split('_');
var count=split[1]
stkIdn = document.getElementById(count+'_vnm').value;
}
var url = "ajaxPrcAction.do?method=memosaleSH&stkIdn="+stkIdn+"&memoIdn="+escape(memoIdn)+"&typ="+typ+"&stt="+stt;
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesCalculationNewSH(req.responseXML);
} else if (req.status == 204){

}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesCalculationNewSH(responseXML){

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

function TRFPktCalculate(){
var isVerify=true;
 var ttlQty = parseInt(document.getElementById('CUR_QTY').innerHTML);
  var ttlCts = parseFloat(document.getElementById('CUR_CTS').innerHTML);
  var rtRte = parseFloat(document.getElementById('CUR_RTE').innerHTML);
 var ttltrfQty=0;
 var ttltrfCts = 0;
 var cnt=true;
 var rnmCts =ttlCts;
  var frm_elements = document.forms[1].elements;
  for(i=0; i<frm_elements.length; i++) {
  var field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='checkbox') {
  var fieldId = frm_elements[i].id;
 if(fieldId.indexOf('CHK_')!=-1){
  if(frm_elements[i].checked){
  cnt=false;
   var stkIdn =frm_elements[i].value;
   var curQty = parseInt(nvl(document.getElementById('CUR_QTY_'+stkIdn).innerHTML,0));
    var trfQty = parseInt(nvl(document.getElementById('TRF_QTY_'+stkIdn).value,0));
    var fnlQty = curQty + trfQty
    document.getElementById('FNL_QTY_'+stkIdn).value = fnlQty;
    ttltrfQty = ttltrfQty + trfQty;
    
    var curCts = parseFloat(nvl(document.getElementById('CUR_CTS_'+stkIdn).innerHTML,0));
    var trfCts = parseFloat(nvl(document.getElementById('TRF_CTS_'+stkIdn).value,0));
    var fnlCts = curCts + trfCts;
    document.getElementById('FNL_CTS_'+stkIdn).value = get2DigitNum(fnlCts);
    ttltrfCts = ttltrfCts + trfCts;
    
    var curRte = parseFloat(nvl(document.getElementById('CUR_RTE_'+stkIdn).innerHTML,0));
    var trfRte = parseFloat(nvl(document.getElementById('TRF_RTE_'+stkIdn).value,0));
    var trfVal =  trfCts * trfRte;
    var curVal = curCts * curRte;
    var fnlRte =(trfVal+curVal)/(trfCts+curCts);
    
    document.getElementById('FNL_RTE_'+stkIdn).value =Math.round(fnlRte);

    var fnlVal = rnmCts *rtRte;
     rtRte = (fnlVal - trfVal)/(rnmCts-trfCts);
     rnmCts = rnmCts - trfCts;
     if(rnmCts==0)
     rtRte=0;
  }}}
  }
   if(ttltrfQty > ttlQty){
    alert("Stones Can't Transfer \n Total Transfer Qty :"+ttltrfQty+" \n Exiting Qty :"+ttlQty);
    isVerify=false;
    }else{
   document.getElementById('FNL_QTY').value =ttlQty-ttltrfQty;
  }
  if(ttltrfCts > ttlCts){
    alert("Stones Can't Transfer \n Total Transfer Carat :"+ttltrfCts+" \n Exiting Carat :"+ttlCts);
        isVerify=false;
  }else{
        document.getElementById('FNL_CTS').value =get2DigitNum(ttlCts-ttltrfCts);
        document.getElementById('FNL_RTE').value =Math.round(rtRte);

  }
  if(cnt){
    alert("please select stone for tansfer");
        isVerify=false;
    }
   if(isVerify){
      document.getElementById('TRANSBTN').style.display="block";

      
   }
  
  }
  
  
  function SingleToMixCalculate(){
 var ttlQty = parseInt(nvl(document.getElementById('CUR_QTY').innerHTML),0);
  var ttlCts = parseFloat(nvl(document.getElementById('CUR_CTS').innerHTML),0);
  var rtRte = parseFloat(nvl(document.getElementById('CUR_RTE').innerHTML),0);
  var frm_elements = document.forms[1].elements;
  for(i=0; i<frm_elements.length; i++) {
  var field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='checkbox') {
  var fieldId = frm_elements[i].id;
 if(fieldId.indexOf('CHK_')!=-1){
  if(frm_elements[i].checked){
  cnt=false;
   var stkIdn =frm_elements[i].value;
   var curQty = parseInt(nvl(document.getElementById('CUR_QTY_'+stkIdn).innerHTML,0));
    ttlQty = ttlQty + curQty;
    document.getElementById('FNL_QTY').value = ttlQty;

   var curCts = parseFloat(nvl(document.getElementById('CUR_CTS_'+stkIdn).innerHTML,0));

    
    var curRte = parseFloat(nvl(document.getElementById('CUR_RTE_'+stkIdn).innerHTML,0));
    var ttlVal =  ttlCts * rtRte;
    var curVal = curCts * curRte;
     rtRte =(ttlVal+curVal)/(ttlCts+curCts);
     ttlCts = ttlCts + curCts;

    document.getElementById('FNL_CTS').value = ttlCts;
    document.getElementById('FNL_RTE').value = Math.round(rtRte);

   
  }}}
  }

  }

  function verifyCts(obj){
     var trfCts=0;
     var frm_elements = document.forms[1].elements;
  for(i=0; i<frm_elements.length; i++) {
  var field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='checkbox') {
  var fieldId = frm_elements[i].id;
 if(fieldId.indexOf('CHK_')!=-1){
  if(frm_elements[i].checked){
    var stkIdn = frm_elements[i].value;
     var curCts = parseFloat(nvl(document.getElementById('TRF_CTS_'+stkIdn).value,0));
     trfCts = trfCts + curCts;
  }
 }}}
 var adjCts = parseFloat(nvl(obj.value,'0'));
 var fnlCts = parseFloat(nvl(document.getElementById('FNL_CTS').value,"0"));
 trfCts = trfCts+0.10
 rtCts = adjCts+ fnlCts
 alert(rtCts);
 alert(trfCts);
 if(rtCts >trfCts ){
   obj.value="";
   alert("Carat not match.");
 }
  }
  
  function StoneTransCalculation(CHK,CHKTYP){
 var ttlQty = 0;
  var ttlCts = 0;
  var ttlVal=0;
  var frm_elements = document.forms[1].elements;
  for(i=0; i<frm_elements.length; i++) {
  var field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='checkbox') {
  var fieldId = frm_elements[i].id;
 if(fieldId.indexOf(CHK)!=-1){
 if(frm_elements[i].checked){
  var stkIdn =frm_elements[i].value;
   var curQty = parseInt(nvl(document.getElementById(CHKTYP+'_QTY_'+stkIdn).value,0));
   var curCts = parseFloat(nvl(document.getElementById(CHKTYP+'_CTS_'+stkIdn).value,0));
    var curRt = parseFloat(nvl(document.getElementById(CHKTYP+'_RTE_'+stkIdn).value,0));
    ttlQty = ttlQty + curQty;
    ttlCts = ttlCts + curCts;
    ttlVal = ttlVal + (curCts*curRt);
   var fnlRte =  document.getElementById(CHKTYP+'_FRTE_'+stkIdn);
   if(fnlRte!=null){
       var orgRte =parseFloat(nvl(document.getElementById('CUR_FRTE_'+stkIdn).innerHTML,0));
        var orgCts =parseFloat(nvl(document.getElementById('CUR_FCTS_'+stkIdn).value,0));
       
        var diff= orgCts-curCts;
        if(diff>0){
       var fnlTrfRte = (((orgCts*orgRte)-(curCts*curRt))/(orgCts-curCts));
       document.getElementById(CHKTYP+'_FRTE_'+stkIdn).value = Math.round(fnlTrfRte);
       }else{
        document.getElementById(CHKTYP+'_FRTE_'+stkIdn).value = orgRte;
       }
       
          }
    
     }
   }}
  }
  var avgRte =ttlVal/ttlCts;
   document.getElementById(CHKTYP+'_QTY_LBL').innerHTML = ttlQty;
    document.getElementById(CHKTYP+'_CTS_LBL').innerHTML = (new Number(ttlCts)).toFixed(3);
     document.getElementById(CHKTYP+'_RTE_LBL').innerHTML = Math.round(avgRte);
  }
function nvl(val,rplVal){
  var rtnVal = val
  if(val=='')
    rtnVal=rplVal;
    
    return rtnVal;
      
  }
  function SetCalculationOnFnlExrt(){
    var curExh = document.getElementById('exhRte').value;
    var fnlexhRte = document.getElementById('fnlexhRte').value;
    if(fnlexhRte!=''){
    var curVlu = document.getElementById('vluamt').value;
  if(curVlu!='' && curVlu!='0') {
    var curCts = document.getElementById('cts').innerHTML;
    
    fnlexhRte = parseFloat(fnlexhRte);
    curVlu = parseFloat(curVlu);
    curExh = parseFloat(curExh);
    curCts = parseFloat(curCts);
     
    var calVal = get2DigitNum((curVlu/curExh)*fnlexhRte);
    var avgPri = get2DigitNum(calVal/curCts);
    document.getElementById('vlu').innerHTML=calVal;
      document.getElementById('avgprc').innerHTML=avgPri;
      document.getElementById('net_dis').innerHTML=calVal
  }
    }
}
  function addAdjVal(typ){
    var adjVal = parseFloat(nvl(document.getElementById('TRF_'+typ).value,"0"));
    var fnlVal = parseFloat(nvl(document.getElementById('FNL_'+typ).value,"0"));
      var fnlCal = adjVal+fnlVal;
   document.getElementById('FNL_'+typ).value = fnlCal;
  }
  
  function ApplyTranAmt(){
  var isChecked=false;
   var frm_elements = document.getElementsByTagName('input');
      for(i=0; i<frm_elements.length; i++) {
        var field_type = frm_elements[i].type.toLowerCase();
          if(field_type=='radio') {
           var fieldId = frm_elements[i].id;
           if(fieldId.indexOf('CHKR_')!=-1){
            if(frm_elements[i].checked){
             var stkIdn = frm_elements[i].value;
            isChecked=true;
            var curQty = parseInt(nvl(document.getElementById('CUR_TQTY_'+stkIdn).innerHTML,"0"));
            var lblQty = parseInt(document.getElementById('QTY_LBL').innerHTML);
            var lblCts = parseFloat(document.getElementById('CTS_LBL').innerHTML);
            var CurCts= parseFloat(nvl(document.getElementById('CUR_TCTS_'+stkIdn).innerHTML,"0"));
            var lblRte = parseFloat(nvl(document.getElementById('RTE_LBL').innerHTML,"0"));
            var CurRte =parseFloat(nvl(document.getElementById('CUR_TRTE_'+stkIdn).innerHTML,"0"));
             var fnlCts = (CurCts+lblCts);
            rtRte =(((lblCts* lblRte) + (CurCts*CurRte))/(CurCts+lblCts));
            
            document.getElementById('FNL_QTY_'+stkIdn).value=curQty+lblQty;
           document.getElementById('FNL_CTS_'+stkIdn).value=get2DigitNum(fnlCts);
           document.getElementById('FNL_RTE_'+stkIdn).value=Math.round(rtRte);
           break;
           }
         }
      }
  }
  if(!isChecked){
  alert("Please Select Packet To Transfer from Rightside Table!!");
  }
  }
  
  function VerifiedTransStoneAmt(obj){
    obj.disable=true;
  var trfCts = parseFloat(document.getElementById('TRN_CTS_LBL').innerHTML);
   var addCts = parseFloat(document.getElementById('ADD_CTS_LBL').innerHTML);
   
    var trfQty = parseFloat(document.getElementById('TRN_QTY_LBL').innerHTML);
   var addQty = parseFloat(document.getElementById('ADD_QTY_LBL').innerHTML);
  
   if(trfCts > 0 && addCts>0){
   if(addCts!=trfCts){
        alert("Please Verify carat of transfer stone");
        obj.disable=false;
        return false;
    }else if(trfQty!=addQty){
      alert("Please Verify Qty of transfer stone");
        obj.disable=false;
        return false;
    }else{
    var r = confirm("Are You Sure You Want To Save Changes?");
    if(r)
    loading();
    else
     obj.disable=false;
    return r;
    }
        
     }else{
        alert("Please Select Stones for transfer!!");
        obj.disable=false;
        return false;
     }
    
               
}
  function calNewPkt(){
  var qty = parseInt(document.getElementById('FNL_QTY_NEW').value);
   var cts = parseFloat(document.getElementById('FNL_CTS_NEW').value);
  document.getElementById('ADD_QTY_LBL').innerHTML=qty;
  document.getElementById('ADD_CTS_LBL').innerHTML=cts;
  }
 
  function ApplyCurrVal(obj,fldId,CKTYP,CHK){

  if(fldId=='ALL'){
   
     var ischecked  = obj.checked;
     var frm_elements = document.getElementsByTagName('input');
      for(i=0; i<frm_elements.length; i++) {
        var field_type = frm_elements[i].type.toLowerCase();
          if(field_type=='checkbox') {
          var fieldId = frm_elements[i].id;
        
           if(fieldId.indexOf(CHK)!=-1){
         
           document.getElementById(fieldId).checked = ischecked;
           var stkIdn = frm_elements[i].value;
           
           if(ischecked){
            document.getElementById(CKTYP+'_QTY_'+stkIdn).disabled=false;
            document.getElementById(CKTYP+'_CTS_'+stkIdn).disabled=false;
            document.getElementById(CKTYP+'_RTE_'+stkIdn).disabled=false;
            if(CKTYP=='TRN'){
            document.getElementById(CKTYP+'_FQTY_'+stkIdn).disabled=false;
            document.getElementById(CKTYP+'_FCTS_'+stkIdn).disabled=false;
            var frte =document.getElementById(CKTYP+'_FRTE_'+stkIdn);
            if(frte!=null){
             document.getElementById(CKTYP+'_FRTE_'+stkIdn).disabled=false;
            }
            
            }else{
            document.getElementById(CKTYP+'_TQTY_'+stkIdn).disabled=false;
            document.getElementById(CKTYP+'_TCTS_'+stkIdn).disabled=false;
            }
           
           }else{
            document.getElementById(CKTYP+'_QTY_'+stkIdn).value="";
            document.getElementById(CKTYP+'_CTS_'+stkIdn).value="";
            document.getElementById(CKTYP+'_RTE_'+stkIdn).value="";
           if(CKTYP=='TRN'){
             document.getElementById(CKTYP+'_FQTY_'+stkIdn).value="";
            document.getElementById(CKTYP+'_FCTS_'+stkIdn).value="";
            }else{
             document.getElementById(CKTYP+'_FQTY_'+stkIdn).value="";
            document.getElementById(CKTYP+'_FCTS_'+stkIdn).value="";
            }
            document.getElementById(CKTYP+'_QTY_'+stkIdn).disabled=true;
            document.getElementById(CKTYP+'_CTS_'+stkIdn).disabled=true;
            document.getElementById(CKTYP+'_RTE_'+stkIdn).disabled=true; 
             if(CKTYP=='TRN'){
            document.getElementById(CKTYP+'_FQTY_'+stkIdn).disabled=true;
            document.getElementById(CKTYP+'_FCTS_'+stkIdn).disabled=true;
             var frte =document.getElementById(CKTYP+'_FRTE_'+stkIdn);
            if(frte!=null){
             document.getElementById(CKTYP+'_FRTE_'+stkIdn).disabled=true;
            }
            }else{
            document.getElementById(CKTYP+'_TQTY_'+stkIdn).disabled=false;
            document.getElementById(CKTYP+'_TCTS_'+stkIdn).disabled=false;
            }
           }
          }
      }}
      
   }else{
     
     obj = document.getElementById(fldId);
     ischecked  = obj.checked;
     stkIdn = obj.value;
    if(ischecked){
          document.getElementById(CKTYP+'_QTY_'+stkIdn).disabled=false;
           document.getElementById(CKTYP+'_CTS_'+stkIdn).disabled=false;
           document.getElementById(CKTYP+'_RTE_'+stkIdn).disabled=false;
           if(CKTYP=='TRN'){
            document.getElementById(CKTYP+'_FQTY_'+stkIdn).disabled=false;
            document.getElementById(CKTYP+'_FCTS_'+stkIdn).disabled=false;
             var frte =document.getElementById(CKTYP+'_FRTE_'+stkIdn);
            if(frte!=null){
             document.getElementById(CKTYP+'_FRTE_'+stkIdn).disabled=false;
            }
            }else{
            document.getElementById(CKTYP+'_TQTY_'+stkIdn).disabled=false;
            document.getElementById(CKTYP+'_TCTS_'+stkIdn).disabled=false;
            }
            }else{
           document.getElementById(CKTYP+'_QTY_'+stkIdn).value="";
            document.getElementById(CKTYP+'_CTS_'+stkIdn).value="";
            document.getElementById(CKTYP+'_RTE_'+stkIdn).value="";
            document.getElementById(CKTYP+'_QTY_'+stkIdn).disabled=true;
            document.getElementById(CKTYP+'_CTS_'+stkIdn).disabled=true;
            document.getElementById(CKTYP+'_RTE_'+stkIdn).disabled=true; 
          
             if(CKTYP=='TRN'){
             document.getElementById(CKTYP+'_FQTY_'+stkIdn).value="";
            document.getElementById(CKTYP+'_FCTS_'+stkIdn).value="";
              document.getElementById(CKTYP+'_FQTY_'+stkIdn).disabled=true;
            document.getElementById(CKTYP+'_FCTS_'+stkIdn).disabled=true;
             var frte =document.getElementById(CKTYP+'_FRTE_'+stkIdn);
            if(frte!=null){
             document.getElementById(CKTYP+'_FRTE_'+stkIdn).disabled=true;
            }
            }else{
             document.getElementById(CKTYP+'_TQTY_'+stkIdn).value="";
            document.getElementById(CKTYP+'_TCTS_'+stkIdn).value="";
              document.getElementById(CKTYP+'_TQTY_'+stkIdn).disabled=true;
            document.getElementById(CKTYP+'_TCTS_'+stkIdn).disabled=true;
            }
       }
     }
  
   }
  
  function CalculateDiff(obj,stkIdn,typ){
 
  if(typ=='F'){
  var curQty =  parseInt(document.getElementById('CUR_FQTY_'+stkIdn).value);
  var curCts = parseFloat(document.getElementById('CUR_FCTS_'+stkIdn).value);
  var objId = obj.id;
  
  if(objId.indexOf('TRN_F')!=-1){
       var diffQty =  parseInt(nvl(document.getElementById('TRN_FQTY_'+stkIdn).value,0));
  var diffCts =  parseFloat(nvl(document.getElementById('TRN_FCTS_'+stkIdn).value,0));
  var calQty=curQty-diffQty;
  var calCts=curCts-diffCts;
  
  if(calQty < 0){
      alert("Please Verify Qty. it can't be more then "+curQty);
      document.getElementById('TRN_FQTY_'+stkIdn).value="";
      document.getElementById('TRN_FCTS_'+stkIdn).value="";
  }else if (calCts < 0){
        alert("Please Verify Carat it can't be more then "+curCts);
        document.getElementById('TRN_FQTY_'+stkIdn).value="";
      document.getElementById('TRN_FCTS_'+stkIdn).value="";
  }else{
  document.getElementById('TRN_QTY_'+stkIdn).value=curQty-diffQty;
  document.getElementById('TRN_CTS_'+stkIdn).value=get3DigitNum(curCts-diffCts);
  }
  }else{
  var diffQty =  parseInt(nvl(document.getElementById('TRN_QTY_'+stkIdn).value,0));
  var diffCts =  parseFloat(nvl(document.getElementById('TRN_CTS_'+stkIdn).value,0));
  if(curQty < diffQty){
       alert("Please Verify Qty. it can't be more then "+curQty);
       document.getElementById('TRN_QTY_'+stkIdn).value="";
       document.getElementById('TRN_CTS_'+stkIdn).value="";
  }else if(curCts < diffCts){
        alert("Please Verify Carat it can't be more then "+curCts);
         document.getElementById('TRN_QTY_'+stkIdn).value="";
       document.getElementById('TRN_CTS_'+stkIdn).value="";
  }else{
  document.getElementById('TRN_FQTY_'+stkIdn).value=curQty-diffQty;
  document.getElementById('TRN_FCTS_'+stkIdn).value=get3DigitNum(curCts-diffCts);
  }
  }
 }else{
   curQty =  parseInt(document.getElementById('CUR_TQTY_'+stkIdn).value);
   curCts = parseFloat(document.getElementById('CUR_TCTS_'+stkIdn).value);
     var objId = obj.id;
  if(objId.indexOf('ADD_T')!=-1){
  diffQty =  parseInt(nvl(document.getElementById('ADD_TQTY_'+stkIdn).value,0));
   diffCts =  parseFloat(nvl(document.getElementById('ADD_TCTS_'+stkIdn).value,0));
  document.getElementById('ADD_QTY_'+stkIdn).value=diffQty-curQty;
  document.getElementById('ADD_CTS_'+stkIdn).value=get3DigitNum(diffCts-curCts);
  }else{
   diffQty =  parseInt(nvl(document.getElementById('ADD_QTY_'+stkIdn).value,0));
   diffCts =  parseFloat(nvl(document.getElementById('ADD_CTS_'+stkIdn).value,0));
  document.getElementById('ADD_TQTY_'+stkIdn).value=curQty+diffQty;
  document.getElementById('ADD_TCTS_'+stkIdn).value=get3DigitNum(curCts+diffCts);
  }
  }
      
  }
  
  function MixDlvCalCulation(){
  var cnt = document.getElementById('rdCount').value;
   for(var j=1;j<=10;j++){
   document.getElementById("CRTWT_"+j).value="";
   document.getElementById("RTE_"+j).value="";
   }
  for(var i=1;i<=cnt;i++){
  var isDlv = document.getElementById("DLV_"+i).checked;

  if(isDlv){
       var sr = document.getElementById(i+"_SR").value;
 
     if(sr!=''){
     var ttlCtsSr = parseFloat(nvl(document.getElementById("CRTWT_"+sr).value,0));

     var ttlavgSr = parseFloat(nvl(document.getElementById("RTE_"+sr).value,0));
     var ttlvalsr = ttlCtsSr*ttlavgSr;  
      var curcts = parseFloat(nvl(document.getElementById(i+"_cts").value,0));
      var currte = parseFloat(nvl(document.getElementById(i+"_quot").value,0));
      var curVal = curcts*currte;
      
      ttlavgSr =(ttlvalsr+curVal)/(ttlCtsSr+curcts);
      ttlCtsSr =ttlCtsSr+curcts;
      document.getElementById("CRTWT_"+sr).value=get2DigitNum(ttlCtsSr);
      document.getElementById("RTE_"+sr).value=Math.round(ttlavgSr);
       }
  }
  }
        
}

function ApplyCalcultion(stkIdn){
    var ttlQty = document.getElementById('TRN_QTY_LBL').innerHTML;
    var ttlCts = document.getElementById('TRN_CTS_LBL').innerHTML;
     var ttlRte = document.getElementById('TRN_RTE_LBL').innerHTML;
    if(stkIdn==''){
     document.getElementById('FNL_QTY_NEW').value=ttlQty;
     document.getElementById('FNL_CTS_NEW').value=ttlCts;
     document.getElementById('FNL_RTE_NEW').value=ttlRte;
     document.getElementById('CRTWT').value=ttlCts;
     calNewPkt();
     }else{
    document.getElementById('ADD_QTY_'+stkIdn).value=ttlQty;
    document.getElementById('ADD_CTS_'+stkIdn).value=ttlCts;
     
     var cts = parseFloat(nvl(document.getElementById('CUR_TCTS_'+stkIdn).value,0));
     var rte = parseFloat(nvl(document.getElementById('CUR_TRTE_'+stkIdn).innerHTML,0));
     var vlu =cts*rte;
     var ttlVlu = (parseFloat(ttlCts)*parseFloat(ttlRte))
     ttlRte=(ttlVlu+vlu)/(parseFloat(ttlCts)+cts);
    
     document.getElementById('ADD_RTE_'+stkIdn).value=Math.round(ttlRte);
    var obj=document.getElementById('ADD_CTS_'+stkIdn);
    CalculateDiff(obj,stkIdn,'T');
    StoneTransCalculation('CHKR_','ADD');
    }
    
}
function setAllPktUpdatePriceBy(obj){
var updateBy = document.getElementById(obj).value;
var pktDtl = document.getElementById('pktDtl').value;
var dataArray = pktDtl .split(",");
if(updateBy!=''){
for(var i=0; i<dataArray.length; i++){
var stkIdn = dataArray[i];
document.getElementById("typ_"+stkIdn).value=updateBy ;
PriceChangeMemo(stkIdn);
}
}
}

function getPriceInfo(stkIdn){
   var tdId = "TDOffer_"+stkIdn;
   var reqUrl = document.getElementById('reqUrl').value;
   var isDisplay = document.getElementById(tdId).style.display ;
   if(isDisplay=='none'){
    var url = reqUrl+"/pri/ajaxPrcAction.do?method=getPriceInfo&stkIdn="+stkIdn ;
    var divId = "BYR_"+stkIdn;
    document.getElementById(tdId).style.display = '';
    document.getElementById(divId).innerHTML = "<img src=\"../images/load.gif\" align=\"middle\" border=\"0\" />";
    var req = initRequest();
          req.onreadystatechange = function() {
            if (req.readyState == 4) {
              if (req.status == 200) {
                parseMessagegetPriceInfo(req.responseXML , stkIdn );
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

function parseMessagegetPriceInfo(responseXML , stkIdn ){
var divId = "BYR_"+stkIdn;
var tdId = "TDOffer_"+stkIdn;
var prcLists = responseXML.getElementsByTagName("prcDtl")[0];
var prcList = prcLists.childNodes[0];
var nme = unescape((prcList.getElementsByTagName('nme')[0]).childNodes[0].nodeValue);
var iss_dte = (prcList.getElementsByTagName('iss_dte')[0]).childNodes[0].nodeValue;
var rtn_dte = (prcList.getElementsByTagName('rtn_dte')[0]).childNodes[0].nodeValue;
var mprp = (prcList.getElementsByTagName('mprp')[0]).childNodes[0].nodeValue;
var rtnrte = (prcList.getElementsByTagName('rtnrte')[0]).childNodes[0].nodeValue;
//End -- changes for Memo typ display
var str = "<div align=\"left\">";

if(nme!=0)
str = str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Employee Name: <b>"+nme+"</b> ";
if(rtn_dte!=0)
str = str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Date: <b>"+rtn_dte+"</b> ";

if(rtnrte!='NA')
str = str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Price: <b>"+rtnrte+"</b> ";

str = str+"</div>";
document.getElementById(divId).innerHTML=str;
document.getElementById(tdId).style.display = '';
}