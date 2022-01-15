<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"%>

<html>

<head>
<style>
		/* styles unrelated to zoom */
		* { border:0; margin:0; padding:0; }
		p { position:absolute; top:3px; right:28px; color:#555; font:bold 13px/1 sans-serif;}

		/* these styles are for the demo, but are not required for the plugin */
		.zoom {
			display:inline-block;
			position: relative;
                        padding:10px;
		}
		
		/* magnifying glass icon */
		.zoom:after {
			content:'';
			display:block; 
			width:33px; 
			height:33px; 
			position:absolute; 
			top:0;
			right:0;
			background:url(icon.png);
		}

		.zoom img {
			display: block;
		}

		.zoom img::selection { background-color: transparent; }

		#ex2 img:hover { cursor: url(grab.cur), default; }
		#ex2 img:active { cursor: url(grabbed.cur), default; }
	</style>
	<script src='jquery/jquery.min.js?v=2'></script>
        
<script src="scripts/zoom-pic-intro-3.0.js?v=2" type="text/javascript"></script>
<script>
		$(document).ready(function(){
			$('#ex1').zoom();
		});
	</script>
</head>

<body>

<%

String fileNme = request.getParameter("fileNme");

String height = request.getParameter("ht");
String width = request.getParameter("wd");
String cnt = request.getParameter("cnt");
if(width==null)
width="600";
if(cnt==null){
%>
<span class='zoom' id='ex1'>
<img src="<%=fileNme%>" width="<%=width%>"  alt='Daisy on the Ohoopee'/>
</span>
<%}else if(cnt.equals("ag")){
width="";
if(fileNme.indexOf("CERTIMG") > -1)
width="800";
%>
<div style="padding:25px;">
<img src="<%=fileNme%>" width="<%=width%>"/>
</div>
<%}else{%>
<span class='zoom' id='ex1'>
<img src="<%=fileNme%>" width="<%=width%>"  alt='Daisy on the Ohoopee'/>
</span>
<%}%>
</body>

</html>