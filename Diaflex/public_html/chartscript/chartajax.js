var chart;
var legend;
var chartLst;
var chartLst1;
var chartData=[];
var chartData1=[];
var obj = {};
var obj1 = {};
var bullet=['round','square','triangleUp','triangleDown','bubble']
var colorBar=['#D8E0BD','#B3DBD4','#69A55C','#B5B8D3','#F4E23B']
var grpColorbar =
{
"MKTCts": "#666666",
        "MKTQty": "#666666",
        "MKTAvg": "#666666",
        "MKTVlu": "#666666",
        "DLVQty": "#FF5555",
        "DLVAvg": "#FF5555",
        "SOLDCts": "#FF5555",
        "SOLDQty": "#FF5555",
        "SOLDAvg": "#FF5555",
        "SOLDVlu": "#FF5555",
        "SLQty": "#FF5555",
        "SLAvg": "#FF5555",
        "LABQty": "#008000",
        "LABAvg": "#008000",
        "ASRTQty": "#800000",
        "ASRTAvg": "#800000",
        "LABOKQty": "#06159B",
        "LABOKAvg": "#06159B",
        "NAQty": "#FF0F00",
        "NAAvg": "#FF0F00",
        "P1Qty": "#800000",
        "P1Avg": "#800000",
        "P2Qty": "#06159B",
        "P2Avg": "#06159B",
        "P3Qty": "#FF0F00",
        "P3Avg": "#FF0F00",
        "Qty": "#7A7A7A",
        "Cts": "#FF6F6F",
        "Vlu": "#2323FF",
        "NEWMKTQty": "#9d9d9d",
        "NEWMKTAvg": "#9d9d9d",
	"NEWMKTVlu": "#9d9d9d",
        "ADDCts": "#9d9d9d",
        "ADDAvg": "#9d9d9d",
	"ADDVlu": "#9d9d9d",
        "NEWSOLDQty": "#ffaaaa",
        "NEWSOLDAvg": "#ffaaaa",
	"NEWSOLDVlu": "#ffaaaa"
};
function initRequest() {
       if (window.XMLHttpRequest) {
           return new XMLHttpRequest();
       } else if (window.ActiveXObject) {
           isIE = true;
           return new ActiveXObject("Microsoft.XMLHTTP");
       }
}
function callcommonpieChart(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_DIV")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  commonpieChart(url+"&stt="+fieldVal,fieldVal+" "+title,fieldVal,wdth);
  }
  }
  }
}
function callcreatememoPieChart(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDD")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  var strarry = fieldVal.split("_");
  commonpieChart(url+"&prp="+unescape(strarry[0])+"&dept="+unescape(strarry[1]),unescape(strarry[1])+" "+unescape(strarry[0])+" "+title,fieldVal,wdth);
  }
  }
  }
}
function commonpieChart(url,title,chartwrite,wdth){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonpieChart(req.responseXML,title,chartwrite,wdth);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function agepieChart(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonpieChart(req.responseXML,title,chartwrite,50);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}


function createbuyerwisePieChart(url,title,chartwrite,wdth,ht){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonpieChart(req.responseXML,title,chartwrite,wdth);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagescommonpieChart(responseXML,title,chartwrite,valueWd){
chartLst=[];
chartLst = ['country','litres'];
chartData=[];
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
var lkey = unescape((columnroot.getElementsByTagName("attrVal")[0]).childNodes[0].nodeValue);
lval = replaceAll(lval,'%26','&');
lval = replaceAll(lval,'%22','\"');
lval = replaceAll(lval,'%28','(');
lval = replaceAll(lval,'%29',')');
lval = replaceAll(lval,'%2F','/');
lval = replaceAll(lval,'%2A','*');
lval = replaceAll(lval,'%21','!');
lval = replaceAll(lval,'%24','$');
lval = replaceAll(lval,'%25','%');
lval = replaceAll(lval,'%2D','-');
lval = replaceAll(lval,'%2E','.');
lval = replaceAll(lval,'%3F','?');
lval = replaceAll(lval,'%20',' ');
obj = {};
obj[chartLst[0]] = lval;
obj[chartLst[1]] = lkey;
chartData.push(obj);
}
callPie(chartwrite,title,valueWd);
set3D();
}

function callPie(chartwrite,title,valueWd) {
    // PIE CHART
    chart = new AmCharts.AmPieChart();
    chart.dataProvider = chartData;
    chart.titleField = "country";
    chart.valueField = "litres";
    chart.minRadius = 100;//circle radius
    chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
    // LEGEND1)title 2)Fontsize of title 4)gap between toop tip and circle
    legend = new AmCharts.AmLegend();
    legend.align = "center";
    legend.markerType = "circle";
    legend.valueWidth = parseInt(valueWd);//Gap between marker and value 
    legend.verticalGap = 1;//gap betwwen legent and circle
    chart.balloonText = "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>";
    chart.addLegend(legend);
    
    // WRITE
    chart.write(chartwrite);
    
}

function callrptshapeclarityBar(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDD")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  var ttl=document.getElementById(fieldVal+'_TTL').value;
  commonBarChart(url+"&key="+unescape(fieldVal),ttl,fieldVal);
  }
  }
  }
}

