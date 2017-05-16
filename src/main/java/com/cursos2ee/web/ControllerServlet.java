package com.cursos2ee.web;

import com.cursos2ee.db.CursoDAO;
import com.cursos2ee.db.CursoDAODervyImpl;
import com.cursos2ee.model.Curso;
import com.cursos2ee.model.Nivel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller server
 * Manage application resources and navigation
 * Created by usuario on 13/05/2017.
 */
public class ControllerServlet extends HttpServlet {

    private static final String FORM_VIEW = "/cursos2ee/cursosForm.jsp";
    private static final String LIST_VIEW = "/cursos2ee/list.jsp";

    private static final String CREATE_PARAM = "create";
    private static final String TITULO_PARAM = "titulo";
    private static final String HORAS_PARAM = "horas";
    private static final String NIVEL_PARAM = "nivel";
    private static final String ACTIVO_PARAM = "activo";
    private static final String CURSOS_PARAM = "cursos";
    private CursoDAO cursoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            cursoDAO = new CursoDAODervyImpl();
        } catch (SQLException e) {
            System.err.println("Error on data base connection");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final boolean create = parseBooleanParam(req.getParameter(CREATE_PARAM));

        String fowartdTo = "";
        if (create) {
            fowartdTo= FORM_VIEW;
        } else {
            req.setAttribute(CURSOS_PARAM, cursoDAO.findAllAlphabetical());
            fowartdTo= LIST_VIEW;
        }

        getServletContext().getRequestDispatcher(fowartdTo).forward(req, resp);

    }

    private boolean parseBooleanParam(String create) {
        return create != null && Boolean.parseBoolean(create);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final Curso newCurso =new Curso(
                req.getParameter(TITULO_PARAM),
                Integer.parseInt(req.getParameter(HORAS_PARAM)),
                Nivel.getByCode(Integer.parseInt(req.getParameter(NIVEL_PARAM))),
                Boolean.parseBoolean(req.getParameter(ACTIVO_PARAM))
        );

        cursoDAO.save(newCurso);

        doGet(req,resp);
    }
}
