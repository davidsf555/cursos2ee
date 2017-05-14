package com.cursos2ee.web;

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
public class SimpleConnectorReadServlet extends HttpServlet {

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
        final List<String> nombres = new ArrayList<String>();
        try {
            Statement statement = conn.createStatement();

            boolean existCursosTable = conn.getMetaData().getTables(null, null, "CURSOS", new String[]{"TABLE"}).next();
            if (existCursosTable) {
                System.out.println("Existe");
                ResultSet resultSet = statement.executeQuery("select nombre from CURSOS");
                while(resultSet.next()){
                    nombres.add(resultSet.getString("nombre"));
                }
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error with data base stament");
            e.printStackTrace();
        }


        PrintWriter out = resp.getWriter();
        out.println("Nombres:");
        for(String nombre:nombres) out.println(nombre);
        out.flush();
        out.close();
    }

    private void connectionToDerby() throws SQLException {
        String dbUrl = "jdbc:derby:CURSOS2eeDB;create=true";
        conn = DriverManager.getConnection(dbUrl);
    }
}