function commonBarChart(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonBarChart(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescommonBarChart(responseXML,title,chartwrite,valueWd){
chartLst=[];
chartLst = ['Shape','Avg'];
chartData=[];
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
var lkey = unescape((columnroot.getElementsByTagName("attrVal")[0]).childNodes[0].nodeValue);
obj = {};
obj[chartLst[0]] = lval;
obj[chartLst[1]] = lkey;
chartData.push(obj);
}
callSingleBar(chartwrite,title,valueWd);
}

function callSingleBar(chartwrite,title,valueWd) {
    // SERIAL CHART
    chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = chartLst[0];
    // this single line makes the chart a bar chart, 
    // try to set it to false - your bars will turn to columns                
    chart.rotate = true;
    // the following two lines makes chart 3D
    chart.depth3D = 10;
    chart.angle = 20;
    chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
    chart.startDuration = 1;
    chart.columnWidth=0.6;  //bar width
    
    // AXES
    // Category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.gridPosition = "center";
    categoryAxis.axisColor = "#DADADA";
    categoryAxis.fillAlpha = 1;
    categoryAxis.gridAlpha = 0;
    categoryAxis.fillColor = "#FAFAFA";
    categoryAxis.labelRotation = 35;
    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.axisColor = "#DADADA";
    valueAxis.title = "Avg";
    valueAxis.gridAlpha = 0.1;
    chart.addValueAxis(valueAxis);

    // GRAPH
    var graph = new AmCharts.AmGraph();
    graph.title = chartLst[1];
    graph.valueField = chartLst[1];
    graph.type = "column";
    graph.balloonText = "Avg in [[category]]:[[value]]";
    graph.lineAlpha = 0;
    graph.fillColors = grpColorbar[chartLst[1]];
    graph.fillAlphas = 1;
    graph.labelText = "[[value]]";
    graph.labelPosition = "inside" ;//bar label on top
    chart.addGraph(graph);

    // WRITE
    chart.write(chartwrite);
    
}

function createChartSizepurity(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDD")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  commonLineChart(url+"&szVal="+unescape(fieldVal)+"&mfgrte=Y",unescape(fieldVal)+" "+title,fieldVal);
  commonLineChart(url+"&szVal="+unescape(fieldVal)+"&mfgrte=",unescape(fieldVal)+" "+title,fieldVal+"_MFG");
  }
  }
  }
}

function pricelinegraph(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDD")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  var ttl=document.getElementById(fieldVal+'_TTL').value;
  commonLineChart(url+"&key="+unescape(fieldVal),unescape(ttl)+" "+title,fieldVal);
  }
  }
  }
}

