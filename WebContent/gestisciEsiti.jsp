<%@ page contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="no-js">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Fitness &mdash; 100% Free Fully Responsive HTML5 Template
	by FREEHTML5.co</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
<meta name="keywords"
	content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
<meta name="author" content="FREEHTML5.CO" />

<!-- Facebook and Twitter integration -->
<meta property="og:title" content="" />
<meta property="og:image" content="" />
<meta property="og:url" content="" />
<meta property="og:site_name" content="" />
<meta property="og:description" content="" />
<meta name="twitter:title" content="" />
<meta name="twitter:image" content="" />
<meta name="twitter:url" content="" />
<meta name="twitter:card" content="" />

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
<link rel="shortcut icon" href="favicon.ico">

<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700,900'
	rel='stylesheet' type='text/css'>

<!-- Animate.css -->
<link rel="stylesheet" href="css/animate.css">
<!-- Icomoon Icon Fonts-->
<link rel="stylesheet" href="css/icomoon.css">
<!-- Bootstrap  -->
<link rel="stylesheet" href="css/bootstrap.css">
<!-- Superfish -->
<link rel="stylesheet" href="css/superfish.css">

<link rel="stylesheet" href="css/style.css">


<!-- Modernizr JS -->
<script src="js/modernizr-2.6.2.min.js"></script>
<!-- FOR IE9 below -->
<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

