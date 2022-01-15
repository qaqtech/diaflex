var count = 0;
	var accumArray = [];
    function writeSummary(summary) {
        summaryElem =
            document.getElementById("summary");
        summaryElem.innerHTML += summary+"</br>";
    }
	
	function countEvent(tag) {
		if(document.getElementById("accumulateMode").checked == true)
		{
			if(accumArray.indexOf(tag) == -1)
			{
				accumArray.push(tag);
				summaryElem1 = document.getElementById("count");
				return;
			}
		}
		else
		{	
			//var list = document.getElementById("list");
			summaryElem1 = document.getElementById("count");
			if(tag){
				count++;				
				//AddItems(list, tag,tag)
			}
			summaryElem1.innerHTML =count+"</br>";
		}
    }
	function notifyEvent(summary) {
		var data= JSON.parse(summary);
		if(document.getElementById("accumulateMode").checked == true && data.Message == "scanCompleted")
		{			
			app = document.getElementById("napp");
			app.scanRFID();
		}
                summaryElem2 = document.getElementById("notify");
                summaryElem2.innerHTML = summary;
                lightList = data.TagAll || [];
                var str = lightList.join();
               var chk =document.getElementById("contscan").checked;
               var listnametextContent=document.getElementById(document.getElementById("listname").value).textContent;
               if(chk && listnametextContent!=''){
                          if(str!=''){
                              str=listnametextContent+","+str;
                          }
                        }
                        document.getElementById(document.getElementById("listname").value).textContent = str;
                        var listnametextContentCount=document.getElementById(document.getElementById("listname").value).textContent;
                        if(listnametextContentCount!=''){
                        var resCount = listnametextContentCount.split(",");
                        document.getElementById("count").innerHTML=resCount.length;
                }
    }
	function weightEvent(summary) {
        summaryElem2 = document.getElementById("summary");
        summaryElem2.innerHTML = summary;
    }
	
	function scanRfUSB()
	{	
		app = document.getElementById("napp");
		document.getElementById("count").innerHTML = "";
		document.getElementById("notify").innerHTML = "";        
		count = 0;
		app.scanRFID();
	}
        
        function ligtOnUSB()
	{
		app = document.getElementById("napp");
		//var li = document.getElementById("list").textContent;
        //var arr = li.split(',');
		var str = lightList.join();
		/*
		for (var i=0 ;i< arr.length;i++)
		{
			if(i == 0)
			{str += arr[i].replace(/\W+/g, "");}
			else
			{str += "," + arr[i].replace(/\W+/g, "");}			
		}
		document.getElementById("list").textContent = str;
		*/
		document.getElementById("list").textContent = str;
		/*
                app.LightOn(str);
		alert("Press Ok to turn it off");
		app.LightOff();
                */
	}
	
	function waitOnUSB(txtarea)
	{
		app = document.getElementById("napp");
		var li = document.getElementById(txtarea).value;
                var arr = li.split(',');
		var str = "";
		for (var i=0 ;i< arr.length;i++)
		{
			if(i == 0)
			{str += arr[i].replace(/\W+/g, "");}
			else
			{str += "," + arr[i].replace(/\W+/g, "");}
			
		}
		document.getElementById(txtarea).value = str;
                app.LightOn(str);
                alert('Light On Process Completed Please check');
                app.LightOff();     
	}
	
	function waitOffUSB()
	{
		app = document.getElementById("napp");
		summaryElem1 = document.getElementById("count");
                summaryElem1.innerHTML = "";
		app.LightOff();
	}	
	
	function getWeightUSB()
	{
		app = document.getElementById("napp");
		summaryElem1 = document.getElementById("count");
                summaryElem1.innerHTML = "";
		app.TagAndWeight();
	}
	
	function disposeUSB()
	{
		app = document.getElementById("napp");
		app.Dispose();
	}
	function testDllUSB()
	{
		app = document.getElementById("napp");
		var t= app.test();
		alert(t);
	}
	
	function AddItems(ddlName, textField, ValueField) 
	{
		var opt = document.createElement("input");
		opt.type = 'checkbox';
		opt.id = textField;
		opt.innerHTML= textField;
		//opt.value = '1';
		ddlName.appendChild(opt);
		ddlName.appendChild(document.createElement("br"));
	}
        
        function displayTypAllCur(fielddisplay){
        if(fielddisplay=='IS' || fielddisplay=='AP' || fielddisplay=='SL' || fielddisplay=='DLV'){
            document.getElementById('allcurrent').style.display='block';
        }  else{
            document.getElementById('allcurrent').style.display='none';
        }
        }