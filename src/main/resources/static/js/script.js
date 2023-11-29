//variables globales
const form = document.forms[0]
const matricula = document.getElementById("matricula")
const nombre = document.getElementById("nombre")
const apellido = document.getElementById("apellido")
const cardResponse = document.getElementById("card-response")


const url = "http://localhost:8081"

form.addEventListener('submit', function (event) {
    event.preventDefault();
    const payload = {
        matricula: matricula.value,
        nombre: nombre.value, 
        apellido: apellido.value,
    };
    const settings = {
        method: 'POST',
        body: JSON.stringify(payload),
        headers: {
            'Content-Type': 'application/json'
        }
    };
    //lanzamos la consulta de login a la API
    registrarOdontologo(settings);

    //limpio los campos del formulario
    form.reset();
});

function registrarOdontologo(settings) {
    console.log("Lanzando la consulta a la API");
    fetch(`${url}/odontologos/registrar`, settings)
        .then(response => {
            console.log(response);

            if (response.ok != true) {
                alert(`¡Error! Status: ${response.status}`)
            }

            return response.json();

        })
        .then(data => {
            console.log("Promesa cumplida:");
            console.log(data);

            
        }).catch(err => {
            console.log("Promesa rechazada:");
            console.log(err);
        })
};

function displayOdontologo(data){
const odontologo_id = document.getElementById("matricula_display")
const matricula_display = document.getElementById("matricula_display")
const nombre_display = document.getElementById("nombre_display")
const apellido_display = document.getElementById("apellido_display")

odontologo_id.innerHTML = data.

cardResponse.style.display = "block"

}