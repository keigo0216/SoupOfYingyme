/**
 * 
 */
let xhr = new XMLHttpRequest();
	xhr.open("get", "/numberofQ", true);
	xhr.send(null);
	xhr.onload = function (event) {
		if(xhr.readyState === 4 && xhr.status === 200) {
			console.log('Hello');
			
			if(!xhr.responseText) {
				alert("エラー発生");
			}else {
				const questionList = JSON.parse(xhr.responseText)
				console.log(questionList);
				for (let i = 0; i < questionList.length; i++) {
					console.log(questionList[i].id);
					document.getElementById(questionList[i].id).style.color = "blue";
				}
			}
		} else {
			alert("エラー発生");
		} 
	} 