<%-- 
    Document   : template
    Created on : 22 mars 2022, 18 h 57 min 06 s
    Author     : Manoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<!--
	Editorial by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<% 
    String included = String.valueOf(request.getAttribute("#included")) ; 
    String file = included.concat(".jsp") ;
%>
<html>
	<head>
		<title>Editorial by HTML5 UP</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
	</head>
	<body class="is-preload">

		<!-- Wrapper -->
			<div id="wrapper">

				<!-- Main -->
					<div id="main">
						<div class="inner">

							<!-- Header -->
								

							<!-- Banner -->
							

							<!-- Section -->
							<jsp:include page="<%= file %>" />
							<!-- Section -->
								
						</div>
					</div>

				<!-- Sidebar -->
					<div id="sidebar">
						<div class="inner">

							<!-- Search -->
								

							<!-- Menu -->
								<nav id="menu">
									<header class="major">
										<h2>Menu</h2>
									</header>
									<ul>
										<li><a href="#">Liste des produits</a></li>
										<li>
											<span class="opener">Liste des produits disponibles</span>
											<ul>
												<li><a href="listercat.do?requestArgumentMethod=true&categorie=1">Soupe</a></li>
												<li><a href="listercat.do?requestArgumentMethod=true&categorie=2">Vary</a></li>
												<li><a href="listercat.do?requestArgumentMethod=true&categorie=3">Mine sao</a></li>
												<li><a href="listercat.do?requestArgumentMethod=true&categorie=4">Dessert</a></li>
											</ul>
										</li>
									</ul>
								</nav>

							<!-- Section -->
								
							<!-- Section -->
								

							<!-- Footer -->
								<footer id="footer">
									<p class="copyright">&copy; Untitled. All rights reserved. Demo Images: <a href="https://unsplash.com">Unsplash</a>. Design: <a href="https://html5up.net">HTML5 UP</a>.</p>
								</footer>

						</div>
					</div>

			</div>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/browser.min.js"></script>
			<script src="assets/js/breakpoints.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>

	</body>
</html>