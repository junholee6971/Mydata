package com.idispt.mydataPrj.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idispt.mydataPrj.util.ComUtil;
import com.idispt.mydataPrj.vo.SubScribeListVo;

public class ProcMessageReqIL extends ProcMessageTemplate {
    
    private static final Logger logger = LoggerFactory.getLogger(ProcMessageReqIL.class);

    private String reqCnt;
    private String remainCnt;
    
    private void setReqCnt(String reqCnt) {
        this.reqCnt = ComUtil.nvl2OneZero(reqCnt);
    }
    
    public String getReqCnt() {
        if (reqCnt == null ) return "";
        return reqCnt;
    }
    
    private void setRemainCnt(String remainCnt) {
        this.remainCnt = ComUtil.nvl2OneZero(remainCnt);
    }
    
    public String getRemainCnt() {
        if (remainCnt == null ) return "";
        return remainCnt;
    }
    
    @Override
    protected void init() {
        setSqlid("getSubScribeList");
        setMaxlen(118);

    }

    @Override
    protected boolean parseMsgAndSet(String msg) {
        setCi(msg.substring(22,110).trim());
        setReqCnt(msg.substring(110, 113).trim());
        setRemainCnt(msg.substring(113, 117).trim());
        
        return true;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Map setParameter() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ci", getCi());
        map.put("reqCnt", getReqCnt());
        map.put("remainCnt", getRemainCnt());
            
        return  map;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected byte[] makeSubMsg(List res) {
        String msg = getMsg();
        
        if (res == null  || res.size() == 0) return ComUtil.errorProc(msg, "40402");
                
        StringBuilder sb = new StringBuilder();
        
        sb.append("G")
          .append(msg.substring(1,110));

        int i = 0;
        for(SubScribeListVo vo : (List<SubScribeListVo>)res) {
            if(i == 0) {
                sb.append(String.format("%-4.4s", ("0".equals(vo.getRemaincnt())? "    ":vo.getRemaincnt())))
                  .append(String.format("%05d", res.size()));
            }

            sb.append(String.format("%-15s", vo.getSaid()))
              .append(String.format("%-15s", vo.getSubscno()))
              .append(vo.getTeltype())
              .append(vo.getStatus());
            
            i++;
            
        }
          
        sb.append("\r").toString().getBytes();
        
        return sb.toString().getBytes();
        
    }

}
