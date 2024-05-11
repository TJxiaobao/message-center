# 工程简介

一个基于 sms4j 的消息中台

# 延伸阅读

    开发中

系统架构图：
![](./doc/images/system_architecture.png)

1. 配置对外暴露的API(进行管理)
2. 新增监控模块,这个在拉取任务后进行修改
3. job模块需要添加同步或src加异步(添加定时任务)的单次任务执行API