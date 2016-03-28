package logicalexpression.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import logicalexpression.model.operand.Operand;

/**
 *
 * @author Shulander
 */
public enum EOperator {

	EQ(new String[]{"EQ", "=="}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			boolean returnValue = a == null ? b == null : b != null && a.compareTo(b) == 0;
			return Operand.build(returnValue);
		}
	},
	NE(new String[]{"NE", "!="}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			boolean returnValue = EQ.compare(a, b).compareTo(Operand.build(false)) == 0;
			return Operand.build(returnValue);
		}
	},
	GT(new String[]{"GT", ">"}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			boolean returnValue = a == null || b == null ? false : a.compareTo(b) > 0;
			return Operand.build(returnValue);
		}
	},
	LT(new String[]{"LT", "<"}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			boolean returnValue = a == null || b == null ? false : a.compareTo(b) < 0;
			return Operand.build(returnValue);
		}
	},
	LE(new String[]{"LE", ">="}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			int returnValue = LT.compare(a, b).compareTo(Operand.build(true));
			returnValue = returnValue == 0 ? returnValue : EQ.compare(a, b).compareTo(Operand.build(true));
			return Operand.build(returnValue == 0);
		}
	},
	GE(new String[]{"GE", "<="}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			int returnValue = GT.compare(a, b).compareTo(Operand.build(true));
			returnValue = returnValue == 0 ? returnValue : EQ.compare(a, b).compareTo(Operand.build(true));
			return Operand.build(returnValue == 0);
		}
	},
	AND(new String[]{"AND", "&&"}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			boolean returnValue = a.compareTo(Operand.build(true)) == 0 && a.compareTo(b) == 0;
			return Operand.build(returnValue);
		}
	},
	OR(new String[]{"OR", "||"}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			boolean returnValue = a.compareTo(Operand.build(true)) == 0 || b.compareTo(Operand.build(true)) == 0;
			return Operand.build(returnValue);
		}
	},
	NOT(new String[]{"NOT"}) {
		@Override
		public Operand compare(Operand a, Operand b) {
			boolean returnValue;
			if (b != null) {
				returnValue = b.compareTo(Operand.build(true)) != 0;
			} else {
				returnValue = a.compareTo(Operand.build(true)) != 0;
			}
			return Operand.build(returnValue);
		}
	};

	private final List<String> values;

	private EOperator(String[] newValue) {
		values = new LinkedList<>();
		values.addAll(Arrays.asList(newValue));
	}

	public abstract Operand compare(Operand a, Operand b);

	public static EOperator parseString(String str) {
		for (EOperator operator : EOperator.values()) {
			for (String strValue : operator.values) {
				if (strValue.compareTo(str.toUpperCase()) == 0) {
					return operator;
				}
			}
		}
		return null;
	}
}
