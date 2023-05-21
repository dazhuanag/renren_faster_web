package io.renren.modules.exam.VO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.exam.entity.QuestionEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 考试题目表
 *
 * @author renjw
 * @email
 * @date 2023-05-20 00:20:07
 */
@Data
public class QuestionVO extends QuestionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String courseName;
}
