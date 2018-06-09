package com.chinaoly.frm.core.configurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * defaultDb 持久化配置，业务db参考这个配置。Mybatis & Mapper & PageHelper 配置
 * @author jiangyi
 */
@Configuration
public class DefaultDbConfigurer {

    @Bean(name = "defaultDb")
    @ConfigurationProperties(prefix = "spring.datasource.defaultDb") // application.properteis中对应属性的前缀
    public DruidDataSource DefaultDbDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBeanDefaultDb( @Qualifier("defaultDb")DataSource defaultDb) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(defaultDb);
        //配置分页插件，详情请查阅官方文档
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("pageSizeZero", "true");//分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("reasonable", "true");//页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("supportMethodsArguments", "true");//支持通过 Mapper 接口参数来传递分页参数
        pageHelper.setProperties(properties);
        //添加插件
        factory.setPlugins(new Interceptor[]{pageHelper});
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:com/chinaoly/**/baseDao/mapper/*.xml"));//扫描xml映射文件路径
        return factory.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager DefaultDbTransactionManager(@Qualifier("defaultDb")DataSource defaultDb){
        return new DataSourceTransactionManager(defaultDb);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurerDefaultDb() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBeanDefaultDb");
        mapperScannerConfigurer.setBasePackage("com.chinaoly.**.dao");
        //配置通用Mapper，详情请查阅官方文档
        Properties properties = new Properties();
        properties.setProperty("mappers", "com.chinaoly.frm.core.baseDao.mybatis.Mapper");
        properties.setProperty("notEmpty", "false");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
        properties.setProperty("IDENTITY", "select sys_guid() from dual");
        properties.setProperty("ORDER", "BEFORE");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

}

