package com.cursos2ee.controller;

import com.cursos2ee.db.CursoDAO;
import com.cursos2ee.db.CursoDAODervyImpl;
import com.cursos2ee.model.Curso;
import com.cursos2ee.model.Nivel;

import java.sql.SQLException;
import java.util.Map;

/**
 * Generate resources necesary for curso business and prepare datas for response
 * TODO error control
 * Created by usuario on 17/05/2017.
 */
public enum ControllerCursoImpl implements Controller {
    INSTANCE;

    private static final String FORM_VIEW = "/cursos2ee/cursosForm.jsp";
    private static final String LIST_VIEW = "/cursos2ee/list.jsp";

    private static final String CREATE_PARAM = "create";
    private static final String TITULO_PARAM = "titulo";
    private static final String HORAS_PARAM = "horas";
    private static final String NIVEL_PARAM = "nivel";
    private static final String ACTIVO_PARAM = "activo";
    private static final String CURSOS_PARAM = "cursos";

    private CursoDAO cursoDAO;

    ControllerCursoImpl(){
        try {
            cursoDAO = new CursoDAODervyImpl();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ControllerResponse doGet(Map<String, String> params) {
        final boolean create = parseBooleanParam(params.get(CREATE_PARAM));
        final ControllerResponse response = new ControllerResponse();

        if (create) {
            response.setView(FORM_VIEW);
        } else {
            //FIXME
            response.getRequestParamas().put(CURSOS_PARAM, cursoDAO.findAllAlphabetical());
            response.setView(LIST_VIEW);
        }
        return response;
    }

    @Override
    public ControllerResponse doPost(Map<String, String> params) {

        final Curso newCurso =new Curso(
                params.get(TITULO_PARAM),
                Integer.parseInt(params.get(HORAS_PARAM)),
                Nivel.getByCode(Integer.parseInt(params.get(NIVEL_PARAM))),
                Boolean.parseBoolean(params.get(ACTIVO_PARAM))
        );

        cursoDAO.save(newCurso);

        return new ControllerResponse();
    }

    private boolean parseBooleanParam(final Object create) {
        return create != null && Boolean.parseBoolean((String) create);
    }
}
