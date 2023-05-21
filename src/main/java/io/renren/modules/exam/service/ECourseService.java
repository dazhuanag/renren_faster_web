package io.renren.modules.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.exam.entity.ECourseEntity;

import java.util.Map;

/**
 * 课程表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-05-18 23:16:35
 */
public interface ECourseService extends IService<ECourseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

