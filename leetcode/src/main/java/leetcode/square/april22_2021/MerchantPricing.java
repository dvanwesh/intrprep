package leetcode.square.april22_2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/*
Problem

Square provides custom pricing for merchants. Those custom price is requested by merchants and our team decide to approve / reject the request. Please design a system to store these requests from merchants, allow squares to reject/approve those requests, and output the final rate for each merchant.

Each request from the merchant has a random-generated request_id, a merchant id to identify the requester and a rate.

Request format: [request_id(integer), merchant_id(string), rate(double)]

Decision format: [request_id(integer), decision(boolean, false->reject, true->approve)]

Rule No.1: A request can only be approved/reject once (At most one decision for one request).

Rule No.2: Later approved rate will override former approved rate for a same merchant.

Step 1:
Please design a data structure ,with methods take_request(), take_decision(), output_rate_list().



Sample
Input
take_request( [1, 'XSCMA', 2.75] )

take_decision( [1, true] )

take_request( [2, 'XSC', 2.75] )

take_request( [3, 'XSC', 2.76] )

take_decision( [2, true] )

take_decision( [3, true] )

Output
XSCMA, 2.75

XSC, 2.76


Map<Integer, Pair<String, double>>
Map<String, Double>

*/

/*
Step 2:
Requests may have a parent-request, so the request Request format becomes: [request_id(integer), merchant_id(string), rate(double), parent_id(integer) ].

Rule No.1: Once a request is approved, all sub_requests of this request will also be approved.

Rule No.2: Reject decision will not influence sub_requests.

Rule No.3: Approval decision will not influence future sub_requests.

Sample
Input
take_request(1, 'XSCMA', 2.75, null);

take_request(2, 'XSC', 2.75, 1);

take_request(3, 'XSCX', 2.76, 1);


take_request(4, 'CPP', 3.09, 2);
take_decision(1, true);

Output
XSCMA, 2.75

XSC, 2.75

XSCX, 2.76



*/
class MerchantPricing {
    private Map<Integer, Pair> requestMap;
    private Map<String, Double> resultMap;
    private Map<Integer, List<Integer>> parentMap;
    public static void main(String[] args) {
        MerchantPricing sol = new MerchantPricing();
        sol.takeRequest(1, "XSCMA", 2.75, null);
        sol.takeRequest(2, "XSC", 2.75, 1);
        sol.takeRequest(3, "XSCX", 2.76, 1);
        sol.takeRequest(4, "CPP", 3.09, 2);
        sol.takeDecision(1, false);

        Map<String, Double> res = sol.getResult();
        for(String key : res.keySet()){
            System.out.println(key+"--"+res.get(key));
        }
    }

    public MerchantPricing(){
        requestMap = new HashMap<>();
        resultMap = new HashMap<>();
        parentMap = new HashMap<>();
    }

    public void takeRequest(int requestId, String merchantId, Double price, Integer parent){
        requestMap.put(requestId, new Pair(merchantId, price));
        if(parent != null){
            parentMap.computeIfAbsent(parent, a->new ArrayList<>()).add(requestId);
        }
    }

    public void takeRequest(int requestId, String merchantId, Double price){
        requestMap.put(requestId, new Pair(merchantId, price));
    }

    public void takeDecision(int requestId, boolean decision){
        if(decision && requestMap.containsKey(requestId)){
            resultMap.put(requestMap.get(requestId).getKey(), requestMap.get(requestId).getValue());
        }
        if(!parentMap.isEmpty() && parentMap.get(requestId) != null){
            applyPrice(requestId, new HashSet<>());
        }
    }

    private void applyPrice(int requestId, Set<Integer> seen){
        if(requestMap.containsKey(requestId)){
            resultMap.put(requestMap.get(requestId).getKey(), requestMap.get(requestId).getValue());
        }
        seen.add(requestId);
        for(int childRequest : parentMap.getOrDefault(requestId, new ArrayList<>())){
            if(!seen.contains(childRequest)){
                applyPrice(childRequest, seen);
            }
        }
    }

    private Map<String, Double> getResult(){
        return Collections.unmodifiableMap(resultMap);
    }
}

class Pair{
    String key;
    Double value;
    public Pair(String key, Double value){
        this.key = key;
        this.value = value;
    }
    public String getKey(){
        return key;
    }

    public Double getValue(){
        return value;
    }
}
