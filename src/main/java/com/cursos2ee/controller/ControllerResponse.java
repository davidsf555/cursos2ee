package com.cursos2ee.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller exit data for a request
 * Created by usuario on 17/05/2017.
 */
public class ControllerResponse {

    private final Map<String,Object> requestParamas = new HashMap<String,Object>();
    private String view;

    public Map<String, Object> getRequestParamas() {
        return requestParamas;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
