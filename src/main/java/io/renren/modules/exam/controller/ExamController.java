package io.renren.modules.exam.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import io.renren.common.exception.RRException;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.oss.entity.SysOssEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.exam.entity.ExamEntity;
import io.renren.modules.exam.service.ExamService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 考试表
 *
 * @author renjw
 * @email
 * @date 2023-05-20 00:20:07
 */
@RestController
@RequestMapping("exam/exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        OSS ossClient = new OSSClientBuilder().build("oss-cn-hangzhou.aliyuncs.com", "LTAI4GCH1vX6DKqJWxd6nEuW", "yBshYweHOpqDuhCArrVHwIiBKpyqSL");
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");

        PutObjectResult putObjectResult = ossClient.putObject("web-tlias", "picture", file.getInputStream(), meta);
        ossClient.shutdown();
        //上传文件
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

//        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

        //保存文件信息
//        SysOssEntity ossEntity = new SysOssEntity();
//        ossEntity.setUrl(url);
//        ossEntity.setCreateDate(new Date());
//        sysOssService.save(ossEntity);

        return R.ok().put("result", putObjectResult);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("exam:exam:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = examService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("exam:exam:info")
    public R info(@PathVariable("id") Long id) {
        ExamEntity exam = examService.getById(id);

        return R.ok().put("exam", exam);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("exam:exam:save")
    public R save(@RequestBody ExamEntity exam) {
        exam.setCreateTime(new Date());
        examService.save(exam);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("exam:exam:update")
    public R update(@RequestBody ExamEntity exam) {
        exam.setCreateTime(new Date());
        examService.updateById(exam);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("exam:exam:delete")
    public R delete(@RequestBody Long[] ids) {
        examService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
