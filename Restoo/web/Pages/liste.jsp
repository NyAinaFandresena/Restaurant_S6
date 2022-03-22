<%@page import="modele.Categorie"%>
<%@page import="modele.Produit"%>
<%@page import="java.util.List"%>
<% List lpro=(List)request.getAttribute("lpro");
List lcat=(List)request.getAttribute("lcat");
List lcats=(List)request.getAttribute("lcats");%>

<section id="banner">
    <div class="content">
        <% if(lcat!= null && lcat.size()>0){ Categorie cenc=(Categorie) lcat.get(0);%>
        <header>
            <h1><%= cenc.getNom() %>
            </h1>
            <p>Liste des <%= cenc.getNom() %> disponibles</p>
        </header>
        <%}
        else{%>
        <header>
            
            <p>Aucun produit de cette catégorie n'est disponibles</p>
        </header>
        <%}%>
    </div>
    
</section>

<section>
    <div class="posts">
        <%   for(int i=0;i<lpro.size();i++){
            Produit enc=(Produit) lpro.get(i); %>
        <article>
            <a href="#" class="image"><img src="images/pic01.jpg" alt="" /></a>
            <h3><%= enc.getNom() %></h3>
            Prix :<%= enc.getPrixUnitaire() %>
            <ul class="actions">
                <li><a href="#" class="button">More</a></li>
            </ul>
        </article>
        <% } %>
    </div>
</section>


