package com.pqc.rule.demo.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.support.CompositeRule;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>规则连续执行。</p>
 * <p>
 * 创建日期 2019/2/12
 *
 * @author panqingcui
 * @since 1.0.0
 */
@Slf4j
public class RulesContinuousGroup extends CompositeRule {
    public RulesContinuousGroup() {
    }

    public RulesContinuousGroup(String name) {
        super(name);
    }

    public RulesContinuousGroup(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean evaluate(Facts facts) {
        if (!rules.isEmpty()) {
            int size = 0;
            // 排序
            List<Rule> ruleList = getRules(rules);
            for (Rule rule : ruleList) {
                size++;
                if (!rule.evaluate(facts)) {
                    return false;
                }
                if (size < ruleList.size()) {
                    log.info(" execute facts rule name {}|descr {}", rule.getName(), rule.getDescription());
                    try {
                        rule.execute(facts);
                    } catch (Exception e) {
                        log.error("rule execute error", e);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public List<Rule> getRules(Set<Rule> ruleSet) {
        List<Rule> ruleList = new ArrayList(ruleSet);
        ruleList.sort(Comparator.comparing(Rule::getPriority).reversed());
        return ruleList;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Rule rule : rules) {
            log.info(" execute  rule facts {}", facts);
            rule.execute(facts);
        }
    }
}
