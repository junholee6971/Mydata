package com.idispt.mydataPrj;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idispt.mydataPrj.Interface.ProcMessage;
import com.idispt.mydataPrj.impl.ProcMessageReqIB;
import com.idispt.mydataPrj.impl.ProcMessageReqID;
import com.idispt.mydataPrj.impl.ProcMessageReqIL;
import com.idispt.mydataPrj.session.SessionFactory;
import com.idispt.mydataPrj.util.ComUtil;

public class BranchProc {
    
    private static final Logger logger = LoggerFactory.getLogger(BranchProc.class);
    
    private String msg;
    
    public String getMsg() {
        if (msg == null ) return "";
        return msg;
    }
    
    
    public byte[] branchDetermin(String msg) {
        
        if(msg.length() < 100) {
            logger.error("***************************");
            logger.error("Message too short!!");
            logger.error("***************************");
            return null;
        }
        
        byte[] rtn = null;
        ProcMessage proc;
        String curTime = insertMsg(msg);
        
        switch (msg.substring(0,2)) {
        case "IB": //청구정보 조회 요청
            proc = new ProcMessageReqIB();
            break;

        case "ID":  //본인 확인 요청
            proc = new ProcMessageReqID();
            break;
            
        case "IL":  //계약 목록 조회 요청
            proc = new ProcMessageReqIL();
            break;
            
        case "IP":  //결제 내역 요청
            return ComUtil.errorProc(msg, "50004") ;
//            break;
            
        case "IS":  //상태조회 
            return ComUtil.errorProc(msg, "50004") ;
//            break;
            
        case "IT":  //거래 내역 조회 요청
            return ComUtil.errorProc(msg, "50004") ;
//            break;

            
        default:
            return ComUtil.errorProc(msg, "50004") ;
        }
        
        rtn = proc.process(msg);
        
        if (!"".equals(curTime)) updateMsg(curTime, new String(rtn));
        return rtn;
        
        
    }
    
    /*
     * Request Message를 저장하고 timestamp를 리턴한다.
     */
    private String insertMsg(String msg) {
         
         SqlSession session = null;
         String curtime = null; 
         
         try {
             session = SessionFactory.getSqlSession();

             curtime = session.selectOne("getTimestamp");
             
             HashMap<String, String> map = new HashMap<>();
             map.put("curtime", curtime);
             map.put("msg", msg);
             
             session.insert("insertMsg", map);         

             session.commit();
         }catch(Exception e) {
             logger.error(e.getMessage());
             session.rollback();
             return "";
         } 
         
         session.close();
         
         return curtime;
         
    }
    
    
    /*
     * Res Message를 update 
     */
    private String updateMsg(String curtime, String resmsg) {
         
         SqlSession session = null;
        
         try {
             session = SessionFactory.getSqlSession();

             HashMap<String, String> map = new HashMap<>();
             map.put("curtime", curtime);
             map.put("resmsg", (resmsg.length()>4000?resmsg.substring(0, 3999):resmsg));
             
             session.update("updateMsg", map);
             session.commit();
             
         }catch(Exception e) {
             logger.error(e.getMessage());
             session.rollback();
             return "";
         } 
         
         session.close();
         
         return curtime;
         
    }

}
