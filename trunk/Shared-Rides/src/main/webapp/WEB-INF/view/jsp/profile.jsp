<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<% 
	String id = request.getParameter("user");
%>
<body>
	<div id="theContent" class="theBox">	
		
		<section title="User Data" class="tupper">
				<h2>${name}	${surname}</h2>
		
				<div class="theBoard theLightBox" style="margin-top: 10px"> 		
					<div id="userData">				<!-- Datos 	-->
						<div id="pictureData">		<!-- Foto 	-->
							<img id="thePic" src="resources/profilePic/${picture}"  width="140px" height="150px"/>  
						</div>
						
						<div id="publicData"> 		<!-- Datos Publicos	--> 
							<p><b>Información Básica</b></p>
							<br>
							<p><img class="helpIcon" src="resources/images/location.png" 	/>${address}		</p>	
							<br>
							<p><img class="helpIcon" src="resources/images/house.png" 		/>${neighborhood}	</p>		
						</div> 
						 		
						<div id="privateData"> 		<!-- Datos Privados	-->
							<p><b>Información de Contacto</b></p>
							<br>
							<p><img class="helpIcon" src="resources/images/phone.png" 		/>${telephone}		</p>
							<br>
							<p><img class="helpIcon" src="resources/images/email.png" 		/>${email} 			</p>
						</div>		
					</div>	
				</div>
		</section>
		
	
		<section title="Profile Data" class="tupper" style="margin-top: 50px">
			<div class="theBoard theLightBox"> 		
					<div id="profileData">				<!-- Resto del Perfil	--> 
						<div id="driverData">			<!-- Perfil conductor 	-->		 		
							<h2>
								<spring:message code="label.driver"/>
							</h2>	 	
							<div  style="float:left; margin-left:2%"> 	<!--Puntuacion			-->		
								<img src="resources/images/star.png" width="50px" style="float:right; margin-right: 2%"/>
								${ratingDriver}	
							</div>
								 	
							<p> 	<!--Resto de datos		-->	

							</p>	
							 		
							<div> 	<!--Horario				--> 	
								<table id="tableDriver" class="theSchedule">

								</table>					
							</div>	
							
							<div style="margin-top: 45px">	<!--Mapa				-->		
								<div id="map3" class="mapStatic"></div>
							</div>
								 
						</div>
						
						<div id="line" class="vrs"></div>
						
						<div id="pedestrianData">		<!-- Perfil peaton 	-->		 		
							<h2> 	<spring:message code="label.pedestrian"/>		</h2>	
							<div style="margin: 10px 0 50px 0;"> 	<!--Puntuacion			-->		
								<img src="resources/images/star.png" width="50px" style="float:left; margin-left:2%"/>
								<span style="display: inline-block;">${ratingPedestrian}</span>
							</div>	 	
							
							<p> 	
								<!--Resto de datos		-->		
							</p>	
							 		
							<div> 	<!--Horario				--> 	
								<table id="tablePed" class="theSchedule">
								</table>					
							</div>	
							
							<div  style="margin-top: 45px; margin-left: 40px"> 	<!--Mapa				-->		
								<div id="map2" class="mapStatic"></div>
							</div>
								 
						</div>
					</div>	
			</div>
		</section>
	</div>
	
	<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
	<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
	<script src="resources/maps/osmapStatic.js"		type="text/javascript"></script>	
	<script src="resources/scripts/jsProfile.js"	type="text/javascript"></script>

	<script type="text/javascript">
	
		var schPed = [];							
		var schDriver = [];
		
		if("${visible}" === 'false')
			$( '#privateData' ).hide( 0 );
		
		initMap("${lonPed}", "${latPed}");
		initMap1("santiago.gpx");
		
		<c:forEach var="day" items="${schPed}">
			schPed.push(["${day.dayPed}","${day.hourInPed}","${day.hourOutPed}","${day.haveDriverOut}"]);
		</c:forEach>
		
		<c:forEach var="day" items="${schDriver}">
			schDriver.push(["${day.dayDriver}","${day.hourInDriver}","${day.hourOutDriver}","${day.freeSeatsIn}", "${day.freeSeatsOut}"]);
		</c:forEach>
		
		function getDay(aux)
		{
			var day;
			
			switch(aux)
			{
				case '1':
						day	= '<spring:message code="label.monday"/>';
						break;
				case '2':
						day	= '<spring:message code="label.tuesday"/>';
						break;
				case '3':
						day	= '<spring:message code="label.wednesday"/>';
						break;
				case '4':
						day	= '<spring:message code="label.thursday"/>';
						break;
				case '5':
						day	= '<spring:message code="label.friday"/>';
						break;		
			}
			return day;
		}

		function fillTablePed(){
			var tPed = document.getElementById("tablePed");
			
			var rDay 	= tPed.insertRow(-1);
			var rIn 	= tPed.insertRow(-1);
			var rOut	= tPed.insertRow(-1);
			
			var hDay 		= document.createElement('th'); 
			hDay.innerHTML 	= "";
			rDay.appendChild(hDay);
			var cIn 		= rIn.insertCell(-1);
			cIn.innerHTML 	= "<spring:message code="label.arrival"/>";
			var cOut 		= rOut.insertCell(-1);
			cOut.innerHTML 	= "<spring:message code="label.departure"/>";
			
			for(var i=0; i<schPed.length; i++)
			{
				var hDay 		= document.createElement('th');
				hDay.innerHTML 	= getDay(schPed[i][0]);
				
				var btnRequest	= '<button class="btnRequestAssoc" style="margin-left: 3px"><img src="resources/images/steering.png" width="25px"/></button>'; 
				var hdnDay		= '<input id="hdnDay"	type="hidden" value="'+ schPed[i][0] +'"/>'; 
				var hdnIn		= '<input id="hdn" 		type="hidden" value="'+ 1 +'"/>'; 
				var hdnOut		= '<input id="hdn"		type="hidden" value="'+ 2 +'"/>';
				
				rDay.appendChild(hDay);
				var cIn 		= rIn.insertCell(-1);
				cIn.id			= "day" + schPed[i][0] + "-1";	//In
				cIn.innerHTML 	= schPed[i][1] + btnRequest + hdnDay + hdnIn;				
				
				var cOut 		= rOut.insertCell(-1);
				cOut.id			= "day" + schPed[i][0] + "-2"; 	//Out
				cOut.innerHTML 	= schPed[i][2] + btnRequest + hdnDay + hdnOut;	
			}
		}
		
		/*	Llenamos la tabla del Conductor		*/
		function fillTableDriver(){
			var tDriver = document.getElementById("tableDriver");
					
			var rDay 	= tDriver.insertRow(-1);
			var rIn 	= tDriver.insertRow(-1);
			var rOut	= tDriver.insertRow(-1);
			
			var hDay 		= document.createElement('th'); 
			hDay.innerHTML 	= "";
			rDay.appendChild(hDay);
			var cIn 		= rIn.insertCell(-1);
			cIn.innerHTML 	= "<spring:message code="label.arrival"/>";
			var cOut 		= rOut.insertCell(-1);
			cOut.innerHTML 	= "<spring:message code="label.departure"/>";
			
			for(var i=0; i<schDriver.length; i++)
			{		
				var hDay 		= document.createElement('th'); 
				hDay.innerHTML 	= getDay(schDriver[i][0]);			
				
				var btnRequest	= '<button class="btnRequestAssoc" style="margin-left: 3px"><img src="resources/images/seat.png" width="25px"/></button>'; 
				var hdnDay		= '<input id="hdnDay" 	type="hidden" value="'+ schDriver[i][0] +'"/>'; 
				var hdnIn		= '<input id="hdn" 		type="hidden" value="'+ 1 +'"/>'; 
				var hdnOut		= '<input id="hdn"		type="hidden" value="'+ 2 +'"/>';
				
				rDay.appendChild(hDay);
				var cIn 		= rIn.insertCell(-1);
				cIn.id			= "day" + schDriver[i][0] + "-1";	//In
				cIn.innerHTML 	= schDriver[i][1] + btnRequest + hdnDay + hdnIn;

				var cOut 		= rOut.insertCell(-1);
				cOut.id			= "day" + schDriver[i][0] + "-2"; 	//Out
				cOut.innerHTML 	= schDriver[i][2] + btnRequest + hdnDay + hdnOut;
			}
		}
		
		fillTablePed();
		fillTableDriver();
		
		if("${driver}" === 'false')
		{	
			$( '#driverData' ).css("display", "none");
			$( '#line' ).css("display", "none");			
			$( '#pedestrianData' ).css("width", "100%");
			$( '#driverData' ).css("float", "none");
		}
		
		if("${pedestrian}" === 'false')
		{
			$( '#pedestrianData' ).css("display", "none");
			$( '#line' ).css("display", "none");			
			$( '#driverData' ).css("width", "100%");
			$( '#pedestrianData' ).css("float", "none");
		}
		
		$( '.btnRequestAssoc' ).click(function(){
			var dayJs 	= $( this ).parent().find("#hdnDay").val();
			var inOutJs = $( this ).parent().find("#hdn").val();			
			var idUserJs = ${id};
			$.post( 'requestAssoc.do', { "day":  dayJs, "inout": inOutJs, "idUser": idUserJs },
					function(msg)
					{
						if (msg != '')
						{
							window.alert(msg);
						}
					}); 			
		});
		
	</script>
	
</body>


<!--	Datos	-->
<!--   
	<h1>
		Perfil de contactos
	</h1>

	<h1>${id}</h1>
	<h1>${name}</h1>
	<h1>${surname}</h1>
	<h1>${address}</h1>
	<h1>${neighborhood}</h1>
	<h1>${telephone}</h1>
	<h1>${email}</h1>
	<h1>${shift}</h1>
	<h1>${idPedestrian}</h1>
	<h1>${ratingPedestrian}</h1>		
	<h1>${lat}</h1>
-->	
