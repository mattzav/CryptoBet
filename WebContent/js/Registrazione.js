/**
 * 
 */
		function error(errore, nome){
			$.ajax({
				url : 'sendData',
				data : {
					nome:value
				},
	            type:'POST',
	            cache:false,
	            error:function(){alert('error');}
				
			});
		}
		
		function checkName(){
			
			var value=$("#name").val();
			var errore= value.match(/\d+/g);
			if (errore != null || value=="") {
				$("#nameError").css('display','block');
				error("true","erroreName");
			}
			else{
				$("#nameError").css('display','none');
				error("false","erroreName");
			}
			
		}
		
		function checkSurname(){
			
			var value=$("#surname").val();
			var errore= value.match(/\d+/g);
			if (errore != null || value=="") {
				$("#surnameError").css('display','block');
				error("true","erroreSurname");
			}
			else{
				$("#surnameError").css('display','none');
				error("false","erroreSurname");
			}
		}
		
		function checkCreditCard(){
			var value=$("#creditCard").val();
			var format=/^\d{4}\s\d{4}\s\d{4}\s\d{4}$/;
			var result= format.test(value);
			if(result==false){
				$("#creditCardError").css('display','block');
				error("true","erroreCreditCard");
			}
			else{
				$.ajax({
					url:'sendData',
					data:"creditCard=" +$("#creditCard").val()+ "&checkCreditCard=check",
		            type:'POST',
		            cache:false,
		            error:function(){alert('error');}
					
				}).done(function(response){
					if(response!="ok"){
						$("#creditCardError").css('display','block');
						error("true","erroreCreditCard");
					}else{
						$("#creditCardError").css('display','none');
						error("false","erroreCreditCard");
					}
				});
			}
		
		}
		function checkUsername(){
			$.ajax({
				url:'sendData',
				data:"usr=" +$("#username").val()+ "&checkUsername=check",
	            type:'POST',
	            cache:false,
	            error:function(){alert('error');}
				
			}).done(function(response){
				if(response!="ok"){
					$("#usernameError").css('display','block');
					error("true","erroreUsername");
				}else{
					$("#usernameError").css('display','none');
					error("false","erroreUsername");
				}
			});
		}
		
		function checkPassword(){
			var pwd=$("#password").val();
			if (pwd=="") {
				$("#passwordError").css('display', 'block');
				error("true","errorePassword");
			}
			else{
				$("#passwordError").css('display', 'none');
				error("false","errorePassword");
			}
		}
		
		function checkCheckPwd(){
			var confPwd=$("#checkPwd").val();
			var pwd=$("#password").val();
			if (confPwd != pwd) {
				$("#checkPwdError").css('display', 'block');
				error("true","erroreCheckPassword");
			}
			else{
				$("#checkPwdError").css('display', 'none');
				error("false","erroreCheckPassword");
			}
		}