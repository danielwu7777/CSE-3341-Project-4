import java.util.ArrayList;
import java.util.List;

class Formals {
    Id id;
    Formals formals;
    List<Id> listFormal = new ArrayList<Id>();

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
        listFormal.add(id);
        if (formals != null) {
            formals.execute();
        }
    }
}
