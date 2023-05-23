package io.renren.modules.exam.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.ImageUtils;
import io.renren.modules.exam.entity.PaperEntity;
import io.renren.modules.exam.entity.QuestionEntity;
import io.renren.modules.exam.service.PaperService;
import io.renren.modules.exam.service.QuestionService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
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
    @Autowired
    private QuestionService questionService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserService userService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("exam:answerpaper:list")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = answerPaperService.queryPage(params);
        SysUserEntity user = getUser();
        List<Long> longs = sysUserRoleService.queryRoleIdList(user.getUserId());
        QueryWrapper<AnswerPaperEntity> wrapper = new QueryWrapper<>();
        if (longs.contains(3L)){
            wrapper.eq("user_id", user.getUserId());
        }
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
            if (!answerPapers.isEmpty()){
                Long userId = answerPapers.get(0).getUserId();
                SysUserEntity userEntity = userService.getById(userId);
                jsonObject.put("userName", userEntity.getUsername());
            }
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
            AnswerPaperEntity answerPaperEntity = new AnswerPaperEntity();
            answerPaperEntity.setAnswer(reply);
            answerPaperEntity.setPaperId(paperId);
            answerPaperEntity.setQuestionId(id);
            answerPaperEntity.setCreateTime(new Date());
            answerPaperEntity.setUserId(userId);
            String standerAnswer = question.getString("standerAnswer");
            Integer maxScore = question.getInteger("score");
            // 识别图片,自动计算分数
//            String imageUrl = question.getString("imageUrl");
//            String answer = ImageUtils.getImageText(imageUrl);
            int score = ImageUtils.getScore(standerAnswer, reply, maxScore);
            answerPaperEntity.setScore(score);
            answerPaperService.save(answerPaperEntity);
        });
        return R.ok();
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
        Long userId = getUserId();
        QueryWrapper<AnswerPaperEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", id).eq("user_id", userId);
        answerPaperService.remove(wrapper);
        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/detail/{id}")
//    @RequiresPermissions("exam:paper:info")
    public R getPaperInfo(@PathVariable("id") Long id, @RequestParam Map<String, Object> params) {
        Long userId = getUserId();
        QueryWrapper<AnswerPaperEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_id", id);
        queryWrapper.eq("user_id", userId);
        List<AnswerPaperEntity> list = answerPaperService.list(queryWrapper);

        PaperEntity paper = paperService.getById(id);
//        QueryWrapper<PaperQuestionEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("paper_id", paper.getId());
//        List<PaperQuestionEntity> paperQuestions = paperQuestionService.list(queryWrapper);
        List<JSONObject> questionJsons = new ArrayList<>();
        list.forEach(answerPaperEntity -> {
            JSONObject jsonObject = new JSONObject();
//            BeanUtil.copyProperties(paperQuestion, jsonObject);
            QuestionEntity question = questionService.getById(answerPaperEntity.getQuestionId());
            jsonObject.put("content", question.getContent());
            jsonObject.put("answer", answerPaperEntity.getAnswer());
            jsonObject.put("type", question.getType());
            jsonObject.put("score", question.getScores());
            jsonObject.put("questionId", answerPaperEntity.getId());
            jsonObject.put("paperId", paper.getId());
            jsonObject.put("getScore", answerPaperEntity.getScore());
            questionJsons.add(jsonObject);
        });
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(paper));
//        jsonObject.put("paper",paper);
        jsonObject.put("questionList", questionJsons);
        return R.ok().put("data", jsonObject);
    }

}