function commonLineChart(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonLineChart(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescommonLineChart(responseXML,title,chartwrite,valueWd){
var lineGrp=document.getElementById('lineGrp').value
chartLst=[];
chartData=[];
chartLst = ['MonthYear'];
var counter=1;
var grpArry = lineGrp.split("_");
for (var lo = 0; lo < grpArry.length; lo++) {
chartLst[counter++] = grpArry[lo];
}
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
obj = {};
obj[chartLst[0]] = lval;
counter=1;
var lkey = unescape((columnroot.getElementsByTagName("attrVal")[0]).childNodes[0].nodeValue);
var lkeyArry = lkey.split("XML");
for (var lk = 0; lk < lkeyArry.length; lk++) {
obj[chartLst[counter++]] = lkeyArry[lk];
}
chartData.push(obj);
}
callLine(chartwrite,title,valueWd);
}

function callLine(chartwrite,title,valueWd) {
    // SERIAL CHART
    var vluaxisttl=document.getElementById(chartwrite+'_VLUTTL').value;
    chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = "MonthYear";
    chart.startDuration = 0.5;
    chart.balloon.color = "#000000";
    chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.fillAlpha = 1;
    categoryAxis.fillColor = "#FAFAFA";
    categoryAxis.gridAlpha = 0;
    categoryAxis.axisAlpha = 0;
    categoryAxis.gridPosition = "start";
    categoryAxis.position = "top";
    
    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.title = vluaxisttl;
    valueAxis.dashLength = 5;
    valueAxis.axisAlpha = 0;

    valueAxis.integersOnly = true;
    valueAxis.gridCount = 10;
    valueAxis.reversed = true; // this line makes the value axis reversed
    chart.addValueAxis(valueAxis);
    
    // GRAPHS
    // Italy graph
    for (var ins = 1; ins < chartLst.length; ins++) {
    var graph = new AmCharts.AmGraph();
    graph.title = chartLst[ins];
    graph.valueField = chartLst[ins];;
//    graph.hidden = true; // this line makes the graph initially hidden           
    graph.balloonText = chartLst[ins]+" [[category]]: [[value]]";
    graph.bullet = bullet[ins-1];
    graph.labelText = "[[value]]";
    chart.addGraph(graph);
    }   
                
        // CURSOR
    var chartCursor = new AmCharts.ChartCursor();
    chartCursor.cursorPosition = "mouse";
    chart.addChartCursor(chartCursor);
    // LEGEND
    var legend = new AmCharts.AmLegend();
    legend.useGraphSettings = true;
    chart.addLegend(legend);
    
    // WRITE
    chart.write(chartwrite);
    
}
// changes label position (labelRadius)

function ioWeekChart(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDD")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  commonStackBarChart(url,title,fieldVal);
  }
  }
  }
}
function commonStackBarChart(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonStackBarChart(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescommonStackBarChart(responseXML,title,chartwrite,valueWd){
var stackGrp=document.getElementById('stackGrp').value
chartLst=[];
chartData=[];
chartLst = ['Week'];
var counter=1;
var grpArry = stackGrp.split("_");
for (var lo = 0; lo < grpArry.length; lo++) {
chartLst[counter++] = grpArry[lo];
}
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
obj = {};
obj[chartLst[0]] = lval;
counter=1;
var lkey = unescape((columnroot.getElementsByTagName("attrVal")[0]).childNodes[0].nodeValue);
var lkeyArry = lkey.split("XML");
for (var lk = 0; lk < lkeyArry.length; lk++) {
obj[chartLst[counter++]] = lkeyArry[lk];
}
chartData.push(obj);
}
callStackBar(chartwrite,title,valueWd);
}

function callStackBar(chartwrite,title,valueWd) {
   chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = "Week";
    chart.plotAreaBorderAlpha = 0.2;
    chart.startDuration = 2;
    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.gridAlpha = 0.1;
    categoryAxis.axisAlpha = 0;
    categoryAxis.gridPosition = "start";

    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.stackType = "regular";
    valueAxis.gridAlpha = 0.1;
    valueAxis.axisAlpha = 0;
    chart.addValueAxis(valueAxis);
    chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title

    // GRAPHS
    // first graph
    
    for (var ins = 1; ins < chartLst.length-2; ins++) {
    var graph = new AmCharts.AmGraph();
    graph.title = chartLst[ins];
    graph.labelText = "[[value]]";
    graph.balloonText =chartLst[ins]+" [[category]]: [[value]]";
    graph.valueField = chartLst[ins];
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.fillAlphas = 1;
    graph.lineColor = colorBar[ins];
    chart.addGraph(graph);;
    } 
               
    
    var valueAxis2 = new AmCharts.ValueAxis();
    valueAxis2.stackType = "regular";
    valueAxis2.gridAlpha = 0.1;
    valueAxis2.axisAlpha = 0;
    valueAxis2.position = "right";
    chart.addValueAxis(valueAxis2);
    for (var ins = chartLst.length-2; ins < chartLst.length; ins++) {
    graph = new AmCharts.AmGraph();
    graph.title = chartLst[ins];
    graph.labelText = "[[value]]";
    graph.valueField = chartLst[ins];
    graph.balloonText =chartLst[ins]+" [[category]]: [[value]]";
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.fillAlphas = 1;
    graph.lineColor = colorBar[ins];
    graph.stackable = true;
    graph.valueAxis = valueAxis2;
    chart.addGraph(graph);
    } 

    // LEGEND                  
    var legend = new AmCharts.AmLegend();
    legend.borderAlpha = 0.2;
    legend.valueWidth = 0;
    legend.horizontalGap = 10;
    chart.addLegend(legend);

    // WRITE
    chart.write(chartwrite);
    
}

function callcreateMultipleBarGraph(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
 
  if (fieldId.indexOf("_HIDD")!=-1){
  var fieldVal=document.getElementById(fieldId).value;

  commonMultipleBarGraph(url,title,fieldVal);
  }
  }
  }
}
function commonMultipleBarGraph(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonMultipleBarGraph(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagescommonMultipleBarGraph(responseXML,title,chartwrite,valueWd){
var barGrp=document.getElementById('barGrp').value;

chartLst=[];
chartData=[];
chartLst = ['Prp'];
var counter=1;
var grpArry = barGrp.split("_");
var grp;
for (grp = 0; grp < grpArry.length; grp++) {
chartLst[counter++] = grpArry[grp]+'Qty';
}
for (grp = 0; grp < grpArry.length; grp++) {
chartLst[counter++] = grpArry[grp]+'Avg';
}
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
obj = {};
counter=1;
obj[chartLst[0]] = lval;
for (grp = 0; grp < grpArry.length; grp++) {
var grpQty=new String(grpArry[grp]+'Qty');
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpQty))[0]).childNodes[0].nodeValue);
}
for (grp = 0; grp < grpArry.length; grp++) {
var grpVlu=new String(grpArry[grp]+'Avg');
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpVlu))[0]).childNodes[0].nodeValue);
}
chartData.push(obj);
}
callMultipleBar(chartwrite,title,valueWd);
}

function callcreateMultipleBarGraphmultionPg(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDULTI")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  commonMultipleBarGraphmultionPg(url,title,fieldVal);
  }
  }
  }
}
function commonMultipleBarGraphmultionPg(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonMultipleBarGraphmultionPg(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagescommonMultipleBarGraphmultionPg(responseXML,title,chartwrite,valueWd){
var barGrp=document.getElementById('barGrpmultionPg').value;
chartLst=[];
chartData=[];
chartLst = ['Prp'];
var counter=1;
var grpArry = barGrp.split("_");
var grp;
for (grp = 0; grp < grpArry.length; grp++) {
chartLst[counter++] = grpArry[grp]+'Qty';
}
for (grp = 0; grp < grpArry.length; grp++) {
chartLst[counter++] = grpArry[grp]+'Vlu';
}
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
obj = {};
counter=1;
lval = replaceAll(lval,'%26','&');
lval = replaceAll(lval,'%22','\"');
lval = replaceAll(lval,'%28','(');
lval = replaceAll(lval,'%29',')');
lval = replaceAll(lval,'%2F','/');
lval = replaceAll(lval,'%2A','*');
lval = replaceAll(lval,'%21','!');
lval = replaceAll(lval,'%24','$');
lval = replaceAll(lval,'%25','%');
lval = replaceAll(lval,'%2D','-');
lval = replaceAll(lval,'%2E','.');
lval = replaceAll(lval,'%3F','?');
lval = replaceAll(lval,'%20',' ');
obj[chartLst[0]] = lval;
for (grp = 0; grp < grpArry.length; grp++) {
var grpQty=new String(grpArry[grp]+'Qty');
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpQty))[0]).childNodes[0].nodeValue);
}
for (grp = 0; grp < grpArry.length; grp++) {
var grpVlu=new String(grpArry[grp]+'Vlu');
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpVlu))[0]).childNodes[0].nodeValue);
}
chartData.push(obj);
}
callMultipleBarmultionPg(chartwrite,title,valueWd);
}

