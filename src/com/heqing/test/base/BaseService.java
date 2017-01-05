package com.heqing.test.base;

import java.io.Serializable;  
import java.lang.reflect.InvocationTargetException;  
import java.util.List;  
  
@SuppressWarnings("hiding")  
public interface BaseService<T, Serializable> {  
      
    public int insert(T record);  
    public int insertSelective(T record);  
    public T selectByPrimaryKey(String id);  
    public int updateByPrimaryKey(T record);  
    public int updateByPrimaryKeySelective(T record);  
    public int deleteByPrimaryKey(String id);  
    public List<T> findTop(int top, String statementKey, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;  
    public T findTopOne(String statementKey, Object parameter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;  
}  
