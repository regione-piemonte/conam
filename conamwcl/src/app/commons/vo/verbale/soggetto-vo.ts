import { RegioneVO, ComuneVO, RuoloVO, ProvinciaVO, NazioneVO, StatoSoggettoOrdinanzaVO, StatoOrdinanzaVO } from "../../../commons/vo/select-vo";
import { RelataNotifica } from "../relata-notifica-vo";
import { MinSoggettoVO } from "./min-soggetto-vo";

export class SoggettoVO extends MinSoggettoVO {
	public regioneResidenza: RegioneVO;
	public comuneResidenza: ComuneVO;
	public provinciaResidenza: ProvinciaVO;
	public indirizzoResidenza: string;
	public cap: string;
	public civicoResidenza: string;
	public nazioneResidenza: NazioneVO;
	public residenzaEstera: boolean;
	public denomComuneResidenzaEstero: string;
	public ruolo: RuoloVO;

	public idStas: number;
	public id: number;
	public idSoggettoVerbale: number;
	public idSoggettoOrdinanza: number;
	public idPianoRateizzazione: number;
	public numDetOrdinanza: string;
	public statoOrdinanza: StatoOrdinanzaVO;
	public statoSoggettoOrdinanza: StatoSoggettoOrdinanzaVO;
	public noteSoggetto: string;

	////
	public importoVerbale: number;
	public importoResiduoVerbale: number;
	///

	public indirizzoResidenzaStas: string;
	public civicoResidenzaStas: string;
	public capStas: string;

	public pianoRateizzazioneCreato: boolean;
	public verbaleAudizioneCreato: boolean;
	public idAllegatoVerbaleAudizione: number;

	public superatoIlMassimo:boolean;

	public importoSpeseProcessuali: number;
	public importoTitoloSanzione: number;
	public listaOrdinanze?:any
	public comuneNascitaValido: boolean;
	public verbale: any;
	public hasMasterIstanza: boolean;
	public relataNotifica: RelataNotifica
	constructor() {
		super();
		if (!this.regioneResidenza) this.regioneResidenza = new RegioneVO();
		if (!this.comuneResidenza) this.comuneResidenza = new ComuneVO();
		if (!this.provinciaResidenza) this.provinciaResidenza = new ProvinciaVO();
		if (!this.ruolo) this.ruolo = new RuoloVO();
		if (!this.statoSoggettoOrdinanza) this.statoSoggettoOrdinanza = new StatoSoggettoOrdinanzaVO();
		if (!this.nazioneResidenza ) this.nazioneResidenza = new NazioneVO();
		if (!this.statoOrdinanza ) this.statoOrdinanza = new StatoOrdinanzaVO();
	}

	static editSoggettoFromSoggetto(soggetto: SoggettoVO, data: SoggettoVO): SoggettoVO {
		let updatedSoggetto = {...soggetto, ...data};
		if (data.ruolo == null) updatedSoggetto.ruolo = new RuoloVO();
		if (data.regioneResidenza == null) updatedSoggetto.regioneResidenza = new RegioneVO();
		if (data.provinciaResidenza == null) updatedSoggetto.provinciaResidenza = new ProvinciaVO();
		if (data.comuneResidenza == null) updatedSoggetto.comuneResidenza = new ProvinciaVO();
		if (data.regioneNascita == null) updatedSoggetto.regioneNascita = new RegioneVO();
		if (data.comuneNascita == null) updatedSoggetto.comuneNascita = new ComuneVO();
		if (data.provinciaNascita == null) updatedSoggetto.provinciaNascita = new ProvinciaVO();
		if (data.nazioneNascita == null) updatedSoggetto.nazioneNascita = new NazioneVO();
		if (data.nazioneResidenza == null) updatedSoggetto.nazioneResidenza = new NazioneVO();
		if (data.statoOrdinanza == null ) updatedSoggetto.statoOrdinanza = new StatoOrdinanzaVO();
		return updatedSoggetto;
	}

}