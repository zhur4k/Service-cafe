package com.yulkost.service.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageSplitterService {
    private final int MAX_MESSAGE_LENGTH = 4096;

    public List<String> splitMessage(String message) {
        List<String> messages = new ArrayList<>();
        int start = 0;
        while (start < message.length()) {
            int end = Math.min(message.length(), start + MAX_MESSAGE_LENGTH);
            messages.add(message.substring(start, end));
            start = end;
        }
        return messages;
    }
}
