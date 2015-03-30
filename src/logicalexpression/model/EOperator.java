package logicalexpression.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Shulander
 */
public enum EOperator {
    
    EQ(new String[]{"EQ", "=="}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            return a == null ? b == null : b!=null && a.compareTo(b) == 0;
        }
    },
    NE(new String[]{"NE", "!="}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            return a == null ? b != null : b==null || a.compareTo(b) != 0;
        }
    },
    GT(new String[]{"GT", ">"}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            return a.compareTo(b) > 0;
        }
    },
    LT(new String[]{"LT", "<"}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            return a.compareTo(b) < 0;
        }
    },
    LE(new String[]{"LE", ">="}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            return LT.compare(a, b) || EQ.compare(a, b);
        }
    },
    GE(new String[]{"GE", "<="}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            return GT.compare(a, b) || EQ.compare(a, b);
        }
    },
    AND(new String[]{"AND", "&&"}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            return a.compareTo(true)==0 && a.compareTo(b)==0;
        }
    },
    OR(new String[]{"OR", "||"}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            return a.compareTo(true)==0 || b.compareTo(true)==0;
        }
    },
    NOT(new String[]{"NOT"}) {
        @Override
        public Boolean compare(Comparable a, Comparable b) {
            if(b != null) {
                return b.compareTo(true)!=0;
            } else {
                return a.compareTo(true)!=0;
            }
        }
    };
    
    private List<String> values;
    
    private EOperator(String[] newValue) {
        values = new LinkedList<>();
        values.addAll(Arrays.asList(newValue));
    }
    
    public abstract Boolean compare(Comparable a, Comparable b);
    
    public static EOperator parseString(String str) {
        for (EOperator operator : EOperator.values()) {
            for (String strValue : operator.values) {
                if(strValue.compareTo(str.toUpperCase())==0) {
                    return operator;
                }
            }
        }
        return null;
    }
}
