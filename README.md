# 简介

## 申明

本作的初始目的是探索2D游戏中镜头与角色运动的分离, 以及其所带来的效果

因此程序的架构非常不完善, 仅仅是实现了一些简单的跑跳功能, 以及基础的体积碰撞

## 运行

通过运行src/Main.java进入主程序

绿色方块为操纵的主角, 使用wad进行左右移动与跳跃

## 修改地图

通过修改map/test.txt修改地图

### 地图存档结构

第1行: ground: 接下去的n行均为ground方块坐标

第2 ~ 1+n行: ground方块的(x, y)坐标: (注: x正方向为右, y正方向为下)

第2+n行: end: 文件的结束

目前该程序只有ground这一种方块类型, 若添加一种方块类型(例: cloud), 则格式如下:

第1行: ground: 接下去的n行均为ground方块坐标

第2 ~ 1+n行: ground方块的(x, y)坐标

第2+n行: cloud: 接下去的m行均为cloud方块坐标

第3+n ~ 2+n+m行: cloud方块的(x, y)坐标

第3+n+m行: end: 文件的结束

##程序架构

Main.java种的主程序调用MainThread.main()

MainThread.java对程序的帧率, 窗口大小, 以及绘制过程作出了规定

MainThread会调用以下几个class的init()和main()

1. Background
2. Map
3. Character
4. Camera

### Background

负责背景绘制

目前添加了纯淡蓝色背景以及雪花飘落效果

### Map

负责读取地图map/test.txt, 以及地图的绘制

目前只有一种方块ground

### Character

负责储存主角的各类信息, 以及主角的绘制

### Camera

负责储存镜头信息

镜头有两种模式: lock 和 unlock

unlock状态下, Camera会实现对Character的滞后跟随, 以及左右移动时的视野扩大

lock状态下, Camera会逐渐对主角视角完成锁定

目前, Camera默认使用unlock状态更新镜头信息, 在主角下落至即将脱离屏幕范围时更改为lock状态, 在主角触碰地面停止下落后再次更改为unlock状态