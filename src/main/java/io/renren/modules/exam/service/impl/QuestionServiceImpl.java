package io.renren.modules.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.modules.exam.VO.QuestionVO;
import io.renren.modules.exam.entity.ECourseEntity;
import io.renren.modules.exam.service.ECourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.exam.dao.QuestionDao;
import io.renren.modules.exam.entity.QuestionEntity;
import io.renren.modules.exam.service.QuestionService;


@Service("questionService")
@RequiredArgsConstructor
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, QuestionEntity> implements QuestionService {

    private final ECourseService courseService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<QuestionEntity> wrapper = new QueryWrapper<>();
        Object key = params.get("key");
        if (key != null){
            wrapper.eq("course_id",key);
        }
        IPage<QuestionEntity> page = this.page(
                new Query<QuestionEntity>().getPage(params),
                wrapper
        );
        List<QuestionEntity> questionEntities = page.getRecords();
        List<Long> courseIds = questionEntities.stream().map(QuestionEntity::getCourseId).collect(Collectors.toList());
        List<ECourseEntity> eCourseEntities = courseService.listByIds(courseIds);

        IPage<QuestionVO> convert = page.convert(questionEntity -> {
            QuestionVO questionVO = new QuestionVO();
            BeanUtil.copyProperties(questionEntity, questionVO);
            ECourseEntity eCourseEntity = eCourseEntities.stream().filter(item -> item.getId().equals(questionEntity.getCourseId())).findFirst().orElse(null);
            if (eCourseEntity != null) {
                questionVO.setCourseName(eCourseEntity.getName());
            }
            return questionVO;
        });
        return new PageUtils(convert);
    }

}
