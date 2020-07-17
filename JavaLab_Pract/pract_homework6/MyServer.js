const express = require('express');
const bodyParser = require('body-parser');
const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
require('./app/routes')(app);
app.use(express.static('public'));
app.listen(12228);
console.log("Server started at 12228");