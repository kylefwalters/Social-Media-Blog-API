package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.*;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::postRegisterHandler);
        app.post("login", this::postLoginHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages/{message_id}", this::getMessageHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::patchMessageHandler);
        app.get("accounts/{account_id}/messages", this::getAccountMessagesHandler);

        return app;
    }

    /**
     * Creates a new account and auto-generate an account_id, and adds it to the database
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        AccountService accountService = new AccountService();

        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null) {
            context.json(addedAccount);
        } else{
            context.status(400);
        }
    }

    /**
     * Verifies login credentials are valid
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        AccountService accountService = new AccountService();

        Account foundAccount = accountService.getAccount(account);
        if(foundAccount != null) {
            context.json(foundAccount);
        } else{
            context.status(401);
        }
    }

    /**
     * Gets all messages from database
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context) throws JsonProcessingException {
        MessageService messageService = new MessageService();

        List<Message> foundMessages = messageService.getAllMessages();
        context.json(foundMessages);
    }

    /**
     * Posts a message to the database
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        MessageService messageService = new MessageService();

        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null) {
            context.json(addedMessage);
        } else{
            context.status(400);
        }
    }

    /**
     * Posts a message to the database
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageHandler(Context context) throws JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        MessageService messageService = new MessageService();

        Message message = messageService.getMessage(message_id);
        if(message != null) {
            context.json(message);
        }
    }

    /**
     * Posts a message to the database
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageHandler(Context context) throws JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        MessageService messageService = new MessageService();

        Message message = messageService.deleteMessage(message_id);
        if(message != null) {
            context.json(message);
        }
    }

    /**
     * Updates the message_text of an existing message
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void patchMessageHandler(Context context) throws JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        MessageService messageService = new MessageService();

        Message updatedMessage = messageService.updateMessage(message_id, message.getMessage_text());
        if(updatedMessage != null) {
            context.json(updatedMessage);
        } else {
            context.status(400);
        }
    }

    /**
     * Gets all messages by user
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAccountMessagesHandler(Context context) throws JsonProcessingException {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        MessageService messageService = new MessageService();

        List<Message> foundMessages = messageService.getAllMessagesForUser(account_id);
        context.json(foundMessages);
    }
}