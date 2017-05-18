package com.cursos2ee.db;

import com.cursos2ee.model.Curso;
import com.cursos2ee.model.Nivel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cursos database access implementation with derby
 * Created by usuario on 16/05/2017.
 */
public class CursoDAODervyImpl implements CursoDAO {

    private static final String DB_URL = "jdbc:derby:CURSOS2eeDB;create=true";
    private static final String CURSOS_TABLE_NAME = "CURSOS";

    private static final String INSERT_CURSO_SQL = "INSERT INTO %s (titulo,horas,nivel,activo) values('%s',%s,%d,%b)";
    private static final String CREATE_CURSOS_TABLE_SQL = "CREATE TABLE %s (titulo VARCHAR(500),activo BOOLEAN,nivel SMALLINT, horas INTEGER)";
    //FIXME order semantic, not string value
    private static final String SELECT_ACTIVE_CURSOS_ORDERED_SQL = "SELECT * FROM %s WHERE activo=true ORDER BY titulo";

    private static final String TITULO_COLUMN_NAME = "titulo";
    private static final String HORAS_COLUMN_NAME = "horas";
    private static final String NIVEL_COLUMN_NAME = "nivel";
    private static final String ACTIVO_COLUMN_NAME = "activo";

    private static final String TABLE_DBOBJECT_TYPE = "TABLE";

    //FIXME connection pool
    private final Connection conn;

    public CursoDAODervyImpl() throws SQLException {
        super();
        conn = connectionToDerby();
        //FIXME close connection
    }

    //TODO input encoding to avoid sql injection

    @Override
    public void save(final Curso newCurso) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            final boolean existCursosTable = existTable(CURSOS_TABLE_NAME);

            if (!existCursosTable) {
                createTable(statement, CURSOS_TABLE_NAME);
            }
            statement.executeUpdate(String.format(INSERT_CURSO_SQL, CURSOS_TABLE_NAME, newCurso.getTitulo(), newCurso.getHoras(), newCurso.getNivel().getCodigo(), newCurso.isActivo()));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            tryToClose(statement);
        }
    }

    @Override
    public List<Curso> findAllAlphabetical() {
        final List<Curso> cursos = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            final boolean existCursosTable = existTable(CURSOS_TABLE_NAME);
            if (existCursosTable) {
                final String sql = String.format(SELECT_ACTIVE_CURSOS_ORDERED_SQL, CURSOS_TABLE_NAME);
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    cursos.add(new Curso(
                            resultSet.getString(TITULO_COLUMN_NAME),
                            resultSet.getInt(HORAS_COLUMN_NAME),
                            Nivel.getByCode(resultSet.getInt(NIVEL_COLUMN_NAME)),
                            resultSet.getBoolean(ACTIVO_COLUMN_NAME)
                    ));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            tryToClose(statement);
            tryToClose(resultSet);
        }
        return cursos;
    }

    private boolean existTable(final String tableName) throws SQLException {
        ResultSet tableResulset = null;
        try {
            tableResulset = conn.getMetaData().getTables(null, null, tableName, new String[]{TABLE_DBOBJECT_TYPE});
            return tableResulset.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            tryToClose(tableResulset);
        }
    }

    private Connection connectionToDerby() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void tryToClose(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createTable(final Statement statement, final String tableName) throws SQLException {
        final String sql = String.format(CREATE_CURSOS_TABLE_SQL, tableName);
        statement.executeUpdate(sql);
    }
}
