package com.pqc.rule.demo.util;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>销售提成中计算。</p>
 * 创建日期 2019/2/21
 *
 * @author panqingcui
 * @since 1.0.0
 */
@Slf4j
public class SalesCommissionUtilSingleton {
    /**
     * 客户经理，扩展
     */
    public static void setAccountManagerLabel(JSONObject jsonObject) {
        log.info("客户经理扩展");
        SalesCommissionContent salesCommissionContent = JSONObject.parseObject(jsonObject.toJSONString(),
                                                                               SalesCommissionContent.class);
        if (salesCommissionContent.getReceivedGrossProfit() > salesCommissionContent.getPreviousGrossProfit()) {
            // 扩展，
            // 已回款-上次回款=扩展
            // 新签 ，续签
            // 扩展毛利
            double expansionGrossProfit = salesCommissionContent.getReceivedGrossProfit()
                                          - salesCommissionContent.getPreviousGrossProfit();
            if (salesCommissionContent.getRepaymentGrossProfit() > expansionGrossProfit) {
                // 根据比例拆分
                double factExpansionGrossProfit = 0;
                jsonObject.put("expansion_gross_profit", factExpansionGrossProfit);
                // 续签
                jsonObject.put("gross_profit",
                               salesCommissionContent.getGrossProfit() - factExpansionGrossProfit);
                jsonObject.put("label", ContractLabel.EXTENSION_EXPANSION.getType());
            } else {
                jsonObject.put("expansion_gross_profit", salesCommissionContent.getGrossProfit());
                jsonObject.put("label", ContractLabel.EXPANSION.getType());
                jsonObject.put("gross_profit", 0.0);
            }
        } else {
            // 按照续约计算
            jsonObject.put("gross_profit", salesCommissionContent.getGrossProfit());
            jsonObject.put("expansion_gross_profit", 0.0);
            jsonObject.put("label", ContractLabel.EXTENSION.getType());
        }
        jsonObject.put("label", ContractLabel.EXPANSION.getType());
        log.info("客户经理------------->{}", jsonObject.toJSONString());
    }
}
