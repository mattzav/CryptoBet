<%@ page contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
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

<!-- 
	//////////////////////////////////////////////////////

	FREE HTML5 TEMPLATE 
	DESIGNED & DEVELOPED by FREEHTML5.CO
		
	Website: 		http://freehtml5.co/
	Email: 			info@freehtml5.co
	Twitter: 		http://twitter.com/fh5co
	Facebook: 		https://www.facebook.com/fh5co

	//////////////////////////////////////////////////////
	 -->

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

<script type="text/javascript">
	function getSquadre() {
		if(!($(".squadre").length)){

			var squadre = [];
			var campionati = [];
			$.ajax({
				headers : {
					'X-Auth-Token' : '9c8c10fb6ee545a5a46161e402d73dee'
				},
				url : 'http://api.football-data.org/v1/competitions/?season=2017',
				dataType : 'json',
				type : 'GET',
				async : false,
			}).done(function(response) {
				$.each(response, function(index, item) {
					campionati.push(item.id);
					campionati.push(item.caption);
					var _url = item._links.teams.href;
					$.ajax({
						headers : {
							'X-Auth-Token' : '9c8c10fb6ee545a5a46161e402d73dee'
						},
						url : _url,
						dataType : 'json',
						type : 'GET',
						async : false,
					}).done(function(_response) {
						$.each(_response, function(i, _item) {
							if (i == "teams") {
								$.each(_item, function(_i, item2) {
									squadre.push(item2.name);
								});
							}
						});
					});
				});
			});
			var result_squadre="";
			var result_campionati="";
			$.each(squadre,function(i,item){
				result_squadre=result_squadre.concat(item);
				result_squadre=result_squadre.concat(";");
			});
			
			$.each(campionati,function(i,item){
				result_campionati=result_campionati.concat(item);
				if(i%2 == 0)
					result_campionati=result_campionati.concat(":");
				else
					result_campionati=result_campionati.concat(";");
			});
			$.ajax({
				url : 'aggiornaDati',
				type : 'POST',
				data : {
					squadre: result_squadre,
					campionati: result_campionati,
					aggiorna: "Aggiorna"
				},
				error:function(){
					alert("error");
				}
			});
		}
	
	}
	
	function getPartite() {
		if(!($(".partite").length)){

			var data = new Date();
			var data_inizio = new Date(data.getTime()-1000*60*60*24*7);
			var data_inizio_string = data_inizio.getFullYear()+"-"+(data_inizio.getMonth()+1)+"-"+data_inizio.getDate();
			if(data_inizio_string[7] != '-'){
				data_inizio_string=data_inizio_string.substr(0,5)+"0"+data_inizio_string.substr(5,4);
			}
			if(data_inizio_string.length != 10){
				data_inizio_string=data_inizio_string.substr(0,7)+"0"+data_inizio_string[8];
			}
			alert(data_inizio_string);
			var data_fine = new Date(data.getTime()+1000*60*60*24*7);
			var data_fine_string = data_fine.getFullYear()+"-"+(data_fine.getMonth()+1)+"-"+data_fine.getDate();
			if(data_fine_string[7] != '-'){
				data_fine_string=data_fine_string.substr(0,5)+"0"+data_fine_string.substr(5,4);
			}
			if(data_fine_string.length != 10){
				data_fine_string=data_fine_string.substr(0,8)+"0"+data_fine_string[8];
			}
			alert(data_fine_string);
			var _url = 'http://api.football-data.org/v1/fixtures/?timeFrameStart='+data_inizio_string+'&timeFrameEnd='+data_fine_string;
			
			var partite = [];
			$.ajax({
				headers : {
					'X-Auth-Token' : '9c8c10fb6ee545a5a46161e402d73dee'
				},
				url : _url,
				dataType : 'json',
				type : 'GET',
				async : false,
			}).done(function(response) {
				$.each(response, function(index, item) {
					if(index == "fixtures"){
						$.each(item, function(index2,item2){
							var str = item2._links.competition.href;
							partite.push(str.substr(str.length-3,str.length)+"£"+item2.homeTeamName+"£"+item2.awayTeamName+"£"+item2.result.goalsHomeTeam+"£"+item2.result.goalsAwayTeam+"£"+item2.status+"£"+item2.date);
						});
					
					}
					
					
				});

			});
			var result="";
			$.each(partite,function(i,item){
				result=result.concat(item);
				result=result.concat(";");
			});
			alert(result);
			$(".updatePartite").append("<input type=\"text\" style=\" display:none\" class=\"partite\" name=\"partite\" value=\""+result+"\">");
		}
	}
	</script>

</head>
<body>
	<jsp:useBean id = "esito" class = "model.Esito" /> 
	<div id="fh5co-wrapper">
		<div id="fh5co-page">
			<div id="fh5co-header">
				<header id="fh5co-header-section">
					<c:if test="${loggato!=null}">
						<div>
							<span class="col-sm-8"></span> <span class="col-sm-4">
								${mex} <a href="login"><input type="button"
									class="btn btn-primary" value=LOG-OUT
									onclick="<c:set var="page" value="gestisciPartite.jsp" scope="session"  />"></a>
							</span>
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
									<li><a class="fh5co-sub-ddown">Scommesse</a>
										<ul class="fh5co-sub-menu campionati">

										</ul></li>
									<li><a href="MioConto.jsp">Il Mio Conto</a></li>
									<li><a href="gestisciPartite.jsp"> Gestisci Partite</a></li>
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
								<c:if test="${utente==\"ADMIN\"}">
									<h1 class="text-center">Aggiorna il tuo sito</h1>
									<br />
									<p>Qui potrai modificare le quote relative alle partite
										disponibili o richiedere un aggiornamento completo dei tuoi
										dati</p>
								</c:if>
								<c:if test="${loggato==null}">
									<div>
										<br> 
										<br>
										<br> 
										<br>
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
													<a class="btn btn-primary" href="Registrati.html">Registrati</a>
												</div>
											</form>
										</div>
									</div>
								</c:if>
								<c:if test="${utente==\"USER\"}">
									<div>
										<span class="btn btn-danger"> Errore: Effuttua il login
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
										<div class="desc">
											<h3>Aggiornamento dati</h3>
											<p>Ti permette di ricevere gli ultimi aggiornamenti sulle
												partite attualmente disponibili</p>
											<form class="updatePartite" action="aggiornaDati" method="post">
												<input type="submit" class="btn btn-primary" name="aggiorna"
													onmouseover="getPartite();" value="Aggiorna Partite">
											</form>
											<br> <br> <br>
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
												<a class="btn btn-primary" href="aggiornaQuote"> Aggiorna Quote </a>
											<br> <br> <br> <br>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-4 col-sm-6">
								<div class="team-section-grid animate-box"
									style="background-image: url(images/trainer-3.jpg);">
									<div class="overlay-section">
										<div class="desc">
											<h3>Aggiorna Campionati e Squadre</h3>
											<p>Ti permette di ricevere gli ultimi aggiornamenti sulle
												squadre e i campionati attualmente disponibili</p>
											<form class="updateDati" action="aggiornaDati" method="post">
												<input type="submit" class="btn btn-primary" name="aggiorna"
													onmouseover="getSquadre();" value="Aggiorna">
											</form>
											<br> <br>
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