function callcreateMultipleBarGraphmultionPgCtsVlu(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDULTI")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  commonMultipleBarGraphmultionPgCtsVlu(url,title,fieldVal);
  }
  }
  }
}
function commonMultipleBarGraphmultionPgCtsVlu(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonMultipleBarGraphmultionPgCtsVlu(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagescommonMultipleBarGraphmultionPgCtsVlu(responseXML,title,chartwrite,valueWd){
var barGrp=document.getElementById('barGrpmultionPg').value;
chartLst=[];
chartData=[];
chartLst = ['Prp'];
var counter=1;
var grpArry = barGrp.split("_");
var grp;
for (grp = 0; grp < grpArry.length; grp++) {
chartLst[counter++] = grpArry[grp]+'Cts';
}
for (grp = 0; grp < grpArry.length; grp++) {
chartLst[counter++] = grpArry[grp]+'Avg';
}
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
obj = {};
counter=1;
lval = replaceAll(lval,'%26','&');
lval = replaceAll(lval,'%22','\"');
lval = replaceAll(lval,'%28','(');
lval = replaceAll(lval,'%29',')');
lval = replaceAll(lval,'%2F','/');
lval = replaceAll(lval,'%2A','*');
lval = replaceAll(lval,'%21','!');
lval = replaceAll(lval,'%24','$');
lval = replaceAll(lval,'%25','%');
lval = replaceAll(lval,'%2D','-');
lval = replaceAll(lval,'%2E','.');
lval = replaceAll(lval,'%3F','?');
lval = replaceAll(lval,'%20',' ');
obj[chartLst[0]] = lval;
for (grp = 0; grp < grpArry.length; grp++) {
var grpCts=new String(grpArry[grp]+'Cts');
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpCts))[0]).childNodes[0].nodeValue);
}
for (grp = 0; grp < grpArry.length; grp++) {
var grpVlu=new String(grpArry[grp]+'Avg');
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpVlu))[0]).childNodes[0].nodeValue);
}
chartData.push(obj);
}
callMultipleBarmultionPgCtsVlu(chartwrite,title,valueWd);
}


function callMultipleBarmultionPgCtsVlu(chartwrite,title,valueWd) {
var barGrp=document.getElementById('barGrpmultionPg').value;
var grpArry = barGrp.split("_");
chart = new AmCharts.AmSerialChart();
chart.dataProvider = chartData;
chart.categoryField = "Prp";
chart.startDuration = 2;
chart.plotAreaBorderColor = "#DADADA";
chart.plotAreaBorderAlpha = 1;
// this single line makes the chart a bar chart
chart.rotate = false;
chart.depth3D = 10;
chart.angle = 20;

// AXES
// Category
var categoryAxis = chart.categoryAxis;
categoryAxis.gridPosition = "start";
categoryAxis.gridAlpha = 0.1;
categoryAxis.axisAlpha = 0;

// Value
var valueAxis = new AmCharts.ValueAxis();
valueAxis.axisAlpha = 0;
valueAxis.gridAlpha = 0.1;
valueAxis.title = "Cts";
chart.addValueAxis(valueAxis);
chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
var durationAxis = new AmCharts.ValueAxis();
durationAxis.title = "Avg";
// the following line makes this value axis to convert values to duration
// it tells the axis what duration unit it should use. mm - minute, hh - hour...
// durationAxis.duration = "mm";

durationAxis.gridAlpha = 0;
durationAxis.axisAlpha = 0;
// durationAxis.inside = true;
durationAxis.position = "right";
chart.addValueAxis(durationAxis);

// GRAPHS
// first graph
for (var lo = 0; lo < grpArry.length; lo++) {
var graph1 = new AmCharts.AmGraph();
graph1.type = "column";
graph1.title = chartLst[lo+1];
graph1.valueField = chartLst[lo+1];
graph1.balloonText = chartLst[lo+1]+" [[category]]:[[value]]";
graph1.lineAlpha = 0;
graph1.fillColors = grpColorbar[chartLst[lo+1]];
graph1.labelText = "[[value]]";
graph1.fillAlphas = 1;
graph1.labelPosition = "inside" ;//bar label on top
graph1.columnWidth=0.9; //To Control The Graph With
chart.addGraph(graph1);
}

for (var lo = 0; lo < grpArry.length; lo++) {
var graph3 = new AmCharts.AmGraph();
graph3.type = "line";
graph3.title = chartLst[lo+(1+grpArry.length)];
graph3.valueAxis = durationAxis;
graph3.lineColor = grpColorbar[chartLst[(lo+(1+grpArry.length))]];
graph3.valueField = chartLst[lo+(1+grpArry.length)];
graph3.balloonText = chartLst[lo+(1+grpArry.length)]+" [[category]]: [[value]]";
graph3.lineThickness = 2;
graph3.bullet = "round";
chart.addGraph(graph3);
}
// LEGEND
var legend = new AmCharts.AmLegend();
chart.addLegend(legend);

// WRITE
chart.write(chartwrite);
    
}

