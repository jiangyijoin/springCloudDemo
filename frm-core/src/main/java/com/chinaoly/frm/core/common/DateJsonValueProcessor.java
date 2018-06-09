package com.chinaoly.frm.core.common;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;

/**
 * 说明：解決json-lib中日期转换问题
 *
 * @author jiangyi
 * @Date Mar 15, 2018
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

    private String format;

    public DateJsonValueProcessor(String format) {
        this.format = format;
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return null;
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (value == null) {
            return "";
        }
        if (value instanceof java.sql.Timestamp) {
            String str = new SimpleDateFormat(format).format((java.sql.Timestamp) value);
            return str;
        }
        if (value instanceof java.util.Date) {
            String str = new SimpleDateFormat(format).format((java.util.Date) value);
            return str;
        }
        return value.toString();
    }
}