package com.pqc.rule.demo.service;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 * <p>
 * 创建日期 2019/2/12
 *
 * @author panqingcui
 * @since 1.0.0
 */
@Slf4j
public class AddProperty {
    public void add(JSONObject jsonObject) {
        log.info("---------add");
        String label = jsonObject.getString("label");
        log.info("---------label {}", label);
        if ("extension".equalsIgnoreCase(label)) {
            jsonObject.put("label", "new");
        }
    }

    public static void add1(JSONObject jsonObject, long interval, String unit) {
        log.info("---------add");
        String label = jsonObject.getString("label");
        log.info("---------label {}", label);
        if ("extension".equalsIgnoreCase(label)) {
            jsonObject.put("label", "new");
        }
        jsonObject.put("label", "new");
    }
}
