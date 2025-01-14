export interface StiloResearch {
    idRicerca: number
    ricerca: fieldStiloResearch[]
  }
  
  export interface fieldStiloResearch {
    idField: number
    value: string
  }

  export interface ResponseSearchStilo {
    pageReq: number
    maxLineReq: number
    pageResp: number
    lineRes: number
    totalLineResp: number
    documentoStiloVOList: DocumentoStiloVolist[]
    messaggio: any
  }
  
  export interface DocumentoStiloVolist {
    file: string
    filename: string
    oggetto: string
    numero: string
    anno: string
    data: string
  }
  