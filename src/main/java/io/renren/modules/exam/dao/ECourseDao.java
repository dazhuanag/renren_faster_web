package io.renren.modules.exam.dao;

import io.renren.modules.exam.entity.ECourseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-05-18 23:16:35
 */
@Mapper
public interface ECourseDao extends BaseMapper<ECourseEntity> {

}
