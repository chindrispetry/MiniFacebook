var templateFriends = document.getElementById('recomandari');
var containerPost = document.getElementById('container-post');
var containerFriends = document.getElementById('template-follow');
var user = {id : localStorage.getItem('user')}
var utilizator = {
    
}
$.ajax({
    type: "POST",
    url: "Home",
    data: user,
    success: function(msg){
        var json = JSON.parse(msg);
        for(var key in json){
            if(key.indexOf('user') !== -1){
                //containerPost.appendChild(createPost(json[key]))
                containerFriends.appendChild(createFollow(json[key]));
            }
            if(key.indexOf('post') !== -1){
                for(var k in json){
                    if(k.indexOf('user') !== -1){
                        if(json[k].id == json[key].iduser){
                            containerPost.appendChild(createPost(json[key],json[k]));
                        }
                    }
                }
            }
        }
        var recomandari = json.recomandari;
        for(var key in recomandari){
            templateFriends.appendChild(createRecomandari(recomandari[key]));
        }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert("some error");
        }
    });
function createFollow(user){
    let grand = document.createElement('div');
    grand.className = 'mini-card';
    let img = document.createElement('img');
    img.className =  'user-online';
    img.src = user.urlprofil;
    let userName = document.createElement('p');
    userName.className = 'username';
    userName.innerText = user.firstname + " " + user.lastname;
    let status = document.createElement('p');
    status.className = 'status';
    status.innerText = 'offline';
    grand.appendChild(img);
    grand.appendChild(userName);
    grand.appendChild(status);
    return grand;
}
function createPost(post,user){
    let grand = document.createElement('div');
    grand.className = 'postari';
    
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
    parent.appendChild(imgUser);
    parent.appendChild(userName);
    
    grand.appendChild(parent);
    grand.appendChild(parent1);
    grand.appendChild(parent2);
    grand.appendChild(parent3);
    grand.appendChild(parent4);
    return grand;
}
function createRecomandari(user){
    let grand = document.createElement('div');
    grand.className = 'card-new-friends';
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

