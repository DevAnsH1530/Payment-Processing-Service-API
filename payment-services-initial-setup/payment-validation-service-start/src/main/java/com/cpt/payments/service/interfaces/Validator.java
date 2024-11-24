package com.cpt.payments.service.interfaces;

import org.springframework.stereotype.Service;

import com.cpt.payments.dto.PaymentRequestDTO;

@Service
public interface Validator {
	public void validate(PaymentRequestDTO paymentRequestDTO);
}
