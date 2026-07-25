package com.xiaogan.domain.item;

import java.util.List;
import java.util.Map;

public class ItemPool {
    public static final Item HP_POTION = new Item(
            "生命药水",
            "恢复30%最大生命值",
            Map.of(ItemEffect.HEAL_HP_PCT, 30)   // 30表示30%
    );

    public static final Item RAGE_POTION = new Item(
            "狂战药剂",
            "恢复30生命，永久提升8攻击力",
            Map.of(ItemEffect.ADD_HP, 30, ItemEffect.ADD_ATK, 8)
    );

    public static final Item MP_POTION = new Item(
            "魔力药水",
            "恢复40点蓝量",
            Map.of(ItemEffect.ADD_MP, 40)
    );

    public static final Item DEF_POTION = new Item(
            "防御药剂",
            "恢复20生命，永久提升4防御力",
            Map.of(ItemEffect.ADD_HP, 20, ItemEffect.ADD_DEF, 4)
    );

    public static List<Item> ALL_ITEMS = List.of(
            HP_POTION,
            RAGE_POTION,
            MP_POTION,
            DEF_POTION
    );

}
