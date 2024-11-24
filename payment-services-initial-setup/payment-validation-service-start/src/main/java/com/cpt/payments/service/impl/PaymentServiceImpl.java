package com.cpt.payments.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cpt.payments.constant.ValidatorEnum;
import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.service.interfaces.PaymentService;
import com.cpt.payments.service.interfaces.Validator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	
	private ApplicationContext applicationContext;
	public PaymentServiceImpl(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	@Value("${validator.rules}")
	private String rule;
	@Override
	public String validateAndProcessPayment(PaymentRequestDTO paymentRequestDTO) {
		log.info("Payment Request Received: {}",paymentRequestDTO);
		
		String[] rules = rule.split(",");
		for(String vRule: rules) {
			vRule = vRule.trim();
			Validator validator = null;
			Class<? extends Validator> validatorClass = ValidatorEnum.getClassByName(vRule);
			
			if(validatorClass != null) {
				validator = applicationContext.getBean(validatorClass);
				if(validator != null) {
					validator.validate(paymentRequestDTO);
				}
			}
			
			if(validator == null || validatorClass == null) {
				log.error("Validator not found for rule: {}",rule);
			}
//			Rule ran successfully
		}
		log.info("All Validation rules executed successfully");
		
		
		return "Response from Service";
	}

}
