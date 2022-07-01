import java.util.ArrayList;
import java.util.List;

class Formals {
    Id id;
    Formals formals;
    List<String> listIdString = new ArrayList<>();
    List<Id> listId = new ArrayList<>();

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

    void executeStringList() {
        listIdString.add(id.getString());
        if (formals != null) {
            formals.executeStringList();
        }
    }

    void executeIdList() {
        listId.add(id);
        if (formals != null) {
            formals.executeIdList();
        }
    }
}
