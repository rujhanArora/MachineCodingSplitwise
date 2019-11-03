package com.splitwise.commands;

import com.splitwise.BookKeeper;
import com.splitwise.Utils;
import com.splitwise.exceptions.BadCommandFormatException;
import com.splitwise.exceptions.NoSuchUserException;
import com.splitwise.models.User;

public class ShowCommand implements Command {
    @Override
    public void execute(String[] cmd) throws BadCommandFormatException {
        BookKeeper bk = BookKeeper.getInstance();
        if(cmd.length > 1) {
            User user;
            try {
                user = Utils.getUser(cmd[1]);
                bk.showBalance(user);
            } catch (NoSuchUserException e) {
                System.out.println(e.getMessage());
            }
        } else {
            bk.showAllBalances();
        }
    }
}
