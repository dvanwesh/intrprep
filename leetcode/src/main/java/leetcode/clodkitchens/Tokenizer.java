package leetcode.clodkitchens;

/*
Feb 8th, 2021
 * Click `Run` to execute the snippet below!
 */

import java.util.*;

/*
You are provided with a set of APIs for reading a simplified XML document. You’re tasked with designing an object that captures all the tags, texts, and parent-child relationships between elements found in an XML document. After designing the object, you’re to instantiate an instance of it using data from the provided token stream API.

<a>
  <b>
    <c>foo</c>
    <c></c>
  </b>
  <d>blah</d>
</a>


(BEGIN, “a”), (BEGIN, “b”), (BEGIN, “c”), (TEXT, “foo”), (END, “c”)...

 */


interface Tokenizer {
    Token nextToken();
}

interface Token {
    String value();
    TokenType type();
}

enum TokenType {
    BEGIN,
    END,
    TEXT,
}

class FToken implements Token {
    private final String value;
    private final TokenType type;

    public FToken(TokenType type, String value) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public TokenType type() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Token{" +
            "value='" + value + '\'' +
            ", type=" + type +
            '}';
    }
}

class FakeTokenizer implements Tokenizer {
    private int position = 0;
    private final List<Token> tokens = Arrays.asList(
        new FToken(TokenType.BEGIN, "a"),
        new FToken(TokenType.BEGIN, "b"),
        new FToken(TokenType.BEGIN, "c"),
        new FToken(TokenType.TEXT, "foo"),
        new FToken(TokenType.END, "c"),
        new FToken(TokenType.BEGIN, "c"),
        new FToken(TokenType.END, "c"),
        new FToken(TokenType.END, "b"),
        new FToken(TokenType.BEGIN, "d"),
        new FToken(TokenType.TEXT, "blah"),
        new FToken(TokenType.END, "d"),
        new FToken(TokenType.END, "a")
    );

    public FakeTokenizer() { }

    public Token nextToken() {
        if (position < tokens.size()) {
            return tokens.get(position++);
        } else {
            return null;
        }
    }
}


class XmlNode extends Node{
    List<Node> children;

    public XmlNode(String tag){
        super(tag);
        children = new ArrayList<>();
    }

    public void addChildren(Node node){
        children.add(node);
    }

}

class StringNode extends Node{
    public StringNode(String val){
        super(val);
    }
}

class Node {
    String val;
    public Node(String val){
        this.val = val;
    }
}


class Solution {
    static XmlNode root = new XmlNode("#");
    public static void main(String[] args) {
        FakeTokenizer inpToken = new FakeTokenizer();
        readXml(inpToken);
        for(Node node :  root.children){
            printXml(node, "");
        }
    }

    private static void printXml(Node node, String SpaceWidth){
        if( node instanceof XmlNode){
            System.out.println(SpaceWidth+"<"+node.val+">");
            for(Node child : ((XmlNode)node).children){
                printXml(child, SpaceWidth+"  ");
            }
            System.out.println(SpaceWidth+"</"+node.val+">");
        } else{
            System.out.println(SpaceWidth+node.val);
        }
    }

    public static void readXml(Tokenizer tokenizer){
        XmlNode curr = root;
        XmlNode parent = root;
        Token token = tokenizer.nextToken();
        while(token != null){
            if(token.type() == TokenType.BEGIN){
                XmlNode child = new XmlNode(token.value());
                curr.addChildren(child);
                parent = curr;
                curr = child;
            } else if(token.type() == TokenType.TEXT){
                curr.addChildren(new StringNode(token.value()));
            } else{
                curr = parent;
            }
            token = tokenizer.nextToken();
        }
    }
}

