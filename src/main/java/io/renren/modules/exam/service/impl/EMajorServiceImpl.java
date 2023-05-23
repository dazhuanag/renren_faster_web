package io.renren.modules.exam.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.exam.dao.EMajorDao;
import io.renren.modules.exam.entity.EMajorEntity;
import io.renren.modules.exam.service.EMajorService;


@Service("eMajorService")
public class EMajorServiceImpl extends ServiceImpl<EMajorDao, EMajorEntity> implements EMajorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object key = params.get("key");
        QueryWrapper<EMajorEntity> wrapper = new QueryWrapper<>();

        if (key != null) {
            wrapper.like("name",  key );
        }
        IPage<EMajorEntity> page = this.page(
                new Query<EMajorEntity>().getPage(params), wrapper
        );

        return new PageUtils(page);
    }

}
