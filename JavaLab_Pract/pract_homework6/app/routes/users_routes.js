const pg = require('pg');
const config = {
    user: 'postgres', database: 'homework8', password: '07072001', port: 5432
};
const pool = new pg.Pool(config);
module.exports = function (app) {
    app.get('/users', (request, response) => {
        const result = [{
            "id": 1,
            "name": "Аня",
            "surname": "Будревич",
            "description": "<p>Студентка Высшей Школы Информационных Технологий и Интеллектуальных Систем КФУ</p>" +
                    "<p>Закончила первый курс на хорошо и отлично</p>" +
                    "<p>В детстве занималась плаванием, музыкой(в том числе и пением), театральным исскуством, танцами</p>" +
                    "<p>Сбылась моя мечта!Я попала в JavaLab!!!И теперь я счааааастлива:D</p>" +
                    "<p>Люблю програмировать,а программировать не очень любит меня;)</p>" 
        }];
        response.setHeader("Content-Type", "application/JSON");
        response.send(JSON.stringify(result));
    });
    app.get('/love', function(request, response, next) {
        pool.connect(function(error, client, done){
            if(error){
                return next(error)
            }
            client.query('select * from users', [], function(err, result){
                done()
                if(err){
                    return next(err);
                }
                response.json(result.rows)
            })
        })
    })
    app.post('/love', function (req, res, next) {
        const user = req.body;
        pool.connect(function (err, client, done) {
            if (err) {
                return next(err)
            }
            client.query('INSERT INTO users (name, email, surname) VALUES ($1, $2, $3);', [user.name, user.email, user.surname], function (err, result) {
                done()
                if (err) {
                    return next(err)
                }
                res.sendStatus(200)
            })
        })
    });
    
}