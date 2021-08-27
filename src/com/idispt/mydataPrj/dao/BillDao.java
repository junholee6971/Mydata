package com.idispt.mydataPrj.dao;

import java.util.List;
import java.util.Map;

import com.idispt.mydataPrj.vo.InvChrgVo;;

public interface BillDao {
    List<InvChrgVo> getChrgByInvMonth(Map<String, Object> map);
}
