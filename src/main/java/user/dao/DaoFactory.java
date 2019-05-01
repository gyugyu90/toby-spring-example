package user.dao;

public class DaoFactory {

    public UserDao userDao() {
        ConnectionMaker connectionMaker = new DConnectionMaker(); // 팩토리 메소드에서 생성 로직을 결정
        return new UserDao(connectionMaker);
    }

}
