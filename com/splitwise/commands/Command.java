package com.splitwise.commands;

import com.splitwise.exceptions.BadCommandFormatException;

public interface Command {
    void execute(String[] cmd) throws BadCommandFormatException;
}
