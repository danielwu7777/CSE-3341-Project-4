import java.util.ArrayList;
import java.util.List;

class Formals {
    Id id;
    Formals formals;
    static List<String> listIdString = new ArrayList<>();

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

    void execute() {
        listIdString.add(id.getString());
        if (formals != null) {
            formals.execute();
        }
    }
}
