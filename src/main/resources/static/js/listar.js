const tbody = document.getElementsByTagName("tbody")[0];


window.addEventListener('load', async ()=>{

    const response = await fetch("http://localhost:8081/odontologos/listar");
    const odontologoLista = await response.json()

    listarOdontologos(odontologoLista)

} )

function listarOdontologos(lista){

    if(lista.length > 0){

        
        lista.forEach(odontologo => {
            tbody.innerHTML += `
            <tr>
            <th scope="row">${odontologo.id}</th>
            <td>${odontologo.matricula}</td>
            <td>${odontologo.nombre}</td>
            <td>${odontologo.apellido}</td>
            </tr>
            `
        });
    }else{
        tbody.innerHTML = `<td>No hay odontologos registrados.</td>`
    }


}