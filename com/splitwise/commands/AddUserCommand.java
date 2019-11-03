package com.splitwise.commands;

import com.splitwise.BookKeeper;
import com.splitwise.exceptions.BadCommandFormatException;
import com.splitwise.models.User;

class AddUserCommand implements Command {
    @Override
    public void execute(String[] cmd) throws BadCommandFormatException {
        BookKeeper bk = BookKeeper.getInstance();
        User u = new User(cmd[1], cmd[2], cmd[3]);
        bk.addUser(u);
    }
}
