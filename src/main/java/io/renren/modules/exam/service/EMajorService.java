package io.renren.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.exam.entity.EMajorEntity;

import java.util.Map;

/**
 * 学院表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-05-18 23:16:35
 */
public interface EMajorService extends IService<EMajorEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

