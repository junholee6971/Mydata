package com.idispt.mydataPrj.impl;


import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.idispt.mydataPrj.util.ComUtil;
import com.idispt.mydataPrj.vo.InvChrgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProcMessageReqIB extends ProcMessageTemplate {
    
    private static final Logger logger = LoggerFactory.getLogger(ProcMessageReqIB.class);
   
    private String said;
    private String invYyyymm;
    
    
    public String getMsg() {
        if (msg == null ) return "";
        return msg;
    }
      
    private void setSaid(String said) {
        this.said = said;
    }
    
    public String getSaid() {
        if (said == null ) return "";
        return said;
    }
   
    private void setInvYyyymm(String invYyyymm) {
        this.invYyyymm = invYyyymm;
    }
    
    public String getInvYyyymm() {
        if (invYyyymm == null ) return "";
        return invYyyymm;
    }

    @Override
    protected void init() {
        setSqlid("getChrgByInvMonth");
        setMaxlen(132);
    }
       
    @Override
    protected boolean parseMsgAndSet(String msg) {
        
        setCi(msg.substring(22,110).trim());
        setSaid(msg.substring(110, 125).trim());
        setInvYyyymm(msg.substring(125, 132).trim());
        
        return true;
    }
           
    @Override
    protected Map<String, String> setParameter() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ci", getCi());
        map.put("said", getSaid());
        map.put("invYyyymm", getInvYyyymm());
        
        return  map;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected byte[] makeSubMsg(List res) {
        String msg = getMsg();
        
        if (res == null  || res.size() == 0) return ComUtil.errorProc(msg, "40402");
                
        StringBuilder sb = new StringBuilder();
        
        sb.append("G")
          .append(msg.substring(1,115))
          .append(String.format("%03d", res.size()));
        
        for(InvChrgVo vo : (List<InvChrgVo>)res) {
            sb.append(vo.getInvamt())
              .append(vo.getDueDay());
        }
          
        sb.append("\r").toString().getBytes();
        
        return sb.toString().getBytes();
        
    }
    
}
