package com;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTest {
    private String id;
    private String userName;

    public static void main(String[] args){
        UserTest t1 = new UserTest("1","aa");
        UserTest t2 = new UserTest("1","aa");
        System.out.println(t1.equals(t2));
        System.out.println(t1.hashCode());
        System.out.println(t2.hashCode());

    }
}
