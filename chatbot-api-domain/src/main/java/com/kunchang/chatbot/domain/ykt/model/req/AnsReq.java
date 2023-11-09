package com.kunchang.chatbot.domain.ykt.model.req;

public class AnsReq {

    private ReqData reqData;

    public AnsReq(ReqData reqData) {
        this.reqData = reqData;
    }

    public ReqData getReqData() {
        return reqData;
    }

    public void setReqData(ReqData reqData) {
        this.reqData = reqData;
    }


}
