package com.shenke.service;

import com.shenke.entity.PeiFang;
import com.shenke.entity.PeiFangShou;

import java.util.List;

public interface PeiFangService {

    List<PeiFang> findAll();

    boolean add(PeiFang peiFang);

    List<PeiFang> findByName(String s);

    void delete(Integer id);

    List<PeiFangShou> findByinformNumber(Long infromNumber);
}
