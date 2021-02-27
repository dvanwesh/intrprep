package leetcode.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
   // [["a", ["b", "c"]], ["d"], "abc"]
   serializedString: ((a,(b,c))d,abc)
   2. (a, b, c)
*/
public class SerializeDeserializeNestedList {

    public static void main(String[] args) {
        new SerializeDeserializeNestedList().run();
    }

    private void run() {
        System.out.println("I'm compilable and runnable");
        List lst = new ArrayList<NestedList>();
        lst.add(new NestedList("a"));
        lst.add(new NestedList("b"));
        List lt = new ArrayList<NestedList>();
        lt.add(new NestedList("a"));
        lt.add(new NestedList("b"));
        lst.add(new NestedList(lt));
        List list3 = new ArrayList<NestedList>();
        list3.add(new NestedList("ABC"));
        list3.add(new NestedList("DEF"));
        List list4 = new ArrayList<NestedList>();
        list4.add(new NestedList("111"));
        list4.add(new NestedList("222"));
        List list2 = new ArrayList<NestedList>();
        list2.add(new NestedList("AA"));
        list2.add(new NestedList("BB"));
        list2.add(new NestedList(list3));
        list2.add(new NestedList(list4));
        list2.add(new NestedList("CC"));
        List list1 = new ArrayList<NestedList>();
        list1.add(new NestedList("A"));
        list1.add(new NestedList("B"));
        list1.add(new NestedList(list2));
        list1.add(new NestedList("C"));
        NestedList obj = new NestedList(list1);
        String str = serialize(obj);
        System.out.println(str);
        NestedList nl = deserialize(str);
        System.out.println(serialize(nl));
    }

    private String serialize(NestedList nestedList) {
        StringBuffer sb = new StringBuffer();
        serialize(nestedList, sb);
        return sb.toString();
    }

    private void serialize(NestedList nestedList, StringBuffer sb){
        if(nestedList == null){
            return;
        }
        if(nestedList.isString()){
            sb.append(nestedList.getValueAsString());
            return;
        } else if(nestedList.isList()){
            sb.append("(");
            for(NestedList child : nestedList.getValueAsList()){
                serialize(child, sb);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
        }
    }
    private NestedList deserialize(String str) {

        Stack<NestedList> stk = new Stack<>();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '('){
                stk.push(new NestedList());
            } else if(str.charAt(i) == ')'){
                if(stk.size() > 1) {
                    NestedList curr = stk.pop();
                    stk.peek().getValueAsList().add(curr);
                }
            } else if(str.charAt(i) == ','){
                continue;
            }
            else{
                int j = i + 1;
                while(j < str.length() && str.charAt(j) != ')' && str.charAt(j) != '('){
                    j++;
                }
                for(String s : str.substring(i, j).split(",")){
                    stk.peek().getValueAsList().add(new NestedList(s));
                }
                i = j-1;
            }
        }
        return stk.peek();
    }
}

/*
 * Implement serialization and deserialization for the class below.
 * Note: string elements will contain only Latin letters
 */
class NestedList {
    private final Object value;

    public NestedList() {
        this.value = new ArrayList<NestedList>();
    }

    public NestedList(List<NestedList> value) {
        this.value = value;
    }

    public NestedList(String value) {
        this.value = value;
    }

    public boolean isList() {
        return value instanceof List;
    }

    public List<NestedList> getValueAsList() {
        return (List<NestedList>) value;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public String getValueAsString() {
        return (String) value;
    }
}
// [["a", ["b", "c"]], ["d"], "abc"]

