<%@	taglib uri="http://www.springframework.org/tags" 	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c" %>

<body onLoad="stepNext();">

	<!----	Content	---->
	<div id="theContent">	
		<section title="Contact" class="tupper">
			<div id="navSteps" class="lightBorder"> 
        		<ul>
        			<li id="stepSignUp1"><b>1. </b>Datos Personales</li>
        			<li id="stepSignUp2"><b>2. </b>Informaci�n de Perfil</li>
        			<li id="stepSignUp3"><b>3. </b>Horarios y Ubicaciones</li>
        		</ul>
        	</div>

			<form action="signupUser.do" method="POST" class="theBoard lightBorder">
       		    	        		    	
       		    <div id="firstStep" class="steps">
         			<span>
           				<span class="blockLeft">
			               	Organizacion :          
			            </span> 
         				<select id="organization" class="blockRight theInputs">
           					<option value="ucc" selected>Universidad Cat�lica de C�rdoba</option>	            					            					
           				</select>       
         			</span>	
         			
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               	Identificaci�n Personal :          
			            </span> 
           				
           				<input type="text" id="personalId" class="blockRight theInputs" onChange="checkIt(this)"/>
         			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			              	Nombre :          
			        	</span> 
           				
           				<input type="text" id="name" class="blockRight theInputs" onChange="checkIt(this)" onKeyPress="checkAlphabetic(event);"/>
         			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               	Apellido :          
			            </span> 
           				
           				<input type="text" id="surname" class="blockRight theInputs" onChange="checkIt(this)" onKeyPress="checkAlphabetic(event);"/>
          			</span> 
         				
         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               	E-mail :          
			            </span> 
           				
           				<input type="text" id="email" class="blockRight theInputs" onChange="checkIt(this)"/>
          			</span> 
         				
         			<div class="split"></div>
         				          				
       				<span>
			            <span class="blockLeft">
               				Contrase�a :          
            			</span> 
        				
        				<input type="password" id="password-first" class="blockRight theInputs" onChange="checkIt(this);"/>
       				</span>

         			<div class="split"></div>
         				
         			<span>
           				<span class="blockLeft">
			               Repita su contrase�a :          
			            </span> 
           				
           				<input type="password" id="password-check" class="blockRight theInputs" onChange="checkIt(this); checkPassword();"/>
         			</span> 
         				
         			<div class="split"></div>

         			<span>
           				<span class="blockLeft">
			               Tel�fono Celular :          
			        	</span> 
           				
           				<input type="text" id="cellphone" class="blockRight theInputs" onChange="checkIt(this);" onKeyUp="checkNumeric(this);"/>
         			</span> 
         			
       		    </div>
       		    <!-------------------------------------------------- END of FIRST STEP -------------------------------------------------->
       		    
       		    <div id="secondStep" class="steps" style="display: none">
          				
       				<span>
	           			<span class="blockLeft">
				         	Direcci�n :          				              	 
				        </span> 
	           		
	           			<input type="number" 	id="number" class="blockRight theInputs" style="width: 4em" 	onChange="checkIt(this);" onKeyUp="checkNumeric(this);" min="0" max="99999"/>
	           			<input type="text" 		id="street" class="blockRight theInputs" style="width: 8.8em" 	onChange="checkIt(this);" />
          			</span> 
          				
          			<div class="split"></div>
          				
          			<span>
	           			<span class="blockLeft">
				        	Barrio :          				              	 
				        </span> 
	           				
	           			<select id="neighborhood" class="blockRight theInputs" onChange="checkIt(this);">
	           				<option value="0" selected>Barrio</option>	           			
	           				<option>Arguello</option>
	           			</select>
          			</span> 

          			<div class="split"></div>
          				
          			<span>
           				<span class="blockLeft">
			               	Turno :          				              	 
			            </span> 
           				
           				<select id="shift" class="blockRight theInputs" onChange="checkIt(this);">
           					<option value="0" selected>Turno</option>	            					           				
           					<option value="morning">Ma�ana</option>	            				
           					<option value="afternoon">Tarde</option>
           				</select>            				
           			</span> 
          				
          			<div class="split"></div>
          				
         			<span>
 				        <span class="blockLeft">
		            		Tipo de Usuario :          			              	 
		           	 	</span> 
          				
           				<select id="userType" name="userType" onChange="checkIt(this); userTypeChanged(this);" class="blockRight theInputs">
           				    <option value="0" selected> </option>
           					<option value="pedestrian">Peat�n</option>	            				
           					<option value="driver">Conductor</option>
           					<option value="driver-pedestrian">Peat�n / Conductor</option>	            					
           				</select>               				
           			</span>
          				
          			<div id="drives" style="display:none">
          				<div class="split"></div>
          			
       					<hr class="hrs">
       					
       					<h4>Informaci�n de Veh�culo</h4>
       					
       					<div class="split"></div>
         					
           				<span>
            				<span class="blockLeft">
				               Marca del Veh�culo :          					               
				            </span> 
            				
            				<select id="brand" class="blockRight theInputs" onChange="checkIt(this);">
	            				<option value="0" selected></option>	            					            				            				
            					<option>Renault</option>	            				
            					<option>Fiat</option>
            					<option>Ford</option>	            					
            				</select>         	            				
            			</span> 
	            			
            			<div class="split"></div>
	            			
           				<span>
            				<span class="blockLeft">
				               Modelo del Veh�culo :          					               
				            </span> 
            				
            				<input type="text" id="model" class="blockRight theInputs" onChange="checkIt(this);">
   						</span> 
	      						
            			<div class="split"></div>
	            			
           				<span>
            				<span class="blockLeft">
				               Patente del Veh�culo :          					             
				            </span> 
            				
            				<input type="number"	id="plateNumbers" class="blockRight theInputs" style="width: 4em" onChange="checkIt(this);" min="000" max="999" />	            				
            				<input type="text" 		id="plateLetters" class="blockRight theInputs" style="width: 4em" onChange="checkIt(this);" onKeyPress="checkAlphabetic(event);" maxlength="3" />
      					</span>         	
	      						
            			<div class="split"></div>
	      						
           				<span>
            				<span class="blockLeft">
				               Cantidad de Asientos Libres :          					           
				            </span> 
		            				
   	        				<select id="numberSeats" class="blockRight theInputs" onChange="checkIt(this);">
            					<option value="0" selected></option>	            					            				
            					<option value="1">1</option>	            				
            					<option value="2">2</option>
            					<option value="3">3</option>	 
            					<option value="4">4</option>	            					
            					<option value="5">5</option>	            					
            					<option value="6">6</option>	            					
            					<option value="7">7</option>	            					  					
            				</select>          						
            			</span>  	
   					</div>
       		    </div>
       		    <!-------------------------------------------------- END of SECOND STEP -------------------------------------------------->


       		    <div id="thirdStep" class="steps" style="display: none">
					<table id="tableSignUp" class="theSchedule">
						<tr>
							<th>
								<!-- Empty -->
							</th>	
							<th>
								Lunes
							</th>
							<th>
								Martes
							</th>
							<th>
								Mi�rcoles
							</th>
							<th>
								Jueves
							</th>
							<th>
								Viernes
							</th>				
						</tr>
						<tr id="userTypeRow">
			
						</tr>
						<tr id="in">
							<td>
								Entrada
							</td>
						</tr>
						<tr id="out">
							<td>
								Salida
							</td>									
						</tr>
					</table>
					
					<div class="split"></div>
	      						
          			<span id="applyMapDefinition">
          				<!--  
	           				<span>
				               Aplicar para:        					           
				            </span> 
		            				
	  	        			<select id="selectApply" class="theInputs" onChange="checkIt(this);">
	           				</select>       
           				-->
           				<input type="button" id="btnMap" class="btn" value="Listo"/>  
           				<input type="hidden" id="hdnInOut" 			/>  
             			<input type="hidden" id="hdnDay" 			/>
              			<input type="hidden" id="hdnUserTypeDay" 	/>
             			  	            						            						
           			</span>  	
				</div>
				<!-------------------------------------------------- END of THIRD STEP -------------------------------------------------->
				
				
				<!----	Map Driver		---->		
				<div id="mapDriver">
					<div id="map"></div>
					<span class="t1" style = "visibility:hidden">
						<a id="permalink" href=""></a>
					</span>

					<span id='mapinfo'>
						<span id='currentscale' style="display:none"></span>
					</span>						
				</div>
				
				<!----	Map Pedestrian	---->	
        		<div id="mapPedestrian">
        			<div id="map2" class="mapSimple"></div>
        		</div>

				<div class="split"></div>        								         													            			
	
				<!----	Red Alerts 	----->
				<div id="alert" class="alerts"></div>          				
				
        		<!----	Buttons	---->
        		<div id="buttonsSteps">
        			<input type="button" class="btn" id="btnBack" 	value="Anterior" 	onClick="stepBack();"	/>	
           	   		<input type="button" class="btn" id="btnNext" 	value="Siguiente"	onClick="stepNext();"	/>	
        			<input type="submit" class="btn" id="btnOK" 	value="Confirmar" 	style="display: none" 	/>  
        		</div> 	  
       		</form>	  
			<div class="clearer"></div>
		</section>
	
	</div>
</body>

<script src="resources/scripts/Register.js" 	type="text/javascript"></script>
<script src="resources/maps/OpenLayers.js"		type="text/javascript"></script>    
<script src="resources/maps/OpenStreetMap.js"  	type="text/javascript"></script>
<script src="resources/maps/proj4js.js"			type="text/javascript"></script>
<script src="resources/maps/osmap.js" 			type="text/javascript"></script>
<script src="resources/maps/track.js" 			type="text/javascript"></script>
<script src="resources/maps/osmapSimple.js" 	type="text/javascript"></script>

<script >
	initMapCoords(lonlat, zoom, map, null);
</script>


