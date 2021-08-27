package com.idispt.mydataPrj.util;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import sinsiway.*;

public class CryptoUtil {
//    private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);
    
	private static PcaSession session;
	private static final String initpath = String.format("petra_cipher_api.conf", "");

	static {
		init();
	}

	public static void init() {
		try {
			PcaSessionPool.initialize(initpath,"");
			session = PcaSessionPool.getSession();

		} catch (Exception e) {
//            logger.info("initialize error" + e.getMessage());
			e.printStackTrace();
		}
	}
	

	public static String decrypt(String s) {
		String result = null;
		try {
			if ( session == null ) session = PcaSessionPool.getSession();
			if ( s == null || "".equals(s) )
				result = "";
			else 
				result = session.decrypt("AES-256",s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}



    
    /*
     *@ 부분암호화 추가 함수 생년월일 + 성별 (7자리)
     */
    public static String decryptJumin(String s) {
        String result = null;
        try {
                if ( session == null ) session = PcaSessionPool.getSession();
                if ( s == null || "".equals(s) )
                        result = "";
                else
                        result = session.decrypt("JUMIN",s);
        } catch (Exception e) {
                e.printStackTrace();
        }
        return result;
    }
    public static String encrypt(String s) {
        String result = null;
        try {
            if ( session == null ) session = PcaSessionPool.getSession();
            if ( s == null || "".equals(s) )
                result = "";
            else 
                result = session.encrypt("AES-256",s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

