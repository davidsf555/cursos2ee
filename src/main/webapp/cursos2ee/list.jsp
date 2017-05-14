<%@ page import="com.cursos2ee.model.Curso" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="cursos" type="List<Curso>" scope="request" />
<html>
    <head>
        <title>Cursos2ee</title>
    </head>
    <body>
        <h1>Cursos</h1>
        <table>
            <tr>
                <th>T&iacute;tulo</th>
                <th>Nivel</th>
                <th>Horas</th>
            </tr>
            <tr>
        <%for(com.cursos2ee.model.Curso curso:cursos){%>
            <tr>
                <td><%=curso.getTitulo()%></td>
                <td><%=curso.getNivel().getDescripcion()%></td>
                <td><%=curso.getHoras()%></td>
            </tr>
        <%}%>
        </table>
        <a href="/cursos2ee/controller?create=true">Nuevo curso</a>
    </body>
</html>