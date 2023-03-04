

const URLParams = new URLSearchParams(window.location.search);
const fotoId = URLParams.get('id');



fotoDetail();

function fotoDetail() {
    axios.get(`http://localhost:8080/api/foto/${fotoId}`)
        .then((result) => {

            const foto = result.data;
            

            document.querySelector('#titolo').innerHTML = `${foto.titolo}`;
            document.querySelector('#foto-img').src = `${foto.urlFoto}`;
            document.querySelector('#descrizione').innerHTML = `${foto.descrizione}`;
            document.querySelector('#tag').innerHTML = `${foto.tag}`;

            

            

            foto.categorie.forEach(categoria => {
                document.querySelector('#categorie').innerHTML += `
               ${categoria.nome}
                `;
            });


           

        }).catch((result) => {
            console.error('Errore nella richiesta', result);
            alert('Errore durante la richiesta');
        })
};

function commentiList() {
    axios.get(`http://localhost:8080/api/foto/${fotoId}/comments`)
    .then((res) =>{
        const commentiList = res.data;
        document.querySelector('#comments-list').innerHTML+``;
        commentiList.forEach(commento => {
            document.querySelector('#comments-list').innerHTML+=`
            
            <li class="list-group-item d-flex justify-content-between align-items-start">
            <div class="ms-2 me-auto">
              <div class="fw-bold">${commento.username}</div>
              <p>${commento.content}</p> 
            </div>
            
          </li>
              `;
        });
    }).catch((res) => {
        console.error('Errore nella richiesta', res);
        alert('Errore durante la richiesta');
    })
}

commentiList();

function creaCommento(fotoId) {
   
        
       let username = document.querySelector('#username').value;
        let content = document.querySelector('#content').value;
       
   
  
    axios.post(`http://localhost:8080/api/foto/${fotoId}/comments`,{
        username: username,
        content: content
      }) .then(res => {
        console.log(res.data);
        document.querySelector('#username').value = '';
        document.querySelector('#content').value = '';
        commentiList(photoId);
        

    }).catch((err) => {
        console.log("errore", err)
    })
};