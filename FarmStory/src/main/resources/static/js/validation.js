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

    // 이메일 인증 코드 확인
    let emailAuthCode = "";

    // 아이디 중복확인 --------------------------------------------------------------
    function checkUid(){

        let uid = $('input[name=uid]').val();
        const resultUid = document.querySelector('span[class=resultUid]');

        if(!uid){ alert('아이디를 입력하세요'); return false; }

        if(uid.match(reUid)){
            $.ajax({
                url:'/FarmStory/user/checkUid',
                data:{ "uid":uid },
                method:'GET',
                dataType:'JSON',
                success: function(data){

                    if(data.result == 1){
                        resultUid.innerText = "이미 사용중인 아이디입니다..";
                        resultUid.style.color = "red";
                        isUidok = false;
                    }else{
                        resultUid.innerText = "사용 가능한 아이디입니다.";
                        resultUid.style.color = "green";
                        isUidok = true;
                    }
                }
            });
        }else{
            resultUid.innerText = "아이디 양식에 맞지 않습니다.";
            resultUid.style.color = "red";
            isUidok = false;
        }
    }
    // 아이디 중복확인 끝 --------------------------------------------------------------

    // 별명 중복확인 --------------------------------------------------------------
    function checkNick(){

        let nick = $('input[name=nick]').val();
        const resultNick = document.querySelector('span[class=resultNick]');

        if(!nick){ alert('별명을 입력하세요'); return false;}

        if(nick.match(reNick)){
            $.ajax({
                url:'/FarmStory/user/checkNick',
                data:{ "nick":nick },
                method:'GET',
                dataType:'JSON',
                success: function(data){
                    if(data.result == 1){
                        resultNick.innerText = "이미 사용중인 별명입니다.";
                        resultNick.style.color = "red"
                        isNickok = false;
                    }else{
                        resultNick.innerText = "사용 가능한 별명입니다.";
                        resultNick.style.color = "green"
                        isNickok = true;
                    }
                }
            });
        }else{
            resultNick.innerText = "별명 양식에 맞지 않습니다.";
            resultNick.style.color = "red"
            isNickok = false;
        }
    }
    // 별명 중복확인 끝 --------------------------------------------------------------

    // 이메일 인증 확인 --------------------------------------------------------------
    function Auth(){
        const checkEmail      = document.getElementById('checkEmail');
        const resultEmail     = document.querySelector('span[class=resultEmail]');

        let email     = checkEmail.value;

        if(!email){ alert('이메일을 입력하세요'); return false; isEmailAuthOk = false;}

        if(email.match(reEmail)){
            $.ajax({
                url:'/FarmStory/user/checkEmail',
                method:'GET',
                data:{ "email":email },
                dataType:'JSON',
                success: function(data){
                    if(data.result == 1){
                        resultEmail.innerText = "이미 사용중인 이메일입니다.";
                        resultEmail.style.color = "red";
                        isEmailok = false;
                    }else{
                        document.getElementById('divEmail').style.display = "";
                        isEmailok = true;
                        $.ajax({
                            url:'/FarmStory/user/emailConfirm',
                            method:'POST',
                            data: { "email":email },
                            dataType:'JSON',
                            success: function(data){
                                emailAuthCode = data.result;
                                console.log(emailAuthCode);
                            }
                        });
                    }
                }
            });
        }else{
            resultEmail.innerText = "이메일 양식에 맞지 않습니다.";
            resultEmail.style.color = "red";
            document.getElementById('divEmail').style.display = "none";
            isEmailAuthOk = false;
            isEmailok = false;
        }
    }

    function emailauth(){
        const checkEmailAuth  = document.getElementById('checkEmailAuth');
        const resultEmailAuth = document.querySelector('span[class=resultEmailAuth]');

        let emailAuth = checkEmailAuth.value;

        if(!emailAuth){
            resultEmailAuth.innerText = "인증 번호를 입력하세요.";
            resultEmailAuth.style.color = "red";
            isEmailAuthOk = false;
        }

        if(emailAuth == emailAuthCode){
            resultEmailAuth.innerText = "인증 되었습니다.";
            resultEmailAuth.style.color = "green";
            isEmailAuthOk = true;
        }else{
            resultEmailAuth.innerText = "인증번호가 일치하지 않습니다.";
            resultEmailAuth.style.color = "red";
            isEmailAuthOk = false;
        }
    }
    // 이메일 인증 끝 --------------------------------------------------------------

    window.onload = function(){
        // 비밀번호 확인 --------------------------------------------------------------
        const checkPass = document.getElementById('checkPass');

        checkPass.addEventListener('focusout', function(){
            const resultPass = document.querySelector('span[class=resultPass]');
            let pass1 = document.querySelector('input[name=pass1]').value;
            let pass2 = checkPass.value;

            if(pass2.match(rePass)){
                if(pass1 == pass2){
                    resultPass.innerText = "사용 가능한 비밀번호 입니다.";
                    resultPass.style.color = "green";
                    isPassok = true;
                }else{
                    resultPass.innerText = "서로 다른 비밀번호 입니다.";
                    resultPass.style.color = "red";
                    isPassok = false;
                }
            }else{
                resultPass.innerText = "비밀번호 양식에 맞지 않습니다.";
                resultPass.style.color = "red";
                isPassok = false;
            }
        });
        // 비밀번호 확인 끝 --------------------------------------------------------------

        // 이름 확인 --------------------------------------------------------------
        const checkName = document.getElementById('checkName');

        checkName.addEventListener('focusout', function(){
            const resultName = document.querySelector('span[class=resultName]');

            let name = checkName.value;

            if(name.match(reName)){
                resultName.innerText = "사용 가능한 이름입니다.";
                resultName.style.color = "green";
                isNameok = true;
            }else{
                resultName.innerText = "이름 양식에 맞지 않습니다.";
                resultName.style.color = "red";
                isNameok = false;
            }
        });
        // 이름 확인 끝 --------------------------------------------------------------

        // 휴대폰 확인 --------------------------------------------------------------
        const checkHp = document.getElementById('checkHp');

        checkHp.addEventListener('focusout', function(){
            const resultHp = document.querySelector('span[class=resultHp]');
            let hp = checkHp.value;

            if(hp.match(reHp)){
                $.ajax({
                    url:'/FarmStory/user/checkHp',
                    method:'GET',
                    data:{ "hp":hp },
                    dataType:'JSON',
                    success: function(data){
                        if(data.result == 1){
                            resultHp.innerText = "이미 사용중인 번호입니다.";
                            resultHp.style.color = "red";
                            isHpok = false;
                        }else{
                            resultHp.innerText = "사용 가능한 번호입니다.";
                            resultHp.style.color = "green";
                            isHpok = true;
                        }
                    }
                });
            }else{
                resultHp.innerText = "휴대폰 양식에 맞지않습니다.";
                resultHp.style.color = "red";
                isHpOk = false;
            }
        });
        // 휴대폰 확인 끝 --------------------------------------------------------------

        $('#user > form').submit(function(){

            if(!isUidok){
                alert('아이디를 확인하세요');
                return false;
            }

            if(!isPassok){
                alert('비밀번호를 확인하세요');
                return false;
            }

            if(!isNameok){
                alert('이름을 확인하세요');
                return false;
            }

            if(!isNickok){
                alert('별명을 확인하세요');
                return false;
            }

            if(!isEmailAuthOk){
                alert('이메일을 확인하세요');
                return false;
            }

            if(!isHpok){
                alert('휴대폰을 확인하세요');
                return false;
            }

            return true;
        });
    };
