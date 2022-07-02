class FuncDecl {
    Id id;
    Formals formals;
    StmtSeq ss;

    void parse() {
        id = new Id();
        id.parse();
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.REF);
        Parser.scanner.nextToken();
        formals = new Formals();
        formals.parse();
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.LBRACE);
        Parser.scanner.nextToken();
        ss = new StmtSeq();
        ss.parse();
        Parser.expectedToken(Core.RBRACE);
        Parser.scanner.nextToken();
    }

    void print(int indent) {
        id.print();
        System.out.print("(ref ");
        formals.print();
        System.out.println(") {");
        ss.print(indent);
        System.out.println("}");
    }

    void execute() {
        Executor.semanticUniqueFuncName(id.getString());
        Executor.functionMap.put(id.getString(), FuncDecl.this);
    }
}
