<%-- 
    Document   : lister
    Created on : 22 mars 2022, 08:54:26
    Author     : RAKOTONDRAINIBE
--%>

<%@page import="modele.Produit"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% List lpro=(List)request.getAttribute("lpro"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Liste plat!</h1>
        <%             for(int i=0;i<lpro.size();i++){
                Produit enc=(Produit) lpro.get(i); %>
        <p><%= enc.getNom() %> , Prix :<%= enc.getPrixUnitaire() %></p>
        <% } %>
    </body>
</html>
