package com.pqc.rule.demo.util;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * <p>向规则引擎发送的内容。</p>
 * 创建日期 2019/2/15
 *
 * @author panqingcui
 * @since 1.0.0
 */
@Data
public class SalesCommissionContent {
    private String id;
    // 合同的id
    @JSONField(name = "contract_id")
    private String contractId;
    // 回款的毛利
    @JSONField(name = "gross_profit")
    private double grossProfit;
    @JSONField(name = "total_price")
    private double totalPrice;
    // 产品名称或者项目名称
    @JSONField(name = "app_name")
    private String appName;
    @JSONField(name = "app_id")
    private String appId;
    @JSONField(name = "commission_rate")
    private Double commissionRate;
    // 回款时间
    @JSONField(name = "date")
    private Long date;
    // 合同的签订时间
    @JSONField(name = "contract_date")
    private Long contractDate;
    @JSONField(name = "sales_team")
    private String salesTeam;
    @JSONField(name = "sales_team_id")
    private String salesTeamId;
    @JSONField(name = "member_id")
    private String memberId;
    @JSONField(name = "member_name")
    private String memberName;
    // 角色
    @JSONField(name = "role")
    private String role;
    // 种类
    @JSONField(name = "category")
    private String category;
    // 销售代表
    @JSONField(name = "sales_rep")
    private String salesRep;
    @JSONField(name = "sales_rep_id")
    private String salesRepId;
    @JSONField(name = "sales_rep_team")
    private String salesRepTeam;
    @JSONField(name = "sales_rep_team_id")
    private String salesRepTeamId;
    // 新签，续签 extension new
    private String label;
    // 是否为首笔回款，yes是，no否
    @JSONField(name = "is_first")
    private String isFirst;
    // private Contract histroy;
    // 已发放
    private Double granted;
    // 记录异常信息，存在此值时，不进行提成计算
    private String remark;
    // 已回款毛利
    @JSONField(name = "received_gross_profit")
    private double receivedGrossProfit;
    // 上次合同毛利
    @JSONField(name = "previous_gross_profit")
    private double previousGrossProfit;
    // 本次回款毛利，未根据 产品拆分
    @JSONField(name = "repayment_gross_profit")
    private double repaymentGrossProfit;

    public SalesCommissionContent() {
    }
}
