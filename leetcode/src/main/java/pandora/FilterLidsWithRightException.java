package pandora;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterLidsWithRightException {
    public static void main(String[] args) throws IOException {
        List<Long> listeners = new ArrayList<>();
        InputStream inp = FilterLidsWithRightException.class.getClassLoader().
            getResourceAsStream("pandora/data2.txt");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(inp ,
            JsonNode.class);
        while (jsonNode.iterator().hasNext()){
            JsonNode node = jsonNode.iterator().next();
            //String str = node.findValue("exception.exception_class").asText();
            if(node.findValue("exception.exception_class").textValue().endsWith("NullPointerException"))
            listeners.add(Long.parseLong(node.findValue("message").asText().split(":")[1].trim()));
        }
        System.out.println(listeners.size());
        listeners = listeners.stream().distinct().collect(Collectors.toList());
        System.out.println(listeners.size());
    }
}
