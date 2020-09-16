package com.pqc.rule.demo;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.YamlRuleDefinitionReader;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pqc.rule.demo.model.Address;
import com.pqc.rule.demo.model.Sales;
import com.pqc.rule.demo.model.User;
import com.pqc.rule.demo.util.RulesContinuousGroup;
import com.pqc.rule.demo.util.RulesOfContinuousGroup;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class MultiMethodTest {
    @Test
    public void test3() {
        JSONObject source = new JSONObject();
        source.put("gross_profit", 10000.0);
        source.put("cost", 1000.0);
        source.put("opportunity_num", 0.5);
        Rules rules = new Rules();
        Facts facts = new Facts();
        facts.put("sales_commissions", source);
        Rule rule = new MVELRule().name("test").when(" sales_commissions.cost >0")
            .then("sales_commissions.commission_rate = 0.02")
            .then(
                "sales_commissions.commission_num = sales_commissions.gross_profit - sales_commissions.cost - sales_commissions.opportunity_num *200")
            .then(
                "sales_commissions.commission = sales_commissions.commission_num >0 ?sales_commissions.commission_num * sales_commissions.commission_rate :sales_commissions.commission_num");
        rules.register(rule);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
        log.info("------------------>{}", source);
    }

    @Test
    public void test6() throws Exception {
        MVELRuleFactory factory = new MVELRuleFactory(new YamlRuleDefinitionReader());

        // given
        //   File rulesDescriptor = new File("src/main/resources/composite-rules.yml");
        File rulesDescriptor = new File("src/main/resources/rules1.yml");

        // when
        Rules rules = factory.createRules(new FileReader(rulesDescriptor));
        //         RuleDefinitionReader ruleDefinitionReader = new YamlRuleDefinitionReader();
        //        // given
        //        File rulesDescriptor = new File("src/main/resources/composite-rules.yml");
        // when
        // when
        //  List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new FileReader(rulesDescriptor));
        Sales sales = new Sales();
        sales.setValue(5000);
        Sales sales1 = new Sales();
        sales1.setValue(150000);
        List<Sales> salesList = new ArrayList<>();
        salesList.add(sales);
        salesList.add(sales1);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        for (Sales s : salesList) {
            Facts facts = new Facts();
            facts.put("sales", s);
            rulesEngine.fire(rules, facts);

            // rulesEngine.fire(ruleDefinitions.get(0).getComposingRules(), facts);
            System.out.println(s);
        }


    }

    @Test
    public void test4() {
        Rule rule = new MVELRule().name("test")
            .when(
                " lead.status == '0' && lead.get('province') != nil && (lead.province.contains ('浙江') || lead.province.contains('湖北') || lead.province.contains('湖南') || lead.province.contains('江西') || lead.province.contains('福建') || lead.province.contains ('广西') || lead.province.contains('安徽') || lead.province.contains('贵州') || lead.province.contains('海南'))")
            .then(" lead.user_name = '陈铮星'");
        //  String j = "{'id':'36de417e-477b-4a13-a478-fabfdc17fe1b','organization_id':'36v8tudu9Z','record_content':{'visitor_record':{'top5_pages':[{'number':2,'address':'http://www.eefung.com/'},{'number':2,'address':'http://www.eefung.com/shiyong'},{'number':1,'address':'http://www.eefung.com/?source=shiyong'}],'first_visit_site':'http://www.eefung.com/','equipment':{'res':'1280x720','os':'Windows','browser':'Microsoft  Edge','ip':'60.8.29.22','ip_address':'河北省 张家口市 '},'source':'广告活动: baiduads-search - 鹰眼速读网','total_time':211000},'immutable_follow_records':[{'follow_time':1565752455893,'name':'赵慧杰','content':'申请试用'},{'follow_time':1565755743844,'name':'zhangxiaoling@eefung.com','content':'原因:通过\\'广告活动: baiduads-search - 鹰眼速读网\\'提交表单申请'}],'city':'张家口市','source_time':1565755805130,'contactName':'赵慧杰','phones':['15132379266'],'reporter':{'name':'seal_lead_collector','id':'2b0db33d-11ab-4fad-971f-2983725e9481','realm_id':'36v8tudu9Z','client_id':'36duh3ok3i34gcrkksb4joROk7bD5gFatv0DHfg3LRt'},'availability':'0','clue_source':'广告活动: baiduads-search - 鹰眼速读网','source':'原因:通过\\'广告活动: baiduads-search - 鹰眼速读网\\'提交表单申请','source_ip':'60.8.29.22','extended_message':{'visitor_record':{'$ref':'$.record_content.visitor_record'},'immutable_follow_records':[{'$ref':'$.record_content.immutable_follow_records[0]'},{'$ref':'$.record_content.immutable_follow_records[1]'}],'reporter':{'$ref':'$.record_content.reporter'}},'access_channel':'识微互动','start_time':1565755805130,'province':'河北','name':'崇礼网信办','id':'36de417e-477b-4a13-a478-fabfdc17fe1b','company_scale':'','last_contact_time':1565755805130,'contacts':[{'def_contancts':'true','phone':['15132379266'],'name':'赵慧杰','position':''}],'status':'0'},'record_type':'lead','trigger':'created'}";
        // JSONObject  source= JSONObject.parseObject(j);
        JSONObject source = new JSONObject();
        source.put("province", "广东");
        source.put("status", "0");
        Rules rules = new Rules();
        Facts facts = new Facts();
        facts.put("lead", source);
        rules.register(rule);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }

    @Test
    public void test2() {
        JSONObject source = new JSONObject();
        source.put("province", "黑龙江省");

        String j = "{'id':'36de417e-477b-4a13-a478-fabfdc17fe1b','organization_id':'36v8tudu9Z','record_content':{'visitor_record':{'top5_pages':[{'number':2,'address':'http://www.eefung.com/'},{'number':2,'address':'http://www.eefung.com/shiyong'},{'number':1,'address':'http://www.eefung.com/?source=shiyong'}],'first_visit_site':'http://www.eefung.com/','equipment':{'res':'1280x720','os':'Windows','browser':'Microsoft  Edge','ip':'60.8.29.22','ip_address':'河北省 张家口市 '},'source':'广告活动: baiduads-search - 鹰眼速读网','total_time':211000},'immutable_follow_records':[{'follow_time':1565752455893,'name':'赵慧杰','content':'申请试用'},{'follow_time':1565755743844,'name':'zhangxiaoling@eefung.com','content':'原因:通过\\'广告活动: baiduads-search - 鹰眼速读网\\'提交表单申请'}],'city':'张家口市','source_time':1565755805130,'contactName':'赵慧杰','phones':['15132379266'],'reporter':{'name':'seal_lead_collector','id':'2b0db33d-11ab-4fad-971f-2983725e9481','realm_id':'36v8tudu9Z','client_id':'36duh3ok3i34gcrkksb4joROk7bD5gFatv0DHfg3LRt'},'availability':'0','clue_source':'广告活动: baiduads-search - 鹰眼速读网','source':'原因:通过\\'广告活动: baiduads-search - 鹰眼速读网\\'提交表单申请','source_ip':'60.8.29.22','extended_message':{'visitor_record':{'$ref':'$.record_content.visitor_record'},'immutable_follow_records':[{'$ref':'$.record_content.immutable_follow_records[0]'},{'$ref':'$.record_content.immutable_follow_records[1]'}],'reporter':{'$ref':'$.record_content.reporter'}},'access_channel':'识微互动','start_time':1565755805130,'province':'河北','name':'崇礼网信办','id':'36de417e-477b-4a13-a478-fabfdc17fe1b','company_scale':'','last_contact_time':1565755805130,'contacts':[{'def_contancts':'true','phone':['15132379266'],'name':'赵慧杰','position':''}],'status':'0'},'record_type':'lead','trigger':'created'}";
        source = JSONObject.parseObject(j);
        Rules rules = new Rules();
        Facts facts = new Facts();
        facts.put("lead", source);
        Rule rule = new MVELRule().name("test")
            .when("lead.get('province') != nil && (lead.province.contains ('广东') || lead.province.contains('黑龙江') )")
            .then("lead.user_name = '陈铮星'");
        Rule rule2 = new MVELRule().name("test2")
            .when("lead.get('province') == nil || (lead.province != '广东' && lead.province !='广东省')")
            .then("lead.user_name = '李爽'");
        Rule rule3 = new MVELRule().name("t").when(
            "lead.get('province') != nil && (lead.province.contains ('黑龙江') || lead.province.contains('吉林') || lead.province.contains('辽宁') || lead.province.contains('山东') || lead.province.contains('山西') || lead.province.contains ('河北') || lead.province.contains('河南') || lead.province.contains('江苏') || lead.province.contains('上海') || lead.province.contains('天津'))")
            .then("lead.user_name = '李爽1111111111'");
        Rule rule4 = new MVELRule().name("t").when(
            "lead.get('province') != nil && (lead.province.contains ('北京'))")
            .then("lead.user_name = '李爽1111111111'");
        Rule rule5 = new MVELRule().name("t").when(
            "lead.get('province') != nil && (lead.province.contains ('广东'))")
            .then("lead.user_name = '李爽1111111111'");
        Rule rule6 = new MVELRule().name("t").when(
            "lead.get('province') != nil && (lead.province.contains ('新疆') || lead.province.contains('西藏') || lead.province.contains('内蒙古') || lead.province.contains('宁夏') || lead.province.contains('青海') lead.province.contains ('陕西') || lead.province.contains('甘肃') || lead.province.contains('四川') || lead.province.contains('云南') || lead.province.contains('重庆'))")
            .then("lead.user_name = '李爽1111111111'");
        rules.register(rule);
        rules.register(rule2);
        rules.register(rule3);
        rules.register(rule4);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
        // Map<Rule,Boolean> ruleMap = rulesEngine.check(rules,facts);
        // List<Rule> matchRules = new ArrayList<>();
        // for (Map.Entry<Rule,Boolean> ruleBooleanEntry:ruleMap.entrySet()){
        // if (ruleBooleanEntry.getValue()){
        // matchRules.add(ruleBooleanEntry.getKey());
        // }
        // }
        //
        // Rules rules1 = new Rules();
        // rules1.register(matchRules.get(0));
        // RulesEngine rulesEngine1 = new DefaultRulesEngine();
        // rulesEngine1.fire(rules1,facts);
        log.info("------------------>{}", source);
    }

    @Test
    public void test1() {
        JSONObject source = new JSONObject();
        source.put("role", "account_manager");
        source.put("label", "extension");
        source.put("category", "产品合同");
        source.put("gross_profit", 40000);
        source.put("total_price", 40000);
        source.put("price", 40000);
        Rule rule = new MVELRule()
            .when(
                "sales_commissions_account_manager.role == 'account_manager'  && sales_commissions_account_manager.label == 'extension'")
            .then(
                "com.pqc.rule.demo.util.SalesCommissionUtilSingleton.setAccountManagerLabel(sales_commissions_account_manager);")
            .priority(2);
        Rule rule1 = new MVELRule()
            .when(
                "sales_commissions_account_manager.category =='产品合同' && sales_commissions_account_manager.total_price >= 0.5 * sales_commissions_account_manager.price   ")
            .priority(1);
        RulesContinuousGroup rulesContinuousGroup = new RulesContinuousGroup();
        rulesContinuousGroup.addRule(rule);
        rulesContinuousGroup.addRule(rule1);
        Rules rules = new Rules();
        Facts facts = new Facts();
        facts.put("sales_commissions_account_manager", source);
        rules.register(rulesContinuousGroup);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        Map<Rule, Boolean> checkResult = rulesEngine.check(rules, facts);
        boolean executed = false;
        for (Map.Entry<Rule, Boolean> ruleEntry : checkResult.entrySet()) {
            executed = ruleEntry.getValue();
            if (executed) {
                break;
            }
        }

        rulesEngine.fire(rules, facts);

    }

    @Test
    public void test5() {
        JSONObject source = new JSONObject();
       source.put("remark", "hello world");
       // source.put("billsec", 1);
        Rule rule = new MVELRule()
            .when(
                " (customer_trace.get('billsec')!=nil && customer_trace.billsec > 0) ||  customer_trace.get('remark') != nil ")
            .then(
                "System.out.println('succes')");
        Rules rules = new Rules();
        Facts facts = new Facts();
        facts.put("customer_trace", source);
        rules.register(rule);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }

    @Test
    public void test() {
        User user = new User();
        Address address = new Address();
        address.setPostCode("123");
        user.setAddress(address);
        JSONObject userJson = JSON.parseObject(JSON.toJSONString(user));
        JSONObject source = new JSONObject();
        source.put("role", "manager");
        source.put("level", 3);
        source.put("sales_team", "西部网信");
        source.put("label", "new");
        source.put("category", "产品合同");
        source.put("gross", 40000);
        source.put("gross_profit", 40000);
        source.put("total_price", 40000);
        source.put("price", 40000);
        source.put("is_first", "false");
        source.put("app_id", "8Usrp3M88v75TP90Iw1IYwNA");
        Map<String, Object> app = new HashMap<>();
        app.put("8Usrp3M88v75TP90Iw1IYwNA", 50000);
        Facts facts = new Facts();
        facts.put("sales_commissions", source);
        facts.put("sales_commissions_manager", source);
        facts.put("app", app);
        facts.put("user", userJson);
        Rules rules = new Rules();
        Rule rule = new MVELRule().description("销售经理提成").name("销售经理提成")
            .when("sales_commissions.role == 'sales_manager' && sales_commissions.label == 'new' ")
            .then(" com.pqc.rule.demo.service.AddProperty add = new com.pqc.rule.demo.service.AddProperty();"
                  + " add.add(sales_commissions);");
        Rule rule5 = new MVELRule().description("销售经理提成").name("销售经理提成")
            .when("sales_commissions.role == 'sales_manager' && sales_commissions.label == 'new' ")
            .then(" com.pqc.rule.demo.service.AddProperty.add1(sales_commissions,3,'month');");
        Rule rule1 = new MVELRule().description("销售经理提成1").name("销售经理提成1").when("source.label == 'extension'")
            .then("source.put('commissions_rate',0.04)").then("source.put('result',gross * 0.04)");
        Rule rule2 = new MVELRule().description("销售经理提成新签，产品,超过标准价5折").name("销售经理提成1")
            .when("source.role == '销售经理' && source.label == 'new' && source.gross >= app.get(source.app_id)")
            .then("source.put('commissions_rate','4%')");
        Rule rule3 = new MVELRule().description("test").name("test").when("user.address.postCode == '123'")
            .then("Sysout.out.println('123')");
        Rule rule4 = new MVELRule().description("销售经理销售提成首笔回款，新签，产品合同，高于标准报价")
            .when(
                "sales_commissions.role == 'sales_manager' && sales_commissions.is_first.contains ('测') && sales_commissions.label == 'new' && (sales_commissions.category =='产品合同' && sales_commissions.total_price >= 0.5 * sales_commissions.price ) || sales_commissions.category !='产品合同'")
            .then("sales_commissions.put('commission_rate',0.04)")
            .then("sales_commissions.test = 0.1")
            .then(
                "sales_commissions.put('result', sales_commissions.gross_profit * 0.04 >10000 ?1 :sales_commissions.gross_profit * 0.04 >10000 == 'new'?2:3 )");
        Rule rule6 = new MVELRule().description("成交团队经理测试")
            .when(
                "sales_commissions_manager.role =='manager' && sales_commissions_manager.level==3 && !sales_commissions_manager.sales_team.contains('拓展')")
            .then("");
        // rules.register(rule);
        // RulesEngine rulesEngine = new DefaultRulesEngine();
        // rulesEngine.fire(rules, facts);
        // Rules rules1 = new Rules();
        // rules1.register(rule1);
        // rulesEngine.fire(rules1, facts);
        RulesOfContinuousGroup rulesOfContinuousGroup = new RulesOfContinuousGroup("group", "连续进行规则匹配");
        // rulesOfContinuousGroup.addRule(rule);
        rulesOfContinuousGroup.addRule(rule6);
        // rulesOfContinuousGroup.addRule(rule5);
        // rulesOfContinuousGroup.addRule(rule4);
        rules.register(rulesOfContinuousGroup);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        Map<Rule, Boolean> ruleBooleanMap = rulesEngine.check(rules, facts);
        log.info("------>{}", ruleBooleanMap);
        rulesEngine.fire(rules, facts);
        log.info("------>{}", source);
    }
}
