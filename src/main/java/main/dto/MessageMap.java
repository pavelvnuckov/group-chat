package main.dto;

import main.model.Message;

//Класс для изменения полей в String для сообщений
public class MessageMap {

    public static DtoMessage mapMessage(Message message) {
        DtoMessage dtoMessage = new DtoMessage();
        dtoMessage.setDatetime(message.getDateTime().toString());
        dtoMessage.setUsername(message.getUser().getName());
        dtoMessage.setText(message.getMessage());
        return dtoMessage;
    }
}
