package com.ssm.maven.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssm.maven.core.dao.ScenicspotMapper;
import com.ssm.maven.core.entity.Scenicspot;
import com.ssm.maven.core.entity.ScenicspotCustom;
import com.ssm.maven.core.service.ScenicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("sceniservice")
public class ScenicServiceImpl implements ScenicService {

    @Resource
    private ScenicspotMapper scenicspotMapper;
    @Resource
    private Scenicspot scenicspot;

    @Override
    public List<Scenicspot> getScenicspotAll(Integer pageNum, Integer pageSize, Scenicspot scenicspot) {

        List<Scenicspot> list = new ArrayList<>();
        try {
            PageHelper.startPage(pageNum, pageSize);
            list = scenicspotMapper.getScenicspotAll(scenicspot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Scenicspot> getScenicspotAll(Scenicspot scenicspot) {

        List<Scenicspot> list = new ArrayList<>();
        try {
            list = scenicspotMapper.getScenicspotAll(scenicspot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Scenicspot> getScenicspotByName(Integer pageNum, Integer pageSize, String scenicname) {
        List<Scenicspot> list = new ArrayList<>();
        try {
            PageHelper.startPage(pageNum, pageSize);
            list = scenicspotMapper.getScenicspotByName(scenicname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insertSelective(Scenicspot scenicspot) throws Exception {
        scenicspotMapper.insertSelective(scenicspot);
    }

    @Override
    public Scenicspot getScenicspotByCode(String str) throws Exception {
        return scenicspotMapper.getScenicspotByCode(str);
    }

    @Override
    public List<Scenicspot> getScenicspotlistByCode(Integer pageNum, Integer pageSize, String code) throws Exception {
        List<Scenicspot> list = new ArrayList<>();
        try {
            PageHelper.startPage(pageNum, pageSize);
            list = scenicspotMapper.getScenicspotlistByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateByCode(Scenicspot scenicspot) throws Exception {
        scenicspotMapper.updateByCode(scenicspot);
    }

    @Override
    public void deleteByCode(String code) throws Exception {
        scenicspotMapper.deleteByCode(code);
    }

    @Override
    public List<ScenicspotCustom> getScenicspotAndDay(Integer pageIndex, Integer pageSize) throws Exception {
        PageHelper.startPage(pageIndex, pageSize);
        List<ScenicspotCustom> list = scenicspotMapper.getScenicspotAndDay();
        return list;
    }

    @Override
    public List<ScenicspotCustom> getScenicspotAndDay() throws Exception {
        List<ScenicspotCustom> list = scenicspotMapper.getScenicspotAndDay();
        return list;
    }

    @Override
    public List<ScenicspotCustom> getpeopleInfor(Integer pageIndex, Integer pageSize, ScenicspotCustom scenicspotCustom) throws Exception {

        PageHelper.startPage(pageIndex, pageSize);
        List<ScenicspotCustom> list = scenicspotMapper.getpeopleInfor(scenicspotCustom);
        return list;
    }

    @Override
    public void insertScenicspotList(List<Scenicspot> scenicspotlist) throws Exception {
        scenicspotMapper.insertScenicspotList(scenicspotlist);
    }

    @Override
    public Scenicspot getScenicspotById(Integer id) throws Exception {
        return scenicspotMapper.getScenicspotById(id);
    }


}
