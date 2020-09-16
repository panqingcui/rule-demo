package com.pqc.rule.demo;

import java.util.HashMap;
import java.util.Map;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 * <p>
 * 创建日期 2019/1/29
 *
 * @author panqingcui
 * @since 1.0.0
 */
@Slf4j
public class SalesCommissions {
    @Test
    public void SalesCommissions() {
        Map<String, Object> source = new HashMap<>();
        source.put("role", "销售经理");
        source.put("commissions_rate_type", "new");
        source.put("contract_type", "产品");
        // source.put("discount_min",0);
        // source.put("discount_max",0.5);
        source.put("gross", 40000);
        source.put("app_id", "8Usrp3M88v75TP90Iw1IYwNA");
        Map<String, Object> app = new HashMap<>();
        app.put("8Usrp3M88v75TP90Iw1IYwNA", 50000);
        Facts facts = new Facts();
        facts.put("source", source);
        facts.put("app", app);
        Rules rules = new Rules();
        Rule rule = new MVELRule().description("销售经理提成标准报价 0-0.5 提成").name("销售经理 0-0.5 提成")
            .when("source.gross >= 0 * app.get(source.app_id) && source.gross < 0.5 * app.get(source.app_id)")
            .then("source.put('result',source.gross * 0.04)").then("source.put('commissions_rate','2%')");
        Rule rule1 = new MVELRule().description("销售经理提成标准报价 0.5-1 提成").name("销售经理 0.5-0 提成")
            .when(" source.gross >= 0.5 * app.get(source.app_id)").then("source.put('result',source.gross * 0.04)")
            .then("source.put('commissions_rate','4%')");
        rules.register(rule);
        rules.register(rule1);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
        log.info("------>{}", source);
    }
}
