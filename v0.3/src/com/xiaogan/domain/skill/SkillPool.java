package com.xiaogan.domain.skill;


import java.util.List;

public class SkillPool {

    public static final Skill POWER_STRIKE = new Skill(
            "强力一击",
            "对敌人造成1.8倍攻击力的伤害",
            10,
            1.8,
            SkillType.ATTACK,
            1


    );

    public static final Skill DOUBLE_SLASH = new Skill(
            "二连斩",
            "快速攻击两次，每次0.8倍伤害",
            8,
            0.8,
            SkillType.ATTACK,
            2


    );

    public static final Skill DRAIN_LIFE = new Skill(
            "生命汲取",
            "恢复最大生命值的30%",
            10,
            0.3,
            SkillType.HEAL_HP,
            1

    );

    // 冥想：回蓝类型
    public static final Skill MEDITATE = new Skill(
            "冥想",
            "恢复最大蓝量的50%",
            0,
            0.5,
            SkillType.HEAL_MP,
            1
    );

    // 战吼：BUFF类型，永久提升攻击力
    public static final Skill WAR_CRY = new Skill(
            "战吼",
            "永久提升4点攻击力",
            12,
            4,
            SkillType.BUFF,
            1
    );

    // 铁壁：DEFENSE类型，下回合伤害减半
    public static final Skill IRON_WALL = new Skill(
            "铁壁",
            "进入防御姿态，下回合受到伤害减半",
            12,
            0,
            SkillType.DEFENSE,
            1
    );

    public static List<Skill> ALL_SKILLS = List.of(
            POWER_STRIKE,
            DRAIN_LIFE,
            MEDITATE,
            DOUBLE_SLASH,
            WAR_CRY,
            IRON_WALL
    );

}
