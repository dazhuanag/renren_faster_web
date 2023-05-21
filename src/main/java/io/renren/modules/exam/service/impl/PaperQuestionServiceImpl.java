package io.renren.modules.exam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.exam.dao.PaperQuestionDao;
import io.renren.modules.exam.entity.PaperQuestionEntity;
import io.renren.modules.exam.service.PaperQuestionService;


@Service("paperQuestionService")
public class PaperQuestionServiceImpl extends ServiceImpl<PaperQuestionDao, PaperQuestionEntity> implements PaperQuestionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PaperQuestionEntity> page = this.page(
                new Query<PaperQuestionEntity>().getPage(params),
                new QueryWrapper<PaperQuestionEntity>()
        );

        return new PageUtils(page);
    }

}