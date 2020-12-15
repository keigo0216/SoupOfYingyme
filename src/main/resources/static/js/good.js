function addGood(id) {
		
	let request = new XMLHttpRequest();
	request.open("get", "/addGood?id=" + id, true);
	request.send(null);
	request.onload = function (event) {
		if(request.readyState === 4 && request.status === 200) {
			if(!request.responseText) {
				alert("該当するデータはありませんでした");
				return;
			}
			
			const questionData = JSON.parse(request.responseText);
			document.getElementById(id).textContent = questionData.good;
		}else{
			alert("エラー発生");
		}
	};
		
	request.onerror = function (event) {
		alert("エラー発生");
	}
}
