package io.renren.modules.exam.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.exam.entity.AnswerPaperEntity;
import io.renren.modules.exam.entity.PaperQuestionEntity;
import io.renren.modules.exam.entity.QuestionEntity;
import io.renren.modules.exam.service.AnswerPaperService;
import io.renren.modules.exam.service.PaperQuestionService;
import io.renren.modules.exam.service.QuestionService;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.exam.entity.PaperEntity;
import io.renren.modules.exam.service.PaperService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 试卷表
 *
 * @author renjw
 * @email
 * @date 2023-05-20 00:20:07
 */
@RestController
@RequestMapping("exam/paper")
public class PaperController {
    @Autowired
    private PaperService paperService;
    @Autowired
    private PaperQuestionService paperQuestionService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerPaperService answerPaperService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("exam:paper:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = paperService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listAll")
    @RequiresPermissions("exam:paper:list")
    public R listAll(@RequestParam Map<String, Object> params) {
        List<PaperEntity> list = paperService.list();

        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("exam:paper:info")
    public R info(@PathVariable("id") Long id) {
        PaperEntity paper = paperService.getById(id);

        return R.ok().put("paper", paper);
    }

    /**
     * 信息
     */
    @RequestMapping("/detail/{id}")
//    @RequiresPermissions("exam:paper:info")
    public R getPaperInfo(@PathVariable("id") Long id, @RequestParam Map<String, Object> params) {
        Object action = params.get("action");
        if (action != null && "joinExam".equals(action.toString())) {
            QueryWrapper<AnswerPaperEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("paper_id", id);
            List<AnswerPaperEntity> list = answerPaperService.list(wrapper);
            if (!list.isEmpty()){
                return R.error("您已经参与该考试,不能二次参与");
            }
        }
        PaperEntity paper = paperService.getById(id);
        QueryWrapper<PaperQuestionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_id", paper.getId());
        List<PaperQuestionEntity> paperQuestions = paperQuestionService.list(queryWrapper);
        List<JSONObject> questionJsons = new ArrayList<>();
        paperQuestions.forEach(paperQuestion -> {
            JSONObject jsonObject = new JSONObject();
            BeanUtil.copyProperties(paperQuestion, jsonObject);
            QuestionEntity question = questionService.getById(paperQuestion.getQuestionId());
            jsonObject.put("content", question.getContent());
            jsonObject.put("standerAnswer", question.getAnswer());
            jsonObject.put("type", question.getType());
            jsonObject.put("score", question.getScores());
            jsonObject.put("id", question.getId());
            jsonObject.put("paperId", paperQuestion.getPaperId());
            questionJsons.add(jsonObject);
        });
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(paper));
//        jsonObject.put("paper",paper);
        jsonObject.put("questionList", questionJsons);
        return R.ok().put("data", jsonObject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("exam:paper:save")
    public R save(@RequestBody JSONObject jsonObject) {
        JSONArray questionList = jsonObject.getJSONArray("testPaperQuestionList");
        if (questionList.isEmpty()) {
            return R.error("题目不能为空");
        }
        String name = jsonObject.getString("name");
        QueryWrapper<PaperEntity> queryWrapper = new QueryWrapper<PaperEntity>().eq("name", name);
        List<PaperEntity> list = paperService.list(queryWrapper);
        if (!list.isEmpty()) {
            return R.error("试卷名称不能重复不能为空");
        }
        PaperEntity paper = JSONObject.parseObject(JSON.toJSONString(jsonObject), PaperEntity.class);
        List<JSONObject> collect = questionList.stream().map(item -> JSONObject.parseObject(JSON.toJSONString(item), JSONObject.class)).collect(Collectors.toList());
        List<Integer> questionIds = collect.stream().map(item -> item.getInteger("question")).collect(Collectors.toList());
        int totalScores = collect.stream().map(item -> item.getInteger("scores")).mapToInt(Integer::valueOf).sum();
        paper.setTotalScore(totalScores);
        paper.setCreateTime(new Date());
        boolean save = paperService.save(paper);
        if (save) {
            PaperEntity paper1 = paperService.getOne(queryWrapper);
            for (Integer question : questionIds) {
                PaperQuestionEntity entity = new PaperQuestionEntity();
                entity.setPaperId(paper1.getId());
                entity.setQuestionId(question.longValue());
                entity.setCreateTime(new Date());
                paperQuestionService.save(entity);
            }
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("exam:paper:update")
    public R update(@RequestBody PaperEntity paper) {
        paperService.updateById(paper);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("exam:paper:delete")
    public R delete(@RequestBody Long[] ids) {
        paperService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
