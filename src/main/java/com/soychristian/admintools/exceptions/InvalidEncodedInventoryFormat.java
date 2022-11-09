package com.soychristian.admintools.exceptions;

import java.io.IOException;

public class InvalidEncodedInventoryFormat extends IOException {
    public InvalidEncodedInventoryFormat(String message) {
        super(message);
    }
}
