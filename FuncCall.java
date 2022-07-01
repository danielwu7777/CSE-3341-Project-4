import java.util.HashMap;
import java.util.Stack;

class FuncCall implements Stmt {
    Id id;
    Formals formals;

    public void parse() {
        Parser.expectedToken(Core.BEGIN);
        Parser.scanner.nextToken();
        id = new Id();
        id.parse();
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        formals = new Formals();
        formals.parse();
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
        System.out.print("begin ");
        id.print();
        System.out.print("(");
        formals.print();
        System.out.println(");");
    }

    public void execute() { ////////////////////////////////////////// NEEDS FIXING
        formals.executeStringList();
        FuncDecl fd = Executor.functionMap.get(id.getString());
        fd.formals.executeIdList();
        System.out.println(formals.listIdString + " list id string"); ///////////not expected
        System.out.println(fd.formals.listId + " list id of fd");
        // Set up new frame
        Stack<HashMap<String, CoreVar>> newFrame = new Stack<>();
        HashMap<String, CoreVar> newScope = new HashMap<>();
        // Copy values from FuncCall formals to FuncDecl formals and add to newScope
        int counter = 0;
        for (Id i : fd.formals.listId) {
            i.executeRefAllocate();
            Executor.referenceCopy(i.getString(), formals.listIdString.get(counter));
            newScope.put(i.getString(), Executor.getStackOrStatic(i.getString()));
            counter++;
        }
        // Push frame onto stack of stack
        newFrame.add(newScope);
        Executor.stackSpace.add(newFrame);
        // Execute stmt-seq
        fd.ss.execute();
        // Pop frame off stack of stack
        Executor.popStackSpace();
    }
}
