/*====================================================
	- HTML Table Filter Generator v1.7
	- By Max Guglielmi
	- mguglielmi.free.fr/scripts/TableFilter/
	- please do not change this comment
	- don't forget to give some credit... it's always
	good for the author
	- Special credit to Cedric Wartel, 
	cnx.claude@free.fr, Florent Hirchy and Váry Péter 
	for active contribution and inspiration
=====================================================*/

// global vars
var TF_TblId, TF_TblData, TF_SearchFlt
TF_TblId = [], TF_TblData = {};


function setFilterGrid(id)
/*====================================================
	- Checks if id exists and is a table
	- Then looks for additional params 
	- Calls fn that generates grid bar
=====================================================*/
{	
    var tbl = tf_Id(id);
    var ref_row, ncells, fObj;
    if(tbl != null && tbl.nodeName.toLowerCase() == "table" && tf_GetRowsNb(id) )
    {
        if(arguments.length>1)
        {
            for(var i=0; i<arguments.length; i++)
            {
                var argtype = typeof arguments[i];
               
                switch(argtype.toLowerCase())
                {
                    case "number":
                        ref_row = arguments[i];
                    break;
                    case "object":
                        fObj = arguments[i];
                    break;
                }//switch
                           
            }//for
        }//if
		ref_row==undefined ? ref_row=2 : ref_row=(ref_row+2);
		// ref_row-1: filters row not yet generated
        try{ ncells = tf_GetCellsNb(id,ref_row-1) }
		catch(e){ ncells = tf_GetCellsNb(id,0) }
		
        tbl.tf_ncells = ncells;
        var nrows = tf_GetRowsNb(id);
		       
        if(tbl.tf_ref_row==undefined) tbl.tf_ref_row = parseInt(ref_row);
        tbl.tf_Obj = fObj;
        if(!tf_HasGrid(id) && nrows>1) tf_AddGrid(id);
    }//if tbl!=null	
}

