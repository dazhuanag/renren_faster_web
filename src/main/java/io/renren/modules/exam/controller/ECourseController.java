package io.renren.modules.exam.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.exam.entity.ECourseEntity;
import io.renren.modules.exam.service.ECourseService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 课程表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-05-18 23:16:35
 */
@RestController
@RequestMapping("generator/ecourse")
public class ECourseController {
    @Autowired
    private ECourseService eCourseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:ecourse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = eCourseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:ecourse:info")
    public R info(@PathVariable("id") Long id){
		ECourseEntity eCourse = eCourseService.getById(id);

        return R.ok().put("eCourse", eCourse);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:ecourse:save")
    public R save(@RequestBody ECourseEntity eCourse){
        eCourse.setCreateTime(new Date());

        eCourseService.save(eCourse);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:ecourse:update")
    public R update(@RequestBody ECourseEntity eCourse){
		eCourseService.updateById(eCourse);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:ecourse:delete")
    public R delete(@RequestBody Long[] ids){
		eCourseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
