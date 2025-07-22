package com.vehicleTracker.vehicleTracker.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                // Optionally set user principal here on CONNECT
            } else {
                if (accessor.getUser() == null
                        || !(accessor.getUser() instanceof Authentication authentication)
                        || !authentication.isAuthenticated()) {
                    throw new IllegalStateException("Unauthenticated user - rejecting STOMP message");
                }
            }
        }

        return message;
    }
}


