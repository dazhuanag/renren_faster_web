package io.renren.modules.exam.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.exam.entity.PaperEntity;
import io.renren.modules.exam.entity.PaperQuestionEntity;
import io.renren.modules.exam.service.PaperService;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.exam.entity.AnswerPaperEntity;
import io.renren.modules.exam.service.AnswerPaperService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 学生答卷表表
 *
 * @author renjw
 * @email
 * @date 2023-05-20 00:20:07
 */
@RestController
@RequestMapping("exam/answerpaper")
public class AnswerPaperController extends AbstractController {
    @Autowired
    private AnswerPaperService answerPaperService;
    @Autowired
    private PaperService paperService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("exam:answerpaper:list")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = answerPaperService.queryPage(params);
        Long userId = getUserId();
        QueryWrapper<AnswerPaperEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<AnswerPaperEntity> list = answerPaperService.list(wrapper);
        Map<Long, List<AnswerPaperEntity>> collect = list.stream().collect(Collectors.groupingBy(AnswerPaperEntity::getPaperId));
        Set<Long> paperIds = collect.keySet();
        List<JSONObject> result = new ArrayList<>();
        for (Long paperId : paperIds) {
            List<AnswerPaperEntity> answerPapers = collect.get(paperId);
            long userTotalScore = answerPapers.stream().map(AnswerPaperEntity::getScore).mapToLong(Long::valueOf).sum();
            JSONObject jsonObject = new JSONObject();
            PaperEntity paper = paperService.getById(paperId);
            jsonObject.put("paperName", paper.getName());
            jsonObject.put("paperId", paper.getId());
            jsonObject.put("paperTotalScore", paper.getTotalScore());
            jsonObject.put("paperTotalTime", paper.getTotalTime());
            jsonObject.put("userTotalScore", userTotalScore);
            jsonObject.put("userName", getUser().getUsername());
            jsonObject.put("examTime", answerPapers.get(0).getCreateTime());

            result.add(jsonObject);
        }
        return R.ok().put("data", result);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("exam:answerpaper:info")
    public R info(@PathVariable("id") Long id) {
        AnswerPaperEntity answerPaper = answerPaperService.getById(id);

        return R.ok().put("answerPaper", answerPaper);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("exam:answerpaper:save")
    public R save(@RequestBody JSONObject jsonObject) {
        Long userId = getUser().getUserId();
        Long paperId = jsonObject.getLong("paperId");
        JSONArray questionList = jsonObject.getJSONArray("questionList");
        List<JSONObject> questions = questionList.stream().map(item -> JSONObject.parseObject(JSON.toJSONString(item), JSONObject.class)).collect(Collectors.toList());
        questions.forEach(question -> {
            Long id = question.getLong("id");
            String reply = question.getString("reply");
            String standerAnswer = question.getString("standerAnswer");
            Integer maxScore = question.getInteger("score");
            AnswerPaperEntity answerPaperEntity = new AnswerPaperEntity();
            answerPaperEntity.setAnswer(reply);
            answerPaperEntity.setPaperId(paperId);
            answerPaperEntity.setQuestionId(id);
            int score = getScore(reply, standerAnswer, maxScore);
            answerPaperEntity.setScore(score);
            answerPaperEntity.setCreateTime(new Date());
            answerPaperEntity.setUserId(userId);
            answerPaperService.save(answerPaperEntity);
        });

//		answerPaperService.save(answerPaper);

        return R.ok();
    }

    private int getScore(String reply, String standerAnswer, Integer maxScore) {
        if (StrUtil.isBlank(reply)) {
            return 0;
        }
        int max = Math.max(0, maxScore - 10);
        return RandomUtil.randomInt(max, maxScore);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("exam:answerpaper:update")
    public R update(@RequestBody AnswerPaperEntity answerPaper) {
        answerPaperService.updateById(answerPaper);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("exam:answerpaper:delete")
    public R delete(@RequestBody Long[] ids) {
        answerPaperService.removeByIds(Arrays.asList(ids));
        QueryWrapper<AnswerPaperEntity> wrapper = new QueryWrapper<>();
        wrapper.in("paper_id", (Object) ids);
        answerPaperService.remove(wrapper);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/remove/{id}")
    @RequiresPermissions("exam:answerpaper:delete")
    public R delete(@PathVariable("id") Long id) {
        QueryWrapper<AnswerPaperEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", id);
        answerPaperService.remove(wrapper);
        return R.ok();
    }

}
