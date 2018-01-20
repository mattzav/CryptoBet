/**
 * 
 */
	

	//notifica alla servlet l'aggiunta o la rimozione di un errore
	function notificaErrore(errore, nome){
			$.ajax({
				url : 'sendData',
				type:'POST',
				data : nome+"="+errore,
	            cache:false,
	            error:function(){alert('error');},
				async:false
			});
		}
		
	//controlla che il nome non contenga numeri
		function controllaNome(){
			
			var valore=$("#name").val();
			var errore= valore.match(/\d+/g);
			if (errore != null || valore=="") {
				$("#nameError").css('display','block');
				notificaErrore("true","erroreName");
			}
			else{
				$("#nameError").css('display','none');
				notificaErrore("false","erroreName");
			}
			
		}
		
		//controlla che il cognome non contenga numeri
		function controllaCognome(){
			
			var valore=$("#surname").val();
			var errore= valore.match(/\d+/g);
			if (errore != null || valore=="") {
				$("#surnameError").css('display','block');
				notificaErrore("true","erroreSurname");
			}
			else{
				$("#surnameError").css('display','none');
				notificaErrore("false","erroreSurname");
			}
		}
		
		//che il formato della carta sia del tipo XXXX XXXX XXXX XXXX 
		//e che non sia già registrata sul db
		function controllaCartaDiCredito(){
			var valore=$("#creditCard").val();
			var formato=/^\d{4}\s\d{4}\s\d{4}\s\d{4}$/;
			var risultato= formato.test(valore);
			if(risultato==false){
				$("#creditCardError").css('display','block');
				notificaErrore("true","erroreCreditCard");
			}
			else{
				$.ajax({
					url:'sendData',
					data:"creditCard=" +$("#creditCard").val()+ "&checkCreditCard=check",
		            type:'POST',
		            cache:false,
		            error:function(){alert('error');},
					async:false
					
				}).done(function(risposta){
					if(risposta!="ok"){
						$("#creditCardError").css('display','block');
						notificaErrore("true","erroreCreditCard");
					}else{
						$("#creditCardError").css('display','none');
						notificaErrore("false","erroreCreditCard");
					}
				});
			}
		
		}
		
		//controlla che non sia già presente sul db questo username contattando la servlet
		function controllaUsername(){
			var format=/\s+/;
			if($("#username").val()!="" && !( format.test($("#username").val()))){
				$.ajax({
					url:'sendData',
					data:"usr=" +$("#username").val()+ "&checkUsername=check",
					type:'POST',
					cache:false,
					error:function(){alert('error');},
					async:false
					
				}).done(function(risposta){
					
					if(risposta!="ok"){
						$("#usernameError").css('display','block');
						notificaErrore("true","erroreUsername");
					}else{
						$("#usernameError").css('display','none');
						notificaErrore("false","erroreUsername");
					}
				});
			}
			else{
				$("#usernameError").css('display','block');
				notificaErrore("true","erroreUsername");
			}
		}
		//controlla che il campo password non sia vuoto
		function controllaPassword(){
			var pwd=$("#password").val();
			if (pwd=="") {
				$("#passwordError").css('display', 'block');
				notificaErrore("true","errorePassword");
			}
			else{
				$("#passwordError").css('display', 'none');
				notificaErrore("false","errorePassword");
			}
			controllaConfermaPassword();
		}
		
		//controlla che il campo conferma password sia uguale al campo password 		function controllaConfermaPassword(){
			
			var confermaPassword=$("#checkPwd").val();
			var password=$("#password").val();
			if (confermaPassword != password) {
				$("#checkPwdError").css('display', 'block');
				notificaErrore("true","erroreCheckPassword");
			}
			else{
				$("#checkPwdError").css('display', 'none');
				notificaErrore("false","erroreCheckPassword");
			}
		}

		function controllaErrorePersistenza(){
			alert("controllo");
			$.ajax({
				url: 'sendData',
				type: 'get',
				headers : {
					'errorePersistenza' : 'true'
				},
				error: function(){alert("errore");}
			}).done(function(response){
				alert(response);
			});
		}