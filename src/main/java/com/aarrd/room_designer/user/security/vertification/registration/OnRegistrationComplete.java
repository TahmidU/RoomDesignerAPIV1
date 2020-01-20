package com.aarrd.room_designer.user.security.vertification.registration;

import com.aarrd.room_designer.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@Data
@EqualsAndHashCode(callSuper = false)
public class OnRegistrationComplete extends ApplicationEvent
{
    private final User user;

    public OnRegistrationComplete(final User user) {
        super(user);
        this.user = user;
    }
}