function callMultipleBarmultionPg(chartwrite,title,valueWd) {
var barGrp=document.getElementById('barGrpmultionPg').value;
var grpArry = barGrp.split("_");
chart = new AmCharts.AmSerialChart();
chart.dataProvider = chartData;
chart.categoryField = "Prp";
chart.startDuration = 1;
chart.plotAreaBorderColor = "#DADADA";
chart.plotAreaBorderAlpha = 1;
// this single line makes the chart a bar chart
chart.rotate = false;
chart.depth3D = 10;
chart.angle = 20;

// AXES
// Category
var categoryAxis = chart.categoryAxis;
categoryAxis.gridPosition = "start";
categoryAxis.gridAlpha = 0.1;
categoryAxis.axisAlpha = 0;

// Value
var valueAxis = new AmCharts.ValueAxis();
valueAxis.axisAlpha = 0;
valueAxis.gridAlpha = 0.1;
valueAxis.title = "Qty";
chart.addValueAxis(valueAxis);
chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
var durationAxis = new AmCharts.ValueAxis();
durationAxis.title = "Vlu";
// the following line makes this value axis to convert values to duration
// it tells the axis what duration unit it should use. mm - minute, hh - hour...
// durationAxis.duration = "mm";

durationAxis.gridAlpha = 0;
durationAxis.axisAlpha = 0;
// durationAxis.inside = true;
durationAxis.position = "right";
chart.addValueAxis(durationAxis);

// GRAPHS
// first graph
for (var lo = 0; lo < grpArry.length; lo++) {
var graph1 = new AmCharts.AmGraph();
graph1.type = "column";
graph1.title = chartLst[lo+1];
graph1.valueField = chartLst[lo+1];
graph1.balloonText = chartLst[lo+1]+" [[category]]:[[value]]";
graph1.lineAlpha = 0;
graph1.fillColors = grpColorbar[chartLst[lo+1]];
graph1.labelText = "[[value]]";
graph1.fillAlphas = 1;
graph1.labelPosition = "inside" ;//bar label on top
graph1.columnWidth=0.6; //To Control The Graph With
chart.addGraph(graph1);
}

for (var lo = 0; lo < grpArry.length; lo++) {
var graph3 = new AmCharts.AmGraph();
graph3.type = "line";
graph3.title = chartLst[lo+(1+grpArry.length)];
graph3.valueAxis = durationAxis;
graph3.lineColor = grpColorbar[chartLst[(lo+(1+grpArry.length))]];
graph3.valueField = chartLst[lo+(1+grpArry.length)];
graph3.balloonText = chartLst[lo+(1+grpArry.length)]+" [[category]]: [[value]]";
graph3.lineThickness = 2;
graph3.bullet = "round";
chart.addGraph(graph3);
}
// LEGEND
var legend = new AmCharts.AmLegend();
chart.addLegend(legend);

// WRITE
chart.write(chartwrite);
    
}

function callMultipleBar(chartwrite,title,valueWd) {
var barGrp=document.getElementById('barGrp').value;
var grpArry = barGrp.split("_");
chart = new AmCharts.AmSerialChart();
chart.dataProvider = chartData;
chart.categoryField = "Prp";
chart.startDuration = 1;
chart.plotAreaBorderColor = "#DADADA";
chart.plotAreaBorderAlpha = 1;
// this single line makes the chart a bar chart
chart.rotate = false;
chart.depth3D = 10;
chart.angle = 20;

// AXES
// Category
var categoryAxis = chart.categoryAxis;
categoryAxis.gridPosition = "start";
categoryAxis.gridAlpha = 0.1;
categoryAxis.axisAlpha = 0;

// Value
var valueAxis = new AmCharts.ValueAxis();
valueAxis.axisAlpha = 0;
valueAxis.gridAlpha = 0.1;
valueAxis.title = "Qty";
chart.addValueAxis(valueAxis);
chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
var durationAxis = new AmCharts.ValueAxis();
durationAxis.title = "Avg";
// the following line makes this value axis to convert values to duration
// it tells the axis what duration unit it should use. mm - minute, hh - hour...
// durationAxis.duration = "mm";

durationAxis.gridAlpha = 0;
durationAxis.axisAlpha = 0;
// durationAxis.inside = true;
durationAxis.position = "right";
chart.addValueAxis(durationAxis);

// GRAPHS
// first graph
for (var lo = 0; lo < grpArry.length; lo++) {
var graph1 = new AmCharts.AmGraph();
graph1.type = "column";
graph1.title = chartLst[lo+1];
graph1.valueField = chartLst[lo+1];
graph1.balloonText = chartLst[lo+1]+" [[category]]:[[value]]";
graph1.lineAlpha = 0;
graph1.fillColors = grpColorbar[chartLst[lo+1]];
graph1.labelText = "[[value]]";
graph1.fillAlphas = 1;
graph1.labelPosition = "inside" ;//bar label on top
graph1.columnWidth=0.6; //To Control The Graph With
chart.addGraph(graph1);
}

for (var lo = 0; lo < grpArry.length; lo++) {
var graph3 = new AmCharts.AmGraph();
graph3.type = "line";
graph3.title = chartLst[lo+(1+grpArry.length)];
graph3.valueAxis = durationAxis;
graph3.lineColor = grpColorbar[chartLst[(lo+(1+grpArry.length))]];
graph3.valueField = chartLst[lo+(1+grpArry.length)];
graph3.balloonText = chartLst[lo+(1+grpArry.length)]+" [[category]]: [[value]]";
graph3.lineThickness = 2;
graph3.bullet = "round";
chart.addGraph(graph3);
}
// LEGEND
var legend = new AmCharts.AmLegend();
chart.addLegend(legend);

// WRITE
chart.write(chartwrite);
    
}

