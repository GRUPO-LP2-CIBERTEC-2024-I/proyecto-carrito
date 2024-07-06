const express = require('express');
const path = require('path');

const app = express();
const router = express.Router();

// Ruta para servir archivos estáticos
app.use('/front', express.static(path.join(__dirname, '')));

// Ruta principal que envía el archivo index.html
router.get('/', function(req, res) {
    res.sendFile(path.join(__dirname, '/index.html'));
});

// Usar el enrutador principal
app.use('/', router);

// Iniciar el servidor en el puerto 3000
app.listen(3000, () => {
    console.log('Servidor front-end está corriendo en el puerto 3000');
});
