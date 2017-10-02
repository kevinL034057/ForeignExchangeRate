package lyc.foreignexchangerate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kk on 2017/5/21.
 */

public class CurrencyAdapter extends  RecyclerView.Adapter<CurrencyAdapter.ViewHolder>{

    //DecimalFormat formatter = new DecimalFormat("#.##00");

    private List<Currencydata> mcurrencydatas;

    public CurrencyAdapter(List<Currencydata> currencydatas) {
        this.mcurrencydatas = currencydatas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView currencyTextview;
        TextView valueTextview;

        public ViewHolder(View itemView) {
            super(itemView);
            currencyTextview = (TextView)itemView.findViewById(R.id.currency);
            valueTextview = (TextView)itemView.findViewById(R.id.value);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DecimalFormat formatter = new DecimalFormat("0.000");

        Currencydata cucydata = mcurrencydatas.get(position);
        holder.currencyTextview.setText(cucydata.getCurrency());
        holder.valueTextview.setText(formatter.format(cucydata.getValue()));

    }

    @Override
    public int getItemCount() {
        return mcurrencydatas.size();
    }


}