function tf_AddGrid(id)
/*====================================================
	- adds row with filtering grid bar and 
	sets grid options
=====================================================*/
{
	TF_TblId.push(id);
	var t = tf_Id(id);
	var f = t.tf_Obj, n = t.tf_ncells, infdiv = null;	
	var inpclass, fltgrid, displayBtn, btntext, enterkey;
	var modfilter_fn, display_allText, on_slcChange;
	var displaynrows, totrows_text, btnreset, btnreset_text;
	var sort_slc, displayPaging, pagingLength, displayLoader;
	var load_text, exactMatch, alternateBgs, colOperation;
	var rowVisibility, colWidth, bindScript, refreshFilters;
	var sortNumAsc, sortNumDesc, matchCase, resultsPerPage;
	var filtersRowIndex, rememberGridValues, loaderHtml;
	var btnResetHtml, pagingBtns, btnNextPageText, btnLastPageText;
	var btnPrevPageText, btnFirstPageText, btnNextPageHtml;
	var btnPrevPageHtml, btnLastPageHtml, btnFirstPageHtml;
	
	fltgrid = 				f!=undefined && f.grid==false ? false : true; //enables/disables filter grid
	displayBtn = 			f!=undefined && f.btn ? true : false; //show/hides filter's validation button
	btntext = 				f!=undefined && f.btn_text!=undefined ? f.btn_text : "go"; //defines button text
	enterkey = 				f!=undefined && f.enter_key==false ? false : true; //enables/disables enter key
	modfilter_fn = 			f!=undefined && f.mod_filter_fn ? true : false; //defines alternative fn
	display_allText = 		f!=undefined && f.display_all_text!=undefined	? f.display_all_text : ""; //defines 1st option text
	on_slcChange = 			f!=undefined && f.on_change==false ? false : true; //enables/disables onChange event on combo-box 
	displaynrows = 			f!=undefined && f.rows_counter ? true : false; //show/hides rows counter
	totrows_text = 			f!=undefined && f.rows_counter_text!=undefined
		? f.rows_counter_text : "Displayed rows: "; //defines rows counter text
	btnreset = 				f!=undefined && f.btn_reset ? true : false; //show/hides reset link
	btnreset_text = 		f!=undefined && f.btn_reset_text!=undefined ? f.btn_reset_text : "Reset"; //defines reset text
	sort_slc = 				f!=undefined && f.sort_select ? true : false; //enables/disables select options sorting
	displayPaging = 		f!=undefined && f.paging ? true : false; //enables/disables table paging
	pagingLength = 			f!=undefined && f.paging_length!=undefined ? f.paging_length : 10; //defines table paging length
	displayLoader = 		f!=undefined && f.loader ? true : false; //enables/disables loader
	load_text = 			f!=undefined && f.loader_text!=undefined ? f.loader_text : "Loading..."; //defines loader text
	exactMatch = 			f!=undefined && f.exact_match ? true : false; //enables/disbles exact match for search
	alternateBgs = 			f!=undefined && f.alternate_rows ? true : false; //enables/disbles rows alternating bg colors
	colOperation = 			f!=undefined && f.col_operation ? true : false; //enables/disbles column operation(sum,mean)
	rowVisibility = 		f!=undefined && f.rows_always_visible ? true : false; //makes a row always visible
	colWidth = 				f!=undefined && f.col_width ? true : false; //defines widths of columns
	bindScript = 			f!=undefined && f.bind_script ? true : false;
	refreshFilters = 		f!=undefined && f.refresh_filters ? true : false; //refreshes drop-down lists upon validation
	sortNumAsc = 			f!=undefined && f.sort_num_asc ? true : false; //enables/disables ascending numeric options sorting
	sortNumDesc = 			f!=undefined && f.sort_num_desc ? true : false; //enables/disables descending numeric options sorting
	matchCase = 			f!=undefined && f.match_case ? true : false; //enables/disables case sensitivity
	resultsPerPage = 		f!=undefined && f.results_per_page ? true : false; //enables/disables results per page drop-down
	filtersRowIndex = 		f!=undefined && f.filters_row_index!=undefined
		? f.filters_row_index>1 ? 1 : f.filters_row_index : 0; //defines in which row filters grid is generated
	rememberGridValues =	f!=undefined && f.remember_grid_values ? true : false; //remembers filters values on page load
	loaderHtml = 			f!=undefined && f.loader_html!=undefined ? f.loader_html : null; //defines loader innerHtml
	btnResetHtml = 			f!=undefined && f.btn_reset_html!=undefined ? f.btn_reset_html : null; //defines reset button innerHtml
	btnNextPageText = 		f!=undefined && f.btn_next_page_text!=undefined
		? f.btn_next_page_text : ">"; //defines next page button text
	btnPrevPageText = 		f!=undefined && f.btn_prev_page_text!=undefined
		? f.btn_prev_page_text : "<"; //defines previous page button text
	btnLastPageText= 		f!=undefined && f.btn_last_page_text!=undefined
		? f.btn_last_page_text : ">|"; //defines last page button text
	btnFirstPageText = 		f!=undefined && f.btn_first_page_text!=undefined
		? f.btn_first_page_text : "|<" ; //defines first page button text
	btnNextPageHtml = 		f!=undefined && f.btn_next_page_html!=undefined
		? f.btn_next_page_html : null; //defines next page button html
	btnPrevPageHtml = 		f!=undefined && f.btn_prev_page_html!=undefined
		? f.btn_prev_page_html : null; //defines previous page button html
	btnFirstPageHtml = 		f!=undefined && f.btn_first_page_html!=undefined
		? f.btn_first_page_html : null; //defines last page button html
	btnLastPageHtml = 		f!=undefined && f.btn_last_page_html!=undefined
		? f.btn_last_page_html : null; //defines previous page button html
	pagingBtns = 			f!=undefined && f.pagingButtons==false ? false : true; //enables/disables paging buttons
	
	// props are added to table in order to be easily accessible from other fns
	t.tf_fltGrid			=	fltgrid;
	t.tf_displayBtn			= 	displayBtn;
	t.tf_btnText			=	btntext;
	t.tf_enterKey			= 	enterkey;
	t.tf_isModfilter_fn		= 	modfilter_fn;
	t.tf_display_allText 	= 	display_allText;
	t.tf_on_slcChange 		= 	on_slcChange;
	t.tf_rowsCounter 		= 	displaynrows;
	t.tf_rowsCounter_text	= 	totrows_text;
	t.tf_btnReset 			= 	btnreset;
	t.tf_btnReset_text 		= 	btnreset_text;
	t.tf_sortSlc 			=	sort_slc;
	t.tf_displayPaging 		= 	displayPaging;
	t.tf_pagingLength 		= 	pagingLength;
	t.tf_displayLoader		= 	displayLoader;
	t.tf_loadText			= 	load_text;
	t.tf_exactMatch 		= 	exactMatch;
	t.tf_alternateBgs		=	alternateBgs;
	t.tf_refreshFilters		=	refreshFilters;
	t.tf_nRows				=	0;
	t.tf_hiddenRows			=	0;
	t.tf_startPagingRow		= 	0;
	t.tf_activeFilter		=	null;
	t.tf_matchCase			=	matchCase;
	t.tf_filtersRowIndex	=	filtersRowIndex;
	t.tf_rememberGridValues	=	rememberGridValues;
	t.tf_loaderHtml			=	loaderHtml;
	t.tf_btnResetHtml		=	btnResetHtml;
	t.tf_btnNextPageText	=	btnNextPageText;
	t.tf_btnPrevPageText	=	btnPrevPageText;
	t.tf_btnLastPageText	=	btnLastPageText;
	t.tf_btnFirstPageText	=	btnFirstPageText;
	t.tf_btnNextPageHtml	=	btnNextPageHtml;
	t.tf_btnPrevPageHtml	=	btnPrevPageHtml;
	t.tf_btnLastPageHtml	=	btnLastPageHtml;
	t.tf_btnFirstPageHtml	=	btnFirstPageHtml;
	t.tf_pagingBtnEvents	=	null;
	
	if(displayLoader) infdiv = tf_SetLoader(id);
	if(modfilter_fn) t.tf_modfilter_fn = f["mod_filter_fn"];// used by tf_DetectKey fn
	if(sortNumAsc) t.tf_sortNumAsc = f["sort_num_asc"];
	if(sortNumDesc) t.tf_sortNumDesc = f["sort_num_desc"];
	if(resultsPerPage)
	{ 
		t.tf_resultsPerPage = f["results_per_page"];
		if(t.tf_resultsPerPage.length<2)
			resultsPerPage=false;
		else
			t.tf_pagingLength = t.tf_resultsPerPage[1][0];
	}
	
	if(!fltgrid) t.tf_ref_row = t.tf_ref_row-1;
	else {
		var fltrow = t.insertRow(filtersRowIndex); //adds filter row
		fltrow.className = "fltrow";
		
		for(var i=0; i<n; i++)// this loop adds filters
		{
			var fltcell = tf_CreateElm("td");
			fltrow.appendChild( fltcell );
			i==n-1 && displayBtn==true ? inpclass = "flt_s" : inpclass = "flt";
			
			if(f==undefined || f["col_"+i]==undefined || f["col_"+i]=="none") 
			{
				var inptype;
				(f==undefined || f["col_"+i]==undefined) ? inptype="text" : inptype="hidden";//show/hide input	
				var inp = tf_CreateElm( "input",["id","flt"+i+"_"+id],
					["type",inptype],["class",inpclass] );					
				inp.className = inpclass;// for ie<=6
				inp.onfocus = function(){ t.tf_activeFilter=this.getAttribute('id'); };
				fltcell.appendChild(inp);
				if(enterkey) inp.onkeypress = tf_DetectKey;
			}
			else if(f["col_"+i]=="select")
			{
				var slc = tf_CreateElm( "select",["id","flt"+i+"_"+id],["class",inpclass] );
				slc.className = inpclass;// for ie<=6
				slc.onfocus = function(){ t.tf_activeFilter=this.getAttribute('id'); };
				fltcell.appendChild(slc);
				(refreshFilters) ? tf_PopulateOptions(id,i,true) : tf_PopulateOptions(id,i);

				if(enterkey) slc.onkeypress = tf_DetectKey;
				if(on_slcChange) 
				{
					(!modfilter_fn) 
						? slc.onchange = function(){ tf_Filter(id); }  
						: slc.onchange = f["mod_filter_fn"];
				} 
			}
			
			if(i==n-1 && displayBtn==true)// this adds button
			{
				var btn = tf_CreateElm(
										"input",
										["id","btn"+i+"_"+id],["type","button"],
										["value",btntext],["class","btnflt"] 
									);
				btn.className = "btnflt";
				
				fltcell.appendChild(btn);
				(!modfilter_fn) ? btn.onclick = function(){ tf_Filter(id); } : btn.onclick = f["mod_filter_fn"];					
			}//if		
			
		}// for i		
	}//if fltgrid

	if(displaynrows || btnreset || displayPaging || resultsPerPage)
	{//if loader set true infdiv already generated
		if(infdiv==null)
		{
			/*** div containing rows # displayer + reset btn ***/
			infdiv = tf_CreateElm( "div",["id","inf_"+id],["class","inf"] );
			infdiv.className = "inf";// setAttribute method for class attribute doesn't seem to work on ie<=6
			t.parentNode.insertBefore(infdiv, t);
		}
		
		/*** left div containing rows # displayer ***/
		var ldiv = tf_CreateElm( "div",["id","ldiv_"+id] );
		ldiv.className = "ldiv";
		infdiv.appendChild(ldiv);		
		
		/*** 	right div containing reset button 
				+ nb results per page select 	***/	
		var rdiv = tf_CreateElm( "div",["id","rdiv_"+id] );
		rdiv.className = "rdiv";
		infdiv.appendChild(rdiv);
		
		/*** mid div containing paging displayer ***/
		var mdiv = tf_CreateElm( "div",["id","mdiv_"+id] );
		mdiv.className = "mdiv";						
		infdiv.appendChild(mdiv);
		
		if(displaynrows)
		{
			var totrows;
			displayPaging ? totrows = pagingLength : totrows = tf_GetRowsNb(id);
			
			var totrows_span = tf_CreateElm( "span",
				["id","totrows_span_"+id],["class","tot"] ); // tot # of rows displayer
			totrows_span.className = "tot";//for ie<=6
			totrows_span.appendChild( tf_CreateText(totrows) );
		
			var totrows_txt = tf_CreateText(totrows_text);
			ldiv.appendChild(totrows_txt);
			ldiv.appendChild(totrows_span);
		}
				
		if(displayPaging)
		{			
			var start_row = t.tf_ref_row;
			var row = tf_Tag(t,"tr");
			var nrows = row.length;
			var npages = Math.ceil( (nrows - start_row)/pagingLength );//calculates page nb
			
			// Paging drop-down list
			var slcPages = tf_CreateElm( "select",
				["id","slcPages_"+id],["class","pgSlc"] );
			slcPages.className = 'pgSlc';// for ie<=6
			slcPages.onchange = function(){ tf_ChangePage(id); }
			
			var btnNextSpan, btnPrevSpan, btnLastSpan, btnFirstSpan;
			btnNextSpan = tf_CreateElm("span",["id","btnNextSpan_"+id]);
			btnPrevSpan = tf_CreateElm("span",["id","btnPrevSpan_"+id]);
			btnLastSpan = tf_CreateElm("span",["id","btnLastSpan_"+id]);
			btnFirstSpan = tf_CreateElm("span",["id","btnFirstSpan_"+id]);
			
			if(pagingBtns)
			{
				if(btnNextPageHtml==null)
				{// Next button
					var btn_next = tf_CreateElm( 
						"input",["id","btnNext_"+id],["type","button"],
						["value",btnNextPageText],["title","Next"],["class","pgInp"] );
					btn_next.className = 'pgInp';// for ie<=6
					btn_next.onclick = function(){ btnFn.next(); }
					btnNextSpan.appendChild(btn_next);
				} else {
					btnNextSpan.innerHTML = btnNextPageHtml;
					btnNextSpan.onclick = function(){ btnFn.next(); };
				}
				
				if(btnPrevPageHtml==null)
				{// Previous button
					var btn_prev = tf_CreateElm( 
						"input",["id","btnPrev_"+id],["type","button"],
						["value",btnPrevPageText],["title","Previous"],["class","pgInp"] );
					btn_prev.className = 'pgInp';// for ie<=6
					btn_prev.onclick = function(){ btnFn.prev(); }
					btnPrevSpan.appendChild(btn_prev);
				} else { 
					btnPrevSpan.innerHTML = btnPrevPageHtml;
					btnPrevSpan.onclick = function(){ btnFn.prev(); };
				}
				
				if(btnLastPageHtml==null)
				{// Last button
					var btn_last = tf_CreateElm( 
						"input",["id","btnLast_"+id],["type","button"],
						["value",btnLastPageText],["title","Last"],["class","pgInp"] );
					btn_last.className = 'pgInp';// for ie<=6
					btn_last.onclick = function(){ btnFn.last(); }
					btnLastSpan.appendChild(btn_last);
				} else { 
					btnLastSpan.innerHTML = btnLastPageHtml;
					btnLastSpan.onclick = function(){ btnFn.last(); };
				}
				
				if(btnFirstPageHtml==null)
				{// First button
					var btn_first = tf_CreateElm( 
						"input",["id","btnFirst_"+id],["type","button"],
						["value",btnFirstPageText],["title","First"],["class","pgInp"] );
					btn_first.className = 'pgInp';// for ie<=6
					btn_first.onclick = function(){ btnFn.first(); }
					btnFirstSpan.appendChild(btn_first);
				} else { 
					btnFirstSpan.innerHTML = btnFirstPageHtml;
					btnFirstSpan.onclick = function(){ btnFn.first(); };
				}			
			}//if pagingBtns
			
			// paging elements (buttons+drop-down list) are added to mdiv
			if(displayPaging) mdiv.appendChild(btnPrevSpan);
			if(displayPaging) mdiv.appendChild(btnFirstSpan);
			mdiv.appendChild( tf_CreateText(" Page ") );
			mdiv.appendChild(slcPages);
			mdiv.appendChild( tf_CreateText(" of ") );
			var pgspan = tf_CreateElm( "span",["id","pgspan_"+id],["class","nbpg"] );
			pgspan.className = 'nbpg';// for ie<=6
			pgspan.appendChild( tf_CreateText(" "+npages+" ") );
			mdiv.appendChild(pgspan);
			if(displayPaging) mdiv.appendChild(btnLastSpan);
			if(displayPaging) mdiv.appendChild(btnNextSpan);	
			
			var btnFn = {// onclick event for paging buttons
				slc: tf_Id("slcPages_"+id),
				slcIndex: function(){ return this.slc.options.selectedIndex },
				nbOpts: function(){ return parseInt(this.slc.options.length)-1 },
				next: function(){
					var nextIndex = this.slcIndex()<this.nbOpts() ? this.slcIndex()+1 : 0;
					this.slc.options[nextIndex].selected = true;
					tf_ChangePage(id);
				},
				prev: function(){
					var nextIndex = this.slcIndex()>0 ? this.slcIndex()-1 : this.nbOpts();
					this.slc.options[nextIndex].selected = true;
					tf_ChangePage(id);
				},
				last: function(){
					this.slc.options[this.nbOpts()].selected = true;
					tf_ChangePage(id);
				},
				first: function(){
					this.slc.options[0].selected = true;
					tf_ChangePage(id);
				}
			};
			
			t.tf_pagingBtnEvents = btnFn;
			
			for(var j=start_row; j<nrows; j++)//this sets rows to validRow=true
				row[j].setAttribute("validRow","true");
			
			tf_SetPagingInfo(id);
		}
		
		if(resultsPerPage && displayPaging)
		{
			var slcR = tf_CreateElm( "select",["id","slc_results_"+id],["class","rspg"] );
			slcR.className = 'rspg';// for ie<=6
			var slcRText = t.tf_resultsPerPage[0], slcROpts = t.tf_resultsPerPage[1];
			rdiv.appendChild(tf_CreateText(slcRText));
			rdiv.appendChild(slcR);
			
			for(var r=0; r<slcROpts.length; r++)
			{
				var currOpt = new Option(slcROpts[r],slcROpts[r],false,false);
				tf_Id("slc_results_"+id).options[r] = currOpt;
			}
			slcR.onchange = function(){ tf_ChangeResultsPerPage(id); }
		}
		
		if(btnreset && fltgrid)
		{	
			var resetspan = tf_CreateElm("span",["id","resetspan_"+id]);
			rdiv.appendChild(resetspan);	
			if(btnResetHtml==null)
			{	
				var fltreset = tf_CreateElm( "a",
								["href","javascript:tf_ClearFilters('"+id+"');" +
									"tf_Filter('"+id+"');"] );
				fltreset.appendChild(tf_CreateText(btnreset_text));
				resetspan.appendChild(fltreset);
			} else {
				resetspan.innerHTML = btnResetHtml;
				var resetEl = resetspan.firstChild;
				resetEl.onclick = function(){ tf_ClearFilters(id);tf_Filter(id); };
			}
		}
		
	}//if displaynrows etc.
	
	if(colWidth)
	{
		t.tf_colWidth = f.col_width;
		tf_SetColWidths(id);
	}
	
	if(rememberGridValues) tf_ResetGridValues(id,'tf_'+id);
	
	if(alternateBgs && !displayPaging)
		tf_SetAlternateRows(id);

	if(rowVisibility)
	{
		t.tf_rowVisibility = f["rows_always_visible"];
		if(displayPaging) tf_SetVisibleRows(id);
	}
	
	if(colOperation)
	{
		t.tf_colOperation = f["col_operation"];
		tf_SetColOperation(id);
	}
		
	if(bindScript)
	{
		t.tf_bindScript = f["bind_script"];
		if(	t.tf_bindScript!=undefined &&
			t.tf_bindScript["target_fn"]!=undefined )
		{//calls a fn if defined  
			t.tf_bindScript["target_fn"].call(null,id);
		}
	}//if bindScript
	
	if(displayLoader) tf_ShowLoader(id,"none");
}

