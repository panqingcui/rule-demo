package com.pqc.rule.demo.util;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.support.CompositeRule;

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
public class RulesOfContinuousGroup extends CompositeRule {
    public RulesOfContinuousGroup() {
    }

    public RulesOfContinuousGroup(String name) {
        super(name);
    }

    public RulesOfContinuousGroup(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean evaluate(Facts facts) {
        if (!rules.isEmpty()) {
            int size = 0;
            for (Rule rule : rules) {
                size++;
                if (!rule.evaluate(facts)) {
                    return false;
                }
                if (rule.evaluate(facts)) {
                    if (size < rules.size()) {
                        log.info(" execute facts rule name {}|descr {}", rule.getName(), rule.getDescription());
                        try {
                            rule.execute(facts);
                        } catch (Exception e) {
                            log.error("rule execute error", e);
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Rule rule : rules) {
            log.info(" execute  rule facts {}", facts);
            rule.execute(facts);
        }
    }
}
