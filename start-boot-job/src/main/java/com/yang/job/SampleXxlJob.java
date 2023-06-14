package com.yang.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2023/06/13/09:47
 */



/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 *      1、任务开发：在Spring Bean实例中，开发Job方法；
 *      2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 *      3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 *      4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 *
 */
@Component
@Slf4j
public class SampleXxlJob {

    /**
     * 简单任务实例（bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler(){
        // 需要执行的任务业务代码
        log.info("xxl-job hello world");
        XxlJobHelper.log("xxl-job hello world");
    }

    /**
     * 获取admin传入的参数
     */
    @XxlJob("demoJobHandlerParams")
    public void demoJobHandlerParams(){
        // 获取后台管理传过来的参数
        String jobParam = XxlJobHelper.getJobParam();

        // 需要执行的任务业务代码
        log.info("xxl-job demoJobHandlerParams={}",jobParam);
    }


    /**
     * 2、分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() throws Exception {
        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        XxlJobHelper.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);
        log.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);
        // 业务逻辑
        for (int i = 0; i < shardTotal; i++) {
            if (i == shardIndex) {
                XxlJobHelper.log("第 {} 片, 命中分片开始处理", i);
                log.info("第 {} 片, 命中分片开始处理", i);
            } else {
                XxlJobHelper.log("第 {} 片, 忽略", i);
                log.info("第 {} 片, 忽略", i);
            }
        }

    }
}
