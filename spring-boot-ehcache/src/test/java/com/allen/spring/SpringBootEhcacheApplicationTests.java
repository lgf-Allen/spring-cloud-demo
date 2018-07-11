package com.allen.spring;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringBootEhcacheApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SpringBootEhcacheApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Resource
    private CacheManager cacheManager;

    @Test
    public void cacheTest() {

     // 显示所有的Cache空间
        System.out.println(StringUtils.join(cacheManager.getCacheNames()));
        Cache cache = cacheManager.getCache("mvtmEhcache");
        cache.put("key", "123");
        System.out.println("缓存成功");
        String res = cache.get("key", String.class);
        System.out.println(res);
    }

}
