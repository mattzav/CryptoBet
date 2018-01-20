<%@ page contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<script src="js/modernizr-2.6.2.min.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body style="background-image: url(images/newmessi.jpg);">
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
									<a href="login"><input type="button" class="btn btn-primary" value=LOG-OUT onclick="<c:set var="page" value="index.jsp" scope="session"  />"></a>
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
								<li><a href="contact.html">Informazioni su di noi</a></li>
							</ul>
							</nav>
					</div>
				</div>
				</header>
			</div>
			<!-- end:fh5co-header -->
			<div class="fh5co-hero">
				<div class="fh5co-cover" data-stellar-background-ratio="0.5">
					<div class="desc animate-box">
						<div class="container ">
							<div class="row">	
								<div class="col-sm-2">
								</div>
								<div class="col-sm-6">
									<h2>
										<a href="scommetti">Bet &amp; Win</a><br> <b>Unisciti
											a CryptoBet</b>
									</h2>
								</div>
								<div class="col-sm-4">
									<c:if test="${loggato==null}">
										<form method="post" action="login">
											<div class="form-group">
												<label for="user"> Username: </label> 
												<input type="text" class="form-control" name="user">
											</div>
											<div class="form-group">
												<label for="pwd"> Password: </label> <input type="password"
													class="form-control" name="pwd">
											</div>
											<div>
												<label class="col-sm-8 label" for="admin"> Sei un
													Admin? </label> <span class="col-sm-4"><input
													type="checkbox" name="admin"></span>
											</div>
											<div class="form-group">
												<span class="col-sm-6"><input class="btn btn-primary" type="submit" name="accesso" value="Accedi" onclick="<c:set var="page" value="index.jsp" scope="session"/>" /></span> 
												<span class="col-sm-6"> <a class="btn btn-primary" href="sendData" onclick="<c:set var="page" value="index.jsp" scope="session"/>">Registrati</a></span>
											</div>
										</form>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- end:fh5co-hero -->



	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.easing.1.3.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/slideShow.min.js"></script>
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/jquery.stellar.min.js"></script>
	<script src="js/hoverIntent.js"></script>
	<script src="js/superfish.js"></script>

	<script src="js/main.js"></script>

</body>
</html>

