package com.hzcharmander.actable.manager.common;

import java.lang.reflect.Field;
import java.util.*;

import com.hzcharmander.actable.annotation.Column;
import com.hzcharmander.actable.annotation.Table;
import com.hzcharmander.actable.command.PageResultCommand;
import com.hzcharmander.actable.command.SaveOrUpdateDataCommand;
import com.hzcharmander.actable.dao.common.BaseMysqlCRUDMapper;
import com.hzcharmander.actable.utils.AutoAddIdUtils;
import com.hzcharmander.actable.utils.DateUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("baseMysqlCRUDManager")
public class BaseMysqlServiceImpl implements BaseMysqlService {

    private static final Logger log = LoggerFactory.getLogger(BaseMysqlServiceImpl.class);

    private static final String KEYFIELDMAP = "keyFieldMap";

    @Autowired
    private BaseMysqlCRUDMapper baseMysqlCRUDMapper;

    public <T> Long save(T obj) {
        boolean isSave = true;
        Table table = obj.getClass().getAnnotation(Table.class);
        String tableName = ("".equals(table.name())) ? obj.getClass().getSimpleName() : table.name();
        Field[] declaredFields = getAllFields(obj);
        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        Map<String, Object> keyFieldMap = new HashMap<String, Object>();
        Long updateId = null;
        for (Field field : declaredFields) {
            try {
                // 私有属性需要设置访问权限
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }
                //修改，如果是主键，且查询本地表内，不存在该值
                if (column.isKey() && field.get(obj) != null && field.get(obj).toString().length() > 0) {
                    // 如果为更新
                    isSave = false;
                    keyFieldMap.put(field.getName(), field.get(obj));
                    updateId = new Long(field.get(obj).toString());
                }
                if (isSave && column.isAutoIncrement()) {
                    continue;
                }
                String columnName = ("".equals(column.name())) ? field.getName() : column.name();
                if (column.isUpdateTime() || (isSave && column.isNowTime())) {
                    dataMap.put(columnName, new DateUtils().toYMDHMS());
                } else if (isSave&&column.isKey()) {
                    updateId=AutoAddIdUtils.getInstance().nextId();
                    dataMap.put(columnName, updateId);
                } else
                    dataMap.put(columnName, field.get(obj));

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (isSave) {
            tableMap.put(tableName, dataMap);
            SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
            // 执行保存操作
            baseMysqlCRUDMapper.save(saveOrUpdateDataCommand);
            return updateId;
        } else {
            dataMap.put(KEYFIELDMAP, keyFieldMap);
            tableMap.put(tableName, dataMap);
            SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
            // 执行更新操作根据主键
            baseMysqlCRUDMapper.update(saveOrUpdateDataCommand);
            return updateId;
        }

    }

    private <T> Field[] getAllFields(T obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        // 递归扫描父类的filed
        declaredFields = recursionParents(obj.getClass(), declaredFields);
        return declaredFields;
    }

    public <T> void delete(T obj) {

        // 得到表名
        Table table = obj.getClass().getAnnotation(Table.class);
        String tableName = ("".equals(table.name())) ? obj.getClass().getSimpleName() : table.name();

        Field[] declaredFields = getAllFields(obj);
        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        for (Field field : declaredFields) {
            // 设置访问权限
            field.setAccessible(true);
            // 得到字段的配置
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }
            try {
                String columnName = ("".equals(column.name())) ? field.getName() : column.name();
                dataMap.put(columnName, field.get(obj));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        tableMap.put(tableName, dataMap);
        baseMysqlCRUDMapper.delete(tableMap);
    }

    @SuppressWarnings("unchecked")
    public <T> PageResultCommand<T> query(T obj) {
        String startKey = "start";
        String sizeKey = "pageSize";
        String currentPageKey = "currentPage";
        String orderFieldKey = "orderField";
        String sortKey = "sortStr";

        Integer startVal = null;
        Integer sizeVal = null;
        Integer currentPageVal = null;
        String orderFieldVal = null;
        String sortVal = null;
        PageResultCommand<T> pageResultCommand = new PageResultCommand<T>();
        // 得到表名
        Table table = obj.getClass().getAnnotation(Table.class);
        String tableName = ("".equals(table.name())) ? obj.getClass().getSimpleName() : table.name();
        Field[] declaredFields = getAllFields(obj);

        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        for (Field field : declaredFields) {
            try {
                // 设置访问权限
                field.setAccessible(true);
                // 获取分页start和size
                if (startKey.equals(field.getName())) {
                    startVal = (Integer) field.get(obj);
                }
                if (sizeKey.equals(field.getName())) {
                    sizeVal = (Integer) field.get(obj);
                }
                if (currentPageKey.equals(field.getName())) {
                    currentPageVal = (Integer) field.get(obj);
                }
                if (orderFieldKey.equals(field.getName())) {
                    orderFieldVal = (String) field.get(obj);
                }
                if (sortKey.equals(field.getName())) {
                    sortVal = (String) field.get(obj);
                }

                // 得到字段的配置
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }
                String columnName = ("".equals(column.name())) ? field.getName() : column.name();
                if (field.get(obj) instanceof String && field.get(obj) != null && "".equals(field.get(obj))) {
                    dataMap.put(columnName, null);
                } else {
                    dataMap.put(columnName, field.get(obj));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        tableMap.put(tableName, dataMap);
        if (currentPageVal != null && currentPageVal > 0) {
            tableMap.put(startKey, startVal);
            tableMap.put(sizeKey, sizeVal);
        }
        if (orderFieldVal != null && !orderFieldVal.equals("")) {
            tableMap.put(orderFieldKey, orderFieldVal);
            tableMap.put(sortKey, sortVal);
        }
        List<Map<String, Object>> query = baseMysqlCRUDMapper.query(tableMap);

        List<T> list = new ArrayList<T>();
        try {
            for (Map<String, Object> map : query) {
                T newInstance = (T) obj.getClass().newInstance();
//                Field[] declaredFields2 = getAllFields(obj.getClass());
                convertT(map, (T) newInstance, declaredFields);
                list.add(newInstance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (null != list) {
            pageResultCommand.setData(list);
            int queryCount = baseMysqlCRUDMapper.queryCount(tableMap);
            pageResultCommand.setRecordsFiltered(queryCount);
            pageResultCommand.setRecordsTotal(queryCount);
        }
        return pageResultCommand;
    }

    @Override
    public <T> T queryByKey(T obj) {
        Table table = obj.getClass().getAnnotation(Table.class);
        String tableName = ("".equals(table.name())) ? obj.getClass().getSimpleName() : table.name();
        //得到ID到值
        try {
            Field field = obj.getClass().getSuperclass().getField("id");
            Map<Object, Object> tableMap = new HashMap<Object, Object>();
            Map<Object, Object> dataMap = new HashMap<Object, Object>();
            Column column = field.getAnnotation(Column.class);
            String columnName = ("".equals(column.name())) ? field.getName() : column.name();
            dataMap.put(columnName, field.get(obj));
            tableMap.put(tableName, dataMap);
            List<Map<String, Object>> query = baseMysqlCRUDMapper.query(tableMap);
            if (query != null && query.size() > 0) {
                Map<String, Object> map = query.get(0);
                T newInstance = (T) obj.getClass().newInstance();
                Field[] declaredFields2 = getAllFields(obj);
                convertT(map, (T) newInstance, declaredFields2);
                return newInstance;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    //转换成t
    private <T> void convertT(Map<String, Object> map, T newInstance, Field[] declaredFields2) throws IllegalAccessException {
        for (Field fd : declaredFields2) {
            fd.setAccessible(true);
            // 得到字段的配置
            Column cl = fd.getAnnotation(Column.class);
            if (cl == null) {
                continue;
            }
            String clName = ("".equals(cl.name())) ? fd.getName() : cl.name();
            fd.set(newInstance, map.get(clName));
        }
    }

    //设置值

    /**
     * 递归扫描父类的fields
     *
     * @param clas
     * @param fields
     */
    @SuppressWarnings("rawtypes")
    private Field[] recursionParents(Class<?> clas, Field[] fields) {
        if (clas.getSuperclass() != null) {
            Class clsSup = clas.getSuperclass();
            fields = (Field[]) ArrayUtils.addAll(fields, clsSup.getDeclaredFields());
            recursionParents(clsSup, fields);
        }
        return fields;
    }

}
