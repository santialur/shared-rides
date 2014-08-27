/**
 * Returns the day's Label according to the index.
 * 
 * @param {number} index - number of day to be calculated.
 */
function getDayLabel(index) {
	var label;
	
	switch (index) {	
		case 1:
		case '1':
			label = $("#lbl-monday").val();
			break;
		case 2:
		case '2':
			label = $("#lbl-tuesday").val();
			break;
		case 3:
		case '3':
			label = $("#lbl-wednesday").val();
			break;
		case 4:
		case '4':
			label = $("#lbl-thursday").val();
			break;					
		case 5:
		case '5':
			label = $("#lbl-friday").val();		
			break;
	}	
	return label;
}