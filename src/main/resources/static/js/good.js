function addGood(id) {
	
	let request = new XMLHttpRequest();
	request.open("get", "/addGood?id=" + id, true);
	request.send(null);
	request.onload = function (event) {
		if(request.readyState === 4 && request.status === 200) {
			if(request.responseText == "") {
				alert("ログインしてください");
			} 
			else {
			const jsonQuestionData = JSON.parse(request.responseText);
			document.getElementById(id).textContent = jsonQuestionData.good;
			document.getElementById(id).style.color = "blue";
			}
		}else{
			alert("エラー発生");
		}
	};
		
	request.onerror = function (event) {
		alert("エラー発生");
	}
}

