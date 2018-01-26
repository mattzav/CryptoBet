<%@ page contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="no-js">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CryptoBet</title>

<link rel="shortcut icon" href="favicon.ico">

<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700,900'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/icomoon.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/superfish.css">
<link rel="stylesheet" href="css/style.css">


<script src="js/modernizr-2.6.2.min.js"></script>
<script src="js/gestisciEsiti.js"> </script>

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
								<a href="index.jsp">Crypto<span>Bet</span></a>
							</h1>
							<!-- START #fh5co-menu-wrap -->
							<nav id="fh5co-menu-wrap" role="navigation">
								<ul class="sf-menu" id="fh5co-primary-menu">
									<li><a href="index.jsp">Home</a></li>
									<li><a href="scommetti">Scommesse</a></li>
									<li><a href="MioConto.jsp">Il Mio Conto</a></li>
									<li><a href="gestisciPartite.jsp"> Gestisci Partite</a></li>
									<li><a href="SquadreCampionati.jsp">Tutte le squadre</a></li>
									<li><a href="InformazioniSuDiNoi.html">Su di noi</a></li>
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
			<div class="container">
				<div class="row">
					<div class="col-sm-4">
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
													value="${campionato.nome}" name="campionato">
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-sm-1"></div>
					<div class="col-sm-7">
						<c:forEach items="${campionatiAttivi}" var="campionato">
							<div class="scroll-table">
								<table class="table table-responsive" id="esitiasd">
									<tr class="danger">
										<th>${campionato}</th>
										<c:forEach items="${esiti}" var="esitoOrdinato">
											<th>${esitoOrdinato}</th>
										</c:forEach>
										<th></th>
									</tr>
									<c:forEach items="${partiteAttive}" var="partita">
										<c:if test="${partita.campionato.nome==campionato}">
											<tr class="info">
												<td>${partita.squadra_casa.nome} vs
													${partita.squadra_ospite.nome}</td>
												<c:forEach items="${esiti}" var="esitoOrdinato">
													<c:forEach items="${esitiAttivi}" var="esito">
														<c:if
															test="${partita.codice==esito.partita.codice && esitoOrdinato==esito.esito.descrizione}">
															<td><c:if test="${esito.disponibile}">
																	<input class="btn btn-info btn-xs" type="submit"
																		name="${partita.codice};${esito.esito.descrizione}"
																		value="${esito.quota}"
																		onclick="abilitaEsito('${partita.codice};${esito.esito.descrizione}')">
																</c:if> <c:if test="${!esito.disponibile}">
																	<input class="btn btn-danger btn-xs" type="submit"
																		name="${partita.codice};${esito.esito.descrizione}"
																		value="${esito.quota}"
																		onclick="abilitaEsito('${partita.codice};${esito.esito.descrizione}')">
																</c:if></td>
														</c:if>
													</c:forEach>
												</c:forEach>
												<td class="partitaInModifica${partita.codice}">
													<button
														onclick="selezionaPartitaDaModificare(${partita.codice},false);"
														class="btn btn-default btn-xs">
														<span class="glyphicon glyphicon-pencil"></span>
													</button>
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<footer>
				<div id="footer">
					<div class="container">
						<div class="row">
							<div class="col-md-4 animate-box">
								<h3 class="section-title">Su di noi</h3>
								<p>
									Entra nel mondo <strong>CryptoBet</strong>, il miglior sito di
									scommesse online, e segui la tua passione per il calcio.
								</p>
								<p>
									<em>"Un uomo deve fare almeno una scommessa al giorno,
										altrimenti potrebbe andare in giro, essere fortunato e non
										saperlo mai."</em>
								</p>


							</div>
							<div class="col-md-4"></div>
							<div class="col-md-4 animate-box">
								<h3 class="section-title">I nostri contatti</h3>
								<ul class="contact-info">
									<li><i class="icon-map-marker"></i>Dipartimento di
										Matematica e Informatica, Università Della Calabria. <br>Ponte
										Pietro Bucci, 87036 Quattromiglia CS</li>
									<li><i class="icon-phone"></i>+ 345 1234 567</li>
									<li><i class="icon-envelope"></i><a href="#">cryptoBetTeam@unical.it</a></li>
									<li><i class="icon-globe2"></i><a href="#">www.CryptoBet.com</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</footer>

		</div>
		<!-- END fh5co-page -->

	</div>
	<!-- END fh5co-wrapper -->



	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.easing.1.3.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/jquery.stellar.min.js"></script>
	<script src="js/hoverIntent.js"></script>
	<script src="js/superfish.js"></script>

	<script src="js/main.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			window.location.href = "#esitiasd";
		});
	</script>
</body>
</html>

