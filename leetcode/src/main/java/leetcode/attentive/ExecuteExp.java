package leetcode.attentive;


/*
Build an expression parser.
The format of an individual statement will be:
“( CMD VAL VAL )”


VAL can be an integer or another statement.


You can assume valid inputs for this entire exercise.
If you need to, clarify what you assume are valid inputs.


The first two commands to implement are ADD and MULT,
which add and multiply the values, respectively.


Example:
parse("( ADD 3 4 )") == 7
parse("( ADD 3 ( MULT 3 2 ) )") == 9


*/


import java.util.Stack;

class ExecuteExp {
    public static void main(String[] args) {
        //System.out.println(executeExpression("( ADD 3 4 )")); // 7
        //System.out.println(executeExpression("( MULT 3 4 )"));  // 12
        System.out.println(executeExpression("( ADD ( MULT 3 4 ) 4 )")); // 16
        //System.out.println(executeExpression("( ADD 3 ( MULT 3 2 ) )")); // 9
        //System.out.println(executeExpression("( ADD 3 ( ADD 3 ( MULT 3 2 ) )")); // 12
        //System.out.println(executeExpression("( ADD ( MULT 3 4 ) ( ADD 1 2 ) )")); // 15
    }

    private static int executeExpression(String exp) {
        Stack<Object> stack = new Stack<>();
        String[] tokens = exp.split(" ");

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i].replaceAll("[()]", "").trim();
            if (token.isEmpty()) continue;

            if (token.equals("ADD") || token.equals("MULT")) {
                // When we see an operator, pop the top two values, compute and push the result
                int val1 = (int) stack.pop();
                int val2 = (int) stack.pop();
                if (token.equals("ADD")) {
                    stack.push(val1 + val2);
                } else if (token.equals("MULT")) {
                    stack.push(val1 * val2);
                }
            } else {
                // Push number onto stack
                stack.push(Integer.parseInt(token));
            }
        }

        return (int) stack.pop();
    }

    private static int executeExpression1(String exp) {
        int i = 2;
        int val1 = 0;
        int val2 = 0;
        int j = 0;
        boolean isAdd = false;
        if(exp.charAt(i) == 'A') {
            isAdd = true;
            i = i+4;
        } else {
            i = i+5;
        }


        if(Character.isDigit(exp.charAt(i))) {
            int idx = exp.indexOf(' ', i);
            val1 = Integer.valueOf(exp.substring(i, idx));
            j = idx+1;
        } else {
            int closingBracketIdx = i + findClosingBracketIdx(exp.substring(i)); // ( MULT 3 2 ) )
            val1 = executeExpression(exp.substring(i, closingBracketIdx));
            j = closingBracketIdx + 2;
        }


        if(Character.isDigit(exp.charAt(j))) {
            int idx = exp.indexOf(' ', j);
            val2 = Integer.valueOf(exp.substring(j, idx));
        } else {
            int closingBracketIdx = j + findClosingBracketIdx(exp.substring(j)); // ( MULT 3 2 ) )
            val2 = executeExpression(exp.substring(j, closingBracketIdx));
        }


        if(isAdd) {
            return val1 + val2;
        }
        return val1 * val2;
    }


    private static int findClosingBracketIdx(String exp) {
        int op = 0;
        int cl = 0;
        for(int i = 0; i < exp.length(); i++) {
            if(exp.charAt(i) == '(') {
                op++;
            } else if(exp.charAt(i) == ')') {
                cl++;
            }
            if(op == cl && op != 0) {
                return i;
            }
        }
        return -1;
    }
}

