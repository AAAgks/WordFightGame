# WordFight — 文字格斗游戏

> Java 回合制文字 RPG · 从控制台到 GUI 的完整演进

[![version](https://img.shields.io/badge/version-v0.3-blue)](v0.3/)
[![language](https://img.shields.io/badge/language-Java-orange)]()

---

## 简介

WordFight 是一款 Java 回合制文字格斗游戏。创建角色、分配属性、学习技能、收集道具，挑战 8 种各具特色的敌人，在战斗中不断成长。

---

## 版本

| 版本 | 目录 | 界面 | 亮点 |
|------|------|------|------|
| **[v0.3](v0.3/)** | `v0.3/` | 🖥️ Swing GUI | 完整图形界面 · 可视化角色创建 · 按钮式技能/道具选择 |
| [v0.2](v0.2/) | `v0.2/` | ⌨️ 控制台 | 系统化重构 · 6技能/8敌人/4道具 · 百分比减伤公式 |
| v0.1 | `master` | ⌨️ 控制台 | 初版 · 基础战斗循环 |

> 点击版本号进入对应目录查看详细 README。

---

## 快速开始

```bash
# GUI 版（推荐）
cd v0.3
javac -encoding UTF-8 -d out -sourcepath src src/com/xiaogan/App.java
java -cp out com.xiaogan.App

# 控制台版
cd v0.2
javac -encoding UTF-8 -d out -sourcepath src src/com/xiaogan/App.java
java -cp out com.xiaogan.App
```

---

## 核心系统

- ⚔️ **百分比减伤**：`伤害 = ATK × 100 / (100 + DEF)`，防御永远不归零
- 🛡️ **防御姿态**：下回合受伤减半，攻防博弈
- 🎯 **6 种技能**：ATTACK / HEAL / BUFF / DEFENSE 四大类
- 👹 **8 种敌人**：从初级战士到暗黑领主，各具技能
- 🎒 **道具系统**：20% 概率掉落，战斗中使用
- 📈 **成长系统**：每 3 胜永久提升属性，敌人同步变强

---

## 许可证

MIT
