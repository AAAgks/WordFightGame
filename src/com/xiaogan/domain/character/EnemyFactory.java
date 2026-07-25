package com.xiaogan.domain.character;

import java.util.List;
import java.util.Random;

public class EnemyFactory {
    private static final Random random = new Random();

    private static final List<EnemyTemplate> TEMPLATES = List.of(
            new EnemyTemplate("初级战士",    100, 18, 12, "猛击",     1.5, 1, false),
            new EnemyTemplate("敏捷刺客",     70, 25,  5, "快速攻击", 0.9, 2, false),
            new EnemyTemplate("重装坦克",    150,  8, 22, "防御姿态", 0,   0, true),
            new EnemyTemplate("神秘法师",     80, 30,  6, "火球术",   1.8, 1, false),
            new EnemyTemplate("暗黑领主",    200, 35, 20, "暗影爆裂", 2.0, 1, false),
            new EnemyTemplate("狂战士",      110, 28,  8, "狂暴连击", 0.7, 3, false),
            new EnemyTemplate("石像鬼",      140, 20, 28, "石化打击", 1.5, 1, false),
            new EnemyTemplate("冰霜女巫",     95, 32,  7, "冰霜风暴", 0.9, 2, false)
    );

    public static EnemyCharacter randomEnemy() {
        EnemyTemplate t = TEMPLATES.get(random.nextInt(TEMPLATES.size()));
        return t.create();
    }

    public static List<EnemyTemplate> getAll() {
        return TEMPLATES;
    }
}