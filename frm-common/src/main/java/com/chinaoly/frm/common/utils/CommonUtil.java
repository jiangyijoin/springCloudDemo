package com.chinaoly.frm.common.utils;

public class CommonUtil {

	public static String  DATE_LONG_FORMAT = "yyyy-MM-dd hh:mm:ss";
	
	public static String  DATE_SHORT_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 判断是否为空，空为true，不空为false
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		if(object == null) { return true;}
		else { return false;}
	}
	
	/**
	 * 判断是否为空，空为true，不空为false
	 * @param object
	 * @return
	 */
	public static boolean isNull(String value) {
		if(value == null || value.getClass() != String.class || "".equals(value.trim())) { 
			return true;
		}else { 
			return false;
		}
	}
	
	/**
	 * 判断是否为空，空为抛出空指针异常
	 * @param object
	 * @param message
	 */
    public static void isNull(Object object ,String message) {
        if (isNull(object)){
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
	 * 判断是否为空，空为抛出空指针异常
	 * @param object
	 * @param message
	 */
    public static void isNull(String object ,String message) {
        if (isNull(object)){
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * 判断是否为空，空则返回"",不空返回value原值
     * @param value
     * @return
     */
    public static String nvl(String value) {
        if (isNull(value) ){ return ""; }
        else { return value; }
    }
    
    /**
     * 判断是否为空，空则返回defaultValue,不空返回value原值
     * @param value
     * @param defaultValue
     * @return
     */
    public static String nvlString(String value, String defaultValue) {
        if (isNull(value) ){ return defaultValue; }
        else { return value; }
    }
    
    /**
     * 判断是否为空，空则返回defaultValue,不空返回value的Integer类型值,若不是数字，返回-1
     * @param value  数字类型的字符串
     * @param defaultValue
     * @return
     */
    public static int nvlInteger(String value, int defaultValue) {
        if (isNull(value) ){ return defaultValue; }
        else { 
        	int result;
        	try {
        		result = Integer.parseInt(value); 
        	}catch(ClassCastException e) {
        		result = -1;
        	}catch(Exception e1) {
        		result = -1;
        	}
        	return result;
        }
    }
    
    /**
     * 判断是否为空，空则返回defaultValue,不空返回value的Short类型值,若不是数字，返回-1
     * @param value  数字类型的字符串
     * @param defaultValue
     * @return
     */
    public static short nvlShort(String value, short defaultValue) {
    	if (isNull(value) ){ return defaultValue; }
        else { 
        	short result;
        	try {
        		result = Short.parseShort(value); 
        	}catch(ClassCastException e) {
        		result = -1;
        	}catch(Exception e1) {
        		result = -1;
        	}
        	return result;
        }
    }
    
    /**
     * 日期格式判断  ："yyyy-MM-dd hh:mm:ss" 或者 "yyyy-MM-dd"
     */
   /* public static boolean isDate(String date) {
    	if(isNull(date)) return false;
    	
    	date = date.trim();
    	if(date.length() == 10) {
    		String[] temps = date.split("-");
    		if(temps.length != 3)	return false;
    		int year,month,day;
    		try {
    			year = Integer.parseInt(temps[0]);
        		month = Integer.parseInt(temps[1]);
        		if(month <1 || month >12) return false;
        		day = Integer.parseInt(temps[2]);
    		}catch(ClassCastException e) { 
    			return false;
    		}catch(Exception e) { 
    			return false;
    		}
    		
    	}
    	if(date.length() != 19 && date.length() != 10) return false;
    	
    	
    	return true;
    }*/
    
    
    
}
