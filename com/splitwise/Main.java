package com.splitwise;

import com.splitwise.commands.CommandFactory;
import com.splitwise.exceptions.BadCommandFormatException;
import com.splitwise.models.User;

import java.util.Scanner;

public class Main { // Driver / View
    public static void main(String[] args) {
        BookKeeper bk = BookKeeper.getInstance();

        bk.addUser(new User("rujhan", "rujhan@ib.com", "rujhan"));
        bk.addUser(new User("atul", "atul@ib.com", "atul"));
        bk.addUser(new User("pooja", "pooja@ib.com", "pooja"));

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String[] cmd = sc.nextLine().split(" ");
            try {
                CommandFactory.getInstance().execute(cmd);
            } catch (BadCommandFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

// fix everything and run the code
// simplify
// 2.20
// today / monday?
// code

// time management
// all of this in 3 hours?
// new question - parking lot
// end to end
// 5 hours <- xplaining some things along the way



// simplify - minimizing the number of transactions in the worst case
// O(n-1)

// -8 -3 -2 5 3 5

// A  B  C  D
// 5 -4  3 -4
// totals of each user

// sort the balances
//  B  D  C  A
// -4 -4  3  5

// 2 pointer approach
//   D  C  A
//  -4  3  1

//   D  C
//  -3  3

// settled

// worst case O(n-1) transactions is linear
// O(n log n) because of sorting is time
// A B C D
// settle A
// A against the rest of the group
// A gets settled up with the next person
// no sorting required
// iterate over each person, and settle up with next person
