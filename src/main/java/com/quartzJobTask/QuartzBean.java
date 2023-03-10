package com.quartzJobTask;

import lombok.Data;

/**
 * @Author xubin
 * @Date 2022/10/8 21:27
 * @Version
 **/

@Data
public class QuartzBean {
    /** 任务id */
    private String id;

    /** 任务名称 */
    private String jobName;

    /** 任务执行类 */
    private String jobClass;

    /** 任务状态 启动还是暂停*/
    private Integer status;

    /** 任务运行时间表达式 */
    private String cronExpression;
}
