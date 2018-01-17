/**
 * 
 */

function cambiaPagina(button) {
	if (!((button && $(".nextButton").hasClass("disabled"))
			||( !button  && $(".previousButton").hasClass("disabled")))) {
		$.ajax({
			url : 'partiteEScudetti',
			data : {method:button},
			type : 'POST',
			success : function(data) {
				$(".disabled").removeClass("disabled");
				if (data == "ultima") {
					if (button == true)
						$(".nextButton").addClass("disabled");

					else
						$(".previousButton").addClass("disabled");

				}
			}
		});
	}
}