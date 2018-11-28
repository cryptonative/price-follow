package mx.com.trader;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.trader.vo.SoporteResistenciaVO;

public class MainActivity extends AppCompatActivity {

    protected TextView mTextMessage;
    protected TextView mTextVolumen;
    protected TextView mTextContador;
    protected TextView mTextSoporte;
    protected TextView mTextResistencia;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("Home");
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextVolumen = (TextView) findViewById(R.id.volumen);
        mTextContador = (TextView) findViewById(R.id.contador);
        mTextSoporte = (TextView) findViewById(R.id.soporte);
        mTextResistencia = (TextView) findViewById(R.id.resistencia);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        List<SoporteResistenciaVO> soportes = setSoportesResistencias();

        Log.d("pos", "Validando permisos a Internet");
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
            BinanceApiWebSocketClient client = factory.newWebSocketClient();

            TareaAsync tareaAsync = new TareaAsync();
            tareaAsync.setActivity(MainActivity.this);
            tareaAsync.setPair("XRPUSDT");
            tareaAsync.setSoporteResistenciaVO(soportes);
            tareaAsync.setIndiceSoporte(obtenerIndiceSoporte(soportes, new BigDecimal("0.58325")));
            tareaAsync.execute(client);
        } else {
            Toast.makeText(MainActivity.this, "Falta, permiso a Internet", Toast.LENGTH_SHORT);
        }

    }

    private Integer obtenerIndiceSoporte(List<SoporteResistenciaVO> soportes, BigDecimal precioToken) {
        Integer indice = 0;

        for(SoporteResistenciaVO soporte : soportes) {
            if (soporte.getValor().compareTo(precioToken) > 0) {
                indice--;
                mTextSoporte.setText("Soporte: " + soportes.get(indice).getValor().toString());
                mTextResistencia.setText("Resistencia: " + soportes.get(indice+1).getValor().toString());
                return indice;
            } else {
                indice++;
            }
        }
        return indice;
    }

    /**
     *
     * @return
     */
    private List<SoporteResistenciaVO> setSoportesResistencias() {

        List<SoporteResistenciaVO> soportes = new ArrayList<>();

        soportes.add(setSoporteResistenciaVO(new BigDecimal("0.57662")));
        soportes.add(setSoporteResistenciaVO(new BigDecimal("0.57823")));
        soportes.add(setSoporteResistenciaVO(new BigDecimal("0.57965")));
        soportes.add(setSoporteResistenciaVO(new BigDecimal("0.58229")));
        soportes.add(setSoporteResistenciaVO(new BigDecimal("0.58355")));
        soportes.add(setSoporteResistenciaVO(new BigDecimal("0.58694")));
        soportes.add(setSoporteResistenciaVO(new BigDecimal("0.58876")));
        soportes.add(setSoporteResistenciaVO(new BigDecimal("0.59106")));

        Log.d("soporte", "Se cargan soportes: ");

        return soportes;
    }

    /**
     *
     * @param valor
     * @return
     */
    private SoporteResistenciaVO setSoporteResistenciaVO(BigDecimal valor) {
        SoporteResistenciaVO vo = new SoporteResistenciaVO();
        vo.setValor(valor);
        return vo;
    }
}
