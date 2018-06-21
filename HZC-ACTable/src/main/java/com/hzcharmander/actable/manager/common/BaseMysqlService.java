package com.hzcharmander.actable.manager.common;

import com.hzcharmander.actable.command.PageResultCommand;

import java.util.List;


public interface BaseMysqlService {

	/**
	 * 保存，如果主键有值则进行更新操作
	 * @param <T> model类型
	 * @param t 要保存的model类型数据
	 * @return id 操作数据的id
	 */
	<T> Long save(T t);
	
	/**
	 * 根据传入对象非空的条件删除
	 * @param <T> model类型
	 * @param t 要删除的model类型数据
	 */
	<T> void delete(T t);
	
	/**
	 * 根据传入对象非空的条件进行查询
	 * @param <T> model类型
	 * @param t 要查询的model类型数据
	 */
	<T> PageResultCommand<T> query(T t);

	/**
	 * 根据传入对象的key 查询
	 * @param <T> model类型，只用到key
	 * @param t 要查询的model类型数据
	 */
	<T> T queryByKey(T t);
}
