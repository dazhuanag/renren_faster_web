package io.renren.modules.exam.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.exam.entity.EMajorEntity;
import io.renren.modules.exam.service.EMajorService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 学院表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-05-18 23:16:35
 */
@RestController
@RequestMapping("generator/emajor")
public class EMajorController {
    @Autowired
    private EMajorService eMajorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:emajor:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = eMajorService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/listAll")
    @RequiresPermissions("generator:emajor:list")
    public R listAll(@RequestParam Map<String, Object> params){
//        PageUtils page = eMajorService.queryPage(params);
        List<EMajorEntity> list = eMajorService.list();

        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:emajor:info")
    public R info(@PathVariable("id") Long id){
		EMajorEntity eMajor = eMajorService.getById(id);

        return R.ok().put("eMajor", eMajor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:emajor:save")
    public R save(@RequestBody EMajorEntity eMajor){
        eMajor.setCreateTime(new Date());
		eMajorService.save(eMajor);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:emajor:update")
    public R update(@RequestBody EMajorEntity eMajor){
		eMajorService.updateById(eMajor);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:emajor:delete")
    public R delete(@RequestBody Long[] ids){
		eMajorService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
