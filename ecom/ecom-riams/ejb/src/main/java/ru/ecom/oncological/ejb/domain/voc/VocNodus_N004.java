package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import javax.persistence.Entity;

/**N004 - классификатор Nodus*/
@Entity
public class VocNodus_N004 extends VocBaseFederal {

    private Integer id_voc;
    private String ds;

    public Integer getId_voc() {
        return id_voc;
    }
    public void setId_voc(Integer id_voc) {
        this.id_voc = id_voc;
    }

    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }
}


