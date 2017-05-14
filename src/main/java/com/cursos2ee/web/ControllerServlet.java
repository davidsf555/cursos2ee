package com.cursos2ee.web;

import com.cursos2ee.model.Curso;
import com.cursos2ee.model.Nivel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 13/05/2017.
 */
public class ControllerServlet extends HttpServlet {

    //FIXME connection pull
    private Connection conn;

    private static final String CURSOS_TABLE = "CURSOS";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            connectionToDerby();
        } catch (SQLException e) {
            System.err.println("Error on data base connection");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error on data base connection");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String create = req.getParameter("create");
        if (create != null && create.equalsIgnoreCase("true")) {
            getServletContext().getRequestDispatcher("/cursos2ee//cursosForm.jsp").forward(req, resp);
        } else {
            List<Curso> cursos = new ArrayList<>();
            req.setAttribute("cursos", cursos);
            //FIXME to dao
            try {
                Statement statement = conn.createStatement();
                boolean existCursosTable = conn.getMetaData().getTables(null, null, CURSOS_TABLE, new String[]{"TABLE"}).next();
                if (existCursosTable) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM "+CURSOS_TABLE+" WHERE activo=true");
                    while (resultSet.next()) {
                        cursos.add(new Curso(
                                resultSet.getString("titulo"),
                                resultSet.getInt("horas"),
                                Nivel.getByCode(resultSet.getInt("nivel")),
                                resultSet.getBoolean("activo")
                        ));
                    }
                    resultSet.close();
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getServletContext().getRequestDispatcher("/cursos2ee/list.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
        final Curso newCurso =new Curso(
                req.getParameter("titulo"),
                Integer.parseInt(req.getParameter("horas")),
                Nivel.getByCode(Integer.parseInt(req.getParameter("nivel"))),
                Boolean.parseBoolean(req.getParameter("activo"))
        );
        try {
            Statement statement = conn.createStatement();
            boolean existCursosTable = conn.getMetaData().getTables(null, null, CURSOS_TABLE, new String[]{"TABLE"}).next();
            if (!existCursosTable) {
                System.out.println("no existe");
                String sql = "CREATE TABLE " + CURSOS_TABLE + " (titulo VARCHAR(500),activo BOOLEAN,nivel SMALLINT, horas INTEGER)";
                System.out.println(sql);
                statement.executeUpdate(sql);
            }
            int inserted = statement.executeUpdate(String.format("INSERT INTO %s (titulo,horas,nivel,activo) values('%s',%s,%d,%b  )", CURSOS_TABLE, newCurso.getTitulo(), newCurso.getHoras(), newCurso.getNivel().getCodigo(), newCurso.isActivo()));
            System.out.println("insertado "+inserted);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        doGet(req,resp);
    }

    //FIXME to dao
    private void connectionToDerby() throws SQLException {
        String dbUrl = "jdbc:derby:CURSOS2eeDB;create=true";
        conn = DriverManager.getConnection(dbUrl);
    }
}
