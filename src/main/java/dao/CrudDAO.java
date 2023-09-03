package dao;

import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    public List<T> getAll() throws Exception;

    public boolean add(T entity) throws Exception;

    public boolean update(T entity) throws Exception;

    public boolean exist(String id) throws Exception;

    public String generateNewID() throws Exception;

    public boolean delete(String id) throws Exception;

    public T search(String id) throws Exception;
}
