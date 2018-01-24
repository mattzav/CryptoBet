<%@ page contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link href="css/utility.css" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" href="css/style.css">

<script src="js/modernizr-2.6.2.min.js"></script>
<script src="js/gestisciPartite.js"></script>

</head>
<body>
	<div id="fh5co-wrapper">
		<div id="fh5co-page">
			<div id="fh5co-header">
				<header id="fh5co-header-section">
					<c:if test="${loggato!=null}">
						<div class="info">
							<div class="col-sm-5">
								<span id="messaggio" class="btn col-sm-6">
									${mex}
 								</span> 
							</div>
							<span class="col-sm-5">
								<c:if test="${utente==\"USER\"}">
									<h2><a href="MioConto.jsp" class="btn">Saldo conto : ${loggato.conto.saldo}</a></h2>
								</c:if>
							</span> 
							<div class="col-sm-2">
								<span class="col-sm-6">
									<a href="login"><input type="button" class="btn btn-primary" value=LOG-OUT onclick="<c:set var="page" value="gestisciPartite.jsp" scope="session"  />"></a>
								</span>
							</div>
						</div>
					</c:if>
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
								<li><a href="InformazioniSuNoi.jsp">Informazioni su di noi</a></li>
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
								<c:if test="${utente==\"ADMIN\"}">
									<h1 class="text-center">Aggiorna il tuo sito</h1>
									<br />
									<p>Qui potrai modificare le quote relative alle partite
										disponibili o richiedere un aggiornamento completo dei tuoi
										dati</p>
								</c:if>
								<c:if test="${loggato==null}">
									<div>
										<br> <br> <br> <br>
										<div class="col-sm-6">
											<h1>Aggiorna Il Tuo Sito</h1>
										</div>
										<div class="col-sm-6">
											<form method="post" action="login">
												<div class="form-group">
													<label class="label" for="user"> Username: </label> <input
														type="text" class="form-control" name="user">
												</div>
												<div class="form-group">
													<label class="label" for="pwd"> Password: </label> <input
														type="password" class="form-control" name="pwd">
												</div>
												<div>
													<input name="admin" type="checkbox" checked="checked"
														style="display: none">
												</div>
												<div class="form-group">
													<input class="btn btn-primary" type="submit" name="accesso"
														value="Accedi"
														onclick="<c:set var="page" value="gestisciPartite.jsp" scope="session"/>" />
												</div>
											</form>
										</div>
									</div>
								</c:if>
								<c:if test="${utente==\"USER\"}">
									<div>
										<span class="btn btn-danger"> Errore: Effettua il login
											come amministratore</span>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end: fh5co-parallax -->
			<!-- end:fh5co-hero -->
			<div id="fh5co-team-section">
				<c:if test="${utente==\"ADMIN\"}">
					<div class="container">
						<div class="row">
							<div class="col-md-8 col-md-offset-2">
								<div class="heading-section text-center animate-box">
									<h2>Ecco le operazioni che il sito ti offre :</h2>
									<p>Scorri sui rettangoli che seguono per scoprirle</p>
								</div>
							</div>
						</div>
						<div class="row text-center">
							<div class="col-md-4 col-sm-6">
								<div class="team-section-grid animate-box"
									style="background-image: url(images/trainer-1.jpg);">
									<div class="overlay-section">
										<div id="aggiornaPartite">
											<div class="desc">
												<h3>Aggiornamento dati</h3>
												<p>Ti permette di ricevere gli ultimi aggiornamenti sulle
													partite attualmente disponibili</p>
												<c:if test="${loadingPartite==null}">
													<input type="button" class="btn btn-primary" id="partite" name="aggiorna"
														onclick="getPartite();" value="Aggiorna Partite"> <br>
												</c:if>
												<c:if test="${loadingPartite!=null}">
													<input type="button" class="btn btn-primary" id="partite" name="aggiorna"
														onclick="getPartite();" value="Aggiorna Partite" disabled="disabled"> <br>
												</c:if>
												<br> <br>
											</div>
											<c:if test="${loadingPartite!=null}">
												<div id="loadingPartite" class="loader"></div>
											</c:if>
										</div>	
									</div>
								</div>
							</div>
							<div class="col-md-4 col-sm-6">
								<div class="team-section-grid animate-box"
									style="background-image: url(images/trainer-2.jpg);">
									<div class="overlay-section">
										<div class="desc">
											<h3>Modifica le tue quote</h3>
											<p>Ti permette di abilitare, disabilitare o modificare le
												tue quote</p>
											<a class="btn btn-primary" href="aggiornaQuote"> Aggiorna
												Quote </a> <br> <br> <br> <br>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-4 col-sm-6">
								<div class="team-section-grid animate-box"
									style="background-image: url(images/trainer-3.jpg);">
									<div class="overlay-section">
										<div id="aggiornaSquadre">
											<div class="desc">
												<h3>Aggiorna Campionati e Squadre</h3>
												<p>Ti permette di ricevere gli ultimi aggiornamenti sulle
													squadre e i campionati attualmente disponibili</p>
												<c:if test="${loadingSquadre==null}">
													<input type="button" class="btn btn-primary" id="squadre" name="aggiorna"
														onclick="getSquadre();" value="Aggiorna"> <br>
												</c:if>
												<c:if test="${loadingSquadre!=null}">
													<input type="button" class="btn btn-primary" id="squadre" name="aggiorna"
														onclick="getSquadre();" value="Aggiorna" disabled="disabled"> <br>
												</c:if>
												<br>
											</div>
											<c:if test="${loadingSquadre!=null}">
												<div id="loadingSquadre" class="loader">
												</div>
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
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
								<li><i class="icon-map-marker"></i>Dipartimento	di Matematica e Informatica, Università Della Calabria.
								<br>Ponte Pietro Bucci, 87036 Quattromiglia CS</li>
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

</body>
</html>

