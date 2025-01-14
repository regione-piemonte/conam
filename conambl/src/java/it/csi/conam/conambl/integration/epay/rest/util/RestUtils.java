package it.csi.conam.conambl.integration.epay.rest.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import it.csi.conam.conambl.integration.epay.rest.model.DebtPositionData;
import it.csi.conam.conambl.integration.epay.rest.model.DebtPositionReference;
import it.csi.conam.conambl.integration.epay.rest.model.PaymentComponent;


public interface RestUtils {
	
	public DebtPositionReference callCreateDebtPosition(String url, DebtPositionData requestBody, String username, String password);
	
	public String generateUrl(String url, String organization, String paymentType);
	
	public String formatRestParamDate(Date dataFineValidita);
	
	public List<PaymentComponent> buildPaymentComponent(String descrizioneCausaleVersamento, BigDecimal importo,String  numeroAccertamento, BigInteger annoAccertamento, Date dataOraAccertamento);
	
}

