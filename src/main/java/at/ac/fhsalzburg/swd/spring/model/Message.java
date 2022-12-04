package at.ac.fhsalzburg.swd.spring.model;

import at.ac.fhsalzburg.swd.spring.model.ids.MessageId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.security.Timestamp;

@Entity
public class Message {

    @EmbeddedId
    private MessageId messageId;
    private String text;
    private Timestamp timeSent;

    public Message() {}

    public Message(MessageId messageId, String text, Timestamp timeSent) {
        this.messageId = messageId;
        this.text = text;
        this.timeSent = timeSent;
    }

    public MessageId getMessageId() {
        return messageId;
    }

    public void setMessageId(MessageId messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }
}

