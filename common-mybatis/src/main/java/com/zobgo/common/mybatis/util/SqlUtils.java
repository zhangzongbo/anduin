package com.zobgo.common.mybatis.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/11/2.
 */
@Slf4j
public class SqlUtils {

    public static String in(List<Object> ids){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(0");
        for (Object id : ids) {
            stringBuilder.append(",").append(id);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
    /**
     * 生成sql需要的in参数
     *
     * @param ids
     * @return
     */
    public static String in(Collection<Long> ids) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(0");
        for (Long id : ids) {
            stringBuilder.append(",").append(id);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    /**
     * 生成sql需要的in参数
     *
     * @param ids
     * @return
     */
    public static String inStringList(Collection<String> ids) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("('");
        for (String id : ids) {
            stringBuilder.append("','").append(id);
        }
        stringBuilder.append("')");
        return stringBuilder.toString();
    }

    /**
     * 为sql添加where子句
     *
     * @param sql
     * @param where
     * @return
     */
    public static String addWhere(String sql, String where) {
        if (StringUtils.isEmpty(where)) {
            return sql;
        }

        if (sql.toLowerCase().contains("where")) {
            int index = sql.toLowerCase().lastIndexOf("where");

            String first = sql.substring(0, index);

            String second = sql.substring(index + 5, sql.length());

            log.info("first = {}", first);

            log.info("second = {}", second);

            log.info("where = {}", where);

            String result = String.format("%s where %s and %s", first, where, second);

            log.info("result = {}", result);

            return result;
        }
        return sql;
    }

    public static void main(String[] args) {
        log.info(SqlUtils.addWhere("select * from ca_permission where 1 = 1", "2 = 2"));
    }
}
