<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cursos2ee.model.Curso" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="cursos" type="List<Curso>" scope="request" />
<html>
    <head>
        <title>Cursos2ee</title>
        <link rel="stylesheet" href="/cursos2ee/pure-release-0.6.2/pure-min.css" />
    </head>
    <body>
        <h1>Cursos</h1>
        <table class="pure-table pure-table-horizontal">
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
        <p>
            <a class="pure-button pure-button-primary" href="/cursos2ee/cursos?create=true">Nuevo curso</a>
        </p>
    </body>
</html>