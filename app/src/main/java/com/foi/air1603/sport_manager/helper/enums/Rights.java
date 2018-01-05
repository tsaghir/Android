package com.foi.air1603.sport_manager.helper.enums;

/**
 * Created by Generalko on 08-Dec-16.
 */

public enum Rights {
    Admin,
    User,
    Owner;

    public static Rights getRightFormInt(int right){
        switch (right){
            case 0:
                return Rights.User;
            case 1:
                return Rights.Admin;
            case 2:
                return Rights.Owner;
        }
        return null;
    }

}
