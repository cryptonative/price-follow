package mx.com.trader;

import android.os.AsyncTask;
import android.util.Log;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mx.com.trader.constante.Constante;
import mx.com.trader.vo.SoporteResistenciaVO;

/**
 * Created by Admin on 15/05/2017.
 */
public class TareaAsync  extends AsyncTask<BinanceApiWebSocketClient, String, Void> {

    /**
     * Variables para el envio desde la actividad origen.
     */
    private MainActivity activity;
    private String pair;
    private List<SoporteResistenciaVO> soporteResistenciaVO;
    private Integer indiceSoporte;

    /**
     * Variables para el uso de la clase.
     */
    private Map<Long, Candlestick> candlesticksCache;
    private Long openTimeAnterior;
    Integer volAnt = 0;
    Integer contador = 0;

    @Override
    protected Void doInBackground(BinanceApiWebSocketClient... client) {
        this.candlesticksCache = new TreeMap<>();

        client[0].onCandlestickEvent(pair.toLowerCase(), CandlestickInterval.ONE_MINUTE, response -> {

            Long openTime = response.getOpenTime();
            Long inc = 0L;

            Candlestick candlestick = candlesticksCache.get(openTime);
            if (candlestick == null) {
                // new candlestick
                if (candlesticksCache.size()>0) {
                    Candlestick candleStickAnterior = candlesticksCache.get(this.openTimeAnterior);

                    String [] parteEntera = candleStickAnterior.getVolume().split("\\.");
                    volAnt = Integer.parseInt(parteEntera[0]);
                }
                candlestick = new Candlestick();
                contador = 0;
            }
            // update candlestick with the stream data
            setCandlestick(candlestick, response);

            this.openTimeAnterior = response.getOpenTime();

            String [] parteEnteraNew = response.getVolume().split("\\.");
            Integer volNew = Integer.parseInt(parteEnteraNew[0]);

            if (volAnt.compareTo(0)>0) {
                inc = Long.valueOf((((100 * volNew) / volAnt) - 100));
            }
            // Store the updated candlestick in the cache
            candlesticksCache.put(openTime, candlestick);
            contador++;

            // Analiza el precio actual.
            String cercania = validaPrecioActual(candlestick.getClose());
            Double variacion = getPorcetanjeVariacion(BigDecimal.ZERO, BigDecimal.ZERO);

            this.publishProgress(pair + ": " + candlestick.getClose(), contador.toString(), candlestick.getNumberOfTrades().toString());
        });
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {

        this.activity.mTextMessage.setText("Trades: " + values[2]);
        this.activity.mTextContador.setText("Cont: " + values[1]);

        if (this.pair.endsWith("USDT")) {
            this.activity.mTextVolumen.setText(formatUSD(values[0], Constante.xrp));
        } else {
            this.activity.mTextVolumen.setText(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Void avoid) {

    }

    /**
     * Formatea a los decimales indicados.
     * @param cadenaOriginal
     * @param decimales
     * @return
     */
    private String formatUSD(String cadenaOriginal, int decimales) {
        return cadenaOriginal.substring(0,cadenaOriginal.indexOf(".")+decimales);
    }

    private String validaPrecioActual(String precioActual) {
        BigDecimal precio = new BigDecimal(precioActual);

        return "Se acerca a soporte y resistencia a los " ;
    }

    // Codigo para getters & setters de la clase.

    public void setSoporteResistenciaVO(List<SoporteResistenciaVO> soporteResistenciaVO) {
        this.soporteResistenciaVO = soporteResistenciaVO;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public void setIndiceSoporte(Integer indiceSoporte) {
        this.indiceSoporte = indiceSoporte;
    }


    /**
     *
     * Obtiene el porcentaje en el cual varia un precioIngreso, respecto al precio actual,
     * valores de precioActual mayores a porcetajeIngreso dan porcentaje positivo.
     * valores de precioActual menores a porcetajeIngreso dan porcentaje negativo.
     *
     * @param precioIngreso
     * @param precioActual
     * @return
     */
    private Double getPorcetanjeVariacion(BigDecimal precioIngreso, BigDecimal precioActual) {
        return 100.0 - precioActual.multiply(BigDecimal.valueOf(100.0)).divide(precioIngreso).doubleValue();
    }

    private Candlestick setCandlestick(Candlestick candlestick, CandlestickEvent response) {

        candlestick.setOpenTime(response.getOpenTime());
        candlestick.setOpen(response.getOpen());
        candlestick.setLow(response.getLow());
        candlestick.setHigh(response.getHigh());
        candlestick.setClose(response.getClose());
        candlestick.setCloseTime(response.getCloseTime());
        candlestick.setVolume(response.getVolume());
        candlestick.setNumberOfTrades(response.getNumberOfTrades());
        candlestick.setQuoteAssetVolume(response.getQuoteAssetVolume());
        candlestick.setTakerBuyQuoteAssetVolume(response.getTakerBuyQuoteAssetVolume());
        candlestick.setTakerBuyBaseAssetVolume(response.getTakerBuyQuoteAssetVolume());

        return candlestick;
    }
}
