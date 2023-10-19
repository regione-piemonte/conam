/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.exception;

 /**
 * Eccezione da creare quando l'invocazione alla moveDocument torna l'eccezione Acaris SER-E167 (così da richiamarla con offlineRequest: true).
 */
public class TroppiAllegatiPerSpostamentoException extends Exception
{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = -7156599370005938086L;
	
	private Exception itsOriginalException;
        
    public TroppiAllegatiPerSpostamentoException(final String aMessage, final Exception anException)
    {
        super(aMessage);
        this.itsOriginalException = anException;
    }
    
    public TroppiAllegatiPerSpostamentoException(final String aMessage){
        super(aMessage);
        
    }
    
    public void printStackTrace()
    {
        this.itsOriginalException.printStackTrace();
    }

	public Exception getItsOriginalException() {
		return itsOriginalException;
	}

	public void setItsOriginalException(Exception itsOriginalException) {
		this.itsOriginalException = itsOriginalException;
	}
    
    
    
}
