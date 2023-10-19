package main.dto;

import main.model.User;

//Класс для изменения полей в String для пользователей
public class UserMap {

    public static DtoUsers mapUsers(User user) {
        DtoUsers dtoUsers = new DtoUsers(user.getName());
        return dtoUsers;
    }
}
