import java.util.*;

class CoreVar {
	Core type;
	Integer value;

	public CoreVar(Core varType) {
		type = varType;
		if (type == Core.INT) {
			value = 0;
		} else {
			value = null;
		}
	}
}

class Executor {

	static HashMap<String, CoreVar> globalSpace;
	static Stack<Stack<HashMap<String, CoreVar>>> stackSpace;
	static ArrayList<Integer> heapSpace;
	static HashMap<String, FuncDecl> functionMap;

	static Scanner dataFile;

	static void initialize(String dataFileName) {
		globalSpace = new HashMap<String, CoreVar>();
		stackSpace = new Stack<Stack<HashMap<String, CoreVar>>>();
		heapSpace = new ArrayList<Integer>();
		dataFile = new Scanner(dataFileName);
		functionMap = new HashMap<String, FuncDecl>();
	}

	static void semanticUniqueFuncName(String funcName) {
		if (Executor.functionMap.containsKey(funcName)) {
			System.out.println("ERROR: No duplicate function names allowed");
			System.exit(0);
		}
	}

	static void semanticValidFuncName(FuncDecl fd) {
		if (fd == null) {
			System.out.println("ERROR: Function call has no target");
			System.exit(0);
		}
	}

	static void semanticValidNumArg(List<String> formalParams, List<String> actualParams) {
		if (formalParams.size() != actualParams.size()) {
			System.out.println("ERROR: Invalid number of arguments in function call");
			System.exit(0);
		}
	}

	static void semanticDistinctParams(List<String> formalParams) {
		Set<String> helperSet = new HashSet<>();
		for (String param : formalParams) {
			if (!helperSet.add(param)) {
				System.out.println("ERROR: Formal parameters of a function must be distinct from each other");
				System.exit(0);
			}
		}
	}

	static void addFunction(String funcName, FuncDecl funcDecl) {
		functionMap.put(funcName, funcDecl);
	}

	static FuncDecl getFunction(String funcName) {
		return functionMap.get(funcName);
	}

	static void pushStackSpace() {
		stackSpace.push(new Stack<HashMap<String, CoreVar>>());
	}

	static void popStackSpace() {
		stackSpace.pop();
	}

	static void pushLocalScope() {
		stackSpace.peek().push(new HashMap<String, CoreVar>());
	}

	static void popLocalScope() {
		stackSpace.peek().pop();
	}

	static int getNextData() {
		int data = 0;
		if (dataFile.currentToken() == Core.EOS) {
			System.out.println("ERROR: data file is out of values!");
			System.exit(0);
		} else {
			data = dataFile.getCONST();
			dataFile.nextToken();
		}
		return data;
	}

	static void allocate(String identifier, Core varType) {
		CoreVar record = new CoreVar(varType);
		// If we are in the DeclSeq, the local scope will not be created yet
		if (stackSpace.size() == 0) {
			globalSpace.put(identifier, record);
		} else {
			stackSpace.peek().peek().put(identifier, record);
		}
	}

	static CoreVar getStackOrStatic(String identifier) {
		CoreVar record = null;
		for (int i = stackSpace.peek().size() - 1; i >= 0; i--) {
			if (stackSpace.peek().get(i).containsKey(identifier)) {
				record = stackSpace.peek().get(i).get(identifier);
				break;
			}
		}
		if (record == null) {
			record = globalSpace.get(identifier);
		}
		return record;
	}

	static void heapAllocate(String identifier) {
		CoreVar x = getStackOrStatic(identifier);
		if (x.type != Core.REF) {
			System.out.println("ERROR: " + identifier + " is not of type ref, cannot perform \"new\"-assign!");
			System.exit(0);
		}
		x.value = heapSpace.size();
		heapSpace.add(null);
	}

	static Core getType(String identifier) {
		CoreVar x = getStackOrStatic(identifier);
		return x.type;
	}

	static Integer getValue(String identifier) {
		CoreVar x = getStackOrStatic(identifier);
		Integer value = x.value;
		if (x.type == Core.REF) {
			try {
				value = heapSpace.get(value);
			} catch (Exception e) {
				System.out.println("ERROR: invalid heap read attempted!");
				System.exit(0);
			}
		}
		return value;
	}

	static void storeValue(String identifier, int value) {
		CoreVar x = getStackOrStatic(identifier);
		if (x.type == Core.REF) {
			try {
				heapSpace.set(x.value, value);
			} catch (Exception e) {
				System.out.println("ERROR: invalid heap write attempted!");
				System.exit(0);
			}
		} else {
			x.value = value;
		}
	}

	static void referenceCopy(String var1, String var2) {
		CoreVar x = getStackOrStatic(var1);
		CoreVar y = getStackOrStatic(var2);
		x.value = y.value;
	}

	static void pushFrame(List<String> formalParams, List<String> actualParams) {
		// Set up new frame
		Stack<HashMap<String, CoreVar>> newFrame = new Stack<>();
		newFrame.add(new HashMap<String, CoreVar>());
		for (int i = 0; i < formalParams.size(); i++) {
			CoreVar temp = new CoreVar(Core.REF);
			temp.value = getStackOrStatic(actualParams.get(i)).value;
			newFrame.peek().put(formalParams.get(i), temp);
		}
		// Push frame onto stack of stack
		Executor.stackSpace.add(newFrame);
		pushLocalScope();
	}

	static void copyList(List<String> from, List<String> to) {
		for (String id : from) {
			to.add(id);
		}
	}

}