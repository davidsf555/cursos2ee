package com.cursos2ee.web;

import com.cursos2ee.controller.Controller;
import com.cursos2ee.controller.ControllerCursoImpl;
import com.cursos2ee.controller.ControllerResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller server
 * Manage application resources and navigation
 * Created by usuario on 13/05/2017.
 */
public class CursoServlet extends HttpServlet {

    //TODO add error control
    //TODO extract abstract

    private Controller controller = ControllerCursoImpl.INSTANCE;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        ControllerResponse controllerResponse = controller.doGet(getParametersMap(req));

        fillParams(req, controllerResponse.getRequestParamas());

        if (controllerResponse.getView() != null) {
            getServletContext().getRequestDispatcher(controllerResponse.getView()).forward(req, resp);
        } else {
            doPost(req, resp);
        }

    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        ControllerResponse controllerResponse = controller.doPost(getParametersMap(req));

        fillParams(req, controllerResponse.getRequestParamas());

        if (controllerResponse.getView() != null) {
            getServletContext().getRequestDispatcher(controllerResponse.getView()).forward(req, resp);
        }else {
            doGet(req, resp);
        }
    }

    /**
     * Extract a map of parameter from request in map key,value
     * TODO multivalue params
     * @param req
     * @return
     */
    private Map<String,String> getParametersMap(HttpServletRequest req){
        final Map<String,String> parameters = new HashMap<>();
        Map<String,String[]> parameterMap = req.getParameterMap();
        for(Map.Entry<String,String[]> entry: parameterMap.entrySet()){
            if(entry.getValue().length==1) {
                parameters.put(entry.getKey(), entry.getValue()[0]);
            }
            //TODO multi value parameters
        }
        return parameters;
    }

    /**
     * Put in request params all map content
     * @param req
     * @param paramsMap
     */
    private void fillParams(HttpServletRequest req, Map<String, Object> paramsMap) {
        for(Map.Entry<String,Object> entry:paramsMap.entrySet()){
                req.setAttribute(entry.getKey(), entry.getValue());
        }
    }
}

