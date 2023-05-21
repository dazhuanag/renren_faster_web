package io.renren.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.exam.entity.QuestionEntity;

import java.util.Map;

/**
 * 考试题目表
 *
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
public interface QuestionService extends IService<QuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

