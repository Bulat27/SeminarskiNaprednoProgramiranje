
package rs.ac.bg.fon.nprog.server.repository;

import java.util.List;

/**
 *
 * @author Dragon
 * @param <T>
 */
public interface Repository<T> {
    
     public List<T> findRecords(T t, String whereCondition) throws Exception;
     public void insertRecord(T t) throws Exception;
     public void deleteRecord(T t) throws Exception;
     public void updateRecord(T t) throws Exception;
     public void deleteRecords(T t, String whereCondition) throws Exception;
}
