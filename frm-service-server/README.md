### 服务中心使用说明

- 1，客户端中引入包
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
- 2，在启动类上加@EnableDiscoveryClient注解，
  引入的类：import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

启动之后会在服务中心发现这个微服务（http://192.168.0.71:8761/）

注意：
@EnableDiscoveryClient比@EnableEurekaClient能注册更多的接口，一般使用@EnableDiscoveryClient比