function tf_PopulateOptions(id,cellIndex,isRefreshed)
/*====================================================
	- populates select
	- adds only 1 occurence of a value
=====================================================*/
{
	var t = tf_Id(id);
	var ncells = t.tf_ncells, opt0txt = t.tf_display_allText;
	var sort_opts = t.tf_sortSlc, paging = t.tf_displayPaging;
	var start_row = t.tf_ref_row;
	var sort_num_asc = t.tf_sortNumAsc, sort_num_desc = t.tf_sortNumDesc;
	var matchCase = t.tf_matchCase;
	var row = tf_Tag(t,"tr");
	var OptArray = [];
	var optIndex = 0; // option index
	var currOpt = new Option(opt0txt,"",false,false); //1st option
	tf_Id("flt"+cellIndex+"_"+id).options[optIndex] = currOpt;

	for(var k=start_row; k<row.length; k++)
	{
		var cell = tf_GetChildElms(row[k]).childNodes;
		var nchilds = cell.length;
		
		if(nchilds == ncells){// checks if row has exact cell #
			
			for(var j=0; j<nchilds; j++)// this loop retrieves cell data
			{
				if((cellIndex==j && !isRefreshed) || (cellIndex==j && isRefreshed && row[k].style.display == ""))
				{
					var cell_data = tf_GetCellText(cell[j]);
					var cell_string = cell_data.tf_MatchCase(matchCase).tf_Trim();//Váry Péter's patch
					// checks if celldata is already in array
					var isMatched = false, arr_string;
					for(var w=0; w<OptArray.length; w++)
					{
						arr_string = OptArray[w].tf_MatchCase(matchCase).tf_Trim();
						if(cell_string==arr_string) isMatched = true;
					}
					if(!isMatched) OptArray.push(cell_data);
				}//if cellIndex==j
			}//for j
		}//if
	}//for k
	
	if(sort_opts) OptArray.sort();
	if(sort_num_asc)
	{
		for(var u=0; u<sort_num_asc.length; u++)
		{
			if(sort_num_asc[u]==cellIndex)
			{
				try{ OptArray.sort( tf_NumSortAsc ); }
				catch(e){}//in case there are alphanumeric values
			}
		}//for u
	}//if sort_num_asc
	if(sort_num_desc)
	{
		for(var z=0; z<sort_num_desc.length; z++)
		{
			if(sort_num_desc[z]==cellIndex)
			{
				try{ OptArray.sort( tf_NumSortDesc ); }
				catch(e){}//in case there are alphanumeric values
			}
		}//for z
	}//if sort_num_desc
	
	for(var y=0; y<OptArray.length; y++)
	{/*for(y in OptArray)// compatibility issue with prototype*/
		optIndex++;
		var currOpt = new Option(OptArray[y],OptArray[y],false,false);
		tf_Id("flt"+cellIndex+"_"+id).options[optIndex] = currOpt;
	}	
}

