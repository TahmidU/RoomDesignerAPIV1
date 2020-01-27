package com.aarrd.room_designer.user.security.password.token_production;

import com.aarrd.room_designer.user.User;
import org.springframework.context.ApplicationEvent;

public class TokenEvent extends ApplicationEvent
{
    private final User user;

    public TokenEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
