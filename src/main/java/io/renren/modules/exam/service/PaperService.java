package io.renren.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.exam.entity.PaperEntity;

import java.util.Map;

/**
 * 试卷表
 *
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
public interface PaperService extends IService<PaperEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