function tf_Filter(id)
/*====================================================
	- Filtering fn
	- gets search strings from TF_SearchFlt array
	- retrieves data from each td in every single tr
	and compares to search string for current
	column
	- tr is hidden if all search strings are not 
	found
=====================================================*/
{
	TF_SearchFlt = tf_GetFilters(id);
	var t = tf_Id(id);
	if(t.tf_displayLoader) tf_ShowLoader(id,"");
	t.tf_Obj!=undefined ? fprops = t.tf_Obj : fprops = [];
	var SearchArgs = [];
	var ncells = tf_GetCellsNb(id);
	var totrows = tf_GetRowsNb(id), hiddenrows = 0;
	var ematch = t.tf_exactMatch;
	var matchCase = t.tf_matchCase;
	var showPaging = t.tf_displayPaging;
	var rememberGridValues = t.tf_rememberGridValues;

	for(var i=0; i<TF_SearchFlt.length; i++)
		SearchArgs.push( (tf_Id(TF_SearchFlt[i]).value).tf_MatchCase(matchCase).tf_Trim() );
	
	var start_row = t.tf_ref_row;
	var row = tf_Tag(t,"tr");
	
	for(var k=start_row; k<row.length; k++)
	{
		/*** if table already filtered some rows are not visible ***/
		if(row[k].style.display == "none") row[k].style.display = "";
				
		var cell = tf_GetChildElms(row[k]).childNodes;
		var nchilds = cell.length;

		if(nchilds == ncells)// checks if row has exact cell #
		{
			var cell_value = [];
			var occurence = [];
			var isRowValid = true;
				
			for(var j=0; j<nchilds; j++)
			{// this loop retrieves cell data
				var cell_data = tf_GetCellText(cell[j]).tf_MatchCase(matchCase);
				cell_value.push(cell_data);
				
				if(SearchArgs[j]!="")
				{
					var num_cell_data = parseFloat(cell_data);
					var re_le = new RegExp('<='), re_ge = new RegExp('>=');
					var re_l = new RegExp('<'), re_g = new RegExp('>');
					// first checks if there is any operator (<,>,<=,>=)
					if(re_le.test(SearchArgs[j]) && !isNaN(num_cell_data))
						num_cell_data <= parseFloat(SearchArgs[j].replace(re_le,"")) ? occurence[j] = true : occurence[j] = false;
					
					else if(re_ge.test(SearchArgs[j]) && !isNaN(num_cell_data))
						num_cell_data >= parseFloat(SearchArgs[j].replace(re_ge,"")) ? occurence[j] = true : occurence[j] = false;
					
					else if(re_l.test(SearchArgs[j]) && !isNaN(num_cell_data))
						num_cell_data < parseFloat(SearchArgs[j].replace(re_l,"")) ? occurence[j] = true : occurence[j] = false;
										
					else if(re_g.test(SearchArgs[j]) && !isNaN(num_cell_data))
						num_cell_data > parseFloat(SearchArgs[j].replace(re_g,"")) ? occurence[j] = true : occurence[j] = false;					
					
					else 
					{						
						// Improved by Cedric Wartel (cwl)
						// automatic exact match for selects and special characters are now filtered
						var regexp;
						(matchCase) ? modifier = "g" : modifier = "gi";
						if(ematch || fprops["col_"+j]=="select")//Váry Péter's patch
							regexp = new RegExp('(^\\s*)'+tf_RegexpEscape(SearchArgs[j])+'(\\s*$)', modifier);							
						else 
							regexp = new RegExp(tf_RegexpEscape(SearchArgs[j]), modifier);
						occurence[j] = regexp.test(cell_data);
					}
				}//if SearchArgs
			}//for j
			
			for(var z=0; z<ncells; z++)
			{
				if(SearchArgs[z]!="" && !occurence[z]) isRowValid = false;
			}//for t
			
		}//if
		
		if(!isRowValid)
		{ 
			row[k].style.display = "none"; hiddenrows++; 
			if( showPaging ) row[k].setAttribute("validRow","false");
		} else {
			row[k].style.display = ""; 
			if( showPaging ) row[k].setAttribute("validRow","true");
		}
		
	}// for k
	
	t.tf_nRows = parseInt( tf_GetRowsNb(id) )-hiddenrows;
	t.tf_hiddenRows = hiddenrows;	
	if( rememberGridValues ) tf_RememberFiltersValue(id,'tf_'+id);
	if( !showPaging ) tf_ApplyFilterProps(id);//applies filter props after filtering process
	if( showPaging ){ t.tf_startPagingRow=0; tf_SetPagingInfo(id); }//starts paging process		
}

function tf_SetPagingInfo(id)
/*====================================================
	- Paging fn
	- calculates page # according to valid rows
	- refreshes paging select according to page #
	- Calls GroupByPage fn
=====================================================*/
{	
	var t = tf_Id(id);
	var start_row = t.tf_ref_row;//filter start row
	var pagelength = t.tf_pagingLength;
	var row = tf_Tag(t,"tr");	
	var mdiv = tf_Id("mdiv_"+id);
	var slcPages = tf_Id("slcPages_"+id);
	var pgspan = tf_Id("pgspan_"+id);
	var nrows = 0;
	
	for(var j=start_row; j<row.length; j++)//counts rows to be grouped 
	{
		if(row[j].getAttribute("validRow") == "true") nrows++;
	}//for j
	
	var npg = Math.ceil( nrows/pagelength );//calculates page nb
	pgspan.innerHTML = npg; //refresh page nb span 
	slcPages.innerHTML = "";//select clearing shortcut
	
	if( npg>0 )
	{
		mdiv.style.visibility = "visible";
		for(var z=0; z<npg; z++)
		{
			var currOpt = new Option((z+1),z*pagelength,false,false);
			slcPages.options[z] = currOpt;
		}
	} else {/*** if no results paging select and buttons are hidden ***/
		mdiv.style.visibility = "hidden";
	}
	tf_GroupByPage(id);
}

function tf_GroupByPage(id)
/*====================================================
	- Paging fn
	- Displays current page rows
=====================================================*/
{
	var t = tf_Id(id);
	var start_row = t.tf_ref_row;//filter start row
	var pagelength = parseInt( t.tf_pagingLength );
	var paging_start_row = parseInt( t.tf_startPagingRow );//paging start row
	var paging_end_row = paging_start_row + pagelength;
	var row = tf_Tag(t,"tr");
	var nrows = 0;
	var validRows = [];//stores valid rows index
	
	for(var j=start_row; j<row.length; j++)	
	{//this loop stores valid rows index in validRows Array
		var isRowValid = row[j].getAttribute("validRow");
		if(isRowValid=="true") validRows.push(j);
	}//for j

	for(h=0; h<validRows.length; h++)
	{//this loop shows valid rows of current page
		if( h>=paging_start_row && h<paging_end_row )
		{
			nrows++;
			row[ validRows[h] ].style.display = "";
		} else row[ validRows[h] ].style.display = "none";
	}//for h
	
	t.tf_nRows = parseInt(nrows);
	tf_ApplyFilterProps(id);//applies filter props after filtering process
}

