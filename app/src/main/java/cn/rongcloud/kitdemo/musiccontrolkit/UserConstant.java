package cn.rongcloud.kitdemo.musiccontrolkit;

/**
 * Created by gyn on 2021/12/2
 * 模拟2个语聊房账号
 */
public class UserConstant {

    public final static User USER1 = new User("1314000", "ECuzpyIw7VgP24aAzBQb20oAGVeArlCAA8D2xIQZIrw=@7cp1.cn.rongnav.com;7cp1.cn.rongcfg.com");
    public final static User USER2 = new User("1314001", "ECuzpyIw7VjpJEcBEpt/dEoAGVeArlCASApnt6EhTqw=@7cp1.cn.rongnav.com;7cp1.cn.rongcfg.com");


    public static class User {
        private String userId;
        private String token;

        public User(String userId, String token) {
            this.userId = userId;
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public String getToken() {
            return token;
        }
    }
}
