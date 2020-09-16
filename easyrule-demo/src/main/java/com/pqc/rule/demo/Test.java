package com.pqc.rule.demo;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pqc.rule.demo.model.Person;

/**
 * 功能描述,该部分必须以中文句号结尾。
 *
 * @author panqingcui
 * @create 2018-08-24 15:19
 */
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    static Instrumentation instrumentation;

    @org.junit.Test
    public void test() throws Exception {
        // Action action = new MVELAction("person.name = '张三'");
        // Condition isAdult = new MVELCondition("person.age > 18 && person.age <20");
        Person person = new Person("市场部", 30);
        Rule rule = new MVELRule().name("find").description("自动分配").priority(3).when("person.age >10")
            .then("person.name = ' 张 priority30'");
        Rule rule1 = new MVELRule().name("find1").description("find1").priority(2).when("person.age >20")
            .then("person.name = ' 张 priority20'");
        Facts facts = new Facts();
        Rules rules = new Rules();
        rules.register(rule);
        rules.register(rule1);
        facts.put("person", person);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        Map<Rule, Boolean> check = rulesEngine.check(rules, facts);
        List<Rule> rulesList = new ArrayList<>();
        for (Map.Entry<Rule, Boolean> entry : check.entrySet()) {
            if (entry.getValue()) {
                rulesList.add(entry.getKey());
            }
        }
        Rules rules1 = new Rules();
        rulesList.sort(Comparator.comparing(Rule::getPriority).reversed());
        rules1.register(rulesList.get(0));
        rulesEngine.fire(rules1, facts);
        LOGGER.info("--->" + rulesList.get(0));
        // fire(person);
        // String expression = "clue.a == null && clue.b == nil";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sales_team_name", "销售部");
        paramMap.put("status", "1");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("clue", paramMap);
        // Object object = MVEL.eval(expression, map1);
        // fireMap(map1);
        // fireMap1(paramMap);
        // LOGGER.info(">>>>>{}",object);
        // Facts facts = new Facts();
        // facts.put("person", person);
        // // // when
        // // boolean evaluationResult = isAdult.evaluate(facts);
        // // if (evaluationResult) {
        // // action.execute(facts);
        // // }
        // // System.out.println(person);
        // // LOGGER.info("-->{}", person);
        // Rule rule = new MVELRule().name("find").description("自动分配").priority(3)
        // .when("person.age >10 && person.age < 22").then("person.name = ' 张 priority1'");
        // Rule rule1 = new MVELRule().name("find2").description("自动分配").priority(2)
        // .when("person.age >10 && person.age < 22").then("person.name = ' 张 priority 2'");
        // Rule rule2 = new MVELRule().name("find 3").description("测试").priority(1).when("person.name == '市场部'")
        // .then("person.name = ' 张 find 3'");
        // // rule.execute(facts);
        // Rules rules = new Rules();
        // rules.register(rule);
        // rules.register(rule1);
        // rules.register(rule2);
        // RulesEngine rulesEngine = new DefaultRulesEngine();
        // rulesEngine.fire(rules, facts);
        LOGGER.info("----------->{}", person);
        // Map<Rule, Boolean> map = rulesEngine.check(rules, facts);
        // User user = new User();
        // Address address = new Address();
        // address.setPostCode("123456");
        // user.setAddress(address);
        // user.setName("123456");
        // Facts facts1 = new Facts();
        // facts1.put("user", user);
        // Rule rule3 = new MVELRule().name(" rule 3").description("测试组合").priority(1)
        // .when("user.address.postCode ~='\\\\d{6}'").then("user.name = 'successfully'");
        // rules.register(rule3);
        // rulesEngine.fire(rules, facts1);
        // "user_name".replaceAll("_", "");
        // LOGGER.info("-->{}", user);
        // String in = "source_user_name";
        // System.out.println("in is= " + in);
        // StringBuffer sb = new StringBuffer();
        // Pattern p = Pattern.compile("_[a-z|A-Z]");
        // Matcher m = p.matcher(in);
        // while (m.find()) { // Find each match in turn; String can't do this.
        // // String name = m.group(1); // Access a submatch group; String can't do this.
        // m.appendReplacement(sb, m.group().toUpperCase().replaceAll("_", ""));
        // System.out.println("m.group() is= " + m.group());
        // }
        // m.appendTail(sb);
        // System.out.println("sb is= " + sb);
        // Byte a = 1;
        // int c = 1;
        // UserModel userModel = new UserModel();
        // userModel.setAge(10);
        // Rule rule4 = new MVELRule().name("mvel rule").description("description----").priority(1)
        // .when("userModel.age <=10").then("userModel.name = 'zhangsan'");
        // rules.register(rule4);
        // Facts facts2 = new Facts();
        // facts2.put("userModel", userModel);
        // rulesEngine.fire(rules, facts2);
        // System.out.println("userModel is= " + userModel);
    }

    public static void fireMap1(Map<String, Object> param) {
        Facts facts = new Facts();
        facts.put("p", param);
        Rule rule = new MVELRule().name("mvel map test").description("map测试").priority(1)
            .when("p.sales_team_name == '销售部' or p.status == '1'").then("p.sales_team_name = '销售部2222' ")
            .then("p.put('1','2') ")
            .then(" com.pqc.rule.demo.service.Producer producer = new com.pqc.rule.demo.service.Producer();"
                  + " producer.send(p);");
        Rules rules = new Rules();
        rules.register(rule);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
        LOGGER.info("----->>>{}", param);
    }

    public static void fireMap(Map<String, Object> param) {
        Facts facts = new Facts();
        facts.put("p", param);
        Rule rule = new MVELRule().name("mvel map test").description("map测试").priority(1)
            .when("p.clue.sales_team_name == '销售部' && p.clue.status == '1'")
            .then(" com.pqc.rule.demo.service.Producer producer = new com.pqc.rule.demo.service.Producer();"
                  + " java.util.HashMap map  = new java.util.HashMap();"
                  + " java.util.HashMap all  = new java.util.HashMap();"
                  + " map.put('type','clue');"
                  + " map.put('action_type','updated');"
                  + " map.put('user_name','彭宇');"
                  + " map.put('status','1');"
                  + " map.put('sales_team_name','销售部');"
                  + " all.put('clue',map);"
                  + " java.util.HashMap customerMap  = new java.util.HashMap();"
                  + " customerMap.put('customer_type','意向');"
                  + " all.put('customer',customerMap);"
                  + " producer.send(all)");
        Rules rules = new Rules();
        rules.register(rule);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }

    public static void fire(Person person) {
        Facts facts = new Facts();
        facts.put("p", person);
        // // when
        // boolean evaluationResult = isAdult.evaluate(facts);
        // if (evaluationResult) {
        // action.execute(facts);
        // }
        // System.out.println(person);
        // LOGGER.info("-->{}", person);
        // then(" java.util.HashMap map = new java.util.HashMap();"
        // + " map.put('a','b');"
        // + " p.add('com.antfact.oplate.auditlog',map)")
        List<String> strings = new ArrayList<>();
        strings.add("1");
        List list = new ArrayList<>();
        Rule rule = new MVELRule().name("find").description("自动分配").priority(1)
            .when("p.age <50").then("p.name = ' 张 priority1'")
            .then(" com.pqc.rule.demo.service.Producer producer = new com.pqc.rule.demo.service.Producer();"
                  + " java.util.HashMap map  = new java.util.HashMap();"
                  + " java.util.HashMap all  = new java.util.HashMap();"
                  + " map.put('type','clue');"
                  + " map.put('action_type','updated');"
                  + " map.put('user_name','彭宇');"
                  + " map.put('status','1');"
                  + " map.put('sales_team_name','销售部');"
                  + " map.put('test','" + person.getName() + "');"
                  + " all.put('clue',map);"
                  + " java.util.HashMap customerMap  = new java.util.HashMap();"
                  + " customerMap.put('customer_type','意向');"
                  + " all.put('customer',customerMap);"
                  + " producer.send(all)");
        // Rule rule1 = new MVELRule().name("find2").description("自动分配").priority(2)
        // .when("(person.age >10 && person.age < 22) || person.age == 30").then("person.name = ' 张 priority 2'");
        // Rule rule2 = new MVELRule().name("find 3").description("测试").priority(1)
        // .when("persion.name ")
        // .then("person.user_name = ' 张 find 3'");
        // rule.execute(facts);
        LOGGER.info("------------" + person);
        Rules rules = new Rules();
        rules.register(rule);
        // rules.register(rule1);
        // rules.register(rule2);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }

    /**
     * 下划线命名转驼峰式
     *
     * @param str
     * @return
     */
    public static String toCamel(String str) {
        StringBuffer buffer = new StringBuffer();
        str = str.toLowerCase().trim();
        char[] charArray = str.toCharArray();
        if (charArray != null) {
            for (int i = 0; i < charArray.length; i++) {
                if ('_' == charArray[i]) {
                    i = i + 1;
                    buffer.append(Character.toUpperCase(charArray[i]));
                } else {
                    buffer.append(charArray[i]);
                }
            }
        }
        return buffer.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        StringBuffer buffer = new StringBuffer();
        str = str.trim();
        char[] charArray = str.toCharArray();
        if (charArray != null) {
            for (int i = 0; i < charArray.length; i++) {
                if (Character.isUpperCase(charArray[i])) {
                    buffer.append("_");
                    buffer.append(Character.toLowerCase(charArray[i]));
                } else {
                    buffer.append(charArray[i]);
                }
            }
        }
        return buffer.toString();
    }
}
