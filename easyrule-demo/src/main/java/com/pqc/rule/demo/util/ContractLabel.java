package com.pqc.rule.demo.util;

/**
 * <p>合同标签。</p>
 * 创建日期 2019/3/14
 *
 * @author panqingcui
 * @since 1.0.0
 */
public enum ContractLabel {
    NEW("new", "新签"),
    EXPANSION("expansion", "扩展"),
    EXTENSION_EXPANSION("extension_expansion", "续签_扩展"),
    EXTENSION("extension", "续签");
    // 描述
    private final String type;
    private final String desc;

    private ContractLabel(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
