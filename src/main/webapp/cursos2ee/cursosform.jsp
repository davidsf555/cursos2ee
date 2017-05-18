<!-- TODO extract common -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cursos2ee.model.Nivel" %>
<html>
    <head>
        <title>Cursos2ee</title>
        <link rel="stylesheet" href="/cursos2ee/pure-release-0.6.2/pure-min.css" />
    </head>
    <body>
        <h1>Nuevo curso</h1>
        <form class="pure-form-stacked" action="/cursos2ee/cursos" method="post">
            <ul>
                <li><Label for="titulo">Título</label><input type="text" name="titulo" id="titulo" /></li>
                <li><Label for="horas">Horas</label><input type="number" name="horas" id="horas" min="1" /></li>
                <li><label for="nivel">Nivel</label><select id="nivel" name="nivel">
                    <%for(Nivel nivel:Nivel.values()){%>
                        <option value="<%=nivel.getCodigo()%>"><%=nivel.getDescripcion()%></option>
                    <%}%>
                </select></li>
                <li><label for="activo">Activo</label><input id="activo" name="activo" type="checkbox" value="true" /></li>
            <ul>
            <input class="pure-button pure-button-primary" type="submit" value="Enviar">
            <a class="pure-button pure-button-seconfary" href="/cursos2ee/cursos">Cancelar</a>
        </form>
    </body>
</html>