function tf_ApplyFilterProps(id)
/*====================================================
	- checks fns that should be called
	after filtering and/or paging process
=====================================================*/
{
	var t = tf_Id(id);
	var activeFlt = tf_Id(t.tf_activeFilter);
	if(t.tf_activeFilter!=null && activeFlt.nodeName.toLowerCase()=="select" && window.attachEvent)
	{//blurs select type filters (for ie only) to prevent select scrolling on window scrolling, 
		//very inelegant but effective: activeFlt.options[activeFlt.selectedIndex].selected = false 
		//doesn't seem to work with ie
		var tempEl = tf_CreateElm('table',['id','_'+t.tf_activeFilter]);
		activeFlt.parentNode.appendChild(tempEl);
		tf_Id('_'+t.tf_activeFilter).focus();
		activeFlt.parentNode.removeChild(tempEl);
	}
	var rowsCounter = t.tf_rowsCounter;
	var nRows = t.tf_nRows;
	var rowVisibility = t.tf_rowVisibility;
	var alternateRows = t.tf_alternateBgs;
	var colOperation = t.tf_colOperation;
	var refreshFilters = t.tf_refreshFilters;
	
	if( rowsCounter ) tf_ShowRowsCounter( id,parseInt(nRows) );//refreshes rows counter
	if( rowVisibility ) tf_SetVisibleRows(id);//shows rows always visible
	if( alternateRows ) tf_SetAlternateRows(id);//alterning row colors
	if( colOperation  ) tf_SetColOperation(id);//makes operation on a col
	if( refreshFilters ) tf_RefreshFiltersGrid(id);//re-populates drop-down filters

	if(t.tf_displayLoader) tf_ShowLoader(id,"none");
}

function tf_HasGrid(id)
/*====================================================
	- checks if table has a filter grid
	- returns a boolean
=====================================================*/
{
	var r = false, t = tf_Id(id);
	if(t != null && t.nodeName.toLowerCase() == "table")
	{
		for(var i=0; i<TF_TblId.length; i++)
		{
			if(id == TF_TblId[i]) r = true;
		}// for i
	}//if
	return r;
}

function tf_GetCellsNb(id,nrow)
/*====================================================
	- returns number of cells in a row
	- if nrow param is passed returns number of cells 
	of specified row
=====================================================*/
{
  	var t = tf_Id(id), tr;
	if(nrow == undefined) tr = tf_Tag(t,"tr")[0];
	else  tr = tf_Tag(t,"tr")[nrow];
	var n = tf_GetChildElms(tr);
	return n.childNodes.length;
}

function tf_GetRowsNb(id)
/*====================================================
	- returns total nb of filterable rows starting 
	from reference row if defined
=====================================================*/
{
	var t = tf_Id(id);
	var s = t.tf_ref_row==undefined ? 0 : t.tf_ref_row;
	var ntrs = tf_Tag(t,"tr").length;
	return parseInt(ntrs-s);
}

function tf_GetFilters(id)
/*====================================================
	- returns an array containing filters ids
	- Note that hidden filters are also returned
=====================================================*/
{
	var TF_SearchFltId = [];
	var t = tf_Id(id);
	var tr = tf_Tag(t,"tr")[t.tf_filtersRowIndex];
	var enfants = tr.childNodes;
	if(t.tf_fltGrid)
	{
		for(var i=0; i<enfants.length; i++) 
			TF_SearchFltId.push(enfants[i].firstChild.getAttribute("id"));		
	}
	return TF_SearchFltId;
}

function tf_GetFiltersByType(id,type,bool)
/*====================================================
	- returns an array containing only filters of a 
	type (inputs or selects)
	- Note that hidden filters are also returned
	- Needs folllowing args:
		- table id
		- filter type string ("input" or "select")
		- optional boolean: if set true function
		returns column indexes otherwise filters ids
=====================================================*/
{
	var ftls = tf_GetFilters(id);
	var arr = [];
	for(var i=0; i<ftls.length; i++)
	{
		var curFlt =  tf_Id(ftls[i]);
		var fltType = curFlt.nodeName.toLowerCase();
		if(fltType == type.toLowerCase())
		{
			var a;
			bool ? a=i : a=curFlt.getAttribute("id");
			arr.push(a);
		}
	}// for i
	return arr;
}

function tf_ClearFilters(id)
/*====================================================
	- clears grid filters
=====================================================*/
{
	var t = tf_Id(id);
	TF_SearchFlt = tf_GetFilters(id);
	for(var i=0; i<TF_SearchFlt.length; i++)
		tf_Id(TF_SearchFlt[i]).value = "";
}

function tf_ShowLoader(id,p)
/*====================================================
	- displays/hides loader div
=====================================================*/
{
	var t = tf_Id(id);
	var infdiv = tf_Id("inf_"+id);
	var loader = tf_Id("load_"+id);
	if(infdiv != null && t.tf_displayLoader)
	{
		function showLoader(){ loader.style.display = p; }
		var t = (p=="none") ? 200 : 5;
		setTimeout(showLoader,t);
	}
}

function tf_ShowRowsCounter(id,p)
/*====================================================
	- Shows total number of filtered rows
=====================================================*/
{
	var t = tf_Id(id);
	var totrows = tf_Id("totrows_span_"+id), tot_text;
	var paging = t.tf_displayPaging;
	if(totrows != null && totrows.nodeName.toLowerCase() == "span" )
	{
		if(!paging) tot_text=p;
		else{
			var nrows = tf_GetRowsNb(id);
			var hiddenRows = t.tf_hiddenRows;
			var totRows = nrows-hiddenRows;
			var pagelength = parseInt(t.tf_pagingLength);
			var paging_start_row = parseInt(t.tf_startPagingRow) + ((totRows>0) ? 1 : 0);//paging start row
			var paging_end_row = (paging_start_row+pagelength)-1 <= totRows 
				? (paging_start_row+pagelength)-1 : totRows;
			tot_text = paging_start_row+"-"+paging_end_row+" / "+totRows;
		}
		totrows.innerHTML = tot_text;
	}
}

function tf_ChangePage(id)
/*====================================================
	- fn called by paging select
=====================================================*/
{
	var t = tf_Id(id);
	var slcPages = tf_Id("slcPages_"+id);
	if(t.tf_displayLoader) tf_ShowLoader(id,"");
	t.tf_startPagingRow = slcPages.value;
	tf_GroupByPage(id);
}

function tf_ChangeResultsPerPage(id)
/*====================================================
	- fn called by nb results per page select
=====================================================*/
{
	var t = tf_Id(id);
	if(t.tf_displayLoader) tf_ShowLoader(id,"");
	var slcR = tf_Id("slc_results_"+id);
	var slcPages = tf_Id("slcPages_"+id);
	var slcPagesSelIndex = slcPages.selectedIndex;
	t.tf_pagingLength = parseInt(slcR.options[slcR.selectedIndex].text);
	t.tf_startPagingRow = t.tf_pagingLength*slcPagesSelIndex;
	var nRows = tf_GetRowsNb(id);
	if( !isNaN(t.tf_pagingLength) )
	{
		if( t.tf_startPagingRow>nRows )
			t.tf_startPagingRow = nRows-t.tf_pagingLength;
		tf_SetPagingInfo(id);
		var slcIndex = (slcPages.options.length-1<=slcPagesSelIndex ) 
						? (slcPages.options.length-1) : slcPagesSelIndex;
		slcPages.options[slcIndex].selected = true;
	}//if isNaN
}

