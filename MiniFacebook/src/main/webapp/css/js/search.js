let middle = document.getElementById('container');
if(localStorage.getItem('search') != null){
    let body = document.getElementsByTagName('body');
    $.ajax({
    type: "GET",
    url: "Search",
    data: {search : localStorage.getItem('search')},
    success: function(msg){
        var obj = JSON.parse(msg);
        for(var key in obj){
            middle.appendChild(createTemplate(obj[key]));
        }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert("some error");
        }
    });
}


function removeCookie(){
    let arr = document.cookie.split(' ');
    document.cookie = arr[0];
}
function createTemplate(object){
    let grandParent = document.createElement('div');
    grandParent.className  = 'result';
    let parent1 = document.createElement('div');
    parent1.className = 'result-img';
    let parent2 = document.createElement('div');
    parent2.className = 'result-details';
    let parent3 = document.createElement('div');
    parent3.className = 'btn-result';
    let img = document.createElement('img');
    img.className = 'result-img-profil';
    img.src = object.urlprofil;
    let username = document.createElement('h2');
    username.innerText = object.firstname + " " + object.lastname;
    let p = document.createElement('p');
    p.style.display = "none";
    p.innerText = object.id;
    let p1 = document.createElement('p');
    p1.innerText = 'Location '+object.numecity;
    let p2 = document.createElement('p');
    p2.innerText = 'Age ' + object.dateofbirth;
    let p3 = document.createElement('p');
    p3.innerText = 'Studies ' + object.studies;
    let parent4 = document.createElement('div');
    parent4.className = 'result-info';
    let btn1 = document.createElement('button');
    btn1.innerText = 'Add Friend';
    let btn2 = document.createElement('button');
    btn2.innerText = 'Follow';
    let btn3 = document.createElement('button');
    btn3.innerText = 'View Profile';
    btn3.addEventListener('click',()=>{
        localStorage.setItem("otherID",btn3.parentNode.parentNode.childNodes[1].childNodes[1].childNodes[0].innerText);
        window.location.replace('http://localhost:8080/Test/profil.html');
    });
    parent4.appendChild(p);
    parent4.appendChild(p1);
    parent4.appendChild(p2);
    parent4.appendChild(p3);
    
    parent3.appendChild(btn1);
    parent3.appendChild(btn2);
    parent3.appendChild(btn3);
    
    parent2.appendChild(username);
    parent2.appendChild(parent4);
    
    parent1.appendChild(img);
    
    grandParent.appendChild(parent1);
    grandParent.appendChild(parent2);
    grandParent.appendChild(parent3);
    return grandParent;
    
}

