package mx.com.trader.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.binance.api.client.domain.general.SymbolInfo;

import java.util.ArrayList;

public class SymbolsAdapter extends ArrayAdapter<SymbolInfo> {

    public SymbolsAdapter(@NonNull Context context, int resource, ArrayList<SymbolInfo> simbolos) {
        super(context, resource, simbolos);
    }


}
