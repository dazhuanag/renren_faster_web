package io.renren.modules.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.modules.exam.VO.ExamVO;
import io.renren.modules.exam.VO.QuestionVO;
import io.renren.modules.exam.entity.ECourseEntity;
import io.renren.modules.exam.entity.PaperEntity;
import io.renren.modules.exam.entity.QuestionEntity;
import io.renren.modules.exam.service.ECourseService;
import io.renren.modules.exam.service.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.exam.dao.ExamDao;
import io.renren.modules.exam.entity.ExamEntity;
import io.renren.modules.exam.service.ExamService;


@Service("examService")
@RequiredArgsConstructor
public class ExamServiceImpl extends ServiceImpl<ExamDao, ExamEntity> implements ExamService {

    private final PaperService paperService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<ExamEntity> wrapper = new QueryWrapper<>();
        Object key = params.get("key");
        if (key != null) {
            wrapper.like("name", "%" + key + "%");
        }
        IPage<ExamEntity> page = this.page(
                new Query<ExamEntity>().getPage(params),
                wrapper
        );
        List<ExamEntity> questionEntities = page.getRecords();
        List<String> paperIds = questionEntities.stream().map(ExamEntity::getPaperId).collect(Collectors.toList());
        List<PaperEntity> eCourseEntities = paperService.listByIds(paperIds);

        IPage<ExamVO> convert = page.convert(examEntity -> {
            ExamVO examVO = new ExamVO();
            BeanUtil.copyProperties(examEntity, examVO);
            PaperEntity paperEntity = eCourseEntities.stream().filter(item -> String.valueOf(item.getId()).equals(examEntity.getPaperId())).findFirst().orElse(null);
            if (paperEntity != null) {
                examVO.setPaperName(paperEntity.getName());
                examVO.setTotalTime(paperEntity.getTotalTime());
                examVO.setTotalScore(paperEntity.getTotalScore());
            }
            return examVO;
        });
        return new PageUtils(convert);
    }

}
