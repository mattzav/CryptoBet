/**
 * 
 */
		function error(errore,nome){
			$.ajax({
				url:'sendData',
				data: nome+"="+errore,
	            type:'POST',
	            cache:false,
	            error:function(){alert('error');}
				
			});
		}
		
		function checkName(){
		
			var value=$("#name").val();
			var errore= value.match(/\d+/g);
			if (errore != null) {
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
			if (errore != null) {
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
			var format=/[0-9][0-9][0-9][0-9]\s[0-9][0-9][0-9][0-9]\s[0-9][0-9][0-9][0-9]\s[0-9][0-9][0-9][0-9]/;
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
					}else{
						$("#creditCardError").css('display','none');
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
				}else{
					$("#usernameError").css('display','none');
				}
			});
		}
		
		function checkCheckPwd(){
			var confPwd=$("#checkPwd").val();
			var pwd=$("#password").val();
			if (confPwd != pwd) {
				$("#checkPwdError").css('display', 'block');
				error("true","erroreCheckPwd");
			}
			else{
				$("#checkPwdError").css('display', 'none');
				error("false","erroreCheckPwd");
			}
		}