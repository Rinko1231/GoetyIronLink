# Goety Iron Link Guide

## 中文说明

### 功能概述
当 **诡厄巫法** 的 **聚晶** 被附魔 **强效** 时，如果玩家同时装备了 **Iron's Spellbooks** 的 **Spellbook**，该聚晶会根据 ISS 提供的法术强度获得额外的 **强效等级**。  
支持 Iron's Spellbooks 的一些流派，以及可选模组（如 T.O Magic 'n Extras 的源流法术）。

### 配置文件说明
配置文件路径：`config/goety_iron_spell_power.json`  

配置文件示例：

```json
{
  "ZombieSpell": { "type": "blood", "multiplier": 0.7 },
  "IceSpikeSpell": { "type": "ice", "multiplier": 0.6 },
  "FrostNovaSpell": { "type": "ice", "multiplier": 0.6 },
  "HuntingSpell": { "type": "nature", "multiplier": 0.7 },
  "FireboltSpell": { "type": "fire", "multiplier": 0.8 }
}
```
Goety 法术类名可在官方仓库查看：com.Polarice3.Goety.common.magic.spells (https://github.com/Polarice3/Goety-2/tree/1.20/src/main/java/com/Polarice3/Goety/common/magic/spells)

type：指定该法术对应的法强类别。支持的内置类型包括：fire、ice、blood、nature、ender、evocation、wind、holy、eldritch、lightning、aqua、geo、fantasy

multiplier：收益系数，会与对应法强相乘，得到最终的 Potency 加成。

如果 type 写错或未安装对应模组（如 "aqua" 在没有 T.O Magic 'n Extras 时），该法术不会获得额外加成

## English

### Overview
When a **Magic Focus** from **Goety** is enchanted with **Potency** , and the player also equips a **Spellbook** from **Iron's Spellbooks**, the Focus will gain additional Potency levels based on the spell power provided by Iron’s Spellbooks.
Support is included for several spell schools from Iron's Spellbooks, as well as optional add-on mods (e.g., source-flow spells from T.O Magic 'n Extras).

### Configuration File
Path:`config/goety_iron_spell_power.json`  

Example configuration：

```json
{
  "ZombieSpell": { "type": "blood", "multiplier": 0.7 },
  "IceSpikeSpell": { "type": "ice", "multiplier": 0.6 },
  "FrostNovaSpell": { "type": "ice", "multiplier": 0.6 },
  "HuntingSpell": { "type": "nature", "multiplier": 0.7 },
  "FireboltSpell": { "type": "fire", "multiplier": 0.8 }
}
```
Goety spell class names: see the official Goety repository at com.Polarice3.Goety.common.magic.spells (https://github.com/Polarice3/Goety-2/tree/1.20/src/main/java/com/Polarice3/Goety/common/magic/spells)

type: specifies the corresponding spell power type. Built-in supported types include: fire, ice, blood, nature, ender, evocation, wind, holy, eldritch, lightning, aqua, geo, fantasy.

multiplier: the coefficient to multiply by the corresponding spell power, resulting in the final Potency bonus.

If the type is incorrect or the corresponding mod is not installed (e.g., "aqua" without T.O Magic 'n Extras), the spell will not receive any additional bonus.
