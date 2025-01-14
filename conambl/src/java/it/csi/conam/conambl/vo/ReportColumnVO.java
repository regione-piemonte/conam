/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author 73405
 * @date 02 feb 2024
 */
public class ReportColumnVO extends ParentVO {

    public enum ColumnName{
        
		NUMERO_PROTOCOLLO("numeroProtocollo"),
		NUMERO_VERBALE("numero"), //
		ANNO_ACCERTAMENTO("annoAccertamento"), //
		ENTE_RIF_NOR("enteRiferimentiNormativi"), //
		AMBITO("ambiti"),//
		LEGGE_VIOLATA("leggeViolata"),//
		COMUNE_ENTE_ACC("comuneEnteAccertatore"), //
		STATO("stato.denominazione"), //
		ENTE_ACCERTATORE("enteAccertatore.denominazione"), //
		ASSEGNATARIO("assegnatario.denominazione"), //
		DATA_ORA_ACCERTAMENTO("dataOraAccertamento"), //
		DATA_PROCESSO("dataProcesso"), // 
		TRASGRESSORI("trasgressori"), // 
		OBBLIGATI("obbligati");//

        private String columnName;
        ColumnName(String name){
            columnName = name;
        }
        public String getColumnName() {
            return columnName;
        }
        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
        public static ColumnName fromString(String text) {
            for (ColumnName c : ColumnName.values()) {
                if (c.columnName.equalsIgnoreCase(text)) {
                    return c;
                }
            }
            return null;
        }
    };

	private String columnName;
	private String displayName;
	private boolean checked;
	private Long order;
    

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getColumnName() {
        return columnName;
    }

	public ColumnName getENUMColumnName() {
        return ColumnName.fromString(columnName);
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
