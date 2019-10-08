package com.example.hello.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SysCacheMemoryVO implements Serializable {

    @ApiModelProperty("内存使用的当前值")
    private String usedMemoryHuman;

    @ApiModelProperty("内存使用的峰值")
    private String usedMemoryPeakHuman;

    @ApiModelProperty("Redis进程占用的物理内存总量")
    private String usedMemoryRss;

    @ApiModelProperty("used_memory_rss/used_memory比值，内存碎片率")
    private String memFragmentationRatio;
}
