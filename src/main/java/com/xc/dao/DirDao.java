package com.xc.dao;

import com.xc.entity.DirInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public interface DirDao extends JpaRepository<DirInfo, String>{
}
