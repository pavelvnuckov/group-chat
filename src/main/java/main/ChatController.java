package main;

import main.dto.DtoMessage;
import main.dto.DtoUsers;
import main.dto.MessageMap;
import main.dto.UserMap;
import main.model.Message;
import main.model.MessageRepository;
import main.model.User;
import main.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    //При загрузке сайта проверяет пользователь авторизован?
    @GetMapping("/init")
    public HashMap<String, Boolean> init() {
        HashMap map = new HashMap<>();
        //Получение сессии текущей
        String sessionId = RequestContextHolder.currentRequestAttributes()
                .getSessionId();
        Iterable<User> userIterator = userRepository.findAll();
        for (User u : userIterator) {
            //Сравнение текущей сессии и авторизованных пользователей
            if (u.getSessionId().equals(sessionId)) {
                map.put("result", true);
                return map;
            }
        }
        map.put("result", false);
        return map;
    }

    //Авторизация пользователя
    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(String name) {
        HashMap map = new HashMap<>();
        map.put("result", true);
        User user = new User();
        user.setName(name);
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        user.setSessionId(sessionId);
        userRepository.save(user);
        return map;
    }

    //Запись нового сообщения
    @PostMapping("/message")
    public HashMap<String, Boolean> setMessage(String message) {
        HashMap map = new HashMap<>();
        String sessionId = RequestContextHolder.currentRequestAttributes()
                .getSessionId();
        Iterable<User> userIterator = userRepository.findAll();
        User user = null;
        //Определяем от какого пользователя сообщение
        for (User u : userIterator) {
            if (u.getSessionId().equals(sessionId)) {
                user = u;
            }
        }
        Message message1 = new Message();
        message1.setMessage(message);
        message1.setDateTime(LocalDateTime.now());
        if (user == null) {
            map.put("result", false);
            return map;
        } else {
            message1.setUser(user);
            messageRepository.save(message1);
            map.put("result", true);
            return map;
        }
    }

    //Вывод сообщений на экран
    @GetMapping("/message")
    public List<DtoMessage> getMessageList() {
        List<DtoMessage> list = new ArrayList<>();
//Получаем сообщения упорядоченные по дате
        Iterable<Message> messages = messageRepository
                .findAll(Sort.by(Sort.Direction.ASC, "dateTime"));
//Приводим все сообщения к String для этого класс DtoMessage
        for (Message m : messages) {
            DtoMessage dtoMessage = MessageMap.mapMessage(m);
            list.add(dtoMessage);
        }
        return list;
    }

    //Вывод имён пользователей на экран
    @GetMapping("/users")
    public List<DtoUsers> getUsers() {
        List<DtoUsers> list = new ArrayList<>();
        Iterable<User> userIterable = userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        for (User u : userIterable) {
            DtoUsers dtoUsers = UserMap.mapUsers(u);
            list.add(dtoUsers);
        }
        return list;
    }
}
