package io.renren.modules.exam.service.impl;

import io.renren.modules.exam.entity.EMajorEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.exam.dao.ECourseDao;
import io.renren.modules.exam.entity.ECourseEntity;
import io.renren.modules.exam.service.ECourseService;


@Service("eCourseService")
public class ECourseServiceImpl extends ServiceImpl<ECourseDao, ECourseEntity> implements ECourseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object key = params.get("key");
        QueryWrapper<ECourseEntity> wrapper = new QueryWrapper<>();

        if (key != null) {
            wrapper.like("name",  key );
        }
        IPage<ECourseEntity> page = this.page(
                new Query<ECourseEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}
