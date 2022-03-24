/**
 * 
 */
function required(){
	var empt = document.forms["form1"]["password"].value;
	if (empt === "")
	{
	alert("This field can't be empty!'");
	return false;
	}
		else 
		{
		alert('Ok!');
		return true; 
		}
}