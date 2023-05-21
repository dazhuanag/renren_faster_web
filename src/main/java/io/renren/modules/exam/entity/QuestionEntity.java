package io.renren.modules.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考试题目表
 *
 * @author renjw
 * @email
 * @date 2023-05-20 00:20:07
 */
@Data
@TableName("e_question")
public class QuestionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 课程id
	 */
	private Long courseId;
	/**
	 * 题目类型
	 */
	private String type;
	/**
	 * 题目图片
	 */
	private String imgurl;
	/**
	 * 题目
	 */
	private String content;
	/**
	 * 答案
	 */
	private String answer;
	/**
	 * 题目满分分数
	 */
	private Integer scores;
	/**
	 * 是否删除  0 未删除 1已删除
	 */
	private Integer deleted;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
