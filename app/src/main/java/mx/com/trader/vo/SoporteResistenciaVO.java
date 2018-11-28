package mx.com.trader.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
public class SoporteResistenciaVO implements Serializable {

    private BigDecimal valor;
    private Integer hits;

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getHits() {
        return hits;
    }
}
