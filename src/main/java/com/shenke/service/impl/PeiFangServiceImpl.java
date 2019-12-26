package com.shenke.service.impl;

import com.shenke.entity.PeiFang;
import com.shenke.entity.PeiFangInfo;
import com.shenke.entity.PeiFangShou;
import com.shenke.repository.PeiFangRepository;
import com.shenke.repository.PeifangShouRepository;
import com.shenke.service.PeiFangInfoService;
import com.shenke.service.PeiFangService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("peiFangService")
public class PeiFangServiceImpl implements PeiFangService {

    @Resource
    private PeiFangRepository peiFangRepository;

    @Resource
    private PeiFangInfoService peiFangInfoService;

    @Resource
    private PeifangShouRepository peifangShouRepository;

    @Override
    public List<PeiFang> findAll() {
        return peiFangRepository.findAll();
    }

    @Override
    public boolean add(PeiFang peiFang) {
        List<PeiFang> peiFang1 = peiFangRepository.findByName(peiFang.getName());
        if (peiFang1.size() > 0) {
            return false;
        }
        peiFangRepository.save(peiFang);
        return true;
    }

    @Override
    public List<PeiFang> findByName(String s) {
        return peiFangRepository.findByLikeName(s);
    }

    @Override
    public void delete(Integer id) {
        System.out.println(id);
        List<PeiFangInfo> peiFangInfos = peiFangInfoService.findByPeiFangId(id);
        if (peiFangInfos.size() > 0) {
            peiFangInfoService.delete(peiFangInfos);
            peiFangRepository.delete(id);
        } else {
            peiFangRepository.delete(id);
        }
    }

    @Override
    public List<PeiFangShou> findByinformNumber(Long infromNumber) {
        return peifangShouRepository.findByInfornNumber(infromNumber);
    }
}