function tf_GetChildElms(n)
/*====================================================
	- checks passed node is a ELEMENT_NODE nodeType=1
	- removes TEXT_NODE nodeType=3  
=====================================================*/
{
	if(n!=undefined && n.nodeType == 1)
	{
		var enfants = n.childNodes;
		for(var i=0; i<enfants.length; i++)
		{
			var child = enfants[i];
			if(child.nodeType == 3) n.removeChild(child);
		}
		return n;	
	}
}

function tf_GetCellText(n)
/*====================================================
	- returns text + text of child nodes of a cell
=====================================================*/
{
	var s = "";
	var enfants = n.childNodes;
	for(var i=0; i<enfants.length; i++)
	{
		var child = enfants[i];
		if(child.nodeType == 3) s+= child.data;
		else s+= tf_GetCellText(child);
	}
	return s;
}

function tf_GetColValues(id,colindex,num,exclude)
/*====================================================
	- returns an array containing cell values of
	a column
	- needs following args:
		- filter id (string)
		- column index (number)
		- a boolean set to true if we want only 
		numbers to be returned
		- array containing rows index to be excluded
		from returned values
=====================================================*/
{
	var t = tf_Id(id);
	var row = tf_Tag(t,"tr");
	var nrows = row.length;
	var start_row = t.tf_ref_row;//filter start row
	var ncells = tf_GetCellsNb( id,start_row );
	var colValues = [];

	for(var i=start_row; i<nrows; i++)//iterates rows
	{
		var isExludedRow = false;
		if(exclude!=undefined && (typeof exclude).toLowerCase()=="object")
		{ // checks if current row index appears in exclude array
			var reg = new RegExp("^"+i+"$","g");
			for(var k=0; k<exclude.length; k++)
				isExludedRow = reg.test(exclude[k]);//boolean
		}
		var cell = tf_GetChildElms(row[i]).childNodes;
		var nchilds = cell.length;
		
		if(nchilds == ncells && !isExludedRow)
		{// checks if row has exact cell # and is not excluded
			for(var j=0; j<nchilds; j++)// this loop retrieves cell data
			{
				if(j==colindex && row[i].style.display=="" )
				{
					var cell_data = tf_GetCellText( cell[j] ).toLowerCase();
					(num) ? colValues.push( parseFloat(cell_data) ) : colValues.push( cell_data );
				}//if j==k
			}//for j
		}//if nchilds == ncells
	}//for i
	return colValues;	
}

function tf_GetFilterValue(id,index)
/*====================================================
	- Get value of a specified filter
	- Params:
		- id: table id (string)
		- index: filter column index (numeric value)
=====================================================*/
{
	if( tf_HasGrid(id) )
	{
		var fltValue;
		var flts = tf_GetFilters(id);
		for(var i=0; i<flts.length; i++)
		{
			if( i==index ) fltValue = tf_Id(flts[i]).value;
		}
		return fltValue;
	}
}

function tf_GetTableData(id)
/*====================================================
	- returns an array containing table data:
	[rowindex,([value1,value2,value3...]]
=====================================================*/
{
	TF_TblData[id] = [];	
	var t = tf_Id(id);

	var start_row = t.tf_ref_row;
	var row = tf_Tag(t,"tr");
	
	for(var k=start_row; k<row.length; k++)
	{
		var rowData, cellData;
		rowData = [k,[]];
		
		var cell = tf_GetChildElms(row[k]).childNodes;
		var nchilds = cell.length;
		for(var j=0; j<nchilds; j++)
		{// this loop retrieves cell data
			var cell_data = tf_GetCellText(cell[j]);
			rowData[1].push( cell_data );
		}
		TF_TblData[id].push( rowData )
	}
	return TF_TblData[id];
}

function tf_SetLoader(id)
/*====================================================
	- generates loader div and returns 
	loader element
=====================================================*/
{
	var t = tf_Id(id);
	var loaderHtml = t.tf_loaderHtml;
	var load_text = t.tf_loadText;
	/*** div containing rows # displayer + reset btn ***/
	var infdiv = tf_CreateElm( "div",["id","inf_"+id],["class","inf"] );
	infdiv.className = "inf";// setAttribute method for class attribute omitted on ie<=6
	t.parentNode.insertBefore(infdiv, t);
	
	var loaddiv = tf_CreateElm( "div",["id","load_"+id],["class","loader"] );
	loaddiv.className = "loader";// for ie<=6
	loaddiv.style.display = "none";
	infdiv.appendChild(loaddiv);
	if(loaderHtml==null) loaddiv.appendChild( tf_CreateText(load_text) );
	else tf_Id("load_"+id).innerHTML = loaderHtml;
	tf_ShowLoader(id,"");
	return infdiv;
}

function tf_SetFilterValue(id,index,searcharg)
/*====================================================
	- Inserts value in a specified filter
	- Params:
		- id: table id (string)
		- index: filter column index (numeric value)
		- searcharg: search string
=====================================================*/
{
	if( TF_HasGrid(id) )
	{
		var flts = tf_GetFilters(id);
		for(var i=0; i<flts.length; i++)
		{
			if( i==index ) tf_Id(flts[i]).value = searcharg;
		}
	}//if
}

function tf_SetColWidths(id)
/*====================================================
	- sets widths of columns
=====================================================*/
{
	if( tf_HasGrid(id) )
	{
		var t = tf_Id(id);
		//t.style.tableLayout = "fixed";//problems with firefox
		var colWidth = t.tf_colWidth;
		var start_row = t.tf_ref_row;//filter start row
		var row = tf_Tag(t,"tr")[0];
		var ncells = tf_GetCellsNb(id,start_row);
		for(var i=0; i<colWidth.length; i++)
		{
			for(var k=0; k<ncells; k++)
			{
				cell = tf_GetChildElms(row).childNodes[k];
				if(k==i) cell.style.width = colWidth[i];				
			}//var k
		}//for i		
	}//if hasGrid
}

function tf_SetVisibleRows(id)
/*====================================================
	- makes a row always visible
=====================================================*/
{
	if( tf_HasGrid(id) )
	{
		var t = tf_Id(id);		
		var row = tf_Tag(t,"tr");
		var nrows = row.length;
		var showPaging = t.tf_displayPaging;
		var visibleRows = t.tf_rowVisibility;
		for(var i=0; i<visibleRows.length; i++)
		{
			if(visibleRows[i]<=nrows)//row index cannot be > nrows
			{
				if(showPaging)
					row[ visibleRows[i] ].setAttribute("validRow","true");
				row[ visibleRows[i] ].style.display = "";
			}//if
		}//for i
	}//if hasGrid
}

function tf_SetAlternateRows(id)
/*====================================================
	- alternates row colors for better readability
=====================================================*/
{
	if( tf_HasGrid(id) )
	{
		var t = tf_Id(id);		
		var row = tf_Tag(t,"tr");
		var nrows = row.length;
		var start_row = t.tf_ref_row;//filter start row
		var visiblerows = [];
		for(var i=start_row; i<nrows; i++)//visible rows are stored in visiblerows array
			if( row[i].style.display=="" ) visiblerows.push(i);
		
		for(var j=0; j<visiblerows.length; j++)//alternates bg color
			(j % 2 == 0) ? row[ visiblerows[j] ].className = "even" : row[ visiblerows[j] ].className = "odd";
		
	}//if hasGrid
}

