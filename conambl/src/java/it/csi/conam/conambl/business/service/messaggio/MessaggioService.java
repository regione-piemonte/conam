package it.csi.conam.conambl.business.service.messaggio;

import it.csi.conam.conambl.vo.common.MessageVO;

public interface MessaggioService {

	MessageVO getMessaggio(String errorCode);

}
