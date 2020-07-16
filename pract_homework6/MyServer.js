const express = require('express');
const app = express();
app.use(express.static('public'));
app.listen(12228);
console.log("Server started at 12228");