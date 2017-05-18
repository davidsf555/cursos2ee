package com.cursos2ee.db;

import com.cursos2ee.model.Curso;

import java.util.List;

/**
 * Cuso persistence operations
 * Created by usuario on 16/05/2017.
 */
public interface CursoDAO {

    /**
     * Save curso to database, TODO duplicates control
     * @param curso
     */
    void save(Curso curso);

    /**
     * List of ACTIVE cursos in database alphabetical ordered
     * @return
     */
    List<Curso> findAllAlphabetical();

    //void findAllOrderBy(Map<String,Boolean> fieldsAsc)

    //void findAllPaginated(int offset,int size)

    //void findAllOrderByPaginated(Map<String,Boolean> fieldsAsc)
}
