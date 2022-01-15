function GenSel(pBox, pIdn) {
     var stt = pBox.checked;
     if(pIdn=='ALL'){
        var frm_elements = document.forms['1'].elements;

for(i=0; i<frm_elements.length; i++) {

field_type = frm_elements[i].type.toLowerCase();

if(field_type=='checkbox') {
var fieldId = frm_elements[i].id;
if(fieldId.indexOf('CHK_')!=-1){
document.getElementById(fieldId).checked =stt;
}
}
}}
     var lstNme = document.getElementById('lstNme').value;
     var url = "ajaxLabAction.do?method=GtList&stt="+stt+"&stkIdn="+pIdn+"&lstNme="+lstNme;  
           var req = initRequest();
           req.onreadystatechange = function() {    
               if (req.readyState == 4) {
                   if (req.status == 200) {
                   
                      parseMessagesGenTtls(req.responseXML)
                   } else if (req.status == 204){
                      
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
}


function parseMessagesGenTtls(responseXML) {
//alert('@parseMessage '+ fld);


         var totals = responseXML.getElementsByTagName("values")[0];
         var columnroot = totals.childNodes[0];
         var qty = columnroot.getElementsByTagName("qty")[0];
         var cts = columnroot.getElementsByTagName("cts")[0];
         var base = columnroot.getElementsByTagName("base")[0];
         var vlu = columnroot.getElementsByTagName("vlu")[0];
         var rap = columnroot.getElementsByTagName("rapvlu")[0];
       
    
        document.getElementById("qtyTtl").innerHTML = qty.childNodes[0].nodeValue;
        document.getElementById("ctsTtl").innerHTML = cts.childNodes[0].nodeValue;
        document.getElementById("avgTtl").innerHTML = base.childNodes[0].nodeValue;
        document.getElementById("vluTtl").innerHTML = vlu.childNodes[0].nodeValue;
        document.getElementById("rapvluTtl").innerHTML = rap.childNodes[0].nodeValue;
      
}

