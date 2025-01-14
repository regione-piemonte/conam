package it.csi.conam.conambl.integration.epay.rest.util;

import it.csi.conam.conambl.integration.epay.rest.model.DebtPositionData;
import it.csi.conam.conambl.integration.epay.rest.model.DebtPositionReference;
import it.csi.conam.conambl.integration.epay.rest.model.PaymentComponent;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.epay.rest.client.DebtPositionApi;

@Component
public class RestUtilsImpl implements RestUtils {
	
	public DebtPositionReference callCreateDebtPosition(String url, DebtPositionData requestBody, String username, String password) throws BusinessException {
	    DebtPositionApi api = new DebtPositionApi(username, password);
	    try {
	        return api.createDebtPosition(url, requestBody);
	    } catch (BusinessException be) {
	        throw be; 
	    } catch (Exception e) {
	        throw new BusinessException("Errore durante la comunicazione con il servizio esterno: " + e.getMessage());
	    }
	}

	
	public String generateUrl(String uri, String organization, String paymentType) {
		StringBuilder uriSb = new StringBuilder();
		uriSb.append(uri)
			.append("/organizations/" + organization)
			.append("/paymenttypes/" + paymentType)
			.append("/debtpositions");
		
		return uriSb.toString();
	}


	@Override
	public String formatRestParamDate(Date data) {
	    long timeInMillis = data.getTime();
	    Instant instantFineValidita = Instant.ofEpochMilli(timeInMillis);
	    OffsetDateTime offsetDateTimeFineValidita = instantFineValidita.atOffset(ZoneOffset.UTC);
	    // Imposta i millisecondi a 0 se la data originale non li ha
	    if (offsetDateTimeFineValidita.getNano() == 0) {
	        offsetDateTimeFineValidita = offsetDateTimeFineValidita.withNano(191_000_000);
	    }

	    return offsetDateTimeFineValidita.toString();
	}


	@Override
	public List<PaymentComponent> buildPaymentComponent(String descrizioneCausaleVersamento, BigDecimal importo,String  numeroAccertamento, BigInteger annoAccertamento, Date dataOraAccertamento ) {
		List<PaymentComponent> components =  new ArrayList<>();
		PaymentComponent component = new PaymentComponent();
		
		component.setCausale(descrizioneCausaleVersamento);
		component.setImporto(importo);
		component.setNumeroAccertamento(numeroAccertamento);
		component.setProgressivo(1);
		
		// Facoltativo
		component.setDatiSpecificiRiscossione(null);
		
		// Conversione annoAccertamento
		if (annoAccertamento != null) {
		    Integer annoAccertamentoInt = annoAccertamento.intValue();

		    component.setAnnoAccertamento(annoAccertamentoInt);
		}	
		else if (dataOraAccertamento != null) {
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(dataOraAccertamento);
		    int anno = calendar.get(Calendar.YEAR);

		    component.setAnnoAccertamento(anno);
		}
		
		
		components.add(component);
			
		return components;
	}
	
	
}

