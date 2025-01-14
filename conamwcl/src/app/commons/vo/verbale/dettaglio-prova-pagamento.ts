export interface DatiProvaPagamentoVO {
    allegatoFields: AllegatoField[]
    config: Config[]
    edit: boolean
  }
  
  export interface AllegatoField {
    booleanValue: any
    numberValue?: number
    stringValue?: string
    dateValue?: string
    dateTimeValue: any
    fieldType: FieldType
    idField: number
    genericValue?: string
  }
  
  export interface FieldType {
    id: number
    denominazione: string
  }
  
  export interface Config {
    idTipo: number
    label: string
    fieldType: FieldType
    maxLength?: number
    scale: any
    required: boolean
    riga: number
    idFonteElenco: any
    ordine: number
    idField: number
  }
 