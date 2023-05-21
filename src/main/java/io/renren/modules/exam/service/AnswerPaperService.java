package io.renren.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.exam.entity.AnswerPaperEntity;

import java.util.Map;

/**
 * 学生答卷表表
 *
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
public interface AnswerPaperService extends IService<AnswerPaperEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

