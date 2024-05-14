# 


## 项目命名
* https://www.51test.net/show/5854275.html


## 项目相关

* http相关常量使用org.springframework.http.HttpHeaders中提供的
* 响应token可以放置在响应的响应头中

### mysql日期时间
* https://juejin.cn/post/7290382189992165415
* 性能: bigint > datetime > timestamp


## 提交格式

* 提交类型 
  * feat: 新功能
  * fix: 修复问题(bug)
  * docs: 修改文档
  * style: 修改代码格式，不影响代码逻辑
  * refactor: 重构代码，理论上不影响现有功能
  * perf: 提升性能
  * test: 增加修改测试用例
  * chore: 修改工具相关（包括但不限于文档、代码生成等）添加依赖 修改格式等
  * deps: 升级依赖
  * revert: 回滚版本
  * build: 构建系统或者包依赖更新
  * other: 其他不重要修改
* 范围Scope
    * 用于说明本次修改涉及的范围-模块
* 主题 Subject
* 详细说明 Body

* 例如:
```
<type>(<scope>): <subject>
<空行>
<body>
<空行>
<footer>
**************************************
feat(模块): 新增功能
-- 空行
详细说明
```