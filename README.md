# WordFight - 文字格斗游戏

> Java 控制台回合制文字 RPG

---

## 版本

| 版本 | 分支 | 说明 |
|------|------|------|
| **v0.2** | `v0.2` | 当前版本 — 系统化重构，大幅扩展内容 |
| v0.1 | `master` | 初版 — 基础战斗循环 |

---

## v0.2 相较于 v0.1 的改动

### 🔧 架构重构

| 维度 | v0.1 | v0.2 |
|------|------|------|
| 包名 | `bean`（不规范） | `domain`（标准分层） |
| 技能驱动 | `switch(skillIndex)` 按顺序硬编码 | `switch(SkillType)` 枚举驱动，数据与行为分离 |
| 敌人创建 | 硬编码 `new EnemyCharacter(...)` | `EnemyTemplate` 模板 + `EnemyFactory` 工厂 |
| 道具效果 | 固定参数 | `ItemEffect` 枚举 + `Map<Effect, Value>` 多效果组合 |

### ⚔️ 战斗系统

- **伤害公式**：`atk - def` → `atk × 100 / (100 + def)`（百分比减伤，高攻不再完全穿透高防）
- **多段攻击**：技能支持 `hits` 参数，一次技能可打出多段伤害
- **防御姿态**：新增 DEFENSE 类型技能，使用后下回合受伤减半

### 🎯 技能（4 → 6）

| 技能 | 类型 | 消耗 | v0.2 新增/改动 |
|------|------|------|---------------|
| 强力一击 | ATTACK | 10 MP | 1.8× 倍率（不变） |
| 二连斩 | ATTACK | 8 MP | 🆕 0.8× ×2hits |
| 生命汲取 | HEAL_HP | 10 MP | 恢复30%最大HP |
| 冥想 | HEAL_MP | 0 MP | 恢复50%最大MP |
| 战吼 | BUFF | 12 MP | 🆕 永久 +4 ATK |
| 铁壁 | DEFENSE | 12 MP | 🆕 下回合受伤减半 |

### 👹 敌人（3 → 8）

| 敌人 | HP | ATK | DEF | 技能 |
|------|-----|-----|-----|------|
| 初级战士 | 100 | 18 | 12 | 猛击 1.5× |
| 敏捷刺客 | 70 | 25 | 5 | 快速攻击 0.9× ×2 |
| 重装坦克 | 150 | 8 | 22 | 防御姿态 |
| 神秘法师 | 80 | 30 | 6 | 火球术 1.8× |
| 暗黑领主 | 200 | 35 | 20 | 暗影爆裂 2.0× |
| 狂战士 | 110 | 28 | 8 | 🆕 狂暴连击 0.7× ×3 |
| 石像鬼 | 140 | 20 | 28 | 🆕 石化打击 1.5× |
| 冰霜女巫 | 95 | 32 | 7 | 🆕 冰霜风暴 0.9× ×2 |

### 🎒 道具（1 → 4）

| 道具 | v0.2 新增/改动 | 效果 |
|------|---------------|------|
| 生命药水 | — | 恢复30%最大HP |
| 狂战药剂 | — | +30 HP + 永久+8 ATK |
| 魔力药水 | 🆕 | +40 MP |
| 防御药剂 | 🆕 | +20 HP + 永久+4 DEF |

### ⚖️ 平衡性

- 战后恢复 20% HP & MP（代替 v0.1 的无恢复）
- 战前休整 15% HP 恢复
- 每 3 胜永久提升属性：HP+30 / MP+20 / ATK+5 / DEF+3
- 敌人随胜场成长：每场 +8 HP / +2 ATK / +1 DEF

---

## 运行

```bash
# 编译
javac -d out src/com/xiaogan/App.java

# 运行
java -cp out com.xiaogan.App
```

或直接用 IDE（IntelliJ IDEA）打开运行 `App.java`。

---

## 项目结构

```
src/com/xiaogan/
├── App.java                         # 入口
├── domain/
│   ├── User.java                    # 用户模型
│   ├── character/
│   │   ├── Character.java           # 角色基类
│   │   ├── HeroCharacter.java       # 玩家角色
│   │   ├── EnemyCharacter.java      # 敌人角色
│   │   ├── EnemyTemplate.java       # 敌人模板
│   │   └── EnemyFactory.java        # 敌人工厂
│   ├── skill/
│   │   ├── Skill.java               # 技能数据
│   │   ├── SkillType.java           # 技能类型枚举
│   │   └── SkillPool.java           # 技能池
│   ├── item/
│   │   ├── Item.java                # 道具数据
│   │   ├── ItemEffect.java          # 道具效果枚举
│   │   └── ItemPool.java            # 道具池
│   └── tips/
│       └── BattleTip.java           # 战斗提示语
└── ui/
    ├── Login.java                   # 登录/注册
    ├── FightingGame.java            # 战斗主逻辑
    └── Test.java                    # 开发用测试代码
```
