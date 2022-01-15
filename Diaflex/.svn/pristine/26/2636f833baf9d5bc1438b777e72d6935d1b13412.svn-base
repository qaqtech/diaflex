 function initRequest() {
       if (window.XMLHttpRequest) {
           return new XMLHttpRequest();
       } else if (window.ActiveXObject) {
           isIE = true;
           return new ActiveXObject("Microsoft.XMLHTTP");
       }
   }

    function showPktWise(chk_id) {
      //alert("showPktWise");
      var splitstr = chk_id.split("_");
      
      var typename = splitstr[0];
      var memono = splitstr[1];
      var dept = splitstr[2];
      var subdept = splitstr[3];
      //alert("typename"+typename);
      //alert("memono"+memono);
      //alert("dept"+dept);
      //alert("subdept"+subdept);
    
        var url = "Verification.do?method=pktWise&type="+typename.toUpperCase()
        +"&memono="+memono+"&dept="+dept.toUpperCase()+"&subdept="+subdept;
        
        var req = initRequest();
        req.onreadystatechange = function() {
        
           if (req.readyState == 4) {
               if (req.status == 200) {
               parsePktWise(req.responseXML);
               } else if (req.status == 204){
               
               }
           }
        };
        req.open("GET", url, true);
        req.send(null);
    }

    function parsePktWise(responseXML) 
    {      
      //alert("parsePktWise");
      //alert(responseXML);
      
      var myCheckBoxUnchecked, selectElement;
      var option;
      
      var div = document.getElementById("pktwise");
      div.innerHTML = "";
      div.style.display='';
      
      //var table = document.getElementById('pkttable');
      var table = document.createElement('table');
      table.width = "70%";
      table.className = "dataTable";
      
      var tblBody = document.createElement("tbody");
      
      /*var firstrow = "<tr> <td colspan=\"9\">Pkt Wise Detail of Memo No<\/td> <\/tr> <tr> <th>Sr. No.<\/th>"+
        "<th>Date<\/th> <th>Memo No.<\/th> <th>Pkt No.<\/th> <th>Cts<\/th> <th>Dept.<\/th> <th>Size<\/th>"+
        "<th>Verification fail<\/th> <th>Fail Remark<\/th> <\/tr>";
      
      var row = document.createElement("TR");
      var cell = document.createElement("td");
      var tempdiv = document.createElement("div");
      
      tempdiv.innerHTML = firstrow;
      cell.appendChild(tempdiv);
      row.appendChild(cell);*/
      
      var row = document.createElement("TR");
      var cell = document.createElement("td");
      cell.colSpan = "9";
      var cellText = document.createTextNode("Pkt Wise Detail of Memo No");
      cell.appendChild(cellText);
      row.appendChild(cell);
      tblBody.appendChild(row);
      
      row = document.createElement("TR");
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Sr. No.");
      cell.appendChild(cellText);
      
      myCheckBoxUnchecked = document.createElement('input');
      myCheckBoxUnchecked.setAttribute('type','checkbox');
      myCheckBoxUnchecked.setAttribute('name','Allpktwisechk');
      myCheckBoxUnchecked.setAttribute('id',"Allpktwisechk");
      myCheckBoxUnchecked.onclick = function() {  
        
          CheckAllChkbox(this.id, 'pktwisechk')
        
      };
      cell.appendChild(myCheckBoxUnchecked);
      
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Date");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Memo No.");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Pkt No.");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Cts");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Dept.");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Size");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Verification fail");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Fail Remark");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      tblBody.appendChild(row);
      
             
          
      var pktdata = responseXML.getElementsByTagName("pktdata")[0];
      //var row = pktdata;
      
      for (var loop = 0; loop < pktdata.childNodes.length; loop++) 
      {
        var rownode = pktdata.childNodes[loop];
        
        var pktno = rownode.getElementsByTagName("pktno")[0].childNodes[0].nodeValue;
        var idn = rownode.getElementsByTagName("idn")[0].childNodes[0].nodeValue;
        var stt = rownode.getElementsByTagName("status")[0].childNodes[0].nodeValue;
        
        //alert("pktno: "+pktno);
        
        row = document.createElement("TR");
        
        myCheckBoxUnchecked = document.createElement('input');
        myCheckBoxUnchecked.setAttribute('type','checkbox');
        myCheckBoxUnchecked.setAttribute('id',idn+"_pktwisechk");
        myCheckBoxUnchecked.setAttribute('name',"pktwisechk");
        myCheckBoxUnchecked.setAttribute('value',idn);
        cell = document.createElement("td");
        cell.appendChild(myCheckBoxUnchecked);
        row.appendChild(cell);
        //Mayur Status Hidden Start
        myCheckBoxUnchecked = document.createElement('input');
        myCheckBoxUnchecked.setAttribute('type','hidden');
        myCheckBoxUnchecked.setAttribute('id','stt_'+idn);
        myCheckBoxUnchecked.setAttribute('name','stt_'+idn);
        myCheckBoxUnchecked.setAttribute('value',stt);
//        cell = document.createElement("td");
//        cell.appendChild(myCheckBoxUnchecked);
        row.appendChild(myCheckBoxUnchecked);
        //Mayur Status Hidden end
        cell = document.createElement("td");
        cellText = document.createTextNode("");
        cell.appendChild(cellText);
        row.appendChild(cell);
        
        cell = document.createElement("td");
        cellText = document.createTextNode(rownode.getElementsByTagName("memo")[0].childNodes[0].nodeValue);
        cell.appendChild(cellText);
        row.appendChild(cell);
        //alert("subdept:"+rownode.getElementsByTagName("subdept")[0].childNodes[0].nodeValue);
        
        cell = document.createElement("td");
        cellText = document.createTextNode(pktno);
        cell.appendChild(cellText);
        row.appendChild(cell);
        //alert("qty:"+rownode.getElementsByTagName("qty")[0].childNodes[0].nodeValue);
        
        cell = document.createElement("td");
        cellText = document.createTextNode(rownode.getElementsByTagName("cts")[0].childNodes[0].nodeValue);
        cell.appendChild(cellText);
        row.appendChild(cell);
        
        cell = document.createElement("td");
        cellText = document.createTextNode(rownode.getElementsByTagName("dept")[0].childNodes[0].nodeValue);
        cell.appendChild(cellText);
        row.appendChild(cell);
        
        cell = document.createElement("td");
        cellText = document.createTextNode(rownode.getElementsByTagName("subdept")[0].childNodes[0].nodeValue);
        cell.appendChild(cellText);
        row.appendChild(cell);
        
        myCheckBoxUnchecked = document.createElement('input');
        myCheckBoxUnchecked.setAttribute('type','checkbox');
        myCheckBoxUnchecked.setAttribute('id',pktno+"_pktwisefail");
        myCheckBoxUnchecked.setAttribute('name',pktno+"_pktwisefail");
        myCheckBoxUnchecked.setAttribute('value',pktno);
        
        /*myCheckBoxUnchecked.onclick = function() {  
            if(this.checked){
              //alert(this.id);
              verifyPktWise(this.id)
            }              
          };
          */
        cell = document.createElement("td");
        cell.appendChild(myCheckBoxUnchecked);
        row.appendChild(cell);
        
        selectElement = document.createElement('select');
        selectElement.setAttribute('id', pktno+"_selectpktwise");
        selectElement.setAttribute('name', pktno+"_selectpktwise");
        selectFailRemark(selectElement);
        
        cell = document.createElement("td");
        cell.appendChild(selectElement);
        row.appendChild(cell);
        
        tblBody.appendChild(row);
      
      }
      
      //write to html page
      table.appendChild(tblBody);
      div.appendChild(table);
      
    }
    
    
