package io.renren.modules.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学院表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-05-18 23:16:35
 */
@Data
@TableName("e_major")
public class EMajorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 学院名称
	 */
	private String name;
	/**
	 * 是否删除  0 未删除 1已删除
	 */
	private Integer deleted;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