function tf_SetColOperation(id)
/*====================================================
	- Calculates values of a column
	- params are stored in 'colOperation' table's
	attribute
		- colOperation["id"] contains ids of elements 
		showing result (array)
		- colOperation["col"] contains index of 
		columns (array)
		- colOperation["operation"] contains operation
		type (array, values: sum, mean)
		- colOperation["write_method"] array defines 
		which method to use for displaying the 
		result (innerHTML, setValue, createTextNode).
		Note that innerHTML is the default value.
		
	!!! to be optimised
=====================================================*/
{
	if( tf_HasGrid(id) )
	{
		var t = tf_Id(id);
		var labelId = t.tf_colOperation["id"];
		var colIndex = t.tf_colOperation["col"];
		var operation = t.tf_colOperation["operation"];
		var outputType = t.tf_colOperation["write_method"];
		var totRowIndex = t.tf_colOperation["tot_row_index"];
		var excludeRow = t.tf_colOperation["exclude_row"];
		var decimalPrecision = t.tf_colOperation["decimal_precision"]!=undefined
								? t.tf_colOperation["decimal_precision"] : 2;
					
		if( (typeof labelId).toLowerCase()=="object" 
			&& (typeof colIndex).toLowerCase()=="object" 
			&& (typeof operation).toLowerCase()=="object" )
		{
			var row = tf_Tag(t,"tr");
			var nrows = row.length;
			var start_row = t.tf_ref_row;//filter start row
			var ncells = tf_GetCellsNb( id,start_row );
			var colvalues = [];
						
			for(var k=0; k<colIndex.length; k++)//this retrieves col values
			{
				if(totRowIndex!=undefined) row[totRowIndex[k]].style.display = 'none';
				colvalues.push( tf_GetColValues(id,colIndex[k],true,excludeRow) );
				if(totRowIndex!=undefined) row[totRowIndex[k]].style.display = '';
			}//for k
			
			for(var i=0; i<colvalues.length; i++)
			{
				var result=0, nbvalues=0, minValue, maxValue;
				var precision = decimalPrecision[i]!=undefined && !isNaN( decimalPrecision[i] )
									? decimalPrecision[i] : 2;
				for(var j=0; j<colvalues[i].length; j++ )
				{
					var cvalue = colvalues[i][j];
					if( !isNaN(cvalue) )
					{
						switch( operation[i].toLowerCase() )
						{
							case "sum":
								result += parseFloat( cvalue );
							break;
							case "mean":
								nbvalues++;
								result += parseFloat( cvalue );
							break;
							//add cases for other operations
						}//switch
					}
				}//for j
				
				switch( operation[i].toLowerCase() )
				{
					case "mean":
						result = result/nbvalues;
					break;
					case "min":
						var v = colvalues[i].toString();
						minValue = eval('Math.min('+v+')');
						result = minValue;
					break;
					case "max":
						var v = colvalues[i].toString();
						maxValue = eval('Math.max('+v+')');
						result = maxValue;
					break;
				}
				
				if(outputType != undefined && (typeof outputType).toLowerCase()=="object")
				//if outputType is defined
				{
					result = result.toFixed( precision );
					if( tf_Id( labelId[i] )!=undefined )
					{
						switch( outputType[i].toLowerCase() )
						{
							case "innerhtml":
								tf_Id( labelId[i] ).innerHTML = result;
							break;
							case "setvalue":
								tf_Id( labelId[i] ).value = result;
							break;
							case "createtextnode":
								var oldnode = tf_Id( labelId[i] ).firstChild;
								var txtnode = tf_CreateText( result );
								tf_Id( labelId[i] ).replaceChild( txtnode,oldnode );
							break;
						}//switch
					}
				} else {
					try
					{
						tf_Id( labelId[i] ).innerHTML = result.toFixed( precision );
					} catch(e){ }//catch
				}//else
				
			}//for i

		}//if typeof
	}//if hasGrid
}

function tf_RefreshFiltersGrid(id)
/*====================================================
	- retrieves select filters
	- calls fn repopulating filter values
=====================================================*/
{
	var t = tf_Id(id);
	var slcIndex = tf_GetFiltersByType( id,'select',true );
	if( t.tf_activeFilter!=null )//for paging
	{
		var activeFlt = t.tf_activeFilter.split("_")[0];
		activeFlt = activeFlt.split("flt")[1];
		
		for(var i=0; i<slcIndex.length; i++)
		{
			var curSlc = tf_Id("flt"+slcIndex[i]+"_"+id);
			var slcSelectedValue = curSlc.options[curSlc.selectedIndex].text;
			var opt0txt = t.tf_display_allText;
			
			if(activeFlt!=slcIndex[i] || slcSelectedValue==opt0txt )
			{
				curSlc.innerHTML = "";
				tf_PopulateOptions(id,slcIndex[i],true);
			}
		}// for i
	}
}

function tf_RememberFiltersValue(id,name)
/*==============================================
	- stores filters' values in a cookie
	when tf_Filter() fn is called
	- Params:
		- id: table id (string)
		- name: cookie name (string)
	- credits to Florent Hirchy
===============================================*/
{
	var flt_ids = tf_GetFilters(id);
	var flt_values = [];

	for(var i=0; i<flt_ids.length; i++)
	{//creates an array with filters' values
		value = tf_GetFilterValue(id,i);
		if (value == '') value = " ";
		flt_values.push(value);
	}
	 
	flt_values.push(flt_ids.length); //adds array size
	tf_WriteCookie(name,flt_values,100000); //writes cookie  
}

function tf_ResetGridValues(id,name)
/*==============================================
	- re-sets filters' values when page is 
	reloaded
	- Params:
		- id: table id (string)
		- name: cookie name (string)
	- credits to Florent Hirchy
===============================================*/
{
	var flt_ids = tf_GetFilters(id);
	var flts = tf_ReadCookie(name); //reads the cookie
	var reg = new RegExp(",","g");	
	var flts_values = flts.split(reg); //creates an array with filters' values
	tf_ClearFilters(id); //clears grid
	
	if(flts_values[(flts_values.length-1)] == flt_ids.length)
	{//if the number of columns is the same as before page reload
		for(var i=0; i<(flts_values.length - 1); i++)
		{
			if (flts_values[i] !=" ")  
				tf_SetFilterValue(id,i,flts_values[i]);
		}//end for
	}//end if
	tf_Filter(id); 
}

function tf_Id(id)
/*====================================================
	- this is just a getElementById shortcut
=====================================================*/
{
	return document.getElementById( id );
}

function tf_Tag(obj,tagname)
/*====================================================
	- this is just a getElementsByTagName shortcut
=====================================================*/
{
	return obj.getElementsByTagName( tagname );
}

function tf_RegexpEscape(s)
/*====================================================
	- escapes special characters [\^$.|?*+() 
	for regexp
	- Many thanks to Cedric Wartel for this fn
=====================================================*/
{
	// traite les caractères spéciaux [\^$.|?*+()
	//remplace le carctère c par \c
	function escape(e)
	{
		a = new RegExp('\\'+e,'g');
		s = s.replace(a,'\\'+e);
	}

	chars = new Array('\\','[','^','$','.','|','?','*','+','(',')');
	//for(e in chars) escape(chars[e]); // compatibility issue with prototype
	for(var e=0; e<chars.length; e++) escape(chars[e]);
	return s;
}

function tf_CreateElm(elm)
/*====================================================
	- returns an html element with its attributes
	- accepts the following params:
		- a string defining the html element 
		to create
		- an undetermined # of arrays containing the
		couple "attribute name","value" ["id","myId"]
=====================================================*/
{
	var el = document.createElement( elm );		
	if(arguments.length>1)
	{
		for(var i=0; i<arguments.length; i++)
		{
			var argtype = typeof arguments[i];
			switch( argtype.toLowerCase() )
			{
				case "object":
					if( arguments[i].length==2 )
					{							
						el.setAttribute( arguments[i][0],arguments[i][1] );
					}//if array length==2
				break;
			}//switch
		}//for i
	}//if args
	return el;	
}

function tf_CreateText(node)
/*====================================================
	- this is just a document.createTextNode shortcut
=====================================================*/
{
	return document.createTextNode( node );
}

function tf_DetectKey(e)
/*====================================================
	- common fn that detects return key for a given
	element (onkeypress attribute on input)
=====================================================*/
{
	var evt=(e)?e:(window.event)?window.event:null;
	if(evt)
	{
		var key=(evt.charCode)?evt.charCode:
			((evt.keyCode)?evt.keyCode:((evt.which)?evt.which:0));
		if(key=="13")
		{
			var cid, leftstr, tblid, CallFn, Match;		
			cid = this.getAttribute("id");
			leftstr = this.getAttribute("id").split("_")[0];
			tblid = cid.substring(leftstr.length+1,cid.length);
			t = tf_Id(tblid);
			(t.tf_isModfilter_fn) ? t.tf_modfilter_fn.call() : tf_Filter(tblid);
		}//if key
	}//if evt	
}


