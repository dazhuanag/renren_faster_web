package io.renren.modules.exam.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.exam.dao.PaperDao;
import io.renren.modules.exam.entity.PaperEntity;
import io.renren.modules.exam.service.PaperService;


@Service("paperService")
public class PaperServiceImpl extends ServiceImpl<PaperDao, PaperEntity> implements PaperService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PaperEntity> wrapper = new QueryWrapper<>();
        String key = params.get("key").toString();
        if (StrUtil.isNotBlank(key)) {
            wrapper.like("name", key);
        }
        IPage<PaperEntity> page = this.page(
                new Query<PaperEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}
