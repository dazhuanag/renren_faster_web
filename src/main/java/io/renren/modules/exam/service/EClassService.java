package io.renren.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.exam.entity.EClassEntity;

import java.util.Map;

/**
 * 班级表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-05-18 23:16:35
 */
public interface EClassService extends IService<EClassEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

