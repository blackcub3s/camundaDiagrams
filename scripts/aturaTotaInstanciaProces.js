// Hacer una solicitud GET al endpoint 
// de Camunda para obtener instancias de proceso
fetch('https://camunda-tdgov.laberit.com/engine-rest/process-instance?processDefinitionKey=idDiagramaDMN')
  .then(response => {
    // Verificamos si la respuesta fue exitosa (código 200–299)
    if (!response.ok) {
      throw new Error(`Error en la solicitud: ${response.status} ${response.statusText}`);
    }
    return response.json(); //converteixo resposta a jsons
  })
  .then(data => {
    console.log('Instancias de proceso obtenidas:', data); //mostro dades a consola
  


})
  .catch(error => {
    console.error('Ocurrió un error al obtener las instancias de proceso:', error);
  });
