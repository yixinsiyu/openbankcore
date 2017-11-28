package com.xbeer.exception;

import com.xbeer.net.BaseRequestCommand;

public class BaseException extends Exception {

  BaseRequestCommand t;

  public BaseException(Exception e) {



  }

  public BaseException(Exception e, BaseRequestCommand c) {
    this.t = c;


  }

  public BaseException setReq(BaseRequestCommand cmd) {
    this.t = cmd;
    return this;
  }

  public BaseRequestCommand getReq() {
    return this.t;
  }
}
