package io.github.anotherme17.spiderproxy.config.database;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lirenhao
 * date: 2017/12/1 下午3:37
 */
@Slf4j
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ProxyDataSourceConfig.PACKAGE,
        sqlSessionFactoryRef = "proxySqlSessionFactory")
public class ProxyDataSourceConfig {

    static final String PACKAGE = "io.github.anotherme17.spiderproxy.dao.proxy";
    private static final String TYPE_ALIASES_PACKAGE = "io.github.anotherme17.spiderproxy.po.proxy";
    private static final String MAPPER_LOCATION = "classpath:/mapper/proxy/*.xml";
    private static final String CONFIG_LOCATION = "classpath:/mapper/proxy_config.xml";

    @Autowired
    private Environment env;

    @Value("${proxy.datasource.url}")
    private String url;

    @Value("${proxy.datasource.username}")
    private String user;

    @Value("${proxy.datasource.password}")
    private String password;

    @Value("${proxy.datasource.driverClassName}")
    private String driverClass;

    private Map<String, Object> datasourceMap;

    @PostConstruct
    public void init() {
        datasourceMap = new HashMap<>();
        for (PropertySource<?> propertySource : ((AbstractEnvironment) env).getPropertySources()) {
            this.getPropertiesFromSource(propertySource, datasourceMap);
        }
    }

    private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
                map.put(key, propertySource.getProperty(key));
            }
        }
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
                getPropertiesFromSource(s, map);
            }
        }
    }

    @Bean(name = "proxyDataSource")
    @Primary
    public DataSource masterDataSource() {
        datasourceMap.put(DruidDataSourceFactory.PROP_URL, url);
        datasourceMap.put(DruidDataSourceFactory.PROP_USERNAME, user);
        datasourceMap.put(DruidDataSourceFactory.PROP_PASSWORD, password);
        datasourceMap.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, driverClass);

        try {
            return DruidDataSourceFactory.createDataSource(datasourceMap);
        } catch (Exception e) {
            log.error("无法获得数据源'{}':'{}'", e);
            throw new RuntimeException("无法获得数据源.");
        }
    }

    @Bean(name = "proxyTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "proxySqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("proxyDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ProxyDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        sessionFactory.setConfigLocation(
                new DefaultResourceLoader().getResource(CONFIG_LOCATION));
        return sessionFactory.getObject();
    }
}

