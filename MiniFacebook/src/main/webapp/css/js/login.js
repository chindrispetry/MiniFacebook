
var loginEmail = document.getElementById('email-login');
var loginPassword = document.getElementById('password-login');

var loginButton = document.getElementById('btn-login');

let loginAlert = document.getElementById('login-alert');

let formLogin = document.getElementById('login');
loginButton.addEventListener('click', () => {
	if (ValidateLogin() == 1) {
                const user = {
                    email : loginEmail.value,
                    password:loginPassword.value
                }
                $.ajax({
                    type: "POST",
                    url: "Login",
                    data: user,
                    success: function(msg){
                        if(msg != "-1"){
                           let res = JSON.parse(msg)
                           loginAlert.className = 'login-alert-check';
                           loginAlert.innerText = 'Connected';
                           var cookie = document.cookie.split('=');
                           localStorage.setItem("user",cookie[1]);
                           window.location.replace('http://localhost:8080/Test/'+res.page);
                        }else{
                            loginAlert.className = 'login-alert-wrong';
                            loginAlert.innerText = 'E-mail or password wrong!';
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                       alert("some error");
                    }
                  });
		
	}
	else {
		loginAlert.className = 'login-alert-wrong';
		loginAlert.innerText = 'E-mail or password wrong!';
	}
        formLogin.onsubmit = () => { return false; }
});

loginEmail.addEventListener('input', (e) => {
	if (e.target.value.length < 6) {
		loginEmail.className = 'animation-wrong';
	}
	else {
		loginEmail.className = 'animation-done';
	}
});
loginPassword.addEventListener('input', (e) => {
	if (e.target.value.length < 6) {
		loginPassword.className = 'animation-wrong';
	}
	else {
		loginPassword.className = 'animation-done';
	}
});
function ValidateLogin() {
	var arr = [1, 1];
	if (loginEmail.value == "" || loginEmail.value.length < 6) {
		loginEmail.className = ' animation-wrong';
		loginEmail.className += ' earthquake';
		arr[0] = 0;
	}
	if (loginPassword.value == "" || loginPassword.value.length < 6) {
		loginPassword.className = 'animation-wrong';
		loginPassword.className += ' earthquake';
		arr[1] = 0;
	}
	if (arr[0] == 0 || arr[1] == 0) {
		setTimeout(() => {
			loginEmail.classList.remove('earthquake');
			loginPassword.classList.remove('earthquake');
		}, 1000);
		return -1;
	}
	return 1;
}
function resetLoginControls() {
	loginEmail.value = "";
	loginEmail.className = "";
	loginPassword.value = "";
	loginPassword.className = "";

}
