var registerFirstName = document.getElementById('register-first-name');
var registerLastName = document.getElementById('register-last-name');
var registerEmail = document.getElementById('register-email');
var registerPassword = document.getElementById('register-password');
var registerConfirmPassword = document.getElementById('register-confirm-password');

var registerButton = document.getElementById('register-btn');

var registerAlert = document.getElementById('register-alert');
var registerFrom = document.getElementById('form-register');

var registerGender = document.getElementById('gender');

registerButton.addEventListener('click', () => {
	if (validateRegister()) {
                const user = {
                    firstname:registerFirstName.value,
                    lastname:registerLastName.value,
                    email:registerEmail.value,
                    gen:registerGender.value,
                    password:registerPassword.value
                }
                $.ajax({
                    type: "POST",
                    url: "Register",
                    data: user,
                    success: function(msg){
                        if(msg == 1){
                          registerAlert.className = "register-alert-check";
                          registerAlert.innerText = "Register with success!";
                        }
                        else{
                            registerAlert.className = "register-alert-wrong";
                            registerAlert.innerText = "This e-mail already use";
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                       alert(errorThrown);
                    }
                  });
		registerFrom.onsubmit = () => { return false; }
	}
	else {
		registerAlert.className = "register-alert-wrong";
		registerAlert.innerText = "Please complete all fields"
		registerFrom.onsubmit = () => { return false; }
	}
});
registerFirstName.addEventListener('input', e => {
	if (e.target.value.length < 3) {
		registerFirstName.className = 'animation-wrong';
	}
	else {
		registerFirstName.className = 'animation-done';
	}
});
registerLastName.addEventListener('input', e => {
	if (e.target.value.length < 3) {
		registerLastName.className = 'animation-wrong';
	}
	else {
		registerLastName.className = 'animation-done';
	}
});
registerEmail.addEventListener('input', e => {
	if (e.target.value.length < 10) {
		registerEmail.className = 'animation-wrong';
	}
	else {
		registerEmail.className = 'animation-done';
	}
});
registerPassword.addEventListener('input', e => {
	if (e.target.value.length < 6) {
		registerPassword.className = 'animation-wrong';
	}
	else {
		registerPassword.className = 'animation-done';
	}
});
registerConfirmPassword.addEventListener('input', e => {
	if (e.target.value.length > 5 && registerConfirmPassword.value == registerPassword.value) {
		registerConfirmPassword.className = 'animation-done';
	}
	else {
		registerConfirmPassword.className = 'animation-wrong';
	}
});
function validateRegister() {
	let arr = [1, 1, 1, 1, 1, 1];
	if (registerFirstName.value.length < 3) {
		registerFirstName.className = 'earthquake ';
		registerFirstName.className += 'animation-wrong';
		arr[0] = 0;
	}
	if (registerLastName.value.length < 3) {
		registerLastName.className = ' earthquake ';
		registerLastName.className += 'animation-wrong';
		arr[1] = 0;
	}
	if (registerConfirmPassword.value.length < 6) {
		registerConfirmPassword.className = ' earthquake ';
		registerConfirmPassword.className += ' animation-wrong';
		arr[2] = 0;
	}
	if (registerPassword.value.length < 3 || registerConfirmPassword.value != registerPassword.value) {
		registerPassword.className = ' earthquake';
		registerPassword.className += ' animation-wrong';
		arr[3] = 0;
	}
	if (registerEmail.value.length < 3) {
		registerEmail.className = ' earthquake';
		registerEmail.className += ' animation-wrong';
		arr[4] = 0;
	}
	if (registerGender.value == 'None') {
		registerGender.className += ' earthquake';
		arr[5] = 0;
	}
	setTimeout(resetEffect, 1000);
	for (let i = 0; i < arr.length; i++) { if (arr[i] == 0) return false; }
	return true;
}
function resetEffect() {

	registerFirstName.classList.remove('earthquake');
	registerLastName.classList.remove('earthquake');
	registerEmail.classList.remove('earthquake');
	registerPassword.classList.remove('earthquake');
	registerConfirmPassword.classList.remove('earthquake');
	registerGender.classList.remove('earthquake');
}
function resetControl() {
	registerFirstName.value = '';
	registerLastName.value = '';
	registerEmail.value = '';
	registerPassword.value = '';
	registerConfirmPassword.value = '';

}