function callageGraph(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagesgenerateChartData(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}
function parseMessagesgenerateChartData(responseXML,title,chartwrite,valueWd){
chartLst=[];
chartLst1=[];
chartData=[];
chartData1=[];
chartLst = ['Month','Vlu'];
chartLst1= ['Month','Qty','Cts'];
var counter=0;
var counter1=0;
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
obj = {};
obj1 = {};
counter=0;
counter1=0;
var grpMonth=new String('Month');
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpMonth))[0]).childNodes[0].nodeValue);
var grpVlu=new String('Vlu');
obj[chartLst[counter]]=unescape((columnroot.getElementsByTagName(String(grpVlu))[0]).childNodes[0].nodeValue);
chartData.push(obj);

obj1[chartLst1[counter1++]]=unescape((columnroot.getElementsByTagName(String(grpMonth))[0]).childNodes[0].nodeValue);
var grpQty=new String('Qty');
obj1[chartLst1[counter++]]=unescape((columnroot.getElementsByTagName(String(grpQty))[0]).childNodes[0].nodeValue);
var grpCts=new String('Cts');
obj1[chartLst1[counter++]]=unescape((columnroot.getElementsByTagName(String(grpCts))[0]).childNodes[0].nodeValue);
chartData1.push(obj1);
}
 generateChartData('chartdiv1',title,valueWd);
 createStockChart('chartdiv',title,valueWd);
}
function generateChartData(chartwrite,title,valueWd) {
// SERIAL CHART
chart = new AmCharts.AmSerialChart();
chart.dataProvider = chartData;
chart.categoryField = "Month";
chart.plotAreaBorderAlpha = 0.2;
chart.depth3D = 10;
chart.angle = 20;
// chart.columnSpaceing=15;
//chart.columnSpacing3D=15;

chart.columnWidth=0.2;
// AXES
// category
var categoryAxis = chart.categoryAxis;
categoryAxis.gridAlpha = 0.1;
categoryAxis.axisAlpha = 0;
categoryAxis.gridPosition = "start";


// value
var valueAxis = new AmCharts.ValueAxis();


valueAxis.gridAlpha = 0.1;
valueAxis.axisAlpha = 0;
valueAxis.minimum=50000;
chart.addValueAxis(valueAxis);


// GRAPHS
// first graph
var graph = new AmCharts.AmGraph();
graph.title = chartLst[1];
graph.valueField = chartLst[1];
graph.labelPosition="inside";
graph.type = "column";
graph.fillColors = grpColorbar[chartLst[1]];
graph.lineAlpha = 0;
graph.fillAlphas = 1;
graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
chart.addGraph(graph);
// legend = new AmCharts.AmLegend();
// chart.addLegend(legend);

chart.write(chartwrite);

}

function createStockChart(chartwrite,title,valueWd) {
chart = new AmCharts.AmSerialChart();
chart.dataProvider = chartData1;
chart.marginLeft=555;
chart.categoryField = "Month";
// chart.marginLeft = 165;
// chart.marginRight = 60;
//chart.marginBottom = 410;
chart.plotAreaBorderAlpha = 0.2;
chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title

chart.depth3D = 10;
chart.angle =20;
chart.columnWidth=0.4;


// AXES
// category
var categoryAxis = chart.categoryAxis;
categoryAxis.gridAlpha = 0.1;
categoryAxis.axisAlpha = 0;
categoryAxis.gridPosition = "start";
categoryAxis.labelsEnabled = false;
categoryAxis.axisAlpha = 0;
// value
var valueAxis = new AmCharts.ValueAxis();


valueAxis.gridAlpha = 0.1;
valueAxis.axisAlpha = 0;
chart.addValueAxis(valueAxis);
valueAxis.minimum=5;

// GRAPHS
// first graph
for (var lo = 1; lo < chartLst1.length; lo++) {
var graph = new AmCharts.AmGraph();
graph.title = chartLst1[lo];

graph.valueField = chartLst1[lo];
graph.type = "column";
graph.fillColors = grpColorbar[chartLst1[lo]];;
// graph.labelPosition="inside";
graph.lineAlpha = 0;
graph.fillAlphas = 1;

graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
chart.addGraph(graph);
}
chart.write(chartwrite);
}

