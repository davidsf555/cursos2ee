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

            boolean existCursosTable = conn.getMetaData().getTables(null, null, "cursos", new String[]{"TABLE"}).last();
            if (!existCursosTable) {
                statement.executeUpdate("Create table cursos (id int primary key AUTO_INCREMENT, nombre varchar(30))");
            }
            statement.executeUpdate("insert into cursos (nombre) values ('Mi nombre')");

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
        String dbUrl = "jdbc:derby:cursos2ee;create=true";
        conn = DriverManager.getConnection(dbUrl);
    }
}
