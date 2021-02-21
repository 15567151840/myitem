package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.subject.OneSubject;
import com.atguigu.subject.TwoSubject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-10
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
      try{
          InputStream in = file.getInputStream();
          EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
      }catch(Exception e){
          e.printStackTrace();
      }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //获取一级分类的所有数据
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id",0);
        List<EduSubject> oneSubjects = baseMapper.selectList(oneWrapper);

        //查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id",0);
        List<EduSubject> twoSubjects = baseMapper.selectList(twoWrapper);

        //创建list集合封装最终数据
        List<OneSubject> finalList = new ArrayList<>();

        for (int i = 0; i < oneSubjects.size(); i++) {
            EduSubject eduSubject = oneSubjects.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalList.add(oneSubject);

            List<TwoSubject> twoFinalList = new ArrayList<>();
            for (int m = 0; m < twoSubjects.size(); m++) {
                EduSubject eduSubject1 =twoSubjects.get(m);
                if(eduSubject1.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject1,twoSubject);
                    twoFinalList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalList);
        }
        return finalList;
    }
}
