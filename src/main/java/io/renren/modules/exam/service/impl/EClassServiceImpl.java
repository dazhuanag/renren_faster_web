package io.renren.modules.exam.service.impl;

import io.renren.modules.exam.entity.ECourseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.exam.dao.EClassDao;
import io.renren.modules.exam.entity.EClassEntity;
import io.renren.modules.exam.service.EClassService;


@Service("eClassService")
public class EClassServiceImpl extends ServiceImpl<EClassDao, EClassEntity> implements EClassService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object key = params.get("key");
        QueryWrapper<EClassEntity> wrapper = new QueryWrapper<>();

        if (key != null) {
            wrapper.like("name", key);
        }
        IPage<EClassEntity> page = this.page(
                new Query<EClassEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}
