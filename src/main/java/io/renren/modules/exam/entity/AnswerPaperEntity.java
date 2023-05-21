package io.renren.modules.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学生答卷表表
 * 
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
@Data
@TableName("e_answer_paper")
public class AnswerPaperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 学生id
	 */
	private Long userId;
	/**
	 * 试卷id
	 */
	private Long paperId;
	/**
	 * 题目id
	 */
	private Long questionId;
	/**
	 * 解析出来的答案
	 */
	private String answer;
	/**
	 * 学生的答案图片
	 */
	private String answerUrl;
	/**
	 * 该题的答案得分
	 */
	private Integer score;
	/**
	 * 是否删除  0 未删除 1已删除
	 */
	private Integer deleted;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
