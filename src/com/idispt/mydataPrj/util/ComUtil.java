package com.idispt.mydataPrj.util;

public class ComUtil {
    public static byte[] errorProc(String msg, String errcd) {
        StringBuilder sb = new StringBuilder();
        return sb.append("E")
          .append(msg.substring(1,100))
          .append(errcd)
          .append("\r").toString().getBytes();
    }
    
    public static String nvl2OneZero(String str) {
        if(str == null || "".equals(str)) return "0";
        
        return str;
    }
}
