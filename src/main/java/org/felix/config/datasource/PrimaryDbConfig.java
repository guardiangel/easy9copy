package org.felix.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 用来扫描Mapper类及对应的mapper.xml文件
 * @author Felix
 */
@Configuration
@MapperScan(basePackages = PrimaryDbConfig.MAPPER_PACKAGE, sqlSessionFactoryRef = "")
@Primary
public class PrimaryDbConfig {

    static final String MAPPER_PACKAGE = "org.felix.mapper";
    static final String MAPPER_XML_LOCATION = "classpath:mapper/*.xml";

    @Value("${system.isShowMybatisLog}")
    private String isShowMybatisLog;

    @Value("${spring.datasource.druid.url}")
    private String url;

    @Value("${spring.datasource.druid.username}")
    private String user;

    @Value("${spring.datasource.druid.password}")
    private String password;

    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClass;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClass);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

    @Bean(name = "dbTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "dbSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource primaryDataSource)
            throws Exception {

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(PrimaryDbConfig.MAPPER_XML_LOCATION));
        org.apache.ibatis.session.Configuration ibatisConfiguration
                = new org.apache.ibatis.session.Configuration();

        ibatisConfiguration.setLogImpl("true".equals(isShowMybatisLog) ? StdOutImpl.class : null);
        sessionFactory.setConfiguration(ibatisConfiguration);

        return sessionFactory.getObject();
    }
}
