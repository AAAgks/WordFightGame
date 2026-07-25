# WordFight

> Java 控制台回合制文字 RPG

---

## 版本

| 版本 | 目录 | 说明 |
|------|------|------|
| **[v0.2](v0.2/)** | `v0.2/` | 最新版 — 系统化重构，6技能、8敌人、4道具 |
| [v0.1](v0.1/) | `v0.1/` | 初版 — 基础战斗循环 |

---

## 快速开始

```bash
# v0.2（推荐）
cd v0.2
javac -d out src/com/xiaogan/App.java
java -cp out com.xiaogan.App

# v0.1
cd v0.1
javac -d out src/com/xiaogan/App.java
java -cp out com.xiaogan.App
```

## v0.1 → v0.2 主要变化

- 包结构 `bean` → `domain` 标准化
- 技能 `switch(index)` → `switch(SkillType)` 枚举驱动
- 敌人硬编码 → `EnemyTemplate` + `EnemyFactory`
- 伤害公式 `atk-def` → `atk×100/(100+def)`
- 新增 3 技能、5 敌人、3 道具
- 详见 [v0.2/README.md](v0.2/README.md)
