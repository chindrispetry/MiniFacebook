let btnCopert = document.getElementById('add-copert');

let btnProfil = document.getElementById('add-profil');

let btnNewPost = document.getElementById('btn-post');
let btnDetails = document.getElementById('btn-update');
let btnCancelPost = document.getElementById('btn-anuleaza-post');

let btnPost = document.getElementById('btn-post-new');
let btnCancelDetails = document.getElementById('btn-anuleaza-details');

let menuPost = document.getElementById('menu-post');
let menuDetails = document.getElementById('menu-details');

let menuBackground = document.getElementById('menu-background');
let menuPostBackground = document.getElementById('post-background');

let inputFile = document.getElementById('add-file-post');
let btnAddImg = document.getElementById('btn-add-img-post');
let preview = document.getElementById('img-post');
  
let backImage = document.getElementById('background-image');

let inputDescription = document.getElementById('description-post');
let alertError = document.getElementById('error');

let favday = document.getElementById('favday');
let favmusic = document.getElementById('favmusic');
let favfood = document.getElementById('favfood');
let dateofbirth = document.getElementById('dateofbirth');
let locat = document.getElementById('locat');
let studies = document.getElementById('studies');

let addFriend = document.getElementById('btn-add-friend');
let follow = document.getElementById('btn-follow');
let templateFriends = document.getElementById('template-friends');

follow.addEventListener('click',()=>{
    let session={
            follow:localStorage.getItem('otherID'),
            user:localStorage.getItem('user')
        }
        $.ajax({
        type: "GET",
        url: "ManagerFollowrs",
        data: session,
        success: function(msg){console.log(msg)},
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("some error");
            }
        });
});
var nume = '';
let valid = false;
inputFile.onchange = ()=>{
  const file = inputFile.files[0];
  const reader = new FileReader();
    reader.addEventListener("load", function () {
       preview.src = reader.result;
       valid = true;
    }, false);
  if (file) {
    reader.readAsDataURL(file);
  }
}
btnAddImg.addEventListener('click',()=>{
  inputFile.click();
})
btnPost.addEventListener('click',async ()=>{
    if(inputDescription.value.length > 0 || valid){
        valid = false;
        let cookies = document.cookie.split(' ');
        let value = cookies[0].split('=');
        let post={
            id : value[1],
            description:inputDescription.value,
            name:nume,
            date:'27.05.2021'
        }
        let str = JSON.stringify(post);
        let formData = new FormData();
        formData.append("post",str);
        formData.append("photo", inputFile.files[0]);
        await fetch('UploadImage', {
          method: "POST", 
          body: formData
        })
        window.location.replace('http://localhost:8080/Test/profil.html');
    }else{
        alertError.innerText='wrong operation';
        setTimeout(()=>{alertError.innerText = '';},1000)
    }
    
})
function showPostMenu(){
    menuPostBackground.style.display = "block";
    menuPost.style.display = "block";
}
btnCopert.addEventListener('click',()=>{
    showPostMenu();
    nume='copert';
});
btnProfil.addEventListener('click',()=>{
    showPostMenu();
    nume='profil';
})
btnNewPost.addEventListener('click',()=>{
    showPostMenu();
    nume='post';
})
btnDetails.addEventListener('click',()=>{
    menuBackground.style.display = "block";
    menuDetails.style.display = 'block';
    completeAutomateDetails();
})
btnCancelPost.addEventListener('click',()=>{
    menuPost.style.display = "none";
    menuPostBackground.style.display = "none";
    preview.src = '';
})
btnCancelDetails.addEventListener('click',()=>{
    menuDetails.style.display = "none";
    menuBackground.style.display = "none";
})

let arr = document.cookie.split(' ');
let presentUser = arr[0].split('=');
let otherUser;

addDataProfil();

