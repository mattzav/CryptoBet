<%@ page contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
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

<script src="js/jquery.min.js"></script>
<script src="js/jquery.easing.1.3.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.waypoints.min.js"></script>
<script src="js/jquery.stellar.min.js"></script>
<script src="js/hoverIntent.js"></script>
<script src="js/superfish.js"></script>
<script src="js/SquadreCampionati.js"></script>
<script src="js/main.js"></script>
<script src="js/modernizr-2.6.2.min.js"></script>
<script>
	$(document).ready(function() {
		caricaScudettiIniziali();
	});
</script>

</head>
<body>
	<div id="fh5co-wrapper">
		<div id="fh5co-page">
			<div id="fh5co-header">
				<header id="fh5co-header-section">
					<c:if test="${loggato!=null}">
						<div class="info">
							<div class="col-sm-5">
								<span id="messaggio" class="btn col-sm-6"> ${mex} </span>
							</div>
							<span class="col-sm-5"> <c:if test="${utente==\"USER\"}">
									<h2>
										<a href="MioConto.jsp" class="btn">Saldo conto :
											${loggato.conto.saldo}</a>
									</h2>
								</c:if>
							</span>
							<div class="col-sm-2">
								<span class="col-sm-6"> <a href="login"><input
										type="button" class="btn btn-primary" value=LOG-OUT
										onclick="<c:set var="page" value="SquadreCampionati.jsp" scope="session" />"></a>
								</span>
							</div>
						</div>
					</c:if>
					<div class="container">
						<div class="nav-header">
							<a href="#" class="js-fh5co-nav-toggle fh5co-nav-toggle"><i></i></a>
							<h1 id="fh5co-logo">
								<a href="index.html">Crypto<span>Bet</span></a>
							</h1>
							<!-- START #fh5co-menu-wrap -->
							<nav id="fh5co-menu-wrap" role="navigation">
								<ul class="sf-menu" id="fh5co-primary-menu">
									<li><a href="index.jsp">Home</a></li>
									<li><a href="scommetti">Scommesse</a></li>
									<li><a href="MioConto.jsp">Il Mio Conto</a></li>
									<li><a href="gestisciPartite.jsp"> Gestisci Partite</a></li>
									<li><a href="SquadreCampionati.jsp">Tutte le squadre</a></li>
									<li><a href="InformazioniSuDiNoi.html">Informazioni su di
											noi</a></li>
								</ul>
							</nav>
						</div>
					</div>
				</header>
			</div>
			<!-- end:fh5co-header -->
			<div class="fh5co-parallax"
				style="background-image: url(images/home-image-4.jpg);"
				data-stellar-background-ratio="0.5">
				<div class="overlay"></div>
				<div class="container">
					<div class="row">
						<div
							class="col-md-8 col-md-offset-2 col-sm-12 col-sm-offset-0 col-xs-12 col-xs-offset-0 text-center fh5co-table">
							<div class="fh5co-intro fh5co-table-cell animate-box">
								<h1 class="text-center">
									<br><br>
									<strong>CryptoBet</strong>
								</h1>
								<br> <br>
								<p>
									Qui troverai tutte le squadre disponibili sul nuovo sito di
									scommesse fatto apposta per <strong>TE</strong>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end: fh5co-parallax -->
			<!-- end:fh5co-hero -->
			<div id="fh5co-team-section">
				<div class="container">
					<div>
						<div>
							<button
								class="previous disabled btn btn-xs btn-success glyphicon glyphicon-chevron-left"
								type="button" value="PREVIOUS" name="previous"
								onclick="caricaScudetti(true);"></button>
							<label class="page"> </label>
							<button
								class="next btn btn-xs btn-success glyphicon glyphicon-chevron-right"
								type="button" value="NEXT" name="next"
								onclick="caricaScudetti(false);"></button>

						</div>
						<div>
							<div class="col-sm-7">
								<table class="table">
									<tr class="warning">
										<th colspan="1">Squadra:</th>
										<th colspan="1">Scudetto:</th>
									</tr>
									<tbody class="tabellaScudetti">
									</tbody>
								</table>
							</div>
							<div class="col-sm-1"></div>
							<div class="embed-responsive embed-responsive-16by9 col-sm-4">
								<iframe class="embed-responsive-item"
									src="http://www.gazzetta.it/"></iframe>
							</div>
						</div>

					</div>

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

	<!-- jQuery -->

</body>
</html>

