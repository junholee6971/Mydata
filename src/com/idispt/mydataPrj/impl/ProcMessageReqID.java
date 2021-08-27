package com.idispt.mydataPrj.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idispt.mydataPrj.util.ComUtil;

public class ProcMessageReqID extends ProcMessageTemplate {

    @Override
    protected void init() {
        setSqlid("getCi");
        setMaxlen(111);

    }

    @Override
    protected boolean parseMsgAndSet(String msg) {
        setCi(msg.substring(22,110).trim());
       
        return true;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Map setParameter() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ci", getCi());
        
        return  map;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected byte[] makeSubMsg(List res) {
        String msg = getMsg();
        
        if (res == null  || res.size() == 0) return ComUtil.errorProc(msg, "40403");
                
        StringBuilder sb = new StringBuilder();
        
        sb.append("G")
          .append(msg.substring(1,110))
          .append(String.format("%5.5s\r", ""));
        
        return sb.toString().getBytes();
     
    }

}
