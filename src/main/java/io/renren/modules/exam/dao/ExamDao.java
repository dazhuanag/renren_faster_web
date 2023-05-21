package io.renren.modules.exam.dao;

import io.renren.modules.exam.entity.ExamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考试表
 * 
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
@Mapper
public interface ExamDao extends BaseMapper<ExamEntity> {
	
}
