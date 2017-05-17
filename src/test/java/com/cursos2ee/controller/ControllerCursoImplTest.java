package com.cursos2ee.controller;

import com.cursos2ee.db.CursoDAO;
import com.cursos2ee.model.Curso;
import com.cursos2ee.model.Nivel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by usuario on 17/05/2017.
 */
public class ControllerCursoImplTest extends Mockito{

    private static final String TITULO_TEST = "test";
    private static final int HORAS_TEST = 5;
    private static final boolean ACTIVO_TEST = true;
    private static final Nivel NIVEL_TEST = Nivel.basico;
    private static Curso CURSO_VISIBLE = new Curso(TITULO_TEST, HORAS_TEST, NIVEL_TEST, ACTIVO_TEST);

    private CursoDAO cursoDAOMock;

    @Before
    public void init(){
        cursoDAOMock = mock(CursoDAO.class);
        ControllerCursoImpl.INSTANCE.setDAO(cursoDAOMock);
    }

    /**
     * Call Get without parameters
     */
    @Test
    public void viewListTest(){

        //Stub mock
        when(cursoDAOMock.findAllAlphabetical()).thenReturn(Arrays.asList(new Curso[]{CURSO_VISIBLE}));

        //Call
        ControllerResponse controllerResponse = ControllerCursoImpl.INSTANCE.doGet(new HashMap<>());

        //Asserts
        assertEquals("/cursos2ee/list.jsp",controllerResponse.getView());
        List<Curso> cursos = (List<Curso>)controllerResponse.getRequestParamas().get("cursos");
        assertNotNull(cursos);
        assertEquals(1,cursos.size());
        assertEquals(CURSO_VISIBLE,cursos.get(0));
    }

    @Test
    public void viewEmptyListTest(){
        //Stub mock
        when(cursoDAOMock.findAllAlphabetical()).thenReturn(Arrays.asList(new Curso[]{}));

        //Call
        ControllerResponse controllerResponse = ControllerCursoImpl.INSTANCE.doGet(new HashMap<>());

        //Asserts
        assertEquals("/cursos2ee/list.jsp",controllerResponse.getView());
        List<Curso> cursos = (List<Curso>)controllerResponse.getRequestParamas().get("cursos");
        assertNotNull(cursos);
        assertEquals(0,cursos.size());

    }

    @Test
    public void viewFormTest(){
        //Call
        HashMap<String, String> params = new HashMap<>();
        params.put("create","true");
        ControllerResponse controllerResponse = ControllerCursoImpl.INSTANCE.doGet(params);

        //Asserts
        assertEquals("/cursos2ee/cursosForm.jsp",controllerResponse.getView());
        List<Curso> cursos = (List<Curso>)controllerResponse.getRequestParamas().get("cursos");
        assertEquals(null,cursos);
    }

    public void newCurso(){

        //Test params
        HashMap<String, String> params = new HashMap<>();
        params.put("titulo",TITULO_TEST);
        params.put("horas",String.valueOf(HORAS_TEST));
        params.put("activo",String.valueOf(ACTIVO_TEST));
        params.put("nivle",String.valueOf(NIVEL_TEST.getCodigo()));

        //Stub
        when(cursoDAOMock.findAllAlphabetical()).thenReturn(Arrays.asList(new Curso[]{CURSO_VISIBLE}));

        //Call
        ControllerResponse controllerResponse = ControllerCursoImpl.INSTANCE.doPost(params);
        //Assert
        verify(cursoDAOMock).save(eq(CURSO_VISIBLE));

        assertEquals("/cursos2ee/list.jsp",controllerResponse.getView());
        List<Curso> cursos = (List<Curso>)controllerResponse.getRequestParamas().get("cursos");
        assertNotNull(cursos);
        assertEquals(1,cursos.size());
        assertEquals(CURSO_VISIBLE,cursos.get(0));
    }

    //TODO errors
}
