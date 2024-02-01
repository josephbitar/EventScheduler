package org.events.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.IM_USED)
public class NotAvailableTimeSlot extends RuntimeException {
    public NotAvailableTimeSlot(String message) {
        super(message);
    }
}
