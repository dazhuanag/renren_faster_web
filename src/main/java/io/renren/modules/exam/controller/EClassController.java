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

import io.renren.modules.exam.entity.EClassEntity;
import io.renren.modules.exam.service.EClassService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 班级表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-05-18 23:16:35
 */
@RestController
@RequestMapping("generator/eclass")
public class EClassController {
    @Autowired
    private EClassService eClassService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:eclass:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = eClassService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:eclass:info")
    public R info(@PathVariable("id") Long id){
		EClassEntity eClass = eClassService.getById(id);

        return R.ok().put("eClass", eClass);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:eclass:save")
    public R save(@RequestBody EClassEntity eClass){
        eClass.setCreateTime(new Date());
        eClassService.save(eClass);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:eclass:update")
    public R update(@RequestBody EClassEntity eClass){
		eClassService.updateById(eClass);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:eclass:delete")
    public R delete(@RequestBody Long[] ids){
		eClassService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
