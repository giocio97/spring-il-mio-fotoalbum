fotoList();

function fotoList(){
    axios.get(`http://localhost:8080/api/foto`)
    .then((result) =>{
        const fotoList = result.data;
        document.querySelector('#foto-card').innerHTML+``;
        fotoList.forEach(foto => {
            document.querySelector('#foto-card').innerHTML+=`
            
              <div class="col">
              <div class="card h-100">
                <img src="${foto.urlFoto}" class="card-img-top" alt="${foto.titolo}">
                <div class="card-body">
                  <h5 class="card-title">${foto.titolo}</h5>
                 <a href="/dettaglio?id=${foto.id}" class="btn btn-primary">Mostra Dettaglio</a>
                </div>
                
              </div>
              `;
        });
    }).catch((result) => {
        console.error('Errore nella richiesta', result);
        alert('Errore durante la richiesta');
    })
}