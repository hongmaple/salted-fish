# deflower

## 介绍
一、登陆界面
需要注册功能、登录功能和学生、管理员身份选择功能
学生注册：学生学号、姓名、年级选择（大一到大四）、电话号码、宿舍楼选择、密码、再次确认密码
管理员无需注册，数据库内存档几个帐号密码，登陆时输入

二、学生端
个人主页：进行个人信息的查看、已上架物品的审核与售出情况和购入物品的查看、留言的查看（在这里可以对留言进行回复）
闲置查询：用户可以从搜索框内输入想要的东西关键词，选择寄放或者普通闲置通道进行查询（也可以全选），系统将物品展出（最好可以寄放物品优先显示）；用户看到想要的东西后点击进入可以查看详情，并进行留言，有确定购买的物品可以选择加入购物车并进行结算
购买通道：即购物车，用户在这里进行购买，购买时直接连接支付宝/微信。
闲置上架：用户可以上架自己的闲置物品，要求填写物品信息：物品图片、物品名称、规格、新旧程度、价格、简介（除简介外为必填项）
寄放上架：点击这里弹出须知，用户点击确定后可以上传闲置物品，填写物品信息：除了闲置上架所需要的信息还有联系方式（电话号码）、寄放时间（三月或半年）、后续处理选择（进行捐赠、卖出或寄送回家）；用户可以在这里查看寄存物品审核情况，如果审核通过，用户将物品拿到现实寄放处后可以在这里选择已入库，并支付物品预期价格的2%为寄存费

三、管理员端
个人主页：进行个人信息的查看、收入查询（寄放管理的收入在这里做统计，管理员是轮班上线，上线期间寄存物品的卖出收益归此时在线的管理员）
闲置管理：对用户上传的商品进行审核，审核通过则点击通过，未通过则退回
通知发布：管理员可以在这编写通知内容，若选择发布则用户在登录进系统的时候会进行通知的弹出
寄存管理：①物品审核：进行寄存物品的审核，审核通过后系统自动给与一个编号返还给学生；②入库审核：对学生选择已入库的物品进行审核，若确实入库可以点击确认；③寄存管理：若寄存物品售出，管理员收取2%的管理费，若学生选择的寄放时间到期，管理员则点击通知键获取学生联系方式进行通知。
## 软件架构
### 后端架构

1. 技术栈

| 技术 | 说明 | 官网 |
| -------------------- | ------------------- | ---------------------------------------------- |
| spring | 类的管理工具 | http://c.biancheng.net/spring/ |
| springMvc | 显示层、控制层、数据层的操作 | https://www.jianshu.com/p/91a2d0a1e45a |
| mybatis | ORM框架 | http://www.mybatis.org/mybatis-3/zh/index.html |
| mybatisPlus | MyBatis的增强工具,在 MyBatis 的基础上只做增强不做改变,为简化开发、提高效率而生 | https://mp.baomidou.com/ |
| springBoot | 容器+MVC框架 | https://spring.io/projects/spring-boot |
| websocket | 单个TCP连接上进行全双工通信的协议 | http://www.ruanyifeng.com/blog/2017/05/websocket.html |
| pagehelper | MyBatis物理分页插件 | http://git.oschina.net/free/Mybatis_PageHelper |
| Swagger2 | 文档生成工具 | https://github.com/swagger-api/swagger-ui |
| lombok | 省略getter和setter方法 | https://www.jianshu.com/p/2543c71a8e45 |
| modelmapper | Java Object Mapping 工具 | https://github.com/modelmapper/modelmapper |
| fastjson| 将Java对象转换为JSON表示 | https://github.com/alibaba/fastjson |
| Hibernator-Validator | 验证框架 | https://blog.csdn.net/hy_coming/article/details/93978555 |

2. 数据库用的是mysql8.0，使用Navicat进行管理

