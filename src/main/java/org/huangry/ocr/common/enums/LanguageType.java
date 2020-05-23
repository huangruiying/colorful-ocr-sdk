package org.huangry.ocr.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum LanguageType {

    /** 英文 */
    ENG("eng","英文"),
    /** 中文 */
    CHI_SIM("chi_sim","中文"),
    ;

    private String language;

    private String name;

    private static final List<Map<String,String>> data;

    LanguageType(String language, String name){
        this.language = language;
        this.name = name;
    }


    static {
        LanguageType[] values = values();

        // init data
        data = new ArrayList(values.length);
        for (LanguageType value : values) {
            Map<String, String> map = new HashMap();
            map.put("name",value.name);
            map.put("type",value.language);
            data.add(map);
        }
    }

    public static List<Map<String,String>> data(){
        return data;
    }

    public String value(){
        return this.language;
    }



}
