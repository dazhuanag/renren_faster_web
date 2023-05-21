package io.renren.modules.exam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.exam.dao.AnswerPaperDao;
import io.renren.modules.exam.entity.AnswerPaperEntity;
import io.renren.modules.exam.service.AnswerPaperService;


@Service("answerPaperService")
public class AnswerPaperServiceImpl extends ServiceImpl<AnswerPaperDao, AnswerPaperEntity> implements AnswerPaperService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AnswerPaperEntity> page = this.page(
                new Query<AnswerPaperEntity>().getPage(params),
                new QueryWrapper<AnswerPaperEntity>()
        );


        return new PageUtils(page);
    }

}
