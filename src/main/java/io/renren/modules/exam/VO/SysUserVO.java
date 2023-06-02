package io.renren.modules.exam.VO;

import io.renren.modules.sys.entity.SysUserEntity;
import lombok.Data;

/**
 * @author renjw
 * @desc
 * @date 2023/6/2 3:33 PM
 **/
@Data
public class SysUserVO extends SysUserEntity {

    private String roleName;

    private String className;
    private String courseName;
    private String majorName;
}
