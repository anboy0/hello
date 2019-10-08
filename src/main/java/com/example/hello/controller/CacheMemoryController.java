package com.example.hello.controller;

import com.example.hello.pojo.vo.SysCacheMemoryVO;
import com.example.hello.version.ApiVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@ApiVersion(1)
@Api(description = "缓存接口")
@RequestMapping("/{version}")
@RestController
public class CacheMemoryController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("cache/totalMemory")
    @ApiModelProperty("查询redis内存详情")
    public SysCacheMemoryVO getTotalMemory(){
        Properties prop = redisTemplate.getConnectionFactory().getConnection().info("memory");
        String usedMemoryHuman = prop.get("used_memory_human").toString();
        String usedMemoryPeakHuman = prop.get("used_memory_peak_human").toString();
        String usedMemoryRss = prop.get("used_memory_rss").toString();
        String memFragmentationRatio = prop.get("mem_fragmentation_ratio").toString();
        return new SysCacheMemoryVO(usedMemoryHuman,usedMemoryPeakHuman,usedMemoryRss,memFragmentationRatio);
    }
}
