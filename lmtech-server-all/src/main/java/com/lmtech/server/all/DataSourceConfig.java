package com.lmtech.server.all;

import com.alibaba.druid.pool.DruidDataSource;
import com.lmtech.util.LoggerManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataSourceConfig {

	@Value("${data_source.driver_class_name}")
	private String driverClassName;
	
    @Value("${data_source.url}")
    private String url;
	
	@Value("${data_source.user_name}")
    private String userName;
	
	@Value("${data_source.password}")
    private String password;
	
	@Value("${data_source.max_active}")
    private int maxActive;
	
	@Value("${data_source.initial_size}")
    private int initialSize;
	
	@Value("${data_source.max_idle}")
    private int maxIdle;
	
	//@Value("${data_source.driver_class_name}")
    private int minIdle;
	
	@Value("${data_source.maxWait}")
    private int maxWait;
	
	@Value("${data_source.test_on_borrow}")
    private boolean testOnBorrow;
	
	@Value("${data_source.test_while_idle}")
    private boolean testWhileIdle;
	
	@Value("${data_source.time_between_eviction_runs_millis}")
    private long timeBetweenEvictionRunsMillis;
    
    @Value("${data_source.min_evictable_idle_time_millis}")
    private long minEvictableIdleTimeMillis;
    
    @Value("${data_source.num_tests_per_eviction_run}")
    private int numTestsPerEvictionRun;
    
    @Value("${data_source.remove_abandoned}")
    private boolean removeAbandoned;
    
    @Value("${data_source.remove_abandoned_timeout}")
    private int removeAbandonedTimeout;

    @Bean
    public DataSource dataSource() {
        LoggerManager.info("初始化数据源 => 开始");
        DruidDataSource bds = new DruidDataSource();
        bds.setDriverClassName(this.getDriverClassName());
        bds.setUrl(this.getUrl());
        bds.setUsername(this.getUserName());
        bds.setPassword(this.getPassword());
        bds.setMaxActive(this.getMaxActive());
        bds.setInitialSize(this.getInitialSize());
        bds.setMaxIdle(this.getMaxIdle());
        bds.setMinIdle(this.getMinIdle());
        bds.setMaxWait(this.getMaxWait());
        bds.setTimeBetweenEvictionRunsMillis(this.getTimeBetweenEvictionRunsMillis());
        bds.setMinEvictableIdleTimeMillis(this.getMinEvictableIdleTimeMillis());
        bds.setNumTestsPerEvictionRun(this.getNumTestsPerEvictionRun());
        bds.setRemoveAbandonedTimeout(this.getRemoveAbandonedTimeout());
        bds.setTestOnBorrow(this.isTestOnBorrow());
        bds.setTestWhileIdle(this.isTestWhileIdle());
        bds.setRemoveAbandoned(this.isRemoveAbandoned());
        List<String> initSqls = new ArrayList<>();
        initSqls.add("set names 'utf8mb4'");
        bds.setConnectionInitSqls(initSqls);
        LoggerManager.info("初始化数据源 => 结束");
        return bds;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public boolean isRemoveAbandoned() {
        return removeAbandoned;
    }

    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }
}
