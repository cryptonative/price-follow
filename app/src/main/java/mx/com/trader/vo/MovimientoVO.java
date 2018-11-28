package mx.com.trader.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class MovimientoVO implements Serializable {

    private Boolean isVenta;

    private Double porcetajeVariacion;

    private BigDecimal stopLoss;
    private BigDecimal takeProfit;
    private BigDecimal reIngreso;
    private BigDecimal precioIngreso;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;

    private String tipoIngreso;
    private String nextStep;

    public Boolean getVenta() {
        return isVenta;
    }

    public void setVenta(Boolean venta) {
        isVenta = venta;
    }

    public Double getPorcetajeVariacion() {
        return porcetajeVariacion;
    }

    public void setPorcetajeVariacion(Double porcetajeVariacion) {
        this.porcetajeVariacion = porcetajeVariacion;
    }

    public BigDecimal getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(BigDecimal stopLoss) {
        this.stopLoss = stopLoss;
    }

    public BigDecimal getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(BigDecimal takeProfit) {
        this.takeProfit = takeProfit;
    }

    public BigDecimal getReIngreso() {
        return reIngreso;
    }

    public void setReIngreso(BigDecimal reIngreso) {
        this.reIngreso = reIngreso;
    }

    public BigDecimal getPrecioIngreso() {
        return precioIngreso;
    }

    public void setPrecioIngreso(BigDecimal precioIngreso) {
        this.precioIngreso = precioIngreso;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }
}
