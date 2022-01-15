function autoSearchData(lprp,objId,selectStt,setVal="key"){
      $('.magicsearch-wrapper').addClass('focus'); 
   var objVal = document.getElementById(objId).value;
  
   if(objVal!=''){
      var url = "ajaxsrchAction.do?method=activeSearch&lprp="+lprp;
       var req = initRequest();
       req.onreadystatechange = function() {    
       if (req.readyState == 4) {
                   if (req.status == 200) {
                  
               parseMessagesautoSearchData(req.responseXML,objId,selectStt,setVal);
                
                   } else if (req.status == 204){
                
                   }
               }
           };
           req.open("GET", url, true);
           req.send(null);
   }else{
         $('#'+objId).focus();
   }
  
    }
    
 function parseMessagesautoSearchData(responseXML,objId,selectStt,setVal){
   
  var columns = responseXML.getElementsByTagName("columns")[0];
  var test=new Array();
  for (var loop = 0; loop < columns.childNodes.length; loop++) {
           var columnroot = columns.childNodes[loop];
            var lkey = columnroot.getElementsByTagName("key")[0];
             test[loop]=xmlToJson(columnroot);
              
       }
    
  displayautoSearchData(test,objId,selectStt,setVal)
  
  }

function displayautoSearchData(displayArray,objId,selectStt,setVal){  

            $('#'+objId).magicsearch({
                dataSource: displayArray,
                fields: ['print'],
                id: setVal,
                format: '%print%',
                multiple: selectStt,
                multiField: 'print',
                maxShow:300,
                multiStyle: {
                    space: 5,
                    width: 80
                    
                }
            })

      $('#'+objId).focus();
}
          


function xmlToJson(xml) {

	// Create the return object
	var obj = {};

	if (xml.nodeType == 1) { // element
		// do attributes
		if (xml.attributes.length > 0) {
		obj["@attributes"] = {};
			for (var j = 0; j < xml.attributes.length; j++) {
				var attribute = xml.attributes.item(j);
                                alert(attribute.nodeValue);
                                  alert("unescape"+unescape(attribute.nodeValue));
				obj["@attributes"][attribute.nodeName] = unescape(attribute.nodeValue);
			}
		}
	} else if (xml.nodeType == 3) { // text
		obj = decodeURIComponent(xml.nodeValue);
	}

	// do children
	// If just one text node inside
	if (xml.hasChildNodes() && xml.childNodes.length === 1 && xml.childNodes[0].nodeType === 3) {
		obj =decodeURIComponent(xml.childNodes[0].nodeValue);
	}
	else if (xml.hasChildNodes()) {
		for(var i = 0; i < xml.childNodes.length; i++) {
			var item = xml.childNodes.item(i);
			var nodeName = item.nodeName;
			if (typeof(obj[nodeName]) == "undefined") {
                       
				obj[nodeName] = xmlToJson(item);
			} else {
				if (typeof(obj[nodeName].push) == "undefined") {
					var old = obj[nodeName];
					obj[nodeName] = [];
					obj[nodeName].push(old);
				}
				obj[nodeName].push(xmlToJson(item));
			}
		}
	}
        return obj;
}