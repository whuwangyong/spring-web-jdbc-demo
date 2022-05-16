该示例演示了基于 spring web 和 jdbc 的rest api。

## 应用分层（自顶向下）
- controller 控制层，暴露rest api，接收前端请求
- service 服务层，具体的业务逻辑，处理控制层下发的请求
- repository 持久层，操作数据库表的方法
- entity 数据表对象，与数据库里面的表结构一致。同时出现在控制层、服务层、持久层。这点存疑，不知道这样设计是否合理

其他目录：
- exception 自定义异常
- advice 全局异常处理，同一封装异常响应结果
- dto 定义数据传输对象，与前端交互。dto只出现在控制层
    - response 统一的响应代码和格式
- util 辅助工具类

## 关键点
1. 前后端对错误码的约定，见`ResponseCode`
2. 异常信息层层上报。repository -> service -> controller，最终展示给前端用户。本项目通过`@RestControllerAdvice`实现
3. 分页查询
4. 如何实现部分更新：比如一张表10个字段，前端传了一个对象过来，只改了2个字段，其余字段为空。更新时，不能将空的值写入数据库。  
   这里要么做一个空值检查；要么与前端约定，不传空值到后端，没修改的字段也一起传过来，浪费一点带宽。

## 如何运行
1. 准备一个mysql。建表语句在resources目录下
2. 运行test目录下的测试用例，会生成一些测试数据
3. 启动项目（MainApp），访问 [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) 查看和测试REST API