$(document).ready(function() {


	function validateEmail(email) { 
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	} 

	var products = [{
                                                ProductID : "DarkChocolate",
                                                ProductName : "Dark Chocolate",
												Price : "25.70"
                                }, {
                                                ProductID : "Caramel",
                                                ProductName : "Caramel",
												Price : "25.70"
                                }, {
                                                ProductID : "Bukeela",
                                                ProductName : "Bukeela",
												Price : "24.60"
                                }, {
                                                ProductID : "Ristretto",
                                                ProductName : "Ristretto",
												Price : "22.40"
                                }, {
                                                ProductID : "Dharkan",
                                                ProductName : "Dharkan",
												Price : "24.60"
                                }, {
                                                ProductID : "Kazzar",
                                                ProductName : "Kazzar",
												Price : "24.60"
                                }, {
                                                ProductID : "Vanil",
                                                ProductName : "Vanil",
												Price : "25.70"
                                }, {
                                                ProductID : "Capriccio",
                                                ProductName : "Capriccio",
												Price : "22.40"
                                }, {
                                                ProductID : "Livanto",
                                                ProductName : "Livanto",
												Price : "22.40"
                                }, {
                                                ProductID : "Roma",
                                                ProductName : "Roma",
												Price : "22.40"
                                }, {
                                                ProductID : "Arpeggio",
                                                ProductName : "Arpeggio",
												Price : "22.40"
                                }, {
                                                ProductID : "DecaffinattoIntenso",
                                                ProductName : "Decaffinatto Intenso",
												Price : "22.40"
                                }, {
                                                ProductID : "Decaffinatto",
                                                ProductName : "Decaffinatto",
												Price : "22.40"
                                }, {
                                                ProductID : "Cosi",
                                                ProductName : "Cosi",
												Price : "22.40"
                                }, {
                                                ProductID : "Volluto",
                                                ProductName : "Volluto",
												Price : "22.40"
                                }, {
                                                ProductID : "FortissioLungo",
                                                ProductName : "Fortissio Lungo",
												Price : "23.50"
                                }, {
                                                ProductID : "DulsaoDoBrasil",
                                                ProductName : "Dulsao Do Brasil",
												Price : "24.60"
                                }, {
                                                ProductID : "RosabayaDeColombia",
                                                ProductName : "Rosabaya De Colombia",
												Price : "24.60"
                                }, {
                                                ProductID : "IndriyaFromIndia",
                                                ProductName : "Indriya From India",
												Price : "24.60"
                                }, {
                                                ProductID : "DecaffinatoLungo",
                                                ProductName : "Decaffinato Lungo",
												Price : "23.50"
                                }, {
                                                ProductID : "VivaltoLungo",
                                                ProductName : "Vivalto Lungo",
												Price : "23.50"
                                }, {
                                                ProductID : "LinizioLungo",
                                                ProductName : "Linizio Lungo",
												Price : "23.50"
                                }];



			var dataSource = new kendo.data.DataSource({
                data: products
            });

            $("#listView").kendoListView({
                dataSource: dataSource,
                template: kendo.template($("#template").html())
            });
			
			$(".nTextbox").kendoNumericTextBox({
				value: 0
			});
			
			$("#submit").click(function(){
				
				if (!validateEmail($("#email").val())){
					$(".emptyEmail").show();
					return;
				}
				
				var items = {};
				
				//var list = $("#listView").data('kendoListView');
				//var ds = list.getDataSource().items();
				for (var i = 0; i < products.length; i++){
					var currAmount = $("#" + products[i].ProductID).find("#amount" + products[i].ProductID).data("kendoNumericTextBox").value();
					if (currAmount > 0){
						items[products[i].ProductName] = currAmount;					
					}
				}
			
				var requestData = {
					email: $("#email").val(),
					order: items
				};
				
				$.ajax({
					url: "/order",
					data: JSON.stringify(requestData),
					type: 'POST',
					dataType: 'text',
					contentType: 'application/json',
					success: function(data, textStatus, jqXHR){
						alert('confirmed');
					},
					error: function(jqXHR, textStatus, errorThrown){
						alert('error: ' + jqXHR + '; status: ' + status + '; errorThrown: ' + errorThrown);
					}
				});
			});

});
