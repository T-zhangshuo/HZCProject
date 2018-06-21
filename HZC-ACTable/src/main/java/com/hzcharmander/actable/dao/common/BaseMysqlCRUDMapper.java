package com.hzcharmander.actable.dao.common;

import java.util.List;
import java.util.Map;

import com.hzcharmander.actable.command.SaveOrUpdateDataCommand;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;



/**
 * 创建更新表结构的Mapper
 * @author sunchenbin
 *
 */
@Transactional
public interface BaseMysqlCRUDMapper {

	/**
	 * 保存
	 * @param saveOrUpdateDataCommand id+表结构的map
	 */
	public void save(SaveOrUpdateDataCommand saveOrUpdateDataCommand);
	
	/**
	 * 更新
	 * @param saveOrUpdateDataCommand id+表结构的map
	 */
	public void update(SaveOrUpdateDataCommand saveOrUpdateDataCommand);
	
	/**
	 * 删除
	 * @param tableMap 表结构的map
	 */
	public void delete(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);
	
	/**
	 * 查询
	 * @param tableMap 表结构的map
	 */
	public List<Map<String,Object>> query(@Param("tableMap") Map<Object, Object> tableMap);

	/**
	 * 查询的count
	 * @param tableMap 表结构的map
	 */
	public int queryCount(@Param("tableMap") Map<Object, Object> tableMap);

}