/*====================================================
	- utility fns below
=====================================================*/
function tf_NumSortAsc(a, b){ return (a-b); }

function tf_NumSortDesc(a, b){ return (b-a); }

String.prototype.tf_MatchCase = function (mc) {
	if (!mc) return this.toLowerCase();
	else return this.toString();
}

String.prototype.tf_Trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g,''); 
}

function tf_ImportScript(scriptName,scriptPath)
{
	var isImported = false; 
	var scripts = tf_Tag(document,"script");

	for (var i=0; i<scripts.length; i++)
	{
		if(scripts[i].src.match(scriptPath))
		{ 
			isImported = true;	
			break;
		}
	}

	if( !isImported )//imports script if not available
	{
		var head = tf_Tag(document,"head")[0];
		var extScript = tf_CreateElm(	"script",
									["id",scriptName],
									["type","text/javascript"],
									["src",scriptPath]	);
		head.appendChild(extScript);
	}
}//fn tf_ImportScript

function tf_WriteCookie(name, value, hours)
{
	var expire = "";
	if(hours != null)
	{
		expire = new Date((new Date()).getTime() + hours * 3600000);
		expire = "; expires=" + expire.toGMTString();
	}
	document.cookie = name + "=" + escape(value) + expire;
}

function tf_ReadCookie(name)
{
	var cookieValue = "";
	var search = name + "=";
	if(document.cookie.length > 0)
	{ 
		offset = document.cookie.indexOf(search);
		if (offset != -1)
		{ 
			offset += search.length;
			end = document.cookie.indexOf(";", offset);
			if (end == -1) end = document.cookie.length;
			cookieValue = unescape(document.cookie.substring(offset, end))
		}
	}
	return cookieValue;
}

function tf_RemoveCookie(name)
{
	tf_WriteCookie(name,"",-1);
}
/* --- */

/*====================================================
	- fns ensuring backward compatibility
	- to be removed in next release
=====================================================*/
function grabEBI(id){ return tf_Id( id ); }
function grabTag(obj,tagname){ return tf_Tag(obj,tagname); }
function Filter(id){ tf_Filter(id); }
/* --- */



/*====================================================
	- Below a collection of public functions 
	for developement purposes
	- all public methods start with prefix 'TF_'
	- These methods can be removed safely if not
	needed
=====================================================*/

function TF_GetFilterIds()
/*====================================================
	- returns an array containing filter grids ids
=====================================================*/
{
	try{ return TF_TblId }
	catch(e){ alert('TF_GetFilterIds() fn: could not retrieve any ids'); }
}

function TF_HasGrid(id)
/*====================================================
	- checks if table has a filter grid
	- returns a boolean
=====================================================*/
{
	return tf_HasGrid(id);
}

function TF_GetFilters(id)
/*====================================================
	- returns an array containing filters ids of a
	specified grid
=====================================================*/
{
	try
	{
		var flts = tf_GetFilters(id);
		return flts;
	} catch(e) {
		alert('TF_GetFilters() fn: table id not found');
	}
	
}

function TF_GetStartRow(id)
/*====================================================
	- returns starting row index for filtering
	process
=====================================================*/
{
	try
	{
		var t = tf_Id(id);
		return t.tf_ref_row;
	} catch(e) {
		alert('TF_GetStartRow() fn: table id not found');
	}
}

function TF_GetColValues(id,colindex,num,excludeRow)
/*====================================================
	- returns an array containing cell values of
	a column
	- needs following args:
		- filter id (string)
		- column index (number)
		- a boolean set to true if we want only 
		numbers to be returned
=====================================================*/
{
	if( tf_HasGrid(id) )
	{
		return tf_GetColValues(id,colindex,num,excludeRow);
	}//if TF_HasGrid
	else alert('TF_GetColValues() fn: table id not found');
}

function TF_Filter(id)
/*====================================================
	- filters a table
=====================================================*/
{
	var t = tf_Id(id);
	if( TF_HasGrid(id) ) tf_Filter(id);
	else alert('TF_Filter() fn: table id not found');
}

function TF_RemoveFilterGrid(id)
/*====================================================
	- removes a filter grid
=====================================================*/
{
	if( TF_HasGrid(id) )
	{
		var t = tf_Id(id);
		var start_row = t.tf_ref_row;//filter start row
		tf_ClearFilters(id);
				
		if(tf_Id("inf_"+id)!=null)
			t.parentNode.removeChild(t.previousSibling);

		// remove paging here
		var row = tf_Tag(t,"tr");
		
		for(var j=start_row; j<row.length; j++)
		//this loop shows all rows and removes validRow attribute
		{			
			row[j].style.display = "";
			try
			{ 
				if( row[j].hasAttribute("validRow") ) 
					row[j].removeAttribute("validRow");
			} //ie<=6 doesn't support hasAttribute method
			catch(e){
				for( var x = 0; x < row[j].attributes.length; x++ ) 
				{
					if( row[j].attributes[x].nodeName.toLowerCase()=="validrow" ) 
						row[j].removeAttribute("validRow");
				}//for x
			}//catch(e)
		}//for j		
		
		if( t.tf_alternateBgs )//removes alterning row colors
		{
			for(var k=0; k<row.length; k++)//this loop removes tr className
				row[k].className = "";
		}

		if(t.tf_fltGrid) t.deleteRow(t.tf_filtersRowIndex);
		for(var i=0; i<TF_TblId.length; i++)
		{
			if(id == TF_TblId[i]) TF_TblId.splice(i,1);
		}

	}//if TF_HasGrid
	else alert('TF_RemoveFilterGrid() fn: table id not found');
}

function TF_ClearFilters(id)
/*====================================================
	- clears grid filters only, table is not filtered
=====================================================*/
{
	if( TF_HasGrid(id) ) tf_ClearFilters(id);
	else alert('TF_ClearFilters() fn: table id not found');
}

function TF_GetFilterValue(id,index)
/*====================================================
	- Get value of a specified filter
	- Params:
		- id: table id (string)
		- index: filter column index (numeric value)
=====================================================*/
{
	if( TF_HasGrid(id) ) return tf_GetFilterValue(id,index)
	else alert('TF_GetFilterValue() fn: table id not found');
}

function TF_SetFilterValue(id,index,searcharg)
/*====================================================
	- Inserts value in a specified filter
	- Params:
		- id: table id (string)
		- index: filter column index (numeric value)
		- searcharg: search string
=====================================================*/
{
	if( TF_HasGrid(id) ) tf_SetFilterValue(id,index,searcharg)
	else alert('TF_SetFilterValue() fn: table id not found');
}

function TF_SetPage(id,action)
/*====================================================
	- If paging set true shows page according to
	action param value:
		- "next","previous","last","first"
=====================================================*/
{
	if( TF_HasGrid(id) )
	{
		var t = tf_Id(id);
		if(t.tf_displayPaging)
		{
			var btnEvt = t.tf_pagingBtnEvents;
			switch(action.toLowerCase())
			{
				case "next":
					btnEvt.next();
				break;
				case "previous":
					btnEvt.prev();
				break;
				case "last":
					btnEvt.last();
				break;
				case "first":
					btnEvt.first();
				break;
				default:
					btnEvt.next();
				break;
			}//switch
		}
	} else alert('TF_SetPage() fn: table id not found');
}

function TF_GetTableData(id)
/*====================================================
	- returns an array containing table data:
	[rowindex,[value1,value2,value3...]]
=====================================================*/
{
	if( TF_HasGrid(id) ) return tf_GetTableData(id);
	else
		alert('TF_GetTableData() fn: table id not found');
}


