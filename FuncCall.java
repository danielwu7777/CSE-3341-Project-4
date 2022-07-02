import java.util.ArrayList;
import java.util.List;

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

    public void execute() {
        Formals.listIdString.clear();
        formals.execute();
        List<String> actualParams = new ArrayList<>();
        Executor.copyList(Formals.listIdString, actualParams);
        Formals.listIdString.clear();
        FuncDecl fd = Executor.functionMap.get(id.getString());
        Executor.semanticValidFuncName(fd);
        fd.formals.execute();
        List<String> formalParams = new ArrayList<>();
        Executor.copyList(Formals.listIdString, formalParams);
        Executor.semanticDistinctParams(formalParams);
        Formals.listIdString.clear();
        Executor.semanticValidNumArg(formalParams, actualParams);
        Executor.pushFrame(formalParams, actualParams);
        // Execute stmt-seq
        fd.ss.execute();
        // Pop frame off stack of stack
        Executor.popStackSpace();
    }
}
