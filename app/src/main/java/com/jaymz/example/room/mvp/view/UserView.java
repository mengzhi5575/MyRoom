package com.jaymz.example.room.mvp.view;

import com.jaymz.example.room.base.IView;
import com.jaymz.example.room.db.room.User;

public interface UserView extends IView {
    void showUserInfo(User user);

    void hideUserInfo();
}
