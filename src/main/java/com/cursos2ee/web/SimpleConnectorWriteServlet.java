package com.cursos2ee.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by usuario on 13/05/2017.
 */
public class SimpleConnectorWriteServlet extends HttpServlet {

    private static final String CURSOS_TABLE = "CURSOS";
    Connection conn;

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
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Statement statement = conn.createStatement();

            boolean existCursosTable = conn.getMetaData().getTables(null, null, CURSOS_TABLE, new String[]{"TABLE"}).next();
            if (!existCursosTable) {
                statement.executeUpdate("Create table "+CURSOS_TABLE+" (id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), nombre varchar(30))");
            }
            int inserted = statement.executeUpdate("insert into "+CURSOS_TABLE+" (nombre) values ('Mi nombre')");

            System.out.println("Inserted:"+inserted);

            statement.close();
        } catch (SQLException e) {
            System.err.println("Error with data base stament");
            e.printStackTrace();
        }


        PrintWriter out = resp.getWriter();
        out.println("He escrito un nombre");
        out.flush();
        out.close();
    }

    private void connectionToDerby() throws SQLException {
        String dbUrl = "jdbc:derby:cursos2eeDB;create=true";
        conn = DriverManager.getConnection(dbUrl);
    }
}
