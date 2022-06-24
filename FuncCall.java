class FuncCall {
    Id id;
    Formals formals;

    void parse() {
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

    void print() {
        System.out.print("begin ");
        id.print();
        System.out.print("(");
        formals.print();
        System.out.println(");");
    }
}