function monthWiselinegraph(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDD")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  var ttl=document.getElementById(fieldVal+'_TTL').value;
  commonmonthWiseLineChart(url+"&key="+unescape(fieldVal),title,fieldVal);
  }
  }
  }
}
function commonmonthWiseLineChart(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommonmonthWiseLineChart(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescommonmonthWiseLineChart(responseXML,title,chartwrite,valueWd){
var lineGrp=document.getElementById('lineGrp').value
chartLst=[];
chartData=[];
chartLst = ['MonthYear'];
var counter=1;
var grpArry = lineGrp.split("_");
for (var lo = 0; lo < grpArry.length; lo++) {
chartLst[counter++] = grpArry[lo];
}
var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
obj = {};
obj[chartLst[0]] = lval;
counter=1;
var lkey = unescape((columnroot.getElementsByTagName("attrVal")[0]).childNodes[0].nodeValue);
var lkeyArry = lkey.split("XML");
for (var lk = 0; lk < lkeyArry.length; lk++) {
obj[chartLst[counter++]] = lkeyArry[lk];
}
chartData.push(obj);
}
callmonthWiseLine(chartwrite,title,valueWd);
}

function callmonthWiseLine(chartwrite,title,valueWd) {
    // SERIAL CHART
        // SERIAL CHART    
    chart = new AmCharts.AmSerialChart();
    chart.pathToImages = "http://www.amcharts.com/lib/3/images/";
    chart.dataProvider = chartData;
    chart.categoryField = "MonthYear";
    chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
    // first value axis (on the left)
    var valueAxis1 = new AmCharts.ValueAxis();
    valueAxis1.axisColor = "#FF6600";
    valueAxis1.axisThickness = 2;
    valueAxis1.gridAlpha = 0;
    valueAxis1.title = chartLst[1];
//    valueAxis1.minimum=0;
    chart.addValueAxis(valueAxis1);
    
    // second value axis (on the right) 
    var valueAxis2 = new AmCharts.ValueAxis();
    valueAxis2.position = "right"; // this line makes the axis to appear on the right
    valueAxis2.axisColor = "#FCD202";
    valueAxis2.gridAlpha = 0;
    valueAxis2.axisThickness = 2;
    valueAxis2.title = chartLst[2];
//    valueAxis2.minimum=0;
    chart.addValueAxis(valueAxis2);
    
    // GRAPHS
    // first graph
    var graph1 = new AmCharts.AmGraph();
    graph1.valueAxis = valueAxis1; // we have to indicate which value axis should be used
    graph1.title = chartLst[1];
    graph1.valueField = chartLst[1];
    graph1.bullet = bullet[1];
    graph1.balloonText = chartLst[1]+" [[category]]: [[value]]";
    graph1.hideBulletsCount = 30;
    graph1.bulletBorderThickness = 1;
    chart.addGraph(graph1);
    
    // second graph                
    var graph2 = new AmCharts.AmGraph();
    graph2.valueAxis = valueAxis2; // we have to indicate which value axis should be used
    graph2.title = chartLst[2];
    graph2.valueField = chartLst[2];
    graph2.bullet = bullet[2];
    graph2.balloonText = chartLst[2]+" [[category]]: [[value]]";
    graph2.hideBulletsCount = 30;
    graph2.bulletBorderThickness = 1;
    chart.addGraph(graph2);
    
    
    // CURSOR
    var chartCursor = new AmCharts.ChartCursor();
    chartCursor.cursorPosition = "mouse";
    chart.addChartCursor(chartCursor);
    
    // SCROLLBAR
    var chartScrollbar = new AmCharts.ChartScrollbar();
    chart.addChartScrollbar(chartScrollbar);
    
    // LEGEND
    var legend = new AmCharts.AmLegend();
    legend.marginLeft = 110;
    legend.useGraphSettings = true;
    chart.addLegend(legend);
    
    // WRITE
    chart.write(chartwrite);
    
}

function callcreatedoubleBarGraph(url,title,wdth,ht){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_HIDD")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  var ttl=document.getElementById(fieldVal+'_TTL').value;
  commondoubleBarGraph(url+"&grid="+unescape(fieldVal),ttl+" - "+title,fieldVal);
  }
  }
  }
}

function commondoubleBarGraph(url,title,chartwrite){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommondoubleBarGraph(req.responseXML,title,chartwrite,10);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescommondoubleBarGraph(responseXML,title,chartwrite,valueWd){
var barGrp=document.getElementById('barGrp').value;
chartLst=[];
chartData=[];
chartLst = ['nme'];
var counter=1;
var grpArry = barGrp.split("_");
var grp;
for (grp = 0; grp < grpArry.length; grp++) {
chartLst[counter++] = grpArry[grp];
}

var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
obj = {};
counter=1;
obj[chartLst[0]] = lval;
for (grp = 0; grp < grpArry.length; grp++) {
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpArry[grp]))[0]).childNodes[0].nodeValue);
}
chartData.push(obj);
}
calldoubleBarGraph(chartwrite,title,valueWd);
}

function calldoubleBarGraph(chartwrite,title,valueWd) {
var barGrp=document.getElementById('barGrp').value;
var grpArry = barGrp.split("_");
chart = new AmCharts.AmSerialChart();
chart.dataProvider = chartData;
chart.categoryField = "nme";
chart.startDuration = 3;
chart.plotAreaBorderColor = "#DADADA";
chart.plotAreaBorderAlpha = 1;
// this single line makes the chart a bar chart
if (barGrp.indexOf("Total")!=-1){
chart.rotate = false;
}else{
chart.rotate = false;
}
chart.depth3D = 10;
chart.angle = 20;
chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
    // AXES
    // Category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.gridPosition = "start";
    categoryAxis.gridAlpha = 0.1;
    categoryAxis.axisAlpha = 0;

    // Value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.axisAlpha = 0;
    valueAxis.gridAlpha = 0.1;
    valueAxis.position = "top";
    valueAxis.minimum=0;
    chart.addValueAxis(valueAxis);

    // GRAPHS
    // first graph
    for (var lo = 0; lo < grpArry.length; lo++) {
    var graph1 = new AmCharts.AmGraph();
    graph1.type = "column";
    graph1.title = grpArry[lo];
    graph1.valueField = grpArry[lo];
    if (barGrp.indexOf("Total")!=-1){
    graph1.balloonText = "[[category]] "+grpArry[lo]+": [[value]]";
    graph1.columnWidth=0.4; 
    }else{
    graph1.balloonText = "[[category]] "+grpArry[lo]+": [[value]]";
    }
    graph1.lineAlpha = 0;
    graph1.fillAlphas = 1;
    graph1.labelText = "[[value]]";
    graph1.labelPosition = "inside" ;//bar label on top
    chart.addGraph(graph1);
    }
    // LEGEND
    var legend = new AmCharts.AmLegend();
    chart.addLegend(legend);

    // WRITE
    chart.write(chartwrite);
    
}


