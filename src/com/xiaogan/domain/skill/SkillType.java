package com.xiaogan.domain.skill;

public enum SkillType {
    ATTACK("攻击"),
    DEFENSE("防御减伤"),
    HEAL_HP("回血"),
    HEAL_MP("回蓝"),
    BUFF("增益buff");

    private final String desc;

    SkillType(String desc) {
        this.desc = desc;
    }

    // 获取中文说明
    public String getDesc() {
        return desc;
    }
}
