<%-- 
    Document   : lister
    Created on : 22 mars 2022, 08:54:26
    Author     : RAKOTONDRAINIBE
--%>

<%@page import="modele.Categorie"%>
<%@page import="modele.Produit"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% List lpro=(List)request.getAttribute("lpro");
List lcat=(List)request.getAttribute("lcat");
List lcats=(List)request.getAttribute("lcats");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Liste plat </h1>
        <form action="listercat.do" method="get">
            <input type="hidden" name="requestArgumentMethod" value="true">
            <select name="categorie">
               <option value="1">Soupe</option>
                <option value="2">Vary</option>
                 <option value="3">MinSao</option>
              <option value="4">dessert</option>
             </select>
            <input type="submit" value="ok">
         </form>
        <% if(lcat!= null && lcat.size()>0){ Categorie cenc=(Categorie) lcat.get(0);%>
       <%= cenc.getNom() %>
        <%}%>!
        <%   for(int i=0;i<lpro.size();i++){
                Produit enc=(Produit) lpro.get(i); %>
        <p><%= enc.getNom() %> , Prix :<%= enc.getPrixUnitaire() %></p>
        <% } %>
    </body>
</html>