function showSizeWise(typename, memono, dept) {
//alert("showsizewise");
//alert("typ: "+ typename + " memo: " + memono + " dept: "+dept);
    var url = "Verification.do?method=sizeWise&type="+typename.toUpperCase()+"&memono="+memono;
    
    if(dept != '') {
      url = url +"&dept="+dept;
    }
             
    var req = initRequest();
    req.onreadystatechange = function() {    
    
       if (req.readyState == 4) {
           if (req.status == 200) {          
               parseSizeWise(req.responseXML, memono, typename);
           } else if (req.status == 204){
        
           }
       }
    };
    req.open("GET", url, true);
    req.send(null);
}


    function parseSizeWise(responseXML, memono, typename) 
    {      
      //alert("parseSizeWise");
      //alert(responseXML);
      var memo = responseXML.getElementsByTagName("memo")[0];
      
      var myCheckBoxUnchecked, selectElement;
      var option;
      
      var div = document.getElementById("sizewise");
      div.innerHTML = "";
      div.style.display='';
      
      /*var newdiv = document.getElementById("dbtablenewrows");
      newdiv.innerHTML="";*/
      
      //var table = document.getElementById('dbtable');
      var table = document.createElement('table');
      table.width = "70%";
      table.className = "dataTable";
      
      var tblBody = document.createElement("tbody");

      var row = document.createElement("TR");
      var cell = document.createElement("td");
      
      cell.colSpan = "7";
      var cellText = document.createTextNode("Size Wise Detail of Memo No");
      cell.appendChild(cellText);
      row.appendChild(cell);
      tblBody.appendChild(row);
      
      row = document.createElement("TR");
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Sr. No.");
      cell.appendChild(cellText);
      
      myCheckBoxUnchecked = document.createElement('input');
      myCheckBoxUnchecked.setAttribute('type','checkbox');
      myCheckBoxUnchecked.setAttribute('name','Allsizewisechk');
      myCheckBoxUnchecked.setAttribute('id',"Allsizewisechk");
      myCheckBoxUnchecked.onclick = function() {  
        
          CheckAllChkbox(this.id, 'sizewisechk')
        
      };
      cell.appendChild(myCheckBoxUnchecked);
      
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Date");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Size");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("TotalPkt");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Cts");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Verification fail");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      cell = document.createElement("th");
      cellText = document.createTextNode("Fail Remark");
      cell.appendChild(cellText);
      row.appendChild(cell);
      
      tblBody.appendChild(row);
      
      var rowdept;
      
      for(var i=0; i<5; i++) {
        if(i==0) {
          deptname = ".18 Down";
          rowdept = memo.getElementsByTagName("n18Down")[0];        
        }
        else if(i==1) {
          deptname = "18-96 MIX";
          rowdept = memo.getElementsByTagName("n1896MIX")[0];
        }
        else if(i==2) {
          deptname = "18-96 GIA";
          rowdept = memo.getElementsByTagName("n1896GIA")[0];
        }
        else if(i==3) {
          deptname = "0.96 UP GIA";
          rowdept = memo.getElementsByTagName("n96UPGIA")[0];
        }
        else if(i==4) {
          deptname = "0.96 UP MIX";
          rowdept = memo.getElementsByTagName("n96UPMIX")[0];
        }
        
        if(rowdept != null) {
          row = document.createElement("TR");
          cell = document.createElement("th");
          cellText = document.createTextNode("Memo: "+ memono + "   Dept: " + deptname);
          cell.appendChild(cellText);
          cell.colSpan = "7";
          //cell.style = "font-weight:bold;"
          row.appendChild(cell);
          
          tblBody.appendChild(row);          
          
          for (var loop = 0; loop < rowdept.childNodes.length; loop++) 
          {
            var rownode = rowdept.childNodes[loop];
            var dept = rownode.getElementsByTagName("dept")[0].childNodes[0].nodeValue;
            var subdept = rownode.getElementsByTagName("subdept")[0].childNodes[0].nodeValue;
            
            row = document.createElement("TR");
            
            /*alert("typename "+typename);
            alert("memono "+memono);
            alert("dept "+dept);
            alert("subdept "+subdept);
            */
            myCheckBoxUnchecked = document.createElement('input');
            myCheckBoxUnchecked.setAttribute('type','checkbox');
            myCheckBoxUnchecked.setAttribute('name','sizewisechk');
            myCheckBoxUnchecked.setAttribute('value',typename+"_"+memono+"_"+dept+"_"+subdept);
            myCheckBoxUnchecked.setAttribute('id',typename+"_"+memono+"_"+dept+"_"+subdept+"_sizewisechk");
            //alert(myCheckBoxUnchecked.id);
            /*myCheckBoxUnchecked.onclick = function() {  
                if(this.checked){
                  //alert(this.id);
                  showPktWise(this.id)
                }              
              };*/
            
            cell = document.createElement("td");
            cell.appendChild(myCheckBoxUnchecked);
            row.appendChild(cell);
            
            cell = document.createElement("td");
            cellText = document.createTextNode("");
            cell.appendChild(cellText);
            row.appendChild(cell);
            
            //alert("subdept:"+subdept);
            cell = document.createElement("td");
            var tempdiv = document.createElement("div");
            tempdiv.innerHTML = "<a onclick=\"showPktWise('"+typename+"_"+memono+"_"+dept+"_"+subdept+"_subdept');\">"+subdept+"</a>"
            //cellText = document.createTextNode(subdept);
            //cell.appendChild(cellText);
            cell.appendChild(tempdiv);
            row.appendChild(cell);
            
            /*cell = document.createElement("td");
            var tempdiv = document.createElement("div");
            tempdiv.innerHTML = rownode.getElementsByTagName("qty")[0].childNodes[0].nodeValue;
            tempdiv.setAttribute('style', 'text-decoration : underline; color:blue;');
            tempdiv.setAttribute('id', typename+"_"+memono+"_"+dept+"_"+subdept+"_qty");
            tempdiv.onclick = function() {  
                  alert(this.id);
                  showPktWise(this.id);
              };
            
            cell.appendChild(tempdiv);
            row.appendChild(cell);*/
            cell = document.createElement("td");
            tempdiv = document.createElement("div");
            tempdiv.innerHTML = "<a onclick=\"showPktWise('"+typename+"_"+memono+"_"+dept+"_"+subdept+"_qty');\">"+rownode.getElementsByTagName("qty")[0].childNodes[0].nodeValue+"</a>"
            cell.appendChild(tempdiv);
            row.appendChild(cell);
            //alert("qty:"+rownode.getElementsByTagName("qty")[0].childNodes[0].nodeValue);
            
            cell = document.createElement("td");
            tempdiv = document.createElement("div");
            tempdiv.innerHTML = "<a onclick=\"showPktWise('"+typename+"_"+memono+"_"+dept+"_"+subdept+"_cts');\">"+rownode.getElementsByTagName("cts")[0].childNodes[0].nodeValue+"</a>"
            /*tempdiv.innerHTML = rownode.getElementsByTagName("cts")[0].childNodes[0].nodeValue;
            tempdiv.setAttribute('style', 'text-decoration : underline; color:blue;');
            tempdiv.setAttribute('id', typename+"_"+memono+"_"+dept+"_"+subdept+"_cts");
            tempdiv.onclick = function() {                  
                  alert(this.id);
                  showPktWise(this.id);                
              };*/
            cell.appendChild(tempdiv);
            row.appendChild(cell);
            
            myCheckBoxUnchecked = document.createElement('input');
            myCheckBoxUnchecked.setAttribute('type','checkbox');
            myCheckBoxUnchecked.setAttribute('id',typename+"_"+memono+"_"+dept+"_"+subdept+"_sizewisefail");
            myCheckBoxUnchecked.setAttribute('name', typename+"_"+memono+"_"+dept+"_"+subdept+"_sizewisefail");
            myCheckBoxUnchecked.setAttribute('value',typename+"_"+memono+"_"+dept+"_"+subdept);
            /*myCheckBoxUnchecked.onclick = function() {  
                if(this.checked){
                  
                  verifySizeWise(this.id)
                }              
              };
              */
            cell = document.createElement("td");
            cell.appendChild(myCheckBoxUnchecked);
            row.appendChild(cell);
            
            selectElement = document.createElement('select');
            selectElement.setAttribute('id', typename+"_"+memono+"_"+dept+"_"+subdept+"_selectsizewise");
            selectElement.setAttribute('name', typename+"_"+memono+"_"+dept+"_"+subdept+"_selectsizewise");
            selectFailRemark(selectElement);
            
            cell = document.createElement("td");
            cell.appendChild(selectElement);
            row.appendChild(cell);
            
            tblBody.appendChild(row);
          
          }
        }
        
      }
      //write to html page
      table.appendChild(tblBody);
      div.appendChild(table);
      //newdiv.innerHTML = tblBody;
    }
    
    function selectFailRemark(select) 
    {
      var url = "Verification.do?method=selectFailRemark";
      
      var req = initRequest();
      req.onreadystatechange = function() {
      
         if (req.readyState == 4) {
             if (req.status == 200) {
             parseFailRemark(req.responseXML, select);
             } else if (req.status == 204){
             
             }
         }
      };
      req.open("GET", url, true);
      req.send(null);
    }
    
    function parseFailRemark(responseXML, selectElement) {
      //alert("parseFailRemark");
		
      var failremlist = responseXML.getElementsByTagName("failrem")[0];
      for (var loop = 0; loop < failremlist.childNodes.length; loop++) 
      {
          var rownode = failremlist.childNodes[loop];
          
          var val = rownode.getElementsByTagName("val")[0].childNodes[0].nodeValue;
          var dsc = rownode.getElementsByTagName("dsc")[0].childNodes[0].nodeValue;
          
          prepareSelect(selectElement, val, dsc);
          
      }
    }
    
    function prepareSelect(selectElement, value, dsc) 
    {
      option = document.createElement("option");
      option.text = dsc;
      option.value = value;
      try
      {
      // for IE earlier than version 8
        selectElement.add(option,selectElement.options[null]);
      }
      catch (e)
      {
        selectElement.add(option,null);
      }      
    }

    function verifySizeWise(chk_id) 
    {
      var splitstr = chk_id.split("_");
      
      var typename = splitstr[0];
      var memono = splitstr[1];
      var dept = splitstr[2];
      var subdept = splitstr[3];
      //alert("typename"+typename);
      //alert("memono"+memono);
      //alert("dept"+dept);
      //alert("subdept"+subdept);
      
      var url = "Verification.do?method=verifySizeWise&type="+typename.toUpperCase()
      +"&memono="+memono+"&dept="+dept.toUpperCase()+"&subdept="+subdept;
      
      var req = initRequest();
      req.onreadystatechange = function() {
      
         if (req.readyState == 4) {
             if (req.status == 200) {
             parsePktWise(req.responseXML);
             } else if (req.status == 204){
             
             }
         }
      };
      req.open("GET", url, true);
      req.send(null);
    }
    
    function verifyPktWise(pktno) 
    {
      var url = "Verification.do?method=verifyPktWise&pktno="+pktno;
      
      var req = initRequest();
      req.onreadystatechange = function() {
      
         if (req.readyState == 4) {
             if (req.status == 200) {
             parsePktWise(req.responseXML);
             } else if (req.status == 204){
             
             }
         }
      };
      req.open("GET", url, true);
      req.send(null);
    }
    