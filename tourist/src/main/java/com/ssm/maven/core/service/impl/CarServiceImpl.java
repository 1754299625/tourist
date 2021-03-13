package com.ssm.maven.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssm.maven.core.dao.ParkingCarMapper;
import com.ssm.maven.core.entity.CarCustom;
import com.ssm.maven.core.entity.ParkingCar;
import com.ssm.maven.core.entity.Scenicspot;
import com.ssm.maven.core.entity.TouristCustom;
import com.ssm.maven.core.service.CarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("CarService")
public class CarServiceImpl implements CarService {
    @Resource
    ParkingCarMapper parkingCarMapper;

    @Override
    public List<Scenicspot> getAllScenicspot() {
        List<Scenicspot> s = parkingCarMapper.getAllScenicspot();
        return s;
    }

    @Override
    public List<CarCustom> getAllSpotCar() {
        return parkingCarMapper.getAllSpotCar();
    }

    /**
     * 获取当前页面信息
     */
    @Override
    public List<CarCustom> getCurrentPage(Integer pageNum, Integer pageSize, CarCustom carCustom) {
        List<CarCustom> list = new ArrayList<>();
        try {
            PageHelper.startPage(pageNum, pageSize);
            list = parkingCarMapper.getAllSpotCar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询
     */
    @Override
    public List<CarCustom> findSpotCarByNameOrTime(Integer pageNum, Integer pageSize, CarCustom carCustom) {
        List<CarCustom> list = new ArrayList<>();
        try {
            PageHelper.startPage(pageNum, pageSize);
            list = parkingCarMapper.findSpotCarByNameOrTime(carCustom);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ParkingCar> findAllCarByCodeAndTime(ParkingCar parkingCar) throws Exception {
        return parkingCarMapper.findAllCarByCodeAndTime(parkingCar);
    }

    @Override
    public Integer getAllCarNumber(ParkingCar parkingCar) throws Exception {
        return parkingCarMapper.getAllCarNumber(parkingCar);
    }

    /**
     * 获取车辆第一页页面信息
     */
    @Override
    public List<ParkingCar> getFirstCar(Integer pageNum, Integer pageSize, ParkingCar parkingCar) {
        List<ParkingCar> list = new ArrayList<>();
        try {
            PageHelper.startPage(pageNum, pageSize);
            list = parkingCarMapper.findAllCarByCodeAndTime(parkingCar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 模糊查询车辆
     */
    public List<ParkingCar> findCar(Integer pageNum, Integer pageSize, ParkingCar parkingCar) {
        List<ParkingCar> list = new ArrayList<>();
        try {
            PageHelper.startPage(pageNum, pageSize);
            list = parkingCarMapper.findCar(parkingCar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 删除
     *
     * @param parkingCar
     */
    public void deleteByCodeAndTime(ParkingCar parkingCar) {
        try {
            parkingCarMapper.deleteByCodeAndTime(parkingCar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> searchCarHLCInfor(CarCustom carCustom) throws Exception {
        List<Integer> list = parkingCarMapper.searchCarHLCInfor(carCustom);
        return list;
    }

    @Override
    public Integer getCount(CarCustom carCustom) throws Exception {
        return parkingCarMapper.getCount(carCustom);
    }

    public void insertCarBatch(List<ParkingCar> parkingCarList) {
        try {
            parkingCarMapper.insertCarBatch(parkingCarList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CarCustom> getAllCar() {
        List<CarCustom> carCustomList = null;
        try {
            carCustomList = parkingCarMapper.getAllCar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carCustomList;
    }

    public Scenicspot getNumberByCode(int code) {
        Scenicspot scenicspot = new Scenicspot();
        try {
            scenicspot = parkingCarMapper.getNumberByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scenicspot;
    }

    public int getNumByCodeAndTime(ParkingCar parkingCar) {
        int i = 0;
        try {
            i = parkingCarMapper.getNumByCodeAndTime(parkingCar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
