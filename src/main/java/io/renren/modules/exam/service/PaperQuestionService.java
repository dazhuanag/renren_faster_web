package io.renren.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.exam.entity.PaperQuestionEntity;

import java.util.Map;

/**
 * 试卷题目表
 *
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
public interface PaperQuestionService extends IService<PaperQuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

