package io.renren.modules.exam.VO;

import io.renren.modules.exam.entity.ExamEntity;
import io.renren.modules.exam.entity.QuestionEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 考试题目表
 *
 * @author renjw
 * @email
 * @date 2023-05-20 00:20:07
 */
@Data
public class ExamVO extends ExamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String paperName;

	private Integer totalScore;
	/**
	 * 试卷总时长
	 */
	private Integer totalTime;
}
