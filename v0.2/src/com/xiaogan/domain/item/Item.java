package com.xiaogan.domain.item;
import java.util.Map;

public class Item {
    private String name;
    private String describe;
    private Map<ItemEffect, Integer> effectMap;

    public Item(String name, String describe, Map<ItemEffect, Integer> effectMap) {
        this.name = name;
        this.describe = describe;
        this.effectMap = effectMap;
    }

    public int getEffectValue(ItemEffect effect) {
        return effectMap.getOrDefault(effect, 0);
    }

    public String getName() { return name; }
    public String getDescribe() { return describe; }
    public Map<ItemEffect, Integer> getEffectMap() { return effectMap; }
}
