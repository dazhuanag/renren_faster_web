package io.renren.modules.exam.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.exam.entity.PaperQuestionEntity;
import io.renren.modules.exam.service.PaperQuestionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 试卷题目表
 *
 * @author renjw
 * @email 
 * @date 2023-05-20 00:20:07
 */
@RestController
@RequestMapping("exam/paperquestion")
public class PaperQuestionController {
    @Autowired
    private PaperQuestionService paperQuestionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("exam:paperquestion:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = paperQuestionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("exam:paperquestion:info")
    public R info(@PathVariable("id") Long id){
		PaperQuestionEntity paperQuestion = paperQuestionService.getById(id);

        return R.ok().put("paperQuestion", paperQuestion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("exam:paperquestion:save")
    public R save(@RequestBody PaperQuestionEntity paperQuestion){
		paperQuestionService.save(paperQuestion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("exam:paperquestion:update")
    public R update(@RequestBody PaperQuestionEntity paperQuestion){
		paperQuestionService.updateById(paperQuestion);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("exam:paperquestion:delete")
    public R delete(@RequestBody Long[] ids){
		paperQuestionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
