package io.renren.modules.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考试表
 * 
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
@Data
@TableName("e_exam")
public class ExamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 考试名称
	 */
	private String name;
	/**
	 * 试卷ID
	 */
	private String paperId;
	/**
	 * 合格分数
	 */
	private Integer qualifiedScore;
	/**
	 * 是否删除  0 未删除 1已删除
	 */
	private Integer deleted;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
