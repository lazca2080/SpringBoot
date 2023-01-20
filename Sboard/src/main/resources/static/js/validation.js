// 데이터 검증에 사용하는 정규표현식
let reUid   = /^[a-z]+[a-z0-9]{5,19}$/g;
let rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
let reName  = /^[ㄱ-힣]+$/;
let reNick  = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
let reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
let reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;
let reAuth  = /^[0-9]+$/;

// 폼 데이터 검증 결과 상태변수
let isUidok = false;
let isPassok = false;
let isNameok = false;
let isNickok = false;
let isEmailok = false;
let isEmailAuthOk = false;
let isHpok = false;

// 아이디 검증
function checkUid(){
	//const btnCheckUid = document.getElementById('btnCheckUid');
	//btnCheckUid.addEventListener('click', ()=>{
		
	let uid = document.querySelector('input[name=uid]').value;
	const resultUid = document.querySelector('.resultUid');
	
	// 자바스크립트 자료형에서 false로 반환되는 값은
	// "", null, undefined, 0, NaN 나머지는 모두 true
	if(!uid){
		alert('아이디를 먼저 입력하세요')
		isUidok = false;
		return;
	}
	
	if(!uid.match(reUid)){
		resultUid.innerText = "아이디 양식에 맞지 않습니다.";
		resultUid.style.color = "red";
		isUidok = false;
		return;
	}
	
	// AJAX 전송     자바스크립트의 AJAX 통신 수행 객체
	const xhr = new XMLHttpRequest();
	xhr.open("GET", "/Sboard/user/checkUid?uid="+uid);
	xhr.responseType = "json";
	xhr.send();
	
	xhr.onreadystatechange = function(){
		
		if(xhr.readyState == XMLHttpRequest.DONE){
			if(xhr.status == 200){
				const data = xhr.response;
				
				if(data.result == 1){
					resultUid.innerText = "이미 사용중인 아이디 입니다.";
					resultUid.style.color = "red";
					isUidok = false;
				}else{
					resultUid.innerText = "사용 가능한 아이디 입니다.";
					resultUid.style.color = "green";
					isUidok = true;
				}
				
			}else{
				alert("Request fail.....")
				isUidok = false;
			}
		}
	}
}

// 별명 검증
function checkNick(){
	
	let nick = document.querySelector('input[name=nick]').value;
	const resultNick = document.querySelector('.resultNick');
	
	if(!nick){
		alert('별명을 입력하세요');
		isNickok = false;
		return;
	}
	
	if(nick.match(reNick)){
		
		$.ajax({
			url:'/Sboard/user/checkNick',
			data: { "nick":nick },
			method:'get',
			dataType:'JSON',
			success: function(data){
				console.log(data.result)
				if(data.result == 1){
					resultNick.innerText = "이미 사용중인 별명입니다.";
					resultNick.style.color = "red";
					isNickok = false;
				}else{
					resultNick.innerText = "사용 가능한 별명입니다.";
					resultNick.style.color = "green";
					isNickok = true;
				}
			}
		});		
		
	}else{
		resultNick.innerText = "사용 불가능한 별명입니다.";
		resultNick.style.color = "red";
		isNickok = false;
		return;
	}
}

$(function(){
	// 비밀번호 검증
	const checkPass = document.getElementById('checkPass');			
	
	checkPass.addEventListener('focusout', function(){
		let pass1 = document.querySelector('input[name=pass1]').value;
		let pass2 = document.querySelector('input[name=pass2]').value;
		const resultPass = document.querySelector('.resultPass');
		
		if(pass1.match(rePass)){
			
			if(pass1 == pass2){
				resultPass.innerText = "사용 가능한 비밀번호 입니다.";
				resultPass.style.color = "green"
				isPassok = true;
			}else{
				resultPass.innerText = "서로 다른 비밀번호 입니다.";
				resultPass.style.color = "red"
				isPassok = false;
			}
		}else{
			resultPass.innerText = "비밀번호 양식에 맞지 않습니다.";
			resultPass.style.color = "red"
			isPassok = false;
		}
	});
	
	// 이름 검증
	const checkName = document.getElementById('checkName');
	checkName.addEventListener("focusout", function(){
		
		let name = document.querySelector('input[name=name]').value;
		const resultName = document.querySelector('.resultName')
		
		if(!name.match(reName)){
			resultName.innerText = "두 글자 이상 한글이여야 합니다.";
			resultName.style.color = "red";
			isNameok = false;
		}else{
			resultName.innerText = "";
			isNameok = true;
		}
	});
	
	// 이메일 검증
	const email = document.getElementById("email");
	const resultEmail = document.querySelector(".resultEmail");
	
	email.addEventListener("focusout", function(){
		let email = document.querySelector("input[name=email]").value;
		
		if(!email){
			resultEmail.innerText = "이메일을 입력하세요";
			resultEmail.style.color = "red";
			isEmailok = false;
			return;
		}
		
		if(email.match(reEmail)){
			$.ajax({
				url:'/Sboard/user/checkEmail',
				data: { "email":email },
				method:'get',
				dataType:'JSON',
				success: function(data){
					
					if(data.result == 1){
						resultEmail.innerText = "이미 사용중인 이메일 입니다.";
						resultEmail.style.color = "red";
						isEmailok = false;
					}else{
						resultEmail.innerText = "사용 가능한 이메일 입니다..";
						resultEmail.style.color = "green";
						isEmailok = true;
					}
				}
			});					
		}else{
			resultEmail.innerText = "이메일 양식에 맞지 않습니다.";
			resultEmail.style.color = "red";
			isEmailok = false;
		}
	});
	
	// 휴대폰 검증
	const hp = document.getElementById("hp");
	const resultHp = document.querySelector(".resultHp");
	
	hp.addEventListener("focusout", function(){
		
		let hp = document.querySelector("input[name=hp]").value;
		
		if(!hp){
			resultHp.innerText = "번호를 입력하세요";
			resultHp.style.color = "red";
			isHpok = false;
			return;
		}
		
		if(hp.match(reHp)){
			resultHp.innerText = "";
			resultHp.style.color = "green";
			isHpok = true;
		}else{
			resultHp.innerText = "휴대폰 양식에 맞지 않습니다.";
			resultHp.style.color = "red";
			isHpok = false;
		}
	});
	
	$('#user > form').submit(function(){

		if(!isUidok){
			alert('아이디 중복확인 하셔야합니다.');
			return false;
		}
		
		if(!isPassok){
			alert('비밀번호 확인 하셔야합니다.');
			return false;
		}
		
		if(!isNameok){
			alert('이름 확인 하셔야합니다.');
			return false;
		}
		
		if(!isNickok){
			alert('별명 중복확인 하셔야합니다.');
			return false;
		}
		
		if(!isEmailok){
			alert('이메일 확인 하셔야합니다.');
			return false;
		}
		
		if(!isHpok){
			alert('휴대폰 확인 하셔야합니다.');
			return false;
		}
		
		if(!isUidok){
			alert('아이디 중복확인 하셔야합니다.');
			return false;
		}
		
		return true;

	});
	
	
});