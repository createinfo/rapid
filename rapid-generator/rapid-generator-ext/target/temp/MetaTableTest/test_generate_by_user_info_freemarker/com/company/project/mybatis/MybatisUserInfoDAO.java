/*
 * Alipay.com Inc.
 * Copyright (c) 2010 All Rights Reserved.
 */
 
package com.company.project.mybatis;

import com.company.project.query.*;

import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;

import com.iwallet.biz.common.util.PageList;
import com.iwallet.biz.common.util.money.Money;
import com.company.project.dataobject.UserInfoDO;
import com.company.project.daointerface.UserInfoDAO;

/**
 * UserInfoDAO
 * database table: USER_INFO
 */
public class MybatisUserInfoDAO extends BaseIbatis3Dao implements UserInfoDAO {


	/**
	 * 根据订单号查询订单
	 * sql: 
	 * <pre>
	 UPDATE
        USER_INFO 
    SET
        USERNAME = ? ,          PASSWORD = ? ,          BIRTH_DATE = ? ,          SEX = ? ,          AGE = ?          
    WHERE
        USER_ID = ?
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public int update(String username ,String password ,java.sql.Date birthDate ,Byte sex ,Integer age ,Long userId) throws DataAccessException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username",username);
		param.put("password",password);
		param.put("birthDate",birthDate);
		param.put("sex",sex);
		param.put("age",age);
		param.put("userId",userId);
		return getSqlSessionTemplate().update("UserInfo.update", param);
	}

	/**
	 * 根据订单号查询订单
	 * sql: 
	 * <pre>
	 DELETE 
    FROM
        USER_INFO 
    WHERE
        USER_ID = ?
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public int delete(Long userId) throws DataAccessException {
		return getSqlSessionTemplate().delete("UserInfo.delete", userId);
	}

	/**
	 * 根据订单号查询订单
	 * sql: 
	 * <pre>
	 SELECT
        USER_ID ,USERNAME ,PASSWORD ,BIRTH_DATE ,SEX ,AGE                 
    FROM
        USER_INFO           
    WHERE
        USER_ID = ?
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public UserInfo getById(Long userId) throws DataAccessException {
		return (UserInfo)getSqlSessionTemplate().selectOne("UserInfo.getById",userId);
	}

	/**
	 * 根据订单号查询订单
	 * sql: 
	 * <pre>
	 select
        r.role_id,sum(r.user_id) sum_user_id 
    from
        role r        
    inner join
        role_permission rp 
            on r.role_id = rp.role_id       
    left join
        blog b 
            on b.user_id = r.user_id      
    where
        r.role_id = ?        
        and r.role_name = ?       
        and b.content like ?       
        and r.modified between ? and ?       
    group by
        r.role_id
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public PageList testGroupByPaging(Long roleId,String roleName,String content,java.sql.Date modifiedBegin,java.sql.Date modifiedEnd,int pageSize,int pageNo) throws DataAccessException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("roleId",roleId);
		param.put("roleName",roleName);
		param.put("content",content);
		param.put("modifiedBegin",modifiedBegin);
		param.put("modifiedEnd",modifiedEnd);
		return (PageList)PageQueryUtils.pageQuery(getSqlSessionTemplate(),"UserInfo.testGroupByPaging",param,pageNo,pageSize);
	}

	/**
	 * 根据订单号查询订单
	 * sql: 
	 * <pre>
	 select
        r.role_id,sum(r.user_id) sum_user_id 
    from
        role r           
    inner join
        role_permission rp 
            on r.role_id = rp.role_id       
    left join
        blog b 
            on b.user_id = r.user_id      
    where
        r.role_id = ?        
        and r.role_name = ?       
        and b.content like ?       
        and r.modified between ? and ?       
    group by
        r.role_id               
    UNION
    select
        r.role_id,sum(r.user_id) sum_user_id 
    from
        role r              
    inner join
        role_permission rp 
            on r.role_id = rp.role_id       
    left join
        blog b 
            on b.user_id = r.user_id      
    where
        r.role_id = ?        
        and r.role_name = ?       
        and b.content like ?       
        and r.modified between ? and ?       
    group by
        r.role_id               
    UNION
    ALL      select
        r.role_id,sum(r.user_id) sum_user_id 
    from
        role r              
    inner join
        role_permission rp 
            on r.role_id = rp.role_id       
    left join
        blog b 
            on b.user_id = r.user_id      
    where
        r.role_id = ?        
        and r.role_name = ?       
        and b.content like ?       
        and r.modified between ? and ?       
    group by
        r.role_id               MINUS       select
            r.role_id,sum(r.user_id) sum_user_id 
        from
            role r              
        inner join
            role_permission rp 
                on r.role_id = rp.role_id       
        left join
            blog b 
                on b.user_id = r.user_id      
        where
            r.role_id = ?        
            and r.role_name = ?       
            and b.content like ?       
            and r.modified between ? and ?       
        group by
            r.role_id               EXCEPT        select
                r.role_id,sum(r.user_id) sum_user_id 
            from
                role r              
            inner join
                role_permission rp 
                    on r.role_id = rp.role_id       
            left join
                blog b 
                    on b.user_id = r.user_id      
            where
                r.role_id = ?        
                and r.role_name = ?       
                and b.content like ?       
                and r.modified between ? and ?       
            group by
                r.role_id               INTERSECT         select
                    r.role_id,sum(r.user_id) sum_user_id 
                from
                    role r              
                inner join
                    role_permission rp 
                        on r.role_id = rp.role_id       
                left join
                    blog b 
                        on b.user_id = r.user_id      
                where
                    r.role_id = ?        
                    and r.role_name = ?       
                    and b.content like ?       
                    and r.modified between ? and ?       
                group by
                    r.role_id
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public PageList testSetOperations(Long roleId,String roleName,String content,java.sql.Date modifiedBegin,java.sql.Date modifiedEnd,int pageSize,int pageNo) throws DataAccessException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("roleId",roleId);
		param.put("roleName",roleName);
		param.put("content",content);
		param.put("modifiedBegin",modifiedBegin);
		param.put("modifiedEnd",modifiedEnd);
		return (PageList)PageQueryUtils.pageQuery(getSqlSessionTemplate(),"UserInfo.testSetOperations",param,pageNo,pageSize);
	}

	/**
	 * 根据订单号查询订单
	 * sql: 
	 * <pre>
	 select
        r.role_id,sum(r.user_id) sum_user_id 
    from
        role r        
    inner join
        role_permission rp 
            on r.role_id = rp.role_id       
    left join
        blog b 
            on b.user_id = r.user_id      
    where
        r.role_id = ?        
        and r.role_name = ?       
        and b.content like ?       
        and r.modified between ? and ?                    
        and r.role_name in (
            ?
        )                              
        and r.role_name in (
            ?
        )                    
        and r.role_name not in (
            ?
        )                              
        and r.role_name = ?                    
        and r.role_name = ?                    
        and r.role_name = ?                                             
        and r.role_id = ?                        
        and r.role_id = ?                        
        and r.role_id = ?              
    group by
        r.role_id
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public PageList testOpenClose(TestOpenCloseQuery param) throws DataAccessException {
		return (PageList)PageQueryUtils.pageQuery(getSqlSessionTemplate(),"UserInfo.testOpenClose",param);
	}

	/**
	 * 根据订单号查询订单
	 * sql: 
	 * <pre>
	 select
        r.role_id,sum(r.user_id) sum_user_id 
    from
        role r        
    inner join
        role_permission rp 
            on r.role_id = rp.role_id       
    left join
        blog b 
            on b.user_id = r.user_id      
    where
        r.role_id = ?        
        and r.role_name = ?       
        and b.content like ?       
        and r.modified between ? and ?          
        and r.role_name in (
            ?         
        )           
    group by
        r.role_id
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public PageList testOpenClose_with_list(Long roleId,String roleName,String content,java.sql.Date modifiedBegin,java.sql.Date modifiedEnd,Long r,int pageSize,int pageNo) throws DataAccessException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("roleId",roleId);
		param.put("roleName",roleName);
		param.put("content",content);
		param.put("modifiedBegin",modifiedBegin);
		param.put("modifiedEnd",modifiedEnd);
		param.put("r",r);
		return (PageList)PageQueryUtils.pageQuery(getSqlSessionTemplate(),"UserInfo.testOpenClose_with_list",param,pageNo,pageSize);
	}

}
