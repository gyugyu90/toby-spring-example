package user.dao;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(connectionMaker()); // 팩토리 메소드에서 생성 로직을 결정
    }

    public AccountDao accountDao() {
        return new AccountDao(connectionMaker());
    }

    public MessageDao messageDao() {
        return new MessageDao(connectionMaker());
    }

    private ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

}
