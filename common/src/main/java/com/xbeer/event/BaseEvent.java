package com.xbeer.event;

import java.util.Date;

import com.xbeer.command.BaseRequestCommand;
import com.xbeer.constants.EventConstants.EventType;
import com.xbeer.util.JsonUtil;

public abstract class BaseEvent {

  public static enum Stats {

    COMPLETE(1), SENT_NOT_BACK(0);

    int v;

    Stats(int v) {
      this.v = v;

    }

    public int code() {
      return v;
    }

  }

  private long tranSeqNo;// 交易内部SeqNo

  private String uuid;// 交易唯一标识

  protected String topic;// 关于哪个主题

  private Date createDate;

  private long eventId;

  private String tranCode;

  private EventType eventType;

  private String urlContext;// RestFul的url patch

  private String orgCmd;// 原始command

  private String body;// json 格式

  public BaseEvent() {}

  public BaseEvent(long eventId, String topic, EventType type) {
    this.topic = topic;
    this.eventId = eventId;
    this.eventType = type;

  }



  public String getUrlContext() {
    return urlContext;
  }



  public void setUrlContext(String urlContext) {
    this.urlContext = urlContext;
  }



  public String getUuid() {
    return uuid;
  }



  public void setUuid(String uuid) {
    this.uuid = uuid;
  }



  public String getOrgCmd() {
    return orgCmd;
  }



  public void setOrgCmd(String orgCmd) {
    this.orgCmd = orgCmd;
  }

  public void setOrgCmd(BaseRequestCommand orgCmd) {
    this.orgCmd = JsonUtil.object2Json(orgCmd);
  }



  public EventType getEventType() {
    return eventType;
  }


  public String getTranCode() {
    return tranCode;
  }

  public void setTranCode(String tranCode) {
    this.tranCode = tranCode;
  }



  public long getTranSeqNo() {
    return tranSeqNo;
  }

  public void setTranSeqNo(long tranSeqNo) {
    this.tranSeqNo = tranSeqNo;
  }

  public String getTopic() {
    return topic;
  }


  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public long getEventId() {
    return eventId;
  }



  public String getBody() {
    return body;
  }

  /**
   * 将整个Command作为字符串送出去
   * */
  public void setBody(String body) {
    this.body = body;
  }

  public abstract BaseEvent fromCommand(BaseRequestCommand cmd );
  


}