function callcreatedoubleBarGraphonpagemultiplepage(url,title,wdth,ht,barGrpnm){
 url = url.replace('+', '%2B');
 url=unescape(url);
 var frm_elements = document.getElementsByTagName('input');
 for(i=0; i<frm_elements.length; i++) {
  field_type = frm_elements[i].type.toLowerCase();
  if(field_type=='hidden'){
  var fieldId = frm_elements[i].id;
  if (fieldId.indexOf("_MULTIAGE")!=-1){
  var fieldVal=document.getElementById(fieldId).value;
  var ttl=document.getElementById(fieldVal+'_TTL').value;
  commondoubleBarGraphmultiplepage(url+"&grid="+unescape(fieldVal),ttl+" - "+title,fieldVal,barGrpnm);
  }
  }
  }
}
function commondoubleBarGraphmultiplepage(url,title,chartwrite,barGrpnm){
url = url.replace('+', '%2B');
url=unescape(url);
var req = initRequest();
req.onreadystatechange = function() {
if (req.readyState == 4) {
if (req.status == 200) {
parseMessagescommondoubleBarGraphmultiplepage(req.responseXML,title,chartwrite,10,barGrpnm);
} else if (req.status == 204){
}
}
};
req.open("GET", url, true);
req.send(null);
}

function parseMessagescommondoubleBarGraphmultiplepage(responseXML,title,chartwrite,valueWd,barGrpnm){
var barGrp=document.getElementById(barGrpnm).value;
chartLst=[];
chartData=[];
chartLst = ['nme'];
var counter=1;
var grpArry = barGrp.split("_");
var grp;
for (grp = 0; grp < grpArry.length; grp++) {
chartLst[counter++] = grpArry[grp];
}

var columns = responseXML.getElementsByTagName("materTag")[0];
for (var loop = 0; loop < columns.childNodes.length; loop++) {
var columnroot = columns.childNodes[loop];
var lval = unescape((columnroot.getElementsByTagName("attrNme")[0]).childNodes[0].nodeValue);
obj = {};
counter=1;
obj[chartLst[0]] = lval;
for (grp = 0; grp < grpArry.length; grp++) {
obj[chartLst[counter++]]=unescape((columnroot.getElementsByTagName(String(grpArry[grp]))[0]).childNodes[0].nodeValue);
}
chartData.push(obj);
}
calldoubleBarGraphmultiplepage(chartwrite,title,valueWd,barGrpnm);
}


function calldoubleBarGraphmultiplepage(chartwrite,title,valueWd,barGrpnm) {
var barGrp=document.getElementById(barGrpnm).value;
var grpArry = barGrp.split("_");
chart = new AmCharts.AmSerialChart();
chart.dataProvider = chartData;
chart.categoryField = "nme";
chart.startDuration = 3;
chart.plotAreaBorderColor = "#DADADA";
chart.plotAreaBorderAlpha = 1;
// this single line makes the chart a bar chart
if (barGrp.indexOf("Total")!=-1){
chart.rotate = false;
}else{
chart.rotate = false;
}
chart.depth3D = 10;
chart.angle = 20;
chart.addTitle(title, 15, "#305378", 2, true)//Pie Chart title
    // AXES
    // Category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.gridPosition = "start";
    categoryAxis.gridAlpha = 0.1;
    categoryAxis.axisAlpha = 0;

    // Value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.axisAlpha = 0;
    valueAxis.gridAlpha = 0.1;
    valueAxis.position = "top";
    valueAxis.minimum=0;
    chart.addValueAxis(valueAxis);

    // GRAPHS
    // first graph
    for (var lo = 0; lo < grpArry.length; lo++) {
    var graph1 = new AmCharts.AmGraph();
    graph1.type = "column";
    graph1.title = grpArry[lo];
    graph1.valueField = grpArry[lo];
    if (barGrp.indexOf("Total")!=-1){
    graph1.balloonText = "[[category]] "+grpArry[lo]+": [[value]]";
    graph1.columnWidth=0.4; 
    }else{
    graph1.balloonText = "[[category]] "+grpArry[lo]+": [[value]]";
    }
    graph1.lineAlpha = 0;
    graph1.fillAlphas = 1;
    graph1.labelText = "[[value]]";
    graph1.labelPosition = "inside" ;//bar label on top
    chart.addGraph(graph1);
    }
    // LEGEND
    var legend = new AmCharts.AmLegend();
    chart.addLegend(legend);

    // WRITE
    chart.write(chartwrite);
    
}
function setLabelPosition() {
    if (document.getElementById("rb1").checked) {
        chart.labelRadius = 30;
        chart.labelText = "[[title]]: [[value]]";
    } else {
        chart.labelRadius = -30;
        chart.labelText = "[[percents]]%";
    }
    chart.validateNow();
}


// makes chart 2D/3D                   
function set3D() {
   // if (document.getElementById("rb3").checked) {
        chart.depth3D = 10;
        chart.angle = 10;
  //  } else {
      //  chart.depth3D = 0;
    //    chart.angle = 0;
  //  }
    chart.validateNow();
}

// changes switch of the legend (x or v)
function setSwitch() {
    if (document.getElementById("rb5").checked) {
        legend.switchType = "x";
    } else {
        legend.switchType = "v";
    }
    legend.validateNow();
}