</head>
<body>
	<div id="fh5co-wrapper">
		<div id="fh5co-page">
			<div id="fh5co-header">
				<header id="fh5co-header-section">
					<div class="container">
						<div class="nav-header">
							<a href="#" class="js-fh5co-nav-toggle fh5co-nav-toggle"><i></i></a>
							<h1 id="fh5co-logo">
								<a href="index.html">Crypto<span>Bet</span></a>
							</h1>
							<!-- START #fh5co-menu-wrap -->
							<nav id="fh5co-menu-wrap" role="navigation">
								<ul class="sf-menu" id="fh5co-primary-menu">
									<li><a href="index.html">Home</a></li>
									<li class="active"><a href="classes.html"
										class="fh5co-sub-ddown">Classes</a>
										<ul class="fh5co-sub-menu">
											<li><a href="left-sidebar.html">Web Development</a></li>
											<li><a href="right-sidebar.html">Branding &amp;
													Identity</a></li>
											<li><a href="#" class="fh5co-sub-ddown">Free HTML5</a>
												<ul class="fh5co-sub-menu">
													<li><a
														href="http://freehtml5.co/preview/?item=build-free-html5-bootstrap-template"
														target="_blank">Build</a></li>
													<li><a
														href="http://freehtml5.co/preview/?item=work-free-html5-template-bootstrap"
														target="_blank">Work</a></li>
													<li><a
														href="http://freehtml5.co/preview/?item=light-free-html5-template-bootstrap"
														target="_blank">Light</a></li>
													<li><a
														href="http://freehtml5.co/preview/?item=relic-free-html5-template-using-bootstrap"
														target="_blank">Relic</a></li>
													<li><a
														href="http://freehtml5.co/preview/?item=display-free-html5-template-using-bootstrap"
														target="_blank">Display</a></li>
													<li><a
														href="http://freehtml5.co/preview/?item=sprint-free-html5-template-bootstrap"
														target="_blank">Sprint</a></li>
												</ul></li>
											<li><a href="#">UI Animation</a></li>
											<li><a href="#">Copywriting</a></li>
											<li><a href="#">Photography</a></li>
										</ul></li>

									<li><a href="schedule.html">Schedule</a></li>
									<li><a href="trainer.html">Trainers</a></li>
									<li><a href="about.html">About</a></li>
									<li><a href="contact.html">Contact</a></li>
								</ul>
							</nav>
						</div>
					</div>
				</header>
			</div>
			<!-- end:fh5co-header -->
			<div class="fh5co-parallax"
				style="background-image: url(images/home-image-3.jpg);"
				data-stellar-background-ratio="0.5">
				<div class="overlay"></div>
				<div class="container">
					<div class="row">
						<div
							class="col-md-8 col-md-offset-2 col-sm-12 col-sm-offset-0 col-xs-12 col-xs-offset-0 text-center fh5co-table">
							<div class="fh5co-intro fh5co-table-cell animate-box">
								<h1 class="text-center">Gestisci le tue quote</h1>
								<p>Qui potrai gestire le tue quote apportando le modifiche
									che più desideri!</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end: fh5co-parallax -->
			<!-- end:fh5co-hero -->
			<!-- 
			<div class="container">
				<div class="row">
					<div class="col-sm-1">
						<br>
						<br>
						<br>
						<span class="label label-default"> Ecco i tuoi Campionati</span>
						<br>
						<br>
						<br>
						<ul class="nav navbar-nav">
							<c:forEach items="${campionati}" var="campionato">
								<li>
								
									<form method="post" action="aggiornaQuote">
										<input class="btn btn-primary" type="submit"
											value="${campionato.nome}" name="campionato">
									</form>
											<br>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="col-sm-4">
					</div>
					<div class="col-sm-7">
						<ul class="nav navbar-nav">
							<c:forEach items="${campionatiAttivi}" var="campionato">
								<li>
									<div>
									<span class="btn btn-danger"> ${campionato}</span>
									<ul class="nav navbar-nav">
										<c:forEach items="${partiteAttive}" var="partita">
											<c:if test="${partita.campionato.nome==campionato}">
												<li>
													<div>
														<span class="btn"> ${partita.squadra_casa.nome} vs ${partita.squadra_ospite.nome} </span>
														<nav role="navigation">
															<ul class="nav navbar-nav">
																<c:forEach items="${esitiAttivi}" var="esito">
																	<c:if test="${partita.codice==esito.partita.codice}">
																		<li>
																			<form method="post" action="aggiornaQuote">
																				<input class="btn btn-primary" type="submit" value="${esito.esito.descrizione}" name="esito">
																				<span> ${esito.quota}</span>
																			</form>
																		</li>
																	</c:if>		
																</c:forEach>
															</ul>
														</nav>
													</div>
												</li>
											</c:if>
										</c:forEach>
									</ul>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			 -->
			<div class="container">
				<div class="row">
					<div class="col-sm-2">
						<table class="table table-responsive">
							<thead>
								<tr>
									<th>Campionati Disponibili</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${campionati}" var="campionato">
									<tr class="success">
										<td>
											<form method="post" action="aggiornaQuote">
												<input class="btn btn-primary btn-xs" type="submit"
													value="${campionato.nome}" name="campionato" >
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-sm-2"></div>
					<div class="col-sm-8">
						<c:forEach items="${campionatiAttivi}" var="campionato">
							<table class="table table-responsive">
								<tr class="danger">
									<th>${campionato}</th>
									<c:forEach items="${esiti}" var="esitoOrdinato">
										<th> ${esitoOrdinato} </th>
									</c:forEach>
									<th></th>
								</tr>
								<c:forEach items="${partiteAttive}" var="partita">
									<c:if test="${partita.campionato.nome==campionato}">
										<tr class="info">
											<td>
												${partita.squadra_casa.nome} vs ${partita.squadra_ospite.nome} 
											</td>
											<c:forEach items="${esiti}" var="esitoOrdinato">
												<c:forEach items="${esitiAttivi}" var="esito">
													<c:if test="${partita.codice==esito.partita.codice && esitoOrdinato==esito.esito.descrizione}">
														<td>
															<form method="post" action="aggiornaQuote">
																<c:if test="${esito.disponibile}">
																	<input class="btn btn-info btn-xs" type="submit" name="${partita.codice};${esito.esito.descrizione}" value="${esito.quota}">
																</c:if>
																<c:if test="${!esito.disponibile}">
																	<input class="btn btn-danger btn-xs" type="submit" name="${partita.codice};${esito.esito.descrizione}" value="${esito.quota}">
																</c:if>
															</form>
														</td>
													</c:if>		
												</c:forEach>
											</c:forEach>
											<td class="glyphicon glyphicon-pencil"></td>
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</c:forEach>
					</div>
				</div>
			</div>
			<footer>
				<div id="footer">
					<div class="container">
						<div class="row">
							<div class="col-md-4 animate-box">
								<h3 class="section-title">About Us</h3>
								<p>Far far away, behind the word mountains, far from the
									countries Vokalia and Consonantia, there live the blind texts.
									Separated they live in Bookmarksgrove right at the coast of the
									Semantics.</p>
							</div>

							<div class="col-md-4 animate-box">
								<h3 class="section-title">Our Address</h3>
								<ul class="contact-info">
									<li><i class="icon-map-marker"></i>198 West 21th Street,
										Suite 721 New York NY 10016</li>
									<li><i class="icon-phone"></i>+ 1235 2355 98</li>
									<li><i class="icon-envelope"></i><a href="#">info@yoursite.com</a></li>
									<li><i class="icon-globe2"></i><a href="#">www.yoursite.com</a></li>
								</ul>
							</div>
							<div class="col-md-4 animate-box">
								<h3 class="section-title">Drop us a line</h3>
								<form class="contact-form">
									<div class="form-group">
										<label for="name" class="sr-only">Name</label> <input
											type="name" class="form-control" id="name" placeholder="Name">
									</div>
									<div class="form-group">
										<label for="email" class="sr-only">Email</label> <input
											type="email" class="form-control" id="email"
											placeholder="Email">
									</div>
									<div class="form-group">
										<label for="message" class="sr-only">Message</label>
										<textarea class="form-control" id="message" rows="7"
											placeholder="Message"></textarea>
									</div>
									<div class="form-group">
										<input type="submit" id="btn-submit"
											class="btn btn-send-message btn-md" value="Send Message">
									</div>
								</form>
							</div>
						</div>
						<div class="row copy-right">
							<div class="col-md-6 col-md-offset-3 text-center">
								<p class="fh5co-social-icons">
									<a href="#"><i class="icon-twitter2"></i></a> <a href="#"><i
										class="icon-facebook2"></i></a> <a href="#"><i
										class="icon-instagram"></i></a> <a href="#"><i
										class="icon-dribbble2"></i></a> <a href="#"><i
										class="icon-youtube"></i></a>
								</p>
								<p>
									Copyright 2016 Free Html5 <a href="#">Fitness</a>. All Rights
									Reserved. <br>Made with <i class="icon-heart3"></i> by <a
										href="http://freehtml5.co/" target="_blank">Freehtml5.co</a> /
									Demo Images: <a href="https://unsplash.com/" target="_blank">Unsplash</a>
								</p>
							</div>
						</div>
					</div>
				</div>
			</footer>


		</div>
		<!-- END fh5co-page -->

	</div>
	<!-- END fh5co-wrapper -->

	<!-- jQuery -->


	<script src="js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="js/jquery.waypoints.min.js"></script>
	<!-- Stellar -->
	<script src="js/jquery.stellar.min.js"></script>
	<!-- Superfish -->
	<script src="js/hoverIntent.js"></script>
	<script src="js/superfish.js"></script>

	<!-- Main JS (Do not remove) -->
	<script src="js/main.js"></script>

</body>
</html>