3.系统环境，windows ，jdk1.8
### 前端项目
#### mall4uni
感谢[mall4uni ](https://gitee.com/gz-yami/mall4uni)
介绍： 
一个基于springboot的开源商城 uniapp端
uniapp版本，做啥都不愁。即使不跨端，uni-app也是更好的小程序开发框架、更好的App跨平台框架、更方便的H5开发框架。不管领导安排什么样的项目，你都可以快速交付，不需要转换开发思维、不需要更改开发习惯。

![输入图片说明](https://images.gitee.com/uploads/images/2021/0330/105908_f19c45bb_8893195.png "142445_a8976d9e_5094767.png")
uni-app 是一个使用 Vue.js 开发所有前端应用的框架，开发者编写一套代码，可发布到iOS、Android、Web（响应式）、以及各种小程序（微信/支付宝/百度/头条/QQ/钉钉/淘宝）、快应用等多个平台。 ![输入图片说明](https://images.gitee.com/uploads/images/2021/0330/105942_fe5888a7_8893195.png "140954_a32581f8_5094767.png")
##### 亮点功能
视频教学模块，具备实时弹幕功能
客服功能，实现了实时消息刷新，分开展示，界面美化。
##### uni-app相关截图
![首页](https://images.gitee.com/uploads/images/2021/0401/112346_5c335774_2099985.jpeg "屏幕截图 2021-04-01 100453.jpg")
![分类](https://images.gitee.com/uploads/images/2021/0401/112408_6eac4168_2099985.jpeg "屏幕截图 2021-04-01 100545.jpg")
![花语解说](https://images.gitee.com/uploads/images/2021/0401/112430_70c4a95e_2099985.jpeg "屏幕截图 2021-04-01 100604.jpg")
![视频教学播放](https://images.gitee.com/uploads/images/2021/0401/112505_b03d4ee5_2099985.jpeg "屏幕截图 2021-04-01 100649.jpg")
![购物车](https://images.gitee.com/uploads/images/2021/0401/112534_5c74d69f_2099985.jpeg "屏幕截图 2021-04-01 100710.jpg")
![客服聊天](https://images.gitee.com/uploads/images/2021/0401/112600_7ff93bc3_2099985.jpeg "屏幕截图 2021-04-01 101506.jpg")
##### 开发环境启动
请使用 uniapp官方推荐的开发工具 HBuilderX 导入项目进行开发

编辑 utils/config.js 里的接口域名配置

点击工具栏的运行 -> 运行到内置浏览器 即可开发调试~
#### vue-admin-master
#### 基于vue3.0实现后台管理模板

> 感谢[vue-admin](https://github.com/MrZHLF/vue-admin)

> 使用vue最新脚手架搭建环境，编写界面，使用axios请求接口，渲染界面,实现页面登录注册，数据的增删改查,数据部分存储到easy-mock数据中。

##### 项目结构
```
  |——— client  #vue项目入口文件
	|——— config  #秘钥配置文件
	|——— node_modules #一些常用安装的依赖
	|——— models #接口模型
	|——— router       #接口文档
	|——— package.json #项目配置文件
	|___ README.md    #项目的说明文档，markdown 格式
```
##### 相关技术
	1. vuejs2.0：一套构建用户界面的渐进式框架，易用、灵活、高效。
	2. vue-router：官方的路由组件，配合vue.js创建单页应用（SPA）非常简单。
	3. axios: 基于 Promise 的 HTTP 请求客户端，可同时在浏览器和 node.js 中使用。
	
##### 功能介绍
	- Element-ui
	- 请求拦截和响应拦截
	- 富文本编辑器
	- Markdown编辑器
	- Echarts
	- tab数据切换
	- token本地存储
	- 表单
	- 高德地图引入
	- 登录注册
	- 路由守卫
	- vuex存储
	- 数据分页和查询
	- Excel表格导出
	- 递归组件
	- 主题更换
	- 404
##### 部分界面展示
![输入图片说明](https://images.gitee.com/uploads/images/2021/0330/115909_984381e2_2099985.jpeg "屏幕截图 2021-03-30 114521.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0330/115927_bacb0736_2099985.jpeg "屏幕截图 2021-03-30 114539.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0330/115955_f6e0366f_2099985.jpeg "屏幕截图 2021-03-30 114558.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0330/120014_ea395437_2099985.jpeg "屏幕截图 2021-03-30 114617.jpg")
##### 项目编译和运行
  + 可以直接在git上下载项目源码。把github下载到本地，
		git clone [](https://github.com/MrZHLF/vue-admin.git)
	+ 进入node-api-element当前页面初始化
		cnpm install
	+ 进入client项目目录中
		cnpm install
  + 所有依赖安装成功后执行启动命令在当前目录启动,不要在client进行启动，已经配置好前后端联调
	  npm run dev 
	  如果显示一下内容说明安装成功
	  I  Your application is running here: http://localhost:8080
#### 安装教程

1.  安装mysql，导入deflower\sql\deflower.sql创建deflower数据库
2.  打开iade导入deflower项目工程，使用maven下载依赖
3.  配置项目：
在src/resources/application.yml中

配置数据库连接
```
  datasource:
    url: jdbc:mysql://localhost:3306/deflower? 
    useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```
配置文件上传

```
ruoyi:
  # 名称
  name: deflower
  # 版本
  version: 1.0.0
  # 版权年份
  copyrightYear: 2021
  # 文件路径 示例（ Windows配置D:/ruoyi/uploadPath，Linux配置 /home/ruoyi/uploadPath）
  profile: E:/GitWarehouse/deflower/uploadPath
```
到此就可以使用了。
#### 另外我试着打包了安卓app，感觉挺好玩的，感兴趣的童鞋可以试试。
1. 用HubilderX 配置应用图标等信息

![用HubilderX 配置应用图标等信息](https://images.gitee.com/uploads/images/2021/0401/111123_3786d728_2099985.jpeg "屏幕截图 2021-04-01 111054.jpg")

2.让手机跟电脑连接在一个局域网下，并配置电脑的网络ip地址

![配置电脑的网络ip地址](https://images.gitee.com/uploads/images/2021/0401/111345_9a8a90b4_2099985.jpeg "屏幕截图 2021-04-01 111324.jpg")

3.点击左上方发行，选择原生app云打包，并做如下设置

![原生app云打包](https://images.gitee.com/uploads/images/2021/0401/111647_9a06ce49_2099985.jpeg "屏幕截图 2021-04-01 111607.jpg")

4.安装即可运行。

