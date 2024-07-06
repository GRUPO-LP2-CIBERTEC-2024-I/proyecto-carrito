const BASE_URL = "http://47f0-2800-200-e0e0-2fe-dcc9-6214-9394-2091.ngrok-free.app/Producto/list"; // Asegura que la URL incluya el protocolo

$(document).ready(function() {
    $.ajax({
        url: BASE_URL,
        method: 'GET',
        success: function(response) {
            console.log('Response:', response); // Imprime la respuesta en la consola
            try {
                const data = JSON.parse(response);
                if (Array.isArray(data)) {
                    let productosContainer = $('#productos-container');
                    productosContainer.empty(); // Limpiar cualquier contenido existente

                    data.forEach(function(producto) {
                        let productHtml = `
                            <div class="carts">
                                <div>
                                    <img src="${producto.imagen}" alt="">
                                    <p>S/.<span>${producto.precioUnidad}</span></p>
                                </div>
                                <p class="title">${producto.descripcion}</p>
                                <a href="" class="btn-agregar-carrito" data-id="${producto.idProducto}">Agregar</a>
                            </div>
                        `;
                        productosContainer.append(productHtml);
                    });
                } else {
                    console.error('Unexpected response format:', data);
                }
            } catch (error) {
                console.error('Error parsing response:', error);
            }
        },
        error: function(xhr, status, error) {
            console.error('Error fetching productos:', error);
        }
    });
});
