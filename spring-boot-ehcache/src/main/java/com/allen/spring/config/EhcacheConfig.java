/**
 * 
 */
package com.allen.spring.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;

/**
 * @author first
 *
 */
@Configuration
public class EhcacheConfig implements CachingConfigurer {

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("mvtmEhcache");
        cacheConfiguration.setEternal(false);
        cacheConfiguration.addPersistence(new PersistenceConfiguration().strategy(Strategy.NONE));//No persist
        cacheConfiguration.setTimeToIdleSeconds(43200);//12 hours
        cacheConfiguration.setTimeToLiveSeconds(86400);//24 hours
        cacheConfiguration.setMaxEntriesLocalHeap(1000);
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");//清理内存策略,LRU(最近最少使用)
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfiguration);
        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }

}
