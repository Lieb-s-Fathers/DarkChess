# DarkChess

This is the project for SUSTech_CS107A.

------

## AI策略（玩天天象棋有感）

1. 一定要保住将，方法是千万不要主动翻将周围的棋子
2. 尽量在前期吃子，后期很难吃，并且输出主要靠炮，所以要保住炮。用卒吃将存疑。
3. 没有吃的的子了再去翻棋，尽量翻周围己方棋子多敌方棋子少的，
   - 可以考虑按棋子等级己方+分敌方-分，有未翻开适当加分
4. 如果有多个子可以吃，优先吃可以吃的等级最高的，但要考虑保住自己。但小子换大子是被允许的。

------

### Maybe 可以作为参考 但是是python

[xinjiyier/Dark_chess: 象棋小游戏，暗棋 (github.com)](https://github.com/xinjiyier/Dark_chess)

------

## Important

### commit规范

- commit信息应遵循Angular规范，建议使用Intellij IDEA的插件Git Commit Temperate
- 每个commit的更改内容应该尽可能保持小范围且集中
- 尽可能确保commit时源码能够正常编译，正常运行，通过测试

------

## Develop Log

### 11-14 提供demo

1. 建立Github Repository并确立commit规范

2. 尝试阅读demo代码

3. 学习uml图，尝试对demo进行uml图构建，帮助理解项目架构

   ![image-20221115165939210](./README.assets/image-20221115165939210.png)

   

