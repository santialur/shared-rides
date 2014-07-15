
var i 		=  -1; 	//Contador de Pasos
var userJs	= 	0;	//Tipo de Usuario
var shiftJs	= 	0;	//Turno

function stepsUpdate(value) {
	if(value === 1)							//Si estoy sumando
		if(i===0 && userJs===0)
			alert("Seleccione un tipo de Usuario!");
		else if(i===1 && shiftJs===0)
			alert("Seleccione un turno!");
		else
			i = i + value;
	else									//Si estoy restando
		i = i + value;
}

$( document ).ready(function() {

	//Iniciar Mapa Simple
	initMap(); 
	
	//Esconder elementos
	start();
	
	//Resaltar Primer Paso
	highlightStep(i);
	
	//Acciones al presionar las imagenes
	$( "#imgSun" ).click(function(){
		shiftJs		= 1;
		$( "#imgSun" ).css('opacity', '1');
		$( "#imgMoon" ).css('opacity', '0.05');
	});
	$( "#imgMoon" ).click(function(){
		shiftJs 	= 2;
		$( this ).css('opacity', '1');
		$( "#imgSun" ).css('opacity', '0.05');
	});
	$( "#imgBoot" ).click(function(){
		userJs		= 1;
		$( this ).css('opacity', '1');
		$( "#imgSteering" ).css('opacity', '0.05');
	});
	$( "#imgSteering" ).click(function(){
		userJs		= 2;
		$( this ).css('opacity', '1');
		$( "#imgBoot" ).css('opacity', '0.05');
	});

	//Acciones al presionar Siguiente
	$( "#btnNext" ).click(function(){
		switch(i)
		{
		case 1:
			//	De TipoUsuario a Turno
			
			$( this ).css('marginLeft', '60px');
			
			highlightStep(i);
			nextStep(i);
			
			$( "#btnBack" ).show( 'fast' );	
			break;
		case 2:		
			//	De Turno a Mapa
			
			highlightStep(i);
			nextStep(i);
			
			if(userJs === 2)
				$( "#mapPedestrian" ).show( 'slow' );
			else
				$( "#mapDriver" ).show( 'slow' );
		
			$( this ).hide( 0 );
			$( "#btnOK" ).css('marginLeft', '60px');
			$( "#btnOK" ).show( 'slow' );
			break;	
		}
	});
	
	//Acciones al presionar Anterior
	$( "#btnBack" ).click(function(){
		switch(i)
		{
		case 0:		
			//	De Turno a TipoUsuario
			
			highlightStep(i);
			backStep(i);

			$( this ).hide( 'fast' );
			$( "#btnNext" ).css('marginLeft', '0px');
			break;
		case 1:		
			//	De Mapa a Turno
			
			highlightStep(i);
			backStep(i);
			
			$( "#btnOK" ).hide( 'fast' );
			$( "#btnNext" ).show( 'slow' );
			$( "#listFound" ).hide( 'fast' );
			$( "#tableFound td" ).remove();
			break;
		}
	});
	

	function start(){
		//	Establece las propiedades iniciales
				
		$( "#mapDriver" ).css( 'display', 'none' );
		$( "#mapPedestrian" ).css( 'display', 'none' );
		$( "#btnBack" ).hide( 0 );							
		$( "#listFound" ).hide( 0 );
	} 
	
	
	function nextStep(step){
		//	Inserta elementos del paso actual 
		//	del formulario al avanzar
		//	step: numero de paso actual.
		
		switch(step)
		{
		case 1:
			$( "#imgBoot" ).hide( 0 );
			$( "#imgSteering" ).hide( 0 );
			$( "#imgSun" ).show( 'fast' );
			$( "#imgMoon" ).show( 'fast' );
			break;
		case 2:
			$( "#imgSun" ).hide( 0 );
			$( "#imgMoon" ).hide( 0 );
			break;
		}
	}
	
	
	function backStep(step){
		//	Inserta elementos del paso actual 
		//	del formulario al retroceder
		//	step: numero de paso actual.
		
		switch(step)
		{
		case 0:
			$( "#imgSun" ).hide( 0 );
			$( "#imgMoon" ).hide( 0 );
			$( "#imgBoot" ).show( 'fast' );
			$( "#imgSteering" ).show( 'fast' );
			break;
		case 1:
			$( "#mapDriver" ).hide( 0 );
			$( "#mapPedestrian" ).hide( 0 );
			$( "#imgSun" ).show( 'fast' );
			$( "#imgMoon" ).show( 'fast' );
			break;
		}
	}

	
	function highlightStep(step){
		//	Resalta el paso actual del formulario
		//	step: numero de paso actual.
			
		switch(step)
		{
		case -1:
		case 0:
			$( "#step1" ).css('opacity', '1');
			$( "#step2" ).css('opacity', '0.2');
			$( "#step3" ).css('opacity', '0.2');
			break;
		case 1:
			$( "#step1" ).css('opacity', '0.2');
			$( "#step2" ).css('opacity', '1');
			$( "#step3" ).css('opacity', '0.2');
			break;
		case 2:
			$( "#step1" ).css('opacity', '0.2');
			$( "#step2" ).css('opacity', '0.2');
			$( "#step3" ).css('opacity', '1');
			break;
		}
	}
	
	
	$( "#btnOK" ).click(function(){
		var coordsJs;				//Datos de coordenadas
		
		if(userJs === 2)
			coordsJs = "[{lon=" + lonJs.toString() + " , lat=" + latJs.toString() + "}]";
		else
			coordsJs = gpxTrack.confirm();
		
		$.post( "find.do", { "user": userJs , "shift": shiftJs, "mapData": coordsJs }, 
				function(json)
				{
					var jsonNew = $.parseJSON(json);
					$.each(jsonNew, function(i, data){
						$( "#tableFound" ).append("<tr><td>" + data.name + "</td><td>" + data.surname + "</td><td><a href='/Shared-Rides/profile.do?user="+ data.id +"'><img src='resources/profilePic/" + data.picture + "'/></a></td></tr>");
					});
				}); 
		
		//Traer la lista
		$( "#mapDriver" ).css('display', 'none');
		$( "#mapPedestrian" ).css('display', 'none');
		
		$( "#listFound" ).show( 'fast' );
		$( "#btnOK"	).hide( 'fast' );
	});
	
});