function addDataProfil(){
    if(localStorage.getItem('otherID') == null){
        let session={
            id:localStorage.getItem('user')
        }
        $.ajax({
        type: "POST",
        url: "Profil",
        data: session,
        success: function(msg){
            completeProfil(msg);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("some error");
            }
        });
    }
    else{
        offButton();
        onButton();
        let session={
            id:localStorage.getItem('otherID')
        }
        $.ajax({
        type: "POST",
        url: "Profil",
        data: session,
        success: function(msg){
            completeProfil(msg);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("some error");
            }
        });
    }
}
function offButton(){
    btnCopert.style.display = 'none';
    btnNewPost.style.display = 'none';
    btnProfil.style.opacity = '0';
    btnDetails.style.display = 'none';
}
function onButton(){
    addFriend.style.display = 'block';
    follow.style.display = 'block';
}
function completeProfil(msg){
    var obj = JSON.parse(msg);
    completeDetails(obj.details,obj.location);
    completeUser(obj.profil);
    createPictures(obj.profil);
    let container = document.getElementById('container-post');
    for(var key in obj.postari){
        container.appendChild(createPost(obj.postari[key],obj.profil));
    }
    for(var key in obj.friends){
        templateFriends.appendChild(createRecomandari(obj.friends[key]));
    }
}
function createPost(post,user){
    let grand = document.createElement('div');
    grand.className = 'postari';
    let p  = document.createElement('p');
    p.style.display = 'none';
    p.innerText = post.idpostare;
    let btndelete = document.createElement('delete');
    btndelete.className = 'delete-post';
    btndelete.innerText = 'X';
    btndelete.addEventListener('click',()=>{
        let post = {
            idpostare:btndelete.parentElement.childNodes[0].innerText
        }
        console.log(post.idpostare);
        $.ajax({
        type: "POST",
        url: "Delete",
        data: post,
        success: function(msg){
            window.location.reload();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("postarea nu a putut fi stearsa");
            }
        });
    })
    let parent = document.createElement('div');
    parent.className = 'card-user';
    
    let imgUser = document.createElement('img');
    imgUser.className = 'user-img';
    imgUser.src = user.urlprofil;
    
    let userName = document.createElement('h4');
    userName.innerText = user.firstname + " " + user.lastname;
    
    let parent1 = document.createElement('div');
    parent1.innerText = 'Date post :' + post.datepost;
    
    let parent2 = document.createElement('div');
    parent2.className = 'card-description';
    parent2.innerText = post.description;
    
    let parent3 = document.createElement('div');
    parent3.className = 'card-img';
    let imgPost = document.createElement('img');
    imgPost.src = post.imageurl;
    imgPost.className = 'img-dimension';
    let parent4 = document.createElement('div');
    parent4.className = 'card-footer';
    
    let parent5 = document.createElement('div');
    parent5.className = 'option option-border-left';
    
    let imgLike = document.createElement('img');
    imgLike.className = 'icon-option';
    imgLike.src = 'img/like.png';
    
    let parent6 = document.createElement('div');
    parent6.className = 'option';
    
    let imgComment = document.createElement('img');
    imgComment.className = 'icon-option';
    imgComment.src = 'img/comment.png';
    
    let parent7 = document.createElement('div');
    parent7.className = 'option option-border-right';
    
    let imgForward = document.createElement('img');
    imgForward.className = 'icon-option';
    imgForward.src = 'img/forward.png';
    
    parent7.appendChild(imgForward);
    parent6.appendChild(imgComment);
    parent5.appendChild(imgLike);
    parent4.appendChild(parent5);
    parent4.appendChild(parent6);
    parent4.appendChild(parent7);
    parent3.appendChild(imgPost);
    parent.appendChild(p);
    parent.appendChild(imgUser);
    parent.appendChild(userName);
    parent.appendChild(btndelete);
    grand.appendChild(parent);
    grand.appendChild(parent1);
    grand.appendChild(parent2);
    grand.appendChild(parent3);
    grand.appendChild(parent4);
    return grand;
}
function completeDetails(details,location){
    let table = document.getElementById('table-details').childNodes[1];
    table.childNodes[0].childNodes[3].innerText = details.bestd;
    table.childNodes[2].childNodes[3].innerText = details.bestm;
    table.childNodes[4].childNodes[3].innerText = details.bestf;
    table.childNodes[6].childNodes[3].innerText = details.dateofbirth;
    table.childNodes[8].childNodes[3].innerText = details.friends;
    table.childNodes[10].childNodes[3].innerText =  location.City;
    table.childNodes[12].childNodes[3].innerText = details.studies;
}
function completeUser(profil){
    let profilIMG = document.getElementById('user-profil-img');
    let copert = document.getElementById('user-copert-img');
    let username = document.getElementById('name-user');
    username.innerText = profil.firstname  + " " + profil.lastname;
    profilIMG.src = profil.urlprofil;
    copert.src = profil.urlcopert;
}
function createPictures(profil){
    let parent = document.getElementById('pictures');
    let grand = document.createElement('div');
    grand.className = 'picture-inline';
    let parent1 = document.createElement('div');
    let imgPictures = document.createElement('img');
    imgPictures.src = profil.urlprofil;
    imgPictures.className = 'picture-profil-img';
    parent1.appendChild(imgPictures);
    let parent2 = document.createElement('div');
    let imgCopert = document.createElement('img');
    imgCopert.src = profil.urlcopert;
    imgCopert.className = 'picture-profil-img';
    parent2.appendChild(imgCopert);
    grand.appendChild(parent1);
    grand.appendChild(parent2);
    parent.appendChild(grand);
}
function completeAutomateDetails(){
    let table = document.getElementById('table-details').childNodes[1];
    favday.value = table.childNodes[0].childNodes[3].innerText;
    favmusic.value = table.childNodes[2].childNodes[3].innerText;
    favfood.value =table.childNodes[4].childNodes[3].innerText;
    dateofbirth.value = table.childNodes[6].childNodes[3].innerText;
    locat.value = table.childNodes[10].childNodes[3].innerText;
    studies.value = table.childNodes[12].childNodes[3].innerText;
}
function createRecomandari(user){
    let grand = document.createElement('div');
    grand.className = 'friends';
    let img = document.createElement('img');
    img.className = 'img-friends';
    img.src = user.urlprofil;
    let parent = document.createElement('div');
    parent.className = 'info-new-friends';
    let p = document.createElement('p');
    p.innerText = user.id;
    p.style.display = "none";
    let p1 = document.createElement('p');
    p1.innerText = user.firstname + " " + user.lastname;
    let p2 = document.createElement('p');
    p2.innerText = user.dateofbirth;
    let p3 = document.createElement('p');
    p3.innerText = user.numecity;
    let btn = document.createElement('button');
    btn.innerText = 'View profil';
    btn.addEventListener('click',()=>{
        console.log(btn.parentElement.childNodes[0]);
        localStorage.setItem("otherID",btn.parentElement.childNodes[0].innerText);
        window.location.replace('http://localhost:8080/Test/profil.html');
        
    })
    parent.appendChild(p);
    parent.appendChild(p1);
    parent.appendChild(p2);
    parent.appendChild(p3);
    parent.appendChild(btn);
    grand.appendChild(img);
    grand.appendChild(parent);
    return grand;  
}
function removeCookie(){
    let arr = document.cookie.split(' ');
    document.cookie = arr[0];
}
let UpdateDetails = document.getElementById('update-details');

UpdateDetails.addEventListener('click',()=>{
    let valueDOB = '';
    if(dateofbirth.value.length < 1){
        valueDOB = 'none';
    }else{
        valueDOB = dateofbirth.value;
    }
    let details = {
        id:presentUser[1],
        favday:favday.value,
        favmusic:favmusic.value,
        favfood:favfood.value,
        dateofbirth:valueDOB,
        location:locat.value,
        studies:studies.value
    }
    $.ajax({
    type: "POST",
    url: "Details",
    data: details,
    success: function(msg){
        completeProfil(msg);
        alert(msg);
        removeCookie();
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert("some error");
        removeCookie();
        }
    });
})