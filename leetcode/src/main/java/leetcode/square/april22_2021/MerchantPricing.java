package leetcode.square.april22_2021;

import java.util.*;
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

    static class Request {
        int requestId;
        String merchantId;
        double price;
        Integer parentId;

        public Request(int requestId, String merchantId, double price, Integer parentId) {
            this.requestId = requestId;
            this.merchantId = merchantId;
            this.price = price;
            this.parentId = parentId;
        }
    }
    private Map<Integer, Request> requestMap = new HashMap<>();
    private Map<String, Double> resultMap = new HashMap<>();
    private Set<Integer> handledRequests = new HashSet<>();
    public static void main(String[] args) {
        MerchantPricing merchantPricing = new MerchantPricing();
        merchantPricing.takeRequest(1, "XSCMA", 2.75, null);
        merchantPricing.takeRequest(2, "XSC", 2.75, 1);
        merchantPricing.takeRequest(3, "XSCX", 2.76, 1);
        merchantPricing.takeRequest(4, "CPP", 3.09, 2);
        merchantPricing.takeDecision(1, true);
        merchantPricing.takeRequest(5, "XSCX", 5.00, null);
        merchantPricing.takeDecision(5, true);

        Map<String, Double> res = merchantPricing.getResult();
        for(String key : res.keySet()){
            System.out.println(key+"--"+res.get(key));
        }
    }

    public void takeRequest(int requestId, String merchantId, Double price, Integer parent){
        requestMap.put(requestId, new Request(requestId, merchantId, price, parent));
    }

    public void takeRequest(int requestId, String merchantId, Double price){
        requestMap.put(requestId, new Request(requestId, merchantId, price, null));
    }

    public void takeDecision(int requestId, boolean decision){
        if (handledRequests.contains(requestId)) {
            return;
        }
        if(decision){
            approveRequestAndSubRequests(requestId);
        }
        handledRequests.add(requestId);
    }

    private void approveRequestAndSubRequests(int requestId) {
        Request request = requestMap.get(requestId);
        if (request == null) {
            return;
        }
        resultMap.put(request.merchantId, request.price);
        for (Request rqst:requestMap.values()) {
            if (rqst.parentId != null && rqst.parentId == requestId) {
                approveRequestAndSubRequests(rqst.requestId);
            }
        }
    }

    private Map<String, Double> getResult(){
        return Collections.unmodifiableMap(resultMap);
    }
}

