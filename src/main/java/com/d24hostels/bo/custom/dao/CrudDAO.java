package com.d24hostels.bo.custom.dao;
import java.util.List;

public interface CrudDAO<T,ID> extends SuperDAO {
    public List<T> getAll() throws Exception ;

    public void save(T entity) throws Exception ;

    public void update(T entity) throws Exception ;

    public void delete(ID id) throws Exception ;

    public T search(ID id) throws Exception ;
}
