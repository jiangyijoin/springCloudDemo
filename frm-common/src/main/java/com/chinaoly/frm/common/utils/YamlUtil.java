package com.chinaoly.frm.common.utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 操作yaml配置文件
 * Created by jiangyi on 2017/8/31.
 */
public class YamlUtil {

    /**
     * 使用YAML配置文件
     * @param profilepath
     * @param map
     */
    public static void updatePropertiesWithYaml(String profilepath,HashMap<String,String> map) {
        if(map == null){
            return;
        }
        Properties props = new Properties();
        try {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);

            Map<String, Object> yamlMap = new LinkedHashMap<>();
            for(Map.Entry<String, String> entry : map.entrySet()){
                yamlMap = setYamlProperties(yamlMap, entry.getKey(), entry.getValue());
            }

            yaml.dump(yamlMap, new FileWriter(profilepath));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("属性文件更新错误");
        }
    }

    public static Map<String, Object> setYamlProperties(Map<String, Object> data, String keyStr, Object value){
        String[] keyItem = keyStr.split("[.]");
        Map<String, Object> iter = data;
        for(int i=0; i<keyItem.length; i++){
            String key = keyItem[i];
            if(iter.containsKey(key)){
                if(i < keyItem.length -1){
                    iter = (Map<String, Object>)iter.get(key);
                }else{
                    iter.put(key, value);
                }
            }else{
                if(i < keyItem.length-1) {
                    iter.put(key, new LinkedHashMap<String, Objects>());
                    iter = (Map<String, Object>)iter.get(key);
                }else{
                    iter.put(key, value);
                }
            }
        }

        return data;
    }
}
