class Formals {
    Id id;
    Formals formals;

    void parse() {
        id = new Id();
        id.parse();
        if (Parser.scanner.currentToken() == Core.COMMA) {
            Parser.scanner.nextToken();
            formals = new Formals();
            formals.parse();
        }
    }

    void print() {
        id.print();
        if (formals != null) {
            System.out.print(", ");
            formals.print();
        }
    }

}
