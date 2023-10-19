package main.dto;

//Класс для отправки данных о пользователе
public class DtoUsers {

    private String userName;

    public DtoUsers(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
