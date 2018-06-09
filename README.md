# springCloudDemo
spring cloud框架
### 基础框架1.0.1
#####设计思路：大中台，微应用，框架轻量级。
#####各个基础包的说明
- frm-common 公共方法（统一工具注入）
- frm-core 核心包（公共三层的base）,一些基本过滤器。token解析器，定时器，redis
- frm-security 安全模块（用户，资源，角色，部门），
- frm-log 日志处理，提供统一日志方法
- frm-base *规划当中*  系统设置(统一门户，系统设置，公共登陆，在线用户，redis操作)
- frm-service *规划当中*   统一接口（对内，对外接口授权，调用）
- frm-login 统一登录，认证，登录完成之后存redis，数据库
- frm-process 结合公司角色系统，基于activity的工作流统一封装，提供方法和流程配置公共方法
- frm-configserver  配置中心   各自子系统配置文件统一配置
- frm-operation 运维模块
- frm-service-server  服务发现，服务注册。目前使用spring cloud eurka做了服务发现。

包依赖关系

- frm-common（不连redis，数据库）
                                 
- frm-core(连接redis和基础数据库)
                                   
- frm-security （连接数据库）
                                   
- frm-log（连接数据库）

开发依赖这四个包



框架介绍
- 1，结构框架
框架使用spring boot+mybatis结构，使用了annotation注入，这样减少了许多的配置文件，代码更简洁
- 2，安全框架
使用jjwt进行统一认证。使用shiro进行安全管理,会话管理。
- 3，数据访问层框架
使用MyBatis封装dao层，使用阿里druid连接池，更轻便，好用
- 4，缓存框架
使用redis作为缓存层，redis作为独立缓存服务器，实现双机备份，并可配置把缓存数据储存到硬盘中，提供统一公用的缓存读写方法调用。
- 5，项目构建
使用Maven来构建项目，这样可以统一管理第三方jar包和模块间依赖关系，项目中各模块文件结构清晰，java文件，资源文件，测试文件都分的很清楚。在新框架中可以配合Jenkins做版本持续集成。
- 6，服务框架（规划）
准备自己封装controller，方案准备中，通过zuul网关进行拦截，接口统一分发，使用eurke做服务发现。使用cofigserver做配置中心，动态配置。
- 7，快速开发模板（技术选型）
结合框架生成快速开发模板。 



框架运行环境
- 1，jdk ： 1.8
- 2，编译器：推荐IDEA
- 3，代码管理：git
- 4，缓存：redis
- 5，数据连接

架构部署配置
- 1，docker环境，可以是虚拟机，可以是物理机（用来部署应用，前后台分开部署）
  后台包大概需要6个镜像包（三个后台300M/一个，三个前台200m/一个），根据系统的多少调整资源
- 2，redis缓存机器，尽量实现双机备份
- 3，数据库机器，安装oracle或者mysql
- 4，cdn静态资源库，用来存放（视频，图片等大资源）



技术选型：
- json使用jackson包，提供的方法更全，项目中可以自己使用fastjson，可以快一点点，但是可能出现很多扩展性上的问题
- 日志使用sef4j-api集合实现类logback今天统一配置，使用sef4j接口记日志，能使用占位符配置日志，提高效率


框架规范约定(可调整)
约定优于配置(convention over configuration)，此框架约定了很多编程规范，下面一一列举：

- service类，需要在叫名`service`的包下，并以`Service`结尾，如`CmsArticleServiceImpl`

- controller类，需要在以`controller`结尾的包下，类名以Controller结尾，如`CmsArticleController.java`，并继承`BaseController`

- spring task类，需要在叫名`task`的包下，并以`Task`结尾，如`TestTask.java`

<--mybatis start--->
- mapper.xml，需要在名叫`mapper`的包下，并以`Mapper.xml`结尾，如`CmsArticleMapper.xml`
- mapper接口，需要在名叫`mapper`的包下，并以`Mapper`结尾，如`CmsArticleMapper.java`
- model实体类，需要在名叫`model`的包下，命名规则为数据表转驼峰规则，如`CmsArticle.java`
<--mybatis end--->


<!--springdata start--->
- repository接口，需要在名叫`repository`的包下，并以`repository`结尾，如`CmsArticleRepository.java`

- entity实体类，需要在名叫`entity`的包下，命名规则为数据表转驼峰规则，如`CmsEntity.java`
<!--springdata end--->
- spring配置文件，命名规则为`applicationContext-*.xml`

- 类名：首字母大写驼峰规则；方法名：首字母小写驼峰规则；常量：全大写；变量：首字母小写驼峰规则，尽量非缩写

- springmvc配置加到对应模块的`springMVC-servlet.xml`文件里

- 配置文件放到`src/main/resources`目录下

- 静态资源文件放到`src/main/webapp/resources`目录下

- jsp文件，需要在`/WEB-INF/jsp`目录下

- `RequestMapping`和返回物理试图路径的url尽量写全路径，如：`@RequestMapping("/manage")`、`return "/manage/index"`

- `RequestMapping`指定method

- 模块命名为`项目`-`子项目`-`业务`，如`zheng-cms-admin`

- 数据表命名为：`子系统`_`表`，如`cms_article`

- 更多规范，参考[[阿里巴巴Java开发手册]


