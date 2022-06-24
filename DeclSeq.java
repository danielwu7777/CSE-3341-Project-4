class DeclSeq {
	Decl decl;
	DeclSeq ds;
	FuncDecl fd;

	void parse() {
		if (Parser.scanner.currentToken() != Core.ID) {
			decl = new Decl();
			decl.parse();
		} else {
			fd = new FuncDecl();
			fd.parse();
		}
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
	}

	void print(int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		if (decl != null) {
			decl.print(indent);
		} else {
			fd.print(indent);
		}
		if (ds != null) {
			ds.print(indent);
		}
	}

	void execute() {
		if (decl != null) {
			decl.execute();
		} else {
			fd.execute();
		}
		if (ds != null) {
			ds.execute();
		}
	}
}