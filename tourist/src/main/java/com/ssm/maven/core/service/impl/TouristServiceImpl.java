package com.ssm.maven.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssm.maven.core.dao.TouristMapper;
import com.ssm.maven.core.entity.ScenicspotCustom;
import com.ssm.maven.core.entity.Tourist;
import com.ssm.maven.core.entity.TouristCustom;
import com.ssm.maven.core.service.TouristService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("touristservice")
public class TouristServiceImpl implements TouristService {

    @Resource
    private TouristMapper touristMapper;

    @Override
    public void deleteTouristByCode(ScenicspotCustom scenicspotCustom) throws Exception {
        touristMapper.deleteTouristByCode(scenicspotCustom);
    }

    @Override
    public List<TouristCustom> getTouristList(TouristCustom touristCustom) throws Exception {
        return touristMapper.getTouristList(touristCustom);
    }

    @Override
    public List<TouristCustom> getTouristList(Integer pageIndex, Integer pageSize, TouristCustom touristCustom) throws Exception {
        PageHelper.startPage(pageIndex, pageSize);
        List<TouristCustom> list = touristMapper.getTouristList(touristCustom);
        return list;
    }

    @Override
    public List<TouristCustom> searchTouristInfor(Integer pageIndex, Integer pageSize, TouristCustom touristCustom) throws Exception {
        PageHelper.startPage(pageIndex, pageSize);
        List<TouristCustom> list = touristMapper.searchTouristInfor(touristCustom);
        return list;
    }

    @Override
    public List<TouristCustom> searchTouristInfor(TouristCustom touristCustom) throws Exception {
        List<TouristCustom> list = touristMapper.searchTouristInfor(touristCustom);
        return list;
    }

    @Override
    public List<Integer> searchHLCTouristInfor(TouristCustom touristCustom) throws Exception {
        List<Integer> list = touristMapper.searchHLCTouristInfor(touristCustom);
        return list;
    }


    @Override
    public void deleteByTouristCode(TouristCustom touristCustom) throws Exception {
        touristMapper.deleteByTouristCode(touristCustom);
    }

    @Override
    public void updateTouristInfor(Tourist tourist) throws Exception {
        touristMapper.updateTouristInfor(tourist);
    }

    @Override
    public void insertTouristBatch(List<Tourist> touristlist) throws Exception {
        touristMapper.insertTouristBatch(touristlist);
    }

    @Override
    public Integer getCount(TouristCustom touristCustom) throws Exception {
        return touristMapper.getCount(touristCustom);
    }

    @Override
    public int getNumByCodeAndTime(Tourist tourist) {
        int i = 0;
        try {
            i = touristMapper.getNumByCodeAndTime(tourist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

}
