package cn.jnx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.jnx.bean.Gqsj;
import cn.jnx.bean.Member;

@Mapper 
public interface SaveExcelDao {
	@Insert("")
	public void insertMemberBatch(List<Member> list);
	@Insert("")
	public void insertMapBatch(List<Map<Integer, String>> datas);
	
    public void insertGqsjBatch(@Param("list")List<Gqsj> datas);
}
