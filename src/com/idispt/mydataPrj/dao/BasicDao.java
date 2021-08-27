package com.idispt.mydataPrj.dao;

import java.util.List;

import com.idispt.mydataPrj.vo.BasicVo;

public interface BasicDao {
    List<BasicVo> getCustInfo(final String ci);
}
