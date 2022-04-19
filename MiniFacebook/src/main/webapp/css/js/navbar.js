let btnProfilBar = document.getElementById('btn-navbar-profil');
let btnSettings = document.getElementById('btn-navbar-settings');

let btnLogout = document.getElementById('logout');

let barSearch = document.getElementById('bar-search');
let inputSearch = document.getElementById('search-friend');
let btnSearch = document.getElementById('btn-search');
let btnHome = document.getElementById('btn-home');
btnHome.style.cursor='pointer';

btnHome.addEventListener('click',()=>{
    localStorage.removeItem("otherID");
    window.location.replace('http://localhost:8080/Test/home.html');
})
btnProfilBar.addEventListener('click',()=>{
    localStorage.removeItem("otherID");
    window.location.replace('http://localhost:8080/Test/profil.html');
})
btnSearch.addEventListener('click',()=>{
    if(inputSearch.value.length == 0){
        barSearch.className += ' earthquake';
        setTimeout(()=>{
            barSearch.className = 'search';
        },1000);
    }else{
        localStorage.setItem('search',inputSearch.value);
        window.location.replace('http://localhost:8080/Test/search.html');
    }
})
btnProfilBar.addEventListener('click',()=>{
    let page = {
        page:"profil"
    }
    $.ajax({
    type: "GET",
    url: "Page",
    data: page,
    success: function(msg){
        window.location.replace('http://localhost:8080/Test/' + msg);
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert("some error");
        }
    });
})
btnSettings.addEventListener('click',()=>{
    btnLogout.style.display = "block"
})
btnLogout.addEventListener('click',()=>{
    $.ajax({
    type: "GET",
    url: "Logout",
    success: function(msg){
        localStorage.removeItem("otherID");
        window.location.replace('http://localhost:8080/Test/' + msg);
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert("some error");
        }
    });
    btnLogout.style.display = "none"
})


