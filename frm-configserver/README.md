### 配置中心使用说明

- 1，http://192.168.1.244/jiangy/springCloudConfig 项目中提交配置文件
- 2，客户端中引入包
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
</dependency>
- 3，客户端配置
spring.application.name=config-server  配置文件名称
server.port=8888   配置中心端口
spring.cloud.config.label=master  配置文件分支
spring.cloud.config.server.git.uri=http://192.168.1.244/jiangy/springCloudConfig.git  配置项目地址
spring.cloud.config.server.git.search-paths=springCloudConfig    服务器名称

spring.cloud.config.server.git.username=jiangy@chinaoly.com  git账号
spring.cloud.config.server.git.password=jiangyi900823   git密码

注意：
客户端配置文件必须是bootstrap.properties或bootstrap.yml文件，因为bootstrap配置优先级最高






