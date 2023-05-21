package io.renren.modules.exam.dao;

import io.renren.modules.exam.entity.PaperQuestionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 试卷题目表
 * 
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
@Mapper
public interface PaperQuestionDao extends BaseMapper<PaperQuestionEntity> {
	
}
