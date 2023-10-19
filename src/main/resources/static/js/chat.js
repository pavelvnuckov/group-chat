$(function(){

    let getMessageElement = function(message) {
        let item = $('<div class="message-item"></div>');
        let header = $('<div class="message-header"></div>');
        header.append($('<div class="datetime">' + message.datetime + '</div>'));
        header.append($('<div class="username">' + message.username +'</div>'));
        let textElement = $('<div class="message-text"></div>');
        textElement.text(message.text);
        item.append(header, textElement);
        return item;

    };

    let updateMessages = function() {
        $.get('/message', {}, function(response) {

            if(response == 0) {
                return;
            }
            $('.messages-list').html('');
            for (i in response) {

                let element = getMessageElement(response[i]);
                $('.messages-list').append(element);
            }
        });
    };

    let initApplication = function() {
        $('.messages-and-users').css({display: 'flex'});
        $('.control').css({display: 'flex'});
        //При нажатии на кнопку отправить
        $('.send-message').on('click', function(){
        //Берём содержимое поля new-messages
        let message = $('.new-messages').val();
        //Отправляем post запрос с телом message (сообщение)
            $.post('/message', {message: message}, function(response){
                //Если сообщение отправлено,
                //то очищаем поле для новых сообщений
                if(response.result) {
                    $('.new-messages').val('');
                }
                else {
                    alert('Ошибка: Повторите попытку позже!');
                }
            });
        });
        //Вызываем функции для отображения сообщений и пользователей
        setInterval(updateMessages, 1000);
        setInterval(updateUsers, 1000);
    };
//Регистрация пользователя
    let registerUser = function(name) {
    //Отправление post запроса с телом name
        $.post('/auth', {name: name}, function(response){
        //Если регистрация прошла успешно отображается чат
            if(response.result) {
                initApplication();
            }
        });
    };
        //Делается запрос зарегистрирован пользователь или нет
        $.get('/init', {}, function(response){
        // Если не зарегистрирован
            if(!response.result)
            {
                let name = prompt('Введите своё имя:');
                registerUser(name);
                return;
            }
            //Если зарегистрирован, то отображается поля
            initApplication();
    });
//Получение пользователя и добавление его в форму
    let getUserElement = function(user) {
        let userHeader = $('<div class="user-header">' + user.userName + '</div>');
        return userHeader;
    };
//Вывод пользователе в поле
    let updateUsers = function() {
    //Получаем список пользователей
        $.get('/users', {}, function(response) {

            if (response == 0) {
                return;
            }
            //Очищается поле с пользователями
            $('.users-list').html('');
            for(i in response) {
            //Добавление пользователей в фому
                let name = getUserElement(response[i]);
                //Вывод пользователей в поле
                $('.users-list').append(name);
            }
        });
    };
});