package com.cursos2ee.controller;

import java.util.Map;

/**
 * Analyce params and return correct response
 * Isolate servlet code from business code
 * Created by usuario on 17/05/2017.
 */
public interface Controller {

    ControllerResponse doGet(Map<String,String> params);
    ControllerResponse doPost(Map<String,String> params);

}
