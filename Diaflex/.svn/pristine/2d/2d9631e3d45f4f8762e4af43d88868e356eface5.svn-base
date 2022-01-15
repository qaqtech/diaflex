package ft.com;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InfoMgr {
    int smtpPort = 25 ;
    String usr,loApplNm, reqUrl,jsVersion="",dbTyp="",connectBy="", nmeIdn , isXrt="0" ,macId="",lclIp="",df_xrt="",msgheader="",srchBaseOn="NA",srchBaseOnLst="",dfAcct="" ;
    int usrId = 0,smsModeCounter=1, currentYear,srchId,refSrchId=0,df_rln_id=0,df_nme_id=0,setUppartyId,setUpTermId, rap_pct=0;
    HashMap formFields ,mprp,prp,srchMprp , srchPrp,eventList,ktsViewPrpDtls;
    ArrayList nmeSrchList, addrList, nmeRelList , nmemaillist = new ArrayList(),nmemassmaillist = new ArrayList() ;
    String smtpHost = "",sessionId="", smtpUsr = "", smtpPwd = "",setUpEX="",auth_mode="",rfid_seq="",memo_lmt=null,crt_lmt=null,reportFor="QTY";
    public final Map values = new HashMap();
    String byrNm="",sid="",bkPrcOn="N",schema="",byrEml="", dbUsr="",mongoanalysis="",fromnewgoods="",tonewgoods="", trms="",shorttrms="",mem_ip="13.229.255.212",cnt="", rln = "E",isEx="",dfUsrTyp="",spaceCodeIp="", dfNmeIdn ="",dfGrpNmeIdn ="",dfNme="", slexId="",setUpbyrNm="",setUppartyNm="",setUpTermNm="",compareWith="",nmestatus="A",vwMdl="MEMO_VW",searchView="GRID",searchPriCalcView="GRID",stktallylinkWise="";
    int rlnId, byrId, trmId,srchByrId , srchPryID , logId ,setUpbyrId,iPageNo=0,pageCt =25,mem_port=0;
    ArrayList prpCombvw=new ArrayList(),imagelistDtl=new ArrayList();
    ArrayList srchPrpLst = null,vwPrpLst = null,soldPrpLst=null, gncPrpLst=null,dfStkStt=null,  mixScrhLst =null,smxScrhLst=null,rghScrhLst=null, memoLst=new ArrayList(),advPrpLst = new ArrayList(), prpDspBlocked=new ArrayList(),srtPrpLst = null, momoRtnLst=null, invPrpLst = null,ajaxPrpPrnt=null,ajaxPrpSrt=null,chkNmeIdnList = new ArrayList();
    HashMap lab_qtyctstable=null;
    HashMap stt_qtyctstable=null;
    HashMap sh_QtyCtstbl=null;
    HashMap sz_Qtyctstbl=null;
    HashMap col_ctsqty=null;
    HashMap clry_ctsqty=null;
    ArrayList svdmdDis=new ArrayList();
    ArrayList dashboardvisibility=new ArrayList();
    ArrayList pageblockList=new ArrayList();
    ArrayList crtwtPrpLst=new ArrayList();
    ArrayList labList=null , mixPrpLst =null, assortViewMap=null,roleLst=null,smxPrpLst=null,allowPlMnLst=null,suggBoxLst=null;
    ArrayList sttList=null;
    HashMap pageDetails =new HashMap();
    HashMap pageDetailsdf =new HashMap();
    HashMap qty_ctstbl=null , stockViewMap=null;
    ArrayList shapeList=null;
    ArrayList sizeList=null;
    HashMap qtyctsshsztbl=null;
    ArrayList colorList=null;
    ArrayList purityList=null;
    HashMap qtyctsClrPurtbl=null;
    boolean isSalesExec = false;
    ArrayList prcBsePrp = null,SortPrpLst=null, prcDisPrp = null,rghVwList=null, prcRefPrp = null, prcDefnPrp = null,refPrpLst=null, stkCrtprplist =null,saleperson=new ArrayList(),noteperson=new ArrayList(),groupcompany=new ArrayList();
    HashMap invTtls = null, prcPrp=null, grpDtls=null;
    String id="", machineName = "", usrIp = "", mdl = "",srchTyp="",saleExNme="",saleExeEml="",usrFlg="",df_term="",blockLs="",memoTypSel="";    
    double cmpTrm,xrt;
    ArrayList mainMenu = new ArrayList(),subMenu = new ArrayList(),trdLevelLst= new ArrayList(),recPktList= new ArrayList(),iememov= new ArrayList();
    HashMap favMaps = null,dmdMap=null,subMenuMap= new HashMap(),trdMenuMap = new HashMap(), multiSrchMap = new HashMap();
    ArrayList multiSrchLst = new ArrayList();
    String isFancy = "N",rportUrl="",webUrl="",viewForm="",isMix="",level1="";
    HashMap srchValMap = new HashMap(), multiSrchDsc = new HashMap(), dmbsInfoLst=new HashMap();
    ArrayList srchMPrpList = new ArrayList(),slPktList=new ArrayList(),dlvPktList= new ArrayList(), rfiddevice = null;
    String errorPath ="",seqidn="",prpSplit="#",pri_chng_typ="",pri_chng_val="";
    ArrayList arrSubdept = new ArrayList(),prpUpdTempList = new ArrayList(),brnchDlvList=new ArrayList(),brnchStkIdnLst = new ArrayList(),srchsttLst = new ArrayList();
    HashMap htSubdept = new HashMap();
    Connection con=null;
    ArrayList arrPktNo = new ArrayList();
    private ArrayList yrList = null;
    private ArrayList monthList = null;
    private ArrayList quarterList = null;
    ArrayList arrFailRem = new ArrayList();
    HashMap htFailRem = new HashMap();

    HashMap htdeptEmpRel = new HashMap();

    ArrayList arrAlocThru = new ArrayList();
    HashMap htAlocThru = new HashMap();
    
    
    public InfoMgr() {
        super();
    }
   
    public void setValue(String key, Object value) {
        //util.SOP(" Key : "+ key + " | Value : "+ (String)value);
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    
    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    public int getUsrId() {
        return usrId;
    }

    public void setFormFields(String key, HashMap val) {
        if(formFields == null)
            formFields = new HashMap();
        
        formFields.put(key, val);
    }

    public HashMap getFormFields() {
        if(formFields == null)
            formFields = new HashMap();
        return formFields;
    }
    
    public HashMap getFormFields(String formName) {
        if(formFields == null)
            formFields = new HashMap();
        
        HashMap lFormFields = (formFields.get(formName) == null)?new HashMap():(HashMap)formFields.get(formName);
        
        return lFormFields;
    }

    public void setNmeSrchList(ArrayList nmeSrchList) {
        this.nmeSrchList = nmeSrchList;
    }

    public ArrayList getNmeSrchList() {
        return (nmeSrchList == null)?new ArrayList():nmeSrchList;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setAddrList(ArrayList addrList) {
        this.addrList = addrList;
    }

    public ArrayList getAddrList() {
        return addrList;
    }

    public void setNmeRelList(ArrayList nmeRelList) {
        this.nmeRelList = nmeRelList;
    }

    public ArrayList getNmeRelList() {
        return nmeRelList;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setFormFields(HashMap formFields) {
        this.formFields = formFields;
    }

    public ArrayList getNmeSrchList1() {
        return nmeSrchList;
    }

    public void setNmeIdn(String nmeIdn) {
        this.nmeIdn = nmeIdn;
    }

    public String getNmeIdn() {
        return nmeIdn;
    }

    public Map getValues() {
        return values;
    }

    public void setByrNm(String byrNm) {
        this.byrNm = byrNm;
    }

    public String getByrNm() {
        return byrNm;
    }

    public void setByrEml(String byrEml) {
        this.byrEml = byrEml;
    }

    public String getByrEml() {
        return byrEml;
    }

    public void setTrms(String trms) {
        this.trms = trms;
    }

    public String getTrms() {
        return trms;
    }

    public void setRln(String rln) {
        this.rln = rln;
    }

    public String getRln() {
        return rln;
    }

    public void setRlnId(int rlnId) {
        this.rlnId = rlnId;
    }

    public int getRlnId() {
        return rlnId;
    }

    public void setByrId(int byrId) {
        this.byrId = byrId;
    }

    public int getByrId() {
        return byrId;
    }

    public void setTrmId(int trmId) {
        this.trmId = trmId;
    }

    public int getTrmId() {
        return trmId;
    }

    public void setSrchPrpLst(ArrayList srchPrpLst) {
        this.srchPrpLst = srchPrpLst;
    }

    public ArrayList getSrchPrpLst() {
        return srchPrpLst;
    }

    public void setVwPrpLst(ArrayList vwPrpLst) {
        this.vwPrpLst = vwPrpLst;
    }

    public ArrayList getVwPrpLst() {
        return vwPrpLst;
    }

    public void setMemoLst(ArrayList memoLst) {
        this.memoLst = memoLst;
    }

    public ArrayList getMemoLst() {
        return memoLst;
    }

    public void setCmpTrm(double cmpTrm) {
        this.cmpTrm = cmpTrm;
    }

    public double getCmpTrm() {
        return cmpTrm;
    }

    public void setXrt(double xrt) {
        this.xrt = xrt;
    }

    public double getXrt() {
        return xrt;
    }

    public void setFavMaps(HashMap favMaps) {
        this.favMaps = favMaps;
    }

    public HashMap getFavMaps() {
        return favMaps;
    }

    public void setDmdMap(HashMap dmdMap) {
        this.dmdMap = dmdMap;
    }

    public HashMap getDmdMap() {
        return dmdMap;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpUsr(String smtpUsr) {
        this.smtpUsr = smtpUsr;
    }

    public String getSmtpUsr() {
        return smtpUsr;
    }

    public void setSmtpPwd(String smtpPwd) {
        this.smtpPwd = smtpPwd;
    }

    public String getSmtpPwd() {
        return smtpPwd;
    }

    public void setSrchId(int srchId) {
        this.srchId = srchId;
    }

    public int getSrchId() {
        return srchId;
    }

    public void setMprp(HashMap mprp) {
        this.mprp = mprp;
    }

    public HashMap getMprp() {
        return (HashMap)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setMprp");
    }

    public void setPrp(HashMap prp) {
        this.prp = prp;
    }

    public HashMap getPrp() {
        return (HashMap)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setPrp");
    }

    public void setMainMenu(ArrayList mainMenu) {
        this.mainMenu = mainMenu;
    }

    public ArrayList getMainMenu() {
        return mainMenu;
    }

    public void setSubMenu(ArrayList subMenu) {
        this.subMenu = subMenu;
    }

    public ArrayList getSubMenu() {
        return subMenu;
    }

    public void setTrdLevelLst(ArrayList trdLevelLst) {
        this.trdLevelLst = trdLevelLst;
    }

    public ArrayList getTrdLevelLst() {
        return trdLevelLst;
    }

    public void setSubMenuMap(HashMap subMenuMap) {
        this.subMenuMap = subMenuMap;
    }

    public HashMap getSubMenuMap() {
        return subMenuMap;
    }

    public void setTrdMenuMap(HashMap trdMenuMap) {
        this.trdMenuMap = trdMenuMap;
    }

    public HashMap getTrdMenuMap() {
        return trdMenuMap;
    }

    public void setMultiSrchLst(ArrayList multiSrchLst) {
        this.multiSrchLst = multiSrchLst;
    }

    public ArrayList getMultiSrchLst() {
        return multiSrchLst;
    }

    public void setPrpDspBlocked(ArrayList prpDspBlocked) {
        this.prpDspBlocked = prpDspBlocked;
    }

    public ArrayList getPrpDspBlocked() {
        return prpDspBlocked;
    }

    public void setMomoRtnLst(ArrayList momoRtnLst) {
        this.momoRtnLst = momoRtnLst;
    }

    public ArrayList getMomoRtnLst() {
        return momoRtnLst;
    }

    public void setPrcBsePrp(ArrayList prcBsePrp) {
        this.prcBsePrp = prcBsePrp;
    }

    public ArrayList getPrcBsePrp() {
        return (ArrayList)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setPrcBsePrp");
    }

    public void setPrcDisPrp(ArrayList prcDisPrp) {
        this.prcDisPrp = prcDisPrp;
    }

    public ArrayList getPrcDisPrp() {
        return (ArrayList)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setPrcDisPrp");
    }

    public void setPrcRefPrp(ArrayList prcRefPrp) {
        this.prcRefPrp = prcRefPrp;
    }

    public ArrayList getPrcRefPrp() {
        return (ArrayList)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setPrcRefPrp");
    }

    public void setPrcDefnPrp(ArrayList prcDefnPrp) {
        this.prcDefnPrp = prcDefnPrp;
    }

    public ArrayList getPrcDefnPrp() {
        return prcDefnPrp;
    }

    public void setInvTtls(HashMap invTtls) {
        this.invTtls = invTtls;
    }

    public HashMap getInvTtls() {
        return invTtls;
    }

    public void setPrcPrp(HashMap prcPrp) {
        this.prcPrp = prcPrp;
    }

    public HashMap getPrcPrp() {
        return (HashMap)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setPrcPrp");
    }

    public void setGrpDtls(HashMap grpDtls) {
        this.grpDtls = grpDtls;
    }

    public HashMap getGrpDtls() {
        return (HashMap)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setGrpDtls");
    }

    public void setInvPrpLst(ArrayList invPrpLst) {
        this.invPrpLst = invPrpLst;
    }

    public ArrayList getInvPrpLst() {
        return invPrpLst;
    }

    public void setAjaxPrpPrnt(ArrayList ajaxPrpPrnt) {
        this.ajaxPrpPrnt = ajaxPrpPrnt;
    }

    public ArrayList getAjaxPrpPrnt() {
        return ajaxPrpPrnt;
    }

    public void setAjaxPrpSrt(ArrayList ajaxPrpSrt) {
        this.ajaxPrpSrt = ajaxPrpSrt;
    }

    public ArrayList getAjaxPrpSrt() {
        return ajaxPrpSrt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setUsrIp(String usrIp) {
        this.usrIp = usrIp;
    }

    public String getUsrIp() {
        return usrIp;
    }

    public void setMdl(String mdl) {
        this.mdl = mdl;
    }

    public String getMdl() {
        return mdl;
    }

    public void setIsFancy(String isFancy) {
        this.isFancy = isFancy;
    }

    public String getIsFancy() {
        return isFancy;
    }

    public void setRportUrl(String rportUrl) {
        this.rportUrl = rportUrl;
    }

    public String getRportUrl() {
        return rportUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setRefPrpLst(ArrayList refPrpLst) {
        this.refPrpLst = refPrpLst;
    }

    public ArrayList getRefPrpLst() {
        return refPrpLst;
    }

    public void setRefSrchId(int refSrchId) {
        this.refSrchId = refSrchId;
    }

    public int getRefSrchId() {
        return refSrchId;
    }

    public void setMultiSrchMap(HashMap mutilSrchMap) {
        this.multiSrchMap = mutilSrchMap;
    }

    public HashMap getMultiSrchMap() {
        return multiSrchMap;
    }

    public void setSrchValMap(HashMap srchValMap) {
        this.srchValMap = srchValMap;
    }

    public HashMap getSrchValMap() {
        return srchValMap;
    }

    public void setSrchMPrpList(ArrayList srchMPrpList) {
        this.srchMPrpList = srchMPrpList;
    }

    public ArrayList getSrchMPrpList() {
        return srchMPrpList;
    }

    public void setMultiSrchDsc(HashMap multiSrchDsc) {
        this.multiSrchDsc = multiSrchDsc;
    }

    public HashMap getMultiSrchDsc() {
        return multiSrchDsc;
    }

    public void setSrchTyp(String srchTyp) {
        this.srchTyp = srchTyp;
    }

    public String getSrchTyp() {
        return srchTyp;
    }

    public void setSlPktList(ArrayList slPktList) {
        this.slPktList = slPktList;
    }

    public ArrayList getSlPktList() {
        return slPktList;
    }

    public void setDlvPktList(ArrayList dlvPktList) {
        this.dlvPktList = dlvPktList;
    }

    public ArrayList getDlvPktList() {
        return dlvPktList;
    }

    public void setSrchByrId(int srchByrId) {
        this.srchByrId = srchByrId;
    }

    public int getSrchByrId() {
        return srchByrId;
    }

    public void setSrchPryID(int srchPryID) {
        this.srchPryID = srchPryID;
    }

    public int getSrchPryID() {
        return srchPryID;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }

    public String getErrorPath() {
        return errorPath;
    }

    public void setDmbsInfoLst(HashMap dmbsInfoLst) {
        this.dmbsInfoLst = dmbsInfoLst;
    }

    public HashMap getDmbsInfoLst() {
        return dmbsInfoLst;
    }

    public void setSrtPrpLst(ArrayList srtPrpLst) {
        this.srtPrpLst = srtPrpLst;
    }

    public ArrayList getSrtPrpLst() {
        return srtPrpLst;
    }

    public void setSaleExNme(String saleExNme) {
        this.saleExNme = saleExNme;
    }

    public String getSaleExNme() {
        return saleExNme;
    }

    public void setSaleExeEml(String saleExeEml) {
        this.saleExeEml = saleExeEml;
    }

    public String getSaleExeEml() {
        return saleExeEml;
    }

    public void setUsrFlg(String usrFlg) {
        this.usrFlg = usrFlg;
    }

    public String getUsrFlg() {
        return usrFlg;
    }

    public void setIsEx(String isEx) {
        this.isEx = isEx;
    }

    public String getIsEx() {
        return isEx;
    }

    public void setStkCrtprplist(ArrayList stkCrtprplist) {
        this.stkCrtprplist = stkCrtprplist;
    }

    public ArrayList getStkCrtprplist() {
        return stkCrtprplist;
    }

    public void setDf_rln_id(int df_rln_id) {
        this.df_rln_id = df_rln_id;
    }

    public int getDf_rln_id() {
        return df_rln_id;
    }

    public void setDf_nme_id(int df_nme_id) {
        this.df_nme_id = df_nme_id;
    }

    public int getDf_nme_id() {
        return df_nme_id;
    }


    public void setLabList(ArrayList labList) {
        this.labList = labList;
    }

    public ArrayList getLabList() {
        return labList;
    }

    public void setSttList(ArrayList sttList) {
        this.sttList = sttList;
    }

    public ArrayList getSttList() {
        return sttList;
    }

    public void setQty_ctstbl(HashMap qty_ctstbl) {
        this.qty_ctstbl = qty_ctstbl;
    }

    public HashMap getQty_ctstbl() {
        return qty_ctstbl;
    }

    public void setShapeList(ArrayList shapeList) {
        this.shapeList = shapeList;
    }

    public ArrayList getShapeList() {
        return shapeList;
    }

    public void setSizeList(ArrayList sizeList) {
        this.sizeList = sizeList;
    }

    public ArrayList getSizeList() {
        return sizeList;
    }

    public void setQtyctsshsztbl(HashMap qtyctsshsztbl) {
        this.qtyctsshsztbl = qtyctsshsztbl;
    }

    public HashMap getQtyctsshsztbl() {
        return qtyctsshsztbl;
    }

    public void setColorList(ArrayList colorList) {
        this.colorList = colorList;
    }

    public ArrayList getColorList() {
        return colorList;
    }

    public void setPurityList(ArrayList purityList) {
        this.purityList = purityList;
    }

    public ArrayList getPurityList() {
        return purityList;
    }

    public void setQtyctsClrPurtbl(HashMap qtyctsClrPurtbl) {
        this.qtyctsClrPurtbl = qtyctsClrPurtbl;
    }

    public HashMap getQtyctsClrPurtbl() {
        return qtyctsClrPurtbl;
    }

    public void setAdvPrpLst(ArrayList advPrpLst) {
        this.advPrpLst = advPrpLst;
    }

    public ArrayList getAdvPrpLst() {
        return advPrpLst;
    }

    public void setSrchMprp(HashMap srchMprp) {
        this.srchMprp = srchMprp;
    }

    public HashMap getSrchMprp() {
        return (HashMap)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setSrchMprp");
    }

    public void setSrchPrp(HashMap srchPrp) {
        this.srchPrp = srchPrp;
    }

    public HashMap getSrchPrp() {
        return (HashMap)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_setSrchPrp");
    }

    public void setGncPrpLst(ArrayList gncPrpLst) {
        this.gncPrpLst = gncPrpLst;
    }

    public ArrayList getGncPrpLst() {
        return gncPrpLst;
    }

    public void setLab_qtyctstable(HashMap lab_qtyctstable) {
        this.lab_qtyctstable = lab_qtyctstable;
    }

    public HashMap getLab_qtyctstable() {
        return lab_qtyctstable;
    }

    public void setStt_qtyctstable(HashMap stt_qtyctstable) {
        this.stt_qtyctstable = stt_qtyctstable;
    }

    public HashMap getStt_qtyctstable() {
        return stt_qtyctstable;
    }

    public void setSh_QtyCtstbl(HashMap sh_QtyCtstbl) {
        this.sh_QtyCtstbl = sh_QtyCtstbl;
    }

    public HashMap getSh_QtyCtstbl() {
        return sh_QtyCtstbl;
    }

    public void setSz_Qtyctstbl(HashMap sz_Qtyctstbl) {
        this.sz_Qtyctstbl = sz_Qtyctstbl;
    }

    public HashMap getSz_Qtyctstbl() {
        return sz_Qtyctstbl;
    }

    public void setCol_ctsqty(HashMap col_ctsqty) {
        this.col_ctsqty = col_ctsqty;
    }

    public HashMap getCol_ctsqty() {
        return col_ctsqty;
    }

    public void setClry_ctsqty(HashMap clry_ctsqty) {
        this.clry_ctsqty = clry_ctsqty;
    }

    public HashMap getClry_ctsqty() {
        return clry_ctsqty;
    }

    public void setViewForm(String viewForm) {
        this.viewForm = viewForm;
    }

    public String getViewForm() {
        return viewForm;
    }

    public void setDfUsrTyp(String dfUsrTyp) {
        this.dfUsrTyp = dfUsrTyp;
    }

    public String getDfUsrTyp() {
        return dfUsrTyp;
    }

    public void setDfNmeIdn(String dfNmeIdn) {
        this.dfNmeIdn = dfNmeIdn;
    }

    public String getDfNmeIdn() {
        return dfNmeIdn;
    }

    public void setIsSalesExec(boolean isSalesExec) {
        this.isSalesExec = isSalesExec;
    }

    public boolean getIsSalesExec() {
        return isSalesExec;
    }

    public void setPrpCombvw(ArrayList prpCombvw) {
        this.prpCombvw = prpCombvw;
    }

    public ArrayList getPrpCombvw() {
        return prpCombvw;
    }

    public void setArrSubdept(ArrayList arrSubdept) {
        this.arrSubdept = arrSubdept;
    }

    public ArrayList getArrSubdept() {
        return arrSubdept;
    }

    public void setHtSubdept(HashMap htSubdept) {
        this.htSubdept = htSubdept;
    }

    public HashMap getHtSubdept() {
        return htSubdept;
    }

    public void setArrPktNo(ArrayList arrPktNo) {
        this.arrPktNo = arrPktNo;
    }

    public ArrayList getArrPktNo() {
        return arrPktNo;
    }

    public void setArrFailRem(ArrayList arrFailRem) {
        this.arrFailRem = arrFailRem;
    }

    public ArrayList getArrFailRem() {
        return arrFailRem;
    }

    public void setHtFailRem(HashMap htFailRem) {
        this.htFailRem = htFailRem;
    }

    public HashMap getHtFailRem() {
        return htFailRem;
    }

    public void setHtdeptEmpRel(HashMap htdeptEmpRel) {
        this.htdeptEmpRel = htdeptEmpRel;
    }

    public HashMap getHtdeptEmpRel() {
        return htdeptEmpRel;
    }

    public void setArrAlocThru(ArrayList arrAlocThru) {
        this.arrAlocThru = arrAlocThru;
    }

    public ArrayList getArrAlocThru() {
        return arrAlocThru;
    }

    public void setHtAlocThru(HashMap htAlocThru) {
        this.htAlocThru = htAlocThru;
    }

    public HashMap getHtAlocThru() {
        return htAlocThru;
    }

    public void setNmemaillist(ArrayList nmemaillist) {
        this.nmemaillist = nmemaillist;
    }

    public ArrayList getNmemaillist() {
        return nmemaillist;
    }

    public void setPrpUpdTempList(ArrayList prpUpdTempList) {
        this.prpUpdTempList = prpUpdTempList;
    }

    public ArrayList getPrpUpdTempList() {
        return prpUpdTempList;
    }

    public void setIsXrt(String isXrt) {
        this.isXrt = isXrt;
    }

    public String getIsXrt() {
        return isXrt;
    }

    public void setMixPrpLst(ArrayList mixPrpLst) {
        this.mixPrpLst = mixPrpLst;
    }

    public ArrayList getMixPrpLst() {
        return mixPrpLst;
    }


    public void setStockViewMap(HashMap stockViewMap) {
        this.stockViewMap = stockViewMap;
    }

    public HashMap getStockViewMap() {
        return (HashMap)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_stockViewMap");
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getMacId() {
        return macId;
    }

    public void setLclIp(String lclIp) {
        this.lclIp = lclIp;
    }

    public String getLclIp() {
        return lclIp;
    }

    public void setAssortViewMap(ArrayList assortViewMap) {
        this.assortViewMap = assortViewMap;
    }

    public ArrayList getAssortViewMap() {
        return assortViewMap;
    }

    public void setSvdmdDis(ArrayList svdmdDis) {
        this.svdmdDis = svdmdDis;
    }

    public ArrayList getSvdmdDis() {
        return svdmdDis;
    }

    public void setPageDetails(HashMap pageDetails) {
        this.pageDetails = pageDetails;
    }

    public HashMap getPageDetails() {
        return pageDetails;
    }

    public void setIsMix(String isMix) {
        this.isMix = isMix;
    }

    public String getIsMix() {
        return isMix;
    }

    public void setDfStkStt(ArrayList dfStkStt) {
        this.dfStkStt = dfStkStt;
    }

    public ArrayList getDfStkStt() {
        return dfStkStt;
    }

    public void setSlexId(String slexId) {
        this.slexId = slexId;
    }

    public String getSlexId() {
        return slexId;
    }

    public void setNmemassmaillist(ArrayList nmemassmaillist) {
        this.nmemassmaillist = nmemassmaillist;
    }

    public ArrayList getNmemassmaillist() {
        return nmemassmaillist;
    }

    public void setMixScrhLst(ArrayList mixScrhLst) {
        this.mixScrhLst = mixScrhLst;
    }

    public ArrayList getMixScrhLst() {
        return mixScrhLst;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getLogId() {
        return logId;
    }

    public void setRoleLst(ArrayList roleLst) {
        this.roleLst = roleLst;
    }

    public ArrayList getRoleLst() {
        return roleLst;
    }

    public void setSetUppartyId(int setUppartyId) {
        this.setUppartyId = setUppartyId;
    }

    public int getSetUppartyId() {
        return setUppartyId;
    }

    public void setSetUpTermId(int setUpTermId) {
        this.setUpTermId = setUpTermId;
    }

    public int getSetUpTermId() {
        return setUpTermId;
    }

    public void setSetUpEX(String setUpEX) {
        this.setUpEX = setUpEX;
    }

    public String getSetUpEX() {
        return setUpEX;
    }

    public void setSetUpbyrNm(String setUpbyrNm) {
        this.setUpbyrNm = setUpbyrNm;
    }

    public String getSetUpbyrNm() {
        return setUpbyrNm;
    }

    public void setSetUppartyNm(String setUppartyNm) {
        this.setUppartyNm = setUppartyNm;
    }

    public String getSetUppartyNm() {
        return setUppartyNm;
    }

    public void setSetUpTermNm(String setUpTermNm) {
        this.setUpTermNm = setUpTermNm;
    }

    public String getSetUpTermNm() {
        return setUpTermNm;
    }

    public void setSetUpbyrId(int setUpbyrId) {
        this.setUpbyrId = setUpbyrId;
    }
    

    public int getSetUpbyrId() {
        return setUpbyrId;
    }

    public void setDf_term(String df_term) {
        this.df_term = df_term;
    }

    public String getDf_term() {
        return df_term;
    }

    public void setDf_xrt(String df_xrt) {
        this.df_xrt = df_xrt;
    }

    public String getDf_xrt() {
        return df_xrt;
    }

    public void setRecPktList(ArrayList recPktList) {
        this.recPktList = recPktList;
    }

    public ArrayList getRecPktList() {
        return recPktList;
    }

    public void setRfiddevice(ArrayList rfiddevice) {
        this.rfiddevice = rfiddevice;
    }

    public ArrayList getRfiddevice() {
        return (ArrayList)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_rfiddevice");
    }


    public void setRfid_seq(String rfid_seq) {
        this.rfid_seq = rfid_seq;
    }

    public String getRfid_seq() {
        return rfid_seq;
    }

    public void setMsgheader(String msgheader) {
        this.msgheader = msgheader;
    }

    public String getMsgheader() {
        return msgheader;
    }

    public void setPageDetailsdf(HashMap pageDetailsdf) {
        this.pageDetailsdf = pageDetailsdf;
    }

    public HashMap getPageDetailsdf() {
        return pageDetailsdf;
    }

    public void setIPageNo(int iPageNo) {
        this.iPageNo = iPageNo;
    }

    public int getIPageNo() {
        return iPageNo;
    }

    public void setPageCt(int pageCt) {
        this.pageCt = pageCt;
    }

    public int getPageCt() {
        return pageCt;
    }

    public void setChkNmeIdnList(ArrayList chkNmeIdnList) {
        this.chkNmeIdnList = chkNmeIdnList;
    }

    public ArrayList getChkNmeIdnList() {
        return chkNmeIdnList;
    }

    public void setPageblockList(ArrayList pageblockList) {
        this.pageblockList = pageblockList;
    }

    public ArrayList getPageblockList() {
        return pageblockList;
    }

    public void setSrchBaseOn(String srchBaseOn) {
        this.srchBaseOn = srchBaseOn;
    }

    public String getSrchBaseOn() {
        return srchBaseOn;
    }

    public void setSrchBaseOnLst(String srchBaseOnLst) {
        this.srchBaseOnLst = srchBaseOnLst;
    }

    public String getSrchBaseOnLst() {
        return srchBaseOnLst;
    }

    public void setCrtwtPrpLst(ArrayList crtwtPrpLst) {
        this.crtwtPrpLst = crtwtPrpLst;
    }

    public ArrayList getCrtwtPrpLst() {
        return crtwtPrpLst;
    }

    public void setDfGrpNmeIdn(String dfGrpNmeIdn) {
        this.dfGrpNmeIdn = dfGrpNmeIdn;
    }

    public String getDfGrpNmeIdn() {
        return dfGrpNmeIdn;
    }

    public void setYrList(ArrayList yrList) {
        this.yrList = yrList;
    }

    public ArrayList getYrList() {
        return yrList;
    }

    public void setMonthList(ArrayList monthList) {
        this.monthList = monthList;
    }

    public ArrayList getMonthList() {
        return monthList;
    }

    public void setDfNme(String dfNme) {
        this.dfNme = dfNme;
    }

    public String getDfNme() {
        return dfNme;
    }

    public void setJsVersion(String jsVersion) {
        this.jsVersion = jsVersion;
    }

    public String getJsVersion() {
        return jsVersion;
    }

    public void setConnectBy(String connectBy) {
        this.connectBy = connectBy;
    }

    public String getConnectBy() {
        return connectBy;
    }

    public void setDbTyp(String dbTyp) {
        this.dbTyp = dbTyp;
    }

    public String getDbTyp() {
        return dbTyp;
    }

    public void setEventList(HashMap eventList) {
        this.eventList = eventList;
    }

    public HashMap getEventList() {
        return eventList;
    }

    public void setImagelistDtl(ArrayList imagelistDtl) {
        this.imagelistDtl = imagelistDtl;
    }

    public ArrayList getImagelistDtl() {
        return (ArrayList)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_ImaageDtls");
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setMemo_lmt(String memo_lmt) {
        this.memo_lmt = memo_lmt;
    }

    public String getMemo_lmt() {
        return memo_lmt;
    }

    public void setCrt_lmt(String crt_lmt) {
        this.crt_lmt = crt_lmt;
    }

    public String getCrt_lmt() {
        return crt_lmt;
    }

    public void setPrpSplit(String prpSplit) {
        this.prpSplit = prpSplit;
    }

    public String getPrpSplit() {
        return prpSplit;
    }

    public void setBrnchDlvList(ArrayList brnchDlvList) {
        this.brnchDlvList = brnchDlvList;
    }

    public ArrayList getBrnchDlvList() {
        return brnchDlvList;
    }

    public void setBrnchStkIdnLst(ArrayList brnchStkIdnLst) {
        this.brnchStkIdnLst = brnchStkIdnLst;
    }

    public ArrayList getBrnchStkIdnLst() {
        return brnchStkIdnLst;
    }

    public void setRap_pct(int rap_pct) {
        this.rap_pct = rap_pct;
    }

    public int getRap_pct() {
        return rap_pct;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public void setLoApplNm(String loApplNm) {
        this.loApplNm = loApplNm;
    }

    public String getLoApplNm() {
        return loApplNm;
    }

    public void setDashboardvisibility(ArrayList dashboardvisibility) {
        this.dashboardvisibility = dashboardvisibility;
    }

    public ArrayList getDashboardvisibility() {
        return dashboardvisibility;
    }

    public void setSaleperson(ArrayList saleperson) {
        this.saleperson = saleperson;
    }

    public ArrayList getSaleperson() {
        return saleperson;
    }

    public void setGroupcompany(ArrayList groupcompany) {
        this.groupcompany = groupcompany;
    }

    public ArrayList getGroupcompany() {
        return groupcompany;
    }

    public void setSeqidn(String seqidn) {
        this.seqidn = seqidn;
    }

    public String getSeqidn() {
        return seqidn;
    }

    public void setCompareWith(String compareWith) {
        this.compareWith = compareWith;
    }

    public String getCompareWith() {
        return compareWith;
    }

    public void setSmsModeCounter(int smsModeCounter) {
        this.smsModeCounter = smsModeCounter;
    }

    public int getSmsModeCounter() {
        return smsModeCounter;
    }

    public void setAuth_mode(String auth_mode) {
        this.auth_mode = auth_mode;
    }

    public String getAuth_mode() {
        return auth_mode;
    }

    public void setNmestatus(String nmestatus) {
        this.nmestatus = nmestatus;
    }

    public String getNmestatus() {
        return nmestatus;
    }

    public void setSortPrpLst(ArrayList SortPrpLst) {
        this.SortPrpLst = SortPrpLst;
    }

    public ArrayList getSortPrpLst() {
        return SortPrpLst;
    }

    public void setNoteperson(ArrayList noteperson) {
        this.noteperson = noteperson;
    }

    public ArrayList getNoteperson() {
        return noteperson;
    }

    public void setSmxScrhLst(ArrayList smxScrhLst) {
        this.smxScrhLst = smxScrhLst;
    }

    public ArrayList getSmxScrhLst() {
        return smxScrhLst;
    }

    public void setSmxPrpLst(ArrayList smxPrpLst) {
        this.smxPrpLst = smxPrpLst;
    }

    public ArrayList getSmxPrpLst() {
        return smxPrpLst;
    }

    public void setSpaceCodeIp(String spaceCodeIp) {
        this.spaceCodeIp = spaceCodeIp;
    }

    public String getSpaceCodeIp() {
        return spaceCodeIp;
    }

    public void setVwMdl(String vwMdl) {
        this.vwMdl = vwMdl;
    }

    public String getVwMdl() {
        return vwMdl;
    }

    public void setSearchView(String searchView) {
        this.searchView = searchView;
    }

    public String getSearchView() {
        return searchView;
    }

    public void setStktallylinkWise(String stktallylinkWise) {
        this.stktallylinkWise = stktallylinkWise;
    }

    public String getStktallylinkWise() {
        return stktallylinkWise;
    }

    public void setPri_chng_typ(String pri_chng_typ) {
        this.pri_chng_typ = pri_chng_typ;
    }

    public String getPri_chng_typ() {
        return pri_chng_typ;
    }

    public void setPri_chng_val(String pri_chng_val) {
        this.pri_chng_val = pri_chng_val;
    }

    public String getPri_chng_val() {
        return pri_chng_val;
    }

    public void setSrchsttLst(ArrayList srchsttLst) {
        this.srchsttLst = srchsttLst;
    }

    public ArrayList getSrchsttLst() {
        return srchsttLst;
    }

    public void setShorttrms(String shorttrms) {
        this.shorttrms = shorttrms;
    }

    public String getShorttrms() {
        return shorttrms;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel1() {
        return level1;
    }

    public void setBlockLs(String blockLs) {
        this.blockLs = blockLs;
    }

    public String getBlockLs() {
        return blockLs;
    }

    public void setIememov(ArrayList iememov) {
        this.iememov = iememov;
    }

    public ArrayList getIememov() {
        return iememov;
    }

    public void setMemoTypSel(String memoTypSel) {
        this.memoTypSel = memoTypSel;
    }

    public String getMemoTypSel() {
        return memoTypSel;
    }

    public void setRghScrhLst(ArrayList rghScrhLst) {
        this.rghScrhLst = rghScrhLst;
    }

    public ArrayList getRghScrhLst() {
        return rghScrhLst;
    }

  

    public void setRghVwList(ArrayList rghVwList) {
        this.rghVwList = rghVwList;
    }

    public ArrayList getRghVwList() {
        return rghVwList;
    }

    public void setMem_ip(String mem_ip) {
        this.mem_ip = mem_ip;
    }

    public String getMem_ip() {
        return mem_ip;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getCnt() {
        return cnt;
    }

    public void setMem_port(int mem_port) {
        this.mem_port = mem_port;
    }

    public int getMem_port() {
        return mem_port;
    }

    public void setQuarterList(ArrayList quarterList) {
        this.quarterList = quarterList;
    }

    public ArrayList getQuarterList() {
        return quarterList;
    }

    public void setMongoanalysis(String mongoanalysis) {
        this.mongoanalysis = mongoanalysis;
    }

    public String getMongoanalysis() {
        return mongoanalysis;
    }

    public void setReportFor(String reportFor) {
        this.reportFor = reportFor;
    }

    public String getReportFor() {
        return reportFor;
    }

    public void setFromnewgoods(String fromnewgoods) {
        this.fromnewgoods = fromnewgoods;
    }

    public String getFromnewgoods() {
        return fromnewgoods;
    }

    public void setTonewgoods(String tonewgoods) {
        this.tonewgoods = tonewgoods;
    }

    public String getTonewgoods() {
        return tonewgoods;
    }

    public void setDfAcct(String dfAcct) {
        this.dfAcct = dfAcct;
    }

    public String getDfAcct() {
        return dfAcct;
    }

    public void setAllowPlMnLst(ArrayList allowPlMnLst) {
        this.allowPlMnLst = allowPlMnLst;
    }

    public ArrayList getAllowPlMnLst() {
        return allowPlMnLst;
    }

    public void setSearchPriCalcView(String searchPriCalcView) {
        this.searchPriCalcView = searchPriCalcView;
    }

    public String getSearchPriCalcView() {
        return searchPriCalcView;
    }

    public void setKtsViewPrpDtls(HashMap ktsViewPrpDtls) {
        this.ktsViewPrpDtls = ktsViewPrpDtls;
    }

    public HashMap getKtsViewPrpDtls() {
        return (HashMap)new JspUtil().getFromMem(mem_ip,mem_port,cnt+"_ktsViewPrpDtls");
    }

    public void setSoldPrpLst(ArrayList soldPrpLst) {
        this.soldPrpLst = soldPrpLst;
    }

    public ArrayList getSoldPrpLst() {
        return soldPrpLst;
    }

    public void setDbUsr(String dbUsr) {
        this.dbUsr = dbUsr;
    }

    public String getDbUsr() {
        return dbUsr;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }

    public void setBkPrcOn(String bkPrcOn) {
        this.bkPrcOn = bkPrcOn;
    }

    public String getBkPrcOn() {
        return bkPrcOn;
    }

    public void setSuggBoxLst(ArrayList suggBoxLst) {
        this.suggBoxLst = suggBoxLst;
    }

    public ArrayList getSuggBoxLst() {
        return suggBoxLst;
    }
}
