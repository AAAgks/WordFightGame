package com.xiaogan.domain.tips;

import java.util.ArrayList;
import java.util.Random;

public enum BattleTip {
    HP_LACK(createHpLackTips()),
    MP_LACK(createMpLackTips()),
    DEATH(createDeathTips()),
    ENEMY_DEATH(createEnemyDeathTips());

    private static final Random random = new Random();
    private ArrayList<String> tipList;

    BattleTip(ArrayList<String> tipList) {
        this.tipList = tipList;
    }

    private static ArrayList<String> createHpLackTips() {
        ArrayList<String> tips = new ArrayList<>();
        tips.add("【%s】浑身布满伤痕，生命气息摇摇欲坠！%n");
        tips.add("【%s】身受重创，生命气息摇摇欲坠！%n");
        tips.add("危险！%s 生命值濒临归零，再受一击就会倒下！%n");
        tips.add("【%s】浑身伤痕，生命已经快要支撑不住！%n");
        tips.add("【%s】伤口不断渗血，身体早已支撑不住。%n");
        tips.add("剧痛席卷全身，【%s】脚步虚浮，濒临倒地。%n");
        return tips;
    }
    private static ArrayList<String> createMpLackTips() {
        ArrayList<String> tips = new ArrayList<>();
        tips.add("【%s】抬手准备释放【%s】，体内魔力却一空，法术直接消散！%n");
        tips.add("【%s】奋力催动【%s】，可魔力空空如也，法术根本无法成型！%n");
        tips.add("【%s】刚要释放【%s】，体内魔力瞬间枯竭，施法被迫中断！%n");
        tips.add("糟糕【%s】的魔力见底，连最低要求都达不到，【%s】放不出来！%n");
        tips.add("【%s】企图释放【%s】，但魔力匮乏，法术直接消散在空气中！%n");
        tips.add("【%s】默念【%s】的咒语，丹田毫无魔力回应，技能释放失败！%n");
        return tips;
    }

    private static ArrayList<String> createDeathTips() {
        ArrayList<String> tips = new ArrayList<>();
        tips.add("【%s】周身生机散尽，轰然倒地，彻底落败！%n");
        tips.add("【%s】再也无力支撑身躯，倒在了战场之上！%n");
        tips.add("【%s】的生命完全耗尽，本次战斗宣告结束！%n");
        tips.add("伤势无法挽回，【%s】无力地倒下，战斗宣告失败。%n");
        tips.add("【%s】抵挡不住攻势，彻底战败离场！%n");
        tips.add("【%s】的生命值归零，本次挑战就此落幕。%n");
        tips.add("【%s】战败了，本次战斗结束。%n");
        tips.add("很遗憾【%s】没能撑到最后，被敌人击败。%n");
        tips.add("【%s】生机彻底散尽，轰然倒地，战斗彻底落幕！%n");
        tips.add("【%s】扛不住致命一击，重重倒在了战场之上。%n");
        tips.add("生命力完全耗尽【%s】无力再支撑身躯。%n");
        return tips;
    }

    private static ArrayList<String> createEnemyDeathTips() {
        ArrayList<String> tips = new ArrayList<>();
        tips.add("【%s】 一击击溃【%s】，对方浑身脱力重重倒地！%n");
        tips.add("【%s】 的攻势彻底耗尽【%s】全部生命力，对手战败！%n");
        tips.add("【%s】抵挡不住【%s】的进攻，生机散尽倒在地上。%n");
        tips.add("【%s】 挥出最后一击，【%s】无力支撑，轰然倒落战场！%n");
        tips.add("几番激烈缠斗，【%s】终究不敌【%s】，彻底失去战斗能力！%n");
        tips.add("【%s】 的招式狠狠命中要害，【%s】浑身一软瘫倒在地！%n");
        tips.add("【%s】身上伤痕累累，扛不住%s的猛攻，缓缓倒下！%n");
        tips.add("【%s】 终结了【%s】所有抵抗，对手再也无法站起身！%n");
        tips.add("致命一击落在身上，【%s】在【%s】面前彻底落败！%n");
        tips.add("【%s】耗尽全部体力，最终倒在了【%s】的脚下！%n");
        return tips;
    }
    public String getRandomTip() {
        int index = random.nextInt(tipList.size());
        return tipList.get(index);
    }

}
