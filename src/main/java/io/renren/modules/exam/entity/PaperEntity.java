package io.renren.modules.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试卷表
 * 
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
@Data
@TableName("e_paper")
public class PaperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 试卷名称
	 */
	private String name;
	/**
	 * 试卷总数
	 */
	private Integer totalScore;
	/**
	 * 试卷总时长
	 */
	private Integer totalTime;
	/**
	 * 学院id
	 */
	private Long majorId;
	/**
	 * 课程id
	 */
	private Long courseId;
	/**
	 * 是否删除  0 未删除 1已删除
	 */
	private Integer deleted;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
