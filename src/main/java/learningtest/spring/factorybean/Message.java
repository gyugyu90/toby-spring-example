package learningtest.spring.factorybean;

public class Message {

    private String text;

    // 생성자가 private로 되어있어서 외부에서 생성자 호출 불가능
    private Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    // 대신 사용할 수 있는 static factory method를 제공한다.
    public static Message newMessage(String text) {
        return new Message(text);
    }
}
