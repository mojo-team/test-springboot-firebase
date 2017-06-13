package fr.pe.hack.test;

import java.util.UUID;

/**
 * Created by christophe on 12/06/17.
 */
public class User {

    public String date_of_birth;
    public String full_name;
    public String nickname;
    public String uuid;

    public User(String date_of_birth, String full_name) {
        this.date_of_birth = date_of_birth;
        this.full_name = full_name;
        this.uuid = UUID.randomUUID().toString();
    }

    public User(String date_of_birth, String full_name, String nickname) {
        this.date_of_birth = date_of_birth;
        this.full_name = full_name;
        this.nickname = nickname;
        this.uuid = UUID.randomUUID().toString();
    }


    @Override
    public String toString() {
        return "[User : " + nickname + " - " + uuid + "]";
    }

}
