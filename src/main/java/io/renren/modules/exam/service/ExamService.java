package io.renren.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.exam.entity.ExamEntity;

import java.util.Map;

/**
 * 考试表
 *
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
public interface ExamService extends IService<ExamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

