package helloworld;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class User {
    private String id;
    private String name;
    private String asdf;

    public String getAsdf() {
        return asdf;
    }

    public void setAsdf(String asdf) {
        this.asdf = asdf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", asdf='" + asdf + '\'' +
                '}';
    }

    public static void main(String[] args) {
        JSONObject userJson = new JSONObject();
        userJson.put("id","1234");
        userJson.put("name","wangchong");
        User user = JSON.toJavaObject(userJson, User.class);
        System.out.println(user.getAsdf());
        System.out.println(user);

    }
}