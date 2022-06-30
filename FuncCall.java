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

    public void execute() { // NOT SURE WHAT TO DO HERE
        // Set up new frame
        // Create a stack of maps
        Stack<HashMap<String, CoreVar>> newFrame = new Stack<>();
        // add a map to newly created stack of maps
        HashMap<String, CoreVar> newScope = new HashMap<>();
        // create the formal parameters, copying the values of the arguments (use list from Formals)
        for (String id : formals.listFormal) {
            newScope.put(id, /* TODO */);
        }
        
        // push fram onto the top of stack of stack of maps

        // execute body of function

        // pop frame off call stack
        Executor.pushStackSpace();
        FuncDecl fd = Executor.functionMap.get(id.getString());
        fd.execute();
        Executor.popStackSpace();
    }
}
