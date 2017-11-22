package com.xbeer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.xbeer.controller.validate.ControllerValidator;
import com.xbeer.controller.validate.ValidateResult;
import com.xbeer.exception.BaseException;
import com.xbeer.message.Message;
import com.xbeer.model.feign.ILimitService;
import com.xbeer.net.CommandResponse;
import com.xbeer.net.command.LimitQueryCommand;
import com.xbeer.net.command.TransferCommand;
import com.xbeer.net.command.TransferReturnBody;
import com.xbeer.service.TransferService;
import com.xbeer.util.DoubleUtil;

@RestController
@EnableAutoConfiguration

public class TransferController extends BaseController {

  private final static Logger logger = LoggerFactory.getLogger(TransferController.class);

  @Autowired
  TransferService transferService;

  
  @Autowired
  ILimitService limitService;

  @ResponseBody
  @RequestMapping(value = "/transfer", method = RequestMethod.POST)

  // 可以使用RequestBody 转换成对象
  //@RequestBody TransferCommand transferCmd

  public CommandResponse transfer(@RequestBody  String transferStr

  ) throws BaseException {
   logger.info("recieve:{}",transferStr);
    
    TransferCommand transferCmd = new Gson().fromJson(transferStr, TransferCommand.class);
    
    LimitQueryCommand  queryCmd = new LimitQueryCommand();
    
    queryCmd.setHeader(transferCmd.getHeader());
    
    
    
   limitService.transfer(new Gson().toJson(queryCmd));
    
    
    
    
    //throw new BaseException(null);
    
    
    

    ValidateResult validResult = ControllerValidator.validate(transferCmd);
    
    
    if ( ! validResult.isHasPassed()) {
      
     
      return this.newCommandReturn(transferCmd.getHeader(), validResult.getValidateMessage(),
          new TransferReturnBody());
      
      
    }
    
    


    String acct_from = transferCmd.getBody().getAcct_from();
    String acct_to = transferCmd.getBody().getAcct_to();

    double amount = DoubleUtil.strToDouble(transferCmd.getBody().getAmount());
    Message result  = null;
    try{
      result = transferService.transfer(acct_from, acct_to, amount, transferCmd);
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.getMessage());
      throw new BaseException(e,transferCmd);
    }

     
    return this.newCommandReturn(transferCmd.getHeader(), result, new TransferReturnBody());



  }

}