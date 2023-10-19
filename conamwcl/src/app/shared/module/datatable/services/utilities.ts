import { Injectable } from "@angular/core";

@Injectable()
export class UtilsService {

    private _uidCounter:number;
    private _ids:Array<string>;

    private init(){
        this._uidCounter = 0;
        this._ids = [];
    }

    byString = function(o:any, s:string):any {
        s = s.replace(/\[(\w+)\]/g, '.$1').replace(/^\./, '');
        let a = s.split('.');
        return a.reduce((xs, x) => (xs && xs[x] != null) ? xs[x] : null, o);
    }

    byStringGetDate = function(o:any, s:string):any {
        const dateString = this.byString(o,s);
        if(dateString){
            const tmpDate=dateString.split('/');
            return  new Date(tmpDate[1]+'/'+tmpDate[0]+'/'+tmpDate[2]);
        }else{
            return new Date('1900-01-01');
        }
    }
	
	spawnNewUID() {
		return this._uidCounter++;
	}

    spawnId() {
        let id = Math.floor(1000000000 + Math.random() * 9000000000).toString(36)
        if(this._ids.length > 0 && this._ids.includes(id))
            id = this.spawnId();
        else 
            this._ids.push(id);
        return id;
    }

    constructor() {
        this.init();
    }
}