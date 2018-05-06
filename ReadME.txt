这是一个SSM框架的空的模板 里面是最基本的配置以及基本的测试类 使用idea打开项目
idea下载地址 https://www.jetbrains.com/idea/download/#section=windows
file -> open 打开项目

此框架屏蔽了复杂的jdbc操作 数据的传输以及前端和后台的交互都非常简单 远比JavaEE中的Servlet和PHP要简单

目录结构

config 配置文件
    db.properties 数据库相关配置
    其他尽量不要改
lib 项目依赖
src 项目java代码
    controller 控制层 负责url的转发
    service 业务层 controller中会调用service中的方法 实现业务逻辑
    mapper 持久层 service中会调用mapper中的方法 操作数据库
    entity 实体类 javabean对象 封装一类对象
web 项目html代码
    static 静态资源 js css font img等
    web-inf 项目保护页面 不可直接访问
        views 项目jsp代码
        web.xml 项目总配置
    index.jsp 项目入口文件


