package Service;

import java.util.List;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
    * Searches database for message with matching message_id
    * @param message_id id of the message to search for
    * @return returns message that matches message_id, if one is not found returns null instead
    */
    public Message getMessage(int message_id) {
        return messageDAO.getMessage(message_id);
    }

    /**
    * Retrieves all messages posted by account_id
    * @param account_id id of the account messages are posted by
    * @return returns list of messages sent by account
    */
    public List<Message> getAllMessagesForUser(int account_id) {
        return messageDAO.getAllMessagesForUser(account_id);
    }

    /**
    * Retrieves all messages from database
    * @return returns list of messages sent by account
    */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
    * Adds message to dsatabase if message is not blank, no longer than 255 characters, and posted_by refers to a real user
    * @param message the message to be added to the databse
    * @return returns message with new message_id if successfully added to databse, returns null otherwise
    */
    public Message addMessage(Message message) {
        if(!messageIsValid(message)) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    /**
    * Removes message from database with matching message_id
    * @param message_id id of the message to search for
    * @return returns message that matches message_id, if one is not found returns null instead
    */
    public Message deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

    /**
    * Updates message with matching message_id
    * @param message_id id of the message to search for
    * @return returns message that matches message_id with updated message_text, if one is not found returns null instead
    */
    public Message updateMessage(int message_id, String message_text) {
        if(!messageIsValid(message_text)) {
            return null;
        }
        return messageDAO.updateMessage(message_id, message_text);
    }

    private boolean messageIsValid(Message message) {
        return messageIsValid(message.getMessage_text());
    }

    private boolean messageIsValid(String message_text) {
        // since the database will verify if posted_by refers to a real account_id, there is no need to validate here
        return message_text != "" && message_text.length() <= 255;
    }
}
